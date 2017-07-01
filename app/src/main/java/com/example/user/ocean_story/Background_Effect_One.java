package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-23.
 */

public class Background_Effect_One extends Background_Effect_Control{

    /**
     * Background_Effect_One 기본 생성자
     */
    Background_Effect_One(int window_Width, int window_Height) {
        super(window_Width, window_Height);

    }

    /**
     * 이펙트 움직임
     * 생성과 동시에 움직임 시작
     */
    public void Background_Effect_Move_Pattern(){

        bg_Effect_Draw_Status++;
        if(bg_Effect_Draw_Status > 15){
            bg_Effect_Draw_Status = 0;
        }


        if(background_Point_X > 0 || background_Point_Y > 0) {
            background_Point_X-=8;
            //background_Point_X -= (Math.sin(angle * Math.PI / 180)) * 100;
            background_Point_Y -=8;
        }else {
            background_Point_X = width;
            background_Point_Y = (float)Math.random() * (height);


        }
        //bg_Effect_Draw_Status = 1;
    }



    //********************************************************************************************//
}
