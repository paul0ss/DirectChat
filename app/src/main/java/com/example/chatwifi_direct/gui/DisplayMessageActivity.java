package com.example.chatwifi_direct.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chatwifi_direct.R;
import com.example.chatwifi_direct.chatMemory.Chats;
import com.example.chatwifi_direct.chatMemory.ChatsImpl;

import java.util.HashSet;
import java.util.Set;

public class DisplayMessageActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.chatwifi_direct.MESSAGE";
    Chats chats;
    ListView listView;
    MessageListAdapter adapter;
    String myMessage;
    String[] members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        chats = ChatsImpl.getInstance(MainActivity.obj); //Context of MainActivity

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        //Sets the Tittle of the Activity to the name of Chat participents
        members = intent.getStringArrayExtra("members");
        String title = "";
        Set<String> m = new HashSet<>();
        for(String s : members){
            title = title + s + " ";
            m.add(s);
        }
        this.setTitle(title);


        listView = (ListView) findViewById(R.id.messagesListViewID);
        adapter = new MessageListAdapter(DisplayMessageActivity.this, chats.getSenders(m), chats.getMessages(m));
        listView.setAdapter(adapter);


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                myMessage = editText.getText().toString();
                chats.save("Me", myMessage, m);
                adapter = new MessageListAdapter(DisplayMessageActivity.this, chats.getSenders(m), chats.getMessages(m));
                listView.setAdapter(adapter);
            }
        });
    }

}
