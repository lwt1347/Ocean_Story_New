package com.example.user.ocean_story;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

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
    private int sound_Effect[] = new int[10];                        //효과음


    //두점 사이의 거리를 구하기위한 변수
    private int smallFishIndex      = 0;        //가장 가까운 물고기 인덱스
    private double pointXBig        = 0;
    private double pointXSmall      = 0;
    private double pointYBig        = 0;
    private double pointYSmall      = 0;
    private double smallFishTemp    = 5000;     //가장 가까운 물고기 찾기 위한 변수
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
    private boolean seaurchin_Ground_Hit_Flag = false;;//성게


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

    //pause 이미지  -> 컨트롤 화면에서
    //private Bitmap pause_img[] = new Bitmap[2];
    //private Bitmap pause_View_img = null;   //퍼지 눌렀을때 뜨는 창

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

    /**
     * 오징어 이미지
     */
    private Bitmap fish_Touch_Squid_img[] = new Bitmap[8];

    //드래그로 죽는 물고기 이미지
    private Bitmap fish_Drag_Default_img[] = new Bitmap[4];         //드래그로 죽는 물고기 이미지

    /**
     * 기본 물고기 설명 이미지
     */
    private Bitmap explain_Default_Fish_img[] = new Bitmap[5];
    /**
     * 드래그 물고기 설명 이미지
     */
    private Bitmap explain_Drag_Fish_img[] = new Bitmap[5];

    /**
     * 오징어 설명 이미지
     */
    private Bitmap explain_Squid_img[] = new Bitmap[5];

    /**
     * 성게 설명 이미지
     */
    private Bitmap explain_Urchin_img[] = new Bitmap[5];

    /**
     * 성게 설명 이미지 [공격 중]
     */
    private Bitmap explain_Urchin_Attack_img[] = new Bitmap[5];

    /**
     * 달팽이 설명 이미지
     */
    private Bitmap explain_Snail_img[] = new Bitmap[5];

    /**
     * 꽃게 설명 이미지
     */
    private Bitmap explain_Crab_img[] = new Bitmap[5];


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
     * 꽃게 이미지
     */
    private Bitmap ground_Drag_Crab_img[] = new Bitmap[4];          //꽃게 이미지




    //메인 캐릭터 이미지 [문어]
    private Bitmap main_Character_Octopus[] = new Bitmap[2];        //메인 캐릭터
    //메인 캐릭터 이미지 [오리진: 곰팡이]
    private Bitmap main_Character_Amoeba[] = new Bitmap[6];        //메인 캐릭터
    //메인 캐릭터 이미지 [오리진: 꽃게]
    private Bitmap main_Character_Orijin_Crab[] = new Bitmap[6];        //메인 캐릭터
    //메인 캐릭터 이미지 [오리진: 오징어]
    private Bitmap main_Character_Orijin_Squid[] = new Bitmap[6];        //메인 캐릭터
    //메인 캐릭터 이미지 [오리진: 물고기]
    private Bitmap main_Character_Orijin_Fish[] = new Bitmap[6];        //메인 캐릭터
    //메인 캐릭터 이미지 [오리진: 삼엽충]
    private Bitmap main_Character_Orijin_Trilobite[] = new Bitmap[6];        //메인 캐릭터

    //회전 물고기 비트맵 템프 변수
    private Bitmap temp_Fish = null;

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
    private Bitmap effect_Pop1_img[] = new Bitmap[5];
    private Bitmap effect_Pop2_img[] = new Bitmap[5];
    private Bitmap effect_Pop3_img[] = new Bitmap[5];
    private Bitmap effect_Pop4_img[] = new Bitmap[5];
    private Bitmap effect_Pop5_img[] = new Bitmap[5];
    private Bitmap effect_Pop6_img[] = new Bitmap[5];

    /**
     * 백그라운드 이펙트 동적인 화면 구성하기 위한 이미지
     */
    private Bitmap effect_Background_One_1_img[] = new Bitmap[8];
    private Bitmap effect_Background_Two_1_img[] = new Bitmap[8];
    //미역
    private Bitmap effect_Background_Seaweed_img[] = new Bitmap[8];
    //말미잘
    private Bitmap effect_background_Seaanemone_img[] = new Bitmap[8];
    //돌
    private Bitmap effect_background_Rock[] = new Bitmap[8];
    //상어
    private Bitmap effect_Background_Shark_img[] = new Bitmap[5];
    //먹물 [오징어 사냥 시 발생]
    private Bitmap effect_Background_Squid_Ink_img[] = new Bitmap[8];
    //상어 친구 부르기
    private Bitmap effect_Background_Friend_Shark_img[] = new Bitmap[8];


    //퍼지 이미지 변경
    //boolean pause_Push = false;

    //물기고 생성
    private ArrayList<Fish_Default_Body> fish_List = new ArrayList<Fish_Default_Body>();            //물고기를 넣을 어레이 리스트
    private ArrayList<Ground_Default_Body> ground_List = new ArrayList<Ground_Default_Body>();      //물고기를 넣을 어레이 리스트

    private ArrayList<Float> effect_Fish_Draw_X = new ArrayList<Float>();                           //지워지는 물고기 위치에 이펙트 넣어야한다. //circly_X_Draw - > effect_Fish_Draw_X
    private ArrayList<Float> effect_Fish_Draw_Y = new ArrayList<Float>();

    private ArrayList<Float> effect_Ground_Draw_X = new ArrayList<Float>();                         //지워지는 달팽이 위치에 이펙트 넣어햐 한다. //effect_X_Pop2 - > effect_Ground_Draw_X
    private ArrayList<Float> effect_Ground_Draw_Y = new ArrayList<Float>();

    private ArrayList<Integer> effect_TempRandom_Temp = new ArrayList<Integer>();                   //이펙트 효과를 위한 랜덤 리스트



    private Draw_Image draw = new Draw_Image();
    private Paint paint = new Paint();                 //점수
    private Paint paint_Temp = new Paint();            //점수 테두리

    private Paint paint_Best = new Paint();                 //점수
    private Paint paint_Temp_Best = new Paint();            //점수 테두리


    private Canvas canvas;
//    Thread background_Effect;                           //배경화면 쓰레드

    private Fish_Touch_Default fish_Touch_Default;      //기본 물고기 생성
    private Fish_Drag_Default fish_Drag_Default;        //드래그 물고기 생성
    private Fish_Trap_Jellyfish fish_Trap_Jellyfish;    //해파리 생성
    private Fish_Touch_Squid fish_Touch_Squid;          //오징어 생성
    private Background_Effect_Squid_Ink fish_Touch_Squid_Ink;   //오징어 잡았을때 먹물 발사.


    private Ground_Touch_Snail ground_Touch_Snail;      //달팽이 생성
    private Ground_Drag_Crab ground_Drag_Crab;          //꽃게 생성
    private Ground_Trap_Urchin ground_trap_urchin;      //성게 생성




    private Background_Effect_One background_Effect_One;    //배경화면 1번 움직임
    private Background_Effect_Two background_Effect_Two;    //배경화면 1번 움직임

    private Background_Effect_Shark background_Effect_Shark;    //배경화면 상어 움직임


    private ArrayList<Point> background_Effect_Location = new ArrayList<Point>(); //배경 이펙트 위치 선정


    private Bitmap effect_Temp;                         //어떤 이펙트를 넣을것인가 랜덤 변수
    private Random random = new Random();

    private Context _context;                           //화면 얻어오기
    private Display display;                            //디스플레이 넓이 구하기

    private String tempStr = "";

    private SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_ALARM, 0); //사운드 앞에 1은 하나만 가져다 놓겠다는 뜻. 나중에 추가 요망
    private MediaPlayer background_Sound;



    //Thread default_Fish_Effect;   //이펙트 쓰레드, 부하 때문에 삭제
    int rand_Effect;    //이펙트 쓰레드 대체

    Timer mTimer; //게임요소 추가 타이머

    //********************************************************************************************//


    /**
     * 기본 생성자
     */
    public GameMain(Context context, AttributeSet attrs){
        super(context,attrs);   //커스텀 뷰 사용 -> attrs.xml 에 등록 해야함

        _context = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        game_thread = new Game_Thread(/*mSurfaceHolder*/);      //그림이 그려지고, 게임 동작하는곳

        //게임 요소 추가 할 타이머 [물고기, 함정 등] 1초후에 실행해서 5초마다 반복
        mTimer = new Timer();
        mTimer.schedule(fish_Maker_1, 1000, 5000);


        /**
         * 게임 내에 필요한 버튼 객체 생성
         */
        button_Create_method();

//        Intent intent = new Intent(_context, explain_Panel.class);
//        _context.startActivity(intent); //-> 기본 물고기 설명
////        if(){ //물고기 설명창 뜨면 게임 일시정지
//            m_Run_False();
////        }



//        //메인캐릭터 생성
//        main_Character = new Main_Character(window_Width, window_Height);



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


        /**
         *  배경음악 사운드풀로 안되서
         */
        background_Sound = MediaPlayer.create(_context, R.raw.background_music_1);
        background_Sound.setLooping(true);
        background_Sound.setVolume(1f,1f);
        background_Sound.start();

    }





    /**
     * 게임 동작 함수
     */

    //기본 물고기
    private boolean first_Default_Fish = true;
    //드래그 물고기
    private boolean first_Drag_Fish = true;
    //오징어
    private boolean first_Squid = true;
    //성게
    private boolean first_Urchin = true;
    //달팽이
    private boolean first_Snail = true;
    //꽃게
    private boolean first_Crab = true;


    /**
     * 배경 화면 움직임
     */
    public void background_Effect_Move(){
        background_Effect_One.Background_Effect_Move_Pattern();
        background_Effect_Two.Background_Effect_Move_Pattern();
        background_Effect_Shark.Background_Effect_Move_Pattern();
    }



    /**
     * 버튼 객체 생성
     */
    public void button_Create_method(){

        confirm_Button_1 = new GraphicButton(new Rect(window_Width / 2 - convertPixelsToDp(50, _context),
                window_Height / 2 + convertPixelsToDp(145, _context),
                window_Width / 2 - convertPixelsToDp(50, _context) + convertPixelsToDp(105, _context),
                window_Height / 2 + convertPixelsToDp(145, _context) + convertPixelsToDp(40, _context)));

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
     * 다시시작 및 시작 처음에 초기화
     */
    public void re_Start(){

        //배경 화면 동적 움직임 생성
        //따로 쓰레드를 돌릴 필요 없이 게임 안에서 동작하게끔 한다.
        background_Effect_One = new Background_Effect_One(window_Width, window_Height);
//        background_Effect = new Thread(background_Effect_One);
//        background_Effect.start();                   //쓰레드 작동 방법

        background_Effect_Two = new Background_Effect_Two(window_Width, window_Height);
//        background_Effect = new Thread(background_Effect_Two);
//        background_Effect.start();

        //배경 화면 상어 움직임
        background_Effect_Shark = new Background_Effect_Shark(window_Width, window_Height);
//        background_Effect = new Thread(background_Effect_Shark);




        //스테이지  = 1, 속도 = 1
         fish_List.clear();
         ground_List.clear();
         Score = 0;

        first_Default_Fish = true;
        first_Drag_Fish = true;
        first_Urchin = true;
        first_Snail = true;
        first_Squid = true;

        main_Character = new Main_Character_Amoeba(window_Width, window_Height);

        //재시작 하면 초기화
        background_Effect_Location = new ArrayList<Point>(); //배경 이펙트 위치 선정;
        //배경 물방울 이펙트 개수
        for(int i=0; i<6; i++){
            Point point = new Point(
                    convertPixelsToDp(30, _context) + random.nextInt(window_Width - convertPixelsToDp(60, _context)),
                    convertPixelsToDp(30, _context) + random.nextInt(window_Height - convertPixelsToDp(60, _context)));
            background_Effect_Location.add(point);
        }



    }

    //게임 상태 컨트롤
    public void m_Run_False(){
        mRun = false;
    }
    public void m_Run_True(){
        mRun = true;
    }


    //********************************************************************************************//

    /**
     *  내부 클래스
     *  게임 요소 추가 할 스레드 [물고기, 바닥 생명체 등.]
     */


    /**
     * 타이머를 통한 물고기 생성
     * 게임 요소 생성[물고기, 함정 등]쓰레드 생성
     */

    TimerTask fish_Maker_1 = new TimerTask(){
        public void run() {
            try {


                //게임 동작 중에만 추가한다.
                if(mRun) {



                    add_Fish_Touch_Default();           //기본 물고기 추가
                    add_Fish_Touch_Default();           //기본 물고기 추가
                    add_Fish_Touch_Default();           //기본 물고기 추가

                    add_Fish_Touch_Squid();//오징어 추가


                    add_Fish_Drag_Default();            //드래그 물고기 추가

                    add_Fish_JellyFish();               //해파리 추가


                    add_Ground_Snail();                 //달팽이 추가
                    add_Ground_Crab();                  //꽃게 추가

                    add_Ground_Urchin();                //성게추가


                }
                //Thread.sleep(1000);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    };




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




            //pause_View_img = BitmapFactory.decodeResource(getResources(), R.drawable.pause_view);    //나인패치 적용방법
            //ninePatch = new NinePatch(pause_View_img, pause_View_img.getNinePatchChunk(), "");       //나인패치 적용방법




            //게임 요소 추가 할 쓰레드 [물고기, 함정 등]
            Init_Background_Image(_context); //배경
            DisplayMetrics dm = _context.getApplicationContext().getResources().getDisplayMetrics();
            backGroundImg = Bitmap.createScaledBitmap(backGroundImg, dm.widthPixels, dm.heightPixels, false);
            backGroundImg_black = Bitmap.createScaledBitmap(backGroundImg_black, dm.widthPixels, dm.heightPixels, false); //배경 화면 어둡게

            /**
             * 등장 창 초기화
             */
            Init_Explain_Window_Image(_context);

            //퍼즈 이미지
            //pause_img[0] = Init_Pause_Image(_context, 0);
            //pause_img[1] = Init_Pause_Image(_context, 1);

            for(int i = 0; i < 4; i++) {
                fish_Touch_Default_Hp1_img[i] = Init_Fish_Touch_Default_Hp1_Image(_context, i); //캐릭터 이미지 추가 hp = 1
                fish_Touch_Default_Hp2_img[i] = Init_Fish_Touch_Default_Hp2_Image(_context, i); //캐릭터 이미지 추가
                fish_Touch_Default_Hp3_img[i] = Init_Fish_Touch_Default_Hp3_Image(_context, i); //캐릭터 이미지 추가
                fish_Touch_Default_Hp4_img[i] = Init_Fish_Touch_Default_Hp4_Image(_context, i); //캐릭터 이미지 추가
                fish_Touch_Default_Hp5_img[i] = Init_Fish_Touch_Default_Hp5_Image(_context, i); //캐릭터 이미지 추가 hp = 5

                ground_Touch_Snail_Hp1_img[i] = Init_Ground_Touch_Snail_Hp1_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp2_img[i] = Init_Ground_Touch_Snail_Hp2_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp3_img[i] = Init_Ground_Touch_Snail_Hp3_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp4_img[i] = Init_Ground_Touch_Snail_Hp4_Image(_context, i);  //달팽이 이미지
                ground_Touch_Snail_Hp5_img[i] = Init_Ground_Touch_Snail_Hp5_Image(_context, i);  //달팽이 이미지

                fish_Drag_Default_img[i] = Init_Fish_Drag_Default_Image(_context, i);            //드래그 물고기

                ground_Drag_Crab_img[i] = Init_Ground_Drag_Crab_Image(_context, i);              //꽃게 이미지

            }

            //오징어 이미지
            for(int i=0; i<8; i++) {
                fish_Touch_Squid_img[i] = Init_Fish_Touch_Squid_Image(_context, i);
            }

            //해파리 이미지
            for(int i=0; i<7; i++){
                fish_Trap_Jelly_img[i] = Init_Fish_Trap_Jellyfish(_context, i);
            }


            /**
             * 물고기 설명창
             */
            for(int i=0; i<5; i++){
                explain_Default_Fish_img[i] = Init_Explain_Fish_Default(_context, i);

                //드래그 물고기 설명
                explain_Drag_Fish_img[i] = Init_Explain_Fish_Drag(_context, i);

                //오징어 설명
                explain_Squid_img[i] = Init_Explain_Squid(_context, i);

                //성게 설명
                explain_Urchin_img[i] = Init_Explain_Urchin(_context, i);
                //성게 공격 모드 설명
                explain_Urchin_Attack_img[i] = Init_Explain_Urchin_Attack(_context, i);

                //달팽이 설명
                explain_Snail_img[i] = Init_Explain_Snail(_context, i);

                //꽃게 설명
                explain_Crab_img[i] = Init_Explain_Crab(_context, i);
            }



//            for(int i = 0; i < 5; i++){
//                main_Character_img[i] = Init_Main_Character_Image(_context, i); //메인 캐릭터
//            }
            //메인 캐릭터 업로딩
            Init_Main_Character_Image(_context);



            for(int i=0; i<5; i++){

                effect_Orange_img[i] = Init_Effect_Orange_Image(_context, i); //이펙트
                effect_Blue_img[i]   = Init_Effect_Blue_Image(_context, i);
                effect_Yellow_img[i] = Init_Effect_Yellow_Image(_context, i);
                effect_Green_img[i]  = Init_Effect_Green_Image(_context, i);
                effect_Black_img[i] = Init_Effect_Black_Image(_context, i);

                effect_Pop1_img[i] = Init_Effect_Pop1_Image(_context, i);
                effect_Pop2_img[i] = Init_Effect_Pop2_Image(_context, i);
                effect_Pop3_img[i] = Init_Effect_Pop3_Image(_context, i);
                effect_Pop4_img[i] = Init_Effect_Pop4_Image(_context, i);
                effect_Pop5_img[i] = Init_Effect_Pop5_Image(_context, i);
                effect_Pop6_img[i] = Init_Effect_Pop6_Image(_context, i);

                ground_Trap_Urchin_img[i] = Init_Ground_Trap_Urchin_Image(_context, i);              //성게 이미지
                ground_Trap_Urchin_Rest_Mode_img[i] = Init_Ground_Trap_Urchin_Rest_Mode_Image(_context, i); //성게 휴식기 이미지

            }
            for(int i=0; i<8; i++){
                effect_Background_One_1_img[i] = Init_Background_Effect_Background_One_1_Image(_context, i);    //배경 이펙트
                effect_Background_Two_1_img[i] = Init_Background_Effect_Background_Two_1_Image(_context, i);    //배경 이펙트
                effect_Background_Seaweed_img[i] = Init_Background_Effect_Background_Seaweed_Image(_context, i);    //배경 이펙트
                effect_background_Seaanemone_img[i] = Init_Background_Effect_Background_Seaanemone_Image(_context, i);    //배경 이펙트
                effect_background_Rock[i] = Init_Background_Effect_Background_Rock_Image(_context, i);    //배경 이펙트
                effect_Background_Squid_Ink_img[i] = Init_Background_Effect_Background_Squid_Ink_Image(_context, i);    //오징어 이펙트
                effect_Background_Friend_Shark_img[i] = Init_Background_Effect_Background_Friend_Shark_Image(_context, i);    //상어 친구 부르기 이펙트
            }
            for(int i=0; i<5; i++){
                effect_Background_Shark_img[i] = Init_Background_Effect_Background_Shark_Image(_context, i);    //배경 이펙트
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

        /**
         * 등장 창 초기화
         */
        public void Init_Explain_Window_Image(Context context){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.explain_window_origincrab);
            explain_Window_Origin_Crab = image.getBitmap();
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.explain_window_revolrution);
            explain_Window_Revoluition = image.getBitmap();

            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.explain_window_originfish);
            explain_Window_Origin_Fish = image.getBitmap();

            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.explain_window_originsquid);
            explain_Window_Origin_Squid = image.getBitmap();

            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.explain_window_origintrilobite);
            explain_Window_Origin_Trilbite = image.getBitmap();

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

        //오징어 이미지
        public Bitmap Init_Fish_Touch_Squid_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.fish_squid_1 + num); //인트형이라 + 1하면 그림 변경됨
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
        public Bitmap Init_Explain_Fish_Default(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.explain_default_fish_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        /**
         * 오징어 설명
         */
        public Bitmap Init_Explain_Squid(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.explain_squid_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }


        /**
         * 드래그 물고기 설명
         */
        public Bitmap Init_Explain_Fish_Drag(Context context, int num) {
            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.explain_drag_fish_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        /**
         *  성게 설명
         */
        public Bitmap Init_Explain_Urchin(Context context, int num) {
            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.explain_urchin_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        /**
         * 성게 공격 모드 설명
         */
        public Bitmap Init_Explain_Urchin_Attack(Context context, int num) {
            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.explain_urchin_attack_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        /**
         * 달팽이 설명
         */
        public Bitmap Init_Explain_Snail(Context context, int num) {
            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.explain_snail_1 + num); //인트형이라 + 1하면 그림 변경됨
            return image.getBitmap();
        }

        /**
         * 꽃게 설명
         */
        public Bitmap Init_Explain_Crab(Context context, int num) {
            image = (BitmapDrawable) context.getResources().getDrawable(R.drawable.explain_crab_1 + num); //인트형이라 + 1하면 그림 변경됨
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
        //꽃게 이미지
        public Bitmap Init_Ground_Drag_Crab_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ground_crab_1 + num);     //인트형이라 + 1하면 그림 변경됨
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





        //퍼즈 이미지
        /*public Bitmap Init_Pause_Image(Context context, int num){
                image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.pause_1 + num); //인트형이라 + 1하면 그림 변경됨
                return image.getBitmap();
        }*/




        //메인 캐릭터 이미지
        public void Init_Main_Character_Image(Context context){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.character_octopus_10); //인트형이라 + 1하면 그림 변경됨

            main_Character_Octopus[0] = image.getBitmap();


            for(int i=0; i<6; i++){
                //메인 캐릭터 이미지 [오리진: 곰팡이]
                image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.character_amoeba_1 + i);
                main_Character_Amoeba[i] = image.getBitmap();

                //메인 캐릭터 이미지 [오리진: 꽃게]
                image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.character_origincrab_1 + i);
                main_Character_Orijin_Crab[i] = image.getBitmap();

                //메인 캐릭터 이미지 [오리진: 오징어]
                image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.character_originsquid_1 + i);
                main_Character_Orijin_Squid[i] = image.getBitmap();

                //메인 캐릭터 이미지 [오리진: 물고기]
                image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.character_originfish_1 + i);
                main_Character_Orijin_Fish[i] = image.getBitmap();

                //메인 캐릭터 이미지 [오리진: 삼엽충]
                image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.character_trilobite_1 + i);
                main_Character_Orijin_Trilobite[i] = image.getBitmap();
            }





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
        public Bitmap Init_Effect_Pop1_Image(Context context, int num){
            image = (BitmapDrawable)context.getResources().getDrawable(R.drawable.effect_pop1_1 + num);
            return image.getBitmap();
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
    public void doDraw(Canvas canvas) {


        /**
         *  배경이미지
         */
        draw.draw_Bmp(canvas, backGroundImg, 0, 0);



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
                background_Effect_Location.get(1).x,
                background_Effect_Location.get(1).y);
        //~~~ 물방울 이펙트



        //미역 이펙트
        draw.draw_Bmp(canvas, effect_Background_Seaweed_img[background_Effect_Two.get_Draw_Background_Effect_Status()],
                background_Effect_Location.get(2).x,
                background_Effect_Location.get(2).y);

        draw.draw_Bmp(canvas, effect_Background_Seaweed_img[effect_Background_Two_1_img_Control_Temp],
                background_Effect_Location.get(3).x,
                background_Effect_Location.get(3).y);

        //~~~ 미역이펙트

        //말미잘 이펙트
        draw.draw_Bmp(canvas, effect_background_Seaanemone_img[background_Effect_Two.get_Draw_Background_Effect_Status()],
                background_Effect_Location.get(4).x,
                background_Effect_Location.get(4).y);

        draw.draw_Bmp(canvas, effect_background_Seaanemone_img[effect_Background_Two_1_img_Control_Temp],
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
        for(int i=0; i<ground_List.size(); i++) {

            //달팽이 움직임
            if (ground_List.get(i) instanceof Ground_Touch_Snail) {

                //첫 번째 달팽이 일때
                if(ground_List.get(i).get_First_Test_Object()){
                    draw.draw_Bmp(canvas, explain_Snail_img[ground_List.get(i).get_Draw_Ground_Status()],
                            ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(70, _context),
                            ground_List.get(i).get_Ground_Point_Y()- convertPixelsToDp(13, _context));

                    //달팽이 hp
                    paint_Temp.setTextSize(convertPixelsToDp(15, _context));
                    paint_Temp.setColor(Color.RED);
                    paint_Temp.setStrokeWidth(4);

                    canvas.drawText(""+ ground_List.get(i).get_Ground_Hp(),
                            ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(35, _context),
                            ground_List.get(i).get_Ground_Point_Y() + convertPixelsToDp(37, _context), paint_Temp);

                }

                if (ground_List.get(i).get_Ground_Hp() == 5) {
                    draw.draw_Bmp(canvas, ground_Touch_Snail_Hp5_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                } else if (ground_List.get(i).get_Ground_Hp() == 4) {
                    draw.draw_Bmp(canvas, ground_Touch_Snail_Hp4_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                } else if (ground_List.get(i).get_Ground_Hp() == 3) {
                    draw.draw_Bmp(canvas, ground_Touch_Snail_Hp3_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                } else if (ground_List.get(i).get_Ground_Hp() == 2) {
                    draw.draw_Bmp(canvas, ground_Touch_Snail_Hp2_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                } else if (ground_List.get(i).get_Ground_Hp() == 1) {
                    draw.draw_Bmp(canvas, ground_Touch_Snail_Hp1_img[((Ground_Touch_Snail) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                }
            }
            //꽃게 움직임
            if(ground_List.get(i) instanceof Ground_Drag_Crab){

                    //첫 번째 꽃게 일때
                if(ground_List.get(i).get_First_Test_Object()){
                    draw.draw_Bmp(canvas, explain_Crab_img[ground_List.get(i).get_Draw_Ground_Status()],
                            ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(78, _context),
                            ground_List.get(i).get_Ground_Point_Y()- convertPixelsToDp(30, _context));

                    //꽃게 hp
                    paint_Temp.setTextSize(convertPixelsToDp(15, _context));
                    paint_Temp.setColor(Color.RED);
                    paint_Temp.setStrokeWidth(4);

                    canvas.drawText(""+ ground_List.get(i).get_Ground_Hp(),
                            ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(33, _context),
                            ground_List.get(i).get_Ground_Point_Y() + convertPixelsToDp(13, _context), paint_Temp);



                }


                    draw.draw_Bmp(canvas, ground_Drag_Crab_img[((Ground_Drag_Crab) ground_List.get(i)).get_Draw_Ground_Status()], ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
            }

            //성게 움직임
            if(ground_List.get(i) instanceof Ground_Trap_Urchin){


                //첫 번째 성게 일때
                if(ground_List.get(i).get_First_Test_Object()){

                    //성게가 150 시간 넘어가면 부활한다.
                    if(((Ground_Trap_Urchin) ground_List.get(i)).get_Live_Time() <= 150 ){

                    draw.draw_Bmp(canvas, explain_Urchin_img[ground_List.get(i).get_Draw_Ground_Status()],
                            ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(78, _context),
                            ground_List.get(i).get_Ground_Point_Y()- convertPixelsToDp(28, _context));

                    // 남은 시간 그리기
                    paint_Temp.setTextSize(convertPixelsToDp(15, _context));
                    paint_Temp.setColor(Color.RED);
                    paint_Temp.setStrokeWidth(4);


                        canvas.drawText(""+ (150 - ((Ground_Trap_Urchin) ground_List.get(i)).get_Live_Time()),
                                ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(40, _context),
                                ground_List.get(i).get_Ground_Point_Y() + convertPixelsToDp(30, _context), paint_Temp);
                    }else{ //성게가 공격 모드일때.
                        draw.draw_Bmp(canvas, explain_Urchin_Attack_img[ground_List.get(i).get_Draw_Ground_Status()],
                                ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(78, _context),
                                ground_List.get(i).get_Ground_Point_Y()- convertPixelsToDp(28, _context));

                        // 남은 시간 그리기
                        paint_Temp.setTextSize(convertPixelsToDp(15, _context));
                        paint_Temp.setColor(Color.RED);
                        paint_Temp.setStrokeWidth(4);


                        canvas.drawText(""+ (300 - ((Ground_Trap_Urchin) ground_List.get(i)).get_Live_Time()),
                                ground_List.get(i).get_Ground_Point_X() - convertPixelsToDp(40, _context),
                                ground_List.get(i).get_Ground_Point_Y() + convertPixelsToDp(30, _context), paint_Temp);
                    }



                }


                //성게가 공격 모드인가 아닌가.
                if(((Ground_Trap_Urchin) ground_List.get(i)).get_Urchin_Attack_Mode()){
                    //공격모드 일때
                    draw.draw_Bmp(canvas,
                            draw.rotate_Image(ground_Trap_Urchin_img[ground_List.get(i).get_Draw_Ground_Status()], ((Ground_Trap_Urchin) ground_List.get(i)).get_Urchin_Angle()),
                            ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                }else {
                    //성게가 공격 모드가 아닐때.
                    draw.draw_Bmp(canvas,
                            draw.rotate_Image(ground_Trap_Urchin_Rest_Mode_img[ground_List.get(i).get_Draw_Ground_Status()], ((Ground_Trap_Urchin) ground_List.get(i)).get_Urchin_Angle()),
                            ground_List.get(i).get_Ground_Point_X(), ground_List.get(i).get_Ground_Point_Y());
                }
            }

        }



        /**
         * 물고기 그리기
         */
        for(int i=0; i<fish_List.size(); i++){
            if(fish_List.get(i) instanceof Fish_Touch_Default) {

                /**
                 * 물고기 설명 그림
                 */
                if((fish_List.get(i)).get_First_Test_Object()){ //첫 번째 물고기일때

                    draw.draw_Bmp(canvas, explain_Default_Fish_img[fish_List.get(i).get_Draw_Fish_Status()],
                            fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(65, _context),
                            fish_List.get(i).get_Fish_Point_Y() - convertPixelsToDp(20, _context));

                    // 남은 hp 그리기
                    paint_Temp.setTextSize(convertPixelsToDp(15, _context));
                    paint_Temp.setColor(Color.RED);
                    paint_Temp.setStrokeWidth(4);
                    canvas.drawText(""+ fish_List.get(i).get_Fish_Hp(),
                            fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(25, _context),
                            fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(47, _context), paint_Temp);

                }


                /**
                 *  이미지 회전
                 */
                if(fish_List.get(i).get_Fish_Hp() == 1) {
                    temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp1_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                }else if(fish_List.get(i).get_Fish_Hp() == 2){
                    temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp2_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                }else if(fish_List.get(i).get_Fish_Hp() == 3){
                    temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp3_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                }else if(fish_List.get(i).get_Fish_Hp() == 4){
                    temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp4_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                }else if(fish_List.get(i).get_Fish_Hp() == 5){
                    temp_Fish = draw.rotate_Image(fish_Touch_Default_Hp5_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                }
                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                /**
                 * 오징어 그리기
                 */
            }else if(fish_List.get(i) instanceof  Fish_Touch_Squid){

                if((fish_List.get(i)).get_First_Test_Object()) { //첫 번째 오징어 일때

                    draw.draw_Bmp(canvas, explain_Squid_img[fish_List.get(i).get_Draw_Fish_Status()],
                            fish_List.get(i).get_Fish_Point_X() + convertPixelsToDp(0, _context),
                            fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(25, _context));

                }


                temp_Fish = draw.rotate_Image(fish_Touch_Squid_img[fish_List.get(i).get_Draw_Fish_Status()], 0);
                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());

                /**
                 * 드래그로 죽이는 물고기
                 */
            }else if(fish_List.get(i) instanceof  Fish_Drag_Default){


                /**
                 * 드래그 물고기 설명 그림
                 */
                if((fish_List.get(i)).get_First_Test_Object()){ //첫 번째 물고기일때

                    draw.draw_Bmp(canvas, explain_Drag_Fish_img[fish_List.get(i).get_Draw_Fish_Status()],
                            fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(85, _context),
                            fish_List.get(i).get_Fish_Point_Y() - convertPixelsToDp(20, _context));

                    // 남은 hp 그리기
                    paint_Temp.setTextSize(convertPixelsToDp(15, _context));
                    paint_Temp.setColor(Color.RED);
                    paint_Temp.setStrokeWidth(4);
                    canvas.drawText(""+ fish_List.get(i).get_Fish_Hp(), fish_List.get(i).get_Fish_Point_X() - convertPixelsToDp(43, _context), fish_List.get(i).get_Fish_Point_Y() + convertPixelsToDp(57, _context), paint_Temp);

                }




                temp_Fish = draw.rotate_Image(fish_Drag_Default_img[fish_List.get(i).get_Draw_Fish_Status()], -fish_List.get(i).get_Fish_Angle());
                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());


                /**
                 *  해파리 그리기
                 */
            }else if(fish_List.get(i) instanceof Fish_Trap_Jellyfish){
                temp_Fish = draw.rotate_Image(fish_Trap_Jelly_img[fish_List.get(i).get_Draw_Fish_Status()], 90-fish_List.get(i).get_Fish_Angle());
                draw.draw_Bmp(canvas, temp_Fish, fish_List.get(i).get_Fish_Point_X(), fish_List.get(i).get_Fish_Point_Y());
            }

        }





        /**
         * 물고기 터치 이팩트
         */
        if(default_Fish_Hit_Flag){
            draw.draw_Bmp(canvas, effect_Temp, fish_List.get(smallFishIndex).get_Fish_Point_X() - 15, fish_List.get(smallFishIndex).get_Fish_Point_Y());
            default_Fish_Hit_Flag = false;
        }else if(drag_Fish_Hit_Flag){
            /**
             * 드래그 물고기 터치 이벤트
             */
            draw.draw_Bmp(canvas, effect_Pop1_img[random.nextInt(5)],
                    fish_List.get(smallFishIndex).get_Fish_Point_X() + random.nextInt(fish_Drag_Default_img[0].getWidth() - 25),
                    fish_List.get(smallFishIndex).get_Fish_Point_Y() + random.nextInt(fish_Drag_Default_img[0].getHeight()) - 35);
            drag_Fish_Hit_Flag = false;
        }

        /**
         * 그라운드 터리 이팩트
         */
        if(snail_Ground_Hit_Flag){
            /**
             * 달팽이 터치 이벤트
             */
            draw.draw_Bmp(canvas, effect_Temp,
                    ground_List.get(ground_Remove_Temp).get_Ground_Point_X() - 35,
                    ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() - 35);
            snail_Ground_Hit_Flag = false;

        }else if(crap_Ground_Hit_Flag){
            /**
             * 꽃게 터치 이벤트.
             */
            draw.draw_Bmp(canvas, effect_Temp,
                    ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(ground_Drag_Crab_img[0].getWidth())-35 ,
                    ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(ground_Drag_Crab_img[0].getHeight())-35);
            crap_Ground_Hit_Flag = false;
        }else if(seaurchin_Ground_Hit_Flag){
            /**
             * 성게 터치 이벤트
             */
            draw.draw_Bmp(canvas, effect_Temp,
                    ground_List.get(ground_Remove_Temp).get_Ground_Point_X() + random.nextInt(ground_Touch_Snail_Hp1_img[0].getWidth()) - 35,
                    ground_List.get(ground_Remove_Temp).get_Ground_Point_Y() + random.nextInt(ground_Touch_Snail_Hp1_img[0].getHeight()) - 35);

            seaurchin_Ground_Hit_Flag = false;
            ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();  //성게 삭제,
            delete_Ground_Select(ground_Remove_Temp);   //피가 감소된 객체 0일때 삭제

        }








    /*
        //퍼지 그리기
        if(!pause_Push) { //안눌렸을때
            draw.draw_Bmp(canvas, pause_img[0], window_Width - 100, 10);
        }else { //눌렸을때
            draw.draw_Bmp(canvas, pause_img[1], window_Width - 100, 10);
            //퍼지 뷰
            //ninePatch.draw(canvas, new Rect(100,200, window_Width - 100, window_Height/2 + 100), paint);           //나인패치 적용방법
            //draw.draw_Bmp(canvas, pause_View_img, window_Width/2 - 100, window_Height/2 - 100); //나인패치 적용방법
            Intent intent = new Intent(_context, Menu_Sliding_Panel.class);
            //intent.putExtra("a", mRun);
            _context.startActivity(intent); //-> 일시정지 창을 팝업한다. Menu_Sliding_Panel 호출
            mRun = false; //화면 정지 일시정지 화면 출력
        }
    */


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

        }



        draw.draw_Bmp(canvas, nine_Patch_Hp, 20, 1); //나인패치 적용방법






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


    /*
    등장 설명창, 진화 할 때 컨트롤
     */

    if(!mRun) {

        draw.draw_Bmp(canvas, backGroundImg_black, 0, 0);


        if(revolution_Flag) {


            draw.draw_Bmp(canvas, explain_Window_Revoluition,
                    window_Width / 2 - convertPixelsToDp(120, _context),
                    window_Height / 2 - convertPixelsToDp(220, _context));

            button_Create_method();

            Bitmap upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);
            Bitmap downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.explain_window_revolrution_card_1);

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

            Bitmap upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_1);
            Bitmap downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_2);

            draw.draw_Bmp(canvas, backGroundImg_black, 0, 0);

            if(main_Character instanceof Main_Character_Origin_Crab) {
                //꽃게로 진화하면
                draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
//            revolution_Flag_Confirm = false;



            }else if(main_Character instanceof Main_Character_Origin_Fish){
                draw.draw_Bmp(canvas, explain_Window_Origin_Fish,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));

            }else if(main_Character instanceof Main_Character_Origin_Squid){


                draw.draw_Bmp(canvas, explain_Window_Origin_Squid,
                        window_Width / 2 - convertPixelsToDp(120, _context),
                        window_Height / 2 - convertPixelsToDp(220, _context));
            }

            soundPool.play(sound_Effect[8], 1F, 1F, 1, 1, 1.0F);
            confirm_Button_1.setImages(upimage, downimage);
            confirm_Button_1.draw(canvas);

            m_Run_False();



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
                soundPool.play(sound_Effect[4], 0.2F, 0.2F, 1, 1, 1.0F);
            }else if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 4){
//                soundPool.play(sound_Effect[5], 0.5F, 0.5F, 1, 1, 1.0F);
            }else if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 6){
//                soundPool.play(sound_Effect[6], 0.5F, 0.5F, 1, 1, 1.0F);
            }else if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 7){
                soundPool.play(sound_Effect[7], 0.5F, 0.5F, 1, 1, 1.0F);
            }


            if(background_Effect_Friend_Shark_Call.get_Draw_Background_Effect_Status() == 7){

                //모든 물고기 및 그라운드 생명체 hp - 100 해야된다.

                for(int i=0; i<fish_List.size(); i++){
                    fish_List.get(i).set_Hp_Minus(100);
                }
                for(int i=fish_List.size()-1; i>=0; i--){
                    delete_Fish_Select(i);
                }


                for(int i=0; i<ground_List.size(); i++){
                    ground_List.get(i).set_Ground_Hp_Minus(100);
                }

                for(int i=ground_List.size()-1; i>=0; i--){
                   delete_Ground_Select(i);
                }




                shark_Friend_Call_Flag = false;     //상어 이펙트 그린후 사라져야한다.
            }
        }

    }


        /**
     * 게임이 동작하는 구간
     */
    public void run() {
        while(true)
        while (mRun) {
            //퍼즈 걸도록 mRun 컨트롤

                //canvas = null;
                try {
                    canvas = mSurfaceHolder.lockCanvas(null);
//                    synchronized (mSurfaceHolder) {


                        //물고기 및 그라운드 생명체 체력 0 인것 삭제
                        delete_Fish();
                        delete_Ground();

                        sleep(20);

                        //물고기 움직임을 하나의 쓰레드로 작동한다.
                        fish_Move();
                        //그라운드 움직임을 하나의 쓰레드로 작동합니다.
                        ground_Move();

                    /**
                     * 배경 효과 움직임
                     */
                    background_Effect_Move();


                    /**
                     * 그림 그리기 구간
                     */
                    doDraw(canvas);






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
     * 물고기 추가하기
     */
    public void add_Fish_Touch_Default(){

        fish_Touch_Default = new Fish_Touch_Default(window_Width, 5);       //기본 fish_touch_default 물고기 생성

        //처음 시작할 때 물고기 설명 추가
        if(first_Default_Fish) {
            fish_Touch_Default.set_First_Test_Object((window_Width/2) - convertPixelsToDp(30, _context));
            first_Default_Fish = false;
        }


        fish_List.add(fish_Touch_Default);                                  //물고기 리스트에 추가


        //fish_Shake.startFishMove();
    }

    /**
     * 오징어 추가
     */
    public void add_Fish_Touch_Squid(){
        fish_Touch_Squid = new Fish_Touch_Squid(window_Width, 1);       //오징어 생성

        if(first_Squid){
            fish_Touch_Squid.set_First_Test_Object((window_Width/2) - convertPixelsToDp(30, _context));
            first_Squid = false;
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
     *  드래그 물고기 추가하기
     */
    public void add_Fish_Drag_Default(){
        fish_Drag_Default = new Fish_Drag_Default(window_Width, 30);       //드래그로 잡는 fish_Touch_Default 물고기생성

        //처음 나올때 설명 붙여서 나옴
        if(first_Drag_Fish){
            fish_Drag_Default.set_First_Test_Object((window_Width/2)- convertPixelsToDp(30, _context));
            first_Drag_Fish = false;
        }


        fish_List.add(fish_Drag_Default);
    }

    /**
     * 해파리 추가
     */
    public void add_Fish_JellyFish(){

        fish_Trap_Jellyfish = new Fish_Trap_Jellyfish(window_Width, window_Height, 1);                //화면 좌우축 둘중 한군대만 생성 hp = 1
        fish_List.add(fish_Trap_Jellyfish);

    }


    /**
     * 그라운드 생성구간 (달팽이)
     */
    public void add_Ground_Snail(){

        ground_Touch_Snail = new Ground_Touch_Snail(window_Width,
                ground_Touch_Snail_Hp1_img[0].getWidth(),
                ground_Touch_Snail_Hp1_img[0].getHeight(), 5);

        //첫 번째 달팽이 생성
        if(first_Snail){
            ground_Touch_Snail.set_First_Test_Object((window_Width/2)- convertPixelsToDp(30, _context));
            first_Snail = false;
        }


        ground_List.add(ground_Touch_Snail); //달팽이 생성

    }

    /**
     * 꽃게 추가
     */
    public void add_Ground_Crab(){
        ground_Drag_Crab = new Ground_Drag_Crab(window_Width,
                ground_Drag_Crab_img[0].getWidth(),
                ground_Drag_Crab_img[0].getHeight(), 30);

        //첫 번째 꽃게 생성
        if(first_Crab){
            ground_Drag_Crab.set_First_Test_Object((window_Width/2)- convertPixelsToDp(30, _context));
            first_Crab = false;
        }

        ground_List.add(ground_Drag_Crab); //꽃게
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
                1);

        if(first_Urchin){
            // fish_Drag_Default.set_First_Test_Object((window_Width/2)- convertPixelsToDp(30, _context));
            ground_trap_urchin.set_First_Test_Object((window_Width/2)- convertPixelsToDp(30, _context), (window_Height/2)- convertPixelsToDp(30, _context));
            first_Urchin = false;
        }

        ground_List.add(ground_trap_urchin);//성게
    }



    /**
     * 물고기 움직임 -> 물고기 각 쓰레드를 주게 되면 부하가 심하대 따라서 한 함수로 모든 물고기를 제어한다.
     */

    public void fish_Move(){
        for(int i=0; i<fish_List.size(); i++){
            fish_List.get(i).fish_Object_Move();
        }
    }


    /**
     * 그라운드 움직임
     */

    public void ground_Move(){




        for(int i=0; i<ground_List.size(); i++){



            if(ground_List.get(i) instanceof Ground_Touch_Snail) {      //달팽이 무빙 함수 이용
                ((Ground_Touch_Snail) ground_List.get(i)).ground_Object_Move();
            }else if(ground_List.get(i) instanceof Ground_Drag_Crab){   //꽃게 무빙 함수
                ((Ground_Drag_Crab) ground_List.get(i)).ground_Object_Move();
            }else if(ground_List.get(i) instanceof Ground_Trap_Urchin){
                ((Ground_Trap_Urchin) ground_List.get(i)).ground_Object_Move();
            }
        }




    }


    /**
     * 물고기 삭제
     */
    public void delete_Fish_Select(int fish_Number){
            //물고기 피가 0 이면 피 검사후에 피가 0 이면
            if(fish_List.get(fish_Number).get_Fish_Hp() <= 0){

                //오징어를 죽였으면 먹물을 생성한다.
                if(fish_List.get(fish_Number) instanceof Fish_Touch_Squid){
                    add_Fish_Touch_Squid_Ink(fish_List.get(fish_Number).get_Fish_Point_X(), fish_List.get(fish_Number) .get_Fish_Point_Y());
                }


                fish_List.remove(fish_Number);
            }

    }
    public void delete_Fish(){
        for(int i=0; i<fish_List.size();i++){

            //물고기가 y축 으로 넘어가면 삭제
            if(fish_List.get(i).get_Fish_Point_Y() >= getHeight() - 30
                    || fish_List.get(i).get_Fish_Point_X() < -30                  //X축으로 0 보다 작으면 삭제
                    || fish_List.get(i).get_Fish_Point_X() > getWidth() + 30){       //X축으로 화면 보다 크면 삭제

                //물고기가 y축으로 넘어가면 hp, 해파리는 x축 기준으로 사라지기때문에
                if(fish_List.get(i).get_Fish_Point_Y() >= getHeight() - 30) {
                    main_Character.set_Hp_Minus();
                }

                fish_List.remove(i);


                break;
            }

        }
    }


    /**
     *  바닥 생명체 삭제
     */
    public void delete_Ground_Select(int ground_Number){
            if(ground_List.get(ground_Number).get_Ground_Hp() <= 0){
                ground_List.remove(ground_Number);
            }
    }

    public void delete_Ground(){
        for(int i=0; i<ground_List.size(); i++){

            //성개는 시간이 지나면 삭제되야 함으로 피가 0인 객체를 탐색한다.
            if((ground_List.get(i).get_Ground_Class() == 10)) {
                delete_Ground_Select(i);
                break;
            }


            //물고기가 y축 으로 넘어가면 삭제
            if(ground_List.get(i).get_Ground_Point_Y() >= getHeight() - 30){
                ground_List.remove(i);

                //그라운드 생명체 y축 넘어가면 hp 감소
                main_Character.set_Hp_Minus();
                break;
            }
        }
    }





    /**
     * 그라운드 생명체 대미지 넣기 (달팽이)
     */

    public boolean ground_Hit_Chose(float x, float y, int ground_Class){     //그라운드 객체의 종류 알아오기

        ground_Remove_Temp = -1;                    //달팽이 없을때를 기준 터치하는 곳의 달팽이 인덱스를 집어 넣는다.
        for(int i=0; i<ground_List.size(); i++){    // +- 45 는 판정을 좋게 하기 위해 추가

            if(        x >= ground_List.get(i).get_Ground_Point_X() - 45
                    && x <= ground_List.get(i).get_Ground_Point_X() + ground_List.get(i).get_GroundPoint_Width() + 45
                    && y >= ground_List.get(i).get_Ground_Point_Y() - 45
                    && y <= ground_List.get(i).get_Ground_Point_Y() + ground_List.get(i).get_GroundPoint_Height() + 45){

                ground_Remove_Temp = i;
                break;

            }

        }

        //선택된 달팽이가 존재 한다면. && 달팽이라면
        if(ground_Remove_Temp >= 0 && ground_List.get(ground_Remove_Temp).get_Ground_Class() == 1 && ground_Class == ground_List.get(ground_Remove_Temp).get_Ground_Class()) {

            //Log.d("aaaa", ground_Class + "a" + ground_List.get(ground_Remove_Temp).get_Ground_Class());
            //if(ground_List.get(ground_Remove_Temp).get_Ground_Hp() != 1) //달팽이 피가 1일때 클릭당하면 아무 이펙트 없음 이유는 굳이 이 루틴 하나 때문에 많은 코드를 넣기 불편함과 동시에 부하
/*
            effect_Ground_Draw_X.add(ground_List.get(ground_Remove_Temp).get_Ground_Point_X());
            effect_Ground_Draw_Y.add(ground_List.get(ground_Remove_Temp).get_Ground_Point_Y());
            new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tempInt = random.nextInt(4);

                        for (int i = 2; i < 5; i++) {
                            try {
                                Thread.sleep(15);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        for (int j = 0; j < effect_Pop2_img.length - 1; j++) {
                            try {
                                //Thread.sleep(15);
                                //if(ground_Remove_Temp < 0){
                                //   break;
                                //}
                                //팝 이펙트 random.nextInt(6)
                                if (tempInt == 0) {
                                    effect_Temp = effect_Pop2_img[i];
                                } else if (tempInt == 1) {
                                    effect_Temp = effect_Pop3_img[i];
                                } else if (tempInt == 2) {
                                    effect_Temp = effect_Pop4_img[i];
                                } else {
                                    effect_Temp = effect_Pop5_img[i];
                                }

                                draw.draw_Bmp(canvas, effect_Temp,
                                        effect_Ground_Draw_X.get(j) + random.nextInt(ground_Touch_Snail_Hp1_img[0].getWidth()) - 35,
                                        effect_Ground_Draw_Y.get(j) + random.nextInt(ground_Touch_Snail_Hp1_img[0].getHeight()) - 35);

                            } catch (Exception e) {
                            }
                        }
                    }
                        effect_Ground_Draw_X.clear();
                        effect_Ground_Draw_Y.clear();
                    }
                }).start();
*/
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
            delete_Ground_Select(ground_Remove_Temp);   //피가 감소된 객체 0일때 삭제
            soundPool.play(sound_Effect[random.nextInt(2)], 0.7F, 0.7F, 1, 1, 1.0F);   //달팽이 기본 팝 사운드
            return true;

            //선택된 꽃게가 있다면.
        }else if(ground_Remove_Temp >= 0 && ground_List.get(ground_Remove_Temp).get_Ground_Class() == 2 && ground_Class == ground_List.get(ground_Remove_Temp).get_Ground_Class()){
            tempInt = random.nextInt(5);


            effect_Temp = effect_Pop1_img[tempInt];



            crap_Ground_Hit_Flag = true; //꽃게 터치 이벤트 doDraw에서 발생



            //드래그된 꽃게의 체력을 깍는다.
            ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();
            delete_Ground_Select(ground_Remove_Temp);   //피가 감소된 객체 0일때 삭제
            soundPool.play(sound_Effect[2 + random.nextInt(2)], 0.05F, 0.05F, 1, 1, 1.0F);   //드래그 사운드
            Score++;            //점수 추가
            main_Character.set_Character_Experience();  //경험치 추가
            return true;

        }else if(ground_Remove_Temp >= 0 && ground_List.get(ground_Remove_Temp).get_Ground_Class() == 10
                && ground_Class == ground_List.get(ground_Remove_Temp).get_Ground_Class()){ //성게를 클릭했다면 hp 감소


            effect_Temp = effect_Black_img[4];


            seaurchin_Ground_Hit_Flag = true;   //성게 터치 이벤트는 doDraw 에서


//            ground_List.get(ground_Remove_Temp).set_Ground_Hp_Minus();  //성게 삭제,
//            delete_Ground_Select(ground_Remove_Temp);   //피가 감소된 객체 0일때 삭제



            //메인 캐릭터 hp 감소 루틴 추가 해야함
            soundPool.play(sound_Effect[random.nextInt(2)], 0.5F, 0.5F, 1, 1, 1.0F);   //물고기 기본 팝 사운드
            return true;
        }

        return false;
    }



    /**
     * 물고기 히트
     */
    public boolean fish_Hit_Chose(int fish_Class){

        //물고기 삭제 1번 먼저
        if(fish_List.size() != 0) {         //물고기가 존재할때 눌러짐
            eraser_Fish = false;            //물고기 지우기 허가하기


            /**
             * 가장 가까운 물고기를 찾는다.
             */
            for(int i=0; i<fish_List.size(); i++){

                //전달받은 물고기 인자가 아닐때 생깜
                if(fish_List.get(i).get_Fish_Class() != fish_Class){
                    continue;
                }

                //대각선 길이를 통해 가장 가까운 거리를 찾는다.
                smallMathResult = pythagoras(fish_List.get(i).get_Fish_Point_X() , fish_List.get(i).get_Fish_Point_Y());

                if(smallMathResult < smallFishTemp){
                    smallFishTemp = smallMathResult;
                    smallFishIndex = i;                     //for 문안에서 가장 가까운 물고기를 찾는다.
                    eraser_Fish = true;
                }

            }
            smallFishTemp = 5000; //제일 가까운 물고기 찾기위한 템프변수

            if(eraser_Fish){


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

//                    draw.draw_Bmp(canvas, effect_Temp, fish_List.get(smallFishIndex).get_Fish_Point_X() - 15, fish_List.get(smallFishIndex).get_Fish_Point_Y());


                    Log.d("fishListSize", "" + fish_List.size());

                    soundPool.play(sound_Effect[random.nextInt(2)], 0.5F, 0.5F, 1, 1, 1.0F);   //물고기 기본 팝 사운드
                    fish_List.get(smallFishIndex).set_Hp_Minus();            //풍타디 처럼 물고기 hp 깍으면 색깔 변경
                    default_Fish_Hit_Flag = true;   //그림은 드로우에서


                    delete_Fish_Select(smallFishIndex);     //피가0이 된 물고기 삭제.
                    Score++;
                    main_Character.set_Character_Experience(); //경험치 추가
                    return true;

                }



                if(fish_List.get(smallFishIndex).get_Fish_Hp() > 0) {        //드래그 속도까 빨라서 0밑으로내려감 방지
                    fish_List.get(smallFishIndex).set_Hp_Minus();            //풍타디 처럼 물고기 hp 깍으면 색깔 변경
                    delete_Fish_Select(smallFishIndex);     //피가0이 된 물고기 삭제.
                    Score++;                                                 //점수 증가
                    main_Character.set_Character_Experience();  //경험치 추가

                    if(fish_Class == 2) {
                        //드래그시 공격당한다는 느낌 주기 위해


                        drag_Fish_Hit_Flag = true;  //그림은 드로우에서
                        soundPool.play(sound_Effect[2 + random.nextInt(2)], 0.05F, 0.05F, 1, 1, 1.0F);   //드래그 사운드
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







    /**
     * 피타 고라스 함수 정의, 핸드폰 최하단 좌표 400, 1000 이랑 비교
     * 가장 가까운 물고기를 찾기 위해서
     */
    public double pythagoras(double x, double y){

        //피타고라스 정의 사용하기 위해 큰 x,y 값 도출
        if(window_Width/2 >= x){
            pointXBig = window_Width/2;
            pointXSmall = x;
        }else if(window_Width/2 <= x){
            pointXBig = x;
            pointXSmall = window_Width/2;
        }


        if(window_Height >= y){
            pointYBig = window_Height;
            pointYSmall = y;
        }else if(window_Height <= y){
            pointYBig = y;
            pointYSmall = window_Height;
        }

        return Math.sqrt(Math.pow((pointXBig - pointXSmall), 2) + Math.pow((pointYBig - pointYSmall), 2));
    }


    //********************************************************************************************//

    /**
     * 메인 캐릭터 그리기
     */
    public void draw_Main_Character_Touch(){ //공격할때 -> 터치 이벤트 일때, 드래그일때(x)


/*
        new Thread(new Runnable() {
            @Override
            public void run() {


                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(15);

                        draw.draw_Bmp(canvas, main_Character_img[i], main_Character.get_Main_Character_Point_X(),main_Character.get_Main_Character_Point_Y());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
*/

    }
    public void draw_Main_Character_Draw(){  //가만히 있을때


        if(main_Character.set_Character_Revolution(5)){
            //진화 경험치
            revolution_Flag = true;

        }


        //가장 처음 아메바 그리기
        if(main_Character instanceof Main_Character_Amoeba){

            main_Character_Draw(Score, main_Character_Amoeba);

            //진화 #인자에 진화할 점수 넣어줘야한다.
//            if(main_Character.set_Character_Revolution(50)){
//                main_Character = new Main_Character_Origin_Crab(window_Width, window_Height);
//                main_Character.set_Main_Character_Mode_Status_Init();
//                main_Character.set_Hp_Init();
//            }

        }else if(main_Character instanceof Main_Character_Origin_Crab){
            //꽃게 오리진
            main_Character_Draw(Score, main_Character_Orijin_Crab);


            //진화 #인자에 진화할 점수 넣어줘야한다. [물고기]
//            if(main_Character.set_Character_Revolution(150)){
//                main_Character = new Main_Character_Origin_Fish(window_Width, window_Height);
//                main_Character.set_Main_Character_Mode_Status_Init();
//                main_Character.set_Hp_Init();
//
//            }

        }else if(main_Character instanceof Main_Character_Origin_Fish){
            main_Character_Draw(Score, main_Character_Orijin_Fish);

            //진화 #인자에 진화할 점수 넣어줘야한다. [오징어]
//            if(main_Character.set_Character_Revolution(150)){
//                main_Character = new Main_Character_Origin_Squid(window_Width, window_Height);
//                main_Character.set_Main_Character_Mode_Status_Init();
//                main_Character.set_Hp_Init();
//
//            }
        }else if(main_Character instanceof Main_Character_Origin_Squid){
            main_Character_Draw(Score, main_Character_Orijin_Squid);
        }


        //진화할때
        if(revolution_Flag){
            m_Run_False();
        }



    }
//    private boolean upgrade_Character = true;   //진화가 아닌 업그레이드


    //메인 캐릭터 진화 할때 새로 그리기 #Score 진화할 점수 #character_img 캐릭터 이미지 배열
    private void main_Character_Draw(int score, Bitmap[] character_img){


        // #50 부분에 각 캐릭터마다 모양 변화 경험치가 들어가야함
        if(main_Character.set_Character_Upgrade(50)){
            //점수에 따라 곰팡이 진화
            main_Character.Set_Main_Character_Mode_Status();

        }
        //곰팡이 공격상태
        if(main_Character.get_Attack_State()){
            draw.draw_Bmp(canvas, character_img[main_Character.get_Main_Character_Mode_Status()+1], main_Character.get_Main_Character_Point_X(),main_Character.get_Main_Character_Point_Y());
        }else { //곰팡이 기본상태
            draw.draw_Bmp(canvas, character_img[main_Character.get_Main_Character_Mode_Status()], main_Character.get_Main_Character_Point_X(),main_Character.get_Main_Character_Point_Y());

        }
    }


    GraphicButton confirm_Button_1; //메인 캐릭터 진화시 뜨는 확인창

    GraphicButton confirm_Button_card_1; //메인 캐릭터 진화시 뜨는 확인창
    GraphicButton confirm_Button_card_2; //메인 캐릭터 진화시 뜨는 확인창
    GraphicButton confirm_Button_card_3; //메인 캐릭터 진화시 뜨는 확인창

    /**
     * 터치 이벤트
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {



        if(!mRun) {

            int touchx = (int)event.getX();
            int touchy = (int)event.getY();

            switch (event.getAction()) {
//                case MotionEvent.ACTION_CANCEL:
//                    confirm_Button.setPress(false);    //버튼 상태 초기화
//                    break;

                case MotionEvent.ACTION_UP:

                    confirm_Button_1.setPress(false);    //버튼 상태 초기화



                    if (confirm_Button_1.touch(touchx, touchy)) { //버튼 클릭 행동    //확인
                        //확인 버튼
//                  Score = 1000;
                    m_Run_True();   //게임 재게
                    revolution_Flag_Confirm = false; //변신창 확인 누르먄 재시작

                    }else if(confirm_Button_card_1.touch(touchx, touchy) || confirm_Button_card_2.touch(touchx, touchy)){
                        //진화창 버튼
//                        m_Run_True();   //게임 재게

                        //꽃게로 진화하면
//                        draw.draw_Bmp(canvas, explain_Window_Origin_Crab,
//                                window_Width / 2 - convertPixelsToDp(120, _context),
//                                window_Height / 2 - convertPixelsToDp(220, _context));
//
//                        soundPool.play(sound_Effect[8], 1F, 1F, 1, 1, 1.0F);
//
//                        Bitmap upimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_1);
//                        Bitmap downimage = BitmapFactory.decodeResource(_context.getResources(), R.drawable.confirm_button_2);
//
//                        confirm_Button_1.setImages(upimage, downimage);
//                        confirm_Button_1.draw(canvas);



                        //진화 성공시
                        int rand_Temp = random.nextInt(100);
                        if(rand_Temp < 30) {
                            main_Character = new Main_Character_Origin_Crab(window_Width, window_Height);

                        }else if(rand_Temp < 60){
                            main_Character = new Main_Character_Origin_Fish(window_Width, window_Height);

                        }else {
                            main_Character = new Main_Character_Origin_Squid(window_Width, window_Height);

                        }

                        main_Character.set_Main_Character_Mode_Status_Init();
                        main_Character.set_Hp_Init();

                        m_Run_True();   //게임 재게
                        revolution_Flag_Confirm = true;

                        //카드 선택시


//                        }

                    }else if(confirm_Button_card_3.touch(touchx, touchy)){
                        //광고 눌렀을때

                    }

                    break;
//                case MotionEvent.ACTION_MOVE: //버튼 상태 변경
//
//                    confirm_Button.setPress(confirm_Button.touch(touchx, touchy));
//                    break;
//                case MotionEvent.ACTION_DOWN: //버튼 상태 변경
//                    confirm_Button.setPress(confirm_Button.touch(touchx, touchy));
            }
        }else { //진화 창 떳을떄 확인 버튼


            //게임 터치 이벤트

            if (event.getAction() == MotionEvent.ACTION_DOWN) {           //손가락이 눌렸을때.
                touch_Check = 0;   //터치 체크 상태가 5이하 일때 터치라 판정.


            } else if (event.getAction() == MotionEvent.ACTION_UP) {      //때졌을때.


//            draw_Main_Character_Touch();    //메인캐릭터 그리기
                main_Character.set_Attack_State_True(); //메인캐릭터 공격


                if (touch_Check <= 5) {
                    //퍼즈 컨트롤
                    //if(pause_Effect(event.getX(), event.getY())){

                    if (ground_Hit_Chose(event.getX(), event.getY(), 1)) { //달팽이 삭제
                        //달팽이 터치 팝 이벤트


                    } else if (ground_Hit_Chose(event.getX(), event.getY(), 10)) { //성게 삭제

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


            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {    //드래그 중일때
                touch_Check++;
                drag_Action_Move++;
                if (drag_Action_Move > 2) {

                    if (ground_Hit_Chose(event.getX(), event.getY(), 2)) {    //꽃게 삭제

                    } else {
                        fish_Hit_Chose(2);                                  //드래그 물고기
                    }

                    drag_Action_Move = 0;
                }


            }
        }

        return true;

    }


    /**
     * 퍼즈 이벤트 게임 정지
     *
    public boolean pause_Effect(float x, float y){

        //window_Width - 100, 10
        //퍼지 이미지의 위치
        if(x >= window_Width - 100
                && x <= window_Width - 100 + pause_img[0].getWidth()
                && y >= 10
                && y <= 10 + + pause_img[0].getHeight()){
            pause_Push = true;
            //mRun = false;  //일시정지 화면 띄워야 함. -> 이미지가변경 되야 하기 때문에 이미지 변경후 멈춤으로 진행


            return true;
        }
        return false;


    }*/





    //********************************************************************************************//

    /**
     * 오버라이드 된것 시작과 동시에 구성, 교체
     * @param holder
     */


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Log.i("[뷰]", "구성");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Log.i("[뷰]", "교체");
        window_Width = width; //화면의 크기
        window_Height = height;


        //초기화
        re_Start();


        if(game_thread.getState() == Thread.State.TERMINATED) {
            game_thread = new Game_Thread(/*holder*/);  //쓰레드가 홈버튼을 누름으로 인해 파괴 된다면 다시 생성
            game_thread.start(); //게임

        }else {
            game_thread.start();
            //gameMainThread.start();
        }

//        if(game_element_thread.getState() == Thread.State.TERMINATED) {
//            game_element_thread = new Game_Element_Thread();  //쓰레드가 홈버튼을 누름으로 인해 파괴 된다면 다시 생성
//            game_element_thread.start(); //게임
//
//        }else {
//            game_element_thread.start();
//            //gameMainThread.start();
//        }

        mRun = true;

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //Log.i("[뷰]", "파괴");

        try{
//            game_thread.join(); //쓰레드 종료 -> 타이머로 대체함
//            game_element_thread.join();
            //gameMainThread.join();

//            타이머 파괴
            fish_Maker_1.cancel();
        }catch (Exception e){

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
