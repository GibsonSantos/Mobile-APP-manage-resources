package com.example.manageresourceshome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConfigName extends AppCompatActivity {

    private String prefsName = "userPrefsAPP";

    private int reqCode = 1; // requestCode
    private TextView userName;
    private Button btnSalveConfName;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_name);

        // obtains the SharedPreferences object
        SharedPreferences prefs=getSharedPreferences(prefsName, MODE_PRIVATE);


        // reads from sharedPreferences
        // The second argument defines the return value if it does not exist
        String aUserName = prefs.getString("userName", null);

        userName = (TextView) findViewById(R.id.edit_name_player);

        builder = new AlertDialog.Builder(this);

        if(aUserName!=null){
            // writes to the left EditText
            userName.setText(aUserName);
        }

        btnSalveConfName = (Button) findViewById(R.id.btn_salve_name);

        btnSalveConfName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = (EditText) findViewById(R.id.edit_name_player);
                if(userName.getText().toString().length()==0){
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
                } else {
                    // obtains the SharedPreferences object
                    SharedPreferences prefs=getSharedPreferences(prefsName, MODE_PRIVATE);

                    // obtains an editor to it - needed only for writing
                    SharedPreferences.Editor editor = prefs.edit();

                    // includes or changes the value of variables
                    editor.putString("userName", userName.getText().toString());

                    // we need to commit at the end
                    editor.commit();

                    finish();
                }
            }
        });

    }
}