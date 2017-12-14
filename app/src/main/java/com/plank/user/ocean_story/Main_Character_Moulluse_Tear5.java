package com.plank.user.ocean_story;

/**
 * Created by Lee on 2017-07-02.
 */
//낙지꼬지
public class Main_Character_Moulluse_Tear5 extends Main_Character  {
    /**
     * 기본 생성자
     */
    Main_Character_Moulluse_Tear5(float x, float y, int window_Width_Size, int window_Height_Size,int w_Size, int h_Size) {
        super(x, y, window_Width_Size, window_Height_Size, w_Size, h_Size);
        pref_Set = 105000000;
        this.set_Max_Hp(6);
        set_Damage(6);
    }
}
class Skill_Fork extends Skill_Body{

    Skill_Fork(float x, float y) {
        super(x, y);
    }


    //폭탄 떨어지는
    int life_Cycle = 0;
    boolean life_Cycle_Flag = false;
    public void set_Skill_Move(int param_Add_Length){

        y_Point += param_Add_Length;

        life_Cycle++;

        if(life_Cycle > 3) {
            skill_Status = 1;
        }

        if(life_Cycle >= 6){
            life_Cycle_Flag = true;
        }
    }

    int g_Target = 0;
    int f_Target = 0;
    int Species = 0;
    public void set_Aim_Species(int param_Species){
        //1 = 물고기, 2 = 그라운드
        Species = param_Species;
    }
    public int get_Aim_Species(){
        //1 = 물고기, 2 = 그라운드
        return Species;
    }
    public void set_Aim_Ground(int target){
        g_Target = target;
    }
    public int get_Aim_Ground(){
        return g_Target;
    }
    public void set_Aim_Fish(int target){
        f_Target = target;
    }
    public int get_Aim_Fish(){
        return f_Target;
    }

    public boolean get_Live(){
        return life_Cycle_Flag;
    }
}