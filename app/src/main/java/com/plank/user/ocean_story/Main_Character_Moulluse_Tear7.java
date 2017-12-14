package com.plank.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */
//해파리
public class Main_Character_Moulluse_Tear7 extends Main_Character  {
    /**
     * 기본 생성자
     */
    Main_Character_Moulluse_Tear7(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size,w_Size, h_Size);
        pref_Set = 107000000;
        this.set_Max_Hp(8);
        set_Damage(8);
    }
}
