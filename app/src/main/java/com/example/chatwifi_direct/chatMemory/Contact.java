package com.example.chatwifi_direct.chatMemory;

public class Contact {
    private String name;
    private String mac;

    //ein Kontakt besteht aus dem Namen und der MAC-Adresse seines Handys
    public Contact(String name, String mac){
        this.name = name;
        this.mac = mac;
    }

    public String getName(){
        return this.name;
    }

    public String getMac(){ return this.mac; }

    public String toString(){
        return name + "-" + mac + "\n";
    }

}
