package com.example.user.ocean_story;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class option_Panel extends Activity {


    //일시정지 눌렀을때 뜨는 화면
    // android:theme="@android:style/Theme.Dialog" - > 다이얼로그 형식으로 띄우게된다.

    //인텐트 객체
    Intent intent;
    SeekBar b_Sound;
    SeekBar e_Sound;

    CheckBox checkBox1;
    CheckBox checkBox2;


    GameActivity gameActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //->팝업창에서 제목 타이틀 없앤다
        setContentView(R.layout.optionpanel);      //일시정지 눌렀을때 뜨는 xml 화면

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        b_Sound = (SeekBar)findViewById(R.id.seekBar);
        e_Sound = (SeekBar)findViewById(R.id.seekBar2);

        checkBox1 = (CheckBox)findViewById(R.id.checkBox);
        checkBox2 = (CheckBox)findViewById(R.id.checkBox2);

        /**
         * 사운드 백그라운드, 이펙트
         */
        b_Sound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((GameActivity)gameActivity._context_Send).set_Back_Sound(progress);
                if(progress > 0){
                    checkBox1.setChecked(false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        e_Sound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((GameActivity)gameActivity._context_Send).set_Effect_Sound(progress);
                if(progress > 0){
                    checkBox1.setChecked(false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        /**
         * 체크박스 음소거, 튜토리얼
         */
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        Log.e("@","a");
                        ((GameActivity)gameActivity._context_Send).set_Sound(false);
                        ((GameActivity)gameActivity._context_Send).set_Effect_Sound(0);
                        ((GameActivity)gameActivity._context_Send).set_Back_Sound(0);
                        b_Sound.setProgress(0);
                        e_Sound.setProgress(0);
                    } else {
                        Log.e("@","b");
                        ((GameActivity)gameActivity._context_Send).set_Sound(true);
                    }

            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((GameActivity)gameActivity._context_Send).set_Tuto(false);
                    Log.e("@","a");

                } else {
                    ((GameActivity)gameActivity._context_Send).set_Tuto(true);
                    Log.e("@","b");

                }

            }
        });


    }



}
