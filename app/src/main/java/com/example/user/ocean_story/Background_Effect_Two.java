package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-23.
 */

public class Background_Effect_Two extends Background_Effect_Control{
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
    public void Background_Effect_Move_Pattern(){

        bg_Effect_Draw_Status++;
        if(bg_Effect_Draw_Status > 31){
            bg_Effect_Draw_Status = 0;
        }

    }

    //물방울 미역 천천히
    public int get_Draw_Background_Effect_Status()
    {
        return bg_Effect_Draw_Status/4; //물고기 헤엄 이미지 2번씩 송출
    }


    //********************************************************************************************//
}
