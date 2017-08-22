package com.example.user.ocean_story;

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
    Ground_Drag_Wave(float window_Width, int width, int height, int hp, int param_Width_Size, int param_Height_Size, float x_Point, float y_Point) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size);

        ground_Point_X = x_Point;//생성될 위치
        ground_Point_Y = y_Point;

        ground_Class = 2;           //파도 2번
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
