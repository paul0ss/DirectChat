package com.example.chatwifi_direct.chatMemory;

import java.io.File;
import java.util.LinkedHashSet;

public class Chats {
    //TODO: TreeSet: Elemente können nur ein mal vorkommen. Sind aber nach einem Kriterium sortiert(Datum der letzer Nachricht)
    //LinkedHashList: Elemente kommen nur ein mal vor.Sind aber in der Reihenfole, in der sie
    //eingefügt wurden
    LinkedHashSet<Chat> allChats = new LinkedHashSet<>();

    public Chats(){
        restore();
    }

    //TODO: adds Chat to allChats
    //fügt einen Neuerstellten Chat zur Menge der Chats ein
    public void addChat(Chat chat){
        allChats.add(chat);
    }

    private void restore(){
        //TODO: holt alle Daten aus Dateie aus dem Ordner Chats raus und speicher die ins allChats
    }

}
