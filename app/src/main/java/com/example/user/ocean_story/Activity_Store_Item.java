package com.example.user.ocean_story;

import android.util.Log;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * Created by Lee on 2017-07-31.
 */

public class Activity_Store_Item {

    String name;
    double cost;

    int resId;
    int resId2;
    double resId3;
    double lever;

    public Activity_Store_Item() {

    }

    public Activity_Store_Item(String name, double lever, double cost, int resId, int resId2, double resId3) {
        this.name = name;
        this.cost = cost;
        this.lever = lever;

        this.resId = resId;
        this.resId2 = resId2;
        this.resId3 = resId3;



    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        Log.e("a", cost + "@@");


        return (cost);
    }

    public void setCost(double cost) {
        this.cost = (cost);
    }
    public double getLever(){
        return lever;
    }
    public void setLever(int lever){
        this.lever = lever;
    }

    public void setLever(){
        lever++;
    }


    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getResId2() {
        return resId2;
    }

    public void setResId2(int resId2) {
        this.resId2 = resId2;
    }

    public double getResId3() {
        return resId3;
    }

    public void setResId3(int resId3) {
        this.resId3 = resId3;
    }

    NumberFormat f;
    @Override
    public String toString() {
        f = NumberFormat.getInstance();
        f.setGroupingUsed(false);
        return "Activity_Store_Item{" +
                "name='" + name + '\'' +
                ", cost='" + f.format(cost) + '\'' +
                '}';
    }




}
