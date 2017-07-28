package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-01.
 */

//달팽이 생성
public class Main_Character_Shellfish_Tear2 extends Main_Character {

    /**
     * 기본 생성자
     *
     * @param x
     * @param y
     */
    Main_Character_Shellfish_Tear2(float x, float y, int window_Width_Size, int window_Height_Size) {
        super(x, y, window_Width_Size, window_Height_Size);
        this.set_Max_Hp(3);
        set_Damage(3);
    }

    /**
     * 적군을 멈추게 함
     */
    public void stop_Enemy(Fish_Default_Body fish_Touch_Default){
        fish_Touch_Default.set_Fish_Speed(0);
    }
    public void stop_Enemy(Ground_Default_Body ground_Default_Body){
        ground_Default_Body.set_Ground_Speed(0);
    }





}
