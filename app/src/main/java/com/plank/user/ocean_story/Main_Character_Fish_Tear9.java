package com.plank.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */

class Main_Character_Fish_Tear9 extends Main_Character {

    public Main_Character_Fish_Tear9(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size, w_Size, h_Size);
        pref_Set = 100000900;
        this.set_Max_Hp(10);
        set_Damage(10);
    }
}
class Skill_Sea_Snake extends Skill_Body{

    Skill_Sea_Snake(float x, float y) {
        super(x, y);

    }

    public void set_Skill_Move(int dpi_Add_X){
        x_Point -= dpi_Add_X;

        if(-200 > x_Point){
            live_Skill = true;
        }

    }



}