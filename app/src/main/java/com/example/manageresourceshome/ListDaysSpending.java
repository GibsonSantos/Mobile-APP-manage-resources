package com.example.manageresourceshome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ListDaysSpending extends AppCompatActivity {

    private appDBAdapter gAdapter;
    Vector<DaySpending> VectorDaySpeding;
    ListView contactsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_days_spending);

        gAdapter = new appDBAdapter(this);

        displayGames();
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
        Cursor curRes = gAdapter.getAllGames();
        if(curRes!=null){
            if(curRes.getCount()==0){
                Toast.makeText(this, "Não existem jogos finalizados!", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder sb = new StringBuilder();

            curRes.moveToFirst();
            while(!curRes.isAfterLast()){
                //String player1, String player2, String stringDate, String aNameTorneio, String aSet_Player1, String aSet_Player2,String aWinner
                Game game = new Game(curRes.getString(0),curRes.getString(1)
                        ,curRes.getString(2),curRes.getString(3),curRes.getString(4),
                        curRes.getString(5),curRes.getString(6),curRes.getString(7));
                games.add(0,game);
                curRes.moveToNext();
            }
        }

        gAdapter.close();

        //games = MainActivity.gereGames.getGames();
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
            TextView textPlayer1 = (TextView) rowView.findViewById(R.id.msg_name_player1);
            TextView textPlayer2 = (TextView) rowView.findViewById(R.id.msg_name_player2);
            TextView nameTorneio = (TextView) rowView.findViewById(R.id.msg_name_game);
            TextView date = (TextView) rowView.findViewById(R.id.msg_date);
            TextView setsPLayer1 = (TextView) rowView.findViewById(R.id.msg_sets_score_player1);
            TextView setsPLayer2 = (TextView) rowView.findViewById(R.id.msg_sets_score_player2);
            ImageView imagePlayer1 = (ImageView) rowView.findViewById(R.id.imageViewPlayer1);
            ImageView imagePlayer2 = (ImageView) rowView.findViewById(R.id.imageViewPlayer2);
            Button btnRemove = (Button) rowView.findViewById(R.id.btn_remove);
            btnRemove.setBackgroundColor(Color.RED);
            btnRemove.setTextColor(Color.WHITE);


            // obtains the contact for this position
            Game game = adaptContacts.get(position);

            textPlayer1.setText(game.getPlayer1());
            textPlayer2.setText(game.getPlayer2());
            nameTorneio.setText(game.getNameTorneio());
            date.setText(game.getGameStringDate(game.getGameDate()));
            setsPLayer1.setText(game.getSetsPlayer1());
            setsPLayer2.setText(game.getSetsPlayer2());

            //caso tenha sido o jogador 1 a ganhar o jogo
            if(game.getWinner().equals(game.getPlayer1())){
                imagePlayer1.setImageResource(R.drawable.winnerr);
                imagePlayer2.setImageResource(R.drawable.loser);
                setsPLayer1.setTextColor(Color.GREEN);
            }else{
                imagePlayer2.setImageResource(R.drawable.winnerr);
                imagePlayer1.setImageResource(R.drawable.loser);
                setsPLayer2.setTextColor(Color.GREEN);
            }

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gAdapter.open();
                    int numDeleted = gAdapter.deleteGame(game.getIdGame());
                    gAdapter.close();
                    //MainActivity.gereGames.getGames().remove(games.get(position));
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