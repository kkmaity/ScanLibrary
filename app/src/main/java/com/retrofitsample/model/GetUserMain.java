package com.retrofitsample.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kamal on 03/10/2018.
 */

public class GetUserMain {
    @SerializedName("earthquakes")
    @Expose
    private List<UserData> earthquakes = null;

    public List<UserData> getEarthquakes() {
        return earthquakes;
    }

    public void setEarthquakes(List<UserData> earthquakes) {
        this.earthquakes = earthquakes;
    }
}
