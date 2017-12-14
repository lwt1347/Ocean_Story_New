package com.plank.user.ocean_story;

import android.util.Log;

import java.util.Random;

/**
 * Created by USER on 2017-01-21.
 */

public class Main_Character {

    /**
     *  Main_Character 변수 지정
     */

    private int damage = 1;                     //대미지 [업그레이드시 증가]
    private int attack_Speed = 2;               //공격속도 [낮을수록 빨라진다.]
    private int attack_Coll_Time = 0;           //공격 쿨 타임
    private float main_Character_Point_X;       //메인 캐릭터 위치 포인터
    private float main_Character_Point_Y;

    private int main_Character_Hp = 1;
    private int main_Character_Max_Hp = 1;
    private boolean attack_State = false;   //공격중인가?

    private int tear = 0;   //캐릭터의 티어를 결정한다.

    private int window_W_Size;
    private int window_H_Size;

    int character_W_Size = 0;
    int character_H_Size = 0;


    //********************************************************************************************//


    /**
     *  기본 생성자
     */
    Main_Character(float x, float y, int window_Width_Size, int window_Height_Size, int w_Size, int h_Size){
        main_Character_Point_X = x;//(x / 2) - 120;           //기본위치 임의로 정해놓은 상태
        main_Character_Point_Y = y;//(y - 350);
        window_W_Size = window_Width_Size;
        window_H_Size = window_Height_Size;

        character_W_Size = w_Size;
        character_H_Size = h_Size;
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

    public void set_Hp_Add(){
        main_Character_Hp = get_Max_Hp();
    }

    public int get_Max_Hp(){
        return main_Character_Max_Hp;
    }

    public int get_Tear(){
        return tear;
    }

    public int get_Weight_Size(){
        return character_W_Size;
    }
    public int get_Height_Size(){
        return character_H_Size;
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

    /**
     * 메인 캐릭터 1~6 가지 모션중 점수 획득하면 0 -> 2 -> 4로 승금한다.
     * 이떄 1, 3 ,5 컨트롤할 변수
     */
    private int main_Character_Mode_Status = 0;

    private int transform_Chra = 0;
    private boolean transform_Chra_Flag = false;

    public boolean get_Transform_Chra_Flag(){
        return transform_Chra_Flag;
    }
    //점수에 따라서 모드 변경
    public void Set_Main_Character_Mode_Status(){


        if(main_Character_Mode_Status < 4) {
            main_Character_Mode_Status += 2;
        }
    }
    public int get_Main_Character_Mode_Status(){
        return main_Character_Mode_Status;
    }
    public void set_Main_Character_Mode_Status_Init(){
        main_Character_Mode_Status = 0;
    }

    /**
     * 경험치
     */
    int experience = 0;
    public void set_Character_Experience(int index){
        experience+=index;
        upgrade_Experience+=index;
        revolution_Character = true;
    }

    public boolean set_Character_Revolution(int up_Score){
        if(up_Score <= experience){

            experience = 0;
            return true;
        }else {
            return false;
        }
    }
    private boolean revolution_Character = true;   //진화가 한 번만 이루어지도록



    /**
     * 적군을 멈추게 함
     */
    public void stop_Enemy(Fish_Default_Body fish_Touch_Default){
        fish_Touch_Default.set_Fish_Speed(0);
    }
    public void stop_Enemy(Ground_Default_Body ground_Default_Body){
        ground_Default_Body.set_Ground_Speed(0);
    }

//        if(revolution_Character) {
//            revolution_Character = false;
//            if (150 <= experience) {
//                return true;
//            } else if (100 <= experience) {
//                return true;
//            } else if (50 <= experience) {
//                return true;
//            }
//        }
//        return false;
//
//    }


    public float get_Main_Character_Point_X(){
        return main_Character_Point_X;
    }

    public float get_Main_Character_Point_Y(){
        return main_Character_Point_Y;
    }
    //********************************************************************************************//

    /**
     * pref 저장 클래스
     */
    protected int pref_Set = 100000001;
    public int get_Pref(){
        return pref_Set;
    }


    /**
     * 동작 함수, 설정
     */

    public void set_Damage(int param_Damage){
        damage = param_Damage;
    }

    public void set_Attack_Speed(){
        attack_Speed--;
    }

    /**
     * 캐릭터 hp 감소
     */
    boolean hit_Flag = false;
    public void set_Hp_Minus(){
        hit_Flag = true;
        main_Character_Hp --;
    }


    /**
     * 캐릭터 hp 지정
     */
    public void set_Hp(int param_Hp){
        main_Character_Hp = param_Hp;
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


    /**
     * 티어 증가
     */
    public void set_Tear(int paramTear){
        tear = paramTear;
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
    Random random = new Random();
    private int character_Draw_Status = 0;
    boolean direct_Flag_X = true;
    boolean direct_Flag_Stop_X = true;

    boolean direct_Flag_Y = true;
    boolean direct_Flag_Stop_Y = true;

    public void character_Moving() {

        transform_Chra++;

        if(transform_Chra > 5){
            transform_Chra_Flag = !transform_Chra_Flag;
            transform_Chra = 0;
        }


        if(0 >= main_Character_Point_X){
            direct_Flag_X = true;
        }else if(main_Character_Point_X >= window_W_Size - 150){
            direct_Flag_X = false;
        }

        if(random.nextFloat() < 0.01){
            direct_Flag_Stop_X = false;
            if(random.nextFloat() < 0.1){
                direct_Flag_X = !direct_Flag_X;
            }
        }
        if(random.nextFloat() < 0.01){
            direct_Flag_Stop_X = true;
            if(random.nextFloat() < 0.1){
                direct_Flag_X = !direct_Flag_X;
            }
        }


        if(direct_Flag_Stop_X) {
            if (direct_Flag_X) {
                main_Character_Point_X += 3 + random.nextInt(5);
            } else {
                main_Character_Point_X -= 3 + random.nextInt(5);
            }
        }


        if(window_H_Size/5 >= main_Character_Point_Y){
            direct_Flag_Y = true;
        }else if(main_Character_Point_Y >= window_H_Size - 150){
            direct_Flag_Y = false;
        }

        if(random.nextFloat() < 0.01){
            direct_Flag_Stop_Y = false;
            if(random.nextFloat() < 0.1){
                direct_Flag_Y = !direct_Flag_Y;
            }
        }
        if(random.nextFloat() < 0.01){
            direct_Flag_Stop_Y = true;
            if(random.nextFloat() < 0.1){
                direct_Flag_Y = !direct_Flag_Y;
            }
        }


        if(direct_Flag_Stop_Y) {
            if (direct_Flag_Y) {
                main_Character_Point_Y += 1 + random.nextInt(2);
            } else {
                main_Character_Point_Y -= 1 + random.nextInt(2);
            }
        }

    }

    public boolean get_Direct_Status(){
        return direct_Flag_X;
    }

    /**
     * 캐릭터 움직임
     */
    public void character_Move(){
        character_Draw_Status++;
        if(character_Draw_Status > 7){

            //공격상태로 변하면 잠시 후에 돌아온다.
            if(attack_State){
                set_Attack_State_False();
            }
            character_Draw_Status = 0;
        }
    }

    /**
     *  처맞았을때 효과 [피격 효과]
     */
    int blood = -1;
    public int get_Character_Hit(){

        if(hit_Flag){
            blood++;
            if(blood > 9){
                blood = -1;
                hit_Flag = false;
            }
            Log.e("@",blood/3 +  " #");
            return blood/3;
        }
        return -1;





    }


    //********************************************************************************************//
    /**
     * 모양 변화
     */
    int upgrade_Experience = 0;
    int up_Score = 0;
    public boolean set_Character_Upgrade(){

        if(tear == 0){
            up_Score = 1000;
        }else if(tear == 1){
            up_Score = 3000;
        }else if(tear == 2){
            up_Score = 5000;
        }else if(tear == 3){
            up_Score = 13300;
        }else if(tear == 4){
            up_Score = 23300;
        }else if(tear == 5){
            up_Score = 33300;
        }else if(tear == 6){
            up_Score = 50000;
        }else if(tear == 7){
            up_Score = 100000;
        }else if(tear == 8){
            up_Score = 130000;
        }else if(tear == 9){
            up_Score = 300000;
        }else if(tear == 10){
            up_Score = 600000;
        }

        if(up_Score <= upgrade_Experience){
            upgrade_Experience = 0;
            return true;
        }else {
            return false;
        }
    }

    /**
     * 티어당 진화 할 점수
     */
    public int get_Revolrution_Step(){
        if(tear == 0){
            return 3000;
        }else if(tear == 1){
            return 9000;
        }else if(tear == 2){
            return 15000;
        }else if(tear == 3){
            return 40000;
        }else if(tear == 4){
            return 70000;
        }else if(tear == 5){
            return 100000;
        }else if(tear == 6){
            return 150000;
        }else if(tear == 7){
            return 300000;
        }else if(tear == 8){
            return 500000;
        }else if(tear == 9){
            return 1000000;
        }else if(tear == 10){
            return 2000000;
        }

        return 50000000;
    }


}
