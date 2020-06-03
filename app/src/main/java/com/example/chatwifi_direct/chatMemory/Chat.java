package com.example.chatwifi_direct.chatMemory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Chat implements MemoryManagement<Message>{
    File chatLog;
    final String[] memebers;
    ArrayList<Message> messages = new ArrayList<>();

    //erstellt einen eindeutigen Namen für den File, wo das Chatlog abgespeichert wird
    public String createFilename(String[] members){
        String name = "";
        for(int i = 0; i < memebers.length; i++){
            name = name + members[i];
            if(i != members.length -1){
                name = name + "-";
            }
        }
        return name = name + ".txt";
    }

    //Ein Chat wird mit Hilfe von den Teilnehmern eindeutig identifiziert
    public Chat(String[] members){
        this.memebers = members;
        chatLog = new File(createFilename(members));
        //TODO: save Chat to File
    }


    @Override
    public void save(Message message) {
        messages.add(message);
        //TODO: save message to a file chatLog
    }

    @Override
    public void delete(Message message) {
        messages.remove(message);
        //TODO: delete message from file chatLog
    }
}
