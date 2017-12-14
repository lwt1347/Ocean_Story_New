package com.plank.user.ocean_story;

/**
 * Created by Lee on 2017-07-01.
 */

public class Background_Effect_Shark extends Background_Effect_Control{

    /**
     * Background_Effect_Shark 기본 생성자
     */
    Background_Effect_Shark(int window_Width, int window_Height) {
        super(window_Width, window_Height);
        background_Point_X = - 500;
    }

    /**
     * 이펙트 움직임
     * 생성과 동시에 움직임 시작
     */
    public void Background_Effect_Move_Pattern(){

        bg_Effect_Draw_Status++;
        if(bg_Effect_Draw_Status > 9){
            bg_Effect_Draw_Status = 0;
        }

        if(background_Point_X < width) {
            background_Point_X+=8;
            //background_Point_X -= (Math.sin(angle * Math.PI / 180)) * 100;
//            background_Point_Y -=8;
        }else {
            background_Point_X = -700;
            background_Point_Y = (float)Math.random() * (height);


        }
        //bg_Effect_Draw_Status = 1;
    }



    //********************************************************************************************//
}
