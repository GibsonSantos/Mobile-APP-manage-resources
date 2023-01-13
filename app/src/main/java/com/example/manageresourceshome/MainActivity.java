package com.example.manageresourceshome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int reqCode = 1; // requestCode
    private String prefsName = "userPrefsAPP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // obtains the SharedPreferences object
        SharedPreferences prefs=getSharedPreferences(prefsName, MODE_PRIVATE);

        // reads from sharedPreferences
        // The second argument defines the return value if it does not exist
        String aUserName = prefs.getString("userName", null);

        TextView userName = (TextView) findViewById(R.id.text_name_user);

        if(aUserName!=null){
            // writes to the left EditText
            userName.setText(aUserName);
        }
    }

    public void btnStart(View view){
        // creates the explicit intent
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivityForResult(intent, reqCode);
    }
}