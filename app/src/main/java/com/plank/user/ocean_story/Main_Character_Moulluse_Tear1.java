package com.plank.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */
//플라나리아
public class Main_Character_Moulluse_Tear1 extends Main_Character  {
    /**
     * 기본 생성자
     */
    Main_Character_Moulluse_Tear1(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size, w_Size, h_Size);
        pref_Set = 101000000;
        this.set_Max_Hp(2);
        set_Damage(2);
    }
}