package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-01.
 */

//게딱지 생성
public class Main_Character_Shellfish_Tear3 extends Main_Character {

    /**
     * 기본 생성자
     *
     * @param x
     * @param y
     */
    Main_Character_Shellfish_Tear3(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size, w_Size, h_Size);
        this.set_Max_Hp(4);
        set_Damage(4);
    }
}
