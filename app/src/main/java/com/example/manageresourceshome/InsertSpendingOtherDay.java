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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InsertSpendingOtherDay extends AppCompatActivity {

    private appDBAdapter gAdapter;
    private TextView textViewDate;
    private Button btnSalve;
    private Button btnCancel;
    private EditText water;
    private EditText gas;
    private EditText energy;
    private EditText otherDay;
    private AlertDialog.Builder builder;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_spending_other_day);

        gAdapter = new appDBAdapter(this);

        btnSalve = (Button) findViewById(R.id.btn_salve);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        water = (EditText) findViewById(R.id.edit_insert_water);
        gas = (EditText) findViewById(R.id.edit_insert_gas);
        energy = (EditText) findViewById(R.id.edit_insert_energy);
        otherDay = (EditText) findViewById(R.id.edit_insert_day);


        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(date);

        textViewDate = findViewById(R.id.msg_current_day);
        textViewDate.setText(dateString);

        builder = new AlertDialog.Builder(this);

        btnSalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(water.getText().toString().length()==0 || gas.getText().toString().length()==0 || energy.getText().toString().length()==0 || otherDay.getText().toString().length()==0){
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
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int currentDay = cal.get(Calendar.DAY_OF_MONTH);
                    int daySelect = Integer.parseInt(otherDay.getText().toString());//stores the day selected by the user
                    if(daySelect>currentDay||daySelect<1){
                        builder.setMessage("Invalid day!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
    
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("ALERT");
                        alert.show();
                    }else {
                        int year = Calendar.getInstance().get(Calendar.YEAR);
                        int month = Calendar.getInstance().get(Calendar.MONTH);
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, daySelect);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = dateFormat.format(cal.getTime());
                        gAdapter.open();
                        Cursor curRes = gAdapter.verifyIfAlreadyInsertDaySpeding(dateString);
                        if(curRes.getCount()!=0){
                            builder.setMessage("This date already has your consumption entered! You can change it by viewing daily consumption!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            //Cria um caixa de dialogo
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("ALERTA");
                            alert.show();
                        }else{
                            long result = gAdapter.insertDaySpending(new DaySpending(dateString, parseFloat(water.getText().toString()), parseFloat(gas.getText().toString()), parseFloat(energy.getText().toString())));
                            if (result < 0) {
                                Toast.makeText(getApplicationContext(), "Unable to save data", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Saved game data!", Toast.LENGTH_SHORT).show();
                                updateTableMonth();
                                // end activit
                                finish();
                            }
                        }
                        gAdapter.close();
                    }

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

    public void updateTableMonth(){
        gAdapter.open();
        //gets data from the last month entered by the user
        Cursor cursor = gAdapter.getLastMonth();
        if(cursor.getCount()!=0){
            cursor.moveToLast();
            Cursor cursorAllSpeding = gAdapter.getAllSpedingMonth();
            //get the statistics for the last month
            if(cursorAllSpeding.getCount()!=0){
                cursorAllSpeding.moveToFirst();
                gAdapter.insertAllDatasMonth(cursor.getString(0),Float.parseFloat(cursorAllSpeding.getString(1)),Float.parseFloat(cursorAllSpeding.getString(2)),Float.parseFloat(cursorAllSpeding.getString(3)));
            }
        }
        gAdapter.close();
    }
}
