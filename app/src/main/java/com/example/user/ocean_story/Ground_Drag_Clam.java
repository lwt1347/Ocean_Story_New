package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-12.
 */

public class Ground_Drag_Clam  extends Ground_Default_Body{

    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     * @param hp
     */
    Ground_Drag_Clam(float window_Width, int width, int height, int hp, float yPoint) {
        super(window_Width, width, height, hp);
        ground_Point_Y = yPoint;

        ground_Class = 2;           //꽃게 2번

    }
}
