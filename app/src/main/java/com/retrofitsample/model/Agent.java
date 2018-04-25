package com.retrofitsample.model;

/**
 * Created by User on 25-04-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Agent {

    @SerializedName("item1")
    @Expose
    private String item1;
    @SerializedName("item2")
    @Expose
    private String item2;

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    @Override
    public String toString() {
        return item2 ;
    }
}