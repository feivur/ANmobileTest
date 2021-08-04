package com.example.anmobiletest.api.pojomodels.archive;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Archive {
    @SerializedName("accessPoint")
    @Expose
    private String accessPoint;

    public String getAccessPoint() {
        return accessPoint;
    }

    public void setAccessPoint(String accessPoint) {
        this.accessPoint = accessPoint;
    }

    @Override
    public String toString() {
        return "Archive{" +
                "accessPoint='" + accessPoint + '\'' +
                '}';
    }
}
