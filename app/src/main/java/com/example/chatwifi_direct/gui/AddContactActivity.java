package com.example.chatwifi_direct.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatwifi_direct.R;

public class AddContactActivity extends AppCompatActivity {
    Button save;
    ImageView picture;
    EditText nameView;
    TextView macView;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        save = findViewById(R.id.button2);
        picture = findViewById(R.id.imageView);
        nameView = findViewById(R.id.editText4);
        macView = findViewById(R.id.macAdressView2);

        Intent intent = getIntent();
        String mac = intent.getStringExtra("mac");
        name = intent.getStringExtra("name");
        nameView.setText(name);
        macView.setText(mac);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameView.getText().toString().trim();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Contact "+name+ " saved!",
                        Toast.LENGTH_SHORT);
                toast.show();
                Intent newIntent = new Intent(AddContactActivity.this, PeersActivity.class);
                startActivity(newIntent);
            }
        });

    }
}
