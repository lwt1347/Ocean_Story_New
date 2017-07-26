package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */
//문어
public class Main_Character_Moulluse_Tear9 extends Main_Character  {
    /**
     * 기본 생성자
     */
    Main_Character_Moulluse_Tear9(float x, float y) {
        super(x, y);
        this.set_Max_Hp(10);
        set_Damage(10);
    }
}

class Skill_Wall extends Skill_Body{

    Skill_Wall(float x, float y) {
        super(x, y);
    }


    int live_time = 0;
    public void set_Skill_Move(){
        if(skill_Status < 3) {
            skill_Status++;
        }
        live_time++;

        if(live_time >= 100){
            live_Skill = true;
        }

    }


}