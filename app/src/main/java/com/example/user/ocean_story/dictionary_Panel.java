package com.example.user.ocean_story;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Lee on 2017-10-02.
 */

public class dictionary_Panel extends Activity {

    //일시정지 눌렀을때 뜨는 화면
    // android:theme="@android:style/Theme.Dialog" - > 다이얼로그 형식으로 띄우게된다.

    //인텐트 객체
    Intent intent;

    //오른쪽 왼쪽
    int page_Num = 0;

    //설명창 보여줄지 말지 정보
    int[] m_Explain_Info = new int[40];
    int[] c_Explain_Info = new int[40];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //->팝업창에서 제목 타이틀 없앤다
        setContentView(R.layout.dictionary);      //일시정지 눌렀을때 뜨는 xml 화면

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        intent = this.getIntent();
        m_Explain_Info = intent.getIntArrayExtra("mexplain");
        c_Explain_Info = intent.getIntArrayExtra("cexplain");


        for(int i=0; i<c_Explain_Info.length; i++){
            Log.e("@","!@#!@# = " + c_Explain_Info[i]);
        }

//        android:background="@drawable/menu_background"

        //Intent intent = getIntent();
        /// String s = intent.getStringExtra("a");
        // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT ).show();

        //intent = new Intent(getApplicationContext(), GameMain.class);   //인텐트 객체 할당

        btn_m_s_1 = (Button)findViewById(R.id.btn_m_s_1);
        btn_m_s_2 = (Button)findViewById(R.id.btn_m_s_2);
        btn_m_s_3 = (Button)findViewById(R.id.btn_m_s_3);
        btn_m_s_4 = (Button)findViewById(R.id.btn_m_s_4);
        btn_m_s_5 = (Button)findViewById(R.id.btn_m_s_5);
        btn_m_s_6 = (Button)findViewById(R.id.btn_m_s_6);
        btn_m_s_7 = (Button)findViewById(R.id.btn_m_s_7);
        btn_m_s_8 = (Button)findViewById(R.id.btn_m_s_8);
        btn_m_s_9 = (Button)findViewById(R.id.btn_m_s_9);

        imageview = (ImageView)findViewById(R.id.imageView4);
        imageview_Text = (ImageView)findViewById(R.id.imageView5);

        confirm = (Button)findViewById(R.id.button17);
        left = (Button)findViewById(R.id.button15);
        right = (Button)findViewById(R.id.button16);
        explain_Confirm = (Button)findViewById(R.id.button2);
        explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_null));
        explain_Confirm.setEnabled(false);

        imageview_Text.setImageResource(R.drawable.text_diction_m);

        btn_mc = (Button)findViewById(R.id.button5);
        btn_mc.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_right_mc));

        //몬스터 종류 알아보기



        set_Card_Monster_Image();
    }

    Button btn_m_s_1;
    Button btn_m_s_2;
    Button btn_m_s_3;
    Button btn_m_s_4;
    Button btn_m_s_5;
    Button btn_m_s_6;
    Button btn_m_s_7;
    Button btn_m_s_8;
    Button btn_m_s_9;




    Button confirm;
    Button left;
    Button right;
    Button explain_Confirm;
    Button btn_mc;

    ImageView imageview = null;
    ImageView imageview_Text = null;

    boolean mc_Flag = true;

    //몬스터/ 캐릭터 도감 체인지
    public void btn_mc_change(View v){
        mc_Flag = !mc_Flag;
        page_Num = 0;
        if(mc_Flag){
            btn_mc.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_right_mc));
            imageview_Text.setImageResource(R.drawable.text_diction_m);
            set_Card_Monster_Image();
        }else{
            btn_mc.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_left_mc));
            imageview_Text.setImageResource(R.drawable.text_diction_c);
            set_Card_Character_Image();
        }

    }


    public void card_1(View v){


//        btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.effect_bird_1));
        //몬스터 설명창
        if(mc_Flag) {
            if(m_Explain_Info[0] != 1 && page_Num == 0){
                return;
            }else if(m_Explain_Info[9] != 1 && page_Num == 1) {
                return;
            }else if(m_Explain_Info[18] != 1 && page_Num == 2) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_a1);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_b1);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_c1);
            }
        }else {
            if(c_Explain_Info[0] != 1 && page_Num == 0){
                return;
            }else if(c_Explain_Info[9] != 1 && page_Num == 1) {
                return;
            }else if(c_Explain_Info[18] != 1 && page_Num == 2) {
                return;
            }else if(c_Explain_Info[27] != 1 && page_Num == 3) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_1tear_plankton);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_5tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_4tearshellfish);
            }else if(page_Num == 3){
                imageview.setImageResource(R.drawable.explain_window_3moullusc);
            }
        }

        set_Enabled_Button();
        explain_Confirm.setEnabled(true);
        explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));

    }
    public void card_2(View v){


        //몬스터 설명창
        if(mc_Flag) {
            if(m_Explain_Info[1] != 1 && page_Num == 0){
                return;
            }else if(m_Explain_Info[10] != 1 && page_Num == 1) {
                return;
            }else if(m_Explain_Info[19] != 1 && page_Num == 2) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_a2);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_b2);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_c2);
            }
        }else {
            if(c_Explain_Info[1] != 1 && page_Num == 0){
                return;
            }else if(c_Explain_Info[10] != 1 && page_Num == 1) {
                return;
            }else if(c_Explain_Info[19] != 1 && page_Num == 2) {
                return;
            }else if(c_Explain_Info[28] != 1 && page_Num == 3) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_2tear_plankton);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_6tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_5tearshellfish);
            }else if(page_Num == 3){
                imageview.setImageResource(R.drawable.explain_window_4moullusc);
            }
        }
        set_Enabled_Button();
        explain_Confirm.setEnabled(true);
        explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));

    }
    public void card_3(View v){


        //몬스터 설명창
        if(mc_Flag) {
            if(m_Explain_Info[2] != 1 && page_Num == 0){
                return;
            }else if(m_Explain_Info[11] != 1 && page_Num == 1) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_a3);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_b3);
            }

            if (page_Num != 2) {
                set_Enabled_Button();
                explain_Confirm.setEnabled(true);
                explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
            }
        }else {
            if(c_Explain_Info[2] != 1 && page_Num == 0){
                return;
            }else if(c_Explain_Info[11] != 1 && page_Num == 1) {
                return;
            }else if(c_Explain_Info[20] != 1 && page_Num == 2) {
                return;
            }else if(c_Explain_Info[29] != 1 && page_Num == 3) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_3tear_plankton);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_7tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_6tearshellfish);
            }else if(page_Num == 3){
                imageview.setImageResource(R.drawable.explain_window_5moullusc);
            }
            set_Enabled_Button();
            explain_Confirm.setEnabled(true);
            explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
        }
    }
    public void card_4(View v){

        //몬스터 설명창
        if(mc_Flag) {
            if(m_Explain_Info[3] != 1 && page_Num == 0){
                return;
            }else if(m_Explain_Info[12] != 1 && page_Num == 1) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_a4);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_b4);
            }
            if (page_Num != 2) {
                set_Enabled_Button();
                explain_Confirm.setEnabled(true);
                explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
            }
        }else {
            if(c_Explain_Info[3] != 1 && page_Num == 0){
                return;
            }else if(c_Explain_Info[12] != 1 && page_Num == 1) {
                return;
            }else if(c_Explain_Info[21] != 1 && page_Num == 2) {
                return;
            }else if(c_Explain_Info[30] != 1 && page_Num == 3) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_4tear_plankton);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_8tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_7tearshellfish);
            }else if(page_Num == 3){
                imageview.setImageResource(R.drawable.explain_window_6moullusc);
            }
            set_Enabled_Button();
            explain_Confirm.setEnabled(true);
            explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
        }
    }
    public void card_5(View v){

        //몬스터 설명창
        if(mc_Flag) {
            if(m_Explain_Info[4] != 1 && page_Num == 0){
                return;
            }else if(m_Explain_Info[13] != 1 && page_Num == 1) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_a5);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_b5);
            }
            if (page_Num != 2) {
                set_Enabled_Button();
                explain_Confirm.setEnabled(true);
                explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
            }
        }else {
            if(c_Explain_Info[4] != 1 && page_Num == 0){
                return;
            }else if(c_Explain_Info[13] != 1 && page_Num == 1) {
                return;
            }else if(c_Explain_Info[22] != 1 && page_Num == 2) {
                return;
            }else if(c_Explain_Info[31] != 1 && page_Num == 3) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_5tear_plankton);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_9tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_8tearshellfish);
            }else if(page_Num == 3){
                imageview.setImageResource(R.drawable.explain_window_7moullusc);
            }
            set_Enabled_Button();
            explain_Confirm.setEnabled(true);
            explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
        }
    }
    public void card_6(View v){

        //몬스터 설명창
        if(mc_Flag) {
            if(m_Explain_Info[5] != 1 && page_Num == 0){
                return;
            }else if(m_Explain_Info[14] != 1 && page_Num == 1) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_a6);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_b6);
            }
            if (page_Num != 2) {
                set_Enabled_Button();
                explain_Confirm.setEnabled(true);
                explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
            }
        }else {
            if(c_Explain_Info[5] != 1 && page_Num == 0){
                return;
            }else if(c_Explain_Info[14] != 1 && page_Num == 1) {
                return;
            }else if(c_Explain_Info[23] != 1 && page_Num == 2) {
                return;
            }else if(c_Explain_Info[32] != 1 && page_Num == 3) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_1tearfish);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_10tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_9tearshellfish);
            } else if(page_Num == 3){
                imageview.setImageResource(R.drawable.explain_window_8moullusc);
            }
            set_Enabled_Button();
            explain_Confirm.setEnabled(true);
            explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
        }
    }
    public void card_7(View v){

        //몬스터 설명창
        if(mc_Flag) {
            if(m_Explain_Info[6] != 1 && page_Num == 0){
                return;
            }else if(m_Explain_Info[15] != 1 && page_Num == 1) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_a7);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_b7);
            }
            if (page_Num != 2) {
                set_Enabled_Button();
                explain_Confirm.setEnabled(true);
                explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
            }
        }else {
            if(c_Explain_Info[6] != 1 && page_Num == 0){
                return;
            }else if(c_Explain_Info[15] != 1 && page_Num == 1) {
                return;
            }else if(c_Explain_Info[24] != 1 && page_Num == 2) {
                return;
            }else if(c_Explain_Info[33] != 1 && page_Num == 3) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_2tearfish);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_1tearshellfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_10tearshellfish);
            }else if(page_Num == 3){
                imageview.setImageResource(R.drawable.explain_window_9moullusc);
            }
            set_Enabled_Button();
            explain_Confirm.setEnabled(true);
            explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
        }
    }
    public void card_8(View v){

        //몬스터 설명창
        if(mc_Flag) {
            if(m_Explain_Info[7] != 1 && page_Num == 0){
                return;
            }else if(m_Explain_Info[16] != 1 && page_Num == 1) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_a8);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_b8);
            }
            if (page_Num != 2) {
                set_Enabled_Button();
                explain_Confirm.setEnabled(true);
                explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
            }
        }else {
            if(c_Explain_Info[7] != 1 && page_Num == 0){
                return;
            }else if(c_Explain_Info[16] != 1 && page_Num == 1) {
                return;
            }else if(c_Explain_Info[25] != 1 && page_Num == 2) {
                return;
            }else if(c_Explain_Info[34] != 1 && page_Num == 3) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_3tearfish);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_2tearshellfish);
            }else if(page_Num == 2){
                imageview.setImageResource(R.drawable.explain_window_1moullusc);
            }else if(page_Num == 3){
                imageview.setImageResource(R.drawable.explain_window_10moullusc);
            }

                set_Enabled_Button();
                explain_Confirm.setEnabled(true);
                explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));

        }
        Log.e("@",""+page_Num);
    }
    public void card_9(View v){

        //몬스터 설명창
        if(mc_Flag) {
            if(m_Explain_Info[8] != 1 && page_Num == 0){
                return;
            }else if(m_Explain_Info[17] != 1 && page_Num == 1) {
                return;
            }
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_a9);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_b9);
            }
            if (page_Num != 2) {
                set_Enabled_Button();
                explain_Confirm.setEnabled(true);
                explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
            }
        }else {
            if(c_Explain_Info[8] != 1 && page_Num == 0){
                return;
            }else if(c_Explain_Info[17] != 1 && page_Num == 1) {
                return;
            }else if(c_Explain_Info[26] != 1 && page_Num == 2) {
                return;
            }

            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_4tearfish);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_3tearshellfish);
            }else if(page_Num == 2){
                imageview.setImageResource(R.drawable.explain_window_2moullusc);
            }
            if (page_Num != 3) {
                set_Enabled_Button();
                explain_Confirm.setEnabled(true);
                explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
            }
        }
    }

    public void left(View v){
//몬스터 설명창
        if(mc_Flag) {
            if (page_Num > 0) {
                page_Num--;
            }
            set_Card_Monster_Image();
        }else {
            if (page_Num > 0) {
                page_Num--;
            }
            set_Card_Character_Image();
        }

    }
    public void right(View v){
        //몬스터 설명창
        if(mc_Flag) {
            if (page_Num < 2) {
                page_Num++;
            }
            set_Card_Monster_Image();
        }else {
            if (page_Num < 3) {
                page_Num++;
            }
            set_Card_Character_Image();
        }
    }
    public void confirm(View v){
        intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("key",1);
        setResult(0, intent);

            finish();
    }
    public void confirm_explain(View v){
        set_abled_Button();
        imageview.setImageResource(R.drawable.image_null);
        explain_Confirm.setEnabled(false);
        explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_null));
    }



    public void set_abled_Button(){
        btn_m_s_1.setEnabled(true);
        btn_m_s_2.setEnabled(true);
        btn_m_s_3.setEnabled(true);
        btn_m_s_4.setEnabled(true);
        btn_m_s_5.setEnabled(true);
        btn_m_s_6.setEnabled(true);
        btn_m_s_7.setEnabled(true);
        btn_m_s_8.setEnabled(true);
        btn_m_s_9.setEnabled(true);

        confirm.setEnabled(true);
        left.setEnabled(true);
        right.setEnabled(true);
    }
    public void set_Enabled_Button(){
        btn_m_s_1.setEnabled(false);
        btn_m_s_2.setEnabled(false);
        btn_m_s_3.setEnabled(false);
        btn_m_s_4.setEnabled(false);
        btn_m_s_5.setEnabled(false);
        btn_m_s_6.setEnabled(false);
        btn_m_s_7.setEnabled(false);
        btn_m_s_8.setEnabled(false);
        btn_m_s_9.setEnabled(false);

        confirm.setEnabled(false);
        left.setEnabled(false);
        right.setEnabled(false);

    }



    public void set_Card_Monster_Image(){


        if(page_Num == 0){
            //몬스터 카드
            if(m_Explain_Info[0] == 0) {
                //색 변경
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a1_shadow));
            }else if(m_Explain_Info[0] == 1){
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a1));
            }

            if(m_Explain_Info[1] == 0) {
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a2_shadow));
            }else if(m_Explain_Info[1] == 1) {
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a2));
            }

            if(m_Explain_Info[2] == 0) {
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a3_shadow));
            }else if(m_Explain_Info[2] == 1) {
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a3));
            }

            if(m_Explain_Info[3] == 0) {
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a4_shadow));
            }else if(m_Explain_Info[3] == 1) {
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a4));
            }

            if(m_Explain_Info[4] == 0) {
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a5_shadow));
            }else if(m_Explain_Info[4] == 1) {
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a5));
            }

            if(m_Explain_Info[5] == 0) {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a6_shadow));
            }else if(m_Explain_Info[5] == 1) {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a6));
            }

            if(m_Explain_Info[6] == 0) {
            btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a7_shadow));
            }else if(m_Explain_Info[6] == 1) {
                btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a7));
            }

            if(m_Explain_Info[7] == 0) {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a8_shadow));
            }else if(m_Explain_Info[7] == 1) {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a8));
            }

            if(m_Explain_Info[8] == 0) {
                btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a9_shadow));
            }else if(m_Explain_Info[8] == 1) {
                btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a9));
            }

        }else if(page_Num == 1){

            if(m_Explain_Info[9] == 0) {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b1_shadow));
            }else if(m_Explain_Info[9] == 1) {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b1));
            }

            if(m_Explain_Info[10] == 0) {
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b2_shadow));
            }else if(m_Explain_Info[10] == 1) {
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b2));
            }

            if(m_Explain_Info[11] == 0) {
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b3_shadow));
            }else if(m_Explain_Info[11] == 1) {
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b3));
            }

            if(m_Explain_Info[12] == 0) {
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b4_shadow));
            }else if(m_Explain_Info[12] == 1) {
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b4));
            }

            if(m_Explain_Info[13] == 0) {
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b5_shadow));
            }else if(m_Explain_Info[13] == 1) {
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b5));
            }

            if(m_Explain_Info[14] == 0) {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b6_shadow));
            }else if(m_Explain_Info[14] == 1) {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b6));
            }

            if(m_Explain_Info[15] == 0) {
                btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b7_shadow));
            }else if(m_Explain_Info[15] == 1) {
                btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b7));
            }

            if(m_Explain_Info[16] == 0) {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b8_shadow));
            }else if(m_Explain_Info[16] == 1) {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b8));
            }

            if(m_Explain_Info[17] == 0) {
                btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b9_shadow));
            }else if(m_Explain_Info[17] == 1) {
                btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b9));
            }

        }else if(page_Num == 2){

            if(m_Explain_Info[18] == 0) {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_c1_shadow));
            }else if(m_Explain_Info[18] == 1) {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_c1));
            }

            btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));
            btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));

            btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));

            btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));

            btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));

            btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));

            btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));

            btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));

            btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));


        }
    }

    public void set_Card_Character_Image(){

        if(page_Num == 0){

            if(c_Explain_Info[0] == 0) {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a0_shadow));
            }else {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a0));
            }

            if(c_Explain_Info[1] == 0) {
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a1_shadow));
            }else {
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a1));
            }

            if(c_Explain_Info[2] == 0) {
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a2_shadow));
            }else{
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a2));
            }

            if(c_Explain_Info[3] == 0) {
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a3_shadow));
            }else{
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a3));
            }

            if(c_Explain_Info[4] == 0) {
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a4_shadow));
            }else {
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a4));
            }

            if(c_Explain_Info[5] == 0) {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b0_shadow));
            }else {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b0));
            }

            if(c_Explain_Info[6] == 0) {
                btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b1_shadow));
            }else {
                btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b1));
            }

            if(c_Explain_Info[7] == 0) {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b2_shadow));
            } else {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b2));
            }

            if(c_Explain_Info[8] == 0) {
                btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b3_shadow));
            }else {
                btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b3));
            }

        }else if(page_Num == 1){

            if(c_Explain_Info[9] == 0) {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b4_shadow));
            }else {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b4));
            }
            if(c_Explain_Info[10] == 0) {
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b5_shadow));
            }else{
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b5));
            }

            if(c_Explain_Info[11] == 0) {
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b6_shadow));
            }else {
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b6));
            }

            if(c_Explain_Info[12] == 0) {
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b7_shadow));
            }else {
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b7));
            }

            if(c_Explain_Info[13] == 0) {
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b8_shadow));
            }else {
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b8));
            }

            if(c_Explain_Info[14] == 0) {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b9_shadow));
            }else {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b9));
            }

            if(c_Explain_Info[15] == 0) {
                btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c0_shadow));
            }else {
                btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c0));
            }

            if(c_Explain_Info[16] == 0) {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c1_shadow));
            }else {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c1));
            }

            if(c_Explain_Info[17] == 0) {
                btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c2_shadow));
            }else {
                btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c2));
            }

        }else if(page_Num == 2){
            if(c_Explain_Info[18] == 0) {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c3_shadow));
            }else {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c3));
            }
            if(c_Explain_Info[19] == 0) {
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c4_shadow));
            }else {
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c4));
            }
            if(c_Explain_Info[20] == 0) {
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c5_shadow));
            }else {
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c5));
            }
            if(c_Explain_Info[21] == 0) {
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c6_shadow));
            }else {
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c6));
            }
            if(c_Explain_Info[22] == 0) {
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c7_shadow));
            }else{
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c7));
            }
            if(c_Explain_Info[23] == 0) {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c8_shadow));
            }else {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c8));
            }
            if(c_Explain_Info[24] == 0) {
                btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c9_shadow));
            }else {
                btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c9));
            }
            if(c_Explain_Info[25] == 0) {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d0_shadow));
            }else {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d0));
            }
            if(c_Explain_Info[26] == 0) {
                btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d1_shadow));
            }else {
                btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d1));
            }

        }else if(page_Num == 3){

            if(c_Explain_Info[27] == 0) {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d2_shadow));
            }else {
                btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d2));
            }
            if(c_Explain_Info[28] == 0) {
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d3_shadow));
            }else {
                btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d3));
            }
            if(c_Explain_Info[29] == 0) {
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d4_shadow));
            }else {
                btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d4));
            }
            if(c_Explain_Info[30] == 0) {
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d5_shadow));
            }else {
                btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d5));
            }
            if(c_Explain_Info[31] == 0) {
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d6_shadow));
            }else {
                btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d6));
            }
            if(c_Explain_Info[32] == 0) {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d7_shadow));
            }else {
                btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d7));
            }
            if(c_Explain_Info[33] == 0) {
                btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d8_shadow));
            }else {
                btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d8));
            }
            if(c_Explain_Info[34] == 0) {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d9_shadow));
            }else {
                btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_d9));
            }


            btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));

        }
    }

    GameActivity gameActivity;
    @Override
    protected void onUserLeaveHint() {



        super.onUserLeaveHint();
        ((GameActivity)gameActivity._context_Send).sound_Exit();


    }

}
