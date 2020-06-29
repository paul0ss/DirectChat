package com.example.chatwifi_direct;

import android.content.Context;

import com.example.chatwifi_direct.chatMemory.Chat;
import com.example.chatwifi_direct.chatMemory.Chats;
import com.example.chatwifi_direct.chatMemory.ChatsImpl;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {



    @Test
    public void saveTest() {
        Context ctx = Mockito.mock(Context.class);
        Chats chats = ChatsImpl.getInstance(ctx);
        Set<String> members = new HashSet<String>();
        members.add("TestSener");

        chats.save("TestSender", "TestMessage", new HashSet<String>());

        assertEquals(4, 2 + 2);
    }

}