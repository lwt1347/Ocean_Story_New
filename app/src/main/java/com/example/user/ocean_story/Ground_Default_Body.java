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

    protected int hp;                               // Hp
    protected int width;                            //물고기와 다르게 직접 터치를 해야 하기 때문에 넓이와 높이 갚이 필요하다.
    protected int height;
    protected int ground_Draw_Status = 0;             //그라운드 움직임
    protected int ground_Class = 0;                     //그라운드 생명체의 종류를 알아옴

    protected float speed = 1;
    protected float window_Width = 0;               // 윈도우 크기를 가져와 물고기 생성할 위치 조절
    protected float ground_Point_X;                 // 바닥 생명체 생성될 좌표
    protected float ground_Point_Y;


    //********************************************************************************************//


    /**
     * 기본 생성자
     */

    Ground_Default_Body(float window_Width, int width, int height, int hp){

        //width height 를 이미지의 넓이를 파라미터로 가져온다.
        this.width = width;
        this.height = height;
        this.hp = hp;

        this.window_Width = window_Width;       //생성될 위치 및 벽을 못 넘도록 하기 위해 사용
        ground_Point_X = 30 + (float)Math.random() * (window_Width-100);         //생성될 위치

        //처음 생성된 객체인가.
        first_Test_Object = false;

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

    public int get_Ground_Hp(){
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

    //********************************************************************************************//


    /**
     * 동작 함수, 설정
     */

    public void set_Ground_Hp_Minus(){
        hp--;                           //체력깍기
    }
    public void set_Ground_Hp_Minus(int damage){
        hp = hp-damage;                           //체력깍기
    }

    public void ground_Object_Move(){               //오버라이드 인터페이스

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