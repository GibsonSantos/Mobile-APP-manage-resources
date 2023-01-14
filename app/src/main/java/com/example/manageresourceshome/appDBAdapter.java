package com.example.manageresourceshome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class appDBAdapter {
    String DB_NAME = "MyDB";
    String DB_TABLE_DAYS = "monthly_expenses";
    String DB_TABLE_MONTHS = "year_expenses";
    int DB_VERSION = 2;
    //esta tabela vai armazenar os valores consumidos para cada dia de um mes
    String SQL_CREATE = "CREATE TABLE " + DB_TABLE_DAYS +
            " (d_dia TEXT NOT NULL, " +
            "d_water FLOAT NOT NULL, " +
            "d_gas FLOAT NOT NULL," +
            "d_light FLOAT NOT NULL); ";
    //esta tabela contem o valor gasto (speding) de cada mes, e o a meta(goal) de consumo para cada recurso
    String SQL_CREATE2 = "CREATE TABLE " + DB_TABLE_MONTHS +
            " (m_mount TEXT NOT NULL, " +
            "m_water_speding FLOAT, " +
            "m_water_goal FLOAT NOT NULL, " +
            "m_gas_speding FLOAT ," +
            "m_gas_goal FLOAT NOT NULL, " +
            "m_light_speding FLOAT , " +
            "m_light_goal FLOAT NOT NULL); ";

    String SQL_DROP = "DROP TABLE IF EXISTS " + DB_TABLE_DAYS;
    String SQL_DROP2 = "DROP TABLE IF EXISTS " + DB_TABLE_MONTHS;


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

        return db.insert(DB_TABLE_DAYS,  // table
                null,           // null when some value is provided (nullColumnHack)
                vals );         // initial values
    }

    public Cursor getAllDaysSepending(){
        Cursor cursor = db.query(
                DB_TABLE_DAYS,
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
        return db.query(DB_TABLE_DAYS,  // table
                null,           // columns
                "d_dia = ?", // selection
                selectionArgs,  // selectionArgs
                null,           // groupBy
                null,           // having
                null);          // orderBy
    }

    // insere as metas para um detreminado mês
    public long insertMonthGoal(MonthsSpeding monthsSpeding){
        ContentValues vals = new ContentValues();
        vals.put("m_mount", monthsSpeding.getMonth());
        vals.put("m_water_goal", monthsSpeding.getWaterSpendingGoal());
        vals.put("m_gas_goal", monthsSpeding.getGasSpendingGoal());
        vals.put("m_light_goal", monthsSpeding.getEnergySpendingGoal());

        return db.insert(DB_TABLE_MONTHS,  // table
                null,           // null when some value is provided (nullColumnHack)
                vals );         // initial values
    }

    //verifica se ja foram inseridas metas para o mes
    public Cursor verifyIfAlreadyInsertMonthGoal(String month){
        String[] selectionArgs = {month};
        return db.query(DB_TABLE_MONTHS,  // table
                null,           // columns
                "m_mount = ?", // selection
                selectionArgs,  // selectionArgs
                null,           // groupBy
                null,           // having
                null);          // orderBy
    }
    //atualiza as metas de consumo
    public int updateGoals(MonthsSpeding monthsSpeding){
        ContentValues values = new ContentValues();
        values.put("m_water_goal", monthsSpeding.getWaterSpendingGoal());
        values.put("m_gas_goal", monthsSpeding.getGasSpendingGoal());
        values.put("m_light_goal", monthsSpeding.getEnergySpendingGoal());

        String whereClause = "m_mount = ?";
        String[] whereArgs = {monthsSpeding.getMonth()};

        int numberOfRowsAffected = db.update(DB_TABLE_MONTHS, values, whereClause, whereArgs);
        return numberOfRowsAffected;
    }

    //verifica se ja foram inseridas metas para o mes
    public Cursor getAllSpedingMonth(){
        return db.query(DB_TABLE_DAYS,  // table
                new String[] {"COUNT(*) ", "SUM(d_water) as d_water", "SUM(d_gas) as d_gas","SUM(d_light) as d_light"},           // columns
                null, // selection
                null,  // selectionArgs
                null,           // groupBy
                null,           // having
                null);          // orderBy
    }

    // insere o valor consumido para aquele mes
    public long insertAllDatasMonth(String month, Float allSpentWater, Float allSpentGas,Float allSpentEnergy){
        ContentValues values = new ContentValues();
        values.put("m_water_speding", allSpentWater);
        values.put("m_gas_speding", allSpentGas);
        values.put("m_light_speding", allSpentEnergy);

        String whereClause = "m_mount = ?";
        String[] whereArgs = {month};

        int numberOfRowsAffected = db.update(DB_TABLE_MONTHS, values, whereClause, whereArgs);
        return numberOfRowsAffected;
    }

    //busca o ultimo mês
    public Cursor getLastMonth(){
        return db.query(DB_TABLE_MONTHS,  // table
                null,           // columns
                null, // selection
                null,  // selectionArgs
                null,           // groupBy
                null,           // having
                null);          // orderBy
    }

    public Cursor verifyIfAlreadyInsertDaySpeding(String day){
        System.out.println(day);
        String[] selectionArgs = {day};
        return db.query(DB_TABLE_DAYS,  // table
                null,           // columns
                "d_dia = ?", // selection
                selectionArgs,  // selectionArgs
                null,           // groupBy
                null,           // having
                null);          // orderBy
    }

    public int updateDaySpeding(DaySpending aDaySpending){
        ContentValues vals = new ContentValues();
        vals.put("d_dia", aDaySpending.getDate());
        vals.put("d_water", aDaySpending.getSpedingWater());
        vals.put("d_gas", aDaySpending.getSpedingGas());
        vals.put("d_light", aDaySpending.getSpedingEnergy());

        String whereClause = "d_dia = ?";
        String[] whereArgs = {aDaySpending.getDate()};

        int numberOfRowsAffected = db.update(DB_TABLE_DAYS, vals, whereClause, whereArgs);
        return numberOfRowsAffected;
    }


    public void clearDataMount(){
        db.execSQL("delete from "+ DB_TABLE_DAYS);
    }

    public void clearDataYear(){
        db.execSQL("delete from  "+ DB_TABLE_MONTHS);
    }

}
