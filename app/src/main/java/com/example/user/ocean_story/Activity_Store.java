package com.example.user.ocean_story;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Lee on 2017-07-31.
 */

public class Activity_Store extends Activity {
    Item_Adapter adapter;


    double info [] = new double[50];
    double info_Price[] = new double[50];

    ListView listView;

    TextView money;
    TextView ruby;
    TextView store_Item_View_TextView[] = new TextView[50];
    TextView store_Item_View_TextLevel[] = new TextView[50];

    NumberFormat f;
    DecimalFormat df = new DecimalFormat("#,##0");


    //sql 라이트
    SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        f = NumberFormat.getInstance();

        Intent intent = getIntent();
        info = intent.getDoubleArrayExtra("info");

        money = (TextView) findViewById(R.id.textView12);
        ruby = (TextView) findViewById(R.id.textView13);


        //데이터 베이스
        database = openOrCreateDatabase("oceanstory1.db", MODE_PRIVATE, null);



        money.setText("x " + df.format(info[1]));
        ruby.setText("x " + df.format(info[0]));

        listView = (ListView) findViewById(R.id.listView);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);     //리스트뷰 가장 위일때 반짝임 없앰
        listView.setVerticalScrollBarEnabled(false); //스크롤 바 없앰

        adapter = new Item_Adapter();
//        adapter.addItem(new Activity_Store_Item("dragdamage", "드래그 대미지 증가"));
//        adapter.addItem(new Activity_Store_Item("score", "점수"));
//        adapter.addItem(new Activity_Store_Item("money", "돈"));
        adapter.addItem(new Activity_Store_Item("ruby", 0, 10000000, R.drawable.store_explain_ruby, R.drawable.button_store_buy, 1));


        //수정 사항 : 시작과 동시에 소수점 찍히는거 제거, 스크롤 변경후 숫자 작아지는것
        info_Price[2] = 1;
        for(int i=0; i<info[2]-1; i++){
            info_Price[2] *= 1.01;
        }

        adapter.addItem(new Activity_Store_Item("structure_damage", info[2] , (info_Price[2]) , R.drawable.store_structure_damage, R.drawable.button_store_buy, 1));

        info_Price[3] = 500;
        for(int i=0; i<info[3]-1; i++){
            info_Price[3] *= 2;
        }

        adapter.addItem(new Activity_Store_Item("drag_damage",  info[3] , (info_Price[3]), R.drawable.store_drag_damage, R.drawable.button_store_buy, 1));

        info_Price[4] = 500;
        for(int i=0; i<info[4]-1; i++){
            info_Price[4] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("urchin_resistance", info[4] , (info_Price[4]), R.drawable.store_urchin_resistance, R.drawable.button_store_buy, 1));

        info_Price[5] = 500;
        for(int i=0; i<info[5]-1; i++){
            info_Price[5] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("lightning_resistance",  info[5] , (info_Price[5]), R.drawable.store_lightning_resistance, R.drawable.button_store_buy, 1));

        info_Price[6] = 500;
        for(int i=0; i<info[6]-1; i++){
            info_Price[6] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("crocodile_resistance", info[6] ,(info_Price[6]), R.drawable.store_crocodile_resistance, R.drawable.button_store_buy, 1));



//        adapter.addItem(new Activity_Store_Item("ft1", "물고기1"));
        info_Price[8] = 200;
        for(int i=0; i<info[8]-1; i++){
            info_Price[8] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft2", info[8] , (info_Price[8]), R.drawable.store_fish2, R.drawable.button_store_probability, 1));

        info_Price[9] = 300;
        for(int i=0; i<info[9]-1; i++){
            info_Price[9] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft3", info[9] , (info_Price[9]),  R.drawable.store_fish3, R.drawable.button_store_probability, 1));

        info_Price[10] = 400;
        for(int i=0; i<info[10]-1; i++){
            info_Price[10] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft4", info[10], (info_Price[10]),  R.drawable.store_fish4, R.drawable.button_store_probability, 1));

        info_Price[11] = 500;
        for(int i=0; i<info[11]-1; i++){
            info_Price[11] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft5",  info[11] , (info_Price[11]),  R.drawable.store_fish5, R.drawable.button_store_probability, 1));

        info_Price[12] = 600;
        for(int i=0; i<info[12]-1; i++){
            info_Price[12] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft6",  info[12] , (info_Price[12]),  R.drawable.store_fish6, R.drawable.button_store_probability, 1));

        info_Price[13] = 700;
        for(int i=0; i<info[13]-1; i++){
            info_Price[13] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft7", info[13] , (info_Price[13]),  R.drawable.store_fish7, R.drawable.button_store_probability, 1));

        info_Price[14] = 800;
        for(int i=0; i<info[14]-1; i++){
            info_Price[14] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft8", info[14] , (info_Price[14]),  R.drawable.store_fish8, R.drawable.button_store_probability, 1));

        info_Price[15] = 900;
        for(int i=0; i<info[15]-1; i++){
            info_Price[15] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft9",  info[15] , (info_Price[15]),  R.drawable.store_fish9, R.drawable.button_store_probability, 1));

        info_Price[16] = 1000;
        for(int i=0; i<info[16]-1; i++){
            info_Price[16] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft10",  info[16], (info_Price[16]),  R.drawable.store_fish10, R.drawable.button_store_probability, 1));

//        adapter.addItem(new Activity_Store_Item("st1", "꽃게1",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear2_text));
        info_Price[18] = 200;
        for(int i=0; i<info[18]-1; i++){
            info_Price[18] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st2", info[18] , (info_Price[18]),  R.drawable.store_shellfish2, R.drawable.button_store_probability, 1));

        info_Price[19] = 200;
        for(int i=0; i<info[19]-1; i++){
            info_Price[19] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st3", info[19] , (info_Price[19]),   R.drawable.store_shellfish3, R.drawable.button_store_buy, 1));

//        adapter.addItem(new Activity_Store_Item("st3", "꽃게3",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear3_text));
        info_Price[20] = 400;
        for(int i=0; i<info[20]-1; i++){
            info_Price[20] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st4", info[20] , (info_Price[20]),  R.drawable.store_shellfish4, R.drawable.button_store_probability, 1));

        info_Price[21] = 500;
        for(int i=0; i<info[21]-1; i++){
            info_Price[21] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st5", info[21] , (info_Price[21]),  R.drawable.store_shellfish5, R.drawable.button_store_probability, 1));

        info_Price[22] = 600;
        for(int i=0; i<info[22]-1; i++){
            info_Price[22] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st6", info[22] , (info_Price[22]),  R.drawable.store_shellfish6, R.drawable.button_store_probability, 1));

        info_Price[23] = 700;
        for(int i=0; i<info[23]-1; i++){
            info_Price[23] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st7",  info[23] , (info_Price[23]),  R.drawable.store_shellfish7, R.drawable.button_store_probability, 1));

        info_Price[24] = 800;
        for(int i=0; i<info[24]-1; i++){
            info_Price[24] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st8",  info[24] , (info_Price[24]),  R.drawable.store_shellfish8, R.drawable.button_store_probability, 1));

        info_Price[25] = 900;
        for(int i=0; i<info[25]-1; i++){
            info_Price[25] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st9",  info[25] ,(info_Price[25]),  R.drawable.store_shellfish9, R.drawable.button_store_probability, 1));

        info_Price[26] = 1000;
        for(int i=0; i<info[26]-1; i++){
            info_Price[26] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st10", info[26], (info_Price[26]),  R.drawable.store_shellfish10, R.drawable.button_store_probability, 1));


//        adapter.addItem(new Activity_Store_Item("mt1", "오징어1",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear3_text));
//        adapter.addItem(new Activity_Store_Item("mt2", "오징어2",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_fish_tear2_text));
        info_Price[29] = 300;
        for(int i=0; i<info[29]-1; i++){
            info_Price[29] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt3",  info[29], (info_Price[29]),  R.drawable.store_mollusc3, R.drawable.button_store_probability, 1));

        info_Price[30] = 400;
        for(int i=0; i<info[30]-1; i++){
            info_Price[30] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt4",  info[30] , (info_Price[30]),  R.drawable.store_mollusc4, R.drawable.button_store_probability, 1));

        info_Price[31] = 500;
        for(int i=0; i<info[31]-1; i++){
            info_Price[31] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt5",  info[31] , (info_Price[31]),  R.drawable.store_mollusc5, R.drawable.button_store_probability, 1));

        info_Price[32] = 600;
        for(int i=0; i<info[32]-1; i++){
            info_Price[32] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt6",  info[32] , (info_Price[32]),  R.drawable.store_mollusc6, R.drawable.button_store_probability, 1));

        info_Price[33] = 700;
        for(int i=0; i<info[33]-1; i++){
            info_Price[33] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt7", info[33] , (info_Price[33]),  R.drawable.store_mollusc7, R.drawable.button_store_probability, 1));

        info_Price[34] = 800;
        for(int i=0; i<info[34]-1; i++){
            info_Price[34] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt8",  info[34] ,(info_Price[34]),  R.drawable.store_mollusc8, R.drawable.button_store_probability, 1));

        info_Price[35] = 900;
        for(int i=0; i<info[35]-1; i++){
            info_Price[35] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt9",  info[35] , (info_Price[35]),  R.drawable.store_mollusc9, R.drawable.button_store_probability, 1));

        info_Price[36] = 1000;
        for(int i=0; i<info[36]-1; i++){
            info_Price[36] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt10",  info[36] , (info_Price[36]),  R.drawable.store_mollusc10, R.drawable.button_store_probability, 1));






        listView.setAdapter(adapter);


        //메모리 관리
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.store_View);
        layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_start_black));




//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = getIntent();
        intent.putExtra("num", 1);
        setResult(RESULT_OK, intent);


//        recycleView(findViewById(R.id.store_View));
//        recycleView(findViewById(R.id.button_buy));
//        recycleView(findViewById(R.id.imageView));
//        recycleView(findViewById(R.id.textView));
//        recycleView(findViewById(R.id.textView3));
//        recycleView(findViewById(R.id.textView));



        finish();
    }

    private void recycleView(View view) {
        if(view != null) {
            Drawable bg = view.getBackground();
            if(bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable)bg).getBitmap().recycle();
                view.setBackgroundDrawable(null);
                Log.e("a", "A");
            }
        }
    }


    //데이터 관리
    class Item_Adapter extends BaseAdapter{

        ArrayList<Activity_Store_Item> items = new ArrayList<Activity_Store_Item>();
        Activity_Store_Item item;
        String temp;

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Activity_Store_Item item){
            items.add(item);
        }


        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {



                Activity_Store_Item_View view = null;
            if(convertView == null){
                view = new Activity_Store_Item_View(getApplicationContext());
            }else {
                view = (Activity_Store_Item_View) convertView;
            }


                Activity_Store_Item item = items.get(position);

                store_Item_View_TextView[position] =  view.get_TextView();
                store_Item_View_TextLevel[position] = view.get_TextLevel();
//이미지
                view.setImage(item.getResId(), item.getResId2());
//                view.setName(item.getName().toString());
            if(position != 0) {
                view.setTextLever(df.format(item.getLever()));
                view.setText(df.format(item.getCost()));
            }else{
                view.setTextLever("");
                view.setText(f.format(Double.valueOf(String.format("%.0f",item.getCost())).doubleValue()));
            }

            //                     "Lv: " + info[8] + ", 가격 : "

                view.get_TextView_Button().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Activity_Store_Item item =  (Activity_Store_Item)adapter.getItem(position);
                 if(item.getName() == "ruby" && 10000000 <= info[1]){
//                     Toast.makeText(getApplicationContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();
                     info[0] += 1;
                     info[1] -= 10000000;

                     money.setText("x " + df.format(info[1]));
                     ruby.setText("x " + df.format(info[0]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', ruby = '"+ info[0] +"'";
                     database.execSQL(sql);

                 }else if(item.getName() == "structure_damage" && info_Price[2] <= info[1]){

                     info[1] -= info_Price[2];  //돈
                     info[2]++;         //기술 레벨

                     info_Price[2]*=1.01;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[2]);
                     item.setLever();

//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
//                     f.format(Double.valueOf(String.format("%.0f",info_Price[2])).doubleValue()

//                     store_Item_View_TextView[1].setText("Lv: " + df.format(info[2])+ ", 가격 : " + df.format(info_Price[2]));
                     store_Item_View_TextLevel[1].setText(df.format(info[2]));
                     store_Item_View_TextView[1].setText(df.format(info_Price[2]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', structuredamage = '"+ info[2] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "drag_damage" && info_Price[3] <= info[1]){


                     info[1] -= info_Price[3];  //돈
                     info[3]++;         //기술 레벨

                     info_Price[3]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[3]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
//                     store_Item_View_TextView[2].setText("Lv: " + info[3]+ ", 가격 : " + f.format(Double.valueOf(String.format("%.0f",info_Price[3])).doubleValue()));
                     store_Item_View_TextLevel[2].setText(df.format(info[3]));
                     store_Item_View_TextView[2].setText(df.format(info_Price[3]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', dragdamage = '"+ info[3] +"'";
                     database.execSQL(sql);


                 }else if(item.getName() == "urchin_resistance" && info_Price[4] <= info[1]){

                     info[1] -= info_Price[4];  //돈
                     info[4]++;         //기술 레벨

                     info_Price[4]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[4]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[3].setText(df.format(info[4]));
                     store_Item_View_TextView[3].setText(df.format(info_Price[4]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', urchinresistance = '"+ info[4] +"'";
                     database.execSQL(sql);

                 }else if(item.getName() == "lightning_resistance" && info_Price[5] <= info[1]){

                     info[1] -= info_Price[5];  //돈
                     info[5]++;         //기술 레벨

                     info_Price[5]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[5]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[4].setText(df.format(info[5]));
                     store_Item_View_TextView[4].setText(df.format(info_Price[5]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', lightningresistance = '"+ info[5] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "crocodile_resistance" && info_Price[6] <= info[1]){

                     info[1] -= info_Price[6];  //돈
                     info[6]++;         //기술 레벨

                     info_Price[6]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[6]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[5].setText(df.format(info[6]));
                     store_Item_View_TextView[5].setText(df.format(info_Price[6]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', crocodileresistance = '"+ info[6] +"'";
                     database.execSQL(sql);


                }else if(item.getName() == "ft1"){


                }else if(item.getName() == "ft2"  && info_Price[8] <= info[1]){

                     info[1] -= info_Price[8];  //돈
                     info[8]++;         //기술 레벨

                     info_Price[8]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[8]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[6].setText(df.format(info[8]));
                     store_Item_View_TextView[6].setText(df.format(info_Price[8]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', ft2 = '"+ info[8] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "ft3"  && info_Price[9] <= info[1]){

                     info[1] -= info_Price[9];  //돈
                     info[9]++;         //기술 레벨

                     info_Price[9]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[9]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[7].setText(df.format(info[9]));
                     store_Item_View_TextView[7].setText(df.format(info_Price[9]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', ft3 = '"+ info[9] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "ft4"  && info_Price[10] <= info[1]){

                     info[1] -= info_Price[10];  //돈
                     info[10]++;         //기술 레벨

                     info_Price[10]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[10]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[8].setText(df.format(info[10]));
                     store_Item_View_TextView[8].setText(df.format(info_Price[10]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', ft4 = '"+ info[10] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "ft5"  && info_Price[11] <= info[1]){

                     info[1] -= info_Price[11];  //돈
                     info[11]++;         //기술 레벨

                     info_Price[11]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[11]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[9].setText(df.format(info[11]));
                     store_Item_View_TextView[9].setText(df.format(info_Price[11]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', ft5 = '"+ info[11] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "ft6"  && info_Price[12] <= info[1]){

                     info[1] -= info_Price[12];  //돈
                     info[12]++;         //기술 레벨

                     info_Price[12]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[12]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[10].setText(df.format(info[12]));
                     store_Item_View_TextView[10].setText(df.format(info_Price[12]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', ft6 = '"+ info[12] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "ft7"  && info_Price[13] <= info[1]){

                     info[1] -= info_Price[13];  //돈
                     info[13]++;         //기술 레벨

                     info_Price[13]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[13]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[11].setText(df.format(info[13]));
                     store_Item_View_TextView[11].setText(df.format(info_Price[13]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', ft7 = '"+ info[13] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "ft8"  && info_Price[14] <= info[1]){

                     info[1] -= info_Price[14];  //돈
                     info[14]++;         //기술 레벨

                     info_Price[14]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[14]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[12].setText(df.format(info[14]));
                     store_Item_View_TextView[12].setText(df.format(info_Price[14]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', ft8 = '"+ info[14] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "ft9"  && info_Price[15] <= info[1]){

                     info[1] -= info_Price[15];  //돈
                     info[15]++;         //기술 레벨

                     info_Price[15]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[15]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[13].setText(df.format(info[15]));
                     store_Item_View_TextView[13].setText(df.format(info_Price[15]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', ft9 = '"+ info[15] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "ft10"  && info_Price[16] <= info[1]){

                     info[1] -= info_Price[16];  //돈
                     info[16]++;         //기술 레벨

                     info_Price[16]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[16]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[14].setText(df.format(info[16]));
                     store_Item_View_TextView[14].setText(df.format(info_Price[16]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', ft10 = '"+ info[16] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "st1"){

                }else if(item.getName() == "st2"  && info_Price[18] <= info[1]){

                     info[1] -= info_Price[18];  //돈
                     info[18]++;         //기술 레벨

                     info_Price[18]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[18]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[15].setText(df.format(info[18]));
                     store_Item_View_TextView[15].setText(df.format(info_Price[18]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', st2 = '"+ info[18] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "st3"  && info_Price[19] <= info[1]){

                     info[1] -= info_Price[19];  //돈
                     info[19]++;         //기술 레벨

                     info_Price[19]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[19]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[16].setText(df.format(info[19]));
                     store_Item_View_TextView[16].setText(df.format(info_Price[19]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', st3 = '"+ info[19] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "st4"  && info_Price[20] <= info[1]){

                     info[1] -= info_Price[20];  //돈
                     info[20]++;         //기술 레벨

                     info_Price[20]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[20]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[17].setText(df.format(info[20]));
                     store_Item_View_TextView[17].setText(df.format(info_Price[20]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', st4 = '"+ info[20] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "st5"  && info_Price[21] <= info[1]){

                     info[1] -= info_Price[21];  //돈
                     info[21]++;         //기술 레벨

                     info_Price[21]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[21]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[18].setText(df.format(info[21]));
                     store_Item_View_TextView[18].setText(df.format(info_Price[21]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', st5 = '"+ info[21] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "st6"  && info_Price[22] <= info[1]){

                     info[1] -= info_Price[22];  //돈
                     info[22]++;         //기술 레벨

                     info_Price[22]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[22]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[19].setText(df.format(info[22]));
                     store_Item_View_TextView[19].setText(df.format(info_Price[22]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', st6 = '"+ info[22] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "st7" && info_Price[23] <= info[1]){

                     info[1] -= info_Price[23];  //돈
                     info[23]++;         //기술 레벨

                     info_Price[23]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[23]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[20].setText(df.format(info[23]));
                     store_Item_View_TextView[20].setText(df.format(info_Price[23]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', st7 = '"+ info[23] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "st8" && info_Price[24] <= info[1]){

                     info[1] -= info_Price[24];  //돈
                     info[24]++;         //기술 레벨

                     info_Price[24]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[24]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[21].setText(df.format(info[24]));
                     store_Item_View_TextView[21].setText(df.format(info_Price[24]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', st8 = '"+ info[24] +"'";
                     database.execSQL(sql);

                 }else if(item.getName() == "st9" && info_Price[25] <= info[1]){

                     info[1] -= info_Price[25];  //돈
                     info[25]++;         //기술 레벨

                     info_Price[25]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[25]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[22].setText(df.format(info[25]));
                     store_Item_View_TextView[22].setText(df.format(info_Price[25]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', st9 = '"+ info[25] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "st10" && info_Price[26] <= info[1]){

                     info[1] -= info_Price[26];  //돈
                     info[26]++;         //기술 레벨

                     info_Price[26] *=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[26]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[23].setText(df.format(info[26]));
                     store_Item_View_TextView[23].setText(df.format(info_Price[26]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', st10 = '"+ info[26] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "mt1"){

                }else if(item.getName() == "mt2"){

                }else if(item.getName() == "mt3" && info_Price[29] <= info[1]){

                     info[1] -= info_Price[29];  //돈
                     info[29]++;         //기술 레벨

                     info_Price[29] *=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[29]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[24].setText(df.format(info[29]));
                     store_Item_View_TextView[24].setText(df.format(info_Price[29]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', mt3 = '"+ info[29] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "mt4" && info_Price[30] <= info[1]){

                     info[1] -= info_Price[30];  //돈
                     info[30]++;         //기술 레벨

                     info_Price[30]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[30]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[25].setText(df.format(info[30]));
                     store_Item_View_TextView[25].setText(df.format(info_Price[30]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', mt4 = '"+ info[30] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "mt5" && info_Price[31] <= info[1]){

                     info[1] -= info_Price[31];  //돈
                     info[31]++;         //기술 레벨

                     info_Price[31]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[31]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[26].setText(df.format(info[31]));
                     store_Item_View_TextView[26].setText(df.format(info_Price[31]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', mt5 = '"+ info[31] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "mt6" && info_Price[32] <= info[1]){

                     info[1] -= info_Price[32];  //돈
                     info[32]++;         //기술 레벨

                     info_Price[32]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[32]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[27].setText(df.format(info[32]));
                     store_Item_View_TextView[27].setText(df.format(info_Price[32]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', mt6 = '"+ info[32] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "mt7" && info_Price[33] <= info[1]){

                     info[1] -= info_Price[33];  //돈
                     info[33]++;         //기술 레벨

                     info_Price[33]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[33]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[28].setText(df.format(info[33]));
                     store_Item_View_TextView[28].setText(df.format(info_Price[33]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', mt7 = '"+ info[33] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "mt8" && info_Price[34] <= info[1]){

                     info[1] -= info_Price[34];  //돈
                     info[34]++;         //기술 레벨

                     info_Price[34]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[34]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[29].setText(df.format(info[34]));
                     store_Item_View_TextView[29].setText(df.format(info_Price[34]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', mt8 = '"+ info[34] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "mt9" && info_Price[35] <= info[1]){

                     info[1] -= info_Price[35];  //돈
                     info[35]++;         //기술 레벨

                     info_Price[35]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[35]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[30].setText(df.format(info[35]));
                     store_Item_View_TextView[30].setText(df.format(info_Price[35]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', mt9 = '"+ info[35] +"'";
                     database.execSQL(sql);

                }else if(item.getName() == "mt10" && info_Price[36] <= info[1]){
                     info[1] -= info_Price[36];  //돈
                     info[36]++;         //기술 레벨

                     info_Price[36]*=2;

                     money.setText("x "+ df.format(info[1])); //돈 표시
                     item.setCost(info_Price[36]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextLevel[31].setText(df.format(info[36]));
                     store_Item_View_TextView[31].setText(df.format(info_Price[36]));

                     String sql = "UPDATE maincharacterinfo SET money = '"+ info[1] +"', mt10 = '"+ info[36] +"'";
                     database.execSQL(sql);
                }

                    }
                });

            //아랫줄을 통해 상점 아이템 즉시 갱신
            adapter.notifyDataSetChanged();


//                if(){
//                    recycleView(findViewById(R.id.activity_main));
//                }


//                Log.e("e", "eee" + position);

            item =  (Activity_Store_Item)adapter.getItem(position);


            return view;
        }
    }






}
