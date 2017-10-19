package com.example.user.ocean_story;

import static android.R.attr.x;

/**
 * Created by USER on 2017-01-21.
 * 기본 물고기 HP1 ~ Hp5 까지
 * 터치로 잡는 물고기
 */

public class Fish_Touch_Default extends Fish_Default_Body {

    /**
     * 기본 생성자
     * 윈도우 크기와 hp 를 받아와 물고기를 생성한다.
     *
     * @param window_Width
     * @param hp
     */
    Fish_Touch_Default(int window_Width, int hp, int param_Width_Size, int param_Height_Size, int t_Speed) {
        super(window_Width, hp, param_Width_Size, param_Height_Size, t_Speed);

        fish_Class = 1;
        class_Num = 0;  //기본 물고기
        scf_Flag = true;
    }

    //기본인가, 중보스인가, 보스인가.
    int class_Num = 0;



    Fish_Touch_Default(int window_Width, int hp, int param_Width_Size,int param_Height_Size, float X_Point, float y_Point, int t_Speed) {
        super(window_Width, hp, param_Width_Size, param_Height_Size, t_Speed);
        fish_Class = 1;

        fish_Point_X = X_Point;
        fish_Point_Y = y_Point;
    }


    public void set_Class_Num(int param_Class_Num){
        //class_Num = 1 중간보스
        //class_Num = 2 보스
        class_Num = param_Class_Num;
    }

    public int get_Class_Num(){
        return class_Num;
    }












}
