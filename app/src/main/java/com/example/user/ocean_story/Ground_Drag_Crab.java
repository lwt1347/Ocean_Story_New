package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-22.
 */

public class Ground_Drag_Crab extends Ground_Default_Body{

    /**
     * Ground_Drag_Crab 변수
     */
    private int angle_X_Speed = 0;

    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     * @param hp
     */
    Ground_Drag_Crab(float window_Width, int width, int height, int hp) {
        super(window_Width, width, height, hp);

        //꽃게 생성될때 방향
        if(Math.random() < 0.5){
            angle_X_Speed = 3;
        }else angle_X_Speed = -3;

        ground_Class = 2;           //꽃게 2번
    }

    /**
     * 반환
     */


    //********************************************************************************************//

    /**
     * 꽃게 움직이기
     * 동작 함수, 설정
     */
    @Override
    public void ground_Object_Move() {


        ground_Point_Y += speed;
        ground_Point_X += Math.random() * angle_X_Speed;

        if(Math.random() < 0.01) {

            //속도 변화 주기
            speed = 2 + (float)Math.random() * 3;
            if(Math.random() < 0.5){
                angle_X_Speed = angle_X_Speed*-1;
            }
        }

        //왼쪽 오른쪽을 넘어가면 방향 반대로
        if(ground_Point_X <= 30 || ground_Point_X >= window_Width - 150){
            angle_X_Speed = angle_X_Speed*-1;
        }

        ground_Draw_Status++;
        if(ground_Draw_Status > 7){
            ground_Draw_Status = 0;
        }
    }

    //********************************************************************************************//

}
