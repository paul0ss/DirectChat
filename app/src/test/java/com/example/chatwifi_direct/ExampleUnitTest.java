package com.example.chatwifi_direct;

import android.content.Context;
import static org.mockito.Mockito.*;

import com.example.chatwifi_direct.chatMemory.Chats;
import com.example.chatwifi_direct.chatMemory.ChatsImpl;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Rule
    public TemporaryFolder mTempFolder = new TemporaryFolder();
    Context context = mock(Context.class);
    Chats chats;


    @Test
    public void saveMessageTest() throws IOException {
        when(context.getFilesDir()).thenReturn(mTempFolder.newFolder());
        Chats chats = ChatsImpl.getInstance(context);
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
        System.out.println(messageUnitStored);
        String[] senders = chats.getSenders(chatMembers);
        String[] messagges = chats.getMessages(chatMembers);
        String messageUnit = senders[0] + ": " + messagges[0];
        assertEquals(expectedSavedMessage, messageUnitStored);
        assertEquals(expectedSavedMessage, messageUnit);
    }

}