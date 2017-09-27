package com.example.user.ocean_story;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {



    //SurfaceView surfaceView;
    GameMain gameMain; // 게임 화면
    Button button_Pause;


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


        //음향
        sound_Effect[0] = soundPool.load(this, R.raw.effect_window_sound, 1);      //팝1


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
    }



    Intent intent;
    /**
     * 퍼지 버튼
     */
    public void onButtonPause(View view){

//        soundPool.play(sound_Effect[0], 0.7F, 0.7F, 0, 0, 1.0F);   //성공
        //게임 진화창이 떴을때 눌리면 안된다.
        if(gameMain.get_m_Run()) {
        Log.e("e", "ee");



            //intent.putExtra("a", mRun);
            startActivityForResult(intent, 0); //-> 일시정지 창을 팝업한다. Menu_Sliding_Panel 호출

            //퍼지 버튼 눌렀을때 이미지 변경
            button_Pause.setBackgroundResource(R.drawable.pause_2);

            gameMain.m_Run_False(true);

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
    }



    /**
     * 상어 친구 부르기
     */
    public void onButtonFriendSharkCall(View view){

        //게임 진화창이 떴을때 눌리면 안된다.
        if(gameMain.get_m_Run()) {
//            Toast.makeText(getApplicationContext(), "상어 친구 호출", Toast.LENGTH_SHORT).show();
            gameMain.shark_Friend_Call();
        }

    }

    /**
     * 넥스트 페이지
     */
    public void onButtonNextStage(View view){
//게임 진화창이 떴을때 눌리면 안된다.
        if(gameMain.get_m_Run()) {
            Toast.makeText(getApplicationContext(), "넥스트 스테이지", Toast.LENGTH_SHORT).show();
            gameMain.next_Stage_Call();
        }
    }


    /**
     * 힐
     */
    public void onButtonHeal(View view){
//게임 진화창이 떴을때 눌리면 안된다.
        if(gameMain.get_m_Run()) {
            Toast.makeText(getApplicationContext(), "힐", Toast.LENGTH_SHORT).show();
            gameMain.Heal_Call();
        }
    }


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
            Toast.makeText(getApplicationContext(), "a", Toast.LENGTH_SHORT).show();




            if (key == 1) {
                gameMain.m_Run_True(); //게임 재게
            } else if (key == 2) {  //다시 시작
                gameMain.m_Run_True();
                gameMain.re_Start();
            }

        }catch (Exception e){
            gameMain.m_Run_False(true);
            Intent intent = new Intent(this, menu_Sliding_Panel.class);
            startActivityForResult(intent, 0); //-> 일시정지 창을 팝업한다. Menu_Sliding_Panel 호출

        }


    }





}
