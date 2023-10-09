package com.example.manageresourceshome;

import static java.lang.Float.parseFloat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.DateFormatSymbols;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class SpedingGoal extends AppCompatActivity {
    private appDBAdapter gAdapter;
    private TextView month;
    private Button btnCancelSpedingGoal;
    private Button btnSalveSpedingGoal;
    private EditText waterSpedingGoal;
    private EditText gasSpedingGoal;
    private EditText energySpedingGoal;
    private AlertDialog.Builder builder;
    private String StringMonth;
    private boolean alreadyGols = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speding_goal);

        gAdapter = new appDBAdapter(this);

        btnCancelSpedingGoal = findViewById(R.id.btn_cancel_speding_goal);
        btnSalveSpedingGoal = findViewById(R.id.btn_salve_speding_goal);
        waterSpedingGoal = findViewById(R.id.edit_water_speding_goal);
        gasSpedingGoal = findViewById(R.id.edit_gas_speding_goal);
        energySpedingGoal = findViewById(R.id.edit_energy_speding_goal);

        builder = new AlertDialog.Builder(this);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
        StringMonth = sdf.format(c.getTime());

        month = (TextView) findViewById(R.id.txt_month_speding_goal);
        month.setText(StringMonth);

        //loads the goals already stipulated so that these months already exist
        loadGoals();

        btnCancelSpedingGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSalveSpedingGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(waterSpedingGoal.getText().toString().length()==0 || gasSpedingGoal.getText().toString().length()==0 || energySpedingGoal.getText().toString().length()==0 ){
                    builder.setMessage("There cannot be empty fields!")
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
                    if(alreadyGols){
                        int result = gAdapter.updateGoals(new MonthsSpeding(StringMonth,parseFloat(waterSpedingGoal.getText().toString()),parseFloat(gasSpedingGoal.getText().toString()),parseFloat(energySpedingGoal.getText().toString())));
                        if(result==0){
                            Toast.makeText(getApplicationContext(),"Unable to save data",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Saved game data!",Toast.LENGTH_SHORT).show();
                            // end activit
                            finish();
                        }
                    }else{
                        long result = gAdapter.insertMonthGoal(new MonthsSpeding(StringMonth,parseFloat(waterSpedingGoal.getText().toString()),parseFloat(gasSpedingGoal.getText().toString()),parseFloat(energySpedingGoal.getText().toString())));
                        if(result < 0){
                            Toast.makeText(getApplicationContext(),"Unable to salve data",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Saved game data!",Toast.LENGTH_SHORT).show();
                            // end activit
                            finish();
                        }
                    }
                    gAdapter.close();
                }
            }
        });
    }

    public void loadGoals(){
        gAdapter.open();
        Cursor cursor = gAdapter.verifyIfAlreadyInsertMonthGoal(StringMonth);
        if(cursor.getCount()!=0){
            alreadyGols = true;
            builder.setMessage("THERE ARE ALREADY TARGETS SET FOR THIS MONTH! YOU CAN CHANGE THEM AND PRESS THE SAVE BUTTON")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            //Create a dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("ALERTA");
            alert.show();

            cursor.moveToFirst();
            waterSpedingGoal.setText(cursor.getString(2));
            gasSpedingGoal.setText(cursor.getString(4));
            energySpedingGoal.setText(cursor.getString(6));

        }
        gAdapter.close();
    }
}
