package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */

class Main_Character_Fish_Tear2 extends Main_Character {

    public Main_Character_Fish_Tear2(float x, float y,  int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size,w_Size, h_Size);
        pref_Set = 100000200;
        this.set_Max_Hp(3);
        set_Damage(3);
    }
}

class Skill_Thorn  extends Skill_Body{

    Skill_Thorn(float x, float y) {
        super(x, y);
    }

    private boolean skill_Status_Add_S = true;
    public void set_Skill_Move(){

            if(skill_Status_Add_S) {
                skill_Status++;
            }else{
                skill_Status--;
            }

            if(skill_Status > 2){
                skill_Status_Add_S = false;
            }

            if(skill_Status == -1){
                live_Skill = true;
            }

    }
    //스킬 상태가져오기
    public int get_Skill_Status(){
        return skill_Status;
    }

    boolean live_Skill = false;
    //true 가 반환되면 객체 삭제
    public boolean get_Live(){
        return live_Skill;
    }

}