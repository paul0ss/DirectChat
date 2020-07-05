package com.example.chatwifi_direct.networkController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import com.example.chatwifi_direct.gui.DisplayMessageActivity;
import com.example.chatwifi_direct.gui.PeersActivity;

import java.util.ArrayList;
import java.util.Collection;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {
    WifiP2pManager wifiP2pManager;
    WifiP2pManager.Channel channel;
    PeersActivity activity;

    public WifiDirectBroadcastReceiver(WifiP2pManager wifiP2pManager, WifiP2pManager.Channel channel, PeersActivity activity) {
        this.wifiP2pManager = wifiP2pManager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                //Toast.makeText(activity, "Wifi on", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(activity, "Wifi off", Toast.LENGTH_SHORT).show();
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // The peer list has changed! We should probably do something about
            // that.
            if(wifiP2pManager != null){
                wifiP2pManager.requestPeers(channel, activity.peerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Connection state changed! We should probably do something about
            // that.
            if(wifiP2pManager == null){
                return;
            }

            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            WifiP2pGroup group = (WifiP2pGroup)intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_GROUP);
            activity.setGroup(group);

            if(networkInfo.isConnected()){
                wifiP2pManager.requestConnectionInfo(channel, activity.connectionInfoListener);
            }else{
                System.out.println("Device disconnected");
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }
}
