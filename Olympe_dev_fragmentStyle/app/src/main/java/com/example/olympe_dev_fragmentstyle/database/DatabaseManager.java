package com.example.olympe_dev_fragmentstyle.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.olympe_dev_fragmentstyle.R;
import com.example.olympe_dev_fragmentstyle.aliments.Aliment;
import com.example.olympe_dev_fragmentstyle.performances.Performance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    SQLiteDatabase db;
    Context context;
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE users (" +
                    "idUser INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "pseudo TEXT NOT NULL," +
                    "password TEXT NOT NULL)";
    private static final String CREATE_INFOS_TABLE =
            "CREATE TABLE infos (" +
                    "idInfo INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "taille INTEGER," +
                    "poids INTEGER," +
                    "sexe TEXT," +
                    "idUser INTEGER NOT NULL," +
                    "FOREIGN KEY(idUser) REFERENCES users(idUser))";
    private static final String CREATE_TABLE_PERF =
            "CREATE TABLE performances (" +
                    "idPerf INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nomPerf TEXT NOT NULL," +
                    "datePerf DATE NOT NULL," +
                    "valeurPerf INTEGER NOT NULL," +
                    "idUser INTEGER NOT NULL," +
                    "FOREIGN KEY(idUser) REFERENCES users(idUser))";
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
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TABLE_PERF);
        db.execSQL(CREATE_TABLE_ALIM);
        db.execSQL(CREATE_INFOS_TABLE);
        fillDatas(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertPerf(SQLiteDatabase db, int idUser, String nomPerf, int valeurPerf) {

        String insertPerfSQL =
                "INSERT INTO performances (nomPerf, datePerf, valeurPerf, idUser) VALUES ('" +
                nomPerf + "', " +
                "'" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', " +
                valeurPerf + ", " +
                idUser + ")";
        db.execSQL(insertPerfSQL);

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
    public void insertUser(SQLiteDatabase db, String pseudo, String password) {
        String addUserSQL = "INSERT INTO users (pseudo, password) VALUES (" +
                "'" + pseudo + "', " +
                "'" + password + "')";
        db.execSQL(addUserSQL);

    }

    public void insertInfos(SQLiteDatabase db, int taille, int poids, String sexe, int idUser) {
        Log.d("debug", "insertInfos avant: " + taille + ", " + poids + ", " + sexe + ", " + idUser);
        String addNewInfosSQL = "INSERT INTO infos (taille, poids, sexe, idUser) VALUES (" +
                taille + ", " +
                poids + ", " +
                "'" + sexe + "', " +
                idUser + ")";
        String updateInfosSQL = "UPDATE infos " +
                "SET taille = " + taille + ", " +
                "poids = " + poids + ", " +
                "sexe = '" + sexe + "' " +
                "WHERE idUser = " + idUser;
        Cursor cursor = db.rawQuery("SELECT * FROM infos WHERE idUser = '" + idUser + "'", null);
        if(cursor.getCount() == 0) {
            db.execSQL(addNewInfosSQL);
            Log.d("debug", "New infos");
        } else {
            db.execSQL(updateInfosSQL);
            Log.d("debug", "Update infos : " + updateInfosSQL);
        }

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

        insertAlim(db,context.getResources().getString(R.string.aliment_avocat), R.drawable.avocat, 160, (float) 2, (float) 8.5, (float) 14.66);
        insertAlim(db,context.getResources().getString(R.string.aliment_banane), R.drawable.banane, 94, (float) 1.2, (float) 20.5, (float) 0.2);
        insertAlim(db,context.getResources().getString(R.string.aliment_brocoli), R.drawable.brocoli, 29, (float) 2.1, (float) 2.8, (float) 0.5);
        insertAlim(db,context.getResources().getString(R.string.aliment_carotte), R.drawable.carotte, 36, (float) 0.8, (float) 6.6, (float) 0.3);
        insertAlim(db,context.getResources().getString(R.string.aliment_lentille), R.drawable.lentilles, 353, (float) 25.8, (float) 60.1, (float) 0.2);
        insertAlim(db,context.getResources().getString(R.string.aliment_oeuf), R.drawable.oeuf, 145, (float) 12.3, (float) 0.7, (float) 10.3);
        insertAlim(db,context.getResources().getString(R.string.aliment_poisson_cabillaud), R.drawable.cabillaud, 85, (float) 19, (float) 0, (float) 0.8);
        insertAlim(db,context.getResources().getString(R.string.aliment_poivron), R.drawable.poivron, 20, (float) 0.9, (float) 4.6, (float) 0.2);
        insertAlim(db,context.getResources().getString(R.string.aliment_pomme), R.drawable.pomme, 53, (float) 0.3, (float) 11.3, (float) 0.2);
        insertAlim(db,context.getResources().getString(R.string.aliment_poulet_blanc), R.drawable.blancpoulet, 121, (float) 26.2, (float) 0, (float) 1.8);

        insertPerf(db, 1,"Développé couché", 105);
        insertPerf(db,1,"Développé couché", 110);
        insertPerf(db,1,"Développé couché", 115);
        insertPerf(db,1,"Traction lestée", 50);
        insertPerf(db,1,"Traction lestée", 60);
        insertPerf(db,1,"Traction lestée", 70);
        insertPerf(db,2,"Dip lestée", 70);
        insertPerf(db,2,"Dip lestée", 80);
        insertPerf(db,2,"Dip lestée", 90);

        insertUser(db,"Marc", "123");
        insertUser(db,"Wassim", "456");

        insertInfos(db,175, 70, "Homme", 1);
        insertInfos(db,175, 80, "Homme", 2);
    }

    public void fillAliments(SQLiteDatabase db) {
        insertAlim(db,context.getResources().getString(R.string.aliment_avocat), R.drawable.avocat, 160, (float) 2, (float) 8.5, (float) 14.66);
        insertAlim(db,context.getResources().getString(R.string.aliment_banane), R.drawable.banane, 94, (float) 1.2, (float) 20.5, (float) 0.2);
        insertAlim(db,context.getResources().getString(R.string.aliment_brocoli), R.drawable.brocoli, 29, (float) 2.1, (float) 2.8, (float) 0.5);
        insertAlim(db,context.getResources().getString(R.string.aliment_carotte), R.drawable.carotte, 36, (float) 0.8, (float) 6.6, (float) 0.3);
        insertAlim(db,context.getResources().getString(R.string.aliment_lentille), R.drawable.lentilles, 353, (float) 25.8, (float) 60.1, (float) 0.2);
        insertAlim(db,context.getResources().getString(R.string.aliment_oeuf), R.drawable.oeuf, 145, (float) 12.3, (float) 0.7, (float) 10.3);
        insertAlim(db,context.getResources().getString(R.string.aliment_poisson_cabillaud), R.drawable.cabillaud, 85, (float) 19, (float) 0, (float) 0.8);
        insertAlim(db,context.getResources().getString(R.string.aliment_poivron), R.drawable.poivron, 20, (float) 0.9, (float) 4.6, (float) 0.2);
        insertAlim(db,context.getResources().getString(R.string.aliment_pomme), R.drawable.pomme, 53, (float) 0.3, (float) 11.3, (float) 0.2);
        insertAlim(db,context.getResources().getString(R.string.aliment_poulet_blanc), R.drawable.blancpoulet, 121, (float) 26.2, (float) 0, (float) 1.8);

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
            Performance performance = new Performance(1,"nom", 2,3);
            performances.add(performance);
            cursor.moveToNext();
        }
        return performances;
    }

    public List<Performance> getPerformances(int idUser, String categorie) {
        List<Performance> performances = new ArrayList<>();
        String sqlRequest = "SELECT * FROM performances WHERE nomPerf = '" + categorie + "' AND idUser = " + idUser;
        Cursor cursor = getReadableDatabase().rawQuery(sqlRequest, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Performance performance = new Performance(cursor.getInt(4), cursor.getString(1), cursor.getInt(2),
                    cursor.getInt(3));
            performances.add(performance);
            cursor.moveToNext();
        }
        return  performances;
    }

    public List<String> getPerformanceCategories(int idUser) {
        List<String> categories = new ArrayList<>();
        String sqlRequest = "SELECT DISTINCT nomPerf FROM performances WHERE idUser = " + idUser + " ORDER BY nomPerf";
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

    public int getAlimentsRowsCOUNT() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM aliments", null);
        return cursor.getCount();
    }

    public boolean isPseudoAlreadyUsed(String pseudo) {
        String pseudoTrim = pseudo.trim();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM users WHERE pseudo = '" + pseudoTrim + "'", null);
        return (cursor.getCount() != 0);
    }

    public void updatePseudo(String pseudo, int idUser) {
        String pseudoTrim = pseudo.trim();
        String updatePseudoSQL = "UPDATE users " +
                "SET pseudo = '" + pseudoTrim + "' " +
                "WHERE idUser = " + idUser;
        getWritableDatabase().execSQL(updatePseudoSQL);
    }

    public void updatePassword(String password, int idUser) {
        String passwordTrim = password.trim();
        String updatePasswordSQL = "UPDATE users " +
                "SET password = '" + passwordTrim + "' " +
                "WHERE idUser = " + idUser;
        getWritableDatabase().execSQL(updatePasswordSQL);
    }
    public boolean loginOK(String pseudo, String password) {
        String isLoginMatchSQL = "SELECT * FROM users WHERE pseudo = '" + pseudo + "' " +
                "AND password = '" + password + "'";

        Cursor cursor = getReadableDatabase().rawQuery(isLoginMatchSQL, null);
        return (cursor.getCount() != 0);
    }

    public int getUserID(String pseudo) {
        String selectUserSQL = "SELECT idUser FROM users WHERE pseudo = '" + pseudo + "'";
        Cursor cursor = getReadableDatabase().rawQuery(selectUserSQL, null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        cursor.close();
        return id;
    }
    public int getUserRows() {
        String requete = "SELECT * FROM users";
        Cursor cursor = getReadableDatabase().rawQuery(requete, null);
        return (cursor.getCount());
    }
    public String getPseudo(int idUser) {
        String selectUserSQL = "SELECT pseudo FROM users WHERE idUser = " + idUser;
        Cursor cursor = getReadableDatabase().rawQuery(selectUserSQL, null);
        cursor.moveToFirst();
        String pseudo = cursor.getString(0);
        cursor.close();
        return pseudo;
    }
    public String getTaille(int idUser) {
        String selectUserSQL = "SELECT taille FROM infos WHERE idUser = " + idUser;
        Cursor cursor = getReadableDatabase().rawQuery(selectUserSQL, null);
        cursor.moveToFirst();
        String taille = cursor.getString(0);
        cursor.close();
        return taille;
    }

    public String getPoids(int idUser) {
        String selectUserSQL = "SELECT poids FROM infos WHERE idUser = " + idUser;
        Cursor cursor = getReadableDatabase().rawQuery(selectUserSQL, null);
        cursor.moveToFirst();
        String poids = cursor.getString(0);
        cursor.close();
        return poids;
    }
    public String getSexe(int idUser) {
        String selectUserSQL = "SELECT sexe FROM infos WHERE idUser = " + idUser;
        Cursor cursor = getReadableDatabase().rawQuery(selectUserSQL, null);
        cursor.moveToFirst();
        String sexe = cursor.getString(0);
        cursor.close();
        return sexe;
    }
}
