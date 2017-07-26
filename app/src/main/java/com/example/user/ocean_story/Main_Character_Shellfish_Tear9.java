package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-01.
 */

//소라 생성
public class Main_Character_Shellfish_Tear9 extends Main_Character {

    /**
     * 기본 생성자
     *
     * @param x
     * @param y
     */
    Main_Character_Shellfish_Tear9(float x, float y) {
        super(x, y);
        this.set_Max_Hp(10);
        set_Damage(10);
    }
}

class Skill_Wave extends Skill_Body{

    Skill_Wave(float x, float y) {
        super(x, y);

    }

    public void set_Skill_Move(int dpi_Add_Y){
        y_Point -= dpi_Add_Y;

        if(skill_Status < 6) {
            skill_Status++;
        }

        if(skill_Status == 6){
            skill_Status = 0;
        }

        if(-200 > y_Point){
            live_Skill = true;
        }

    }

    //스킬 상태가져오기
    public int get_Skill_Status(){
        return skill_Status/2;
    }


}
