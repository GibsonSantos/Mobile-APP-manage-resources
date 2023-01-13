package com.example.manageresourceshome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class monthStatisticsView extends AppCompatActivity {
    private appDBAdapter gAdapter;
    private AlertDialog.Builder builder;
    private TextView month;
    private String StringMonth;
    private TextView allSpendWater;
    private TextView allSpendGas;
    private TextView allSpendEnergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_statistics_view);

        gAdapter = new appDBAdapter(this);
        builder = new AlertDialog.Builder(this);

        allSpendEnergy = findViewById(R.id.text_spend_energy_statistics);
        allSpendWater = findViewById(R.id.text_spend_water_statistics);
        allSpendGas = findViewById(R.id.text_spend_gas_statistics);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
        StringMonth = sdf.format(c.getTime());

        month = (TextView) findViewById(R.id.textMonthStatistics);
        month.setText(StringMonth);

        getAllSpeding();

    }

    public void getAllSpeding(){
        gAdapter.open();
        Cursor cursor = gAdapter.getAllSpedingMonth(StringMonth);
        if(cursor.getCount()!=0){

            cursor.moveToFirst();
            allSpendEnergy.setText(cursor.getString(2));
            allSpendGas.setText(cursor.getString(4));
            allSpendWater.setText(cursor.getString(6));

        }else{
            builder.setMessage("Ainda não existe consumo inseridos para o mês atual!")
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
        gAdapter.close();
    }
}