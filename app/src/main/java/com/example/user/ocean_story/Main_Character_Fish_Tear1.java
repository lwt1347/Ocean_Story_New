package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */

class Main_Character_Fish_Tear1 extends Main_Character {

    public Main_Character_Fish_Tear1(float x, float y, int window_Width_Size, int window_Height_Size, int w_Size, int h_Size) {
        super(x, y,window_Width_Size, window_Height_Size , w_Size, h_Size);
        pref_Set = 100000100;
        this.set_Max_Hp(2);
        set_Damage(2);
    }
}
