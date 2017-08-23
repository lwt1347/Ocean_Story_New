package com.example.user.ocean_story;

import android.app.Activity;
import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by USER on 2017-01-21.
 * 게임의 모든 화면
 */

public class GameMain extends SurfaceView implements SurfaceHolder.Callback{


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

    private int Score = 0;                          //점수



    /**
     * 터치 이벤트시 발생하면 draw 에서 그리기 위한 flag 변수들
     */
    private boolean default_Fish_Hit_Flag = false;//기본물고기
    private boolean drag_Fish_Hit_Flag = false;;//드래그물고기
    private boolean jellyfish_Fish_Hit_Flag = false;;//해파리는 아직 이벤트 없음

    private boolean snail_Ground_Hit_Flag = false;;//달팽이
    private boolean crap_Ground_Hit_Flag = false;;//꽃게
    private boolean clam_Ground_Hit_Flag = false; //조개
    private boolean seaurchin_Ground_Hit_Flag = false;;//성게
    private boolean land_Mark_Hit_Flag = false;//랜드마크
    private boolean hermit_Ground_Hit_Flag = false;//소라게
    private boolean wave_Ground_Hit_Flag = false; //드래그 파도


    //진화 사운드 한번만
    private boolean revolution_Flag = false;
    private boolean revolution_Flag_Confirm = false;





    private Game_Thread game_thread;                    //스레드 돌릴 클래스 선언
//    private Game_Element_Thread game_element_thread;    //게임 요소 생성[물고기, 함정 등]쓰레드 생성
    private Main_Character main_Character;              //메인 캐릭터 생성
    private boolean mRun = true;                        //run 함수 제어 //퍼즈 걸도록 mRun 컨트롤
    private SurfaceHolder mSurfaceHolder;               //쓰레드 외부에서 SurfaceHolder를 얻기 위한 선언
    private BitmapDrawable image = null;                //메모리 절약 기법
    private Bitmap backGroundImg = null;                //배경이미지
    private Bitmap backGroundImg_black = null;                //배경이미지

    private Bitmap shadow_img[] = new Bitmap[7];//그림자 이미지

    private Bitmap nine_Patch_Hp = null;   //hp

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

    private Bitmap ground_snail_Middle_Hp1_img[] = new Bitmap[4];    //달팽이 중간 보스
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


    //드래그로 죽는 물고기 이미지
    private Bitmap fish_Drag_Default_img[] = new Bitmap[4];         //드래그로 죽는 물고기 이미지

//    /**
//     * 기본 물고기 설명 이미지
//     */
//    private Bitmap explain_Default_Fish_img[] = new Bitmap[5];
//    /**
//     * 드래그 물고기 설명 이미지
//     */
//    private Bitmap explain_Drag_Fish_img[] = new Bitmap[5];
//
//    /**
//     * 오징어 설명 이미지
//     */
//    private Bitmap explain_Squid_img[] = new Bitmap[5];
//    /**
//     * 전기 뱀장어 설명 이미지
//     */
//    private Bitmap explain_Ell_img[] = new Bitmap[5];
//
//    /**
//     * 성게 설명 이미지
//     */
//    private Bitmap explain_Urchin_img[] = new Bitmap[5];
//
//    /**
//     * 성게 설명 이미지 [공격 중]
//     */
//    private Bitmap explain_Urchin_Attack_img[] = new Bitmap[5];
//
//    /**
//     * 달팽이 설명 이미지
//     */
//    private Bitmap explain_Snail_img[] = new Bitmap[5];
//
//    /**
//     * 꽃게 설명 이미지
//     */
//    private Bitmap explain_Crab_img[] = new Bitmap[5];

    /**
     * 해파리 이미지
     */
    private Bitmap fish_Trap_Jelly_img[] = new Bitmap[7];           //해파리 이미지

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



    //메인 캐릭터 이미지 [문어]

    //메인 캐릭터 이미지 [오리진: 곰팡이]
    private Bitmap main_Character_Img[] = new Bitmap[6];        //메인 캐릭터
    private Bitmap main_Character_Img_1[] = new Bitmap[6];        //메인 캐릭터

    //회전 물고기 비트맵 템프 변수
    private Bitmap temp_Fish = null;

    //그림자
    private Bitmap temp_Shadow_img = null;


    //바닥 생명체 비트맵 탬프 변수
    private Bitmap temp_Ground = null;

    /**
     * 등장 창
     */
    private Bitmap explain_Window_Revoluition = null;


    //꽃게 등장
    private Bitmap explain_Window_Origin_Crab = null;
    private Bitmap explain_Window_Origin_Fish = null;
    private Bitmap explain_Window_Origin_Squid = null;
    private Bitmap explain_Window_Origin_Trilbite = null;


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
     * 파도 팦 이미지
     */
    private Bitmap effect_Wave_Pop_img;


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
    private Bitmap effect_Background_Friend_Shark_img[] = new Bitmap[8];


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


    private Canvas canvas;

    private Fish_Touch_Default fish_Touch_Default;      //기본 물고기 생성
    private Fish_Drag_Default fish_Drag_Default;        //드래그 물고기 생성
    private Fish_Trap_Jellyfish fish_Trap_Jellyfish;    //해파리 생성
    private Fish_Touch_Squid fish_Touch_Squid;          //오징어 생성
    private Background_Effect_Squid_Ink fish_Touch_Squid_Ink;   //오징어 잡았을때 먹물 발사.
    private Fish_Touch_Ell fish_Touch_Ell;              //전기뱀장어 생성

    private Ground_Touch_Snail ground_Touch_Snail;      //달팽이 생성
    private Ground_Drag_Crab ground_Drag_Crab;          //꽃게 생성
    private Ground_Drag_Clam ground_Drag_Clam;
    private Ground_Trap_Urchin ground_trap_urchin;      //성게 생성
    private Ground_Touch_Crocodile ground_Touch_Crocodile; //악어 생성
    private Ground_Touch_Hermit ground_Touch_Hermit;    //소라게 생성
    private Ground_Drag_Wave ground_drag_wave;          //드래그 파도 생성




    private Background_Effect_One background_Effect_One;    //배경화면 1번 움직임
    private Background_Effect_Two background_Effect_Two;    //배경화면 1번 움직임

    private Background_Effect_Shark background_Effect_Shark;    //배경화면 상어 움직임


    private ArrayList<Point> background_Effect_Location = new ArrayList<Point>(); //배경 이펙트 위치 선정


    private Bitmap effect_Temp;                         //어떤 이펙트를 넣을것인가 랜덤 변수
    private Random random = new Random();

    private Context _context;                           //화면 얻어오기
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
    /**
     * 기본 생성자
     */
    public GameMain(Context context, AttributeSet attrs){

        super(context,attrs);   //커스텀 뷰 사용 -> attrs.xml 에 등록 해야함

        _context = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
//        game_thread = new Game_Thread(/*mSurfaceHolder*/);      //그림이 그려지고, 게임 동작하는곳

        //게임 요소 추가 할 타이머 [물고기, 함정 등] 1초후에 실행해서 5초마다 반복


        sideInversion = new Matrix();
        sideInversion.setScale(-1, 1); // 좌우반전



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




        /**
         *  배경음악 사운드풀로 안되서
         */
        background_Sound = MediaPlayer.create(_context, R.raw.background_music_1);
        background_Sound.setLooping(true);
        background_Sound.setVolume(1f,1f);
        background_Sound.start();

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

    }
/**
 * 버튼 객체 제거
 */
private void button_Create_method_Init(){

    confirm_Button_1 = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(50, _context),
            window_Height / 2 + convertPixelsToDp(145, _context),
            window_Width / 2 - convertPixelsToDp(50, _context) + convertPixelsToDp(105, _context),
            window_Height / 2 + convertPixelsToDp(145, _context) + convertPixelsToDp(40, _context)));

    confirm_Button_card_1 = new GraphicButton(new Rect(0, 0, 0, 0));
    confirm_Button_card_2 = new GraphicButton(new Rect(0, 0, 0, 0));
    confirm_Button_card_3 = new GraphicButton(new Rect(0, 0, 0, 0));




//    if(main_Character instanceof Main_Character_Fish_Tear1){
//
//        explain_Window_Origin_Trilbite.recycle();
//        explain_Window_Origin_Trilbite = null;
//    }else if(main_Character instanceof Main_Character_Moulluse_Tear1){
//        explain_Window_Origin_Squid.recycle();
//        explain_Window_Origin_Squid = null;
//    }else if(main_Character instanceof Main_Character_Shellfish_Tear1){
//        explain_Window_Origin_Crab.recycle();
//        explain_Window_Origin_Crab = null;
//    }







}



    /**
     * 다시시작 및 시작 처음에 초기화
     */
    public void re_Start(){




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
        revolution_Draw_Flag_Confirm = false;



        //스테이지  = 1, 속도 = 1
         fish_List.clear();
         ground_List.clear();
         Score = 0;
        land_Mark_Class = 1;

        first_Default_Fish = true;
        first_Drag_Fish = true;
        first_Urchin = true;
        first_Snail = true;
        first_Squid = true;
        first_Ell = true;


        main_Character = new Main_Character_Shellfish_Tear1(0,0, window_Width, window_Height,0,0);

        //메인 캐릭터
        Init_Main_Character_Image(_context, main_Character);

        main_Character = new Main_Character_Shellfish_Tear1((window_Width/2) - (main_Character_Img[0].getWidth()/2), (window_Height)/2 + convertPixelsToDp(110, _context), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());
//        game_thread.function_Skill_Soycrab_img();



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
        land_Mark_Img_View_Separate[2] = 30;
        land_Mark_Img_View_Separate[1] = 20;
        land_Mark_Img_View_Separate[0] = 10;


        //이빨 지뢰 제거
        skill_Teeth_Mine_List = new ArrayList<Skill_Teeth_Mine>();
        skill_Teeth_Mine2_List = new ArrayList<Skill_Teeth_Mine2>();

    }


    //게임 상태 컨트롤
    public void m_Run_False(){
        mRun = false;
    }

    boolean pause_State = false;

    public void m_Run_False(boolean param_Pause_State){ //일시정지 버튼을 통해 눌려 지면 화면이 안 어두워 져야 한다.
        mRun = false;
        pause_State = param_Pause_State;
    }


    //화면에서 버튼 눌릴지 말지
    public boolean get_m_Run(){
        return mRun;
    }

    public void m_Run_True(){
        mRun = true;




    }


    //********************************************************************************************//

    /**
     *  내부 클래스
     *  게임 요소 추가 할 스레드 [물고기, 바닥 생명체 등.]
     */






    //********************************************************************************************//


    /**
     *  이너 클래스
     *  내부 클래스 게임 스레드
     *  더블 버퍼링 및 게임 그리기
     */

    class Game_Thread extends Thread{

        /**
         *  Game_Thread 기본 생성자, 이미지 초기화
         */
        public Game_Thread(/*SurfaceHolder surfaceHolder*/) { //더블 버퍼링 같은것,



            //게임 요소 추가 할 쓰레드 [물고기, 함정 등]
            Init_Background_Image(_context); //배경
            DisplayMetrics dm = _context.getApplicationContext().getResources().getDisplayMetrics();
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

            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_drag_wave_s);
            ground_Drag_Wave_img[0] =  image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.ground_drag_wave);
            ground_Drag_Wave_img[1] = image.getBitmap();


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
            }

            for(int i = 0; i < 4; i++) {
                ground_snail_Middle_Hp1_img[i] = Init_Ground_Touch_Snail_Middle_Hp1_Image(_context, i);  //달팽이 이미지  중간보스
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
                ground_snail_Boss_Hp5_img[i] = Init_Ground_Touch_Snail_Boss_Hp4_Image(_context, i);  //달팽이 이미지
            }

            for(int i = 0; i < 4; i++) {
                fish_Drag_Default_img[i] = Init_Fish_Drag_Default_Image(_context, i);            //드래그 물고기
                ground_Drag_Crab_img[i] = Init_Ground_Drag_Crab_Image(_context, i);              //꽃게 이미지
                ground_Drag_Clam_img[i] = Init_Ground_Drag_Clam_Image(_context, i);             //조개이미지
            }







            for(int i=0; i<8; i++) {
                //오징어 이미지
                fish_Touch_Squid_img[i] = Init_Fish_Touch_Squid_Image(_context, i);
                //전기 뱀장어 이미지
                fish_Touch_Ell_img[i] = Init_Fish_Touch_Ell_Image(_context, i);
                fish_Touch_Ell_Attack_img[i] = Init_Fish_Touch_Ell_Attack_Image(_context, i);
            }


            //해파리 이미지
            for(int i=0; i<7; i++){
                fish_Trap_Jelly_img[i] = Init_Fish_Trap_Jellyfish(_context, i);
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
                effect_Background_Friend_Shark_img[i] = Init_Background_Effect_Background_Friend_Shark_Image(_context, i);    //상어 친구 부르기 이펙트
            }




            for(int i=0; i<5; i++){
                effect_Background_Shark_img[i] = Init_Background_Effect_Background_Shark_Image(_context, i);    //배경 이펙트
            }



        }



        /**
         * 기본 물고기 설명창
         */
//        public void function_Init_Explain_Fish_Default(){
//            for(int i=0; i<5; i++) {
//                //물고기 설명창
//                explain_Default_Fish_img[i] = Init_Explain_Fish_Default(_context, i);
//            }
//        }
//        public void recycle_Init_Explain_Fish_Default(){
//            for(int i=0; i<5; i++) {
//                //물고기 설명창
//                explain_Default_Fish_img[i].recycle();
//                explain_Default_Fish_img[i] = null;
//            }
//        }
//
//
//        /**
//         * 드래그 물고기 설명창
//         */
//        public void function_Init_Explain_Fish_Drag() {
//            for (int i = 0; i < 5; i++) {
//                //드래그 물고기 설명
//                explain_Drag_Fish_img[i] = Init_Explain_Fish_Drag(_context, i);
//            }
//        }
//        public void recycle_Init_Explain_Fish_Drag() {
//            for (int i = 0; i < 5; i++) {
//                explain_Drag_Fish_img[i].recycle();
//                explain_Drag_Fish_img[i] = null;
//            }
//        }
//
//
//        /**
//         * 오징어 설명창
//         */
//        public void function_Init_Explain_Squid() {
//            for (int i = 0; i < 5; i++) {
//                //오징어 설명
//                explain_Squid_img[i] = Init_Explain_Squid(_context, i);
//            }
//        }
//        public void recycle_Init_Explain_Squid() {
//            for (int i = 0; i < 5; i++) {
//                explain_Squid_img[i].recycle();
//                explain_Squid_img[i] = null;
//            }
//        }
//
//
//
//        /**
//         * 전기 뱀장어 설명창
//         */
//        public void function_Init_Explain_Ell() {
//            for (int i = 0; i < 5; i++) {
//                //전기 뱀장어 설명
//                explain_Ell_img[i] = Init_Explain_Ell(_context, i);
//            }
//        }
//        public void recycle_Init_Explain_Ell() {
//            for (int i = 0; i < 5; i++) {
//                explain_Ell_img[i].recycle();
//                explain_Ell_img[i] = null;
//            }
//        }
//
//
//        /**
//         * 성게 설명창
//         */
//        public void function_Init_Explain_Urchin() {
//            for (int i = 0; i < 5; i++) {
//                //성게 설명
//                explain_Urchin_img[i] = Init_Explain_Urchin(_context, i);
//                //성게 공격 모드 설명
//                explain_Urchin_Attack_img[i] = Init_Explain_Urchin_Attack(_context, i);
//            }
//        }
//        public void recycle_Init_Explain_Urchin() {
//            for (int i = 0; i < 5; i++) {
//                explain_Urchin_img[i].recycle();
//                explain_Urchin_img[i]= null;
//
//                explain_Urchin_Attack_img[i].recycle();
//                explain_Urchin_Attack_img[i] = null;
//            }
//        }
//
//        /**
//         * 달팽이 설명 창
//         */
//        public void function_Init_Explain_Snail() {
//            for (int i = 0; i < 5; i++) {
//                //달팽이 설명
//                explain_Snail_img[i] = Init_Explain_Snail(_context, i);
//            }
//        }
//        public void recycle_Init_Explain_Snail() {
//
//
//
//
//            for (int i = 0; i < 5; i++) {
//                explain_Snail_img[i].recycle();
//                explain_Snail_img[i] = null;
//            }
//        }
//
//
//
//
//        /**
//         * 꽃게 설명창
//         */
//        public void function_Init_Explain_Crab() {
//            for (int i = 0; i < 5; i++) {
//                //꽃게 설명
//                explain_Crab_img[i] = Init_Explain_Crab(_context, i);
//            }
//        }
//        public void recycle_Init_Explain_Crab() {
//            for (int i = 0; i < 5; i++) {
//                explain_Crab_img[i].recycle();
//                explain_Crab_img[i] = null;
//            }
//        }

        /**
         * 진화의창 설명창
         */
        public void function_Explain_Window_Revolrution() {
            //진화의창 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_revolrution);
            explain_Window_Revoluition = image.getBitmap();
        }
        public void recycle_Explain_Window_Revolrution() {
            explain_Window_Revoluition.recycle();
            explain_Window_Revoluition = null;
        }


        /**
         *  fish_Tear
         */
        public void function_Explain_Window_Fish_Tear_1() { //explain_window_origincrab
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_originfish);
            explain_Window_Origin_Fish = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_2() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_originfish);
            explain_Window_Origin_Fish = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_3() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_originfish);
            explain_Window_Origin_Fish = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_4() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_originfish);
            explain_Window_Origin_Fish = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_5() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_originfish);
            explain_Window_Origin_Fish = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_6() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_originfish);
            explain_Window_Origin_Fish = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_7() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_originfish);
            explain_Window_Origin_Fish = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_8() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_originfish);
            explain_Window_Origin_Fish = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_9() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_originfish);
            explain_Window_Origin_Fish = image.getBitmap();
        }
        public void function_Explain_Window_Fish_Tear_10() {
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_originfish);
            explain_Window_Origin_Fish = image.getBitmap();
        }


        /**
         * moulluse_Tear
         */
        public void function_Explain_Window_Moulluse_Tear_1() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Squid = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_2() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Squid = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_3() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Squid = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_4() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Squid = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_5() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Squid = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_6() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Squid = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_7() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Squid = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_8() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Squid = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_9() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Squid = image.getBitmap();
        }
        public void function_Explain_Window_Moulluse_Tear_10() {
            //삼징어 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Squid = image.getBitmap();
        }


        /**
         *  Shellfish_Tear
         */
        public void function_Explain_Window_Shellfish_Tear_1() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Crab = image.getBitmap();

        }
        public void function_Explain_Window_Shellfish_Tear_2() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Crab = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_3() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Crab = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_4() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Crab = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_5() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Crab = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_6() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Crab = image.getBitmap();
        }public void function_Explain_Window_Shellfish_Tear_7() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Crab = image.getBitmap();
        }public void function_Explain_Window_Shellfish_Tear_8() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Crab = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_9() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Crab = image.getBitmap();
        }
        public void function_Explain_Window_Shellfish_Tear_10() {
            //게딱지 설명창
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Crab = image.getBitmap();
        }







        public void function_Explain_Window_Origintrilobite() {
            //삼엽충
            image = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.explain_window_origintrilobite);
            explain_Window_Origin_Trilbite = image.getBitmap();
        }


        /**
         * 메인 캐릭터
         */

        public void recycle_Main_Character(){
            for(int i=0; i<6; i++){
                main_Character_Img[i].recycle();
                main_Character_Img[i] = null;

                main_Character_Img[i].recycle();
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
        public void recycle_Skill_Crab_img(){
            for(int i=0; i<4; i++){
                skill_Crab_img[i].recycle();
                skill_Crab_img[i] = null;
            }
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
        public void recycle_Skill_Soycrab_img(){
            for(int i=0; i<4; i++){
                skill_Soycrab_img[i].recycle();
                skill_Soycrab_img[i] = null;
            }
        }
        ///////////////////////////////////////////////////////////

        //연체류 티어 6
        public void function_Skill_Laser_img(){
            //레이저
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_laser_1);
            skill_Laser_img = image.getBitmap();
        }
        public void recycle_Skill_Laser_img(){
                skill_Laser_img.recycle();
                skill_Laser_img = null;
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
        public void recycle_Skill_Thorn_img(){
            for(int i=0; i<4; i++){
                skill_Thorn_img[i].recycle();
                skill_Thorn_img[i] = null;
            }
        }
        ///////////////////////////////////////////////////////////

        //연체류 티어 10
        public void function_Skill_Poison123_img(){
            //독구름 소환
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud1_1);
            skill_Poison1_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud1_2);
            skill_Poison1_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud1_3);
            skill_Poison1_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud1_4);
            skill_Poison1_img[3] = image.getBitmap();

            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud2_1);
            skill_Poison2_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud2_2);
            skill_Poison2_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud2_3);
            skill_Poison2_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud2_4);
            skill_Poison2_img[3] = image.getBitmap();

            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud3_1);
            skill_Poison3_img[0] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud3_2);
            skill_Poison3_img[1] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud3_3);
            skill_Poison3_img[2] = image.getBitmap();
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_posion_cloud3_4);
            skill_Poison3_img[3] = image.getBitmap();
        }
        public void recycle_Skill_Poison123_img(){
            for(int i=0; i<4; i++){
                skill_Poison1_img[i].recycle();
                skill_Poison1_img[i] = null;
            }
            for(int i=0; i<4; i++){
                skill_Poison2_img[i].recycle();
                skill_Poison2_img[i] = null;
            }
            for(int i=0; i<4; i++){
                skill_Poison3_img[i].recycle();
                skill_Poison3_img[i] = null;
            }
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
        public void recycle_Skill_earthquake_img(){
            for(int i=0; i<4; i++){
                skill_earthquake_img[i].recycle();
                skill_earthquake_img[i] = null;
            }
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
        public void recycle_Skill_Teeth_mine_img(){
            for(int i=0; i<3; i++){
                skill_Teeth_mine_img[i].recycle();
                skill_Teeth_mine_img[i] = null;
            }
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
        public void recycle_Skill_Teeth_mine2_img(){
            for(int i=0; i<4; i++){
                skill_Teeth_mine2_img[i].recycle();
                skill_Teeth_mine2_img[i] = null;
            }
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
        public void recycle_Skill_Sea_Snake_img(){
            for(int i=0; i<3; i++){
                skill_Sea_Snake_img[i].recycle();
                skill_Sea_Snake_img[i] = null;
            }
        }
        ///////////////////////////////////////////////////////////

        //연체류 티어 3
        public void function_Skill_Slow_Cloud_img(){

            //이속 느려지는 구름 생성
            image = (BitmapDrawable)_context.getResources().getDrawable(R.drawable.skill_slow_cloud_1);
            skill_Slow_Cloud_img = image.getBitmap();
        }
        public void recycle_Skill_Slow_Cloud_img(){

            skill_Slow_Cloud_img.recycle();
            skill_Slow_Cloud_img = null;

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
        public void recycle_Skill_Boom_Poison_img(){
            for(int i=0; i<4; i++){
                skill_Boom_Poison_img[i].recycle();
                skill_Boom_Poison_img[i] = null;
            }
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
        public void recycle_Skill_Wave_img(){
            for(int i=0; i<3; i++){
                skill_Wave_img[i].recycle();
                skill_Wave_img[i] = null;
            }
        }
        ///////////////////////////////////////////////////////////

        //연체류 티어 9
        public void function_Skill_wall_img(){
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
        public void recycle_Skill_wall_img(){
            for(int i=0; i<4; i++){
                skill_wall_img[i].recycle();
                skill_wall_img[i] = null;
            }
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
        public void recycle_Skill_Thorn2_img(){
            for(int i=0; i<3; i++){
                skill_Thorn2_img[i].recycle();
                skill_Thorn2_img[i] = null;
            }
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
        public void recycle_Skill_lightnign_img(){
            for(int i=0; i<4; i++){
                skill_lightnign_img[i].recycle();
                skill_lightnign_img[i] = null;
            }
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
        public void recycle_Skill_lightnign1_img(){
            for(int i=0; i<4; i++){
                skill_lightnign1_img[i].recycle();
                skill_lightnign1_img[i] = null;
            }
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
        public void recycle_Skill_stomp_img(){
            for(int i=0; i<4; i++){
                skill_stomp_img[i].recycle();
                skill_stomp_img[i] = null;
            }
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
        public void recycle_Skill_fry_img(){
            for(int i=0; i<5; i++){
                skill_fry_img[i].recycle();
                skill_fry_img[i] = null;
            }
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
        public void recycle_Skill_Butter_img(){
            for(int i=0; i<3; i++){
                skill_Butter_img[i].recycle();
                skill_Butter_img[i] = null;
            }
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
        public void recycle_Skill_Fork_img(){
            for(int i=0; i<2; i++){
                skill_Fork_img[i].recycle();
                skill_Fork_img[i] = null;
            }
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

        /**
         * 물고기 설명창
         * */
//        public Bitmap Init_Explain_Fish_Default(Context context, int num){
//            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.explain_default_fish_1 + num); //인트형이라 + 1하면 그림 변경됨
//            return image.getBitmap();
//        }
//
//        /**
//         * 오징어 설명
//         */
//        public Bitmap Init_Explain_Squid(Context context, int num){
//            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.explain_squid_1 + num); //인트형이라 + 1하면 그림 변경됨
//            return image.getBitmap();
//        }
//
//        /**
//         * 전기뱀장어 설명 이미지
//         */
//        public Bitmap Init_Explain_Ell(Context context, int num){
//            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.explain_ell_1 + num); //인트형이라 + 1하면 그림 변경됨
//            return image.getBitmap();
//        }
//
//        /**
//         * 드래그 물고기 설명
//         */
//        public Bitmap Init_Explain_Fish_Drag(Context context, int num) {
//            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.explain_drag_fish_1 + num); //인트형이라 + 1하면 그림 변경됨
//            return image.getBitmap();
//        }
//
//        /**
//         *  성게 설명
//         */
//        public Bitmap Init_Explain_Urchin(Context context, int num) {
//            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.explain_urchin_1 + num); //인트형이라 + 1하면 그림 변경됨
//            return image.getBitmap();
//        }
//
//        /**
//         * 성게 공격 모드 설명
//         */
//        public Bitmap Init_Explain_Urchin_Attack(Context context, int num) {
//            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.explain_urchin_attack_1 + num); //인트형이라 + 1하면 그림 변경됨
//            return image.getBitmap();
//        }
//
//        /**
//         * 달팽이 설명
//         */
//        public Bitmap Init_Explain_Snail(Context context, int num) {
//            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.explain_snail_1 + num); //인트형이라 + 1하면 그림 변경됨
//            return image.getBitmap();
//        }
//
//        /**
//         * 꽃게 설명
//         */
//        public Bitmap Init_Explain_Crab(Context context, int num) {
//            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.explain_crab_1 + num); //인트형이라 + 1하면 그림 변경됨
//            return image.getBitmap();
//        }





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









    /**
     *  그리기 함수
     */
    //배경 이펙트 객체 1개로 처리하기 위해서, 각자 다르게 보여주기 위한 이미지 배열 컨트롤
    int effect_Background_Two_1_img_Control_Temp = 0;
    LandMark_Damage_View landMark_Damage_View;
        ArrayList<LandMark_Damage_View> landMark_Damage_View_List = new ArrayList<LandMark_Damage_View>();

    public synchronized void doDraw(Canvas canvas) {

        try{


        if(revolution_Button_Activation ){
            m_Run_False();
            revolution_Button_Activation = false;
            //            진화버튼 제거
            revolution_Button = new GraphicButton(new Rect(0,
                    0,
                    0,
                    0));
            revolution_Draw_Flag_Confirm = false;

            revolution_Button_Background_Effect.recycle();
            revolution_Button_Background_Effect = null;

        }



        //일시정지 버튼을 통해 눌린 일시정지는 화면이 어두워지지 않아야 한다.
        if(pause_State){
            pause_State = false;
        }



        /**
         *  배경이미지
         */
        draw.draw_Bmp(canvas, backGroundImg, 0, 0);



        //랜드마크 그리기
        for(int i=0; i<ground_List.size(); i++){
            if(ground_List.get(i) instanceof Land_Mark){


                if(land_Mark_Hit_Flag){
                    landMark_Damage_View = new LandMark_Damage_View(touchx,touchy);
                    landMark_Damage_View.set_Damage(character_Damege);
                    landMark_Damage_View_List.add(landMark_Damage_View);
                }

                if(land_Mark.get_Class_Num() == 0) {
                    if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[2]) {

                        draw.draw_Bmp(canvas, land_Mark1_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());

                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[1]) {
                        draw.draw_Bmp(canvas, land_Mark1_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[0]) {
                        draw.draw_Bmp(canvas, land_Mark1_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else {
                        draw.draw_Bmp(canvas, land_Mark1_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    }
                    if (land_Mark_Hit_Flag) {


                        draw.draw_Bmp(canvas, effect_Land_Mark_Pop1_img[random.nextInt(4)],
                                ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark1_img[0].getWidth()) - 35,
                                ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark1_img[0].getHeight()) - 35);


                        land_Mark_Hit_Flag = false;
                    }
                }else if(land_Mark.get_Class_Num() == 1){
                    if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[2]) {
                        draw.draw_Bmp(canvas, land_Mark2_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[1]) {
                        draw.draw_Bmp(canvas, land_Mark2_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[0]) {
                        draw.draw_Bmp(canvas, land_Mark2_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else{
                        draw.draw_Bmp(canvas, land_Mark2_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    }
                    if (land_Mark_Hit_Flag) {


                        draw.draw_Bmp(canvas, effect_Land_Mark_Pop2_img[random.nextInt(4)],
                                ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark2_img[0].getWidth()) - 35,
                                ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark2_img[0].getHeight()) - 35);



                        land_Mark_Hit_Flag = false;
                    }
                }else if(land_Mark.get_Class_Num() == 2){
                    if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[2]) {
                        draw.draw_Bmp(canvas, land_Mark3_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[1]) {
                        draw.draw_Bmp(canvas, land_Mark3_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[0]) {
                        draw.draw_Bmp(canvas, land_Mark3_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else {
                        draw.draw_Bmp(canvas, land_Mark3_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    }
                    if (land_Mark_Hit_Flag) {


                        draw.draw_Bmp(canvas, effect_Land_Mark_Pop2_img[random.nextInt(4)],
                                ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark3_img[0].getWidth()) - 35,
                                ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark3_img[0].getHeight()) - 35);

                        land_Mark_Hit_Flag = false;
                    }
                }else if(land_Mark.get_Class_Num() == 3){
                    if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[2]) {
                        draw.draw_Bmp(canvas, land_Mark4_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[1]) {
                        draw.draw_Bmp(canvas, land_Mark4_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[0]) {
                        draw.draw_Bmp(canvas, land_Mark4_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else {
                        draw.draw_Bmp(canvas, land_Mark4_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    }
                    if (land_Mark_Hit_Flag) {

                        draw.draw_Bmp(canvas, effect_Land_Mark_Pop3_img[random.nextInt(4)],
                                ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark4_img[0].getWidth()) - 35,
                                ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark4_img[0].getHeight()) - 35);

                        land_Mark_Hit_Flag = false;
                    }
                }else if(land_Mark.get_Class_Num() == 4){
                    if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[2]) {
                        draw.draw_Bmp(canvas, land_Mark5_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[1]) {
                        draw.draw_Bmp(canvas, land_Mark5_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[0]) {
                        draw.draw_Bmp(canvas, land_Mark5_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else  {
                        draw.draw_Bmp(canvas, land_Mark5_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    }
                    if (land_Mark_Hit_Flag) {

                        draw.draw_Bmp(canvas, effect_Land_Mark_Pop2_img[random.nextInt(4)],
                                ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark5_img[0].getWidth()) - 35,
                                ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark5_img[0].getHeight()) - 35);


                        land_Mark_Hit_Flag = false;
                    }

                }else if(land_Mark.get_Class_Num() == 5){
                    if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[2]) {
                        draw.draw_Bmp(canvas, land_Mark6_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[1]) {
                        draw.draw_Bmp(canvas, land_Mark6_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else if (ground_List.get(i).get_Ground_Hp() > land_Mark_Img_View_Separate[0]) {
                        draw.draw_Bmp(canvas, land_Mark6_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    } else  {
                        draw.draw_Bmp(canvas, land_Mark6_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    }
                    if (land_Mark_Hit_Flag) {

                        draw.draw_Bmp(canvas, effect_Land_Mark_Pop3_img[random.nextInt(4)],
                                ground_List.get(i).get_Ground_Point_X() + random.nextInt(land_Mark6_img[0].getWidth()) - 35,
                                ground_List.get(i).get_Ground_Point_Y() + random.nextInt(land_Mark6_img[0].getHeight()) - 35);


                        land_Mark_Hit_Flag = false;
                    }

                }

                break;
            }
        }

//랜드 마크 대미지 보여주기
        score_Text_Size = convertPixelsToDp(15, _context);
        paint_Temp.setTextSize(score_Text_Size);



        for(int i=0; i<landMark_Damage_View_List.size(); i++){



            paint_Temp.setStyle(Paint.Style.STROKE);
            paint_Temp.setStrokeWidth(5);

            paint_Temp.setColor(Color.BLACK);
            canvas.drawText(landMark_Damage_View_List.get(i).get_Damage()+"", landMark_Damage_View_List.get(i).get_X_Point(),landMark_Damage_View_List.get(i).get_Y_Point(), paint_Temp);

            paint_Temp.setStyle(Paint.Style.FILL);
            paint_Temp.setStrokeWidth(3);


                paint_Temp.setColor(Color.YELLOW);


            canvas.drawText(landMark_Damage_View_List.get(i).get_Damage()+"", landMark_Damage_View_List.get(i).get_X_Point(),landMark_Damage_View_List.get(i).get_Y_Point(), paint_Temp);




            landMark_Damage_View_List.get(i).set_Text_Move();


            if(landMark_Damage_View_List.get(i).get_Live_Flag()){
                landMark_Damage_View_List.remove(i);
            }

        }


        for(int i=0; i<ground_List.size(); i++){
            if(ground_List.get(i) instanceof Ground_Drag_Clam){
                if (ground_List.get(i) instanceof Ground_Drag_Clam) {
                    //조개 그리기

                    if(ground_List.get(i).get_Ground_Hp() > 300) {
                        draw.draw_Bmp(canvas, ground_Drag_Clam_img[0], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    }else if(ground_List.get(i).get_Ground_Hp() > 200) {
                        draw.draw_Bmp(canvas, ground_Drag_Clam_img[1], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    }else if(ground_List.get(i).get_Ground_Hp() > 100) {
                        draw.draw_Bmp(canvas, ground_Drag_Clam_img[2], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    }else if(ground_List.get(i).get_Ground_Hp() > 0) {
                        draw.draw_Bmp(canvas, ground_Drag_Clam_img[3], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                    }

                    /**
                     * 조개 터치 이펙트
                     */
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
                    }else if(character_Damege == 10) {
                        pop_Temp_img = effect_Pop_damage10_Image[tempInt];
                    }

                    if(i == ground_Remove_Temp && clam_Ground_Hit_Flag){
                        draw.draw_Bmp(canvas, pop_Temp_img,
                                ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(ground_Drag_Crab_img[0].getWidth()) ,
                                ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(ground_Drag_Crab_img[0].getHeight()));
                        clam_Ground_Hit_Flag = false;

                        break;
                    }



                }
            }

        }




        /**
         * 배경 이펙트
         */
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




        draw_Main_Character_Draw();  //메인 캐릭터 기본 이미지
        main_Character.character_Move();    //메인 캐릭터 움직임 효과






        /**
         * 그라운드 그리기 (달팽이) 가장 아랫부분에 깔려야 하기 때문에 가장 위쪽에서 그림
         */
        for(int i=ground_List.size() - 1; i >= 0; i--) {




            if(ground_List.get(i).get_Ground_Hp() > 0){




                //달팽이 움직임
                 if (ground_List.get(i) instanceof Ground_Touch_Snail) {

//                    //첫 번째 달팽이 일때
//                    if (ground_List.get(i).get_First_Test_Object()) {
//                        draw.draw_Bmp(canvas, explain_Snail_img[ground_List.get(i).get_Draw_Ground_Status()],
//                                ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(70, _context),
//                                ground_List.get(i).get_Ground_Point_Y() - convertPixelsToDp(13, _context));
//
//                        //달팽이 hp
//                        paint_Temp.setTextSize(convertPixelsToDp(15, _context));
//                        paint_Temp.setColor(Color.RED);
//                        paint_Temp.setStrokeWidth(4);
//
//                        canvas.drawText("" + ground_List.get(i).get_Ground_Hp(),
//                                ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(35, _context),
//                                ground_List.get(i).get_Ground_Point_Y() + convertPixelsToDp(37, _context), paint_Temp);
//
//                    }



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
                            draw.draw_Bmp(canvas, ground_snail_Middle_Hp1_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                        }

                    }else if(ground_List.get(i).get_Class_Num() == 2){  //중간 보스

                        if (ground_List.get(i).get_Ground_Hp() == 4) {
                            draw.draw_Bmp(canvas, ground_snail_Boss_Hp5_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                        } else if (ground_List.get(i).get_Ground_Hp() == 3) {
                            draw.draw_Bmp(canvas, ground_snail_Boss_Hp4_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                        } else if (ground_List.get(i).get_Ground_Hp() == 2) {
                            draw.draw_Bmp(canvas, ground_snail_Boss_Hp3_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                        } else if (ground_List.get(i).get_Ground_Hp() == 1) {
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
                                ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - 35,
                                ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - 35);
                        snail_Ground_Hit_Flag = false;
                    }


                        //이미지 크기 재 조정
                    if(ground_List.get(i).get_Ground_Speed() <= 0) {
                        effect_Slow_img[ground_List.get(i).get_Slow_Effect()] = Bitmap.createScaledBitmap(
                                effect_Slow_img[ground_List.get(i).get_Slow_Effect()],
                                ground_Touch_Snail_Hp1_img[0].getWidth(),
                                ground_Touch_Snail_Hp1_img[0].getHeight(), true);
                    }



                }else if (ground_List.get(i) instanceof Ground_Touch_Hermit) {
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
                        hermit_Ground_Hit_Flag = false;
                    }


                    if(ground_List.get(i).get_Ground_Speed() <= 0) {
                        effect_Slow_img[ground_List.get(i).get_Slow_Effect()] = Bitmap.createScaledBitmap(
                                effect_Slow_img[ground_List.get(i).get_Slow_Effect()],
                                ground_Touch_Hermit_Hp1_img[0].getWidth(),
                                ground_Touch_Hermit_Hp1_img[0].getHeight(), true);
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

//                    //첫 번째 꽃게 일때
//                    if (ground_List.get(i).get_First_Test_Object()) {
//                        draw.draw_Bmp(canvas, explain_Crab_img[ground_List.get(i).get_Draw_Ground_Status()],
//                                ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(78, _context),
//                                ground_List.get(i).get_Ground_Point_Y() - convertPixelsToDp(30, _context));
//
//                        //꽃게 hp
//                        paint_Temp.setTextSize(convertPixelsToDp(15, _context));
//                        paint_Temp.setColor(Color.RED);
//                        paint_Temp.setStrokeWidth(4);
//
//                        canvas.drawText("" + ground_List.get(i).get_Ground_Hp(),
//                                ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(33, _context),
//                                ground_List.get(i).get_Ground_Point_Y() + convertPixelsToDp(13, _context), paint_Temp);
//
//
//                    }


                    draw.draw_Bmp(canvas, shadow_img[5],  ground_List.get(i).get_Ground_Point_X()  + convertPixelsToDp(4, _context), ground_List.get(i).get_Ground_Point_Y()  + convertPixelsToDp(19, _context) );
                    draw.draw_Bmp(canvas, ground_Drag_Crab_img[((Ground_Drag_Crab) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());

                    /**
                     * 꽃게 터치 이펙트
                     */
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
                    }else if(character_Damege == 10) {
                        pop_Temp_img = effect_Pop_damage10_Image[tempInt];
                    }

                    if(i == ground_Remove_Temp && crap_Ground_Hit_Flag){
                        draw.draw_Bmp(canvas, pop_Temp_img,
                                ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(ground_Drag_Crab_img[0].getWidth())-35 ,
                                ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(ground_Drag_Crab_img[0].getHeight())-35);
                        crap_Ground_Hit_Flag = false;
                    }

                    //이미지 크기 재 조정
                    if(ground_List.get(i).get_Ground_Speed() <= 0) {
                        effect_Slow_img[ground_List.get(i).get_Slow_Effect()] = Bitmap.createScaledBitmap(
                                effect_Slow_img[ground_List.get(i).get_Slow_Effect()],
                                ground_Drag_Crab_img[0].getWidth(),
                                ground_Drag_Crab_img[0].getHeight(), true);
                    }


                }


                //성게 움직임
                else if (ground_List.get(i) instanceof Ground_Trap_Urchin) {


                    //첫 번째 성게 일때
//                    if (ground_List.get(i).get_First_Test_Object()) {
//
//                        //성게가 150 시간 넘어가면 부활한다.
//                        if (((Ground_Trap_Urchin) ground_List.get(i)).get_Live_Time() <= 150) {
//
//                            draw.draw_Bmp(canvas, explain_Urchin_img[ground_List.get(i).get_Draw_Ground_Status()],
//                                    ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(78, _context),
//                                    ground_List.get(i).get_Ground_Point_Y() - convertPixelsToDp(28, _context));
//
//                            // 남은 시간 그리기
//                            paint_Temp.setTextSize(convertPixelsToDp(15, _context));
//                            paint_Temp.setColor(Color.RED);
//                            paint_Temp.setStrokeWidth(4);
//
//
//                            canvas.drawText("" + (150 - ((Ground_Trap_Urchin) ground_List.get(i)).get_Live_Time()),
//                                    ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(40, _context),
//                                    ground_List.get(i).get_Ground_Point_Y() + convertPixelsToDp(30, _context), paint_Temp);
//                        } else { //성게가 공격 모드일때.
//                            draw.draw_Bmp(canvas, explain_Urchin_Attack_img[ground_List.get(i).get_Draw_Ground_Status()],
//                                    ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(78, _context),
//                                    ground_List.get(i).get_Ground_Point_Y() - convertPixelsToDp(28, _context));
//
//                            // 남은 시간 그리기
//                            paint_Temp.setTextSize(convertPixelsToDp(15, _context));
//                            paint_Temp.setColor(Color.RED);
//                            paint_Temp.setStrokeWidth(4);
//
//
//                            canvas.drawText("" + (300 - ((Ground_Trap_Urchin) ground_List.get(i)).get_Live_Time()),
//                                    ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(40, _context),
//                                    ground_List.get(i).get_Ground_Point_Y() + convertPixelsToDp(30, _context), paint_Temp);
//                        }
//
//
//                    }


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
//                       draw.draw_Bmp(canvas, effect_Temp,
//                               ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(ground_Touch_Snail_Hp1_img[0].getWidth()) - 35,
//                               ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(ground_Touch_Snail_Hp1_img[0].getHeight()) - 35);

                       seaurchin_Ground_Hit_Flag = false;
//                       ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();  //성게 삭제,
                   }


                }


                //속도가 0인 객체 위에 달팽이 [슬로우 이팩트를 그린다.]
                if(ground_List.get(i).get_Ground_Speed() <= 0){
                    draw.draw_Bmp(canvas, effect_Slow_img[0],
                            ground_List.get(i).get_Ground_Point_X(),
                            ground_List.get(i).get_Ground_Point_Y() - convertPixelsToDp(13, _context));



                    draw.draw_Bmp(canvas, effect_Slow_img[ground_List.get(i).get_Slow_Effect()],
                            ground_List.get(i).get_Ground_Point_X(),
                            ground_List.get(i).get_Ground_Point_Y());
                }

                //독 상태 이상에 걸린 객체에 포이즌 마크를 표시한다.
                if(ground_List.get(i).get_Status_Poison()){

                    ground_List.get(i).set_Status_Poison_AttacK();


                    if(ground_List.get(i) instanceof Ground_Touch_Snail){
                        draw.draw_Bmp(canvas, effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ///중독된 물고기 hp 감속과 동시에 이미지 표현
                                ground_List.get(i).get_Ground_Point_X() - (effect_Poison_img[0].getWidth()),
                                ground_List.get(i).get_Ground_Point_Y() - convertPixelsToDp(13, _context));
                    }else {
                        draw.draw_Bmp(canvas, effect_Poison_img[ground_List.get(i).get_Status_Poison_AttacK()], ///중독된 물고기 hp 감속과 동시에 이미지 표현
                                ground_List.get(i).get_Ground_Point_X(),
                                ground_List.get(i).get_Ground_Point_Y() - convertPixelsToDp(13, _context));
                    }

                    draw.draw_Bmp(canvas, effect_Poison_img[0],
                            ground_List.get(i).get_Ground_Point_X(),
                            ground_List.get(i).get_Ground_Point_Y() - convertPixelsToDp(13, _context));

                }



            }


        }





        /**
         * 물고기 그리기
         */

        for(int i=fish_List.size() - 1; i >= 0 ; i--) {
        if(fish_List.get(i).get_Fish_Hp() > 0){





            if (fish_List.get(i) instanceof Fish_Touch_Default) {

                /**
                 * 물고기 설명 그림
                 */
//                if ((fish_List.get(i)).get_First_Test_Object()) { //첫 번째 물고기일때
//
//                    draw.draw_Bmp(canvas, explain_Default_Fish_img[fish_List.get(i).get_Draw_Fish_Status()],
//                            fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(65, _context),
//                            fish_List.get(i).get_Fish_Point_Y() - convertPixelsToDp(20, _context));
//
//                    // 남은 hp 그리기
//                    paint_Temp.setTextSize(convertPixelsToDp(15, _context));
//                    paint_Temp.setColor(Color.RED);
//                    paint_Temp.setStrokeWidth(4);
//                    canvas.drawText("" + fish_List.get(i).get_Fish_Hp(),
//                            fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(25, _context),
//                            fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(47, _context), paint_Temp);
//
//                }




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

                     if (fish_List.get(i).get_Fish_Hp() >= 4) {
                         temp_Fish = draw.rotate_Image(fish_Touch_Default_Middle_Hp1_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                     } else if (fish_List.get(i).get_Fish_Hp() >= 3) {
                         temp_Fish = draw.rotate_Image(fish_Touch_Default_Middle_Hp2_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                     } else if (fish_List.get(i).get_Fish_Hp() >= 2) {
                         temp_Fish = draw.rotate_Image(fish_Touch_Default_Middle_Hp3_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                     } else if (fish_List.get(i).get_Fish_Hp() >= 1) {
                         temp_Fish = draw.rotate_Image(fish_Touch_Default_Middle_Hp4_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                     } else {
                         temp_Fish = draw.rotate_Image(fish_Touch_Default_Middle_Hp5_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                     }

                     temp_Shadow_img = draw.rotate_Image(shadow_img[1], -fish_List.get(i).get_Fish_Angle());

                 }else if(fish_List.get(i).get_Class_Num() == 2){   //보스




                     if (fish_List.get(i).get_Fish_Hp() >= 4) {
                         temp_Fish = draw.rotate_Image(fish_Touch_Default_Boss_Hp5_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                     } else if (fish_List.get(i).get_Fish_Hp() >= 3) {
                         temp_Fish = draw.rotate_Image(fish_Touch_Default_Boss_Hp4_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                     } else if (fish_List.get(i).get_Fish_Hp() >= 2) {
                         temp_Fish = draw.rotate_Image(fish_Touch_Default_Boss_Hp3_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                     } else if (fish_List.get(i).get_Fish_Hp() >= 1) {
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

                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());

                //이미지 크기 재 조정
                if(fish_List.get(i).get_Fish_Speed() <= 0) {
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

                    draw.draw_Bmp(canvas, effect_Temp, fish_List.get(smallFishIndex).get_Fish_Point_X() - 15, fish_List.get(smallFishIndex).get_Fish_Point_Y());
                    default_Fish_Hit_Flag = false;
                }



                /**
                 * 오징어 그리기
                 */
            } else if (fish_List.get(i) instanceof Fish_Touch_Squid) {

//                if ((fish_List.get(i)).get_First_Test_Object()) { //첫 번째 오징어 일때
//
//                    draw.draw_Bmp(canvas, explain_Squid_img[fish_List.get(i).get_Draw_Fish_Status()],
//                            fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(0, _context),
//                            fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));
//
//                }


                //그림자 그리기
                draw.draw_Bmp(canvas, shadow_img[4], fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(10, _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(45, _context));

                temp_Fish = draw.rotate_Image(fish_Touch_Squid_img[fish_List.get(i).get_Draw_Fish_Status()], 0);
                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());//convertPixelsToDp(100, _context));

                /**
                 * 드래그로 죽이는 물고기
                 */
            } else if (fish_List.get(i) instanceof Fish_Drag_Default) {


                /**
                 * 드래그 물고기 설명 그림
                 */
//                if ((fish_List.get(i)).get_First_Test_Object()) { //첫 번째 물고기일때
//
//                    draw.draw_Bmp(canvas, explain_Drag_Fish_img[fish_List.get(i).get_Draw_Fish_Status()],
//                            fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(85, _context),
//                            fish_List.get(i).get_Fish_Point_Y() - convertPixelsToDp(20, _context));
//
//                    // 남은 hp 그리기
//                    paint_Temp.setTextSize(convertPixelsToDp(15, _context));
//                    paint_Temp.setColor(Color.RED);
//                    paint_Temp.setStrokeWidth(4);
//                    canvas.drawText("" + fish_List.get(i).get_Fish_Hp(), fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(43, _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(57, _context), paint_Temp);
//
//                }

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
                    }else if(character_Damege == 10) {
                        pop_Temp_img = effect_Pop_damage10_Image[tempInt];
                    }




                    draw.draw_Bmp(canvas, pop_Temp_img,
                            fish_List.get(smallFishIndex).get_Fish_Point_X() + random.nextInt(fish_Drag_Default_img[0].getWidth() - 25),
                            fish_List.get(smallFishIndex).get_Fish_Point_Y() + random.nextInt(fish_Drag_Default_img[0].getHeight()) - 35);




                    drag_Fish_Hit_Flag = false;

                    //이미지 크기 재 조정
                    if(fish_List.get(i).get_Fish_Speed() <= 0) {
                        effect_Slow_img[fish_List.get(i).get_Slow_Effect()] = Bitmap.createScaledBitmap(
                                effect_Slow_img[fish_List.get(i).get_Slow_Effect()],
                                fish_Drag_Default_img[0].getWidth(),
                                fish_Drag_Default_img[0].getHeight(), true);
                    }


                }



            }else if(fish_List.get(i) instanceof Fish_Touch_Ell){


                /**
                 * 전기 뱀장어 그리기
                 */
//                if ((fish_List.get(i)).get_First_Test_Object()) { //첫 번째 전기 뱀장어 일때
//
//                    draw.draw_Bmp(canvas, explain_Ell_img[fish_List.get(i).get_Draw_Fish_Status()],
//                            fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(0, _context),
//                            fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));
//
//                }

                //그림자 그리기
                draw.draw_Bmp(canvas, shadow_img[4], fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(15, _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(45, _context));


                if(fish_List.get(i).get_Attack_Mode()) {
                    temp_Fish = draw.rotate_Image(fish_Touch_Ell_img[fish_List.get(i).get_Draw_Fish_Status()], 0);
                    draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));
                }else if(!fish_List.get(i).get_Attack_Mode()){
                    temp_Fish = draw.rotate_Image(fish_Touch_Ell_Attack_img[fish_List.get(i).get_Draw_Fish_Status()], 0);
                    draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));
                }
            }else if(fish_List.get(i) instanceof Fish_Touch_Marlin){
                /**
                 * 청새치 그리기
                 */
//                if ((fish_List.get(i)).get_First_Test_Object()) { //첫 번째 청새치 일때.
//                    draw.draw_Bmp(canvas, explain_Ell_img[fish_List.get(i).get_Draw_Fish_Status()],
//                            fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(0, _context),
//                            fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));

                draw.draw_Bmp(canvas, fish_Touch_Marlin_img[fish_List.get(i).get_Draw_Fish_Status()],
                        fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(0, _context),
                        fish_List.get(i).get_Fish_Point_Y());//- convertPixelsToDp(100, _context));
//               }
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
                if(!(fish_List.get(i) instanceof Fish_Trap_Jellyfish)) {
                    draw.draw_Bmp(canvas, effect_Slow_img[0],
                            fish_List.get(i).get_Fish_Point_X(),
                            fish_List.get(i).get_Fish_Point_Y() - convertPixelsToDp(13, _context));


                    draw.draw_Bmp(canvas, effect_Slow_img[fish_List.get(i).get_Slow_Effect()],
                            fish_List.get(i).get_Fish_Point_X(),
                            fish_List.get(i).get_Fish_Point_Y());
                }
            }

            //독 상태 이상에 걸린 객체에 포이즌 마크를 표시한다.
            if(fish_List.get(i).get_Status_Poison()){
                fish_List.get(i).set_Status_Poison_AttacK();

                if(fish_List.get(i) instanceof Fish_Touch_Default) {
                    draw.draw_Bmp(canvas, effect_Poison_img[fish_List.get(i).get_Status_Poison_AttacK()], ///중독된 물고기 hp 감속과 동시에 이미지 표현
                            fish_List.get(i).get_Fish_Point_X() - effect_Poison_img[0].getWidth(),
                            fish_List.get(i).get_Fish_Point_Y() - convertPixelsToDp(13, _context));
                }else {
                    draw.draw_Bmp(canvas, effect_Poison_img[fish_List.get(i).get_Status_Poison_AttacK()], ///중독된 물고기 hp 감속과 동시에 이미지 표현
                            fish_List.get(i).get_Fish_Point_X(),
                            fish_List.get(i).get_Fish_Point_Y() - convertPixelsToDp(13, _context));
                }

                draw.draw_Bmp(canvas, effect_Poison_img[0],
                        fish_List.get(i).get_Fish_Point_X(),
                        fish_List.get(i).get_Fish_Point_Y() - convertPixelsToDp(13, _context));
            }



        }
        }




        //집게발 그리기
        for(int i=0; i<skill_Crab_Claws_List.size(); i++){


            draw.draw_Bmp(canvas, skill_Crab_img[skill_Crab_Claws_List.get(i).get_Skill_Status()], skill_Crab_Claws_List.get(i).get_X_Point(), skill_Crab_Claws_List.get(i).get_Y_Point());
            skill_Crab_Claws_List.get(i).set_Skill_Move();


            if(skill_Crab_Claws_List.get(i).get_Live()){

                //범위 안에 드는 몬스터들의 체력을 떨군다.
                for(int j=0; j<fish_List.size(); j++){

                    if (skill_Crab_Claws_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Crab_Claws_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Crab_Claws_List.get(i).get_Y_Point()
                                && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Crab_Claws_List.get(i).get_Y_Point() + skill_Crab_img[0].getHeight()) {
                            fish_List.get(j).set_Hp_Minus(50);

                        }
                    } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Crab_Claws_List.get(i).get_X_Point() + skill_Crab_img[0].getWidth()
                            && fish_List.get(j).get_Fish_Point_X() >= skill_Crab_Claws_List.get(i).get_X_Point()
                            ) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Crab_Claws_List.get(i).get_Y_Point()
                                && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Crab_Claws_List.get(i).get_Y_Point() + skill_Crab_img[0].getHeight()) {

                            fish_List.get(j).set_Hp_Minus(50);

                        }
                    }



                }


                for(int j=0; j<ground_List.size(); j++){

                    if (skill_Crab_Claws_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Crab_Claws_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Crab_Claws_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Crab_Claws_List.get(i).get_Y_Point() + skill_Crab_img[0].getHeight()) {
                            ground_List.get(j).set_Ground_Hp_Minus(100);

                        }
                    } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Crab_Claws_List.get(i).get_X_Point() + skill_Crab_img[0].getWidth()
                            && ground_List.get(j).get_Ground_Point_X() >= skill_Crab_Claws_List.get(i).get_X_Point()
                            ) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Crab_Claws_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Crab_Claws_List.get(i).get_Y_Point() + skill_Crab_img[0].getHeight()) {

                            ground_List.get(j).set_Ground_Hp_Minus(100);

                        }
                    }


                }

                skill_Crab_Claws_List.remove(i);
            }
        }

        //간장게장 집게발
        for(int i=0; i<skill_Soycrab_Claws_List.size(); i++){

            draw.draw_Bmp(canvas, skill_Soycrab_img[skill_Soycrab_Claws_List.get(i).get_Skill_Status()], skill_Soycrab_Claws_List.get(i).get_X_Point(), skill_Soycrab_Claws_List.get(i).get_Y_Point());
            skill_Soycrab_Claws_List.get(i).set_Skill_Move();

            if(skill_Soycrab_Claws_List.get(i).get_Live()){

                //범위 안에 드는 몬스터들의 체력을 떨군다.
                for(int j=0; j<fish_List.size(); j++){

                    if (skill_Soycrab_Claws_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Soycrab_Claws_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Soycrab_Claws_List.get(i).get_Y_Point()
                                && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Soycrab_Claws_List.get(i).get_Y_Point() + skill_Soycrab_img[0].getHeight()) {
                            fish_List.get(j).set_Hp_Minus(50);

                        }
                    } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Soycrab_Claws_List.get(i).get_X_Point() + skill_Soycrab_img[0].getWidth()
                            && fish_List.get(j).get_Fish_Point_X() >= skill_Soycrab_Claws_List.get(i).get_X_Point()
                            ) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Soycrab_Claws_List.get(i).get_Y_Point()
                                && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Soycrab_Claws_List.get(i).get_Y_Point() + skill_Soycrab_img[0].getHeight()) {

                            fish_List.get(j).set_Hp_Minus(50);

                        }
                    }


                }
                for(int j=0; j<ground_List.size(); j++){


                    if (skill_Soycrab_Claws_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Soycrab_Claws_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Soycrab_Claws_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Soycrab_Claws_List.get(i).get_Y_Point() + skill_Soycrab_img[0].getHeight()) {
                            ground_List.get(j).set_Ground_Hp_Minus(100);

                        }
                    } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Soycrab_Claws_List.get(i).get_X_Point() + skill_Soycrab_img[0].getWidth()
                            && ground_List.get(j).get_Ground_Point_X() >= skill_Soycrab_Claws_List.get(i).get_X_Point()
                            ) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Soycrab_Claws_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Soycrab_Claws_List.get(i).get_Y_Point() + skill_Soycrab_img[0].getHeight()) {

                            ground_List.get(j).set_Ground_Hp_Minus(100);

                        }
                    }

                }

                skill_Soycrab_Claws_List.remove(i);
            }
        }


        //스톰프
        for(int i=0; i<skill_Stomp_List.size(); i++){

            draw.draw_Bmp(canvas, skill_stomp_img[skill_Stomp_List.get(i).get_Skill_Status()], skill_Stomp_List.get(i).get_X_Point(), skill_Stomp_List.get(i).get_Y_Point());
            skill_Stomp_List.get(i).set_Skill_Move();


            if(skill_Stomp_List.get(i).get_Live()) {
                //범위 안에 드는 몬스터들의 체력을 떨군다.
                for (int j = 0; j < fish_List.size(); j++) {


                    if (skill_Stomp_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Stomp_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Stomp_List.get(i).get_Y_Point()
                                && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Stomp_List.get(i).get_Y_Point() + skill_stomp_img[0].getHeight()) {
                            fish_List.get(j).set_Hp_Minus(50);

                        }
                    } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Stomp_List.get(i).get_X_Point() + skill_stomp_img[0].getWidth()
                            && fish_List.get(j).get_Fish_Point_X() >= skill_Stomp_List.get(i).get_X_Point()
                            ) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Stomp_List.get(i).get_Y_Point()
                                && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Stomp_List.get(i).get_Y_Point() + skill_stomp_img[0].getHeight()) {

                            fish_List.get(j).set_Hp_Minus(50);

                        }
                    }


//

                }


                for (int j = 0; j < ground_List.size(); j++) {

                    if (skill_Stomp_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Stomp_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Stomp_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Stomp_List.get(i).get_Y_Point() + skill_stomp_img[0].getHeight()) {
                            ground_List.get(j).set_Ground_Hp_Minus(50);

                        }
                    } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Stomp_List.get(i).get_X_Point() + skill_stomp_img[0].getWidth()
                            && ground_List.get(j).get_Ground_Point_X() >= skill_Stomp_List.get(i).get_X_Point()
                            ) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Stomp_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Stomp_List.get(i).get_Y_Point() + skill_stomp_img[0].getHeight()) {

                            ground_List.get(j).set_Ground_Hp_Minus(50);

                        }
                    }

//
                }

                skill_Stomp_List.remove(i);
            }
        }




        //가시
        for(int i=0; i<skill_Thorn_List.size(); i++){
            draw.draw_Bmp(canvas, skill_Thorn_img[skill_Thorn_List.get(i).get_Skill_Status()], skill_Thorn_List.get(i).get_X_Point(), skill_Thorn_List.get(i).get_Y_Point());
            skill_Thorn_List.get(i).set_Skill_Move();


            if(skill_Thorn_List.get(i).get_Live()){
                skill_Thorn_List.remove(i);
            }

        }

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


                if (skill_poison_cloud_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_poison_cloud_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_poison_cloud_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_poison_cloud_List.get(i).get_Y_Point() + pop_Temp_img.getHeight()) {
                        fish_List.get(j).set_Status_Poison(10);

                    }
                } else if (fish_List.get(j).get_Fish_Point_X() <= skill_poison_cloud_List.get(i).get_X_Point() + pop_Temp_img.getWidth()
                        && fish_List.get(j).get_Fish_Point_X() >= skill_poison_cloud_List.get(i).get_X_Point()
                        ) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_poison_cloud_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_poison_cloud_List.get(i).get_Y_Point() + pop_Temp_img.getHeight()) {

                        fish_List.get(j).set_Status_Poison(10);

                    }
                }


            }


            for(int j=0; j<ground_List.size(); j++){


                if (skill_poison_cloud_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_poison_cloud_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_poison_cloud_List.get(i).get_Y_Point()
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_poison_cloud_List.get(i).get_Y_Point() + pop_Temp_img.getHeight()) {
                        ground_List.get(j).set_Status_Poison(10);

                    }
                } else if (ground_List.get(j).get_Ground_Point_X() <= skill_poison_cloud_List.get(i).get_X_Point() + pop_Temp_img.getWidth()
                        && ground_List.get(j).get_Ground_Point_X() >= skill_poison_cloud_List.get(i).get_X_Point()
                        ) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_poison_cloud_List.get(i).get_Y_Point()
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_poison_cloud_List.get(i).get_Y_Point() + pop_Temp_img.getHeight()) {

                        ground_List.get(j).set_Status_Poison(10);

                    }
                }


            }


            if(skill_poison_cloud_List.get(i).get_Live()){
                skill_poison_cloud_List.remove(i);
            }

        }



        //지진 공격
        for(int i=0; i<skill_Earthquake_List.size(); i++){

            pop_Temp_img = draw.rotate_Image(skill_earthquake_img[skill_Earthquake_List.get(i).get_Skill_Status()], skill_Earthquake_List.get(i).get_Angle());
            draw.draw_Bmp(canvas, pop_Temp_img, skill_Earthquake_List.get(i).get_X_Point(), skill_Earthquake_List.get(i).get_Y_Point());


            skill_Earthquake_List.get(i).set_Skill_Move();



            if(skill_Earthquake_List.get(i).get_Live()){

            for (int j = 0; j < fish_List.size(); j++) {
                if (skill_Earthquake_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Earthquake_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Earthquake_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Earthquake_List.get(i).get_Y_Point() + skill_earthquake_img[0].getHeight()) {
                        fish_List.get(j).set_Hp_Minus(100);

                    }
                } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Earthquake_List.get(i).get_X_Point() + skill_earthquake_img[0].getWidth()
                        && fish_List.get(j).get_Fish_Point_X() >= skill_Earthquake_List.get(i).get_X_Point()
                        ) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Earthquake_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Earthquake_List.get(i).get_Y_Point() + skill_earthquake_img[0].getHeight()) {

                        fish_List.get(j).set_Hp_Minus(100);

                    }
                }
            }


                for (int j = 0; j < ground_List.size(); j++) {
                    if (skill_Earthquake_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Earthquake_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Earthquake_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Earthquake_List.get(i).get_Y_Point() + skill_earthquake_img[0].getHeight()) {
                            ground_List.get(j).set_Ground_Hp_Minus(100);

                        }
                    } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Earthquake_List.get(i).get_X_Point() + skill_earthquake_img[0].getWidth()
                            && ground_List.get(j).get_Ground_Point_X() >= skill_Earthquake_List.get(i).get_X_Point()
                            ) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Earthquake_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Earthquake_List.get(i).get_Y_Point() + skill_earthquake_img[0].getHeight()) {

                            ground_List.get(j).set_Ground_Hp_Minus(100);

                        }
                    }
                }




            }
            if(skill_Earthquake_List.get(i).get_Live()){
                skill_Earthquake_List.remove(i);
            }
        }



        //이빨 지뢰
        for(int i=0; i< skill_Teeth_Mine_List.size(); i++){



            draw.draw_Bmp(canvas, skill_Teeth_mine_img[skill_Teeth_Mine_List.get(i).get_Skill_Status()], skill_Teeth_Mine_List.get(i).get_X_Point() -  - convertPixelsToDp(15, _context), skill_Teeth_Mine_List.get(i).get_Y_Point());
            skill_Teeth_Mine_List.get(i).set_Skill_Move();

            for (int j = 0; j < fish_List.size(); j++) {
                if (skill_Teeth_Mine_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Teeth_Mine_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Teeth_Mine_List.get(i).get_Y_Point() + convertPixelsToDp(35, _context)
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Teeth_Mine_List.get(i).get_Y_Point() + skill_Teeth_mine_img[0].getHeight()) {
                        fish_List.get(j).set_Hp_Minus(10);
                        skill_Teeth_Mine_List.get(i).set_play_Attack();
                        break;
                    }
                } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Teeth_Mine_List.get(i).get_X_Point() + skill_Teeth_mine_img[0].getWidth()
                        && fish_List.get(j).get_Fish_Point_X() >= skill_Teeth_Mine_List.get(i).get_X_Point()
                        ) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Teeth_Mine_List.get(i).get_Y_Point() + convertPixelsToDp(35, _context)
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Teeth_Mine_List.get(i).get_Y_Point() + skill_Teeth_mine_img[0].getHeight()) {

                        fish_List.get(j).set_Hp_Minus(10);
                        skill_Teeth_Mine_List.get(i).set_play_Attack();
                        break;
                    }
                }
            }

            for (int j = 0; j < ground_List.size(); j++) {
                if (skill_Teeth_Mine_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Teeth_Mine_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Teeth_Mine_List.get(i).get_Y_Point() + convertPixelsToDp(35, _context)
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Teeth_Mine_List.get(i).get_Y_Point() + skill_Teeth_mine_img[0].getHeight()) {

                        ground_List.get(j).set_Ground_Hp_Minus(10);
                        skill_Teeth_Mine_List.get(i).set_play_Attack();
                        break;
                    }
                } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Teeth_Mine_List.get(i).get_X_Point() + skill_Teeth_mine_img[0].getWidth()
                        && ground_List.get(j).get_Ground_Point_X() >= skill_Teeth_Mine_List.get(i).get_X_Point()
                        ) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Teeth_Mine_List.get(i).get_Y_Point() + convertPixelsToDp(35, _context)
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Teeth_Mine_List.get(i).get_Y_Point() + skill_Teeth_mine_img[0].getHeight()) {

                        ground_List.get(j).set_Ground_Hp_Minus(10);
                        skill_Teeth_Mine_List.get(i).set_play_Attack();
                        break;
                    }
                }
            }



            if(skill_Teeth_Mine_List.get(i).get_Delete_Attack()){
                skill_Teeth_Mine_List.remove(i);
            }


        }


        //이빨 지뢰2
        for(int i=0; i<skill_Teeth_Mine2_List.size(); i++){



            if(skill_Teeth_Mine2_List.get(i).get_Skill_Status() == 0) {
                draw.draw_Bmp(canvas, skill_Teeth_mine2_img[0], skill_Teeth_Mine2_List.get(i).get_X_Point() - convertPixelsToDp(15, _context), skill_Teeth_Mine2_List.get(i).get_Y_Point());
            }else {
                draw.draw_Bmp(canvas, skill_Teeth_mine2_img[skill_Teeth_Mine2_List.get(i).get_Skill_Status()], skill_Teeth_Mine2_List.get(i).get_X_Point() - convertPixelsToDp(75, _context), skill_Teeth_Mine2_List.get(i).get_Y_Point());
            }
            skill_Teeth_Mine2_List.get(i).set_Skill_Move();



            for (int j = 0; j < fish_List.size(); j++) {
                if (skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(15, _context) >= fish_List.get(j).get_Fish_Point_X() && skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(15, _context) <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Teeth_Mine2_List.get(i).get_Y_Point() + convertPixelsToDp(15, _context)
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[0].getHeight()) {

                        skill_Teeth_Mine2_List.get(i).set_play_Attack();
                        break;
                    }
                } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(15, _context) + skill_Teeth_mine2_img[0].getWidth()
                        && fish_List.get(j).get_Fish_Point_X() >= skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(15, _context)
                        ) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Teeth_Mine2_List.get(i).get_Y_Point() + convertPixelsToDp(15, _context)
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[0].getHeight()) {


                        skill_Teeth_Mine2_List.get(i).set_play_Attack();
                        break;
                    }
                }
            }
            if(skill_Teeth_Mine2_List.get(i).get_Play_Attack()){
                for (int j = 0; j < fish_List.size(); j++) {
                    if (skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(75, _context) >= fish_List.get(j).get_Fish_Point_X() && skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(75, _context) <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Teeth_Mine2_List.get(i).get_Y_Point()
                                && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[3].getHeight()) {
                            fish_List.get(j).set_Hp_Minus(100);

                        }
                    } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(75, _context) + skill_Teeth_mine2_img[3].getWidth()
                            && fish_List.get(j).get_Fish_Point_X() >= skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(75, _context)
                            ) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Teeth_Mine2_List.get(i).get_Y_Point()
                                && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[3].getHeight()) {

                            fish_List.get(j).set_Hp_Minus(100);

                        }
                    }
                }
            }



            for (int j = 0; j < ground_List.size(); j++) {
                if (skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(15, _context) >= ground_List.get(j).get_Ground_Point_X() && skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(15, _context) <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Teeth_Mine2_List.get(i).get_Y_Point() + convertPixelsToDp(15, _context)
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[0].getHeight()) {

                        skill_Teeth_Mine2_List.get(i).set_play_Attack();
                        break;
                    }
                } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(15, _context) + skill_Teeth_mine2_img[0].getWidth()
                        && ground_List.get(j).get_Ground_Point_X() >= skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(15, _context)
                        ) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Teeth_Mine2_List.get(i).get_Y_Point() + convertPixelsToDp(15, _context)
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[0].getHeight()) {


                        skill_Teeth_Mine2_List.get(i).set_play_Attack();
                        break;
                    }
                }
            }
            if(skill_Teeth_Mine2_List.get(i).get_Play_Attack()){
                for (int j = 0; j < ground_List.size(); j++) {
                    if (skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(75, _context) >= ground_List.get(j).get_Ground_Point_X() && skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(75, _context) <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Teeth_Mine2_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[3].getHeight()) {
                            ground_List.get(j).set_Ground_Hp_Minus(100);

                        }
                    } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(75, _context) + skill_Teeth_mine2_img[3].getWidth()
                            && ground_List.get(j).get_Ground_Point_X() >= skill_Teeth_Mine2_List.get(i).get_X_Point()- convertPixelsToDp(75, _context)
                            ) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Teeth_Mine2_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Teeth_Mine2_List.get(i).get_Y_Point() + skill_Teeth_mine2_img[3].getHeight()) {

                            ground_List.get(j).set_Ground_Hp_Minus(100);

                        }
                    }
                }
            }



    if (skill_Teeth_Mine2_List.get(i).get_Delete_Attack()) {
        skill_Teeth_Mine2_List.remove(i);
    }







        }



        //버터
        for(int i=0; i<skill_Butter_List.size(); i++){
            if(skill_Butter_List.get(i).get_Skill_Status() <= 0) {

                draw.draw_Bmp(canvas, skill_Butter_img[0], skill_Butter_List.get(i).get_X_Point(), skill_Butter_List.get(i).get_Y_Point() - convertPixelsToDp(25, _context) );
            }else{
                draw.draw_Bmp(canvas, skill_Butter_img[skill_Butter_List.get(i).get_Skill_Status()], skill_Butter_List.get(i).get_X_Point(), skill_Butter_List.get(i).get_Y_Point()- convertPixelsToDp(25, _context) );
            }


            for (int j = 0; j < fish_List.size(); j++) {
                if (skill_Butter_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Butter_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Butter_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Butter_List.get(i).get_Y_Point() + skill_Butter_img[0].getHeight()) {
                        skill_Butter_List.get(i).set_play_Attack();
                        fish_List.get(j).set_Fish_Speed(0);
                    }
                } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Butter_List.get(i).get_X_Point() + skill_Butter_img[0].getWidth()
                        && fish_List.get(j).get_Fish_Point_X() >= skill_Butter_List.get(i).get_X_Point()
                        ) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Butter_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Butter_List.get(i).get_Y_Point() + skill_Butter_img[0].getHeight()) {

                        skill_Butter_List.get(i).set_play_Attack();
                        fish_List.get(j).set_Fish_Speed(0);
                    }
                }
            }

            for(int j=0; j<ground_List.size(); j++){

                if (skill_Butter_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Butter_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {
                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Butter_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context)
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Butter_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context) + skill_Butter_img[0].getHeight()) {
                        skill_Butter_List.get(i).set_play_Attack();
                        ground_List.get(j).set_Ground_Speed(0);
                    }
                } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Butter_List.get(i).get_X_Point() + skill_Butter_img[0].getWidth()
                        && ground_List.get(j).get_Ground_Point_X() >= skill_Butter_List.get(i).get_X_Point()
                        ) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Butter_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context)
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Butter_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context) + skill_Butter_img[0].getHeight()) {
                        skill_Butter_List.get(i).set_play_Attack();
                        ground_List.get(j).set_Ground_Speed(0);
                    }
                }

            }


            if(skill_Butter_List.get(i).get_Live()){
                skill_Butter_List.remove(i);
            }


        }




        //튀김 지뢰
        for(int i=0; i<skill_Fry_List.size(); i++){


            if(skill_Fry_List.get(i).get_Skill_Status() <= 2) {
                draw.draw_Bmp(canvas, skill_fry_img[skill_Fry_List.get(i).get_Skill_Status()], skill_Fry_List.get(i).get_X_Point() - convertPixelsToDp(15, _context), skill_Fry_List.get(i).get_Y_Point());
            }else{
                draw.draw_Bmp(canvas, skill_fry_img[skill_Fry_List.get(i).get_Skill_Status()], skill_Fry_List.get(i).get_X_Point() - convertPixelsToDp(55, _context), skill_Fry_List.get(i).get_Y_Point());
            }


            skill_Fry_List.get(i).set_Skill_Move();

            for (int j = 0; j < fish_List.size(); j++) {
                if (skill_Fry_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Fry_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Fry_List.get(i).get_Y_Point()
                     && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[0].getHeight()) {
                        skill_Fry_List.get(i).set_play_Attack();
                    }
                } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Fry_List.get(i).get_X_Point() + skill_fry_img[0].getWidth()
                        && fish_List.get(j).get_Fish_Point_X() >= skill_Fry_List.get(i).get_X_Point()
                        ) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Fry_List.get(i).get_Y_Point()
                     && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[0].getHeight()) {

                        skill_Fry_List.get(i).set_play_Attack();
                    }
                }
            }

            if(skill_Fry_List.get(i).get_Play_Attack()) {

                for (int j = 0; j < fish_List.size(); j++) {
                    if (skill_Fry_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Fry_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Fry_List.get(i).get_Y_Point()
                         && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[3].getHeight()) {
                            fish_List.get(j).set_Hp_Minus(20);
                        }
                    } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Fry_List.get(i).get_X_Point() + skill_fry_img[3].getWidth()
                            && fish_List.get(j).get_Fish_Point_X() >= skill_Fry_List.get(i).get_X_Point()
                            ) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Fry_List.get(i).get_Y_Point()
                         && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[3].getHeight()) {
                            fish_List.get(j).set_Hp_Minus(20);
                        }
                    }
                }
            }


                for (int j = 0; j < ground_List.size(); j++) {
                    if (skill_Fry_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Fry_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {
                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Fry_List.get(i).get_Y_Point()
                         && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[0].getHeight()) {
                            skill_Fry_List.get(i).set_play_Attack();
                        }
                    } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Fry_List.get(i).get_X_Point() + skill_fry_img[0].getWidth()
                            && ground_List.get(j).get_Ground_Point_X() >= skill_Fry_List.get(i).get_X_Point()
                            ) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Fry_List.get(i).get_Y_Point()
                         && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[0].getHeight()) {
                            skill_Fry_List.get(i).set_play_Attack();
                        }
                    }
                }

            if(skill_Fry_List.get(i).get_Play_Attack()) {

                for (int j = 0; j < ground_List.size(); j++) {
                    if (skill_Fry_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Fry_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {
                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Fry_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[3].getHeight()) {
                            ground_List.get(j).set_Ground_Hp_Minus(20);
                        }
                    } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Fry_List.get(i).get_X_Point() + skill_fry_img[3].getWidth()
                            && ground_List.get(j).get_Ground_Point_X() >= skill_Fry_List.get(i).get_X_Point()
                            ) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Fry_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Fry_List.get(i).get_Y_Point() + skill_fry_img[3].getHeight()) {
                            ground_List.get(j).set_Ground_Hp_Minus(20);
                        }
                    }
                }
            }

            if(skill_Fry_List.get(i).get_Delete_Attack()){
                skill_Fry_List.remove(i);
            }


        }



        //바다뱀 소환
        for(int i=0; i<skill_Sea_Snake_List.size(); i++){

            draw.draw_Bmp(canvas, skill_Sea_Snake_img[0], skill_Sea_Snake_List.get(i).get_X_Point(), skill_Sea_Snake_List.get(i).get_Y_Point());
            skill_Sea_Snake_List.get(i).set_Skill_Move(convertPixelsToDp(60, _context));





            for(int j=0; j<fish_List.size(); j++){

                if (skill_Sea_Snake_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Sea_Snake_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {
                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Sea_Snake_List.get(i).get_Y_Point() + convertPixelsToDp(30, _context)
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Sea_Snake_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context) + skill_Sea_Snake_img[0].getHeight()) {
                        fish_List.get(j).set_Hp_Minus(5);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                    }
                } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Sea_Snake_List.get(i).get_X_Point() + skill_Sea_Snake_img[0].getWidth()
                        && fish_List.get(j).get_Fish_Point_X() >= skill_Sea_Snake_List.get(i).get_X_Point()
                        ) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Sea_Snake_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context)
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Sea_Snake_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context) + skill_Sea_Snake_img[0].getHeight()) {
                        fish_List.get(j).set_Hp_Minus(5);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                    }
                }




            }
            for(int j=0; j<ground_List.size(); j++){

                if (skill_Sea_Snake_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Sea_Snake_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {
                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Sea_Snake_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context)
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Sea_Snake_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context) + skill_Sea_Snake_img[0].getHeight()) {
                        ground_List.get(j).set_Ground_Hp_Minus(5);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                    }
                } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Sea_Snake_List.get(i).get_X_Point() + skill_Sea_Snake_img[0].getWidth()
                        && ground_List.get(j).get_Ground_Point_X() >= skill_Sea_Snake_List.get(i).get_X_Point()
                        ) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Sea_Snake_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context)
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Sea_Snake_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context) + skill_Sea_Snake_img[0].getHeight()) {
                        ground_List.get(j).set_Ground_Hp_Minus(5);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                    }
                }

            }






            if(skill_Sea_Snake_List.get(i).get_Live()){
                skill_Sea_Snake_List.remove(i);
            }


        }

        //파도 소환
        for(int i=0; i<skill_Wave_List.size(); i++){

            draw.draw_Bmp(canvas, skill_Wave_img[skill_Wave_List.get(i).get_Skill_Status()], skill_Wave_List.get(i).get_X_Point(), skill_Wave_List.get(i).get_Y_Point());

            skill_Wave_List.get(i).set_Skill_Move(convertPixelsToDp(30, _context));



            for(int j=0; j<fish_List.size(); j++){


                if (skill_Wave_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Wave_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Wave_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Wave_List.get(i).get_Y_Point() + skill_wall_img[0].getHeight()) {
                        fish_List.get(j).set_Hp_Minus(1);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드

                    }
                } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Wave_List.get(i).get_X_Point() + skill_wall_img[0].getWidth()
                        && fish_List.get(j).get_Fish_Point_X() >= skill_Wave_List.get(i).get_X_Point()
                        ) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Wave_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Wave_List.get(i).get_Y_Point() + skill_wall_img[0].getHeight()) {

                        fish_List.get(j).set_Hp_Minus(1);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드

                    }
                }




            }


            for(int j=0; j<ground_List.size(); j++){

                if (skill_Wave_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Wave_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Wave_List.get(i).get_Y_Point()
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Wave_List.get(i).get_Y_Point() + skill_earthquake_img[0].getHeight()) {
                        ground_List.get(j).set_Ground_Hp_Minus(1);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드

                    }
                } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Wave_List.get(i).get_X_Point() + skill_earthquake_img[0].getWidth()
                        && ground_List.get(j).get_Ground_Point_X() >= skill_Wave_List.get(i).get_X_Point()
                        ) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Wave_List.get(i).get_Y_Point()
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Wave_List.get(i).get_Y_Point() + skill_earthquake_img[0].getHeight()) {

                        ground_List.get(j).set_Ground_Hp_Minus(1);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드

                    }
                }



            }



            if(skill_Wave_List.get(i).get_Live()){
                skill_Wave_List.remove(i);
            }
        }


        //벽 소환
        for(int i=0; i<skill_Wall_List.size(); i++){

            draw.draw_Bmp(canvas, skill_wall_img[skill_Wall_List.get(i).get_Skill_Status()], skill_Wall_List.get(i).get_X_Point(), skill_Wall_List.get(i).get_Y_Point()  + convertPixelsToDp(30, _context) );
            skill_Wall_List.get(i).set_Skill_Move();


            for(int j=0; j<fish_List.size(); j++){

                if (skill_Wall_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Wall_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                    if (fish_List.get(j).get_Fish_Point_Y() >= skill_Wall_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y() <= skill_Wall_List.get(i).get_Y_Point() + skill_wall_img[3].getHeight()) {
                        fish_List.get(j).set_Fish_Speed(0);

                    }
                } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Wall_List.get(i).get_X_Point() + skill_wall_img[3].getWidth()
                        && fish_List.get(j).get_Fish_Point_X() >= skill_Wall_List.get(i).get_X_Point()
                        ) {

                    if (fish_List.get(j).get_Fish_Point_Y() >= skill_Wall_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y()<= skill_Wall_List.get(i).get_Y_Point() + skill_wall_img[3].getHeight()) {

                        fish_List.get(j).set_Fish_Speed(0);

                    }
                }

            }
            for(int j=0; j<ground_List.size(); j++){

                if (skill_Wall_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Wall_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                    if (ground_List.get(j).get_Ground_Point_Y()>= skill_Wall_List.get(i).get_Y_Point()
                            && ground_List.get(j).get_Ground_Point_Y()<= skill_Wall_List.get(i).get_Y_Point() + skill_wall_img[3].getHeight()) {
                        ground_List.get(j).set_Ground_Speed(0);

                    }
                } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Wall_List.get(i).get_X_Point() + skill_wall_img[3].getWidth()
                        && ground_List.get(j).get_Ground_Point_X() >= skill_Wall_List.get(i).get_X_Point()
                        ) {

                    if (ground_List.get(j).get_Ground_Point_Y() >= skill_Wall_List.get(i).get_Y_Point()
                            && ground_List.get(j).get_Ground_Point_Y() <= skill_Wall_List.get(i).get_Y_Point() + skill_wall_img[3].getHeight()) {

                        ground_List.get(j).set_Ground_Speed(0);

                    }
                }

            }


            if(skill_Wall_List.get(i).get_Live()){
                skill_Wall_List.remove(i);
            }

        }

        //가시2 소환
        for(int i=0; i<skill_Thorn2_List.size(); i++){

            draw.draw_Bmp(canvas, skill_Thorn2_img[skill_Thorn2_List.get(i).get_Skill_Status()], skill_Thorn2_List.get(i).get_X_Point(), skill_Thorn2_List.get(i).get_Y_Point());
            skill_Thorn2_List.get(i).set_Skill_Move(convertPixelsToDp(30, _context));



            for(int j=0; j<fish_List.size(); j++){
                if(skill_Thorn2_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Thorn2_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()){
                    if(fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Thorn2_List.get(i).get_Y_Point() && skill_Thorn2_List.get(i).get_Y_Point() <= fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size()){
                        fish_List.get(j).set_Hp_Minus(10);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                        skill_Thorn2_List.get(i).set_Live();
                    }
                }else if(fish_List.get(j).get_Fish_Point_X() <= skill_Thorn2_List.get(i).get_X_Point() + skill_Thorn2_img[0].getWidth()
                        && skill_Thorn2_List.get(i).get_X_Point() + skill_Thorn2_img[0].getWidth()  <= fish_List.get(j).get_Fish_Point_X()
                        ){

                    if(fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Thorn2_List.get(i).get_Y_Point() && skill_Thorn2_List.get(i).get_Y_Point() <= fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size()){
                        fish_List.get(j).set_Hp_Minus(10);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                        skill_Thorn2_List.get(i).set_Live();
                    }
                }
            }

            for(int j=0; j<ground_List.size(); j++){
                if(!(ground_List.get(j) instanceof Land_Mark)) {
                    if (skill_Thorn2_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Thorn2_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {
                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Thorn2_List.get(i).get_Y_Point() && skill_Thorn2_List.get(i).get_Y_Point() <= ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size()) {
                            ground_List.get(j).set_Ground_Hp_Minus(10);
                            soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                            skill_Thorn2_List.get(i).set_Live();
                        }
                    } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Thorn2_List.get(i).get_X_Point() + skill_Thorn2_img[0].getWidth()
                            && skill_Thorn2_List.get(i).get_X_Point() + skill_Thorn2_img[0].getWidth() <= ground_List.get(j).get_Ground_Point_X()
                            ) {

                        if (ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Height_Size() >= skill_Thorn2_List.get(i).get_Y_Point() && skill_Thorn2_List.get(i).get_Y_Point() <= ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size()) {
                            ground_List.get(j).set_Ground_Hp_Minus(10);
                            soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                            skill_Thorn2_List.get(i).set_Live();
                        }
                    }
                }
            }


//

            if(skill_Thorn2_List.get(i).get_Live()){
                skill_Thorn2_List.remove(i);
            }

        }


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

                if(skill_Lightning_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Lightning_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()){
                    if(fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Lightning_List.get(i).get_Y_Point() && skill_Lightning_List.get(i).get_Y_Point() <= fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size()){
                        fish_List.get(j).set_Hp_Minus(10);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                        skill_Lightning_List.get(i).set_play_Attack();
                    }
                }else if(fish_List.get(j).get_Fish_Point_X() <= skill_Lightning_List.get(i).get_X_Point() + skill_lightnign_img[0].getWidth()
                        && skill_Lightning_List.get(i).get_X_Point() + skill_lightnign_img[0].getWidth()  <= fish_List.get(j).get_Fish_Point_X()
                        ){

                    if(fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Lightning_List.get(i).get_Y_Point() && skill_Lightning_List.get(i).get_Y_Point() <= fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size()){
                        fish_List.get(j).set_Hp_Minus(10);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                        skill_Lightning_List.get(i).set_play_Attack();
                    }
                }

            }

                    if(skill_Lightning_List.get(i).get_play_Attack()){


                        for(int j=0; j<fish_List.size(); j++){

                            if(skill_Lightning_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Lightning_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()){
                                if(fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Lightning_List.get(i).get_Y_Point() && skill_Lightning_List.get(i).get_Y_Point() <= fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size()){
                                    fish_List.get(j).set_Hp_Minus(30);
                                }
                            }else if(fish_List.get(j).get_Fish_Point_X() <= skill_Lightning_List.get(i).get_X_Point() + skill_lightnign_img[0].getWidth()
                                    && skill_Lightning_List.get(i).get_X_Point() + skill_lightnign_img[0].getWidth()  <= fish_List.get(j).get_Fish_Point_X()
                                    ){

                                if(fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Lightning_List.get(i).get_Y_Point() && skill_Lightning_List.get(i).get_Y_Point() <= fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size()){
                                    fish_List.get(j).set_Hp_Minus(30);
                                }
                            }

//
                        }
                    }

            for(int j=0; j<ground_List.size(); j++){

                if(!(ground_List.get(j) instanceof Land_Mark)){

                    if(skill_Lightning_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Lightning_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()){
                        if(ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Lightning_List.get(i).get_Y_Point() && skill_Lightning_List.get(i).get_Y_Point() <= ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size()){
                            skill_Lightning_List.get(i).set_play_Attack();
                        }
                    }else if(ground_List.get(j).get_Ground_Point_X() <= skill_Lightning_List.get(i).get_X_Point() + skill_lightnign_img[0].getWidth()
                            && skill_Lightning_List.get(i).get_X_Point() + skill_lightnign_img[0].getWidth()  <= ground_List.get(j).get_Ground_Point_Y()
                            ){

                        if(ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Lightning_List.get(i).get_Y_Point() && skill_Lightning_List.get(i).get_Y_Point() <= ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size()){
                            skill_Lightning_List.get(i).set_play_Attack();
                        }
                    }

                }

            }




            if(skill_Lightning_List.get(i).get_play_Attack()){
                for(int j=0; j<ground_List.size(); j++){

                    if(skill_Lightning_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Lightning_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()){
                        if(ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Lightning_List.get(i).get_Y_Point() && skill_Lightning_List.get(i).get_Y_Point() <= ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size()){
                            ground_List.get(j).set_Ground_Hp_Minus(30);
                        }
                    }else if(ground_List.get(j).get_Ground_Point_X() <= skill_Lightning_List.get(i).get_X_Point() + skill_lightnign_img[0].getWidth()
                            && skill_Lightning_List.get(i).get_X_Point() + skill_lightnign_img[0].getWidth()  <= ground_List.get(j).get_Ground_Point_Y()
                            ){

                        if(ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Lightning_List.get(i).get_Y_Point() && skill_Lightning_List.get(i).get_Y_Point() <= ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size()){
                            ground_List.get(j).set_Ground_Hp_Minus(30);
                        }
                    }
//
                }
            }

            if(skill_Lightning_List.get(i).get_Live()){
                skill_Lightning_List.remove(i);
            }

        }



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

                if(skill_Lightning2_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Lightning2_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()){
                    if(fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Lightning2_List.get(i).get_Y_Point() && skill_Lightning2_List.get(i).get_Y_Point() <= fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size()){
                        fish_List.get(j).set_Hp_Minus(10);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                        skill_Lightning2_List.get(i).set_play_Attack();
                    }
                }else if(fish_List.get(j).get_Fish_Point_X() <= skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[0].getWidth()
                        && skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[0].getWidth()  <= fish_List.get(j).get_Fish_Point_X()
                        ){

                    if(fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Lightning2_List.get(i).get_Y_Point() && skill_Lightning2_List.get(i).get_Y_Point() <= fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size()){
                        fish_List.get(j).set_Hp_Minus(10);
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
                        skill_Lightning2_List.get(i).set_play_Attack();
                    }
                }

            }

            if(skill_Lightning2_List.get(i).get_play_Attack()){


                for(int j=0; j<fish_List.size(); j++){

                    if(skill_Lightning2_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Lightning2_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()){
                        if(fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Lightning2_List.get(i).get_Y_Point() && skill_Lightning2_List.get(i).get_Y_Point() <= fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size()){
                            fish_List.get(j).set_Hp_Minus(30);
                        }
                    }else if(fish_List.get(j).get_Fish_Point_X() <= skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[0].getWidth()
                            && skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[0].getWidth()  <= fish_List.get(j).get_Fish_Point_X()
                            ){

                        if(fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Lightning2_List.get(i).get_Y_Point() && skill_Lightning2_List.get(i).get_Y_Point() <= fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size()){
                            fish_List.get(j).set_Hp_Minus(30);
                        }
                    }

//
                }
            }

            for(int j=0; j<ground_List.size(); j++){

                if(!(ground_List.get(j) instanceof Land_Mark)){

                    if(skill_Lightning2_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Lightning2_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()){
                        if(ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Lightning2_List.get(i).get_Y_Point() && skill_Lightning2_List.get(i).get_Y_Point() <= ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size()){
                            skill_Lightning2_List.get(i).set_play_Attack();
                        }
                    }else if(ground_List.get(j).get_Ground_Point_X() <= skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[0].getWidth()
                            && skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[0].getWidth()  <= ground_List.get(j).get_Ground_Point_Y()
                            ){

                        if(ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Lightning2_List.get(i).get_Y_Point() && skill_Lightning2_List.get(i).get_Y_Point() <= ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size()){
                            skill_Lightning2_List.get(i).set_play_Attack();
                        }
                    }

                }

            }




            if(skill_Lightning2_List.get(i).get_play_Attack()){
                for(int j=0; j<ground_List.size(); j++){

                    if(skill_Lightning2_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Lightning2_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()){
                        if(ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Lightning2_List.get(i).get_Y_Point() && skill_Lightning2_List.get(i).get_Y_Point() <= ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size()){
                            ground_List.get(j).set_Ground_Hp_Minus(30);
                        }
                    }else if(ground_List.get(j).get_Ground_Point_X() <= skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[0].getWidth()
                            && skill_Lightning2_List.get(i).get_X_Point() + skill_lightnign1_img[0].getWidth()  <= ground_List.get(j).get_Ground_Point_Y()
                            ){

                        if(ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Lightning2_List.get(i).get_Y_Point() && skill_Lightning2_List.get(i).get_Y_Point() <= ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size()){
                            ground_List.get(j).set_Ground_Hp_Minus(30);
                        }
                    }
//
                }
            }

            if(skill_Lightning2_List.get(i).get_Live()){
                skill_Lightning2_List.remove(i);
            }

        }


        //포크 소환
        for(int i=0; i<skill_Fork_List.size(); i++){

            draw.draw_Bmp(canvas, skill_Fork_img[skill_Fork_List.get(i).get_Skill_Status()], skill_Fork_List.get(i).get_X_Point(), skill_Fork_List.get(i).get_Y_Point());
            skill_Fork_List.get(i).set_Skill_Move(convertPixelsToDp(40, _context));


            if(skill_Fork_List.get(i).get_Live()){


                try{
                    //물고기 생명체
                    if(skill_Fork_List.get(i).get_Aim_Species() == 1){
                        fish_List.get(skill_Fork_List.get(i).get_Aim_Fish()).set_Hp_Minus(30);
                    }

                    //그라운드 생명체
                    if(skill_Fork_List.get(i).get_Aim_Species() == 2){
                        ground_List.get(skill_Fork_List.get(i).get_Aim_Ground()).set_Ground_Hp_Minus(30);
                    }
                }catch (Exception e){

                }


                skill_Fork_List.remove(i);
            }

        }

        //독 폭탄
        for(int i=0; i<skill_Boom_Poison_List.size(); i++){



            draw.draw_Bmp(canvas, skill_Boom_Poison_img[skill_Boom_Poison_List.get(i).get_Skill_Status()], skill_Boom_Poison_List.get(i).get_X_Point(), skill_Boom_Poison_List.get(i).get_Y_Point());
            skill_Boom_Poison_List.get(i).set_Skill_Move(convertPixelsToDp(30, _context));

if(skill_Boom_Poison_List.get(i).get_Skill_Status() >= 3){


            for(int j=0; j<fish_List.size(); j++){
                if (skill_Boom_Poison_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Boom_Poison_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Boom_Poison_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Boom_Poison_List.get(i).get_Y_Point() + skill_Boom_Poison_img[3].getHeight()) {
                        fish_List.get(j).set_Status_Poison(5);

                    }
                } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Boom_Poison_List.get(i).get_X_Point() + skill_Boom_Poison_img[3].getWidth()
                        && fish_List.get(j).get_Fish_Point_X() >= skill_Boom_Poison_List.get(i).get_X_Point()
                        ) {

                    if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Boom_Poison_List.get(i).get_Y_Point()
                            && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Boom_Poison_List.get(i).get_Y_Point() + skill_Boom_Poison_img[3].getHeight()) {

                        fish_List.get(j).set_Status_Poison(5);
                    }
                }
            }

            for(int j=0; j<ground_List.size(); j++){

                if (skill_Boom_Poison_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Boom_Poison_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Boom_Poison_List.get(i).get_Y_Point()
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Boom_Poison_List.get(i).get_Y_Point() + skill_Boom_Poison_img[3].getHeight()) {
                        ground_List.get(j).set_Status_Poison(5);

                    }
                } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Boom_Poison_List.get(i).get_X_Point() + skill_Boom_Poison_img[3].getWidth()
                        && ground_List.get(j).get_Ground_Point_X() >= skill_Boom_Poison_List.get(i).get_X_Point()
                        ) {

                    if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Boom_Poison_List.get(i).get_Y_Point()
                            && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Boom_Poison_List.get(i).get_Y_Point() + skill_Boom_Poison_img[3].getHeight()) {

                        ground_List.get(j).set_Status_Poison(5);

                    }
                }
            }
}



            if(skill_Boom_Poison_List.get(i).get_Live()){

                skill_Boom_Poison_List.remove(i);
            }

        }







        //느려지는 구름 생성
        for(int i=0; i< skill_Slow_Cloud_List.size(); i++){

            pop_Temp_img = draw.rotate_Image(skill_Slow_Cloud_img, skill_Slow_Cloud_List.get(i).getAngle());
            draw.draw_Bmp(canvas, pop_Temp_img, skill_Slow_Cloud_List.get(i).get_X_Point(), skill_Slow_Cloud_List.get(i).get_Y_Point());

            skill_Slow_Cloud_List.get(i).set_Skill_Move();









                for(int j=0; j<fish_List.size(); j++){

                    if (skill_Slow_Cloud_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Slow_Cloud_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Slow_Cloud_List.get(i).get_Y_Point()
                                && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Slow_Cloud_List.get(i).get_Y_Point() + skill_Slow_Cloud_img.getHeight()) {
                            fish_List.get(j).set_Fish_Speed(1);

                        }
                    } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Slow_Cloud_List.get(i).get_X_Point() + skill_Slow_Cloud_img.getWidth()
                            && fish_List.get(j).get_Fish_Point_X() >= skill_Slow_Cloud_List.get(i).get_X_Point()
                            ) {

                        if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Slow_Cloud_List.get(i).get_Y_Point()
                                && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Slow_Cloud_List.get(i).get_Y_Point() + skill_Slow_Cloud_img.getHeight()) {

                            fish_List.get(j).set_Fish_Speed(1);

                        }
                    }




                }
                for(int j=0; j<ground_List.size(); j++){




                    if (skill_Slow_Cloud_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Slow_Cloud_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Slow_Cloud_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Slow_Cloud_List.get(i).get_Y_Point() + skill_Slow_Cloud_img.getHeight()) {
                            ground_List.get(j).set_Ground_Speed(1);

                        }
                    } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Slow_Cloud_List.get(i).get_X_Point() + skill_Slow_Cloud_img.getWidth()
                            && ground_List.get(j).get_Ground_Point_X() >= skill_Slow_Cloud_List.get(i).get_X_Point()
                            ) {

                        if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Slow_Cloud_List.get(i).get_Y_Point()
                                && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Slow_Cloud_List.get(i).get_Y_Point() + skill_Slow_Cloud_img.getHeight()) {

                            ground_List.get(j).set_Ground_Speed(1);

                        }
                    }


                }
            if(skill_Slow_Cloud_List.get(i).get_Live()){
                skill_Slow_Cloud_List.remove(i);
            }

        }


        //레이저
        for(int i = 0; i < skill_Laser_List.size(); i++){

            draw.draw_Bmp(canvas, skill_Laser_img, skill_Laser_List.get(i).get_X_Point(), skill_Laser_List.get(i).get_Y_Point());
            skill_Laser_List.get(i).set_Skill_Move(convertPixelsToDp(100, _context));


            if(skill_Laser_List.get(i).get_Live()){

                skill_Laser_List.remove(i);
            }else  //레이저 안에 있을때 대미지
                {
                    for(int j=0; j<fish_List.size(); j++){

                        if (skill_Laser_List.get(i).get_X_Point() >= fish_List.get(j).get_Fish_Point_X() && skill_Laser_List.get(i).get_X_Point() <= fish_List.get(j).get_Fish_Point_X() + fish_List.get(j).get_Width_Size()) {
                            if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Laser_List.get(i).get_Y_Point() + convertPixelsToDp(30, _context)
                                    && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Laser_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context) + skill_Laser_img.getHeight()) {
                                fish_List.get(j).set_Hp_Minus(1);
                            }
                        } else if (fish_List.get(j).get_Fish_Point_X() <= skill_Laser_List.get(i).get_X_Point() + skill_Laser_img.getWidth()
                                && fish_List.get(j).get_Fish_Point_X() >= skill_Laser_List.get(i).get_X_Point()
                                ) {

                            if (fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() >= skill_Laser_List.get(i).get_Y_Point() + convertPixelsToDp(30, _context)
                                    && fish_List.get(j).get_Fish_Point_Y() + fish_List.get(j).get_Height_Size() <= skill_Laser_List.get(i).get_Y_Point() + convertPixelsToDp(30, _context) + skill_Laser_img.getHeight()) {
                                fish_List.get(j).set_Hp_Minus(1);
                            }
                        }


                    }
                    for(int j=0; j<ground_List.size(); j++){


                        if (skill_Laser_List.get(i).get_X_Point() >= ground_List.get(j).get_Ground_Point_X() && skill_Laser_List.get(i).get_X_Point() <= ground_List.get(j).get_Ground_Point_X() + ground_List.get(j).get_Width_Size()) {
                            if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Laser_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context)
                                    && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Laser_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context) + skill_Laser_img.getHeight()) {
                                ground_List.get(j).set_Ground_Hp_Minus(1);
                            }
                        } else if (ground_List.get(j).get_Ground_Point_X() <= skill_Laser_List.get(i).get_X_Point() + skill_Laser_img.getWidth()
                                && ground_List.get(j).get_Ground_Point_X() >= skill_Laser_List.get(i).get_X_Point()
                                ) {

                            if (ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() >= skill_Laser_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context)
                                    && ground_List.get(j).get_Ground_Point_Y() + ground_List.get(j).get_Height_Size() <= skill_Laser_List.get(i).get_Y_Point()+ convertPixelsToDp(30, _context) + skill_Laser_img.getHeight()) {
                                ground_List.get(j).set_Ground_Hp_Minus(1);
                            }
                        }



                    }




            }

        }





        /**
         * 오징어 먹물 이벤트
         */
        if(Background_Effect_Squid_Ink_Arr.size() > 0){
            for(int i=0; i < Background_Effect_Squid_Ink_Arr.size(); i++){



                Background_Effect_Squid_Ink_Arr.get(i).Background_Effect_Move_Pattern();    //오징어 먹묵이팩트 움직임
                draw.draw_Bmp(canvas, effect_Background_Squid_Ink_img[Background_Effect_Squid_Ink_Arr.get(i).get_Draw_Background_Effect_Status()],
                        Background_Effect_Squid_Ink_Arr.get(i).get_Background_Point_X(),
                        Background_Effect_Squid_Ink_Arr.get(i).get_Background_Point_Y() + convertPixelsToDp(30, _context));

            }

            //먹물 시간다되면 삭제
            for(int i=0; i < Background_Effect_Squid_Ink_Arr.size(); i++) {
                if(!Background_Effect_Squid_Ink_Arr.get(i).up_Continue_Time()){
                    Background_Effect_Squid_Ink_Arr.remove(i);
                    break;
                }
            }


        }


        /**
         *  점수 그리기
         */
//        score_Text_Size = window_Width / 15;

        score_Text_Size = convertPixelsToDp(30, _context);
        paint_Temp.setTextSize(score_Text_Size);
        paint_Temp.setColor(Color.YELLOW);
        canvas.drawText(Score+"", 30, convertPixelsToDp(80, _context), paint_Temp);

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



        draw.draw_Bmp(canvas, nine_Patch_Hp, 20, 1); //나인패치 적용방법





//
        //점수 배경
        paint.setTextSize(score_Text_Size);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        canvas.drawText(Score+"", 30, convertPixelsToDp(80, _context), paint);

        //베스트 점수
        paint_Best.setTextSize(score_Text_Size-(score_Text_Size/2));
        paint_Best.setStyle(Paint.Style.STROKE);
        paint_Best.setStrokeWidth(2);
        paint_Best.setColor(Color.BLACK);
        canvas.drawText("Best ", 30, convertPixelsToDp(80, _context)+(score_Text_Size/2), paint_Best);
//

    /*
    등장 설명창, 진화 할 때 컨트롤
     */


    if(!mRun) {

        if(!pause_State) {
            draw.draw_Bmp(canvas, backGroundImg_black, 0, 0);
        }




        if(revolution_Flag && !pause_State) {


            draw.draw_Bmp(canvas, explain_Window_Revoluition,
                    window_Width / 2 - convertPixelsToDp(120, _context),
                    window_Height / 2 - convertPixelsToDp(220, _context));

            //버튼 객체 생성
            button_Create_Method();



            upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);
            downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);

            confirm_Button_card_1.setImages(upimage, downimage);
            confirm_Button_card_1.draw(canvas);

            upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);
            downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);


            confirm_Button_card_2.setImages(upimage, downimage);
            confirm_Button_card_2.draw(canvas);

            upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_2);
            downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_2);

            confirm_Button_card_3.setImages(upimage, downimage);
            confirm_Button_card_3.draw(canvas);

            revolution_Flag = false;


        }


    }



        if(revolution_Flag_Confirm){

            //진화의창 버튼 객체 제거
            button_Create_method_Init();


            upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_1);
            downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_2);

            draw.draw_Bmp(canvas, backGroundImg_black, 0, 0);


            /**
             * 설명창 띄우기
             */
            if(main_Character instanceof Main_Character_Shellfish_Tear1) {
                draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Shellfish_Tear2) {
                draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Shellfish_Tear3) {
                draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Shellfish_Tear4) {
                draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Shellfish_Tear5) {
                draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Shellfish_Tear6) {
                draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Shellfish_Tear7) {
                draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Shellfish_Tear8) {
                draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Shellfish_Tear9) {
                draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Shellfish_Tear10) {
                draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }




            else if(main_Character instanceof Main_Character_Fish_Tear1){
                draw.draw_Bmp(canvas, explain_Window_Origin_Fish,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Fish_Tear2){
                draw.draw_Bmp(canvas, explain_Window_Origin_Fish,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Fish_Tear3){
                draw.draw_Bmp(canvas, explain_Window_Origin_Fish,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Fish_Tear4){
                draw.draw_Bmp(canvas, explain_Window_Origin_Fish,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Fish_Tear5){
                draw.draw_Bmp(canvas, explain_Window_Origin_Fish,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Fish_Tear6){
                draw.draw_Bmp(canvas, explain_Window_Origin_Fish,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Fish_Tear7){
                draw.draw_Bmp(canvas, explain_Window_Origin_Fish,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Fish_Tear8){
                draw.draw_Bmp(canvas, explain_Window_Origin_Fish,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Fish_Tear9){
                draw.draw_Bmp(canvas, explain_Window_Origin_Fish,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Fish_Tear10){
                draw.draw_Bmp(canvas, explain_Window_Origin_Fish,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }




            else if(main_Character instanceof Main_Character_Moulluse_Tear1){
                draw.draw_Bmp(canvas, explain_Window_Origin_Squid,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Moulluse_Tear2){
                draw.draw_Bmp(canvas, explain_Window_Origin_Squid,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Moulluse_Tear3){
                draw.draw_Bmp(canvas, explain_Window_Origin_Squid,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Moulluse_Tear4){
                draw.draw_Bmp(canvas, explain_Window_Origin_Squid,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Moulluse_Tear5){
                draw.draw_Bmp(canvas, explain_Window_Origin_Squid,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Moulluse_Tear6){
                draw.draw_Bmp(canvas, explain_Window_Origin_Squid,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Moulluse_Tear7){
                draw.draw_Bmp(canvas, explain_Window_Origin_Squid,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Moulluse_Tear8){
                draw.draw_Bmp(canvas, explain_Window_Origin_Squid,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Moulluse_Tear9){
                draw.draw_Bmp(canvas, explain_Window_Origin_Squid,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }else if(main_Character instanceof Main_Character_Moulluse_Tear10){
                draw.draw_Bmp(canvas, explain_Window_Origin_Squid,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }





            soundPool.play(sound_Effect[8], 1F, 1F, 0, 0, 1.0F);
            confirm_Button_1.setImages(upimage, downimage);
            confirm_Button_1.draw(canvas);

            m_Run_False();





        }


        //진화할때
        if(revolution_Flag && !revolution_Draw_Flag_Confirm){


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
        }



        //진화의 버튼 뒤쪽의 이펙트
        if(revolution_Draw_Flag_Confirm){

                draw.draw_Bmp(canvas, revolution_Button_Background_Effect,
                        window_Width / 2 - convertPixelsToDp(70 , _context)  ,
                        window_Height / 2 - convertPixelsToDp(198 , _context) );


        }


        if(revolution_Draw_Flag_Confirm){
            revolution_Button.draw(canvas);
        }



//



        /**
         * 상어친구 호출하면 여기서 그린다.
         */
        if(shark_Friend_Call_Flag){







            background_Effect_Friend_Shark_Call.Background_Effect_Move_Pattern();
            draw.draw_Bmp(canvas, effect_Background_Friend_Shark_img[background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status()],
                    window_Width/2 - convertPixelsToDp(340, _context),
                    window_Height/2 - convertPixelsToDp(380, _context));

            if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 0){
                soundPool.play(sound_Effect[4], 0.2F, 0.2F, 0, 0, 1.0F);
            }else if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 4){
//                soundPool.play(sound_Effect[5], 0.5F, 0.5F, 1, 1, 1.0F);
            }else if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 6){
//                soundPool.play(sound_Effect[6], 0.5F, 0.5F, 1, 1, 1.0F);
            }else if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 7){
                soundPool.play(sound_Effect[7], 0.5F, 0.5F, 0, 0, 1.0F);
            }


            if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 7){

                //모든 물고기 및 그라운드 생명체 hp - 100 해야된다.

                for(int i=0; i<fish_List.size(); i++){
                    fish_List.get(i).set_Hp_Minus(100);
                }


                for(int i=0; i<ground_List.size(); i++){
                    ground_List.get(i).set_Ground_Hp_Minus(100);
                }



                shark_Friend_Call_Flag = false;     //상어 이펙트 그린후 사라져야한다.
            }
        }




        }catch (Exception e){

            Log.e("그리기", "그리기");
            Log.e("그리기", e.toString());
            Log.e("그리기", e.getMessage());
        }

    }













        /**
     * 게임이 동작하는 구간
     */
    public synchronized void run() {
        while(true)
        while (mRun) {
            //퍼즈 걸도록 mRun 컨트롤

                //canvas = null;
                try {
                    canvas = mSurfaceHolder.lockCanvas(null);
//                    synchronized (mSurfaceHolder) {



/**
 * 그림 그리기 구간
 */
                    doDraw(canvas);

                    sleep(18);

                    //물고기 및 그라운드 생명체 체력 0 인것 삭제
                    delete_Fish();
                    delete_Ground();



                        //물고기 움직임을 하나의 쓰레드로 작동한다.
                        fish_Move();
                        //그라운드 움직임을 하나의 쓰레드로 작동합니다.
                        ground_Move();

                    main_Character.character_Moving();

                    /**
                     * 배경 효과 움직임
                     */
                    background_Effect_Move();













                        //문어 공격 스피드에 따라서 터치 이벤트 제어
                        if (main_Character.get_Attack_Cool_time() != 0) {
                            main_Character.set_Attack_Cool_Time();
                        }





//                    }

                } catch (Exception e) {

                } finally {
                    if (canvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
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
     * dpi 구하기
     * */
    public int convertPixelsToDp(float px, Context context) {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
        return value;
    }






/**
 * 청새치 추가하기
 */
public void add_Fish_Touch_Marlin(){
    fish_Touch_Marlin = new Fish_Touch_Marlin(window_Width, 1, fish_Touch_Marlin_img[0].getWidth(), fish_Touch_Marlin_img[0].getHeight());
    fish_List.add(fish_Touch_Marlin);
}



    /**
     * 물고기 추가하기
     */
    public void add_Fish_Touch_Default(){
        fish_Touch_Default = new Fish_Touch_Default(window_Width, 5, fish_Touch_Default_Hp1_img[0].getWidth() + convertPixelsToDp(15, _context), fish_Touch_Default_Hp1_img[0].getHeight());       //기본 fish_touch_default 물고기 생성

        //처음 시작할 때 물고기 설명 추가
        if(first_Default_Fish) {
            fish_Touch_Default.set_First_Test_Object((window_Width/2) - convertPixelsToDp(30, _context));
            first_Default_Fish = false;

            //메모리 오버 때문에 비트맵 설명, 기본 물고기 일때
//            if(fish_Touch_Default.get_Fish_Class() == 1) {
//                game_thread.function_Init_Explain_Fish_Default();
//            }
        }
        fish_List.add(fish_Touch_Default);                                  //물고기 리스트에 추가
    }

    public void add_Fish_Touch_Default(float x, float y){
        fish_Touch_Default = new Fish_Touch_Default(window_Width, 5, fish_Touch_Default_Hp1_img[0].getWidth(), fish_Touch_Default_Hp1_img[0].getHeight(), x, y);       //기본 fish_touch_default 물고기 생성
        fish_Touch_Default.set_Class_NUm(0);
        fish_List.add(fish_Touch_Default);
    }

    /**
     * 중간보스 터치 물고기 추가
     */
    public void add_Middle_Fish_Touch_Default(){
        fish_Touch_Default = new Fish_Touch_Default(window_Width, 5 , fish_Touch_Default_Middle_Hp1_img[0].getWidth(), fish_Touch_Default_Middle_Hp1_img[0].getHeight());
        fish_Touch_Default.set_Class_NUm(1);
        fish_List.add(fish_Touch_Default);                                  //물고기 리스트에 추가
    }

    public void add_Middle_Fish_Touch_Default(float x, float y){
        fish_Touch_Default = new Fish_Touch_Default(window_Width, 5, fish_Touch_Default_Middle_Hp1_img[0].getWidth(),fish_Touch_Default_Middle_Hp1_img[0].getHeight(),  x, y);
        fish_Touch_Default.set_Class_NUm(1);
        fish_List.add(fish_Touch_Default);
    }

    /**
     * 보스 터치 물고기 추가
     */
    public void add_Boss_Fish_Touch_Default(){

        fish_Touch_Default = new Fish_Touch_Default(window_Width, 5, fish_Touch_Default_Boss_Hp1_img[0].getWidth(), fish_Touch_Default_Boss_Hp1_img[0].getHeight());
        fish_Touch_Default.set_Class_NUm(2);

        fish_List.add(fish_Touch_Default);                                  //물고기 리스트에 추가
    }



    /**
     * 오징어 추가
     */
    public void add_Fish_Touch_Squid(){
        fish_Touch_Squid = new Fish_Touch_Squid(window_Width, 1 , fish_Touch_Squid_img[0].getWidth(), fish_Touch_Squid_img[0].getHeight());       //오징어 생성

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
     * 전기뱀장어 추가
     */
    public void add_Fish_Touch_Ell(){
        fish_Touch_Ell = new Fish_Touch_Ell(window_Width, 1, fish_Touch_Ell_img[0].getWidth(), fish_Touch_Ell_img[0].getHeight());       //오징어 생성

        if(first_Ell){
            fish_Touch_Ell.set_First_Test_Object((window_Width/2) - convertPixelsToDp(30, _context));
            first_Ell = false;

            //메모리 오버 때문에 비트맵 설명, 기본 물고기 일때
//            if(fish_Touch_Default.get_Fish_Class() == 1) {
//                game_thread.function_Init_Explain_Ell();
//            }
        }



        fish_List.add(fish_Touch_Ell);
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
     *  드래그 물고기 추가하기
     */
    public void add_Fish_Drag_Default(){
        fish_Drag_Default = new Fish_Drag_Default(window_Width, 30, fish_Drag_Default_img[0].getWidth(), fish_Drag_Default_img[0].getHeight());       //드래그로 잡는 fish_Touch_Default 물고기생성

        //처음 나올때 설명 붙여서 나옴
        if(first_Drag_Fish){
            fish_Drag_Default.set_First_Test_Object((window_Width/2) -fish_Drag_Default_img[0].getHeight());//- convertPixelsToDp(30, _context));
            first_Drag_Fish = false;

            //메모리 관리 기법 [드래그 물고기]
//            if(fish_Drag_Default.get_Fish_Class() == 2) {
//                game_thread.function_Init_Explain_Fish_Drag();
//            }

        }


        fish_List.add(fish_Drag_Default);
    }

    /**
     * 해파리 추가
     */
    public void add_Fish_JellyFish(){

        fish_Trap_Jellyfish = new Fish_Trap_Jellyfish(window_Width, window_Height, 1, fish_Trap_Jelly_img[0].getWidth(),fish_Trap_Jelly_img[0].getHeight());                //화면 좌우축 둘중 한군대만 생성 hp = 1
        fish_List.add(fish_Trap_Jellyfish);

    }


    /**
     * 그라운드 생성구간 (달팽이)
     */
    public void add_Ground_Snail(){



        ground_Touch_Snail = new Ground_Touch_Snail(window_Width,
                ground_Touch_Snail_Hp1_img[0].getWidth(),
                ground_Touch_Snail_Hp1_img[0].getHeight(), 5, ground_Touch_Snail_Hp1_img[0].getWidth() + convertPixelsToDp(15, _context) ,ground_Touch_Snail_Hp1_img[0].getHeight());
        ground_Touch_Snail.set_Class_NUm(0);
        //첫 번째 달팽이 생성
        if(first_Snail){
            ground_Touch_Snail.set_First_Test_Object((window_Width/2) - ground_Touch_Snail_Hp1_img[0].getHeight());
            first_Snail = false;

            //메모리 관리 기법 [달팽이]
//            if(ground_Touch_Snail.get_Ground_Class() == 1) {
//                game_thread.function_Init_Explain_Snail();
//            }

        }
        ground_List.add(ground_Touch_Snail); //달팽이 생성



    }
    public void add_Ground_Snail(float x, float y){
        ground_Touch_Snail = new Ground_Touch_Snail(window_Width,
                ground_Touch_Snail_Hp1_img[0].getWidth(),
                ground_Touch_Snail_Hp1_img[0].getHeight(), 5,ground_Touch_Snail_Hp1_img[0].getWidth(),ground_Touch_Snail_Hp1_img[0].getHeight(), x , y);
        ground_Touch_Snail.set_Class_NUm(0);
        //첫 번째 달팽이 생성

        ground_List.add(ground_Touch_Snail); //달팽이 생성
    }

    /**
     * 그라운드 생성구간 (소러게)
     */
    public void add_Ground_Hermit(){
        ground_Touch_Hermit = new Ground_Touch_Hermit(window_Width,
                ground_Touch_Hermit_Hp1_img[0].getWidth(),
                ground_Touch_Hermit_Hp1_img[0].getHeight(), 5, ground_Touch_Hermit_Hp1_img[0].getWidth() + convertPixelsToDp(15, _context) ,ground_Touch_Hermit_Hp1_img[0].getHeight());
        ground_Touch_Hermit.set_Class_NUm(0);
        //첫 번째 달팽이 생성
        if(first_Snail){
            ground_Touch_Hermit.set_First_Test_Object((window_Width/2) - ground_Touch_Hermit_Hp1_img[0].getHeight());
            first_Snail = false;

            //메모리 관리 기법 [달팽이]
//            if(ground_Touch_Hermit.get_Ground_Class() == 1) {
//                game_thread.function_Init_Explain_Snail();
//            }
        }
        ground_List.add(ground_Touch_Hermit); //달팽이 생성
    }



    /**
     * 달팽이 중간 보스 생성
     */
    public void add_Ground_Middle_Boss(float x, float y){
        ground_Touch_Snail = new Ground_Touch_Snail(window_Width,
                ground_snail_Middle_Hp1_img[0].getWidth(),
                ground_snail_Middle_Hp1_img[0].getHeight(), 5,ground_snail_Middle_Hp1_img[0].getWidth(),ground_snail_Middle_Hp1_img[0].getHeight(), x, y);
        ground_Touch_Snail.set_Class_NUm(1);
        ground_List.add(ground_Touch_Snail); //달팽이 생성
    }
    /**
     * 달팽이 보스 생성
     */
    public void add_Ground_Boss(float x, float y){
        ground_Touch_Snail = new Ground_Touch_Snail(window_Width,
                ground_snail_Middle_Hp1_img[0].getWidth(),
                ground_snail_Middle_Hp1_img[0].getHeight(), 5,ground_snail_Boss_Hp1_img[0].getWidth(),ground_snail_Boss_Hp1_img[0].getHeight(), x, y);
        ground_Touch_Snail.set_Class_NUm(2);
        ground_List.add(ground_Touch_Snail); //달팽이 생성
    }



    /**
     * 꽃게 추가
     */
    public void add_Ground_Crab(){
        ground_Drag_Crab = new Ground_Drag_Crab(window_Width,
                ground_Drag_Crab_img[0].getWidth(),
                ground_Drag_Crab_img[0].getHeight(), 30, ground_Drag_Crab_img[0].getWidth(),ground_Drag_Crab_img[0].getHeight());

        //첫 번째 꽃게 생성
        if(first_Crab){
            ground_Drag_Crab.set_First_Test_Object((window_Width/2) - ground_Drag_Crab_img[0].getHeight());
            first_Crab = false;

            //메모리 관리 기법 [달팽이]
//            if(ground_Drag_Crab.get_Ground_Class() == 2){
//                game_thread.function_Init_Explain_Crab();
//            }

        }

        ground_List.add(ground_Drag_Crab); //꽃게
    }

    /**
     * 그라운드 웨이브 추가
     */
    public void add_Ground_Wave(){

        float wave_X_Point = 30 + (float)Math.random() * (window_Width-100);         //생성될 위치

        for(int i=0; i<20; i++) {
            ground_drag_wave = new Ground_Drag_Wave(window_Width,
                    ground_Drag_Wave_img[0].getWidth(),
                    ground_Drag_Wave_img[0].getHeight() , 1, ground_Drag_Wave_img[1].getWidth(), ground_Drag_Wave_img[0].getHeight(), wave_X_Point, -convertPixelsToDp(i*15, _context));

            ground_List.add(ground_drag_wave); //파도
        }
    }

    /**
     * 악어 추가
     */
    public void add_Ground_Crocodile(){
        ground_Touch_Crocodile = new Ground_Touch_Crocodile(window_Width,window_Height,
                ground_Touch_Crocodile_img[0].getWidth(),
                ground_Touch_Crocodile_img[0].getHeight(), 1, ground_Touch_Crocodile_img[0].getWidth(),ground_Touch_Crocodile_img[0].getHeight());

        ground_List.add(ground_Touch_Crocodile);
    }

    /**
     * 조개 추가
     */
    public void add_Ground_Clam(){
        ground_Drag_Clam = new Ground_Drag_Clam(window_Width,
                ground_Drag_Crab_img[0].getWidth(),
                ground_Drag_Crab_img[0].getHeight(), 500,ground_Drag_Clam_img[0].getWidth(),ground_Drag_Clam_img[0].getHeight(), window_Height, window_Width);

        ground_List.add(ground_Drag_Clam); //꽃게
    }



    /**
     * 랜드마크 추가
     */
    Land_Mark land_Mark;
    int land_Mark_Class = 1;
    int land_Mark_Img_View_Separate[] = new int[3];

    public void add_Ground_Land_Mark(){
        //이 부분 고칠
        if(land_Mark_Class == 1) {

            //랜드마크 메모리 기법
            game_thread.function_Land_Mark_1();
            land_Mark = new Land_Mark(window_Width, land_Mark1_img[0].getWidth(), land_Mark1_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark1_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark1_img[0].getHeight() / 2));

        }else if(land_Mark_Class == 2){

            //랜드마크 메모리 기법

            game_thread.function_Land_Mark_2();
            land_Mark = new Land_Mark(window_Width, land_Mark2_img[0].getWidth(), land_Mark2_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark2_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark2_img[0].getHeight() / 2));

        }else if(land_Mark_Class == 3){

            //랜드마크 메모리 기법
            game_thread.function_Land_Mark_3();
            land_Mark = new Land_Mark(window_Width, land_Mark3_img[0].getWidth(), land_Mark3_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark3_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark3_img[0].getHeight() / 2));

        }else if(land_Mark_Class == 4){

            //랜드마크 메모리 기법
            game_thread.function_Land_Mark_4();
            land_Mark = new Land_Mark(window_Width, land_Mark4_img[0].getWidth(), land_Mark4_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark4_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark4_img[0].getHeight() / 2));

        }else if(land_Mark_Class == 5){

            //랜드마크 메모리 기법
            game_thread.function_Land_Mark_5();
            land_Mark = new Land_Mark(window_Width, land_Mark5_img[0].getWidth(), land_Mark5_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark5_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark5_img[0].getHeight() / 2));

        }else if(land_Mark_Class == 6){
            game_thread.function_Land_Mark_6();
            land_Mark = new Land_Mark(window_Width, land_Mark6_img[0].getWidth(), land_Mark6_img[0].getHeight(), 400, (window_Width / 2) - (land_Mark6_img[0].getWidth() / 2), (window_Height / 2) - (land_Mark6_img[0].getHeight() / 2));

        }


        land_Mark.set_Class_Num(land_Mark_Class);
        land_Mark_Class++;

        if(land_Mark_Class == 2) {
            land_Mark_Img_View_Separate[0] *= 10;
            land_Mark_Img_View_Separate[1] *= 10;
            land_Mark_Img_View_Separate[2] *= 10;
        }

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
            fish_List.get(i).fish_Object_Move();
        }
    }


    /**
     * 그라운드 움직임
     */

    public synchronized void ground_Move(){




        for(int i=0; i<ground_List.size(); i++){



            if(ground_List.get(i) instanceof Ground_Touch_Snail) {      //달팽이 무빙 함수 이용
                ((Ground_Touch_Snail) ground_List.get(i)).ground_Object_Move();

                if(((Ground_Touch_Snail) ground_List.get(i)).get_Class_Num() == 1){ //달팽이 중간 보스 일때

                    if(((Ground_Touch_Snail) ground_List.get(i)).get_Pragnant_Flag()){  //일반 달팽이 소환
                        ((Ground_Touch_Snail) ground_List.get(i)).set_Pragnant();

                        add_Ground_Snail(ground_List.get(i).get_Ground_Point_X() + convertPixelsToDp(random.nextInt(30), _context) , ground_List.get(i).get_Ground_Point_Y()  - ground_List.get(i).get_Height_Size());     //달팽이 추가


                    }

                }else if(((Ground_Touch_Snail) ground_List.get(i)).get_Class_Num() == 2){ //달팽이 중간 보스 일때

                    if(((Ground_Touch_Snail) ground_List.get(i)).get_Pragnant_Flag()){  //중간보스 달팽이 생성
                        ((Ground_Touch_Snail) ground_List.get(i)).set_Pragnant();

                        add_Ground_Middle_Boss(ground_List.get(i).get_Ground_Point_X() + convertPixelsToDp(random.nextInt(30), _context) , ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size());     //달팽이 추가


                    }

                }


            }else if(ground_List.get(i) instanceof Ground_Touch_Hermit){
                ((Ground_Touch_Hermit)ground_List.get(i)).ground_Object_Move();
            }
            else if(ground_List.get(i) instanceof Ground_Touch_Crocodile){
                ((Ground_Touch_Crocodile)ground_List.get(i)).ground_Object_Move();
            }
            else if(ground_List.get(i) instanceof Ground_Drag_Crab){   //꽃게 무빙 함수
                ((Ground_Drag_Crab) ground_List.get(i)).ground_Object_Move();
            }else if(ground_List.get(i) instanceof Ground_Drag_Wave){   //파도 무빙 함수
                ((Ground_Drag_Wave) ground_List.get(i)).ground_Object_Move();
            }else if(ground_List.get(i) instanceof Ground_Trap_Urchin){
                ((Ground_Trap_Urchin) ground_List.get(i)).ground_Object_Move();
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


        for(int i=0; i<fish_List.size();i++){

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

                        fish_List.remove(i);
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
                split_Fish_Class = fish_List.get(i).get_Class_Num();



                //중간 보스 및 대장 물고기를 잡으면 그 자리에서 물고기 추가
                if(split_Fish_Class == 1){  //중간보스이면
                    add_Fish_Touch_Default(split_Fish_X, split_Fish_Y);
                    add_Fish_Touch_Default(split_Fish_X, split_Fish_Y);
                    add_Fish_Touch_Default(split_Fish_X, split_Fish_Y);
                    add_Fish_Touch_Default(split_Fish_X, split_Fish_Y);

                }else if(split_Fish_Class == 2){
                    add_Middle_Fish_Touch_Default(split_Fish_X, split_Fish_Y);
                    add_Middle_Fish_Touch_Default(split_Fish_X, split_Fish_Y);
                }

                //첫 번째 물고기 삭제 되면 비트맵 리 사이클, 기본 물고기
//                if(fish_List.get(i).get_First_Test_Object() && fish_List.get(i).get_Fish_Class() == 1 && fish_List.get(i) instanceof Fish_Touch_Default && fish_List.get(i).get_Class_Num() == 0){
//                    game_thread.recycle_Init_Explain_Fish_Default();
//                }
//                //첫 번째 물고기 삭제 되면 비트맵 리 사이클, 오징어
//                else if(fish_List.get(i).get_First_Test_Object() && fish_List.get(i).get_Fish_Class() == 1 && fish_List.get(i) instanceof Fish_Touch_Squid){
//                    game_thread.recycle_Init_Explain_Squid();
//                }//첫 번째 물고기 삭제 되면 비트맵 리 사이클, 전기 뱀장어
//                else if(fish_List.get(i).get_First_Test_Object() && fish_List.get(i).get_Fish_Class() == 1 && fish_List.get(i) instanceof Fish_Touch_Ell){
//                    game_thread.recycle_Init_Explain_Ell();
//                }else //메모리 관리 기법 [드래그 물고기]
//                    if(fish_List.get(i).get_Fish_Class() == 2 && fish_List.get(i).get_First_Test_Object() ) {
//                        game_thread.recycle_Init_Explain_Fish_Drag();
//                    }

                fish_List.remove(i);
                break;
            }



            //물고기가 y축 으로 넘어가면 삭제
            if(fish_List.get(i).get_Fish_Point_Y() >= getHeight() - 30
                    || fish_List.get(i).get_Fish_Point_X() < -30                  //X축으로 0 보다 작으면 삭제
                    || fish_List.get(i).get_Fish_Point_X() > getWidth() + 30){       //X축으로 화면 보다 크면 삭제

                //물고기가 y축으로 넘어가면 hp, 해파리는 x축 기준으로 사라지기때문에
                if(fish_List.get(i).get_Fish_Point_Y() >= getHeight() - 30) {
                    main_Character.set_Hp_Minus();
                }


                //첫 번째 물고기 삭제 되면 비트맵 리 사이클, 기본 물고기
//                if(fish_List.get(i).get_First_Test_Object() && fish_List.get(i).get_Fish_Class() == 1 && fish_List.get(i) instanceof Fish_Touch_Default && fish_List.get(i).get_Class_Num() == 0){
//                    game_thread.recycle_Init_Explain_Fish_Default();
//                }
//                //첫 번째 물고기 삭제 되면 비트맵 리 사이클, 오징어
//                else if(fish_List.get(i).get_First_Test_Object() && fish_List.get(i).get_Fish_Class() == 1 && fish_List.get(i) instanceof Fish_Touch_Squid){
//                    game_thread.recycle_Init_Explain_Squid();
//                }//첫 번째 물고기 삭제 되면 비트맵 리 사이클, 전기 뱀장어
//                else if(fish_List.get(i).get_First_Test_Object() && fish_List.get(i).get_Fish_Class() == 1 && fish_List.get(i) instanceof Fish_Touch_Ell){
//                    game_thread.recycle_Init_Explain_Ell();
//                }else //메모리 관리 기법 [드래그 물고기]
//                    if(fish_List.get(i).get_Fish_Class() == 2 && fish_List.get(i).get_First_Test_Object() ) {
//                        game_thread.recycle_Init_Explain_Fish_Drag();
//                    }


                fish_List.remove(i);


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

//                //메모리 관리 기법 [달팽이]
//                if(ground_List.get(i).get_Ground_Class() == 1 && ground_List.get(i).get_First_Test_Object() && ground_List.get(i).get_Class_Num() == 0) {
//
//                    game_thread.recycle_Init_Explain_Snail();
//                }
//                //비트맵 메모리 관리 기법 [성게]
//                else if(ground_List.get(i).get_First_Test_Object() && ground_List.get(i).get_Ground_Class() == 10) {
//                    game_thread.recycle_Init_Explain_Urchin();
//                }
//                else //메모리 관리 기법 [꽃게]
//                    if(ground_List.get(i).get_Ground_Class() == 2 && ground_List.get(i).get_First_Test_Object()) {
//                        game_thread.recycle_Init_Explain_Crab();
//                    }
//                    else
                        if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 0){
                        game_thread.recycle_Land_Mark_1();    Log.e("aaaa", "1");
                    }else if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 1){
                        game_thread.recycle_Land_Mark_2();    Log.e("aaaa", "2");
                    }else if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 2){
                        game_thread.recycle_Land_Mark_3();    Log.e("aaaa", "3");
                    }else if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 3){
                        game_thread.recycle_Land_Mark_4();    Log.e("aaaa", "4");
                    }else if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 4){
                        game_thread.recycle_Land_Mark_5();    Log.e("aaaa", "5");
                    }else if(ground_List.get(i).get_Ground_Class() == 11 && ground_List.get(i).get_Class_Num() == 5){
                        game_thread.recycle_Land_Mark_6();    Log.e("aaaa", "5");
                    }

                ground_List.remove(i);
                break;
            }



            //물고기가 y축 으로 넘어가면 삭제
            if(ground_List.get(i).get_Ground_Point_Y() >= getHeight() - 30){


//                //메모리 관리 기법 [달팽이]
//                if(ground_List.get(i).get_Ground_Class() == 1 && ground_List.get(i).get_First_Test_Object() && ground_List.get(i).get_Class_Num() == 0) {
//                    game_thread.recycle_Init_Explain_Snail();
//                }
//                //비트맵 메모리 관리 기법 [성게]
//                else if(ground_List.get(i).get_First_Test_Object() && ground_List.get(i).get_Ground_Class() == 10) {
//                    game_thread.recycle_Init_Explain_Urchin();
//                }
//                else //메모리 관리 기법 [꽃게]
//                    if(ground_List.get(i).get_Ground_Class() == 2 && ground_List.get(i).get_First_Test_Object()) {
//                        game_thread.recycle_Init_Explain_Crab();
//                    }


                ground_List.remove(i);

                //그라운드 생명체 y축 넘어가면 hp 감소
                main_Character.set_Hp_Minus();
                break;
            }


            //악어 삭제
            if(ground_List.get(i) instanceof  Ground_Touch_Crocodile){
                if(ground_List.get(i).get_Ground_Point_X() < - ground_List.get(i).get_Width_Size()){
                    ground_List.remove(i);
                }else if(ground_List.get(i).get_Ground_Point_X() > window_Width + ground_List.get(i).get_Width_Size()){
                    ground_List.remove(i);
                }
            }


        }

        }catch (IndexOutOfBoundsException e){
            Log.e("ground", e.toString() + " ground");
        }

    }

public void skill_Fish_Attack(){
    if(main_Character instanceof Main_Character_Shellfish_Tear2){
        if(random.nextInt(100) < 50) {
            ((Main_Character_Shellfish_Tear2) main_Character).stop_Enemy(fish_List.get(smallFishIndex));
            fish_List.get(smallFishIndex).set_Slow_Effect();
        }
    }else //집게발 소환
        if(main_Character instanceof Main_Character_Shellfish_Tear4 && random.nextInt(100) > 80) {
            skill_Crab_Claws = new Skill_Crab_Claws(fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(70, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(70, _context));
            skill_Crab_Claws_List.add(skill_Crab_Claws);
            soundPool.play(sound_Effect[9], 1F, 1F, 0, 0, 1.0F);
        }else //간장게장 집게발 소환
            if(main_Character instanceof Main_Character_Shellfish_Tear5 && random.nextInt(100) > 80){
                skill_Soycrab_Claws = new Skill_Soycrab_Claws(fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(70, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(70, _context));
                skill_Soycrab_Claws_List.add(skill_Soycrab_Claws);
                soundPool.play(sound_Effect[9], 1F, 1F, 0, 0, 1.0F);
            }else //레이저 소환
                if(main_Character instanceof Main_Character_Moulluse_Tear6 && random.nextInt(100) > 80){
                    skill_Laser = new Skill_Laser(- convertPixelsToDp(1000, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y(), window_Width);
                    skill_Laser_List.add(skill_Laser);
                    soundPool.play(sound_Effect[10], 1F, 1F, 0, 0, 1F);
                }else  //가오리 독 걸기
                    if(main_Character instanceof Main_Character_Fish_Tear6){
                        fish_List.get(smallFishIndex).set_Status_Poison(5);
                    }else  //티어2 가시고기 가시 소환
                        if(main_Character instanceof Main_Character_Fish_Tear2 && random.nextInt(100) > 30){
                            skill_Thorn = new Skill_Thorn(fish_List.get(smallFishIndex).get_Fish_Point_X(), fish_List.get(smallFishIndex).get_Fish_Point_Y() + convertPixelsToDp(10, _context));
                            skill_Thorn_List.add(skill_Thorn);

                            fish_List.get(smallFishIndex).set_Hp_Minus();
                        }else //독구름 소환
                            if(main_Character instanceof Main_Character_Moulluse_Tear10){
                                skill_poison_cloud = new Skill_Poison_Cloud(fish_List.get(smallFishIndex).get_Fish_Point_X() - (skill_Poison1_img[0].getWidth()/2), fish_List.get(smallFishIndex).get_Fish_Point_Y()  );
                                skill_poison_cloud_List.add(skill_poison_cloud);
                            }else   //지진 소환
                                if(main_Character instanceof Main_Character_Fish_Tear4){
                                    skill_Earthquake = new Skill_Earthquake(fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(45, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(20, _context)  );
                                    skill_Earthquake_List.add(skill_Earthquake);
                                }else if(main_Character instanceof Main_Character_Fish_Tear3){
                                    //이빨 지뢰 소환
                                    skill_Teeth_Mine = new Skill_Teeth_Mine(0,0);
                                    skill_Teeth_Mine.set_Position(window_Width, window_Height);
                                    skill_Teeth_Mine_List.add(skill_Teeth_Mine);
                                }else if(main_Character instanceof Main_Character_Fish_Tear10){
                                    //티어 10 전역에 가시 생성

                                    for(int i=0; i<fish_List.size(); i++){
                                        skill_Thorn = new Skill_Thorn(fish_List.get(i).get_Fish_Point_X() -convertPixelsToDp(15, _context), fish_List.get(i).get_Fish_Point_Y() );
                                        skill_Thorn_List.add(skill_Thorn);
                                        fish_List.get(i).set_Hp_Minus();
                                    }
                                    for(int i=0; i<ground_List.size(); i++){
                                        skill_Thorn = new Skill_Thorn(ground_List.get(i).get_Ground_Point_X() -convertPixelsToDp(15, _context), ground_List.get(i).get_Ground_Point_Y() );
                                        skill_Thorn_List.add(skill_Thorn);
                                        ground_List.get(i).set_Ground_Hp_Minus();
                                    }


                                }else if(main_Character instanceof Main_Character_Shellfish_Tear8){
                                    //갑각류 티어 8 쌍 집게 소환
                                    skill_Crab_Claws = new Skill_Crab_Claws(fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(100, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(70, _context));
                                    skill_Crab_Claws_List.add(skill_Crab_Claws);

                                    skill_Crab_Claws = new Skill_Crab_Claws(fish_List.get(smallFishIndex).get_Fish_Point_X() + convertPixelsToDp(50, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(20, _context));

                                    skill_Crab_Claws_List.add(skill_Crab_Claws);
                                    soundPool.play(sound_Effect[9], 1F, 1F, 0, 0, 1.0F);

                                }else if(main_Character instanceof Main_Character_Moulluse_Tear3){
                                    //슬로우 구름 생성
                                    skill_Slow_Cloud = new Skill_Slow_Cloud(fish_List.get(smallFishIndex).get_Fish_Point_X() - (skill_Slow_Cloud_img.getWidth()/2) , fish_List.get(smallFishIndex).get_Fish_Point_Y() - (skill_Slow_Cloud_img.getHeight()/4));
                                    skill_Slow_Cloud_List.add(skill_Slow_Cloud);

                                }else if(main_Character instanceof Main_Character_Moulluse_Tear7){
                                    //독 주입 [해파리]
                                    fish_List.get(smallFishIndex).set_Status_Poison(10);
                                }else if(main_Character instanceof Main_Character_Moulluse_Tear8){
                                    //독 폭탄
                                    skill_Boom_Poison = new Skill_Boom_Poison(fish_List.get(smallFishIndex).get_Fish_Point_X() - (skill_Boom_Poison_img[0].getWidth()/2) , fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(150, _context));
                                    skill_Boom_Poison_List.add(skill_Boom_Poison);

                                }else if(main_Character instanceof Main_Character_Fish_Tear9){

                                    //바다뱀 소환
                                    skill_Sea_Snake = new Skill_Sea_Snake(window_Width-200, fish_List.get(smallFishIndex).get_Fish_Point_Y());
                                    skill_Sea_Snake_List.add(skill_Sea_Snake);

                                }else if(main_Character instanceof Main_Character_Shellfish_Tear9){
                                    //파도 소환
                                    skill_Wave = new Skill_Wave(fish_List.get(smallFishIndex).get_Fish_Point_X() - (skill_Wave_img[0].getWidth()/2), window_Height);
                                    skill_Wave_List.add(skill_Wave);
                                }else if(main_Character instanceof Main_Character_Moulluse_Tear9){
                                    //벽 소환
                                    skill_Wall = new Skill_Wall(fish_List.get(smallFishIndex).get_Fish_Point_X() - (skill_wall_img[0].getWidth()/2), fish_List.get(smallFishIndex).get_Fish_Point_Y());
                                    skill_Wall_List.add(skill_Wall);
                                }else if(main_Character instanceof Main_Character_Fish_Tear5){
                                    //이빨2 소환
                                    skill_Teeth_Mine2 = new Skill_Teeth_Mine2(0,0);
                                    skill_Teeth_Mine2.set_Position(window_Width, window_Height);
                                    skill_Teeth_Mine2_List.add(skill_Teeth_Mine2);
                                }else if(main_Character instanceof Main_Character_Shellfish_Tear6){
                                    //가시2 소환
                                    skill_Thorn2 = new Skill_Thorn2(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                    skill_Thorn2_List.add(skill_Thorn2);
                                }else if(main_Character instanceof Main_Character_Fish_Tear7){
                                    //번개 소환
                                    skill_Lightning = new Skill_Lightning(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                    skill_Lightning_List.add(skill_Lightning);
                                }else if(main_Character instanceof Main_Character_Fish_Tear8){
                                    //번개2 소환
                                    skill_Lightning2 = new Skill_Lightning2(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                    skill_Lightning2_List.add(skill_Lightning2);
                                }else if(main_Character instanceof Main_Character_Shellfish_Tear10){
                                    //거북이 스톰프
                                    skill_Stomp = new Skill_Stomp(fish_List.get(smallFishIndex).get_Fish_Point_X() - convertPixelsToDp(70, _context), fish_List.get(smallFishIndex).get_Fish_Point_Y() - convertPixelsToDp(70, _context));
                                    skill_Stomp_List.add(skill_Stomp);

                                }else if(main_Character instanceof Main_Character_Shellfish_Tear7){
                                    //튀김 지뢰 소환
                                    skill_Fry = new Skill_Fry(0,0);
                                    skill_Fry.set_Position(window_Width, window_Height);
                                    skill_Fry_List.add(skill_Fry);
                                }else if(main_Character instanceof Main_Character_Moulluse_Tear4){
                                    //버터 소환
                                    skill_Butter = new Skill_Butter(0,0);
                                    skill_Butter.set_Position(window_Width, window_Height);
                                    skill_Butter_List.add(skill_Butter);
                                }else if(main_Character instanceof Main_Character_Moulluse_Tear5){
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
        if(main_Character instanceof Main_Character_Shellfish_Tear2){
            //20퍼 확률로 속도 낮춘다.
            if(random.nextInt(100) < 50) {
                ((Main_Character_Shellfish_Tear2) main_Character).stop_Enemy(ground_List.get(ground_Remove_Temp));
                ground_List.get(ground_Remove_Temp).set_Slow_Effect();
            }
        }else //집게발 소환
            if(main_Character instanceof Main_Character_Shellfish_Tear4 && random.nextInt(100) > 80) {
                skill_Crab_Claws = new Skill_Crab_Claws(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(70, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(70, _context));
                skill_Crab_Claws_List.add(skill_Crab_Claws);
                soundPool.play(sound_Effect[9], 1F, 1F, 0, 0, 1.0F);


            }else //간장게장 집게발 소환
                if(main_Character instanceof Main_Character_Shellfish_Tear5 && random.nextInt(100) > 80){
                    skill_Soycrab_Claws = new Skill_Soycrab_Claws(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(70, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(70, _context));
                    skill_Soycrab_Claws_List.add(skill_Soycrab_Claws);
                    soundPool.play(sound_Effect[9], 1F, 1F, 0, 0, 1.0F);
                }else //레이저 소환
                    if(main_Character instanceof Main_Character_Moulluse_Tear6 && random.nextInt(100) > 80){
                        skill_Laser = new Skill_Laser(- convertPixelsToDp(1000, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y(), window_Width);
                        skill_Laser_List.add(skill_Laser);
                        soundPool.play(sound_Effect[10], 1F, 1F, 0, 0, 1F);
                    }else  //가오리 독 걸기
                        if(main_Character instanceof Main_Character_Fish_Tear6){
                            ground_List.get(ground_Remove_Temp).set_Status_Poison(5);
                        } else  //티어2 가시고기 가시 소환
                            if(main_Character instanceof Main_Character_Fish_Tear2  && random.nextInt(100) > 30){

                                skill_Thorn = new Skill_Thorn(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() -convertPixelsToDp(15, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() );
                                skill_Thorn_List.add(skill_Thorn);

                                ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();
                            }else //독구름 소환
                                if(main_Character instanceof Main_Character_Moulluse_Tear10){
                                    skill_poison_cloud = new Skill_Poison_Cloud(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - (skill_Poison1_img[0].getWidth()/2) , ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() );
                                    skill_poison_cloud_List.add(skill_poison_cloud);
                                }else   //지진 소환
                                    if(main_Character instanceof Main_Character_Fish_Tear4){
                                        skill_Earthquake = new Skill_Earthquake(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(45, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y()  - convertPixelsToDp(20, _context) );
                                        skill_Earthquake_List.add(skill_Earthquake);
                                    }else if(main_Character instanceof Main_Character_Fish_Tear3){
                                        //이빨 지뢰 소환
                                        skill_Teeth_Mine = new Skill_Teeth_Mine(0,0);
                                        skill_Teeth_Mine.set_Position(window_Width, window_Height);
                                        skill_Teeth_Mine_List.add(skill_Teeth_Mine);
                                    }else if(main_Character instanceof Main_Character_Fish_Tear10){
                                        //티어 10 전역에 가시 생성

                                        for(int i=0; i<fish_List.size(); i++){
                                            skill_Thorn = new Skill_Thorn(fish_List.get(i).get_Fish_Point_X() -convertPixelsToDp(15, _context), fish_List.get(i).get_Fish_Point_Y() );
                                            skill_Thorn_List.add(skill_Thorn);
                                            fish_List.get(i).set_Hp_Minus();
                                        }
                                        for(int i=0; i<ground_List.size(); i++){
                                            skill_Thorn = new Skill_Thorn(ground_List.get(i).get_Ground_Point_X() -convertPixelsToDp(15, _context), ground_List.get(i).get_Ground_Point_Y() );
                                            skill_Thorn_List.add(skill_Thorn);
                                            ground_List.get(i).set_Ground_Hp_Minus();
                                        }

                                    }else if(main_Character instanceof Main_Character_Shellfish_Tear8){
                                        //갑각류 티어 8 쌍 집게 소환
                                        skill_Crab_Claws = new Skill_Crab_Claws(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(100, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(70, _context));
                                        skill_Crab_Claws_List.add(skill_Crab_Claws);

                                        skill_Crab_Claws = new Skill_Crab_Claws(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + convertPixelsToDp(50, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(20, _context));

                                        skill_Crab_Claws_List.add(skill_Crab_Claws);

                                        soundPool.play(sound_Effect[9], 1F, 1F, 0, 0, 1.0F);

                                    }else if(main_Character instanceof Main_Character_Moulluse_Tear3){
                                        //슬로우 구름 생성
                                        skill_Slow_Cloud = new Skill_Slow_Cloud(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - (skill_Slow_Cloud_img.getWidth()/2) , ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - (skill_Slow_Cloud_img.getHeight()/4));
                                        skill_Slow_Cloud_List.add(skill_Slow_Cloud);

                                    }else if(main_Character instanceof Main_Character_Moulluse_Tear7){
                                        //독 주입 [해파리]
                                        ground_List.get(ground_Remove_Temp).set_Status_Poison(10);
                                    }else if(main_Character instanceof Main_Character_Moulluse_Tear8){
                                        //독 폭탄
                                        skill_Boom_Poison = new Skill_Boom_Poison(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - (skill_Boom_Poison_img[0].getWidth()/2) , ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(150, _context));
                                        skill_Boom_Poison_List.add(skill_Boom_Poison);

                                    }else if(main_Character instanceof Main_Character_Fish_Tear9){

                                        //바다뱀 소환
                                        skill_Sea_Snake = new Skill_Sea_Snake(window_Width-200, ground_List.get(ground_Remove_Temp).get_Ground_Point_Y());
                                        skill_Sea_Snake_List.add(skill_Sea_Snake);

                                    }else if(main_Character instanceof Main_Character_Shellfish_Tear9){
                                        //파도 소환
                                        skill_Wave = new Skill_Wave(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - (skill_Wave_img[0].getWidth()/2), window_Height);
                                        skill_Wave_List.add(skill_Wave);
                                    }else if(main_Character instanceof Main_Character_Moulluse_Tear9){
                                        //벽 소환
                                        skill_Wall = new Skill_Wall(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - (skill_wall_img[0].getWidth()/2), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y());
                                        skill_Wall_List.add(skill_Wall);
                                    }else if(main_Character instanceof Main_Character_Fish_Tear5){
                                        //이빨2 소환
                                        skill_Teeth_Mine2 = new Skill_Teeth_Mine2(0,0);
                                        skill_Teeth_Mine2.set_Position(window_Width, window_Height);
                                        skill_Teeth_Mine2_List.add(skill_Teeth_Mine2);
                                    }else if(main_Character instanceof Main_Character_Shellfish_Tear6){
                                        //가시2 소환
                                        skill_Thorn2 = new Skill_Thorn2(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                        skill_Thorn2_List.add(skill_Thorn2);
                                    }else if(main_Character instanceof Main_Character_Fish_Tear7){
                                        //번개 소환
                                        skill_Lightning = new Skill_Lightning(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                        skill_Lightning_List.add(skill_Lightning);
                                    }else if(main_Character instanceof Main_Character_Fish_Tear8){
                                        //번개2 소환
                                        skill_Lightning2 = new Skill_Lightning2(30 + random.nextFloat() * (window_Width - 30), window_Height);
                                        skill_Lightning2_List.add(skill_Lightning2);
                                    }else if(main_Character instanceof Main_Character_Shellfish_Tear10){
                                        //거북이 스톰프
                                        skill_Stomp = new Skill_Stomp(ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - convertPixelsToDp(70, _context), ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - convertPixelsToDp(70, _context));
                                        skill_Stomp_List.add(skill_Stomp);

                                    }else if(main_Character instanceof Main_Character_Shellfish_Tear7){
                                        //튀김 지뢰 소환
                                        skill_Fry = new Skill_Fry(0,0);
                                        skill_Fry.set_Position(window_Width, window_Height);
                                        skill_Fry_List.add(skill_Fry);
                                    }else if(main_Character instanceof Main_Character_Moulluse_Tear4){
                                        //버터 소환
                                        skill_Butter = new Skill_Butter(0,0);
                                        skill_Butter.set_Position(window_Width, window_Height);
                                        skill_Butter_List.add(skill_Butter);
                                    }else if(main_Character instanceof Main_Character_Moulluse_Tear5){
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
            for(int i=0; i<ground_List.size(); i++){    // +- 45 는 판정을 좋게 하기 위해 추가
                //달팽이를 가장 먼저 찾는다.
                if(ground_List.get(i) instanceof Ground_Touch_Snail && ground_Hit_Drag == false){
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


                        snail_Ground_Hit_Flag = true;   //달팽이 터치 이벤트 doDraw에서 발생


                        Score++;
                        //클릭된 달팽이의체력을 깍는다.
                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();




                        //            delete_Ground_Select(ground_Remove_Temp);   //피가 감소된 객체 0일때 삭제
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드

                        return true;

                    }
                }

                //선택된 소라게가 존재한다면.
                if(ground_List.get(i) instanceof Ground_Touch_Hermit && ground_Hit_Drag == false){
                    if(        x >= ground_List.get(i).get_Ground_Point_X()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width()
                            && y >= ground_List.get(i).get_Ground_Point_Y()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height()){
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


                        hermit_Ground_Hit_Flag = true;   //달팽이 터치 이벤트 doDraw에서 발생


                        Score++;
                        //클릭된 달팽이의체력을 깍는다.
                        if(!((Ground_Touch_Hermit)ground_List.get(ground_Remove_Temp)).get_Immortal_Mode()) {
                            ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();
                        }
                        ((Ground_Touch_Hermit)ground_List.get(ground_Remove_Temp)).set_Immortal_Mode();




                        //            delete_Ground_Select(ground_Remove_Temp);   //피가 감소된 객체 0일때 삭제
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 0, 0, 1.0F);   //달팽이 기본 팝 사운드
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


                        tempInt = random.nextInt(5);
                        wave_Ground_Hit_Flag = true; //꽃게 터치 이벤트 doDraw에서 발생

                        //드래그된 파도의 체력을 깍는다.
                        if(main_Character.get_Damage() > 1) {
                            character_Damege = 1 + random.nextInt(main_Character.get_Damage());
                        }

                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);
                        soundPool.play(sound_Effect[11], 0.05F, 0.05F, 0, 0, 1.0F);   //드래그 사운드
                        Score++;            //점수 추가
                        main_Character.set_Character_Experience();  //경험치 추가
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


                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);
                        soundPool.play(sound_Effect[2 + random.nextInt(2)], 0.05F, 0.05F, 0, 0, 1.0F);   //드래그 사운드
                        Score++;            //점수 추가
                        main_Character.set_Character_Experience();  //경험치 추가
                        return true;

                    }
                }


                //조게를찾는다.
                if(drag_Action_Move > 3 && ground_List.get(i) instanceof Ground_Drag_Clam && ground_Hit_Drag){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()/4
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + ground_List.get(i).get_Width_Size()/4
                            && y >= ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size()/4
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()/4){

                        ground_Remove_Temp = i;


                        tempInt = random.nextInt(5);
                        clam_Ground_Hit_Flag = true; //꽃게 터치 이벤트 doDraw에서 발생

                        //드래그된 조게의 체력을 깍는다.
                        if(main_Character.get_Damage() > 1) {
                            character_Damege = 1 + random.nextInt(main_Character.get_Damage());
                        }


                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);
                        soundPool.play(sound_Effect[2 + random.nextInt(2)], 0.05F, 0.05F, 0, 0, 1.0F);   //드래그 사운드
                        Score++;            //점수 추가
//                main_Character.set_Character_Experience();  //경험치 추가
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


                        //드래그된 조게의 체력을 깍는다.
                        if(main_Character.get_Damage() > 1) {
                            character_Damege = 1 + random.nextInt(main_Character.get_Damage());
                        }

                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);

                        return true;


                    }
                }


                //랜드마크를 찾는다.
                if(drag_Action_Move > 3 && ground_List.get(i) instanceof Land_Mark && ground_Hit_Drag){
                    if(        x >= ground_List.get(i).get_Ground_Point_X() - ground_List.get(i).get_Width_Size()
                            && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + ground_List.get(i).get_Width_Size()
                            && y >= ground_List.get(i).get_Ground_Point_Y() - ground_List.get(i).get_Height_Size()
                            && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + ground_List.get(i).get_Height_Size()){

                        ground_Remove_Temp = i;
                        tempInt = random.nextInt(5);
                        land_Mark_Hit_Flag = true; //랜드마크 이펙트 doDraw에서 발생

                        //드래그된 랜드마크의 체력을 깍는다.
                        if(main_Character.get_Damage() > 1) {
                            character_Damege = 1 + random.nextInt(main_Character.get_Damage());
                        }


                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus(character_Damege);
                        soundPool.play(sound_Effect[2 + random.nextInt(2)], 0.05F, 0.05F, 0, 0, 1.0F);   //드래그 사운드
                        Score++;            //점수 추가
//                main_Character.set_Character_Experience();  //경험치 추가

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

                        Log.e("e","성게");


                        effect_Temp = effect_Black_img[4];


                        seaurchin_Ground_Hit_Flag = true;   //성게 터치 이벤트는 doDraw 에서
                        ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();  //성게 삭제,

                        //성게가 공격 모드 일때, 게딱지가 아닐때
                        if(((Ground_Trap_Urchin)ground_List.get(ground_Remove_Temp)).get_Urchin_Attack_Mode() && !(main_Character instanceof Main_Character_Shellfish_Tear3)){
                            //get_Urchin_Attack_Mode()
                            main_Character.set_Hp_Minus();
                        }


                        //메인 캐릭터 hp 감소 루틴 추가 해야함
                        soundPool.play(sound_Effect[random.nextInt(2)], 0.5F, 0.5F, 0, 0, 1.0F);   //물고기 기본 팝 사운드
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
            for(int i=fish_List.size() - 1; i>=0; i--){

                //전달받은 물고기 인자가 아닐때 생깜
                if(fish_List.get(i).get_Fish_Class() != fish_Class){
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
                skill_Fish_Attack();




                if(fish_Class == 1){        //전달 받은 인자가 기본 물고기 일때.


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


                    soundPool.play(sound_Effect[random.nextInt(2)], 0.5F, 0.5F, 0, 0, 1.0F);   //물고기 기본 팝 사운드


                    fish_List.get(smallFishIndex).set_Hp_Minus();            //풍타디 처럼 물고기 hp 깍으면 색깔 변경

                    default_Fish_Hit_Flag = true;   //그림은 드로우에서






//                    delete_Fish_Select(smallFishIndex);     //피가0이 된 물고기 삭제.
                    Score++;
                    main_Character.set_Character_Experience(); //경험치 추가
                    return true;

                }



                if(fish_List.get(smallFishIndex).get_Fish_Hp() > 0) {        //드래그 속도까 빨라서 0밑으로내려감 방지

                    if(main_Character.get_Damage() > 1) {
                        character_Damege = 1 + random.nextInt(main_Character.get_Damage());
                    }



                    fish_List.get(smallFishIndex).set_Hp_Minus(character_Damege);            //풍타디 처럼 물고기 hp 깍으면 색깔 변경
                    Score++;                                                 //점수 증가
                    main_Character.set_Character_Experience();  //경험치 추가

                    if(fish_Class == 2) {
                        //드래그시 공격당한다는 느낌 주기 위해


                        drag_Fish_Hit_Flag = true;  //그림은 드로우에서
                        soundPool.play(sound_Effect[2 + random.nextInt(2)], 0.05F, 0.05F, 0, 0, 1.0F);   //드래그 사운드
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

        }

        if(main_Character.get_Transform_Chra_Flag()) {
            main_Character_Draw(main_Character_Img_1);
        }else {
            main_Character_Draw(main_Character_Img);
        }


    }



    //메인 캐릭터 진화 할때 새로 그리기 #Score 진화할 점수 #character_img 캐릭터 이미지 배열
    private void main_Character_Draw(Bitmap[] character_img){


        // #50 부분에 각 캐릭터마다 모양 변화 경험치가 들어가야함
        if(main_Character.set_Character_Upgrade(50)){
            //점수에 따라 곰팡이 진화
            main_Character.Set_Main_Character_Mode_Status();

        }
        //곰팡이 공격상태, 메인캐릭터 그리기
        if(main_Character.get_Attack_State()){

            if(!main_Character.get_Direct_Status()) {


                    temp_Fish = draw.reverse_Image(character_img[main_Character.get_Main_Character_Mode_Status() + 1]);
                    draw.draw_Bmp(canvas, temp_Fish, main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y());


            }else {

                draw.draw_Bmp(canvas, character_img[main_Character.get_Main_Character_Mode_Status()+1], main_Character.get_Main_Character_Point_X(),main_Character.get_Main_Character_Point_Y());

            }



        }else { //곰팡이 기본상태

            if(!main_Character.get_Direct_Status()) {
                temp_Fish = draw.reverse_Image(character_img[main_Character.get_Main_Character_Mode_Status()]);
                draw.draw_Bmp(canvas, temp_Fish, main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y());
            }else {

                draw.draw_Bmp(canvas, character_img[main_Character.get_Main_Character_Mode_Status()], main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y());
            }
        }
    }


    GraphicButton confirm_Button_1; //메인 캐릭터 진화시 뜨는 확인창
    GraphicButton revolution_Button; //경험치 다 찾을때 띄울 버튼

    GraphicButton confirm_Button_card_1; //메인 캐릭터 진화시 뜨는 확인창
    GraphicButton confirm_Button_card_2; //메인 캐릭터 진화시 뜨는 확인창
    GraphicButton confirm_Button_card_3; //메인 캐릭터 진화시 뜨는 확인창



    /**
     * 터치 이벤트
     */
    int touchx;
    int touchy;
    private boolean revolution_Button_Activation = false;
    private boolean revolution_Button_Activation_Down = false;
    private boolean revolution_Button_Activation_Up = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        touchx = (int)event.getX();
        touchy = (int)event.getY();

        if(!mRun && !pause_State) {


            switch (event.getAction()) {

                case MotionEvent.ACTION_UP:

                    confirm_Button_1.setPress(false);    //버튼 상태 초기화



                    if (confirm_Button_1.touch(touchx, touchy)) { //버튼 클릭 행동    //확인
                        //확인 버튼
//                  Score = 1000;
                    m_Run_True();   //게임 재게
                    revolution_Flag_Confirm = false; //변신창 확인 누르먄 재시작




                    }else if(confirm_Button_card_1.touch(touchx, touchy) || confirm_Button_card_2.touch(touchx, touchy)){
                        //진화창 버튼


                        //진화 성공시
                        int rand_Temp = random.nextInt(100);

                        if(main_Character.get_Tear() == 0) {
                            if (rand_Temp < 30) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Shellfish_Tear_1();
                                main_Character = new Main_Character_Shellfish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else if (rand_Temp < 60) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Fish_Tear_1();
                                main_Character = new Main_Character_Fish_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());


                            } else {
                                //메모리 관리
                                game_thread.function_Explain_Window_Moulluse_Tear_1();
                                main_Character = new Main_Character_Moulluse_Tear1(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());


                            }
                            main_Character.set_Tear(1);
                        }else if(main_Character.get_Tear() == 1){

                            //메모리 관리 기법
                            if(main_Character instanceof Main_Character_Shellfish_Tear1){
//                                explain_Window_Origin_Crab.recycle();
                                explain_Window_Origin_Crab = null;



                            }else if(main_Character instanceof Main_Character_Fish_Tear1){
//                                explain_Window_Origin_Fish.recycle();
                                explain_Window_Origin_Fish = null;
                            }else if(main_Character instanceof Main_Character_Moulluse_Tear1){
//                                explain_Window_Origin_Squid.recycle();
                                explain_Window_Origin_Squid = null;
                            }



                            if (rand_Temp < 30) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Shellfish_Tear_2();
                                main_Character = new Main_Character_Shellfish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());


                            } else if (rand_Temp < 60) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Fish_Tear_2();
                                game_thread.function_Skill_Thorn_img();
                                main_Character = new Main_Character_Fish_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else {
                                //메모리 관리
                                game_thread.function_Explain_Window_Moulluse_Tear_2();
                                main_Character = new Main_Character_Moulluse_Tear2(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());


                            }
                            main_Character.set_Tear(2);
                        }else if(main_Character.get_Tear() == 2){


                            //메모리 관리 기법
                            if(main_Character instanceof Main_Character_Shellfish_Tear2){

                            }else if(main_Character instanceof Main_Character_Fish_Tear2){
//                                game_thread.recycle_Skill_Thorn_img();
                            }else if(main_Character instanceof Main_Character_Moulluse_Tear2){

                            }



                            if (rand_Temp < 30) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Shellfish_Tear_3();
                                main_Character = new Main_Character_Shellfish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());


                            } else if (rand_Temp < 60) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Fish_Tear_3();
                                game_thread.function_Skill_Teeth_mine_img();
                                main_Character = new Main_Character_Fish_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else {
                                //메모리 관리
                                game_thread.function_Explain_Window_Moulluse_Tear_3();
                                game_thread.function_Skill_Slow_Cloud_img();
                                main_Character = new Main_Character_Moulluse_Tear3(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            }
                            main_Character.set_Tear(3);
                        }else if(main_Character.get_Tear() == 3){


                            //메모리 관리 기법
                            if(main_Character instanceof Main_Character_Shellfish_Tear3){

                            }else if(main_Character instanceof Main_Character_Fish_Tear3){
//                                game_thread.recycle_Skill_Teeth_mine_img();
                            }else if(main_Character instanceof Main_Character_Moulluse_Tear3){
//                                game_thread.recycle_Skill_Slow_Cloud_img();
                            }


                            if (rand_Temp < 30) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Shellfish_Tear_4();
                                game_thread.function_Skill_Crab_img();
                                main_Character = new Main_Character_Shellfish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else if (rand_Temp < 60) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Fish_Tear_4();
                                game_thread.function_Skill_earthquake_img();
                                main_Character = new Main_Character_Fish_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else {
                                //메모리 관리
                                game_thread.function_Explain_Window_Moulluse_Tear_4();
                                game_thread.function_Skill_Butter_img();
                                main_Character = new Main_Character_Moulluse_Tear4(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            }
                            main_Character.set_Tear(4);
                        }else if(main_Character.get_Tear() == 4){


                            //메모리 관리 기법
                            if(main_Character instanceof Main_Character_Shellfish_Tear4){
//                                game_thread.recycle_Skill_Crab_img();
                            }else if(main_Character instanceof Main_Character_Fish_Tear4){
//                                game_thread.recycle_Skill_earthquake_img();
                            }else if(main_Character instanceof Main_Character_Moulluse_Tear4){
//                                game_thread.recycle_Skill_Butter_img();
                            }


                            if (rand_Temp < 30) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Shellfish_Tear_5();
                                game_thread.function_Skill_Soycrab_img();
                                main_Character = new Main_Character_Shellfish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else if (rand_Temp < 60) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Fish_Tear_5();
                                game_thread.function_Skill_Teeth_mine2_img();
                                main_Character = new Main_Character_Fish_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else {
                                //메모리 관리
                                game_thread.function_Explain_Window_Moulluse_Tear_5();
                                game_thread.function_Skill_Fork_img();
                                main_Character = new Main_Character_Moulluse_Tear5(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            }
                            main_Character.set_Tear(5);
                        }else if(main_Character.get_Tear() == 5){

                            //메모리 관리 기법
                            if(main_Character instanceof Main_Character_Shellfish_Tear5){
//                                game_thread.recycle_Skill_Soycrab_img();
                            }else if(main_Character instanceof Main_Character_Fish_Tear5){
//                                game_thread.recycle_Skill_Teeth_mine2_img();
                            }else if(main_Character instanceof Main_Character_Moulluse_Tear5){
//                                game_thread.recycle_Skill_Fork_img();
                            }


                            if (rand_Temp < 30) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Shellfish_Tear_6();
                                game_thread.function_Skill_Thorn2_img();
                                main_Character = new Main_Character_Shellfish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else if (rand_Temp < 60) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Fish_Tear_6();
                                main_Character = new Main_Character_Fish_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());


                            } else {//메모리 관리
                                game_thread.function_Explain_Window_Moulluse_Tear_6();
                                game_thread.function_Skill_Laser_img();
                                main_Character = new Main_Character_Moulluse_Tear6(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            }
                            main_Character.set_Tear(6);
                        }else if(main_Character.get_Tear() == 6){

                            //메모리 관리 기법
                            if(main_Character instanceof Main_Character_Shellfish_Tear6){
//                                game_thread.recycle_Skill_Thorn2_img();
                            }else if(main_Character instanceof Main_Character_Fish_Tear6){

                            }else if(main_Character instanceof Main_Character_Moulluse_Tear6){
//                                game_thread.recycle_Skill_Laser_img();
                            }




                            if (rand_Temp < 30) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Shellfish_Tear_7();
                                game_thread.function_Skill_fry_img();
                                main_Character = new Main_Character_Shellfish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width,window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else if (rand_Temp < 60) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Fish_Tear_7();
                                game_thread.function_Skill_lightnign_img();
                                main_Character = new Main_Character_Fish_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else {
                                //메모리 관리
                                game_thread.function_Explain_Window_Moulluse_Tear_7();
                                main_Character = new Main_Character_Moulluse_Tear7(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());


                            }
                            main_Character.set_Tear(7);
                        }else if(main_Character.get_Tear() == 7){

                            //메모리 관리 기법
                            if(main_Character instanceof Main_Character_Shellfish_Tear7){
//                                game_thread.recycle_Skill_fry_img();
                            }else if(main_Character instanceof Main_Character_Fish_Tear7){
//                                game_thread.recycle_Skill_lightnign_img();
                            }else if(main_Character instanceof Main_Character_Moulluse_Tear7){

                            }

                            if (rand_Temp < 30) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Shellfish_Tear_8();
                                game_thread.function_Skill_Crab_img();
                                main_Character = new Main_Character_Shellfish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else if (rand_Temp < 60) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Fish_Tear_8();
                                game_thread.function_Skill_lightnign1_img();
                                main_Character = new Main_Character_Fish_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else {
                                //메모리 관리
                                game_thread.function_Explain_Window_Moulluse_Tear_8();
                                game_thread.function_Skill_Boom_Poison_img();
                                main_Character = new Main_Character_Moulluse_Tear8(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            }
                            main_Character.set_Tear(8);
                        }else if(main_Character.get_Tear() == 8){

                            //메모리 관리 기법
                            if(main_Character instanceof Main_Character_Shellfish_Tear8){
//                                game_thread.recycle_Skill_Crab_img();
                            }else if(main_Character instanceof Main_Character_Fish_Tear8){
//                                game_thread.recycle_Skill_lightnign1_img();
                            }else if(main_Character instanceof Main_Character_Moulluse_Tear8){
//                                game_thread.recycle_Skill_Boom_Poison_img();
                            }

                            if (rand_Temp < 30) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Shellfish_Tear_9();
                                main_Character = new Main_Character_Shellfish_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                                game_thread.function_Skill_Wave_img();
                            } else if (rand_Temp < 60) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Fish_Tear_9();
                                main_Character = new Main_Character_Fish_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                                game_thread.function_Skill_Sea_Snake_img();
                            } else {
                                //메모리 관리
                                game_thread.function_Explain_Window_Moulluse_Tear_9();
                                game_thread.function_Skill_wall_img();
                                main_Character = new Main_Character_Moulluse_Tear9(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            }
                            main_Character.set_Tear(9);
                        }else if(main_Character.get_Tear() == 9){

                            //메모리 관리 기법
                            if(main_Character instanceof Main_Character_Shellfish_Tear8){
//                                game_thread.recycle_Skill_Wave_img();
                            }else if(main_Character instanceof Main_Character_Fish_Tear8){
//                                game_thread.recycle_Skill_Sea_Snake_img();
                            }else if(main_Character instanceof Main_Character_Moulluse_Tear8){
//                                game_thread.recycle_Skill_wall_img();
                            }

                            if (rand_Temp < 30) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Shellfish_Tear_10();
                                game_thread.function_Skill_stomp_img();
                                main_Character = new Main_Character_Shellfish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else if (rand_Temp < 60) {
                                //메모리 관리
                                game_thread.function_Explain_Window_Fish_Tear_10();
                                game_thread.function_Skill_Thorn_img();
                                main_Character = new Main_Character_Fish_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            } else {
                                //메모리 관리
                                game_thread.function_Explain_Window_Moulluse_Tear_10();
                                game_thread.function_Skill_Poison123_img();
                                main_Character = new Main_Character_Moulluse_Tear10(main_Character.get_Main_Character_Point_X(), main_Character.get_Main_Character_Point_Y(), window_Width, window_Height, main_Character_Img[0].getWidth(), main_Character_Img[0].getHeight());

                            }
                            main_Character.set_Tear(10);
                        }




                        //매인 캐릭터가 진화할때마다 메인 캐릭터의 이미지를 변화 시켜준다.
                        Init_Main_Character_Image(_context, main_Character);

                        main_Character.set_Main_Character_Mode_Status_Init();
                        main_Character.set_Hp_Init();

                        m_Run_True();   //게임 재게
                        revolution_Flag_Confirm = true;







//                        }

                    }else if(confirm_Button_card_3.touch(touchx, touchy)){
                        //광고 눌렀을때

                    }

                    break;


            }
        }else { //진화 창 떳을때 확인 버튼

            //게임 터치 이벤트
            if (event.getAction() == MotionEvent.ACTION_DOWN) {           //손가락이 눌렸을때.
                touch_Check = 0;   //터치 체크 상태가 5이하 일때 터치라 판정.

            } else if (event.getAction() == MotionEvent.ACTION_UP) {      //때졌을때.


            try{

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
                    }
                    else {
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





            }catch (IndexOutOfBoundsException ioe){
                Log.e("e","ioeErrorTouch");
            }




            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {    //드래그 중일때


                try {



                    touch_Check++;
                    drag_Action_Move++;

                    if(ground_Hit_Chose(touchx, touchy, true, drag_Action_Move)){
                        // 그라운드

                    }else if(drag_Action_Move > 3) {
                            fish_Hit_Chose(2);                                  //드래그 물고기
                    }

                    if(drag_Action_Move > 3){
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



                }catch (IndexOutOfBoundsException ioe){
                    Log.e("e","ioeErrorDrag");
                }

            }




        }


        //진화의 버튼은 클릭해야 열린다.
        if(revolution_Button.touch(touchx, touchy) && event.getAction() == MotionEvent.ACTION_DOWN){
            revolution_Button_Activation_Down = true;
        }else if(revolution_Button.touch(touchx, touchy) && event.getAction() == MotionEvent.ACTION_UP){
            revolution_Button_Activation_Up = true;
        }

        if(!revolution_Button.touch(touchx, touchy)){
            revolution_Button_Activation_Down = false;
            revolution_Button_Activation_Up = false;
        }

        if(revolution_Button_Activation_Down && revolution_Button_Activation_Up ){
            /**
             * 경험치 다 차면 진화의 버튼 활성화, 진화의 버튼 누르면 진화의 창 등장
             */
            revolution_Button_Activation = true;

        }

        return true;

    }



    //********************************************************************************************//

    /**
     * 오버라이드 된것 시작과 동시에 구성, 교체
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Log.i("[뷰]", "구성");
        Log.e("surfaceCreated","surfaceCreated");
    }
    TimerTask fish_Maker_1;
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Log.i("[뷰]", "교체");
        window_Width = width; //화면의 크기
        window_Height = height;

        game_thread = new Game_Thread(/*holder*/);  //쓰레드가 홈버튼을 누름으로 인해 파괴 된다면 다시 생성
        game_thread.start(); //게임


//         if(game_thread.getState() == Thread.State.TERMINATED) {
//            game_thread = new Game_Thread(/*holder*/);  //쓰레드가 홈버튼을 누름으로 인해 파괴 된다면 다시 생성
//            game_thread.start(); //게임
//            Log.e("surfaceChanged true","surfaceChanged true");
//        }else {
//
//            game_thread.start();
//            //gameMainThread.start();
//        }




        //초기화
        re_Start();


        SharedPreferences pref = _context.getSharedPreferences("pref", Activity.MODE_APPEND);
        if(pref != null){
            Log.e("a","!");
            Score = pref.getInt("score", 100);
        }

        /**
         * 타이머를 통한 물고기 생성
         * 게임 요소 생성[물고기, 함정 등]쓰레드 생성
         */

        fish_Maker_1 = new TimerTask(){
            public void run() {
                try {


                    //게임 동작 중에만 추가한다.
                    if(mRun) {

                        add_Ground_Wave();

                        add_Fish_Touch_Default();           //기본 물고기 추가
                        add_Fish_Touch_Default();           //기본 물고기 추가
                        add_Fish_Touch_Default();           //기본 물고기 추가

                    add_Fish_Touch_Marlin();           //청새치



                    add_Fish_Touch_Squid();//오징어 추가
                    add_Fish_Touch_Ell();   //전기뱀장어 추가



                    add_Fish_Drag_Default();            //드래그 물고기 추가

                    add_Fish_JellyFish();               //해파리 추가



                    add_Ground_Snail();                 //달팽이 추가
                    add_Ground_Hermit();                //소라게 추가
                    add_Ground_Crab();                  //꽃게 추가?
                    add_Ground_Crocodile();             //악어 추가

//                    add_Boss_Fish_Touch_Default();            //물고기 보스
//                    add_Ground_Boss(window_Width/2 , -30);    //달팽이 보스


                    add_Ground_Urchin();                //성게추가

                        //조개 하마리 이상 금지
                        if(!ground_List.contains(ground_Drag_Clam)){
                            add_Ground_Clam();                  //조개 추가
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
        };




        mTimer = new Timer();
        mTimer.schedule(fish_Maker_1, 1000, 5000);

//        if(game_thread.getState() == Thread.State.TERMINATED) {
//            game_thread = new Game_Thread(/*holder*/);  //쓰레드가 홈버튼을 누름으로 인해 파괴 된다면 다시 생성
//            game_thread.start(); //게임
//            Log.e("surfaceChanged true","surfaceChanged true");
//        }else {
//            Log.e("surfaceChanged else","surfaceChanged else");
//            game_thread.start();
//            //gameMainThread.start();
//        }

        mRun = true;
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //Log.i("[뷰]", "파괴");


        ///값 저장
        SharedPreferences pref = _context.getSharedPreferences("pref", Activity.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("score", Score);
        Log.e("a",Score + "");
        editor.commit();



        function_Map_Monster_Recycle_Bitmap();

        try{
            Log.e("surfaceDestroyed","surfaceDestroyed");
//            타이머 파괴
            fish_Maker_1.cancel();
        }catch (Exception e){

        }



    }



    public void function_Map_Monster_Recycle_Bitmap(){

        explain_Window_Revoluition.recycle();
        backGroundImg.recycle();
        backGroundImg_black.recycle();
        for(int i=0; i<8; i++){
            effect_Background_One_1_img[i].recycle();
            effect_Background_Two_1_img[i].recycle();
            effect_Background_Seaweed_img[i].recycle();
            effect_Background_Seaweed_img[i].recycle();
            effect_background_Rock[i].recycle();
            effect_Background_Friend_Shark_img[i].recycle();
        }
        for(int i=0; i<3; i++){
            //소라게
            ground_Touch_Hermit_Hp1_img[i].recycle();
            ground_Touch_Hermit_Hp2_img[i].recycle();
            ground_Touch_Hermit_Hp3_img[i].recycle();
            ground_Touch_Hermit_Hp4_img[i].recycle();
            ground_Touch_Hermit_Hp5_img[i].recycle();
        }
        for(int i=0; i<8; i++){
            fish_Touch_Squid_img[i].recycle();
            fish_Touch_Ell_img[i].recycle();
            fish_Touch_Ell_Attack_img[i].recycle();
        }
        for(int i=0; i<7; i++){
            fish_Trap_Jelly_img[i].recycle();
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

        for(int i=0; i<4; i++){
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
            ground_snail_Middle_Hp1_img[i].recycle();
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
