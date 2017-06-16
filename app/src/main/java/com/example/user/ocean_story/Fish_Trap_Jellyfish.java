package com.example.user.ocean_story;

/**
 * Created by USER on 2017-01-24.
 */

public class Fish_Trap_Jellyfish extends Fish_Default_Body {
    /**
     *  Fish_Trap_Jellyfish 변수
     */

    private boolean move_Point;         //true 이면 왼쪽에서 오른쪽으로
    private int angle_Temp;             //오른쪽에서 생성되는 경우 방향이 달라지기때문에

    /**
     * 기본 생성자
     * 윈도우 크기와 hp 를 받아와 물고기를 생성한다.
     *
     * @param window_Width
     * @param hp
     */
    Fish_Trap_Jellyfish(int window_Width, int window_Height, int hp) {
        super(window_Width, hp);
        angle = 30 + (int)Math.random() * 40;
        angle_Temp = angle;
        fish_Class = 10; //클래그

        if(Math.random() < 0.5) {       //왼쪽에서 오른쪽으로
            move_Point = true;
            fish_Point_X = 1;
        }else {
            move_Point = false;
            fish_Point_X = window_Width-1;
            angle += 90;
        }

        fish_Point_Y = 300 + (float)Math.random() * (window_Height - 500);

    }

    //********************************************************************************************//

    /**
     * 무빙 오버라이드
     */
    @Override
    public void fish_Object_Move() {

        /**
         * 왼쪽에서 오른쪽으로
         */
        if(move_Point) {
            fish_Point_X += (Math.cos(angle_Temp * Math.PI / 180)) * 5;            //삼각함수를 통해 이미지 방향으로 전진
            fish_Point_Y -= (Math.sin(angle_Temp * Math.PI / 180)) * 5;
        }else {
            fish_Point_X -= (Math.cos(angle_Temp * Math.PI / 180)) * 5;            //삼각함수를 통해 이미지 방향으로 전진
            fish_Point_Y -= (Math.sin(angle_Temp * Math.PI / 180)) * 5;
        }


        fish_Draw_Status++;
        if(fish_Draw_Status > 13){
            fish_Draw_Status = 0;
        }

    }

    //********************************************************************************************//

}
