package com.example.chatwifi_direct.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.chatwifi_direct.R;
import com.example.chatwifi_direct.chatMemory.Contact;
import com.example.chatwifi_direct.chatMemory.Contacts;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContactsActivity extends AppCompatActivity {
    ListView listView;
    Contacts contacts;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        Menu menu = bottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case(R.id.action_chats):
                        Intent intent1 = new Intent(ContactsActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case(R.id.action_peers):
                        Intent intent2 = new Intent(ContactsActivity.this, PeersActivity.class);
                        startActivity(intent2);
                        break;
                    case(R.id.action_contacts):

                        break;
                }
                return true;
            }
        });

        contacts = Contacts.getContacsInstance(MainActivity.obj);
        ContactsListAdapter adapter = new ContactsListAdapter(this, contacts.getNames(), contacts.getMacs(), contacts.getImage());
        listView = (ListView) findViewById(R.id.contactsListviewID);
        listView.setAdapter(adapter);
    }
}
