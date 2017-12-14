package com.plank.user.ocean_story;

/**
 * Created by USER on 2017-01-22.
 */

public class Ground_Drag_Wave extends Ground_Default_Body{

    /**
     * Ground_Drag_Wave 변수
     */


    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     * @param hp
     */
    Ground_Drag_Wave(float window_Width, int width, int height, int hp, int param_Width_Size, int param_Height_Size, float x_Point, float y_Point, int t_Speed) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size, t_Speed);

        ground_Point_X = x_Point;//생성될 위치
        ground_Point_Y = y_Point;

        ground_Class = 2;           //파도 2번
    }

    public void set_Position(){
        ground_Point_X = 0;
        ground_Point_Y = -1000;
    }

    public void set_Ground_Hp_Minus(int param){
        hp--;
    }


    /**
     * 반환
     */


    //********************************************************************************************//

    /**
     * 파도 움직이기
     * 동작 함수, 설정
     */
    @Override
    public void ground_Object_Move() {

        ground_Point_Y += 20;

    }

    //********************************************************************************************//

}
