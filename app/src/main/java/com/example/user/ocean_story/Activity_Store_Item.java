package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-31.
 */

public class Activity_Store_Item {

    String name;
    String cost;

    int resId;
    int resId2;
    int resId3;


    public Activity_Store_Item(String name, String cost, int resId, int resId2, int resId3) {
        this.name = name;
        this.cost = cost;

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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
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

    public int getResId3() {
        return resId3;
    }

    public void setResId3(int resId3) {
        this.resId3 = resId3;
    }

    @Override
    public String toString() {
        return "Activity_Store_Item{" +
                "name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }



}
