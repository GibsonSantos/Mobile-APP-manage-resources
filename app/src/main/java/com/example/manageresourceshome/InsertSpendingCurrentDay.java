package com.example.manageresourceshome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InsertSpendingCurrentDay extends AppCompatActivity {

    private TextView textViewDate;
    private Button btnSalve;
    private EditText water;
    private EditText gas;
    private EditText energy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_spending_current_day);

        btnSalve = (Button) findViewById(R.id.btn_salve);
        water = (EditText) findViewById(R.id.edit_insert_water);
        gas = (EditText) findViewById(R.id.edit_insert_gas);
        energy = (EditText) findViewById(R.id.edit_insert_energy);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String gameDateString = dateFormat.format(date);

        textViewDate = findViewById(R.id.msg_current_day);
        textViewDate.setText(gameDateString);

        btnSalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(water.getText().toString())<0 || namePlayer2.getText().toString().length()==0 || nameTorneio.getText().toString().length()==0 ){
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
                }
            }
        });
    }


}