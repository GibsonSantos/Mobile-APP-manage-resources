package com.example.manageresourceshome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    private appDBAdapter gAdapter;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_spending);

        builder = new AlertDialog.Builder(this);

    }

    public void btnInsertCurrentDay(View view){
        // creates the explicit intent
        Boolean update = false; //esta variavel verifica se já foram inseridos dados nesse dia
        gAdapter = new appDBAdapter(this);
        gAdapter.open();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(date);
        /*if(gAdapter.verifyIfAlreadyInsertSpending(dateString).getCount()!=0){
            builder.setMessage("O consumo de hoje já foi inserido! \nSe deseja editar siga para Menu->Visualizar consumo->Consumos diários")
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
            Intent intent = new Intent(getApplicationContext(), InsertSpendingCurrentDay.class);
            startActivityForResult(intent, reqCode);
        }*/
        Intent intent = new Intent(getApplicationContext(), InsertSpendingCurrentDay.class);
        startActivityForResult(intent, reqCode);
        gAdapter.close();
    }
}