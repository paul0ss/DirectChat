package com.example.chatwifi_direct.gui;

//import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatwifi_direct.R;

public class MessageListAdapter extends ArrayAdapter {

    private final AppCompatActivity context;

    private String[] senderArray;

    private String[] messagesArray;


    public MessageListAdapter(AppCompatActivity context, String[] senderArray, String[] messagesArray){
        super(context, R.layout.messages_listview_row , messagesArray);

        this.context = context;
        this.senderArray = senderArray;
        this.messagesArray = messagesArray;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.messages_listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView senderTextField = (TextView) rowView.findViewById(R.id.senderViewID);
        TextView messageTextField = (TextView) rowView.findViewById(R.id.messageViewID);

        //this code sets the values of the objects to values from the arrays
        senderTextField.setText(senderArray[position]);
        messageTextField.setText(messagesArray[position]);

        return rowView;

    };

    public void setSenderArray(String [] s){
        senderArray = s;
    }

    public void setMessagesArray(String [] s){
        messagesArray = s;
    }
}
