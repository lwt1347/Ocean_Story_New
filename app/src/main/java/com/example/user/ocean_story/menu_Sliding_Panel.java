package com.example.user.ocean_story;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class menu_Sliding_Panel extends Activity {


    //일시정지 눌렀을때 뜨는 화면
    // android:theme="@android:style/Theme.Dialog" - > 다이얼로그 형식으로 띄우게된다.

    //인텐트 객체
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //->팝업창에서 제목 타이틀 없앤다
        setContentView(R.layout.menuslidingpanel);      //일시정지 눌렀을때 뜨는 xml 화면

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        android:background="@drawable/menu_background"

        //Intent intent = getIntent();
       /// String s = intent.getStringExtra("a");
       // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT ).show();

        //intent = new Intent(getApplicationContext(), GameMain.class);   //인텐트 객체 할당
    }

    //계속하기 버튼
    public void onButtonContinue(View v){
        //Toast.makeText(getApplicationContext(), "계속하기", Toast.LENGTH_SHORT ).show();

        /**
         * 인텐트를 통해 명령을 전달한다. 1 = 계속하기, 2 = 다시하기
         */
        intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("key",1);
        setResult(0, intent);
        finish();   //액티비티 종료


    }
    //다시하기 버튼
    public void onButtonResume(View v){


        intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("key",2);
        setResult(0, intent);
        finish();   //액티비티 종료



    }


}
