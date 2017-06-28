package com.example.user.ocean_story;

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
    Fish_Touch_Default(int window_Width, int hp) {
        super(window_Width, hp);
        fish_Class = 1;
        first_Test_Object = false;
    }

    /**
     * 가장 처음 생성된 물고기인가?
     */
    boolean first_Test_Object;
    public void set_First_Test_Object(){
        fish_Point_X = 200;
        fish_Point_Y = 100;
        first_Test_Object = true;
    }
    public boolean get_Fisrt_Test_Object(){
        return first_Test_Object;
    }



}
