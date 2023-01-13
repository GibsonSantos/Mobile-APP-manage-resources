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
    private TextView averageWater;
    private TextView averageGas;
    private TextView averageEnergy;
    private TextView goalWater;
    private TextView goalGas;
    private TextView goalEnergy;
    private Float sumSpentWater;
    private Float sumSpentGas;
    private Float sumSpentEnergy;
    private int countSpentDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_statistics_view);

        gAdapter = new appDBAdapter(this);
        builder = new AlertDialog.Builder(this);

        allSpentEnergy = (TextView) findViewById(R.id.text_spend_energy_statistics);
        allSpentWater = (TextView) findViewById(R.id.text_spend_water_statistics);
        allSpentGas = (TextView) findViewById(R.id.text_spend_gas_statistics);
        titleDays = (TextView) findViewById(R.id.text_days_consumidos);
        averageWater = (TextView) findViewById(R.id.text_average_water);
        averageGas = (TextView) findViewById(R.id.text_average_gas);
        averageEnergy = (TextView) findViewById(R.id.text_average_energy);
        goalWater = (TextView) findViewById(R.id.text_water_goal);
        goalGas = (TextView) findViewById(R.id.text_gas_goal);
        goalEnergy = (TextView) findViewById(R.id.text_energy_goal);



        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
        StringMonth = sdf.format(c.getTime());

        month = (TextView) findViewById(R.id.textMonthStatistics);
        month.setText(StringMonth);

        //carrega e escreves os dados na activity
        getAllSpeding();
        updateAverage();
        getAllGoals();

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

            countSpentDays = Integer.parseInt(cursor.getString(0));
            sumSpentEnergy = Float.parseFloat(cursor.getString(3));
            sumSpentGas = Float.parseFloat(cursor.getString(2));
            sumSpentWater = Float.parseFloat(cursor.getString(1));
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

    public void updateAverage(){
        averageWater.setText(""+sumSpentWater/countSpentDays);
        averageGas.setText(""+sumSpentGas/countSpentDays);
        averageEnergy.setText(""+sumSpentEnergy/countSpentDays);
    }
    //recebe e escreve todas as metas presentas na base de dados
    public void getAllGoals(){
        gAdapter.open();
        Cursor cursor = gAdapter.verifyIfAlreadyInsertMonthGoal(StringMonth);
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            goalWater.setText(cursor.getString(2));
            goalGas.setText(cursor.getString(4));
            goalEnergy.setText(cursor.getString(6));
        }else{
            builder.setMessage("Ainda não existe metas inseridas para o mês atual!")
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