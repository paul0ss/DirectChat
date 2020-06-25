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

import com.example.chatwifi_direct.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    String[] participents = {"Cristina", "Nikita", "Aaron"};
    String[] lastMessages = {"ich liebe dich;*", "Banger", "lass saufen!"};
    Integer[] pictures = {R.drawable.ic_action_avatar,R.drawable.ic_action_avatar,R.drawable.ic_action_avatar};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        CustomListAdapter adapter = new CustomListAdapter(this, participents, lastMessages, pictures);
        listView = (ListView) findViewById(R.id.listviewID);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                //String message = nameArray[position];
                //intent.putExtra("animal", message);
                startActivity(intent);
            }
        });
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
