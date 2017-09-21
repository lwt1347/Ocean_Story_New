package com.example.user.ocean_story;

import java.util.Random;

/**
 * Created by Lee on 2017-07-12.
 */

public class Ground_Drag_Clam  extends Ground_Default_Body{
    Random random = new Random();
    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     * @param hp
     */
    float x;
    float y;
    Ground_Drag_Clam(float window_Width, int width, int height, int hp,int param_Width_Size, int param_Height_Size, float yPoint, float xPoint) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size);

        x = xPoint;
        y = yPoint;
        ground_Point_X = 100 + random.nextFloat() * (xPoint- 150);
        ground_Point_Y = 100 + random.nextFloat() * (yPoint- 150);

        ground_Class = 2;           //꽃게 2번

    }

    public void set_Position(){

        ground_Point_X = 100 + random.nextFloat() * (x- 150);
        ground_Point_Y = 100 + random.nextFloat() * (y- 150);

    }

}
