package com.example.user.ocean_story;

import java.util.Random;

/**
 * Created by Lee on 2017-07-02.
 */

class Main_Character_Fish_Tear4 extends Main_Character {

    public Main_Character_Fish_Tear4(float x, float y, int window_Width_Size, int window_Height_Size) {
        super(x, y, window_Width_Size, window_Height_Size);
        this.set_Max_Hp(5);
        set_Damage(5);
    }
}

class Skill_Earthquake extends Skill_Body{

    int angle = 0;
    Random random;

    Skill_Earthquake(float x, float y) {
        super(x, y);
        random = new Random();
       angle = random.nextInt(359);
    }

    int live_time = 0;
    public void set_Skill_Move(){
        live_time++;
        if(live_time == 4){
            live_Skill = true;
        }
        skill_Status++;
    }

    boolean live_Skill = false;
    //true 가 반환되면 객체 삭제
    public boolean get_Live(){
        return live_Skill;
    }

    //각도
    public int get_Angle(){
        return angle;
    }


}
