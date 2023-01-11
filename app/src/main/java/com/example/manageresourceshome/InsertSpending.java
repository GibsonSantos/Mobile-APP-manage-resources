package com.example.manageresourceshome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InsertSpending extends AppCompatActivity {

    private int reqCode = 1; // requestCode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_spending);
    }

    public void btnInsertCurrentDay(View view){
        // creates the explicit intent
        Intent intent = new Intent(getApplicationContext(), InsertSpendingCurrentDay.class);
        startActivityForResult(intent, reqCode);
    }
}