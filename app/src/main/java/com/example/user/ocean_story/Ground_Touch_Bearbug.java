package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-21.
 * 달팽이
 */

public class Ground_Touch_Bearbug extends Ground_Default_Body {

    /**
     * Ground_Touch_Snail 변수
     */

   int class_Num = 0;

    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     */
    Ground_Touch_Bearbug(float window_Width, int width, int height, int hp, int param_Width_Size, int param_Height_Size, int t_Speed) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size, t_Speed);
        ground_Class = 1;   //달팽이 = 1
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
     * 곰벌레 움직이기
     * 동작 함수, 설정
     */
    @Override
    public void ground_Object_Move() {


        ground_Point_Y += speed;
        if(Math.random() < 0.01) {

            //속도 변화 주기
            speed = (2f + ((float)Math.random() * 3)/2) * temp_Speed;
        }

        ground_Draw_Status++;
        if(ground_Draw_Status > 10){
            ground_Draw_Status = 0;
        }
    }

    public int get_Draw_Ground_Status()          // 그라운드 드로우할 그림 int형 반환
    {
        return ground_Draw_Status/4;              // 그라운드 이미지 2번씩 송출
    }

    public void set_Knockback(int index) {
        ground_Point_Y -= index;
    }


    //********************************************************************************************//
}
