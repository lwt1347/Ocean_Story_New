package com.plank.user.ocean_story;

/**
 * Created by Lee on 2017-06-27.
 */

public class Main_Character_Plankton_3 extends Main_Character {
    /**
     * 기본 생성자
     *
     * @param x
     * @param y
     */
    Main_Character_Plankton_3(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size, w_Size, h_Size);
        pref_Set = 100000003;
        this.set_Max_Hp(3);
        set_Damage(3);
    }

//곰팡이 생성

}