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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContactsActivity extends AppCompatActivity {
    String[] nameArray = {"Cristina","Aaron","Nikita"};

    String[] macArray = {
            "<43:f2:e3:ab:76:fa>",
            "<98:c4:d6:ca:81:44>",
            "<13:37:ae:6d:5d:c1>",
    };

    Integer[] pictures = {R.drawable.ic_action_avatar,R.drawable.ic_action_avatar,R.drawable.ic_action_avatar};
    ListView listView;

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

        ContactsListAdapter adapter = new ContactsListAdapter(this, nameArray, macArray, pictures);
        listView = (ListView) findViewById(R.id.contactsListviewID);
        listView.setAdapter(adapter);
    }
}
