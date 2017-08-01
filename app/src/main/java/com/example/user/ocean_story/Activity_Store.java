package com.example.user.ocean_story;

import android.app.Activity;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        ListView listView = (ListView) findViewById(R.id.listView);

        adapter = new Item_Adapter();
        adapter.addItem(new Activity_Store_Item("1", "1"));
        adapter.addItem(new Activity_Store_Item("2", "2"));
        adapter.addItem(new Activity_Store_Item("3", "3"));
        adapter.addItem(new Activity_Store_Item("4", "4"));
        adapter.addItem(new Activity_Store_Item("5", "5"));
        adapter.addItem(new Activity_Store_Item("6", "6"));
        adapter.addItem(new Activity_Store_Item("7", "7"));
        listView.setAdapter(adapter);

        //메모리 관리
         RelativeLayout layout = (RelativeLayout)findViewById(R.id.store_View);
         layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_start_black));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Activity_Store_Item item =  (Activity_Store_Item)adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "" + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recycleView(findViewById(R.id.store_View));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


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
