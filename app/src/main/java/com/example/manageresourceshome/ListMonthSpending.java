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
        ListAdapter adapter = new ListMonthSpending.ContactAdapter(this, VectorMonthSpeding);
        contactsListView = (ListView) findViewById(R.id.contacts_lv);
        contactsListView.setAdapter(adapter);

        // enables filtering using the keyboard, if available
        contactsListView.setTextFilterEnabled(true);
    }

    // carrega os jogos para o vetor games
    public void loadContacts() {
        VectorMonthSpeding = new Vector<>();
        gAdapter.open();
        Cursor curRes = gAdapter.getLastMonth();
        if(curRes!=null){
            if(curRes.getCount()==0){
                Toast.makeText(this, "Não existem dias em que foram registrados o cosumo!", Toast.LENGTH_SHORT).show();
                return;
            }

            curRes.moveToFirst();
            while(!curRes.isAfterLast()){
                MonthsSpeding monthsSpeding = new MonthsSpeding(curRes.getString(0),curRes.getFloat(1),curRes.getFloat(2),curRes.getFloat(3),
                        curRes.getFloat(4),curRes.getFloat(5),curRes.getFloat(6));

                VectorMonthSpeding.add(0,monthsSpeding);
                curRes.moveToNext();
            }
        }

        gAdapter.close();
    }

    // This is the adapter. BaseAdapter is abstract. Some methods must be implemented.
    class ContactAdapter extends BaseAdapter {
        Context context;
        List<MonthsSpeding> adaptMonth;

        // The constructor receives a context and the data
        public ContactAdapter(Context ctx, List<MonthsSpeding> list) {
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
                rowView = inflater.inflate(R.layout.list_spent_month, parent, false);
            }

            // These are the Views inside the ListView item

            TextView date = (TextView) rowView.findViewById(R.id.msg_date);
            TextView textWaterSpeding = (TextView) rowView.findViewById(R.id.msg_spending_water_month);
            TextView textWaterGoal = (TextView) rowView.findViewById(R.id.msg_goal_water_month);
            TextView textResultWater = (TextView) rowView.findViewById(R.id.msg_result_water);
            TextView textGasSpeding = (TextView) rowView.findViewById(R.id.msg_spending_gas_month);
            TextView textGasGoal = (TextView) rowView.findViewById(R.id.msg_goal_gas_month);
            TextView textResultGas = (TextView) rowView.findViewById(R.id.msg_result_gas);
            TextView textEnergySpeding = (TextView) rowView.findViewById(R.id.msg_spending_energy_month);
            TextView textEnergyGoal = (TextView) rowView.findViewById(R.id.msg_goal_energy_month);
            TextView textResultEnergy = (TextView) rowView.findViewById(R.id.msg_result_energy);


            // obtains the contact for this position
            MonthsSpeding monthsSpeding = adaptMonth.get(position);

            date.setText(monthsSpeding.getMonth());
            textWaterSpeding.setText(String.valueOf(monthsSpeding.getWaterSpending()));
            textWaterGoal.setText(String.valueOf(monthsSpeding.getWaterSpendingGoal()));
            textGasSpeding.setText(String.valueOf(monthsSpeding.getGasSpending()));
            textGasGoal.setText(String.valueOf(monthsSpeding.getGasSpendingGoal()));
            textEnergySpeding.setText(String.valueOf(monthsSpeding.getEnergySpending()));
            textEnergyGoal.setText(String.valueOf(monthsSpeding.getEnergySpendingGoal()));

            if(monthsSpeding.getWaterSpending()>monthsSpeding.getWaterSpendingGoal()){
                textResultWater.setText("ACIMA");
                textResultWater.setTextColor(Color.RED);
            }else{
                textResultWater.setText("ABAIXO");
                textResultWater.setTextColor(Color.GREEN);
            }
            if(monthsSpeding.getGasSpending()>monthsSpeding.getGasSpendingGoal()){
                textResultGas.setText("ACIMA");
                textResultGas.setTextColor(Color.RED);
            }else{
                textResultGas.setText("ABAIXO");
                textResultGas.setTextColor(Color.GREEN);
            }
            if(monthsSpeding.getEnergySpending()>monthsSpeding.getEnergySpendingGoal()){
                textResultEnergy.setText("ACIMA");
                textResultEnergy.setTextColor(Color.RED);
            }else{
                textResultEnergy.setText("ABAIXO");
                textResultEnergy.setTextColor(Color.GREEN);
            }

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

}