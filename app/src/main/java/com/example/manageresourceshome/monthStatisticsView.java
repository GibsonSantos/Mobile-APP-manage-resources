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
    private TextView allSpentWater;
    private TextView allSpentGas;
    private TextView allSpentEnergy;
    private TextView titleDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_statistics_view);

        gAdapter = new appDBAdapter(this);
        builder = new AlertDialog.Builder(this);

        allSpentEnergy = findViewById(R.id.text_spend_energy_statistics);
        allSpentWater = findViewById(R.id.text_spend_water_statistics);
        allSpentGas = findViewById(R.id.text_spend_gas_statistics);
        titleDays = findViewById(R.id.text_days_consumidos);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
        StringMonth = sdf.format(c.getTime());

        month = (TextView) findViewById(R.id.textMonthStatistics);
        month.setText(StringMonth);

        getAllSpeding();

    }

    public void getAllSpeding(){
        gAdapter.open();
        Cursor cursor = gAdapter.getAllSpedingMonth();
        if(cursor.getCount()!=0){

            cursor.moveToFirst();
            titleDays.setText("Dias consumidos: "+cursor.getString(0));
            allSpentEnergy.setText(cursor.getString(3));
            allSpentGas.setText(cursor.getString(2));
            allSpentWater.setText(cursor.getString(1));

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