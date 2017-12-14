package com.plank.user.ocean_story;

/**
 * Created by USER on 2017-01-21.
 * 드래그로 잡는 상어
 */

public class Fish_Monster extends Fish_Default_Body{

    /**
     * 기본 생성자
     * 윈도우 크기와 hp 를 받아와 물고기를 생성한다.
     *
     * @param window_Width
     * @param hp
     */
    int window_Height = 0;
    Fish_Monster(int window_Width, int hp, int param_Width_Size, int param_Height_Size, int t_Speed, int window_Height) {
        super(window_Width, hp, param_Width_Size, param_Height_Size, t_Speed);
        fish_Class = 3;
        this.window_Height = window_Height;

    }

    public int get_Draw_Fish_Status()
    {
        return fish_Draw_Status/8; //물고기 헤엄 이미지 2번씩 송출
    }

    private int character_Draw_Status = 0;
    boolean direct_Flag_X = true;
    boolean direct_Flag_Stop_X = true;

    boolean direct_Flag_Y = true;
    boolean direct_Flag_Stop_Y = true;
    public void fish_Object_Move(){
//         += fish_Speed;

        fish_Speed = 1;




        if(0 >= fish_Point_X){
            direct_Flag_X = true;
        }else if(fish_Point_X >= window_Width - 150){
            direct_Flag_X = false;
        }

        if(random.nextFloat() < 0.01){
            direct_Flag_Stop_X = false;
            if(random.nextFloat() < 0.1){
                direct_Flag_X = !direct_Flag_X;
            }
        }
        if(random.nextFloat() < 0.01){
            direct_Flag_Stop_X = true;
            if(random.nextFloat() < 0.1){
                direct_Flag_X = !direct_Flag_X;
            }
        }


        if(direct_Flag_Stop_X) {
            if (direct_Flag_X) {
                fish_Point_X += 3 + random.nextInt(5);
            } else {
                fish_Point_X -= 3 + random.nextInt(5);
            }
        }


        //높이
        if(0 >= fish_Point_Y){
            direct_Flag_Y = true;
        }else if(fish_Point_Y >= (window_Height - (window_Height/3))){
            direct_Flag_Y = false;
        }

        if(random.nextFloat() < 0.01){
            direct_Flag_Stop_Y = false;
            if(random.nextFloat() < 0.1){
                direct_Flag_Y = !direct_Flag_Y;
            }
        }
        if(random.nextFloat() < 0.01){
            direct_Flag_Stop_Y = true;
            if(random.nextFloat() < 0.1){
                direct_Flag_Y = !direct_Flag_Y;
            }
        }


        if(direct_Flag_Stop_Y) {
            if (direct_Flag_Y) {
                fish_Point_Y += 1 + random.nextInt(10);
            } else {
                fish_Point_Y -= 1 + random.nextInt(10);
            }
        }



//        //왼쪽 오른쪽을 넘어가면 방향 반대로
//        if(fish_Point_X <= 30 || fish_Point_X >= window_Width - 150){
//            angle_X_Speed = angle_X_Speed*-1;
//        }



        //헤엄치면서 물고기 그림 상태 변형
        fish_Draw_Status++;
        if(fish_Draw_Status > 22){
            fish_Draw_Status = 0;
        }



    }


    public void set_Knockback(){
        fish_Point_Y-= 2 + random.nextInt(3);
    }

    public boolean get_Site(){
        return direct_Flag_X;
    }


}
