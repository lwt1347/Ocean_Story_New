package com.plank.user.ocean_story;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {



    //SurfaceView surfaceView;
    GameMain gameMain; // 게임 화면
    Button button_Pause;
    Button button_Shark;
    Button button_NextStage;
    Button button_Heal;


    //효과음
    private SoundPool soundPool = new SoundPool(20, AudioManager.STREAM_ALARM, 0); //사운드 앞에 1은 하나만 가져다 놓겠다는 뜻. 나중에 추가 요망
    private int sound_Effect[] = new int[10];                        //효과음

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        //인텐트 받는 작업을 수행하고

        //게임메인 뷰를 켠다. 게임이 동작하는 구간.
        setContentView(R.layout.activity_game);

        gameMain = (GameMain)findViewById(R.id.GameMainView);

        //퍼지 버튼
        button_Pause = (Button)findViewById(R.id.pause_Button);
        button_Shark =  (Button)findViewById(R.id.call_FriendShark_Button);
        button_NextStage = (Button)findViewById(R.id.next_Stage);
        button_Heal = (Button)findViewById(R.id.heal_Button);

        //음향
        sound_Effect[0] = soundPool.load(this, R.raw.effect_window_sound, 1);      //팝1
        sound_Effect[1] = soundPool.load(this, R.raw.fail, 1);      //팝1


        //_gGameMain.setZOrderOnTop(false);

        /*
        // 슬라이딩 됨 주석 다시 달고 정지 눌렀을때 이 화면 불러와야함
        setContentView(R.layout.menuslidingpanel);      //화면에 뷰를올린다  setContentView
        translateDownAnim = AnimationUtils.loadAnimation(this, R.anim.translate_down);
        slidingMenuPanel = (LinearLayout) findViewById(R.id.menuslidingpanel);
        slidingMenuPanel.setVisibility(View.VISIBLE);
        slidingMenuPanel.startAnimation(translateDownAnim);
        */


//        KeyguardManager km = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
//        boolean isScreen = km.inKeyguardRestrictedInputMode();
//
//        if(isScreen){
//
//
//        }else {
//
//
//        }

        intent = new Intent(this, menu_Sliding_Panel.class);

        _context_Send = this;

        ad = (AudioManager)getSystemService(AUDIO_SERVICE);


        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        SharedPreferences pref;
        //설정 에서 토토리얼 모드 인지 아닌지.
        pref = this.getSharedPreferences("pref", Activity.MODE_APPEND);
        tuto = pref.getInt("tuto",0);
        vive = pref.getInt("vive",0);
        b_Vol = pref.getInt("bs",0);
        e_Vol = pref.getInt("es",0);

    }

    //진동
    Vibrator vibrator;
    public void set_Vibrator(){

        if(vive == 0) {
            vibrator.vibrate(250);
        }
    }



    Intent intent;

    /**
     * 게임 오버 당시 팝업될 창
     */
    public void popup_Menu(){
        if(gameMain.get_m_Run()) {
            Log.e("e", "ee");


            intent = new Intent(this, menu_Sliding_Panel.class);
            //intent.putExtra("a", mRun);
            startActivityForResult(intent, 0); //-> 일시정지 창을 팝업한다. Menu_Sliding_Panel 호출

            //퍼지 버튼 눌렀을때 이미지 변경
            button_Pause.setBackgroundResource(R.drawable.pause_2);
            gameMain.set_Home_Restart(true);
            gameMain.m_Run_False(true);

        }
    }

    /**
     * 퍼지 버튼
     */
    public void onButtonPause(View view){

//        soundPool.play(sound_Effect[0], 0.7F, 0.7F, 0, 0, 1.0F);   //성공
        //게임 진화창이 떴을때 눌리면 안된다.
        if(gameMain.get_m_Run()) {
        Log.e("e", "ee");


            intent = new Intent(this, menu_Sliding_Panel.class);
            //intent.putExtra("a", mRun);
            startActivityForResult(intent, 0); //-> 일시정지 창을 팝업한다. Menu_Sliding_Panel 호출

            //퍼지 버튼 눌렀을때 이미지 변경
            button_Pause.setBackgroundResource(R.drawable.pause_2);
            gameMain.set_Home_Restart(true);
            gameMain.m_Run_False(true);

            background_Sound_Cont = false;


        }
    }
    public void pause(){

//        soundPool.play(sound_Effect[0], 0.7F, 0.7F, 0, 0, 1.0F);   //성공
        //게임 진화창이 떴을때 눌리면 안된다.
        if(gameMain.get_m_Run()) {
            Log.e("e", "ee!@!");


            intent = new Intent(this, menu_Sliding_Panel.class);
            //intent.putExtra("a", mRun);
            startActivityForResult(intent, 0); //-> 일시정지 창을 팝업한다. Menu_Sliding_Panel 호출

            //퍼지 버튼 눌렀을때 이미지 변경
//            button_Pause.setBackgroundResource(R.drawable.pause_2); 있으면 안됨

            gameMain.m_Run_False(true);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //데이터 저장

        Log.e("a","back");

        //음악끄기
        gameMain.exit();

    }



    AudioManager ad = null;
    int b_Vol = 0;
    int e_Vol = 0;
    boolean sound = true;   //t = 사운드 온, f = 음소거
    int tuto = 0;   //0 = 튜토리얼 온, 1 = 튜토 x
    int vive = 0;   //0 = 진동 on

    public void set_Sound(boolean set){
        sound = set;
        gameMain.sound_Tuto_Value(e_Vol, b_Vol, tuto, vive);
    }
    public void set_Tuto(int set){
        tuto = set;
        gameMain.sound_Tuto_Value(e_Vol, b_Vol, tuto, vive);
    }
    public void set_Vive(int set){
        vive = set;
        gameMain.sound_Tuto_Value(e_Vol, b_Vol, tuto, vive);
    }



    //음향 설정 시크바 컨트롤
    public static Context _context_Send;

    public void set_Back_Sound(int volume, int e_Vol_T, int tuto){
        Log.e("@","v = " + volume);
        if(volume < 0){
            volume = 0;
        }
        ad.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        b_Vol = volume;

        gameMain.sound_Tuto_Value(e_Vol_T, b_Vol, tuto, vive);

    }
    public void set_Effect_Sound(int volume, int b_Vol_T, int tuto){
        if(volume >= 10){
            volume = 10;
        }
//        ad.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        gameMain.set_Volume(volume);
        e_Vol = volume;

        gameMain.sound_Tuto_Value(e_Vol, b_Vol_T, tuto, vive);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Log.e("@","!@#!@#!");

// ((GameActivity)gameActivity._context_Send).set_Effect_Sound(progress, sound_Check_B);

        //볼륨 이벤트
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:


                if(b_Vol > 0){
                    b_Vol--;
                }
                if(e_Vol > 0){
                    e_Vol--;
                }


                gameMain.set_Volume_Down(e_Vol);
                ad.setStreamVolume(AudioManager.STREAM_MUSIC, b_Vol, 0);
                Log.e("@","D = " + b_Vol);

            break;

            case KeyEvent.KEYCODE_VOLUME_UP:


                if(b_Vol < 10){
                    b_Vol++;
                }
                if(e_Vol < 10){
                    e_Vol++;
                }

                ad.setStreamVolume(AudioManager.STREAM_MUSIC, b_Vol, 0);
                gameMain.set_Volume_Up(e_Vol);
                Log.e("@","U = " + b_Vol);

                break;

            case KeyEvent.KEYCODE_BACK:

                intent = new Intent(this, menu_Sliding_Panel.class);
                //intent.putExtra("a", mRun);
                startActivityForResult(intent, 0); //-> 일시정지 창을 팝업한다. Menu_Sliding_Panel 호출

                //퍼지 버튼 눌렀을때 이미지 변경
                button_Pause.setBackgroundResource(R.drawable.pause_2);




                gameMain.m_Run_False(true);

                Log.e("@", "back");
                home_Chaeck = true;
                break;


        }

//        ((AudioManager)getSystemService(AUDIO_SERVICE)).
//                setStreamVolume(AudioManager.STREAM_MUSIC, 5, AudioManager.FLAG_SHOW_UI);
//        e_Vol = 5;
        gameMain.sound_Tuto_Value(e_Vol, b_Vol, tuto, vive);



        return true;
//        return super.onKeyDown(keyCode, event);
    }

    boolean home_Chaeck = false;

    boolean background_Sound_Cont = true;



    @Override
    protected void onUserLeaveHint() {
        Log.e("@","11111111111111111111111");
        super.onUserLeaveHint();
        if(!home_Chaeck) {


            Log.e("@", "Home키 !!");
//            gameMain.set_Home_Restart(false);
            gameMain.set_Home_Setting();

            //설정창, 옵션창, 일시정치 창, 이 아닐때
            if(background_Sound_Cont){
                //배경음 종료
                gameMain.exit();
            }
            background_Sound_Cont = true;


        }
        home_Chaeck = false;
    }

    /**
     * 사전 버튼
     */
    int[] monster_Explain_Info = new int[40];
    int[] character_Explain_Info = new int[40];


    public void onButtonDictionary(View view){
        Log.e("@","22222222222222222222222222222222");
        monster_Explain_Info = gameMain.monster_Explain_Get();
        character_Explain_Info = gameMain.character_Explain_Get();


        if(gameMain.get_m_Run()) {

            intent = new Intent(getApplicationContext(), dictionary_Panel.class);
            direction = true;
            intent.putExtra("mexplain", monster_Explain_Info);
            intent.putExtra("cexplain", character_Explain_Info);

            //intent.putExtra("a", mRun);
            startActivityForResult(intent, 1002); //-

            //퍼지 버튼 눌렀을때 이미지 변경
            gameMain.set_Home_Restart(true);
            gameMain.m_Run_False(true);

            background_Sound_Cont = false;
        }
    }




    /**
     * 상어 친구 부르기
     */
    public void onButtonFriendSharkCall(View view){

        //게임 진화창이 떴을때 눌리면 안된다.
        if(gameMain.get_m_Run()) {
            if(gameMain.get_Ruby_Count() > 0) {
//            Toast.makeText(getApplicationContext(), "상어 친구 호출", Toast.LENGTH_SHORT).show();
                gameMain.shark_Friend_Call();


                button_Shark.setBackgroundResource(R.drawable.friend_shark_button_2);
                button_Shark.setEnabled(false);

                android.os.Handler h = new android.os.Handler();
                h.postDelayed(shark_Task, 10000);

                background_Sound_Cont = false;
                gameMain.set_Ruby_Minus();
            }else {
                //상어 실패
                set_Vibrator();
                float temp = e_Vol/100.0f;
                Log.e("@",temp + "");
                Log.e("@",e_Vol + "");
                soundPool.play(sound_Effect[1], temp, temp, 0, 0, 1.0F);   //성공
            }
        }

    }

    private Runnable shark_Task = new Runnable() {
        @Override public void run() {
            // 실제 동작
            button_Shark.setBackgroundResource(R.drawable.friend_shark_button_1);
            button_Shark.setEnabled(true);
        }
    };



    /**
     * 넥스트 페이지
     */
    public void onButtonNextStage(View view){
//게임 진화창이 떴을때 눌리면 안된다.
        if(gameMain.get_m_Run()) {
//            Toast.makeText(getApplicationContext(), "넥스트 스테이지", Toast.LENGTH_SHORT).show();
            gameMain.next_Stage_Call();

            button_NextStage.setBackgroundResource(R.drawable.next_button_2);
            button_NextStage.setEnabled(false);

            android.os.Handler h = new android.os.Handler();
            h.postDelayed(nextstage_Task, 2000);
            background_Sound_Cont = false;

        }
    }

    private Runnable nextstage_Task = new Runnable() {
        @Override public void run() {
            // 실제 동작
            button_NextStage.setBackgroundResource(R.drawable.next_button_1);
            button_NextStage.setEnabled(true);
        }
    };

    /**
     * 힐
     */
    public void onButtonHeal(View view){
//게임 진화창이 떴을때 눌리면 안된다.
        if(gameMain.get_m_Run()) {
            if(gameMain.get_Heal_State()) {

                if (gameMain.get_Ruby_Count() > 0) {
//                Toast.makeText(getApplicationContext(), "힐", Toast.LENGTH_SHORT).show();
                    gameMain.Heal_Call();

                    button_Heal.setBackgroundResource(R.drawable.heal_button_2);
                    button_Heal.setEnabled(false);

                    android.os.Handler h = new android.os.Handler();
                    h.postDelayed(heal_Task, 10000);
                    background_Sound_Cont = false;
                    gameMain.set_Ruby_Minus();
                } else {
                    //힐 실패
                    set_Vibrator();

                    float temp = e_Vol / 100.0f;
                    Log.e("@", temp + "");
                    Log.e("@", e_Vol + "");
                    soundPool.play(sound_Effect[1], temp, temp, 0, 0, 1.0F);   //성공

                }

            }
        }
    }

    private Runnable heal_Task = new Runnable() {
        @Override public void run() {
            // 실제 동작
            button_Heal.setBackgroundResource(R.drawable.heal_button_1);
            button_Heal.setEnabled(true);
        }
    };


    /**
     * 값 받아오기 메뉴창에서 계속하기 = 1, 다시하기 = 2
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //게임 진화창이 떴을때 눌리면 안된다.

            super.onActivityResult(requestCode, resultCode, data);

        try{
            Log.e("GameActivity", "GameActivity_e");
            int key = data.getIntExtra("key", 0);



            //퍼지 버튼 원상태
            button_Pause.setBackgroundResource(R.drawable.pause_1);
//            Toast.makeText(getApplicationContext(), "a", Toast.LENGTH_SHORT).show();




            if (key == 1) {
                gameMain.m_Run_True(); //게임 재게
                gameMain.set_Home_Restart(false);
//                gameMain.exit();
//                gameMain.bg_Sound();
            } else if (key == 2) {  //다시 시작

//                finish 가 없음으로 영구추출 정보가 db에 저장되지 않는다.
//                소비된 루비는 저장된다. 알아 볼것

                gameMain.set_Best_Point();
                gameMain.set_Best_Point_2();


                gameMain.re_Start();
                gameMain.set_Home_Restart(false);
                gameMain.m_Run_True();



                button_Shark.setEnabled(true);
                button_Shark.setBackgroundResource(R.drawable.friend_shark_button_1);

                button_Heal.setEnabled(true);
                button_Heal.setBackgroundResource(R.drawable.heal_button_1);

                button_NextStage.setEnabled(true);
                button_NextStage.setBackgroundResource(R.drawable.next_button_1);

                background_Sound_Cont = false;




//                gameMain.exit();
//                gameMain.bg_Sound();
            }
            else if(key == 3){ //종료
                gameMain.re_Start();
                gameMain.exit();
                gameMain.set_Distroy_Run_False();
                gameMain.m_Run_False();
                finish();

            }


        }catch (Exception e){

            gameMain.m_Run_False(true);

            if(option){
                Intent intent = new Intent(this, option_Panel.class);
            }else if(direction){
                Intent intent = new Intent(this, dictionary_Panel.class);
            }else{
                Intent intent = new Intent(this, menu_Sliding_Panel.class);
            }
            startActivityForResult(intent, 0); //-> 일시정지 창을 팝업한다. Menu_Sliding_Panel 호출
            background_Sound_Cont = false;
        }
        option = false;
        pause = false;
        direction = false;
    }

    boolean option = false;
    boolean pause = false;
    boolean direction = false;

    /**
     *  옵션 버튼
     */
    public void onButtonOption(View view){
        if(gameMain.get_m_Run()) {

            intent = new Intent(this, option_Panel.class);

            option = true;

            //intent.putExtra("a", mRun);
            startActivityForResult(intent, 0); //-> 일시정지 창을 팝업한다. Menu_Sliding_Panel 호출


            gameMain.set_Home_Restart(true);
            gameMain.m_Run_False(true);

            background_Sound_Cont = false;

        }
    }


    public void sound_Exit() {
        gameMain.exit();
    }
}
