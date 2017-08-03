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
    int info [] = new int[35];
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
        adapter.addItem(new Activity_Store_Item("ruby", "1000", R.drawable.store_ruby, R.drawable.store_buy_button_1, R.drawable.store_ruby_text));
//        adapter.addItem(new Activity_Store_Item("structure_damage", "1000", R.drawable.store_structure_damage, R.drawable.store_buy_button_1, R.drawable.store_structure_damage_text));
//        adapter.addItem(new Activity_Store_Item("drag_damage", "1000", R.drawable.store_drag_damage, R.drawable.store_buy_button_1, R.drawable.store_drag_damage_text));
//        adapter.addItem(new Activity_Store_Item("urchin_resistance", "1000", R.drawable.store_urchin_resistance, R.drawable.store_buy_button_1, R.drawable.store_urchin_resistance_text));
//        adapter.addItem(new Activity_Store_Item("lightning_resistance", "1000", R.drawable.store_lightning_resistance, R.drawable.store_buy_button_1, R.drawable.store_lightning_resistance_text));
//        adapter.addItem(new Activity_Store_Item("crocodile_resistance", "1000", R.drawable.store_crocodile_resistance, R.drawable.store_buy_button_1, R.drawable.store_crocodile_resistance_text));
//
//
//
////        adapter.addItem(new Activity_Store_Item("ft1", "물고기1"));
//        adapter.addItem(new Activity_Store_Item("ft2", "물고기2", R.drawable.skill_thorn_3, R.drawable.store_probability_button_1, R.drawable.store_fish_tear2_text));
//
//        adapter.addItem(new Activity_Store_Item("ft3", "물고기3",  R.drawable.skill_teeth_mine_1, R.drawable.store_probability_button_1, R.drawable.store_fish_tear3_text));
//        adapter.addItem(new Activity_Store_Item("ft4", "물고기4",  R.drawable.skill_earthquake_4, R.drawable.store_probability_button_1, R.drawable.store_fish_tear4_text));
//        adapter.addItem(new Activity_Store_Item("ft5", "물고기5",  R.drawable.skill_teeth_mine2_1, R.drawable.store_probability_button_1, R.drawable.store_fish_tear5_text));
//        adapter.addItem(new Activity_Store_Item("ft6", "물고기6",  R.drawable.effect_poison_1, R.drawable.store_probability_button_1, R.drawable.store_fish_tear6_text));
//        adapter.addItem(new Activity_Store_Item("ft7", "물고기7",  R.drawable.skill_lightning_1, R.drawable.store_probability_button_1, R.drawable.store_fish_tear7_text));
//        adapter.addItem(new Activity_Store_Item("ft8", "물고기8",  R.drawable.skill_lightning1_1, R.drawable.store_probability_button_1, R.drawable.store_fish_tear8_text));
//        adapter.addItem(new Activity_Store_Item("ft9", "물고기9",  R.drawable.skill_sea_snake_1, R.drawable.store_probability_button_1, R.drawable.store_fish_tear9_text));
//        adapter.addItem(new Activity_Store_Item("ft10", "물고기10",  R.drawable.skill_thorn_4, R.drawable.store_probability_button_1, R.drawable.store_fish_tear10_text));
////        adapter.addItem(new Activity_Store_Item("st1", "꽃게1",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear2_text));
//        adapter.addItem(new Activity_Store_Item("st2", "꽃게2",  R.drawable.effect_slow_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear2_text));
////        adapter.addItem(new Activity_Store_Item("st3", "꽃게3",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear3_text));
//        adapter.addItem(new Activity_Store_Item("st4", "꽃게4",  R.drawable.skill_crab_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear4_text));
//        adapter.addItem(new Activity_Store_Item("st5", "꽃게5",  R.drawable.skill_soycrab_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear5_text));
//        adapter.addItem(new Activity_Store_Item("st6", "꽃게6",  R.drawable.skill_thorn2_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear6_text));
//        adapter.addItem(new Activity_Store_Item("st7", "꽃게7",  R.drawable.skill_fry_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear7_text));
//        adapter.addItem(new Activity_Store_Item("st8", "꽃게8",  R.drawable.skill_crab_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear8_text));
//        adapter.addItem(new Activity_Store_Item("st9", "꽃게9",  R.drawable.skill_wave_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear9_text));
//        adapter.addItem(new Activity_Store_Item("st10", "꽃게10",  R.drawable.skill_stomp_1, R.drawable.store_probability_button_1, R.drawable.store_shellfish_tear10_text));
////        adapter.addItem(new Activity_Store_Item("mt1", "오징어1",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear3_text));
////        adapter.addItem(new Activity_Store_Item("mt2", "오징어2",  R.drawable.store_probability_button_1, R.drawable.store_probability_button_1, R.drawable.store_fish_tear2_text));
//        adapter.addItem(new Activity_Store_Item("mt3", "오징어3",  R.drawable.skill_slow_cloud_1, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear3_text));
//        adapter.addItem(new Activity_Store_Item("mt4", "오징어4",  R.drawable.skill_butter_1, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear4_text));
//        adapter.addItem(new Activity_Store_Item("mt5", "오징어5",  R.drawable.skill_fork_1, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear5_text));
//        adapter.addItem(new Activity_Store_Item("mt6", "오징어6",  R.drawable.skill_laser_1, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear6_text));
//        adapter.addItem(new Activity_Store_Item("mt7", "오징어7",  R.drawable.effect_poison_1, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear7_text));
//        adapter.addItem(new Activity_Store_Item("mt8", "오징어8",  R.drawable.skill_boom_poison_1, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear8_text));
//        adapter.addItem(new Activity_Store_Item("mt9", "오징어9",  R.drawable.skill_wall_3, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear9_text));
//        adapter.addItem(new Activity_Store_Item("mt10", "오징어10",  R.drawable.skill_posion_cloud1_1, R.drawable.store_probability_button_1, R.drawable.store_moulluse_tear10_text));




        listView.setAdapter(adapter);


        //메모리 관리
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.store_View);
        layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_start_black));




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e("a","1121");

               Activity_Store_Item item =  (Activity_Store_Item)adapter.getItem(position);

//                Toast.makeText(getApplicationContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();

                if(item.getName() == "ruby"){

                }else if(item.getName() == "1000"){
                }else if(item.getName() == "urchin_resistance"){
                }else if(item.getName() == "lightning_resistance"){
                }else if(item.getName() == "crocodile_resistance"){
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
        public View getView(int position, View convertView, ViewGroup parent) {



                Activity_Store_Item_View view = null;
            if(convertView == null){
                view = new Activity_Store_Item_View(getApplicationContext());
            }else {
                view = (Activity_Store_Item_View) convertView;
            }


                Activity_Store_Item item = items.get(position);

                view.setName(item.getName());
                view.setCost(item.getCost());

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
