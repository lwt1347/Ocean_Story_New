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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lee on 2017-07-31.
 */

public class Activity_Store extends Activity {
    Item_Adapter adapter;

    int structuredamage;
    int dragdamage;
    int score;
    int money;
    int ruby;
    int ft1;
    int ft2;
    int ft3;
    int ft4;
    int ft5;
    int ft6;
    int ft7;
    int ft8;
    int ft9;
    int ft10;
    int st1;
    int st2;
    int st3;
    int st4;
    int st5;
    int st6;
    int st7;
    int st8;
    int st9;
    int st10;
    int mt1;
    int mt2;
    int mt3;
    int mt4;
    int mt5;
    int mt6;
    int mt7;
    int mt8;
    int mt9;
    int mt10;
    int info [] = new int[50];
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);


        Intent intent = getIntent();
        info = intent.getIntArrayExtra("info");


        listView = (ListView) findViewById(R.id.listView);

        adapter = new Item_Adapter();
//        adapter.addItem(new Activity_Store_Item("dragdamage", "드래그 대미지 증가"));
//        adapter.addItem(new Activity_Store_Item("score", "점수"));
//        adapter.addItem(new Activity_Store_Item("money", "돈"));
        adapter.addItem(new Activity_Store_Item("ruby", "1000", R.drawable.store_ruby, R.drawable.button_store_buy, R.drawable.store_ruby_text));
        adapter.addItem(new Activity_Store_Item("structure_damage", "Lv: " + info[0] + ", 가격 : " + info[0] * 500 , R.drawable.store_structure_damage, R.drawable.button_store_buy, R.drawable.store_structure_damage_text));
        adapter.addItem(new Activity_Store_Item("drag_damage", "Lv: " + info[3] + ", 가격 : " + info[3] * 500, R.drawable.store_drag_damage, R.drawable.button_store_buy, R.drawable.store_drag_damage_text));
        adapter.addItem(new Activity_Store_Item("urchin_resistance", "Lv: " + info[4] + ", 가격 : " + info[4] * 500, R.drawable.store_urchin_resistance, R.drawable.button_store_buy, R.drawable.store_urchin_resistance_text));
        adapter.addItem(new Activity_Store_Item("lightning_resistance", "Lv: " + info[5] + ", 가격 : " + info[5] * 500, R.drawable.store_lightning_resistance, R.drawable.button_store_buy, R.drawable.store_lightning_resistance_text));
        adapter.addItem(new Activity_Store_Item("crocodile_resistance", "Lv: " + info[6] + ", 가격 : " + info[6] * 500, R.drawable.store_crocodile_resistance, R.drawable.button_store_buy, R.drawable.store_crocodile_resistance_text));



//        adapter.addItem(new Activity_Store_Item("ft1", "물고기1"));
        adapter.addItem(new Activity_Store_Item("ft2", "Lv: " + info[8] + ", 가격 : " + info[8] * 200, R.drawable.skill_thorn_3, R.drawable.button_store_probability, R.drawable.store_fish_tear2_text));

        adapter.addItem(new Activity_Store_Item("ft3", "Lv: " + info[9] + ", 가격 : " + info[9] * 300,  R.drawable.skill_teeth_mine_1, R.drawable.button_store_probability, R.drawable.store_fish_tear3_text));
        adapter.addItem(new Activity_Store_Item("ft4", "Lv: " + info[10] + ", 가격 : " + info[10] * 400,  R.drawable.skill_earthquake_4, R.drawable.button_store_probability, R.drawable.store_fish_tear4_text));
        adapter.addItem(new Activity_Store_Item("ft5", "Lv: " + info[11] + ", 가격 : " + info[11] * 500,  R.drawable.skill_teeth_mine2_1, R.drawable.button_store_probability, R.drawable.store_fish_tear5_text));
        adapter.addItem(new Activity_Store_Item("ft6", "Lv: " + info[12] + ", 가격 : " + info[12] * 600,  R.drawable.effect_poison_1, R.drawable.button_store_probability, R.drawable.store_fish_tear6_text));
        adapter.addItem(new Activity_Store_Item("ft7", "Lv: " + info[13] + ", 가격 : " + info[13] * 700,  R.drawable.skill_lightning_1, R.drawable.button_store_probability, R.drawable.store_fish_tear7_text));
        adapter.addItem(new Activity_Store_Item("ft8", "Lv: " + info[14] + ", 가격 : " + info[14] * 800,  R.drawable.skill_lightning1_1, R.drawable.button_store_probability, R.drawable.store_fish_tear8_text));
        adapter.addItem(new Activity_Store_Item("ft9", "Lv: " + info[15] + ", 가격 : " + info[15] * 900,  R.drawable.skill_sea_snake_1, R.drawable.button_store_probability, R.drawable.store_fish_tear9_text));
        adapter.addItem(new Activity_Store_Item("ft10", "Lv: " + info[16] + ", 가격 : " + info[16] * 1000,  R.drawable.skill_thorn_4, R.drawable.button_store_probability, R.drawable.store_fish_tear10_text));
//        adapter.addItem(new Activity_Store_Item("st1", "꽃게1",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear2_text));
        adapter.addItem(new Activity_Store_Item("st2", "Lv: " + info[18] + ", 가격 : " + info[18] * 200,  R.drawable.effect_slow_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear2_text));
//        adapter.addItem(new Activity_Store_Item("st3", "꽃게3",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear3_text));
        adapter.addItem(new Activity_Store_Item("st4", "Lv: " + info[20] + ", 가격 : " + info[20] * 400,  R.drawable.skill_crab_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear4_text));
        adapter.addItem(new Activity_Store_Item("st5", "Lv: " + info[21] + ", 가격 : " + info[21] * 500,  R.drawable.skill_soycrab_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear5_text));
        adapter.addItem(new Activity_Store_Item("st6", "Lv: " + info[22] + ", 가격 : " + info[22] * 600,  R.drawable.skill_thorn2_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear6_text));
        adapter.addItem(new Activity_Store_Item("st7", "Lv: " + info[23] + ", 가격 : " + info[23] * 700,  R.drawable.skill_fry_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear7_text));
        adapter.addItem(new Activity_Store_Item("st8", "Lv: " + info[24] + ", 가격 : " + info[24] * 800,  R.drawable.skill_crab_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear8_text));
        adapter.addItem(new Activity_Store_Item("st9", "Lv: " + info[25] + ", 가격 : " + info[25] * 900,  R.drawable.skill_wave_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear9_text));
        adapter.addItem(new Activity_Store_Item("st10", "Lv: " + info[26] + ", 가격 : " + info[26] * 1000,  R.drawable.skill_stomp_1, R.drawable.button_store_probability, R.drawable.store_shellfish_tear10_text));
//        adapter.addItem(new Activity_Store_Item("mt1", "오징어1",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear3_text));
//        adapter.addItem(new Activity_Store_Item("mt2", "오징어2",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_fish_tear2_text));
        adapter.addItem(new Activity_Store_Item("mt3", "Lv: " + info[29] + ", 가격 : " + info[29] * 300,  R.drawable.skill_slow_cloud_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear3_text));
        adapter.addItem(new Activity_Store_Item("mt4", "Lv: " + info[30] + ", 가격 : " + info[30] * 400,  R.drawable.skill_butter_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear4_text));
        adapter.addItem(new Activity_Store_Item("mt5", "Lv: " + info[31] + ", 가격 : " + info[31] * 500,  R.drawable.skill_fork_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear5_text));
        adapter.addItem(new Activity_Store_Item("mt6", "Lv: " + info[32] + ", 가격 : " + info[32] * 600,  R.drawable.skill_laser_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear6_text));
        adapter.addItem(new Activity_Store_Item("mt7", "Lv: " + info[33] + ", 가격 : " + info[33] * 700,  R.drawable.effect_poison_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear7_text));
        adapter.addItem(new Activity_Store_Item("mt8", "Lv: " + info[34] + ", 가격 : " + info[34] * 800,  R.drawable.skill_boom_poison_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear8_text));
        adapter.addItem(new Activity_Store_Item("mt9", "Lv: " + info[35] + ", 가격 : " + info[35] * 900,  R.drawable.skill_wall_3, R.drawable.button_store_probability, R.drawable.store_moulluse_tear9_text));
        adapter.addItem(new Activity_Store_Item("mt10", "Lv: " + info[36] + ", 가격 : " + info[36] * 1000,  R.drawable.skill_posion_cloud1_1, R.drawable.button_store_probability, R.drawable.store_moulluse_tear10_text));




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

                view.setName(item.getName());
                view.setCost(item.getCost());

                view.get_TextView_Button().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Activity_Store_Item item =  (Activity_Store_Item)adapter.getItem(position);
                 if(item.getName() == "ruby"){
                     Toast.makeText(getApplicationContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();

                }else if(item.getName() == "structure_damage"){
                     Toast.makeText(getApplicationContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();
                }else if(item.getName() == "urchin_resistance"){
                     Toast.makeText(getApplicationContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();
                }else if(item.getName() == "lightning_resistance"){
                     Toast.makeText(getApplicationContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();
                }else if(item.getName() == "crocodile_resistance"){
                     Toast.makeText(getApplicationContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();
                }else if(item.getName() == "ft1"){
                }else if(item.getName() == "ft2"){
                }else if(item.getName() == "ft3"){
                }else if(item.getName() == "ft4"){
                }else if(item.getName() == "ft5"){
                }else if(item.getName() == "ft6"){
                }else if(item.getName() == "ft7"){
                }else if(item.getName() == "ft8"){
                }else if(item.getName() == "ft9"){
                }else if(item.getName() == "ft10"){
                }else if(item.getName() == "st1"){
                }else if(item.getName() == "st2"){
                }else if(item.getName() == "st3"){
                }else if(item.getName() == "st4"){
                }else if(item.getName() == "st5"){
                }else if(item.getName() == "st6"){
                }else if(item.getName() == "st7"){
                }else if(item.getName() == "st8"){
                }else if(item.getName() == "st9"){
                }else if(item.getName() == "st10"){
                }else if(item.getName() == "mt1"){
                }else if(item.getName() == "mt2"){
                }else if(item.getName() == "mt3"){
                }else if(item.getName() == "mt4"){
                }else if(item.getName() == "mt5"){
                }else if(item.getName() == "mt6"){
                }else if(item.getName() == "mt7"){
                }else if(item.getName() == "mt8"){
                }else if(item.getName() == "mt9"){
                }else if(item.getName() == "mt10"){

                }

                    }
                });


//                if(){
//                    recycleView(findViewById(R.id.activity_main));
//                }

                //이미지
                view.setImage(item.getResId(), item.getResId2(), item.getResId3());
//                Log.e("e", "eee" + position);

            item =  (Activity_Store_Item)adapter.getItem(position);



            return view;
        }
    }






}
