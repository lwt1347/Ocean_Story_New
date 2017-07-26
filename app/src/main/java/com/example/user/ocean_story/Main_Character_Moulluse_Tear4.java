package com.example.user.ocean_story;

import java.util.Random;

/**
 * Created by Lee on 2017-07-02.
 */
//버터구이
public class Main_Character_Moulluse_Tear4 extends Main_Character  {
    /**
     * 기본 생성자
     */
    Main_Character_Moulluse_Tear4(float x, float y) {
        super(x, y);
        this.set_Max_Hp(5);
        set_Damage(5);
    }
}

class Skill_Butter extends Skill_Body{
    Random random;
    Skill_Butter(float x, float y) {
        super(x, y);
    }

    boolean play_Attack = false;
    boolean delete_Attack = false;

    public void set_Position(float x, float y){

        random = new Random();

        x_Point = 30 + (random.nextFloat() * x) - 60;
        y_Point = 30 + (random.nextFloat() * y) - 120;;

    }

    public boolean get_Delete_Attack(){
        return delete_Attack;
    }

    public void set_play_Attack(){
//            play_Attack = true;
        skill_Status++;
        if(skill_Status >= 3){
            live_Skill = true;
            skill_Status = 0;
        }
    }





}
