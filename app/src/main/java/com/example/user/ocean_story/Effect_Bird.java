package com.example.user.ocean_story;

import android.util.Log;

/**
 * Created by Lee on 2017-09-24.
 */

public class Effect_Bird {

    float x_Point = 0;
    float y_Point = 0;
    boolean attack = false;
    int speed = 100;
    int speed_Temp = 0;

    Effect_Bird(){

    }

    public void position(float x, float y){
        x_Point = x;
        y_Point = y;
    }
    public float get_X_Point(){
        return x_Point;
    }
    public float get_Y_Point(){
        return y_Point;
    }

    //공격속도
    public void set_Speed(int speed){
        this.speed = speed;
    }
    public int get_Speed(){
        return speed;
    }

    public boolean motion(){
        speed_Temp++;

        if(speed <= speed_Temp ){
            speed_Temp = 0;
            attack = true;
        }else {
            attack = false;
        }

        return attack;
    }


}
