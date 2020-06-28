package com.example.chatwifi_direct.chatMemory;

import android.content.Context;

import com.example.chatwifi_direct.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Contacts implements MemoryManagement<Contact>{
    HashMap<Integer, Contact> contactsList = new HashMap<>(); //Map mit IDs und dazugehöriger Kontaktperson
    int maxID = 0; //die ID vom zuletzt abgespeicherter Kontaktperson
    File contactFile;
    private static final String FILENAME = "contactsFile.txt";
    Integer imageID = R.drawable.ic_action_avatar;
    private static Context ctx;
    private static Contacts instance;

    private String createFileName(int id, String contactname){
        return id + "." + contactname;
    }

    private Contacts(){
    }

    public static Contacts getContacsInstance(Context context){
        if(Contacts.instance == null){
            Contacts.instance = new Contacts();
        }
        Contacts.ctx = context;
        return instance;
    }

    private Contact[] getContactsArray(){
        //Contact c = contactsList.get(0);
        //TODO: MAMAYBE ERROR!!!
        //Contact[] c = (Contact[]) contactsList.values().toArray();
        Contact[] c = contactsList.values().toArray(new Contact[contactsList.size()]);
        return c;
    }

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

    public Integer getImage(){
        return imageID;
    }

    public void restore(){
        int id = maxID;
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
                contactsList.put(maxID, new Contact(name, mac));
                maxID++;
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
        //TODO: holt alle Daten aus dem Ordner Contacts ins contactList
        //TODO: setztn maxID
    }

    @Override
    public void save(Contact contact) {
        //int id = maxID++;
        maxID = maxID + 1;
        contactsList.put(maxID, contact);
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
    public void delete(Contact contact) {
        contactsList.remove(contact);
        //TODO: löscht die geeignete Datei aus dem Datessystem
    }
}
