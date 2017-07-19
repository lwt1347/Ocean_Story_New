package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-17.
 */

public class Land_Mark extends Ground_Default_Body {

    float point_X;
    float point_Y;
    int class_Num = 1;


    /**
     * 기본 생성자
     *
     * @param window_Width
     * @param width
     * @param height
     * @param hp
     */
    Land_Mark(float window_Width, int width, int height, int hp, float x, float y) {
        super(window_Width, width, height, hp);
        ground_Point_X = x;
        ground_Point_Y = y;

        ground_Class = 11;           //랜드마크 11번

    }

    public int get_Class_Num(){
        return class_Num-1;
    }

    public void set_Class_Num(int Params_Class_num){
        class_Num =  Params_Class_num;
        set_HP(class_Num);
    }



    private void set_HP(int param_Class_Num){
        if(param_Class_Num == 1){
            this.hp = 400;
        }else if(param_Class_Num == 2){
            this.hp = 400;
        }else if(param_Class_Num == 3){
            this.hp = 400;
        }else if(param_Class_Num == 4){
            this.hp = 400;
        }else if(param_Class_Num == 5){
            this.hp = 400;
        }else if(param_Class_Num == 6){
            this.hp = 400;
        }

    }

}
