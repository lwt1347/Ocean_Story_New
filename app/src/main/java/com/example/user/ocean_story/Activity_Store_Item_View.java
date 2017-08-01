package com.example.user.ocean_story;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Lee on 2017-07-31.
 */

public class Activity_Store_Item_View extends LinearLayout{

    TextView textView;
    TextView textView2;
    public Activity_Store_Item_View(Context context) {
        super(context);
        init(context);
    }

    public Activity_Store_Item_View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_store_item, this, true);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);

    }

    public void setName(String name){
        textView.setText(name);
    }
    public void setCost(String cost){
        textView.setText(cost);
    }


}
