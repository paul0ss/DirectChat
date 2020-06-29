package com.example.chatwifi_direct.chatMemory;

class Message {
    private String sender;
    private String text;

    //Die Nachricht besteht aus dem Namen der Absender und der Inhalt der Nachricht
    Message(String sender, String text){
        this.sender = sender;
        this.text = text;
    }

    String getSender(){
        return sender;
    }

    String getText(){
        return text;
    }

    public String toString(){
        return sender + ": " + text;
    }
}
