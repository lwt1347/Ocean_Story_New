package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-21.
 * 소라개
 */

public class Ground_Touch_Hermit extends Ground_Default_Body {

    /**
     * Ground_Touch_Hermit 변수
     */

    int class_Num = 0;
    boolean pregnant_Flag = false;

    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     */
    Ground_Touch_Hermit(float window_Width, int width, int height, int hp, int param_Width_Size, int param_Height_Size, int t_Speed) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size, t_Speed);
        ground_Class = 1;   //달팽이 = 1
    }


    //********************************************************************************************//



    /**
     * 반환
     */
    public int get_Class_Num(){
        return class_Num;
    }

    public boolean get_Pragnant_Flag(){
        return pregnant_Flag;
    }

    //********************************************************************************************//

    /**
     * 소라개 움직이기
     * 동작 함수, 설정
     */
    @Override
    public void ground_Object_Move() {



        if(!get_Immortal_Mode()) {
            ground_Point_Y += speed;
            ground_Draw_Status++;
            if(ground_Draw_Status > 3){
                ground_Draw_Status = 0;
            }
        }else {
            ground_Draw_Status = 5;
        }
        if(Math.random() < 0.01) {

            //속도 변화 주기
            speed = (2f + ((float)Math.random() * 3)/2) * temp_Speed;
            immortal = false;
        }




    }

    boolean immortal = false;
    public void set_Immortal_Mode(){
        immortal = true;
    }
    public boolean get_Immortal_Mode(){
        return immortal;
    }

    //********************************************************************************************//
}
