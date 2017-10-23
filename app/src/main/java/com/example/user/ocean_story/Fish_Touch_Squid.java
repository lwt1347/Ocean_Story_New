package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */

public class Fish_Touch_Squid  extends Fish_Default_Body  {
    /**
     * 기본 생성자
     * 윈도우 크기와 hp 를 받아와 물고기를 생성한다.
     *
     */
    Fish_Touch_Squid(int window_Width, int hp, int param_Width_Size, int param_Height_Size, int t_Speed) {
        super(window_Width, hp, param_Width_Size, param_Height_Size, t_Speed);
        fish_Class = 1;
        fish_Point_Y = -400 - random.nextInt(200);
    }

    public void fish_Object_Move(){
        fish_Point_Y += fish_Speed*2;


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
        if(fish_Draw_Status > 14){
            fish_Draw_Status = 0;
        }
    }

    /**
     * 오징어 먹물 생성 때문에 더 위로
     */
    public void set_Position(){
        fish_Point_X = (float)(30 + Math.random() * (window_Width -100));        // x축 = 10 ~ 윈도우 널이 - 100 사이에서 생성
        fish_Point_Y = -200 - random.nextInt(400);
    }

    /**
     * 물고기 헤엄칠때 드로우할 그림 int 형 반환
     */
    public int get_Draw_Fish_Status()
    {
        return fish_Draw_Status/4; //물고기 헤엄 이미지 2번씩 송출
    }

}
