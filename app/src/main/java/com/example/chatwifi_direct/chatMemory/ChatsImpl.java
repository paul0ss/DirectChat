package com.example.chatwifi_direct.chatMemory;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ChatsImpl implements Chats{
    //TODO: TreeSet: Elemente können nur ein mal vorkommen. Sind aber nach einem Kriterium sortiert(Datum der letzer Nachricht)
    //LinkedHashList: Elemente kommen nur ein mal vor.Sind aber in der Reihenfole, in der sie
    //eingefügt wurden
    private Set<String> members; // every String = "Member1, Member2, ..."
    private HashMap<Set, Chat> allChats;
    private static ChatsImpl instance = null;
    private static Context ctx;

    private ChatsImpl(){
       allChats = new HashMap<Set, Chat>();
       members = new HashSet<>();
       restore();
    }

    public static ChatsImpl getInstance(Context context){
        ChatsImpl.ctx = context;
        if(ChatsImpl.instance == null){
            instance = new ChatsImpl();
        }
        return ChatsImpl.instance;
    }

    @Override
    public void setChat(Set<String> members){
        if(allChats.get(members) == null){
            Chat chat = new Chat(ctx, members);
            allChats.put(members, chat);
        }
    }

    @Override
    public void save(String sender, String message, Set<String> members){
        Message m = new Message(sender, message);
        Chat c = allChats.get(members);
        c.save(m);
    }

    @Override
    public void delete(String sender, String message, Set<String> members){

    }

    @Override
    public String[] getSenders(Set<String> members){
        Chat c = allChats.get(members);
        ArrayList<String> senders = new ArrayList<>();
        if(c.getMessages().isEmpty()){
            return null;
        }
        for(Message m : c.getMessages()){
            senders.add(m.getSender());
        }
        return senders.toArray(new String[0]);
    }

    @Override
    public String[] getMessages(Set<String> members){
        Chat c = allChats.get(members);
        ArrayList<String> messagesText = new ArrayList<>();
        for(Message m : c.getMessages()){
            messagesText.add(m.getText());
        }
        return messagesText.toArray(new String[0]);
    }


    private void restore(){
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
                    //chat.addMessage(new Message(sender, text));
                    chat.getMessages().add(new Message(sender, text));
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

    @Override
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

    @Override
    public String[] getLastMessages(){
        ArrayList<String> lastMessages = new ArrayList<>();
        for(Chat c : allChats.values()){
            lastMessages.add(c.getLastMessage());
        }
        return lastMessages.toArray(new String[0]);
    }

    @Override
    public Integer[] getPictures(){
        ArrayList<Integer> list = new ArrayList<>();
        for(Chat c : allChats.values()){
            list.add(c.getImage());
        }
        return list.toArray(new Integer[0]);
    }

}
