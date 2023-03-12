package com.example.olympe_dev_fragmentstyle.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.CursorAdapter;

import androidx.annotation.Nullable;

import com.example.olympe_dev_fragmentstyle.R;
import com.example.olympe_dev_fragmentstyle.aliments.Aliment;
import com.example.olympe_dev_fragmentstyle.performances.Performance;

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
                    "image INTEGER," +
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

    public void insertPerf(SQLiteDatabase db, String nomPerf, int valeurPerf) {

        String insertPerfSQL =
                "INSERT INTO performances (nomPerf, datePerf, valeurPerf) VALUES ('" +
                nomPerf + "', " +
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ", " +
                valeurPerf + ")";
        this.getWritableDatabase().execSQL(insertPerfSQL);
    }

    public void insertAlim(SQLiteDatabase db, String nomAlim, int image, int calories, float proteines, float glucides, float lipides) {
        String insertAlimSQL = "INSERT INTO aliments (nomAlim, image, calories, proteines, glucides, lipides) VALUES (" +
                "'" + nomAlim + "', " +
                image + ", " +
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
        insertAlim(db, context.getResources().getString(R.string.aliment_avocat), R.drawable.pomme, 160, (float) 2, (float) 8.5, (float) 14.66);
        insertAlim(db, context.getResources().getString(R.string.aliment_banane), R.drawable.pomme, 94, (float) 1.2, (float) 20.5, (float) 0.2);
        insertAlim(db, context.getResources().getString(R.string.aliment_brocoli), R.drawable.pomme, 29, (float) 2.1, (float) 2.8, (float) 0.5);
        insertAlim(db, context.getResources().getString(R.string.aliment_carotte), R.drawable.pomme, 36, (float) 0.8, (float) 6.6, (float) 0.3);
        insertAlim(db, context.getResources().getString(R.string.aliment_lentille), R.drawable.pomme, 353, (float) 25.8, (float) 60.1, (float) 0.2);
        insertAlim(db, context.getResources().getString(R.string.aliment_oeuf), R.drawable.pomme, 145, (float) 12.3, (float) 0.7, (float) 10.3);
        insertAlim(db, context.getResources().getString(R.string.aliment_poisson_cabillaud), R.drawable.pomme, 85, (float) 19, (float) 0, (float) 0.8);
        insertAlim(db, context.getResources().getString(R.string.aliment_poivron), R.drawable.pomme, 20, (float) 0.9, (float) 4.6, (float) 0.2);
        insertAlim(db, context.getResources().getString(R.string.aliment_pomme), R.drawable.pomme, 53, (float) 0.3, (float) 11.3, (float) 0.2);
        insertAlim(db, context.getResources().getString(R.string.aliment_poulet_blanc), R.drawable.pomme, 121, (float) 26.2, (float) 0, (float) 1.8);

        insertPerf(db, "Développé couché", 105);
        insertPerf(db, "Développé couché", 110);
        insertPerf(db, "Développé couché", 115);
        insertPerf(db, "Traction lestée", 50);
        insertPerf(db, "Traction lestée", 60);
        insertPerf(db, "Traction lestée", 70);
        insertPerf(db, "Dip lestée", 70);
        insertPerf(db, "Dip lestée", 80);
        insertPerf(db, "Dip lestée", 90);
    }

    public List<Aliment> getAliments() {
        List<Aliment> aliments = new ArrayList<>();
        String sqlRequest = "SELECT * FROM aliments";
        Cursor cursor = getReadableDatabase().rawQuery(sqlRequest, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
             Aliment aliment = new Aliment(cursor.getString(1), cursor.getInt(2),
                     cursor.getInt(3), cursor.getFloat(4), cursor.getFloat(5),
                     cursor.getFloat(6));
             aliments.add(aliment);
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

    public List<Performance> getPerformances() {
        List<Performance> performances = new ArrayList<>();
        String sqlRequest = "SELECT * FROM performances";
        Cursor cursor = getReadableDatabase().rawQuery(sqlRequest, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Performance performance = new Performance(cursor.getString(1), cursor.getInt(2),
                    cursor.getInt(3));
            performances.add(performance);
            cursor.moveToNext();
        }
        return  performances;
    }

    public List<Performance> getPerformances(String categorie) {
        List<Performance> performances = new ArrayList<>();
        String sqlRequest = "SELECT * FROM performances WHERE nomPerf = '" + categorie + "'";
        Cursor cursor = getReadableDatabase().rawQuery(sqlRequest, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Performance performance = new Performance(cursor.getString(1), cursor.getInt(2),
                    cursor.getInt(3));
            performances.add(performance);
            cursor.moveToNext();
        }
        return  performances;
    }

    public List<String> getPerformanceCategories() {
        List<String> categories = new ArrayList<>();
        String sqlRequest = "SELECT DISTINCT nomPerf FROM performances ORDER BY nomPerf";
        Cursor cursor = getReadableDatabase().rawQuery(sqlRequest, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String categorie = cursor.getString(0);
            categories.add(categorie);
            cursor.moveToNext();
        }
        return  categories;
    }

    public int getPerfsRows() {
        Cursor mCount= getReadableDatabase().rawQuery("select count(*) from performances", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }
}
