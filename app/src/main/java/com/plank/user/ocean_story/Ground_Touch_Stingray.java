package com.plank.user.ocean_story;

/**
 * Created by USER on 2017-01-21.
 * 가오리
 */

public class Ground_Touch_Stingray extends Ground_Default_Body {


   int class_Num = 0;
    private int angle_X_Speed = 0;
    private boolean cloaking = false;

    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     */
    Ground_Touch_Stingray(float window_Width, int width, int height, int hp, int param_Width_Size, int param_Height_Size,int t_Speed) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size, t_Speed);
        ground_Class = 1;   //가오리 = 1

        if(Math.random() < 0.5){
            angle_X_Speed = 3;
        }else angle_X_Speed = -3;

    }

    //********************************************************************************************//

    /**
     * 반환
     */
    public int get_Class_Num(){
        return class_Num;
    }

    //********************************************************************************************//



    /**
     * 가오리 움직이기
     * 동작 함수, 설정
     */
    @Override
    public void ground_Object_Move() {


        ground_Point_Y += speed;
        ground_Point_X += Math.random() * angle_X_Speed;

        if(Math.random() < 0.01) {

            //속도 변화 주기
            speed = (2f + ((float)Math.random() * 3)/2) * temp_Speed;
            if(Math.random() < 0.5){
                angle_X_Speed = angle_X_Speed*-1;
            }
        }

        //왼쪽 오른쪽을 넘어가면 방향 반대로
        if(ground_Point_X <= 30 || ground_Point_X >= window_Width - 150){
            angle_X_Speed = angle_X_Speed*-1;
        }

        ground_Draw_Status++;
        if(ground_Draw_Status > 15){
            ground_Draw_Status = 0;
        }

        if(random.nextInt(100) < 10){
            cloaking = true;
        }else if(cloaking){
            if(random.nextInt(100) < 2){
                cloaking = false;
            }
        }


    }

    public boolean get_Cloaking_State(){
        return cloaking;
    }

    public int get_Draw_Ground_Status()          // 그라운드 드로우할 그림 int형 반환
    {
        return ground_Draw_Status/4;              // 그라운드 이미지 2번씩 송출
    }



    //********************************************************************************************//
}
