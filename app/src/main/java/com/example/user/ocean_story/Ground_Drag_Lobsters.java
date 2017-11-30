package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-22.
 */

public class Ground_Drag_Lobsters extends Ground_Default_Body{

    /**
     * Ground_Drag_Lobsters 변수
     */
    private int angle_X_Speed = 0;

    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     * @param hp
     */
    Ground_Drag_Lobsters(float window_Width, int width, int height, int hp, int param_Width_Size, int param_Height_Size, int t_Speed) {
        super(window_Width, width, height, hp, param_Width_Size, param_Height_Size, t_Speed);

        //꽃게 생성될때 방향
        if(Math.random() < 0.5){
            angle_X_Speed = 3;
        }else angle_X_Speed = -3;

        ground_Class = 2;           //꽃게 2번
    }

    /**
     * 반환
     */


    //********************************************************************************************//

    /**
     * 가제 움직이기
     * 동작 함수, 설정
     */
    @Override
    public void ground_Object_Move() {


        ground_Draw_Status++;

        if(touch_Or_Drag) {
            if (ground_Draw_Status > 10) {
                ground_Draw_Status = 0;
            }

            ground_Point_Y += speed;
            ground_Point_X += Math.random() * angle_X_Speed;

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
        }



        else if(!touch_Or_Drag){

            if (ground_Draw_Status > 22) {
                ground_Draw_Status = 12;
            }


            if(random.nextInt(100) < 1){
                touch_Or_Drag = true;
            }



        }

    }

    public int get_Draw_Ground_Status()          // 그라운드 드로우할 그림 int형 반환
    {
        return ground_Draw_Status/4;              // 그라운드 이미지 2번씩 송출
    }

    //********************************************************************************************//

    private boolean touch_Or_Drag = true;
    // t = 터치 공격가능, f = 드래그 공격가능
    public void set_Mode(){
        touch_Or_Drag = false; //
    }

    public boolean get_Mode(){
        return touch_Or_Drag;
    }



}
