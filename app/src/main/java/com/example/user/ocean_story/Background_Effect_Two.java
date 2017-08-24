package com.example.user.ocean_story;

import android.util.Log;

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

    public int get_Draw_Background_Friend_Shark_Effect_Status(){
        return bg_Effect_Draw_Status/8; //물고기 헤엄 이미지 2번씩 송출
    }



    /**
     * 비트맵 메모리 관리를 위해서 반복되는 이미지는 반대로 카운트 한다 ex) 오징어 먹물, 말미잘 등.
     */
    boolean pattern_Flag = true;
    int pattern_control = 2;

    public int get_Draw_Background_Effect_Status_2()
    {

    //0~15 true 16~30 false
        if(bg_Effect_Draw_Status == 0){
            pattern_control = 2;
            pattern_Flag = true;
        }else if(bg_Effect_Draw_Status == 20){
            pattern_Flag = false;
        }else if(bg_Effect_Draw_Status == 24){
            pattern_control+=2;
        }else if(bg_Effect_Draw_Status == 28){
            pattern_control+=2;
        }

        if(pattern_Flag){
            return bg_Effect_Draw_Status/4; //물고기 헤엄 이미지 2번씩 송출
        }else {
            return bg_Effect_Draw_Status/4 - pattern_control; //물고기 헤엄 이미지 2번씩 송출
        }
    }

    int return_Status_3 = 0;
    int pattern_control_1 = 2;
    boolean pattern_Flag_1 = true;
    public int get_Draw_Background_Effect_Status_3()
    {

        //0~15 true 16~30 false
        if(bg_Effect_Draw_Status == 0){
            pattern_control_1 = 2;
            pattern_Flag_1 = true;
        }else if(bg_Effect_Draw_Status == 20){
            pattern_Flag_1 = false;
        }else if(bg_Effect_Draw_Status == 24){
            pattern_control_1+=2;
        }else if(bg_Effect_Draw_Status == 28){
            pattern_control_1+=2;
        }


        if(pattern_Flag_1){
            return_Status_3 = bg_Effect_Draw_Status/4;
            //0 1 2 3 4

            if(return_Status_3 < 2){
                return_Status_3 += 2;
            }else {
                return_Status_3 -= 2;
            }
            //0 = 2, 1 = 3, 2 = 4, 3 = 1
            return return_Status_3; //물고기 헤엄 이미지 2번씩 송출
        }else {
            return_Status_3 = bg_Effect_Draw_Status/4 - pattern_control_1;


            return return_Status_3; //물고기 헤엄 이미지 2번씩 송출
        }
    }

    //********************************************************************************************//
}
