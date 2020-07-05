package com.example.chatwifi_direct.chatMemory;

import android.content.Context;

import com.example.chatwifi_direct.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class Chat{
    private File chatLog;
    private Set<String> members = null;
    private Integer imageID = R.drawable.ic_action_avatar;
    private ArrayList<Message> messages = new ArrayList<>();
    private Context context;

    //erstellt einen eindeutigen Namen für den File, wo das Chatlog abgespeichert wird
    private String createFilename(Set<String> members){
        String[] m = members.toArray(new String[0]);
        String name = "";
        for(int i = 0; i < m.length; i++){
            name = name + m[i];
            if(i != m.length -1){
                name = name + "-";
            }
        }
        return name = name + "@Chat" + ".txt";
    }

    protected ArrayList<Message> getMessages(){
        return this.messages;
    }

    protected String getLastMessage(){
        if(messages.size() == 0){
            return "";
        }else{
            return messages.get(messages.size() - 1).getText();
        }
    }


    protected Integer getImage(){
        return imageID;
    }

    //Ein Chat wird mit Hilfe von den Teilnehmern eindeutig identifiziert
    protected Chat(Context context, Set<String> members){
        this.members = members;
        this.context = context;
        chatLog = new File(context.getFilesDir(), createFilename(members));
        try {
            chatLog.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void save(Message message) {
        messages.add(message);
        String messageUnit = message.toString() + "\n";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(chatLog, true);
            byte[] str = messageUnit.getBytes();
            fos.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void delete(Message message) {
        messages.remove(message);
        //TODO: delete message from file chatLog
    }
}
