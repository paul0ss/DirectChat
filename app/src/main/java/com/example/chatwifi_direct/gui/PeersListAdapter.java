package com.example.chatwifi_direct.gui;

//import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatwifi_direct.R;

import java.util.ArrayList;

public class PeersListAdapter extends ArrayAdapter {

    private final AppCompatActivity context;

    private final String[] nameArray;

    private final String[] macArray;

    CheckBox checkBoxAdapter;
    ArrayList<String> checkedMacs = new ArrayList<String>();
    ArrayList<String> checkedNames = new ArrayList<String>();
    ArrayList<Integer> checkedIDs = new ArrayList<Integer>();

    public PeersListAdapter(AppCompatActivity context, String[] nameArray, String[] macArray){
        super(context, R.layout.listview_peers, nameArray);
        this.context = context;
        this.nameArray = nameArray;
        this.macArray = macArray;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_peers, null,true);

        //this code gets references to objects in the listview_row.xml file
        final TextView peersTextField = (TextView) rowView.findViewById(R.id.peerViewID);
        TextView macAdressTextField = (TextView) rowView.findViewById(R.id.macView);

        //this code sets the values of the objects to values from the arrays
        final String currentName = nameArray[position];
        final String currentMac = macArray[position];
        peersTextField.setText(nameArray[position]);
        macAdressTextField.setText(macArray[position]);

        checkBoxAdapter = (CheckBox) rowView.findViewById(R.id.checkBox);
        checkBoxAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                if(cb.isChecked()){
                    checkedNames.add(currentName);
                    checkedMacs.add(currentMac);
                    checkedIDs.add(position);
                }else{
                checkedNames.remove(currentName);
                checkedMacs.remove(currentMac);
                checkedIDs.remove(position);
                }
                Toast.makeText(context.getApplicationContext(),
                        "Clicked on Checkbox: " + nameArray[position] +
                                " is " + cb.isChecked(),
                        Toast.LENGTH_LONG).show();

            }
        });
        return rowView;

    };

    public ArrayList<String> getCheckedMacs(){
        return checkedMacs;
    }

    public ArrayList<String> getCheckedNames(){
        return checkedNames;
    }

    public ArrayList<Integer> getCheckedIDs(){ return checkedIDs; }
}
