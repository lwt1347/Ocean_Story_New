package com.example.user.ocean_story;

import java.util.Random;

/**
 * Created by Lee on 2017-07-27.
 */

public class Ground_Touch_Crocodile extends Ground_Default_Body {
    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     * @param hp
     * @param param_Width_Size
     * @param param_Height_Size
     */
    float w_Height;
    Ground_Touch_Crocodile(float window_Width, float window_Height, int width, int height, int hp, int param_Width_Size, int param_Height_Size) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size, 10000);

        ground_Class = 1;   //달팽이, 악어 = 1

        if(Math.random() < 0.5){
            direction = 1;  //오른쪽에서 왼쪽으로 진행
            ground_Point_X = window_Width;
        }else {
            direction = 2;  //왼쪽에서 오른쪽으로 진행
            ground_Point_X = 0 - width;
        }
        Random random = new Random();
        ground_Point_Y = random.nextFloat() * window_Height;
        w_Height = window_Height;
        speed = 2;
    }

    //악어 위치 초기화
    public void set_Position(){

        ground_Class = 1;   //달팽이, 악어 = 1

        if(Math.random() < 0.5){
            direction = 1;  //오른쪽에서 왼쪽으로 진행
            ground_Point_X = window_Width;
        }else {
            direction = 2;  //왼쪽에서 오른쪽으로 진행
            ground_Point_X = 0 - width;
        }
        Random random = new Random();
        ground_Point_Y = random.nextFloat() * (w_Height - 30);
    }

    int direction = 0;

    public int get_Direction(){
        return direction;
    }

    public void ground_Object_Move() {

        if(direction == 1) {
            ground_Point_X -= speed;
        }else {
            ground_Point_X += speed;
        }

        ground_Draw_Status++;

        if(ground_Draw_Status >= 32){
            ground_Draw_Status = 0;
        }

        if(Math.random() < 0.01) {
            //속도 변화 주기
            speed = 2 + (float)Math.random() * 3;
        }

    }


    public int get_Draw_Ground_Status()          // 그라운드 드로우할 그림 int형 반환
    {
        return ground_Draw_Status/8;              // 그라운드 이미지 2번씩 송출
    }


}
