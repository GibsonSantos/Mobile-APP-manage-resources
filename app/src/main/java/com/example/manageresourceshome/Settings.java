package com.example.manageresourceshome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Settings extends AppCompatActivity {

    private int reqCode = 1; // requestCode
    private Button btn_reset_days;
    private Button btn_reset_months;
    private appDBAdapter gAdapter;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        gAdapter = new appDBAdapter(this);
        builder = new AlertDialog.Builder(this);

        btn_reset_days = (Button) findViewById(R.id.btn_reset_data_days);
        btn_reset_months = (Button) findViewById(R.id.btn_reset_data_month);

        btn_reset_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setMessage("Deseja mesmo apagar os dados armazenados de consumo diário?")
                        .setCancelable(false)
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                gAdapter.open();
                                gAdapter.clearDataMount();
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
                                String StringMonth = sdf.format(c.getTime());
                                gAdapter.clearMonthSpeding(StringMonth);
                                gAdapter.close();
                                Toast.makeText(getApplicationContext(),"Dados apagados!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //cancela o dialogo caso o utilizador deseje continuar no jogo
                                dialog.cancel();
                            }
                        });
                //Cria um caixa de dialogo
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("ALERTA");
                alert.show();
            }
        });

        btn_reset_months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setMessage("Deseja mesmo apagar os dados armazenados de consumo por mês?")
                        .setCancelable(false)
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                gAdapter.open();
                                gAdapter.clearDataYear();
                                gAdapter.close();
                                Toast.makeText(getApplicationContext(),"Dados apagados!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //cancela o dialogo caso o utilizador deseje continuar no jogo
                                dialog.cancel();
                            }
                        });
                //Cria um caixa de dialogo
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("ALERTA");
                alert.show();
            }
        });
    }

    public void btnConfNAME(View view){
        // creates the explicit intent
        Intent intent = new Intent(getApplicationContext(), ConfigName.class);
        startActivityForResult(intent, reqCode);
    }
}