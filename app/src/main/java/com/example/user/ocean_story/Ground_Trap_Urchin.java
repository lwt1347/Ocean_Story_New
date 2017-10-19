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
    //성게는 나와서 일정 시간이 흐르면 어택모드로 들어간다.
    boolean attack_Mode = false;
    float y;

    int live_Time = 0;  //성게가 존재할 시간.
    float angle = 0;    //성게 각도



    Ground_Trap_Urchin(float window_Width, int width, int height, float y_Point, int hp, int param_Width_Size, int param_Height_Size) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size, 10000);

        ground_Point_Y = 50 + (float)Math.random() * (y_Point-150);       //성게는 y 축도 랜덤으로 생성한다.


        ground_Class = 10;           //성게 10번
        angle = 1 + (float)Math.random() * 359;
        y = y_Point;
    }

    //성게 위치
    public void set_Position(){
        live_Time = 0;
        attack_Mode = false;
        angle = 1 + (float)Math.random() * 359;
        ground_Point_X = 30 + (float)Math.random() * (window_Width);         //생성될 위치
        ground_Point_Y = 50 + (float)Math.random() * (y-150);       //성게는 y 축도 랜덤으로 생성한다.
    }




    @Override
    public void ground_Object_Move() {
        //성게는 움직이지 않고 시간이 흐르면 삭제 되야한다.
        live_Time+=1 + random.nextInt(3);
        if(live_Time > 300){
            hp = 0;
        }

        //150이 흐른 후에 어택모드로 들어간다.
        if(live_Time == 150){
            attack_Mode = true;
        }

        ground_Draw_Status++;
        if(ground_Draw_Status > 9){
            ground_Draw_Status = 0;
        }
    }

    /**
     * 성게 변태 시간 반환
     */
    public int get_Live_Time(){
        return live_Time;
    }


    /**
     * 성게 터치시 어택모드인지 아닌지 판별
     */
    public boolean get_Urchin_Attack_Mode(){
        return attack_Mode;
    }



    public float get_Urchin_Angle()
    {
        return angle;
    }
}
