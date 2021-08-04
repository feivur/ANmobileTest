package com.example.anmobiletest.api.pojomodels.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class Event implements Serializable
    {

        @SerializedName("alertState")
        @Expose
        private String alertState;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("origin")
        @Expose
        private String origin;
        @SerializedName("rectangles")
        @Expose
        private List<Rectangle> rectangles = null;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("timestamp")
        @Expose
        private String timestamp;
        @SerializedName("type")
        @Expose
        private String type;

        public String getAlertState() {
        return alertState;
    }

        public void setAlertState(String alertState) {
        this.alertState = alertState;
    }

        public Event withAlertState(String alertState) {
        this.alertState = alertState;
        return this;
    }

        public String getId() {
        return id;
    }

        public void setId(String id) {
        this.id = id;
    }

        public Event withId(String id) {
        this.id = id;
        return this;
    }

        public String getOrigin() {
        return origin;
    }

        public void setOrigin(String origin) {
        this.origin = origin;
    }

        public Event withOrigin(String origin) {
        this.origin = origin;
        return this;
    }

        public List<Rectangle> getRectangles() {
        return rectangles;
    }

        public void setRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

        public Event withRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
        return this;
    }

        public String getSource() {
        return source;
    }

        public void setSource(String source) {
        this.source = source;
    }

        public Event withSource(String source) {
        this.source = source;
        return this;
    }

        public String getTimestamp() {
        return timestamp;
    }

        public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

        public Event withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

        public String getType() {
        return type;
    }

        public void setType(String type) {
        this.type = type;
    }

        public Event withType(String type) {
        this.type = type;
        return this;
    }

}
