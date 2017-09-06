package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-13.
 */

public class Skill_Body {
    //스킬 몸체
    float x_Point = 0;
    float y_Point = 0;

    protected int skill_Status = 0;

    Skill_Body(float x, float y){
        x_Point = x;
        y_Point = y;
    }

    boolean remove_Flag = false;
    //지우기
    public void set_Remove(){
        remove_Flag = true;
    }
    public boolean get_Remove(){
        return remove_Flag;
    }


    //위치 가져오기
    public float get_X_Point(){
        return x_Point;
    }

    public float get_Y_Point(){
        return y_Point;
    }


    //스킬 상태가져오기
    public int get_Skill_Status(){
        return skill_Status;
    }

    int live_time = 0;
    public void set_Skill_Move(){
        live_time++;
        if(live_time == 3){
            live_Skill = true;
        }
        skill_Status++;
    }

    boolean live_Skill = false;
    //true 가 반환되면 객체 삭제
    public boolean get_Live(){
        return live_Skill;
    }

}
