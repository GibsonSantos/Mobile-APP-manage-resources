package com.example.manageresourceshome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuDataView extends AppCompatActivity {
    private int reqCode = 1; // requestCode


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu_data_view);
    }

    public void btnDaysSpeding(View view){
        // creates the explicit intent
        Intent intent = new Intent(getApplicationContext(), ListDaysSpending.class);
        startActivityForResult(intent, reqCode);
    }
}