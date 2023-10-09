package com.example.manageresourceshome;

import static java.lang.Float.parseFloat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InsertSpendingCurrentDay extends AppCompatActivity {

    private appDBAdapter gAdapter;
    private TextView textViewDate;
    private Button btnSalve;
    private Button btnCancel;
    private EditText water;
    private EditText gas;
    private EditText energy;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_spending_current_day);

        gAdapter = new appDBAdapter(this);

        btnSalve = (Button) findViewById(R.id.btn_salve);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        water = (EditText) findViewById(R.id.edit_insert_water);
        gas = (EditText) findViewById(R.id.edit_insert_gas);
        energy = (EditText) findViewById(R.id.edit_insert_energy);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(date);

        textViewDate = findViewById(R.id.msg_current_day);
        textViewDate.setText(dateString);

        builder = new AlertDialog.Builder(this);
        //this function checks whether the month has ended and a new one has started, so data from the days of the previous month will be deleted
        updateTable();

        btnSalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(water.getText().toString().length()==0 || gas.getText().toString().length()==0 || energy.getText().toString().length()==0 ){
                    builder.setMessage("THERE CANNOT BE EMPTY FIELDS!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("ALERTA");
                    alert.show();
                }else{
                    gAdapter.open();
                    long result = gAdapter.insertDaySpending(new DaySpending(dateString,parseFloat(water.getText().toString()), parseFloat(gas.getText().toString()),parseFloat(energy.getText().toString())));
                    if(result < 0){
                        Toast.makeText(getApplicationContext(),"Unable to save data",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Saved game data!",Toast.LENGTH_SHORT).show();
                        updateTable();
                        // end activit
                        finish();
                    }
                    gAdapter.close();

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void updateTable(){
        gAdapter.open();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //int day = 1;
        if(day==1){
            gAdapter.clearDataMount();
        }
        //gets data from the last month entered by the user
        Cursor cursor = gAdapter.getLastMonth();
        if(cursor.getCount()!=0){
            cursor.moveToLast();
            Cursor cursorAllSpeding = gAdapter.getAllSpedingMonth();
            cursorAllSpeding.moveToFirst();
            //get the statistics for the last month
            if(!cursorAllSpeding.getString(0).equalsIgnoreCase("0")){
                gAdapter.insertAllDatasMonth(cursor.getString(0),Float.parseFloat(cursorAllSpeding.getString(1)),Float.parseFloat(cursorAllSpeding.getString(2)),Float.parseFloat(cursorAllSpeding.getString(3)));
            }
        }
        gAdapter.close();
    }


}
