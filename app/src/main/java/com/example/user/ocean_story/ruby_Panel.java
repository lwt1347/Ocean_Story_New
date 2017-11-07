package com.example.user.ocean_story;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class ruby_Panel extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //->팝업창에서 제목 타이틀 없앤다
        setContentView(R.layout.rubypanel);      //일시정지 눌렀을때 뜨는 xml 화면
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView RubyCount = (TextView)findViewById(R.id.ruby_Count);
        Intent intent = new Intent(this.getIntent());
        int ruby_Count  = intent.getIntExtra("rubycount", 1);

        RubyCount.setText("" + ruby_Count);

    }


    public void onButtonClick(View view){
        finish();
    }

}
