package com.example.anmobiletest.api.pojomodels;

public class Post {
    private final String videoSurceId;
    private final String postInfo;
    private final String time;
    private String cameraName;

    public Post(String videoSurceId, String cameraName, String postInfo, String time) {
        this.videoSurceId = videoSurceId.replaceAll("^hosts", "");
        this.cameraName = cameraName;
        this.postInfo = postInfo;
        this.time = time;
    }

    public String getPostInfo() {
        return postInfo;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }



    public String getPostTime() {
        return time;
    }

    public String getVideoSurceId() {
        return videoSurceId;
    }

}
