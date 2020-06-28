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

public class Chat implements MemoryManagement<Message>{
    File chatLog;
    //String[] members;
    Set<String> members = null;
    Integer imageID = R.drawable.ic_action_avatar;
    ArrayList<Message> messages = new ArrayList<>();
    Context context;

    //erstellt einen eindeutigen Namen für den File, wo das Chatlog abgespeichert wird
    public String createFilename(Set<String> members){
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

    public void addMessage(Message msg){
        messages.add(msg);
    }

    public ArrayList<String> getMessages(){
        ArrayList<String> messages = new ArrayList<>();
        for(Message m : this.messages){
            messages.add(m.getText());
        }
        return messages;
    }

    public ArrayList<String> getSenders(){
        ArrayList<String> senders = new ArrayList<>();
        for(Message m : this.messages){
            senders.add(m.getSender());
        }
        return senders;
    }

    public String getLastMessage(){
        if(messages.size() == 0){
            return "";
        }else{
            return messages.get(messages.size() - 1).getText();
        }
    }

    private void writeMembersToFile(File file, Set<String> members){
        String str = "Chatmembers: ";
        for(String s : members){
            str = str + s + " ";
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
            byte[] string = str.getBytes();
            fos.write(string);
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

    public Integer getImage(){
        return imageID;
    }

    //Ein Chat wird mit Hilfe von den Teilnehmern eindeutig identifiziert
    public Chat(Context context, Set<String> members){
        this.members = members;
        //this.members = members;
        this.context = context;
        chatLog = new File(context.getFilesDir(), createFilename(members));
        //TODO: wenn File leer, schreib die Teilnehmer rein
        if(chatLog.length() == 0){
            //writeMembersToFile(chatLog, members);
        }
        //TODO: save Chat to File
    }

    public void restore(){

    }


    @Override
    public void save(Message message) {
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

    @Override
    public void delete(Message message) {
        messages.remove(message);
        //TODO: delete message from file chatLog
    }
}
