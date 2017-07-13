package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */
//레이저문어
public class Main_Character_Moulluse_Tear6 extends Main_Character  {
    /**
     * 기본 생성자
     */

    Main_Character_Moulluse_Tear6(float x, float y) {
        super(x, y);

        this.set_Max_Hp(7);
        set_Damage(7);
    }
}

class Skill_Laser extends Skill_Body{

    float window_X_Size = 0;

    Skill_Laser(float x, float y, float param_Window_X_Size){
        super(x, y);
        window_X_Size = param_Window_X_Size;
    }


    public void set_Skill_Move(int dpi_Add_X){
        x_Point += dpi_Add_X;

        if(x_Point > window_X_Size){
            live_Skill = true;
        }

    }



}
