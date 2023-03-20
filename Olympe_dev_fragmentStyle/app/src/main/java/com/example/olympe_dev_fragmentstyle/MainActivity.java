package com.example.olympe_dev_fragmentstyle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
    public static final int ACTIVITY_RECOGNITION_CODE  = 1;
    private int idUser = -1;
    private boolean isConnected = false;

    BottomNavigationView barreNavigation;
    SharedPreferencesManager sharedPreferencesManager;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        SharedPreferencesManager.setLocal(this, sharedPreferencesManager.getLangue());
        changeFragment(new Fragment_aliments());
        barreNavigation = findViewById(R.id.menu_barreNavigation);
        deleteDatabase("database.db");
        databaseManager = new DatabaseManager(this);
        barreNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_aliments:
                    changeFragment(new Fragment_aliments());
                    databaseManager.clearTableAlim();
                    databaseManager.fillAliments(databaseManager.getWritableDatabase());
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACTIVITY_RECOGNITION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getResources().getString(R.string.all_permissionAccordee), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.all_permissionRefusee), Toast.LENGTH_SHORT).show();
            }
        }
    }
}