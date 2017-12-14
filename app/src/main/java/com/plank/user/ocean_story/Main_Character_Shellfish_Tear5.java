package com.plank.user.ocean_story;

/**
 * Created by Lee on 2017-07-01.
 */

//간장게장 생성
public class Main_Character_Shellfish_Tear5 extends Main_Character {

    /**
     * 기본 생성자
     *
     * @param x
     * @param y
     */
    Main_Character_Shellfish_Tear5(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size, w_Size, h_Size);
        pref_Set = 100050000;
        this.set_Max_Hp(6);
        set_Damage(6);
    }
}
//집게발 스킬
class Skill_Soycrab_Claws extends Skill_Body{


    Skill_Soycrab_Claws(float x, float y) {
        super(x, y);
    }



    public int get_Skill_Status(){
        return skill_Status / 3;
    }

    public void set_Skill_Move(){
        skill_Status++;
        if(skill_Status == 12){
            skill_Status = 0;
            live_Skill = true;
        }
    }


}