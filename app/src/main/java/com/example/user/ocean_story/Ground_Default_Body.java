package com.example.user.ocean_story;

import java.util.Random;

/**
 * Created by USER on 2017-01-21.
 */

public class Ground_Default_Body {

    /**
     * Ground_Default_Body 변수 지정
     * 기본 바닥 생명체 변수지정 메모리가 작은 순서로 지정함
     */

    protected double hp;                               // Hp
    protected int width;                            //물고기와 다르게 직접 터치를 해야 하기 때문에 넓이와 높이 갚이 필요하다.
    protected int height;
    protected int ground_Draw_Status = 0;             //그라운드 움직임
    protected int ground_Class = 0;                     //그라운드 생명체의 종류를 알아옴

    protected float speed = 1;
    protected float window_Width = 0;               // 윈도우 크기를 가져와 물고기 생성할 위치 조절
    protected float ground_Point_X;                 // 바닥 생명체 생성될 좌표
    protected float ground_Point_Y;

    private boolean status_Poison = false;              //중독상태 인지 아닌지 반환한다.

    private int poison_Damage = 1;      //독대미지
    //********************************************************************************************//


    /**
     * 기본 생성자
     */

    Ground_Default_Body(float window_Width, int width, int height, double hp, int param_Width_Size, int param_Height_Size){

        //width height 를 이미지의 넓이를 파라미터로 가져온다.
        this.width = width;
        this.height = height;
        this.hp = hp;

        this.window_Width = window_Width;       //생성될 위치 및 벽을 못 넘도록 하기 위해 사용
        ground_Point_X = 30 + (float)Math.random() * (window_Width);         //생성될 위치

        //처음 생성된 객체인가.
        first_Test_Object = false;

        //이미지 크기
        width_Size = param_Width_Size;
        height_Size = param_Height_Size;
    }


    //********************************************************************************************//


    /**
     * 가장 처음 생성된 바닥 생물인가?
     */
    private boolean first_Test_Object;



    //성게 생성
    public void set_First_Test_Object(int x_Point, int y_Point){
        ground_Point_X = x_Point;
        ground_Point_Y = y_Point;
        first_Test_Object = true;
    }
    //움직이는 바닥 생명체
    public void set_First_Test_Object(int x_Point){
        ground_Point_X = x_Point;
        ground_Point_Y = 100;
        first_Test_Object = true;
    }

    /**
     * 물고기 속도 조절
     */
    public void set_Ground_Speed(int param_Speed){
        speed = param_Speed;
    }


    public boolean get_First_Test_Object(){
        return first_Test_Object;
    }




    /**
     *  반환 [얻어오기]
     */

    public double get_Ground_Hp(){
        return hp;                              //바닥 생명체 Hp 가져오기
    }

    public int get_Draw_Ground_Status()          // 그라운드 드로우할 그림 int형 반환
    {
        return ground_Draw_Status/2;              // 그라운드 이미지 2번씩 송출
    }

    public float get_GroundPoint_Height(){
        return height;                          //바닥 생명체 이미지 높이 가져오기
    }

    public float get_GroundPoint_Width(){
        return width;                           //바닥 생명체 이미지 넓이 가져오기
    }

    public float get_Ground_Point_X(){
        return ground_Point_X;                  //바닥 생명체 x좌표 가져오기
    }

    public float get_Ground_Point_Y(){
        return ground_Point_Y;                  //바닥 생명체 y좌표 가져오기
    }

    public int get_Ground_Class(){              //바닥 생명체 종류 알아오기
        return ground_Class;
    }

    public float get_Ground_Speed(){
        return speed;
    }

    /**
     * 그라운드 이미지 크기 알아오기
     */
    int width_Size = 0;
    int height_Size = 0;
    public Integer get_Width_Size(){
        return width_Size;
    }
    public Integer get_Height_Size(){
        return height_Size;
    }






    /**
     * 중독 hp 감소
     */
    int poison_Timer = 0;     //중독일때 hp 감소
    int poison_Status = 1;
    public void set_Status_Poison_AttacK(){
        //확률적으로 독이 풀린다.
        if(Math.random() < 0.005){
            status_Poison = false;
        }

        //hp 깍는 주기
        poison_Timer++;
        if(poison_Timer >= 15){
            hp-= poison_Damage;
            poison_Timer = 0;
        }

        //포이즌 이팩트 그리기
        if(poison_Timer%2 == 0){
            poison_Status++;
        }
        if(poison_Status > 7){
            poison_Status = 1;
        }


    }
    public int get_Status_Poison_AttacK(){
        return poison_Status;
    }

    /**
     * 중독 상태 반환
     */
    public boolean get_Status_Poison(){

        return status_Poison;
    }

    ///일반 인가, 보스인가, 중보스인가.
    public int get_Class_Num(){
        return 0;
    }



    //********************************************************************************************//
    /*
    오브젝트 풀링을 위한 위치 재선정 - 살아났을때
     */
    public void set_Position(){

        ground_Point_X = 30 + (float)Math.random() * (window_Width);         //생성될 위치
        ground_Point_Y = -100 - random.nextInt(100);
    }
    public void set_Position(float x, float y){
        ground_Point_X = x;       // x축 = 10 ~ 윈도우 널이 - 100 사이에서 생성
        ground_Point_Y = y;
    }

    //보스 달팽이 인가?
    // child = 0 아무것도 아님, 1일때 기본 달팽이, 2일때 중간 보스 달팽이
    int child = 0;
    public void set_Child_Ground(int param){
        child = param;
    }

    public int get_Child_Ground(){
        return child;
    }
    //true 일 때 보임
    boolean scf_Flag = true;
    public void set_Visible_Ground_Flag(boolean param){
        scf_Flag = param;
    }
    public boolean get_Visible_Ground_Flag(){
        return scf_Flag;
    }


    /**
     * 동작 함수, 설정
     */

    public void set_Ground_Hp_Minus(){
        hp--;                           //체력깍기
    }
    public void set_Ground_Hp_Minus(int damage){
        hp = hp-damage;                           //체력깍기
    }
    public void set_Ground_Hp(int param){
        hp = param;
    }


    public void ground_Object_Move(){               //오버라이드 인터페이스

    }

    /**
     * 중독
     */

    public void set_Status_Poison(int param_Poison_Damage){
        poison_Damage = param_Poison_Damage;
        status_Poison = true;
    }



    //********************************************************************************************//

    /**
     * 속도가 0일때 슬로우 이팩트 어떤 것으로 표현 할지
     */
    Random random = new Random();
    protected int effect_Slow_Status = 1;
    public void set_Slow_Effect(){
        effect_Slow_Status = random.nextInt(3) + 1;
    }
    public int get_Slow_Effect(){
        return effect_Slow_Status;
    }



}




























//