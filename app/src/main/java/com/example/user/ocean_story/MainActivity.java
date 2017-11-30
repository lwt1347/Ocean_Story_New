package com.example.user.ocean_story;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.key;

import com.firebase.client.Firebase;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener{

    public static MainActivity mainActivity;
    RewardedVideoAd mRewardedVideoAd;

    double info[] = new double[150];

    private SoundPool soundPool = new SoundPool(20, AudioManager.STREAM_ALARM, 0); //사운드 앞에 1은 하나만 가져다 놓겠다는 뜻. 나중에 추가 요망
    private int sound_Effect[] = new int[10];

    //sql 라이트
    static SQLiteDatabase database;

    private void recycleView(View view) {
        if(view != null) {
            Drawable bg = view.getBackground();
            if(bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable)bg).getBitmap().recycle();
                view.setBackgroundDrawable(null);
            }
        }
    }


    int dbset;

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
//                new AdRequest.Builder().addTestDevice("3079D7D2B9C6AB20E98F623393188D67").build());
                new AdRequest.Builder().build());
    }

    // 현재시간을 msec 으로 구한다.
    long now;
    // 현재시간을 date 변수에 저장한다.
    Date date;
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("dd");
//    yyyy/MM/dd HH:mm:ss
    // nowDate 변수에 값을 저장한다.
    String formatDate;
    TextView time_Text;



    int advertisement_Count = 25;
    int day_1 = 0;
    int day_2 = 0;

    String real_money = "0";

    boolean advertisement_Count_Flag = true;
    RelativeLayout layout;

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    TextView textView1;
    TextView textView2;
    com.google.android.gms.ads.AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setFullAd();

//        ad = (AudioManager)getSystemService(AUDIO_SERVICE);
        //음향
        pref = this.getSharedPreferences("pref", Activity.MODE_APPEND);
        editor = pref.edit();

        advertisement_Count = pref.getInt("advertisement", 25);

        day_1 = pref.getInt("day_1",0);
        day_2 = 0;//현재

        real_money = pref.getString("realmoney","0");

        now = System.currentTimeMillis();
        date = new Date(now);
        formatDate = sdfNow.format(date);
        day_2 = Integer.parseInt(formatDate); //현재 날짜
//        time_Text.setText(day_2 + "");

        //날짜가 달라지면

        if(day_1 != day_2){
            //하루 경과 할 때
            advertisement_Count = 25;
            editor.putInt("advertisement", advertisement_Count);
            editor.commit();
        }
        Log.e("@", "d1 = "+day_1);
        Log.e("@", "d2 = "+day_2);





        vol_E = pref.getInt("es",1);
        set_Sound(vol_E, pref.getInt("bs",1));



        setContentView(R.layout.activity_main);

        time_Text = (TextView)findViewById(R.id.text_Time);
//        Timer_Text timer_text = new Timer_Text();
//        timer_text.start();
        time_Text.setText("오늘 남은 횟수 : " + advertisement_Count + "\n 데이터 발생 주의!!!\n[wi-fi 권장]");





        try {
            //배너 광고 띄우기
            Firebase.setAndroidContext(this);
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            MultiDex.install(this);

        }catch (Exception e){
            Log.e("@","배너 광고");
        }

        try {

            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            if (ai.metaData != null) {
                int metaData = ai.metaData.getInt("com.google.android.gms.version");
                Log.e("metaData",metaData+"");
            }

            mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
            mRewardedVideoAd.setRewardedVideoAdListener(this);

            loadRewardedVideoAd();


            Log.e("@","성공");
        }catch (Exception e){
            Log.e("@",e.toString());
        }


        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //메모리 관리
        layout = (RelativeLayout)findViewById(R.id.activity_main);
        layout.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.background_start)));


        //음향
        sound_Effect[0] = soundPool.load(this, R.raw.up, 1);      //팝1



        ///////////////////////////////////////////////////////////////////////
        //데이터 베이스는 테이블을 최초 한번만 생성해야 하기 때문에 최초 한번의 정보를 가지고 만든다.
        SharedPreferences dbSetFirst = getSharedPreferences("dbSetFirst", Activity.MODE_APPEND);
        if(dbSetFirst != null){
            dbset = dbSetFirst.getInt("dbSetFirst", -1);
        }
        dbset++;
        advertise_Count = dbset;
        ///값 저장
        SharedPreferences.Editor editor = dbSetFirst.edit();
        editor.putInt("dbSetFirst", dbset);
        editor.commit();
        ///////////////////////////////////

        //데이터 베이스 생성 및 오픈
        database = openOrCreateDatabase("oceanstory1.db", MODE_PRIVATE, null);

        if(database != null){
            //db 오픈 되있음.

        }


        Log.e("aa","dbset = "+dbset);
        if(dbset == 0) {
            //데이터 베이스 생성시에 한번만 실행해야 한다.
            String sql = "create table maincharacterinfo(_id integer PRIMARY KEY autoincrement, ruby integer,money double,structuredamage integer, dragdamage integer, bird integer, urchinresistance integer,  lightningresistance integer, crocodileresistance integer, steel integer, ft1 integer, ft2 integer, ft3 integer, ft4 integer, ft5 integer, ft6 integer, ft7 integer, ft8 integer, ft9 integer, ft10 integer, st1 integer, st2 integer, st3 integer, st4 integer, st5 integer, st6 integer, st7 integer, st8 integer, st9 integer, st10 integer, mt1 integer, mt2 integer, mt3 integer, mt4 integer, mt5 integer, mt6 integer, mt7 integer, mt8 integer, mt9 integer, mt10 integer, ftb1 integer, ftb2 integer, ftb3 integer, ftb4 integer, ftb5 integer, ftb6 integer, ftb7 integer, ftb8 integer, ftb9 integer, ftb10 integer, stb1 integer, stb2 integer, stb3 integer, stb4 integer, stb5 integer, stb6 integer, stb7 integer, stb8 integer, stb9 integer, stb10 integer, mtb1 integer, mtb2 integer, mtb3 integer, mtb4 integer, mtb5 integer, mtb6 integer, mtb7 integer, mtb8 integer, mtb9 integer, mtb10 integer, me1 integer , me2 integer, me3 integer, me4 integer, me5 integer, me6 integer, me7 integer, me8 integer, me9 integer, me10 integer, me11 integer, me12 integer, me13 integer, me14 integer, me15 integer, me16 integer, me17 integer, me18 integer, me19 integer, me20 integer, ce1 integer, ce2 integer, ce3 integer, ce4 integer, ce5 integer, ce6 integer, ce7 integer, ce8 integer, ce9 integer, ce10 integer, ce11 integer, ce12 integer, ce13 integer, ce14 integer, ce15 integer, ce16 integer, ce17 integer, ce18 integer, ce19 integer, ce20 integer, ce21 integer, ce22 integer, ce23 integer, ce24 integer, ce25 integer, ce26 integer, ce27 integer, ce28 integer, ce29 integer, ce30 integer, ce31 integer, ce32 integer, ce33 integer, ce34 integer, ce35 integer)";
            database.execSQL(sql);
            insertData(0, 0, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0);
        }

        selectData();

    }

    /**
     * 데이터 베이스
     */
    //값 집어넣기
    public void insertData(int ruby,  double money, int structuredamage, int dragdamage, int bird, int urchinresistance, int lightningresistance, int crocodileresistance, int steel,
                           int ft1 ,int ft2, int ft3, int ft4, int ft5, int ft6, int ft7, int ft8, int ft9, int ft10,
                           int st1, int st2, int st3, int st4, int st5, int st6, int st7, int st8, int st9, int st10,
                           int mt1, int mt2, int mt3, int mt4, int mt5, int mt6, int mt7, int mt8, int mt9, int mt10,
                           int ftb1 ,int ftb2, int ftb3, int ftb4, int ftb5, int ftb6, int ftb7, int ftb8, int ftb9, int ftb10,
                           int stb1, int stb2, int stb3, int stb4, int stb5, int stb6, int stb7, int stb8, int stb9, int stb10,
                           int mtb1, int mtb2, int mtb3, int mtb4, int mtb5, int mtb6, int mtb7, int mtb8, int mtb9, int mtb10,
                           int me1, int me2, int me3, int me4, int me5, int me6, int me7, int me8, int me9, int me10,
                           int me11, int me12, int me13, int me14, int me15, int me16, int me17, int me18, int me19,  int me20,
                           int ce1, int ce2, int ce3, int ce4, int ce5, int ce6, int ce7, int ce8, int ce9, int ce10,
                           int ce11, int ce12, int ce13, int ce14, int ce15, int ce16, int ce17, int ce18, int ce19, int ce20,
                           int ce21, int ce22, int ce23, int ce24, int ce25, int ce26, int ce27, int ce28, int ce29, int ce30,
                           int ce31, int ce32, int ce33, int ce34, int ce35){

        if(database != null){
            //b = 스킬 추출
            //me = 몬스터 설명창 설명 띄우줄지 말지 0 -> 띄어줌
            //ce = 캐릭터 설명창
            String sql = "insert into maincharacterinfo(ruby, money, structuredamage, dragdamage, bird, urchinresistance, lightningresistance, crocodileresistance, steel, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, st1, st2, st3, st4, st5, st6, st7, st8, st9, st10, mt1, mt2, mt3, mt4, mt5, mt6, mt7, mt8, mt9, mt10, ftb1, ftb2, ftb3, ftb4, ftb5, ftb6, ftb7, ftb8, ftb9, ftb10, stb1, stb2, stb3, stb4, stb5, stb6, stb7, stb8, stb9, stb10, mtb1, mtb2, mtb3, mtb4, mtb5, mtb6, mtb7, mtb8, mtb9, mtb10, me1, me2, me3, me4, me5, me6, me7, me8, me9, me10, me11, me12, me13, me14, me15, me16, me17, me18, me19, me20, ce1, ce2, ce3, ce4, ce5, ce6, ce7, ce8, ce9, ce10, ce11, ce12, ce13, ce14, ce15, ce16, ce17, ce18, ce19, ce20, ce21, ce22, ce23, ce24, ce25, ce26, ce27, ce28, ce29, ce30, ce31, ce32, ce33, ce34, ce35) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Object[] params = { ruby, money, structuredamage, dragdamage, bird, urchinresistance, lightningresistance, crocodileresistance, steel,
                    ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10,
                    st1, st2, st3, st4, st5, st6, st7, st8, st9, st10,
                    mt1, mt2, mt3, mt4, mt5, mt6, mt7, mt8, mt9, mt10,
                    ftb1, ftb2, ftb3, ftb4, ftb5, ftb6, ftb7, ftb8, ftb9, ftb10,
                    stb1, stb2, stb3, stb4, stb5, stb6, stb7, stb8, stb9, stb10,
                    mtb1, mtb2, mtb3, mtb4, mtb5, mtb6, mtb7, mtb8, mtb9, mtb10,
                    me1, me2, me3, me4, me5, me6, me7, me8, me9, me10,
                    me11, me12, me13, me14, me15, me16, me17, me18, me19, me20,
                    ce1, ce2, ce3, ce4, ce5, ce6, ce7, ce8, ce9, ce10,
                    ce11, ce12, ce13, ce14, ce15, ce16, ce17, ce18, ce19, ce20,
                    ce21, ce22, ce23, ce24, ce25, ce26, ce27, ce28, ce29, ce30,
                    ce31, ce32, ce33, ce34, ce35,};

            database.execSQL(sql, params);

        }
    }

    //값 가져오기
    public void selectData(){
        if(database != null){
            String sql = "select ruby, money, structuredamage, dragdamage, bird, urchinresistance, lightningresistance, crocodileresistance, steel, ft1, ft2, ft3, ft4, ft5, ft6, ft7, ft8, ft9, ft10, st1, st2, st3, st4, st5, st6, st7, st8, st9, st10, mt1, mt2, mt3, mt4, mt5, mt6, mt7, mt8, mt9, mt10, ftb1, ftb2, ftb3, ftb4, ftb5, ftb6, ftb7, ftb8, ftb9, ftb10, stb1, stb2, stb3, stb4, stb5, stb6, stb7, stb8, stb9, stb10, mtb1, mtb2, mtb3, mtb4, mtb5, mtb6, mtb7, mtb8, mtb9, mtb10, me1, me2, me3, me4, me5, me6, me7, me8, me9, me10, me11, me12, me13, me14, me15, me16, me17, me18, me19, me20, ce1, ce2, ce3, ce4, ce5, ce6, ce7, ce8, ce9, ce10, ce11, ce12, ce13, ce14, ce15, ce16, ce17, ce18, ce19, ce20, ce21, ce22, ce23, ce24, ce25, ce26, ce27, ce28, ce29, ce30, ce31, ce32, ce33, ce34, ce35 from maincharacterinfo";

            Cursor cursor = database.rawQuery(sql, null);
            Log.e("e", " : " + cursor.getCount());
            cursor.moveToNext();
            //37 -> 67 -> 87 -> 112 -> 122
            for(int i=0; i<122; i++){
                info[i] = cursor.getDouble(i);

            }
//            Toast.makeText(getApplicationContext(), "" + "db 값" + info[1], Toast.LENGTH_SHORT).show();


            cursor.close();
        }
        info[1] = Double.parseDouble(real_money);
        Log.e("@","돈 : " + info[1]);

    }

    int get_Item_Integer[] = new int[150];

    //인텐트 결과값 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



    try {
        real_money = pref.getString("realmoney","0");
        info[1] = Double.parseDouble(real_money);
        Log.e("@"," 돈ㄷ = " + info[1]);

        // 넘어갔던 화면에서 되돌아 왔을 때
        if (resultCode == RESULT_OK) { // 정상 반환일 경우에만 동작하겠다
            Log.e("정상 반환","정상 반환");
//            Toast.makeText(getApplicationContext(), "??", Toast.LENGTH_SHORT).show();
//            int num = data.getIntExtra("hap", 0);
//            info = data.getIntArrayExtra("info");
//            Log.e("@", "@" + info[0] + "씨발");
            double get_Item[] = data.getDoubleArrayExtra("item");
//            Toast.makeText(getApplicationContext(), "" + get_Item[0], Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "" + get_Item[1], Toast.LENGTH_SHORT).show();
            info[0] = get_Item[0];
//            info[1] = get_Item[1];
            real_money = pref.getString("realmoney","0");
            info[1] = Double.parseDouble(real_money);


            //0 = money, 1 = ruby
            Log.e("@", "@" + info[1]);

            Log.e("@",0 + " = " + get_Item[0]);
            Log.e("@",1 + " = " + get_Item[1]);
            //77/87
            for(int i=2; i<87; i++){
                info[i] = get_Item[i];
                Log.e("@",i + " = " + get_Item[i]);
            }

//            SharedPreferences pref = GameMain._context.getSharedPreferences("pref", Activity.MODE_APPEND);
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putInt("money", get_Item[0]);

//            if(database != null){

            for(int i=0; i< 87; i++){
                get_Item_Integer[i] = (int)get_Item[i];
            }


//            String sql = "UPDATE maincharacterinfo SET money = '" + get_Item[1] + "', ruby = '" + get_Item_Integer[0] + "'"
//+ "', ftb1 = '" + get_Item_Integer[2] + "'" + "', ftb2 = '" + get_Item_Integer[3] + "'" + "', ftb3 = '" + get_Item_Integer[4] + "'" + "', ftb4 = '" + get_Item_Integer[5] + "'" + "', ftb5 = '" + get_Item_Integer[6] + "'" + "', ftb6 = '" + get_Item_Integer[7] + "'" + "', ftb7 = '" + get_Item_Integer[8] + "'" + "', ftb8 = '" + get_Item_Integer[9] + "'"+ "', ftb9 = '" + get_Item_Integer[10] + "'"+ "', ftb10 = '" + get_Item_Integer[11] + "'"
//+ "', stb1 = '" + get_Item_Integer[12] + "'" + "', stb2 = '" + get_Item_Integer[13] + "'" + "', stb3 = '" + get_Item_Integer[14] + "'" + "', stb4 = '" + get_Item_Integer[15] + "'" + "', stb5 = '" + get_Item_Integer[16] + "'" + "', stb6 = '" + get_Item_Integer[17] + "'" + "', stb7 = '" + get_Item_Integer[18] + "'" + "', stb8 = '" + get_Item_Integer[19] + "'"+ "', stb9 = '" + get_Item_Integer[20] + "'"+ "', stb10 = '" + get_Item_Integer[21] + "'"
//+ "', mtb1 = '" + get_Item_Integer[22] + "'" + "', mtb2 = '" + get_Item_Integer[23] + "'" + "', mtb3 = '" + get_Item_Integer[24] + "'" + "', mtb4 = '" + get_Item_Integer[25] + "'" + "', mtb5 = '" + get_Item_Integer[26] + "'" + "', mtb6 = '" + get_Item_Integer[27] + "'" + "', mtb7 = '" + get_Item_Integer[28] + "'" + "', mtb8 = '" + get_Item_Integer[29] + "'"+ "', mtb9 = '" + get_Item_Integer[30] + "'"+ "', mtb10 = '" + get_Item_Integer[31] + "'";

            String sql = "UPDATE maincharacterinfo SET money = '" + get_Item_Integer[1] + "', ruby = '" + get_Item_Integer[0] + "'"
                    + ", ftb1 = '" + get_Item_Integer[2] + "'" + ", ftb2 = '" + get_Item_Integer[3] + "'" + ", ftb3 = '" + get_Item_Integer[4] + "'" + ", ftb4 = '" + get_Item_Integer[5] + "'" + ", ftb5 = '" + get_Item_Integer[6] + "'" + ", ftb6 = '" + get_Item_Integer[7] + "'" + ", ftb7 = '" + get_Item_Integer[8] + "'" + ", ftb8 = '" + get_Item_Integer[9] + "'"+ ", ftb9 = '" + get_Item_Integer[10] + "'"+ ", ftb10 = '" + get_Item_Integer[11] + "'"
                    + ", stb1 = '" + get_Item_Integer[12] + "'" + ", stb2 = '" + get_Item_Integer[13] + "'" + ", stb3 = '" + get_Item_Integer[14] + "'" + ", stb4 = '" + get_Item_Integer[15] + "'" + ", stb5 = '" + get_Item_Integer[16] + "'" + ", stb6 = '" + get_Item_Integer[17] + "'" + ", stb7 = '" + get_Item_Integer[18] + "'" + ", stb8 = '" + get_Item_Integer[19] + "'"+ ", stb9 = '" + get_Item_Integer[20] + "'"+ ", stb10 = '" + get_Item_Integer[21] + "'"
                    + ", mtb1 = '" + get_Item_Integer[22] + "'" + ", mtb2 = '" + get_Item_Integer[23] + "'" + ", mtb3 = '" + get_Item_Integer[24] + "'" + ", mtb4 = '" + get_Item_Integer[25] + "'" + ", mtb5 = '" + get_Item_Integer[26] + "'" + ", mtb6 = '" + get_Item_Integer[27] + "'" + ", mtb7 = '" + get_Item_Integer[28] + "'" + ", mtb8 = '" + get_Item_Integer[29] + "'"+ ", mtb9 = '" + get_Item_Integer[30] + "'"+ ", mtb10 = '" + get_Item_Integer[31] + "'"
                    + ", me1 = '" + get_Item_Integer[32] + "'"+ ", me2 = '" + get_Item_Integer[33] + "'"+ ", me3 = '" + get_Item_Integer[34] + "'"+ ", me4 = '" + get_Item_Integer[35] + "'"+ ", me5 = '" + get_Item_Integer[36] + "'"+ ", me6 = '" + get_Item_Integer[37] + "'"+ ", me7 = '" + get_Item_Integer[38] + "'"+ ", me8 = '" + get_Item_Integer[39] + "'"+ ", me9 = '" + get_Item_Integer[40] + "'"+ ", me10 = '" + get_Item_Integer[41] + "'"
                    + ", me11 = '" + get_Item_Integer[42] + "'"+ ", me12 = '" + get_Item_Integer[43] + "'"+ ", me13 = '" + get_Item_Integer[44] + "'"+ ", me14 = '" + get_Item_Integer[45] + "'"+ ", me15 = '" + get_Item_Integer[46] + "'"+ ", me16 = '" + get_Item_Integer[47] + "'"+ ", me17 = '" + get_Item_Integer[48] + "'"+ ", me18 = '" + get_Item_Integer[49] + "'"+ ", me19 = '" + get_Item_Integer[50] + "'"+ ", me20 = '" + get_Item_Integer[51] + "'"
                    + ", ce1 = '" + get_Item_Integer[52] + "'"+ ", ce2 = '" + get_Item_Integer[53] + "'"+ ", ce3 = '" + get_Item_Integer[54] + "'"+ ", ce4 = '" + get_Item_Integer[55] + "'"+ ", ce5 = '" + get_Item_Integer[56] + "'"+ ", ce6 = '" + get_Item_Integer[57] + "'"+ ", ce7 = '" + get_Item_Integer[58] + "'"+ ", ce8 = '" + get_Item_Integer[59] + "'"+ ", ce9 = '" + get_Item_Integer[60] + "'"+ ", ce10 = '" + get_Item_Integer[61] + "'"
                    + ", ce11 = '" + get_Item_Integer[62] + "'"+ ", ce12 = '" + get_Item_Integer[63] + "'"+ ", ce13 = '" + get_Item_Integer[64] + "'"+ ", ce14 = '" + get_Item_Integer[65] + "'"+ ", ce15 = '" + get_Item_Integer[66] + "'"+ ", ce16 = '" + get_Item_Integer[67] + "'"+ ", ce17 = '" + get_Item_Integer[68] + "'"+ ", ce18 = '" + get_Item_Integer[69] + "'"+ ", ce19 = '" + get_Item_Integer[70] + "'"+ ", ce20 = '" + get_Item_Integer[71] + "'"
                    + ", ce21 = '" + get_Item_Integer[72] + "'"+ ", ce22 = '" + get_Item_Integer[73] + "'"+ ", ce23 = '" + get_Item_Integer[74] + "'"+ ", ce24 = '" + get_Item_Integer[75] + "'"+ ", ce25 = '" + get_Item_Integer[76] + "'"+ ", ce26 = '" + get_Item_Integer[77] + "'"+ ", ce27 = '" + get_Item_Integer[78] + "'"+ ", ce28 = '" + get_Item_Integer[79] + "'"+ ", ce29 = '" + get_Item_Integer[80] + "'"+ ", ce30 = '" + get_Item_Integer[81] + "'"
                    + ", ce31 = '" + get_Item_Integer[82] + "'"+ ", ce32 = '" + get_Item_Integer[83] + "'"+ ", ce33 = '" + get_Item_Integer[84] + "'"+ ", ce34 = '" + get_Item_Integer[85] + "'"+ ", ce35 = '" + get_Item_Integer[86] + "'";


            save_Db(sql);

            Log.e("onActivityResult", "db 저장 1= " + get_Item_Integer[14] + ", 2= " + get_Item_Integer[3] + ", = " + get_Item_Integer[13]);

//            }

            selectData();


            button1 = (Button)findViewById(R.id.button_game_start);
            button2 = (Button)findViewById(R.id.button_store);
            button3 = (Button)findViewById(R.id.button_diction);
            button4 = (Button)findViewById(R.id.button_getruby);
            textView1 = (TextView)findViewById(R.id.textView5);
            textView2 = (TextView)findViewById(R.id.text_Time);
            adView = (com.google.android.gms.ads.AdView)findViewById(R.id.adView);

            layout.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.background_start)));
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
            textView1.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            adView.setVisibility(View.VISIBLE);

        }
    }catch (Exception e){
        Log.e("onActivityResult", "onActivityResult");
    }

    }

    public void save_Db(String sql){
        database.execSQL(sql);
    }



    /**
     * 터치 이팩트 사운드
     */
    public float sound = 0.0f;
    int vol_E = 0;
    int vol_B = 0;

    SharedPreferences.Editor editor;
    SharedPreferences pref;
    public void set_Sound(int vol, int bs){

        if(vol == 10){
            sound = 1.0f;
        }else if(vol == 9){
            sound = 0.9f;
        }else if(vol == 8){
            sound = 0.8f;
        }else if(vol == 7){
            sound = 0.7f;
        }else if(vol == 6){
            sound = 0.6f;
        }else if(vol == 5){
            sound = 0.5f;
        }else if(vol == 4){
            sound = 0.4f;
        }else if(vol == 3){
            sound = 0.3f;
        }else if(vol == 2){
            sound = 0.2f;
        }else if(vol == 1){
            sound = 0.1f;
        }else if(vol == 0){
            sound = 0.0f;
        }

        sound = sound/5;

        vol_E = vol;
        vol_B = bs;
        editor.putInt("es", vol_E);
        editor.putInt("bs", vol_B);


        editor.commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {



        //볼륨 이벤트
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:

                if(vol_E > 0){
                    vol_E--;
                    vol_B--;
                }

                set_Sound(vol_E,vol_B);

                break;

            case KeyEvent.KEYCODE_VOLUME_UP:

                if(vol_E < 10){
                    vol_E++;
                    vol_B++;
                }

                set_Sound(vol_E,vol_B);

                break;

            case KeyEvent.KEYCODE_BACK:

                finish();
                break;
        }

        return true;
        //        return super.onKeyDown(keyCode, event);
    }



    /**
     * Game_Start 버튼 클릭 이벤트
     */
    int advertise_Count = 0;
    public void onButtonGame_StartClicked(View view){
        //게임 시작 버튼을 눌렀을때 셋팅 정보를 넘긴다.
        //게임화면 시작
        button1 = (Button)findViewById(R.id.button_game_start);
        button2 = (Button)findViewById(R.id.button_store);
        button3 = (Button)findViewById(R.id.button_diction);
        button4 = (Button)findViewById(R.id.button_getruby);
        textView1 = (TextView)findViewById(R.id.textView5);
        textView2 = (TextView)findViewById(R.id.text_Time);
        adView = (com.google.android.gms.ads.AdView)findViewById(R.id.adView);

        layout.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.background_lode)));
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        textView1.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        adView.setVisibility(View.INVISIBLE);

        if(advertise_Count == 0 || advertise_Count%5 != 0 ){

            soundPool.play(sound_Effect[0], sound, sound, 0, 0, 1.0F);   //성공

            Intent intent = new Intent(getApplicationContext(), GameActivity.class);

            selectData();

//          Toast.makeText(getApplicationContext(), "" + "db 값 던지기" + info[1], Toast.LENGTH_SHORT).show();
            intent.putExtra("set", info);

            startActivityForResult(intent, 1001);
//            setFullAd();
        }else {

            displayAD();
            soundPool.play(sound_Effect[0], sound, sound, 0, 0, 1.0F);   //성공

        }

        advertise_Count++;

        Log.e("@",advertise_Count + " @");

//        soundPool.play(sound_Effect[0], sound, sound, 0, 0, 1.0F);   //성공
//
//        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
//
//        selectData();
//
////        Toast.makeText(getApplicationContext(), "" + "db 값 던지기" + info[1], Toast.LENGTH_SHORT).show();
//        intent.putExtra("set", info);
//
//
//        startActivityForResult(intent, 1001);

    }

    private InterstitialAd interstitialAd;
//addTestDevice("3079D7D2B9C6AB20E98F623393188D67")
    private void setFullAd(){
        interstitialAd = new InterstitialAd(this); //새 광고를 만듭니다.
        interstitialAd.setAdUnitId(getResources().getString(R.string.ad_unit_id)); //이전에 String에 저장해 두었던 광고 ID를 전면 광고에 설정합니다.
        AdRequest adRequest1 = new AdRequest.Builder().build(); //새 광고요청
        interstitialAd.loadAd(adRequest1); //요청한 광고를 load 합니다.
        interstitialAd.setAdListener(new AdListener() { //전면 광고의 상태를 확인하는 리스너 등록

            @Override
            public void onAdClosed() { //전면 광고가 열린 뒤에 닫혔을 때
                AdRequest adRequest1 = new AdRequest.Builder().build();  //새 광고요청
                interstitialAd.loadAd(adRequest1); //요청한 광고를 load 합니다.

                soundPool.play(sound_Effect[0], sound, sound, 0, 0, 1.0F);   //성공

                Intent intent = new Intent(getApplicationContext(), GameActivity.class);

                selectData();

//        Toast.makeText(getApplicationContext(), "" + "db 값 던지기" + info[1], Toast.LENGTH_SHORT).show();
                intent.putExtra("set", info);

                startActivityForResult(intent, 1001);

            }
        });
    }

    public void displayAD(){
        if(interstitialAd.isLoaded()) { //광고가 로드 되었을 시
            interstitialAd.show(); //보여준다
        }
    }






    /**
     *  옵션 버튼
     */
    Intent intent;
    public void onButtonOption(View view){


            intent = new Intent(this, option_Panel.class);
            //intent.putExtra("a", mRun);
            startActivityForResult(intent, 0); //-> 일시정지 창을 팝업한다. Menu_Sliding_Panel 호출

    }

    /**
     * 루비 받기
     */
    public void onButtonGetRuby(View view){



        soundPool.play(sound_Effect[0], sound, sound, 0, 0, 1.0F);   //성공
        if(advertisement_Count > 0) {
            try {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }else {

                    Toast.makeText(this, "시스템(인터넷)의 문제로 다시 한번 클릭해 주세요. ", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                Log.e("@", "동영상 광고");
            }
        }else {
            Toast.makeText(this, "광고 횟수가 남아 있지 않습니다.", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 상점 가기
     */
    double setIntent[] = new double[100];
    public void onButtonStore(View view){
        selectData();

        real_money = pref.getString("realmoney","0");
        info[1] = Double.parseDouble(real_money);

        Intent intent = new Intent(getApplicationContext(), Activity_Store.class);
        intent.putExtra("info", info);

//        Toast.makeText(getApplicationContext(), "" + info[0], Toast.LENGTH_SHORT).show();
        //intent.putExtra("cha","aa");
        startActivityForResult(intent, 1002);
//        recycleView(findViewById(R.layout.activity_store));


        soundPool.play(sound_Effect[0], sound, sound, 0, 0, 1.0F);   //성공


    }

    /**
     * 도감 보기
     */

    //몬스터 도감
    int monster_Explain_Db[] = new int[20];
    //캐릭터 도감
    int character_Explain_DB[] = new int[35];

    public void onButtonDiction(View view){
        soundPool.play(sound_Effect[0], sound, sound, 0, 0, 1.0F);

        intent = new Intent(getApplicationContext(), dictionary_Panel.class);

        //몬스터 설명창
        int monster_Explain_Db_Temp = 0;
        for(int i=69; i<89; i++){
            monster_Explain_Db[monster_Explain_Db_Temp] = (int)info[i];
//            Log.e("@", "setIntent = " + monster_Explain_Db[monster_Explain_Db_Temp] + " , " + monster_Explain_Db_Temp);
            monster_Explain_Db_Temp++;
        }

//        for(int i=0; i<setIntent.length; i++){
//            Log.e("a",setIntent[i] + "@" + i);
//        }

        int character_Explain_DB_Temp = 0;
        for(int i = 89; i<124; i++){
            character_Explain_DB[character_Explain_DB_Temp] = (int)info[i];
            character_Explain_DB_Temp++;
        }


        intent.putExtra("mexplain", monster_Explain_Db);
        intent.putExtra("cexplain", character_Explain_DB);

        //intent.putExtra("a", mRun);
        startActivityForResult(intent, 1002); //-


    }



    @Override
    public void onRewardedVideoAdLoaded() {
//        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        Log.e("@","onRewardedVideoAdLoaded");
    }

    @Override
    public void onRewardedVideoAdOpened() {
//        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
        Log.e("@","onRewardedVideoAdOpened");
    }

    @Override
    public void onRewardedVideoStarted() {
//        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
        Log.e("@","onRewardedVideoStarted");
    }

    @Override
    public void onRewardedVideoAdClosed() {
//        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        Log.e("@","onRewardedVideoAdClosed");
    }

    Random random;
    int ruby_Count = 0;
    int random_T = 0;
    @Override
    public void onRewarded(RewardItem rewardItem) {
//        Toast.makeText(this, "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
//                rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.

        Log.e("@","onRewarded" + rewardItem.getAmount());

        if(database != null){

            random = new Random();

            random_T = random.nextInt(100);

            if(random_T > 60){
                ruby_Count = 1;
            }else if(random_T > 40){
                ruby_Count = 2;
            }else if(random_T > 20){
                ruby_Count = 3;
            }else if(random_T > 10){
                ruby_Count = 4;
            }else {
                ruby_Count = 5;
            }

//            ruby_Count = 1 + random.nextInt(5);



            info[0] += ruby_Count;
            String sql = "UPDATE maincharacterinfo SET ruby= '" + info[0] + "'";


            database.execSQL(sql);

            intent = new Intent(getApplicationContext(), ruby_Panel.class);
            intent.putExtra( "rubycount" , ruby_Count);
            startActivity(intent);

            advertisement_Count--;

            editor.putInt("advertisement", advertisement_Count);
            editor.commit();

            editor.putInt("day_1", day_2);
            editor.commit();



            time_Text.setText("남은 횟수 : " + advertisement_Count);
        }


    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
//        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
//                Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "인터넷 연결을 해주세요. 혹은, 시스템 문제 일수도. ㅠㅠ", Toast.LENGTH_SHORT).show();
        Log.e("@","onRewardedVideoAdLeftApplication");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
//        Toast.makeText(this, "인터넷 연결을 해주세요. 혹은, 시스템 문제 일수도. ㅠㅠ", Toast.LENGTH_SHORT).show();
        Log.e("@","onRewardedVideoAdFailedToLoad");
    }

    @Override
    public void onResume() {

        mRewardedVideoAd.resume(this);
        super.onResume();
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();
        Log.e("@","onResume");
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
        Log.e("@","onPause");
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
        Log.e("@","onDestroy");
    }


}


