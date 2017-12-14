package com.plank.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */

class Main_Character_Fish_Tear8 extends Main_Character {

    public Main_Character_Fish_Tear8(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size, w_Size, h_Size);
        pref_Set = 100000800;
        this.set_Max_Hp(9);
        set_Damage(9);
    }
}

class Skill_Lightning2 extends Skill_Body{

    Skill_Lightning2(float x, float y) {
        super(x, y);
    }



    public void set_Skill_Move(int dpi_Add_Y){
        y_Point -= dpi_Add_Y;

        if(skill_Status < 2) {
            skill_Status++;
        }

        if(skill_Status == 2){
            skill_Status = 0;
        }

        if(-200 > y_Point){
            live_Skill = true;
        }

    }

    boolean attack_Status = false;
    public void set_play_Attack(){
        attack_Status = true;
    }

    public boolean get_play_Attack(){
        return attack_Status;
    }

    public void set_Up_State() {
        if(skill_Status < 3){
            skill_Status++;
        }else {
            live_Skill = true;
        }
    }


    public void set_X_Point(float x) {
        x_Point = x;
    }

    public void set_Y_Point(float y){
        y_Point = y;
    }


}