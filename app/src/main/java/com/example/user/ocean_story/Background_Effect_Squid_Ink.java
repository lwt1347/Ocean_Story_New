package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */

public class Background_Effect_Squid_Ink extends Background_Effect_Control {
    /**
     * Background_Effect_Control 기본 생성자
     *
     * @param window_Width
     * @param window_Height
     */
    Background_Effect_Squid_Ink(int window_Width, int window_Height) {
        super(window_Width, window_Height);
    }

    //먹물 지속시간
    private int continue_Time = 0;

    public boolean up_Continue_Time(){
        continue_Time++;
        //파괴
        if(continue_Time > 300){
            //지속 끝 날때
            return false;
        }else{  //지속 상태
            return true;
        }
    }

    public void Background_Effect_Move_Pattern(){

        bg_Effect_Draw_Status++;
        if(bg_Effect_Draw_Status > 15){
            bg_Effect_Draw_Status = 0;
        }

    }




}
