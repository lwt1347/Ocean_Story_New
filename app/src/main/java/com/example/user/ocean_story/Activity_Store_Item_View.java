package com.example.user.ocean_story;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Lee on 2017-07-31.
 */

public class Activity_Store_Item_View extends LinearLayout{

    TextView textView;
    TextView textView2;
    TextView textView6;


    ImageView imageView;


    public TextView get_TextView(){
        textView2 = (TextView) findViewById(R.id.textView2);
        return textView2;
    }

    public void set_Text(){

    }

    public Activity_Store_Item_View(Context context) {
        super(context);
        init(context);
        

    }



    public TextView get_TextView_Button(){
        return textView6;
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
        imageView = (ImageView)findViewById(R.id.imageView);
        textView6 = (TextView) findViewById(R.id.textView6);



    }

    public void setName(String name){
//        textView.setText(name);
    }
    public void setText(String cost){
        textView2.setText(cost);
    }




    //이미지 뷰에 이미지 설정
    public void setImage(int resid, int resid2, int resid3){
        imageView.setImageResource(resid);
        textView6.setBackgroundResource(resid2);
        textView.setBackgroundResource(resid3);
    }

    @Override
    public void destroyDrawingCache() {
        super.destroyDrawingCache();

    }
}
