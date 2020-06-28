package com.example.chatwifi_direct.chatMemory;

public class Message {
    String sender;
    String text;

    //Die Nachricht besteht aus dem Namen der Absender und der Inhalt der Nachricht
    public Message(String sender, String text){
        this.sender = sender;
        this.text = text;
    }

    public String getSender(){
        return sender;
    }

    public String getText(){
        return text;
    }

    public String toString(){
        return sender + ": " + text;
    }
}
