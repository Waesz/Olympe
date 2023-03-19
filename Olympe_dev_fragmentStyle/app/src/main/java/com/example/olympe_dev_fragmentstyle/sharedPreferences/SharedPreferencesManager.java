package com.example.olympe_dev_fragmentstyle.sharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SharedPreferencesManager {
    protected static final String FILE_NAME = "shared_preferences";
    protected static final String KEY_LANGUE = "Langue";
    protected static final String DEFAULT_LANGUE_VALUE = "Francais";
    private List<String> langues;
    private Context context;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        this.context = context;
        langues = new ArrayList<>();
        langues.add("Francais");
        langues.add("Anglais");
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }


    public void setLangue(String langue) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(KEY_LANGUE, langue);
        editor.apply();
    }

    public String getLangue() {
        return sharedPreferences.getString(KEY_LANGUE, DEFAULT_LANGUE_VALUE);
    }

    public static void setLocal(Activity activity, String langueCode) {
        Locale locale = new Locale(langueCode);
        locale.setDefault(locale);
        Configuration config = activity.getResources().getConfiguration();
        config.setLocale(locale);
        activity.getResources().updateConfiguration(config, activity.getResources().getDisplayMetrics());
    }
}
