package com.example.chatwifi_direct.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chatwifi_direct.R;
import com.example.chatwifi_direct.chatMemory.Chats;
import com.example.chatwifi_direct.chatMemory.ChatsImpl;
import com.example.chatwifi_direct.chatMemory.ContactsImpl;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    String[] participents = null;
    String[] lastMessages = null;
    ListView listView;
    public static MainActivity obj;
    ContactsImpl contacts;
    Chats chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obj = this;

        bottomNavigation = findViewById(R.id.bottom_navigation);

        Menu menu = bottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case(R.id.action_chats):


                        break;
                    case(R.id.action_peers):
                        Intent intent1 = new Intent(MainActivity.this, PeersActivity.class);
                        startActivity(intent1);
                        break;
                    case(R.id.action_contacts):
                        Intent intent2 = new Intent(MainActivity.this, ContactsActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        contacts = ContactsImpl.getContacsInstance(this);
        chats = ChatsImpl.getInstance(this);
/*        if(restoreTime == 0){
            contacts.restore();
            chats.restore();
            restoreTime++;
        }*/
        participents = chats.getParticipents();
        lastMessages = chats.getLastMessages();

/*        System.out.println("--------------------------------------------");
        if(participents.length != 0){
            System.out.println("--------------------------------------------");
            System.out.println(participents[0]);
        }*/
        if(participents.length != 0 &&  lastMessages.length != 0) {
            CustomListAdapter adapter = new CustomListAdapter(this, participents, lastMessages, chats.getPictures());
            listView = (ListView) findViewById(R.id.listviewID);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view.findViewById(R.id.chatTextViewID);
                    String clicked = textView.getText().toString();
                    String[] members = clicked.replaceAll("^[.,\\s]+", "").split("[.,\\s]+");

                    Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                    intent.putExtra("members", members);
                    startActivity(intent);
                }
            });
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_chats:
                //addSomething();
                return true;
            case R.id.action_peers:
                //startSettings();
                return true;
            case R.id.action_contacts:
                return true;
            default:
                return false;
        }
    }*/

}
