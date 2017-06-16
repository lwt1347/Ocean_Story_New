package com.example.user.ocean_story;

/**
 * Created by USER on 2017-02-27.
 */

public class Ground_Trap_Urchin extends Ground_Default_Body {
    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     * @param hp
     */
    int live_Time = 0;  //성게가 존재할 시간.
    float angle = 0;    //성게 각도

    Ground_Trap_Urchin(float window_Width, int width, int height, float y_Point, int hp) {
        super(window_Width, width, height, hp);

        ground_Point_Y = 50 + (float)Math.random() * (y_Point-100);       //성게는 y 축도 랜덤으로 생성한다.



        ground_Class = 10;           //성게 10번
        angle = 1 + (float)Math.random() * 359;
    }

    @Override
    public void ground_Object_Move() {
        //성게는 움직이지 않고 시간이 흐르면 삭제 되야한다.
        live_Time++;
        if(live_Time > 100){
            hp = 0;
        }

        ground_Draw_Status++;
        if(ground_Draw_Status > 9){
            ground_Draw_Status = 0;
        }
    }
    public float get_Urchin_Angle(){
        return angle;
    }
}
