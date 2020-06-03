package com.example.chatwifi_direct.chatMemory;

public class Contact {
    private String name;
    private byte[] mac;

    //ein Kontakt besteht aus dem Namen und der MAC-Adresse seines Handys
    public Contact(String name, byte[] mac){
        this.name = name;
        this.mac = mac;
    }

    public String getName(){
        return this.name;
    }

}
