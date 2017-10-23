package com.example.user.ocean_story;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    double info[] = new double[150];

    private SoundPool soundPool = new SoundPool(20, AudioManager.STREAM_ALARM, 0); //사운드 앞에 1은 하나만 가져다 놓겠다는 뜻. 나중에 추가 요망
    private int sound_Effect[] = new int[10];

    //sql 라이트
    SQLiteDatabase database;

    private void recycleView(View view) {
        if(view != null) {
            Drawable bg = view.getBackground();
            if(bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable)bg).getBitmap().recycle();
                view.setBackgroundDrawable(null);
            }
        }
    }


    int dbset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //음향
        pref = this.getSharedPreferences("pref", Activity.MODE_APPEND);
        editor = pref.edit();

        vol_E = pref.getInt("es",0);
        set_Sound(vol_E);




        setContentView(R.layout.activity_main);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //메모리 관리
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.activity_main);
        layout.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.background_start)));


        //음향
        sound_Effect[0] = soundPool.load(this, R.raw.up, 1);      //팝1



        ///////////////////////////////////////////////////////////////////////
        //데이터 베이스는 테이블을 최초 한번만 생성해야 하기 때문에 최초 한번의 정보를 가지고 만든다.
        SharedPreferences dbSetFirst = getSharedPreferences("dbSetFirst", Activity.MODE_APPEND);
        if(dbSetFirst != null){
            dbset = dbSetFirst.getInt("dbSetFirst", -1);
        }
        dbset++;
        ///값 저장
        SharedPreferences.Editor editor = dbSetFirst.edit();
        editor.putInt("dbSetFirst", dbset);
        editor.commit();
        ///////////////////////////////////

        //데이터 베이스 생성 및 오픈
        database = openOrCreateDatabase("oceanstory1.db", MODE_PRIVATE, null);

        if(database != null){
            //db 오픈 되있음.


        }

        Log.e("aa","dbset = "+dbset);
        if(dbset == 0) {
            //데이터 베이스 생성시에 한번만 실행해야 한다.
            String sql = "create table maincharacterinfo(_id integer PRIMARY KEY autoincrement, ruby integer,money double,structuredamage integer, dragdamage integer, urchinresistance integer,  lightningresistance integer, crocodileresistance integer, ft1 integer, ft2 integer, ft3 integer, ft4 integer, ft5 integer, ft6 integer, ft7 integer, ft8 integer, ft9 integer, ft10 integer, st1 integer, st2 integer, st3 integer, st4 integer, st5 integer, st6 integer, st7 integer, st8 integer, st9 integer, st10 integer, mt1 integer, mt2 integer, mt3 integer, mt4 integer, mt5 integer, mt6 integer, mt7 integer, mt8 integer, mt9 integer, mt10 integer, ftb1 integer, ftb2 integer, ftb3 integer, ftb4 integer, ftb5 integer, ftb6 integer, ftb7 integer, ftb8 integer, ftb9 integer, ftb10 integer, stb1 integer, stb2 integer, stb3 integer, stb4 integer, stb5 integer, stb6 integer, stb7 integer, stb8 integer, stb9 integer, stb10 integer, mtb1 integer, mtb2 integer, mtb3 integer, mtb4 integer, mtb5 integer, mtb6 integer, mtb7 integer, mtb8 integer, mtb9 integer, mtb10 integer, me1 integer , me2 integer, me3 integer, me4 integer, me5 integer, me6 integer, me7 integer, me8 integer, me9 integer, me10 integer, me11 integer, me12 integer, me13 integer, me14 integer, me15 integer, me16 integer, me17 integer, me18 integer, me19 integer, me20 integer, ce1 integer, ce2 integer, ce3 integer, ce4 integer, ce5 integer, ce6 integer, ce7 integer, ce8 integer, ce9 integer, ce10 integer, ce11 integer, ce12 integer, ce13 integer, ce14 integer, ce15 integer, ce16 integer, ce17 integer, ce18 integer, ce19 integer, ce20 integer, ce21 integer, ce22 integer, ce23 integer, ce24 integer, ce25 integer, ce26 integer, ce27 integer, ce28 integer, ce29 integer, ce30 integer, ce31 integer, ce32 integer, ce33 integer, ce34 integer, ce35 integer)";
            database.execSQL(sql);
            insertData(1000, 0, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0);
        }

        selectData();

    }

    /**
     * 데이터 베이스
     */
    //값 집어넣기
    public void insertData(int ruby,  double money, int structuredamage, int dragdamage, int urchinresistance, int lightningresistance, int crocodileresistance,
                           int ft1 ,int ft2, int ft3, int ft4, int ft5, int ft6, int ft7, int ft8, int ft9, int ft10,
                           int st1, int st2, int st3, int st4, int st5, int st6, int st7, int st8, int st9, int st10,
                           int mt1, int mt2, int mt3, int mt4, int mt5, int mt6, int mt7, int mt8, int mt9, int mt10,
                           int ftb1 ,int ftb2, int ftb3, int ftb4, int ftb5, int ftb6, int ftb7, int ftb8, int ftb9, int ftb10,
                           int stb1, int stb2, int stb3, int stb4, int stb5, int stb6, int stb7, int stb8, int stb9, int stb10,
                           int mtb1, int mtb2, int mtb3, int mtb4, int mtb5, int mtb6, int mtb7, int mtb8, int mtb9, int mtb10,
                           int me1, int me2, int me3, int me4, int me5, int me6, int me7, int me8, int me9, int me10,
                           int me11, int me12, int me13, int me14, int me15, int me16, int me17, int me18, int me19,  int me20,
                           int ce1, int ce2, int ce3, int ce4, int ce5, int ce6, int ce7, int ce8, int ce9, int ce10,
                           int ce11, int ce12, int ce13, int ce14, int ce15, int ce16, int ce17, int ce18, int ce19, int ce20,
                           int ce21, int ce22, int ce23, int ce24, int ce25, int ce26, int ce27, int ce28, int ce29, int ce30,
                           int ce31, int ce32, int ce33, int ce34, int ce35){

        if(database != null){
            //b = 스킬 추출
            //me = 몬스터 설명창 설명 띄우줄지 말지 0 -> 띄어줌
            //ce = 캐릭터 설명창
            String sql = "insert into maincharacterinfo(ruby, money, structuredamage, dragdamage, urchinresistance, lightningresistance, crocodileresistance, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, st1, st2, st3, st4, st5, st6, st7, st8, st9, st10, mt1, mt2, mt3, mt4, mt5, mt6, mt7, mt8, mt9, mt10, ftb1, ftb2, ftb3, ftb4, ftb5, ftb6, ftb7, ftb8, ftb9, ftb10, stb1, stb2, stb3, stb4, stb5, stb6, stb7, stb8, stb9, stb10, mtb1, mtb2, mtb3, mtb4, mtb5, mtb6, mtb7, mtb8, mtb9, mtb10, me1, me2, me3, me4, me5, me6, me7, me8, me9, me10, me11, me12, me13, me14, me15, me16, me17, me18, me19, me20, ce1, ce2, ce3, ce4, ce5, ce6, ce7, ce8, ce9, ce10, ce11, ce12, ce13, ce14, ce15, ce16, ce17, ce18, ce19, ce20, ce21, ce22, ce23, ce24, ce25, ce26, ce27, ce28, ce29, ce30, ce31, ce32, ce33, ce34, ce35) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Object[] params = { ruby, money, structuredamage, dragdamage, urchinresistance, lightningresistance, crocodileresistance,
                    ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10,
                    st1, st2, st3, st4, st5, st6, st7, st8, st9, st10,
                    mt1, mt2, mt3, mt4, mt5, mt6, mt7, mt8, mt9, mt10,
                    ftb1, ftb2, ftb3, ftb4, ftb5, ftb6, ftb7, ftb8, ftb9, ftb10,
                    stb1, stb2, stb3, stb4, stb5, stb6, stb7, stb8, stb9, stb10,
                    mtb1, mtb2, mtb3, mtb4, mtb5, mtb6, mtb7, mtb8, mtb9, mtb10,
                    me1, me2, me3, me4, me5, me6, me7, me8, me9, me10,
                    me11, me12, me13, me14, me15, me16, me17, me18, me19, me20,
                    ce1, ce2, ce3, ce4, ce5, ce6, ce7, ce8, ce9, ce10,
                    ce11, ce12, ce13, ce14, ce15, ce16, ce17, ce18, ce19, ce20,
                    ce21, ce22, ce23, ce24, ce25, ce26, ce27, ce28, ce29, ce30,
                    ce31, ce32, ce33, ce34, ce35,};

            database.execSQL(sql, params);
        }
    }

    //값 가져오기
    public void selectData(){
        if(database != null){
            String sql = "select ruby, money, structuredamage, dragdamage, urchinresistance, lightningresistance, crocodileresistance, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, st1, st2, st3, st4, st5, st6, st7, st8, st9, st10, mt1, mt2, mt3, mt4, mt5, mt6, mt7, mt8, mt9, mt10, ftb1, ftb2, ftb3, ftb4, ftb5, ftb6, ftb7, ftb8, ftb9, ftb10, stb1, stb2, stb3, stb4, stb5, stb6, stb7, stb8, stb9, stb10, mtb1, mtb2, mtb3, mtb4, mtb5, mtb6, mtb7, mtb8, mtb9, mtb10, me1, me2, me3, me4, me5, me6, me7, me8, me9, me10, me11, me12, me13, me14, me15, me16, me17, me18, me19, me20, ce1, ce2, ce3, ce4, ce5, ce6, ce7, ce8, ce9, ce10, ce11, ce12, ce13, ce14, ce15, ce16, ce17, ce18, ce19, ce20, ce21, ce22, ce23, ce24, ce25, ce26, ce27, ce28, ce29, ce30, ce31, ce32, ce33, ce34, ce35 from maincharacterinfo";

            Cursor cursor = database.rawQuery(sql, null);
            Log.e("e", " : " + cursor.getCount());
            cursor.moveToNext();
            //37 -> 67 -> 87 -> 112 -> 122
            for(int i=0; i<122; i++){
                info[i] = cursor.getInt(i);

            }
//            Toast.makeText(getApplicationContext(), "" + "db 값" + info[1], Toast.LENGTH_SHORT).show();


            cursor.close();
        }
    }

    int get_Item_Integer[] = new int[150];

    //인텐트 결과값 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    try {
        // 넘어갔던 화면에서 되돌아 왔을 때
        if (resultCode == RESULT_OK) { // 정상 반환일 경우에만 동작하겠다
//            Toast.makeText(getApplicationContext(), "??", Toast.LENGTH_SHORT).show();
//            int num = data.getIntExtra("hap", 0);
//            info = data.getIntArrayExtra("info");
            Log.e("@", "@" + info[0] + "씨발");
            double get_Item[] = data.getDoubleArrayExtra("item");
//            Toast.makeText(getApplicationContext(), "" + get_Item[0], Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "" + get_Item[1], Toast.LENGTH_SHORT).show();
            info[0] = get_Item[0];
            info[1] = get_Item[1];
            //0 = money, 1 = ruby
            Log.e("@", "@" + info[1]);

            Log.e("@",0 + " = " + get_Item[0]);
            Log.e("@",1 + " = " + get_Item[1]);
            //77/87
            for(int i=2; i<87; i++){
                info[i] = get_Item[i];
                Log.e("@",i + " = " + get_Item[i]);
            }

//            SharedPreferences pref = GameMain._context.getSharedPreferences("pref", Activity.MODE_APPEND);
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putInt("money", get_Item[0]);

//            if(database != null){

            for(int i=0; i< 87; i++){
                get_Item_Integer[i] = (int)get_Item[i];
            }

//            String sql = "UPDATE maincharacterinfo SET money = '" + get_Item[1] + "', ruby = '" + get_Item_Integer[0] + "'"
//+ "', ftb1 = '" + get_Item_Integer[2] + "'" + "', ftb2 = '" + get_Item_Integer[3] + "'" + "', ftb3 = '" + get_Item_Integer[4] + "'" + "', ftb4 = '" + get_Item_Integer[5] + "'" + "', ftb5 = '" + get_Item_Integer[6] + "'" + "', ftb6 = '" + get_Item_Integer[7] + "'" + "', ftb7 = '" + get_Item_Integer[8] + "'" + "', ftb8 = '" + get_Item_Integer[9] + "'"+ "', ftb9 = '" + get_Item_Integer[10] + "'"+ "', ftb10 = '" + get_Item_Integer[11] + "'"
//+ "', stb1 = '" + get_Item_Integer[12] + "'" + "', stb2 = '" + get_Item_Integer[13] + "'" + "', stb3 = '" + get_Item_Integer[14] + "'" + "', stb4 = '" + get_Item_Integer[15] + "'" + "', stb5 = '" + get_Item_Integer[16] + "'" + "', stb6 = '" + get_Item_Integer[17] + "'" + "', stb7 = '" + get_Item_Integer[18] + "'" + "', stb8 = '" + get_Item_Integer[19] + "'"+ "', stb9 = '" + get_Item_Integer[20] + "'"+ "', stb10 = '" + get_Item_Integer[21] + "'"
//+ "', mtb1 = '" + get_Item_Integer[22] + "'" + "', mtb2 = '" + get_Item_Integer[23] + "'" + "', mtb3 = '" + get_Item_Integer[24] + "'" + "', mtb4 = '" + get_Item_Integer[25] + "'" + "', mtb5 = '" + get_Item_Integer[26] + "'" + "', mtb6 = '" + get_Item_Integer[27] + "'" + "', mtb7 = '" + get_Item_Integer[28] + "'" + "', mtb8 = '" + get_Item_Integer[29] + "'"+ "', mtb9 = '" + get_Item_Integer[30] + "'"+ "', mtb10 = '" + get_Item_Integer[31] + "'";

            String sql = "UPDATE maincharacterinfo SET money = '" + get_Item_Integer[1] + "', ruby = '" + get_Item_Integer[0] + "'"
                    + ", ftb1 = '" + get_Item_Integer[2] + "'" + ", ftb2 = '" + get_Item_Integer[3] + "'" + ", ftb3 = '" + get_Item_Integer[4] + "'" + ", ftb4 = '" + get_Item_Integer[5] + "'" + ", ftb5 = '" + get_Item_Integer[6] + "'" + ", ftb6 = '" + get_Item_Integer[7] + "'" + ", ftb7 = '" + get_Item_Integer[8] + "'" + ", ftb8 = '" + get_Item_Integer[9] + "'"+ ", ftb9 = '" + get_Item_Integer[10] + "'"+ ", ftb10 = '" + get_Item_Integer[11] + "'"
                    + ", stb1 = '" + get_Item_Integer[12] + "'" + ", stb2 = '" + get_Item_Integer[13] + "'" + ", stb3 = '" + get_Item_Integer[14] + "'" + ", stb4 = '" + get_Item_Integer[15] + "'" + ", stb5 = '" + get_Item_Integer[16] + "'" + ", stb6 = '" + get_Item_Integer[17] + "'" + ", stb7 = '" + get_Item_Integer[18] + "'" + ", stb8 = '" + get_Item_Integer[19] + "'"+ ", stb9 = '" + get_Item_Integer[20] + "'"+ ", stb10 = '" + get_Item_Integer[21] + "'"
                    + ", mtb1 = '" + get_Item_Integer[22] + "'" + ", mtb2 = '" + get_Item_Integer[23] + "'" + ", mtb3 = '" + get_Item_Integer[24] + "'" + ", mtb4 = '" + get_Item_Integer[25] + "'" + ", mtb5 = '" + get_Item_Integer[26] + "'" + ", mtb6 = '" + get_Item_Integer[27] + "'" + ", mtb7 = '" + get_Item_Integer[28] + "'" + ", mtb8 = '" + get_Item_Integer[29] + "'"+ ", mtb9 = '" + get_Item_Integer[30] + "'"+ ", mtb10 = '" + get_Item_Integer[31] + "'"
                    + ", me1 = '" + get_Item_Integer[32] + "'"+ ", me2 = '" + get_Item_Integer[33] + "'"+ ", me3 = '" + get_Item_Integer[34] + "'"+ ", me4 = '" + get_Item_Integer[35] + "'"+ ", me5 = '" + get_Item_Integer[36] + "'"+ ", me6 = '" + get_Item_Integer[37] + "'"+ ", me7 = '" + get_Item_Integer[38] + "'"+ ", me8 = '" + get_Item_Integer[39] + "'"+ ", me9 = '" + get_Item_Integer[40] + "'"+ ", me10 = '" + get_Item_Integer[41] + "'"
                    + ", me11 = '" + get_Item_Integer[42] + "'"+ ", me12 = '" + get_Item_Integer[43] + "'"+ ", me13 = '" + get_Item_Integer[44] + "'"+ ", me14 = '" + get_Item_Integer[45] + "'"+ ", me15 = '" + get_Item_Integer[46] + "'"+ ", me16 = '" + get_Item_Integer[47] + "'"+ ", me17 = '" + get_Item_Integer[48] + "'"+ ", me18 = '" + get_Item_Integer[49] + "'"+ ", me19 = '" + get_Item_Integer[50] + "'"+ ", me20 = '" + get_Item_Integer[51] + "'"
                    + ", ce1 = '" + get_Item_Integer[52] + "'"+ ", ce2 = '" + get_Item_Integer[53] + "'"+ ", ce3 = '" + get_Item_Integer[54] + "'"+ ", ce4 = '" + get_Item_Integer[55] + "'"+ ", ce5 = '" + get_Item_Integer[56] + "'"+ ", ce6 = '" + get_Item_Integer[57] + "'"+ ", ce7 = '" + get_Item_Integer[58] + "'"+ ", ce8 = '" + get_Item_Integer[59] + "'"+ ", ce9 = '" + get_Item_Integer[60] + "'"+ ", ce10 = '" + get_Item_Integer[61] + "'"
                    + ", ce11 = '" + get_Item_Integer[62] + "'"+ ", ce12 = '" + get_Item_Integer[63] + "'"+ ", ce13 = '" + get_Item_Integer[64] + "'"+ ", ce14 = '" + get_Item_Integer[65] + "'"+ ", ce15 = '" + get_Item_Integer[66] + "'"+ ", ce16 = '" + get_Item_Integer[67] + "'"+ ", ce17 = '" + get_Item_Integer[68] + "'"+ ", ce18 = '" + get_Item_Integer[69] + "'"+ ", ce19 = '" + get_Item_Integer[70] + "'"+ ", ce20 = '" + get_Item_Integer[71] + "'"
                    + ", ce21 = '" + get_Item_Integer[72] + "'"+ ", ce22 = '" + get_Item_Integer[73] + "'"+ ", ce23 = '" + get_Item_Integer[74] + "'"+ ", ce24 = '" + get_Item_Integer[75] + "'"+ ", ce25 = '" + get_Item_Integer[76] + "'"+ ", ce26 = '" + get_Item_Integer[77] + "'"+ ", ce27 = '" + get_Item_Integer[78] + "'"+ ", ce28 = '" + get_Item_Integer[79] + "'"+ ", ce29 = '" + get_Item_Integer[80] + "'"+ ", ce30 = '" + get_Item_Integer[81] + "'"
                    + ", ce31 = '" + get_Item_Integer[82] + "'"+ ", ce32 = '" + get_Item_Integer[83] + "'"+ ", ce33 = '" + get_Item_Integer[84] + "'"+ ", ce34 = '" + get_Item_Integer[85] + "'"+ ", ce35 = '" + get_Item_Integer[86] + "'";


            database.execSQL(sql);


//            }

            selectData();

        }
    }catch (Exception e){
        Log.e("onActivityResult", "onActivityResult");
    }

    }


    /**
     * 터치 이팩트 사운드
     */
    public float sound = 0.0f;
    int vol_E = 0;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    public void set_Sound(int vol){

        if(vol == 10){
            sound = 1.0f;
        }else if(vol == 9){
            sound = 0.9f;
        }else if(vol == 8){
            sound = 0.8f;
        }else if(vol == 7){
            sound = 0.7f;
        }else if(vol == 6){
            sound = 0.6f;
        }else if(vol == 5){
            sound = 0.5f;
        }else if(vol == 4){
            sound = 0.4f;
        }else if(vol == 3){
            sound = 0.3f;
        }else if(vol == 2){
            sound = 0.2f;
        }else if(vol == 1){
            sound = 0.1f;
        }else if(vol == 0){
            sound = 0.0f;
        }

        editor.putInt("es", vol_E);
        editor.putInt("bs", vol_E);


        editor.commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {



        //볼륨 이벤트
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:

                if(vol_E > 0){
                    vol_E--;
                }

                set_Sound(vol_E);

                break;

            case KeyEvent.KEYCODE_VOLUME_UP:

                if(vol_E < 10){
                    vol_E++;
                }

                set_Sound(vol_E);

                break;

            case KeyEvent.KEYCODE_BACK:

                finish();
                break;
        }

        return true;
        //        return super.onKeyDown(keyCode, event);
    }



    /**
     * Game_Start 버튼 클릭 이벤트
     */
    public void onButtonGame_StartClicked(View view){
        //게임 시작 버튼을 눌렀을때 셋팅 정보를 넘긴다.
        //게임화면 시작

        soundPool.play(sound_Effect[0], sound, sound, 0, 0, 1.0F);   //성공

        Intent intent = new Intent(getApplicationContext(), GameActivity.class);

        selectData();

//        Toast.makeText(getApplicationContext(), "" + "db 값 던지기" + info[1], Toast.LENGTH_SHORT).show();
        intent.putExtra("set", info);


        startActivityForResult(intent, 1001);




    }
    /**
     * 어항가기
     */
    public void onButtonFishbowl(View view){

    }

    /**
     * 상점 가기
     */
    public void onButtonStore(View view){
        selectData();
        Intent intent = new Intent(getApplicationContext(), Activity_Store.class);
        intent.putExtra("info", info);
//        Toast.makeText(getApplicationContext(), "" + info[0], Toast.LENGTH_SHORT).show();
        //intent.putExtra("cha","aa");
        startActivityForResult(intent, 1002);
//        recycleView(findViewById(R.layout.activity_store));


        soundPool.play(sound_Effect[0], sound, sound, 0, 0, 1.0F);   //성공


    }


}






//
// if(ground_List.get(i) instanceof Ground_Drag_Crab) {
//         draw.draw_Bmp(canvas, pop_Temp_img,
//         ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(12, _context),
//         ground_List.get(i).get_Ground_Point_Y() - convertPixelsToDp(12, _context));
//         }else {
//         draw.draw_Bmp(canvas, pop_Temp_img,
//         ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(12, _context),
//         ground_List.get(i).get_Ground_Point_Y());
//         }
//
//
//
//
//
////광고 진화.
//public void set_Ruby_Revolution(){
//        //진화 하기
//
//        int rand_Temp3 = random.nextInt(100);   //종류 선택
//        //진화 확률 = 0티어 플랑크톤 일때 100 %
//        //1티어 일때 90%, 2티어 80%, 3티어 70%, 4티어 60%, 5티어 50%, 6티어 40%, 7티어 30%, 6티어 20%, 9티어, 10%
//
//        if((main_Character.get_Tear() == 0)){
//
//        Log.e("a","asdasdsad@@@");
//
//        //메모리 관리 기법
//
//        //0티어 진화
//        if (rand_Temp3 <= 25) {
//        game_thread.function_Explain_Window_Shellfish_Tear_1();
//        main_Character = new Main_Character_Shellfish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//
//
//        character_Explain_DB[15] = 1;
//
//        } else if (rand_Temp3 <= 50) {
//        game_thread.function_Explain_Window_Fish_Tear_1();
//        main_Character = new Main_Character_Fish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//
//        character_Explain_DB[5] = 1;
//
//        } else if(rand_Temp3 <= 75){
//        //메모리 관리
//        game_thread.function_Explain_Window_Moulluse_Tear_1();
//        main_Character = new Main_Character_Moulluse_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//
//        character_Explain_DB[25] = 1;
//
//        } else {
//        game_thread.function_Explain_Window_Plankton_Tear_2();
//        main_Character = new Main_Character_Plankton_2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//
//        character_Explain_DB[1] = 1;
//
//        }
//        Log.e("a","asdasdsad###");
//
//        main_Character.set_Tear(1);
//        }
//
//        else if((main_Character.get_Tear() == 1) ){
//
//        //메모리 관리 기법
//        explain_Window_MainCharacker.recycle();
//        explain_Window_MainCharacker = null;
//
//        if (rand_Temp3 <= 25) {
//        game_thread.function_Explain_Window_Shellfish_Tear_2();
//        main_Character = new Main_Character_Shellfish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//
//        character_Explain_DB[16] = 1;
//
//        } else if (rand_Temp3 <= 50) {
//        game_thread.function_Explain_Window_Fish_Tear_2();
//        main_Character = new Main_Character_Fish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//
////                                    game_thread.function_Skill_Thorn_img();
//        character_Explain_DB[6] = 1;
//
//        } else if(rand_Temp3 <= 75){
//        //메모리 관리
//        game_thread.function_Explain_Window_Moulluse_Tear_2();
//        main_Character = new Main_Character_Moulluse_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[26] = 1;
//
//        } else {
//        game_thread.function_Explain_Window_Plankton_Tear_3();
//        main_Character = new Main_Character_Plankton_3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[2] = 1;
//        }
//        main_Character.set_Tear(2);
//
//
//
//        }
//
//
//        else if((main_Character.get_Tear() == 2) ){
//
//        //메모리 관리 기법
//        explain_Window_MainCharacker.recycle();
//        explain_Window_MainCharacker = null;
//
//        if (rand_Temp3 <= 25) {
//        game_thread.function_Explain_Window_Shellfish_Tear_3();
//        main_Character = new Main_Character_Shellfish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[17] = 1;
//
//
//        } else if (rand_Temp3 <= 50) {
//        game_thread.function_Explain_Window_Fish_Tear_3();
//        main_Character = new Main_Character_Fish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[7] = 1;
////                                    game_thread.function_Skill_Teeth_mine_img();
//
//        } else if(rand_Temp3 <= 75){
//        //메모리 관리
//        game_thread.function_Explain_Window_Moulluse_Tear_3();
//        main_Character = new Main_Character_Moulluse_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[27] = 1;
////                                    game_thread.function_Skill_Slow_Cloud_img();
//
//        } else {
//        game_thread.function_Explain_Window_Plankton_Tear_4();
//        main_Character = new Main_Character_Plankton_4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[3] = 1;
//        }
//        main_Character.set_Tear(3);
//
//
//
//        }
//
//
//        else if((main_Character.get_Tear() == 3) ){
//
//        //메모리 관리 기법
//        explain_Window_MainCharacker.recycle();
//        explain_Window_MainCharacker = null;
//
//
//        if (rand_Temp3 <= 25) {
//        game_thread.function_Explain_Window_Shellfish_Tear_4();
//        main_Character = new Main_Character_Shellfish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[18] = 1;
////                                    game_thread.function_Skill_Crab_img();
//
//        } else if (rand_Temp3 <= 50) {
//        game_thread.function_Explain_Window_Fish_Tear_4();
//        main_Character = new Main_Character_Fish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[8] = 1;
////                                    game_thread.function_Skill_earthquake_img();
//
//        } else if(rand_Temp3 <= 75){
//        //메모리 관리
//        game_thread.function_Explain_Window_Moulluse_Tear_4();
//        main_Character = new Main_Character_Moulluse_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[28] = 1;
////                                    game_thread.function_Skill_Butter_img();
//
//        } else {
//        game_thread.function_Explain_Window_Plankton_Tear_5();
//        main_Character = new Main_Character_Plankton_5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[4] = 1;
//        }
//        main_Character.set_Tear(4);
//
//
//        }
//
//
//        else if((main_Character.get_Tear() == 4) ){
//
//        //메모리 관리 기법
//        explain_Window_MainCharacker.recycle();
//        explain_Window_MainCharacker = null;
//
//
//        if (rand_Temp3 <= 33) {
//        game_thread.function_Explain_Window_Shellfish_Tear_5();
//        main_Character = new Main_Character_Shellfish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[19] = 1;
////                                    game_thread.function_Skill_Soycrab_img();
//
//        } else if (rand_Temp3 <= 66) {
//        game_thread.function_Explain_Window_Fish_Tear_5();
//        main_Character = new Main_Character_Fish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[9] = 1;
////                                    game_thread.function_Skill_Teeth_mine2_img();
//
//        } else {
//        //메모리 관리
//        game_thread.function_Explain_Window_Moulluse_Tear_5();
//        main_Character = new Main_Character_Moulluse_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[29] = 1;
////                                    game_thread.function_Skill_Fork_img();
//
//        }
//        main_Character.set_Tear(5);
//
//
//        }
//
//
//        else if((main_Character.get_Tear() == 5) ){
//
//        //메모리 관리 기법
//        explain_Window_MainCharacker.recycle();
//        explain_Window_MainCharacker = null;
//
//
//        if (rand_Temp3 <= 33) {
//        game_thread.function_Explain_Window_Shellfish_Tear_6();
//        main_Character = new Main_Character_Shellfish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[20] = 1;
////                                    game_thread.function_Skill_Thorn2_img();
//
//        } else if (rand_Temp3 <= 66) {
//        game_thread.function_Explain_Window_Fish_Tear_6();
//        main_Character = new Main_Character_Fish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[10] = 1;
//
//        } else {
//        //메모리 관리
//        game_thread.function_Explain_Window_Moulluse_Tear_6();
//        main_Character = new Main_Character_Moulluse_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[30] = 1;
////                                    game_thread.function_Skill_Laser_img();
//
//        }
//        main_Character.set_Tear(6);
//
//
//        }
//
//
//
//        else if((main_Character.get_Tear() == 6) ){
//
//        //메모리 관리 기법
//        explain_Window_MainCharacker.recycle();
//        explain_Window_MainCharacker = null;
//
//
//        if (rand_Temp3 <= 33) {
//        game_thread.function_Explain_Window_Shellfish_Tear_7();
//        main_Character = new Main_Character_Shellfish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[21] = 1;
////                                    game_thread.function_Skill_fry_img();
//
//        } else if (rand_Temp3 <= 66) {
//        game_thread.function_Explain_Window_Fish_Tear_7();
//        main_Character = new Main_Character_Fish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[11] = 1;
////                                    game_thread.function_Skill_lightnign_img();
//
//        } else {
//        //메모리 관리
//        game_thread.function_Explain_Window_Moulluse_Tear_7();
//        main_Character = new Main_Character_Moulluse_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[31] = 1;
//
//        }
//        main_Character.set_Tear(7);
//
//
//        }
//
//
//
//        else if((main_Character.get_Tear() == 7) ){
//
//        //메모리 관리 기법
//        explain_Window_MainCharacker.recycle();
//        explain_Window_MainCharacker = null;
//
//
//        if (rand_Temp3 <= 33) {
//        game_thread.function_Explain_Window_Shellfish_Tear_8();
//        main_Character = new Main_Character_Shellfish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[22] = 1;
////                                    game_thread.function_Skill_Crab_img();
//
//        } else if (rand_Temp3 <= 66) {
//        game_thread.function_Explain_Window_Fish_Tear_8();
//        main_Character = new Main_Character_Fish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[12] = 1;
////                                    game_thread.function_Skill_lightnign1_img();
//
//        } else {
//        //메모리 관리
//        game_thread.function_Explain_Window_Moulluse_Tear_8();
//        main_Character = new Main_Character_Moulluse_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[32] = 1;
////                                    game_thread.function_Skill_Boom_Poison_img();
//
//        }
//        main_Character.set_Tear(8);
//
//
//        }
//
//
//        else if((main_Character.get_Tear() == 8) ){
//        //메모리 관리 기법
//        explain_Window_MainCharacker.recycle();
//        explain_Window_MainCharacker = null;
//
//        if (rand_Temp3 <= 33) {
//        game_thread.function_Explain_Window_Shellfish_Tear_9();
//        main_Character = new Main_Character_Shellfish_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[23] = 1;
////                                    game_thread.function_Skill_Wave_img();
//
//        } else if (rand_Temp3 <= 66) {
//        game_thread.function_Explain_Window_Fish_Tear_9();
//        main_Character = new Main_Character_Fish_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[13] = 1;
////                                    game_thread.function_Skill_Sea_Snake_img();
//
//        } else {
//        //메모리 관리
//        game_thread.function_Explain_Window_Moulluse_Tear_9();
//        main_Character = new Main_Character_Moulluse_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[33] = 1;
////                                    game_thread.function_Skill_wall_img();
//        }
//        main_Character.set_Tear(9);
//
//
//        }
//
//
//
//        else if((main_Character.get_Tear() == 9) ){
//        //메모리 관리 기법
//        explain_Window_MainCharacker.recycle();
//        explain_Window_MainCharacker = null;
//
//        if (rand_Temp3 <= 33) {
//        game_thread.function_Explain_Window_Shellfish_Tear_10();
//        main_Character = new Main_Character_Shellfish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[24] = 1;
////                                    game_thread.function_Skill_stomp_img();
//
//        } else if (rand_Temp3 <= 66) {
//        game_thread.function_Explain_Window_Fish_Tear_10();
//        main_Character = new Main_Character_Fish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[14] = 1;
////                                    game_thread.function_Skill_Thorn_img();
//
//        } else {
//        //메모리 관리
//        game_thread.function_Explain_Window_Moulluse_Tear_10();
//        main_Character = new Main_Character_Moulluse_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        character_Explain_DB[34] = 1;
////                                    game_thread.function_Skill_Poison123_img();
//
//        }
//        main_Character.set_Tear(10);
//
//        }
//        }





//else if(confirm_Button_card_2.touch(touchx, touchy)){
//        //루비로 사기
//        if(ruby >= card_Ruby) {
//        ruby = ruby-card_Ruby;
//
//        set_Ruby_Revolution();
//
//        //매인 캐릭터가 진화할때마다 메인 캐릭터의 이미지를 변화 시켜준다.
//        Init_Main_Character_Image(_context, main_Character);
//
//        main_Character.set_Main_Character_Mode_Status_Init();
//        main_Character.set_Hp_Init();
//
//        m_Run_True();   //게임 재게
//        revolution_Flag_Confirm = true;
//
//        }
//        }
//        else if (confirm_Button_card_3.touch(touchx, touchy)) {
//        //광고 눌렀을때
//
//        }

    //루비로 진화 하기 버튼
//    int card_Ruby = 5; 1044
//    int card_Ruby_Position = 11;


//upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);  //7405
//        downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);
//
//        confirm_Button_card_1.setImages(upimage, downimage);
//        confirm_Button_card_1.draw(canvas);
//
//        upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_3);
//        downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_3);
//
//
//
//        confirm_Button_card_2.setImages(upimage, downimage);
//        confirm_Button_card_2.draw(canvas);
//
//        upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_2);
//        downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_3);
//
//        confirm_Button_card_3.setImages(upimage, downimage);
//        confirm_Button_card_3.draw(canvas);
//
//
//        //루비개수
//        paint_Temp.setTextSize(convertPixelsToDp(25, _context));
//        paint_Temp.setStyle(Paint.Style.FILL);
//        paint_Temp.setColor(Color.BLACK);
//
//
//
//        if(main_Character.get_Tear() == 0){
//        card_Ruby = 5;
//        }else{
//        card_Ruby = main_Character.get_Tear() * 5 + 5;
//        }
//        if(card_Ruby < 10){
//        card_Ruby_Position = 5;
//        }else {
//        card_Ruby_Position = 11;
//        }
//
//        canvas.drawText(card_Ruby +"", window_Width / 2 - convertPixelsToDp(card_Ruby_Position, _context), window_Height / 2 + convertPixelsToDp(7, _context), paint_Temp);
//
//        revolution_Flag = false;