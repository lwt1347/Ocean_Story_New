package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-19.
 */

public class LandMark_Damage_View {

    int point_x;
    int point_y;
    int change_Time = 0;
    int live_Time = 0;
    int damage = 0;
    boolean change_Flag = true;
    boolean live_Flag = false;


    LandMark_Damage_View(int x, int y){
        point_x = x;
        point_y = y;
    }

    public void set_Damage(int param_Pamage){
        damage = param_Pamage;
    }

    public int get_Damage(){
        return damage;
    }

    public void set_Text_Move(){
        change_Time++;
        live_Time++;

        if(change_Flag) {
            point_x += 3;
        }else {
            point_x -= 3;
        }

        point_y-=7;

        if(change_Time == 10){
            change_Flag = !change_Flag;
            change_Time = 0;
        }
        if(live_Time == 30){
            live_Flag = true;
        }

    }

    boolean remove_Flag = false;
    public void set_Remove(){
        remove_Flag = true;
    }
    public boolean get_Remove(){
        return remove_Flag;
    }


    public int get_X_Point(){
        return point_x;
    }
    public int get_Y_Point(){
        return point_y;
    }
    public boolean get_Live_Flag(){
        return live_Flag;
    }


}


