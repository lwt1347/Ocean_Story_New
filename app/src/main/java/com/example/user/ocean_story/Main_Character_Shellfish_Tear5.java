package com.example.user.ocean_story;

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
    Main_Character_Shellfish_Tear5(float x, float y) {
        super(x, y);
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