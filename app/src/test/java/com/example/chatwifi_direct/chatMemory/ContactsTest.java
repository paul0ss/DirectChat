package com.example.chatwifi_direct.chatMemory;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContactsTest {

    Context context = mock(Context.class);
    Contacts contacts;
    File contactsFile;

    @Rule
    public TemporaryFolder mTempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        when(context.getFilesDir()).thenReturn(mTempFolder.newFolder());
        contactsFile = new File(context.getFilesDir(), "contactsFile.txt");
        //contactsFile.createNewFile();
    }

    @After
    public void cleanUp(){
        if(contactsFile != null){
            contactsFile.delete();
        }
    }

    @Test
    public void saveContactTest() throws IOException {
        contacts = ContactsImpl.getContacsInstance(context);
        String[] names = {"BobTheTester", "AliceTheTester", "ClaraTheTester"};
        String[] macs = {"de:66:3a:6b:a5", "f4:66:4e:7c:a3", "aa:75:ab:de:a2"};

        for(int i = 0; i < names.length; i++){
            contacts.save(names[i], macs[i]);
        }

        FileInputStream fis = null;
        fis = new FileInputStream(contactsFile);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String contact = "";
        ArrayList<String> l = new ArrayList<>();
        while ((contact = br.readLine()) != null){
            l.add(contact.trim());
        }

        String[] expected = {"BobTheTester-de:66:3a:6b:a5", "AliceTheTester-f4:66:4e:7c:a3", "ClaraTheTester-aa:75:ab:de:a2"};
        String[] result = l.toArray(new String[0]);

        assertArrayEquals(expected, result);
    }

    @Test
    public void saveTestNoFile(){
        contacts = ContactsImpl.getContacsInstance(context);
        String[] names = {"BobTheTester", "AliceTheTester", "ClaraTheTester"};
        String[] macs = {"de:66:3a:6b:a5", "f4:66:4e:7c:a3", "aa:75:ab:de:a2"};
        contactsFile = null;

        for(int i = 0; i < names.length; i++){
            contacts.save(names[i], macs[i]);
        }

    }


    @Test
    public void delete() {
    }


    @Test
    public void restoreContactsTest() throws IOException {
        String[] names = {"BobTheTester", "AliceTheTester", "ClaraTheTester"};
        String[] macs = {"de:66:3a:6b:a5", "f4:66:4e:7c:a3", "aa:75:ab:de:a2"};

        FileOutputStream fos;
        fos = new FileOutputStream(contactsFile);
        for(int i = 0; i < names.length; i++){
            String str = names[i] + "-" +macs[i] + System.lineSeparator();
            fos.write(str.getBytes());
        }
        contacts = ContactsImpl.getContacsInstance(context);
        String[] namesResult = contacts.getNames();
        String[] macsResult = contacts.getMacs();

        assertArrayEquals(names, namesResult);
        assertArrayEquals(macs, macsResult);
    }

    @Test
    public void restoreEmptyTest(){//empty restore
        contacts = ContactsImpl.getContacsInstance(context);
    }

    @Test
    public void getNamesTest() {
        contacts = ContactsImpl.getContacsInstance(context);
        String[] names = {"BobTheTester", "AliceTheTester", "ClaraTheTester"};
        String[] macs = {"de:66:3a:6b:a5", "f4:66:4e:7c:a3", "aa:75:ab:de:a2"};
        for(int i = 0; i < names.length; i++){
            contacts.save(names[i], macs[i]);
        }
        String[] result = contacts.getNames();
        assertArrayEquals(names, result);
    }

    @Test
    public void getMacsTest() {
        contacts = ContactsImpl.getContacsInstance(context);
        String[] names = {"BobTheTester", "AliceTheTester", "ClaraTheTester"};
        String[] macs = {"de:66:3a:6b:a5", "f4:66:4e:7c:a3", "aa:75:ab:de:a2"};
        for(int i = 0; i < names.length; i++){
            contacts.save(names[i], macs[i]);
        }
        String[] result = contacts.getMacs();
        assertArrayEquals(macs, result);
    }

}