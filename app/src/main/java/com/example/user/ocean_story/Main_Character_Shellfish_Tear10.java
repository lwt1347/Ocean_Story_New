package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-01.
 */

//거북이 생성
public class Main_Character_Shellfish_Tear10 extends Main_Character {

    /**
     * 기본 생성자
     *
     * @param x
     * @param y
     */
    Main_Character_Shellfish_Tear10(float x, float y, int window_Width_Size, int window_Height_Size) {
        super(x, y, window_Width_Size, window_Height_Size);
        this.set_Max_Hp(11);
        set_Damage(11);
    }
}
class Skill_Stomp extends Skill_Body{

        boolean live_Skill = false;

            Skill_Stomp(float x, float y) {
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
        //true 가 반환되면 객체 삭제
        public boolean get_Live(){
                return live_Skill;
                }

}