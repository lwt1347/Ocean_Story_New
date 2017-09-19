package com.example.user.ocean_story;

import java.util.Random;

/**
 * Created by Lee on 2017-07-02.
 */
//독문어
public class Main_Character_Moulluse_Tear10 extends Main_Character  {
    /**
     * 기본 생성자
     */
    Main_Character_Moulluse_Tear10(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size, w_Size, h_Size);
        pref_Set = 110000000;
        this.set_Max_Hp(11);
        set_Damage(11);
    }


}
class Skill_Poison_Cloud extends Skill_Body {

    //독구름 이미지 랜덤
    int model  = 0;
    Random random = new Random();


    Skill_Poison_Cloud(float x, float y) {
        super(x, y);
        model = random.nextInt(3);
    }

    private boolean skill_Status_Add_S = true;

    public int get_Model(){
        return model;
    }
    int live_time = 0;
    public void set_Skill_Move(){

        live_time++;

        if(skill_Status_Add_S) {
            skill_Status++;
        }else{
            skill_Status--;
        }

        if(skill_Status > 6){
            skill_Status_Add_S = false;
        }else if(skill_Status <= 0){
            skill_Status_Add_S = true;
        }

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


}
