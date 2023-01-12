package com.example.manageresourceshome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    private int reqCode = 1; // requestCode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void btnInsertData(View view){
        // creates the explicit intent
        Intent intent = new Intent(getApplicationContext(), InsertSpending.class);
        startActivityForResult(intent, reqCode);
    }

    public void btnViewConsumption(View view){
        // creates the explicit intent
        Intent intent = new Intent(getApplicationContext(), MenuDataView.class);
        startActivityForResult(intent, reqCode);
    }

    public void btnSpedingGoal(View view){
        // creates the explicit intent
        Intent intent = new Intent(getApplicationContext(), SpedingGoal.class);
        startActivityForResult(intent, reqCode);
    }
}