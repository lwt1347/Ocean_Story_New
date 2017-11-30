package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-21.
 * 달팽이
 */

public class Ground_Touch_Snail extends Ground_Default_Body {

    /**
     * Ground_Touch_Snail 변수
     */
//기본인가, 중보스인가, 보스인가.
    int class_Num = 0;
    int pregnant_Time = 0;
    boolean pregnant_Flag = false;

    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     */
    Ground_Touch_Snail(float window_Width, int width, int height, int hp, int param_Width_Size, int param_Height_Size, int t_Speed) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size, t_Speed);
        ground_Class = 1;   //달팽이 = 1
    }
    Ground_Touch_Snail(float window_Width, int width, int height, int hp,  int param_Width_Size,int param_Height_Size, float X_Point, float y_Point, int t_Speed) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size, t_Speed);
        ground_Class = 1;   //달팽이 = 1
        ground_Point_X = X_Point;
        ground_Point_Y = y_Point;
    }

    //********************************************************************************************//

    //기본인가, 중보스인가, 보스인가.
    public void set_Class_Num(int param_Class_Num){
        //class_Num = 1 중간보스
        //class_Num = 2 보스
        class_Num = param_Class_Num;
    }
    //달팽이 보스가 새끼 치고 초기화
    public void set_Pragnant(){
        pregnant_Flag = false;
        pregnant_Time = 0;
    }
    //달팽이 위치
    public void set_Position(float X_Point, float y_Point){
        ground_Point_X = X_Point;
        ground_Point_Y = y_Point;
    }


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
     * 달팽이 움직이기
     * 동작 함수, 설정
     */
    @Override
    public void ground_Object_Move() {

        if(class_Num == 1){ // 중간 보스 일때
            pregnant_Time++;
            if(pregnant_Time > 150){
                if(random.nextInt(2) > 0) {
                    pregnant_Flag = true;
                }
            }
        }else if(class_Num == 2){   //보스 일떄
            pregnant_Time++;
            if(pregnant_Time > 300){
                if(random.nextInt(2) > 0) {
                    pregnant_Flag = true;
                }
            }
        }


        ground_Point_Y += speed;
        if(Math.random() < 0.01) {

            //속도 변화 주기
            speed = (2f + ((float)Math.random() * 3)/2) * temp_Speed;
        }

        ground_Draw_Status++;
        if(ground_Draw_Status > 7){
            ground_Draw_Status = 0;
        }
    }

    //********************************************************************************************//
}
