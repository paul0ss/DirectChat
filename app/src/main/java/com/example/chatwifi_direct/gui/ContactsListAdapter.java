package com.example.chatwifi_direct.gui;

//import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatwifi_direct.R;

public class ContactsListAdapter extends ArrayAdapter {

    private final AppCompatActivity context;

    private final Integer[] imageIDarray;

    private final String[] nameArray;

    private final String[] macArray;


    public ContactsListAdapter(AppCompatActivity context, String[] nameArray, String[] macArray, Integer[] imageIDArray){
        super(context, R.layout.listview_contacts , nameArray);

        this.context = context;
        this.imageIDarray = imageIDArray;
        this.nameArray = nameArray;
        this.macArray = macArray;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_contacts, null,true);

        TextView contactsTextField = (TextView) rowView.findViewById(R.id.contactNameViewID);
        TextView macAdressTextField = (TextView) rowView.findViewById(R.id.macAdressViewID);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.contactPictureView1ID);

        contactsTextField.setText(nameArray[position]);
        macAdressTextField.setText(macArray[position]);
        imageView.setImageResource(imageIDarray[position]);

        return rowView;

    };
}
