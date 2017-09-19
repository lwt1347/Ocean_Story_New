package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-01.
 */

//새우 생성
public class Main_Character_Shellfish_Tear6 extends Main_Character {

    /**
     * 기본 생성자
     *
     * @param x
     * @param y
     */
    Main_Character_Shellfish_Tear6(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size, w_Size,  h_Size);
        pref_Set = 100060000;
        this.set_Max_Hp(7);
        set_Damage(7);
    }
}

class Skill_Thorn2 extends Skill_Body{

    Skill_Thorn2(float x, float y) {
        super(x, y);
    }

    public void set_Skill_Move(int dpi_Add_Y){
        y_Point -= dpi_Add_Y;

        if(skill_Status < 3) {
            skill_Status++;
        }

        if(skill_Status == 3){
            skill_Status = 0;
        }

        if(-200 > y_Point){
            live_Skill = true;
        }

    }

    public void set_Live(){
        live_Skill = true;
    }


}