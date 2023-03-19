package com.example.olympe_dev_fragmentstyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.example.olympe_dev_fragmentstyle.database.DatabaseManager;
import com.example.olympe_dev_fragmentstyle.fragments.Fragment_Sante;
import com.example.olympe_dev_fragmentstyle.fragments.Fragment_aliments;
import com.example.olympe_dev_fragmentstyle.fragments.Fragment_entrainement;
import com.example.olympe_dev_fragmentstyle.fragments.Fragment_profil;
import com.example.olympe_dev_fragmentstyle.fragments.Fragment_performances;
import com.example.olympe_dev_fragmentstyle.sharedPreferences.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private int idUser = -1;
    private boolean isConnected = false;

    BottomNavigationView barreNavigation;
    SharedPreferencesManager sharedPreferencesManager;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(new Fragment_Sante());
        barreNavigation = findViewById(R.id.menu_barreNavigation);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        databaseManager = new DatabaseManager(this);
        databaseManager.clearTableAlim();
        databaseManager.clearTablePerf();
        databaseManager.fillDatas();
        Log.d("debug", "perfs : " + databaseManager.getPerformances());
        Log.d("debug", "'" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', ");

        Log.d("debug", "aliments : " + databaseManager.getAlimentsRows());
        Log.d("debug", "aliments count : " + databaseManager.getAlimentsRowsCOUNT());
        barreNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_aliments:changeFragment(new Fragment_aliments());
                    break;
                case R.id.menu_sante:changeFragment(new Fragment_Sante());
                    break;
                case R.id.menu_performances:changeFragment(new Fragment_performances());
                    break;
                case R.id.menu_entrainement:changeFragment(new Fragment_entrainement());
                    break;
                case R.id.menu_profil: changeFragment(new Fragment_profil());
                    break;
            }
            return true;
        });
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragmentLayout, fragment).commit();
    }

    public SharedPreferencesManager getSharedPreferencesManager() {
        return sharedPreferencesManager;
    }
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    public boolean isConnected() {return isConnected;}
    public int getIdUser() {return idUser;}
    public void setSession(int idUser) {
        this.idUser = idUser;
        this.isConnected = true;
    }
    public void disconnect() {
        this.idUser = -1;
        this.isConnected = false;
    }
}