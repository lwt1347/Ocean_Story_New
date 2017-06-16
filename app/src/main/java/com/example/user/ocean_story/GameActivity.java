package com.example.user.ocean_story;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {



    //SurfaceView surfaceView;
    GameMain gameMain; // 게임 화면
    Button button_Pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //인텐트 받는 작업을 수행하고

        //게임메인 뷰를 켠다. 게임이 동작하는 구간.
        setContentView(R.layout.activity_game);

        gameMain = (GameMain)findViewById(R.id.GameMainView);

        //퍼지 버튼
        button_Pause = (Button)findViewById(R.id.pause_Button);



        //_gGameMain.setZOrderOnTop(false);

        /*
        // 슬라이딩 됨 주석 다시 달고 정지 눌렀을때 이 화면 불러와야함
        setContentView(R.layout.menuslidingpanel);      //화면에 뷰를올린다  setContentView
        translateDownAnim = AnimationUtils.loadAnimation(this, R.anim.translate_down);
        slidingMenuPanel = (LinearLayout) findViewById(R.id.menuslidingpanel);
        slidingMenuPanel.setVisibility(View.VISIBLE);
        slidingMenuPanel.startAnimation(translateDownAnim);
        */
    }

    /**
     * 퍼지 버튼
     */
    public void onButtonPause(View view){
        gameMain.m_Run_False();

        Intent intent = new Intent(this, menu_Sliding_Panel.class);
        //intent.putExtra("a", mRun);
        startActivityForResult(intent, 0); //-> 일시정지 창을 팝업한다. Menu_Sliding_Panel 호출

        //퍼지 버튼 눌렀을때 이미지 변경
        button_Pause.setBackgroundResource(R.drawable.pause_2);

        Toast.makeText(getApplicationContext(),"a",Toast.LENGTH_SHORT).show();
    }

    /**
     * 값 받아오기 메뉴창에서 계속하기 = 1, 다시하기 = 2
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //퍼지 버튼 원상태
        button_Pause.setBackgroundResource(R.drawable.pause_1);

        int key = data.getIntExtra("key",0);
        if(key == 1){
            gameMain.m_Run_True(); //게임 재게
        }
        else if(key == 2){  //다시 시작
            gameMain.m_Run_True();
            gameMain.re_Start();
        }

    }

}
