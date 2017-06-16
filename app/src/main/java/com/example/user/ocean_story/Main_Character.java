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

    //********************************************************************************************//

    /**
     *  기본 생성자
     */

    Main_Character(){
        main_Character_Point_X = 300;           //기본위치 임의로 정해놓은 상태
        main_Character_Point_Y = 700;
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

    //터치후 쿨타임 돌기
    public void set_Attack_Cool_Time(){
        attack_Coll_Time++;
        if(attack_Coll_Time >= attack_Speed){
            attack_Coll_Time = 0;
        }
    }


    //********************************************************************************************//

}
