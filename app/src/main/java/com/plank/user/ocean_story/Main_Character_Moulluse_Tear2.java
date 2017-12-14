package com.plank.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */
//삼징어
public class Main_Character_Moulluse_Tear2 extends Main_Character  {
    /**
     * 기본 생성자
     */
    Main_Character_Moulluse_Tear2(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size, w_Size, h_Size);
        pref_Set = 102000000;
        this.set_Max_Hp(3);
        set_Damage(3);
    }
}
