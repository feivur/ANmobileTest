package com.example.anmobiletest.api.pojomodels;


import android.graphics.drawable.Drawable;

public class Post {
    private String cameraName;
    private byte[] postImageUrl;
    private String postInfo;
    private String time;
    private Drawable image;

    public Post(String cameraName, byte[] postImageUrl, String postInfo, String time){
        this.cameraName = cameraName;
        this.postImageUrl=postImageUrl;
        this.postInfo=postInfo;
        this.time=time;
        this.image=image;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public byte[] getPostImageUrl() {
        return postImageUrl;
    }


    public void setPostImageUrl(byte[] postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getPostInfo() {
        return postInfo;
    }

    public void setPostInfo(String postInfo) {
        this.postInfo = postInfo;
    }

    public String getPostTime() {
        return time;
    }

    public void setPostTime(String time) {
        this.time = time;
    }

    public Drawable getPostImage() {
        return image;
    }

    public void setPostImage(Drawable image) {
        this.image = image;
    }
}
