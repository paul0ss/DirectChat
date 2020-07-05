package com.example.chatwifi_direct.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.chatwifi_direct.R;
import com.example.chatwifi_direct.networkController.Network;
import com.example.chatwifi_direct.networkController.WifiDirectBroadcastReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class PeersActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    ListView listView;
    Switch switchWiFi;
    WifiManager mWifiMgr;
    public WifiP2pManager manager;
    public WifiP2pManager.Channel channel;
    public WifiP2pGroup group;
    public Network network;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    PeersListAdapter adapter;
    boolean per = false;

    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 1001;

    List<WifiP2pDevice> peers = new ArrayList<>();
    String[] peersArray;
    WifiP2pDevice[] deviceArray;
    String[] macArray ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peers);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        switchWiFi = (Switch) findViewById(R.id.switch1);
        mWifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        manager = (WifiP2pManager) getApplicationContext().getSystemService(WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        mReceiver = new WifiDirectBroadcastReceiver(manager, channel, this);

        getWifiPermissions();

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

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


        switchWiFi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    mWifiMgr.setWifiEnabled(true);
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //TODO:getWifi();
                    if(per = true){
                        searchPeers();
                    }
                }else{
                    manager.stopPeerDiscovery(channel, new WifiP2pManager.ActionListener() {
                        @Override
                        public void onSuccess() {
                            System.out.println("Discovery stopped");
                            Toast.makeText(PeersActivity.this, "Discovery stopped", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int reason) {
                            System.out.println("Discovery failed");
                            Toast.makeText(PeersActivity.this, "Discovery failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mWifiMgr.setWifiEnabled(false);
                    //peers.clear();
                }
            }
        });

        if(mWifiMgr.isWifiEnabled() == true){
            switchWiFi.setChecked(true);
        }else{
            switchWiFi.setChecked(false);
        }
    }

    public WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if(!peerList.getDeviceList().equals(peers)){
                peers.clear();
                peers.addAll(peerList.getDeviceList());
                peersArray = new String[peerList.getDeviceList().size()];
                macArray = new String[peerList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];

                int i = 0;
                for(WifiP2pDevice device : peerList.getDeviceList()){
                    peersArray[i] = device.deviceName;
                    macArray[i] = device.deviceAddress;
                    deviceArray[i] = device;
                    i++;
                }
                adapter = new PeersListAdapter(PeersActivity.this, peersArray, macArray);
                listView = (ListView) findViewById(R.id.peersListViewID);
                listView.setAdapter(adapter);
            }
            if(peers.size() == 0){
                Toast.makeText(getApplicationContext(), "No Device Found", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0x12345) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            per = true;
        }
    }

    public void getWifiPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0x12345);
            }
        }
    }

    public void searchPeers(){
        //mWifiMgr.setWifiEnabled(true);
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Discovery started");
                Toast.makeText(PeersActivity.this, "Discovery started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                System.out.println("Discovery failed");
                Toast.makeText(PeersActivity.this, "Discovery failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            final InetAddress groupOwnerAdress = info.groupOwnerAddress;
            if(info.groupFormed && info.isGroupOwner){
                System.out.println("Host");
            }else if(info.groupFormed){
                WifiP2pDevice owner = group.getOwner();
                ArrayList<String> connectedNames = new ArrayList<>();
                String name = owner.deviceName;
                connectedNames.add(name);
                Intent intent2 = new Intent(PeersActivity.this, DisplayMessageActivity.class);
                intent2.putExtra("members", connectedNames.toArray(new String[0]));
                PeersActivity.this.startActivity(intent2);
                System.out.println("Client");
            }
        }
    };

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
        ArrayList<Integer> checkedIDs = adapter.getCheckedIDs();
        switch(item.getItemId()){
            case R.id.item1://Chat
                if(!checkedNames.isEmpty()){
                    if(checkedIDs.size() == 1){
                        final WifiP2pDevice device = deviceArray[checkedIDs.get(0)];
                        WifiP2pConfig config = new WifiP2pConfig();
                        config.deviceAddress = device.deviceAddress;

                        manager.connect(channel, config, new WifiP2pManager.ActionListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(PeersActivity.this, "Connected to "+ device.deviceName, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int reason) {
                                Toast.makeText(PeersActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if(checkedIDs.size() > 1){

                    }
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

    public void setGroup(WifiP2pGroup group){
        this.group = group;
    }

}