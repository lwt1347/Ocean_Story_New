package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */
//핵파리
public class Main_Character_Moulluse_Tear8 extends Main_Character  {
    /**
     * 기본 생성자
     */
    Main_Character_Moulluse_Tear8(float x, float y, int window_Width_Size, int window_Height_Size) {
        super(x, y, window_Width_Size, window_Height_Size);
        this.set_Max_Hp(9);
        set_Damage(9);
    }
}

class Skill_Boom_Poison extends Skill_Body{

    Skill_Boom_Poison(float x, float y) {
        super(x, y);
    }

    //폭탄 떨어지는
    int life_Cycle = 0;
    boolean life_Cycle_Flag = false;
    public void set_Skill_Move(int param_Add_Length){

        y_Point += param_Add_Length;

        life_Cycle++;

        if(life_Cycle > 2) {
            skill_Status++;
        }

        if(life_Cycle >= 6){
            life_Cycle_Flag = true;
        }
    }

    public boolean get_Live(){
        return life_Cycle_Flag;
    }


}
