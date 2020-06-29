package com.example.chatwifi_direct.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.example.chatwifi_direct.R;
import com.example.chatwifi_direct.networkController.WifiDirectBroadcastReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class PeersActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    ListView listView;
    Switch s;
    WifiManager mWifiMgr;
    WifiP2pManager mWifiP2pMgr;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    PeersListAdapter adapter;

    String[] peers = {"Peer1", "Peer2", "Peer3"};
    String[] macArray = {
            "<43:f2:e3:ab:76:fa>",
            "<98:c4:d6:ca:81:44>",
            "<13:37:ae:6d:5d:c1>",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peers);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        s = findViewById(R.id.switch1);


        Menu menu = bottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case(R.id.action_chats):
                        Intent intent1 = new Intent(PeersActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case(R.id.action_peers):

                        break;
                    case(R.id.action_contacts):
                        Intent intent2 = new Intent(PeersActivity.this, ContactsActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        mWifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        mWifiMgr.setWifiEnabled(false);
        mWifiP2pMgr = (WifiP2pManager) getApplicationContext().getSystemService(WIFI_P2P_SERVICE);
        mChannel = mWifiP2pMgr.initialize(this, getMainLooper(), null);
        mReceiver = new WifiDirectBroadcastReceiver(mWifiP2pMgr, mChannel, this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    mWifiMgr.setWifiEnabled(true);
                }else{
                    mWifiMgr.setWifiEnabled(false);
                }
            }
        });

        adapter = new PeersListAdapter(this, peers, macArray);
        listView = (ListView) findViewById(R.id.peersListViewID);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.peers_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        ArrayList<String> checkedMacs = adapter.getCheckedMacs();
        ArrayList<String> checkedNames = adapter.getCheckedNames();
        switch(item.getItemId()){
            case R.id.item1://Chat
                if(!checkedNames.isEmpty()){
                    Intent intent = new Intent(PeersActivity.this, DisplayMessageActivity.class);
                    intent.putExtra("members", checkedNames.toArray(new String[0]));
                    startActivity(intent);
                }
                break;
            case R.id.item2://add Contact
                Intent intent2 = new Intent(PeersActivity.this, AddContactActivity.class);
                if(checkedMacs.isEmpty() || checkedNames.isEmpty()){
                 // wenn nichts ausgewählt wurde: tu nichts
                }else {
                    intent2.putExtra("mac", checkedMacs.get(checkedMacs.size() - 1)); //Optional parameters
                    intent2.putExtra("name", checkedNames.get(checkedNames.size() - 1));
                    startActivity(intent2);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
