package com.example.regiserandloginform.pojo;

import java.io.Serializable;

public class PointerLocation implements Serializable {
    private double latitude;
    private double longitude;

    public PointerLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Szélesség: "+latitude+
                "\n Hosszúság:"+longitude;
    }
}
