package com.plank.user.ocean_story;

/**
 * Created by USER on 2017-01-21.
 * 드래그로 잡는 물고기.
 */

public class Fish_Drag_Default extends Fish_Default_Body{

    /**
     * 기본 생성자
     * 윈도우 크기와 hp 를 받아와 물고기를 생성한다.
     *
     * @param window_Width
     * @param hp
     */
    Fish_Drag_Default(int window_Width, int hp, int param_Width_Size, int param_Height_Size, int t_Speed) {
        super(window_Width, hp, param_Width_Size, param_Height_Size, t_Speed);
        fish_Class = 2;
    }

    public void set_Position(float x, float y){
        fish_Point_X =  x;
        fish_Point_Y = y;
    }



}
