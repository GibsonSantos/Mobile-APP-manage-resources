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
        //essa função verifica se o mês acabou e se iniciou um novo, logo os dados dos dias do mes anterior vao ser apagados
        updateTable();

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
                    long result = gAdapter.insertDaySpending(new DaySpending(dateString,parseFloat(water.getText().toString()), parseFloat(gas.getText().toString()),parseFloat(energy.getText().toString())));
                    if(result < 0){
                        Toast.makeText(getApplicationContext(),"Naõ foi possivel guardar os dados",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Dados do jogo guardados!",Toast.LENGTH_SHORT).show();
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
            }else{

            }
            gAdapter.clearDataMount();
        }
        gAdapter.close();
    }


}