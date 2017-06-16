package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-21.
 */

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

    //********************************************************************************************//

    /**
     * 기본 생성자
     * 윈도우 크기와 hp 를 받아와 물고기를 생성한다.
     */

    Fish_Default_Body(int window_Width, int hp){

        this.window_Width = window_Width;                                       //생성될 위치 및 벽을 못 넘도록 하기 위해 사용
        this.hp = hp;

        //물고기의 시야 각도
        angle = (int)(Math.random() * 180) - 90;

        // 물고기 생성 좌표
        fish_Point_X = (float)(30 + Math.random() * (window_Width -100));        // x축 = 10 ~ 윈도우 널이 - 100 사이에서 생성
        fish_Point_Y = (float) Math.random() * 30 - 30;

    }

    //********************************************************************************************//

    /**
     *  반환 [얻어오기]
     */

    /**
     * 물고기 Hp 반환
     */
    public int get_Fish_Hp(){
        return hp;
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


    //********************************************************************************************//


    /**
     * 동작 함수
     * 물고기 움직이는 함수
     */
    public void fish_Object_Move(){
        fish_Point_Y += fish_Speed;
        fish_Point_X += (Math.sin(angle * Math.PI / 180)) * angle_X_Speed;

        if(Math.random() < 0.01) {
            fish_Pattern_Change();
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
     * 물고기 Hp 감소
     */
    public void set_Hp_Minus(){
        hp--;
    }

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



    //********************************************************************************************//



}




























//