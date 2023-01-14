package com.example.manageresourceshome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

public class ListMonthSpending extends AppCompatActivity {

    private appDBAdapter gAdapter;
    Vector<MonthsSpeding> VectorMonthSpeding;
    ListView contactsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_month_spending);

        gAdapter = new appDBAdapter(this);

        displayGames();

    }
    //função responsavel por apresentar os gastos do meses
    public void displayGames(){
        loadContacts();  // carrega os jogos presemtes

        // define o list adaptar
        ListAdapter adapter = new ListDaysSpending.ContactAdapter(this, VectorMonthSpeding);
        contactsListView = (ListView) findViewById(R.id.contacts_lv);
        contactsListView.setAdapter(adapter);

        // enables filtering using the keyboard, if available
        contactsListView.setTextFilterEnabled(true);
    }

    // carrega os jogos para o vetor games
    public void loadContacts() {
        VectorMonthSpeding = new Vector<>();
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
                VectorMonthSpeding.add(0,daySpending);
                curRes.moveToNext();
            }
        }

        gAdapter.close();
    }

    // This is the adapter. BaseAdapter is abstract. Some methods must be implemented.
    class ContactAdapter extends BaseAdapter {
        Context context;
        List<DaySpending> adaptMonth;

        // The constructor receives a context and the data
        public ContactAdapter(Context ctx, List<DaySpending> list) {
            context = ctx;
            adaptMonth = list;
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
            TextView textGas = (TextView) rowView.findViewById(R.id.msg_spending_gas);
            TextView textEnery = (TextView) rowView.findViewById(R.id.msg_spending_energy);
            Button btnEdit = (Button) rowView.findViewById(R.id.btn_edit);
            btnEdit.setBackgroundColor(Color.YELLOW);
            btnEdit.setTextColor(Color.BLACK);


            // obtains the contact for this position
            DaySpending daySpending = adaptMonth.get(position);

            textWater.setText(String.valueOf(daySpending.getSpedingWater()));
            textGas.setText(String.valueOf(daySpending.getSpedingGas()));
            textEnery.setText(String.valueOf(daySpending.getSpedingEnergy()));
            date.setText(daySpending.getDate());


            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gAdapter.open();
                    gAdapter.close();
                    loadContacts();
                    displayGames();
                }
            });

            // returns the view
            return rowView;
        }

        // Estes metodos são necessarios
        @Override
        public int getCount() {
            return adaptMonth.size();
        }

        @Override
        public Object getItem(int position) {
            return adaptMonth.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

}