package com.example.manageresourceshome;

import static java.lang.Float.parseFloat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditSpedingDay extends AppCompatActivity {

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

        Intent iIn = getIntent();  
        Bundle bIn = iIn.getExtras(); 

       //gets the names of the players, as well as the tournament

        textViewDate = findViewById(R.id.msg_current_day);
        textViewDate.setText(bIn.getString("daySpeding"));
        water.setText(bIn.getString("spedingWater"));
        gas.setText(bIn.getString("spedingGas"));
        energy.setText(bIn.getString("spedingEnergy"));

        btnSalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(water.getText().toString().length()==0 || gas.getText().toString().length()==0 || energy.getText().toString().length()==0 ){
                    builder.setMessage("NÃO PODEM EXISTIR CAMPOS VAZIOS!")
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
                    gAdapter.open();
                    int result = gAdapter.updateDaySpeding(new DaySpending(bIn.getString("daySpeding"),parseFloat(water.getText().toString()), parseFloat(gas.getText().toString()),parseFloat(energy.getText().toString())));
                    if(result < 0){
                        Toast.makeText(getApplicationContext(),"Unable to save data",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Saved!",Toast.LENGTH_SHORT).show();
                        updateTableMonth();//atualiza  os gastos da tabela com gastos do mes inteiro
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

    public void updateTableMonth(){
        gAdapter.open();
        //obtem os dados do ultimo mes inserido pelo utilizador
        Cursor cursor = gAdapter.getLastMonth();
        if(cursor.getCount()!=0){
            cursor.moveToLast();
            Cursor cursorAllSpeding = gAdapter.getAllSpedingMonth();
            //obtem as estatisticas do ultimo mes
            if(cursorAllSpeding.getCount()!=0){
                cursorAllSpeding.moveToFirst();
                gAdapter.insertAllDatasMonth(cursor.getString(0),Float.parseFloat(cursorAllSpeding.getString(1)),Float.parseFloat(cursorAllSpeding.getString(2)),Float.parseFloat(cursorAllSpeding.getString(3)));
            }
        }
        gAdapter.close();
    }
}
