package com.example.user.ocean_story;

import android.app.Activity;
import android.content.Intent;
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

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Lee on 2017-07-31.
 */

public class Activity_Store extends Activity {
    Item_Adapter adapter;


    int info [] = new int[50];
    double info_Price[] = new double[50];

    ListView listView;

    TextView money;
    TextView ruby;
    TextView store_Item_View_TextView[] = new TextView[50];
    NumberFormat f;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        f = NumberFormat.getInstance();

        Intent intent = getIntent();
        info = intent.getIntArrayExtra("info");

        money = (TextView) findViewById(R.id.textView12);
        ruby = (TextView) findViewById(R.id.textView13);

        money.setText("x " + info[1]);
        ruby.setText("x " + info[0]);

        listView = (ListView) findViewById(R.id.listView);

        adapter = new Item_Adapter();
//        adapter.addItem(new Activity_Store_Item("dragdamage", "드래그 대미지 증가"));
//        adapter.addItem(new Activity_Store_Item("score", "점수"));
//        adapter.addItem(new Activity_Store_Item("money", "돈"));
        adapter.addItem(new Activity_Store_Item("ruby", 0, 10000, R.drawable.store_ruby, R.drawable.button_store_buy, R.drawable.store_ruby_text));

        info_Price[2] = 10 * info[2];
        info_Price[2] = Double.valueOf(String.format("%.0f",10 * info_Price[2])).doubleValue();

        adapter.addItem(new Activity_Store_Item("structure_damage", info[2] , info_Price[2] , R.drawable.store_structure_damage, R.drawable.button_store_buy, R.drawable.store_structure_damage_text));

        info_Price[3] = 500;
        for(int i=0; i<info[3]-1; i++){
            info_Price[3] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("drag_damage",  info[3] , info_Price[3], R.drawable.store_drag_damage, R.drawable.button_store_buy, R.drawable.store_drag_damage_text));

        info_Price[4] = 500;
        for(int i=0; i<info[4]-1; i++){
            info_Price[4] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("urchin_resistance", info[4] , info_Price[4], R.drawable.store_urchin_resistance, R.drawable.button_store_buy, R.drawable.store_urchin_resistance_text));

        info_Price[5] = 500;
        for(int i=0; i<info[5]-1; i++){
            info_Price[5] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("lightning_resistance",  info[5] , info_Price[5], R.drawable.store_lightning_resistance, R.drawable.button_store_buy, R.drawable.store_lightning_resistance_text));

        info_Price[6] = 500;
        for(int i=0; i<info[6]-1; i++){
            info_Price[6] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("crocodile_resistance", info[6] ,info_Price[6], R.drawable.store_crocodile_resistance, R.drawable.button_store_buy, R.drawable.store_crocodile_resistance_text));



//        adapter.addItem(new Activity_Store_Item("ft1", "물고기1"));
        info_Price[8] = 200;
        for(int i=0; i<info[8]-1; i++){
            info_Price[8] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft2", info[8] , info_Price[8], R.drawable.skill_thorn_3, R.drawable.button_store_probability, R.drawable.store_fish_tear2_text));

        info_Price[9] = 300;
        for(int i=0; i<info[9]-1; i++){
            info_Price[9] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft3", info[9] , info_Price[9],  R.drawable.skill_teeth_mine_1, R.drawable.button_store_probability, R.drawable.store_fish_tear3_text));

        info_Price[10] = 400;
        for(int i=0; i<info[10]-1; i++){
            info_Price[10] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft4", info[10], info_Price[10],  R.drawable.skill_earthquake_4, R.drawable.button_store_probability, R.drawable.store_fish_tear4_text));

        info_Price[11] = 500;
        for(int i=0; i<info[11]-1; i++){
            info_Price[11] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft5",  info[11] , info_Price[11],  R.drawable.skill_teeth_mine2_1, R.drawable.button_store_probability, R.drawable.store_fish_tear5_text));

        info_Price[12] = 600;
        for(int i=0; i<info[12]-1; i++){
            info_Price[12] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft6",  info[12] , info_Price[12],  R.drawable.effect_poison_1, R.drawable.button_store_probability, R.drawable.store_fish_tear6_text));

        info_Price[13] = 700;
        for(int i=0; i<info[13]-1; i++){
            info_Price[13] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft7", info[13] , info_Price[13],  R.drawable.skill_lightning_1, R.drawable.button_store_probability, R.drawable.store_fish_tear7_text));

        info_Price[14] = 800;
        for(int i=0; i<info[14]-1; i++){
            info_Price[14] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft8", info[14] , info_Price[14],  R.drawable.skill_lightning1_1, R.drawable.button_store_probability, R.drawable.store_fish_tear8_text));

        info_Price[15] = 900;
        for(int i=0; i<info[15]-1; i++){
            info_Price[15] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft9",  info[15] , info_Price[15],  R.drawable.skill_sea_snake_1, R.drawable.button_store_probability, R.drawable.store_fish_tear9_text));

        info_Price[16] = 1000;
        for(int i=0; i<info[16]-1; i++){
            info_Price[16] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("ft10",  info[16], info_Price[16],  R.drawable.skill_thorn_4, R.drawable.button_store_probability, R.drawable.store_fish_tear10_text));

//        adapter.addItem(new Activity_Store_Item("st1", "꽃게1",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear2_text));
        info_Price[18] = 200;
        for(int i=0; i<info[18]-1; i++){
            info_Price[18] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st2", info[18] , info_Price[18],  R.drawable.effect_slow_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear2_text));

        info_Price[19] = 200;
        for(int i=0; i<info[19]-1; i++){
            info_Price[19] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st3", info[19] , info_Price[19],   R.drawable.store_urchin_resistance, R.drawable.button_store_buy, R.drawable.store_urchin_resistance_text));

//        adapter.addItem(new Activity_Store_Item("st3", "꽃게3",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear3_text));
        info_Price[20] = 400;
        for(int i=0; i<info[20]-1; i++){
            info_Price[20] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st4", info[20] , info_Price[20],  R.drawable.skill_crab_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear4_text));

        info_Price[21] = 500;
        for(int i=0; i<info[21]-1; i++){
            info_Price[21] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st5", info[21] , info_Price[21],  R.drawable.skill_soycrab_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear5_text));

        info_Price[22] = 600;
        for(int i=0; i<info[22]-1; i++){
            info_Price[22] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st6", info[22] , info_Price[22],  R.drawable.skill_thorn2_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear6_text));

        info_Price[23] = 700;
        for(int i=0; i<info[23]-1; i++){
            info_Price[23] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st7",  info[23] , info_Price[23],  R.drawable.skill_fry_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear7_text));

        info_Price[24] = 800;
        for(int i=0; i<info[24]-1; i++){
            info_Price[24] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st8",  info[24] , info_Price[24],  R.drawable.skill_crab_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear8_text));

        info_Price[25] = 900;
        for(int i=0; i<info[25]-1; i++){
            info_Price[25] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st9",  info[25] , info_Price[25],  R.drawable.skill_wave_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear9_text));

        info_Price[26] = 1000;
        for(int i=0; i<info[26]-1; i++){
            info_Price[26] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("st10", info[26], info_Price[26],  R.drawable.skill_stomp_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear10_text));


//        adapter.addItem(new Activity_Store_Item("mt1", "오징어1",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear3_text));
//        adapter.addItem(new Activity_Store_Item("mt2", "오징어2",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_fish_tear2_text));
        info_Price[29] = 300;
        for(int i=0; i<info[29]-1; i++){
            info_Price[29] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt3",  info[29], info_Price[29],  R.drawable.skill_slow_cloud_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear3_text));

        info_Price[30] = 400;
        for(int i=0; i<info[30]-1; i++){
            info_Price[30] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt4",  info[30] , info_Price[30],  R.drawable.skill_butter_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear4_text));

        info_Price[31] = 500;
        for(int i=0; i<info[31]-1; i++){
            info_Price[31] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt5",  info[31] , info_Price[31],  R.drawable.skill_fork_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear5_text));

        info_Price[32] = 600;
        for(int i=0; i<info[32]-1; i++){
            info_Price[32] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt6",  info[32] , info_Price[32],  R.drawable.skill_laser_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear6_text));

        info_Price[33] = 700;
        for(int i=0; i<info[33]-1; i++){
            info_Price[33] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt7", info[33] , info_Price[33],  R.drawable.effect_poison_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear7_text));

        info_Price[34] = 800;
        for(int i=0; i<info[34]-1; i++){
            info_Price[34] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt8",  info[34] , info_Price[34],  R.drawable.skill_boom_poison_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear8_text));

        info_Price[35] = 900;
        for(int i=0; i<info[35]-1; i++){
            info_Price[35] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt9",  info[35] , info_Price[35],  R.drawable.skill_wall_3, R.drawable.button_store_probability, R.drawable.store_moulluse_tear9_text));

        info_Price[36] = 1000;
        for(int i=0; i<info[36]-1; i++){
            info_Price[36] *= 2;
        }
        adapter.addItem(new Activity_Store_Item("mt10",  info[36] , info_Price[36],  R.drawable.skill_posion_cloud1_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear10_text));






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
//이미지
                view.setImage(item.getResId(), item.getResId2(), item.getResId3());
//                view.setName(item.getName().toString());
            if(position != 0) {
                view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
            }else{
                view.setText("가격 : " + item.getCost());
            }

            //                     "Lv: " + info[8] + ", 가격 : "

                view.get_TextView_Button().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Activity_Store_Item item =  (Activity_Store_Item)adapter.getItem(position);
                 if(item.getName() == "ruby"){
//                     Toast.makeText(getApplicationContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();
                     info[0] += 1;
                     info[1] -= 10000;

                     money.setText("x " + info[1]);
                     ruby.setText("x " + info[0]);


                 }else if(item.getName() == "structure_damage"){


                     info[1] -= info_Price[2];  //돈
                     info[2]++;         //기술 레벨

                     info_Price[2]+=10;

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[2]);
                     item.setLever();

//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[1].setText("Lv: " + info[2]+ ", 가격 : " + info_Price[2] );

                }else if(item.getName() == "drag_damage"){


                     info[1] -= info_Price[3];  //돈
                     info[3]++;         //기술 레벨

                     info_Price[3]*=2;
                     info_Price[3] = Double.valueOf(String.format("%.0f",info_Price[3])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[3]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[2].setText("Lv: " + info[3]+ ", 가격 : " + f.format(info_Price[3]));

                 }else if(item.getName() == "urchin_resistance"){

                     info[1] -= info_Price[4];  //돈
                     info[4]++;         //기술 레벨

                     info_Price[4]*=2;
                     info_Price[4] = Double.valueOf(String.format("%.0f",info_Price[4])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[4]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[3].setText("Lv: " + info[4]+ ", 가격 : " + f.format(info_Price[4]));

                 }else if(item.getName() == "lightning_resistance"){

                     info[1] -= info_Price[5];  //돈
                     info[5]++;         //기술 레벨

                     info_Price[5]*=2;
                     info_Price[5] = Double.valueOf(String.format("%.0f",info_Price[5])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[5]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[4].setText("Lv: " + info[5]+ ", 가격 : " + f.format(info_Price[5]));

                }else if(item.getName() == "crocodile_resistance"){

                     info[1] -= info_Price[6];  //돈
                     info[6]++;         //기술 레벨

                     info_Price[6]*=2;
                     info_Price[6] = Double.valueOf(String.format("%.0f",info_Price[6])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[6]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[5].setText("Lv: " + info[6]+ ", 가격 : " + f.format(info_Price[6]));

                }else if(item.getName() == "ft1"){


                }else if(item.getName() == "ft2"){

                     info[1] -= info_Price[8];  //돈
                     info[8]++;         //기술 레벨

                     info_Price[8]*=2;
                     info_Price[8] = Double.valueOf(String.format("%.0f",info_Price[8])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[8]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[6].setText("Lv: " + info[8]+ ", 가격 : " + f.format(info_Price[8]));

                }else if(item.getName() == "ft3"){

                     info[1] -= info_Price[9];  //돈
                     info[9]++;         //기술 레벨

                     info_Price[9]*=2;
                     info_Price[9] = Double.valueOf(String.format("%.0f",info_Price[9])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[9]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[7].setText("Lv: " + info[9]+ ", 가격 : " + f.format(info_Price[9]));

                }else if(item.getName() == "ft4"){

                     info[1] -= info_Price[10];  //돈
                     info[10]++;         //기술 레벨

                     info_Price[10]*=2;
                     info_Price[10] = Double.valueOf(String.format("%.0f",info_Price[10])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[10]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[8].setText("Lv: " + info[10]+ ", 가격 : " + f.format(info_Price[10]));

                }else if(item.getName() == "ft5"){

                     info[1] -= info_Price[11];  //돈
                     info[11]++;         //기술 레벨

                     info_Price[11]*=2;
                     info_Price[11] = Double.valueOf(String.format("%.0f",info_Price[11])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[11]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[9].setText("Lv: " + info[11]+ ", 가격 : " + f.format(info_Price[11]));

                }else if(item.getName() == "ft6"){

                     info[1] -= info_Price[12];  //돈
                     info[12]++;         //기술 레벨

                     info_Price[12]*=2;
                     info_Price[12] = Double.valueOf(String.format("%.0f",info_Price[12])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[12]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[10].setText("Lv: " + info[12]+ ", 가격 : " + f.format(info_Price[12]));

                }else if(item.getName() == "ft7"){

                     info[1] -= info_Price[13];  //돈
                     info[13]++;         //기술 레벨

                     info_Price[13]*=2;
                     info_Price[13] = Double.valueOf(String.format("%.0f",info_Price[13])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[13]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[11].setText("Lv: " + info[13]+ ", 가격 : " + f.format(info_Price[13]));

                }else if(item.getName() == "ft8"){

                     info[1] -= info_Price[14];  //돈
                     info[14]++;         //기술 레벨

                     info_Price[14]*=2;
                     info_Price[14] = Double.valueOf(String.format("%.0f",info_Price[14])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[14]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[12].setText("Lv: " + info[14]+ ", 가격 : " + f.format(info_Price[14]));

                }else if(item.getName() == "ft9"){

                     info[1] -= info_Price[15];  //돈
                     info[15]++;         //기술 레벨

                     info_Price[15]*=2;
                     info_Price[15] = Double.valueOf(String.format("%.0f",info_Price[15])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[15]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[13].setText("Lv: " + info[15]+ ", 가격 : " + f.format(info_Price[15]));

                }else if(item.getName() == "ft10"){

                     info[1] -= info_Price[16];  //돈
                     info[16]++;         //기술 레벨

                     info_Price[16]*=2;
                     info_Price[16] = Double.valueOf(String.format("%.0f",info_Price[16])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[16]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[14].setText("Lv: " + info[16]+ ", 가격 : " + f.format(info_Price[16]));

                }else if(item.getName() == "st1"){

                }else if(item.getName() == "st2"){

                     info[1] -= info_Price[18];  //돈
                     info[18]++;         //기술 레벨

                     info_Price[18]*=2;
                     info_Price[18] = Double.valueOf(String.format("%.0f",info_Price[18])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[18]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[15].setText("Lv: " + info[18]+ ", 가격 : " + f.format(info_Price[18]));

                }else if(item.getName() == "st3"){

                     info[1] -= info_Price[19];  //돈
                     info[19]++;         //기술 레벨

                     info_Price[19]*=2;
                     info_Price[19] = Double.valueOf(String.format("%.0f",info_Price[19])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[19]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[16].setText("Lv: " + info[19]+ ", 가격 : " + f.format(info_Price[19]));

                }else if(item.getName() == "st4"){

                     info[1] -= info_Price[20];  //돈
                     info[20]++;         //기술 레벨

                     info_Price[20]*=2;
                     info_Price[20] = Double.valueOf(String.format("%.0f",info_Price[20])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[20]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[17].setText("Lv: " + info[20]+ ", 가격 : " + f.format(info_Price[20]));

                }else if(item.getName() == "st5"){

                     info[1] -= info_Price[21];  //돈
                     info[21]++;         //기술 레벨

                     info_Price[21]*=2;
                     info_Price[21] = Double.valueOf(String.format("%.0f",info_Price[21])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[21]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[18].setText("Lv: " + info[21]+ ", 가격 : " + f.format(info_Price[21]));

                }else if(item.getName() == "st6"){

                     info[1] -= info_Price[22];  //돈
                     info[22]++;         //기술 레벨

                     info_Price[22]*=2;
                     info_Price[22] = Double.valueOf(String.format("%.0f",info_Price[22])).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[22]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[19].setText("Lv: " + info[22]+ ", 가격 : " + f.format(info_Price[22]));

                }else if(item.getName() == "st7"){

                     info[1] -= info_Price[23];  //돈
                     info[23]++;         //기술 레벨

                     info_Price[23] = Double.valueOf(String.format("%.0f",info_Price[23]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[23]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[20].setText("Lv: " + info[23]+ ", 가격 : " + f.format(info_Price[23]));

                }else if(item.getName() == "st8"){

                     info[1] -= info_Price[24];  //돈
                     info[24]++;         //기술 레벨

                     info_Price[24] = Double.valueOf(String.format("%.0f",info_Price[24]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[24]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[21].setText("Lv: " + info[24]+ ", 가격 : " + f.format(info_Price[24]));


                 }else if(item.getName() == "st9"){

                     info[1] -= info_Price[25];  //돈
                     info[25]++;         //기술 레벨

                     info_Price[25] = Double.valueOf(String.format("%.0f",info_Price[25]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[25]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[22].setText("Lv: " + info[25]+ ", 가격 : " + f.format(info_Price[25]));

                }else if(item.getName() == "st10"){

                     info[1] -= info_Price[26];  //돈
                     info[26]++;         //기술 레벨

                     info_Price[26] = Double.valueOf(String.format("%.0f",info_Price[26]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[26]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[23].setText("Lv: " + info[26]+ ", 가격 : " + f.format(info_Price[26]));

                }else if(item.getName() == "mt1"){

                }else if(item.getName() == "mt2"){

                }else if(item.getName() == "mt3"){

                     info[1] -= info_Price[29];  //돈
                     info[29]++;         //기술 레벨

                     info_Price[29] = Double.valueOf(String.format("%.0f",info_Price[29]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[29]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[24].setText("Lv: " + info[29]+ ", 가격 : " + f.format(info_Price[29]));

                }else if(item.getName() == "mt4"){

                     info[1] -= info_Price[30];  //돈
                     info[30]++;         //기술 레벨

                     info_Price[30] = Double.valueOf(String.format("%.0f",info_Price[30]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[30]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[25].setText("Lv: " + info[30]+ ", 가격 : " + f.format(info_Price[30]));

                }else if(item.getName() == "mt5"){

                     info[1] -= info_Price[31];  //돈
                     info[31]++;         //기술 레벨

                     info_Price[31] = Double.valueOf(String.format("%.0f",info_Price[31]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[31]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[26].setText("Lv: " + info[31]+ ", 가격 : " + f.format(info_Price[31]));

                }else if(item.getName() == "mt6"){

                     info[1] -= info_Price[32];  //돈
                     info[32]++;         //기술 레벨

                     info_Price[32] = Double.valueOf(String.format("%.0f",info_Price[32]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[32]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[27].setText("Lv: " + info[32]+ ", 가격 : " + f.format(info_Price[32]));

                }else if(item.getName() == "mt7"){

                     info[1] -= info_Price[33];  //돈
                     info[33]++;         //기술 레벨

                     info_Price[33] = Double.valueOf(String.format("%.0f",info_Price[33]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[33]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[28].setText("Lv: " + info[33]+ ", 가격 : " + f.format(info_Price[33]));

                }else if(item.getName() == "mt8"){

                     info[1] -= info_Price[34];  //돈
                     info[34]++;         //기술 레벨

                     info_Price[34] = Double.valueOf(String.format("%.0f",info_Price[34]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[34]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[29].setText("Lv: " + info[34]+ ", 가격 : " + f.format(info_Price[34]));

                }else if(item.getName() == "mt9"){

                     info[1] -= info_Price[35];  //돈
                     info[35]++;         //기술 레벨

                     info_Price[35] = Double.valueOf(String.format("%.0f",info_Price[35]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[35]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[30].setText("Lv: " + info[35]+ ", 가격 : " + f.format(info_Price[35]));

                }else if(item.getName() == "mt10"){
                     info[1] -= info_Price[36];  //돈
                     info[36]++;         //기술 레벨

                     info_Price[36] = Double.valueOf(String.format("%.0f",info_Price[36]*2)).doubleValue();

                     money.setText("x "+ info[1]); //돈 표시
                     item.setCost(info[36]);
                     item.setLever();
                     f.setGroupingUsed(false);
//                     view.setText("Lv: " + item.getLever() + ", 가격 : " + item.getCost());
                     store_Item_View_TextView[31].setText("Lv: " + info[36]+ ", 가격 : " + f.format(info_Price[36]));
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
