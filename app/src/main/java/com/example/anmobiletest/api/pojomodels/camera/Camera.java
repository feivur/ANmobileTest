package com.example.anmobiletest.api.pojomodels.camera;

import androidx.annotation.NonNull;

import com.example.anmobiletest.api.pojomodels.archive.Archive;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Iterator;

public class Camera implements Iterable<Archive> {
    @SerializedName("archives")
    @Expose
    private ArrayList<Archive> archives;

    @SerializedName("displayName")
    @Expose
    private String displayName;

    public ArrayList<Archive> getArchives() {
        return archives;
    }

    public void setArchives(ArrayList<Archive> archives) {
        this.archives = archives;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "Cameras{" +
                "archives=" + archives +
                ", displayName='" + displayName + '\'' +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Archive> iterator() {
        return archives.iterator();
    }
}
