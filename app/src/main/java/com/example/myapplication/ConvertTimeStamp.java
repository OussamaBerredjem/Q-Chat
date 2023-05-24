package com.example.myapplication;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertTimeStamp {

    Timestamp timestamp;

    public ConvertTimeStamp() {

    }
    public String convert(Timestamp timestamp){
        this.timestamp = timestamp;

        Date date = timestamp.toDate();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

// Format the date to display the hour
        String hour = formatter.format(date);

        return hour;

    }
}
