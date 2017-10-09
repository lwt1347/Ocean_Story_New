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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);     //->팝업창에서 제목 타이틀 없앤다
        setContentView(R.layout.dictionary);      //일시정지 눌렀을때 뜨는 xml 화면

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_a1);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_b1);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_c1);
            }
        }else {
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_1tear_plankton);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_5tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_4tearshellfish);
            }
        }

        set_Enabled_Button();
        explain_Confirm.setEnabled(true);
        explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));

    }
    public void card_2(View v){
        //몬스터 설명창
        if(mc_Flag) {
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_a2);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_b2);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_c2);
            }
        }else {
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_2tear_plankton);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_6tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_5tearshellfish);
            }
        }
        set_Enabled_Button();
        explain_Confirm.setEnabled(true);
        explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));

    }
    public void card_3(View v){
        //몬스터 설명창
        if(mc_Flag) {
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
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_3tear_plankton);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_7tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_6tearshellfish);
            }
            set_Enabled_Button();
            explain_Confirm.setEnabled(true);
            explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
        }
    }
    public void card_4(View v){
        //몬스터 설명창
        if(mc_Flag) {
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
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_4tear_plankton);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_8tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_7tearshellfish);
            }
            set_Enabled_Button();
            explain_Confirm.setEnabled(true);
            explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
        }
    }
    public void card_5(View v){
        //몬스터 설명창
        if(mc_Flag) {
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
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_5tear_plankton);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_9tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_8tearshellfish);
            }
            set_Enabled_Button();
            explain_Confirm.setEnabled(true);
            explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
        }
    }
    public void card_6(View v){
        //몬스터 설명창
        if(mc_Flag) {
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
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_1tearfish);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_10tearfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_9tearshellfish);
            }
            set_Enabled_Button();
            explain_Confirm.setEnabled(true);
            explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
        }
    }
    public void card_7(View v){
        //몬스터 설명창
        if(mc_Flag) {
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
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_2tearfish);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_1tearshellfish);
            } else if (page_Num == 2) {
                imageview.setImageResource(R.drawable.explain_window_10tearshellfish);
            }
            set_Enabled_Button();
            explain_Confirm.setEnabled(true);
            explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
        }
    }
    public void card_8(View v){
        //몬스터 설명창
        if(mc_Flag) {
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
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_3tearfish);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_2tearshellfish);
            }
            if (page_Num != 2) {
                set_Enabled_Button();
                explain_Confirm.setEnabled(true);
                explain_Confirm.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_confirm));
            }
        }
        Log.e("@",""+page_Num);
    }
    public void card_9(View v){
        //몬스터 설명창
        if(mc_Flag) {
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
            if (page_Num == 0) {
                imageview.setImageResource(R.drawable.explain_window_4tearfish);
            } else if (page_Num == 1) {
                imageview.setImageResource(R.drawable.explain_window_3tearshellfish);
            }
            if (page_Num != 2) {
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
            if (page_Num < 2) {
                page_Num++;
            }
            set_Card_Character_Image();
        }
    }
    public void confirm(View v){
            Log.e("@","@@@");
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
            btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a1));
            btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a2));
            btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a3));
            btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a4));
            btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a5));
            btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a6));
            btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a7));
            btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a8));
            btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_a9));

        }else if(page_Num == 1){
            btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b1));
            btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b2));
            btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b3));
            btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b4));
            btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b5));
            btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b6));
            btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b7));
            btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b8));
            btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_b9));
        }else if(page_Num == 2){
            btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_monster_c1));
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
            btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a0));
            btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a1));
            btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a2));
            btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a3));
            btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_a4));
            btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b0));
            btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b1));
            btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b2));
            btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b3));

        }else if(page_Num == 1){
            btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b4));
            btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b5));
            btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b6));
            btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b7));
            btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b8));
            btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_b9));
            btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c0));
            btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c1));
            btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c2));
        }else if(page_Num == 2){
            btn_m_s_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c3));
            btn_m_s_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c4));
            btn_m_s_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c5));
            btn_m_s_4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c6));
            btn_m_s_5.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c7));
            btn_m_s_6.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c8));
            btn_m_s_7.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_character_c9));
            btn_m_s_8.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));
            btn_m_s_9.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_question));

        }
    }


}