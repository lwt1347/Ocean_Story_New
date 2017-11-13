package com.example.user.ocean_story;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
    public static SeekBar b_Sound;
    public static SeekBar e_Sound;

    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;


    GameActivity gameActivity;


    int sound_Check_E = 0;
    int sound_Check_B = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                finish();

                break;

        }

        return true;
    }


    //확인 버튼
    public void confirm(View v){

        intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("key",1);
        setResult(0, intent);

        finish();
    }

    int check;
    int vive;
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
        checkBox3 = (CheckBox)findViewById(R.id.checkBox3);



        SharedPreferences.Editor editor;
        SharedPreferences pref;

        //설정 에서 토토리얼 모드 인지 아닌지.
        pref = this.getSharedPreferences("pref", Activity.MODE_APPEND);
        editor = pref.edit();
        check = pref.getInt("tuto",0);
        vive = pref.getInt("vive",0);

        if(check == 0){
            checkBox2.setChecked(true);
        }else {
            checkBox2.setChecked(false);
        }

        if(vive == 0){
            checkBox3.setChecked(true);
        }else {
            checkBox3.setChecked(false);
        }

        sound_Check_E = pref.getInt("es",0);
        sound_Check_B = pref.getInt("bs",0);

        e_Sound.setProgress(sound_Check_E);
        b_Sound.setProgress(sound_Check_B);

        if(b_Sound.getProgress() == 0 && e_Sound.getProgress() == 0){
            checkBox1.setChecked(true);
        }

        /**
         * 사운드 백그라운드, 이펙트
         */
        b_Sound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    ((GameActivity) gameActivity._context_Send).set_Back_Sound(progress, sound_Check_E, check);
                    sound_Check_B = progress;
                    if (progress > 0) {
                        //음소거
                        checkBox1.setChecked(false);
                    }
                    if (progress == 0 && e_Sound.getProgress() == 0) {
                        checkBox1.setChecked(true);
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
                ((GameActivity)gameActivity._context_Send).set_Effect_Sound(progress, sound_Check_B, check);
                sound_Check_E = progress;
                if(progress > 0){
                    //음소거
                    checkBox1.setChecked(false);
                }
                if(progress == 0 && b_Sound.getProgress() == 0){
                    checkBox1.setChecked(true);
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
                        ((GameActivity)gameActivity._context_Send).set_Effect_Sound(0, 0, check);
                        ((GameActivity)gameActivity._context_Send).set_Back_Sound(0, 0, check);
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

                    ((GameActivity)gameActivity._context_Send).set_Tuto(0);
                    Log.e("@","a");

                } else {
                    ((GameActivity)gameActivity._context_Send).set_Tuto(1);
                    Log.e("@","b");

                }

            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    ((GameActivity)gameActivity._context_Send).set_Vive(0);
                    Log.e("@","a");

                } else {
                    ((GameActivity)gameActivity._context_Send).set_Vive(1);
                    Log.e("@","b");

                }

            }
        });


    }


    @Override
    protected void onUserLeaveHint() {



        super.onUserLeaveHint();

        ((GameActivity) gameActivity._context_Send).sound_Exit();

    }



}
