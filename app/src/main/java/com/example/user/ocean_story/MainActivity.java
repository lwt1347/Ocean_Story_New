package com.example.user.ocean_story;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int structuredamage;
    int dragdamage;
    int score;
    int money;
    int ruby;
    int ft1;
    int ft2;
    int ft3;
    int ft4;
    int ft5;
    int ft6;
    int ft7;
    int ft8;
    int ft9;
    int ft10;
    int st1;
    int st2;
    int st3;
    int st4;
    int st5;
    int st6;
    int st7;
    int st8;
    int st9;
    int st10;
    int mt1;
    int mt2;
    int mt3;
    int mt4;
    int mt5;
    int mt6;
    int mt7;
    int mt8;
    int mt9;
    int mt10;

    int info[] = new int[35];

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //메모리 관리
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.activity_main);
        layout.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.background_start)));


        //데이터 베이스 생성 및 오픈
        database = openOrCreateDatabase("oceanstory1", MODE_PRIVATE, null);

        if(database != null){
            //db 오픈 되있음.
            selectData();

        }else {

            //데이터 베이스 생성시에 한번만 실행해야 한다.
            String sql = "create table maincharacterinfo(_id integer PRIMARY KEY autoincrement, structuredamage integer, dragdamage integer, score integer, money integer, ruby integer, " +
                    "ft1 integer, ft2 integer, ft3 integer, ft4 integer, ft5 integer, ft6 integer, ft7 integer, ft8 integer, ft9 integer, ft10 integer, " +
                    "st1 integer, st2 integer, st3 integer, st4 integer, st5 integer, st6 integer, st7 integer, st8 integer, st9 integer, st10 integer, " +
                    "mt1 integer, mt2 integer, mt3 integer, mt4 integer, mt5 integer, mt6 integer, mt7 integer, mt8 integer, mt9 integer, mt10 integer)";
            database.execSQL(sql);
            insertData(1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

    }

    /**
     * 데이터 베이스
     */
    //값 집어넣기
    public void insertData(int structuredamage, int dragdamage, int score, int money, int ruby, int ft1 ,int ft2, int ft3, int ft4, int ft5, int ft6, int ft7, int ft8, int ft9, int ft10,
                           int st1, int st2, int st3, int st4, int st5, int st6, int st7, int st8, int st9, int st10,
                           int mt1, int mt2, int mt3, int mt4, int mt5, int mt6, int mt7, int mt8, int mt9, int mt10){
        if(database != null){

            String sql = "insert into maincharacterinfo(structuredamage, dragdamage, score, money, ruby, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, st1, st2, st3, st4, st5, st6, st7, st8, st9, st10, mt1, mt2, mt3, mt4, mt5, mt6, mt7, mt8, mt9, mt10) " +
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] params = {structuredamage, dragdamage, score, money, ruby, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, st1, st2, st3, st4, st5, st6, st7, st8, st9, st10, mt1, mt2, mt3, mt4, mt5, mt6, mt7, mt8, mt9, mt10};

            database.execSQL(sql, params);
        }
    }

    //값 가져오기
    public void selectData(){
        if(database != null){
            String sql = "select structuredamage, dragdamage, score, money, ruby, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, st1, st2, st3, st4, st5, st6, st7, st8, st9, st10, mt1, mt2, mt3, mt4, mt5, mt6, mt7, mt8, mt9, mt10 from maincharacterinfo";
            Cursor cursor = database.rawQuery(sql, null);

            for(int i=0; i<cursor.getCount(); i++){
                cursor.moveToNext();

//                String name = cursor.getString(0);
//                int age = cursor.getInt(1);
//                String mobile = cursor.getString(2);
                    info[i] = cursor.getInt(i);
//                 structuredamage = cursor.getInt(0);
//                 dragdamage = cursor.getInt(1);
//                 score = cursor.getInt(2);
//                 money = cursor.getInt(3);
//                 ruby = cursor.getInt(4);
//                 ft1 = cursor.getInt(5);
//                 ft2 = cursor.getInt(6);
//                 ft3 = cursor.getInt(7);
//                 ft4 = cursor.getInt(8);
//                 ft5 = cursor.getInt(9);
//                 ft6 = cursor.getInt(10);
//                 ft7 = cursor.getInt(11);
//                 ft8 = cursor.getInt(12);
//                 ft9 = cursor.getInt(13);
//                 ft10 = cursor.getInt(14);
//                 st1 = cursor.getInt(15);
//                 st2 = cursor.getInt(16);
//                 st3 = cursor.getInt(17);
//                 st4 = cursor.getInt(18);
//                 st5 = cursor.getInt(19);
//                 st6 = cursor.getInt(20);
//                 st7 = cursor.getInt(21);
//                 st8 = cursor.getInt(22);
//                 st9 = cursor.getInt(23);
//                 st10 = cursor.getInt(24);
//                 mt1 = cursor.getInt(25);
//                 mt2 = cursor.getInt(26);
//                 mt3 = cursor.getInt(27);
//                 mt4 = cursor.getInt(28);
//                 mt5 = cursor.getInt(29);
//                 mt6 = cursor.getInt(30);
//                 mt7 = cursor.getInt(31);
//                 mt8 = cursor.getInt(32);
//                 mt9 = cursor.getInt(33);
//                 mt10 = cursor.getInt(34);
            }

            cursor.close();
        }
    }

    //인텐트 결과값 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        // 넘어갔던 화면에서 되돌아 왔을 때
        if (resultCode==RESULT_OK) { // 정상 반환일 경우에만 동작하겠다
            Toast.makeText(getApplicationContext(), "??", Toast.LENGTH_SHORT).show();
//            int num = data.getIntExtra("hap", 0);
            info = data.getIntArrayExtra("info");
            Toast.makeText(getApplicationContext(), "" + info[0], Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * Game_Start 버튼 클릭 이벤트
     */
    public void onButtonGame_StartClicked(View view){
        //게임 시작 버튼을 눌렀을때 셋팅 정보를 넘긴다.
        //게임화면 시작

        Intent intent = new Intent(getApplicationContext(), GameActivity.class);

        //intent.putExtra("cha","aa");
        startActivityForResult(intent, 1001);
        recycleView(findViewById(R.id.activity_main));



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
        Intent intent = new Intent(getApplicationContext(), Activity_Store.class);
        intent.putExtra("info", info);
        //intent.putExtra("cha","aa");
        startActivityForResult(intent, 1002);
//        recycleView(findViewById(R.layout.activity_store));





    }


}

























