package com.example.manageresourceshome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.time.LocalDate;

public class Menu extends AppCompatActivity {

    private int reqCode = 1; // requestCode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        LocalDate now = LocalDate.now(); // 2015-11-24
        LocalDate earlier = now.minusMonths(1); // 2015-10-24

        System.out.println(earlier.getMonth());

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

    public void btnConfAPP(View view){
        // creates the explicit intent
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivityForResult(intent, reqCode);
    }

    public void btnAboutAPP(View view){
        // creates the explicit intent
        Intent intent = new Intent(getApplicationContext(), AboutAPP.class);
        startActivityForResult(intent, reqCode);
    }
}