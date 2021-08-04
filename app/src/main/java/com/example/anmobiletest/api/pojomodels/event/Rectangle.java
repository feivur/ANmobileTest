package com.example.anmobiletest.api.pojomodels.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rectangle implements Serializable
{

    @SerializedName("bottom")
    @Expose
    private Double bottom;
    @SerializedName("index")
    @Expose
    private Integer index;
    @SerializedName("left")
    @Expose
    private Double left;
    @SerializedName("right")
    @Expose
    private Double right;
    @SerializedName("top")
    @Expose
    private Double top;

    public Double getBottom() {
        return bottom;
    }

    public void setBottom(Double bottom) {
        this.bottom = bottom;
    }

    public Rectangle withBottom(Double bottom) {
        this.bottom = bottom;
        return this;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Rectangle withIndex(Integer index) {
        this.index = index;
        return this;
    }

    public Double getLeft() {
        return left;
    }

    public void setLeft(Double left) {
        this.left = left;
    }

    public Rectangle withLeft(Double left) {
        this.left = left;
        return this;
    }

    public Double getRight() {
        return right;
    }

    public void setRight(Double right) {
        this.right = right;
    }

    public Rectangle withRight(Double right) {
        this.right = right;
        return this;
    }

    public Double getTop() {
        return top;
    }

    public void setTop(Double top) {
        this.top = top;
    }

    public Rectangle withTop(Double top) {
        this.top = top;
        return this;
    }

}