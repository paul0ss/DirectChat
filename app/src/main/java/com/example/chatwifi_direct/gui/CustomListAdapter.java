package com.example.chatwifi_direct.gui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatwifi_direct.R;

public class CustomListAdapter extends ArrayAdapter {

    private final AppCompatActivity context;

    private final Integer[] imageIDarray;

    private final String[] participentsArray;

    private String[] clickedMembers = new String[1];

    private final String[] lastMessagesArray;


    public CustomListAdapter(AppCompatActivity context, String[] chatsArray, String[] lastMessagesArray, Integer[] imageIDArray){
        super(context, R.layout.listview_row , chatsArray);

        this.context = context;
        this.imageIDarray = imageIDArray;
        this.participentsArray = chatsArray;
        this.lastMessagesArray = lastMessagesArray;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        TextView participentsTextField = (TextView) rowView.findViewById(R.id.chatTextViewID);
        TextView lastMessageTextField = (TextView) rowView.findViewById(R.id.lastMessageViewID);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);

        participentsTextField.setText(participentsArray[position]);
        lastMessageTextField.setText(lastMessagesArray[position]);
        imageView.setImageResource(imageIDarray[position]);

        return rowView;

    };

    public String[] getMembers(){
        return clickedMembers;
    }

    public String[] getLastMessages(){
        return lastMessagesArray;
    }
}
