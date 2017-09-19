package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-01.
 */

//랍스터 생성
public class Main_Character_Shellfish_Tear8 extends Main_Character {

    /**
     * 기본 생성자
     *
     * @param x
     * @param y
     */
    Main_Character_Shellfish_Tear8(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size, w_Size, h_Size);
        pref_Set = 100080000;
        this.set_Max_Hp(9);
        set_Damage(9);
    }
}
