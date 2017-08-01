package com.example.user.ocean_story;

/**
 * Created by Lee on 2017-07-31.
 */

public class Activity_Store_Items {

    String name;
    String cost;


    public Activity_Store_Items(String name, String cost) {
        this.name = name;
        this.cost = cost;
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


    @Override
    public String toString() {
        return "Activity_Store_Items{" +
                "name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
