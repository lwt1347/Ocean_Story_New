package com.plank.user.ocean_story;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by USER on 2017-01-21.
 * 게임의 모든 화면
 */

public class GameMain extends SurfaceView implements SurfaceHolder.Callback {



    /**
     * GameMain 변수 지정
     */

    private int drag_Action_Move = 0;           //드래그 이벤트 너무 빨리 일어나지 않도록 변수
    private int ground_Remove_Temp = -1;        //달팽이 삭제할 인덱스
    private int tempInt = 0;                    //템프 변수

    private int window_Width = 0;
    private int window_Height = 0;
    private int sound_Effect[] = new int[50];                        //효과음

    private int character_Damege = 1; //드래그 대미지는 랜덤으로 들어간다.
    private int character_Randmark_Damege_Temp; //드래그 대미지는 랜덤으로 들어간다.

    private int skill_Plus_D = 0;

    //두점 사이의 거리를 구하기위한 변수
    private int smallFishIndex      = 0;        //가장 가까운 물고기 인덱스
    private double pointXBig        = 0;
    private double pointXSmall      = 0;
    private double pointYBig        = 0;
    private double pointYSmall      = 0;
    private double smallFishTemp    = -30;     //가장 가까운 물고기 찾기 위한 변수
    private double smallMathResult  = 0;        //가장 가까운 물고기 찾기 위한 변수
    private boolean eraser_Fish = false;        //물고기를 지우기 허가가 떨어졌을때

    private int touch_Check = 0;                    //물고기는 터치가 되어야 히트를한다.

    private double Score = 0;                          //점수


    int warning_Time = 100;
    int warning_Time_Cycle = 0;



    boolean warning_Flag = false;
    boolean warning_Sound_Flag = false;
    boolean warning_Marlin_Flag = false;
    boolean warning_Ell_Flag = false;
    boolean warning_Wave_Flag = false;



    /**
     * 터치 이벤트시 발생하면 draw 에서 그리기 위한 flag 변수들
     */
    private boolean default_Fish_Hit_Flag = false;//기본물고기
    private boolean drag_Fish_Hit_Flag = false;//드래그물고기
    private boolean drag_Steelbream_Hit_Flag = false;//강철 참돔
    private boolean drag_Shark_Hit_Flag = false;//강철 참돔
    private boolean jellyfish_Fish_Hit_Flag = false;//해파리는 아직 이벤트 없음
    private boolean turtle_Fish_Hit_Flag = false; //방해 거북
    private boolean monster_Fish_Hit_Flag = false; //몬스터
    private boolean touch_Marlin_Hit_Flag = false; //청새치
    private boolean touch_Squid_Hit_Flag = false; //오징어
    private boolean touch_Ell_Hit_Flag = false; //전기 뱀장어



    private boolean snail_Ground_Hit_Flag = false;//달팽이
    private boolean bearbug_Ground_Hit_Flag = false;//곰버레
    private boolean stingray_Ground_Hit_Flag = false;//가오리
    private boolean lobsters_Ground_Hit_Flag = false; //가제
    private boolean starfish_Ground_Hit_Flag = false;//불가사리

    private boolean crap_Ground_Hit_Flag = false;//꽃게
    private boolean clam_Ground_Hit_Flag = false; //조개
    private boolean seaurchin_Ground_Hit_Flag = false;//성게
    private boolean land_Mark_Hit_Flag = false;//랜드마크
    private boolean hermit_Ground_Hit_Flag = false;//소라게
    private boolean wave_Ground_Hit_Flag = false; //드래그 파도


    //진화 사운드 한번만
    private boolean revolution_Flag = false;
    private boolean revolution_Flag_Confirm = false;

    //추출찰 띄우기
    private boolean Extraction = false;


    //사운드
    //기본 터치 팝 사운드
    float pop_Touch = 0.4f;
    //드래그 팝 사운드
    float pop_Drag = 0.2f;
    //외적 사운드
    float pop_Effect = 0.5f;


    private Game_Thread game_thread;                    //스레드 돌릴 클래스 선언
    //    private Game_Element_Thread game_element_thread;    //게임 요소 생성[물고기, 함정 등]쓰레드 생성
    private Main_Character main_Character;              //메인 캐릭터 생성
    private Effect_Bird effect_Bird;                    //딱따구리 생성
    private boolean mRun = true;                        //run 함수 제어 //퍼즈 걸도록 mRun 컨트롤
    private boolean distroy_Run = true;                        //run 함수 제어 //퍼즈 걸도록 mRun 컨트롤

    private SurfaceHolder mSurfaceHolder;               //쓰레드 외부에서 SurfaceHolder를 얻기 위한 선언
    private BitmapDrawable image = null;                //메모리 절약 기법
    private Bitmap backGroundImg = null;                //배경이미지
    private Bitmap backGroundImg_black = null;                //배경이미지

    private Bitmap shadow_img[] = new Bitmap[8];//그림자 이미지

    private Bitmap nine_Patch_Hp = null;   //hp
    private Bitmap warning_img = null;

    private NinePatch ninePatch;     //나인패치 적용방법 변수
    /**
     * 기본 물고기 이미지
     */

    private Bitmap fish_Touch_Default_Hp1_img[] = new Bitmap[4];    //Hp1 물고기
    private Bitmap fish_Touch_Default_Hp2_img[] = new Bitmap[4];    //Hp2 물고기
    private Bitmap fish_Touch_Default_Hp3_img[] = new Bitmap[4];    //Hp3 물고기
    private Bitmap fish_Touch_Default_Hp4_img[] = new Bitmap[4];    //Hp4 물고기
    private Bitmap fish_Touch_Default_Hp5_img[] = new Bitmap[4];    //Hp5 물고기

    private Bitmap fish_Touch_Default_Middle_Hp1_img[] = new Bitmap[4];    //Hp1 물고기    중간 보스
    private Bitmap fish_Touch_Default_Middle_Hp2_img[] = new Bitmap[4];    //Hp1 물고기
    private Bitmap fish_Touch_Default_Middle_Hp3_img[] = new Bitmap[4];    //Hp1 물고기
    private Bitmap fish_Touch_Default_Middle_Hp4_img[] = new Bitmap[4];    //Hp1 물고기
    private Bitmap fish_Touch_Default_Middle_Hp5_img[] = new Bitmap[4];    //Hp1 물고기

    private Bitmap fish_Touch_Default_Boss_Hp1_img[] = new Bitmap[4];    //Hp1 물고기
    private Bitmap fish_Touch_Default_Boss_Hp2_img[] = new Bitmap[4];    //Hp1 물고기
    private Bitmap fish_Touch_Default_Boss_Hp3_img[] = new Bitmap[4];    //Hp1 물고기
    private Bitmap fish_Touch_Default_Boss_Hp4_img[] = new Bitmap[4];    //Hp1 물고기
    private Bitmap fish_Touch_Default_Boss_Hp5_img[] = new Bitmap[4];    //Hp1 물고기

    private Bitmap ground_Snail_Middle_Hp1_img[] = new Bitmap[4];    //달팽이 중간 보스
    private Bitmap ground_snail_Middle_Hp2_img[] = new Bitmap[4];    //달팽이 중간 보스
    private Bitmap ground_snail_Middle_Hp3_img[] = new Bitmap[4];    //달팽이 중간 보스
    private Bitmap ground_snail_Middle_Hp4_img[] = new Bitmap[4];    //달팽이 중간 보스
    private Bitmap ground_snail_Middle_Hp5_img[] = new Bitmap[4];    //달팽이 중간 보스


    private Bitmap ground_snail_Boss_Hp1_img[] = new Bitmap[4];    //달팽이 중간 보스
    private Bitmap ground_snail_Boss_Hp2_img[] = new Bitmap[4];    //달팽이 중간 보스
    private Bitmap ground_snail_Boss_Hp3_img[] = new Bitmap[4];    //달팽이 중간 보스
    private Bitmap ground_snail_Boss_Hp4_img[] = new Bitmap[4];    //달팽이 중간 보스
    private Bitmap ground_snail_Boss_Hp5_img[] = new Bitmap[4];    //달팽이 중간 보스

    //진화 버튼 배경
    private Bitmap revolution_Button_Background_Effect;

    /**
     * 오징어 이미지
     */
    private Bitmap fish_Touch_Squid_img[] = new Bitmap[8];

    /**
     * 전기 뱀장어 이미지
     */
    private Bitmap fish_Touch_Ell_img[] = new Bitmap[8];
    private Bitmap fish_Touch_Ell_Attack_img[] = new Bitmap[8];

    /**
     * 청새치 이미지
     */
    private Bitmap fish_Touch_Marlin_img[] = new Bitmap[4];


    /**
     *드래그로 죽는 물고기 이미지
     */
    private Bitmap fish_Drag_Default_img[] = new Bitmap[4];         //드래그로 죽는 물고기 이미지

    /**
     * 드래그로 확률적으로 갑옷을 깰 수 있는 물고기 [참돔]
     */
    private Bitmap fish_Drag_Steelbream_img[] = new Bitmap[4];

    /**
     * 드래그로 잡는 [상어]
     */
    private Bitmap fish_Drag_Shark_img[] = new Bitmap[4];



    /**
     * 해파리 이미지
     */
    private Bitmap fish_Trap_Jelly_img[] = new Bitmap[4];           //해파리 이미지

    /**
     * 방해 거북 이미지
     */
    private Bitmap fish_Turtle_img[] = new Bitmap[3];

    /**
     * 몬스터 이미지
     */
    private Bitmap fish_Monster_Left_img[] = new Bitmap[3];
    private Bitmap fish_Monster_Right_img[] = new Bitmap[3];

    /**
     * 성게 이미지
     */
    private Bitmap ground_Trap_Urchin_img[] = new Bitmap[5];
    /**
     * 성게 휴식기 이미지
     */
    private Bitmap ground_Trap_Urchin_Rest_Mode_img[] = new Bitmap[5];

    /**
     * 달팽이 이미지
     */
    private Bitmap ground_Touch_Snail_Hp1_img[] = new Bitmap[4];    //Hp1 달팽이
    private Bitmap ground_Touch_Snail_Hp2_img[] = new Bitmap[4];    //Hp2 달팽이
    private Bitmap ground_Touch_Snail_Hp3_img[] = new Bitmap[4];    //Hp3 달팽이
    private Bitmap ground_Touch_Snail_Hp4_img[] = new Bitmap[4];    //Hp4 달팽이
    private Bitmap ground_Touch_Snail_Hp5_img[] = new Bitmap[4];    //Hp5 달팽이

    private Bitmap ground_Touch_Bearbug_img[] = new Bitmap[3];


    /**
     * 불가사리 이미지
     */
    private Bitmap ground_Touch_Starfish_img[] = new Bitmap[4];

    /**
     * 가오리 이미지
     */
    private Bitmap ground_Touch_Stingray_img[] = new Bitmap[5];

    /**
     * 소라게 이미지
     */
    private Bitmap ground_Touch_Hermit_Hp1_img[] = new Bitmap[3];    //Hp1 달팽이
    private Bitmap ground_Touch_Hermit_Hp2_img[] = new Bitmap[3];    //Hp1 달팽이
    private Bitmap ground_Touch_Hermit_Hp3_img[] = new Bitmap[3];    //Hp1 달팽이
    private Bitmap ground_Touch_Hermit_Hp4_img[] = new Bitmap[3];    //Hp1 달팽이
    private Bitmap ground_Touch_Hermit_Hp5_img[] = new Bitmap[3];    //Hp1 달팽이

    /**
     * 꽃게 이미지
     */
    private Bitmap ground_Drag_Crab_img[] = new Bitmap[4];          //꽃게 이미지

    /**
     * 꽃게 이미지
     */
    private Bitmap ground_Drag_Lobsters_img[] = new Bitmap[6];          //가제 이미지

    /**
     * 드래그 파도 이미지
     */
    private Bitmap ground_Drag_Wave_img[] = new Bitmap[2];          //꽃게 이미지


    /**
     * 악어 이미지
     */
    private Bitmap ground_Touch_Crocodile_img[] = new Bitmap[6];          //꽃게 이미지


    /**
     * 조개 이미지
     */
    private Bitmap ground_Drag_Clam_img[] = new Bitmap[4];


    /**
     * 랜드마크 이미지
     */
    private Bitmap land_Mark1_img[] = new Bitmap[4];
    private Bitmap land_Mark2_img[] = new Bitmap[4];
    private Bitmap land_Mark3_img[] = new Bitmap[4];
    private Bitmap land_Mark4_img[] = new Bitmap[4];
    private Bitmap land_Mark5_img[] = new Bitmap[4];
    private Bitmap land_Mark6_img[] = new Bitmap[4];

    /**
     * 랜드마크 드래그 이미지
     */
    private Bitmap land_Mark_Drag_img[] = new Bitmap[3];

    /**
     * 랜드마크 딱따구리 이미지
     */
    private Bitmap land_Mark_Bird_img[] = new Bitmap[2];




    //메인 캐릭터 이미지 [문어]

    //메인 캐릭터 이미지 [오리진: 곰팡이]
    private Bitmap main_Character_Img[] = new Bitmap[6];        //메인 캐릭터
    private Bitmap main_Character_Img_1[] = new Bitmap[6];        //메인 캐릭터

    private Bitmap main_Character_Blood_Img[] = new Bitmap[6];        //메인 캐릭터

    //게임오버 이미지
    private Bitmap gameover_Img = null;
    //무한모드 이미지
    private Bitmap infinitymode_Img = null;

    //회전 물고기 비트맵 템프 변수
    private Bitmap temp_Fish = null;

    //그림자
    private Bitmap temp_Shadow_img = null;


    //바닥 생명체 비트맵 탬프 변수
    private Bitmap temp_Ground = null;

    /**
     * 설명창
     */
    private Bitmap explain_Window_ima = null;

    /**
     * 등장 창
     */
    private Bitmap explain_Window_Revoluition = null;
    /**
     * 추출창
     */
    private Bitmap explain_Window_Extraction = null;


    //캐릭터 진화버튼 설명창
    private Bitmap explain_Window_MainCharacker = null;




    /**
     * 슬로우 효솨
     */
    private Bitmap effect_Slow_img[] = new Bitmap[5];
    /**
     * 독 효과
     */
    private Bitmap effect_Poison_img[] = new Bitmap[8];


    /**
     * 기본 터치 물고기 이펙트
     */
    private Bitmap effect_Orange_img[] = new Bitmap[5];     //오렌지
    private Bitmap effect_Blue_img[] = new Bitmap[5];       //블루
    private Bitmap effect_Yellow_img[] = new Bitmap[5];     //옐로우
    private Bitmap effect_Green_img[] = new Bitmap[5];      //그린
    private Bitmap effect_Black_img[] = new Bitmap[5];      //그린

    /**
     * 나인패치 HP바 표현
     */
    private Bitmap nine_Patch_Image[] = new Bitmap[15];

    /**
     * 드래그 물고기 이펙트 팝
     *  달팽이 이펙트
     *  꽃게 이팩트
     */
    private Bitmap effect_Pop_damage1_Image[] = new Bitmap[5];
    private Bitmap effect_Pop_damage2_Image[] = new Bitmap[5];
    private Bitmap effect_Pop_damage3_Image[] = new Bitmap[5];
    private Bitmap effect_Pop_damage4_Image[] = new Bitmap[5];
    private Bitmap effect_Pop_damage5_Image[] = new Bitmap[5];
    private Bitmap effect_Pop_damage6_Image[] = new Bitmap[5];
    private Bitmap effect_Pop_damage7_Image[] = new Bitmap[5];
    private Bitmap effect_Pop_damage8_Image[] = new Bitmap[5];
    private Bitmap effect_Pop_damage9_Image[] = new Bitmap[5];
    private Bitmap effect_Pop_damage10_Image[] = new Bitmap[5];
    private Bitmap pop_Temp_img;
    private Bitmap gold_img;
    private Bitmap ruby_img;

    /**
     * 랜드마크 팝 이미지
     */
    private Bitmap effect_Land_Mark_Pop1_img[] = new Bitmap[4];
    private Bitmap effect_Land_Mark_Pop2_img[] = new Bitmap[4];
    private Bitmap effect_Land_Mark_Pop3_img[] = new Bitmap[4];


    private Bitmap effect_Pop2_img[] = new Bitmap[5];
    private Bitmap effect_Pop3_img[] = new Bitmap[5];
    private Bitmap effect_Pop4_img[] = new Bitmap[5];
    private Bitmap effect_Pop5_img[] = new Bitmap[5];
    private Bitmap effect_Pop6_img[] = new Bitmap[5];


    /**
     * 스테이지 이미지
     */
    private Bitmap effect_Stage_Day_img;

    /**
     * 파도 팦 이미지
     */
    private Bitmap effect_Wave_Pop_img;
    /**
     * 참돔 팝 이팩트
     */
    private Bitmap effect_Pop_Steel_img;
    /**
     * 방해 거북 이펙트
     */
    private Bitmap effect_Pop_Turtle;

    /**
     * 스킬 이미지
     */
    private Bitmap skill_Crab_img[] = new Bitmap[4];
    private Bitmap skill_Soycrab_img[] = new Bitmap[4];
    private Bitmap skill_Laser_img;
    private Bitmap skill_Thorn_img[] = new Bitmap[4];
    private Bitmap skill_Poison1_img[] = new Bitmap[4];
    private Bitmap skill_Poison2_img[] = new Bitmap[4];
    private Bitmap skill_Poison3_img[] = new Bitmap[4];
    private Bitmap skill_earthquake_img[] = new Bitmap[4];
    private Bitmap skill_Teeth_mine_img[] = new Bitmap[3];
    private Bitmap skill_Teeth_mine2_img[] = new Bitmap[4];

    private Bitmap skill_Sea_Snake_img[] = new Bitmap[3];
    private Bitmap skill_Slow_Cloud_img;
    private Bitmap skill_Boom_Poison_img[] = new Bitmap[4];
    private Bitmap skill_Wave_img[] = new Bitmap[3];
    private Bitmap skill_wall_img[] = new Bitmap[4];
    private Bitmap skill_Thorn2_img[] = new Bitmap[3];

    private Bitmap skill_lightnign_img[] = new Bitmap[4];
    private Bitmap skill_lightnign1_img[] = new Bitmap[4];
    private Bitmap skill_stomp_img[] = new Bitmap[4];
    private Bitmap skill_fry_img[] = new Bitmap[5];

    private Bitmap skill_Butter_img[] = new Bitmap[3];
    private Bitmap skill_Fork_img[] = new Bitmap[3];




    /**
     * 백그라운드 이펙트 동적인 화면 구성하기 위한 이미지
     */
    private Bitmap effect_Background_One_1_img[] = new Bitmap[8];
    private Bitmap effect_Background_Two_1_img[] = new Bitmap[8];
    //미역
    private Bitmap effect_Background_Seaweed_img[] = new Bitmap[8];
    //말미잘
    private Bitmap effect_background_Seaanemone_img[] = new Bitmap[5];
    //돌
    private Bitmap effect_background_Rock[] = new Bitmap[8];
    //상어
    private Bitmap effect_Background_Shark_img[] = new Bitmap[5];
    //먹물 [오징어 사냥 시 발생]
    private Bitmap effect_Background_Squid_Ink_img[] = new Bitmap[5];
    //상어 친구 부르기
    private Bitmap effect_Background_Friend_Shark_img[] = new Bitmap[4];


    //청새치
    Fish_Touch_Marlin fish_Touch_Marlin;

    //퍼지 이미지 변경
    //boolean pause_Push = false;

    //물기고 생성
    private ArrayList<Fish_Default_Body> fish_List = new ArrayList<Fish_Default_Body>();            //물고기를 넣을 어레이 리스트
    private ArrayList<Ground_Default_Body> ground_List = new ArrayList<Ground_Default_Body>();      //물고기를 넣을 어레이 리스트




    private Draw_Image draw = new Draw_Image();
    private Paint paint = new Paint();                 //점수
    private Paint paint_Temp = new Paint();            //점수 테두리

    private Paint paint_Best = new Paint();                 //점수
    private Paint paint_Temp_Best = new Paint();            //점수 테두리

    private double best_Point = 0;


    private Canvas canvas;

    private Fish_Touch_Default fish_Touch_Default;      //기본 물고기 생성
    private Fish_Drag_Default fish_Drag_Default;        //드래그 물고기 생성
    private Fish_Drag_Steelbream fish_Drag_Steelbream;  //참돔 생성
    private Fish_Drag_Shark fish_Drag_Shark;
    private Fish_Trap_Jellyfish fish_Trap_Jellyfish;    //해파리 생성
    private Fish_Trap_Turtle fish_Trap_Turtle;          //방해 거북 생성
    private Fish_Monster fish_Trap_Monster;          //몬스터 생성
    private Fish_Touch_Squid fish_Touch_Squid;          //오징어 생성
    private Background_Effect_Squid_Ink fish_Touch_Squid_Ink;   //오징어 잡았을때 먹물 발사.
    private Fish_Touch_Ell fish_Touch_Ell;              //전기뱀장어 생성

    private Ground_Touch_Snail ground_Touch_Snail;      //달팽이 생성
    private Ground_Touch_Bearbug ground_Touch_Bearbug;  //곰벌레 생성
    private Ground_Drag_Crab ground_Drag_Crab;          //꽃게 생성
    private Ground_Drag_Lobsters ground_drag_lobsters;          //꽃게 생성
    private Ground_Drag_Clam ground_Drag_Clam;           //조개
    private Ground_Trap_Urchin ground_trap_urchin;      //성게 생성
    private Ground_Touch_Crocodile ground_Touch_Crocodile; //악어 생성
    private Ground_Touch_Hermit ground_Touch_Hermit;    //소라게 생성
    private Ground_Touch_Starfish ground_Touch_Starfish; //불가사리 생성
    private Ground_Drag_Wave ground_drag_wave;          //드래그 파도 생성
    private Ground_Touch_Stingray ground_Touch_Stingray;//가오리 생성



    private Background_Effect_One background_Effect_One;    //배경화면 1번 움직임
    private Background_Effect_Two background_Effect_Two;    //배경화면 1번 움직임

    private Background_Effect_Shark background_Effect_Shark;    //배경화면 상어 움직임


    private ArrayList<Point> background_Effect_Location = new ArrayList<Point>(); //배경 이펙트 위치 선정


    private Bitmap effect_Temp;                         //어떤 이펙트를 넣을것인가 랜덤 변수
    private Random random = new Random();

    public static Context _context;                           //화면 얻어오기
    private Display display;                            //디스플레이 넓이 구하기

    private String tempStr = "";

    private SoundPool soundPool = new SoundPool(20, AudioManager.STREAM_ALARM, 0); //사운드 앞에 1은 하나만 가져다 놓겠다는 뜻. 나중에 추가 요망


    private MediaPlayer background_Sound;

    boolean revolution_Draw_Flag_Confirm = false; //진화 버튼이 활성화 되면 재생성을 멈추고 림을 그려준다.

    boolean ground_Hit_Drag = false;    //바닥 생명체, 터치 인가, 드래그 인가 구별
    /**
     * 게임 동작 함수
     */

    //기본 물고기
    private boolean first_Default_Fish = true;
    //드래그 물고기
    private boolean first_Drag_Fish = true;
    //오징어
    private boolean first_Squid = true;
    //전기 뱀장어
    private boolean first_Ell = true;
    //성게
    private boolean first_Urchin = true;
    //달팽이
    private boolean first_Snail = true;
    //꽃게
    private boolean first_Crab = true;

    private int skill_Add = 0;

    private boolean enumy_Appear = true;


    //몬스터, 캐릭터 카드
    int me1  = 0;
    int me2  = 0;
    int me3  = 0;
    int me4  = 0;
    int me5  = 0;
    int me6  = 0;
    int me7  = 0;
    int me8  = 0;
    int me9  = 0;
    int me10  = 0;
    int me11 = 0;
    int me12  = 0;
    int me13  = 0;
    int me14  = 0;
    int me15  = 0;
    int me16  = 0;
    int me17  = 0;
    int me18  = 0;
    int me19  = 0;
    int me20  = 0;

    int ce1 = 0;
    int ce2 = 0;
    int ce3 = 0;
    int ce4 = 0;
    int ce5 = 0;
    int ce6 = 0;
    int ce7 = 0;
    int ce8 = 0;
    int ce9 = 0;
    int ce10 = 0;
    int ce11 = 0;
    int ce12 = 0;
    int ce13 = 0;
    int ce14 = 0;
    int ce15 = 0;
    int ce16 = 0;
    int ce17 = 0;
    int ce18 = 0;
    int ce19 = 0;
    int ce20 = 0;
    int ce21 = 0;
    int ce22 = 0;
    int ce23 = 0;
    int ce24 = 0;
    int ce25 = 0;
    int ce26 = 0;
    int ce27 = 0;
    int ce28 = 0;
    int ce29 = 0;
    int ce30 = 0;
    int ce31 = 0;
    int ce32 = 0;
    int ce33 = 0;
    int ce34 = 0;
    int ce35 = 0;


    //화면 껏다 켯을때 컨트롤
    KeyguardManager km;
    boolean isScreen;
    GameActivity gameActivity;


    //Thread default_Fish_Effect;   //이펙트 쓰레드, 부하 때문에 삭제
    int rand_Effect;    //이펙트 쓰레드 대체

    //버튼 이미지
    Bitmap upimage;
    Bitmap downimage;

    Timer mTimer; //게임요소 추가 타이머



    //********************************************************************************************//

    //집게발
    Skill_Crab_Claws skill_Crab_Claws;
    ArrayList<Skill_Crab_Claws> skill_Crab_Claws_List = new ArrayList<Skill_Crab_Claws>();

    //간장 게장 집게발
    Skill_Soycrab_Claws skill_Soycrab_Claws;
    ArrayList<Skill_Soycrab_Claws> skill_Soycrab_Claws_List = new ArrayList<Skill_Soycrab_Claws>();

    //스톰프
    Skill_Stomp skill_Stomp;
    ArrayList<Skill_Stomp> skill_Stomp_List = new ArrayList<Skill_Stomp>();

    //가시 소환
    Skill_Thorn skill_Thorn;
    ArrayList<Skill_Thorn> skill_Thorn_List = new ArrayList<Skill_Thorn>();

    //레이저
    Skill_Laser skill_Laser;
    ArrayList<Skill_Laser> skill_Laser_List = new ArrayList<Skill_Laser>();

    //독구름 소환
    Skill_Poison_Cloud skill_poison_cloud;
    ArrayList<Skill_Poison_Cloud> skill_poison_cloud_List = new ArrayList<Skill_Poison_Cloud>();

    //지진 소환
    Skill_Earthquake skill_Earthquake;
    ArrayList<Skill_Earthquake> skill_Earthquake_List = new ArrayList<Skill_Earthquake>();

    //이빨 지뢰 소환
    Skill_Teeth_Mine skill_Teeth_Mine;
    ArrayList<Skill_Teeth_Mine> skill_Teeth_Mine_List = new ArrayList<Skill_Teeth_Mine>();

    //이빨 지회2 소환
    Skill_Teeth_Mine2 skill_Teeth_Mine2;
    ArrayList<Skill_Teeth_Mine2> skill_Teeth_Mine2_List = new ArrayList<Skill_Teeth_Mine2>();

    //튀김 지뢰 소환
    Skill_Fry skill_Fry;
    ArrayList<Skill_Fry> skill_Fry_List = new ArrayList<Skill_Fry>();

    //버터 소환
    Skill_Butter skill_Butter;
    ArrayList<Skill_Butter> skill_Butter_List = new ArrayList<Skill_Butter>();

    //느려지는 구름
    Skill_Slow_Cloud skill_Slow_Cloud;
    ArrayList<Skill_Slow_Cloud> skill_Slow_Cloud_List = new ArrayList<Skill_Slow_Cloud>();

    //포크 소환
    Skill_Fork skill_Fork;
    ArrayList<Skill_Fork> skill_Fork_List = new ArrayList<Skill_Fork>();

    //독 폭탄
    Skill_Boom_Poison skill_Boom_Poison;
    ArrayList<Skill_Boom_Poison> skill_Boom_Poison_List = new ArrayList<Skill_Boom_Poison>();

    //바다뱀 소환
    Skill_Sea_Snake skill_Sea_Snake;
    ArrayList<Skill_Sea_Snake> skill_Sea_Snake_List = new ArrayList<Skill_Sea_Snake>();

    //파도소환
    Skill_Wave skill_Wave;
    ArrayList<Skill_Wave> skill_Wave_List = new ArrayList<Skill_Wave>();

    //벽소환
    Skill_Wall skill_Wall;
    ArrayList<Skill_Wall> skill_Wall_List = new ArrayList<Skill_Wall>();

    //가시2 소환
    Skill_Thorn2 skill_Thorn2;
    ArrayList<Skill_Thorn2> skill_Thorn2_List = new ArrayList<Skill_Thorn2>();

    //번개 소환
    Skill_Lightning skill_Lightning;
    ArrayList<Skill_Lightning>skill_Lightning_List = new ArrayList<Skill_Lightning>();

    //번개 2소환
    Skill_Lightning2 skill_Lightning2;
    ArrayList<Skill_Lightning2>skill_Lightning2_List = new ArrayList<Skill_Lightning2>();
    Matrix sideInversion;



    //광고 변수

    /**
     * 기본 생성자
     */

//스킬 판정
    float s_x;
    float s_x_w;
    float s_y;
    float s_y_h;
    float f_x;
    float f_x_w;
    float f_y;
    float f_y_h;
    public GameMain(Context context, AttributeSet attrs){

        super(context,attrs);   //커스텀 뷰 사용 -> attrs.xml 에 등록 해야함

        _context = context;

        //임시저장

        pref = _context.getSharedPreferences("pref", Activity.MODE_APPEND);
        editor = pref.edit();

        //화면 껏다 켯을때 컨트롤
        km = (KeyguardManager) _context.getSystemService(Context.KEYGUARD_SERVICE);
        gameActivity = (GameActivity) _context;






        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
//        game_thread = new Game_Thread(/*mSurfaceHolder*/);      //그림이 그려지고, 게임 동작하는곳

        //게임 요소 추가 할 타이머 [물고기, 함정 등] 1초후에 실행해서 5초마다 반복


        sideInversion = new Matrix();
        sideInversion.setScale(-1, 1); // 좌우반전


        ((Activity)_context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        /**
         *  배경음악 사운드풀로 안되서
         */
        ((GameActivity) _context).setVolumeControlStream(AudioManager.STREAM_MUSIC);

        background_Sound = MediaPlayer.create(_context, R.raw.background_music_1);
        background_Sound.setLooping(true);

//        background_Sound.setVolume(0.5f,0.5f);


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                background_Sound.start();
                //여기에 딜레이 후 시작할 작업들을 입력
            }
        }, 3000);// 0.5초 정도 딜레이를 준 후 시작



//        soundPool.play(R.raw.background_music_1, 1.0f, 1.0f, 0, -1, 1.0F);   //달팽이 기본 팝 사운드




    }

    //pop_Drag2 pop_Touch4 pop_Effect5
    public void set_Volume(int vol){
        if(vol == 10){
            pop_Touch = 1.0f;
            pop_Drag = 0.4f;
            pop_Effect = 1.0f;

        }else if(vol == 9){
            pop_Touch = 0.9f;
            pop_Drag = 0.36f;
            pop_Effect = 0.9f;
        }else if(vol == 8){
            pop_Touch = 0.8f;
            pop_Drag = 0.32f;
            pop_Effect = 0.8f;
        }else if(vol == 7){
            pop_Touch = 0.7f;
            pop_Drag = 0.28f;
            pop_Effect = 0.7f;
        }else if(vol == 6){
            pop_Touch = 0.6f;
            pop_Drag = 0.24f;
            pop_Effect = 0.6f;
        }else if(vol == 5){
            pop_Touch = 0.5f;
            pop_Drag = 0.20f;
            pop_Effect = 0.5f;
        }else if(vol == 4){
            pop_Touch = 0.4f;
            pop_Drag = 0.16f;
            pop_Effect = 0.4f;
        }else if(vol == 3){
            pop_Touch = 0.3f;
            pop_Drag = 0.12f;
            pop_Effect = 0.3f;
        }else if(vol == 2){
            pop_Touch = 0.2f;
            pop_Drag = 0.08f;
            pop_Effect = 0.2f;
        }else if(vol == 1){
            pop_Touch = 0.1f;
            pop_Drag = 0.04f;
            pop_Effect = 0.1f;
        }else if(vol == 0){
            pop_Touch = 0.0f;
            pop_Drag = 0.0f;
            pop_Effect = 0.0f;
        }

        pop_Touch = pop_Touch/7;
        pop_Drag = pop_Drag/5;
        pop_Effect= pop_Effect/5;

    }

    public void set_Volume_Up(int vol){

        pop_Touch+=0.1;
        pop_Drag+=0.04;     //젤 작음
        pop_Effect+=0.1; //젤큼

        if(pop_Effect >= 1){
            pop_Touch = 0.9f;
            pop_Drag = 0.4f;
            pop_Effect = 1.0f;
        }



    }
    public void set_Volume_Down(int vol){

        pop_Touch-=0.1;
        pop_Drag-=0.04;     //젤 작음
        pop_Effect-=0.1; //젤큼

        if(pop_Drag <= 0.1){
            pop_Touch = 0.0f;
            pop_Drag = 0.0f;
            pop_Effect = 0.0f;
        }


    }
    public void sound_Tuto_Value(int es, int bs, int tuto, int vive){

        editor.putInt("es", es);
        editor.putInt("bs", bs);
        editor.putInt("tuto", tuto);
        editor.putInt("vive", vive);
        editor.commit();

//        Log.e("@", "es = " + es + ", bs = " + bs);

    }












    /**
     *
     * 배경 화면 움직임
     */
    public synchronized void background_Effect_Move(){
        background_Effect_One.Background_Effect_Move_Pattern();
        background_Effect_Two.Background_Effect_Move_Pattern();
        background_Effect_Shark.Background_Effect_Move_Pattern();

    }



    /**
     * 버튼 객체 생성
     */
    public void button_Create_Method(){

        confirm_Button_1 = new GraphicButton(new Rect(0,
                0,
                0,
                0));
        confirm_Button_2 = new GraphicButton(new Rect(0,
                0,
                0,
                0));
        confirm_Button_3 = new GraphicButton(new Rect(0,
                0,
                0,
                0));

        confirm_Button_card_1 = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(75, _context),
                window_Height / 2 - convertPixelsToDp(50, _context),
                window_Width / 2 - convertPixelsToDp(75, _context) + convertPixelsToDp(50, _context),
                window_Height / 2 - convertPixelsToDp(50, _context) + convertPixelsToDp(70, _context)));

        confirm_Button_card_2 = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(22, _context),
                window_Height / 2 - convertPixelsToDp(50, _context),
                window_Width / 2 - convertPixelsToDp(22, _context) + convertPixelsToDp(50, _context),
                window_Height / 2 - convertPixelsToDp(50, _context) + convertPixelsToDp(70, _context)));

        confirm_Button_card_3 = new GraphicButton(new Rect(window_Width / 2 + convertPixelsToDp(31, _context),
                window_Height / 2 - convertPixelsToDp(50, _context),
                window_Width / 2 + convertPixelsToDp(31, _context) + convertPixelsToDp(50, _context),
                window_Height / 2 - convertPixelsToDp(50, _context) + convertPixelsToDp(70, _context)));

        //추출
        Extraction_Button_1 = new GraphicButton(new Rect(0,
                0,
                0,
                0));
        Extraction_Button_2 = new GraphicButton(new Rect(0,
                0,
                0,
                0));
        Extraction_Button_3 = new GraphicButton(new Rect(0,
                0,
                0,
                0));

    }
    /**
     * 버튼 객체 제거
     */
    private void button_Create_method_Init(){

        confirm_Button_1 = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(50, _context),
                window_Height / 2 + convertPixelsToDp(145, _context),
                window_Width / 2 - convertPixelsToDp(50, _context) + convertPixelsToDp(105, _context),
                window_Height / 2 + convertPixelsToDp(145, _context) + convertPixelsToDp(40, _context)));

        confirm_Button_3 = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(50, _context),
                window_Height / 2 + convertPixelsToDp(145, _context),
                window_Width / 2 - convertPixelsToDp(50, _context) + convertPixelsToDp(105, _context),
                window_Height / 2 + convertPixelsToDp(145, _context) + convertPixelsToDp(40, _context)));

        confirm_Button_card_1 = new GraphicButton(new Rect(0, 0, 0, 0));
        confirm_Button_card_2 = new GraphicButton(new Rect(0, 0, 0, 0));
        confirm_Button_card_3 = new GraphicButton(new Rect(0, 0, 0, 0));

    }

    //위에꺼랑 같은데 캐릭터 진화창 떴을때 컨펌 위치 바꿔주기 위해서
    private void button_Create_method_Init_Small(){

        confirm_Button_1 = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(70, _context),
                window_Height / 2 + convertPixelsToDp(145, _context),
                window_Width / 2 - convertPixelsToDp(70, _context) + convertPixelsToDp(65, _context),
                window_Height / 2 + convertPixelsToDp(145, _context) + convertPixelsToDp(40, _context)));
        confirm_Button_2 = new GraphicButton(new Rect(window_Width / 2 + convertPixelsToDp(10, _context),
                window_Height / 2 + convertPixelsToDp(145, _context),
                window_Width / 2 + convertPixelsToDp(10, _context) + convertPixelsToDp(65, _context),
                window_Height / 2 + convertPixelsToDp(145, _context) + convertPixelsToDp(40, _context)));
        confirm_Button_3 = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(70, _context),
                window_Height / 2 + convertPixelsToDp(145, _context),
                window_Width / 2 - convertPixelsToDp(70, _context) + convertPixelsToDp(65, _context),
                window_Height / 2 + convertPixelsToDp(145, _context) + convertPixelsToDp(40, _context)));

        confirm_Button_card_1 = new GraphicButton(new Rect(0, 0, 0, 0));
        confirm_Button_card_2 = new GraphicButton(new Rect(0, 0, 0, 0));
        confirm_Button_card_3 = new GraphicButton(new Rect(0, 0, 0, 0));
    }

    //추출 버튼 눌렀을때 확인/추출 버튼 제거
    private void button_Init_Confirm_Or_Extraction(){
        confirm_Button_1 = new GraphicButton(new Rect(0, 0, 0, 0));
        confirm_Button_2 = new GraphicButton(new Rect(0, 0, 0, 0));
        confirm_Button_3 = new GraphicButton(new Rect(0, 0, 0, 0));
    }
    //일반 추출, 영구추출, 캔슬 버튼 제거
    private void button_Init_Confirm_Or_Extraction_123(){
        confirm_Button_1 = new GraphicButton(new Rect(0, 0, 0, 0));
        confirm_Button_2 = new GraphicButton(new Rect(0, 0, 0, 0));
        confirm_Button_3 = new GraphicButton(new Rect(0, 0, 0, 0));
    }


    /**
     * 다시시작 및 시작 처음에 초기화
     */

    private double ruby = 0;
    private double money = 0;
    private double character_Randmark_Damege= 0;
    private double character_Drag_Damege= 0;
    private double urchinresistance= 0;
    private double lightningresistance= 0;
    private double crocodileresistance= 0;
    private double bird = 0;
    private double steel = 0;
    private double ft1 = 0;    private double ft2 = 0;    private double ft3 = 0;    private double ft4 = 0;    private double ft5 = 0;    private double ft6 = 0;    private double ft7 = 0;    private double ft8 = 0;    private double ft9 = 0;    private double ft10 = 0;
    private double st1 = 0;    private double st2 = 0;    private double st3 = 0;    private double st4 = 0;    private double st5 = 0;    private double st6 = 0;    private double st7 = 0;    private double st8 = 0;    private double st9 = 0;    private double st10 = 0;
    private double mt1 = 0;    private double mt2 = 0;    private double mt3 = 0;    private double mt4 = 0;    private double mt5 = 0;    private double mt6 = 0;    private double mt7 = 0;    private double mt8 = 0;    private double mt9 = 0;    private double mt10 = 0;

    private double ftb1 = 0;    private double ftb2 = 0;    private double ftb3 = 0;    private double ftb4 = 0;    private double ftb5 = 0;    private double ftb6 = 0;    private double ftb7 = 0;    private double ftb8 = 0;    private double ftb9 = 0;    private double ftb10 = 0;
    private double stb1 = 0;    private double stb2 = 0;    private double stb3 = 0;    private double stb4 = 0;    private double stb5 = 0;    private double stb6 = 0;    private double stb7 = 0;    private double stb8 = 0;    private double stb9 = 0;    private double stb10 = 0;
    private double mtb1 = 0;    private double mtb2 = 0;    private double mtb3 = 0;    private double mtb4 = 0;    private double mtb5 = 0;    private double mtb6 = 0;    private double mtb7 = 0;    private double mtb8 = 0;    private double mtb9 = 0;    private double mtb10 = 0;

    private boolean skill_Fish_Extract_Nomar[] = new boolean[10];
    private boolean skill_Shellfish_Extract_Nomar[] = new boolean[10];
    private boolean skill_Mollus_Extract_Nomar[] = new boolean[10];

    //값 전달
    Intent intent_Item = new Intent();
    double sent_Item[] = new double[91];

    //루비로 진화 하기 버튼
    int card_Ruby = 5;
    int card_Ruby_Position = 11;

    public void re_Start(){

        editor.putString("realmoney", String.valueOf(money));
        editor.commit();
        day_Count = 0;

        gameover_Flag = false;
        infinitymode_Status = false;
        infinitymode_Status_Flag = false;
        Monster_Clean= false;
//        Log.e("@","리스타트");
        //모든 캐릭터
        for(int i=0; i<fish_List.size(); i++){
            fish_List.get(i).set_Hp_Minus(5000000);
            if(fish_List.get(i).get_Class_Num() == 1 || fish_List.get(i).get_Class_Num() == 2 ){
                fish_List.get(i).set_Production(false);
            }

        }
        land_Mark_Class = 1;
        for(int i=0; i<ground_List.size(); i++){
            ground_List.get(i).set_Ground_Hp(0);
            if((ground_List.get(i) instanceof Land_Mark)) {
                //랜드마크

                ground_List.remove(i);
                add_Ground_Land_Mark();
            }
        }





        //플랑크노 1티어
        character_Explain_DB[0] = 1;

        /**
         *  money
         */

        //일반 추출 날리기
        for(int i=0; i<10; i++){
            skill_Fish_Extract_Nomar[i] = false;
            skill_Shellfish_Extract_Nomar[i] = false;
            skill_Mollus_Extract_Nomar[i] = false;
        }

        //영구 추출 스킬 셋팅
//        Log.e("@","스킬");
        for(int i=0; i<30; i++){

            if(i < 10){
                if(setIntent[39 + i] == 1){
                    skill_Fish_Extract_Nomar[i] = true;
//                    Log.e("@","fish = " + i );
                }
            }
            else if(i < 20){
                if(setIntent[39 + i] == 1){
                    skill_Shellfish_Extract_Nomar[i-10] = true;
//                    Log.e("@","Shellfish = " + (i-10) );
                }
            }
            else if(i < 30){
                if(setIntent[39 + i] == 1){
                    skill_Mollus_Extract_Nomar[i-20] = true;
//                    Log.e("@","Mollus = " + (i-20) );
                }
            }
        }


//        Log.e("@","skill_Fish_Extract_Nomar[0] = " + skill_Fish_Extract_Nomar[0] );
//        Log.e("@","skill_Fish_Extract_Nomar[1] = " + skill_Fish_Extract_Nomar[1] );
//        Log.e("@","skill_Fish_Extract_Nomar[2] = " + skill_Fish_Extract_Nomar[2] );
//
//        Log.e("@","skill_Shellfish_Extract_Nomar[0] = " + skill_Shellfish_Extract_Nomar[0] );
//        Log.e("@","skill_Shellfish_Extract_Nomar[1] = " + skill_Shellfish_Extract_Nomar[1] );
//        Log.e("@","skill_Shellfish_Extract_Nomar[2] = " + skill_Shellfish_Extract_Nomar[2] );



        //배경 화면 동적 움직임 생성
        //따로 쓰레드를 돌릴 필요 없이 게임 안에서 동작하게끔 한다.
        background_Effect_One = new Background_Effect_One(window_Width, window_Height);

        background_Effect_Two = new Background_Effect_Two(window_Width, window_Height);

        //배경 화면 상어 움직임
        background_Effect_Shark = new Background_Effect_Shark(window_Width, window_Height);

        //진화의 버튼 초기화
        revolution_Button = new GraphicButton(new Rect(0,
                0,
                0,
                0));




        //진화 버튼
        revolution_Flag = false;
        revolution_Draw_Flag_Confirm = false;
        revolution_Button_Activation_Down  = false;
        revolution_Button_Activation_Up = false;



        //스테이지  = 1, 속도 = 1

        //오브젝트 풀링으로 인해 클리어 할 필요없다.
//        fish_List.clear();


//         ground_List.clear();
        Score = 0;
//        land_Mark_Class = 1;

        first_Default_Fish = true;
        first_Drag_Fish = true;
        first_Urchin = true;
        first_Snail = true;
        first_Squid = true;
        first_Ell = true;

//        game_thread.function_Skill_Thorn_img();
        main_Character = new Main_Character_Plankton_1(0,0, window_Width, window_Height,0,0);
        //메인 캐릭터
        Init_Main_Character_Image(_context, main_Character);
        main_Character = new Main_Character_Plankton_1((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        game_thread.function_Skill_earthquake_img();

//          main_Character.set_Tear(0);

        //재시작 하면 초기화
        background_Effect_Location = new ArrayList<Point>(); //배경 이펙트 위치 선정;
        //배경 물방울 이펙트 개수
        for(int i=0; i<6; i++){

            if(i < 3) {

                Point point = new Point(
                        convertPixelsToDp(30, _context) + random.nextInt(window_Width - convertPixelsToDp(60, _context)),
                        convertPixelsToDp(30, _context) + random.nextInt((window_Height / 3) - convertPixelsToDp(60, _context)));
                background_Effect_Location.add(point);

            }else {

                Point point = new Point(
                        convertPixelsToDp(30, _context) + random.nextInt(window_Width - convertPixelsToDp(60, _context)),
                        convertPixelsToDp(30, _context) + (window_Height / 2) + random.nextInt((window_Height / 3)));
                background_Effect_Location.add(point);

            }


        }


        /**
         *  랜드 마크 이미지 변경 시점
         */


        //이빨 지뢰 제거
        skill_Teeth_Mine_List = new ArrayList<Skill_Teeth_Mine>();
        skill_Teeth_Mine2_List = new ArrayList<Skill_Teeth_Mine2>();



        //스테이지
        if(pref.getInt("tuto", 0) == 0){
            tutorial_Flag = true;
            first_Text_Explain_Flag = true;
            first_Text_Explain_Index = 0;
        }
        //사운드

        gameActivity.set_Back_Sound(pref.getInt("bs",0), pref.getInt("es",0), pref.getInt("tuto", 0) );
        gameActivity.set_Effect_Sound(pref.getInt("es",0), pref.getInt("bs",0), pref.getInt("tuto", 0) );




    }

    //게임 종료, 소라 제거
    public void exit(){
//        background_Sound.pause();
//        background_Sound.release();
        background_Sound.stop();
//        gameActivity.finish();
    }
    public void bg_Sound(){
        background_Sound = MediaPlayer.create(_context, R.raw.background_music_1);
        background_Sound.setLooping(true);

//        background_Sound.setVolume(0.5f,0.5f);
        background_Sound.start();
    }




    //게임 상태 컨트롤
    public void m_Run_False(){
        mRun = false;
//        fish_Maker.cancel();
        stage_Day.cancel();

    }

    boolean pause_State = false;

    public void m_Run_False(boolean param_Pause_State){ //일시정지 버튼을 통해 눌려 지면 화면이 안 어두워 져야 한다.
        mRun = false;
        pause_State = param_Pause_State;
//        fish_Maker.cancel();
        stage_Day.cancel();
    }


    //화면에서 버튼 눌릴지 말지
    public boolean get_m_Run(){
        return mRun;
    }

    public void m_Run_True(){
        mRun = true;

//        fish_Maker = timerTask_FishMaker();
        stage_Day = timerTask_Stage_Day();

//        mTimer.schedule(fish_Maker, 1000, 8000);
        mTimer.schedule(stage_Day, 1000, 10000);

    }


    //********************************************************************************************//

    /**
     *  내부 클래스
     *  게임 요소 추가 할 스레드 [물고기, 바닥 생명체 등.]
     */






    //********************************************************************************************//

    //힐 사용 가능 여부
    public boolean get_Heal_State() {
        if(main_Character.get_Hp() == main_Character.get_Max_Hp()){
            return false;
        }else {
            return true;
        }
    }

    /**
     *  이너 클래스
     *  내부 클래스 게임 스레드
     *  더블 버퍼링 및 게임 그리기
     */
    DisplayMetrics dm;

    public void set_Best_Point() {
        try {
            if (best_Point < Score) {
                editor.putString("score", Score + "");
            }
        }catch (Exception e){
            Log.e("@","bestPoint");
        }
    }
    public void set_Best_Point_2() {
        if (best_Point < Score) {
            best_Point = Score;
        }
    }



        class Game_Thread extends Thread{

        /**
         *  Game_Thread 기본 생성자, 이미지 초기화
         */
        public Game_Thread(/*SurfaceHolder surfaceHolder*/) { //더블 버퍼링 같은것,



            //게임 요소 추가 할 쓰레드 [물고기, 함정 등]
            Init_Background_Image(_context); //배경

            dm = _context.getApplicationContext().getResources().getDisplayMetrics();
            backGroundImg = Bitmap.createScaledBitmap(backGroundImg, dm.widthPixels, dm.heightPixels, false);
            backGroundImg_black = Bitmap.createScaledBitmap(backGroundImg_black, dm.widthPixels, dm.heightPixels, false); //배경 화면 어둡게





            /**
             * 슬로우 이미지
             */
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_slow_1);
            effect_Slow_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_slow_2);
            effect_Slow_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_slow_3);
            effect_Slow_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_slow_4);
            effect_Slow_img[3] = image.getBitmap();

            /**
             * 처 맞을때 효과
             */
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_blood_1);
            main_Character_Blood_Img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_blood_2);
            main_Character_Blood_Img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_blood_3);
            main_Character_Blood_Img[2] = image.getBitmap();


            /**
             * 독 효과 이미지
             */
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_poison_1);
            effect_Poison_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_poison_2);
            effect_Poison_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_poison_3);
            effect_Poison_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_poison_4);
            effect_Poison_img[3] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_poison_5);
            effect_Poison_img[4] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_poison_6);
            effect_Poison_img[5] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_poison_7);
            effect_Poison_img[6] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_poison_8);
            effect_Poison_img[7] = image.getBitmap();

            /**
             * 파도 팝 이미지
             */
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_pop_wave_1);
            effect_Wave_Pop_img = image.getBitmap();

            /**
             * 스테이지 이미지
             */
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_stage_day);
            effect_Stage_Day_img = image.getBitmap();


            /**
             * 참돔 이펙트
             */
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_pop_steel);
            effect_Pop_Steel_img = image.getBitmap();

            /**
             * 방해 거북 이펙트
             */
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_pop_turtle);
            effect_Pop_Turtle = image.getBitmap();




            /**
             * 랜드마크 팝 이미지
             */
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark1pop_1);
            effect_Land_Mark_Pop1_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark1pop_2);
            effect_Land_Mark_Pop1_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark1pop_3);
            effect_Land_Mark_Pop1_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark1pop_4);
            effect_Land_Mark_Pop1_img[3] = image.getBitmap();

            /**
             * 랜드마크 드래그 이미지
             */
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_drag_landmark_1);
            land_Mark_Drag_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_drag_landmark_2);
            land_Mark_Drag_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_drag_landmark_3);
            land_Mark_Drag_img[2] = image.getBitmap();

            /**
             * 딱따구리
             */
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_bird_1);
            land_Mark_Bird_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_bird_2);
            land_Mark_Bird_img[1] = image.getBitmap();

            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark2pop_1);
            effect_Land_Mark_Pop2_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark2pop_2);
            effect_Land_Mark_Pop2_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark2pop_3);
            effect_Land_Mark_Pop2_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark2pop_4);
            effect_Land_Mark_Pop2_img[3] = image.getBitmap();




            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark3pop_1);
            effect_Land_Mark_Pop3_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark3pop_2);
            effect_Land_Mark_Pop3_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark3pop_3);
            effect_Land_Mark_Pop3_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_land_mark3pop_4);
            effect_Land_Mark_Pop3_img[3] = image.getBitmap();


            //진화의창
            function_Explain_Window_Revolrution();


            //악어
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_crocodile_rest_1);
            ground_Touch_Crocodile_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_crocodile_rest_2);
            ground_Touch_Crocodile_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_crocodile_rest_3);
            ground_Touch_Crocodile_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_crocodile_rest_4);
            ground_Touch_Crocodile_img[3] = image.getBitmap();



            //창새치
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.fish_marlin_1);
            fish_Touch_Marlin_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.fish_marlin_2);
            fish_Touch_Marlin_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.fish_marlin_3);
            fish_Touch_Marlin_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.fish_marlin_4);
            fish_Touch_Marlin_img[3] = image.getBitmap();



            //소라게 이미지
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp1_1);
            ground_Touch_Hermit_Hp1_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp1_2);
            ground_Touch_Hermit_Hp1_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp1_3);
            ground_Touch_Hermit_Hp1_img[2] = image.getBitmap();

            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp2_1);
            ground_Touch_Hermit_Hp2_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp2_2);
            ground_Touch_Hermit_Hp2_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp2_3);
            ground_Touch_Hermit_Hp2_img[2] = image.getBitmap();

            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp3_1);
            ground_Touch_Hermit_Hp3_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp3_2);
            ground_Touch_Hermit_Hp3_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp3_3);
            ground_Touch_Hermit_Hp3_img[2] = image.getBitmap();

            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp4_1);
            ground_Touch_Hermit_Hp4_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp4_2);
            ground_Touch_Hermit_Hp4_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp4_3);
            ground_Touch_Hermit_Hp4_img[2] = image.getBitmap();

            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp5_1);
            ground_Touch_Hermit_Hp5_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp5_2);
            ground_Touch_Hermit_Hp5_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_hermitcrab_hp5_3);
            ground_Touch_Hermit_Hp5_img[2] = image.getBitmap();


            //그림자
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.shadow_small_fish);
            shadow_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.shadow_middle_fish);
            shadow_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.shadow_big_fish);
            shadow_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.shadow_drag_fish);
            shadow_img[3] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.shadow_squid_fish);
            shadow_img[4] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.shadow_crab);
            shadow_img[5] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.shadow_crocodile);
            shadow_img[6] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.shadow_turtle);
            shadow_img[7] = image.getBitmap();

            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.gameover_massage);
            gameover_Img = image.getBitmap();

            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.infinitymode);
            infinitymode_Img = image.getBitmap();

            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_drag_wave_s);
            ground_Drag_Wave_img[0] =  image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_drag_wave);
            ground_Drag_Wave_img[1] = image.getBitmap();

            //골드
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.background_gold);
            gold_img = image.getBitmap();

            //루비
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.background_ruby);
            ruby_img = image.getBitmap();



            //기본 물고기
            for(int i = 0; i < 4; i++) {
                fish_Touch_Default_Hp1_img[i] = Init_Fish_Touch_Default_Hp1_Image(_context, i); //캐릭터 이미지 추가 hp = 1
                fish_Touch_Default_Hp2_img[i] = Init_Fish_Touch_Default_Hp2_Image(_context, i); //캐릭터 이미지 추가
                fish_Touch_Default_Hp3_img[i] = Init_Fish_Touch_Default_Hp3_Image(_context, i); //캐릭터 이미지 추가
                fish_Touch_Default_Hp4_img[i] = Init_Fish_Touch_Default_Hp4_Image(_context, i); //캐릭터 이미지 추가
                fish_Touch_Default_Hp5_img[i] = Init_Fish_Touch_Default_Hp5_Image(_context, i); //캐릭터 이미지 추가 hp = 5
            }



            //터치 중간 보스
            for(int i = 0; i < 4; i++) {
                fish_Touch_Default_Middle_Hp1_img[i] = Init_Fish_Touch_Default_Middle_Hp1_Image(_context, i);
                fish_Touch_Default_Middle_Hp2_img[i] = Init_Fish_Touch_Default_Middle_Hp2_Image(_context, i);
                fish_Touch_Default_Middle_Hp3_img[i] = Init_Fish_Touch_Default_Middle_Hp3_Image(_context, i);
                fish_Touch_Default_Middle_Hp4_img[i] = Init_Fish_Touch_Default_Middle_Hp4_Image(_context, i);
                fish_Touch_Default_Middle_Hp5_img[i] = Init_Fish_Touch_Default_Middle_Hp5_Image(_context, i);
            }

            //터치 보스
            for(int i = 0; i < 4; i++) {
                fish_Touch_Default_Boss_Hp1_img[i] = Init_Fish_Touch_Default_Boss_Hp1_Image(_context, i);
                fish_Touch_Default_Boss_Hp2_img[i] = Init_Fish_Touch_Default_Boss_Hp2_Image(_context, i);
                fish_Touch_Default_Boss_Hp3_img[i] = Init_Fish_Touch_Default_Boss_Hp3_Image(_context, i);
                fish_Touch_Default_Boss_Hp4_img[i] = Init_Fish_Touch_Default_Boss_Hp4_Image(_context, i);
                fish_Touch_Default_Boss_Hp5_img[i] = Init_Fish_Touch_Default_Boss_Hp5_Image(_context, i);
            }

            for(int i = 0; i < 4; i++) {
                ground_Touch_Snail_Hp1_img[i] = Init_Ground_Touch_Snail_Hp1_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp2_img[i] = Init_Ground_Touch_Snail_Hp2_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp3_img[i] = Init_Ground_Touch_Snail_Hp3_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp4_img[i] = Init_Ground_Touch_Snail_Hp4_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp5_img[i] = Init_Ground_Touch_Snail_Hp5_Image(_context, i);  //달팽이 이미지

                ground_Touch_Starfish_img[i] = Init_Ground_Touch_Starfish_Image(_context, i);  //불가사리 이미지
            }

            for(int i=0; i<3; i++){
                ground_Touch_Bearbug_img[i] = Init_Ground_Touch_Bearbug_Image(_context, i);   //곰벌레 이미지
            }

            for(int i = 0; i < 4; i++) {
                ground_Snail_Middle_Hp1_img[i] = Init_Ground_Touch_Snail_Middle_Hp1_Image(_context, i);  //달팽이 이미지  중간보스
                ground_snail_Middle_Hp2_img[i] = Init_Ground_Touch_Snail_Middle_Hp2_Image(_context, i);  //달팽이 이미지
                ground_snail_Middle_Hp3_img[i] = Init_Ground_Touch_Snail_Middle_Hp3_Image(_context, i);  //달팽이 이미지
                ground_snail_Middle_Hp4_img[i] = Init_Ground_Touch_Snail_Middle_Hp4_Image(_context, i);  //달팽이 이미지
                ground_snail_Middle_Hp5_img[i] = Init_Ground_Touch_Snail_Middle_Hp5_Image(_context, i);  //달팽이 이미지
            }

            for(int i = 0; i < 4; i++) {
                ground_snail_Boss_Hp1_img[i] = Init_Ground_Touch_Snail_Boss_Hp1_Image(_context, i);  //달팽이 이미지 보스
                ground_snail_Boss_Hp2_img[i] = Init_Ground_Touch_Snail_Boss_Hp2_Image(_context, i);  //달팽이 이미지
                ground_snail_Boss_Hp3_img[i] = Init_Ground_Touch_Snail_Boss_Hp3_Image(_context, i);  //달팽이 이미지
                ground_snail_Boss_Hp4_img[i] = Init_Ground_Touch_Snail_Boss_Hp4_Image(_context, i);  //달팽이 이미지
                ground_snail_Boss_Hp5_img[i] = Init_Ground_Touch_Snail_Boss_Hp5_Image(_context, i);  //달팽이 이미지
            }

            for(int i = 0; i < 4; i++) {
                fish_Drag_Default_img[i] = Init_Fish_Drag_Default_Image(_context, i);            //드래그 물고기
                fish_Drag_Steelbream_img[i] =  Init_Fish_Drag_Steelbream_Image(_context, i);        //참돔
                ground_Drag_Crab_img[i] = Init_Ground_Drag_Crab_Image(_context, i);              //꽃게 이미지
                ground_Drag_Clam_img[i] = Init_Ground_Drag_Clam_Image(_context, i);             //조개이미지
                fish_Drag_Shark_img[i] = Init_Fish_Shark_Image(_context, i); //드래그 상어
            }

            for(int i=0; i<5; i++){
                ground_Touch_Stingray_img[i] = Init_Ground_Touch_Stingray_Image(_context, i);   //가오리 이미지

            }





            for(int i=0; i<8; i++) {
                //오징어 이미지
                fish_Touch_Squid_img[i] = Init_Fish_Touch_Squid_Image(_context, i);
                //전기 뱀장어 이미지
                fish_Touch_Ell_img[i] = Init_Fish_Touch_Ell_Image(_context, i);
                fish_Touch_Ell_Attack_img[i] = Init_Fish_Touch_Ell_Attack_Image(_context, i);
            }


            //해파리 이미지
            for(int i=0; i<4; i++){
                fish_Trap_Jelly_img[i] = Init_Fish_Trap_Jellyfish(_context, i);
            }
            //방해거북 이미지
            for(int i=0; i<3; i++){
                fish_Turtle_img[i] =  Init_Fish_Turtle(_context, i);
            }
            //몬스터 이미지
            for(int i=0; i<3; i++) {
                fish_Monster_Left_img[i] = Init_Fish_Monster_Left(_context, i);
            }
            for(int i=0; i<3; i++) {
                fish_Monster_Right_img[i] = Init_Fish_Monster_Right(_context, i);
            }


                for(int i=0; i<5; i++){

                effect_Orange_img[i] = Init_Effect_Orange_Image(_context, i); //이펙트
                effect_Blue_img[i]   = Init_Effect_Blue_Image(_context, i);
                effect_Yellow_img[i] = Init_Effect_Yellow_Image(_context, i);
                effect_Green_img[i]  = Init_Effect_Green_Image(_context, i);
                effect_Black_img[i] = Init_Effect_Black_Image(_context, i);

                Init_Effect_Drag_Pop_Image(_context, i);



                effect_Pop2_img[i] = Init_Effect_Pop2_Image(_context, i);
                effect_Pop3_img[i] = Init_Effect_Pop3_Image(_context, i);
                effect_Pop4_img[i] = Init_Effect_Pop4_Image(_context, i);
                effect_Pop5_img[i] = Init_Effect_Pop5_Image(_context, i);
                effect_Pop6_img[i] = Init_Effect_Pop6_Image(_context, i);

                ground_Trap_Urchin_img[i] = Init_Ground_Trap_Urchin_Image(_context, i);              //성게 이미지
                ground_Trap_Urchin_Rest_Mode_img[i] = Init_Ground_Trap_Urchin_Rest_Mode_Image(_context, i); //성게 휴식기 이미지

            }

            for(int i=0; i<5; i++){
                effect_background_Seaanemone_img[i] = Init_Background_Effect_Background_Seaanemone_Image(_context, i);    //배경 이펙트
                effect_Background_Squid_Ink_img[i] = Init_Background_Effect_Background_Squid_Ink_Image(_context, i);    //오징어 이펙트
            }

            for(int i=0; i<8; i++){
                effect_Background_One_1_img[i] = Init_Background_Effect_Background_One_1_Image(_context, i);    //배경 이펙트
                effect_Background_Two_1_img[i] = Init_Background_Effect_Background_Two_1_Image(_context, i);    //배경 이펙트
                effect_Background_Seaweed_img[i] = Init_Background_Effect_Background_Seaweed_Image(_context, i);    //배경 이펙트

                effect_background_Rock[i] = Init_Background_Effect_Background_Rock_Image(_context, i);    //배경 이펙트

            }

            for(int i=0; i<4; i++){
                effect_Background_Friend_Shark_img[i] = Init_Background_Effect_Background_Friend_Shark_Image(_context, i);    //상어 친구 부르기 이펙트
            }


            for(int i=0; i<5; i++){
                effect_Background_Shark_img[i] = Init_Background_Effect_Background_Shark_Image(_context, i);    //배경 이펙트
            }
            for(int i=0; i<6; i++) {
                ground_Drag_Lobsters_img[i] = Init_Ground_Drag_Lobsters_Image(_context, i);     //가제 이미지
            }



            //오브젝트 풀링 물고기 가져오기
            fish_List.clear();
            ground_List.clear();

            fish_Total_Production();
            ground_Total_Production();

        }



        /**
         * 진화의창 설명창
         */
        public void function_Explain_Window_Revolrution() {
            //진화의창 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_revolrution);
            explain_Window_Revoluition = image.getBitmap();

            //추출창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_extraction);
            explain_Window_Extraction = image.getBitmap();



        }
        public void recycle_Explain_Window_Revolrution() {
            explain_Window_Revoluition.recycle();
            explain_Window_Revoluition = null;
        }


        /**
         *  fish_Tear
         */
        public void function_Explain_Window_Fish_Tear_1() { //explain_window_origincrab
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_1tearfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_2() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_2tearfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_3() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_3tearfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_4() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_4tearfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_5() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_5tearfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_6() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_6tearfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_7() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_7tearfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_8() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_8tearfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_9() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_9tearfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_10() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_10tearfish);
            explain_Window_MainCharacker = image.getBitmap();
        }


        /**
         * moulluse_Tear
         */
        public void function_Explain_Window_Moulluse_Tear_1() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_1moullusc);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_2() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_2moullusc);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_3() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_3moullusc);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_4() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_4moullusc);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_5() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_5moullusc);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_6() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_6moullusc);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_7() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_7moullusc);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_8() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_8moullusc);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_9() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_9moullusc);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_10() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_10moullusc);
            explain_Window_MainCharacker = image.getBitmap();
        }


        /**
         *  Shellfish_Tear
         */
        public void function_Explain_Window_Shellfish_Tear_1() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_1tearshellfish);
            explain_Window_MainCharacker = image.getBitmap();

        }
        public void function_Explain_Window_Shellfish_Tear_2() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_2tearshellfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_3() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_3tearshellfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_4() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_4tearshellfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_5() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_5tearshellfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_6() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_6tearshellfish);
            explain_Window_MainCharacker = image.getBitmap();
        }public void function_Explain_Window_Shellfish_Tear_7() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_7tearshellfish);
            explain_Window_MainCharacker = image.getBitmap();
        }public void function_Explain_Window_Shellfish_Tear_8() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_8tearshellfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_9() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_9tearshellfish);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_10() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_10tearshellfish);
            explain_Window_MainCharacker = image.getBitmap();
        }



        public void function_Explain_Window_Plankton_Tear_1() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_1tear_plankton);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Plankton_Tear_2() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_2tear_plankton);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Plankton_Tear_3() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_3tear_plankton);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Plankton_Tear_4() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_4tear_plankton);
            explain_Window_MainCharacker = image.getBitmap();
        }
        public void function_Explain_Window_Plankton_Tear_5() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_5tear_plankton);
            explain_Window_MainCharacker = image.getBitmap();
        }





        /**
         * 메인 캐릭터
         */

        public void recycle_Main_Character(){
            for(int i=0; i<6; i++){
                main_Character_Img[i].recycle();
                main_Character_Img[i] = null;

                main_Character_Img_1[i].recycle();
                main_Character_Img_1[i] = null;
            }
        }



        public void function_Main_Character_Plankton1(){

            for(int i=0; i<6;i++) {
                image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_plankton_tear1_1 + i);
                main_Character_Img[i] = image.getBitmap();
            }
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_plankton_1tear1_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }

        }

        //////////////////////////////////////////////
        public void function_Main_Character_Plankton2(){

            for(int i=0; i<6;i++) {
                image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_plankton_tear2_1 + i);
                main_Character_Img[i] = image.getBitmap();
            }
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_plankton_1tear2_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        //////////////////////////////////////////////
        public void function_Main_Character_Plankton3(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_plankton_tear3_1 + i);
                main_Character_Img[i] = image.getBitmap();
            }
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_plankton_1tear3_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        //////////////////////////////////////////////
        public void function_Main_Character_Plankton4(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_plankton_tear4_1 + i);
                main_Character_Img[i] = image.getBitmap();
            }
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_plankton_1tear4_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        //////////////////////////////////////////////
        public void function_Main_Character_Plankton5(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_plankton_tear5_1 + i);
                main_Character_Img[i] = image.getBitmap();
            }
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.character_plankton_1tear5_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        //////////////////////////////////////////////


        public void function_Main_Character_Fish1(){
            for(int i=0; i<6; i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_tear1_1 + i);
                main_Character_Img[i] = image.getBitmap();

                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_1tear1_1 + i);
                main_Character_Img_1[i] = image.getBitmap();

            }
        }
        ////////////////////////////////////////////////
        public void function_Main_Character_Fish2(){
            for(int i=0; i<6; i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_tear2_1 + i);
                main_Character_Img[i] = image.getBitmap();

                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_1tear2_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Fish3(){
            for(int i=0; i<6; i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_tear3_1 + i);
                main_Character_Img[i] = image.getBitmap();

                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_1tear3_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Fish4(){
            for(int i=0; i<6; i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_tear4_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_1tear4_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Fish5(){
            for(int i=0; i<6; i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_tear5_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_1tear5_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Fish6(){
            for(int i=0; i<6; i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_tear6_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_1tear6_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Fish7(){
            for(int i=0; i<6; i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_tear7_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_1tear7_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Fish8(){
            for(int i=0; i<6; i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_tear8_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_1tear8_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Fish9(){
            for(int i=0; i<6; i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_tear9_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_1tear9_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }
        ////////////////////////////////////////////////
        public void function_Main_Character_Fish10(){
            for(int i=0; i<6; i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_tear10_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_fish_1tear10_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Shellfish1(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_tear1_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_1tear1_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }

        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Shellfish2(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_tear2_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_1tear2_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }

        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Shellfish3(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_tear3_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_1tear3_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }

        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Shellfish4(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_tear4_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_1tear4_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Shellfish5(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_tear5_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_1tear5_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Shellfish6(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_tear6_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_1tear6_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Shellfish7(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_tear7_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_1tear7_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Shellfish8(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_tear8_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_1tear8_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Shellfish9(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_tear9_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_1tear9_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Shellfish10(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_tear10_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_shellfish_1tear10_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Moulluse1(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_tear1_1 + i);
                main_Character_Img[i] = image.getBitmap();

                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_1tear1_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Moulluse2(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_tear2_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_1tear2_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Moulluse3(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_tear3_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_1tear3_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Moulluse4(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_tear4_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_1tear4_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Moulluse5(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_tear5_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_1tear5_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Moulluse6(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_tear6_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_1tear6_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Moulluse7(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_tear7_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_1tear7_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Moulluse8(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_tear8_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_1tear8_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }

        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Moulluse9(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_tear9_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_1tear9_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////
        public void function_Main_Character_Moulluse10(){
            for(int i=0; i<6;i++) {
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_tear10_1 + i);
                main_Character_Img[i] = image.getBitmap();
                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.character_mollusc_1tear10_1 + i);
                main_Character_Img_1[i] = image.getBitmap();
            }
        }

        ////////////////////////////////////////////////

        /**
         * 스킬 이미지
         */
        //갑각류 티어 4
        public void function_Skill_Crab_img(){
            //꽃게 스킬
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_crab_1);
            skill_Crab_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_crab_2);
            skill_Crab_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_crab_3);
            skill_Crab_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_crab_4);
            skill_Crab_img[3] = image.getBitmap();

        }


        ///////////////////////////////////////////////////////////

        //갑각류 티어 5
        public void function_Skill_Soycrab_img(){
            //간장 게장 스킬
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_soycrab_1);
            skill_Soycrab_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_soycrab_2);
            skill_Soycrab_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_soycrab_3);
            skill_Soycrab_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_soycrab_4);
            skill_Soycrab_img[3] = image.getBitmap();

        }

        ///////////////////////////////////////////////////////////

        //연체류 티어 6
        public void function_Skill_Laser_img(){
            //레이저
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_laser_1);
            skill_Laser_img = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////
        //물고기 티어 2
        public void function_Skill_Thorn_img(){
            //가시소환
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_thorn_1);
            skill_Thorn_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_thorn_2);
            skill_Thorn_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_thorn_3);
            skill_Thorn_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_thorn_4);
            skill_Thorn_img[3] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////

        //연체류 티어 10
        public void function_Skill_Poison123_img() {
            //독구름 소환
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud1_1);
            skill_Poison1_img[0] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud1_2);
            skill_Poison1_img[1] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud1_3);
            skill_Poison1_img[2] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud1_4);
            skill_Poison1_img[3] = image.getBitmap();

            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud2_1);
            skill_Poison2_img[0] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud2_2);
            skill_Poison2_img[1] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud2_3);
            skill_Poison2_img[2] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud2_4);
            skill_Poison2_img[3] = image.getBitmap();

            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud3_1);
            skill_Poison3_img[0] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud3_2);
            skill_Poison3_img[1] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud3_3);
            skill_Poison3_img[2] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.skill_posion_cloud3_4);
            skill_Poison3_img[3] = image.getBitmap();
        }
        ///////////////////////////////////////////////////////////

        //물고기 티어 4
        public void function_Skill_earthquake_img(){
//지진 소환
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_earthquake_1);
            skill_earthquake_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_earthquake_2);
            skill_earthquake_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_earthquake_3);
            skill_earthquake_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_earthquake_4);
            skill_earthquake_img[3] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////

        //물고기 티어 3
        public void function_Skill_Teeth_mine_img(){
            //이빨 지뢰
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_teeth_mine_1);
            skill_Teeth_mine_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_teeth_mine_2);
            skill_Teeth_mine_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_teeth_mine_3);
            skill_Teeth_mine_img[2] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////

        //물고기 티어 5
        public void function_Skill_Teeth_mine2_img(){
//중간 이빨 지뢰
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_teeth_mine2_1);
            skill_Teeth_mine2_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_teeth_mine2_2);
            skill_Teeth_mine2_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_teeth_mine2_3);
            skill_Teeth_mine2_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_teeth_mine2_4);
            skill_Teeth_mine2_img[3] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////

        //물고기 티어 9
        public void function_Skill_Sea_Snake_img(){
//바다뱀 소환
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_sea_snake_1);
            skill_Sea_Snake_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_sea_snake_2);
            skill_Sea_Snake_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_sea_snake_3);
            skill_Sea_Snake_img[2] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////

        //연체류 티어 3
        public void function_Skill_Slow_Cloud_img(){

            //이속 느려지는 구름 생성
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_slow_cloud_1);
            skill_Slow_Cloud_img = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////


        //연체류 티어 8
        public void function_Skill_Boom_Poison_img(){

            //독 폭탄
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_boom_poison_1);
            skill_Boom_Poison_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_boom_poison_2);
            skill_Boom_Poison_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_boom_poison_3);
            skill_Boom_Poison_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_boom_poison_4);
            skill_Boom_Poison_img[3] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////


        //갑각류 티어 9
        public void function_Skill_Wave_img(){
//파도 소환
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_wave_1);
            skill_Wave_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_wave_2);
            skill_Wave_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_wave_3);
            skill_Wave_img[2] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////

        //연체류 티어 9
        public void function_Skill_Wall_img(){
            //벽 소환
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_wall_1);
            skill_wall_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_wall_2);
            skill_wall_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_wall_3);
            skill_wall_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_wall_4);
            skill_wall_img[3] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////

        //갑각류 티어 6
        public void function_Skill_Thorn2_img(){
            //가시2 소환
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_thorn2_1);
            skill_Thorn2_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_thorn2_2);
            skill_Thorn2_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_thorn2_3);
            skill_Thorn2_img[2] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////

        //물고기 티어 7
        public void function_Skill_lightnign_img(){
//번개 소환
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_lightning_1);
            skill_lightnign_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_lightning_2);
            skill_lightnign_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_lightning_3);
            skill_lightnign_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_lightning_4);
            skill_lightnign_img[3] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////

        //물고기 티어 8
        public void function_Skill_lightnign1_img(){
            //번개2 소환
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_lightning1_1);
            skill_lightnign1_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_lightning1_2);
            skill_lightnign1_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_lightning1_3);
            skill_lightnign1_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_lightning1_4);
            skill_lightnign1_img[3] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////


        //갑각률 티어 10
        public void function_Skill_stomp_img(){
//스톰프
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_stomp_1);
            skill_stomp_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_stomp_2);
            skill_stomp_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_stomp_3);
            skill_stomp_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_stomp_4);
            skill_stomp_img[3] = image.getBitmap();
        }

///////////////////////////////////////////////////////////

        //갑각류 티어 7
        public void function_Skill_fry_img(){

            //튀김 지회
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_fry_1);
            skill_fry_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_fry_2);
            skill_fry_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_fry_3);
            skill_fry_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_fry_4);
            skill_fry_img[3] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_fry_5);
            skill_fry_img[4] = image.getBitmap();
        }


        ///////////////////////////////////////////////////////////

        //연체류 티어 4
        public void function_Skill_Butter_img(){
            //버터
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_butter_1);
            skill_Butter_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_butter_2);
            skill_Butter_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_butter_3);
            skill_Butter_img[2] = image.getBitmap();
        }

        ///////////////////////////////////////////////////////////

        //연체류 티어 5
        public void function_Skill_Fork_img(){
            //포크
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_fork_1);
            skill_Fork_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_fork_2);
            skill_Fork_img[1] = image.getBitmap();
        }

        public void function_Skill_All_Recycle(){
            //꽃게
            skill_Crab_img[0].recycle();
            skill_Crab_img[1].recycle();
            skill_Crab_img[2].recycle();
            skill_Crab_img[3].recycle();

            //간장꽃게
            skill_Soycrab_img[0].recycle();
            skill_Soycrab_img[1].recycle();
            skill_Soycrab_img[2].recycle();
            skill_Soycrab_img[3].recycle();

            //레이저
            skill_Laser_img.recycle();

            //가시
            skill_Thorn_img[0].recycle();
            skill_Thorn_img[1].recycle();
            skill_Thorn_img[2].recycle();
            skill_Thorn_img[3].recycle();

            //가스
            skill_Poison1_img[0].recycle();
            skill_Poison1_img[1].recycle();
            skill_Poison1_img[2].recycle();
            skill_Poison1_img[3].recycle();

            skill_Poison2_img[0].recycle();
            skill_Poison2_img[1].recycle();
            skill_Poison2_img[2].recycle();
            skill_Poison2_img[3].recycle();

            skill_Poison3_img[0].recycle();
            skill_Poison3_img[1].recycle();
            skill_Poison3_img[2].recycle();
            skill_Poison3_img[3].recycle();

            //지진소환
            skill_earthquake_img[0].recycle();
            skill_earthquake_img[1].recycle();
            skill_earthquake_img[2].recycle();
            skill_earthquake_img[3].recycle();

            //이빨 지뢰1
            skill_Teeth_mine_img[0].recycle();
            skill_Teeth_mine_img[1].recycle();
            skill_Teeth_mine_img[2].recycle();

            //이빨 지뢰2
            skill_Teeth_mine2_img[0].recycle();
            skill_Teeth_mine2_img[1].recycle();
            skill_Teeth_mine2_img[2].recycle();
            skill_Teeth_mine2_img[3].recycle();

            //바다뱀 소환
            skill_Sea_Snake_img[0].recycle();
            skill_Sea_Snake_img[1].recycle();
            skill_Sea_Snake_img[2].recycle();

            //슬로우 구름
            skill_Slow_Cloud_img.recycle();

            //독 폭탄
            skill_Boom_Poison_img[0].recycle();
            skill_Boom_Poison_img[1].recycle();
            skill_Boom_Poison_img[2].recycle();
            skill_Boom_Poison_img[3].recycle();

            //파도
            skill_Wave_img[0].recycle();
            skill_Wave_img[1].recycle();
            skill_Wave_img[2].recycle();

            //벽
            skill_wall_img[0].recycle();
            skill_wall_img[1].recycle();
            skill_wall_img[2].recycle();
            skill_wall_img[3].recycle();

            //가시2
            skill_Thorn2_img[0].recycle();
            skill_Thorn2_img[1].recycle();
            skill_Thorn2_img[2].recycle();

            //번개1
            skill_lightnign_img[0].recycle();
            skill_lightnign_img[1].recycle();
            skill_lightnign_img[2].recycle();
            skill_lightnign_img[3].recycle();

            //번개2
            skill_lightnign1_img[0].recycle();
            skill_lightnign1_img[1].recycle();
            skill_lightnign1_img[2].recycle();
            skill_lightnign1_img[3].recycle();

            //스톰프
            skill_stomp_img[0].recycle();
            skill_stomp_img[1].recycle();
            skill_stomp_img[2].recycle();
            skill_stomp_img[3].recycle();

            //튀김 지뢰
            skill_fry_img[0].recycle();
            skill_fry_img[1].recycle();
            skill_fry_img[2].recycle();
            skill_fry_img[3].recycle();
            skill_fry_img[4].recycle();

            //버터
            skill_Butter_img[0].recycle();
            skill_Butter_img[1].recycle();
            skill_Butter_img[2].recycle();


            //포크
            skill_Fork_img[0].recycle();
            skill_Fork_img[1].recycle();
        }



        /**
         * 랜드마크 이미지
         */

        /**
         * 랜드마크 1 이미지
         */
        public void function_Land_Mark_1() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark1_1);
            land_Mark1_img[0] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark1_2);
            land_Mark1_img[1] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark1_3);
            land_Mark1_img[2] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark1_4);
            land_Mark1_img[3] = image.getBitmap();

        }
        public void recycle_Land_Mark_1() {
            for(int i=0; i<4; i++){
                land_Mark1_img[i].recycle();
                land_Mark1_img[i] = null;
            }

        }


        /**
         * 랜드마크 2 이미지
         */
        public void function_Land_Mark_2() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark2_1);
            land_Mark2_img[0] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark2_2);
            land_Mark2_img[1] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark2_3);
            land_Mark2_img[2] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark2_4);
            land_Mark2_img[3] = image.getBitmap();
        }
        public void recycle_Land_Mark_2() {
            for(int i=0; i<4; i++){
                land_Mark2_img[i].recycle();
                land_Mark2_img[i] = null;
            }
        }


        /**
         * 랜드마크 3 이미지
         */
        public void function_Land_Mark_3() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark3_1);
            land_Mark3_img[0] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark3_2);
            land_Mark3_img[1] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark3_3);
            land_Mark3_img[2] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark3_4);
            land_Mark3_img[3] = image.getBitmap();
        }
        public void recycle_Land_Mark_3() {
            for(int i=0; i<4; i++){
                land_Mark3_img[i].recycle();
                land_Mark3_img[i] = null;
            }
        }


        /**
         * 랜드마크 4 이미지
         */
        public void function_Land_Mark_4() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark4_1);
            land_Mark4_img[0] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark4_2);
            land_Mark4_img[1] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark4_3);
            land_Mark4_img[2] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark4_4);
            land_Mark4_img[3] = image.getBitmap();
        }
        public void recycle_Land_Mark_4() {
            for(int i=0; i<4; i++){
                land_Mark4_img[i].recycle();
                land_Mark4_img[i] = null;
            }
        }


        /**
         * 랜드마크 5 이미지
         */
        public void function_Land_Mark_5() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark5_1);
            land_Mark5_img[0] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark5_2);
            land_Mark5_img[1] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark5_3);
            land_Mark5_img[2] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark5_4);
            land_Mark5_img[3] = image.getBitmap();
        }
        public void recycle_Land_Mark_5() {
            for(int i=0; i<4; i++){
                land_Mark5_img[i].recycle();
                land_Mark5_img[i] = null;
            }
        }

        /**
         * 랜드마크 6 이미지
         */
        public void function_Land_Mark_6() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark6_1);
            land_Mark6_img[0] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark6_2);
            land_Mark6_img[1] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark6_3);
            land_Mark6_img[2] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark6_4);
            land_Mark6_img[3] = image.getBitmap();
        }

        public void recycle_Land_Mark_6() {
            for(int i=0; i<4; i++){
                land_Mark6_img[i].recycle();
                land_Mark6_img[i] = null;
            }
        }

        public void function_Land_Mark_7() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark6_1);
            land_Mark6_img[0] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark6_2);
            land_Mark6_img[1] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark6_3);
            land_Mark6_img[2] = image.getBitmap();
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.land_mark6_4);
            land_Mark6_img[3] = image.getBitmap();
        }

        public void recycle_Land_Mark_7() {
            for(int i=0; i<4; i++){
                land_Mark6_img[i].recycle();
                land_Mark6_img[i] = null;
            }
        }





        /**
         * 경고 이미지
         */


        boolean warning_Add_Flag = false;
        public void function_Warning_Marlin(){
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.warning_marlin);
            warning_img = image.getBitmap();
        }
        public void function_Warning_Wave(){
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.warning_wave);
            warning_img = image.getBitmap();
        }
        public void function_Warning_Ell(){
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.warning_ell);
            warning_img = image.getBitmap();
        }



        /**
         *  이미지 초기화 기법 함수
         */

        //배경이미지
        public void Init_Background_Image(Context context){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.background);
            backGroundImg = image.getBitmap();

            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.background_1);
            backGroundImg_black = image.getBitmap();
        }
        //배경 이펙트
        public Bitmap Init_Background_Effect_Background_One_1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_background_one_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Background_Effect_Background_Two_1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_background_two_1 + num);
            return image.getBitmap();
        }
        public Bitmap  Init_Background_Effect_Background_Seaweed_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_background_seeweed_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Background_Effect_Background_Seaanemone_Image(Context context, int num) {
            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.effect_background_seaanemone_1 + num);

            return image.getBitmap();
        }
        public Bitmap Init_Background_Effect_Background_Rock_Image(Context context, int num) {
            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.effect_background_rock_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Background_Effect_Background_Shark_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_background_shark_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Background_Effect_Background_Squid_Ink_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_squid_ink_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Background_Effect_Background_Friend_Shark_Image (Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_friend_shark_1 + num);
            return image.getBitmap();
        }





        //물고기 hp1 이미지
        public Bitmap Init_Fish_Touch_Default_Hp1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp1_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp2 이미지
        public Bitmap Init_Fish_Touch_Default_Hp2_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp2_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp3 이미지
        public Bitmap Init_Fish_Touch_Default_Hp3_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp3_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp4 이미지
        public Bitmap Init_Fish_Touch_Default_Hp4_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp4_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 hp5 이미지
        public Bitmap Init_Fish_Touch_Default_Hp5_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp5_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //드래그로 잡는 물고기 이미지
        public Bitmap Init_Fish_Drag_Default_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_shake1_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //참돔 이미지
        public Bitmap  Init_Fish_Drag_Steelbream_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_steelbream_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //상어 이미지
        public Bitmap  Init_Fish_Shark_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_shark_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        //물고기 중간보스
        public Bitmap Init_Fish_Touch_Default_Middle_Hp1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp1small_boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Fish_Touch_Default_Middle_Hp2_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp2small_boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Fish_Touch_Default_Middle_Hp3_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp3small_boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Fish_Touch_Default_Middle_Hp4_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp4small_boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Fish_Touch_Default_Middle_Hp5_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp5small_boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //물고기 보스
        public Bitmap Init_Fish_Touch_Default_Boss_Hp1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp1boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Fish_Touch_Default_Boss_Hp2_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp2boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Fish_Touch_Default_Boss_Hp3_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp3boss_1 + num) ; //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Fish_Touch_Default_Boss_Hp4_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp4boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Fish_Touch_Default_Boss_Hp5_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_hp5boss_1 +num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        //달팽이, 물고기 사이즈좀줄여야함







        //오징어 이미지
        public Bitmap Init_Fish_Touch_Squid_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_squid_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //전기 뱀장어 이미지
        public Bitmap Init_Fish_Touch_Ell_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_ell_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Fish_Touch_Ell_Attack_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_ell_attack_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }


        //해파리 이미지
        public Bitmap Init_Fish_Trap_Jellyfish(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_jellyfish_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        //방해거북 이미지
        public Bitmap Init_Fish_Turtle(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_turtle_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        //몬스터 이미지
        public Bitmap Init_Fish_Monster_Left(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_monster_left_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Fish_Monster_Right(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_monster_right_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }


        //불가사리 이미지
        public Bitmap Init_Ground_Touch_Starfish_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_starfish_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        //곰 벌레 이미지
        public Bitmap Init_Ground_Touch_Bearbug_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_bearbug_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        public Bitmap Init_Ground_Touch_Stingray_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_stingray_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        //달팽이 이미지
        public Bitmap Init_Ground_Touch_Snail_Hp1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp1_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Hp2_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp2_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Hp3_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp3_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Hp4_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp4_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Hp5_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp5_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        public Bitmap Init_Ground_Touch_Snail_Middle_Hp1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp1small_boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Middle_Hp2_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp2small_boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Middle_Hp3_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp3small_boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Middle_Hp4_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp4small_boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Middle_Hp5_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp5small_boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        public Bitmap Init_Ground_Touch_Snail_Boss_Hp1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp1boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Boss_Hp2_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp2boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Boss_Hp3_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp3boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Boss_Hp4_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp4boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }
        public Bitmap Init_Ground_Touch_Snail_Boss_Hp5_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_snail_hp5boss_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }



        //꽃게 이미지
        public Bitmap Init_Ground_Drag_Crab_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_crab_1 + num);     //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        //가제 이미지
        public Bitmap Init_Ground_Drag_Lobsters_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_lobsters_1 + num);     //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }


        //조개 이미지
        public Bitmap Init_Ground_Drag_Clam_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_clam_1 + num);     //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        /**
         * 성게이미지
         */
        public Bitmap Init_Ground_Trap_Urchin_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_urchin_1 + num);     //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        /**
         * 성게 휴식기 이미지
         */
        public Bitmap Init_Ground_Trap_Urchin_Rest_Mode_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_urchin_rest_mode_1 + num);     //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }






        //이펙트 효과
        public Bitmap Init_Effect_Orange_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_orange_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Blue_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_blue_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Yellow_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_yellow_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Green_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_green_1 + num);
            return image.getBitmap();
        }



        public void Init_Effect_Drag_Pop_Image(Context context, int num){

            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop_damege1_1 + num);
            effect_Pop_damage1_Image[num] = image.getBitmap();
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop_damege2_1 + num);
            effect_Pop_damage2_Image[num] = image.getBitmap();
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop_damege3_1 + num);
            effect_Pop_damage3_Image[num] = image.getBitmap();
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop_damege4_1 + num);
            effect_Pop_damage4_Image[num] = image.getBitmap();
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop_damege5_1 + num);
            effect_Pop_damage5_Image[num] = image.getBitmap();
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop_damege6_1 + num);
            effect_Pop_damage6_Image[num] = image.getBitmap();
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop_damege7_1 + num);
            effect_Pop_damage7_Image[num] = image.getBitmap();
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop_damege8_1 + num);
            effect_Pop_damage8_Image[num] = image.getBitmap();
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop_damege9_1 + num);
            effect_Pop_damage9_Image[num] = image.getBitmap();
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop_damege10_1 + num);
            effect_Pop_damage10_Image[num] = image.getBitmap();




        }








        public Bitmap Init_Effect_Pop2_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop2_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Pop3_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop3_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Pop4_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop4_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Pop5_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop5_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Pop6_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop6_1 + num);
            return image.getBitmap();
        }
        public Bitmap Init_Effect_Black_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_black_1 + num);
            return image.getBitmap();
        }

        private int score_Text_Size; //기기마다 점수크기가 달라서





        //랜드마크 부서 질때마다 스테이지 진행
        boolean land_Mark_dist_1 = true;
        boolean land_Mark_dist_2 = false;
        public void stage_Jump(){
            stage_Call_Revolution();
            stage_Call_Revolution();
        }




        /**
         *  그리기 함수
         */
        //배경 이펙트 객체 1개로 처리하기 위해서, 각자 다르게 보여주기 위한 이미지 배열 컨트롤
        int effect_Background_Two_1_img_Control_Temp = 0;
        LandMark_Damage_View landMark_Damage_View;
        ArrayList<LandMark_Damage_View> landMark_Damage_View_List = new ArrayList<LandMark_Damage_View>();






        public synchronized void doDraw(Canvas canvas) {

            try{


                try{



                    sent_Item[0] = ruby;
                    sent_Item[1] = money;

                    //물고기1
                    sent_Item[2] = ftb1;
                    sent_Item[3] = ftb2;
                    sent_Item[4] = ftb3;
                    sent_Item[5] = ftb4;
                    sent_Item[6] = ftb5;
                    sent_Item[7] = ftb6;
                    sent_Item[8] = ftb7;
                    sent_Item[9] = ftb8;
                    sent_Item[10] = ftb9;
                    sent_Item[11] = ftb10;
                    sent_Item[12] = stb1;
                    sent_Item[13] = stb2;
                    sent_Item[14] = stb3;
                    sent_Item[15] = stb4;
                    sent_Item[16] = stb5;
                    sent_Item[17] = stb6;
                    sent_Item[18] = stb7;
                    sent_Item[19] = stb8;
                    sent_Item[20] = stb9;
                    sent_Item[21] = stb10;
                    sent_Item[22] = mtb1;
                    sent_Item[23] = mtb2;
                    sent_Item[24] = mtb3;
                    sent_Item[25] = mtb4;
                    sent_Item[26] = mtb5;
                    sent_Item[27] = mtb6;
                    sent_Item[28] = mtb7;
                    sent_Item[29] = mtb8;
                    sent_Item[30] = mtb9;
                    sent_Item[31] = mtb10;

                    //몬스터 설명창
                    sent_Item[32] = monster_Explain_Db[0];
                    sent_Item[33] = monster_Explain_Db[1];
                    sent_Item[34] = monster_Explain_Db[2];
                    sent_Item[35] = monster_Explain_Db[3];
                    sent_Item[36] = monster_Explain_Db[4];
                    sent_Item[37] = monster_Explain_Db[5];
                    sent_Item[38] = monster_Explain_Db[6];
                    sent_Item[39] = monster_Explain_Db[7];
                    sent_Item[40] = monster_Explain_Db[8];
                    sent_Item[41] = monster_Explain_Db[9];
                    sent_Item[42] = monster_Explain_Db[10];
                    sent_Item[43] = monster_Explain_Db[11];
                    sent_Item[44] = monster_Explain_Db[12];
                    sent_Item[45] = monster_Explain_Db[13];
                    sent_Item[46] = monster_Explain_Db[14];
                    sent_Item[47] = monster_Explain_Db[15];
                    sent_Item[48] = monster_Explain_Db[16];
                    sent_Item[49] = monster_Explain_Db[17];
                    sent_Item[50] = monster_Explain_Db[18];
                    sent_Item[51] = monster_Explain_Db[19];

                    sent_Item[52] = character_Explain_DB[0];
                    sent_Item[53] = character_Explain_DB[1];
                    sent_Item[54] = character_Explain_DB[2];
                    sent_Item[55] = character_Explain_DB[3];
                    sent_Item[56] = character_Explain_DB[4];
                    sent_Item[57] = character_Explain_DB[5];
                    sent_Item[58] = character_Explain_DB[6];
                    sent_Item[59] = character_Explain_DB[7];
                    sent_Item[60] = character_Explain_DB[8];
                    sent_Item[61] = character_Explain_DB[9];
                    sent_Item[62] = character_Explain_DB[10];
                    sent_Item[63] = character_Explain_DB[11];
                    sent_Item[64] = character_Explain_DB[12];
                    sent_Item[65] = character_Explain_DB[13];
                    sent_Item[66] = character_Explain_DB[14];
                    sent_Item[67] = character_Explain_DB[15];
                    sent_Item[68] = character_Explain_DB[16];
                    sent_Item[69] = character_Explain_DB[17];
                    sent_Item[70] = character_Explain_DB[18];
                    sent_Item[71] = character_Explain_DB[19];
                    sent_Item[72] = character_Explain_DB[20];
                    sent_Item[73] = character_Explain_DB[21];
                    sent_Item[74] = character_Explain_DB[22];
                    sent_Item[75] = character_Explain_DB[23];
                    sent_Item[76] = character_Explain_DB[24];
                    sent_Item[77] = character_Explain_DB[25];
                    sent_Item[78] = character_Explain_DB[26];
                    sent_Item[79] = character_Explain_DB[27];
                    sent_Item[80] = character_Explain_DB[28];
                    sent_Item[81] = character_Explain_DB[29];
                    sent_Item[82] = character_Explain_DB[30];
                    sent_Item[83] = character_Explain_DB[31];
                    sent_Item[84] = character_Explain_DB[32];
                    sent_Item[85] = character_Explain_DB[33];
                    sent_Item[86] = character_Explain_DB[34];


                    intent_Item.putExtra("item", sent_Item); //키 밸류       배열로 하나의 값만 보낸다.
                    ((Activity)_context).setResult(Activity.RESULT_OK, intent_Item);

                    editor.putString("realmoney", String.valueOf(money));
                    editor.commit();



                    if(revolution_Button_Activation ){

                        m_Run_False();
                        revolution_Button_Activation = false;
                        //            진화버튼 제거
                        revolution_Button = new GraphicButton(new Rect(0,
                                0,
                                0,
                                0));
                        revolution_Draw_Flag_Confirm = false;

//            revolution_Button_Background_Effect.recycle();
//            revolution_Button_Background_Effect = null;

                    }



                    //일시정지 버튼을 통해 눌린 일시정지는 화면이 어두워지지 않아야 한다.
                    if(pause_State){
                        pause_State = false;
                    }
                    home_Revolution_Flag = false;

                    /**
                     *  배경이미지
                     */
                    draw.draw_Bmp(canvas, backGroundImg, 0, 0);

                }catch (Exception e){
                    Log.e("@","배경 이미지!@#");
                }





                try{



                    //랜드마크 그리기
                    for(int i= ground_List.size() - 1; i>=0; i--){
                        if(ground_List.get(i) instanceof Land_Mark){


                            //랜드마크 대미지 그리기
                            if(land_Mark_Hit_Flag){
                                landMark_Damage_View = new LandMark_Damage_View(touchx,touchy);
                                landMark_Damage_View.set_Damage(character_Randmark_Damege_Temp);
                                landMark_Damage_View_List.add(landMark_Damage_View);
//                                Log.e("a",ground_List.get(i).get_Ground_Hp() + "");
                            }

                            if(land_Mark_dist_2){
                                stage_Jump();
//                                Log.e("@", "@ 점프");
                            }

                            if(land_Mark.get_Class_Num() == 0) {
                                if (ground_List.get(i).get_Ground_Hp() > 30000) {
                                    draw.draw_Bmp(canvas, land_Mark1_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    land_Mark_dist_1 = true;
                                    land_Mark_dist_2 = false;
                                } else if (ground_List.get(i).get_Ground_Hp() > 20000) {
                                    draw.draw_Bmp(canvas, land_Mark1_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());

                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }


                                } else if (ground_List.get(i).get_Ground_Hp() > 10000) {
                                    draw.draw_Bmp(canvas, land_Mark1_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());

                                    if(!land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = true;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                } else {

                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                    draw.draw_Bmp(canvas, land_Mark1_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                }
                                if (land_Mark_Hit_Flag) {

                                    draw.draw_Bmp(canvas, land_Mark_Drag_img[random.nextInt(3)],
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_X_Point() -convertPixelsToDp(30, _context),
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_Y_Point());


                                    draw.draw_Bmp(canvas, effect_Land_Mark_Pop1_img[random.nextInt(4)],
                                            ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark1_img[0].getWidth()) - 35,
                                            ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark1_img[0].getHeight()) - 35);



                                    land_Mark_Hit_Flag = false;
                                }
                            }else if(land_Mark.get_Class_Num() == 1){
                                if (ground_List.get(i).get_Ground_Hp() > 150000) {
                                    land_Mark_dist_1 = true;
                                    land_Mark_dist_2 = false;
                                    draw.draw_Bmp(canvas, land_Mark2_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                } else if (ground_List.get(i).get_Ground_Hp() > 100000) {
                                    draw.draw_Bmp(canvas, land_Mark2_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                } else if (ground_List.get(i).get_Ground_Hp() > 50000) {
                                    draw.draw_Bmp(canvas, land_Mark2_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(!land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = true;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                } else{
                                    draw.draw_Bmp(canvas, land_Mark2_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                }
                                if (land_Mark_Hit_Flag) {
                                    draw.draw_Bmp(canvas, land_Mark_Drag_img[random.nextInt(3)],
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_X_Point() -convertPixelsToDp(30, _context),
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_Y_Point());

                                    draw.draw_Bmp(canvas, effect_Land_Mark_Pop2_img[random.nextInt(4)],
                                            ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark2_img[0].getWidth()) - 35,
                                            ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark2_img[0].getHeight()) - 35);



                                    land_Mark_Hit_Flag = false;
                                }
                            }else if(land_Mark.get_Class_Num() == 2){
                                if (ground_List.get(i).get_Ground_Hp() > 700000) {
                                    land_Mark_dist_1 = true;
                                    land_Mark_dist_2 = false;
                                    draw.draw_Bmp(canvas, land_Mark3_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());

                                } else if (ground_List.get(i).get_Ground_Hp() > 400000) {
                                    draw.draw_Bmp(canvas, land_Mark3_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                } else if (ground_List.get(i).get_Ground_Hp() > 100000) {
                                    draw.draw_Bmp(canvas, land_Mark3_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(!land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = true;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                } else {
                                    draw.draw_Bmp(canvas, land_Mark3_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                }
                                if (land_Mark_Hit_Flag) {
                                    draw.draw_Bmp(canvas, land_Mark_Drag_img[random.nextInt(3)],
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_X_Point() -convertPixelsToDp(30, _context),
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_Y_Point());

                                    draw.draw_Bmp(canvas, effect_Land_Mark_Pop2_img[random.nextInt(4)],
                                            ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark3_img[0].getWidth()) - 35,
                                            ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark3_img[0].getHeight()) - 35);

                                    land_Mark_Hit_Flag = false;
                                }
                            }else if(land_Mark.get_Class_Num() == 3){
                                if (ground_List.get(i).get_Ground_Hp() > 1700000) {
                                    land_Mark_dist_1 = true;
                                    land_Mark_dist_2 = false;
                                    draw.draw_Bmp(canvas, land_Mark4_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                } else if (ground_List.get(i).get_Ground_Hp() > 1120000) {
                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                    draw.draw_Bmp(canvas, land_Mark4_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                } else if (ground_List.get(i).get_Ground_Hp() > 500000) {
                                    draw.draw_Bmp(canvas, land_Mark4_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(!land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = true;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                } else {
                                    draw.draw_Bmp(canvas, land_Mark4_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                }
                                if (land_Mark_Hit_Flag) {
                                    draw.draw_Bmp(canvas, land_Mark_Drag_img[random.nextInt(3)],
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_X_Point() -convertPixelsToDp(30, _context),
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_Y_Point());
                                    draw.draw_Bmp(canvas, effect_Land_Mark_Pop3_img[random.nextInt(4)],
                                            ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark4_img[0].getWidth()) - 35,
                                            ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark4_img[0].getHeight()) - 35);

                                    land_Mark_Hit_Flag = false;
                                }
                            }else if(land_Mark.get_Class_Num() == 4){
                                if (ground_List.get(i).get_Ground_Hp() > 4500000) {
                                    draw.draw_Bmp(canvas, land_Mark5_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    land_Mark_dist_1 = true;
                                    land_Mark_dist_2 = false;
                                } else if (ground_List.get(i).get_Ground_Hp() > 2800000) {
                                    draw.draw_Bmp(canvas, land_Mark5_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                } else if (ground_List.get(i).get_Ground_Hp() > 1750000) {
                                    draw.draw_Bmp(canvas, land_Mark5_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(!land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = true;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                } else  {
                                    draw.draw_Bmp(canvas, land_Mark5_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                }
                                if (land_Mark_Hit_Flag) {
                                    draw.draw_Bmp(canvas, land_Mark_Drag_img[random.nextInt(3)],
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_X_Point() -convertPixelsToDp(30, _context),
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_Y_Point());
                                    draw.draw_Bmp(canvas, effect_Land_Mark_Pop2_img[random.nextInt(4)],
                                            ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark5_img[0].getWidth()) - 35,
                                            ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark5_img[0].getHeight()) - 35);


                                    land_Mark_Hit_Flag = false;
                                }

                            }else if(land_Mark.get_Class_Num() == 5){
                                if (ground_List.get(i).get_Ground_Hp() > 100000000.0) {
                                    draw.draw_Bmp(canvas, land_Mark6_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    land_Mark_dist_1 = true;
                                    land_Mark_dist_2 = false;
                                } else if (ground_List.get(i).get_Ground_Hp() > 65000000.0) {
                                    draw.draw_Bmp(canvas, land_Mark6_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                } else if (ground_List.get(i).get_Ground_Hp() > 30000000.0) {
                                    draw.draw_Bmp(canvas, land_Mark6_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(!land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = true;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                } else  {
                                    draw.draw_Bmp(canvas, land_Mark6_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    if(land_Mark_dist_1){
                                        land_Mark_dist_2 = true;
                                        land_Mark_dist_1 = false;
                                    }else {
                                        land_Mark_dist_2 = false;
                                    }
                                }
                                if (land_Mark_Hit_Flag) {
                                    draw.draw_Bmp(canvas, land_Mark_Drag_img[random.nextInt(3)],
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_X_Point() -convertPixelsToDp(30, _context),
                                            landMark_Damage_View_List.get(landMark_Damage_View_List.size() - 1).get_Y_Point());
                                    draw.draw_Bmp(canvas, effect_Land_Mark_Pop3_img[random.nextInt(4)],
                                            ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark6_img[0].getWidth()) - 35,
                                            ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark6_img[0].getHeight()) - 35);


                                    land_Mark_Hit_Flag = false;
                                }

                            }else if(land_Mark.get_Class_Num() == 6){
//                                draw.draw_Bmp(canvas, land_Mark6_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                land_Mark_dist_1 = true;
                                land_Mark_dist_2 = false;
                                land_Mark_Hit_Flag = false;
                            }

                            try{
                                //딱따구리
                                if(effect_Bird.motion()){

                                    landMark_Damage_View = new LandMark_Damage_View((int)effect_Bird.get_X_Point(),(int)effect_Bird.get_Y_Point());
                                    tempInt = 1+random.nextInt((int)character_Randmark_Damege);
                                    landMark_Damage_View.set_Damage(tempInt);
                                    landMark_Damage_View_List.add(landMark_Damage_View);
                                    ground_List.get(i).set_Ground_Hp_Minus(tempInt);
                                    main_Character.set_Character_Experience(tempInt);
                                    draw.draw_Bmp(canvas, land_Mark_Bird_img[1],
                                            effect_Bird.get_X_Point(),
                                            effect_Bird.get_Y_Point());
                                    Score += tempInt;
                                    money += tempInt;
                                }else {
                                    draw.draw_Bmp(canvas, land_Mark_Bird_img[0],
                                            effect_Bird.get_X_Point(),
                                            effect_Bird.get_Y_Point());
                                }

                            }catch (Exception e){
                                Log.e("@","딱따구리");
                            }







                            break;
                        }
                    }


                    //랜드 마크 대미지 보여주기
                    image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.effect_drag_landmark_1);

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","랜드마크 그리기 부분");
                }










                try{

                    for(int i=0; i<landMark_Damage_View_List.size(); i++){

                        score_Text_Size = convertPixelsToDp(15 - landMark_Damage_View_List.get(i).get_Live_Time(), _context);
                        paint_Temp.setTextSize(score_Text_Size);

                        paint_Temp.setStyle(Paint.Style.STROKE);
                        paint_Temp.setStrokeWidth(convertPixelsToDp(3, _context));

                        paint_Temp.setColor(Color.BLACK);
                        canvas.drawText(landMark_Damage_View_List.get(i).get_Damage()+"", landMark_Damage_View_List.get(i).get_X_Point(),landMark_Damage_View_List.get(i).get_Y_Point(), paint_Temp);

                        paint_Temp.setStyle(Paint.Style.FILL);
                        paint_Temp.setStrokeWidth(convertPixelsToDp(1, _context));


                        paint_Temp.setColor(Color.YELLOW);

                        if(landMark_Damage_View_List.get(i).get_Damage() > (character_Randmark_Damege - landMark_Damage_View_List.get(i).get_Damage()/10) && character_Randmark_Damege > 11) {
                            paint_Temp.setColor(Color.RED);
                        }

                        canvas.drawText(landMark_Damage_View_List.get(i).get_Damage()+"", landMark_Damage_View_List.get(i).get_X_Point(),landMark_Damage_View_List.get(i).get_Y_Point(), paint_Temp);




                        landMark_Damage_View_List.get(i).set_Text_Move();


                        if(landMark_Damage_View_List.get(i).get_Live_Flag()){
                            //                landMark_Damage_View_List.remove(i);
                            landMark_Damage_View_List.get(i).set_Remove();
                        }
                    }


                    //지우기 방법
                    for(int i=landMark_Damage_View_List.size()-1; i>=0; i--){
                        if(landMark_Damage_View_List.get(i).get_Remove()){
                            landMark_Damage_View_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","랜드마크 대미지 부분");
                }


                try{

                    for(int i = ground_List.size() - 1; i >= 0; i--){
                        if(ground_List.get(i) instanceof Ground_Drag_Clam && ground_List.get(i).get_Visible_Ground_Flag()){
                            if (ground_List.get(i) instanceof Ground_Drag_Clam) {
                                //조개 그리기

                                if(ground_List.get(i).get_Ground_Hp() > 3500) {
                                    draw.draw_Bmp(canvas, ground_Drag_Clam_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                }else if(ground_List.get(i).get_Ground_Hp() > 2200) {
                                    draw.draw_Bmp(canvas, ground_Drag_Clam_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                }else if(ground_List.get(i).get_Ground_Hp() > 1000) {
                                    draw.draw_Bmp(canvas, ground_Drag_Clam_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                }else if(ground_List.get(i).get_Ground_Hp() > 0) {
                                    draw.draw_Bmp(canvas, ground_Drag_Clam_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                }

                                /**
                                 * 조개 터치 이펙트
                                 */
                                tempInt = random.nextInt(5);
                                if(character_Damege == 1) {
                                    pop_Temp_img = effect_Pop_damage1_Image[tempInt];
                                }else if(character_Damege == 2) {
                                    pop_Temp_img = effect_Pop_damage2_Image[tempInt];
                                }else if(character_Damege == 3) {
                                    pop_Temp_img = effect_Pop_damage3_Image[tempInt];
                                }else if(character_Damege == 4) {
                                    pop_Temp_img = effect_Pop_damage4_Image[tempInt];
                                }else if(character_Damege == 5) {
                                    pop_Temp_img = effect_Pop_damage5_Image[tempInt];
                                }else if(character_Damege == 6) {
                                    pop_Temp_img = effect_Pop_damage6_Image[tempInt];
                                }else if(character_Damege == 7) {
                                    pop_Temp_img = effect_Pop_damage7_Image[tempInt];
                                }else if(character_Damege == 8) {
                                    pop_Temp_img = effect_Pop_damage8_Image[tempInt];
                                }else if(character_Damege == 9) {
                                    pop_Temp_img = effect_Pop_damage9_Image[tempInt];
                                }else if(character_Damege >= 10) {
                                    pop_Temp_img = effect_Pop_damage10_Image[tempInt];
                                }

                                if(i == ground_Remove_Temp && clam_Ground_Hit_Flag){
                                    draw.draw_Bmp(canvas, pop_Temp_img,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(ground_Drag_Crab_img[0].getWidth()) ,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(ground_Drag_Crab_img[0].getHeight()));
                                    ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);

                                    if(ground_List.get(ground_Remove_Temp).get_Ground_Hp() <= 0){
                                        ruby += 1;
                                    }

                                    clam_Ground_Hit_Flag = false;

                                    break;
                                }



                            }
                        }

                    }
                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","조개 그리기 부분");
                }



                /**
                 * 배경 이펙트
                 */
                try{

                    draw.draw_Bmp(canvas,
                            draw.rotate_Image(effect_Background_One_1_img[background_Effect_One.get_Draw_Background_Effect_Status()],background_Effect_One.get_Background_Angle())
                            , background_Effect_One.get_Background_Point_X(), background_Effect_One.get_Background_Point_Y());

                    //물방울 이펙트
                    draw.draw_Bmp(canvas, effect_Background_Two_1_img[background_Effect_Two.get_Draw_Background_Effect_Status()],
                            background_Effect_Location.get(0).x,
                            background_Effect_Location.get(0).y);

                    //배경 이펙트 객체 1개로 처리하기 위해서, 각자 다르게 보여주기 위한 이미지 배열 컨트롤
                    effect_Background_Two_1_img_Control_Temp = 0;
                    if(background_Effect_Two.get_Draw_Background_Effect_Status() < 4){
                        effect_Background_Two_1_img_Control_Temp = background_Effect_Two.get_Draw_Background_Effect_Status() + 4;
                    }else {
                        effect_Background_Two_1_img_Control_Temp = background_Effect_Two.get_Draw_Background_Effect_Status() - 4;
                    }

                    draw.draw_Bmp(canvas, effect_Background_Two_1_img[effect_Background_Two_1_img_Control_Temp],
                            background_Effect_Location.get(4).x,
                            background_Effect_Location.get(4).y);
                    //~~~ 물방울 이펙트



                    //미역 이펙트
                    draw.draw_Bmp(canvas, effect_Background_Seaweed_img[background_Effect_Two.get_Draw_Background_Effect_Status()],
                            background_Effect_Location.get(1).x,
                            background_Effect_Location.get(1).y);

                    draw.draw_Bmp(canvas, effect_Background_Seaweed_img[effect_Background_Two_1_img_Control_Temp],
                            background_Effect_Location.get(3).x,
                            background_Effect_Location.get(3).y);

                    //~~~ 미역이펙트

                    //말미잘 이펙트
                    draw.draw_Bmp(canvas, effect_background_Seaanemone_img[background_Effect_Two.get_Draw_Background_Effect_Status_2()],
                            background_Effect_Location.get(2).x,
                            background_Effect_Location.get(2).y);

                    draw.draw_Bmp(canvas, effect_background_Seaanemone_img[background_Effect_Two.get_Draw_Background_Effect_Status_3()],
                            background_Effect_Location.get(5).x,
                            background_Effect_Location.get(5).y);

                    //~~ 말미잘 이펙트


                    //돌 그리기

                    draw.draw_Bmp(canvas, effect_background_Rock[0],  background_Effect_Location.get(1).x - 10, background_Effect_Location.get(1).y + 100);
                    draw.draw_Bmp(canvas, effect_background_Rock[1],  background_Effect_Location.get(2).x + 10, background_Effect_Location.get(2).y + 80);
                    draw.draw_Bmp(canvas, effect_background_Rock[2],  background_Effect_Location.get(3).x -10, background_Effect_Location.get(3).y+ 80);
                    draw.draw_Bmp(canvas, effect_background_Rock[3],  background_Effect_Location.get(4).x + 30, background_Effect_Location.get(4).y + 70);

                    draw.draw_Bmp(canvas, effect_background_Rock[4], background_Effect_Location.get(5).x - 5, background_Effect_Location.get(5).y + 70);
                    draw.draw_Bmp(canvas, effect_background_Rock[5],  background_Effect_Location.get(0).x + 15, background_Effect_Location.get(0).y + 100);
                    draw.draw_Bmp(canvas, effect_background_Rock[6],  background_Effect_Location.get(5).x + 25, background_Effect_Location.get(5).y + 70);
                    draw.draw_Bmp(canvas, effect_background_Rock[7],  background_Effect_Location.get(4).x + 50, background_Effect_Location.get(4).y + 70);


                    //~ 돌 그리기



                    //배경 상어 이펙트
                    draw.draw_Bmp(canvas,
                            draw.rotate_Image(effect_Background_Shark_img[background_Effect_Shark.get_Draw_Background_Effect_Status()],background_Effect_Shark.get_Background_Angle())
                            , background_Effect_Shark.get_Background_Point_X(), background_Effect_Shark.get_Background_Point_Y());



                }catch (Exception e){
                    Log.e("a","배경 이펙트");
                    Log.e("a", e.getMessage());
                    Log.e("a", e.toString());
                }


                try {

                    draw_Main_Character_Draw();  //메인 캐릭터 기본 이미지
                    main_Character.character_Move();    //메인 캐릭터 움직임 효과


                    // 피 효과 그리기
                    if(main_Character.get_Character_Hit() >= 0){

                        draw.draw_Bmp(canvas, main_Character_Blood_Img[main_Character.get_Character_Hit()],  main_Character.get_Main_Character_Point_X() + (main_Character_Img[0].getWidth()/4) + random.nextInt((main_Character_Img[0].getWidth()/2)) , main_Character.get_Main_Character_Point_Y() + random.nextInt((main_Character_Img[0].getHeight()/2)));
                    }

                }catch (Exception e){
                    Log.e("메인 캐릭터", "메인 캐릭터");
                }



                try{

                    /**
                     * 그라운드 그리기 (달팽이) 가장 아랫부분에 깔려야 하기 때문에 가장 위쪽에서 그림
                     */
//        Log.e("a",ground_List.size() + "");
                    for(int i=0 ; i < ground_List.size(); i++) {


                        if(ground_List.get(i).get_Ground_Hp() > 0 && ground_List.get(i).get_Visible_Ground_Flag()){




                            //달팽이 움직임
                            if (ground_List.get(i) instanceof Ground_Touch_Snail) {

                                if(ground_List.get(i).get_Class_Num() == 0){

                                    if (ground_List.get(i).get_Ground_Hp() == 5) {


                                        draw.draw_Bmp(canvas, ground_Touch_Snail_Hp5_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else if (ground_List.get(i).get_Ground_Hp() == 4) {
                                        draw.draw_Bmp(canvas, ground_Touch_Snail_Hp4_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else if (ground_List.get(i).get_Ground_Hp() == 3) {
                                        draw.draw_Bmp(canvas, ground_Touch_Snail_Hp3_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else if (ground_List.get(i).get_Ground_Hp() == 2) {
                                        draw.draw_Bmp(canvas, ground_Touch_Snail_Hp2_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else{
                                        draw.draw_Bmp(canvas, ground_Touch_Snail_Hp1_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    }

                                }else if(ground_List.get(i).get_Class_Num() == 1){  //중간 보스

                                    if (ground_List.get(i).get_Ground_Hp() == 5) {
                                        draw.draw_Bmp(canvas, ground_snail_Middle_Hp5_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else if (ground_List.get(i).get_Ground_Hp() == 4) {
                                        draw.draw_Bmp(canvas, ground_snail_Middle_Hp4_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else if (ground_List.get(i).get_Ground_Hp() == 3) {
                                        draw.draw_Bmp(canvas, ground_snail_Middle_Hp3_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else if (ground_List.get(i).get_Ground_Hp() == 2) {
                                        draw.draw_Bmp(canvas, ground_snail_Middle_Hp2_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else{
                                        draw.draw_Bmp(canvas, ground_Snail_Middle_Hp1_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    }

                                }else if(ground_List.get(i).get_Class_Num() == 2){  //중간 보스

                                    if (ground_List.get(i).get_Ground_Hp() == 5) {

                                        draw.draw_Bmp(canvas, ground_snail_Boss_Hp5_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else if (ground_List.get(i).get_Ground_Hp() == 4) {
                                        draw.draw_Bmp(canvas, ground_snail_Boss_Hp4_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else if (ground_List.get(i).get_Ground_Hp() == 3) {
                                        draw.draw_Bmp(canvas, ground_snail_Boss_Hp3_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else if (ground_List.get(i).get_Ground_Hp() == 2) {
                                        draw.draw_Bmp(canvas, ground_snail_Boss_Hp2_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    } else{
                                        draw.draw_Bmp(canvas, ground_snail_Boss_Hp1_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                    }

                                }



                                /**
                                 * 달팽이 터치 이펙트
                                 */
                                if( i== ground_Remove_Temp && snail_Ground_Hit_Flag){
                                    draw.draw_Bmp(canvas, effect_Temp,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(random.nextInt(25), _context),
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(random.nextInt(25), _context));
                                    ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();
                                    snail_Ground_Hit_Flag = false;
                                }


                            }


                            else if (ground_List.get(i) instanceof Ground_Touch_Hermit) {
                                //소라게

                                if (ground_List.get(i).get_Ground_Hp() == 5) {
                                    draw.draw_Bmp(canvas, ground_Touch_Hermit_Hp5_img[((Ground_Touch_Hermit) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                } else if (ground_List.get(i).get_Ground_Hp() == 4) {
                                    draw.draw_Bmp(canvas, ground_Touch_Hermit_Hp4_img[((Ground_Touch_Hermit) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                } else if (ground_List.get(i).get_Ground_Hp() == 3) {
                                    draw.draw_Bmp(canvas, ground_Touch_Hermit_Hp3_img[((Ground_Touch_Hermit) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                } else if (ground_List.get(i).get_Ground_Hp() == 2) {
                                    draw.draw_Bmp(canvas, ground_Touch_Hermit_Hp2_img[((Ground_Touch_Hermit) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                } else{
                                    draw.draw_Bmp(canvas, ground_Touch_Hermit_Hp1_img[((Ground_Touch_Hermit) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                }


                                /**
                                 * 소라게 터치 이펙트
                                 */
                                if( i== ground_Remove_Temp && hermit_Ground_Hit_Flag){
                                    draw.draw_Bmp(canvas, effect_Temp,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - 35,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - 35);

                                    //클릭된 소라게의체력을 깍는다.
                                    if(!((Ground_Touch_Hermit)ground_List.get(ground_Remove_Temp)).get_Immortal_Mode()) {
                                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();
                                    }

                                    hermit_Ground_Hit_Flag = false;
                                }


                            }else if(ground_List.get(i) instanceof Ground_Touch_Starfish){
                                //불가사리 그리기



                                temp_Ground = draw.rotate_Image(ground_Touch_Starfish_img[ground_List.get(i).get_Draw_Ground_Status()], ((Ground_Touch_Starfish)ground_List.get(i)).get_Angle());
                                draw.draw_Bmp(canvas, temp_Ground,  ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());

                                /**
                                 * 불가사리 터치 이펙트
                                 */
                                if( i== ground_Remove_Temp && starfish_Ground_Hit_Flag){
                                    draw.draw_Bmp(canvas, effect_Temp,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_X(),
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_Y());
                                    ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();
                                    starfish_Ground_Hit_Flag = false;
                                }


                            }else if(ground_List.get(i) instanceof Ground_Touch_Bearbug){
                                //곰 벌레

                                draw.draw_Bmp(canvas, ground_Touch_Bearbug_img[ground_List.get(i).get_Draw_Ground_Status()],  ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());

                                /**
                                 * 곰 벌레 터치 이펙트
                                 */
                                if( i== ground_Remove_Temp && bearbug_Ground_Hit_Flag){
                                    draw.draw_Bmp(canvas, effect_Temp,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_X(),
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_Y());

                                    //클릭된 곤벌레의체력을 깍는다.
                                    if(ground_List.get(i).get_Ground_Point_Y() < 0) {
                                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(500000);
                                    }

                                    bearbug_Ground_Hit_Flag = false;
                                }

                            }else if(ground_List.get(i) instanceof Ground_Touch_Stingray){
                                //가오리
                                if(((Ground_Touch_Stingray)ground_List.get(i)).get_Cloaking_State()) {
                                    draw.draw_Bmp(canvas, ground_Touch_Stingray_img[4], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                }else{
                                    draw.draw_Bmp(canvas, ground_Touch_Stingray_img[ground_List.get(i).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                }

                                /**
                                 * 가오리 터치 이펙트
                                 */
                                if( i== ground_Remove_Temp && stingray_Ground_Hit_Flag){
                                    draw.draw_Bmp(canvas, effect_Temp,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_X(),
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_Y());

                                    ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();

                                    stingray_Ground_Hit_Flag = false;
                                }


                            }else if(ground_List.get(i) instanceof Ground_Drag_Lobsters){
                                //가제 그리기
                                draw.draw_Bmp(canvas, ground_Drag_Lobsters_img[ground_List.get(i).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());

                                /**
                                 * 가제 터치 이펙트
                                 */
                                if( i== ground_Remove_Temp && lobsters_Ground_Hit_Flag){

                                    if(!((Ground_Drag_Lobsters)ground_List.get(i)).get_Mode()){

                                        draw.draw_Bmp(canvas, effect_Temp,
                                                ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(convertPixelsToDp(30, _context)),
                                                ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(convertPixelsToDp(15, _context)));
                                    }


                                    lobsters_Ground_Hit_Flag = false;
                                }




                            }

                            else if(ground_List.get(i) instanceof Ground_Touch_Crocodile){
                                //악어 그리기

                                draw.draw_Bmp(canvas, shadow_img[6],  ground_List.get(i).get_Ground_Point_X()   + convertPixelsToDp(52, _context) , ground_List.get(i).get_Ground_Point_Y()   + convertPixelsToDp(42, _context)  );
                                if(((Ground_Touch_Crocodile)ground_List.get(i)).get_Direction() == 1) {


                                    draw.draw_Bmp(canvas, ground_Touch_Crocodile_img[ground_List.get(i).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                }else{

                                    temp_Fish = draw.rotate_Image(ground_Touch_Crocodile_img[ground_List.get(i).get_Draw_Ground_Status()], 180);
                                    draw.draw_Bmp(canvas, temp_Fish, ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                }


                            }

                            else if(ground_List.get(i) instanceof Ground_Drag_Wave){ //드래그 파도 그리기
                                draw.draw_Bmp(canvas, ground_Drag_Wave_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());

//                     pop_Temp_img = effect_Pop_damage1_Image[tempInt];
                                pop_Temp_img = effect_Wave_Pop_img;
                                if(i == ground_Remove_Temp && wave_Ground_Hit_Flag){
                                    draw.draw_Bmp(canvas, pop_Temp_img,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(ground_Drag_Wave_img[1].getWidth())-35 ,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(ground_Drag_Wave_img[1].getHeight())-35);
                                    wave_Ground_Hit_Flag = false;
                                }

                            }


                            //꽃게 또는 조개
                            else if (ground_List.get(i) instanceof Ground_Drag_Crab) {



                                draw.draw_Bmp(canvas, shadow_img[5],  ground_List.get(i).get_Ground_Point_X()  + convertPixelsToDp(4, _context), ground_List.get(i).get_Ground_Point_Y()  + convertPixelsToDp(19, _context) );
                                draw.draw_Bmp(canvas, ground_Drag_Crab_img[((Ground_Drag_Crab) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());

                                /**
                                 * 꽃게 터치 이펙트
                                 */
                                tempInt = random.nextInt(5);
                                if(character_Damege == 1) {
                                    pop_Temp_img = effect_Pop_damage1_Image[tempInt];
                                }else if(character_Damege == 2) {
                                    pop_Temp_img = effect_Pop_damage2_Image[tempInt];
                                }else if(character_Damege == 3) {
                                    pop_Temp_img = effect_Pop_damage3_Image[tempInt];
                                }else if(character_Damege == 4) {
                                    pop_Temp_img = effect_Pop_damage4_Image[tempInt];
                                }else if(character_Damege == 5) {
                                    pop_Temp_img = effect_Pop_damage5_Image[tempInt];
                                }else if(character_Damege == 6) {
                                    pop_Temp_img = effect_Pop_damage6_Image[tempInt];
                                }else if(character_Damege == 7) {
                                    pop_Temp_img = effect_Pop_damage7_Image[tempInt];
                                }else if(character_Damege == 8) {
                                    pop_Temp_img = effect_Pop_damage8_Image[tempInt];
                                }else if(character_Damege == 9) {
                                    pop_Temp_img = effect_Pop_damage9_Image[tempInt];
                                }else if(character_Damege >= 10) {
                                    pop_Temp_img = effect_Pop_damage10_Image[tempInt];
                                }

                                if(i == ground_Remove_Temp && crap_Ground_Hit_Flag){
                                    draw.draw_Bmp(canvas, pop_Temp_img,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(ground_Drag_Crab_img[0].getWidth())-35 ,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(ground_Drag_Crab_img[0].getHeight())-35);

                                    ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);
                                    crap_Ground_Hit_Flag = false;
                                }



                            }


                            //성게 움직임
                            else if (ground_List.get(i) instanceof Ground_Trap_Urchin) {

                                //성게가 공격 모드인가 아닌가.
                                if (((Ground_Trap_Urchin) ground_List.get(i)).get_Urchin_Attack_Mode()) {
                                    //공격모드 일때
                                    draw.draw_Bmp(canvas,
                                            draw.rotate_Image(ground_Trap_Urchin_img[ground_List.get(i).get_Draw_Ground_Status()], ((Ground_Trap_Urchin) ground_List.get(i)).get_Urchin_Angle()),
                                            ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                } else {
                                    //성게가 공격 모드가 아닐때.
                                    draw.draw_Bmp(canvas,
                                            draw.rotate_Image(ground_Trap_Urchin_Rest_Mode_img[ground_List.get(i).get_Draw_Ground_Status()], ((Ground_Trap_Urchin) ground_List.get(i)).get_Urchin_Angle()),
                                            ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                                }

                                /**
                                 * 성게 터치 이펙트
                                 */
                                if(i == ground_Remove_Temp && seaurchin_Ground_Hit_Flag){
                                    draw.draw_Bmp(canvas, effect_Temp,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(ground_Touch_Snail_Hp1_img[0].getWidth()) - 35,
                                            ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(ground_Touch_Snail_Hp1_img[0].getHeight()) - 35);

                                    //성게가 공격 모드 일때, 게딱지가 아닐때
                                    if(((Ground_Trap_Urchin)ground_List.get(ground_Remove_Temp)).get_Urchin_Attack_Mode() && !(main_Character instanceof Main_Character_Shellfish_Tear3)  ){
//                                        Log.e("@"," 성게1 : " + skill_Shellfish_Extract_Nomar[1]);
//                                        Log.e("@"," 성게2 : " + skill_Shellfish_Extract_Nomar[2]);
//                                        Log.e("@"," 성게3 : " + skill_Shellfish_Extract_Nomar[3]);
                                        if(!(skill_Shellfish_Extract_Nomar[2])) {
                                            if (random.nextInt(100) > urchinresistance) {
                                                //get_Urchin_Attack_Mode()
                                                main_Character.set_Hp_Minus();
                                                gameActivity.set_Vibrator();
//                                                Log.e("@", "@3" + ground_List.get(i).getClass());
                                            }
                                        }else {
                                            //꽃게를 추출 했을때
                                            if(random.nextInt(100) < st3 + 1){
                                                if (random.nextInt(100) > urchinresistance) {
                                                    //get_Urchin_Attack_Mode()
                                                    main_Character.set_Hp_Minus();
                                                    gameActivity.set_Vibrator();
//                                                Log.e("@", "@3" + ground_List.get(i).getClass());
                                                }
                                            }

                                        }
                                    }

                                    ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();  //성게 삭제,
                                    seaurchin_Ground_Hit_Flag = false;
//                       ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();  //성게 삭제,
                                }


                            }


                            //속도가 0인 객체 위에 달팽이 [슬로우 이팩트를 그린다.]
                            if(ground_List.get(i).get_Ground_Speed() <= 0 && ((!(ground_List.get(i) instanceof Land_Mark)))){

                                if(!(ground_List.get(i) instanceof Ground_Drag_Wave)) {
                                    draw.draw_Bmp(canvas, effect_Slow_img[0],
                                            ground_List.get(i).get_Ground_Point_X(),
                                            ground_List.get(i).get_Ground_Point_Y() - convertPixelsToDp(13, _context));

                                    draw.draw_Bmp(canvas, effect_Slow_img[ground_List.get(i).get_Slow_Effect()],
                                            ground_List.get(i).get_Ground_Point_X(),
                                            ground_List.get(i).get_Ground_Point_Y());
                                }
                            }

                            //독 상태 이상에 걸린 객체에 포이즌 마크를 표시한다.
                            if(ground_List.get(i).get_Status_Poison()){

                                ground_List.get(i).set_Status_Poison_AttacK();


                                pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ground_Snail_Middle_Hp1_img[0].getWidth() * 2, ground_Snail_Middle_Hp1_img[0].getHeight(), false);


                                if(ground_List.get(i) instanceof Ground_Touch_Snail){
                                    if(ground_List.get(i).get_Class_Num() == 2) {
                                        pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ground_snail_Boss_Hp1_img[0].getWidth() * 2, ground_snail_Boss_Hp1_img[0].getHeight(),  false);
                                    }else if(ground_List.get(i).get_Class_Num() == 1){
                                        pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ground_Snail_Middle_Hp1_img[0].getWidth() * 2, ground_Snail_Middle_Hp1_img[0].getHeight(),  false);
                                    }else {
                                        pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ground_Touch_Snail_Hp1_img[0].getWidth() * 2, ground_Touch_Snail_Hp1_img[0].getHeight(),  false);
                                    }
                                }else if(ground_List.get(i) instanceof Ground_Drag_Crab){
                                    pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ground_Drag_Crab_img[0].getWidth() * 3 / 2, ground_Drag_Crab_img[0].getHeight() * 2, false);
                                }else if(ground_List.get(i) instanceof Ground_Drag_Lobsters){
                                    pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ground_Drag_Lobsters_img[0].getWidth() * 3 / 2, ground_Drag_Lobsters_img[0].getHeight(), false);
                                }else if(ground_List.get(i) instanceof Ground_Touch_Hermit){
                                    pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ground_Touch_Hermit_Hp1_img[0].getWidth() * 2, ground_Touch_Hermit_Hp1_img[0].getHeight(), false);
                                }else if(ground_List.get(i) instanceof Ground_Touch_Bearbug){
                                    pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ground_Touch_Bearbug_img[0].getWidth() * 2, ground_Touch_Bearbug_img[0].getHeight(), false);
                                }else if(ground_List.get(i) instanceof Ground_Touch_Starfish){
                                    pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ground_Touch_Starfish_img[0].getWidth() * 2, ground_Touch_Starfish_img[0].getHeight(), false);
                                }else if(ground_List.get(i) instanceof Ground_Touch_Stingray){
                                    pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ground_Touch_Stingray_img[0].getWidth() * 2, ground_Touch_Stingray_img[0].getHeight(), false);
                                }

                                if(ground_List.get(i) instanceof Ground_Drag_Crab) {
                                     draw.draw_Bmp(canvas, pop_Temp_img,
                                     ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(12, _context),
                                     ground_List.get(i).get_Ground_Point_Y() - convertPixelsToDp(12, _context));
                                 }else {
                                    if(!(ground_List.get(i) instanceof Land_Mark)) {
                                        draw.draw_Bmp(canvas, pop_Temp_img,
                                                ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(12, _context),
                                                ground_List.get(i).get_Ground_Point_Y());
                                    }
                                 }

                            }



                        }


                    }


                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","그라운드 그리기 부분");
                }

                try{

                    /**
                     * 물고기 그리기
                     */


                    for(int i=fish_List.size() - 1; i >= 0 ; i--) {
                        if(fish_List.get(i).get_Fish_Hp() > 0 && (fish_List.get(i)).get_Visible_Fish_Flag()){



                            if(fish_List.get(i) instanceof Fish_Trap_Turtle){
                                /**
                                 *  방해거북 그리기
                                 */
                                temp_Shadow_img = draw.rotate_Image(shadow_img[7], 90 - fish_List.get(i).get_Fish_Angle());
                                draw.draw_Bmp(canvas, temp_Shadow_img, fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(15, _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(30, _context));

                                temp_Fish = draw.rotate_Image(fish_Turtle_img[fish_List.get(i).get_Draw_Fish_Status()], 90 - fish_List.get(i).get_Fish_Angle());
                                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());



                                //이팩트 그리기
                                if(turtle_Fish_Hit_Flag){
                                    pop_Temp_img = effect_Pop_Turtle;
                                    draw.draw_Bmp(canvas, pop_Temp_img, fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(random.nextInt(60), _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(random.nextInt(60), _context));
                                    turtle_Fish_Hit_Flag = false;
                                }


                            }

                            else if (fish_List.get(i) instanceof Fish_Touch_Default) {

                                /**
                                 * 물고기 설명 그림
                                 */

                                /**
                                 *  이미지 회전
                                 */
                                if(fish_List.get(i).get_Class_Num() == 0) {
                                    if (fish_List.get(i).get_Fish_Hp() == 1) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp1_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else if (fish_List.get(i).get_Fish_Hp() == 2) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp2_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else if (fish_List.get(i).get_Fish_Hp() == 3) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp3_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else if (fish_List.get(i).get_Fish_Hp() == 4) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp4_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp5_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    }


                                    temp_Shadow_img = draw.rotate_Image(shadow_img[0], -fish_List.get(i).get_Fish_Angle());

                                }else if(fish_List.get(i).get_Class_Num() == 1){   //중간 보스

                                    if (fish_List.get(i).get_Fish_Hp() >= 5) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Middle_Hp1_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else if (fish_List.get(i).get_Fish_Hp() >= 4) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Middle_Hp2_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else if (fish_List.get(i).get_Fish_Hp() >= 3) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Middle_Hp3_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else if (fish_List.get(i).get_Fish_Hp() >= 2) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Middle_Hp4_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Middle_Hp5_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    }

                                    temp_Shadow_img = draw.rotate_Image(shadow_img[1], -fish_List.get(i).get_Fish_Angle());

                                }else if(fish_List.get(i).get_Class_Num() == 2){   //보스


                                    if (fish_List.get(i).get_Fish_Hp() >= 5) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Boss_Hp5_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else if (fish_List.get(i).get_Fish_Hp() >= 4) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Boss_Hp4_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else if (fish_List.get(i).get_Fish_Hp() >= 3) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Boss_Hp3_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else if (fish_List.get(i).get_Fish_Hp() >= 2) {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Boss_Hp2_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    } else {
                                        temp_Fish = draw.rotate_Image(fish_Touch_Default_Boss_Hp1_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                    }

                                    temp_Shadow_img = draw.rotate_Image(shadow_img[2], -fish_List.get(i).get_Fish_Angle());
                                }


                                //이미지 크기 변경
                                //                temp_Fish = Bitmap.createScaledBitmap(temp_Fish,fish_Drag_Default_img[0].getWidth(),fish_Drag_Default_img[0].getHeight(),true);


                                //그림자 그리기
                                draw.draw_Bmp(canvas, temp_Shadow_img, fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(1, _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(20, _context));

//                        x_Dm_T = (temp_Fish.getWidth() * dm.widthPixels)/convertPixelsToDp(670, _context);
//                        y_Dm_T = (temp_Fish.getHeight() * dm.heightPixels)/convertPixelsToDp(60, _context);
//                        temp_Fish = Bitmap.createScaledBitmap(temp_Fish, x_Dm_T, y_Dm_T, false); //배경 화면 어둡게
                                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());


                                //이미지 크기 재 조정
                                if(fish_List.get(i).get_Fish_Speed() <= 0 && (!(fish_List.get(i) instanceof Fish_Trap_Jellyfish))) {
                                    effect_Slow_img[fish_List.get(i).get_Slow_Effect()] = Bitmap.createScaledBitmap(
                                            effect_Slow_img[fish_List.get(i).get_Slow_Effect()],
                                            fish_Touch_Default_Hp1_img[0].getWidth(),
                                            fish_Touch_Default_Hp1_img[0].getHeight(), true);
                                }


                                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                                /**
                                 * 물고기 터치 이펙트
                                 */
                                if(i == smallFishIndex && default_Fish_Hit_Flag) {

                                    //                    fish_List.get(fish_List.get(smallFishIndex).get_Fish_Target()).set_Hp_Minus();
                                    fish_List.get(smallFishIndex).set_Hp_Minus();            //풍타디 처럼 물고기 hp 깍으면 색깔 변경
                                    draw.draw_Bmp(canvas, effect_Temp, fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(random.nextInt(15), _context), fish_List.get(smallFishIndex).get_Fish_Point_Y()- convertPixelsToDp(random.nextInt(15), _context));
                                    default_Fish_Hit_Flag = false;
                                }



                                /**
                                 * 오징어 그리기
                                 */
                            } else if (fish_List.get(i) instanceof Fish_Touch_Squid) {

                                //그림자 그리기
                                draw.draw_Bmp(canvas, shadow_img[4], fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(10, _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(45, _context));

                                temp_Fish = draw.rotate_Image(fish_Touch_Squid_img[fish_List.get(i).get_Draw_Fish_Status()], 0);
                                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());//convertPixelsToDp(100, _context));


                                if(touch_Squid_Hit_Flag){
                                    draw.draw_Bmp(canvas, effect_Black_img[4],
                                            fish_List.get(smallFishIndex).get_Fish_Point_X() + convertPixelsToDp(random.nextInt(10), _context),
                                            fish_List.get(smallFishIndex).get_Fish_Point_Y() + convertPixelsToDp(random.nextInt(100), _context));//- convertPixelsToDp(100, _context));

                                    fish_List.get(smallFishIndex).set_Hp_Minus();            //풍타디 처럼 물고기 hp 깍으면 색깔 변경
                                    touch_Squid_Hit_Flag = false;
                                }



                                /**
                                 * 드래그로 죽이는 물고기
                                 */
                            } else if (fish_List.get(i) instanceof Fish_Drag_Default) {


                                /**
                                 * 드래그 물고기 설명 그림
                                 */


                                //그림자 그리기
                                temp_Shadow_img = draw.rotate_Image(shadow_img[3], -fish_List.get(i).get_Fish_Angle());
                                draw.draw_Bmp(canvas, temp_Shadow_img, fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(1, _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(20, _context));


                                temp_Fish = draw.rotate_Image(fish_Drag_Default_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());

                                /**
                                 * 드래그 터치 이펙트
                                 */
                                if(i == smallFishIndex && drag_Fish_Hit_Flag){

                                    //                    pop_Temp_img = Init_Effect_Pop_damage1_Image[random.nextInt(5)];
                                    tempInt = random.nextInt(5);
                                    if(character_Damege == 1) {
                                        pop_Temp_img = effect_Pop_damage1_Image[tempInt];
                                    }else if(character_Damege == 2) {
                                        pop_Temp_img = effect_Pop_damage2_Image[tempInt];
                                    }else if(character_Damege == 3) {
                                        pop_Temp_img = effect_Pop_damage3_Image[tempInt];
                                    }else if(character_Damege == 4) {
                                        pop_Temp_img = effect_Pop_damage4_Image[tempInt];
                                    }else if(character_Damege == 5) {
                                        pop_Temp_img = effect_Pop_damage5_Image[tempInt];
                                    }else if(character_Damege == 6) {
                                        pop_Temp_img = effect_Pop_damage6_Image[tempInt];
                                    }else if(character_Damege == 7) {
                                        pop_Temp_img = effect_Pop_damage7_Image[tempInt];
                                    }else if(character_Damege == 8) {
                                        pop_Temp_img = effect_Pop_damage8_Image[tempInt];
                                    }else if(character_Damege == 9) {
                                        pop_Temp_img = effect_Pop_damage9_Image[tempInt];
                                    }else if(character_Damege >= 10) {
                                        pop_Temp_img = effect_Pop_damage10_Image[tempInt];
                                    }


                                    draw.draw_Bmp(canvas, pop_Temp_img,
                                            fish_List.get(smallFishIndex).get_Fish_Point_X() + random.nextInt(fish_Drag_Default_img[0].getWidth() - 25),
                                            fish_List.get(smallFishIndex).get_Fish_Point_Y() + random.nextInt(fish_Drag_Default_img[0].getHeight()) - 35);

                                    fish_List.get(smallFishIndex).set_Hp_Minus(character_Damege * (int)character_Drag_Damege);            //풍타디 처럼 물고기 hp 깍으면 색깔 변경

                                    drag_Fish_Hit_Flag = false;

                                    //이미지 크기 재 조정
                                    if(fish_List.get(i).get_Fish_Speed() <= 0) {
                                        effect_Slow_img[fish_List.get(i).get_Slow_Effect()] = Bitmap.createScaledBitmap(
                                                effect_Slow_img[fish_List.get(i).get_Slow_Effect()],
                                                fish_Drag_Default_img[0].getWidth(),
                                                fish_Drag_Default_img[0].getHeight(), true);
                                    }


                                }



                            }else if(fish_List.get(i) instanceof Fish_Drag_Steelbream){ //참돔

                                //그림자 그리기
                                temp_Shadow_img = draw.rotate_Image(shadow_img[3], -fish_List.get(i).get_Fish_Angle());
                                draw.draw_Bmp(canvas, temp_Shadow_img, fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(1, _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(20, _context));

                                temp_Fish = draw.rotate_Image(fish_Drag_Steelbream_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());

                                /**
                                 * 참돔 터치 이펙트
                                 */
                                if(i == smallFishIndex && drag_Steelbream_Hit_Flag){

                                    //                    pop_Temp_img = Init_Effect_Pop_damage1_Image[random.nextInt(5)];

                                    pop_Temp_img = effect_Pop_Steel_img;


                                    draw.draw_Bmp(canvas, pop_Temp_img,
                                            fish_List.get(smallFishIndex).get_Fish_Point_X() + random.nextInt(fish_Drag_Steelbream_img[0].getWidth() - 25),
                                            fish_List.get(smallFishIndex).get_Fish_Point_Y() + random.nextInt(fish_Drag_Steelbream_img[0].getHeight()) - 35);


                                    drag_Steelbream_Hit_Flag = false;

                                    //이미지 크기 재 조정
                                    if(fish_List.get(i).get_Fish_Speed() <= 0) {
                                        effect_Slow_img[fish_List.get(i).get_Slow_Effect()] = Bitmap.createScaledBitmap(
                                                effect_Slow_img[fish_List.get(i).get_Slow_Effect()],
                                                fish_Drag_Steelbream_img[0].getWidth(),
                                                fish_Drag_Steelbream_img[0].getHeight(), true);
                                    }


                                }
                            }else if(fish_List.get(i) instanceof Fish_Drag_Shark){


                                //상어
                                draw.draw_Bmp(canvas, fish_Drag_Shark_img[fish_List.get(i).get_Draw_Fish_Status()],
                                        fish_List.get(i).get_Fish_Point_X(),
                                        fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));
                                /**
                                 * 상어 드래그 이펙트
                                 */
                                if(i == smallFishIndex && drag_Shark_Hit_Flag){

                                    //                    pop_Temp_img = Init_Effect_Pop_damage1_Image[random.nextInt(5)];
                                    tempInt = random.nextInt(5);
                                    if(character_Damege == 1) {
                                        pop_Temp_img = effect_Pop_damage1_Image[tempInt];
                                    }else if(character_Damege == 2) {
                                        pop_Temp_img = effect_Pop_damage2_Image[tempInt];
                                    }else if(character_Damege == 3) {
                                        pop_Temp_img = effect_Pop_damage3_Image[tempInt];
                                    }else if(character_Damege == 4) {
                                        pop_Temp_img = effect_Pop_damage4_Image[tempInt];
                                    }else if(character_Damege == 5) {
                                        pop_Temp_img = effect_Pop_damage5_Image[tempInt];
                                    }else if(character_Damege == 6) {
                                        pop_Temp_img = effect_Pop_damage6_Image[tempInt];
                                    }else if(character_Damege == 7) {
                                        pop_Temp_img = effect_Pop_damage7_Image[tempInt];
                                    }else if(character_Damege == 8) {
                                        pop_Temp_img = effect_Pop_damage8_Image[tempInt];
                                    }else if(character_Damege == 9) {
                                        pop_Temp_img = effect_Pop_damage9_Image[tempInt];
                                    }else if(character_Damege >= 10) {
                                        pop_Temp_img = effect_Pop_damage10_Image[tempInt];
                                    }

                                    draw.draw_Bmp(canvas, pop_Temp_img,
                                            fish_List.get(smallFishIndex).get_Fish_Point_X() + random.nextInt(fish_Drag_Shark_img[0].getWidth() - 25),
                                            fish_List.get(smallFishIndex).get_Fish_Point_Y() + random.nextInt(fish_Drag_Shark_img[0].getHeight()) - 35);
                                    fish_List.get(smallFishIndex).set_Hp_Minus(character_Damege * (int)character_Drag_Damege);            //풍타디 처럼 물고기 hp 깍으면 색깔 변경
                                    drag_Shark_Hit_Flag = false;
                                }
                            }else if(fish_List.get(i) instanceof Fish_Monster){




                                //트루 일떄 오른쪽
                                if(((Fish_Monster)fish_List.get(i)).get_Site()){
                                    //몬스터

                                    draw.draw_Bmp(canvas, fish_Monster_Right_img[fish_List.get(i).get_Draw_Fish_Status()],
                                            fish_List.get(i).get_Fish_Point_X(),
                                            fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));
                                }else {
                                    //몬스터
                                    draw.draw_Bmp(canvas, fish_Monster_Left_img[fish_List.get(i).get_Draw_Fish_Status()],
                                            fish_List.get(i).get_Fish_Point_X(),
                                            fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));
                                }

                                /**
                                 * 몬스터 대래그 이펙트
                                 */
                                if(i == smallFishIndex && monster_Fish_Hit_Flag){

                                    //                    pop_Temp_img = Init_Effect_Pop_damage1_Image[random.nextInt(5)];
                                    tempInt = random.nextInt(5);
                                    if(character_Damege == 1) {
                                        pop_Temp_img = effect_Pop_damage1_Image[tempInt];
                                    }else if(character_Damege == 2) {
                                        pop_Temp_img = effect_Pop_damage2_Image[tempInt];
                                    }else if(character_Damege == 3) {
                                        pop_Temp_img = effect_Pop_damage3_Image[tempInt];
                                    }else if(character_Damege == 4) {
                                        pop_Temp_img = effect_Pop_damage4_Image[tempInt];
                                    }else if(character_Damege == 5) {
                                        pop_Temp_img = effect_Pop_damage5_Image[tempInt];
                                    }else if(character_Damege == 6) {
                                        pop_Temp_img = effect_Pop_damage6_Image[tempInt];
                                    }else if(character_Damege == 7) {
                                        pop_Temp_img = effect_Pop_damage7_Image[tempInt];
                                    }else if(character_Damege == 8) {
                                        pop_Temp_img = effect_Pop_damage8_Image[tempInt];
                                    }else if(character_Damege == 9) {
                                        pop_Temp_img = effect_Pop_damage9_Image[tempInt];
                                    }else if(character_Damege >= 10) {
                                        pop_Temp_img = effect_Pop_damage10_Image[tempInt];
                                    }

                                    draw.draw_Bmp(canvas, pop_Temp_img,
                                            fish_List.get(smallFishIndex).get_Fish_Point_X() + random.nextInt(fish_Monster_Right_img[0].getWidth() - 25),
                                            fish_List.get(smallFishIndex).get_Fish_Point_Y() + random.nextInt(fish_Monster_Right_img[0].getHeight()) - 35);
                                    fish_List.get(smallFishIndex).set_Hp_Minus(character_Damege * (int)character_Drag_Damege);            //풍타디 처럼 물고기 hp 깍으면 색깔 변경
                                    monster_Fish_Hit_Flag = false;
                                }
                            }





                            else if(fish_List.get(i) instanceof Fish_Touch_Ell){


                                /**
                                 * 전기 뱀장어 그리기
                                 */


                                //그림자 그리기
                                draw.draw_Bmp(canvas, shadow_img[4], fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(15, _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(45, _context));


                                if(fish_List.get(i).get_Attack_Mode()) {
                                    temp_Fish = draw.rotate_Image(fish_Touch_Ell_img[fish_List.get(i).get_Draw_Fish_Status()], 0);
                                    draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));

                                }else if(!fish_List.get(i).get_Attack_Mode()){
                                    temp_Fish = draw.rotate_Image(fish_Touch_Ell_Attack_img[fish_List.get(i).get_Draw_Fish_Status()], 0);
                                    draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));
                                }


                                if(touch_Ell_Hit_Flag){
                                    draw.draw_Bmp(canvas, effect_Yellow_img[4],
                                            fish_List.get(smallFishIndex).get_Fish_Point_X() + convertPixelsToDp(random.nextInt(10), _context),
                                            fish_List.get(smallFishIndex).get_Fish_Point_Y() + convertPixelsToDp(random.nextInt(100), _context));//- convertPixelsToDp(100, _context));

                                    Log.e("뱀장어","뱀장어");
                                    if(random.nextInt(100) > lightningresistance) {
                                        main_Character.set_Hp_Minus();
                                        gameActivity.set_Vibrator();
                                        Log.e("뱀장어","뱀장어111111111111");
                                    }

                                    fish_List.get(smallFishIndex).set_Hp_Minus();            //풍타디 처럼 물고기 hp 깍으면 색깔 변경

                                    touch_Ell_Hit_Flag = false;
                                }


                            }else if(fish_List.get(i) instanceof Fish_Touch_Marlin){
                                /**
                                 * 청새치 그리기
                                 */

                                draw.draw_Bmp(canvas, fish_Touch_Marlin_img[fish_List.get(i).get_Draw_Fish_Status()],
                                        fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(0, _context),
                                        fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));

                                //그림자 그리기
                                draw.draw_Bmp(canvas, shadow_img[4], fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(12, _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(30, _context));


                                if(touch_Marlin_Hit_Flag){
                                    draw.draw_Bmp(canvas, effect_Blue_img[4],
                                            fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(12, _context) + random.nextInt(fish_Touch_Marlin_img[0].getWidth()/2),
                                            fish_List.get(smallFishIndex).get_Fish_Point_Y() + random.nextInt(fish_Touch_Marlin_img[0].getHeight()/2));//- convertPixelsToDp(100, _context));

                                    fish_List.get(smallFishIndex).set_Hp_Minus();            //풍타디 처럼 물고기 hp 깍으면 색깔 변경

                                    touch_Marlin_Hit_Flag = false;
                                }





                            }


                            /**
                             *  해파리 그리기
                             */
                            else if (fish_List.get(i) instanceof Fish_Trap_Jellyfish) {
                                temp_Fish = draw.rotate_Image(fish_Trap_Jelly_img[fish_List.get(i).get_Draw_Fish_Status()], 90 - fish_List.get(i).get_Fish_Angle());
                                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());
                            }










                            //속도가 0인 객체 위에 달팽이 [슬로우 이팩트를 그린다.]
                            if(fish_List.get(i).get_Fish_Speed() <= 0){

                                //해파리는 슬로우 안걸림
                                if(!(fish_List.get(i) instanceof Fish_Trap_Jellyfish) || !(fish_List.get(i) instanceof Fish_Trap_Turtle)) {
                                    if(!(fish_List.get(i) instanceof Fish_Monster)) {
                                        draw.draw_Bmp(canvas, effect_Slow_img[0],
                                                fish_List.get(i).get_Fish_Point_X(),
                                                fish_List.get(i).get_Fish_Point_Y() - convertPixelsToDp(13, _context));


                                        draw.draw_Bmp(canvas, effect_Slow_img[fish_List.get(i).get_Slow_Effect()],
                                                fish_List.get(i).get_Fish_Point_X(),
                                                fish_List.get(i).get_Fish_Point_Y());
                                    }
                                }
                            }

                            //독 상태 이상에 걸린 객체에 포이즌 마크를 표시한다.
                            if(fish_List.get(i).get_Status_Poison()){
                                fish_List.get(i).set_Status_Poison_AttacK();

                                pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[fish_List.get(i).get_Status_Poison_AttacK()],fish_Touch_Default_Middle_Hp1_img[0].getWidth() * 2, fish_Touch_Default_Middle_Hp1_img[0].getHeight(), false);

                                if(fish_List.get(i) instanceof Fish_Touch_Default) {

                                    if(fish_List.get(i).get_Class_Num() == 2){ //보스
                                        pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[fish_List.get(i).get_Status_Poison_AttacK()], fish_Touch_Default_Boss_Hp1_img[0].getWidth() * 2, fish_Touch_Default_Boss_Hp1_img[0].getHeight(), false);
                                    }else if(fish_List.get(i).get_Class_Num() == 1){ //중간 보스
                                        pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[fish_List.get(i).get_Status_Poison_AttacK()], fish_Touch_Default_Middle_Hp1_img[0].getWidth() * 2, fish_Touch_Default_Middle_Hp1_img[0].getHeight(), false);
                                    }else {
                                        pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[fish_List.get(i).get_Status_Poison_AttacK()], fish_Touch_Default_Hp1_img[0].getWidth() * 2, fish_Touch_Default_Hp1_img[0].getHeight(), false);
                                    }

                                }else if(fish_List.get(i) instanceof Fish_Drag_Default){
                                    pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[fish_List.get(i).get_Status_Poison_AttacK()], fish_Drag_Default_img[0].getWidth() * 2, fish_Drag_Default_img[0].getHeight(), false);
                                }else if(fish_List.get(i) instanceof Fish_Drag_Shark){
                                    pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[fish_List.get(i).get_Status_Poison_AttacK()], fish_Drag_Shark_img[0].getWidth(), fish_Drag_Shark_img[0].getHeight(), false);
                                }else if(fish_List.get(i) instanceof Fish_Trap_Turtle){
                                    pop_Temp_img = Bitmap.createScaledBitmap(effect_Poison_img[fish_List.get(i).get_Status_Poison_AttacK()], 1, 1, false);
                                }

                                draw.draw_Bmp(canvas, pop_Temp_img, ///중독된 물고기 hp 감속과 동시에 이미지 표현
                                        fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(5, _context),
                                        fish_List.get(i).get_Fish_Point_Y()); //- convertPixelsToDp(13, _context));


                            }



                        }
                    }


                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","물고기 그리기 부분");
                }

                try{

                    //집게발 그리기
                    for(int i=0; i<skill_Crab_Claws_List.size(); i++){


                        draw.draw_Bmp(canvas, skill_Crab_img[skill_Crab_Claws_List.get(i).get_Skill_Status()], skill_Crab_Claws_List.get(i).get_X_Point(), skill_Crab_Claws_List.get(i).get_Y_Point());
                        skill_Crab_Claws_List.get(i).set_Skill_Move();


                        if(skill_Crab_Claws_List.get(i).get_Live()){

                            //범위 안에 드는 몬스터들의 체력을 떨군다.
                            for(int j=0; j<fish_List.size(); j++){
                                if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                    continue;
                                }


                                s_x = skill_Crab_Claws_List.get(i).get_X_Point() + convertPixelsToDp(10, _context);
                                s_x_w = skill_Crab_Claws_List.get(i).get_X_Point() + skill_Crab_img[3].getWidth() - convertPixelsToDp(10, _context);
                                s_y = skill_Crab_Claws_List.get(i).get_Y_Point() + convertPixelsToDp(20, _context) ;
                                s_y_h = skill_Crab_Claws_List.get(i).get_Y_Point() + skill_Crab_img[3].getHeight() - convertPixelsToDp(10, _context);

                                f_x = fish_List.get(j).get_Fish_Point_X();
                                f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                                f_y = fish_List.get(j).get_Fish_Point_Y();
                                f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    skill_Plus_D = 0;
                                    if(st4 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) st4);
                                    }
                                    fish_List.get(j).set_Hp_Minus(20 +  skill_Plus_D);

                                }


                            }


                            for(int j=0; j<ground_List.size(); j++){
                                if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                    continue;
                                }

                                s_x = skill_Crab_Claws_List.get(i).get_X_Point() + convertPixelsToDp(10, _context);
                                s_x_w = skill_Crab_Claws_List.get(i).get_X_Point() + skill_Crab_img[3].getWidth() - convertPixelsToDp(10, _context);
                                s_y = skill_Crab_Claws_List.get(i).get_Y_Point() + convertPixelsToDp(20, _context) ;
                                s_y_h = skill_Crab_Claws_List.get(i).get_Y_Point() + skill_Crab_img[3].getHeight() - convertPixelsToDp(10, _context);

                                f_x = ground_List.get(j).get_Ground_Point_X();
                                f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                                f_y = ground_List.get(j).get_Ground_Point_Y();
                                f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    if((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                        if(!((Ground_Touch_Hermit)ground_List.get(j)).get_Immortal_Mode()) {
                                            ground_List.get(j).set_Ground_Hp_Minus(1);
                                        }
                                    }else {

                                        skill_Plus_D = 0;
                                        if(st4 >= 1 ){
                                            skill_Plus_D = random.nextInt((int) st4);
                                        }

                                        ground_List.get(j).set_Ground_Hp_Minus(20 + skill_Plus_D);

                                    }
                                }



                            }

                            //                skill_Crab_Claws_List.remove(i);
                            skill_Crab_Claws_List.get(i).set_Remove();
                        }
                    }

                    //지우기 컨트롤
                    for(int i=skill_Crab_Claws_List.size()-1; i>=0; i--){
                        if(skill_Crab_Claws_List.get(i).get_Remove()){
                            skill_Crab_Claws_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","집게발 그리기 부분");
                }

                try{

                    //간장게장 집게발
                    for(int i=0; i<skill_Soycrab_Claws_List.size(); i++){

                        draw.draw_Bmp(canvas, skill_Soycrab_img[skill_Soycrab_Claws_List.get(i).get_Skill_Status()], skill_Soycrab_Claws_List.get(i).get_X_Point(), skill_Soycrab_Claws_List.get(i).get_Y_Point());
                        skill_Soycrab_Claws_List.get(i).set_Skill_Move();

                        if(skill_Soycrab_Claws_List.get(i).get_Live()){

                            //범위 안에 드는 몬스터들의 체력을 떨군다.
                            for(int j=0; j<fish_List.size(); j++){
                                if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                    continue;
                                }

                                s_x = skill_Soycrab_Claws_List.get(i).get_X_Point() + convertPixelsToDp(10, _context);
                                s_x_w = skill_Soycrab_Claws_List.get(i).get_X_Point() + skill_Soycrab_img[3].getWidth() - convertPixelsToDp(10, _context);
                                s_y = skill_Soycrab_Claws_List.get(i).get_Y_Point() + convertPixelsToDp(20, _context) ;
                                s_y_h = skill_Soycrab_Claws_List.get(i).get_Y_Point() + skill_Soycrab_img[3].getHeight() - convertPixelsToDp(10, _context);

                                f_x = fish_List.get(j).get_Fish_Point_X();
                                f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                                f_y = fish_List.get(j).get_Fish_Point_Y();
                                f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {

                                    skill_Plus_D = 0;
                                    if(st5 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) st5);
                                    }

                                    fish_List.get(j).set_Hp_Minus(30 +  skill_Plus_D);

                                }


                            }
                            for(int j=0; j<ground_List.size(); j++){

                                if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                    continue;
                                }

                                s_x = skill_Soycrab_Claws_List.get(i).get_X_Point() + convertPixelsToDp(10, _context);
                                s_x_w = skill_Soycrab_Claws_List.get(i).get_X_Point() + skill_Soycrab_img[3].getWidth() - convertPixelsToDp(10, _context);
                                s_y = skill_Soycrab_Claws_List.get(i).get_Y_Point() + convertPixelsToDp(20, _context) ;
                                s_y_h = skill_Soycrab_Claws_List.get(i).get_Y_Point() + skill_Soycrab_img[3].getHeight() - convertPixelsToDp(10, _context);

                                f_x = ground_List.get(j).get_Ground_Point_X();
                                f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                                f_y = ground_List.get(j).get_Ground_Point_Y();
                                f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    if((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                        if(!((Ground_Touch_Hermit)ground_List.get(j)).get_Immortal_Mode()) {
                                            ground_List.get(j).set_Ground_Hp_Minus(1);
                                        }
                                    }else {
                                        skill_Plus_D = 0;
                                        if(st5 >= 1 ){
                                            skill_Plus_D = random.nextInt((int) st5);
                                        }
                                        ground_List.get(j).set_Ground_Hp_Minus(30 + skill_Plus_D);

                                    }
                                }


                            }

                            //                skill_Soycrab_Claws_List.remove(i);
                            skill_Soycrab_Claws_List.get(i).set_Remove();
                        }
                    }

                    //간장게장 지우기 컨트롤
                    for(int i=skill_Soycrab_Claws_List.size()-1; i>=0; i--){
                        if(skill_Soycrab_Claws_List.get(i).get_Remove()){
                            skill_Soycrab_Claws_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","간장게장스킬 그리기 부분");
                }

                try {

                    //스톰프
                    for (int i = 0; i < skill_Stomp_List.size(); i++) {

                        draw.draw_Bmp(canvas, skill_stomp_img[skill_Stomp_List.get(i).get_Skill_Status()], skill_Stomp_List.get(i).get_X_Point(), skill_Stomp_List.get(i).get_Y_Point());
                        skill_Stomp_List.get(i).set_Skill_Move();


                        if (skill_Stomp_List.get(i).get_Live()) {
                            //범위 안에 드는 몬스터들의 체력을 떨군다.
                            for (int j = 0; j < fish_List.size(); j++) {
                                if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                    continue;
                                }


                                s_x = skill_Stomp_List.get(i).get_X_Point();
                                s_x_w = skill_Stomp_List.get(i).get_X_Point() + skill_stomp_img[0].getWidth();
                                s_y = skill_Stomp_List.get(i).get_Y_Point() ;
                                s_y_h = skill_Stomp_List.get(i).get_Y_Point() + skill_stomp_img[0].getHeight();

                                f_x = fish_List.get(j).get_Fish_Point_X();
                                f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                                f_y = fish_List.get(j).get_Fish_Point_Y();
                                f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    skill_Plus_D = 0;
                                    if(st10 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) st10* 3);
                                    }
                                    fish_List.get(j).set_Hp_Minus(80 + skill_Plus_D);

                                }


                            }


                            for (int j = 0; j < ground_List.size(); j++) {

                                if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                    continue;
                                }

                                s_x = skill_Stomp_List.get(i).get_X_Point();
                                s_x_w = skill_Stomp_List.get(i).get_X_Point() + skill_stomp_img[0].getWidth();
                                s_y = skill_Stomp_List.get(i).get_Y_Point() ;
                                s_y_h = skill_Stomp_List.get(i).get_Y_Point() + skill_stomp_img[0].getHeight();

                                f_x = ground_List.get(j).get_Ground_Point_X();
                                f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                                f_y = ground_List.get(j).get_Ground_Point_Y();
                                f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    if((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                        if(!((Ground_Touch_Hermit)ground_List.get(j)).get_Immortal_Mode()) {
                                            ground_List.get(j).set_Ground_Hp_Minus(1);
                                        }
                                    }else {
                                        skill_Plus_D = 0;
                                        if(st10 >= 1 ){
                                            skill_Plus_D = random.nextInt((int) st10* 3);
                                        }
                                        ground_List.get(j).set_Ground_Hp_Minus(80 + skill_Plus_D);
                                    }


//                                    gameActivity.set_Vibrator();
                                }

//
                            }

//                skill_Stomp_List.remove(i);
                            skill_Stomp_List.get(i).set_Remove();
                        }
                    }

                    for (int i = skill_Stomp_List.size()-1; i >=0 ; i--) {
                        if (skill_Stomp_List.get(i).get_Remove()) {
                            soundPool.play(sound_Effect[20], pop_Drag, pop_Drag, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                            gameActivity.set_Vibrator();
                            skill_Stomp_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","스톰프 그리기 부분");
                }


                try{

                    //가시
                    for(int i=0; i<skill_Thorn_List.size(); i++){

                        draw.draw_Bmp(canvas, skill_Thorn_img[skill_Thorn_List.get(i).get_Skill_Status()], skill_Thorn_List.get(i).get_X_Point(), skill_Thorn_List.get(i).get_Y_Point());
                        skill_Thorn_List.get(i).set_Skill_Move();


                        if(skill_Thorn_List.get(i).get_Live()){
                            skill_Thorn_List.get(i).set_Remove();
                            //                skill_Thorn_List.remove(i);
                        }
                    }
                    //가시 지우기 컨트롤
                    for(int i=skill_Thorn_List.size()-1; i>=0; i--){
                        if(skill_Thorn_List.get(i).get_Remove()){
                            skill_Thorn_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","가시 그리기 부분");
                    skill_Thorn_List.clear();
                }


                try{

                    //독구름
                    for(int i=0; i<skill_poison_cloud_List.size(); i++){


                        if(skill_poison_cloud_List.get(i).get_Model() == 0){
                            pop_Temp_img = skill_Poison1_img[ skill_poison_cloud_List.get(i).get_Skill_Status() ];
                        }else if (skill_poison_cloud_List.get(i).get_Model() == 1){
                            pop_Temp_img = skill_Poison2_img[ skill_poison_cloud_List.get(i).get_Skill_Status() ];
                        }else{
                            pop_Temp_img = skill_Poison3_img[ skill_poison_cloud_List.get(i).get_Skill_Status() ];
                        }

                        draw.draw_Bmp(canvas, pop_Temp_img, skill_poison_cloud_List.get(i).get_X_Point(), skill_poison_cloud_List.get(i).get_Y_Point());
                        skill_poison_cloud_List.get(i).set_Skill_Move();


                        for(int j=0; j<fish_List.size(); j++){
                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }

                            s_x = skill_poison_cloud_List.get(i).get_X_Point();
                            s_x_w = skill_poison_cloud_List.get(i).get_X_Point() + pop_Temp_img.getWidth();
                            s_y = skill_poison_cloud_List.get(i).get_Y_Point();
                            s_y_h = skill_poison_cloud_List.get(i).get_Y_Point() + pop_Temp_img.getHeight();

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Plus_D = 0;
                                if(mt10 >= 1 ){
                                    skill_Plus_D = random.nextInt((int) mt10);
                                }
                                fish_List.get(j).set_Status_Poison(20 +  random.nextInt((int)mt10));
//                                break;
                            }


                        }


                        for(int j=0; j<ground_List.size(); j++){

                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }

                            s_x = skill_poison_cloud_List.get(i).get_X_Point();
                            s_x_w = skill_poison_cloud_List.get(i).get_X_Point() + pop_Temp_img.getWidth();
                            s_y = skill_poison_cloud_List.get(i).get_Y_Point();
                            s_y_h = skill_poison_cloud_List.get(i).get_Y_Point() + pop_Temp_img.getHeight();

                            f_x = ground_List.get(j).get_Ground_Point_X();
                            f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                            f_y = ground_List.get(j).get_Ground_Point_Y();
                            f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Plus_D = 0;
                                if(mt10 >= 1 ){
                                    skill_Plus_D = random.nextInt((int) mt10);
                                }
                                ground_List.get(j).set_Status_Poison(20 + skill_Plus_D);
//                                break;
                            }



                        }


                        if(skill_poison_cloud_List.get(i).get_Live()){
//                skill_poison_cloud_List.remove(i);
                            skill_poison_cloud_List.get(i).set_Remove();
                        }

                    }
                    for(int i=skill_poison_cloud_List.size()-1; i>=0; i--){
                        if(skill_poison_cloud_List.get(i).get_Remove()){
                            skill_poison_cloud_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","독구름 그리기 부분");
                }

                try{

                    //지진 공격
                    for(int i=0; i<skill_Earthquake_List.size(); i++){

                        pop_Temp_img = draw.rotate_Image(skill_earthquake_img[skill_Earthquake_List.get(i).get_Skill_Status()], skill_Earthquake_List.get(i).get_Angle());
                        draw.draw_Bmp(canvas, pop_Temp_img, skill_Earthquake_List.get(i).get_X_Point(), skill_Earthquake_List.get(i).get_Y_Point());


                        skill_Earthquake_List.get(i).set_Skill_Move();



                        if(skill_Earthquake_List.get(i).get_Live()){

                            for (int j = 0; j < fish_List.size(); j++) {
                                if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                    continue;
                                }

                                s_x = skill_Earthquake_List.get(i).get_X_Point();
                                s_x_w = skill_Earthquake_List.get(i).get_X_Point() + skill_earthquake_img[3].getWidth();
                                s_y = skill_Earthquake_List.get(i).get_Y_Point();
                                s_y_h = skill_Earthquake_List.get(i).get_Y_Point() + skill_earthquake_img[3].getHeight();

                                f_x = fish_List.get(j).get_Fish_Point_X();
                                f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                                f_y = fish_List.get(j).get_Fish_Point_Y();
                                f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    skill_Plus_D = 0;
                                    if(ft4 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) ft4);
                                    }
                                    fish_List.get(j).set_Hp_Minus(20 + skill_Plus_D);
//                                    break;
                                }

                            }


                            for (int j = 0; j < ground_List.size(); j++) {

                                if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                    continue;
                                }
                                s_x = skill_Earthquake_List.get(i).get_X_Point();
                                s_x_w = skill_Earthquake_List.get(i).get_X_Point() + skill_earthquake_img[3].getWidth();
                                s_y = skill_Earthquake_List.get(i).get_Y_Point();
                                s_y_h = skill_Earthquake_List.get(i).get_Y_Point() + skill_earthquake_img[3].getHeight();

                                f_x = ground_List.get(j).get_Ground_Point_X();
                                f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                                f_y = ground_List.get(j).get_Ground_Point_Y();
                                f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    if((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                        if(!((Ground_Touch_Hermit)ground_List.get(j)).get_Immortal_Mode()) {
                                            ground_List.get(j).set_Ground_Hp_Minus(1);
                                        }
                                    }else {
                                        skill_Plus_D = 0;
                                        if(ft4 >= 1 ){
                                            skill_Plus_D = random.nextInt((int) ft4);
                                        }
                                        ground_List.get(j).set_Ground_Hp_Minus(20 + skill_Plus_D);
                                    }
//                                    break;
                                }

                            }




                        }
                        if(skill_Earthquake_List.get(i).get_Live()){
//                skill_Earthquake_List.remove(i);
                            skill_Earthquake_List.get(i).set_Remove();
                        }
                    }
                    for(int i=skill_Earthquake_List.size()-1; i>=0; i--){
                        if(skill_Earthquake_List.get(i).get_Remove()){
                            skill_Earthquake_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","지진 그리기 부분");
                }


                try{

                    //이빨 지뢰
                    for(int i=0; i< skill_Teeth_Mine_List.size(); i++){



                        draw.draw_Bmp(canvas, skill_Teeth_mine_img[skill_Teeth_Mine_List.get(i).get_Skill_Status()], skill_Teeth_Mine_List.get(i).get_X_Point(), skill_Teeth_Mine_List.get(i).get_Y_Point());
                        if(skill_Teeth_Mine_List.get(i).get_Skill_Status() == 1){
                            soundPool.play(sound_Effect[33], pop_Drag, pop_Drag, 0, 0, 1.0F);
                        }if(skill_Teeth_Mine_List.get(i).get_Skill_Status() == 2){
                            soundPool.play(sound_Effect[random.nextInt(2)], pop_Drag, pop_Drag, 0, 0, 1.0F);
                        }

                        skill_Teeth_Mine_List.get(i).set_Skill_Move();

                        for (int j = 0; j < fish_List.size(); j++) {
                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }

                            s_x = skill_Teeth_Mine_List.get(i).get_X_Point();
                            s_x_w = skill_Teeth_Mine_List.get(i).get_X_Point() + skill_Teeth_mine_img[0].getWidth();
                            s_y = skill_Teeth_Mine_List.get(i).get_Y_Point();
                            s_y_h = skill_Teeth_Mine_List.get(i).get_Y_Point() + skill_Teeth_mine_img[0].getHeight();

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Plus_D = 0;
                                if(ft3 >= 1 ){
                                    skill_Plus_D = random.nextInt((int) ft3);
                                }
                                fish_List.get(j).set_Hp_Minus(10 + skill_Plus_D);

                                skill_Teeth_Mine_List.get(i).set_play_Attack();




                            }


                        }

                        for (int j = 0; j < ground_List.size(); j++) {

                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }


                            s_x = skill_Teeth_Mine_List.get(i).get_X_Point();
                            s_x_w = skill_Teeth_Mine_List.get(i).get_X_Point() + skill_Teeth_mine_img[0].getWidth();
                            s_y = skill_Teeth_Mine_List.get(i).get_Y_Point();
                            s_y_h = skill_Teeth_Mine_List.get(i).get_Y_Point() + skill_Teeth_mine_img[0].getHeight();

                            f_x = ground_List.get(j).get_Ground_Point_X();
                            f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                            f_y = ground_List.get(j).get_Ground_Point_Y();
                            f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                if((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                    if(!((Ground_Touch_Hermit)ground_List.get(j)).get_Immortal_Mode()) {
                                        ground_List.get(j).set_Ground_Hp_Minus(1);
                                    }
                                }else {
                                    skill_Plus_D = 0;
                                    if(ft3 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) ft3);
                                    }
                                    ground_List.get(j).set_Ground_Hp_Minus(10 + skill_Plus_D);


                                }
                                    skill_Teeth_Mine_List.get(i).set_play_Attack();

//                                break;
                            }

//                            }



                        }



                        if(skill_Teeth_Mine_List.get(i).get_Delete_Attack()){
//                skill_Teeth_Mine_List.remove(i);
                            skill_Teeth_Mine_List.get(i).set_Remove();
                        }


                    }
                    for(int i=skill_Teeth_Mine_List.size()-1; i>=0 ; i--){
                        if(skill_Teeth_Mine_List.get(i).get_Remove()){
                            skill_Teeth_Mine_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a"," 이빨지뢰 부분");
                }

                try{
                    //이빨 지뢰2
                    for(int i=0; i<skill_Teeth_Mine2_List.size(); i++){



                        if(skill_Teeth_Mine2_List.get(i).get_Skill_Status() == 0) {
                            draw.draw_Bmp(canvas, skill_Teeth_mine2_img[0], skill_Teeth_Mine2_List.get(i).get_X_Point() - convertPixelsToDp(15, _context), skill_Teeth_Mine2_List.get(i).get_Y_Point());
                        }else {
                            if(skill_Teeth_Mine2_List.get(i).get_Skill_Status() == 1){
                                soundPool.play(sound_Effect[32], pop_Drag, pop_Drag, 0, 0, 1.0F);
                            }if(skill_Teeth_Mine2_List.get(i).get_Skill_Status() == 3){
                                soundPool.play(sound_Effect[random.nextInt(2)], pop_Drag, pop_Drag, 0, 0, 1.0F);
                            }
                            draw.draw_Bmp(canvas, skill_Teeth_mine2_img[skill_Teeth_Mine2_List.get(i).get_Skill_Status()], skill_Teeth_Mine2_List.get(i).get_X_Point() - convertPixelsToDp(75, _context), skill_Teeth_Mine2_List.get(i).get_Y_Point());
                        }
                        skill_Teeth_Mine2_List.get(i).set_Skill_Move();



                        for (int j = 0; j < fish_List.size(); j++) {
                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }

                            s_x = skill_Teeth_Mine2_List.get(i).get_X_Point();
                            s_x_w = skill_Teeth_Mine2_List.get(i).get_X_Point() + skill_Teeth_mine2_img[0].getWidth();
                            s_y = skill_Teeth_Mine2_List.get(i).get_Y_Point();
                            s_y_h = skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[0].getHeight();

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {



                                skill_Teeth_Mine2_List.get(i).set_play_Attack();
                                break;
                            }


//                                }
//                            }
                        }
                        if(skill_Teeth_Mine2_List.get(i).get_Play_Attack()){







                            for (int j = 0; j < fish_List.size(); j++) {
                                if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                    continue;
                                }

                                s_x = skill_Teeth_Mine2_List.get(i).get_X_Point() - convertPixelsToDp(75, _context);
                                s_x_w = skill_Teeth_Mine2_List.get(i).get_X_Point() + skill_Teeth_mine2_img[1].getWidth() - convertPixelsToDp(75, _context);
                                s_y = skill_Teeth_Mine2_List.get(i).get_Y_Point();
                                s_y_h = skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[1].getHeight();

                                f_x = fish_List.get(j).get_Fish_Point_X();
                                f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                                f_y = fish_List.get(j).get_Fish_Point_Y();
                                f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    skill_Plus_D = 0;
                                    if(ft5 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) ft5);
                                    }
                                    fish_List.get(j).set_Hp_Minus(35  +  skill_Plus_D);
                                }





                            }
                        }



                        for (int j = 0; j < ground_List.size(); j++) {

                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }

                            s_x = skill_Teeth_Mine2_List.get(i).get_X_Point();
                            s_x_w = skill_Teeth_Mine2_List.get(i).get_X_Point() + skill_Teeth_mine2_img[0].getWidth();
                            s_y = skill_Teeth_Mine2_List.get(i).get_Y_Point();
                            s_y_h = skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[0].getHeight();

                            f_x = ground_List.get(j).get_Ground_Point_X();
                            f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                            f_y = ground_List.get(j).get_Ground_Point_Y();
                            f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {

                                skill_Teeth_Mine2_List.get(i).set_play_Attack();
                                break;
                            }


                        }
                        if(skill_Teeth_Mine2_List.get(i).get_Play_Attack()){




                            for (int j = 0; j < ground_List.size(); j++) {

                                if (!ground_List.get(j).get_Visible_Ground_Flag()) {
                                    continue;
                                }

                                s_x = skill_Teeth_Mine2_List.get(i).get_X_Point() - convertPixelsToDp(75, _context);
                                s_x_w = skill_Teeth_Mine2_List.get(i).get_X_Point() + skill_Teeth_mine2_img[1].getWidth() - convertPixelsToDp(75, _context);
                                s_y = skill_Teeth_Mine2_List.get(i).get_Y_Point();
                                s_y_h = skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[1].getHeight();

                                f_x = ground_List.get(j).get_Ground_Point_X();
                                f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                                f_y = ground_List.get(j).get_Ground_Point_Y();
                                f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();


                                if ((s_x > f_x_w) || (s_x_w < f_x) || (s_y > f_y_h) || (s_y_h < f_y)) {

                                } else {
                                    if ((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                        if (!((Ground_Touch_Hermit) ground_List.get(j)).get_Immortal_Mode()) {
                                            ground_List.get(j).set_Ground_Hp_Minus(1);
                                        }
                                    } else {
                                        skill_Plus_D = 0;
                                        if (ft5 >= 1) {
                                            skill_Plus_D = random.nextInt((int) ft5);
                                        }
                                        ground_List.get(j).set_Ground_Hp_Minus(35 + skill_Plus_D);
                                    }
                                }


                            }
                        }



                        if (skill_Teeth_Mine2_List.get(i).get_Delete_Attack()) {
//        skill_Teeth_Mine2_List.remove(i);
                            skill_Teeth_Mine2_List.get(i).set_Remove();
                        }

                    }

                    for(int i=skill_Teeth_Mine2_List.size()-1; i>=0; i--){
                        if(skill_Teeth_Mine2_List.get(i).get_Remove()){
                            skill_Teeth_Mine2_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a"," 이빨지뢰2 부분");
                }


                try{
                    //버터
                    for(int i=0; i<skill_Butter_List.size(); i++){
                        if(skill_Butter_List.get(i).get_Skill_Status() <= 0) {

                            draw.draw_Bmp(canvas, skill_Butter_img[0], skill_Butter_List.get(i).get_X_Point(), skill_Butter_List.get(i).get_Y_Point() - convertPixelsToDp(25, _context) );
                        }else{
                            draw.draw_Bmp(canvas, skill_Butter_img[skill_Butter_List.get(i).get_Skill_Status()], skill_Butter_List.get(i).get_X_Point(), skill_Butter_List.get(i).get_Y_Point() - convertPixelsToDp(25, _context) );
                        }


                        for (int j = 0; j < fish_List.size(); j++) {
                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }

                            s_x = skill_Butter_List.get(i).get_X_Point();
                            s_x_w = skill_Butter_List.get(i).get_X_Point() + skill_Butter_img[0].getWidth();
                            s_y = skill_Butter_List.get(i).get_Y_Point() - convertPixelsToDp(25, _context);
                            s_y_h = skill_Butter_List.get(i).get_Y_Point() + skill_Butter_img[0].getHeight() - convertPixelsToDp(25, _context);

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Butter_List.get(i).set_play_Attack();
                                fish_List.get(j).set_Fish_Speed(0);
                            }





                        }

                        for(int j=0; j<ground_List.size(); j++){

                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }

                            s_x = skill_Butter_List.get(i).get_X_Point();
                            s_x_w = skill_Butter_List.get(i).get_X_Point() + skill_Butter_img[0].getWidth();
                            s_y = skill_Butter_List.get(i).get_Y_Point() - convertPixelsToDp(25, _context);
                            s_y_h = skill_Butter_List.get(i).get_Y_Point() + skill_Butter_img[0].getHeight() - convertPixelsToDp(25, _context);

                            f_x = ground_List.get(j).get_Ground_Point_X();
                            f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                            f_y = ground_List.get(j).get_Ground_Point_Y();
                            f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Butter_List.get(i).set_play_Attack();
                                ground_List.get(j).set_Ground_Speed(0);
                            }





                        }


                        if(skill_Butter_List.get(i).get_Live()){
//                skill_Butter_List.remove(i);
                            soundPool.play(sound_Effect[27], pop_Drag/5, pop_Drag/5, 0, 0, 1.0F);   //버터
                            skill_Butter_List.get(i).set_Remove();
                        }


                    }

                    for(int i=skill_Butter_List.size()-1; i>=0; i--){
                        if(skill_Butter_List.get(i).get_Remove()){
                            skill_Butter_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","버터 그리기 부분");
                }


                try{

                    //튀김 지뢰
                    for(int i=0; i<skill_Fry_List.size(); i++){


                        if(skill_Fry_List.get(i).get_Skill_Status() <= 2) {
                            draw.draw_Bmp(canvas, skill_fry_img[skill_Fry_List.get(i).get_Skill_Status()], skill_Fry_List.get(i).get_X_Point() - convertPixelsToDp(15, _context), skill_Fry_List.get(i).get_Y_Point());
                        }else{
                            draw.draw_Bmp(canvas, skill_fry_img[skill_Fry_List.get(i).get_Skill_Status()], skill_Fry_List.get(i).get_X_Point() - convertPixelsToDp(45, _context), skill_Fry_List.get(i).get_Y_Point() - convertPixelsToDp(25, _context));
                        }


                        skill_Fry_List.get(i).set_Skill_Move();

                        for (int j = 0; j < fish_List.size(); j++) {
                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }

                            s_x = skill_Fry_List.get(i).get_X_Point() - convertPixelsToDp(15, _context);
                            s_x_w = skill_Fry_List.get(i).get_X_Point() + skill_fry_img[0].getWidth() - convertPixelsToDp(15, _context);
                            s_y = skill_Fry_List.get(i).get_Y_Point();
                            s_y_h = skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[0].getHeight();

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Fry_List.get(i).set_play_Attack();
                            }


                        }

                        if(skill_Fry_List.get(i).get_Play_Attack()) {

                            for (int j = 0; j < fish_List.size(); j++) {
                                if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                    continue;
                                }

                                s_x = skill_Fry_List.get(i).get_X_Point() - convertPixelsToDp(45, _context);
                                s_x_w = skill_Fry_List.get(i).get_X_Point() + skill_fry_img[3].getWidth() - convertPixelsToDp(45, _context);
                                s_y = skill_Fry_List.get(i).get_Y_Point() - convertPixelsToDp(25, _context);
                                s_y_h = skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[3].getHeight() - convertPixelsToDp(25, _context);

                                f_x = fish_List.get(j).get_Fish_Point_X();
                                f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                                f_y = fish_List.get(j).get_Fish_Point_Y();
                                f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    skill_Plus_D = 0;
                                    if(st7 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) st7);
                                    }
                                    fish_List.get(j).set_Hp_Minus(35 + skill_Plus_D);
                                }


                            }
                        }


                        for (int j = 0; j < ground_List.size(); j++) {

                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }


                            s_x = skill_Fry_List.get(i).get_X_Point() - convertPixelsToDp(15, _context);
                            s_x_w = skill_Fry_List.get(i).get_X_Point() + skill_fry_img[0].getWidth() - convertPixelsToDp(15, _context);
                            s_y = skill_Fry_List.get(i).get_Y_Point();
                            s_y_h = skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[0].getHeight();

                            f_x = ground_List.get(j).get_Ground_Point_X();
                            f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                            f_y = ground_List.get(j).get_Ground_Point_Y();
                            f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Fry_List.get(i).set_play_Attack();
                            }


                        }

                        if(skill_Fry_List.get(i).get_Play_Attack()) {

                            for (int j = 0; j < ground_List.size(); j++) {

                                if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                    continue;
                                }

                                s_x = skill_Fry_List.get(i).get_X_Point() - convertPixelsToDp(45, _context);
                                s_x_w = skill_Fry_List.get(i).get_X_Point() + skill_fry_img[3].getWidth() - convertPixelsToDp(45, _context);
                                s_y = skill_Fry_List.get(i).get_Y_Point() - convertPixelsToDp(25, _context);
                                s_y_h = skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[3].getHeight()- convertPixelsToDp(25, _context);

                                f_x = ground_List.get(j).get_Ground_Point_X();
                                f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                                f_y = ground_List.get(j).get_Ground_Point_Y();
                                f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    if((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                        if(!((Ground_Touch_Hermit)ground_List.get(j)).get_Immortal_Mode()) {
                                            ground_List.get(j).set_Ground_Hp_Minus(1);
                                        }
                                    }else {
                                        skill_Plus_D = 0;
                                        if(st7 >= 1 ){
                                            skill_Plus_D = random.nextInt((int) st7);
                                        }
                                        ground_List.get(j).set_Ground_Hp_Minus(35 + skill_Plus_D);
                                    }
                                }


                            }
                        }

                        if(skill_Fry_List.get(i).get_Delete_Attack()){
//                skill_Fry_List.remove(i);
                            soundPool.play(sound_Effect[19], pop_Drag, pop_Drag, 0, 0, 1.0F);
                            skill_Fry_List.get(i).set_Remove();
                        }


                    }
                    for(int i=skill_Fry_List.size()-1; i>=0; i--){
                        if(skill_Fry_List.get(i).get_Remove()){
                            skill_Fry_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","튀김지뢰 그리기 부분");
                }

                try{
                    //바다뱀 소환
                    for(int i=0; i<skill_Sea_Snake_List.size(); i++){

                        draw.draw_Bmp(canvas, skill_Sea_Snake_img[0], skill_Sea_Snake_List.get(i).get_X_Point(), skill_Sea_Snake_List.get(i).get_Y_Point());
                        skill_Sea_Snake_List.get(i).set_Skill_Move(convertPixelsToDp(60, _context));





                        for(int j=0; j<fish_List.size(); j++){
                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }

                            s_x = skill_Sea_Snake_List.get(i).get_X_Point();
                            s_x_w = skill_Sea_Snake_List.get(i).get_X_Point() + skill_Sea_Snake_img[0].getWidth();
                            s_y = skill_Sea_Snake_List.get(i).get_Y_Point();
                            s_y_h = skill_Sea_Snake_List.get(i).get_Y_Point() + skill_Sea_Snake_img[0].getHeight();

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Plus_D = 0;
                                if(ft9 >= 1 ){
                                    skill_Plus_D = random.nextInt((int) ft9);
                                }
                                fish_List.get(j).set_Hp_Minus(25 + skill_Plus_D);
                                if(j%2 == 0) {
                                    soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                                }
                            }






                        }
                        for(int j=0; j<ground_List.size(); j++){

                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }

                            s_x = skill_Sea_Snake_List.get(i).get_X_Point();
                            s_x_w = skill_Sea_Snake_List.get(i).get_X_Point() + skill_Sea_Snake_img[0].getWidth();
                            s_y = skill_Sea_Snake_List.get(i).get_Y_Point();
                            s_y_h = skill_Sea_Snake_List.get(i).get_Y_Point() + skill_Sea_Snake_img[0].getHeight();

                            f_x = ground_List.get(j).get_Ground_Point_X();
                            f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                            f_y = ground_List.get(j).get_Ground_Point_Y();
                            f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                if((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                    if(!((Ground_Touch_Hermit)ground_List.get(j)).get_Immortal_Mode()) {
                                        ground_List.get(j).set_Ground_Hp_Minus(1);
                                    }
                                }else {
                                    skill_Plus_D = 0;
                                    if(ft9 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) ft9);
                                    }
                                    ground_List.get(j).set_Ground_Hp_Minus(25 + skill_Plus_D);
                                    if(j%2 == 0) {
                                        soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                                    }
                                }


                            }


                        }



                        if(skill_Sea_Snake_List.get(i).get_Live()){
//                skill_Sea_Snake_List.remove(i);
                            skill_Sea_Snake_List.get(i).set_Remove();
                        }


                    }
                    for(int i=skill_Sea_Snake_List.size()-1; i>=0; i--){
                        if(skill_Sea_Snake_List.get(i).get_Remove()){

                            skill_Sea_Snake_List.remove(i);
                        }
                    }


                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","바다범 그리기 부분");
                }


                try{
                    //파도 소환
                    for(int i=0; i<skill_Wave_List.size(); i++){

                        draw.draw_Bmp(canvas, skill_Wave_img[skill_Wave_List.get(i).get_Skill_Status()], skill_Wave_List.get(i).get_X_Point(), skill_Wave_List.get(i).get_Y_Point());

                        skill_Wave_List.get(i).set_Skill_Move(convertPixelsToDp(30, _context));



                        for(int j=0; j<fish_List.size(); j++){
                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }


                            s_x = skill_Wave_List.get(i).get_X_Point();
                            s_x_w = skill_Wave_List.get(i).get_X_Point() + skill_Wave_img[0].getWidth();
                            s_y = skill_Wave_List.get(i).get_Y_Point();
                            s_y_h = skill_Wave_List.get(i).get_Y_Point() + skill_Wave_img[0].getHeight();

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Plus_D = 0;
                                if(st9 >= 1 ){
                                    skill_Plus_D = random.nextInt((int) st9);
                                }
                                fish_List.get(j).set_Hp_Minus(10 + skill_Plus_D);
                                if(j%2 == 0) {
                                    soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                                }
                            }


                        }


                        for(int j=0; j<ground_List.size(); j++){

                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }

                            s_x = skill_Wave_List.get(i).get_X_Point();
                            s_x_w = skill_Wave_List.get(i).get_X_Point() + skill_Wave_img[0].getWidth();
                            s_y = skill_Wave_List.get(i).get_Y_Point();
                            s_y_h = skill_Wave_List.get(i).get_Y_Point() + skill_Wave_img[0].getHeight();

                            f_x = ground_List.get(j).get_Ground_Point_X();
                            f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                            f_y = ground_List.get(j).get_Ground_Point_Y();
                            f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                if((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                    if(!((Ground_Touch_Hermit)ground_List.get(j)).get_Immortal_Mode()) {
                                        ground_List.get(j).set_Ground_Hp_Minus(1);
                                    }
                                }else {
                                    skill_Plus_D = 0;
                                    if(st9 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) st9);
                                    }
                                    ground_List.get(j).set_Ground_Hp_Minus(10 + skill_Plus_D);
                                }
                                if(j%2 == 0) {
                                    soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);
                                }
                            }



                        }



                        if(skill_Wave_List.get(i).get_Live()){
//                skill_Wave_List.remove(i);
                            skill_Wave_List.get(i).set_Remove();
                        }
                    }
                    for(int i=skill_Wave_List.size()-1; i>=0; i--){
                        if(skill_Wave_List.get(i).get_Remove()){
                            skill_Wave_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","파도소환 그리기 부분");
                }

                try{
                    //벽 소환
                    for(int i=0; i<skill_Wall_List.size(); i++){

                        draw.draw_Bmp(canvas, skill_wall_img[skill_Wall_List.get(i).get_Skill_Status()], skill_Wall_List.get(i).get_X_Point(), skill_Wall_List.get(i).get_Y_Point()  + convertPixelsToDp(30, _context) );
                        skill_Wall_List.get(i).set_Skill_Move();


                        for(int j=0; j<fish_List.size(); j++){

                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }

                            s_x = skill_Wall_List.get(i).get_X_Point();
                            s_x_w = skill_Wall_List.get(i).get_X_Point() + skill_wall_img[3].getWidth();
                            s_y = skill_Wall_List.get(i).get_Y_Point() + convertPixelsToDp(30, _context);
                            s_y_h = skill_Wall_List.get(i).get_Y_Point() + skill_wall_img[3].getHeight() + convertPixelsToDp(30, _context);

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                fish_List.get(j).set_Fish_Speed(0);
//                                break;
                            }


                        }
                        for(int j=0; j<ground_List.size(); j++){

                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }

                            s_x = skill_Wall_List.get(i).get_X_Point();
                            s_x_w = skill_Wall_List.get(i).get_X_Point() + skill_wall_img[3].getWidth();
                            s_y = skill_Wall_List.get(i).get_Y_Point() + convertPixelsToDp(30, _context);
                            s_y_h = skill_Wall_List.get(i).get_Y_Point() + skill_wall_img[3].getHeight() + convertPixelsToDp(30, _context);

                            f_x = ground_List.get(j).get_Ground_Point_X();
                            f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                            f_y = ground_List.get(j).get_Ground_Point_Y();
                            f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                ground_List.get(j).set_Ground_Speed(0);
//                                break;
                            }


                        }


                        if(skill_Wall_List.get(i).get_Live()){
//                skill_Wall_List.remove(i);
                            skill_Wall_List.get(i).set_Remove();
                        }

                    }
                    for(int i=skill_Wall_List.size()-1; i>=0; i--){
                        if(skill_Wall_List.get(i).get_Remove()){
                            skill_Wall_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","벽 그리기 부분");
                }

                try{
                    //가시2 소환
                    for(int i=0; i<skill_Thorn2_List.size(); i++){

                        draw.draw_Bmp(canvas, skill_Thorn2_img[skill_Thorn2_List.get(i).get_Skill_Status()], skill_Thorn2_List.get(i).get_X_Point(), skill_Thorn2_List.get(i).get_Y_Point());
                        skill_Thorn2_List.get(i).set_Skill_Move(convertPixelsToDp(45, _context));



                        for(int j=0; j<fish_List.size(); j++){


                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }

                            s_x = skill_Thorn2_List.get(i).get_X_Point();
                            s_x_w = skill_Thorn2_List.get(i).get_X_Point() + skill_Thorn2_img[0].getWidth();
                            s_y = skill_Thorn2_List.get(i).get_Y_Point();
                            s_y_h = skill_Thorn2_List.get(i).get_Y_Point() + skill_Thorn2_img[0].getHeight();

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Plus_D = 0;
                                if(st6 >= 1 ){
                                    skill_Plus_D = random.nextInt((int) st6);
                                }
                                fish_List.get(j).set_Hp_Minus(10 + skill_Plus_D);
                                if(j%2 == 0) {
                                    soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                                }
                                skill_Thorn2_List.get(i).set_Live();
                            }

                        }

                        for(int j=0; j<ground_List.size(); j++){


                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }
                            //조개 통과
                            if(ground_List.get(j) instanceof Ground_Drag_Clam){
                                continue;
                            }
                            if(!(ground_List.get(j) instanceof Land_Mark)) {

                                s_x = skill_Thorn2_List.get(i).get_X_Point();
                                s_x_w = skill_Thorn2_List.get(i).get_X_Point() + skill_Thorn2_img[0].getWidth();
                                s_y = skill_Thorn2_List.get(i).get_Y_Point();
                                s_y_h = skill_Thorn2_List.get(i).get_Y_Point() + skill_Thorn2_img[0].getHeight();

                                f_x = ground_List.get(j).get_Ground_Point_X();
                                f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                                f_y = ground_List.get(j).get_Ground_Point_Y();
                                f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    if((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                        if(!((Ground_Touch_Hermit)ground_List.get(j)).get_Immortal_Mode()) {
                                            ground_List.get(j).set_Ground_Hp_Minus(1);
                                        }
                                    }else {
                                        skill_Plus_D = 0;
                                        if(ft10 >= 1 ){
                                            skill_Plus_D = random.nextInt((int) ft10);
                                        }
                                        ground_List.get(j).set_Ground_Hp_Minus(10 + skill_Plus_D);
                                    }
                                    if(j%2 == 0) {
                                        soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                                    }
                                        skill_Thorn2_List.get(i).set_Live();
                                }




                            }
                        }


//

                        if(skill_Thorn2_List.get(i).get_Live()){
//                skill_Thorn2_List.remove(i);
                            skill_Thorn2_List.get(i).set_Remove();
                        }

                    }
                    for(int i=skill_Thorn2_List.size()-1; i>=0; i--){
                        if(skill_Thorn2_List.get(i).get_Remove()){
                            skill_Thorn2_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","가시2 그리기 부분");
                }


                try{

                    //번개 발사
                    for(int i=0; i<skill_Lightning_List.size(); i++){

                        if(skill_Lightning_List.get(i).get_play_Attack()){
                            skill_Lightning_List.get(i).set_Up_State();

                            skill_Lightning_List.get(i).set_X_Point(skill_Lightning_List.get(i).get_X_Point() - convertPixelsToDp(-10 + random.nextInt(40), _context) );
                            skill_Lightning_List.get(i).set_Y_Point(skill_Lightning_List.get(i).get_Y_Point() - convertPixelsToDp(10, _context) );

                        }else {
                            skill_Lightning_List.get(i).set_Skill_Move(convertPixelsToDp(30, _context));
                        }
                        draw.draw_Bmp(canvas, skill_lightnign_img[skill_Lightning_List.get(i).get_Skill_Status()], skill_Lightning_List.get(i).get_X_Point(), skill_Lightning_List.get(i).get_Y_Point());




                        for(int j=0; j<fish_List.size(); j++){
                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }


                            s_x = skill_Lightning_List.get(i).get_X_Point();
                            s_x_w = skill_Lightning_List.get(i).get_X_Point() + skill_lightnign_img[0].getWidth();
                            s_y = skill_Lightning_List.get(i).get_Y_Point();
                            s_y_h = skill_Lightning_List.get(i).get_Y_Point() + skill_lightnign_img[0].getHeight();

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Plus_D = 0;
                                if(ft7 >= 1 ){
                                    skill_Plus_D = random.nextInt((int) ft7);
                                }
                                fish_List.get(j).set_Hp_Minus(30 + skill_Plus_D);
                                if(j%2 == 0) {
                                    soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                                }
                                skill_Lightning_List.get(i).set_play_Attack();
                            }



                        }

                        if(skill_Lightning_List.get(i).get_play_Attack()){


                            for(int j=0; j<fish_List.size(); j++){
                                if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                    continue;
                                }

                                s_x = skill_Lightning_List.get(i).get_X_Point();
                                s_x_w = skill_Lightning_List.get(i).get_X_Point() + skill_lightnign_img[3].getWidth();
                                s_y = skill_Lightning_List.get(i).get_Y_Point();
                                s_y_h = skill_Lightning_List.get(i).get_Y_Point() + skill_lightnign_img[3].getHeight();

                                f_x = fish_List.get(j).get_Fish_Point_X();
                                f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                                f_y = fish_List.get(j).get_Fish_Point_Y();
                                f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    skill_Plus_D = 0;
                                    if(ft7 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) ft7);
                                    }
                                    fish_List.get(j).set_Hp_Minus(30 + skill_Plus_D);
                                }


//
                            }
                        }

                        for(int j=0; j<ground_List.size(); j++){

                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }
                            if((ground_List.get(j) instanceof Land_Mark) || (ground_List.get(j) instanceof Ground_Drag_Clam)) {
                                continue;
                            }

                            s_x = skill_Lightning_List.get(i).get_X_Point();
                            s_x_w = skill_Lightning_List.get(i).get_X_Point() + skill_lightnign1_img[3].getWidth();
                            s_y = skill_Lightning_List.get(i).get_Y_Point();
                            s_y_h = skill_Lightning_List.get(i).get_Y_Point() + skill_lightnign1_img[3].getHeight();

                            f_x = ground_List.get(j).get_Ground_Point_X();
                            f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                            f_y = ground_List.get(j).get_Ground_Point_Y();
                            f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Lightning_List.get(i).set_play_Attack();
                            }





                        }




                        if(skill_Lightning_List.get(i).get_play_Attack()){
                            for(int j=0; j<ground_List.size(); j++){

                                if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                    continue;
                                }

                                s_x = skill_Lightning_List.get(i).get_X_Point();
                                s_x_w = skill_Lightning_List.get(i).get_X_Point() + skill_lightnign1_img[3].getWidth();
                                s_y = skill_Lightning_List.get(i).get_Y_Point();
                                s_y_h = skill_Lightning_List.get(i).get_Y_Point() + skill_lightnign1_img[3].getHeight();

                                f_x = ground_List.get(j).get_Ground_Point_X();
                                f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                                f_y = ground_List.get(j).get_Ground_Point_Y();
                                f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    if((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                        if(!((Ground_Touch_Hermit)ground_List.get(j)).get_Immortal_Mode()) {
                                            ground_List.get(j).set_Ground_Hp_Minus(1);
                                        }
                                    }else {
                                        skill_Plus_D = 0;
                                        if(ft7 >= 1 ){
                                            skill_Plus_D = random.nextInt((int) ft7);
                                        }
                                        ground_List.get(j).set_Ground_Hp_Minus(30 + skill_Plus_D);
                                    }
                                }

                            }
                        }

                        if(skill_Lightning_List.get(i).get_Live()){
//                skill_Lightning_List.remove(i);
                            if(skill_Lightning_List.get(i).get_play_Attack()){
                                soundPool.play(sound_Effect[29], pop_Drag, pop_Drag, 0, 0, 1.0F);
                            }
                            skill_Lightning_List.get(i).set_Remove();
                        }

                    }
                    for(int i=skill_Lightning_List.size()-1; i>=0; i--){
                        if(skill_Lightning_List.get(i).get_Remove()){
                            skill_Lightning_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","번개 그리기 부분");
                }

                try{
                    //번개2 발사
                    for(int i=0; i<skill_Lightning2_List.size(); i++){

                        if(skill_Lightning2_List.get(i).get_play_Attack()){
                            skill_Lightning2_List.get(i).set_Up_State();

                            skill_Lightning2_List.get(i).set_X_Point(skill_Lightning2_List.get(i).get_X_Point() - convertPixelsToDp(-10 + random.nextInt(40), _context) );
                            skill_Lightning2_List.get(i).set_Y_Point(skill_Lightning2_List.get(i).get_Y_Point() - convertPixelsToDp(10, _context) );

                        }else {
                            skill_Lightning2_List.get(i).set_Skill_Move(convertPixelsToDp(30, _context));
                        }
                        draw.draw_Bmp(canvas, skill_lightnign1_img[skill_Lightning2_List.get(i).get_Skill_Status()], skill_Lightning2_List.get(i).get_X_Point(), skill_Lightning2_List.get(i).get_Y_Point());


                        for(int j=0; j<fish_List.size(); j++){

                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }

                            s_x = skill_Lightning2_List.get(i).get_X_Point();
                            s_x_w = skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[0].getWidth();
                            s_y = skill_Lightning2_List.get(i).get_Y_Point();
                            s_y_h = skill_Lightning2_List.get(i).get_Y_Point() + skill_lightnign1_img[0].getHeight();

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Plus_D = 0;
                                if(ft7 >= 1 ){
                                    skill_Plus_D = random.nextInt((int) ft7);
                                }
                                   fish_List.get(j).set_Hp_Minus(50  + skill_Plus_D);
                                if(j%2 == 0) {
                                    soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                                }
                                    skill_Lightning2_List.get(i).set_play_Attack();
                            }



                        }

                        if(skill_Lightning2_List.get(i).get_play_Attack()){


                            for(int j=0; j<fish_List.size(); j++){
                                if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                    continue;
                                }

                                s_x = skill_Lightning2_List.get(i).get_X_Point();
                                s_x_w = skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[3].getWidth();
                                s_y = skill_Lightning2_List.get(i).get_Y_Point();
                                s_y_h = skill_Lightning2_List.get(i).get_Y_Point() + skill_lightnign1_img[3].getHeight();

                                f_x = fish_List.get(j).get_Fish_Point_X();
                                f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                                f_y = fish_List.get(j).get_Fish_Point_Y();
                                f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    skill_Plus_D = 0;
                                    if(ft8 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) ft8);
                                    }
                                    fish_List.get(j).set_Hp_Minus(40 + skill_Plus_D);
                                }

                            }
                        }

                        for(int j=0; j<ground_List.size(); j++){

                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }
                            if((ground_List.get(j) instanceof Land_Mark) || (ground_List.get(j) instanceof Ground_Drag_Clam)) {
                                continue;
                            }

                            s_x = skill_Lightning2_List.get(i).get_X_Point();
                            s_x_w = skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[0].getWidth();
                            s_y = skill_Lightning2_List.get(i).get_Y_Point();
                            s_y_h = skill_Lightning2_List.get(i).get_Y_Point() + skill_lightnign1_img[0].getHeight();

                            f_x = ground_List.get(j).get_Ground_Point_X();
                            f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                            f_y = ground_List.get(j).get_Ground_Point_Y();
                            f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                skill_Lightning2_List.get(i).set_play_Attack();
                            }

                        }




                        if(skill_Lightning2_List.get(i).get_play_Attack()){
                            for(int j=0; j<ground_List.size(); j++){

                                if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                    continue;
                                }

                                s_x = skill_Lightning2_List.get(i).get_X_Point();
                                s_x_w = skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[3].getWidth();
                                s_y = skill_Lightning2_List.get(i).get_Y_Point();
                                s_y_h = skill_Lightning2_List.get(i).get_Y_Point() + skill_lightnign1_img[3].getHeight();

                                f_x = ground_List.get(j).get_Ground_Point_X();
                                f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                                f_y = ground_List.get(j).get_Ground_Point_Y();
                                f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    if((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                        if(!((Ground_Touch_Hermit)ground_List.get(j)).get_Immortal_Mode()) {
                                            ground_List.get(j).set_Ground_Hp_Minus(1);
                                        }
                                    }else {
                                        skill_Plus_D = 0;
                                        if(ft8 >= 1 ){
                                            skill_Plus_D = random.nextInt((int) ft8);
                                        }
                                        ground_List.get(j).set_Ground_Hp_Minus(40 + skill_Plus_D);
                                    }
                                }

                            }
                        }

                        if(skill_Lightning2_List.get(i).get_Live()){
//                skill_Lightning2_List.remove(i);
                            skill_Lightning2_List.get(i).set_Remove();
                            if(skill_Lightning2_List.get(i).get_play_Attack()) {
                                soundPool.play(sound_Effect[30], pop_Drag, pop_Drag, 0, 0, 1.0F);
                            }
                        }

                    }
                    for(int i=skill_Lightning2_List.size()-1; i>=0; i--){
                        if(skill_Lightning2_List.get(i).get_Remove()){
                            skill_Lightning2_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","번개2 그리기 부분");
                }

                try{
                    //포크 소환
                    for(int i=0; i<skill_Fork_List.size(); i++){

                        draw.draw_Bmp(canvas, skill_Fork_img[skill_Fork_List.get(i).get_Skill_Status()], skill_Fork_List.get(i).get_X_Point(), skill_Fork_List.get(i).get_Y_Point());
                        skill_Fork_List.get(i).set_Skill_Move(convertPixelsToDp(40, _context));


                        if(skill_Fork_List.get(i).get_Live()){

                            skill_Fork_List.get(i).set_Remove();
                            soundPool.play(sound_Effect[26], pop_Touch, pop_Touch, 0, 0, 1.0F);

                            try{
                                //물고기 생명체
                                if(skill_Fork_List.get(i).get_Aim_Species() == 1){
                                    fish_List.get(skill_Fork_List.get(i).get_Aim_Fish()).set_Hp_Minus(25  + random.nextInt((int)mt5));
                                    break;
                                }

                                //그라운드 생명체
                                if(skill_Fork_List.get(i).get_Aim_Species() == 2){
                                    ground_List.get(skill_Fork_List.get(i).get_Aim_Ground()).set_Ground_Hp_Minus(25  + random.nextInt((int)mt5));
                                    break;
                                }
                            }catch (Exception e){

                            }


//                skill_Fork_List.remove(i);

                        }

                    }
                    for(int i=skill_Fork_List.size()-1; i>=0; i--){
                        if(skill_Fork_List.get(i).get_Live()){
                            skill_Fork_List.remove(i);
                        }
                    }


                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","포크 그리기 부분");
                }

                try{
                    //독 폭탄
                    for(int i=0; i<skill_Boom_Poison_List.size(); i++){



                        draw.draw_Bmp(canvas, skill_Boom_Poison_img[skill_Boom_Poison_List.get(i).get_Skill_Status()], skill_Boom_Poison_List.get(i).get_X_Point(), skill_Boom_Poison_List.get(i).get_Y_Point());
                        skill_Boom_Poison_List.get(i).set_Skill_Move(convertPixelsToDp(30, _context));

                        if(skill_Boom_Poison_List.get(i).get_Skill_Status() >= 3){


                            for(int j=0; j<fish_List.size(); j++){
                                if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                    continue;
                                }

                                s_x = skill_Boom_Poison_List.get(i).get_X_Point();
                                s_x_w = skill_Boom_Poison_List.get(i).get_X_Point() + skill_Boom_Poison_img[3].getWidth();
                                s_y = skill_Boom_Poison_List.get(i).get_Y_Point();
                                s_y_h = skill_Boom_Poison_List.get(i).get_Y_Point() + skill_Boom_Poison_img[3].getHeight();

                                f_x = fish_List.get(j).get_Fish_Point_X();
                                f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                                f_y = fish_List.get(j).get_Fish_Point_Y();
                                f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    skill_Plus_D = 0;
                                    if(mt8 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) mt8);
                                    }
                                    fish_List.get(j).set_Status_Poison(10 + skill_Plus_D);

                                }


                            }

                            for(int j=0; j<ground_List.size(); j++){

                                if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                    continue;
                                }

                                s_x = skill_Boom_Poison_List.get(i).get_X_Point();
                                s_x_w = skill_Boom_Poison_List.get(i).get_X_Point() + skill_Boom_Poison_img[3].getWidth();
                                s_y = skill_Boom_Poison_List.get(i).get_Y_Point();
                                s_y_h = skill_Boom_Poison_List.get(i).get_Y_Point() + skill_Boom_Poison_img[3].getHeight();

                                f_x = ground_List.get(j).get_Ground_Point_X();
                                f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                                f_y = ground_List.get(j).get_Ground_Point_Y();
                                f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    skill_Plus_D = 0;
                                    if(mt8 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) mt8);
                                    }
                                    ground_List.get(j).set_Status_Poison(10 + skill_Plus_D);

                                }

                            }
                        }



                        if(skill_Boom_Poison_List.get(i).get_Live()){
                            skill_Boom_Poison_List.get(i).set_Remove();
//                skill_Boom_Poison_List.remove(i);
                        }

                    }

                    for(int i=skill_Boom_Poison_List.size()-1; i>=0; i--){
                        if(skill_Boom_Poison_List.get(i).get_Remove()){
                            skill_Boom_Poison_List.remove(i);
                        }
                    }


                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","독폭탄 그리기 부분");
                }



                try{
                    //느려지는 구름 생성
                    for(int i=0; i< skill_Slow_Cloud_List.size(); i++){

//            pop_Temp_img = draw.rotate_Image(skill_Slow_Cloud_img, skill_Slow_Cloud_List.get(i).getAngle());
                        draw.draw_Bmp(canvas, skill_Slow_Cloud_img, skill_Slow_Cloud_List.get(i).get_X_Point(), skill_Slow_Cloud_List.get(i).get_Y_Point());

                        skill_Slow_Cloud_List.get(i).set_Skill_Move();









                        for(int j=0; j<fish_List.size(); j++){
                            if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                continue;
                            }


                            s_x = skill_Slow_Cloud_List.get(i).get_X_Point();
                            s_x_w = skill_Slow_Cloud_List.get(i).get_X_Point() + skill_Slow_Cloud_img.getWidth();
                            s_y = skill_Slow_Cloud_List.get(i).get_Y_Point();
                            s_y_h = skill_Slow_Cloud_List.get(i).get_Y_Point() + skill_Slow_Cloud_img.getHeight();

                            f_x = fish_List.get(j).get_Fish_Point_X();
                            f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                            f_y = fish_List.get(j).get_Fish_Point_Y();
                            f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                fish_List.get(j).set_Fish_Speed(1);

                            }

                        }
                        for(int j=0; j<ground_List.size(); j++){

                            if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                continue;
                            }

                            s_x = skill_Slow_Cloud_List.get(i).get_X_Point();
                            s_x_w = skill_Slow_Cloud_List.get(i).get_X_Point() + skill_Slow_Cloud_img.getWidth();
                            s_y = skill_Slow_Cloud_List.get(i).get_Y_Point();
                            s_y_h = skill_Slow_Cloud_List.get(i).get_Y_Point() + skill_Slow_Cloud_img.getHeight();

                            f_x = ground_List.get(j).get_Ground_Point_X();
                            f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                            f_y = ground_List.get(j).get_Ground_Point_Y();
                            f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                            if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                            }else {
                                ground_List.get(j).set_Ground_Speed(1);

                            }




                        }
                        if(skill_Slow_Cloud_List.get(i).get_Live()){
//                skill_Slow_Cloud_List.remove(i);
                            skill_Slow_Cloud_List.get(i).set_Remove();
                        }

                    }
                    for(int i=skill_Slow_Cloud_List.size()-1; i>=0 ; i--){
                        if(skill_Slow_Cloud_List.get(i).get_Remove()){
                            skill_Slow_Cloud_List.remove(i);
                        }
                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","슬로우 구름 그리기 부분");
                }

                try{
                    //레이저
                    for(int i = skill_Laser_List.size()-1; i >= 0 ; i--){

                        draw.draw_Bmp(canvas, skill_Laser_img, skill_Laser_List.get(i).get_X_Point(), skill_Laser_List.get(i).get_Y_Point());
                        skill_Laser_List.get(i).set_Skill_Move(convertPixelsToDp(100, _context));


                        if(skill_Laser_List.get(i).get_Live()){

                            skill_Laser_List.remove(i);
                        }else  //레이저 안에 있을때 대미지
                        {
                            for(int j=0; j<fish_List.size(); j++){
                                if(!fish_List.get(j).get_Visible_Fish_Flag()){
                                    continue;
                                }

                                s_x = skill_Laser_List.get(i).get_X_Point();
                                s_x_w = skill_Laser_List.get(i).get_X_Point() + skill_Laser_img.getWidth();
                                s_y = skill_Laser_List.get(i).get_Y_Point();
                                s_y_h = skill_Laser_List.get(i).get_Y_Point() + skill_Laser_img.getHeight();

                                f_x = fish_List.get(j).get_Fish_Point_X();
                                f_x_w = fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size();
                                f_y = fish_List.get(j).get_Fish_Point_Y();
                                f_y_h = fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    skill_Plus_D = 0;
                                    if(mt6 >= 1 ){
                                        skill_Plus_D = random.nextInt((int) mt6);
                                    }
                                    fish_List.get(j).set_Hp_Minus(7 + skill_Plus_D);
                                }




                            }
                            for(int j=0; j<ground_List.size(); j++){

                                if(!ground_List.get(j).get_Visible_Ground_Flag()){
                                    continue;
                                }


                                s_x = skill_Laser_List.get(i).get_X_Point();
                                s_x_w = skill_Laser_List.get(i).get_X_Point() + skill_Laser_img.getWidth();
                                s_y = skill_Laser_List.get(i).get_Y_Point();
                                s_y_h = skill_Laser_List.get(i).get_Y_Point() + skill_Laser_img.getHeight();

                                f_x = ground_List.get(j).get_Ground_Point_X();
                                f_x_w = ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size();
                                f_y = ground_List.get(j).get_Ground_Point_Y();
                                f_y_h = ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size();



                                if((s_x > f_x_w) || (s_x_w<f_x) || (s_y>f_y_h) || (s_y_h < f_y)){

                                }else {
                                    if ((ground_List.get(j) instanceof Ground_Touch_Hermit)) {
                                        if (!((Ground_Touch_Hermit) ground_List.get(j)).get_Immortal_Mode()) {
                                            ground_List.get(j).set_Ground_Hp_Minus(1);
                                        }
                                    } else {
                                        skill_Plus_D = 0;
                                        if (mt6 >= 1) {
                                            skill_Plus_D = random.nextInt((int) mt6);
                                        }
                                        ground_List.get(j).set_Ground_Hp_Minus(7 + skill_Plus_D);
                                    }
                                }



                            }




                        }

                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","레이저 그리기 부분");
                }


                try {
                    /**
                     * 오징어 먹물 이벤트
                     */
                    if (Background_Effect_Squid_Ink_Arr.size() > 0) {
                        for (int i = 0; i < Background_Effect_Squid_Ink_Arr.size(); i++) {


                            Background_Effect_Squid_Ink_Arr.get(i).Background_Effect_Move_Pattern();    //오징어 먹묵이팩트 움직임
                            draw.draw_Bmp(canvas, effect_Background_Squid_Ink_img[Background_Effect_Squid_Ink_Arr.get(i).get_Draw_Background_Effect_Status()],
                                    Background_Effect_Squid_Ink_Arr.get(i).get_Background_Point_X(),
                                    Background_Effect_Squid_Ink_Arr.get(i).get_Background_Point_Y() + convertPixelsToDp(30, _context));

                        }

                        //먹물 시간다되면 삭제
                        for (int i = Background_Effect_Squid_Ink_Arr.size()-1; i >= 0; i--) {
                            if (!Background_Effect_Squid_Ink_Arr.get(i).up_Continue_Time()) {
                                Background_Effect_Squid_Ink_Arr.remove(i);
                                break;
                            }
                        }


                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","오징어 먹물 그리기 부분");
                }






                try{

                    //경고 그리기

//            int warning_Time = 0;
//            boolean warning_Flag = false;

                    if(warning_Flag) {
                        if (warning_Time > 255) {
                            warning_Time = 255;
                        }

                        paint.setAlpha(warning_Time);
                        if (warning_Time >= 255) {
                            warning_Add_Flag = false;
                            warning_Time_Cycle++;

                            if(warning_Time_Cycle > 6){
                                warning_Time_Cycle = 0;
                                warning_Flag = false;
                                warning_Marlin_Flag = false;
                            }

                        } else if (warning_Time <= 100) {
                            warning_Add_Flag = true;
                        }

                        if (warning_Add_Flag) {
                            warning_Time += 25;
                        } else {
                            warning_Time -= 25;
                        }


                        if(warning_Marlin_Flag){
//                    warning_img
                            function_Warning_Marlin();
                        }else if(warning_Wave_Flag){
                            function_Warning_Wave();
                        }else if(warning_Ell_Flag){
                            function_Warning_Ell();
                        }

                        canvas.drawBitmap(warning_img, null,
                                new Rect(window_Width / 2 - convertPixelsToDp(95, _context),
                                        window_Height / 2 - convertPixelsToDp(90, _context),
                                        window_Width / 2 - convertPixelsToDp(95, _context) + warning_img.getWidth(),
                                        window_Height / 2 - convertPixelsToDp(90, _context) + warning_img.getHeight()), paint);
                    }
//            draw.draw_Bmp(canvas, warning_img,
//                    window_Width / 2 - convertPixelsToDp(150, _context),
//                    window_Height / 2 - convertPixelsToDp(145, _context));


                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a"," 경고 그리기 부분");
                }





                try{

                    //나인패치 적용한 hp 그리기
                    if(main_Character.get_Max_Hp() == 1){

                        if(main_Character.get_Hp() == 0){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_1);
                        }else if(main_Character.get_Hp() == 1){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_2);
                        }

                    }else if(main_Character.get_Max_Hp() == 2){

                        if(main_Character.get_Hp() == 0){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_3);
                        }else if(main_Character.get_Hp() == 1){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_4);
                        }else if(main_Character.get_Hp() == 2){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_5);
                        }

                    }else if(main_Character.get_Max_Hp() == 3){

                        if(main_Character.get_Hp() == 0){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_6);
                        }else if(main_Character.get_Hp() == 1){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_7);
                        }else if(main_Character.get_Hp() == 2){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_8);
                        }else if(main_Character.get_Hp() == 3){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_9);
                        }

                    }else if(main_Character.get_Max_Hp() == 4){

                        if(main_Character.get_Hp() == 0){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_10);
                        }else if(main_Character.get_Hp() == 1){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_11);
                        }else if(main_Character.get_Hp() == 2){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_12);
                        }else if(main_Character.get_Hp() == 3){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_13);
                        }else if(main_Character.get_Hp() == 4){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_14);
                        }

                    }else if(main_Character.get_Max_Hp() >= 5){

                        if(main_Character.get_Hp() == 0){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_10);
                        }else if(main_Character.get_Hp() == 1){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_11);
                        }else if(main_Character.get_Hp() == 2){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_12);
                        }else if(main_Character.get_Hp() == 3){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_13);
                        }else if(main_Character.get_Hp() == 4){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_14);
                        }else if(main_Character.get_Hp() == 5){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_15);
                        }else if(main_Character.get_Hp() == 6){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_16);
                        }else if(main_Character.get_Hp() == 7){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_17);
                        }else if(main_Character.get_Hp() == 8){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_18);
                        }else if(main_Character.get_Hp() == 9){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_19);
                        }else if(main_Character.get_Hp() == 10){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_20);
                        }else if(main_Character.get_Hp() == 11){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_21);
                        }else if(main_Character.get_Hp() == 12){
                            nine_Patch_Hp = BitmapFactory.decodeResource(getResources(), R.drawable.hp_22);
                        }

                    }

                }catch (Exception e){
                    Log.e("a",e.getMessage());
                    Log.e("a",e.toString());
                    Log.e("a","Hp바 그리기 부분");
                }

                try{


//                    nine_Patch_Hp = Bitmap.createScaledBitmap(nine_Patch_Hp, (dm.widthPixels)/10*8, -convertPixelsToDp(50, _context), false); //배경 화면 어둡게
                    draw.draw_Bmp(canvas, nine_Patch_Hp, 20, 1); //나인패치 적용방법


                    if(day_Count_View < 30) {
                        //날짜 표기법
                    }

                    day_Count_View++;


                    draw.draw_Bmp(canvas, gold_img, convertPixelsToDp(10, _context), convertPixelsToDp(55, _context));
                    score_Text_Size = convertPixelsToDp(20, _context);
                    paint_Temp.setTextSize(score_Text_Size);
                    paint_Temp.setStyle(Paint.Style.FILL);
                    paint_Temp.setColor(Color.WHITE);
                    canvas.drawText("x " + df.format(money) , convertPixelsToDp(27, _context), convertPixelsToDp(70, _context), paint_Temp);

                    score_Text_Size = convertPixelsToDp(20, _context);
                    paint_Temp.setStrokeWidth(convertPixelsToDp(1, _context));
                    paint_Temp.setStyle(Paint.Style.STROKE);
                    paint_Temp.setColor(Color.BLACK);
                    canvas.drawText("x " + df.format(money) , convertPixelsToDp(27, _context), convertPixelsToDp(70, _context), paint_Temp);

                    /**
                     * 루비 그리기
                     */
                    draw.draw_Bmp(canvas, ruby_img, convertPixelsToDp(10, _context), convertPixelsToDp(75, _context));
                    score_Text_Size = convertPixelsToDp(20, _context);
                    paint_Temp.setTextSize(score_Text_Size);
                    paint_Temp.setStyle(Paint.Style.FILL);
                    paint_Temp.setColor(Color.WHITE);
                    canvas.drawText("x " + df.format(ruby) , convertPixelsToDp(27, _context), convertPixelsToDp(90, _context), paint_Temp);

                    score_Text_Size = convertPixelsToDp(20, _context);
                    paint_Temp.setStrokeWidth(convertPixelsToDp(1, _context));
                    paint_Temp.setStyle(Paint.Style.STROKE);
                    paint_Temp.setColor(Color.BLACK);
                    canvas.drawText("x " + df.format(ruby) , convertPixelsToDp(27, _context), convertPixelsToDp(90, _context), paint_Temp);

//
                    /**
                     *  점수 그리기
                     */
//        score_Text_Size = window_Width / 15;

                    paint.setStrokeWidth(convertPixelsToDp(1, _context));
                    score_Text_Size = convertPixelsToDp(30, _context);
                    paint_Temp.setTextSize(score_Text_Size);
                    paint_Temp.setStyle(Paint.Style.FILL);
                    paint_Temp.setColor(Color.YELLOW);
                    canvas.drawText(df.format(Score)+"", convertPixelsToDp(10, _context), convertPixelsToDp(115, _context), paint_Temp);

                    //점수 배경
                    paint.setTextSize(score_Text_Size);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(convertPixelsToDp(1, _context));
                    paint.setColor(Color.BLACK);
                    canvas.drawText(df.format(Score)+"", convertPixelsToDp(10, _context), convertPixelsToDp(115, _context), paint);

                    //베스트 점수
                    paint_Best.setTextSize(score_Text_Size-(score_Text_Size/2));
                    paint_Best.setStyle(Paint.Style.STROKE);
                    paint_Best.setStrokeWidth(convertPixelsToDp(1, _context));
                    paint_Best.setColor(Color.BLACK);
                    canvas.drawText("Best " + df.format(best_Point), convertPixelsToDp(10, _context), convertPixelsToDp(115, _context)+(score_Text_Size/2), paint_Best);


                }catch (Exception e){
                    Log.e("점수그리기", "점수그리기");
                }


                try{
                    //스테이지에 따른 물고기 설명창
                    if(first_Explain) {
                        m_Run_False();
                    }



                }catch (Exception e){
                    Log.e("물고기 설명창", "물고기 설명창");
                }







    /*
    등장 설명창, 진화 할 때 컨트롤
     */
                try{

                    if(!mRun) {

                        if(!pause_State) {
                            draw.draw_Bmp(canvas, backGroundImg_black, 0, 0);
                        }



                        //새로운 적군 등장 할 때
                        if(first_Explain){
                            first_Explain = false;
                            draw.draw_Bmp(canvas, backGroundImg_black, 0, 0);

                            for(int i=0; i<explain.length; i++){

                                if(explain[i]){
//                    Log.e("a","aa" + i);
                                    image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_a1 + i);
                                    explain_Window_ima = image.getBitmap();
//                    Log.e("@", "@#@# = " + i);
//                                    Log.e("@", "i = " +  i);
//                                    Log.e("@", "@@ = " + (R.drawable.explain_window_a1 + i));

                                    //몬스터 설명창 다음부터 생략
                                    monster_Explain_Db[i] = 1;

                                    break;
                                }

                            }

                            draw.draw_Bmp(canvas, explain_Window_ima, window_Width / 2 - convertPixelsToDp(120, _context),
                                    window_Height / 2 - convertPixelsToDp(220, _context));

                            upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_1);
                            downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_2);


                            button_Create_method_Init();
                            soundPool.play(sound_Effect[8], pop_Effect, pop_Effect, 0, 0, 1.0F);
                            confirm_Button_3.setImages(upimage, downimage);
                            confirm_Button_3.draw(canvas);




                            //진화의 버튼이 떠있을때.
                            if(revolution_Draw_Flag_Confirm){
                                //몹 설명창이랑 진화 카드 창이랑 겹치면 안된다.
                                revolution_Flag = false;

                                //진화의 버튼 초기화
                                revolution_Button = new GraphicButton(new Rect(0,
                                        0,
                                        0,
                                        0));

                                //진화의 버튼 뒤에 이펙트 안없어짐 따라서 여기서 없애줌.
//                revolution_Button_Background_Effect = null;
                            }



                            //sql 라이트
                            String sql = "UPDATE maincharacterinfo SET money = '" + money + "', ruby = '" + ruby + "'";
                            MainActivity.database.execSQL(sql);
                            editor.putString("realmoney", String.valueOf(money));
                            editor.commit();

                        }


                        if(revolution_Flag && !pause_State) {

//                            Log.e("a","@@@");
                            home_Revolution_Flag = true;
//                            revolution_Flag -> 돌아오면 false 로 바뀜


//                            Log.e("@","@ 진화의 버튼 눌러짐");

                            draw.draw_Bmp(canvas, explain_Window_Revoluition,
                                    window_Width / 2 - convertPixelsToDp(120, _context),
                                    window_Height / 2 - convertPixelsToDp(220, _context));

                            //버튼 객체 생성
                            button_Create_Method();



                            upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);  //7405
                            downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);

                            confirm_Button_card_1.setImages(upimage, downimage);
                            confirm_Button_card_1.draw(canvas);

                            upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);
                            downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);



                            confirm_Button_card_2.setImages(upimage, downimage);
                            confirm_Button_card_2.draw(canvas);

                            upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_3);
                            downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_3);

                            confirm_Button_card_3.setImages(upimage, downimage);
                            confirm_Button_card_3.draw(canvas);


                            //루비개수
                            paint_Temp.setTextSize(convertPixelsToDp(25, _context));
                            paint_Temp.setStyle(Paint.Style.FILL);
                            paint_Temp.setColor(Color.BLACK);



                            if(main_Character.get_Tear() == 0){
                            card_Ruby = 5;
                            }else{
                            card_Ruby = main_Character.get_Tear() * 5 + 5;
                            }
                            if(card_Ruby < 10){
                            card_Ruby_Position = 47;
                            }else {
                            card_Ruby_Position = 40;
                            }

                            canvas.drawText(card_Ruby +"", window_Width / 2 + convertPixelsToDp(card_Ruby_Position, _context) , window_Height / 2 + convertPixelsToDp(7, _context), paint_Temp);

                            revolution_Flag = false;

                        }



                    }

                }catch (Exception e){
                    Log.e("@","등장 설명창1");
                }


                try{
                    //진화 버튼 클릭
                    if(Extraction && !pause_State){



                        //추출 창
//            explain_Window_Extraction
                        m_Run_False(); //게임 멈춤
                        Extraction = false;

                        draw.draw_Bmp(canvas, backGroundImg_black, 0, 0);

                        draw.draw_Bmp(canvas, explain_Window_Extraction, window_Width / 2 - convertPixelsToDp(100, _context),
                                window_Height / 2 - convertPixelsToDp(120, _context));

                        //일반 추출
                        Extraction_Button_1 = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(45, _context),
                                window_Height / 2 - convertPixelsToDp(110, _context),
                                window_Width / 2 + convertPixelsToDp(45, _context),
                                window_Height / 2 - convertPixelsToDp(75, _context)));


                        //루비 개수(일반 추출)
                        score_Text_Size = convertPixelsToDp(20, _context);
                        paint_Temp.setTextSize(score_Text_Size);
                        paint_Temp.setStyle(Paint.Style.FILL);
                        paint_Temp.setColor(Color.WHITE);
                        canvas.drawText("" + df.format(main_Character.get_Tear()) , window_Width / 2 - convertPixelsToDp(5, _context), window_Height / 2 - convertPixelsToDp(49, _context), paint_Temp);
                        score_Text_Size = convertPixelsToDp(20, _context);
                        paint_Temp.setStrokeWidth(convertPixelsToDp(1, _context));
                        paint_Temp.setStyle(Paint.Style.STROKE);
                        paint_Temp.setColor(Color.BLACK);
                        canvas.drawText("" + df.format(main_Character.get_Tear()) , window_Width / 2 - convertPixelsToDp(5, _context), window_Height / 2 - convertPixelsToDp(49, _context), paint_Temp);





                        //루비 개수(영구 추출)
                        score_Text_Size = convertPixelsToDp(20, _context);
                        paint_Temp.setTextSize(score_Text_Size);
                        paint_Temp.setStyle(Paint.Style.FILL);
                        paint_Temp.setColor(Color.WHITE);
                        canvas.drawText("" + df.format(main_Character.get_Tear()*10) , window_Width / 2 - convertPixelsToDp(5, _context), window_Height / 2 + convertPixelsToDp(64, _context), paint_Temp);
                        score_Text_Size = convertPixelsToDp(20, _context);
                        paint_Temp.setStrokeWidth(convertPixelsToDp(1, _context));
                        paint_Temp.setStyle(Paint.Style.STROKE);
                        paint_Temp.setColor(Color.BLACK);
                        canvas.drawText("" + df.format(main_Character.get_Tear()*10) , window_Width / 2 - convertPixelsToDp(5, _context), window_Height / 2 + convertPixelsToDp(64, _context), paint_Temp);





                        upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_5);
                        downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_5);

                        Extraction_Button_1.setImages(upimage, downimage);
                        Extraction_Button_1.draw(canvas);

                        //영구추출
                        Extraction_Button_2 = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(45, _context),
                                window_Height / 2 + convertPixelsToDp(5, _context),
                                window_Width / 2 + convertPixelsToDp(45, _context),
                                window_Height / 2 + convertPixelsToDp(40, _context)));

                        upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_6);
                        downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_6);

                        Extraction_Button_2.setImages(upimage, downimage);
                        Extraction_Button_2.draw(canvas);

                        //취소버튼
                        Extraction_Button_3 = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(45, _context),
                                window_Height / 2 + convertPixelsToDp(135, _context),
                                window_Width / 2 + convertPixelsToDp(45, _context),
                                window_Height / 2 + convertPixelsToDp(170, _context)));

                        upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_7);
                        downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_7);

                        Extraction_Button_3.setImages(upimage, downimage);
                        Extraction_Button_3.draw(canvas);

                    }

                }catch (Exception e){
                    Log.e("@","추출창");
                }



                try{



                    if(revolution_Flag_Confirm){

                        //진화의창 버튼 객체 제거
                        button_Create_method_Init_Small();


                        upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_1);
                        downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_2);

                        draw.draw_Bmp(canvas, backGroundImg_black, 0, 0);

                        draw.draw_Bmp(canvas, explain_Window_MainCharacker,
                                window_Width / 2 - convertPixelsToDp(120, _context),
                                window_Height / 2 - convertPixelsToDp(220, _context));





                        soundPool.play(sound_Effect[8], pop_Effect, pop_Effect, 0, 0, 1.0F);
                        confirm_Button_1.setImages(upimage, downimage);
                        confirm_Button_1.draw(canvas);



                        if(get_Extraction_Button_Flag(main_Character)) {
                            //추출 버튼
                            upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_3);
                            downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_4);
                        }else {
                            upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_8);
                            downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_samll_8);
                        }



                        confirm_Button_2.setImages(upimage, downimage);
                        confirm_Button_2.draw(canvas);




                        m_Run_False();



                    }
                }catch (Exception e){
                    Log.e("@","진화의창 버튼 객체 제거");
                }

                try{
                    //진화할때 진화 버튼
                    if(revolution_Flag && !revolution_Draw_Flag_Confirm ){


                        revolution_Button = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(50, _context),
                                window_Height / 2 - convertPixelsToDp(145, _context),
                                window_Width / 2 - convertPixelsToDp(50, _context) + convertPixelsToDp(105, _context),
                                window_Height / 2 - convertPixelsToDp(145, _context) + convertPixelsToDp(40, _context)));

                        //백그라운드 이팩트
                        image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.confirm_button_effect);
                        revolution_Button_Background_Effect = image.getBitmap();



                        upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.revolution_img);
                        downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.revolution_img);

                        revolution_Button.setImages(upimage, downimage);


                        revolution_Draw_Flag_Confirm = true;

                        //진화의 버튼 뒤쪽의 이펙트

                        //처음 한번만, 텍스트 설명창
                        if(tutorial_Flag) {
                            if(main_Character instanceof Main_Character_Plankton_1){
                                m_Run_False();
                                first_Text_Explain_Flag = true;
                                gameActivity.set_Tuto(1);

                            }
                        }


                    }
                }catch (Exception e){
                    Log.e("@","진화의창 버튼 객체 제거1");
                }

                try{

                    if(revolution_Draw_Flag_Confirm && revolution_Flag){
                        draw.draw_Bmp(canvas, revolution_Button_Background_Effect,
                                window_Width / 2 - convertPixelsToDp(70 , _context),
                                window_Height / 2 - convertPixelsToDp(198 , _context) );

                        revolution_Button.draw(canvas);
                    }

                }catch (Exception e){
                    Log.e("@","진화의창 버튼 객체 제거2");
                }




//

                try{

                    /**
                     * 상어친구 호출하면 여기서 그린다.
                     */
                    if(shark_Friend_Call_Flag){



                        background_Effect_Friend_Shark_Call.Background_Effect_Move_Pattern();
                        draw.draw_Bmp(canvas, effect_Background_Friend_Shark_img[background_Effect_Friend_Shark_Call.get_Draw_Background_Friend_Shark_Effect_Status()],
                                window_Width/2 - convertPixelsToDp(340, _context),
                                window_Height/2 - convertPixelsToDp(380, _context));

                        if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 0){
                            soundPool.play(sound_Effect[4], pop_Drag/7, pop_Drag/7, 0, 0, 1.0F);
                        }else if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 4){
//                soundPool.play(sound_Effect[5], 0.5F, 0.5F, 1, 1, 1.0F);
                        }else if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 6){
//                soundPool.play(sound_Effect[6], 0.5F, 0.5F, 1, 1, 1.0F);
                        }else if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 7){
                            soundPool.play(sound_Effect[7], pop_Drag/7, pop_Drag/7, 0, 0, 1.0F);
                        }



                        if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 7){

                            //모든 물고기 및 그라운드 생명체 hp - 100 해야된다.


                            for(int i=0; i<fish_List.size(); i++){
                                if(fish_List.get(i) instanceof Fish_Monster){
                                    continue;
                                }
                                if(fish_List.get(i).get_Visible_Fish_Flag()) {
                                    fish_List.get(i).set_Hp_Minus(500000);
                                    if(fish_List.get(i).get_Class_Num() == 1 || fish_List.get(i).get_Class_Num() == 2 ){
                                        fish_List.get(i).set_Production(false);
                                    }
                                }
                            }


                            for(int i=0; i<ground_List.size(); i++){
                                if(!(ground_List.get(i) instanceof Land_Mark)) {
                                    if(!(ground_List.get(i) instanceof Ground_Drag_Clam) && ground_List.get(i).get_Visible_Ground_Flag()) {
                                        ground_List.get(i).set_Ground_Hp_Minus(500000);
                                    }
                                }
                            }

                            shark_Friend_Call_Flag = false;     //상어 이펙트 그린후 사라져야한다.


                        }
                    }


                }catch (Exception e){
                    Log.e("@","상어친구 그리기");
                }


                try{
                    if(first_Text_Explain_Flag){
//
                        if(first_Text_Explain_Index == 0) {
                            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_m0);
                            explain_Window_ima = image.getBitmap();

                            draw.draw_Bmp(canvas, explain_Window_ima,
                                    window_Width / 2 - convertPixelsToDp(105, _context),
                                    window_Height / 2 - convertPixelsToDp(25, _context));
                        }else if(first_Text_Explain_Index == 1){
                            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_m1);
                            explain_Window_ima = image.getBitmap();

                            draw.draw_Bmp(canvas, explain_Window_ima,
                                    window_Width / 2 - convertPixelsToDp(115, _context),
                                    window_Height / 2 + convertPixelsToDp(35, _context));
                        }else if(first_Text_Explain_Index == 2){
                            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_m2);
                            explain_Window_ima = image.getBitmap();

                            draw.draw_Bmp(canvas, explain_Window_ima,
                                    window_Width / 2 - convertPixelsToDp(110, _context),
                                    window_Height / 2 + convertPixelsToDp(25, _context));
                        }else if(first_Text_Explain_Index == 3){
                            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_m3);
                            explain_Window_ima = image.getBitmap();

                            draw.draw_Bmp(canvas, explain_Window_ima,
                                    window_Width  - convertPixelsToDp(275, _context),
                                    convertPixelsToDp(30, _context));
                        }else if(first_Text_Explain_Index == 4){
                            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_m4);
                            explain_Window_ima = image.getBitmap();

                            draw.draw_Bmp(canvas, explain_Window_ima,
                                    window_Width - convertPixelsToDp(275, _context),
                                    convertPixelsToDp(70, _context));
                        }else if(first_Text_Explain_Index == 5){
                            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_m5);
                            explain_Window_ima = image.getBitmap();

                            draw.draw_Bmp(canvas, explain_Window_ima,
                                    window_Width - convertPixelsToDp(290, _context),
                                    convertPixelsToDp(100, _context));
                        }else if(first_Text_Explain_Index == 6){
                            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_m6);
                            explain_Window_ima = image.getBitmap();

                            draw.draw_Bmp(canvas, explain_Window_ima,
                                    window_Width - convertPixelsToDp(265, _context),
                                    convertPixelsToDp(140, _context));
                        }else if(first_Text_Explain_Index == 7){
                            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_m7);
                            explain_Window_ima = image.getBitmap();

                            draw.draw_Bmp(canvas, explain_Window_ima,
                                    window_Width - convertPixelsToDp(275, _context),
                                    convertPixelsToDp(185, _context));
                        }else if(first_Text_Explain_Index == 8){
                            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_m8);
                            explain_Window_ima = image.getBitmap();

                            draw.draw_Bmp(canvas, explain_Window_ima,
                                    window_Width - convertPixelsToDp(320, _context),
                                    convertPixelsToDp(220, _context));
                        }else if(first_Text_Explain_Index == 9){
                            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_m9);
                            explain_Window_ima = image.getBitmap();

                            draw.draw_Bmp(canvas, explain_Window_ima,
                                    window_Width / 2 - convertPixelsToDp(explain_Window_ima.getWidth()/4, _context),
                                    window_Height / 2 - convertPixelsToDp(95, _context));
                        }




                        m_Run_False();
                    }
                }catch (Exception e){
                    Log.e("@","텍스트 설명창");
                }


            }catch (Exception e){

                Log.e("그리기aaa", "그리기aaa");
                Log.e("그리기", e.toString());
                Log.e("그리기", e.getMessage());

            }

        }


        /**
         * 게임 오버 글자 크기 변경 함수
         */
        int gameover_Text_Temp = 0;
        int gameover_Text_Time = 0;

        public int gameover_Test_Size_Change(){

            if(gameover_Text_Time%2 == 0) {
                if (gameover_Text_Time < 20) {
                    gameover_Text_Temp += 5;
                } else {
                    gameover_Text_Temp -= 5;
                }
            }


            gameover_Text_Time++;
            if(gameover_Text_Time >= 40){
                gameover_Text_Time = 0;
            }



            return convertPixelsToDp(gameover_Text_Temp, _context);
        }










        /**
         * 게임이 동작하는 구간
         */
        //기기마다 다른 프레인 속도를 가지고 있기 때문에 아래의 3변수를 통해 [프레임 스키핑]을 비슷하게 구현한다. [프레임 스키핑이 아니나 간편하게 같은 효과를 낼 수 있다.]
        long endTime = 0;
        long delta = 0;
        long targetFrameInterval = 1000L/20L;
        long frame = 20L;
        long frame_Start = 0;
//    long time_Sleep = 0;


boolean bg_Set = false;

        public synchronized void run() {

            while (distroy_Run){    //일시정지 하고 나서 계속 돌린다.
//Log.e("@","1");
//            time_Sleep += add_Time;
                while (mRun) {



                    set_Best_Point();

//                    Log.e("@","skill_Fish_Extract_Nomar[1] = " + skill_Fish_Extract_Nomar[1] );

                    //퍼즈 걸도록 mRun 컨트롤

                    //canvas = null;
                    try {
//                        time_Sleep = 5;
                        frame_Start = System.currentTimeMillis();


                        canvas = mSurfaceHolder.lockCanvas(null);
//                    synchronized (mSurfaceHolder) {


                        //화면 껏다 키면 일시정지 창
                        isScreen = km.inKeyguardRestrictedInputMode();

                        if (isScreen) {
                            Log.e("화면끄기","화면끄기");

                            background_Sound.pause();
                            bg_Set = true;
//                        Log.e("@","!@#!#!@#!#!@#");
                            gameActivity.pause();
                        } else {
                            if(bg_Set){
                                background_Sound.start();
                            }
                            bg_Set = false;
                        }


                        /**
                         * 그림 그리기 구간
                         */
                        if(!gameover_Flag) {
                            if(!infinitymode_Status){
                                doDraw(canvas);
                            }else {

                            }
                        }

                            try{
                                //게임 오버 이팩트
                                if(main_Character.get_Hp() <= 0){
                                    draw.draw_Bmp(canvas, backGroundImg_black,0, 0);
                                    gameover_Img = Bitmap.createScaledBitmap(gameover_Img, dm.widthPixels/2 + gameover_Test_Size_Change(), dm.heightPixels/8 + gameover_Test_Size_Change(), false);
                                    draw.draw_Bmp(canvas, gameover_Img, window_Width/2 - gameover_Img.getWidth()/2, window_Height/2 - gameover_Img.getHeight()/2);

//                            일때 클릭하면 일시정지창, 다시하기가 꺼진
                                    gameover_Flag = true;
                                    stage_Day.cancel();
                                }else if(infinitymode_Status){
                                  //무한모드
                                    draw.draw_Bmp(canvas, backGroundImg_black,0, 0);
                                    infinitymode_Img = Bitmap.createScaledBitmap(infinitymode_Img, dm.widthPixels/2 + gameover_Test_Size_Change(), dm.heightPixels/8 + gameover_Test_Size_Change(), false);
                                    draw.draw_Bmp(canvas, infinitymode_Img, window_Width/2 - infinitymode_Img.getWidth()/2, window_Height/2 - infinitymode_Img.getHeight()/2);


                                }
                            }catch (Exception e){
                                Log.e("a",e.getMessage());
                                Log.e("a",e.toString());
                                Log.e("a"," 게임 오버 이벤트");
                            }

//                        Thread.sleep(15);

                        //물고기 및 그라운드 생명체 체력 0 인것 삭제
                        delete_Fish();
                        delete_Ground();

                        if(!infinitymode_Status) {
                            //물고기 움직임을 하나의 쓰레드로 작동한다.
                            fish_Move();
                            //그라운드 움직임을 하나의 쓰레드로 작동합니다.
                            ground_Move();

                            main_Character.character_Moving();
                        }

                        /**
                         * 배경 효과 움직임
                         */
                        background_Effect_Move();


                        //몬스터 보내기
//                    send_Fish();
//                    send_Ground();


                        //홈 버튼 누르고 복귀시에 한 번 그리고 정지 시키기 위함
                        if(home_Restart_Flag) {
                            m_Run_False();
                        }


                        //문어 공격 스피드에 따라서 터치 이벤트 제어
                        if (main_Character.get_Attack_Cool_time() != 0) {
                            main_Character.set_Attack_Cool_Time();
                        }


//                    }
                        //기기마다 다른 프레인 속도를 가지고 있기 때문에 아래의 3변수를 통해 [프레임 스키핑]을 비슷하게 구현한다. [프레임 스키핑이 아니나 간편하게 같은 효과를 낼 수 있다.]
                        endTime = System.currentTimeMillis();
                        delta = endTime - frame_Start;
                        if(targetFrameInterval-delta > frame ){
                            Thread.sleep(targetFrameInterval-delta);
                        }


                    } catch (Exception e) {

                    } finally {
                        if (canvas != null) {
                            mSurfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }


                }
            }
        }





















    }

    boolean first_Game_Start_Character_Image_Init = false;
    //메인 캐릭터 이미지 -> 진화 할때마다 호출해서 새로 그려준다.
    public void Init_Main_Character_Image(Context context, Main_Character main_character){

        //메인캐릭터 비트맵 반환
        if(first_Game_Start_Character_Image_Init) {
            game_thread.recycle_Main_Character();
            first_Game_Start_Character_Image_Init = true;
        }
        /**
         * 갑각류
         */
        if(main_character instanceof Main_Character_Plankton_1){

//                image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.character_amoeba_1 + i);
//                main_Character_Img[i] = image.getBitmap();
            game_thread.function_Main_Character_Plankton1();


        }else if(main_character instanceof Main_Character_Plankton_2){
            game_thread.function_Main_Character_Plankton2();
        }else if(main_character instanceof Main_Character_Plankton_3){
            game_thread.function_Main_Character_Plankton3();
        }else if(main_character instanceof Main_Character_Plankton_4){
            game_thread.function_Main_Character_Plankton4();
        }else if(main_character instanceof Main_Character_Plankton_5){
            game_thread.function_Main_Character_Plankton5();
        }
        else if(main_character instanceof Main_Character_Shellfish_Tear1){
//                image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.character_shellfish_tear1_1 + i);
//                main_Character_Img[i] = image.getBitmap();
            game_thread.function_Main_Character_Shellfish1();

        }else if(main_character instanceof Main_Character_Shellfish_Tear2){
            game_thread.function_Main_Character_Shellfish2();
        }else if(main_character instanceof Main_Character_Shellfish_Tear3){
            game_thread.function_Main_Character_Shellfish3();
        }else if(main_character instanceof Main_Character_Shellfish_Tear4){
            game_thread.function_Main_Character_Shellfish4();
        }else if(main_character instanceof Main_Character_Shellfish_Tear5){
            game_thread.function_Main_Character_Shellfish5();
        }else if(main_character instanceof Main_Character_Shellfish_Tear6){
            game_thread.function_Main_Character_Shellfish6();
        }else if(main_character instanceof Main_Character_Shellfish_Tear7){
            game_thread.function_Main_Character_Shellfish7();
        }else if(main_character instanceof Main_Character_Shellfish_Tear8){
            game_thread.function_Main_Character_Shellfish8();
        }else if(main_character instanceof Main_Character_Shellfish_Tear9){
            game_thread.function_Main_Character_Shellfish9();
        }else if(main_character instanceof Main_Character_Shellfish_Tear10){
            game_thread.function_Main_Character_Shellfish10();
        }


        /**
         * 생선 이미지
         */
        else if(main_character instanceof Main_Character_Fish_Tear1){

            game_thread.function_Main_Character_Fish1();

        }else if(main_character instanceof Main_Character_Fish_Tear2){
            game_thread.function_Main_Character_Fish2();

        }else if(main_character instanceof Main_Character_Fish_Tear3){
            game_thread.function_Main_Character_Fish3();
        }else if(main_character instanceof Main_Character_Fish_Tear4){
            game_thread.function_Main_Character_Fish4();
        }else if(main_character instanceof Main_Character_Fish_Tear5){
            game_thread.function_Main_Character_Fish5();
        }else if(main_character instanceof Main_Character_Fish_Tear6){
            game_thread.function_Main_Character_Fish6();
        }else if(main_character instanceof Main_Character_Fish_Tear7){
            game_thread.function_Main_Character_Fish7();
        }else if(main_character instanceof Main_Character_Fish_Tear8){
            game_thread.function_Main_Character_Fish8();
        }else if(main_character instanceof Main_Character_Fish_Tear9){
            game_thread.function_Main_Character_Fish9();
        }else if(main_character instanceof Main_Character_Fish_Tear10){
            game_thread.function_Main_Character_Fish10();
        }


/**
 * 연체 동물 이미지
 */
        else if(main_character instanceof Main_Character_Moulluse_Tear1){
            game_thread.function_Main_Character_Moulluse1();
        }else if(main_character instanceof Main_Character_Moulluse_Tear2){
            game_thread.function_Main_Character_Moulluse2();
        }else if(main_character instanceof Main_Character_Moulluse_Tear3){
            game_thread.function_Main_Character_Moulluse3();
        }else if(main_character instanceof Main_Character_Moulluse_Tear4){
            game_thread.function_Main_Character_Moulluse4();
        }else if(main_character instanceof Main_Character_Moulluse_Tear5){
            game_thread.function_Main_Character_Moulluse5();
        }else if(main_character instanceof Main_Character_Moulluse_Tear6){
            game_thread.function_Main_Character_Moulluse6();
        }else if(main_character instanceof Main_Character_Moulluse_Tear7){
            game_thread.function_Main_Character_Moulluse7();
        }else if(main_character instanceof Main_Character_Moulluse_Tear8){
            game_thread.function_Main_Character_Moulluse8();
        }else if(main_character instanceof Main_Character_Moulluse_Tear9){
            game_thread.function_Main_Character_Moulluse9();
        }else if(main_character instanceof Main_Character_Moulluse_Tear10){
            game_thread.function_Main_Character_Moulluse10();
        }







    }




    /**
     * 상어친구 호출 루틴 - background_Effect_Two 와 동작 방법 똑같기 때문에 그림만 그려준다.
     */
    private boolean shark_Friend_Call_Flag = false;
    Background_Effect_Two background_Effect_Friend_Shark_Call;
    public void shark_Friend_Call(){
        background_Effect_Friend_Shark_Call = new Background_Effect_Two(window_Width, window_Height);
        shark_Friend_Call_Flag = true;
    }

    /**
     * 힐 하기
     */
    public void Heal_Call(){
        main_Character.set_Hp_Add();
    }

    /**
     * 판 넘기기
     */
    public void next_Stage_Call(){


        stage_Call();
        day_Count++;
    }



    /**
     * dpi 구하기
     * */
    public int convertPixelsToDp(float px, Context context) {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
        return value;
    }
    public double convertPixelsToDp_A(float px, Context context) {
        double value = (double) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
        return value;
    }






    /**
     * 청 새치 주의!
     */
    public void wave_Marlin(){

//    soundPool.play(sound_Effect[15], 0.5F, 0.5F, 0, 0, 1.0F);

        warning_Flag = true;
        warning_Marlin_Flag = true;
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
        fish_List.add(fish_Touch_Marlin);
    }



    /**
     * 청새치 추가하기
     */
    public void add_Fish_Touch_Marlin(){
        fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), dm.heightPixels);
        fish_List.add(fish_Touch_Marlin);
    }


    //중간 보스 물고기 잡을때 나올 물고기
    public void fish_Child_Set(float x, float y){
        for(int i=0; i<fish_List.size(); i++){

            if(fish_List.get(i) instanceof Fish_Touch_Default  && fish_List.get(i).get_Child_Fish() == 1){
                if(!(fish_List.get(i)).get_Visible_Fish_Flag()){
                    fish_List.get(i).set_Visible_Fish_Flag(true);
                    fish_List.get(i).set_Fish_Hp(5);
                    fish_List.get(i).set_Position(x,y);
                    break;
                }
            }
        }
    }
    //보스 물고기 잡을때 나올 물고기
    public void fish_Child_Middle_Set(float x, float y){
        for(int i=0; i<fish_List.size(); i++){

            if(fish_List.get(i) instanceof Fish_Touch_Default && fish_List.get(i).get_Child_Fish() == 2){
                if(!(fish_List.get(i)).get_Visible_Fish_Flag()){
                    fish_List.get(i).set_Visible_Fish_Flag(true);
                    fish_List.get(i).set_Fish_Hp(5);
                    fish_List.get(i).set_Position(x,y);
                    break;
                }
            }
        }
    }
    // 강철 참돔 잡을때 나올 물고기
    public void fish_Child_Steel_Drag_Set(float x, float y){
        for(int i=0; i<fish_List.size(); i++){

            if(fish_List.get(i) instanceof Fish_Drag_Default){
                if(!(fish_List.get(i)).get_Visible_Fish_Flag() && fish_List.get(i).get_Child_Fish() == 3){
                    fish_List.get(i).set_Visible_Fish_Flag(true);
                    fish_List.get(i).set_Position(x,y);
                    break;
                }
            }
        }
    }



    /**
     *오브젝트의 자리에서 오브젝트를 생성할때 주의할점 그리기, 움직이기, 터치이벤트 에서 막아줘야함
     * get_Child_Fish()
     */


    //오브젝트 풀링 물고기 가져오기
    public void fish_Total_Production(){

//         기본 물고기
        for(int i=0; i<8; i++) {
            fish_Touch_Default = new Fish_Touch_Default(window_Width, random.nextInt(5) + 1, fish_Touch_Default_Hp1_img[0].getWidth() + convertPixelsToDp(15, _context), fish_Touch_Default_Hp1_img[0].getHeight(), dm.heightPixels);       //기본 fish_touch_default 물고기 생성
            fish_Touch_Default.set_Visible_Fish_Flag(false);
            fish_List.add(fish_Touch_Default);
        }

//        중간 보스 물고기에서 나올 물고기
        for(int i=0; i<8; i++){
            fish_Touch_Default = new Fish_Touch_Default(window_Width, 5, fish_Touch_Default_Hp1_img[0].getWidth() + convertPixelsToDp(15, _context), fish_Touch_Default_Hp1_img[0].getHeight(), dm.heightPixels);       //기본 fish_touch_default 물고기 생성
            fish_Touch_Default.set_Child_Fish(1);
            fish_Touch_Default.set_Visible_Fish_Flag(false);
            fish_List.add(fish_Touch_Default);
        }

        // 기본 중간 물고기 보스
        for(int i=0; i<4; i++){
            fish_Touch_Default = new Fish_Touch_Default(window_Width, 5, fish_Touch_Default_Middle_Hp1_img[0].getWidth(), fish_Touch_Default_Middle_Hp1_img[0].getHeight(), dm.heightPixels);
            fish_Touch_Default.set_Child_Fish(2);
            fish_Touch_Default.set_Class_Num(1);
            fish_Touch_Default.set_Visible_Fish_Flag(false);
            fish_List.add(fish_Touch_Default);
        }

        // 기본 물고기 보스
        for(int i=0; i<2; i++){
            fish_Touch_Default = new Fish_Touch_Default(window_Width, 5, fish_Touch_Default_Boss_Hp1_img[0].getWidth(), fish_Touch_Default_Boss_Hp1_img[0].getHeight(), dm.heightPixels);
            fish_Touch_Default.set_Class_Num(2);
            fish_Touch_Default.set_Visible_Fish_Flag(false);
            fish_List.add(fish_Touch_Default);
        }

//        // 드래그 물고기
        for(int i=0; i<4; i++) {
            fish_Drag_Default = new Fish_Drag_Default(window_Width, 2 * day_Count * (1 + random.nextInt(3)), fish_Drag_Default_img[0].getWidth(), fish_Drag_Default_img[0].getHeight(), dm.heightPixels);       //드래그로 잡는 fish_Touch_Default 물고기생성
            fish_Drag_Default.set_Visible_Fish_Flag(false);
            fish_List.add(fish_Drag_Default);
        }

//        강철 참돔에서 나올 물고기
        for(int i=0; i<3; i++) {
            fish_Drag_Default = new Fish_Drag_Default(window_Width, 2 * day_Count * (1 + random.nextInt(3)), fish_Drag_Default_img[0].getWidth(), fish_Drag_Default_img[0].getHeight(), dm.heightPixels);       //드래그로 잡는 fish_Touch_Default 물고기생성
            fish_Drag_Default.set_Child_Fish(3);
            fish_Drag_Default.set_Visible_Fish_Flag(false);
            fish_List.add(fish_Drag_Default);
        }

        // 오징어
        for(int i=0; i<3; i++){
            fish_Touch_Squid = new Fish_Touch_Squid(window_Width, 1 , fish_Touch_Squid_img[0].getWidth(), fish_Touch_Squid_img[0].getHeight(), dm.heightPixels);       //오징어 생성
            fish_Touch_Squid.set_Visible_Fish_Flag(false);
            fish_List.add(fish_Touch_Squid);
        }

        //상어
        for(int i=0; i<2; i++) {
            fish_Drag_Shark = new Fish_Drag_Shark(window_Width, 10 * day_Count * (1 + random.nextInt(3)), fish_Drag_Shark_img[0].getWidth(), fish_Drag_Shark_img[0].getHeight(), dm.heightPixels);       //드래그로 잡는 fish_Touch_Default 물고기생성
            fish_Drag_Shark.set_Visible_Fish_Flag(false);
            fish_List.add(fish_Drag_Shark);
        }

//          청새치
        for(int i=0; i<10; i++){
            fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight(), -convertPixelsToDp(500, _context) + convertPixelsToDp( random.nextInt(300)*-1, _context));
            fish_Touch_Marlin.set_Visible_Fish_Flag(false);
            fish_List.add(fish_Touch_Marlin);
        }


        //        //강철 참돔
        fish_Drag_Steelbream = new Fish_Drag_Steelbream(window_Width, 40000, fish_Drag_Steelbream_img[0].getWidth(), fish_Drag_Steelbream_img[0].getHeight(), dm.heightPixels);       //드래그로 잡는 fish_Touch_Default 물고기생성
        fish_Drag_Steelbream.set_Visible_Fish_Flag(false);
        fish_List.add(fish_Drag_Steelbream);

        //전기 뱀장어
        fish_Touch_Ell = new Fish_Touch_Ell(window_Width, 1, fish_Touch_Ell_img[0].getWidth(), fish_Touch_Ell_img[0].getHeight(), dm.heightPixels);
        fish_Touch_Ell.set_Visible_Fish_Flag(false);
        fish_List.add(fish_Touch_Ell);

//        해파리 추가
        fish_Trap_Jellyfish = new Fish_Trap_Jellyfish(window_Width, window_Height, 1, fish_Trap_Jelly_img[0].getWidth(),fish_Trap_Jelly_img[0].getHeight(), dm.heightPixels);                //화면 좌우축 둘중 한군대만 생성 hp = 1
        fish_Trap_Jellyfish.set_Visible_Fish_Flag(false);
        fish_Trap_Jellyfish.set_Position_Init();
        fish_List.add(fish_Trap_Jellyfish);

//        방해 거북추가
        fish_Trap_Turtle = new Fish_Trap_Turtle(window_Width, window_Height, 100000, fish_Turtle_img[0].getWidth(),fish_Turtle_img[0].getHeight(), convertPixelsToDp(300, _context), dm.heightPixels);
        fish_Trap_Turtle.set_Visible_Fish_Flag(false);
        fish_Trap_Turtle.set_Position_Init();
        fish_List.add(fish_Trap_Turtle);




        //랜드마크 공격 딱따구리 소환
        effect_Bird = new Effect_Bird();
        effect_Bird.set_Speed((int)bird);
    }


    //오브젝트 풀링 물고기 hp0 인것 선별해서 다시 충전 시키기
    public void default_Fish_Alive(int select_Fish_Num){

        //중독 해제
        fish_List.get(select_Fish_Num).init_Status();

        //기본 물고기 피가0인 것을 되살린다.
        if(fish_List.get(select_Fish_Num) instanceof Fish_Touch_Default){
//                if(fish_List.get(select_Fish_Num).get_Fish_Hp() <= 0){
            //보스 잡고 나오는 물고기 아닐때.

            if(fish_List.get(select_Fish_Num).get_Class_Num() == 2){
                //보스
                fish_List.get(select_Fish_Num).set_Fish_Hp(5);
                fish_List.get(select_Fish_Num).set_Production(true);
            }else if(fish_List.get(select_Fish_Num).get_Class_Num() == 1){
                //중간보스
                fish_List.get(select_Fish_Num).set_Fish_Hp(5);
                fish_List.get(select_Fish_Num).set_Production(true);

            }else {
                fish_List.get(select_Fish_Num).set_Fish_Hp(random.nextInt(5) + 1);
            }

        }

        //청새치
        else if(fish_List.get(select_Fish_Num) instanceof Fish_Touch_Marlin){
            fish_List.get(select_Fish_Num).set_Fish_Hp(1);
            fish_List.get(select_Fish_Num).set_Position();
        }

        //드래그 물고기
        else if(fish_List.get(select_Fish_Num) instanceof Fish_Drag_Default){
            fish_List.get(select_Fish_Num).set_Fish_Hp(2 * day_Count * (1 + random.nextInt(3)));
            fish_List.get(select_Fish_Num).set_Position();
            if(fish_List.get(select_Fish_Num).get_Child_Fish() == 3){
                fish_List.get(select_Fish_Num).set_Visible_Fish_Flag(false);
            }
        }

        //오징어
        else if(fish_List.get(select_Fish_Num) instanceof Fish_Touch_Squid){
            fish_List.get(select_Fish_Num).set_Fish_Hp(1);
            fish_List.get(select_Fish_Num).set_Position();
        }

        //전기 뱀장어
        else if(fish_List.get(select_Fish_Num) instanceof Fish_Touch_Ell){
            fish_List.get(select_Fish_Num).set_Fish_Hp(1);
            fish_List.get(select_Fish_Num).set_Position();
        }

        //상어
        else if(fish_List.get(select_Fish_Num) instanceof Fish_Drag_Shark){
            fish_List.get(select_Fish_Num).set_Fish_Hp(10 * day_Count * (1 + random.nextInt(3)));
            fish_List.get(select_Fish_Num).set_Position();

        }

        //강철 참돔
        else if(fish_List.get(select_Fish_Num) instanceof Fish_Drag_Steelbream){
            fish_List.get(select_Fish_Num).set_Fish_Hp(40000);
            fish_List.get(select_Fish_Num).set_Position();
        }

        //해파리
        else if(fish_List.get(select_Fish_Num) instanceof Fish_Trap_Jellyfish){
            fish_List.get(select_Fish_Num).set_Fish_Hp(1);
            ((Fish_Trap_Jellyfish)fish_List.get(select_Fish_Num)).set_Position();
        }

        //거북이
        else if(fish_List.get(select_Fish_Num) instanceof Fish_Trap_Turtle){
            fish_List.get(select_Fish_Num).set_Fish_Hp(10000);
            ((Fish_Trap_Turtle)fish_List.get(select_Fish_Num)).set_Position();
        }

        //몬스터
        else if(fish_List.get(select_Fish_Num) instanceof Fish_Monster){

            //무한모드 등장
            if(infinitymode_Status_Flag) {
                Monster_Clean = true;
                infinitymode_Status = true;
                if (day_Count <= 500) {
                    day_Count = 500;
                }
            }infinitymode_Status_Flag = false;

            fish_List.get(select_Fish_Num).set_Fish_Hp(400000);
            ((Fish_Monster)fish_List.get(select_Fish_Num)).set_Position();
//            Log.e("@","몬스타~!!!!!!!!!!!!!!!!!!!!! " + day_Count);


        }


        //충전한 것 안보이게
        fish_List.get(select_Fish_Num).set_Position_Init();
        fish_List.get(select_Fish_Num).set_Visible_Fish_Flag(false);



//        send_Fish();

    }


    /**
     * 대기 중인 물고기 내보내기
     */
    int[] count_Monster_Fish = new int[100]; //스테이지에 따라 몬스터 나올 수 0 = 기본 물고기, 1 보스 물고기, 2드래그 물고기, 3오징어, 4강철 참돔, 5전기 뱀장어, 6거북이, 7청새치, 8상어
    int[] count_Monster_Ground = new int[100]; //스테이지에 따라 몬스터 나올 수 0 = 기본 달팽이, 1 보스 달팽이, 2드래그 꽃게, 3성게, 4가오리, 5소라게, 6,조개, 7악어, 8파도, 9가재, 10불가사리, 11곰벌레,

    public void send_Fish(){

        Log.e("@","출동 물고기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        for(int i=0; i<100; i++){
            count_Monster_Fish[i] = 0;
            count_Monster_Ground[i] = 0;
        }

        //물고기
        for(int i=0; i<fish_List.size(); i++){
            if(fish_List.get(i).get_Child_Fish() == 0) {
                //현재 화면에 존재하는 물로기 개수를 샌다
                if (fish_List.get(i).get_Visible_Fish_Flag() == true) {


                    //기본 물고기
                    if(fish_List.get(i) instanceof Fish_Touch_Default){
                        count_Monster_Fish[0]++;

                        //보스 물고기
                        if(fish_List.get(i).get_Class_Num() == 2){
                            count_Monster_Fish[1]++;
                        }
                    }

                    //드래그 물고기
                    else if(fish_List.get(i) instanceof Fish_Touch_Default) {
                        count_Monster_Fish[2]++;
                    }

                    //오징어
                    else if(fish_List.get(i) instanceof Fish_Touch_Squid) {
                        count_Monster_Fish[3]++;
                    }

                    //강철 참돔
                    else if(fish_List.get(i) instanceof Fish_Drag_Steelbream) {
                        count_Monster_Fish[4]++;
                    }

                    //전기 뱀장어
                    else if(fish_List.get(i) instanceof Fish_Touch_Ell) {
                        count_Monster_Fish[5]++;
                    }

                    //거북이
                    else if(fish_List.get(i) instanceof Fish_Trap_Turtle) {
                        count_Monster_Fish[6]++;
                    }

                    //청새치
                    else if(fish_List.get(i) instanceof Fish_Touch_Marlin) {
                        count_Monster_Fish[7]++;
                    }

                    //상어
                    else if(fish_List.get(i) instanceof Fish_Drag_Shark) {
                        count_Monster_Fish[8]++;
                    }


                }
            }
        }

        //그라운드
        for(int i=0; i<ground_List.size(); i++){
            if(ground_List.get(i).get_Visible_Ground_Flag() == true) {
                //기본 달팽이
                if(ground_List.get(i) instanceof Ground_Touch_Snail){
                    count_Monster_Ground[0]++;

                    //보스 달팽이
                    if(ground_List.get(i).get_Class_Num() == 2){
                        count_Monster_Ground[1]++;
                    }
                }

                //꽃게
                else if(ground_List.get(i) instanceof Ground_Drag_Crab){
                    count_Monster_Ground[2]++;
                }

                //성게
                else if(ground_List.get(i) instanceof Ground_Trap_Urchin){
                    count_Monster_Ground[3]++;
                }

                //가오리
                else if(ground_List.get(i) instanceof Ground_Touch_Stingray){
                    count_Monster_Ground[4]++;
                }

                //소라게
                else if(ground_List.get(i) instanceof Ground_Touch_Hermit){
                    count_Monster_Ground[5]++;
                }

                //조개
                else if(ground_List.get(i) instanceof Ground_Drag_Clam){
                    count_Monster_Ground[6]++;
                }

                //악어
                else if(ground_List.get(i) instanceof Ground_Touch_Crocodile){
                    count_Monster_Ground[7]++;
                }

                //파도
                else if(ground_List.get(i) instanceof Ground_Drag_Wave){
                    count_Monster_Ground[8]++;
                }

                //가재
                else if(ground_List.get(i) instanceof Ground_Drag_Lobsters){
                    count_Monster_Ground[9]++;
                }

                //불가사리
                else if(ground_List.get(i) instanceof Ground_Touch_Starfish){
                    count_Monster_Ground[10]++;
                }

                //곰벌레
                else if(ground_List.get(i) instanceof Ground_Touch_Bearbug){
                    count_Monster_Ground[11]++;
                }

            }
        }




        //물고기
        for(int i=0; i<fish_List.size(); i++) {
            if (fish_List.get(i).get_Child_Fish() == 0) {
                if (fish_List.get(i).get_Visible_Fish_Flag() == false) {

                    if(day_Count == 1 && (monster_Explain_Db[0] == 0)){
                        explain[0] = true;
                        first_Explain = true;
                        me1 = 1;
                        String sql = "UPDATE maincharacterinfo SET me1 = '" + me1 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 7 && (monster_Explain_Db[1] == 0)){

                        explain[0] = false;
                        explain[1] = true;
                        first_Explain = true;
                        me2 = 1;
                        me1 = 1;
                        String sql = "UPDATE maincharacterinfo SET me2 = '" + me2 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 11 && (monster_Explain_Db[2] == 0)){ //드래그 물고기
                        explain[1] = false;
                        explain[2] = true;
                        first_Explain = true;
                        me3 = 1;
                        String sql = "UPDATE maincharacterinfo SET me3 = '" + me3 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 19 && (monster_Explain_Db[3] == 0)){ //꽃게
                        explain[2] = false;
                        explain[3] = true;
                        first_Explain = true;
                        me4 = 1;
                        String sql = "UPDATE maincharacterinfo SET me4 = '" + me4 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 24 && (monster_Explain_Db[4] == 0)){ //오징어
                        explain[3] = false;
                        explain[4] = true;
                        first_Explain = true;
                        me5 = 1;
                        String sql = "UPDATE maincharacterinfo SET me5 = '" + me5 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 30 && (monster_Explain_Db[5] == 0)){ //성게
                        explain[4] = false;
                        explain[5] = true;
                        first_Explain = true;
                        me6 = 1;
                        String sql = "UPDATE maincharacterinfo SET me6 = '" + me6 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 35 && (monster_Explain_Db[6] == 0)){ //가오리
                        explain[5] = false;
                        explain[6] = true;
                        first_Explain = true;
                        me7 = 1;
                        String sql = "UPDATE maincharacterinfo SET me7 = '" + me7 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 40 && (monster_Explain_Db[7] == 0)){ //강철참돔
                        explain[6] = false;
                        explain[7] = true;
                        first_Explain = true;
                        me8 = 1;
                        String sql = "UPDATE maincharacterinfo SET me8 = '" + me8 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 45 && (monster_Explain_Db[8] == 0)){ //무적소라게
                        explain[7] = false;
                        explain[8] = true;
                        first_Explain = true;
                        me9 = 1;
                        String sql = "UPDATE maincharacterinfo SET me9 = '" + me9 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 50 && (monster_Explain_Db[9] == 0)){ // 조개
                        explain[8] = false;
                        explain[9] = true;
                        first_Explain = true;
                        me10 = 1;
                        String sql = "UPDATE maincharacterinfo SET me10 = '" + me10 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 55 && (monster_Explain_Db[10] == 0)){ // 악어
                        explain[9] = false;
                        explain[10] = true;
                        first_Explain = true;
                        me11 = 1;
                        String sql = "UPDATE maincharacterinfo SET me11 = '" + me11 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 60 && (monster_Explain_Db[11] == 0)){ // 뱀장어
                        explain[10] = false;
                        explain[11] = true;
                        first_Explain = true;
                        me12 = 1;
                        String sql = "UPDATE maincharacterinfo SET me12 = '" + me12 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 64 && (monster_Explain_Db[12] == 0)){ // 파도
                        explain[11] = false;
                        explain[12] = true;
                        first_Explain = true;
                        me13 = 1;
                        String sql = "UPDATE maincharacterinfo SET me13 = '" + me13 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 70 && (monster_Explain_Db[13] == 0)){ //거북이
                        explain[12] = false;
                        explain[13] = true;
                        first_Explain = true;
                        me14 = 1;
                        String sql = "UPDATE maincharacterinfo SET me14 = '" + me14 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 75 && (monster_Explain_Db[14] == 0)){ // 가재
                        explain[13] = false;
                        explain[14] = true;
                        first_Explain = true;
                        me15 = 1;
                        String sql = "UPDATE maincharacterinfo SET me15 = '" + me15 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 80 && (monster_Explain_Db[15] == 0)){ // 청새치
                        explain[14] = false;
                        explain[15] = true;
                        first_Explain = true;
                        me16 = 1;
                        String sql = "UPDATE maincharacterinfo SET me16 = '" + me16 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 90 && (monster_Explain_Db[16] == 0)){ // 불가사리
                        explain[15] = false;
                        explain[16] = true;
                        first_Explain = true;
                        me17 = 1;
                        String sql = "UPDATE maincharacterinfo SET me17 = '" + me17 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 100 && (monster_Explain_Db[17] == 0)){ // 곰벌레
                        explain[16] = false;
                        explain[17] = true;
                        first_Explain = true;
                        me18 = 1;
                        String sql = "UPDATE maincharacterinfo SET me18 = '" + me18 + "'";
                        MainActivity.database.execSQL(sql);
                    }else if(day_Count == 110 && (monster_Explain_Db[18] == 0)){ // 상어
                        explain[17] = false;
                        explain[18] = true;
                        first_Explain = true;
                        me19 = 1;
                        String sql = "UPDATE maincharacterinfo SET me19 = '" + me19 + "'";
                        MainActivity.database.execSQL(sql);
                    }




                    if(day_Count <= 1) {
                        //기본 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[0] < 3) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[0]++;
                        }
                    }
                    if(day_Count == 2) {
                        //기본 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[0] < 5) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[0]++;
                        }
                    }
                    if(day_Count == 3) {
                        //기본 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[0] < 6) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[0]++;
                        }
                    }

                    /**
                     * 계속
                     */
                    if(day_Count >= 4) {
                        //기본 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[0] < 5) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[0]++;
                        }
                    }
                    if(day_Count == 5) {
                        //기본 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[0] < 6) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[0]++;
                        }
                    }
                    if(day_Count == 6) {
                        //기본 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[0] < 8) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[0]++;
                        }
                    }
                    if(day_Count == 7) {
                        //기본 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[0] < 9) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[0]++;
                        }
                    }
                    if(day_Count == 8) {
                        //기본 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[0] < 10) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[0]++;
                        }
                    }
                    if(day_Count == 9) {
                        //기본 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[0] < 10) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[0]++;
                        }
                    }

                    if(day_Count == 10) {
                        //기본 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[0] < 15) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[0]++;
                        }
                    }


                    if(day_Count >= 11) {
                        //드래그 물고기
                        if (fish_List.get(i) instanceof Fish_Drag_Default && count_Monster_Fish[2] < 1) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[2]++;
                        }
                    }

                    /**
                     * 계속
                     */
                    if(day_Count >= 13 && (day_Count%3 == 0)) {
                        //드래그 물고기
                        if (fish_List.get(i) instanceof Fish_Drag_Default && count_Monster_Fish[2] < 2) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[2]++;
                        }
                    }
                    if(day_Count >= 15 && (day_Count%7 == 0)) {
                        //드래그 물고기
                        if (fish_List.get(i) instanceof Fish_Drag_Default && count_Monster_Fish[2] < 3) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[2]++;
                        }
                    }
                    if(day_Count == 17) {
                        //드래그 물고기
                        if (fish_List.get(i) instanceof Fish_Drag_Default && count_Monster_Fish[2] < 5) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[2]++;
                        }
                    }

                    //오징어
                    if(day_Count == 24){
                        if (fish_List.get(i) instanceof Fish_Touch_Squid && count_Monster_Fish[3] < 1) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[3]++;
                        }
                    }
                    if(day_Count >= 26){
                        if (fish_List.get(i) instanceof Fish_Touch_Squid && count_Monster_Fish[3] < 2) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[3]++;
                        }
                    }
                    if(day_Count == 28){
                        if (fish_List.get(i) instanceof Fish_Touch_Squid && count_Monster_Fish[3] < 3) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[3]++;
                        }
                    }


                    //강철 참돔
                    if(day_Count >= 40 && day_Count%3 == 0){
                        if (fish_List.get(i) instanceof Fish_Drag_Steelbream && count_Monster_Fish[4] < 1) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[4]++;
                        }
                    }


                    //4강철 참돔, 5전기 뱀장어, 6거북이, 7청새치, 8상어 5소라게, 6,조개, 7악어, 8파도, 9가재, 10불가사리, 11곰벌레,    조개, 악어 뱀장어 파도 거북이 가재 청새치 불가사리 곰벌레 상어

                    //뱀장어
                    if(day_Count >= 60 && day_Count%5 == 0){
                        if (fish_List.get(i) instanceof Fish_Touch_Ell && count_Monster_Fish[5] < 1) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[5]++;
                        }
                    }







                    //거북이
                    if(day_Count >= 70 && day_Count%7 == 0){
                        if (fish_List.get(i) instanceof Fish_Trap_Turtle && count_Monster_Fish[6] < 1) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[6]++;
                        }
                    }

                    //청새치
                    if(day_Count >= 80 && (day_Count%4 == 0)){
                        if (fish_List.get(i) instanceof Fish_Touch_Marlin && count_Monster_Fish[7] < 1) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[7]++;
                        }
                    }
                    if(day_Count == 81){
                        if (fish_List.get(i) instanceof Fish_Touch_Marlin && count_Monster_Fish[7] < 3) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[7]++;
                        }
                    }

                    if(day_Count >= 85 && (day_Count%3 == 0)) {
                        if (fish_List.get(i) instanceof Fish_Touch_Marlin && count_Monster_Fish[7] < 5) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[7]++;
                        }
                    }

                    if(day_Count == 88){
                        if (fish_List.get(i) instanceof Fish_Touch_Marlin && count_Monster_Fish[7] < 10) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[7]++;
                        }
                    }

                    //상어
                    if(day_Count >= 110 && (day_Count%5 == 0)){
                        if (fish_List.get(i) instanceof Fish_Drag_Shark && count_Monster_Fish[8] < 1) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[8]++;
                        }

                    }

                    if(day_Count >= 120 && (day_Count%12 == 0)){
                        if (fish_List.get(i) instanceof Fish_Drag_Shark && count_Monster_Fish[8] < 2) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[8]++;

                        }
                    }

                    //보스 물고기
                    if(fish_List.get(i).get_Class_Num() == 2){
                        if (fish_List.get(i).get_Visible_Fish_Flag() == false) {
                            if (day_Count >= 5 && (day_Count % 5 == 0) ) {
                                if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[1] < 1) {
                                    fish_List.get(i).set_Visible_Fish_Flag(true);
                                    count_Monster_Fish[1]++;
                                }
                            }
                            if (day_Count >= 10 && (day_Count % 13 == 0) ) {
                                if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[1] < 2) {
                                    fish_List.get(i).set_Visible_Fish_Flag(true);
                                    count_Monster_Fish[1]++;
                                }
                            }
                            if (day_Count >= 20 && (day_Count % 21 == 0)) {
                                if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[1] < 3) {
                                    fish_List.get(i).set_Visible_Fish_Flag(true);
                                    count_Monster_Fish[1]++;
                                }
                            }
                        }
                    }


                    //무한 모드
                    if(Monster_Clean){
                        //기본 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[0] < 7) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[0]++;
                        }
                        //보스 물고기
                        if (fish_List.get(i) instanceof Fish_Touch_Default && count_Monster_Fish[1] < 3) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[1]++;
                        }
                        //드래그 물고기
                        if (fish_List.get(i) instanceof Fish_Drag_Default && count_Monster_Fish[2] < 3) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[2]++;
                        }
                        //오징어
                        if (fish_List.get(i) instanceof Fish_Touch_Squid && count_Monster_Fish[3] < 2) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[3]++;
                        }
                        //강철 참돔
                        if (fish_List.get(i) instanceof Fish_Drag_Steelbream && count_Monster_Fish[4] < 1) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[4]++;
                        }
                        //뱀장어
                        if (fish_List.get(i) instanceof Fish_Touch_Ell && count_Monster_Fish[5] < 1) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[5]++;
                        }
                        //거북이
                        if (fish_List.get(i) instanceof Fish_Trap_Turtle && count_Monster_Fish[6] < 1) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[6]++;
                        }
                        //청새치
                        if (fish_List.get(i) instanceof Fish_Touch_Marlin && count_Monster_Fish[7] < 7) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[7]++;
                        }
                        //상어
                        if (fish_List.get(i) instanceof Fish_Drag_Shark && count_Monster_Fish[8] < 2) {
                            fish_List.get(i).set_Visible_Fish_Flag(true);
                            count_Monster_Fish[8]++;
                        }
                    }


                    fish_List.get(i).set_Position();

                }
            }

        }






//            //바닥 생명체
        for(int i=0; i<ground_List.size(); i++){


            if(ground_List.get(i).get_Child_Ground() == 0) {
                if (ground_List.get(i).get_Visible_Ground_Flag() == false) {
                    if (day_Count == 7) {
                        //달팽이
                        if (ground_List.get(i) instanceof Ground_Touch_Snail && count_Monster_Ground[0] < 1) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[0]++;

//                                if(monster_Explain_Db[1] == 0) {
//                                    explain[0] = false;
//                                    explain[1] = true;
//                                    first_Explain = true;
//                                }

                        }
                    }
                    /**
                     * 계속
                     */
                    if (day_Count >= 8) {
                        if (ground_List.get(i) instanceof Ground_Touch_Snail && count_Monster_Ground[0] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[0]++;
                        }
                    }
                    if (day_Count == 9) {
                        if (ground_List.get(i) instanceof Ground_Touch_Snail && count_Monster_Ground[0] < 3) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[0]++;
                        }
                    }
                    if (day_Count == 10) {
                        if (ground_List.get(i) instanceof Ground_Touch_Snail && count_Monster_Ground[0] < 4) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[0]++;
                        }
                    }
                    if (day_Count == 11) {
                        if (ground_List.get(i) instanceof Ground_Touch_Snail && count_Monster_Ground[0] < 5) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[0]++;
                        }
                    }
//                        달팽이
                    if (day_Count == 12) {
                        if (ground_List.get(i) instanceof Ground_Touch_Snail && count_Monster_Ground[0] < 6) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[0]++;
                        }
                    }

                    //보스 달팽이
                    if(ground_List.get(i).get_Class_Num() == 2){
                        if (ground_List.get(i).get_Visible_Ground_Flag() == false) {
                            if ((day_Count >= 9 && day_Count % 9 == 0 )) {
                                if (ground_List.get(i) instanceof Ground_Touch_Snail && (count_Monster_Ground[1] < 1)) {
                                    ground_List.get(i).set_Visible_Ground_Flag(true);
                                    count_Monster_Ground[1]++;
                                    Log.e("@", " day= " + day_Count + " 달팽이 생성1" + " count_Monster_Ground[1] = " + count_Monster_Ground[1]);
                                }
                            }
                            if ((day_Count >= 15 && day_Count % 15 == 0 ) ) {
                                if (ground_List.get(i) instanceof Ground_Touch_Snail && (count_Monster_Ground[1] < 2)) {
                                    ground_List.get(i).set_Visible_Ground_Flag(true);
                                    count_Monster_Ground[1]++;
                                    Log.e("@", " day= " + day_Count + " 달팽이 생성2" + " count_Monster_Ground[1] = " + count_Monster_Ground[1]);
                                }
                            }
                        }
                    }

                    //꽃게
                    if ((day_Count >= 19 && (day_Count % 2 == 0)) || day_Count == 19 ) {
                        if (ground_List.get(i) instanceof Ground_Drag_Crab && count_Monster_Ground[2] < 1) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[2]++;
                        }
                    }
                    if (day_Count >= 21 && (day_Count % 4 == 0)) {
                        if (ground_List.get(i) instanceof Ground_Drag_Crab && count_Monster_Ground[2] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[2]++;
                        }
                    }
                    if (day_Count == 23) {
                        if (ground_List.get(i) instanceof Ground_Drag_Crab && count_Monster_Ground[2] < 3) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[2]++;
                        }
                    }
                    if (day_Count == 25) {
                        if (ground_List.get(i) instanceof Ground_Drag_Crab && count_Monster_Ground[2] < 4) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[2]++;
                        }
                    }

                    //성게
                    if(day_Count >= 30 && day_Count % 2 == 0 ){
                    //성게성게
                        if (ground_List.get(i) instanceof Ground_Trap_Urchin && count_Monster_Ground[3] < 1) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            ((Ground_Trap_Urchin)ground_List.get(i)).set_Position();
                            count_Monster_Ground[3]++;
                        }
                    }
                    if(day_Count == 31){
                        if (ground_List.get(i) instanceof Ground_Trap_Urchin && count_Monster_Ground[3] < 1) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            ((Ground_Trap_Urchin)ground_List.get(i)).set_Position();
                            count_Monster_Ground[3]++;
                        }
                    }
                    if(day_Count >= 32 && day_Count % 3 == 0 ){
                        if (ground_List.get(i) instanceof Ground_Trap_Urchin && count_Monster_Ground[3] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            ((Ground_Trap_Urchin)ground_List.get(i)).set_Position();
                            count_Monster_Ground[3]++;
                        }
                    }
                    if(day_Count >= 33 && day_Count % 7 == 0  ){
                        if (ground_List.get(i) instanceof Ground_Trap_Urchin && count_Monster_Ground[3] < 3) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            ((Ground_Trap_Urchin)ground_List.get(i)).set_Position();
                            count_Monster_Ground[3]++;
                        }
                    }
                    if(day_Count == 34){
                        if (ground_List.get(i) instanceof Ground_Trap_Urchin && count_Monster_Ground[3] < 4) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            ((Ground_Trap_Urchin)ground_List.get(i)).set_Position();
                            count_Monster_Ground[3]++;
                        }
                    }
                    if(day_Count >= 35  && day_Count % 11 == 0 ){
                        if (ground_List.get(i) instanceof Ground_Trap_Urchin && count_Monster_Ground[3] < 5) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            ((Ground_Trap_Urchin)ground_List.get(i)).set_Position();
                            count_Monster_Ground[3]++;
                        }
                    }

                    //가오리
                    if(day_Count >= 35){
                        if (ground_List.get(i) instanceof Ground_Touch_Stingray && count_Monster_Ground[4] < 1) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[4]++;
                        }
                    }

                    if(day_Count >= 37 && (day_Count % 5 == 0)){
                        if (ground_List.get(i) instanceof Ground_Touch_Stingray && count_Monster_Ground[4] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[4]++;
                        }
                    }

                    if(day_Count == 39){
                        if (ground_List.get(i) instanceof Ground_Touch_Stingray && count_Monster_Ground[4] < 3) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[4]++;
                        }
                    }

                    //무적 소라게
                    if(day_Count >= 45 && (day_Count % 3 == 0)){
                        if (ground_List.get(i) instanceof Ground_Touch_Hermit && count_Monster_Ground[5] < 1) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[5]++;
                        }
                    }
                    if(day_Count >= 46 && (day_Count % 4 == 0)){
                        if (ground_List.get(i) instanceof Ground_Touch_Hermit && count_Monster_Ground[5] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[5]++;
                        }
                    }
                    if(day_Count == 47){
                        if (ground_List.get(i) instanceof Ground_Touch_Hermit && count_Monster_Ground[5] < 3) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[5]++;
                        }
                    }
                    if(day_Count == 48){
                        if (ground_List.get(i) instanceof Ground_Touch_Hermit && count_Monster_Ground[5] < 4) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[5]++;
                        }
                    }


                    //4강철 참돔, 5전기 뱀장어, 6거북이, 7청새치, 8상어 5소라게, 6,조개, 7악어, 8파도, 9가재, 10불가사리, 11곰벌레,    조개, 악어 뱀장어 파도 거북이 가재 청새치 불가사리 곰벌레 상어


                    //조개
                    if(day_Count >= 50 && day_Count%25 == 0){
                        if (ground_List.get(i) instanceof Ground_Drag_Clam && count_Monster_Ground[6] < 1) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[6]++;
                        }
                    }


                    //악어
                    if(day_Count >= 55 && (day_Count % 5 == 0)){
                        if (ground_List.get(i) instanceof Ground_Touch_Crocodile && count_Monster_Ground[7] < 1) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[7]++;
                        }
                    }
                    if(day_Count >= 60 && (day_Count % 3 == 0)){
                        if (ground_List.get(i) instanceof Ground_Touch_Crocodile && count_Monster_Ground[7] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[7]++;
                        }
                    }

                    //파도함



                    //가제
                    if(day_Count >= 75 && (day_Count % 3 == 0)){
                        if (ground_List.get(i) instanceof Ground_Drag_Lobsters && count_Monster_Ground[9] < 1) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[9]++;
                        }
                    }

                    if(day_Count == 81 && (day_Count % 9 == 0)){
                        if (ground_List.get(i) instanceof Ground_Drag_Lobsters && count_Monster_Ground[9] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[9]++;
                        }
                    }

                    //불가사리
                    if(day_Count >= 90 && (day_Count % 2 == 0)){
                        if (ground_List.get(i) instanceof Ground_Touch_Starfish && count_Monster_Ground[10] < 1) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[10]++;
                        }
                    }
                    if(day_Count == 93){
                        if (ground_List.get(i) instanceof Ground_Touch_Starfish && count_Monster_Ground[10] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[10]++;
                        }
                    }

                    //곰벨러
                    if(day_Count >= 100 && (day_Count % 2 == 0)){
                        if (ground_List.get(i) instanceof Ground_Touch_Bearbug && count_Monster_Ground[11] < 1) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[11]++;
                        }
                    }
                    if(day_Count >= 103 && (day_Count % 7 == 0)){
                        if (ground_List.get(i) instanceof Ground_Touch_Bearbug && count_Monster_Ground[11] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[11]++;
                        }
                    }
                    if(day_Count == 107){
                        if (ground_List.get(i) instanceof Ground_Touch_Bearbug && count_Monster_Ground[11] < 3) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[11]++;
                        }
                    }


                    //무한 모드
                    if(Monster_Clean){
                        //달팽이
                        if (ground_List.get(i) instanceof Ground_Touch_Snail && count_Monster_Ground[0] < 4) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[0]++;
                        }
                        //보스 달팽이
                        if (ground_List.get(i) instanceof Ground_Touch_Snail && count_Monster_Ground[1] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[1]++;
                        }
                        //꽃게
                        if (ground_List.get(i) instanceof Ground_Drag_Crab && count_Monster_Ground[2] < 3) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[2]++;
                        }
                        //성게
                        if (ground_List.get(i) instanceof Ground_Trap_Urchin && count_Monster_Ground[3] < 4) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            ((Ground_Trap_Urchin)ground_List.get(i)).set_Position();
                            count_Monster_Ground[3]++;
                        }
                        //가오리
                        if (ground_List.get(i) instanceof Ground_Touch_Stingray && count_Monster_Ground[4] < 3) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[4]++;
                        }
                        //무적소라게
                        if (ground_List.get(i) instanceof Ground_Touch_Hermit && count_Monster_Ground[5] < 3) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[5]++;
                        }
                        //악어
                        if (ground_List.get(i) instanceof Ground_Touch_Crocodile && count_Monster_Ground[7] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[7]++;
                        }
                        //가제
                        if (ground_List.get(i) instanceof Ground_Drag_Lobsters && count_Monster_Ground[9] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[9]++;
                        }
                        //불가사리
                        if (ground_List.get(i) instanceof Ground_Touch_Starfish && count_Monster_Ground[10] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[10]++;
                        }
                        //곰벌레
                        if (ground_List.get(i) instanceof Ground_Touch_Bearbug && count_Monster_Ground[11] < 2) {
                            ground_List.get(i).set_Visible_Ground_Flag(true);
                            count_Monster_Ground[11]++;
                        }
                    }










                    ground_List.get(i).set_Position();

                }



            }


        }


        //파도
        wave_Count = 0;
        wave_Set_X_Temp = 0;
        if(day_Count >= 64 && day_Count%4 == 0) {
            for (int i = 0; i < ground_List.size(); i++) {
                if (ground_List.get(i) instanceof Ground_Drag_Wave) {

                    if(!(ground_List.get(i).get_Visible_Ground_Flag())) {
                        wave_Count++;
                    }

                }
            }

            if (wave_Count >= 20) {
                wave_X_Point = 30 + (float) Math.random() * (window_Width - 100);

//                Log.e("@", ground_List.size() + " = i");
                for (int i = 0; i < ground_List.size(); i++) {
                    if (ground_List.get(i) instanceof Ground_Drag_Wave) {
                        ground_List.get(i).set_Position(wave_X_Point, -convertPixelsToDp(700, _context) + convertPixelsToDp(wave_Set_X_Temp * 15, _context));
                        ground_List.get(i).set_Visible_Ground_Flag(true);
                        wave_Set_X_Temp++;
                    }
                }
            }
        }









    }


    //중간 보스에서 나올 달팽이
    public void set_Snail_Position(float x, float y){
        for(int i=0; i<ground_List.size(); i++){

            if(ground_List.get(i) instanceof Ground_Touch_Snail && ground_List.get(i).get_Child_Ground() == 1){
                if(!(ground_List.get(i)).get_Visible_Ground_Flag()){
                    ground_List.get(i).set_Visible_Ground_Flag(true);
                    ground_List.get(i).set_Ground_Hp(5);
                    ground_List.get(i).set_Position(x,y);
                    break;
                }
            }
        }
    }

    //달팽이 중간보스
    public void set_Snail_Middle_Position(float x, float y){
        for(int i=0; i<ground_List.size(); i++){

            if(ground_List.get(i) instanceof Ground_Touch_Snail && ground_List.get(i).get_Child_Ground() == 2){
                if(!(ground_List.get(i)).get_Visible_Ground_Flag()){
                    ground_List.get(i).set_Visible_Ground_Flag(true);
                    ground_List.get(i).set_Position(x,y);
                    break;
                }
            }
        }
    }

    //불가사리 복제하기
    public void set_Starfish_Position(float x, float y){
        for(int i=0; i<ground_List.size(); i++){

            if(ground_List.get(i) instanceof Ground_Touch_Starfish){
                if(!(ground_List.get(i)).get_Visible_Ground_Flag()){
                    ground_List.get(i).set_Visible_Ground_Flag(true);
                    ground_List.get(i).set_Child_Ground(0);
                    ground_List.get(i).set_Position(x,y);
                    break;
                }
            }
        }
    }

    //그라운드 생명체 오브젝트 풀링
    public void ground_Total_Production(){

        //달팽이
        for(int i=0; i<4; i++) {
            ground_Touch_Snail = new Ground_Touch_Snail(window_Width - convertPixelsToDp(100, _context),
                    ground_Touch_Snail_Hp1_img[0].getWidth(),
                    ground_Touch_Snail_Hp1_img[0].getHeight(), random.nextInt(5) + 1, ground_Touch_Snail_Hp1_img[0].getWidth() + convertPixelsToDp(15, _context) ,ground_Touch_Snail_Hp1_img[0].getHeight(), dm.heightPixels);
            ground_Touch_Snail.set_Class_Num(0);
            ground_Touch_Snail.set_Visible_Ground_Flag(false);
            ground_List.add(ground_Touch_Snail);
        }

        //달팽이 중간보스에서 나올 달팽이
        for(int i=0; i<5; i++) {
            ground_Touch_Snail = new Ground_Touch_Snail(window_Width - convertPixelsToDp(100, _context),
                    ground_Touch_Snail_Hp1_img[0].getWidth(),
                    ground_Touch_Snail_Hp1_img[0].getHeight(), 5, ground_Touch_Snail_Hp1_img[0].getWidth() + convertPixelsToDp(15, _context) ,ground_Touch_Snail_Hp1_img[0].getHeight(), dm.heightPixels);
            ground_Touch_Snail.set_Child_Ground(1);
            ground_Touch_Snail.set_Visible_Ground_Flag(false);
            ground_List.add(ground_Touch_Snail);
        }

        //달팽이 보스에서 나올 달팽이 중간 보스
        for(int i=0; i<3; i++){
            ground_Touch_Snail = new Ground_Touch_Snail(window_Width - convertPixelsToDp(115, _context),
                    ground_Touch_Snail_Hp1_img[0].getWidth(),
                    ground_Touch_Snail_Hp1_img[0].getHeight(), 5, ground_Touch_Snail_Hp1_img[0].getWidth() + convertPixelsToDp(15, _context) ,ground_Touch_Snail_Hp1_img[0].getHeight(), dm.heightPixels);
            ground_Touch_Snail.set_Class_Num(1);
            ground_Touch_Snail.set_Child_Ground(2);
            ground_Touch_Snail.set_Visible_Ground_Flag(false);
            ground_List.add(ground_Touch_Snail); //달팽이 생성
        }

        //달팽이 보스
        for(int i=0; i<2; i++){
            ground_Touch_Snail = new Ground_Touch_Snail(window_Width - convertPixelsToDp(125, _context),
                    ground_Snail_Middle_Hp1_img[0].getWidth(),
                    ground_Snail_Middle_Hp1_img[0].getHeight(), 5,ground_snail_Boss_Hp1_img[0].getWidth(),ground_snail_Boss_Hp1_img[0].getHeight(), random.nextInt(window_Width - convertPixelsToDp(125, _context)), -30, dm.heightPixels);
            ground_Touch_Snail.set_Class_Num(2);
            ground_Touch_Snail.set_Visible_Ground_Flag(false);
            ground_List.add(ground_Touch_Snail); //달팽이 생성
        }

        //꽃게
        for(int i=0; i<3; i++){
            ground_Drag_Crab = new Ground_Drag_Crab(window_Width - convertPixelsToDp(100, _context),
                    ground_Drag_Crab_img[0].getWidth(),
                    ground_Drag_Crab_img[0].getHeight(), 2 * day_Count * (1 + random.nextInt(3)), ground_Drag_Crab_img[0].getWidth(),ground_Drag_Crab_img[0].getHeight(), dm.heightPixels);
            ground_Drag_Crab.set_Visible_Ground_Flag(false);
            ground_List.add(ground_Drag_Crab); //꽃게
        }

//        소라게
        for(int i=0; i<3; i++){
            ground_Touch_Hermit = new Ground_Touch_Hermit(window_Width - convertPixelsToDp(100, _context),
                    ground_Touch_Hermit_Hp1_img[0].getWidth(),
                    ground_Touch_Hermit_Hp1_img[0].getHeight(), 5, ground_Touch_Hermit_Hp1_img[0].getWidth() + convertPixelsToDp(15, _context) ,ground_Touch_Hermit_Hp1_img[0].getHeight(), dm.heightPixels);
            ground_Touch_Hermit.set_Visible_Ground_Flag(false);
            ground_List.add(ground_Touch_Hermit); //소라게 생성
        }

        //불가사리
        for(int i=0; i<6; i++){
            ground_Touch_Starfish = new Ground_Touch_Starfish(window_Width - convertPixelsToDp(100, _context),
                    ground_Touch_Starfish_img[0].getWidth(),
                    ground_Touch_Starfish_img[0].getHeight(), 5, ground_Touch_Starfish_img[0].getWidth() + convertPixelsToDp(15, _context) ,ground_Touch_Starfish_img[0].getHeight(), dm.heightPixels);

            if(i > 1) {
                ground_Touch_Starfish.set_Child_Ground(1);
                ground_Touch_Starfish.set_Visible_Ground_Flag(false);
            }
            ground_Touch_Starfish.set_Visible_Ground_Flag(false);
            ground_List.add(ground_Touch_Starfish); //불가사리 생성
        }

//        가제
        for(int i=0; i<2; i++){
            ground_drag_lobsters = new Ground_Drag_Lobsters(window_Width - convertPixelsToDp(100, _context),
                    ground_Drag_Lobsters_img[0].getWidth(),
                    ground_Drag_Lobsters_img[0].getHeight(), 2 * day_Count * (1 + random.nextInt(3)), ground_Drag_Lobsters_img[0].getWidth() + convertPixelsToDp(15, _context) ,ground_Drag_Lobsters_img[0].getHeight(), dm.heightPixels);
            ground_drag_lobsters.set_Visible_Ground_Flag(false);

            ground_List.add(ground_drag_lobsters); //가제 생성
        }

//        곰벌레
        for(int i=0; i<3; i++){
            ground_Touch_Bearbug = new Ground_Touch_Bearbug(window_Width - convertPixelsToDp(100, _context),
                    ground_Touch_Bearbug_img[0].getWidth(),
                    ground_Touch_Bearbug_img[0].getHeight(), 50000, ground_Touch_Bearbug_img[0].getWidth() + convertPixelsToDp(15, _context) ,ground_Touch_Bearbug_img[0].getHeight(), dm.heightPixels);
            ground_Touch_Bearbug.set_Visible_Ground_Flag(false);
            ground_List.add(ground_Touch_Bearbug); //달팽이 생성
        }

//        가오리
        for(int i=0; i<3; i++){
            ground_Touch_Stingray = new Ground_Touch_Stingray(window_Width - convertPixelsToDp(100, _context),
                    ground_Touch_Stingray_img[0].getWidth(),
                    ground_Touch_Stingray_img[0].getHeight(), 5, ground_Touch_Stingray_img[0].getWidth() + convertPixelsToDp(15, _context) ,ground_Touch_Stingray_img[0].getHeight(), dm.heightPixels);
            ground_Touch_Stingray.set_Visible_Ground_Flag(false);
            ground_List.add(ground_Touch_Stingray);//가오리 추가

        }

//        악어
        for(int i=0; i<2; i++) {
            ground_Touch_Crocodile = new Ground_Touch_Crocodile(window_Width, window_Height,
                    ground_Touch_Crocodile_img[0].getWidth(),
                    ground_Touch_Crocodile_img[0].getHeight(), 1, ground_Touch_Crocodile_img[0].getWidth(), ground_Touch_Crocodile_img[0].getHeight());
            ground_Touch_Crocodile.set_Visible_Ground_Flag(false);
            ground_List.add(ground_Touch_Crocodile);
        }


//        조개
        ground_Drag_Clam = new Ground_Drag_Clam(window_Width,
                ground_Drag_Crab_img[0].getWidth(),
                ground_Drag_Crab_img[0].getHeight(), 5000, ground_Drag_Clam_img[0].getWidth(),ground_Drag_Clam_img[0].getHeight(), window_Height, window_Width);
        ground_Drag_Clam.set_Visible_Ground_Flag(false);
        ground_Drag_Clam.set_Position_Init();
        ground_List.add(ground_Drag_Clam); //꽃게

////        성게
        for(int i=0; i<4; i++){
            ground_trap_urchin = new Ground_Trap_Urchin(
                    window_Width,
                    ground_Trap_Urchin_img[0].getWidth(),
                    ground_Trap_Urchin_img[0].getHeight(),
                    window_Height,  //성게는 y 축도 랜덤으로 생성한다.
                    1,
                    ground_Trap_Urchin_img[0].getWidth(), ground_Trap_Urchin_img[0].getHeight()               );

            //성게 위치 초기화
            ground_trap_urchin.set_Position_Init();

            ground_trap_urchin.set_Visible_Ground_Flag(false);
            ground_List.add(ground_trap_urchin);//성게
        }


        // 파도
        for(int i=0; i<20; i++) {
            ground_drag_wave = new Ground_Drag_Wave(window_Width,
                    ground_Drag_Wave_img[0].getWidth(),
                    ground_Drag_Wave_img[0].getHeight() , 1, ground_Drag_Wave_img[1].getWidth(), ground_Drag_Wave_img[0].getHeight(), wave_X_Point, -convertPixelsToDp(700, _context) + convertPixelsToDp(i*15, _context), dm.heightPixels);
            ground_drag_wave.set_Visible_Ground_Flag(false);
            ground_List.add(ground_drag_wave); //파도
        }

    }

    //파도 숫자
    int wave_Count = 0;
    float wave_X_Point = 30 + (float)Math.random() * (window_Width-100);         //생성될 위치
    int starfish_Count = 0;
    //오브젝트 풀링 그라운드 hp0 인것 선별해서 다시 충전 시키기
    public void default_Ground_Alive(int select_Ground_Num){


        //중독 해제
        ground_List.get(select_Ground_Num).init_Status();

        if(ground_List.get(select_Ground_Num) instanceof Ground_Touch_Snail){
            if(ground_List.get(select_Ground_Num).get_Class_Num() == 2){
                //보스
                ground_List.get(select_Ground_Num).set_Ground_Hp(5);
            }else if(ground_List.get(select_Ground_Num).get_Class_Num() == 1){
                //중간보스
                ground_List.get(select_Ground_Num).set_Ground_Hp(5);
            }else {
                ground_List.get(select_Ground_Num).set_Ground_Hp(random.nextInt(5) + 1);
            }
        }
        //꽃게
        else if(ground_List.get(select_Ground_Num) instanceof Ground_Drag_Crab){
            ground_List.get(select_Ground_Num).set_Ground_Hp(2 * day_Count * (1 + random.nextInt(3)));
        }
        //소라게
        else if(ground_List.get(select_Ground_Num) instanceof Ground_Touch_Hermit){
            ground_List.get(select_Ground_Num).set_Ground_Hp(5);
        }

        //불가사리
        else if(ground_List.get(select_Ground_Num) instanceof Ground_Touch_Starfish){
            ground_List.get(select_Ground_Num).set_Ground_Hp(5);

            for(int i=0; i<ground_List.size(); i++){
                if(ground_List.get(i) instanceof Ground_Touch_Starfish) {

                    if (ground_List.get(i).get_Visible_Ground_Flag()) {
                        ground_List.get(i).set_Child_Ground(0);
                        starfish_Count++;
                    }
                    if (starfish_Count > 2) {
                        ground_List.get(i).set_Child_Ground(1);
                    }
                }
            }
            starfish_Count = 0;
        }

        //가제
        else if(ground_List.get(select_Ground_Num) instanceof Ground_Drag_Lobsters){
            ground_List.get(select_Ground_Num).set_Ground_Hp(2 * day_Count * (1 + random.nextInt(3)));
        }

        //곰벨러
        else if(ground_List.get(select_Ground_Num) instanceof Ground_Touch_Bearbug){
            ground_List.get(select_Ground_Num).set_Ground_Hp(50000);
        }

        //가오리
        else if(ground_List.get(select_Ground_Num) instanceof Ground_Touch_Stingray){
            ground_List.get(select_Ground_Num).set_Ground_Hp(5);
        }

        //악어
        else if(ground_List.get(select_Ground_Num) instanceof Ground_Touch_Crocodile){
            ground_List.get(select_Ground_Num).set_Ground_Hp(1);
        }

        //조개
        else if(ground_List.get(select_Ground_Num) instanceof Ground_Drag_Clam){
            ground_List.get(select_Ground_Num).set_Ground_Hp(5000);
//            ruby += 1;
        }

        //성게
        else if(ground_List.get(select_Ground_Num) instanceof Ground_Trap_Urchin){
            ground_List.get(select_Ground_Num).set_Ground_Hp(1);
            //성게 위치 초기화
        }

        //파도
        else if(ground_List.get(select_Ground_Num) instanceof Ground_Drag_Wave){
            ground_List.get(select_Ground_Num).set_Ground_Hp(1);
        }



        //충전한 것 안보이게
        ground_List.get(select_Ground_Num).set_Position_Init();
        ground_List.get(select_Ground_Num).set_Visible_Ground_Flag(false);





//        send_Ground();

    }




    /**물고기 위치 저장
     *
     */
    public ArrayList<Fish_Default_Body> get_Fish(){
        return fish_List;
    }
    public ArrayList<Ground_Default_Body> get_Ground(){
        return ground_List;
    }




    /**
     * 대기 중인 그라운드 보내기
     */
    int wave_Set_X_Temp = 0;
    public void send_Ground(){



        //그라운드 몬스터
        for(int i=0; i<ground_List.size(); i++){


            if(ground_List.get(i).get_Child_Ground() == 0) {

                if (!(ground_List.get(i) instanceof Ground_Drag_Wave) && ground_List.get(i).get_Visible_Ground_Flag() == false && ground_List.get(i).get_Child_Ground() == 0) {
                    ground_List.get(i).set_Visible_Ground_Flag(true);
                }
            }
        }



        //파도
        wave_Count = 0;
        wave_Set_X_Temp = 0;
        for(int i=0; i<ground_List.size(); i++) {
            if (ground_List.get(i) instanceof Ground_Drag_Wave && !(ground_List.get(i).get_Visible_Ground_Flag())) {
                wave_Count++;
            }
        }

        if(wave_Count == 20){
            wave_X_Point = 30 + (float)Math.random() * (window_Width-100);
            for(int i=0; i<ground_List.size(); i++){
                if(ground_List.get(i) instanceof Ground_Drag_Wave){
                    ground_List.get(i).set_Position(wave_X_Point, -convertPixelsToDp(700, _context) + convertPixelsToDp(wave_Set_X_Temp*15, _context));
                    ground_List.get(i).set_Visible_Ground_Flag(true);
                    wave_Set_X_Temp++;
                }
            }
        }


    }






    /**
     * 물고기 추가하기
     */
    public void add_Fish_Touch_Default(){
        fish_Touch_Default = new Fish_Touch_Default(window_Width, random.nextInt(5)+1, fish_Touch_Default_Hp1_img[0].getWidth() + convertPixelsToDp(15, _context), fish_Touch_Default_Hp1_img[0].getHeight(), dm.heightPixels);       //기본 fish_touch_default 물고기 생성

        //처음 시작할 때 물고기 설명 추가
        if(first_Default_Fish) {
            fish_Touch_Default.set_First_Test_Object((window_Width/2) - convertPixelsToDp(30, _context));
            first_Default_Fish = false;

            //메모리 오버 때문에 비트맵 설명, 기본 물고기 일때
//            if(fish_Touch_Default.get_Fish_ClassLand_Markin_Fish_Default();
//            }
        }
        fish_List.add(fish_Touch_Default);                                  //물고기 리스트에 추가
    }

    public void add_Fish_Touch_Default(float x, float y){
        fish_Touch_Default = new Fish_Touch_Default(window_Width, 5, fish_Touch_Default_Hp1_img[0].getWidth(), fish_Touch_Default_Hp1_img[0].getHeight(), x, y, dm.heightPixels);       //기본 fish_touch_default 물고기 생성
        fish_Touch_Default.set_Class_Num(0);
        fish_List.add(fish_Touch_Default);
    }

    /**
     * 중간보스 터치 물고기 추가
     */
    public void add_Middle_Fish_Touch_Default(){
        fish_Touch_Default = new Fish_Touch_Default(window_Width, 5 , fish_Touch_Default_Middle_Hp1_img[0].getWidth(), fish_Touch_Default_Middle_Hp1_img[0].getHeight(), dm.heightPixels);
        fish_Touch_Default.set_Class_Num(1);
        fish_List.add(fish_Touch_Default);                                  //물고기 리스트에 추가
    }

    public void add_Middle_Fish_Touch_Default(float x, float y){
        fish_Touch_Default = new Fish_Touch_Default(window_Width, 5, fish_Touch_Default_Middle_Hp1_img[0].getWidth(),fish_Touch_Default_Middle_Hp1_img[0].getHeight(),  x, y, dm.heightPixels);
        fish_Touch_Default.set_Class_Num(1);
        fish_List.add(fish_Touch_Default);
    }

    /**
     * 보스 터치 물고기 추가
     */
    public void add_Boss_Fish_Touch_Default(){

        fish_Touch_Default = new Fish_Touch_Default(window_Width, 5, fish_Touch_Default_Boss_Hp1_img[0].getWidth(), fish_Touch_Default_Boss_Hp1_img[0].getHeight(), dm.heightPixels);
        fish_Touch_Default.set_Class_Num(2);

        fish_List.add(fish_Touch_Default);                                  //물고기 리스트에 추가
    }

    /**
     * 오징어 추가
     */
    public void add_Fish_Touch_Squid(){
        fish_Touch_Squid = new Fish_Touch_Squid(window_Width, 1 , fish_Touch_Squid_img[0].getWidth(), fish_Touch_Squid_img[0].getHeight(), dm.heightPixels);       //오징어 생성

        if(first_Squid){
            fish_Touch_Squid.set_First_Test_Object((window_Width/2) - convertPixelsToDp(30, _context));
            first_Squid = false;

            //메모리 오버 때문에 비트맵 설명, 기본 물고기 일때
//            if(fish_Touch_Default.get_Fish_Class() == 1) {
//                game_thread.function_Init_Explain_Squid();
//            }
        }
        fish_List.add(fish_Touch_Squid);
    }
    /**
     * 오징어 먹물 추가
     */
    //먹물 표시
    ArrayList<Background_Effect_Squid_Ink> Background_Effect_Squid_Ink_Arr = new ArrayList<Background_Effect_Squid_Ink>();
    public void add_Fish_Touch_Squid_Ink(float xPoint, float yPoint){

        fish_Touch_Squid_Ink = new Background_Effect_Squid_Ink((int)xPoint, (int)yPoint);
        Background_Effect_Squid_Ink_Arr.add(fish_Touch_Squid_Ink);

    }

    /**
     * 랜드마크 추가
     */
    Land_Mark land_Mark;
    int land_Mark_Class = 1;
    boolean Monster_Clean = false; //보스 잡았을때,
    boolean infinitymode_Status = false; //마지막 건물 뿌러졋을때.
    boolean infinitymode_Status_Flag = false; //마지막 건물 뿌러졋을때.


    public void monster_Day_Add(){
        day_Count = 110;
        String sql = "UPDATE maincharacterinfo SET me1 = '1', me2 = '1', me3 = '1', me4 = '1', me5 = '1', me6 = '1', me7 = '1', me8 = '1', me9 = '1', me10 = '1', me11 = '1', me12 = '1', me13 = '1', me14 = '1', me15 = '1', me16 = '1', me17 = '1', me18 = '1', me19 = '1'";
        MainActivity.database.execSQL(sql);

        for(int i=0; i<explain.length; i++){
            if(explain[i]){
                monster_Explain_Db[i] = 1;
                break;
            }

        }


    }

    public void add_Ground_Land_Mark(){
        //이 부분 고칠


        if(land_Mark_Class == 1) {



            //랜드마크 메모리 기법
            game_thread.function_Land_Mark_1();
            land_Mark = new Land_Mark(window_Width, land_Mark1_img[0].getWidth(), land_Mark1_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark1_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark1_img[0].getHeight() / 2));
            effect_Bird.position((window_Width / 2) - (land_Mark1_img[0].getWidth() / 2)+ convertPixelsToDp(5, _context), (window_Height / 2) - (land_Mark1_img[0].getHeight() / 2) + convertPixelsToDp(30, _context));
        }else if(land_Mark_Class == 2){

            //랜드마크 메모리 기법

            game_thread.function_Land_Mark_2();
            land_Mark = new Land_Mark(window_Width, land_Mark2_img[0].getWidth(), land_Mark2_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark2_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark2_img[0].getHeight() / 2));
            effect_Bird.position((window_Width / 2) - (land_Mark2_img[0].getWidth() / 2)- convertPixelsToDp(10, _context), (window_Height / 2) - (land_Mark2_img[0].getHeight() / 2) + convertPixelsToDp(30, _context));




        }else if(land_Mark_Class == 3){

            //랜드마크 메모리 기법
            game_thread.function_Land_Mark_3();
            land_Mark = new Land_Mark(window_Width, land_Mark3_img[0].getWidth(), land_Mark3_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark3_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark3_img[0].getHeight() / 2));
            effect_Bird.position((window_Width / 2) - (land_Mark3_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark3_img[0].getHeight() / 2) + convertPixelsToDp(30, _context));
        }else if(land_Mark_Class == 4){

            //랜드마크 메모리 기법
            game_thread.function_Land_Mark_4();
            land_Mark = new Land_Mark(window_Width, land_Mark4_img[0].getWidth(), land_Mark4_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark4_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark4_img[0].getHeight() / 2));
            effect_Bird.position((window_Width / 2) - (land_Mark4_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark4_img[0].getHeight() / 2) + convertPixelsToDp(30, _context));
        }else if(land_Mark_Class == 5){

            //랜드마크 메모리 기법
            game_thread.function_Land_Mark_5();
            land_Mark = new Land_Mark(window_Width, land_Mark5_img[0].getWidth(), land_Mark5_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark5_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark5_img[0].getHeight() / 2));
            effect_Bird.position((window_Width / 2) - (land_Mark5_img[0].getWidth() / 2)+ convertPixelsToDp(55, _context), (window_Height / 2) - (land_Mark5_img[0].getHeight() / 2) + convertPixelsToDp(30, _context));

        }else if(land_Mark_Class == 6){
            game_thread.function_Land_Mark_6();
            land_Mark = new Land_Mark(window_Width, land_Mark6_img[0].getWidth(), land_Mark6_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark6_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark6_img[0].getHeight() / 2));
            effect_Bird.position((window_Width / 2) - (land_Mark6_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark6_img[0].getHeight() / 2) + convertPixelsToDp(30, _context));

            //몬스터 추가
            fish_Trap_Monster = new Fish_Monster(window_Width, 400000, fish_Drag_Steelbream_img[0].getWidth(), fish_Drag_Steelbream_img[0].getHeight(), dm.heightPixels, window_Height);
            fish_Trap_Monster.set_Visible_Fish_Flag(true);
            fish_Trap_Monster.set_Position_Init();
            fish_List.add(fish_Trap_Monster);
            infinitymode_Status_Flag = true;
            monster_Day_Add();

        }else {
            game_thread.function_Land_Mark_7();
            land_Mark = new Land_Mark(-1000, -1000, -1000, 400, -1000, -1000);
            effect_Bird.position(-1000, -1000);
        }


        land_Mark.set_Class_Num(land_Mark_Class);
        land_Mark_Class++;


        ground_List.add(land_Mark);

    }

    /**
     * 성게 추가
     */
    public void add_Ground_Urchin(){
        ground_trap_urchin = new Ground_Trap_Urchin(
                window_Width,
                ground_Trap_Urchin_img[0].getWidth(),
                ground_Trap_Urchin_img[0].getHeight(),
                window_Height,  //성게는 y 축도 랜덤으로 생성한다.
                1,
                ground_Trap_Urchin_img[0].getWidth(), ground_Trap_Urchin_img[0].getHeight()               );

        if(first_Urchin && ground_trap_urchin.get_Ground_Class() == 10){
            // fish_Drag_Default.set_First_Test_Object((window_Width/2)- convertPixelsToDp(30, _context));
            ground_trap_urchin.set_First_Test_Object((window_Width/2)- convertPixelsToDp(30, _context), (window_Height/2) - ground_Trap_Urchin_img[0].getHeight() );
            first_Urchin = false;

            //비트맵 메모리 관리 기법
//            game_thread.function_Init_Explain_Urchin();

        }

        ground_List.add(ground_trap_urchin);//성게
    }



    /**
     * 물고기 움직임 -> 물고기 각 쓰레드를 주게 되면 부하가 심하대 따라서 한 함수로 모든 물고기를 제어한다.
     */

    public synchronized void fish_Move(){

        for(int i=0; i<fish_List.size(); i++){

            if(fish_List.get(i).get_Fish_Hp() > 0 && fish_List.get(i).get_Visible_Fish_Flag()) {


//                Log.e("@","@ 크기 = " + dm.heightPixels);
                fish_List.get(i).fish_Object_Move();
            }
        }
    }


    /**
     * 그라운드 움직임
     */

    public synchronized void ground_Move(){




        for(int i=0; i<ground_List.size(); i++){

            if(!ground_List.get(i).get_Visible_Ground_Flag()){
                continue;
            }

            if(ground_List.get(i) instanceof Ground_Touch_Snail) {      //달팽이 무빙 함수 이용
                ((Ground_Touch_Snail) ground_List.get(i)).ground_Object_Move();

                if(((Ground_Touch_Snail) ground_List.get(i)).get_Class_Num() == 1){ //달팽이 중간 보스 일때

                    if(((Ground_Touch_Snail) ground_List.get(i)).get_Pragnant_Flag()){  //일반 달팽이 소환
                        ((Ground_Touch_Snail) ground_List.get(i)).set_Pragnant();

//                        add_Ground_Snail(ground_List.get(i).get_Ground_Point_X() + convertPixelsToDp(random.nextInt(30), _context) , ground_List.get(i).get_Ground_Point_Y()  - ground_List.get(i).get_Height_Size());     //달팽이 추가
                        set_Snail_Position(ground_List.get(i).get_Ground_Point_X() + convertPixelsToDp(random.nextInt(30), _context) , ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size());

                    }

                }else if(((Ground_Touch_Snail) ground_List.get(i)).get_Class_Num() == 2){ //달팽이 중간 보스 일때

                    if(((Ground_Touch_Snail) ground_List.get(i)).get_Pragnant_Flag()){  //중간보스 달팽이 생성
                        ((Ground_Touch_Snail) ground_List.get(i)).set_Pragnant();

                        set_Snail_Middle_Position(ground_List.get(i).get_Ground_Point_X() + convertPixelsToDp(random.nextInt(30), _context) , ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size());
//                        add_Ground_Middle_Boss(ground_List.get(i).get_Ground_Point_X() + convertPixelsToDp(random.nextInt(30), _context) , ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size());     //달팽이 추가


                    }

                }


            }else {

                ground_List.get(i).ground_Object_Move();

            }


            if(ground_List.get(i) instanceof Ground_Touch_Starfish){
                //불가사리
                ((Ground_Touch_Starfish)ground_List.get(i)).ground_Object_Move();

                if(((Ground_Touch_Starfish)ground_List.get(i)).set_Split()){
//                    add_Ground_Starfish(ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    set_Starfish_Position(ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
//                    Log.e("@","@@@@@@@@@@@@@@@@");
                }


            }



        }




    }


    /**
     * 물고기 삭제
     */
    public synchronized void delete_Fish_Select(int fish_Number){
        //물고기 피가 0 이면 피 검사후에 피가 0 이면

        if(fish_List.get(fish_Number).get_Fish_Hp() <= 0){

            //오징어를 죽였으면 먹물을 생성한다.
            if(fish_List.get(fish_Number) instanceof Fish_Touch_Squid){
                add_Fish_Touch_Squid_Ink(fish_List.get(fish_Number).get_Fish_Point_X(), fish_List.get(fish_Number) .get_Fish_Point_Y());
            }
            fish_List.remove(fish_Number);
        }


    }

    float split_Fish_X = 0;
    float split_Fish_Y = 0;
    int split_Fish_Class = 0;

    public synchronized void delete_Fish(){

        try{


            for(int i=0; i<fish_List.size(); i++){
//            split_Fish_Class = ((Fish_Touch_Default)fish_List.get(i)).get_Class_Num();

                //보스 새끼 물고기 일때 무시


                //청새치가 캐릭터에 부딛혔을때
                if(fish_List.get(i) instanceof Fish_Touch_Marlin){

                    if(fish_List.get(i).get_Fish_Point_Y() + fish_List.get(i).get_Height_Size() - convertPixelsToDp(15, _context) < main_Character.get_Main_Character_Point_Y() + main_Character.get_Height_Size()
                            && fish_List.get(i).get_Fish_Point_Y() + fish_List.get(i).get_Height_Size() - convertPixelsToDp(15, _context) > main_Character.get_Main_Character_Point_Y()
                            ||
                            fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(15, _context)  < main_Character.get_Main_Character_Point_Y() + main_Character.get_Height_Size()
                                    && fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(15, _context) + fish_List.get(i).get_Height_Size() > main_Character.get_Main_Character_Point_Y() + main_Character.get_Height_Size()


                            ){


                        if(fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(15, _context) > main_Character.get_Main_Character_Point_X()
                                && fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(15, _context) < main_Character.get_Main_Character_Point_X() + main_Character.get_Weight_Size()
                                ||
                                fish_List.get(i).get_Fish_Point_X() + fish_List.get(i).get_Width_Size() - convertPixelsToDp(15, _context) > main_Character.get_Main_Character_Point_X()
                                        &&  fish_List.get(i).get_Fish_Point_X() + fish_List.get(i).get_Width_Size() - convertPixelsToDp(15, _context) <  main_Character.get_Main_Character_Point_X() + main_Character.get_Weight_Size() ){


                            main_Character.set_Hp_Minus();
                            gameActivity.set_Vibrator();
//                            Log.e("@","@4" + fish_List.get(i).getClass());


                            default_Fish_Alive(i);
                            break;
                        }
                    }

                }

                if(fish_List.get(i).get_Fish_Hp() <= 0){

                    //오징어를 죽였으면 먹물을 생성한다.
                    if(fish_List.get(i) instanceof Fish_Touch_Squid){
                        add_Fish_Touch_Squid_Ink(fish_List.get(i).get_Fish_Point_X(), fish_List.get(i) .get_Fish_Point_Y());
                    }

                    split_Fish_X = fish_List.get(i).get_Fish_Point_X();
                    split_Fish_Y = fish_List.get(i).get_Fish_Point_Y();

                    if(fish_List.get(i) instanceof Fish_Touch_Default && fish_List.get(i).get_Production()) {
                        split_Fish_Class = ((Fish_Touch_Default) fish_List.get(i)).get_Class_Num();

                        //중간 보스 및 대장 물고기를 잡으면 그 자리에서 물고기 추가
                        if(split_Fish_Class == 1){  //중간보스이면
//                    add_Fish_Touch_Default(split_Fish_X, split_Fish_Y);
                            fish_Child_Set(split_Fish_X, split_Fish_Y);
                            fish_Child_Set(split_Fish_X, split_Fish_Y);

                        }else if(split_Fish_Class == 2){
//                    add_Middle_Fish_Touch_Default(split_Fish_X, split_Fish_Y);

                            fish_Child_Middle_Set(split_Fish_X, split_Fish_Y);
                            fish_Child_Middle_Set(split_Fish_X, split_Fish_Y);
//                    fish_Child_Middle_Set(split_Fish_X, split_Fish_Y);
//                    fish_Child_Middle_Set(split_Fish_X, split_Fish_Y);
                        }
                    }






//                fish_List.remove(i);
                    default_Fish_Alive(i);

                    break;
                }



                //물고기가 y축 으로 넘어가면 삭제
                if(fish_List.get(i).get_Fish_Point_Y() >= getHeight() - 30
                        || fish_List.get(i).get_Fish_Point_X() < -30                  //X축으로 0 보다 작으면 삭제
                        || fish_List.get(i).get_Fish_Point_X() > getWidth() + 30){       //X축으로 화면 보다 크면 삭제

                    //물고기가 y축으로 넘어가면 hp, 해파리는 x축 기준으로 사라지기때문에
                    if(fish_List.get(i).get_Fish_Point_Y() >= getHeight() - 30) {

//                        Log.e("@","@1" + fish_List.get(i).getClass());

                        main_Character.set_Hp_Minus();
                        gameActivity.set_Vibrator();
                    }

//                fish_List.remove(i);


                    default_Fish_Alive(i);

                    break;
                }

            }

        }catch (IndexOutOfBoundsException e){
            Log.e("fish", "fish");
        }
    }


    /**
     *  바닥 생명체 삭제
     */
    public synchronized void delete_Ground_Select(int ground_Number){
        if(ground_List.get(ground_Number).get_Ground_Hp() <= 0){
            ground_List.remove(ground_Number);
        }
    }

    public synchronized void delete_Ground(){

        try{

            for(int i=0; i<ground_List.size(); i++){

                if(ground_List.get(i).get_Ground_Hp() <= 0){



                    if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 0){
                        game_thread.recycle_Land_Mark_1();    //Log.e("aaaa", "1");
                    }else if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 1){
                        game_thread.recycle_Land_Mark_2();    //Log.e("aaaa", "2");
                    }else if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 2){
                        game_thread.recycle_Land_Mark_3();    //Log.e("aaaa", "3");
                    }else if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 3){
                        game_thread.recycle_Land_Mark_4();    //Log.e("aaaa", "4");
                    }else if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 4){
                        game_thread.recycle_Land_Mark_5();    //Log.e("aaaa", "5");
                    }else if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 5){
                        game_thread.recycle_Land_Mark_6();    //Log.e("aaaa", "5");
                    }else if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 6){
                        game_thread.recycle_Land_Mark_7();    //Log.e("aaaa", "6");
                    }



                    //진화 문제, hp 감소 안되는 문제



                    if(ground_List.get(i) instanceof Land_Mark) {
                        //랜드마크는 삭제후 재생성
                        ground_List.remove(i);
                        continue;
                    }
                    default_Ground_Alive(i);


                    break;
                }



                //물고기가 y축 으로 넘어가면 삭제
                if(ground_List.get(i).get_Ground_Point_Y() >= getHeight() - 30){


                    default_Ground_Alive(i);


                    //그라운드 생명체 y축 넘어가면 hp 감소
//                    Log.e("@","@2" + ground_List.get(i).getClass());
                    main_Character.set_Hp_Minus();
                    gameActivity.set_Vibrator();
                    break;
                }


                //악어 삭제
                if(ground_List.get(i) instanceof  Ground_Touch_Crocodile){

                    if(ground_List.get(i).get_Ground_Point_X() < - ground_List.get(i).get_Width_Size()){
                        default_Ground_Alive(i);
                    }else if(ground_List.get(i).get_Ground_Point_X() > window_Width + ground_List.get(i).get_Width_Size()){
                        default_Ground_Alive(i);
                    }
                }


            }

        }catch (IndexOutOfBoundsException e){
            Log.e("ground", e.toString() + " ground");
        }

    }

    public void skill_Fish_Attack(){



        if((main_Character instanceof Main_Character_Shellfish_Tear2 || skill_Shellfish_Extract_Nomar[1]) && random.nextInt(1000) < random.nextInt((int)st2) + 50){
            //20퍼 확률로 속도 낮춘다.

            (main_Character).stop_Enemy(fish_List.get(smallFishIndex));
            fish_List.get(smallFishIndex).set_Slow_Effect();

        } //집게발 소환
            if((main_Character instanceof Main_Character_Shellfish_Tear4 || skill_Shellfish_Extract_Nomar[3]) && random.nextInt(1000) < random.nextInt((int)st4) + 20) {
                skill_Crab_Claws = new Skill_Crab_Claws(fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(50, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(30, _context));
                skill_Crab_Claws_List.add(skill_Crab_Claws);
                soundPool.play(sound_Effect[9], pop_Drag, pop_Drag, 0, 0, 1.0F);
            } //간장게장 집게발 소환
                if((main_Character instanceof Main_Character_Shellfish_Tear5 || skill_Shellfish_Extract_Nomar[4]) && random.nextInt(1000) < random.nextInt((int)st5) + 20){
                    skill_Soycrab_Claws = new Skill_Soycrab_Claws(fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(40, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(40, _context));
                    skill_Soycrab_Claws_List.add(skill_Soycrab_Claws);
                    soundPool.play(sound_Effect[9], pop_Drag, pop_Drag, 0, 0, 1.0F);
                } //레이저 소환
                    if((main_Character instanceof Main_Character_Moulluse_Tear6 || skill_Mollus_Extract_Nomar[5] )&& random.nextInt(1000) < random.nextInt((int)mt6) + 20){
                        skill_Laser = new Skill_Laser(- convertPixelsToDp(1000, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y(), window_Width);
                        skill_Laser_List.add(skill_Laser);
                        soundPool.play(sound_Effect[10], pop_Drag, pop_Drag, 0, 0, 1F);
                    }  //가오리 독 걸기
                        if((main_Character instanceof Main_Character_Fish_Tear6 || skill_Fish_Extract_Nomar[5]) && random.nextInt(1000) < random.nextInt((int)ft6) + 50){

                            skill_Plus_D = 0;
                            if(ft6 >= 1 ){
                                skill_Plus_D = random.nextInt((int) ft6);
                            }

                            fish_List.get(smallFishIndex).set_Status_Poison(5 + skill_Plus_D);
                            soundPool.play(sound_Effect[24], pop_Touch, pop_Touch, 0, 0, 1.0F);
                        }  //티어2 가시고기 가시 소환
                            if((main_Character instanceof Main_Character_Fish_Tear2 || skill_Fish_Extract_Nomar[1]) && random.nextInt(1000) < random.nextInt((int)ft2) + 50){
                                soundPool.play(sound_Effect[18], pop_Drag, pop_Drag, 0, 0, 1.0F);
                                skill_Thorn = new Skill_Thorn(fish_List.get(smallFishIndex).get_Fish_Point_X(), fish_List.get(smallFishIndex).get_Fish_Point_Y() + convertPixelsToDp(10, _context));
                                skill_Thorn_List.add(skill_Thorn);
//                                Log.e("@",skill_Thorn_List.size() + "");


                                skill_Plus_D = 0;
                                if(ft2 >= 2 ){
                                    skill_Plus_D = random.nextInt((int) ft2 / 2);
                                }

                                fish_List.get(smallFishIndex).set_Hp_Minus(1 + skill_Plus_D);


                            } //독구름 소환
                                if((main_Character instanceof Main_Character_Moulluse_Tear10 || skill_Mollus_Extract_Nomar[9])  && random.nextInt(1000) < random.nextInt((int)mt10) + 20){

                                    soundPool.play(sound_Effect[25], pop_Drag, pop_Drag, 0, 0, 1F);
                                    skill_poison_cloud = new Skill_Poison_Cloud(fish_List.get(smallFishIndex).get_Fish_Point_X() - (skill_Poison1_img[0].getWidth()/2), fish_List.get(smallFishIndex).get_Fish_Point_Y()  );
                                    if(skill_poison_cloud_List.size() < 3) {
                                        skill_poison_cloud_List.add(skill_poison_cloud);
                                    }else{
                                        skill_poison_cloud_List.remove(0);
                                        skill_poison_cloud_List.add(skill_poison_cloud);
                                    }
                                }   //지진 소환
                                    if((main_Character instanceof Main_Character_Fish_Tear4|| skill_Fish_Extract_Nomar[3])  && random.nextInt(1000) < random.nextInt((int)ft4) + 20){
                                        skill_Earthquake = new Skill_Earthquake(fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(45, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(20, _context)  );
                                        skill_Earthquake_List.add(skill_Earthquake);
                                        soundPool.play(sound_Effect[31], pop_Drag, pop_Drag, 0, 0, 1.0F);   //지진
                                    }
                                    if((main_Character instanceof Main_Character_Fish_Tear3 || skill_Fish_Extract_Nomar[2])  && random.nextInt(1000) < random.nextInt((int)ft3) + 20){
                                        //이빨 지뢰 소환
                                        skill_Teeth_Mine = new Skill_Teeth_Mine(0,0);
                                        skill_Teeth_Mine.set_Position(window_Width, window_Height);

                                        //이빨 지뢰 5개 이하
                                        if(skill_Teeth_Mine_List.size() < 3){
                                            skill_Teeth_Mine_List.add(skill_Teeth_Mine);
                                        }else {
                                            skill_Teeth_Mine_List.remove(0);
                                            skill_Teeth_Mine_List.add(skill_Teeth_Mine);
                                        }

                                    }
                                    if((main_Character instanceof Main_Character_Fish_Tear10 || skill_Fish_Extract_Nomar[9])  && random.nextInt(1000) < random.nextInt((int)ft10) + 20){
                                        //티어 10 전역에 가시 생성
                                        soundPool.play(sound_Effect[18], pop_Drag, pop_Drag, 0, 0, 1.0F);
                                        for(int i=0; i<fish_List.size(); i++){
                                            if(fish_List.get(i).get_Visible_Fish_Flag()) {
                                                skill_Thorn = new Skill_Thorn(fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(15, _context), fish_List.get(i).get_Fish_Point_Y());
                                                skill_Thorn_List.add(skill_Thorn);

                                                skill_Plus_D = 0;
                                                if(ft10 >= 2 ){
                                                    skill_Plus_D = random.nextInt((int) ft10);
                                                }

                                                fish_List.get(i).set_Hp_Minus(20 + skill_Plus_D);
                                            }
                                        }
                                        for(int i=0; i<ground_List.size(); i++){
                                            if( ground_List.get(i) instanceof Land_Mark){
                                                continue;
                                            }
                                            if(ground_List.get(i).get_Visible_Ground_Flag()) {
                                                skill_Thorn = new Skill_Thorn(ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(15, _context), ground_List.get(i).get_Ground_Point_Y());
                                                skill_Thorn_List.add(skill_Thorn);
                                                if((ground_List.get(i) instanceof Ground_Touch_Hermit)) {
                                                    if(!((Ground_Touch_Hermit)ground_List.get(i)).get_Immortal_Mode()) {
                                                        ground_List.get(i).set_Ground_Hp_Minus(1);
                                                    }
                                                }else {

                                                    skill_Plus_D = 0;
                                                    if(ft10 >= 1 ){
                                                        skill_Plus_D = random.nextInt((int) ft10);
                                                    }

                                                    ground_List.get(i).set_Ground_Hp_Minus(20 + skill_Plus_D);
                                                }
                                            }
                                        }


                                    }
                                    if((main_Character instanceof Main_Character_Shellfish_Tear8 || skill_Shellfish_Extract_Nomar[7])  && random.nextInt(1000) < random.nextInt((int)st8) + 20){
                                        //갑각류 티어 8 쌍 집게 소환
                                        skill_Crab_Claws = new Skill_Crab_Claws(fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(70, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(30, _context));
                                        skill_Crab_Claws_List.add(skill_Crab_Claws);

                                        skill_Crab_Claws = new Skill_Crab_Claws(fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(20, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(0, _context));

                                        skill_Crab_Claws_List.add(skill_Crab_Claws);
                                        soundPool.play(sound_Effect[9], pop_Drag, pop_Drag, 0, 0, 1.0F);

                                    }
                                    if((main_Character instanceof Main_Character_Moulluse_Tear3 || skill_Mollus_Extract_Nomar[2])  && random.nextInt(1000) < random.nextInt((int)mt3) + 20){
                                        //슬로우 구름 생성
                                        skill_Slow_Cloud = new Skill_Slow_Cloud(fish_List.get(smallFishIndex).get_Fish_Point_X() - (skill_Slow_Cloud_img.getWidth()/2) , fish_List.get(smallFishIndex).get_Fish_Point_Y() - (skill_Slow_Cloud_img.getHeight()/4));

                                        if(skill_Slow_Cloud_List.size() < 3) {
                                            skill_Slow_Cloud_List.add(skill_Slow_Cloud);
                                        }else {
                                            skill_Slow_Cloud_List.remove(0);
                                            skill_Slow_Cloud_List.add(skill_Slow_Cloud);
                                        }

                                    }
                                    if((main_Character instanceof Main_Character_Moulluse_Tear7 || skill_Mollus_Extract_Nomar[6]) && random.nextInt(1000) < random.nextInt((int)mt7) + 50){
                                        //독 주입 [해파리]

                                        skill_Plus_D = 0;
                                        if(mt7 >= 1 ){
                                            skill_Plus_D = random.nextInt((int) mt7);
                                        }

                                        fish_List.get(smallFishIndex).set_Status_Poison(10 + skill_Plus_D);
                                        soundPool.play(sound_Effect[24], pop_Touch, pop_Touch, 0, 0, 1.0F);
//                                    Log.e("@","독공격@");
                                    }
                                    if((main_Character instanceof Main_Character_Moulluse_Tear8 || skill_Mollus_Extract_Nomar[7]) && random.nextInt(1000) < random.nextInt((int)mt8) + 20){
                                        //독 폭탄
                                        skill_Boom_Poison = new Skill_Boom_Poison(fish_List.get(smallFishIndex).get_Fish_Point_X() - (skill_Boom_Poison_img[0].getWidth()/2) , fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(150, _context));
                                        skill_Boom_Poison_List.add(skill_Boom_Poison);
                                        soundPool.play(sound_Effect[22], pop_Drag, pop_Drag, 0, 0, 1.0F);
                                    }
                                    if((main_Character instanceof Main_Character_Fish_Tear9 || skill_Fish_Extract_Nomar[8]) && random.nextInt(1000) < random.nextInt((int)ft9) + 20){
                                        soundPool.play(sound_Effect[21], pop_Drag, pop_Drag, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                                        //바다뱀 소환
                                        skill_Sea_Snake = new Skill_Sea_Snake(window_Width-200, fish_List.get(smallFishIndex).get_Fish_Point_Y());
                                        skill_Sea_Snake_List.add(skill_Sea_Snake);

                                    }
                                    if((main_Character instanceof Main_Character_Shellfish_Tear9 || skill_Shellfish_Extract_Nomar[8]) && random.nextInt(1000) < random.nextInt((int)st9) + 20){
                                        //파도 소환
                                        skill_Wave = new Skill_Wave(fish_List.get(smallFishIndex).get_Fish_Point_X() - (skill_Wave_img[0].getWidth()/2), window_Height);
                                        skill_Wave_List.add(skill_Wave);
                                        soundPool.play(sound_Effect[28], pop_Drag, pop_Drag, 0, 0, 1.0F);
                                    }
                                    if((main_Character instanceof Main_Character_Moulluse_Tear9 || skill_Mollus_Extract_Nomar[8]) && random.nextInt(1000) < random.nextInt((int)mt9) + 20){
                                        //벽 소환
                                        skill_Wall = new Skill_Wall(fish_List.get(smallFishIndex).get_Fish_Point_X() - (skill_wall_img[0].getWidth()/2), fish_List.get(smallFishIndex).get_Fish_Point_Y());
                                        soundPool.play(sound_Effect[23], pop_Drag, pop_Drag, 0, 0, 1.0F);
                                        if(skill_Wall_List.size() < 2) {
                                            skill_Wall_List.add(skill_Wall);
                                        }else {
                                            skill_Wall_List.remove(0);
                                            skill_Wall_List.add(skill_Wall);
                                        }
                                    }
                                    if((main_Character instanceof Main_Character_Fish_Tear5 || skill_Fish_Extract_Nomar[4]) && random.nextInt(1000) < random.nextInt((int)ft5) + 20){
                                        //이빨2 소환
                                        skill_Teeth_Mine2 = new Skill_Teeth_Mine2(0,0);
                                        skill_Teeth_Mine2.set_Position(window_Width, window_Height);

                                        if(skill_Teeth_Mine2_List.size() < 3) {
                                            skill_Teeth_Mine2_List.add(skill_Teeth_Mine2);
                                        }else {
                                            skill_Teeth_Mine2_List.remove(0);
                                            skill_Teeth_Mine2_List.add(skill_Teeth_Mine2);
                                        }

                                    }
                                    if((main_Character instanceof Main_Character_Shellfish_Tear6 || skill_Shellfish_Extract_Nomar[5]) && random.nextInt(1000) < random.nextInt((int)st6) + 50){
                                        //가시2 소환 7개 이하일때
                                        if(skill_Thorn2_List.size() <= 7) {
                                            skill_Thorn2 = new Skill_Thorn2(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                            skill_Thorn2_List.add(skill_Thorn2);
                                        }
                                    }
                                    if((main_Character instanceof Main_Character_Fish_Tear7 || skill_Fish_Extract_Nomar[6]) && random.nextInt(1000) < random.nextInt((int)ft7) + 20){
                                        //번개 소환
                                        skill_Lightning = new Skill_Lightning(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                        skill_Lightning_List.add(skill_Lightning);

                                    }
                                    if((main_Character instanceof Main_Character_Fish_Tear8 || skill_Fish_Extract_Nomar[7]) && random.nextInt(1000) < random.nextInt((int)ft8) + 20){
                                        //번개2 소환
                                        skill_Lightning2 = new Skill_Lightning2(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                        skill_Lightning2_List.add(skill_Lightning2);

                                    }
                                    if((main_Character instanceof Main_Character_Shellfish_Tear10 || skill_Shellfish_Extract_Nomar[9]) && random.nextInt(1000) < random.nextInt((int)st10) + 20){
                                        //거북이 스톰프
                                        skill_Stomp = new Skill_Stomp(fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(70, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(70, _context));
                                        skill_Stomp_List.add(skill_Stomp);

                                    }
                                    if((main_Character instanceof Main_Character_Shellfish_Tear7 || skill_Shellfish_Extract_Nomar[6]) && random.nextInt(1000) < random.nextInt((int)st7) + 20){
                                        //튀김 지뢰 소환
                                        skill_Fry = new Skill_Fry(0,0);
                                        skill_Fry.set_Position(window_Width, window_Height);

                                        if(skill_Fry_List.size() < 3) {
                                            skill_Fry_List.add(skill_Fry);
                                        }else {
                                            skill_Fry_List.remove(0);
                                            skill_Fry_List.add(skill_Fry);
                                        }
                                    }
                                    if((main_Character instanceof Main_Character_Moulluse_Tear4 || skill_Mollus_Extract_Nomar[3]) && random.nextInt(1000) < random.nextInt((int)mt4) + 20){
                                        //버터 소환
                                        skill_Butter = new Skill_Butter(0,0);
                                        skill_Butter.set_Position(window_Width, window_Height);
                                        if(skill_Butter_List.size() < 3) {
                                            skill_Butter_List.add(skill_Butter);
                                        }else {
                                            skill_Butter_List.remove(0);
                                            skill_Butter_List.add(skill_Butter);
                                        }
                                    }
                                    if((main_Character instanceof Main_Character_Moulluse_Tear5 || skill_Mollus_Extract_Nomar[4]) && random.nextInt(1000) < random.nextInt((int)mt5) + 60){
                                        //포크 소환
                                        skill_Fork = new Skill_Fork(fish_List.get(smallFishIndex).get_Fish_Point_X() - (skill_Fork_img[0].getWidth()/2) + (convertPixelsToDp(10, _context)), fish_List.get(smallFishIndex).get_Fish_Point_Y() - (convertPixelsToDp(150, _context) + skill_Fork_img[0].getHeight()));
                                        skill_Fork.set_Aim_Fish(smallFishIndex);
                                        skill_Fork.set_Aim_Species(1);
                                        skill_Fork_List.add(skill_Fork);
                                    }
    }

    public void skill_Ground_Attack(){

//달팽이 정지
        if(ground_Remove_Temp != -1){
            //메인 캐릭터가 달팽이 일때 공격하면 정지 시킨다. //확률로 정지 시켜야함
            if((main_Character instanceof Main_Character_Shellfish_Tear2 || skill_Shellfish_Extract_Nomar[1]) && random.nextInt(1000) < random.nextInt((int)st2) + 50){
                //20퍼 확률로 속도 낮춘다.

                (main_Character).stop_Enemy(ground_List.get(ground_Remove_Temp));
                ground_List.get(ground_Remove_Temp).set_Slow_Effect();

            } //집게발 소환
                if((main_Character instanceof Main_Character_Shellfish_Tear4 || skill_Shellfish_Extract_Nomar[3]) && random.nextInt(1000) < random.nextInt((int)st4) + 20) {
                    skill_Crab_Claws = new Skill_Crab_Claws(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(50, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(30, _context));
                    skill_Crab_Claws_List.add(skill_Crab_Claws);
                    soundPool.play(sound_Effect[9], pop_Drag, pop_Drag, 0, 0, 1.0F);


                } //간장게장 집게발 소환
                    if((main_Character instanceof Main_Character_Shellfish_Tear5 || skill_Shellfish_Extract_Nomar[4])  && random.nextInt(1000) < random.nextInt((int)st5) + 20){
                        skill_Soycrab_Claws = new Skill_Soycrab_Claws(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(40, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(40, _context));
                        skill_Soycrab_Claws_List.add(skill_Soycrab_Claws);
                        soundPool.play(sound_Effect[9], pop_Drag, pop_Drag, 0, 0, 1.0F);
                    } //레이저 소환
                        if((main_Character instanceof Main_Character_Moulluse_Tear6 || skill_Mollus_Extract_Nomar[5]) && random.nextInt(1000) < random.nextInt((int)mt6) + 20){
                            skill_Laser = new Skill_Laser(- convertPixelsToDp(1000, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y(), window_Width);
                            skill_Laser_List.add(skill_Laser);
                            soundPool.play(sound_Effect[10], pop_Drag, pop_Drag, 0, 0, 1F);
                        }  //가오리 독 걸기
                            if((main_Character instanceof Main_Character_Fish_Tear6 || skill_Fish_Extract_Nomar[5]) && random.nextInt(1000) < random.nextInt((int)ft6) + 50){

                                skill_Plus_D = 0;
                                if(ft6 >= 1 ){
                                    skill_Plus_D = random.nextInt((int) ft6);
                                }

                                ground_List.get(ground_Remove_Temp).set_Status_Poison(5 + skill_Plus_D);
                                soundPool.play(sound_Effect[24], pop_Touch, pop_Touch, 0, 0, 1.0F);
                            }   //티어2 가시고기 가시 소환
                                if((main_Character instanceof Main_Character_Fish_Tear2 || skill_Fish_Extract_Nomar[1])  && random.nextInt(1000) < random.nextInt((int)ft2) + 50){

                                    skill_Thorn = new Skill_Thorn(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() -convertPixelsToDp(15, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() );
                                    skill_Thorn_List.add(skill_Thorn);
                                    soundPool.play(sound_Effect[18], pop_Drag, pop_Drag, 0, 0, 1.0F);

                                    if((ground_List.get(ground_Remove_Temp) instanceof Ground_Touch_Hermit)) {
                                        if(!((Ground_Touch_Hermit)ground_List.get(ground_Remove_Temp)).get_Immortal_Mode()) {
                                            ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(1);
                                        }
                                    }else {
                                        skill_Plus_D = 0;
                                        if(ft2 >= 2 ){
                                            skill_Plus_D = random.nextInt((int) ft2 / 2);
                                        }
                                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(1 + skill_Plus_D);
                                    }

                                } //독구름 소환
                                    if((main_Character instanceof Main_Character_Moulluse_Tear10 || skill_Mollus_Extract_Nomar[9]) && random.nextInt(1000) < random.nextInt((int)mt10) + 20){
                                        skill_poison_cloud = new Skill_Poison_Cloud(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - (skill_Poison1_img[0].getWidth()/2) , ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() );
                                        soundPool.play(sound_Effect[25], pop_Drag, pop_Drag, 0, 0, 1F);
                                        if(skill_poison_cloud_List.size() < 3) {
                                            skill_poison_cloud_List.add(skill_poison_cloud);
                                        }else{
                                            skill_poison_cloud_List.remove(0);
                                            skill_poison_cloud_List.add(skill_poison_cloud);
                                        }
                                    }   //지진 소환
                                        if((main_Character instanceof Main_Character_Fish_Tear4 || skill_Fish_Extract_Nomar[3]) && random.nextInt(1000) < random.nextInt((int)ft4) + 20){
                                            skill_Earthquake = new Skill_Earthquake(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(45, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y()  - convertPixelsToDp(20, _context) );
                                            skill_Earthquake_List.add(skill_Earthquake);
                                            soundPool.play(sound_Effect[31], pop_Drag, pop_Drag, 0, 0, 1.0F);   //지진
                                        }
                                        if((main_Character instanceof Main_Character_Fish_Tear3 || skill_Fish_Extract_Nomar[2]) && random.nextInt(1000) < random.nextInt((int)ft3) + 20){
                                            //이빨 지뢰 소환
                                            skill_Teeth_Mine = new Skill_Teeth_Mine(0,0);
                                            skill_Teeth_Mine.set_Position(window_Width, window_Height);
                                            //이빨 지뢰 7개 이하
                                            if(skill_Teeth_Mine_List.size() < 3){
                                                skill_Teeth_Mine_List.add(skill_Teeth_Mine);
                                            }else {
                                                skill_Teeth_Mine_List.remove(0);
                                                skill_Teeth_Mine_List.add(skill_Teeth_Mine);
                                            }
                                        }
                                        if((main_Character instanceof Main_Character_Fish_Tear10 || skill_Fish_Extract_Nomar[9]) && random.nextInt(1000) < random.nextInt((int)ft10) + 20){
                                            //티어 10 전역에 가시 생성
                                            soundPool.play(sound_Effect[18], pop_Drag, pop_Drag, 0, 0, 1.0F);
                                            for(int i=0; i<fish_List.size(); i++){
                                                if(fish_List.get(i).get_Visible_Fish_Flag()) {
                                                    skill_Thorn = new Skill_Thorn(fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(15, _context), fish_List.get(i).get_Fish_Point_Y());
                                                    skill_Thorn_List.add(skill_Thorn);

                                                    skill_Plus_D = 0;
                                                    if(ft10 >= 2 ){
                                                        skill_Plus_D = random.nextInt((int) ft10 / 2);
                                                    }

                                                    fish_List.get(i).set_Hp_Minus(20 + skill_Plus_D);
                                                }
                                            }
                                            for(int i=0; i<ground_List.size(); i++){
                                                if( ground_List.get(i) instanceof Land_Mark){
                                                    continue;
                                                }
                                                if(ground_List.get(i).get_Visible_Ground_Flag()) {
                                                    skill_Thorn = new Skill_Thorn(ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(15, _context), ground_List.get(i).get_Ground_Point_Y());
                                                    skill_Thorn_List.add(skill_Thorn);
                                                    if((ground_List.get(i) instanceof Ground_Touch_Hermit)) {
                                                        if(!((Ground_Touch_Hermit)ground_List.get(i)).get_Immortal_Mode()) {
                                                            ground_List.get(i).set_Ground_Hp_Minus(1);
                                                        }
                                                    }else {

                                                        skill_Plus_D = 0;
                                                        if(ft10 >= 2 ){
                                                            skill_Plus_D = random.nextInt((int) ft10 / 2);
                                                        }

                                                        ground_List.get(i).set_Ground_Hp_Minus(20 + skill_Plus_D);
                                                    }
                                                }
                                            }

                                        }
                                        if((main_Character instanceof Main_Character_Shellfish_Tear8 || skill_Shellfish_Extract_Nomar[7]) && random.nextInt(1000) < random.nextInt((int)st8) + 20){
                                            //갑각류 티어 8 쌍 집게 소환
                                            skill_Crab_Claws = new Skill_Crab_Claws(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(70, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(30, _context));
                                            skill_Crab_Claws_List.add(skill_Crab_Claws);

                                            skill_Crab_Claws = new Skill_Crab_Claws(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(20, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(0, _context));

                                            skill_Crab_Claws_List.add(skill_Crab_Claws);

                                            soundPool.play(sound_Effect[9], pop_Drag, pop_Drag, 0, 0, 1.0F);

                                        }
                                        if((main_Character instanceof Main_Character_Moulluse_Tear3 || skill_Mollus_Extract_Nomar[2]) && random.nextInt(1000) < random.nextInt((int)mt3) + 20){
                                            //슬로우 구름 생성
                                            skill_Slow_Cloud = new Skill_Slow_Cloud(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - (skill_Slow_Cloud_img.getWidth()/2) , ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - (skill_Slow_Cloud_img.getHeight()/4));
                                            if(skill_Slow_Cloud_List.size() < 3) {
                                                skill_Slow_Cloud_List.add(skill_Slow_Cloud);
                                            }else {
                                                skill_Slow_Cloud_List.remove(0);
                                                skill_Slow_Cloud_List.add(skill_Slow_Cloud);
                                            }

                                        }
                                        if((main_Character instanceof Main_Character_Moulluse_Tear7 || skill_Mollus_Extract_Nomar[6]) && random.nextInt(1000) < random.nextInt((int)mt7) + 50){
                                            //독 주입 [해파리]
                                            skill_Plus_D = 0;
                                            if(mt7 >= 2 ){
                                                skill_Plus_D = random.nextInt((int) mt7 / 2);
                                            }
                                            ground_List.get(ground_Remove_Temp).set_Status_Poison(10 + skill_Plus_D);
                                            soundPool.play(sound_Effect[24], pop_Touch, pop_Touch, 0, 0, 1.0F);
//                                        Log.e("@","독공격");
                                        }
                                        if((main_Character instanceof Main_Character_Moulluse_Tear8 || skill_Mollus_Extract_Nomar[7]) && random.nextInt(1000) < random.nextInt((int)mt8) + 20){
                                            //독 폭탄
                                            skill_Boom_Poison = new Skill_Boom_Poison(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - (skill_Boom_Poison_img[0].getWidth()/2) , ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(150, _context));
                                            skill_Boom_Poison_List.add(skill_Boom_Poison);
                                            soundPool.play(sound_Effect[22], pop_Drag, pop_Drag, 0, 0, 1.0F);
                                        }
                                        if((main_Character instanceof Main_Character_Fish_Tear9 || skill_Fish_Extract_Nomar[8]) && random.nextInt(1000) < random.nextInt((int)ft9) + 20){
                                            soundPool.play(sound_Effect[21], pop_Drag, pop_Drag, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                                            //바다뱀 소환
                                            skill_Sea_Snake = new Skill_Sea_Snake(window_Width-200, ground_List.get(ground_Remove_Temp).get_Ground_Point_Y());
                                            skill_Sea_Snake_List.add(skill_Sea_Snake);

                                        }
                                        if((main_Character instanceof Main_Character_Shellfish_Tear9 || skill_Shellfish_Extract_Nomar[8]) && random.nextInt(1000) < random.nextInt((int)st9) + 20){
                                            //파도 소환
                                            skill_Wave = new Skill_Wave(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - (skill_Wave_img[0].getWidth()/2), window_Height);
                                            skill_Wave_List.add(skill_Wave);
                                            soundPool.play(sound_Effect[28], pop_Drag, pop_Drag, 0, 0, 1.0F);
                                        }
                                        if((main_Character instanceof Main_Character_Moulluse_Tear9 || skill_Mollus_Extract_Nomar[8]) && random.nextInt(1000) < random.nextInt((int)mt9) + 20){
                                            //벽 소환
                                            skill_Wall = new Skill_Wall(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - (skill_wall_img[0].getWidth()/2), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y());
                                            soundPool.play(sound_Effect[23], pop_Drag, pop_Drag, 0, 0, 1.0F);
                                            if(skill_Wall_List.size() < 2) {
                                                skill_Wall_List.add(skill_Wall);
                                            }else {
                                                skill_Wall_List.remove(0);
                                                skill_Wall_List.add(skill_Wall);
                                            }

                                        }
                                        if((main_Character instanceof Main_Character_Fish_Tear5 || skill_Fish_Extract_Nomar[4]) && random.nextInt(1000) < random.nextInt((int)ft5) + 20){
                                            //이빨2 소환
                                            skill_Teeth_Mine2 = new Skill_Teeth_Mine2(0,0);
                                            skill_Teeth_Mine2.set_Position(window_Width, window_Height);


                                            if(skill_Teeth_Mine2_List.size() < 3) {
                                                skill_Teeth_Mine2_List.add(skill_Teeth_Mine2);
                                            }else {
                                                skill_Teeth_Mine2_List.remove(0);
                                                skill_Teeth_Mine2_List.add(skill_Teeth_Mine2);
                                            }

                                        }
                                        if((main_Character instanceof Main_Character_Shellfish_Tear6 || skill_Shellfish_Extract_Nomar[5]) && random.nextInt(1000) < random.nextInt((int)st6) + 50){
                                            //가시2 소환
                                            if(skill_Thorn2_List.size() <= 7) {
                                                skill_Thorn2 = new Skill_Thorn2(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                                skill_Thorn2_List.add(skill_Thorn2);
                                            }
                                        }
                                        if((main_Character instanceof Main_Character_Fish_Tear7 || skill_Fish_Extract_Nomar[6]) && random.nextInt(1000) < random.nextInt((int)ft7) + 20){
                                            //번개 소환
                                            skill_Lightning = new Skill_Lightning(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                            skill_Lightning_List.add(skill_Lightning);

                                        }
                                        if((main_Character instanceof Main_Character_Fish_Tear8 || skill_Fish_Extract_Nomar[7]) && random.nextInt(1000) < random.nextInt((int)ft8) + 20){
                                            //번개2 소환
                                            skill_Lightning2 = new Skill_Lightning2(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                            skill_Lightning2_List.add(skill_Lightning2);
                                        }
                                        if((main_Character instanceof Main_Character_Shellfish_Tear10 || skill_Shellfish_Extract_Nomar[9]) && random.nextInt(1000) < random.nextInt((int)st10) + 20){
                                            //거북이 스톰프
                                            skill_Stomp = new Skill_Stomp(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(70, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(70, _context));
                                            skill_Stomp_List.add(skill_Stomp);

                                        }
                                        if((main_Character instanceof Main_Character_Shellfish_Tear7 || skill_Shellfish_Extract_Nomar[6]) && random.nextInt(1000) < random.nextInt((int)st7) + 20){
                                            //튀김 지뢰 소환
                                            skill_Fry = new Skill_Fry(0,0);
                                            skill_Fry.set_Position(window_Width, window_Height);
                                            if(skill_Fry_List.size() < 3) {
                                                skill_Fry_List.add(skill_Fry);
                                            }else {
                                                skill_Fry_List.remove(0);
                                                skill_Fry_List.add(skill_Fry);
                                            }
                                        }
                                        if((main_Character instanceof Main_Character_Moulluse_Tear4 || skill_Mollus_Extract_Nomar[3]) && random.nextInt(1000) < random.nextInt((int)mt4) + 20){
                                            //버터 소환
                                            skill_Butter = new Skill_Butter(0,0);
                                            skill_Butter.set_Position(window_Width, window_Height);
                                            if(skill_Butter_List.size() < 3) {
                                                skill_Butter_List.add(skill_Butter);
                                            }else {
                                                skill_Butter_List.remove(0);
                                                skill_Butter_List.add(skill_Butter);
                                            }
                                        }
                                        if((main_Character instanceof Main_Character_Moulluse_Tear5 || skill_Mollus_Extract_Nomar[4]) && random.nextInt(1000) < random.nextInt((int)mt5) + 60){
                                            //포크 소환
                                            skill_Fork = new Skill_Fork(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - (skill_Fork_img[0].getWidth()/2) + (convertPixelsToDp(10, _context)), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - (convertPixelsToDp(150, _context) + skill_Fork_img[0].getHeight()));
                                            skill_Fork.set_Aim_Ground(ground_Remove_Temp);
                                            skill_Fork.set_Aim_Species(2);
                                            skill_Fork_List.add(skill_Fork);
                                        }

        }


    }



    /**
     * 그라운드 생명체 대미지 넣기 (달팽이)
     */
    private boolean ground_Hit_Check = false;
    //ground_Hit_Drag = true 일때 드래그 몬스터
    public boolean ground_Hit_Chose(float x, float y, boolean ground_Hit_Drag, int drag_Action_Move){     //그라운드 객체의 종류 알아오기

//        성능 개선을 위한 포문, 달팽이, 꽃게, 성게 등 각각의 포문을 돌려야 오류가 나지 않기 때문에.
        for(int i=0; i<ground_List.size(); i++){    // +- 45 는 판정을 좋게 하기 위해 추가
            //달팽이를 가장 먼저 찾는다.

            if(!ground_List.get(i).get_Visible_Ground_Flag()){
                continue;
            }

            if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                    && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + ground_List.get(i).get_Width_Size()
                    && y >= ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size()
                    && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()){

                ground_Hit_Check = true;

                break;
            }else {
                ground_Hit_Check = false;
            }

        }

        //       성능 개선을 위한 포문, 달팽이, 꽃게, 성게 등 각각의 포문을 돌려야 오류가 나지 않기 때문에.
        if(ground_Hit_Check){

            ground_Remove_Temp = -1;                    //달팽이 없을때를 기준 터치하는 곳의 달팽이 인덱스를 집어 넣는다.
            for(int i=ground_List.size()-1; i>=0; i--){    // +- 45 는 판정을 좋게 하기 위해 추가
                //달팽이를 가장 먼저 찾는다.
                if(!ground_List.get(i).get_Visible_Ground_Flag()){
                    continue;
                }

                if(ground_List.get(i) instanceof Ground_Touch_Snail && ground_Hit_Drag == false){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + ground_List.get(i).get_Width_Size()
                            && y >= ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()){
                        ground_Remove_Temp = i;


                        if(!(ground_List.get(i) instanceof Ground_Drag_Clam)) {
                            if((ground_List.get(i) instanceof Ground_Drag_Crab) && random.nextInt(100) > 90) {
                                skill_Ground_Attack();
                            }else if((ground_List.get(i) instanceof Ground_Drag_Lobsters) && random.nextInt(100) > 90) {
                                skill_Ground_Attack();
                            }else if((ground_List.get(i) instanceof Ground_Drag_Wave) && random.nextInt(100) > 90) {
                                skill_Ground_Attack();
                            }else {
                                skill_Ground_Attack();
                            }
                        }
                        rand_Effect = random.nextInt(4);
                        if (rand_Effect == 0) {
                            effect_Temp = effect_Pop2_img[4];
                        } else if (rand_Effect == 1) {
                            effect_Temp = effect_Pop3_img[4];
                        } else if (rand_Effect == 2) {
                            effect_Temp = effect_Pop4_img[4];
                        } else {
                            effect_Temp = effect_Pop5_img[4];
                        }


                        snail_Ground_Hit_Flag = true;   //달팽이 터치 이벤트 doDraw에서 발생


                        Score+=random.nextInt(5);
                        money+=character_Randmark_Damege_Temp;
                        //클릭된 달팽이의체력을 깍는다.
//                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();
                        main_Character.set_Character_Experience(1);



                        //            delete_Ground_Select(ground_Remove_Temp);   //피가 감소된 객체 0일때 삭제
                        soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드

                        return true;

                    }
                }


                //선택된 불가사리가 존재한다면.
                if(ground_List.get(i) instanceof Ground_Touch_Starfish && ground_Hit_Drag == false){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + ground_List.get(i).get_Width_Size()
                            && y >= ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()){
                        ground_Remove_Temp = i;


                        skill_Ground_Attack();

                        rand_Effect = random.nextInt(4);
                        if (rand_Effect == 0) {
                            effect_Temp = effect_Pop2_img[4];
                        } else if (rand_Effect == 1) {
                            effect_Temp = effect_Pop3_img[4];
                        } else if (rand_Effect == 2) {
                            effect_Temp = effect_Pop4_img[4];
                        } else {
                            effect_Temp = effect_Pop5_img[4];
                        }


                        starfish_Ground_Hit_Flag = true;   //불가사리 터치 이벤트 doDraw에서 발생
//                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();

                        Score+=random.nextInt(5);
                        money+=character_Randmark_Damege_Temp;
                        main_Character.set_Character_Experience(1);

                        //            delete_Ground_Select(ground_Remove_Temp);   //피가 감소된 객체 0일때 삭제
                        soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                        return true;


                    }
                }




                //선택된 소라게가 존재한다면.
                if(ground_List.get(i) instanceof Ground_Touch_Hermit && ground_Hit_Drag == false){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width()  + ground_List.get(i).get_Width_Size()
                            && y >= ground_List.get(i).get_Ground_Point_Y()  - ground_List.get(i).get_Height_Size()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()){
                        ground_Remove_Temp = i;


                        skill_Ground_Attack();

                        rand_Effect = random.nextInt(4);
                        if (rand_Effect == 0) {
                            effect_Temp = effect_Pop2_img[4];
                        } else if (rand_Effect == 1) {
                            effect_Temp = effect_Pop3_img[4];
                        } else if (rand_Effect == 2) {
                            effect_Temp = effect_Pop4_img[4];
                        } else {
                            effect_Temp = effect_Pop5_img[4];
                        }


                        hermit_Ground_Hit_Flag = true;   //소라 터치 이벤트 doDraw에서 발생


                        Score+=random.nextInt(5);
                        money+=character_Randmark_Damege_Temp;
//                        //클릭된 소라게의체력을 깍는다.
                        if(!((Ground_Touch_Hermit)ground_List.get(ground_Remove_Temp)).get_Immortal_Mode()) {
                            ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();
                        }
                        ((Ground_Touch_Hermit)ground_List.get(ground_Remove_Temp)).set_Immortal_Mode();
                        main_Character.set_Character_Experience(1);



                        //            delete_Ground_Select(ground_Remove_Temp);   //피가 감소된 객체 0일때 삭제
                        soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                        return true;


                    }
                }



                //선택된 곰벌레 존재한다면.
                if(ground_List.get(i) instanceof Ground_Touch_Bearbug && ground_Hit_Drag == false){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width()  + ground_List.get(i).get_Width_Size()
                            && y >= ground_List.get(i).get_Ground_Point_Y()  - ground_List.get(i).get_Height_Size()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()){
                        ground_Remove_Temp = i;


                        skill_Ground_Attack();

                        rand_Effect = random.nextInt(4);
                        if (rand_Effect == 0) {
                            effect_Temp = effect_Pop2_img[4];
                        } else if (rand_Effect == 1) {
                            effect_Temp = effect_Pop3_img[4];
                        } else if (rand_Effect == 2) {
                            effect_Temp = effect_Pop4_img[4];
                        } else {
                            effect_Temp = effect_Pop5_img[4];
                        }


                        bearbug_Ground_Hit_Flag = true;
                        ((Ground_Touch_Bearbug)ground_List.get(ground_Remove_Temp)).set_Knockback(convertPixelsToDp(15, _context) + convertPixelsToDp(random.nextInt(60),_context));
                        main_Character.set_Character_Experience(1);
                        Score+=random.nextInt(5);
                        money+=character_Randmark_Damege_Temp;
//                        //클릭된 곤벌레의체력을 깍는다.
//                        if(ground_List.get(i).get_Ground_Point_Y() < 0) {
//                            ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(100);
//                        }
                        soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                        return true;


                    }
                }

                //선택된 가오리 존재한다면.
                if(ground_List.get(i) instanceof Ground_Touch_Stingray && ground_Hit_Drag == false){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width()  + ground_List.get(i).get_Width_Size()
                            && y >= ground_List.get(i).get_Ground_Point_Y()  - ground_List.get(i).get_Height_Size()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()){
                        ground_Remove_Temp = i;


                        skill_Ground_Attack();

                        rand_Effect = random.nextInt(4);
                        if (rand_Effect == 0) {
                            effect_Temp = effect_Pop2_img[4];
                        } else if (rand_Effect == 1) {
                            effect_Temp = effect_Pop3_img[4];
                        } else if (rand_Effect == 2) {
                            effect_Temp = effect_Pop4_img[4];
                        } else {
                            effect_Temp = effect_Pop5_img[4];
                        }

                        stingray_Ground_Hit_Flag = true;   //가오리 터치 이벤트 doDraw에서 발생

                        Score+=random.nextInt(5);
                        money+=character_Randmark_Damege_Temp;
                        //클릭된 가오리의체력을 깍는다.
                        main_Character.set_Character_Experience(1);
//                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();

                        soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                        return true;


                    }
                }

                //선택된 가제가 있다면.
                if(ground_List.get(i) instanceof Ground_Drag_Lobsters){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width()  + ground_List.get(i).get_Width_Size()
                            && y >= ground_List.get(i).get_Ground_Point_Y()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height()){
                        ground_Remove_Temp = i;


                        if(!((Ground_Drag_Lobsters)ground_List.get(ground_Remove_Temp)).get_Mode()) {
                            skill_Ground_Attack();
                        }



                        if(ground_Hit_Drag){
                            //드래그 중일때
                            if(!((Ground_Drag_Lobsters)ground_List.get(ground_Remove_Temp)).get_Mode()) {

                                tempInt = random.nextInt(5);
                                if(character_Damege == 1) {
                                    effect_Temp = effect_Pop_damage1_Image[tempInt];
                                }else if(character_Damege == 2) {
                                    effect_Temp = effect_Pop_damage2_Image[tempInt];
                                }else if(character_Damege == 3) {
                                    effect_Temp = effect_Pop_damage3_Image[tempInt];
                                }else if(character_Damege == 4) {
                                    effect_Temp = effect_Pop_damage4_Image[tempInt];
                                }else if(character_Damege == 5) {
                                    effect_Temp = effect_Pop_damage5_Image[tempInt];
                                }else if(character_Damege == 6) {
                                    effect_Temp = effect_Pop_damage6_Image[tempInt];
                                }else if(character_Damege == 7) {
                                    effect_Temp = effect_Pop_damage7_Image[tempInt];
                                }else if(character_Damege == 8) {
                                    effect_Temp = effect_Pop_damage8_Image[tempInt];
                                }else if(character_Damege == 9) {
                                    effect_Temp = effect_Pop_damage9_Image[tempInt];
                                }else if(character_Damege >= 10) {
                                    effect_Temp = effect_Pop_damage10_Image[tempInt];
                                }

                                if(main_Character.get_Damage() > 1) {
                                    character_Damege = 2 + random.nextInt(main_Character.get_Damage());
                                }
                                ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);
                                Score+=random.nextInt((int)character_Drag_Damege);
                                money+=character_Randmark_Damege_Temp;
                                main_Character.set_Character_Experience((int)character_Drag_Damege);
                                soundPool.play(sound_Effect[random.nextInt(2)], pop_Drag/5, pop_Drag/5, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                            }
                        }else {

                            rand_Effect = random.nextInt(4);
                            if (rand_Effect == 0) {
                                effect_Temp = effect_Pop2_img[4];
                            } else if (rand_Effect == 1) {
                                effect_Temp = effect_Pop3_img[4];
                            } else if (rand_Effect == 2) {
                                effect_Temp = effect_Pop4_img[4];
                            } else {
                                effect_Temp = effect_Pop5_img[4];
                            }

                            //터치 중일때
                            ((Ground_Drag_Lobsters)ground_List.get(ground_Remove_Temp)).set_Mode();
                            Score+=random.nextInt((int)character_Drag_Damege * tempInt);
                            money+=character_Randmark_Damege_Temp;
                            main_Character.set_Character_Experience((int)character_Drag_Damege * tempInt);
                            soundPool.play(sound_Effect[random.nextInt(2)], pop_Drag/5, pop_Drag/5, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                        }

                        lobsters_Ground_Hit_Flag = true;

                        tempInt = main_Character.get_Tear();
                        if(tempInt <= 0){
                            tempInt = 1;
                        }

                        return true;


                    }
                }


                //선택된 파도가 존재한다면.
                if(ground_List.get(i) instanceof Ground_Drag_Wave && ground_Hit_Drag){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + ground_List.get(i).get_Width_Size()
                            && y >= ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()){

                        ground_Remove_Temp = i;

//                        soundPool.play(sound_Effect[2 + random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드

                        tempInt = random.nextInt(5);
                        wave_Ground_Hit_Flag = true; //꽃게 터치 이벤트 doDraw에서 발생

                        //드래그된 파도의 체력을 깍는다.
                        if(main_Character.get_Damage() > 1) {
                            character_Damege = 2 + random.nextInt(main_Character.get_Damage());
                        }

                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);
                        soundPool.play(sound_Effect[11], pop_Drag/5, pop_Drag/5, 0, 0, 1.0F);   //드래그 사운드
                        Score+=random.nextInt(5);     //점수 추가
                        money+=character_Randmark_Damege_Temp;
                        main_Character.set_Character_Experience(1);  //경험치 추가
                        return true;

                    }
                }


                //꽃게를찾는다.
                if(drag_Action_Move > 3 && ground_List.get(i) instanceof Ground_Drag_Crab && ground_Hit_Drag){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + ground_List.get(i).get_Width_Size()
                            && y >= ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()){

                        ground_Remove_Temp = i;
                        skill_Ground_Attack();

                        tempInt = random.nextInt(5);
                        crap_Ground_Hit_Flag = true; //꽃게 터치 이벤트 doDraw에서 발생

                        //드래그된 꽃게의 체력을 깍는다.
                        if(main_Character.get_Damage() > 1) {
                            character_Damege = 1 + random.nextInt(main_Character.get_Damage());
                        }


//                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);
                        soundPool.play(sound_Effect[2 + random.nextInt(2)], pop_Drag/5, pop_Drag/5, 0, 0, 1.0F);   //드래그 사운드
                        tempInt = main_Character.get_Tear();
                        if(tempInt <= 0){
                            tempInt = 1;
                        }
                        Score+=random.nextInt((int)character_Drag_Damege *tempInt);        //점수 추가
                        money+=character_Randmark_Damege_Temp;
                        main_Character.set_Character_Experience((int)character_Drag_Damege * tempInt);  //경험치 추가
                        return true;

                    }
                }


                //조게를찾는다.
                if(drag_Action_Move > 3 && ground_List.get(i) instanceof Ground_Drag_Clam && ground_Hit_Drag){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()/2
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + ground_List.get(i).get_Width_Size()/2
                            && y >= ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size()/2
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()/2){

                        ground_Remove_Temp = i;


                        tempInt = random.nextInt(5);
                        clam_Ground_Hit_Flag = true; //꽃게 터치 이벤트 doDraw에서 발생

                        //드래그된 조게의 체력을 깍는다.
                        if(main_Character.get_Damage() > 1) {
                            character_Damege = 1 + random.nextInt(main_Character.get_Damage());
                        }
//                        character_Damege = 1000;

//                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);
                        soundPool.play(sound_Effect[2 + random.nextInt(2)], pop_Drag/5, pop_Drag/5, 0, 0, 1.0F);   //드래그 사운드
                        tempInt = main_Character.get_Tear();
                        if(tempInt <= 0){
                            tempInt = 1;
                        }
                        Score+=random.nextInt((int)character_Drag_Damege * tempInt);
                        money+=character_Randmark_Damege_Temp;
                        main_Character.set_Character_Experience((int)character_Drag_Damege * tempInt);  //경험치 추가
                        return true;
                    }
                }


                //악어를찾는다.
                if(ground_List.get(i) instanceof Ground_Touch_Crocodile && ground_Hit_Drag == false){
                    if(        x >= ground_List.get(i).get_Ground_Point_X()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width()
                            && y >= ground_List.get(i).get_Ground_Point_Y()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height()){

                        ground_Remove_Temp = i;


                        if(random.nextInt(100) > crocodileresistance) {
                            ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);
                            main_Character.set_Hp_Minus();
                        }


                        return true;


                    }
                }


                //랜드마크를 찾는다.
                if(drag_Action_Move > 2 && ground_List.get(i) instanceof Land_Mark && ground_Hit_Drag){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + ground_List.get(i).get_Width_Size()
                            && y >= ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()){



                        ground_Remove_Temp = i;
                        tempInt = random.nextInt(5);
                        land_Mark_Hit_Flag = true; //랜드마크 이펙트 doDraw에서 발생
                        character_Randmark_Damege_Temp = 1 + random.nextInt((int)character_Randmark_Damege);
                        //드래그된 랜드마크의 체력을 깍는다.
                        if(main_Character.get_Damage() > 1) {
                            character_Damege = 1 + random.nextInt(character_Randmark_Damege_Temp);
                        }


                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Randmark_Damege_Temp);
                        land_Mark_AttackSpeed++;
                        if(land_Mark_AttackSpeed%2 == 0){
                            soundPool.play(sound_Effect[2 + random.nextInt(2)], pop_Drag/2, pop_Drag/2, 0, 0, 1.0F);   //드래그 사운드
                        }


                        Score+=random.nextInt(5);     //점수 추가
                        money+=character_Randmark_Damege_Temp;
                        main_Character.set_Character_Experience(character_Randmark_Damege_Temp);  //경험치 추가

                        //랜드마크 삭제의 경우
                        if(ground_List.get(ground_Remove_Temp).get_Ground_Hp() <= 0){
//                    add_Boss_Fish_Touch_Default();
//                    add_Ground_Boss(window_Width/2 , -30);
                        }

                        return true;

                    }
                }

                //성게를 찾는다.
                if(ground_List.get(i) instanceof Ground_Trap_Urchin && ground_Hit_Drag == false){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + ground_List.get(i).get_Width_Size()
                            && y >= ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()){

                        ground_Remove_Temp = i;

//                        Log.e("e","성게");


                        effect_Temp = effect_Black_img[4];


                        seaurchin_Ground_Hit_Flag = true;   //성게 터치 이벤트는 doDraw 에서


//                        //성게가 공격 모드 일때, 게딱지가 아닐때
//                        if(((Ground_Trap_Urchin)ground_List.get(ground_Remove_Temp)).get_Urchin_Attack_Mode() && !(main_Character instanceof Main_Character_Shellfish_Tear3)){
//                            //get_Urchin_Attack_Mode()
//                            main_Character.set_Hp_Minus();
//                        gameActivity.set_Vibrator();
//                        }


                        //메인 캐릭터 hp 감소 루틴 추가 해야함
                        soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //물고기 기본 팝 사운드
                        return true;
                    }
                }



            }



        }

        return false;
    }









    /**
     * 물고기 히트
     */
    public boolean fish_Hit_Chose(int fish_Class){      //매개변수를 int 형으로 해야 가장 근접한 객체를 찾을 수 있다.






        //물고기 삭제 1번 먼저
        if(fish_List.size() != 0) {         //물고기가 존재할때 눌러짐


            eraser_Fish = false;            //물고기 지우기 허가하기


            /**
             * 가장 가까운 물고기를 찾는다.
             */
//            for(int i=fish_List.size() - 1; i>=0; i--){
            for(int i=0; i<fish_List.size(); i++){

                //전달받은 물고기 인자가 아닐때 생깜, 방해 거북이 아닐때
                if((fish_List.get(i).get_Fish_Class() != fish_Class && fish_List.get(i).get_Fish_Class() != 3) || fish_List.get(i).get_Fish_Hp() <= 0){
                    continue;
                }



                if(!(fish_List.get(i).get_Visible_Fish_Flag())){
                    continue;
                }

                //y축으로 가장 가까운 적을 선택한다.
                smallMathResult = fish_List.get(i).get_Fish_Point_Y() + fish_List.get(i).get_Height_Size();

                if(smallMathResult > smallFishTemp){
                    smallFishTemp = smallMathResult;
                    smallFishIndex = i;                     //for 문안에서 가장 가까운 물고기를 찾는다.
                    eraser_Fish = true;
                }

            }
            smallFishTemp = -30; //제일 가까운 물고기 찾기위한 템프변수

            if(eraser_Fish){

                //메인 캐릭터가 달팽이 일때 공격하면 정지 시킨다. //확률로 정지 시켜야함
                if(!(fish_List.get(smallFishIndex) instanceof Fish_Drag_Steelbream)) {
                    if(!(fish_List.get(smallFishIndex) instanceof Fish_Trap_Turtle)) {

                        if(fish_List.get(smallFishIndex) instanceof Fish_Drag_Shark && random.nextInt(100) > 90 ){
                            skill_Fish_Attack();
                        }else if(fish_List.get(smallFishIndex) instanceof Fish_Drag_Default && random.nextInt(100) > 90 ) {
                            skill_Fish_Attack();
                        }else if(fish_List.get(smallFishIndex) instanceof Fish_Drag_Default && random.nextInt(100) > 90 ) {
                            skill_Fish_Attack();
                        }else if(fish_List.get(smallFishIndex) instanceof Fish_Monster && random.nextInt(100) > 90 ) {
                            skill_Fish_Attack();
                        }else {
                            skill_Fish_Attack();
                        }


                    }
                }



                if(fish_List.get(smallFishIndex) instanceof Fish_Touch_Default){        //전달 받은 인자가 기본 물고기 일때.


                    rand_Effect = random.nextInt(4);
                    //랜덤 이팩트
                    if (rand_Effect == 0) {
                        effect_Temp = effect_Orange_img[4];
                    } else if (rand_Effect == 1) {
                        effect_Temp = effect_Blue_img[4];
                    } else if (rand_Effect == 2) {
                        effect_Temp = effect_Yellow_img[4];
                    } else {
                        effect_Temp = effect_Green_img[4];
                    }


                    soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //물고기 기본 팝 사운드


//                        fish_List.get(smallFishIndex).set_Hp_Minus();            //풍타디 처럼 물고기 hp 깍으면 색깔 변경

                    default_Fish_Hit_Flag = true;   //그림은 드로우에서






//                    delete_Fish_Select(smallFishIndex);     //피가0이 된 물고기 삭제.



                    //main_Character.get_Tear() => 0 * 기가 되서 오류 걸림 다 바꿔야한다.
                    tempInt = main_Character.get_Tear();
                    if(tempInt <= 0){
                        tempInt = 1;
                    }
                    Score+=1 + random.nextInt((int)character_Drag_Damege * tempInt);
                    money+=character_Randmark_Damege_Temp;
                    main_Character.set_Character_Experience(1); //경험치 추가
                    return true;

                }else if(fish_List.get(smallFishIndex) instanceof Fish_Touch_Squid){
                    touch_Squid_Hit_Flag = true;
                    soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //오징어 기본 팝 사운드
                }else if(fish_List.get(smallFishIndex) instanceof Fish_Touch_Ell){
                    touch_Ell_Hit_Flag = true;
                    soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                }else if(fish_List.get(smallFishIndex) instanceof Fish_Touch_Marlin){
                    touch_Marlin_Hit_Flag = true;
                    soundPool.play(sound_Effect[random.nextInt(2)], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드

                }

                //해파리 때리기
                else if((fish_List.get(smallFishIndex) instanceof Fish_Trap_Jellyfish)){
                    fish_List.get(smallFishIndex).set_Hp_Minus(character_Damege);            //풍타디 처럼 물고기 hp 깍으면 색깔 변경
                }



                if(fish_List.get(smallFishIndex).get_Fish_Hp() > 0) {        //드래그 속도까 빨라서 0밑으로내려감 방지

                    if(main_Character.get_Damage() > 1) {
                        character_Damege = 1 + random.nextInt(main_Character.get_Damage());
                    }







                    if(fish_List.get(smallFishIndex).get_Fish_Class() == 3){

                        if(random.nextInt(100) > 60) {
                            soundPool.play(sound_Effect[random.nextInt(2)], pop_Drag/3, pop_Drag/3, 0, 0, 1.0F);   //거북이 기본 팝 사운드
                        }
                        if(fish_List.get(smallFishIndex) instanceof Fish_Trap_Turtle) {
                            turtle_Fish_Hit_Flag = true;
                        }else {
                            ((Fish_Monster)fish_List.get(smallFishIndex)).set_Knockback();
                            monster_Fish_Hit_Flag = true;
                        }
                        return true;
                    }




                    tempInt = main_Character.get_Tear();
                    if(tempInt <= 0){
                        tempInt = 1;
                    }
                    Score+=random.nextInt((int)character_Drag_Damege * tempInt);                                   //점수 증가
                    money+=character_Randmark_Damege_Temp;
                    main_Character.set_Character_Experience((int)character_Drag_Damege * tempInt);  //경험치 추가



                    //드래그시 공격당한다는 느낌 주기 위해
                    if(fish_List.get(smallFishIndex) instanceof Fish_Drag_Default){
                        //드래그 물고기 및 해파리 대미지 입힌다.

//                        fish_List.get(smallFishIndex).set_Hp_Minus(character_Damege);            //풍타디 처럼 물고기 hp 깍으면 색깔 변경

                        drag_Fish_Hit_Flag = true;  //그림은 드로우에
                        soundPool.play(sound_Effect[2 + random.nextInt(2)], pop_Drag/5, pop_Drag/5, 0, 0, 1.0F);   //드래그 사운드

                    }else if(fish_List.get(smallFishIndex) instanceof Fish_Drag_Steelbream){
                        //참돔은 확률로 갑옷을 깨고 대미지를 입힌다.

                        if(random.nextInt(100) < 2 + steel) {
                            fish_List.get(smallFishIndex).set_Hp_Minus(40000);
//                            add_Fish_Drag_Default(fish_List.get(smallFishIndex).get_Fish_Point_X(), fish_List.get(smallFishIndex).get_Fish_Point_Y());
                            /**
                             *  강철참돔 제거하면 제거한자리에 드래그 물고기 생성
                             */
                            fish_Child_Steel_Drag_Set(fish_List.get(smallFishIndex).get_Fish_Point_X(), fish_List.get(smallFishIndex).get_Fish_Point_Y());
                        }

                        drag_Steelbream_Hit_Flag = true;        //참돔
                        soundPool.play(sound_Effect[12], pop_Drag/3, pop_Drag/3, 0, 0, 1.0F);

                    }else if(fish_List.get(smallFishIndex) instanceof Fish_Drag_Shark){
                        //상어 드래그
                        ((Fish_Drag_Shark)fish_List.get(smallFishIndex)).set_Knockback();

                        //대미지 입힌다.
//                        fish_List.get(smallFishIndex).set_Hp_Minus(character_Damege);            //풍타디 처럼 물고기 hp 깍으면 색깔 변경

                        soundPool.play(sound_Effect[2 + random.nextInt(2)], pop_Drag/5, pop_Drag/5, 0, 0, 1.0F);
                        drag_Shark_Hit_Flag = true;


                    }




                    return true;
                }
            }else {
                Log.i("발 터짐",window_Width + " ");
            }
        }else {
        }
        return false;
    }
    //********************************************************************************************//


    public void draw_Main_Character_Draw(){  //가만히 있을때


        if(main_Character.set_Character_Revolution(main_Character.get_Revolrution_Step())){


            //진화 경험치
            revolution_Flag = true;
//            Log.e("@@","!@#!@#!@#@!#");

        }

        if(main_Character.get_Transform_Chra_Flag()) {
            main_Character_Draw(main_Character_Img_1);
        }else {
            main_Character_Draw(main_Character_Img);
        }


    }


    int dm_Temp = 0;
    int x_Dm_T = 0;
    int y_Dm_T = 0;
    //    x_Dm_T = (temp_Fish.getWidth() * dm.widthPixels)/convertPixelsToDp(450, _context);
//    y_Dm_T = (temp_Fish.getHeight() * dm.heightPixels)/convertPixelsToDp(450, _context);
    //메인 캐릭터 진화 할때 새로 그리기 #Score 진화할 점수 #character_img 캐릭터 이미지 배열
    private void main_Character_Draw(Bitmap[] character_img){
//        dm_Temp  = (dm.widthPixels + dm.heightPixels)/convertPixelsToDp(130, _context);

        // #50 부분에 각 캐릭터마다 모양 변화 경험치가 들어가야함
        if(main_Character.set_Character_Upgrade()){
            //점수에 따라 곰팡이 진화
            main_Character.Set_Main_Character_Mode_Status();

        }

        //곰팡이 공격상태, 메인캐릭터 그리기
        if(main_Character.get_Attack_State()){

            if(!main_Character.get_Direct_Status()) {


                temp_Fish = draw.reverse_Image(character_img[main_Character.get_Main_Character_Mode_Status() + 1]);



                draw.draw_Bmp(canvas, temp_Fish, main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y());


            }else {
//                temp_Fish = Bitmap.createScaledBitmap(character_img[main_Character.get_Main_Character_Mode_Status()+1], dm.widthPixels/convertPixelsToDp(6, _context), dm.heightPixels/convertPixelsToDp(6, _context), false);
                temp_Fish = character_img[main_Character.get_Main_Character_Mode_Status()+1];
                draw.draw_Bmp(canvas, temp_Fish, main_Character.get_Main_Character_Point_X(),main_Character.get_Main_Character_Point_Y());


            }



        }else { //곰팡이 기본상태

            if(!main_Character.get_Direct_Status()) {
                temp_Fish = draw.reverse_Image(character_img[main_Character.get_Main_Character_Mode_Status()]);

//                temp_Fish = Bitmap.createScaledBitmap(temp_Fish, dm.widthPixels/convertPixelsToDp(6, _context), dm.heightPixels/convertPixelsToDp(6, _context), false);
                draw.draw_Bmp(canvas, temp_Fish, main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y());
            }else {
//                temp_Fish = Bitmap.createScaledBitmap(character_img[main_Character.get_Main_Character_Mode_Status()], dm.widthPixels/convertPixelsToDp(6, _context), dm.heightPixels/convertPixelsToDp(6, _context), false);
                temp_Fish = character_img[main_Character.get_Main_Character_Mode_Status()];
                draw.draw_Bmp(canvas, temp_Fish, main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y());
            }
        }
    }


    GraphicButton confirm_Button_1 = new GraphicButton(new Rect(0, 0, 0, 0)); //메인 캐릭터 진화시 뜨는 확인창
    GraphicButton confirm_Button_2 = new GraphicButton(new Rect(0, 0, 0, 0)); //메인 캐릭터 진화시 뜨는 확인창 - 추출
    GraphicButton confirm_Button_3 = new GraphicButton(new Rect(0, 0, 0, 0)); //몬스터 설명창 확인 버튼

    GraphicButton revolution_Button; //경험치 다 찾을때 띄울 버튼

    GraphicButton confirm_Button_card_1; //메인 캐릭터 진화시 뜨는 확인창
    GraphicButton confirm_Button_card_2; //메인 캐릭터 진화시 뜨는 확인창
    GraphicButton confirm_Button_card_3; //메인 캐릭터 진화시 뜨는 확인창

    GraphicButton Extraction_Button_1; //일반 추출 버튼
    GraphicButton Extraction_Button_2; //영구 추출 버튼
    GraphicButton Extraction_Button_3; //취소 버튼



    /**
     * 터치 이벤트
     */
    int touchx;
    int touchy;
    private boolean revolution_Button_Activation = false;
    private boolean revolution_Button_Activation_Down = false;
    private boolean revolution_Button_Activation_Up = false;

    boolean up = false;
    boolean down = false;


    /**
     * 게임 오버시 게임 조절
     */
    boolean gameover_Flag = false;


    @Override
    public boolean onTouchEvent(MotionEvent event) {



        if(infinitymode_Status){

            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    if(down)
                        up = true;
                    break;
                case MotionEvent.ACTION_DOWN:
                    down = true;
                    break;
            }

            if(up && down){

                down = false;
                up = false;


                    infinitymode_Status = false;
                    return false;
                }

        }


        touchx = (int) event.getX();
        touchy = (int) event.getY();
        try {

            if(gameover_Flag){

                //게임 오버 했을때 뜰 창
                gameActivity.popup_Menu();

                return true;
            }

            if(!mRun){
                if(tutorial_Flag) {
                    if (first_Text_Explain_Flag) {
                        //가장 첫번째 텍스트 설명창일때 아무대나 터치해도 넘어감

                        switch (event.getAction()) {


                            case MotionEvent.ACTION_UP:
                                if(down)
                                    up = true;
                                break;
                            case MotionEvent.ACTION_DOWN:

                                down = true;
                                break;

                        }

                        if(up && down){





                            first_Text_Explain_Index++;

                            m_Run_True();
                            if (first_Text_Explain_Index > 8) {

                                first_Text_Explain_Flag = false;


                            }
                            down = false;
                            up = false;
                        }
                    }
                }
            }


            if (!mRun && !pause_State) {


                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP:




                        confirm_Button_1.setPress(false);    //버튼 상태 초기화
                        confirm_Button_2.setPress(false);    //버튼 상태 초기화
                        confirm_Button_3.setPress(false);

                        if (confirm_Button_1.touch(touchx, touchy)) { //버튼 클릭 행동    //확인
//Log.e("@","2");

                            //진화의 버튼이 떠있을때. 물고기 설명창이 나오면 진화의 버튼 삭제 후 재 생성, 확인 눌렀을때.
                            if (revolution_Draw_Flag_Confirm) {
                                //몹 설명창이랑 진화 카드 창이랑 겹치면 안된다.
                                revolution_Flag = false;

                                //진화의 버튼 초기화
                                revolution_Button = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(50, _context),
                                        window_Height / 2 - convertPixelsToDp(145, _context),
                                        window_Width / 2 - convertPixelsToDp(50, _context) + convertPixelsToDp(105, _context),
                                        window_Height / 2 - convertPixelsToDp(145, _context) + convertPixelsToDp(40, _context)));

                                //백그라운드 이팩트
                                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.confirm_button_effect);
                                revolution_Button_Background_Effect = image.getBitmap();


                                upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.revolution_img);
                                downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.revolution_img);

                                revolution_Button.setImages(upimage, downimage);

                                revolution_Draw_Flag_Confirm = true;
                                revolution_Flag = true;
                            }


                            //확인 버튼
//                  Score = 1000;
                            m_Run_True();   //게임 재게
                            revolution_Flag_Confirm = false; //변신창 확인 누르먄 재시작


                        } else if(confirm_Button_2.touch(touchx, touchy) && extraction_Button_Flag){
                            //추출 버튼 눌렀을때
//                            Log.e("@","추출");





                            //진화의 버튼이 떠있을때. 물고기 설명창이 나오면 진화의 버튼 삭제 후 재 생성, 확인 눌렀을때.
                            if (revolution_Draw_Flag_Confirm) {
                                //몹 설명창이랑 진화 카드 창이랑 겹치면 안된다.
                                revolution_Flag = false;

                                //진화의 버튼 초기화
                                revolution_Button = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(50, _context),
                                        window_Height / 2 - convertPixelsToDp(145, _context),
                                        window_Width / 2 - convertPixelsToDp(50, _context) + convertPixelsToDp(105, _context),
                                        window_Height / 2 - convertPixelsToDp(145, _context) + convertPixelsToDp(40, _context)));

                                //백그라운드 이팩트
                                image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.confirm_button_effect);
                                revolution_Button_Background_Effect = image.getBitmap();


                                upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.revolution_img);
                                downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.revolution_img);

                                revolution_Button.setImages(upimage, downimage);

                                revolution_Draw_Flag_Confirm = true;
                                revolution_Flag = true;
                            }


                            //확인 버튼
                            revolution_Flag_Confirm = false; //변신창 확인 누르먄 재시작

                            //추출버튼
                            Extraction = true;

                            m_Run_True();   //게임 재게

                            //확인 / 추출창 제거
                            button_Init_Confirm_Or_Extraction();

                        }

                        if(confirm_Button_3.touch(touchx, touchy)){
                            Log.e("@","@@버튼 눌림");
                            first_Explain = false;
                            confirm_Button_3 = new GraphicButton(new Rect(0, 0, 0, 0));
                        }

                        else if(Extraction_Button_1.touch(touchx, touchy)){

                                if(main_Character.get_Tear() <= ruby) {
                                    ruby -= main_Character.get_Tear();


                                    //추출의 일반 추출 클릭
//                                    Log.e("@", "일반추출");
                                    //일반 추출, 영구추출, 캔슬 버튼 제거
                                    button_Init_Confirm_Or_Extraction_123();

                                    //skill_Fish_Extract_Nomar

                                    //캐릭터 스킬 추출
                                    set_Maincharacter_Skill_Set(main_Character, false);

    //확인 버튼
                                    revolution_Flag_Confirm = false; //변신창 확인 누르먄 재시작

                                    //sql 라이트
                                    String sql = "UPDATE maincharacterinfo SET money = '" + money + "', ruby = '" + ruby + "'";
                                    MainActivity.database.execSQL(sql);
                                    editor.putString("realmoney", String.valueOf(money));
                                    editor.commit();

                                    m_Run_True();   //게임 재게
                                }else {
                                    //스킬추출 실패
                                    soundPool.play(sound_Effect[17], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                                    gameActivity.set_Vibrator();
                                }

                        }else if(Extraction_Button_2.touch(touchx, touchy) ){

                            if(main_Character.get_Tear()*10 <= ruby) {
                                ruby -= main_Character.get_Tear() * 10;

                                //추출의 일반 추출 클릭
//                                Log.e("@", "영구추출");
                                //일반 추출, 영구추출, 캔슬 버튼 제거
                                button_Init_Confirm_Or_Extraction_123();

                                set_Maincharacter_Skill_Set(main_Character, true);

                                //확인 버튼
                                revolution_Flag_Confirm = false; //변신창 확인 누르먄 재시작

                                //sql 라이트
                                String sql = "UPDATE maincharacterinfo SET money = '" + money + "', ruby = '" + ruby + "'";
                                MainActivity.database.execSQL(sql);
                                editor.putString("realmoney", String.valueOf(money));
                                editor.commit();

                                m_Run_True();   //게임 재게
                            }else{
                                //스킬 추출 실패
                                soundPool.play(sound_Effect[17], pop_Touch, pop_Touch, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                                gameActivity.set_Vibrator();
                            }

                        }else if(Extraction_Button_3.touch(touchx, touchy) ){



                            //추출의 일반 추출 클릭
//                            Log.e("@","캔슬");
                            //일반 추출, 영구추출, 캔슬 버튼 제거
                            button_Init_Confirm_Or_Extraction_123();

                            //확인 버튼
                            revolution_Flag_Confirm = false; //변신창 확인 누르먄 재시작

                            m_Run_True();   //게임 재게

                        }


                        else if (confirm_Button_card_1.touch(touchx, touchy) || confirm_Button_card_2.touch(touchx, touchy)) {
                            //진화창 버튼


                            //진화 성공시
                            int rand_Temp = random.nextInt(100);    //진화 여부
                            int rand_Temp2 = random.nextInt(100);   //보존, 퇴화 여부
                            int rand_Temp3 = random.nextInt(100);   //종류 선택
                            //진화 확률 = 0티어 플랑크톤 일때 100 %
                            //1티어 일때 90%, 2티어 80%, 3티어 70%, 4티어 60%, 5티어 50%, 6티어 40%, 7티어 30%, 6티어 20%, 9티어, 10%

                            if((main_Character.get_Tear() == 0)){

//                                Log.e("a","asdasdsad@@@");

                                //메모리 관리 기법

                                //0티어 진화
                                if (rand_Temp3 <= 25) {
                                    game_thread.function_Explain_Window_Shellfish_Tear_1();
                                    main_Character = new Main_Character_Shellfish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                    ce16 = 1;
                                    String sql = "UPDATE maincharacterinfo SET ce16 = '" + ce16 + "'";
                                    MainActivity.database.execSQL(sql);

                                    character_Explain_DB[15] = 1;

                                } else if (rand_Temp3 <= 50) {
                                    game_thread.function_Explain_Window_Fish_Tear_1();
                                    main_Character = new Main_Character_Fish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                    ce6 = 1;
                                    String sql = "UPDATE maincharacterinfo SET ce6 = '" + ce6 + "'";
                                    MainActivity.database.execSQL(sql);
                                    character_Explain_DB[5] = 1;


                                } else if(rand_Temp3 <= 75){
                                    //메모리 관리
                                    game_thread.function_Explain_Window_Moulluse_Tear_1();
                                    main_Character = new Main_Character_Moulluse_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                    ce26 = 1;
                                    String sql = "UPDATE maincharacterinfo SET ce26 = '" + ce26 + "'";
                                    MainActivity.database.execSQL(sql);
                                    character_Explain_DB[25] = 1;

                                } else {
                                    game_thread.function_Explain_Window_Plankton_Tear_2();
                                    main_Character = new Main_Character_Plankton_2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                    ce2 = 1;
                                    String sql = "UPDATE maincharacterinfo SET ce2 = '" + ce2 + "'";
                                    MainActivity.database.execSQL(sql);
                                    character_Explain_DB[1] = 1;

                                }
//                                Log.e("a","asdasdsad###");

                                main_Character.set_Tear(1);
                            }

                            else if((main_Character.get_Tear() == 1) ){

                                //메모리 관리 기법
                                explain_Window_MainCharacker.recycle();
                                explain_Window_MainCharacker = null;

                                if(rand_Temp <= 90){
                                    //1티어 진화 확률 90%

                                    if (rand_Temp3 <= 25) {
                                        game_thread.function_Explain_Window_Shellfish_Tear_2();
                                        main_Character = new Main_Character_Shellfish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce17 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce17 = '" + ce17 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[16] = 1;

                                    } else if (rand_Temp3 <= 50) {
                                        game_thread.function_Explain_Window_Fish_Tear_2();
                                        main_Character = new Main_Character_Fish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce7 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce7 = '" + ce7 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_Thorn_img();
                                        character_Explain_DB[6] = 1;

                                    } else if(rand_Temp3 <= 75){
                                        //메모리 관리
                                        game_thread.function_Explain_Window_Moulluse_Tear_2();
                                        main_Character = new Main_Character_Moulluse_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce27 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce27 = '" + ce27 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[26] = 1;

                                    } else {
                                        game_thread.function_Explain_Window_Plankton_Tear_3();
                                        main_Character = new Main_Character_Plankton_3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce3 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce3 = '" + ce3 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[2] = 1;
                                    }
                                    main_Character.set_Tear(2);


                                }else{
                                    //1티어 전이 확률 90%
                                    //1티어 퇴화 확률 10%
                                    if(rand_Temp2 <= 90){
                                        //1티어 전이
                                        //0티어 진화
                                        if (rand_Temp3 <= 25) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_1();
                                            main_Character = new Main_Character_Shellfish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                                        } else if (rand_Temp3 <= 50) {
                                            game_thread.function_Explain_Window_Fish_Tear_1();
                                            main_Character = new Main_Character_Fish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());


                                        } else if(rand_Temp3 <= 75){
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_1();
                                            main_Character = new Main_Character_Moulluse_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        } else {
                                            game_thread.function_Explain_Window_Plankton_Tear_2();
                                            main_Character = new Main_Character_Plankton_2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        }
                                        main_Character.set_Tear(1);

                                    }else {
                                        //1티어 퇴화
                                        game_thread.function_Explain_Window_Plankton_Tear_1();
                                        main_Character = new Main_Character_Plankton_1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        main_Character.set_Tear(0);
                                    }

                                }
                            }


                            else if((main_Character.get_Tear() == 2) ){

                                //메모리 관리 기법
                                explain_Window_MainCharacker.recycle();
                                explain_Window_MainCharacker = null;

                                if(rand_Temp <= 80){
                                    //2티어 진화 확률 80%

                                    if (rand_Temp3 <= 25) {
                                        game_thread.function_Explain_Window_Shellfish_Tear_3();
                                        main_Character = new Main_Character_Shellfish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[17] = 1;
                                        ce18 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce18 = '" + ce18 + "'";
                                        MainActivity.database.execSQL(sql);

                                    } else if (rand_Temp3 <= 50) {
                                        game_thread.function_Explain_Window_Fish_Tear_3();
                                        main_Character = new Main_Character_Fish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[7] = 1;
                                        ce8 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce8 = '" + ce8 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_Teeth_mine_img();

                                    } else if(rand_Temp3 <= 75){
                                        //메모리 관리
                                        game_thread.function_Explain_Window_Moulluse_Tear_3();
                                        main_Character = new Main_Character_Moulluse_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[27] = 1;
//                                    game_thread.function_Skill_Slow_Cloud_img();
                                        ce28 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce28 = '" + ce28 + "'";
                                        MainActivity.database.execSQL(sql);
                                    } else {
                                        game_thread.function_Explain_Window_Plankton_Tear_4();
                                        main_Character = new Main_Character_Plankton_4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[3] = 1;
                                        ce4 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce4 = '" + ce4 + "'";
                                        MainActivity.database.execSQL(sql);
                                    }
                                    main_Character.set_Tear(3);


                                }else{
                                    //2티어 전이 확률 80%
                                    //2티어 퇴화 확률 20%
                                    if(rand_Temp2 <= 80){
                                        //2티어 전이
                                        if (rand_Temp3 <= 25) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_2();
                                            main_Character = new Main_Character_Shellfish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                                        } else if (rand_Temp3 <= 50) {
                                            game_thread.function_Explain_Window_Fish_Tear_2();
                                            main_Character = new Main_Character_Fish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Thorn_img();

                                        } else if(rand_Temp3 <= 75){
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_2();
                                            main_Character = new Main_Character_Moulluse_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        } else {
                                            game_thread.function_Explain_Window_Plankton_Tear_3();
                                            main_Character = new Main_Character_Plankton_3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        }
                                        main_Character.set_Tear(2);
                                    }else {
                                        //2티어 퇴화
                                        if (rand_Temp3 <= 25) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_1();
                                            main_Character = new Main_Character_Shellfish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                                        } else if (rand_Temp3 <= 50) {
                                            game_thread.function_Explain_Window_Fish_Tear_1();
                                            main_Character = new Main_Character_Fish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());


                                        } else if(rand_Temp3 <= 75){
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_1();
                                            main_Character = new Main_Character_Moulluse_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        } else {
                                            game_thread.function_Explain_Window_Plankton_Tear_2();
                                            main_Character = new Main_Character_Plankton_2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        }
                                        main_Character.set_Tear(1);
                                    }

                                }
                            }


                            else if((main_Character.get_Tear() == 3) ){

                                //메모리 관리 기법
                                explain_Window_MainCharacker.recycle();
                                explain_Window_MainCharacker = null;

                                if(rand_Temp <= 70){

                                    if (rand_Temp3 <= 25) {
                                        game_thread.function_Explain_Window_Shellfish_Tear_4();
                                        main_Character = new Main_Character_Shellfish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce19 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce19 = '" + ce19 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[18] = 1;
//                                    game_thread.function_Skill_Crab_img();

                                    } else if (rand_Temp3 <= 50) {
                                        game_thread.function_Explain_Window_Fish_Tear_4();
                                        main_Character = new Main_Character_Fish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce9 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce9 = '" + ce9 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[8] = 1;
//                                    game_thread.function_Skill_earthquake_img();

                                    } else if(rand_Temp3 <= 75){
                                        //메모리 관리
                                        game_thread.function_Explain_Window_Moulluse_Tear_4();
                                        main_Character = new Main_Character_Moulluse_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce29 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce29 = '" + ce29 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[28] = 1;
//                                    game_thread.function_Skill_Butter_img();

                                    } else {
                                        game_thread.function_Explain_Window_Plankton_Tear_5();
                                        main_Character = new Main_Character_Plankton_5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce5 = 1;String sql = "UPDATE maincharacterinfo SET ce5 = '" + ce5 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[4] = 1;
                                    }
                                    main_Character.set_Tear(4);

                                }else{
                                    if(rand_Temp2 <= 70){
                                        if (rand_Temp3 <= 25) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_3();
                                            main_Character = new Main_Character_Shellfish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());



                                        } else if (rand_Temp3 <= 50) {
                                            game_thread.function_Explain_Window_Fish_Tear_3();
                                            main_Character = new Main_Character_Fish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Teeth_mine_img();

                                        } else if(rand_Temp3 <= 75){
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_3();
                                            main_Character = new Main_Character_Moulluse_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Slow_Cloud_img();

                                        } else {
                                            game_thread.function_Explain_Window_Plankton_Tear_4();
                                            main_Character = new Main_Character_Plankton_4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        }
                                        main_Character.set_Tear(3);
                                    }else {
                                        if (rand_Temp3 <= 25) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_2();
                                            main_Character = new Main_Character_Shellfish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                                        } else if (rand_Temp3 <= 50) {
                                            game_thread.function_Explain_Window_Fish_Tear_2();
                                            main_Character = new Main_Character_Fish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Thorn_img();

                                        } else if(rand_Temp3 <= 75){
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_2();
                                            main_Character = new Main_Character_Moulluse_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());



                                        } else {
                                            game_thread.function_Explain_Window_Plankton_Tear_3();
                                            main_Character = new Main_Character_Plankton_3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        }
                                        main_Character.set_Tear(2);
                                    }

                                }
                            }


                            else if((main_Character.get_Tear() == 4) ){

                                //메모리 관리 기법
                                explain_Window_MainCharacker.recycle();
                                explain_Window_MainCharacker = null;

                                if(rand_Temp <= 60){

                                    if (rand_Temp3 <= 33) {
                                        game_thread.function_Explain_Window_Shellfish_Tear_5();
                                        main_Character = new Main_Character_Shellfish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce20 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce20 = '" + ce20 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[19] = 1;
//                                    game_thread.function_Skill_Soycrab_img();

                                    } else if (rand_Temp3 <= 66) {
                                        game_thread.function_Explain_Window_Fish_Tear_5();
                                        main_Character = new Main_Character_Fish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce10 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce10 = '" + ce10 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[9] = 1;
//                                    game_thread.function_Skill_Teeth_mine2_img();

                                    } else {
                                        //메모리 관리
                                        game_thread.function_Explain_Window_Moulluse_Tear_5();
                                        main_Character = new Main_Character_Moulluse_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce30 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce30 = '" + ce30 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[29] = 1;
//                                    game_thread.function_Skill_Fork_img();

                                    }
                                    main_Character.set_Tear(5);

                                }else{
                                    if(rand_Temp2 <= 60){

                                        if (rand_Temp3 <= 25) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_4();
                                            main_Character = new Main_Character_Shellfish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Crab_img();

                                        } else if (rand_Temp3 <= 50) {
                                            game_thread.function_Explain_Window_Fish_Tear_4();
                                            main_Character = new Main_Character_Fish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_earthquake_img();

                                        } else if(rand_Temp3 <= 75){
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_4();
                                            main_Character = new Main_Character_Moulluse_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Butter_img();

                                        } else {
                                            game_thread.function_Explain_Window_Plankton_Tear_5();
                                            main_Character = new Main_Character_Plankton_5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        }
                                        main_Character.set_Tear(4);

                                    }else {

                                        if (rand_Temp3 <= 25) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_3();
                                            main_Character = new Main_Character_Shellfish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                                        } else if (rand_Temp3 <= 50) {
                                            game_thread.function_Explain_Window_Fish_Tear_3();
                                            main_Character = new Main_Character_Fish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Teeth_mine_img();

                                        } else if(rand_Temp3 <= 75){
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_3();
                                            main_Character = new Main_Character_Moulluse_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Slow_Cloud_img();

                                        } else {
                                            game_thread.function_Explain_Window_Plankton_Tear_4();
                                            main_Character = new Main_Character_Plankton_4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        }
                                        main_Character.set_Tear(3);

                                    }

                                }
                            }


                            else if((main_Character.get_Tear() == 5) ){

                                //메모리 관리 기법
                                explain_Window_MainCharacker.recycle();
                                explain_Window_MainCharacker = null;

                                if(rand_Temp <= 50){

                                    if (rand_Temp3 <= 33) {
                                        game_thread.function_Explain_Window_Shellfish_Tear_6();
                                        main_Character = new Main_Character_Shellfish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[20] = 1;
                                        ce21 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce21 = '" + ce21 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_Thorn2_img();

                                    } else if (rand_Temp3 <= 66) {
                                        game_thread.function_Explain_Window_Fish_Tear_6();
                                        main_Character = new Main_Character_Fish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce11 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce11 = '" + ce11 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[10] = 1;

                                    } else {
                                        //메모리 관리
                                        game_thread.function_Explain_Window_Moulluse_Tear_6();
                                        main_Character = new Main_Character_Moulluse_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        ce31 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce31 = '" + ce31 + "'";
                                        MainActivity.database.execSQL(sql);
                                        character_Explain_DB[30] = 1;
//                                    game_thread.function_Skill_Laser_img();

                                    }
                                    main_Character.set_Tear(6);

                                }else{
                                    if(rand_Temp2 <= 50){

                                        if (rand_Temp3 <= 33) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_5();
                                            main_Character = new Main_Character_Shellfish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Soycrab_img();

                                        } else if (rand_Temp3 <= 66) {
                                            game_thread.function_Explain_Window_Fish_Tear_5();
                                            main_Character = new Main_Character_Fish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Teeth_mine2_img();

                                        } else {
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_5();
                                            main_Character = new Main_Character_Moulluse_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Fork_img();

                                        }
                                        main_Character.set_Tear(5);

                                    }else {

                                        if (rand_Temp3 <= 25) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_4();
                                            main_Character = new Main_Character_Shellfish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Crab_img();

                                        } else if (rand_Temp3 <= 50) {
                                            game_thread.function_Explain_Window_Fish_Tear_4();
                                            main_Character = new Main_Character_Fish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                                        } else if(rand_Temp3 <= 75){
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_4();
                                            main_Character = new Main_Character_Moulluse_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Butter_img();

                                        } else {
                                            game_thread.function_Explain_Window_Plankton_Tear_5();
                                            main_Character = new Main_Character_Plankton_5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                                        }
                                        main_Character.set_Tear(4);

                                    }

                                }
                            }



                            else if((main_Character.get_Tear() == 6) ){

                                //메모리 관리 기법
                                explain_Window_MainCharacker.recycle();
                                explain_Window_MainCharacker = null;

                                if(rand_Temp <= 40){

                                    if (rand_Temp3 <= 33) {
                                        game_thread.function_Explain_Window_Shellfish_Tear_7();
                                        main_Character = new Main_Character_Shellfish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[21] = 1;
                                        ce22 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce22 = '" + ce22 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_fry_img();

                                    } else if (rand_Temp3 <= 66) {
                                        game_thread.function_Explain_Window_Fish_Tear_7();
                                        main_Character = new Main_Character_Fish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[11] = 1;
                                        ce12 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce12 = '" + ce12 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_lightnign_img();

                                    } else {
                                        //메모리 관리
                                        game_thread.function_Explain_Window_Moulluse_Tear_7();
                                        main_Character = new Main_Character_Moulluse_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[31] = 1;
                                        ce32 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce32 = '" + ce32 + "'";
                                        MainActivity.database.execSQL(sql);
                                    }
                                    main_Character.set_Tear(7);

                                }else{
                                    if(rand_Temp2 <= 40){

                                        if (rand_Temp3 <= 33) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_6();
                                            main_Character = new Main_Character_Shellfish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Thorn2_img();

                                        } else if (rand_Temp3 <= 66) {
                                            game_thread.function_Explain_Window_Fish_Tear_6();
                                            main_Character = new Main_Character_Fish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                                        } else {
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_6();
                                            main_Character = new Main_Character_Moulluse_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Laser_img();
                                        }
                                        main_Character.set_Tear(6);

                                    }else {

                                        if (rand_Temp3 <= 33) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_5();
                                            main_Character = new Main_Character_Shellfish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Soycrab_img();

                                        } else if (rand_Temp3 <= 66) {
                                            game_thread.function_Explain_Window_Fish_Tear_5();
                                            main_Character = new Main_Character_Fish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Teeth_mine2_img();
                                        } else {
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_5();
                                            main_Character = new Main_Character_Moulluse_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Fork_img();
                                        }
                                        main_Character.set_Tear(5);

                                    }

                                }
                            }



                            else if((main_Character.get_Tear() == 7) ){

                                //메모리 관리 기법
                                explain_Window_MainCharacker.recycle();
                                explain_Window_MainCharacker = null;

                                if(rand_Temp <= 30){

                                    if (rand_Temp3 <= 33) {
                                        game_thread.function_Explain_Window_Shellfish_Tear_8();
                                        main_Character = new Main_Character_Shellfish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[22] = 1;
                                        ce23 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce23 = '" + ce23 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_Crab_img();

                                    } else if (rand_Temp3 <= 66) {
                                        game_thread.function_Explain_Window_Fish_Tear_8();
                                        main_Character = new Main_Character_Fish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[12] = 1;
                                        ce13 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce13 = '" + ce13 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_lightnign1_img();

                                    } else {
                                        //메모리 관리
                                        game_thread.function_Explain_Window_Moulluse_Tear_8();
                                        main_Character = new Main_Character_Moulluse_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[32] = 1;
                                        ce33 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce33 = '" + ce33 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_Boom_Poison_img();

                                    }
                                    main_Character.set_Tear(8);

                                }else{
                                    if(rand_Temp2 <= 30){

                                        if (rand_Temp3 <= 33) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_7();
                                            main_Character = new Main_Character_Shellfish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//                                        game_thread.function_Skill_fry_img();
                                        } else if (rand_Temp3 <= 66) {
                                            game_thread.function_Explain_Window_Fish_Tear_7();
                                            main_Character = new Main_Character_Fish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//                                        game_thread.function_Skill_lightnign_img();
                                        } else {
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_7();
                                            main_Character = new Main_Character_Moulluse_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        }
                                        main_Character.set_Tear(7);

                                    }else {

                                        if (rand_Temp3 <= 33) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_6();
                                            main_Character = new Main_Character_Shellfish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Thorn2_img();

                                        } else if (rand_Temp3 <= 66) {
                                            game_thread.function_Explain_Window_Fish_Tear_6();
                                            main_Character = new Main_Character_Fish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                                        } else {
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_6();
                                            main_Character = new Main_Character_Moulluse_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Laser_img();
                                        }
                                        main_Character.set_Tear(6);

                                    }

                                }
                            }


                            else if((main_Character.get_Tear() == 8) ){
                                //메모리 관리 기법
                                explain_Window_MainCharacker.recycle();
                                explain_Window_MainCharacker = null;

                                if(rand_Temp <= 20){

                                    if (rand_Temp3 <= 33) {
                                        game_thread.function_Explain_Window_Shellfish_Tear_9();
                                        main_Character = new Main_Character_Shellfish_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[23] = 1;
                                        ce24 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce24 = '" + ce24 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_Wave_img();

                                    } else if (rand_Temp3 <= 66) {
                                        game_thread.function_Explain_Window_Fish_Tear_9();
                                        main_Character = new Main_Character_Fish_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[13] = 1;
                                        ce14 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce14 = '" + ce14 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_Sea_Snake_img();

                                    } else {
                                        //메모리 관리
                                        game_thread.function_Explain_Window_Moulluse_Tear_9();
                                        main_Character = new Main_Character_Moulluse_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[33] = 1;
                                        ce34 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce34 = '" + ce34 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_wall_img();
                                    }
                                    main_Character.set_Tear(9);

                                }else{
                                    if(rand_Temp2 <= 20){

                                        if (rand_Temp3 <= 33) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_8();
                                            main_Character = new Main_Character_Shellfish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//                                        game_thread.function_Skill_Crab_img();
                                        } else if (rand_Temp3 <= 66) {
                                            game_thread.function_Explain_Window_Fish_Tear_8();
                                            main_Character = new Main_Character_Fish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//                                        game_thread.function_Skill_lightnign1_img();
                                        } else {
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_8();
                                            main_Character = new Main_Character_Moulluse_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_Boom_Poison_img();

                                        }
                                        main_Character.set_Tear(8);

                                    }else {

                                        if (rand_Temp3 <= 33) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_7();
                                            main_Character = new Main_Character_Shellfish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//                                        game_thread.function_Skill_fry_img();
                                        } else if (rand_Temp3 <= 66) {
                                            game_thread.function_Explain_Window_Fish_Tear_7();
                                            main_Character = new Main_Character_Fish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//                                        game_thread.function_Skill_lightnign_img();
                                        } else {
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_7();
                                            main_Character = new Main_Character_Moulluse_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        }
                                        main_Character.set_Tear(7);

                                    }

                                }
                            }



                            else if((main_Character.get_Tear() == 9) ){
                                //메모리 관리 기법
                                explain_Window_MainCharacker.recycle();
                                explain_Window_MainCharacker = null;

                                if(rand_Temp <= 10){

                                    if (rand_Temp3 <= 33) {
                                        game_thread.function_Explain_Window_Shellfish_Tear_10();
                                        main_Character = new Main_Character_Shellfish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[24] = 1;
                                        ce25 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce25 = '" + ce25 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_stomp_img();

                                    } else if (rand_Temp3 <= 66) {
                                        game_thread.function_Explain_Window_Fish_Tear_10();
                                        main_Character = new Main_Character_Fish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[14] = 1;
                                        ce15 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce15 = '" + ce15 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_Thorn_img();

                                    } else {
                                        //메모리 관리
                                        game_thread.function_Explain_Window_Moulluse_Tear_10();
                                        main_Character = new Main_Character_Moulluse_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                        character_Explain_DB[34] = 1;
                                        ce35 = 1;
                                        String sql = "UPDATE maincharacterinfo SET ce35 = '" + ce35 + "'";
                                        MainActivity.database.execSQL(sql);
//                                    game_thread.function_Skill_Poison123_img();

                                    }
                                    main_Character.set_Tear(10);


                                }else{
                                    if(rand_Temp2 <= 10){

                                        if (rand_Temp3 <= 33) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_9();
                                            main_Character = new Main_Character_Shellfish_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//                                        game_thread.function_Skill_Wave_img();
                                        } else if (rand_Temp3 <= 66) {
                                            game_thread.function_Explain_Window_Fish_Tear_9();
                                            main_Character = new Main_Character_Fish_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//                                        game_thread.function_Skill_Sea_Snake_img();
                                        } else {
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_9();
                                            main_Character = new Main_Character_Moulluse_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

//                                        game_thread.function_Skill_wall_img();
                                        }
                                        main_Character.set_Tear(9);

                                    }else {

                                        if (rand_Temp3 <= 33) {
                                            game_thread.function_Explain_Window_Shellfish_Tear_8();
                                            main_Character = new Main_Character_Shellfish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//                                        game_thread.function_Skill_Crab_img();
                                        } else if (rand_Temp3 <= 66) {
                                            game_thread.function_Explain_Window_Fish_Tear_8();
                                            main_Character = new Main_Character_Fish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//                                        game_thread.function_Skill_lightnign1_img();
                                        } else {
                                            //메모리 관리
                                            game_thread.function_Explain_Window_Moulluse_Tear_8();
                                            main_Character = new Main_Character_Moulluse_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//                                        game_thread.function_Skill_Boom_Poison_img();
                                        }
                                        main_Character.set_Tear(8);

                                    }

                                }
                            }else if((main_Character.get_Tear() == 10) ){

                                if (rand_Temp3 <= 33) {
                                    game_thread.function_Explain_Window_Shellfish_Tear_10();
                                    main_Character = new Main_Character_Shellfish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                    character_Explain_DB[24] = 1;
//                                    game_thread.function_Skill_stomp_img();

                                } else if (rand_Temp3 <= 66) {
                                    game_thread.function_Explain_Window_Fish_Tear_10();
                                    main_Character = new Main_Character_Fish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                    character_Explain_DB[14] = 1;
//                                    game_thread.function_Skill_Thorn_img();

                                } else {
                                    //메모리 관리
                                    game_thread.function_Explain_Window_Moulluse_Tear_10();
                                    main_Character = new Main_Character_Moulluse_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                                    character_Explain_DB[34] = 1;
//                                    game_thread.function_Skill_Poison123_img();

                                }
                                main_Character.set_Tear(10);

                            }



                            //매인 캐릭터가 진화할때마다 메인 캐릭터의 이미지를 변화 시켜준다.
                            Init_Main_Character_Image(_context, main_Character);

                            main_Character.set_Main_Character_Mode_Status_Init();
                            main_Character.set_Hp_Init();

                            m_Run_True();   //게임 재게
                            revolution_Flag_Confirm = true;

                            //sql 라이트
                            String sql = "UPDATE maincharacterinfo SET money = '" + money + "', ruby = '" + ruby + "'";
                            MainActivity.database.execSQL(sql);
                            editor.putString("realmoney", String.valueOf(money));
                            editor.commit();
//                        }

                        }
                        else if (confirm_Button_card_3.touch(touchx, touchy)) {

                            //루비로 사기
                            if(ruby >= card_Ruby) {
                                if(main_Character.get_Tear() != 10) {
                                    ruby = ruby - card_Ruby;
                                }
                            set_Ruby_Revolution();

                            //매인 캐릭터가 진화할때마다 메인 캐릭터의 이미지를 변화 시켜준다.
                            Init_Main_Character_Image(_context, main_Character);

                            main_Character.set_Main_Character_Mode_Status_Init();
                            main_Character.set_Hp_Init();

                            m_Run_True();   //게임 재게
                            revolution_Flag_Confirm = true;

                                //sql 라이트
                                String sql = "UPDATE maincharacterinfo SET money = '" + money + "', ruby = '" + ruby + "'";
                                MainActivity.database.execSQL(sql);
                                editor.putString("realmoney", String.valueOf(money));
                                editor.commit();


                            }else {
//                               루비가 부족할 경우

                                gameActivity.set_Vibrator();
                                soundPool.play(sound_Effect[17], pop_Touch, pop_Touch, 0, 0, 1.0F);

                            }
                        }

                        break;


                }
            } else { //진화 창 떳을때 확인 버튼


                //진화의 버튼은 클릭해야 열린다.
                if (revolution_Button.touch(touchx, touchy) && event.getAction() == MotionEvent.ACTION_DOWN) {
                    revolution_Button_Activation_Down = true;
                } else if (revolution_Button.touch(touchx, touchy) && event.getAction() == MotionEvent.ACTION_UP) {
                    revolution_Button_Activation_Up = true;
                    soundPool.play(sound_Effect[16], pop_Drag, pop_Drag, 0, 0, 1.0F);
                }

                if (!revolution_Button.touch(touchx, touchy)) {
                    revolution_Button_Activation_Down = false;
                    revolution_Button_Activation_Up = false;
                }

                if (revolution_Button_Activation_Down && revolution_Button_Activation_Up) {
                    /**
                     * 경험치 다 차면 진화의 버튼 활성화, 진화의 버튼 누르면 진화의 창 등장
                     */
                    day_Count--;
                    revolution_Button_Activation = true;
                    return false;
                }


                //게임 터치 이벤트
                if (event.getAction() == MotionEvent.ACTION_DOWN) {           //손가락이 눌렸을때.
                    touch_Check = 0;   //터치 체크 상태가 5이하 일때 터치라 판정.

                }

                else if (event.getAction() == MotionEvent.ACTION_UP) {      //때졌을때.


                    try {

//            draw_Main_Character_Touch();    //메인캐릭터 그리기
                        main_Character.set_Attack_State_True(); //메인캐릭터 공격


                        if (touch_Check <= 5) {
                            //퍼즈 컨트롤
                            //if(pause_Effect(event.getX(), event.getY())){


                            if (ground_Hit_Chose(touchx, touchy, false, 0)) { //성게 삭제
                                //달팽이 터치 팝 이벤트

//                    } else if(ground_Hit_Chose(touchx, touchy, ground_Touch_Snail, false)){//달팽이 삭제
//
//                    }
//                    else if (ground_Hit_Chose(touchx, touchy, ground_Touch_Crocodile, false)) { //악어 삭제
//
//                    }
                            } else {
                                //문어 공격 속도로 제어한다. 쿨타임 효과
                                if (main_Character.get_Attack_Cool_time() == 0) {

                                    if (!fish_Hit_Chose(1)) {      //기본 물고기 터치 확인 == 1
                                        //기본 물고기가 존재하지 않을때
                                        fish_Hit_Chose(10);         //해파리 타격 메인 캐릭터 Hp 감소 해야한다.


                                    } else {

                                    }
                                    main_Character.set_Attack_Cool_Time();
                                }
                            }
                        }


                    } catch (Exception e) {
                        Log.e("e", "ioeErrorTouch");
                    }


                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {    //드래그 중일때


                    try {


                        touch_Check++;
                        drag_Action_Move++;

                        if (ground_Hit_Chose(touchx, touchy, true, drag_Action_Move)) {
                            // 그라운드

                        } else if (drag_Action_Move > 3) {
                            fish_Hit_Chose(2);                                  //드래그 물고기
                        }

                        if (drag_Action_Move > 3) {
                            drag_Action_Move = 0;
                        }

//                    if (drag_Action_Move > 30 ) {
//                        if (ground_Hit_Chose(touchx, touchy, ground_Drag_Crab, true)) {    //꽃게 삭제
//
//                        }else if(ground_Hit_Chose(touchx, touchy, ground_Drag_Clam, true)){ //조개 삭제
//
//                        }
//                        else if(ground_Hit_Chose(touchx, touchy, land_Mark, true)){ //랜드마크 드래그
//
//                        }else {
//                            fish_Hit_Chose(2);                                  //드래그 물고기
//                        }
//
//                        drag_Action_Move = 0;
//                    }


                    } catch (IndexOutOfBoundsException ioe) {
                        Log.e("e", "ioeErrorDrag");
                    }

                }



            }




        }catch (Exception e){
            Log.e("onTouchEvent","onTouchEvent");

            //새로운 몬스터 출연 창 에서 확인 버튼 이외것 누르면 에러 발생

//            Log.e("a",e.getMessage());
//            Log.e("a",e.toString());
        }


        return true;

    }



    //********************************************************************************************//
    //광고 진화.
    public void set_Ruby_Revolution(){
            //진화 하기

            int rand_Temp3 = random.nextInt(100);   //종류 선택
            //진화 확률 = 0티어 플랑크톤 일때 100 %
            //1티어 일때 90%, 2티어 80%, 3티어 70%, 4티어 60%, 5티어 50%, 6티어 40%, 7티어 30%, 6티어 20%, 9티어, 10%

            if((main_Character.get_Tear() == 0)){

//            Log.e("a","asdasdsad@@@");

            //메모리 관리 기법

            //0티어 진화
            if (rand_Temp3 <= 25) {
            game_thread.function_Explain_Window_Shellfish_Tear_1();
            main_Character = new Main_Character_Shellfish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                ce16 = 1;

            character_Explain_DB[15] = 1;

            } else if (rand_Temp3 <= 50) {
            game_thread.function_Explain_Window_Fish_Tear_1();
            main_Character = new Main_Character_Fish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                ce6 = 1;
            character_Explain_DB[5] = 1;

            } else if(rand_Temp3 <= 75){
            //메모리 관리
            game_thread.function_Explain_Window_Moulluse_Tear_1();
            main_Character = new Main_Character_Moulluse_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                ce26 = 1;
            character_Explain_DB[25] = 1;

            } else {
            game_thread.function_Explain_Window_Plankton_Tear_2();
            main_Character = new Main_Character_Plankton_2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                ce2 = 1;
            character_Explain_DB[1] = 1;

            }
//            Log.e("a","asdasdsad###");

            main_Character.set_Tear(1);
            }

            else if((main_Character.get_Tear() == 1) ){

            //메모리 관리 기법
            explain_Window_MainCharacker.recycle();
            explain_Window_MainCharacker = null;

            if (rand_Temp3 <= 25) {
            game_thread.function_Explain_Window_Shellfish_Tear_2();
            main_Character = new Main_Character_Shellfish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                ce17 = 1;
            character_Explain_DB[16] = 1;

            } else if (rand_Temp3 <= 50) {
            game_thread.function_Explain_Window_Fish_Tear_2();
            main_Character = new Main_Character_Fish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

    //                                    game_thread.function_Skill_Thorn_img();
            character_Explain_DB[6] = 1;
                ce7 = 1;
            } else if(rand_Temp3 <= 75){
            //메모리 관리
            game_thread.function_Explain_Window_Moulluse_Tear_2();
            main_Character = new Main_Character_Moulluse_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[26] = 1;
                ce27 = 1;
            } else {
            game_thread.function_Explain_Window_Plankton_Tear_3();
            main_Character = new Main_Character_Plankton_3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[2] = 1;
                ce3 = 1;
            }
            main_Character.set_Tear(2);



            }


            else if((main_Character.get_Tear() == 2) ){

            //메모리 관리 기법
            explain_Window_MainCharacker.recycle();
            explain_Window_MainCharacker = null;

            if (rand_Temp3 <= 25) {
            game_thread.function_Explain_Window_Shellfish_Tear_3();
            main_Character = new Main_Character_Shellfish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[17] = 1;
                ce18 = 1;

            } else if (rand_Temp3 <= 50) {
            game_thread.function_Explain_Window_Fish_Tear_3();
            main_Character = new Main_Character_Fish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[7] = 1;
                ce8 = 1;
    //                                    game_thread.function_Skill_Teeth_mine_img();

            } else if(rand_Temp3 <= 75){
            //메모리 관리
            game_thread.function_Explain_Window_Moulluse_Tear_3();
            main_Character = new Main_Character_Moulluse_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[27] = 1;
                ce28 = 1;
    //                                    game_thread.function_Skill_Slow_Cloud_img();

            } else {
            game_thread.function_Explain_Window_Plankton_Tear_4();
            main_Character = new Main_Character_Plankton_4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[3] = 1;
                ce4 = 1;
            }
            main_Character.set_Tear(3);



            }


            else if((main_Character.get_Tear() == 3) ){

            //메모리 관리 기법
            explain_Window_MainCharacker.recycle();
            explain_Window_MainCharacker = null;


            if (rand_Temp3 <= 25) {
            game_thread.function_Explain_Window_Shellfish_Tear_4();
            main_Character = new Main_Character_Shellfish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[18] = 1;
                ce19 = 1;
    //                                    game_thread.function_Skill_Crab_img();

            } else if (rand_Temp3 <= 50) {
            game_thread.function_Explain_Window_Fish_Tear_4();
            main_Character = new Main_Character_Fish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[8] = 1;
                ce9 = 1;
    //                                    game_thread.function_Skill_earthquake_img();

            } else if(rand_Temp3 <= 75){
            //메모리 관리
            game_thread.function_Explain_Window_Moulluse_Tear_4();
            main_Character = new Main_Character_Moulluse_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[28] = 1;
                ce29 = 1;
    //                                    game_thread.function_Skill_Butter_img();

            } else {
            game_thread.function_Explain_Window_Plankton_Tear_5();
            main_Character = new Main_Character_Plankton_5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[4] = 1;
                ce5 = 1;
            }
            main_Character.set_Tear(4);


            }


            else if((main_Character.get_Tear() == 4) ){

            //메모리 관리 기법
            explain_Window_MainCharacker.recycle();
            explain_Window_MainCharacker = null;


            if (rand_Temp3 <= 33) {
            game_thread.function_Explain_Window_Shellfish_Tear_5();
            main_Character = new Main_Character_Shellfish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[19] = 1;
                ce20 = 1;
    //                                    game_thread.function_Skill_Soycrab_img();

            } else if (rand_Temp3 <= 66) {
            game_thread.function_Explain_Window_Fish_Tear_5();
            main_Character = new Main_Character_Fish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[9] = 1;
                ce10 = 1;
    //                                    game_thread.function_Skill_Teeth_mine2_img();

            } else {
            //메모리 관리
            game_thread.function_Explain_Window_Moulluse_Tear_5();
            main_Character = new Main_Character_Moulluse_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[29] = 1;
                ce30 = 1;
    //                                    game_thread.function_Skill_Fork_img();

            }
            main_Character.set_Tear(5);


            }


            else if((main_Character.get_Tear() == 5) ){

            //메모리 관리 기법
            explain_Window_MainCharacker.recycle();
            explain_Window_MainCharacker = null;


            if (rand_Temp3 <= 33) {
            game_thread.function_Explain_Window_Shellfish_Tear_6();
            main_Character = new Main_Character_Shellfish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[20] = 1;
                ce21 = 1;
    //                                    game_thread.function_Skill_Thorn2_img();

            } else if (rand_Temp3 <= 66) {
            game_thread.function_Explain_Window_Fish_Tear_6();
            main_Character = new Main_Character_Fish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[10] = 1;
                ce11 = 1;

            } else {
            //메모리 관리
            game_thread.function_Explain_Window_Moulluse_Tear_6();
            main_Character = new Main_Character_Moulluse_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[30] = 1;
                ce31 = 1;
    //                                    game_thread.function_Skill_Laser_img();

            }
            main_Character.set_Tear(6);


            }



            else if((main_Character.get_Tear() == 6) ){

            //메모리 관리 기법
            explain_Window_MainCharacker.recycle();
            explain_Window_MainCharacker = null;


            if (rand_Temp3 <= 33) {
            game_thread.function_Explain_Window_Shellfish_Tear_7();
            main_Character = new Main_Character_Shellfish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[21] = 1;
                ce22 = 1;
    //                                    game_thread.function_Skill_fry_img();

            } else if (rand_Temp3 <= 66) {
            game_thread.function_Explain_Window_Fish_Tear_7();
            main_Character = new Main_Character_Fish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[11] = 1;
                ce12 = 1;
    //                                    game_thread.function_Skill_lightnign_img();

            } else {
            //메모리 관리
            game_thread.function_Explain_Window_Moulluse_Tear_7();
            main_Character = new Main_Character_Moulluse_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[31] = 1;
                ce32 = 1;

            }
            main_Character.set_Tear(7);


            }



            else if((main_Character.get_Tear() == 7) ){

            //메모리 관리 기법
            explain_Window_MainCharacker.recycle();
            explain_Window_MainCharacker = null;


            if (rand_Temp3 <= 33) {
            game_thread.function_Explain_Window_Shellfish_Tear_8();
            main_Character = new Main_Character_Shellfish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[22] = 1;
                ce23 = 1;
    //                                    game_thread.function_Skill_Crab_img();

            } else if (rand_Temp3 <= 66) {
            game_thread.function_Explain_Window_Fish_Tear_8();
            main_Character = new Main_Character_Fish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[12] = 1;
                ce13 = 1;
    //                                    game_thread.function_Skill_lightnign1_img();

            } else {
            //메모리 관리
            game_thread.function_Explain_Window_Moulluse_Tear_8();
            main_Character = new Main_Character_Moulluse_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[32] = 1;
                ce33 = 1;
    //                                    game_thread.function_Skill_Boom_Poison_img();

            }
            main_Character.set_Tear(8);


            }


            else if((main_Character.get_Tear() == 8) ){
            //메모리 관리 기법
            explain_Window_MainCharacker.recycle();
            explain_Window_MainCharacker = null;

            if (rand_Temp3 <= 33) {
            game_thread.function_Explain_Window_Shellfish_Tear_9();
            main_Character = new Main_Character_Shellfish_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[23] = 1;
                ce24 = 1;
    //                                    game_thread.function_Skill_Wave_img();

            } else if (rand_Temp3 <= 66) {
            game_thread.function_Explain_Window_Fish_Tear_9();
            main_Character = new Main_Character_Fish_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[13] = 1;
                ce14 = 1;
    //                                    game_thread.function_Skill_Sea_Snake_img();

            } else {
            //메모리 관리
            game_thread.function_Explain_Window_Moulluse_Tear_9();
            main_Character = new Main_Character_Moulluse_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[33] = 1;
                ce34 = 1;
    //                                    game_thread.function_Skill_wall_img();
            }
            main_Character.set_Tear(9);


            }



            else if((main_Character.get_Tear() == 9) ){
            //메모리 관리 기법
            explain_Window_MainCharacker.recycle();
            explain_Window_MainCharacker = null;

            if (rand_Temp3 <= 33) {
            game_thread.function_Explain_Window_Shellfish_Tear_10();
            main_Character = new Main_Character_Shellfish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[24] = 1;
                ce25 = 1;
    //                                    game_thread.function_Skill_stomp_img();

            } else if (rand_Temp3 <= 66) {
            game_thread.function_Explain_Window_Fish_Tear_10();
            main_Character = new Main_Character_Fish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[14] = 1;
                ce15 = 1;
    //                                    game_thread.function_Skill_Thorn_img();

            } else {
            //메모리 관리
            game_thread.function_Explain_Window_Moulluse_Tear_10();
            main_Character = new Main_Character_Moulluse_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
            character_Explain_DB[34] = 1;
                ce35 = 1;
    //                                    game_thread.function_Skill_Poison123_img();

            }
            main_Character.set_Tear(10);

            }else if((main_Character.get_Tear() == 10) ){

                if (rand_Temp3 <= 33) {
                    game_thread.function_Explain_Window_Shellfish_Tear_10();
                    main_Character = new Main_Character_Shellfish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                    character_Explain_DB[24] = 1;
                    ce35 = 1;
//                                    game_thread.function_Skill_stomp_img();

                } else if (rand_Temp3 <= 66) {
                    game_thread.function_Explain_Window_Fish_Tear_10();
                    main_Character = new Main_Character_Fish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                    character_Explain_DB[14] = 1;
                    ce25 = 1;
//                                    game_thread.function_Skill_Thorn_img();

                } else {
                    //메모리 관리
                    game_thread.function_Explain_Window_Moulluse_Tear_10();
                    main_Character = new Main_Character_Moulluse_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                    character_Explain_DB[34] = 1;
                    ce35 = 1;
//                                    game_thread.function_Skill_Poison123_img();

                }
                main_Character.set_Tear(10);

            }


        //sql 라이트
        String sql = "UPDATE maincharacterinfo SET money = '" + money + "', ruby = '" + ruby + "'";
        MainActivity.database.execSQL(sql);
        editor.putString("realmoney", String.valueOf(money));
        editor.commit();

            }



    private boolean extraction_Button_Flag = true;
    private boolean get_Extraction_Button_Flag(Main_Character main_character){

        extraction_Button_Flag = true;

        if(main_character instanceof Main_Character_Plankton_1 || main_character instanceof Main_Character_Plankton_2 || main_character instanceof Main_Character_Plankton_3 || main_character instanceof Main_Character_Plankton_4 || main_character instanceof Main_Character_Plankton_5){
            extraction_Button_Flag = false;
        }



        if(main_character instanceof Main_Character_Fish_Tear1){
            extraction_Button_Flag = false;
        }else if(main_character instanceof Main_Character_Fish_Tear2){
            if(skill_Fish_Extract_Nomar[1]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Fish_Tear3){
            if(skill_Fish_Extract_Nomar[2]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Fish_Tear4){
            if(skill_Fish_Extract_Nomar[3]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Fish_Tear5){
            if(skill_Fish_Extract_Nomar[4]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Fish_Tear6){
            if(skill_Fish_Extract_Nomar[5]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Fish_Tear7){
            if(skill_Fish_Extract_Nomar[6]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Fish_Tear8){
            if(skill_Fish_Extract_Nomar[7]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Fish_Tear9){
            if(skill_Fish_Extract_Nomar[8]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Fish_Tear10){
            if(skill_Fish_Extract_Nomar[9]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear1){
            extraction_Button_Flag = false;
        }else if(main_character instanceof Main_Character_Shellfish_Tear2){
            if(skill_Shellfish_Extract_Nomar[1]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear3){
            if(skill_Shellfish_Extract_Nomar[2]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear4){
            if(skill_Shellfish_Extract_Nomar[3]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear5){
            if(skill_Shellfish_Extract_Nomar[4]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear6){
            if(skill_Shellfish_Extract_Nomar[5]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear7){
            if(skill_Shellfish_Extract_Nomar[6]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear8){
            if(skill_Shellfish_Extract_Nomar[7]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear9){
            if(skill_Shellfish_Extract_Nomar[8]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear10){
            if(skill_Shellfish_Extract_Nomar[9]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear1){
            extraction_Button_Flag = false;
        }else if(main_character instanceof Main_Character_Moulluse_Tear2){
            extraction_Button_Flag = false;
        }else if(main_character instanceof Main_Character_Moulluse_Tear3){
            if(skill_Mollus_Extract_Nomar[2]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear4){
            if(skill_Mollus_Extract_Nomar[3]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear5){
            if(skill_Mollus_Extract_Nomar[4]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear6){
            if(skill_Mollus_Extract_Nomar[5]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear7){
            if(skill_Mollus_Extract_Nomar[6]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear8){
            if(skill_Mollus_Extract_Nomar[7]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear9){
            if(skill_Mollus_Extract_Nomar[8]){
                extraction_Button_Flag = false;
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear10){
            if(skill_Mollus_Extract_Nomar[9]){
                extraction_Button_Flag = false;
            }
        }


        return extraction_Button_Flag;
    }
    //메인캐릭터 종류 알아와서 추출에 대한 정보
    private void set_Maincharacter_Skill_Set(Main_Character main_character, boolean flag){

        //물고기 티어
        if(main_character instanceof Main_Character_Fish_Tear2){
            skill_Fish_Extract_Nomar[1] = true;
            if(flag){ //영구추출
                ftb2 = 1;
                setIntent[40] = 1;
                String sql = "UPDATE maincharacterinfo SET ftb2 = '" + ftb2 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Fish_Tear3){
            skill_Fish_Extract_Nomar[2] = true;
            if(flag){ //영구추출
                ftb3 = 1;
                setIntent[41] = 1;
                String sql = "UPDATE maincharacterinfo SET ftb3 = '" + ftb3 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Fish_Tear4){
            skill_Fish_Extract_Nomar[3] = true;
            if(flag){ //영구추출
                ftb4 = 1;
                setIntent[42] = 1;
                String sql = "UPDATE maincharacterinfo SET ftb4 = '" + ftb4 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Fish_Tear5){
            skill_Fish_Extract_Nomar[4] = true;
            if(flag){ //영구추출
                ftb5 = 1;
                setIntent[43] = 1;
                String sql = "UPDATE maincharacterinfo SET ftb5 = '" + ftb5 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Fish_Tear6){
            skill_Fish_Extract_Nomar[5] = true;
            if(flag){ //영구추출
                ftb6 = 1;
                setIntent[44] = 1;
                String sql = "UPDATE maincharacterinfo SET ftb6 = '" + ftb6 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Fish_Tear7){
            skill_Fish_Extract_Nomar[6] = true;
            if(flag){ //영구추출
                ftb7 = 1;
                setIntent[45] = 1;
                String sql = "UPDATE maincharacterinfo SET ftb7 = '" + ftb7 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Fish_Tear8){
            skill_Fish_Extract_Nomar[7] = true;
            if(flag){ //영구추출
                ftb8 = 1;
                setIntent[46] = 1;
                String sql = "UPDATE maincharacterinfo SET ftb8 = '" + ftb8 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Fish_Tear9){
            skill_Fish_Extract_Nomar[8] = true;
            if(flag){ //영구추출
                ftb9 = 1;
                setIntent[47] = 1;
                String sql = "UPDATE maincharacterinfo SET ftb9 = '" + ftb9 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Fish_Tear10){
            skill_Fish_Extract_Nomar[9] = true;
            if(flag){ //영구추출
                ftb10 = 1;
                setIntent[48] = 1;
                String sql = "UPDATE maincharacterinfo SET ftb10 = '" + ftb10 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear2){
            skill_Shellfish_Extract_Nomar[1] = true;
            if(flag){ //영구추출
                stb2 = 1;
                setIntent[50] = 1;
                String sql = "UPDATE maincharacterinfo SET stb2 = '" + stb2 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear3){
            skill_Shellfish_Extract_Nomar[2] = true;
            if(flag){ //영구추출
                stb3 = 1;
                setIntent[51] = 1;
                String sql = "UPDATE maincharacterinfo SET stb3 = '" + stb3 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear4){
            skill_Shellfish_Extract_Nomar[3] = true;
            if(flag){ //영구추출
                stb4 = 1;
                setIntent[52] = 1;
                String sql = "UPDATE maincharacterinfo SET stb4 = '" + stb4 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear5){
            skill_Shellfish_Extract_Nomar[4] = true;
            if(flag){ //영구추출
                stb5 = 1;
                setIntent[53] = 1;
                String sql = "UPDATE maincharacterinfo SET stb5 = '" + stb5 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear6){
            skill_Shellfish_Extract_Nomar[5] = true;
            if(flag){ //영구추출
                stb6 = 1;
                setIntent[54] = 1;
                String sql = "UPDATE maincharacterinfo SET stb6 = '" + stb6 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear7){
            skill_Shellfish_Extract_Nomar[6] = true;
            if(flag){ //영구추출
                stb7 = 1;
                setIntent[55] = 1;
                String sql = "UPDATE maincharacterinfo SET stb7 = '" + stb7 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear8){
            skill_Shellfish_Extract_Nomar[7] = true;
            if(flag){ //영구추출
                stb8 = 1;
                setIntent[56] = 1;
                String sql = "UPDATE maincharacterinfo SET stb8 = '" + stb8 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear9){
            skill_Shellfish_Extract_Nomar[8] = true;
            if(flag){ //영구추출
                stb9 = 1;
                setIntent[57] = 1;
                String sql = "UPDATE maincharacterinfo SET stb9 = '" + stb9 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Shellfish_Tear10){
            skill_Shellfish_Extract_Nomar[9] = true;
            if(flag){ //영구추출
                stb10 = 1;
                setIntent[58] = 1;
                String sql = "UPDATE maincharacterinfo SET stb10 = '" + stb10 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear3){
            skill_Mollus_Extract_Nomar[2] = true;
            if(flag){ //영구추출
                mtb3 = 1;
                setIntent[61] = 1;
                String sql = "UPDATE maincharacterinfo SET mtb3 = '" + mtb3 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear4){
            skill_Mollus_Extract_Nomar[3] = true;
            if(flag){ //영구추출
                mtb4 = 1;
                setIntent[62] = 1;
                String sql = "UPDATE maincharacterinfo SET mtb4 = '" + mtb4 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear5){
            skill_Mollus_Extract_Nomar[4] = true;
            if(flag){ //영구추출
                mtb5 = 1;
                setIntent[63] = 1;
                String sql = "UPDATE maincharacterinfo SET mtb5 = '" + mtb5 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear6){
            skill_Mollus_Extract_Nomar[5] = true;
            if(flag){ //영구추출
                mtb6 = 1;
                setIntent[64] = 1;
                String sql = "UPDATE maincharacterinfo SET mtb6 = '" + mtb6 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear7){
            skill_Mollus_Extract_Nomar[6] = true;
            if(flag){ //영구추출
                mtb7 = 1;
                setIntent[65] = 1;
                String sql = "UPDATE maincharacterinfo SET mtb7 = '" + mtb7 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear8){
            skill_Mollus_Extract_Nomar[7] = true;
            if(flag){ //영구추출
                mtb8 = 1;
                setIntent[66] = 1;
                String sql = "UPDATE maincharacterinfo SET mtb8 = '" + mtb8 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear9){
            skill_Mollus_Extract_Nomar[8] = true;
            if(flag){ //영구추출
                mtb9 = 1;
                setIntent[67] = 1;
                String sql = "UPDATE maincharacterinfo SET mtb9 = '" + mtb9 + "'";
                MainActivity.database.execSQL(sql);
            }
        }else if(main_character instanceof Main_Character_Moulluse_Tear10){
            skill_Mollus_Extract_Nomar[9] = true;
            if(flag){ //영구추출
                mtb10 = 1;
                setIntent[68] = 1;
                String sql = "UPDATE maincharacterinfo SET mtb10 = '" + mtb10 + "'";
                MainActivity.database.execSQL(sql);
            }
        }

    }




    int day_Count = 0;
    int day_Count_View = 0;

    //설명창 띄어주기 위함.
    boolean first_Explain = false;

    //가장 처름 설명서

    boolean first_Text_Explain_Flag = false;
    boolean tutorial_Flag = false;
    int first_Text_Explain_Index = 0;

    boolean explain[] = new boolean[50];
    double setIntent[] = new double[100];

    //몬스터 도감
    int monster_Explain_Db[] = new int[20];
    //캐릭터 도감
    int character_Explain_DB[] = new int[35];

    DecimalFormat df = new DecimalFormat("#,##0");
    /**
     * 오버라이드 된것 시작과 동시에 구성, 교체
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //Log.i("[뷰]", "구성");
//        Log.e("surfaceCreated","surfaceCreated");

        Intent intent = ((Activity)_context).getIntent();
        setIntent = intent.getDoubleArrayExtra("set");


//        Log.e("surfaceCreated","1");



        ruby = setIntent[0];
        money = setIntent[1];

//        money = 10000000000f;
//        ruby = 50000;



        character_Randmark_Damege = setIntent[2];
        character_Drag_Damege = setIntent[3];
        bird = setIntent[4];

//        character_Randmark_Damege = 30000000;
//        character_Drag_Damege = 1000;


        urchinresistance = setIntent[5];
        lightningresistance = setIntent[6];
        crocodileresistance = setIntent[7];
        steel = setIntent[8];

        //스킬 확률 셋팅
        ft1 = setIntent[9];     ft2 = setIntent[10];     ft3 = setIntent[11];     ft4 = setIntent[12];     ft5 = setIntent[13];     ft6 = setIntent[14];     ft7 = setIntent[15];     ft8 = setIntent[16];     ft9 = setIntent[17];   ft10 = setIntent[18];

        st1 = setIntent[19];    st2 = setIntent[20];    st3 = setIntent[21];  st4 = setIntent[22];   st5 = setIntent[23];     st6 = setIntent[24];      st7 = setIntent[25];     st8 = setIntent[26];  st9 = setIntent[27];   st10 = setIntent[28];

        mt1 = setIntent[29];
        mt2 = setIntent[30];
        mt3 = setIntent[31];
        mt4 = setIntent[32];
        mt5 = setIntent[33];
        mt6 = setIntent[34];
        mt7 = setIntent[35];
        mt8 = setIntent[36];
        mt9 = setIntent[37];
        mt10 = setIntent[38];

        //영구추출 스킬 셋팅
        ftb1 = setIntent[39];     ftb2 = setIntent[40];     ftb3 = setIntent[41];     ftb4 = setIntent[42];     ftb5 = setIntent[43];     ftb6 = setIntent[44];     ftb7 = setIntent[45];     ftb8 = setIntent[46];     ftb9 = setIntent[47];   ftb10 = setIntent[48];

        stb1 = setIntent[49];    stb2 = setIntent[50];    stb3 = setIntent[51];  stb4 = setIntent[52];   stb5 = setIntent[53];     stb6 = setIntent[54];      stb7 = setIntent[55];     stb8 = setIntent[56];  stb9 = setIntent[57];   stb10 = setIntent[58];

        mtb1 = setIntent[59];
        mtb2 = setIntent[60];
        mtb3 = setIntent[61];
        mtb4 = setIntent[62];
        mtb5 = setIntent[63];
        mtb6 = setIntent[64];
        mtb7 = setIntent[65];
        mtb8 = setIntent[66];
        mtb9 = setIntent[67];
        mtb10 = setIntent[68];








//        Log.e("surfaceCreated","2");

        //몬스터 설명창
        //몬스터 설명창
        int monster_Explain_Db_Temp = 0;
        for(int i=69; i<89; i++){
            monster_Explain_Db[monster_Explain_Db_Temp] = (int)setIntent[i];
//            Log.e("@", "setIntent = " + monster_Explain_Db[monster_Explain_Db_Temp] + " , " + monster_Explain_Db_Temp);
            monster_Explain_Db_Temp++;
        }
//        Log.e("surfaceCreated","3");
//        for(int i=0; i<setIntent.length; i++){
//            Log.e("a",setIntent[i] + "@" + i);
//        }

        int character_Explain_DB_Temp = 0;
        for(int i = 89; i<124; i++){
            character_Explain_DB[character_Explain_DB_Temp] = (int)setIntent[i];
            character_Explain_DB_Temp++;
        }
//        Log.e("surfaceCreated","4");
//
//        money = 100;

        soundPool.release();
//        Log.e("surfaceCreated","5");

        soundPool = new SoundPool(40, AudioManager.STREAM_ALARM, 0); //사운드 앞에 1은 하나만 가져다 놓겠다는 뜻. 나중에 추가 요망
//        Log.e("surfaceCreated","6");

        //소리 로드
        sound_Effect[0] = soundPool.load(_context, R.raw.fish_default_sound_1, 1);      //팝1
        sound_Effect[1] = soundPool.load(_context, R.raw.fish_default_sound_2, 1);      //팝2
        sound_Effect[2] = soundPool.load(_context, R.raw.drag_sound_1, 1);      //드래그1
        sound_Effect[3] = soundPool.load(_context, R.raw.drag_sound_2, 1);      //드래그2

        //소리 상어 친구 부르기
        sound_Effect[4] = soundPool.load(_context, R.raw.friend_shark_roar, 1);
        sound_Effect[5] = soundPool.load(_context, R.raw.friend_shark_effect_1, 1);
        sound_Effect[6] = soundPool.load(_context, R.raw.friend_shark_effect_2, 1);
        sound_Effect[7] = soundPool.load(_context, R.raw.friend_shark_effect_3, 1);

        sound_Effect[8] = soundPool.load(_context, R.raw.effect_window_sound, 1);   //설명창 등장 사운드
        sound_Effect[9] = soundPool.load(_context, R.raw.skill_crabb_sound, 1);     //꽃게 스킬 소리
        sound_Effect[10] = soundPool.load(_context, R.raw.skill_laser_sound, 1);     //레이저 스킬 소리

        sound_Effect[11] = soundPool.load(_context, R.raw.effect_wave_pop, 1);     //파도 팝 소리

        sound_Effect[12] = soundPool.load(_context, R.raw.drag_steel, 1);     //강철 참돔 드래그 소리

        sound_Effect[13] = soundPool.load(_context, R.raw.wave_1, 1);     //
        sound_Effect[14] = soundPool.load(_context, R.raw.wave_2, 1);     //

        sound_Effect[15] = soundPool.load(_context, R.raw.warning, 1);     ///경고음

        //진화 버튼 눌렀을때
        sound_Effect[16] = soundPool.load(_context, R.raw.up, 1);     ///경고음

        //실패음
        sound_Effect[17] = soundPool.load(_context, R.raw.fail, 1);     ///경고음

        sound_Effect[18] = soundPool.load(_context, R.raw.thorn_1, 1);     //가시
        sound_Effect[19] = soundPool.load(_context, R.raw.fry, 1);     //기름 폭발
        sound_Effect[20] = soundPool.load(_context, R.raw.stomp, 1);     //스톰프
        sound_Effect[21] = soundPool.load(_context, R.raw.snake, 1);     //바다뱀
        sound_Effect[22] = soundPool.load(_context, R.raw.poison_boom, 1);     //바다뱀
        sound_Effect[23] = soundPool.load(_context, R.raw.wall, 1);     //벽소환
        sound_Effect[24] = soundPool.load(_context, R.raw.poison, 1);     //포이즌
        sound_Effect[25] = soundPool.load(_context, R.raw.poison_cloud, 1);     //포이즌 구름
        sound_Effect[26] = soundPool.load(_context, R.raw.fork, 1);     //포크
        sound_Effect[27] = soundPool.load(_context, R.raw.butter, 1);     //버터
        sound_Effect[28] = soundPool.load(_context, R.raw.wave, 1);     //파도
        sound_Effect[29] = soundPool.load(_context, R.raw.thunder_1, 1);     //번개
        sound_Effect[30] = soundPool.load(_context, R.raw.thunder_2, 1);     //번개2
        sound_Effect[31] = soundPool.load(_context, R.raw.earthquake, 1);     //지진

        sound_Effect[32] = soundPool.load(_context, R.raw.teeth1, 1);     //이빨 지뢰1
        sound_Effect[33] = soundPool.load(_context, R.raw.teeth2, 1);     //이빨 지뢰2



        sound_Effect[49] = soundPool.load(_context, R.raw.background_music_1, 1);     ///배경음


        //가장 처름 설명서
        //튜토리얼
        //튜토리얼


        //최고 점수


    }

    public int get_Ruby_Count(){
        return (int)ruby;
    }
    public void set_Ruby_Minus(){

        ruby--;
        //sql 라이트
        String sql = "UPDATE maincharacterinfo SET money = '" + money + "', ruby = '" + ruby + "'";
//        Log.e("@","ruby = " + ruby);
        MainActivity.database.execSQL(sql);
        editor.putString("realmoney", String.valueOf(money));
        editor.commit();
    }



    TimerTask fish_Maker, stage_Day;
    int pref_Class = 0;

    //monster_Explain_Db
    public int[] monster_Explain_Get(){
        return monster_Explain_Db;
    }
    public int[] character_Explain_Get(){
        return character_Explain_DB;
    }

    boolean start = true;


    //일시정지, 설정, 사전 등이 눌려 있을때 홈 버튼 갔다 와도 실행 안되도록
    boolean home_Restart_Flag = false;
    public void set_Home_Restart(boolean flag){
        home_Restart_Flag = flag;

        //복귀시 진화의창이 꺼진다.
        if(home_Revolution_Flag){   //back 버튼
            m_Run_False();
            revolution_Flag = true;
            pause_State = false;
//            home_Revolution_Flag = false;
        }

    }
    //홈 버튼 복귀시에 진화의창이 떠있지 앉는다.
    boolean home_Revolution_Flag = false;

    int land_Mark_AttackSpeed = 0;

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

//        Log.e("[뷰]", "교체");





        if(home_Set_Flag){

//            홈 갔다 오는 부분 되지만..
//            가시 같은 스킬 없어지는듯.
//            퍼지나, 진화의 창 떴을때 홈 버튼 갔다 돌아오면 진화의 창 없어지고, 퍼지 가 뜬 상태에서 실행됨.
            background_Sound = MediaPlayer.create(_context, R.raw.background_music_1);
            background_Sound.setLooping(true);

//            background_Sound.setVolume(0.5f,0.5f);
            background_Sound.start();

            Log.e("@@@@@@@@","스킬 중첩 되나?");

            game_thread.function_Skill_All_Recycle();

            game_thread.function_Skill_Crab_img();
            game_thread.function_Skill_Soycrab_img();
            game_thread.function_Skill_Laser_img();
            game_thread.function_Skill_Thorn_img();
            game_thread.function_Skill_Poison123_img();
            game_thread.function_Skill_earthquake_img();
            game_thread.function_Skill_Teeth_mine_img();
            game_thread.function_Skill_Teeth_mine2_img();
            game_thread.function_Skill_Sea_Snake_img();
            game_thread.function_Skill_Slow_Cloud_img();
            game_thread.function_Skill_Boom_Poison_img();
            game_thread.function_Skill_Wave_img();
            game_thread.function_Skill_Wall_img();
            game_thread.function_Skill_Thorn2_img();
            game_thread.function_Skill_lightnign_img();
            game_thread.function_Skill_lightnign1_img();
            game_thread.function_Skill_stomp_img();
            game_thread.function_Skill_fry_img();
            game_thread.function_Skill_Butter_img();
            game_thread.function_Skill_Fork_img();


                m_Run_True();

            //복귀시 진화의창이 꺼진다.
            if(home_Revolution_Flag){   //home 버튼
                m_Run_False();

                revolution_Flag = true;
                pause_State = false;
            }



            return;
        }

        window_Width = width; //화면의 크기
        window_Height = height;

        if(game_thread != null) {
            game_thread.interrupt();
            game_thread = null;
        }
        game_thread = new Game_Thread(/*holder*/);  //쓰레드가 홈버튼을 누름으로 인해 파괴 된다면 다시 생성
        game_thread.start(); //게임
        start = false;


//        이부분이 추가되면 홈 버튼 했다가도 불러와진다. 그러나 recycle 함수 오류를 잡아야 할 듯.
        distroy_Run = true;




//        distroy_Run = true;

//         if(game_thread.getState() == Thread.State.TERMINATED) {
//            game_thread = new Game_Thread(/*holder*/);  //쓰레드가 홈버튼을 누름으로 인해 파괴 된다면 다시 생성
//            game_thread.start(); //게임
//            Log.e("surfaceChanged true","surfaceChanged true");
//        }else {
//
//            game_thread.start();
//            //gameMainThread.start();
//        }


        //설명창 초기화
        Arrays.fill(explain, false);
        //초기화
        re_Start();


        SharedPreferences pref = _context.getSharedPreferences("pref", Activity.MODE_APPEND);



        if(pref != null){
//            Log.e("a","!");
            best_Point = Double.parseDouble(pref.getString("score", "0"));


//            money = pref.getInt("money", 0);
            pref_Class = pref.getInt("pref_Class",0);

            pref.getInt("hp",1);
            day_Count = pref.getInt("day_Count",1);

//            editor.putInt("landmark_hp",(int)land_Mark.get_Ground_Hp());
//            editor.putInt("landmark_class",land_Mark.get_Class_Num()+1);




            //저장된 랜드마크
            land_Mark_Class = pref.getInt("landmark_class",1);

            add_Ground_Land_Mark();



            day_Count = day_Count-3;
            if(day_Count < 1){
                day_Count = 1;
            }


//            Log.e("day", "day! = " + day_Count);

            //플랑크 클래스   100000001
            //물고기 클래스   100000100
            //갑각류 클래스   100010000
            //연체류 클래스   101000000

            // 1 티어 플랑크톤
            if(pref_Class == 100000001){
                main_Character = new Main_Character_Plankton_1(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Plankton_1((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(0);
            }else if(pref_Class == 100000002){
                // 2 티어 플랑크톤
                main_Character = new Main_Character_Plankton_2(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Plankton_2((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(1);
            }else if(pref_Class == 100000003){
                // 3 티어 플랑크톤
                main_Character = new Main_Character_Plankton_3(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Plankton_3((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(2);
            }else if(pref_Class == 100000004){
                // 4 티어 플랑크톤
                main_Character = new Main_Character_Plankton_4(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Plankton_4((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(3);
            }else if(pref_Class == 100000005){
                // 5 티어 플랑크톤
                main_Character = new Main_Character_Plankton_5(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Plankton_5((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(4);
            }else if(pref_Class == 100000100){
                // 1 티어 물고기
                main_Character = new Main_Character_Fish_Tear1(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Fish_Tear1((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(1);
            }else if(pref_Class == 100000200){
                // 2 티어 물고기
                main_Character = new Main_Character_Fish_Tear2(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Fish_Tear2((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(2);
                game_thread.function_Skill_Thorn_img();
            }else if(pref_Class == 100000300){
                // 3 티어 물고기
                main_Character = new Main_Character_Fish_Tear3(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Fish_Tear3((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(3);
                game_thread.function_Skill_Teeth_mine_img();
            }else if(pref_Class == 100000400){
                // 4 티어 물고기
                main_Character = new Main_Character_Fish_Tear4(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Fish_Tear4((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(4);
                game_thread.function_Skill_earthquake_img();
            }else if(pref_Class == 100000500){
                // 5 티어 물고기
                main_Character = new Main_Character_Fish_Tear5(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Fish_Tear5((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(5);
                game_thread.function_Skill_Teeth_mine2_img();
            }else if(pref_Class == 100000600){
                // 6 티어 물고기
                main_Character = new Main_Character_Fish_Tear6(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Fish_Tear6((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(6);
            }else if(pref_Class == 100000700){
                // 7 티어 물고기
                main_Character = new Main_Character_Fish_Tear7(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Fish_Tear7((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(7);
                game_thread.function_Skill_lightnign_img();
            }else if(pref_Class == 100000800){
                // 8 티어 물고기
                main_Character = new Main_Character_Fish_Tear8(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Fish_Tear8((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(8);
                game_thread.function_Skill_lightnign1_img();
            }else if(pref_Class == 100000900){
                // 9 티어 물고기
                main_Character = new Main_Character_Fish_Tear9(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Fish_Tear9((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(9);
                game_thread.function_Skill_Sea_Snake_img();
            }else if(pref_Class == 100001000){
                // 10 티어 물고기
                main_Character = new Main_Character_Fish_Tear10(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Fish_Tear10((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(10);
                game_thread.function_Skill_Thorn_img();
            }else if(pref_Class == 100010000){
                // 1 티어 갑각류
                main_Character = new Main_Character_Shellfish_Tear1(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Shellfish_Tear1((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(1);
            }else if(pref_Class == 100020000){
                // 2 티어 갑각류
                main_Character = new Main_Character_Shellfish_Tear2(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Shellfish_Tear2((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(2);
            }else if(pref_Class == 100030000){
                // 3 티어 갑각류
                main_Character = new Main_Character_Shellfish_Tear3(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Shellfish_Tear3((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(3);
            }else if(pref_Class == 100040000){
                // 4 티어 갑각류
                main_Character = new Main_Character_Shellfish_Tear4(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Shellfish_Tear4((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(4);
                game_thread.function_Skill_Crab_img();
            }else if(pref_Class == 100050000){
                // 5 티어 갑각류
                main_Character = new Main_Character_Shellfish_Tear5(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Shellfish_Tear5((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(5);
                game_thread.function_Skill_Soycrab_img();
            }else if(pref_Class == 100060000){
                // 6 티어 갑각류
                main_Character = new Main_Character_Shellfish_Tear6(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Shellfish_Tear6((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(6);
                game_thread.function_Skill_Thorn2_img();
            }else if(pref_Class == 100070000){
                // 7 티어 갑각류
                main_Character = new Main_Character_Shellfish_Tear7(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Shellfish_Tear7((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(7);
                game_thread.function_Skill_fry_img();
            }else if(pref_Class == 100080000){
                // 8 티어 갑각류
                main_Character = new Main_Character_Shellfish_Tear8(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Shellfish_Tear8((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(8);
                game_thread.function_Skill_Crab_img();
            }else if(pref_Class == 100090000){
                // 9 티어 갑각류
                main_Character = new Main_Character_Shellfish_Tear9(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Shellfish_Tear9((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(9);
                game_thread.function_Skill_Wave_img();
            }else if(pref_Class == 100100000){
                // 10 티어 갑각류
                main_Character = new Main_Character_Shellfish_Tear10(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Shellfish_Tear10((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(10);
                game_thread.function_Skill_stomp_img();
            }else if(pref_Class == 101000000){
                // 1 티어 연체류
                main_Character = new Main_Character_Moulluse_Tear1(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Moulluse_Tear1((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(1);

            }else if(pref_Class == 102000000){
                // 2 티어 연체류
                main_Character = new Main_Character_Moulluse_Tear2(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Moulluse_Tear2((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(2);
            }else if(pref_Class == 103000000){
                // 3 티어 연체류
                main_Character = new Main_Character_Moulluse_Tear3(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Moulluse_Tear3((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(3);
                game_thread.function_Skill_Slow_Cloud_img();
            }else if(pref_Class == 104000000){
                // 4 티어 연체류
                main_Character = new Main_Character_Moulluse_Tear4(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Moulluse_Tear4((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(4);
                game_thread.function_Skill_Butter_img();
            }else if(pref_Class == 105000000){
                // 5 티어 연체류
                main_Character = new Main_Character_Moulluse_Tear5(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Moulluse_Tear5((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(5);
                game_thread.function_Skill_Fork_img();
            }else if(pref_Class == 106000000){
                // 6 티어 연체류
                main_Character = new Main_Character_Moulluse_Tear6(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Moulluse_Tear6((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(6);
                game_thread.function_Skill_Laser_img();
            }else if(pref_Class == 107000000){
                // 7 티어 연체류
                main_Character = new Main_Character_Moulluse_Tear7(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Moulluse_Tear7((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(7);

            }else if(pref_Class == 108000000){
                // 8 티어 연체류
                main_Character = new Main_Character_Moulluse_Tear8(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Moulluse_Tear8((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(8);
                game_thread.function_Skill_Boom_Poison_img();
            }else if(pref_Class == 109000000){
                // 9 티어 연체류
                main_Character = new Main_Character_Moulluse_Tear9(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Moulluse_Tear9((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(9);
                game_thread.function_Skill_Wall_img();
            }else if(pref_Class == 110000000){
                // 10 티어 연체류
                main_Character = new Main_Character_Moulluse_Tear10(0,0, window_Width, window_Height,0,0);
                Init_Main_Character_Image(_context, main_Character);
                main_Character = new Main_Character_Moulluse_Tear10((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
                main_Character.set_Tear(10);
                game_thread.function_Skill_Poison123_img();
            }

            //안해주면 진화의창 확인 누를때 오류
            game_thread.function_Explain_Window_Plankton_Tear_1();

            main_Character.set_Hp(pref.getInt("hp",1));

            if(main_Character.get_Hp() <= 1){
                main_Character.set_Hp(1);
            }


            //여기 한번더 테스팅 해보고, 진화, 혹은 퇴화 할때 드로우 에러
            //스킬 이미지 로드 하기
            game_thread.function_Skill_Crab_img();
            game_thread.function_Skill_Soycrab_img();
            game_thread.function_Skill_Laser_img();
            game_thread.function_Skill_Thorn_img();
            game_thread.function_Skill_Poison123_img();
            game_thread.function_Skill_earthquake_img();
            game_thread.function_Skill_Teeth_mine_img();
            game_thread.function_Skill_Teeth_mine2_img();
            game_thread.function_Skill_Sea_Snake_img();
            game_thread.function_Skill_Slow_Cloud_img();
            game_thread.function_Skill_Boom_Poison_img();
            game_thread.function_Skill_Wave_img();
            game_thread.function_Skill_Wall_img();
            game_thread.function_Skill_Thorn2_img();
            game_thread.function_Skill_lightnign_img();
            game_thread.function_Skill_lightnign1_img();
            game_thread.function_Skill_stomp_img();
            game_thread.function_Skill_fry_img();
            game_thread.function_Skill_Butter_img();
            game_thread.function_Skill_Fork_img();





        }




        /**
         * 타이머를 통한 물고기 생성
         * 게임 요소 생성[물고기, 함정 등]쓰레드 생성
         */
//        fish_Maker = timerTask_FishMaker();
        stage_Day = timerTask_Stage_Day();






        mTimer = new Timer();


        m_Run_True();


    }


    //데이 증가 타이머 테스크
    public TimerTask timerTask_Stage_Day(){
        //데이 증가
        TimerTask stage_Day = new TimerTask() {
            @Override
            public void run() {
                try {
                    //날짜 증가
                    stage_Call();
                    day_Count++;
                    day_Count_View = 0;

                }catch (Exception e){

                }
            }
        };
        return stage_Day;
    }



    //진화 할때 스테이지 시작
    public void stage_Call_Revolution(){
        send_Fish();
        day_Count++;
    }


    public void stage_Call(){
        try {



            //게임 동작 중에만 추가한다.
            if(mRun) {


                send_Fish();


                //조개 한마리 이상 금지
                if(!ground_List.contains(ground_Drag_Clam)){
//                            add_Ground_Clam();                  //조개 추가
                }

                //랜드 마크 하나 이상 금지
                if(!ground_List.contains(land_Mark)){
                    //랜드마크 생성

                    add_Ground_Land_Mark();

                }
            }

            //Thread.sleep(1000);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    /**
     * 서페이스뷰 파괴 하기 위한 run 종료
     */
    public void set_Distroy_Run_False(){
        distroy_Run = false;

    }


    SharedPreferences.Editor editor;
    SharedPreferences pref;

    boolean home_Set_Flag = false;
    public void set_Home_Setting(){
        home_Set_Flag = true;
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        try{


        if(home_Set_Flag){
            m_Run_False();
            return;
        }


//        Log.e("[뷰]", "파괴@@@@@@@@@@@@@2");

        soundPool.release();
        distroy_Run = false;
        m_Run_False();
        ///값 저장

            editor.putString("realmoney", String.valueOf(money));
            editor.commit();

//            editor.putInt("best", Score);


//        Log.e("day", "day = " + day_Count);
        editor.putInt("day_Count", day_Count);
        editor.putInt("pref_Class", main_Character.get_Pref());
        if(main_Character.get_Hp() <= 1){
            main_Character.set_Hp(1);
        }
        editor.putInt("hp", main_Character.get_Hp());
        editor.putInt("landmark_hp",(int)land_Mark.get_Ground_Hp());
        editor.putInt("landmark_class",land_Mark.get_Class_Num()+1);



        //플랑크 클래스   100000001
        //물고기 클래스   100000100
        //갑각류 클래스   100010000
        //연체류 클래스   101000000

//        editor.putInt("money", money);


//        Log.e("a",Score + "");
        editor.commit();
//        Log.e("day", "$ = " + day_Count);


        }catch(Exception e){
            Log.e("@", "surfaceDestroyed");
        }

        try {
            function_Map_Monster_Recycle_Bitmap();
        }catch (Exception e){
            Log.e("function", "function_Map_Monster_Recycle_Bitmap");
        }
        try{
//            Log.e("surfaceDestroyed","surfaceDestroyed");
//            타이머 파괴
//            fish_Maker.cancel();
            stage_Day.cancel();

        }catch (Exception e){

        }

    }



    public void function_Map_Monster_Recycle_Bitmap(){

        pop_Temp_img.recycle();

        effect_Wave_Pop_img.recycle();
        effect_Pop_Steel_img.recycle();
        effect_Pop_Turtle.recycle();
        effect_Stage_Day_img.recycle();
//        explain_Window_ima.recycle();     //재시작 떄문에 일단 리사이클 안함
        gold_img.recycle();
        ruby_img.recycle();

        gameover_Img.recycle();
        infinitymode_Img.recycle();

        explain_Window_Revoluition.recycle();
        explain_Window_Extraction.recycle();  //추출창

        revolution_Button_Background_Effect.recycle();
        revolution_Button_Background_Effect = null;

        backGroundImg.recycle();
        backGroundImg_black.recycle();
        for(int i=0; i<8; i++){
            effect_Background_One_1_img[i].recycle();
            effect_Background_Two_1_img[i].recycle();
            effect_Background_Seaweed_img[i].recycle();
            effect_Background_Seaweed_img[i].recycle();
            effect_background_Rock[i].recycle();

        }
        for(int i=0; i<3; i++){

            //불가사리
            ground_Touch_Starfish_img[i].recycle();

            //소라게
            ground_Touch_Hermit_Hp1_img[i].recycle();
            ground_Touch_Hermit_Hp2_img[i].recycle();
            ground_Touch_Hermit_Hp3_img[i].recycle();
            ground_Touch_Hermit_Hp4_img[i].recycle();
            ground_Touch_Hermit_Hp5_img[i].recycle();

            fish_Turtle_img[i].recycle();   //방해거북
            fish_Monster_Right_img[i].recycle();
            fish_Monster_Left_img[i].recycle();

        }
        for(int i=0; i<8; i++){
            fish_Touch_Squid_img[i].recycle();
            fish_Touch_Ell_img[i].recycle();
            fish_Touch_Ell_Attack_img[i].recycle();
        }
        for(int i=0; i<4; i++){
            fish_Trap_Jelly_img[i].recycle();
        }
        for(int i=0; i<7; i++){
            shadow_img[i].recycle();
        }
        for(int i=0; i<5; i++){
            effect_Orange_img[i].recycle();
            effect_Blue_img[i].recycle();
            effect_Yellow_img[i].recycle();
            effect_Green_img[i].recycle();
            effect_Black_img[i].recycle();
            effect_Pop2_img[i].recycle();
            effect_Pop3_img[i].recycle();
            effect_Pop4_img[i].recycle();
            effect_Pop5_img[i].recycle();
            effect_Pop6_img[i].recycle();
            ground_Trap_Urchin_img[i].recycle();
            ground_Trap_Urchin_Rest_Mode_img[i].recycle();
            effect_background_Seaanemone_img[i].recycle();
            effect_Background_Squid_Ink_img[i].recycle();
            effect_Background_Shark_img[i].recycle();
        }

        for(int i=0; i<6; i++){
            main_Character_Img[i].recycle();
            ground_Drag_Lobsters_img[i].recycle();
        }

        for(int i=0; i<5; i++) {
            ground_Touch_Stingray_img[i].recycle();
        }

        for(int i=0; i<4; i++){
            effect_Background_Friend_Shark_img[i].recycle(); //상어친구 부르기

            //슬로우 이미지
            effect_Slow_img[i].recycle();
            effect_Land_Mark_Pop1_img[i].recycle(); //랜드마크 팝 이미지
            effect_Land_Mark_Pop2_img[i].recycle();
            effect_Land_Mark_Pop3_img[i].recycle();

            //악어
            ground_Touch_Crocodile_img[i].recycle();
            //참새치
            fish_Touch_Marlin_img[i].recycle();
            //기본 물고기
            fish_Touch_Default_Hp1_img[i].recycle();
            fish_Touch_Default_Hp2_img[i].recycle();
            fish_Touch_Default_Hp3_img[i].recycle();
            fish_Touch_Default_Hp4_img[i].recycle();
            fish_Touch_Default_Hp5_img[i].recycle();

            //기본 물고기 중간 보스
            fish_Touch_Default_Middle_Hp1_img[i].recycle();
            fish_Touch_Default_Middle_Hp2_img[i].recycle();
            fish_Touch_Default_Middle_Hp3_img[i].recycle();
            fish_Touch_Default_Middle_Hp4_img[i].recycle();
            fish_Touch_Default_Middle_Hp5_img[i].recycle();

            //기본 물고기 보스
            fish_Touch_Default_Boss_Hp1_img[i].recycle();
            fish_Touch_Default_Boss_Hp2_img[i].recycle();
            fish_Touch_Default_Boss_Hp3_img[i].recycle();
            fish_Touch_Default_Boss_Hp4_img[i].recycle();
            fish_Touch_Default_Boss_Hp5_img[i].recycle();

            //달팽이
            ground_Touch_Snail_Hp1_img[i].recycle();
            ground_Touch_Snail_Hp2_img[i].recycle();
            ground_Touch_Snail_Hp3_img[i].recycle();
            ground_Touch_Snail_Hp4_img[i].recycle();
            ground_Touch_Snail_Hp5_img[i].recycle();

            //달팽이 중간 보스
            ground_Snail_Middle_Hp1_img[i].recycle();
            ground_snail_Middle_Hp2_img[i].recycle();
            ground_snail_Middle_Hp3_img[i].recycle();
            ground_snail_Middle_Hp4_img[i].recycle();
            ground_snail_Middle_Hp5_img[i].recycle();

            //달팽이 보스
            ground_snail_Boss_Hp1_img[i].recycle();
            ground_snail_Boss_Hp2_img[i].recycle();
            ground_snail_Boss_Hp3_img[i].recycle();
            ground_snail_Boss_Hp4_img[i].recycle();
            ground_snail_Boss_Hp5_img[i].recycle();

            fish_Drag_Default_img[i].recycle();            //드래그 물고기
            fish_Drag_Steelbream_img[i].recycle();          //참돔
            fish_Drag_Shark_img[i].recycle();          //상어
            ground_Drag_Crab_img[i].recycle();              //꽃게 이미지
            ground_Drag_Clam_img[i].recycle();


            if(i < 2){
                ground_Drag_Wave_img[i].recycle();  //드래그 웨이브 이미지
            }

        }

    }

    //********************************************************************************************//
}





//////////////////////////////////////////////////////////////////////////////////////////////////////////
//버튼 오픈 소스 활용

class GraphicButton {
    public static final int IMAGE_UP = 0;
    public static final int IMAGE_DOWN = 1;

    private Bitmap[] mImage;
    private Rect mRect;

    public int mImageNum;

    public GraphicButton(Rect rect) {

        mRect = rect;
        mImage = new Bitmap[2];
        mImageNum = IMAGE_UP;

    }


    public void setImages(Bitmap upimage, Bitmap downimage) {

//이미지를 눌린 상태 및 아닌 상태 두가지로 저장한다

        mImage[IMAGE_UP] = upimage;
        mImage[IMAGE_DOWN] = downimage;

    }


    public boolean touch(int tx, int ty) {

//이미지 좌표와 터치 좌표를 비교한다

        Rect rect = mRect;

        if((rect.left < tx && rect.right > tx) && (rect.top < ty && rect.bottom > ty))
            return true;

        return false;

    }


    public void setPress(boolean press) {
//버튼의 이미지를 변경하여 눌린 상태 및 아닌 상태를 표시한다
        mImageNum = press ? IMAGE_DOWN : IMAGE_UP;
    }

    public void draw(Canvas canvas) {
//이미지 번호대로 이미지를 출력한다
        int imagenum = mImageNum;
        if(mImage[imagenum] != null)
            canvas.drawBitmap(mImage[imagenum], null, mRect, null);
    }

}
//********************************************************************************************//