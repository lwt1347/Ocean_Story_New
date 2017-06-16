package com.example.user.ocean_story;

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
    Fish_Drag_Default(int window_Width, int hp) {
        super(window_Width, hp);
        fish_Class = 2;
    }



}
