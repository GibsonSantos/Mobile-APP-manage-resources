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
    //this table will store the values consumed for each day of a month
    String SQL_CREATE = "CREATE TABLE " + DB_TABLE_DAYS +
            " (d_dia TEXT NOT NULL, " +
            "d_water FLOAT NOT NULL, " +
            "d_gas FLOAT NOT NULL," +
            "d_light FLOAT NOT NULL); ";
    //this table contains the amount spent each month, and the consumption goal for each resource
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

    // insert expenses for a given day
    public long insertDaySpending(DaySpending aDaySpending){
        ContentValues vals = new ContentValues();
        vals.put("d_dia", aDaySpending.getDate());
        vals.put("d_water", aDaySpending.getSpedingWater());
        vals.put("d_gas", aDaySpending.getSpedingGas());
        vals.put("d_light", aDaySpending.getSpedingEnergy());

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
    //checks if consumption has already been entered for a given day
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

    // insert goals for a given month
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

   //checks if goals have already been entered for the month
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
    //update consumption goals
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

    //checks if goals have already been entered for the month
    public Cursor getAllSpedingMonth(){
        return db.query(DB_TABLE_DAYS,  // table
                new String[] {"COUNT(*) ", "SUM(d_water) as d_water", "SUM(d_gas) as d_gas","SUM(d_light) as d_light"},           // columns
                null, // selection
                null,  // selectionArgs
                null,           // groupBy
                null,           // having
                null);          // orderBy
    }

    // insert the amount consumed for that month
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


    public long clearMonthSpeding(String month){
        ContentValues values = new ContentValues();
        values.put("m_water_speding", 0);
        values.put("m_gas_speding", 0);
        values.put("m_light_speding", 0);

        String whereClause = "m_mount = ?";
        String[] whereArgs = {month};

        int numberOfRowsAffected = db.update(DB_TABLE_MONTHS, values, whereClause, whereArgs);
        return numberOfRowsAffected;
    }

    //search for the last month
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
