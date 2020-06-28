package com.example.chatwifi_direct.chatMemory;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Chats {
    //TODO: TreeSet: Elemente können nur ein mal vorkommen. Sind aber nach einem Kriterium sortiert(Datum der letzer Nachricht)
    //LinkedHashList: Elemente kommen nur ein mal vor.Sind aber in der Reihenfole, in der sie
    //eingefügt wurden
    //LinkedHashSet<Chat> allChats = new LinkedHashSet<>();
    Set<String> members; // every String = "Member1, Member2, ..."
    //private HashMap<Set. Chat> allChats = new HashMap<Set. Chat>();
    HashMap<Set, Chat> allChats;
    private static Chats instance = null;
    private static Context ctx;

    private Chats(){
       allChats = new HashMap<Set, Chat>();
       members = new HashSet<>();
        //restore();
    }

    public static Chats getInstance(Context context){
        if(Chats.instance == null){
            instance = new Chats();
        }
        Chats.ctx = context;
        return Chats.instance;
    }

    //TODO: adds Chat to allChats
    //fügt einen Neuerstellten Chat zur Menge der Chats ein
    public void addChat(Set set, Chat chat){
        allChats.put(set, chat);
    }

    public void restore(){
        FilenameFilter filter = (dir, name) -> name.endsWith("@Chat.txt");
        File dir = ctx.getFilesDir();
        File[] chats = dir.listFiles(filter);
        String filename = "";
        String members = "";
        String sender = "";
        String text = "";
        for(File f: chats){
            Set<String> membersOfOneChat = new HashSet<>();
            //get members from Filename
            filename = f.getName();
            members = filename.substring(0, filename.length() - 9);
            String[] membersArray = members.split("-");
            for(String s : membersArray){
                membersOfOneChat.add(s);
            }
            Chat chat = new Chat(ctx, membersOfOneChat);
            //restore Messages
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
                String unit;
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                while((unit = br.readLine()) != null){
                    String[] arr = unit.split(" ", 2);
                    sender = arr[0].substring(0, arr[0].length() - 1);
                    text = arr[1];
                    chat.addMessage(new Message(sender, text));
                }
                allChats.put(membersOfOneChat, chat);
                membersOfOneChat = null;
                chat = null;
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fis != null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Chat getChat(Set<String> s){
        return allChats.get(s);
    }

    public String[] getParticipents(){
        Set[] s = allChats.keySet().toArray(new Set[0]);
        ArrayList<String> str = new ArrayList<>();
        for(Set set : s){
            String string = set.toString();
            String finalString = string.substring(1, string.length() - 1);
            str.add(finalString);
        }
        return str.toArray(new String[0]);
    }

    public String[] getLastMessage(){
        ArrayList<String> lastMessages = new ArrayList<>();
        for(Chat c : allChats.values()){
            lastMessages.add(c.getLastMessage());
        }
        return lastMessages.toArray(new String[0]);
    }

}
