package com.example.olympe_dev_fragmentstyle.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.olympe_dev_fragmentstyle.R;
import com.example.olympe_dev_fragmentstyle.utils.Aliment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_PERF =
            "CREATE TABLE performances (" +
                    "idPerf INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nomPerf TEXT NOT NULL," +
                    "datePerf DATE NOT NULL," +
                    "valeurPerf INTEGER NOT NULL)";
    static final String CREATE_TABLE_ALIM =
            "CREATE TABLE aliments (" +
                    "idAlim INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nomAlim TEXT NOT NULL," +
                    "pathImageAlim TEXT," +
                    "calories INTEGER NOT NULL," +
                    "proteines REAL NOT NULL," +
                    "glucides REAL NOT NULL," +
                    "lipides REAL NOT NULL)";

    public DatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PERF);
        db.execSQL(CREATE_TABLE_ALIM);
        fillDatas(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertPerf(String nomPerf, int valeurPerf) {

        String insertPerfSQL =
                "INSERT INTO performances (nomPerf, datePerf, valeurPerf) VALUES ('" +
                nomPerf + "', " +
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ", " +
                valeurPerf + ")";
        this.getWritableDatabase().execSQL(insertPerfSQL);
    }

    public void insertAlim(SQLiteDatabase db, String nomAlim, String pathImageAlim, int calories, float proteines, float glucides, float lipides) {
        String insertAlimSQL = "INSERT INTO aliments (nomAlim, pathImageAlim, calories, proteines, glucides, lipides) VALUES (" +
                "'" + nomAlim + "', " +
                "'" + pathImageAlim + "', " +
                calories + ", " +
                proteines + ", " +
                glucides + ", " +
                lipides + ")";
        db.execSQL(insertAlimSQL);
    }

    public void clearTablePerf() {
        getWritableDatabase().execSQL("DROP TABLE performances");
        getWritableDatabase().execSQL(CREATE_TABLE_PERF);
    }

    public void clearTableAlim() {
        getWritableDatabase().execSQL("DROP TABLE aliments");
        getWritableDatabase().execSQL(CREATE_TABLE_ALIM);
    }
    public void fillDatas(SQLiteDatabase db) {
        insertAlim(db, context.getResources().getString(R.string.aliment_avocat), "String pathImageAlim", 160, (float) 2, (float) 8.5, (float) 14.66);
        insertAlim(db, context.getResources().getString(R.string.aliment_banane), "String pathImageAlim", 94, (float) 1.2, (float) 20.5, (float) 0.2);
        insertAlim(db, context.getResources().getString(R.string.aliment_brocoli), "String pathImageAlim", 29, (float) 2.1, (float) 2.8, (float) 0.5);
        insertAlim(db, context.getResources().getString(R.string.aliment_carotte), "String pathImageAlim", 36, (float) 0.8, (float) 6.6, (float) 0.3);
        insertAlim(db, context.getResources().getString(R.string.aliment_lentille), "String pathImageAlim", 353, (float) 25.8, (float) 60.1, (float) 0.2);
        insertAlim(db, context.getResources().getString(R.string.aliment_oeuf), "String pathImageAlim", 145, (float) 12.3, (float) 0.7, (float) 10.3);
        insertAlim(db, context.getResources().getString(R.string.aliment_poisson_cabillaud), "String pathImageAlim", 85, (float) 19, (float) 0, (float) 0.8);
        insertAlim(db, context.getResources().getString(R.string.aliment_poivron), "String pathImageAlim", 20, (float) 0.9, (float) 4.6, (float) 0.2);
        insertAlim(db, context.getResources().getString(R.string.aliment_pomme), "String pathImageAlim", 53, (float) 0.3, (float) 11.3, (float) 0.2);
        insertAlim(db, context.getResources().getString(R.string.aliment_poulet_blanc), "String pathImageAlim", 121, (float) 26.2, (float) 0, (float) 1.8);
    }

    public List<Aliment> getAliments() {
        Log.d("debug", "getAliments: ");
        List<Aliment> aliments = new ArrayList<>();
        String sqlRequest = "SELECT * FROM aliments";
        Cursor cursor = getReadableDatabase().rawQuery(sqlRequest, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
             Aliment aliment = new Aliment(cursor.getString(1), cursor.getString(2),
                     cursor.getInt(3), cursor.getFloat(4), cursor.getFloat(5),
                     cursor.getFloat(6));
             aliments.add(aliment);
            Log.d("debug", "adding Aliment...");
            cursor.moveToNext();
        }
        return  aliments;
    }

    public int getAlimentsRows() {
        Cursor mCount= getReadableDatabase().rawQuery("select count(*) from aliments", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }
}
