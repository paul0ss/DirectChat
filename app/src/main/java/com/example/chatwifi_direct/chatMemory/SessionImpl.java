package com.example.chatwifi_direct.chatMemory;

public class SessionImpl implements Session{
    Contacts allContacts;
    Chats allChats;

    @Override
    public void restore() {
        //allContacts = new Contacts();
        //allChats = new Chats();
    }

    @Override
    public Contacts getAllContacts() {
        return allContacts;
    }

    @Override
    public Chats getAllChats() {
        return allChats;
    }
}
