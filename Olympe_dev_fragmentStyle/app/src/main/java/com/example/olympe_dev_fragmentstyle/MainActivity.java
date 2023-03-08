package com.example.olympe_dev_fragmentstyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.example.olympe_dev_fragmentstyle.database.DatabaseManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

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

        Log.d("debug", "Main Activity: " + databaseManager.getDatabaseName());
        Log.d("debug", "Number of rows : " + databaseManager.getAlimentsRows());

        barreNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_aliments:changeFragment(new Fragment_aliments());
                    break;
                case R.id.menu_sante:changeFragment(new Fragment_Sante());
                    break;
                case R.id.menu_stats:changeFragment(new Fragment_Stats());
                    break;
                case R.id.menu_entrainement:changeFragment(new Fragment_entrainement());
                    break;
                case R.id.menu_parametres:changeFragment(new Fragment_parametres());
                    break;
            }
            return true;
        });
    }

    protected void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragmentLayout, fragment).commit();
        Log.i("changeFormat", fragment.toString());
    }

    public SharedPreferencesManager getSharedPreferencesManager() {
        return sharedPreferencesManager;
    }
}