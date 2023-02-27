package com.example.olympe_dev_fragmentstyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView barreNavigation;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(new Fragment_Sante());
        barreNavigation = findViewById(R.id.menu_barreNavigation);
        sharedPreferencesManager = new SharedPreferencesManager(this);

        barreNavigation.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
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
    protected SharedPreferencesManager getSharedPreferencesManager() {
        return sharedPreferencesManager;
    }
}