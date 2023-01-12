package com.example.manageresourceshome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class appDBAdapter {
    String DB_NAME = "MyDB";
    String DB_TABLE_MOUNT = "monthly_expenses";
    String DB_TABLE_YEAR = "year_expenses";
    int DB_VERSION = 1;
    //esta tabela vai armazenar os valores consumidos para cada dia de um mes
    String SQL_CREATE = "CREATE TABLE " + DB_TABLE_MOUNT +
            " (d_dia TEXT NOT NULL, " +
            "d_water FLOAT NOT NULL, " +
            "d_gas FLOAT NOT NULL," +
            "d_light FLOAT NOT NULL); ";
    //esta tabela contem o valor gasto (speding) de cada mes, e o a meta(goal) de consumo para cada recurso
    String SQL_CREATE2 = "CREATE TABLE " + DB_TABLE_YEAR +
            " (m_mount TEXT NOT NULL, " +
            "m_water_speding FLOAT NOT NULL, " +
            "m_water_goal FLOAT NOT NULL, " +
            "m_gas_speding FLOAT NOT NULL," +
            "m_gas_goal FLOAT NOT NULL, " +
            "m_light_speding FLOAT NOT NULL, " +
            "m_light_goal FLOAT NOT NULL); ";

    String SQL_DROP = "DROP TABLE IF EXISTS " + DB_TABLE_MOUNT;
    String SQL_DROP2 = "DROP TABLE IF EXISTS " + DB_TABLE_YEAR;


    DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public appDBAdapter(Context ctx){
        dbHelper = new DatabaseHelper(ctx);
    }

    //-----------------------------------------
    class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);
            db.execSQL(SQL_CREATE2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DROP);
            db.execSQL(SQL_DROP2);
            onCreate(db);
        }

    }
    //---------------------------------------------

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    // insere os gastos de um determinado dia
    public long insertDaySpending(DaySpending aDaySpending){
        ContentValues vals = new ContentValues();
        vals.put("d_dia", aDaySpending.getDate());
        vals.put("d_water", aDaySpending.getSpedingEnergy());
        vals.put("d_gas", aDaySpending.getSpedingGas());
        vals.put("d_light", aDaySpending.getSpedingWater());

        return db.insert(DB_TABLE_MOUNT,  // table
                null,           // null when some value is provided (nullColumnHack)
                vals );         // initial values
    }

    public Cursor getAllDaysSepending(){
        Cursor cursor = db.query(
                DB_TABLE_MOUNT,
                new String[] {"d_dia","d_water","d_gas","d_light"} , // resultset columns/fields
                null,                             // condition or selection
                null,                             // selection arguments (fills in '?' above)
                null,                             // groupBy
                null,                             // having
                null );                           // orderBy

        return cursor;
    }
    //verifica se ja foram inseridos consumos para um determinado dia
    public Cursor verifyIfAlreadyInsertSpending(String date){
        String[] selectionArgs = {date};
        return db.query(DB_TABLE_MOUNT,  // table
                null,           // columns
                "d_dia = ?", // selection
                selectionArgs,  // selectionArgs
                null,           // groupBy
                null,           // having
                null);          // orderBy
    }

    public void clearDataMount(){
        db.execSQL("delete from "+ DB_TABLE_MOUNT);
    }

    public void clearDataYear(){
        db.execSQL("delete from "+ DB_TABLE_YEAR);
    }

}
