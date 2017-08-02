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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);


        Intent intent = getIntent();
        info = intent.getIntArrayExtra("info");


        ListView listView = (ListView) findViewById(R.id.listView);

        adapter = new Item_Adapter();
        adapter.addItem(new Activity_Store_Item("structuredamage", "건물 공격력 증가"));
        adapter.addItem(new Activity_Store_Item("dragdamage", "드래그 대미지 증가"));
        adapter.addItem(new Activity_Store_Item("score", "점수"));
        adapter.addItem(new Activity_Store_Item("money", "돈"));
        adapter.addItem(new Activity_Store_Item("ruby", "루비"));
        adapter.addItem(new Activity_Store_Item("ft1", "물고기1"));
        adapter.addItem(new Activity_Store_Item("ft2", "물고기2"));
        adapter.addItem(new Activity_Store_Item("ft3", "물고기3"));
        adapter.addItem(new Activity_Store_Item("ft4", "물고기4"));
        adapter.addItem(new Activity_Store_Item("ft5", "물고기5"));
        adapter.addItem(new Activity_Store_Item("ft6", "물고기6"));
        adapter.addItem(new Activity_Store_Item("ft7", "물고기7"));
        adapter.addItem(new Activity_Store_Item("ft8", "물고기8"));
        adapter.addItem(new Activity_Store_Item("ft9", "물고기9"));
        adapter.addItem(new Activity_Store_Item("ft10", "물고기10"));
        adapter.addItem(new Activity_Store_Item("st1", "꽃게1"));
        adapter.addItem(new Activity_Store_Item("st2", "꽃게2"));
        adapter.addItem(new Activity_Store_Item("st3", "꽃게3"));
        adapter.addItem(new Activity_Store_Item("st4", "꽃게4"));
        adapter.addItem(new Activity_Store_Item("st5", "꽃게5"));
        adapter.addItem(new Activity_Store_Item("st6", "꽃게6"));
        adapter.addItem(new Activity_Store_Item("st7", "꽃게7"));
        adapter.addItem(new Activity_Store_Item("st8", "꽃게8"));
        adapter.addItem(new Activity_Store_Item("st9", "꽃게9"));
        adapter.addItem(new Activity_Store_Item("st10", "꽃게10"));
        adapter.addItem(new Activity_Store_Item("mt1", "오징어1"));
        adapter.addItem(new Activity_Store_Item("mt2", "오징어2"));
        adapter.addItem(new Activity_Store_Item("mt3", "오징어3"));
        adapter.addItem(new Activity_Store_Item("mt4", "오징어4"));
        adapter.addItem(new Activity_Store_Item("mt5", "오징어5"));
        adapter.addItem(new Activity_Store_Item("mt6", "오징어6"));
        adapter.addItem(new Activity_Store_Item("mt7", "오징어7"));
        adapter.addItem(new Activity_Store_Item("mt8", "오징어8"));
        adapter.addItem(new Activity_Store_Item("mt9", "오징어9"));
        adapter.addItem(new Activity_Store_Item("mt10", "오징어10"));




        listView.setAdapter(adapter);

        //메모리 관리
         RelativeLayout layout = (RelativeLayout)findViewById(R.id.store_View);
         layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_start_black));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Activity_Store_Item item =  (Activity_Store_Item)adapter.getItem(position);


//                Toast.makeText(getApplicationContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();


                if(item.getName() == "structuredamage"){

                }else if(item.getName() == "dragdamage"){

                }else if(item.getName() == "score"){

                }else if(item.getName() == "money"){

                }else if(item.getName() == "ruby"){

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
        recycleView(findViewById(R.id.store_View));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ///////////////////////////////////////// onActivityResult 으로 값이 잘 넘어간다. 그러나 destroy 에서는 안됨, 따로 완료 버튼을 만들어서 확인 하는 방안.
        Intent intent = getIntent();
        intent.putExtra("num", 1);
        setResult(RESULT_OK, intent);

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


            Activity_Store_Item_View view = new Activity_Store_Item_View(getApplicationContext());

            Activity_Store_Item item =  items.get(position);
            view.setName(item.getName());
            view.setCost(item.getCost());


            return view;
        }
    }






}
