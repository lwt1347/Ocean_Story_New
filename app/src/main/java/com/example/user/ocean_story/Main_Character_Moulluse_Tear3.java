package com.example.user.ocean_story;

import java.util.Random;

/**
 * Created by Lee on 2017-07-02.
 */
//오징어
public class Main_Character_Moulluse_Tear3 extends Main_Character  {
    /**
     * 기본 생성자
     */
    Main_Character_Moulluse_Tear3(float x, float y, int window_Width_Size, int window_Height_Size) {
        super(x, y, window_Width_Size, window_Height_Size);
        this.set_Max_Hp(4);
        set_Damage(4);
    }



}

class Skill_Slow_Cloud extends Skill_Body{
    Random random;
    Skill_Slow_Cloud(float x, float y) {
        super(x, y);
        random = new Random();
        angle = random.nextFloat() * 359;
    }


    int live_time = 0;
    float angle = 0;
    public void set_Skill_Move(){

        live_time++;

        if(live_time >= 100){
            live_Skill = true;
        }

    }
    //스킬 상태가져오기
    public int get_Skill_Status(){
        return skill_Status/3;
    }

    boolean live_Skill = false;
    //true 가 반환되면 객체 삭제
    public boolean get_Live(){
        return live_Skill;
    }


    public float getAngle() {
        return angle;
    }
}
