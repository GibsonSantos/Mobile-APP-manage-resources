package com.example.manageresourceshome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private int reqCode = 1; // requestCode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void btnStart(View view){
        // creates the explicit intent
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivityForResult(intent, reqCode);
    }
}