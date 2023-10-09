package com.example.manageresourceshome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class Menu extends AppCompatActivity {
    private appDBAdapter gAdapter;
    private int reqCode = 1; // requestCode
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        gAdapter = new appDBAdapter(this);
        builder = new AlertDialog.Builder(this);

    }

    public void btnInsertData(View view){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
        String StringMonth = sdf.format(c.getTime());
        // creates the explicit intent
        gAdapter.open();
        Cursor cursor = gAdapter.verifyIfAlreadyInsertMonthGoal(StringMonth);
        if(cursor.getCount()!=0){
            Intent intent = new Intent(getApplicationContext(), InsertSpending.class);
            startActivityForResult(intent, reqCode);
        }else{
            builder.setMessage("You still don't have consumption goals for this month, please enter them so you can enter your consumption!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("ALERTA");
            alert.show();
        }
        gAdapter.close();
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
