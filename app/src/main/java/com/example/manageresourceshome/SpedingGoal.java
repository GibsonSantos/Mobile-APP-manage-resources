package com.example.manageresourceshome;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.DateFormatSymbols;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Locale;

public class SpedingGoal extends AppCompatActivity {
    private TextView month;
    private Button btnCancelSpedingGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speding_goal);

        btnCancelSpedingGoal = findViewById(R.id.btn_cancel_speding_goal);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
        String StringMonth = sdf.format(c.getTime());

        month = (TextView) findViewById(R.id.txt_month_speding_goal);
        month.setText(StringMonth);

        btnCancelSpedingGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}