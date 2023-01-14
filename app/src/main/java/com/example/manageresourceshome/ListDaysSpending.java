package com.example.manageresourceshome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Vector;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ListDaysSpending extends AppCompatActivity {

    private appDBAdapter gAdapter;
    Vector<DaySpending> VectorDaySpeding;
    ListView contactsListView;
    private int reqCode = 1; // requestCode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_days_spending);

        gAdapter = new appDBAdapter(this);

        displayGames();
        loadContacts();
    }



    //função responsavel por apresentar os jogos
    public void displayGames(){
        loadContacts();  // carrega os jogos presemtes

        // define o list adaptar
        ListAdapter adapter = new ContactAdapter(this, VectorDaySpeding);
        contactsListView = (ListView) findViewById(R.id.contacts_lv);
        contactsListView.setAdapter(adapter);

        // enables filtering using the keyboard, if available
        contactsListView.setTextFilterEnabled(true);
    }

    // carrega os jogos para o vetor games
    public void loadContacts() {
        VectorDaySpeding = new Vector<>();
        gAdapter.open();
        Cursor curRes = gAdapter.getAllDaysSepending();
        if(curRes!=null){
            if(curRes.getCount()==0){
                Toast.makeText(this, "Não existem dias em que foram registrados o cosumo!", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder sb = new StringBuilder();

            curRes.moveToFirst();
            while(!curRes.isAfterLast()){
                DaySpending daySpending = new DaySpending(curRes.getString(0),curRes.getFloat(1),curRes.getFloat(2),curRes.getFloat(3));
                VectorDaySpeding.add(0,daySpending);
                curRes.moveToNext();
            }
        }

        gAdapter.close();
    }

    // This is the adapter. BaseAdapter is abstract. Some methods must be implemented.
    class ContactAdapter extends BaseAdapter {
        Context context;
        List<DaySpending> adaptContacts;

        // The constructor receives a context and the data
        public ContactAdapter(Context ctx, List<DaySpending> list) {
            context = ctx;
            adaptContacts = list;
        }

        // This method is called each time an item needs to be presented in the ListView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // convertView has the previous View for this position
            View rowView = convertView;

            // we only need to create the view if it does not exist
            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.list_item, parent, false);
            }

            // These are the Views inside the ListView item

            TextView date = (TextView) rowView.findViewById(R.id.msg_date);
            TextView textWater = (TextView) rowView.findViewById(R.id.msg_spending_water);
            TextView textGas = (TextView) rowView.findViewById(R.id.msg_spending_water_month);
            TextView textEnery = (TextView) rowView.findViewById(R.id.msg_spending_energy);
            Button btnEdit = (Button) rowView.findViewById(R.id.btn_edit);
            btnEdit.setBackgroundColor(Color.YELLOW);
            btnEdit.setTextColor(Color.BLACK);


            // obtains the contact for this position
            DaySpending daySpending = adaptContacts.get(position);

            textWater.setText(String.valueOf(daySpending.getSpedingWater()));
            textGas.setText(String.valueOf(daySpending.getSpedingGas()));
            textEnery.setText(String.valueOf(daySpending.getSpedingEnergy()));
            date.setText(daySpending.getDate());


            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // criando um novo intent
                    Intent i = new Intent(getApplicationContext(), EditSpedingDay.class);

                    // adicionando um bundle com os dados do jogo ao intent
                    Bundle b = new Bundle();
                    b.putString("daySpeding", daySpending.getDate());
                    b.putString("spedingWater", String.valueOf(daySpending.getSpedingWater()));
                    b.putString("spedingGas", String.valueOf(daySpending.getSpedingGas()));
                    b.putString("spedingEnergy", String.valueOf(daySpending.getSpedingEnergy()));
                    i.putExtras(b);

                    // esperando por um retorno da página edit game
                    startActivityForResult(i, reqCode);
                    finish();
                }
            });

            // returns the view
            return rowView;
        }

        // Estes metodos são necessarios
        @Override
        public int getCount() {
            return adaptContacts.size();
        }

        @Override
        public Object getItem(int position) {
            return adaptContacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }
}