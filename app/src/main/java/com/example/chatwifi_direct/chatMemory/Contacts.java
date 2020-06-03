package com.example.chatwifi_direct.chatMemory;

import java.io.File;
import java.util.HashMap;

public class Contacts implements MemoryManagement<Contact>{
    HashMap<Integer, Contact> contactsList = new HashMap<>(); //Map mit IDs und dazugehöriger Kontaktperson
    int maxID = 0; //die ID vom zuletzt abgespeicherter Kontaktperson
    File contactFile;

    public String createFileName(int id, String contactname){
        return id + "." + contactname;
    }

    public Contacts(){
        restore();
    }

    private void restore(){
        //TODO: holt alle Daten aus dem Ordner Contacts ins contactList
        //TODO: setztn maxID
    }

    @Override
    public void save(Contact contact) {
        int id = maxID++;
        contactsList.put(id, contact);
        contactFile = new File(createFileName(id, contact.getName()));
        //TODO: speicher den Kontakt in den contactFile
    }

    @Override
    public void delete(Contact contact) {
        contactsList.remove(contact);
        //TODO: löscht die geeignete Datei aus dem Datessystem
    }
}
