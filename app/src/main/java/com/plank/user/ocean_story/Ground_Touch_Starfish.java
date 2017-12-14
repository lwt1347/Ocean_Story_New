package com.plank.user.ocean_story;

/**
 * Created by USER on 2017-01-21.
 * 불가사리
 */

public class Ground_Touch_Starfish extends Ground_Default_Body {

    /**
     * Ground_Touch_Snail 변수
     */
//기본인가, 중보스인가, 보스인가.
    int class_Num = 0;
    int pregnant_Time = 0;
    boolean pregnant_Flag = false;
    private int angle_X_Speed = 0;
    private int angle = 180;

    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     */
    Ground_Touch_Starfish(float window_Width, int width, int height, int hp, int param_Width_Size, int param_Height_Size, int t_Speed) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size, t_Speed);

        //불가사리 생성될때 방향
        if(Math.random() < 0.5){
            angle_X_Speed = 2;
        }else angle_X_Speed = -2;

        ground_Class = 1;   //불가사리 = 1
    }
    //불가사리 복제
    Ground_Touch_Starfish(float window_Width, int width, int height, int hp, int param_Width_Size, int param_Height_Size, float X_Point, float y_Point, int t_Speed) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size, t_Speed);

        //불가사리 생성될때 방향
        if(Math.random() < 0.5){
            angle_X_Speed = 2;
        }else angle_X_Speed = -2;

        ground_Class = 1;   //불가사리 = 1
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

    public int get_Angle(){
        return angle;
    }

    //********************************************************************************************//
    boolean split_Flag = false;
    int split_Time = 0;
    public boolean set_Split(){
        return split_Flag;
    }

    /**
     * 불가사리 움직이기
     * 동작 함수, 설정
     */
    @Override
    public void ground_Object_Move() {


        split_Time++;

        if(split_Time > 400){

            if(random.nextInt(100) > 20){
                split_Flag = true;
            }

            split_Time = 0;
        }else {
            split_Flag = false;
        }

        ground_Point_Y += speed;
        ground_Point_X += Math.random() * angle_X_Speed;

        if(random.nextInt(100) > 10){
            if(random.nextInt(100) > 50){
                angle += 3;
            }else {
                angle -= 3;
            }
        }



        if(Math.random() < 0.01) {

            //속도 변화 주기
            speed = (2f + ((float)Math.random() * 3)/2) * temp_Speed;
            if(Math.random() < 0.5){
                angle_X_Speed = angle_X_Speed*-1;
            }
        }

        //왼쪽 오른쪽을 넘어가면 방향 반대로
        if(ground_Point_X <= 30 || ground_Point_X >= window_Width - 150){
            angle_X_Speed = angle_X_Speed*-1;
        }

        ground_Draw_Status++;
        if(ground_Draw_Status > 28){
            ground_Draw_Status = 0;
        }

    }
    public int get_Draw_Ground_Status()          // 그라운드 드로우할 그림 int형 반환
    {
        return ground_Draw_Status/8;              // 그라운드 이미지 2번씩 송출
    }
    //********************************************************************************************//
}
