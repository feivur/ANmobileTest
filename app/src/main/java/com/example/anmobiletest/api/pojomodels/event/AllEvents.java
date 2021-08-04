package com.example.anmobiletest.api.pojomodels.event;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Iterator;

public class AllEvents implements Iterable<Event>
{

    @SerializedName("events")
    @Expose
    private ArrayList<Event> events = null;

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public AllEvents withEvents(ArrayList<Event> events) {
        this.events = events;
        return this;
    }

    @NonNull
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
