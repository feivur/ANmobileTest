package com.example.anmobiletest.api.pojomodels.camera;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Iterator;

public class allCameras implements Iterable<Camera> {

    @SerializedName("cameras")
    @Expose
    private ArrayList<Camera> camera;

    public allCameras() {
    }


    public ArrayList<Camera> getCameras() {
        return camera;
    }

    public void setCameras(ArrayList<Camera> cameras) {
        this.camera = cameras;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "cameras=" + camera +
                '}';
    }


    @NonNull
    @Override
    public Iterator<Camera> iterator() {
        return camera.iterator();
    }
}
