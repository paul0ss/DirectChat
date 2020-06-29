package com.example.chatwifi_direct.chatMemory;

import android.content.Context;

import com.example.chatwifi_direct.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ContactsImpl implements Contacts{
    //HashMap<Integer, Contact> contactsList = new HashMap<>(); //Map mit IDs und dazugehöriger Kontaktperson
    HashMap<String, Contact> contactsList = new HashMap<>();
    //TODO: HERE
    // int maxID = 0; //die ID vom zuletzt abgespeicherter Kontaktperson
    File contactFile;
    private static final String FILENAME = "contactsFile.txt";
    //Integer imageID = R.drawable.ic_action_avatar;
    private static Context ctx;
    private static ContactsImpl instance;

/*    private String createFileName(int id, String contactname){
        return id + "." + contactname;
    }*/

    private ContactsImpl(){
        restore();
    }

    public void setUpContact(String mac){
        if(contactsList.get(mac) == null){
            Contact c = new Contact("Empty", "mac");
            contactsList.put(mac,c);
        }
    }

    public static ContactsImpl getContacsInstance(Context context){
        ContactsImpl.ctx = context;
        if(ContactsImpl.instance == null){
            ContactsImpl.instance = new ContactsImpl();
        }
        return instance;
    }

    private Contact[] getContactsArray(){
        //Contact c = contactsList.get(0);
        //TODO: MAMAYBE ERROR!!!
        //Contact[] c = (Contact[]) contactsList.values().toArray();
        Contact[] c = contactsList.values().toArray(new Contact[0]);
        return c;
    }

    @Override
    public String[] getNames(){
        Contact[] contactsArray = this.getContactsArray();
        String[] names = new String[contactsArray.length];
        int i = 0;
        for(Contact c : contactsArray) {
            names[i] = c.getName();
            i = i + 1;
        }
        return names;
    }

    @Override
    public String[] getMacs(){
        Contact[] macsArray = this.getContactsArray();
        String[] macs = new String[macsArray.length];
        int i = 0;
        for(Contact c : macsArray) {
            macs[i] = c.getMac();
            i = i + 1;
        }
        return macs;
    }

    @Override
    public Integer getPicture(String mac){
        return contactsList.get(mac).getPicture();
    }

    @Override
    public Integer[] getPictures(){
        ArrayList<Integer> list = new ArrayList<>();
        for(Contact c : contactsList.values()){
            list.add(c.getPicture());
        }
        return list.toArray(new Integer[0]);
    }

    private void restore(){
        //TODO: HERE!!!
        //int id = maxID;
        contactFile = new File(ctx.getFilesDir(), FILENAME);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(contactFile);
            String contactString;
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while((contactString = br.readLine()) != null){
                String[] contactParts = contactString.split("-");
                String name;
                String mac;
                name = contactParts[0];
                mac = contactParts[1].trim();
                contactsList.put(mac, new Contact(name, mac));
                //TODO: HERE!!!
                //maxID++;
                //contactsList.put(id, new Contact(contactString, "fwieuf"));
            }
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

    @Override
    public void save(String name,  String mac) { //TODO: die vorhandene Kontakte werden in dem File nicht überschrieben
        //int id = maxID++;
        Contact contact = new Contact(name, mac);
        //TODO HERE!!!
        //maxID = maxID + 1;
        //TODO: HERE!!!
        //contactsList.put(maxID, contact);
        contactsList.put(mac, contact);
        contactFile = new File(ctx.getFilesDir(), FILENAME);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(contactFile, true);
            //fos = ctx.openFileOutput(FILENAME, ctx.MODE_PRIVATE);
            byte[] str = contact.toString().getBytes();
            fos.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(String mac) {
        contactsList.remove(mac);
        //TODO: löscht die geeignete Datei aus dem Datessystem
    }
}
