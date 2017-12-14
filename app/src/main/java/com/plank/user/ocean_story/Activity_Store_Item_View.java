package com.plank.user.ocean_story;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Lee on 2017-07-31.
 */

public class Activity_Store_Item_View extends LinearLayout{

    TextView textView;
    TextView textView2;
    TextView textView6;


    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;

    public TextView get_TextView(){
        textView2 = (TextView) findViewById(R.id.textView2);
        return textView2;
    }
    public TextView get_TextLevel(){
        textView = (TextView) findViewById(R.id.textView);
        return textView;
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

        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);

    }

    public void setName(String name){
//        textView.setText(name);
    }
    public void setText(String cost){
        textView2.setText(cost);
    }
    public void setTextLever(String lv){
        textView.setText(lv);
    }




    //이미지 뷰에 이미지 설정
    public void setImage(int resid, int resid2){
        try {
            imageView.setImageResource(resid);
            textView6.setBackgroundResource(resid2);

            imageView2.setBackgroundResource(R.drawable.store_explain_gold);
            imageView3.setBackgroundResource(R.drawable.store_explain_level);
        }catch (Exception e){

        }
    }

    @Override
    public void destroyDrawingCache() {
        super.destroyDrawingCache();

    }
}
