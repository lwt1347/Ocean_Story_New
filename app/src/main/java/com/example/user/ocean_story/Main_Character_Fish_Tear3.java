package com.example.user.ocean_story;

import java.util.Random;

/**
 * Created by Lee on 2017-07-02.
 */

class Main_Character_Fish_Tear3 extends Main_Character {

    public Main_Character_Fish_Tear3(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size,w_Size, h_Size);
        this.set_Max_Hp(4);
        set_Damage(4);
    }

}

class Skill_Teeth_Mine extends Skill_Body{
    Random random;
    Skill_Teeth_Mine(float x, float y) {
        super(x, y);
    }

    public void set_Position(float x, float y){

        random = new Random();


        x_Point = 30 + (random.nextFloat() * x) - 60;
        y_Point = 30 + (random.nextFloat() * y) - 120;;

    }

    boolean play_Attack = false;
    boolean delete_Attack = false;

    public boolean get_Delete_Attack(){
        return delete_Attack;
    }
    public void set_play_Attack(){
        play_Attack = true;
    }


    public void set_Skill_Move(){
        if(skill_Status <= 3 && play_Attack) {
            skill_Status++;
        }
        if(skill_Status == 3 && play_Attack){
            delete_Attack = true;
        }
    }

    //스킬 상태가져오기
    public int get_Skill_Status(){
        return skill_Status;
    }

}
