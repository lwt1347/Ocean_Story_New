package com.example.user.ocean_story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class explain_Panel extends Activity {


    //일시정지 눌렀을때 뜨는 화면
    // android:theme="@android:style/Theme.Dialog" - > 다이얼로그 형식으로 띄우게된다.

    GameMain gameMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //->팝업창에서 제목 타이틀 없앤다
        setContentView(R.layout.fish_default_explain_tap);      //일시정지 눌렀을때 뜨는 xml 화면

        gameMain = (GameMain)findViewById(R.id.GameMainView);
        Toast.makeText(getApplicationContext(), "계속하기", Toast.LENGTH_SHORT ).show();
    }

    //계속하기 버튼
    public void onButtonContinue(View v){
        //Toast.makeText(getApplicationContext(), "계속하기", Toast.LENGTH_SHORT ).show();


        gameMain.m_Run_True();
        Intent intent;
        intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("key",1);
        setResult(0, intent);
        finish();   //액티비티 종료
    }




}
