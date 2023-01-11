package com.example.manageresourceshome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InsertSpendingCurrentDay extends AppCompatActivity {

    private TextView textViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_spending_current_day);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String gameDateString = dateFormat.format(date);

        textViewDate = findViewById(R.id.msg_current_day);
        textViewDate.setText(gameDateString);
    }
}