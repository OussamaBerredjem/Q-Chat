package com.example.myapplication;

import com.google.firebase.Timestamp;

import java.util.Objects;

public class MessageModel {

    public String id;
    public String document;
    public String message;
    public int messageType;
    int spam;
    public Timestamp messageTime;
    // Constructor
    public MessageModel(String message, int messageType, Timestamp timestamp,String id,String document) {
        this.document = document;
        this.id = id;
        this.message = message;
        this.messageType = messageType;
        this.spam = spam;
        this.messageTime = timestamp;
    }
    public String GetDocument(){return this.document;}
    public String getMessage(){
        return this.message;
    }
    public String getId(){return this.id;};
    public Timestamp getMessageTime(){
        return this.messageTime;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MessageModel other = (MessageModel) obj;
        return Objects.equals(message, other.message)
                && messageType == other.messageType
                && Objects.equals(messageTime, other.messageTime);
    }
    }


