package com.example.chatwifi_direct.chatMemory;

public interface Session {

    /**
     * stellt die vorherige Sitzung wieder
     */
    public void restore();

    /**
     * liefert ein Objekt in dem alle Kontakte abgespeichert sind
     * @return
     */
    public Contacts getAllContacts();

    /**
     * liefert ein Objekt in dem alle Chats abgespeichert sind
     * @return
     */
    public Chats getAllChats();
}
