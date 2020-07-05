package com.example.chatwifi_direct.chatMemory;

import android.content.Context;

import org.junit.Before;
import org.junit.BeforeClass;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChatsTest {

    @Rule
    public TemporaryFolder mTempFolder = new TemporaryFolder();

    Context context = mock(Context.class);
    Chats chats;

    @Before
    public void setUp() throws Exception {
        when(context.getFilesDir()).thenReturn(mTempFolder.newFolder());
    }

    @Test
    public void saveMessageTest() throws IOException {
        chats = ChatsImpl.getInstance(context);
        Set<String> chatMembers = new HashSet();

        String testSender = "Troll";
        String testMessageText = "Testmessage";
        String expectedSavedMessage = testSender + ": " + testMessageText;

        chatMembers.add(testSender);
        chats.save(testSender, testMessageText, chatMembers);

        File chatlog = new File(context.getFilesDir(), "Troll@Chat.txt");

        FileInputStream fis = new FileInputStream(chatlog);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String messageUnitStored= br.readLine();

        String[] senders = chats.getSenders(chatMembers);
        String[] messagges = chats.getMessages(chatMembers);
        String messageUnit = senders[0] + ": " + messagges[0];

        assertEquals(expectedSavedMessage, messageUnitStored);
        assertEquals(expectedSavedMessage, messageUnit);
    }

    @Test
    public void restoreChatsTest() throws IOException {
        File tempFile1 = new File(context.getFilesDir(), "Bob@Chat.txt");
        File tempFile2 = new File(context.getFilesDir(), "Alice@Chat.txt");
        File tempFile3 = new File(context.getFilesDir(), "Clara@Chat.txt");
        File[] tempFiles = {tempFile1, tempFile2, tempFile3};

        String [] messageUnits = {"Bob: Message from Bob", "Alice: Message from Alice", "Clara: Message from Clara"};
        byte[] unit = null;
        FileOutputStream fos = null;
        for(int i = 0; i < tempFiles.length; i++){
            unit = messageUnits[i].getBytes();
            fos = new FileOutputStream(tempFiles[i]);
            fos.write(unit);
        }

        chats = ChatsImpl.getInstance(context);

        Set<String> chat1 = new HashSet<>();
        Set<String> chat2 = new HashSet<>();
        Set<String> chat3 = new HashSet<>();

        chat1.add("Bob");
        chat2.add("Alice");
        chat3.add("Clara");

        String[] senders1 = chats.getSenders(chat1);
        String[] senders2 = chats.getSenders(chat2);
        String[] senders3 = chats.getSenders(chat3);
        String[] messages1 = chats.getMessages(chat1);
        String[] messages2 = chats.getMessages(chat2);
        String[] messages3 = chats.getMessages(chat3);

        ArrayList<String> log = new ArrayList<>();

        for(int i = 0; i < senders1.length; i++){
            log.add(senders1[i] + ": " + messages1[i]);
        }
        for(int i = 0; i < senders2.length; i++){
            log.add(senders2[i] + ": " + messages2[i]);
        }
        for(int i = 0; i < senders3.length; i++){
            log.add(senders3[i] + ": " + messages3[i]);
        }

        String [] result = log.toArray(new String[0]);

        for(File f: tempFiles){
            f.delete();
        }
        assertArrayEquals( messageUnits, result );
    }

    @Test
    public void nothingToRestoreTest(){ //Prüft nach on es eine Exception kommt, wenn es keine Daten zum wiederherstellen gibt
        chats = ChatsImpl.getInstance(context);
    }

    @Test
    public void delete() {
    }

    @Test
    public void getSendersTest() throws IOException {
        chats = ChatsImpl.getInstance(context);

        Set<String> chatMembers = new HashSet();
        String senderString = "Tester";
        String[] sender = new String[3];
        String message = "Hello";

        for(int i = 0; i < sender.length; i++){
            sender[i] = senderString + (i+1);
            chatMembers.add(sender[i]);
        }

        for(int i = 0; i < sender.length; i++){
            chats.save(sender[i], message, chatMembers);
        }

        String[] expected = {"Tester1", "Tester2", "Tester3"};
        String[] result = chats.getSenders(chatMembers);
        assertArrayEquals( expected, result );
    }

    @Test
    public void getMessagesTest() throws IOException {
        chats = ChatsImpl.getInstance(context);

        Set<String> chatMembers = new HashSet();
        String sender = "Sender";
        String[] message = new String[3];
        String messageString = "Message";

        for(int i = 0; i < message.length; i++){
            message[i] = messageString + (i+1);
            chatMembers.add(message[i]);
        }

        for(int i = 0; i < message.length; i++){
            chats.save(sender, message[i], chatMembers);
        }

        String[] expected = {"Message1", "Message2", "Message3"};
        String[] result = chats.getMessages(chatMembers);
        assertArrayEquals(expected, result);
    }

    @Test
    public void getParticipentsTest() {
        chats = ChatsImpl.getInstance(context);

        Set<String> chatMembers1 = new HashSet();
        Set<String> chatMembers2 = new HashSet();
        Set<String> chatMembers3 = new HashSet();
        String sender = "Sender";
        chatMembers1.add(sender + 1);
        chatMembers1.add(sender + 2);
        chatMembers2.add(sender + 3);
        chatMembers2.add(sender + 4);
        chatMembers3.add(sender + 5);
        chatMembers3.add(sender + 6);
        String[] message = new String[3];
        String messageString = "Message";

        for(int i = 0; i < message.length; i++){
            message[i] = messageString + (i+1);
        }

        for(int i = 0; i < message.length; i++){
            chats.save(sender, message[i], chatMembers1);
            chats.save(sender, message[i], chatMembers2);
            chats.save(sender, message[i], chatMembers3);
        }

        String[] expected = {"Sender6, Sender5", "Sender4, Sender3", "Sender2, Sender1"};
        String[] result = chats.getParticipents();
        for(String s : result){
            System.out.println(s);
        }
        assertArrayEquals( expected, result );

    }

    @Test
    public void getLastMessagesTest() {
        chats = ChatsImpl.getInstance(context);

        Set<String> chatMembers1 = new HashSet();
        Set<String> chatMembers2 = new HashSet();
        Set<String> chatMembers3 = new HashSet();
        String sender = "Sender";
        chatMembers1.add(sender + 1);
        chatMembers1.add(sender + 2);
        chatMembers2.add(sender + 3);
        chatMembers2.add(sender + 4);
        chatMembers3.add(sender + 5);
        chatMembers3.add(sender + 6);
        String[] message = new String[3];
        String messageString = "Message";

        for(int i = 0; i < message.length; i++){
            message[i] = messageString + (i+1);
        }

        for(int i = 0; i < message.length; i++){
            chats.save(sender, message[i], chatMembers1);
            chats.save(sender, message[i], chatMembers2);
            chats.save(sender, message[i], chatMembers3);
        }

        String[] expected = {"Message3", "Message3", "Message3"};
        String[] result = chats.getLastMessages();
        for(String s : result){
            System.out.println(s);
        }
        assertArrayEquals( expected, result );
    }

}