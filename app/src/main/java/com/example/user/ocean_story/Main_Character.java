package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-21.
 */

public class Main_Character {

    /**
     *  Main_Character 변수 지정
     */

    private int damage = 1;                     //대미지 [업그레이드시 증가]
    private int attack_Speed = 3;               //공격속도 [낮을수록 빨라진다.]
    private int attack_Coll_Time = 0;           //공격 쿨 타임
    private float main_Character_Point_X;       //메인 캐릭터 위치 포인터
    private float main_Character_Point_Y;

    private int main_Character_Hp = 4;
    private int main_Character_Max_Hp = 1;
    private boolean attack_State = false;   //공격중인가?


    //********************************************************************************************//

    /**
     *  기본 생성자
     */

    Main_Character(float x, float y){
        main_Character_Point_X = (x / 2) - 120;           //기본위치 임의로 정해놓은 상태
        main_Character_Point_Y = (y - 350);
    }

    //********************************************************************************************//

    /**
     * 반환 [얻어오기]
     */

    public int get_Attack_Cool_time(){          //쿨타임 반환
        return attack_Coll_Time;
    }

    public int get_Attack_Speed(){              //공격속도 반환
        return attack_Speed;
    }

    public int get_Damage(){                    //대미지 반환
        return damage;
    }

    public int get_Hp(){
        return main_Character_Hp;
    }

    public int get_Max_Hp(){
        return main_Character_Max_Hp;
    }

    /**
     * 캐릭터가 공격중인가 아닌가.
     */
    public void set_Attack_State_False(){
        attack_State = false;
    }

    public void set_Attack_State_True(){
        attack_State = true;
    }

    /**
     * 공격상태 반환
     */
    public boolean get_Attack_State(){
        return attack_State;
    }



    public float get_Main_Character_Point_X(){
        return main_Character_Point_X;
    }

    public float get_Main_Character_Point_Y(){
        return main_Character_Point_Y;
    }
    //********************************************************************************************//

    /**
     * 동작 함수, 설정
     */

    public void set_Damage_Up(){
        damage++;
    }

    public void set_Attack_Speed(){
        attack_Speed--;
    }

    /**
     * 캐릭터 hp 감소
     */
    public void set_Hp_Minus(){
        main_Character_Hp --;
    }

    /**
     *진화 생물에따라 hp 변경
     */
    protected void set_Max_Hp(int hp){
        main_Character_Max_Hp = hp;
    }

    /**
     * 진화와 동시에 hp 채움
     */
    public void set_Hp_Init(){
        main_Character_Hp = main_Character_Max_Hp;
    }



    //터치후 쿨타임 돌기
    public void set_Attack_Cool_Time(){
        attack_Coll_Time++;
        if(attack_Coll_Time >= attack_Speed){
            attack_Coll_Time = 0;
        }
    }


    /**
     * 움직임 이펙트
     */
    private int character_Draw_Status = 0;
    public void character_Move() {

        character_Draw_Status++;
        if(character_Draw_Status > 7){

            //공격상태로 변하면 잠시 후에 돌아온다.
            if(attack_State){
                set_Attack_State_False();
            }
            character_Draw_Status = 0;
        }
    }


    //********************************************************************************************//

}
