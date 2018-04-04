package com.retrofitsample.model;

/**
 * Created by tkyh on 9/22/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")

public class Items {

    @SerializedName("item3")
    @Expose
    private String item3;
    @SerializedName("item4")
    @Expose
    private String item4;

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }


}