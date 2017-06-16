package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-23.
 */

public class Background_Effect_Two extends Background_Effect_Control implements Runnable {
    /**
     * Background_Effect_two 기본 생성자
     *
     */
    Background_Effect_Two(int window_Width, int window_Height) {
        super(window_Width, window_Height);
        background_Point_X = 100 + (float)Math.random() * (window_Width-150);           //기포가 올라올자리
        background_Point_Y = 450 + (float)Math.random() * (window_Height-150);
    }

    /**
     * 이펙트 움직임
     * 생성과 동시에 움직임 시작
     */
    public void Background_Effect_Move_Pattern_1(){

        bg_Effect_Draw_Status++;
        if(bg_Effect_Draw_Status > 15){
            bg_Effect_Draw_Status = 0;
        }

    }

    @Override
    public void run() {
        while (true){
            try{
                Thread.sleep(15);
                Background_Effect_Move_Pattern_1();
            }catch (Exception e){

            }

        }
    }

    //********************************************************************************************//
}
