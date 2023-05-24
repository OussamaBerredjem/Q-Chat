package com.example.myapplication;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

public class Search_info implements Parcelable {
    SpannableString fullname;
    String photourl;
    Timestamp date;
    String message;
    String uid;
    String vue;
    String hour;
    boolean visibility = true;

    public Search_info(SpannableString fullname, String photourl, Timestamp date, String message,String vue,String uid) {
        this.fullname = fullname;
        this.photourl = photourl;
        this.vue = vue;
        this.date = date;

        this.message = message;
        this.uid = uid;

    }


    protected Search_info(Parcel in) {
        fullname = SpannableString.valueOf(in.readString());
        photourl = in.readString();
        date = in.readParcelable(Timestamp.class.getClassLoader());
        message = in.readString();
        uid = in.readString();
        vue = in.readString();
        hour = in.readString();
    }

    public static final Creator<ChatInfo> CREATOR = new Creator<ChatInfo>() {
        @Override
        public ChatInfo createFromParcel(Parcel in) {
            return new ChatInfo(in);
        }

        @Override
        public ChatInfo[] newArray(int size) {
            return new ChatInfo[size];
        }
    };

    public String getVue() {
        return vue;
    }

    public String getHour() {
        return hour;
    }

    public SpannableString getFullname() {
        return fullname;
    }

    public String getPhotourl() {
        return photourl;
    }

    public Timestamp getDate() {


        return this.date;
    }

    public String getUid() {
        return uid;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(fullname.toString());
        dest.writeString(photourl);
        dest.writeParcelable(date, flags);
        dest.writeString(message);
        dest.writeString(uid);
        dest.writeString(vue);
        dest.writeString(hour);
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void setFullname(SpannableString fullname) {
        this.fullname = fullname;
    }

    public boolean isVisibility() {
        return visibility;
    }
}
