package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-23.
 */

public class Background_Effect_Control {
    /**
     * Background_Effect_Control 변수
     */
    protected int bg_Effect_Draw_Status = 0;        //이펙트 움직임
    protected int angle = 0;
    protected boolean angleTemp;                    //각도 변화 주기 조절

    protected float width;
    protected float height;

    protected float background_Point_X;                // 이펙트가 생성될 좌표
    protected float background_Point_Y;

    //********************************************************************************************//


    /**
     * Background_Effect_Control 기본 생성자
     */
    Background_Effect_Control(int window_Width, int window_Height){     //넓이 및 높이 알아온다.
        width = window_Width;
        height = window_Height;
        background_Point_X = window_Width;
        background_Point_Y = window_Height;
    }
    //********************************************************************************************//

    /**
     * 반환 얻어오기
     */
    public int get_Background_Angle(){
        return angle;
    }
    public float get_Background_Point_X(){
        return background_Point_X;
    }
    public float get_Background_Point_Y(){
        return background_Point_Y;
    }


    public int get_Draw_Background_Effect_Status()
    {
        return bg_Effect_Draw_Status/2; //물고기 헤엄 이미지 2번씩 송출
    }
    //********************************************************************************************//

}
