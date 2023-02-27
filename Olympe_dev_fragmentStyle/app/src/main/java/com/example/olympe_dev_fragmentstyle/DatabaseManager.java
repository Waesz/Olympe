package com.example.olympe_dev_fragmentstyle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_PERF =
            "CREATE TABLE performance (" +
                    "idPerf INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nomPerf TEXT NOT NULL," +
                    "datePerf DATE NOT NULL," +
                    "valeurPerf INTEGER NOT NULL)";
    static final String CREATE_TABLE_ALIM =
            "CREATE TABLE aliments (" +
                    "idAli INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nomAli TEXT NOT NULL," +
                    "pathImageAli TEXT NOT NULL," +
                    "calories INTEGER NOT NULL," +
                    "proteines INTEGER NOT NULL," +
                    "glucides INTEGER NOT NULL," +
                    "lipides INTEGER NOT NULL)";

    public DatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PERF);
        db.execSQL(CREATE_TABLE_ALIM);

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

    public void insertAlim(String nomAli, String pathImageAli, int calories, int proteines, int glucides, int lipides) {
        String insertAlimSQL = "INSERT INTO aliments (nomAli, pathImageAli, calories, proteines, glucides, lipides) VALUES (" +
                "'" + nomAli + "', " +
                "'" + pathImageAli + "', " +
                calories +
                proteines +
                glucides +
                lipides + ")";
        this.getWritableDatabase().execSQL(insertAlimSQL);
    }

    public void clearTablePerf() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE performances");
        db.execSQL(CREATE_TABLE_PERF);
    }

    public void clearTableAlim() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE aliments");
        db.execSQL(CREATE_TABLE_ALIM);
    }
}
