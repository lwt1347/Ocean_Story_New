package com.example.user.ocean_story;

import java.util.Random;

/**
 * Created by Lee on 2017-07-01.
 */

//새우튀김 생성
public class Main_Character_Shellfish_Tear7 extends Main_Character {

    /**
     * 기본 생성자
     *
     * @param x
     * @param y
     */
    Main_Character_Shellfish_Tear7(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size,window_Height_Size,w_Size, h_Size);
        this.set_Max_Hp(8);
        set_Damage(8);
    }
}
class Skill_Fry extends Skill_Body{
    Random random;
    Skill_Fry(float x, float y) {
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
        if(skill_Status >= 6) {
            play_Attack = true;
        }
    }
    public boolean get_Play_Attack(){
        return play_Attack;
    }


    public void set_Skill_Move(){


        skill_Status++;

        if(!play_Attack){
            if(skill_Status > 6 ){
                skill_Status = 0;
            }
        }

        if(skill_Status == 15){
            delete_Attack = true;
        }

    }

    //스킬 상태가져오기
    public int get_Skill_Status(){
        return skill_Status/3;
    }

}