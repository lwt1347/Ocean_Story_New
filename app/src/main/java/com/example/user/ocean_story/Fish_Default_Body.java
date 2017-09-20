package com.example.user.ocean_story;

import java.util.Random;

/**
 * Created by USER on 2017-01-21.
 */
//1 번 기본 물고기, 1번 오징어 ,2번 드래그 물고기,  10번 해파리, 3번 방해거북
public class Fish_Default_Body {

    /**
     * Fish_Default_Body 변수 지정
     * 기본 물고기 변수지정 메모리가 작은 순서로 지정함
     */

    protected int angle = 0;                    // 물고기 보는 각도
    protected int angle_X_Speed = 1;            // 물고기 각도 x축 으로 스피드 조절
    protected int window_Width = 0;             // 윈도우 크기를 가져와 물고기 생성할 위치 조절
    protected int fish_Draw_Status = 0;         // 물고기 상태에 따라 움직임 표현
    protected int hp;                           // Hp 마다 물고기 색깔 달라지게 할것
    protected int fish_Class;                   // 가장 가까운 물고기 객체를 찾기위해서, 물고기마다 터치 방식이 다름으로 객체 번호로 식별한다.
                                                // 기본 물고기 = 1, 드래그 물고기 = 2

    protected float fish_Speed = 1;              // 물고기 y축 스피드
    protected float fish_Point_X;                // 물고기 생성될 좌표
    protected float fish_Point_Y;
    private float _Fish_Speed;
    private int poison_Damage = 1;      //독대미지


    private boolean status_Poison = false;              //중독상태 인지 아닌지 반환한다.
    //********************************************************************************************//

    /**
     * 기본 생성자
     * 윈도우 크기와 hp 를 받아와 물고기를 생성한다.
     */

    Fish_Default_Body(int window_Width, int hp, int param_Width_Size, int param_Height_Size){

        this.window_Width = window_Width;                                       //생성될 위치 및 벽을 못 넘도록 하기 위해 사용
        this.hp = hp;
        child = 0;
        //물고기의 시야 각도
        angle = (int)(Math.random() * 180) - 90;

        // 물고기 생성 좌표
        fish_Point_X = (float)(30 + Math.random() * (window_Width -100));        // x축 = 10 ~ 윈도우 널이 - 100 사이에서 생성
//        fish_Point_Y = (float) Math.random() * 30 - 30;
        fish_Point_Y = -100 - random.nextInt(500);

        //처음 생성되는 물고기
        first_Test_Object = false;

        //물고기 이미지 사이즈
        width_Size = param_Width_Size;
        height_Size = param_Height_Size;
    }

    //********************************************************************************************//

    /*
    오브젝트 풀링을 위한 위치 재선정 - 살아났을때
     */
    public void set_Position(){
        fish_Point_X = (float)(30 + Math.random() * (window_Width -100));        // x축 = 10 ~ 윈도우 널이 - 100 사이에서 생성
        fish_Point_Y = -100 - random.nextInt(500);
    }
    public void set_Position(float x, float y){
        fish_Point_X = x;       // x축 = 10 ~ 윈도우 널이 - 100 사이에서 생성
        fish_Point_Y = y;
    }





    /**
     * 가장 처음 생성된 물고기인가?
     */
    boolean first_Test_Object;
    public void set_First_Test_Object(int x_Point){
        fish_Point_X = x_Point;
        fish_Point_Y = 100;
        first_Test_Object = true;
    }
    public boolean get_First_Test_Object(){
        return first_Test_Object;
    }

    /**
     *  반환 [얻어오기]
     */

    /**
     * 물고기 Hp 반환
     */
    public int get_Fish_Hp(){
        return hp;
    }

    //오브젝트 풀링 을 위한 hp 채우기
    public void set_Fish_Hp(int param_Hp){
        hp = param_Hp;
    }

    /**
     * 물고기 각도 반환
     */
    public int get_Fish_Angle(){
        return angle;
    }

    /**
     * 물고기 헤엄칠때 드로우할 그림 int 형 반환
     */
    public int get_Draw_Fish_Status()
    {
        return fish_Draw_Status/2; //물고기 헤엄 이미지 2번씩 송출
    }

    /**
     * 물고기 좌표를 반환한다.
     */
    public float get_Fish_Point_X(){
        return fish_Point_X;
    }
    public float get_Fish_Point_Y(){
        return fish_Point_Y;
    }

    /**
     * 물고기 종류를 반환한다.
     * @return
     */
    public int get_Fish_Class() {
        return fish_Class;
    }

    /**
     * 중독 hp 감소
     */
    int poison_Timer = 0;
    int poison_Status = 1;
    public void set_Status_Poison_AttacK(){
        //확률적으로 독이 풀린다.
        if(Math.random() < 0.005){
            status_Poison = false;
        }

        //hp 깍는 주기
        poison_Timer++;
        if(poison_Timer >= 15){
            hp -= poison_Damage;
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


    /**
     * 물고기 이미지 크기 알아오기
     */
    int width_Size = 0;
    int height_Size = 0;
    public Integer get_Width_Size(){
        return width_Size;
    }
    public Integer get_Height_Size(){
        return height_Size;
    }


    //********************************************************************************************//



    //보스 물고기 잡으면 나오는 물고기 인가?
    // child = 0 아무것도 아님, 1일때 기본 물고기, 2일때 중간 보스 물고기
    int child = 0;
    public void set_Child_Fish(int param){
        child = param;
    }

    public int get_Child_Fish(){
        return child;
    }
    //true 일 때 보임
    boolean scf_Flag = true;
    public void set_Visible_Fish_Flag(boolean param){
        scf_Flag = param; //새끼인지 아닌지.
    }
    public boolean get_Visible_Fish_Flag(){
        return scf_Flag; //새끼인지 아닌지.
    }


    /**
     * 동작 함수
     * 물고기 움직이는 함수
     */
    public void fish_Object_Move(){
        fish_Point_Y += fish_Speed;


        //가장 처음에 나오는 물고기는 직선으로 움직인다.
        if(first_Test_Object) {
            angle = 0;
        }else {

            fish_Point_X += (Math.sin(angle * Math.PI / 180)) * angle_X_Speed;

            //각도 변형되지 않는다.
            if(Math.random() < 0.01) {
                fish_Pattern_Change();
            }
        }


        //왼쪽 오른쪽을 넘어가면 방향 반대로
        if(fish_Point_X <= 30 || fish_Point_X >= window_Width - 150){
            angle_X_Speed = angle_X_Speed*-1;
        }

        //헤엄치면서 물고기 그림 상태 변형
        fish_Draw_Status++;
        if(fish_Draw_Status > 7){
            fish_Draw_Status = 0;
        }
    }

    /**
     * 물고기 속도 조절
     */
    public void set_Fish_Speed(int param_Speed){
        fish_Speed = param_Speed;
    }


    /**
     * 물고기 Hp 감소
     */
    public void set_Hp_Minus(){
        hp--;
    }
    public void set_Hp_Minus(int damage){
        hp = hp - damage;
    }

    /**
     * 중독
     */
    public void set_Status_Poison(int param_Poison_Damage){
        poison_Damage = param_Poison_Damage;
        status_Poison = true;
    }


    //번환



    /**
     * 물고기 패턴 변경
     */
    protected void fish_Pattern_Change(){
        angle = (int)(Math.random() * 180) - 90;
        angle_X_Speed = (int)(Math.random() * 3);

        //각도에 따라 속도 조절
        if(angle < -45){
            fish_Speed = 2 + (float)Math.random() * 3;
        }else if(angle < 45){
            fish_Speed = 2 + (float)Math.random() * 5;
        }else {
            fish_Speed = 2 + (float)Math.random() * 3;
        }

    }


    /**
     * 물고기가 공격중인가?
     */
    public boolean get_Attack_Mode(){
        return false;
    }

    public float get_Fish_Speed() {
        return fish_Speed;
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


    //기본인가, 중보스인가, 보스인가.
    public int get_Class_Num() {
        return 0;
    }
}




























//