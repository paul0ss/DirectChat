package com.example.chatwifi_direct.chatMemory;

import com.example.chatwifi_direct.R;

class Contact {
    private String name;
    private String mac;
    private Integer picture = R.drawable.ic_action_avatar;

    //ein Kontakt besteht aus dem Namen und der MAC-Adresse seines Handys
    public Contact(String name, String mac){
        this.name = name;
        this.mac = mac;
    }

    protected String getName(){
        return this.name;
    }

    protected String getMac(){ return this.mac; }

    protected Integer getPicture() {
        return picture;
    }

    public String toString(){
        return name + "-" + mac + "\n";
    }

}
