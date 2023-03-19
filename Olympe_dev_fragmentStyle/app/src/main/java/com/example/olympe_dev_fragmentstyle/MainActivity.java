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
import com.example.olympe_dev_fragmentstyle.performances.Performance;
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
        SharedPreferencesManager.setLocal(this, sharedPreferencesManager.getLangue());
        databaseManager = new DatabaseManager(this);
        Performance performance = new Performance(1, "1", 1, 1);
        Log.d("debug", "onCreate: " + performance);
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
    public void connect(int idUser) {
        this.idUser = idUser;
        this.isConnected = true;
    }
    public void disconnect() {
        this.idUser = -1;
        this.isConnected = false;
    }
}