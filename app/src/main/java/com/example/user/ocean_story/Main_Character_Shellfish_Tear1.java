package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-01.
 */

//삼엽충 생성
public class Main_Character_Shellfish_Tear1 extends Main_Character {

    /**
     * 기본 생성자
     *
     * @param x
     * @param y
     */
    Main_Character_Shellfish_Tear1(float x, float y, int window_Width_Size, int window_Height_Size) {
        super(x, y,  window_Width_Size, window_Height_Size);
        this.set_Max_Hp(2);
        set_Damage(2);
    }
}
