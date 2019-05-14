package com.example.localizeapp2;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Event {
    public String title;
    public String date_time;
    public String location;
    public String tag;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Event(String title, String date_time, String location, String tag) {
        this.title = title;
        this.date_time = date_time;
        this.location = location;
        this.tag = tag;
    }

    public String getTitle() { return title;
    }

    public String getDate_time(){
        return date_time;
    }

    public String getLocation(){
        return location;
    }

    public String getTag() {
        return tag;
    }
}