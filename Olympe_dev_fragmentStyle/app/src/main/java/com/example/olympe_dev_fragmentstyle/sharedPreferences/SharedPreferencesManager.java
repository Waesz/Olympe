package com.example.olympe_dev_fragmentstyle.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    protected static final String FILE_NAME = "shared_preferences";
    protected static final String KEY_TAILLE = "taille";
    protected static final String KEY_POIDS = "poids";
    protected static final String KEY_SEXE = "sexe";
    protected static final int DEFAULT_TAILLE_VALUE = 0;
    protected static final int DEFAULT_POIDS_VALUE = 0;
    protected static final String DEFAULT_SEXE_VALUE = "no_value";

    private Context context;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setValueTaille(int taille) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(KEY_TAILLE, taille);
        editor.apply();
    }

    public void setValuePoids(int poids) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(KEY_POIDS, poids);
        editor.apply();
    }

    public void setValueSexe(String sexe) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(KEY_SEXE, sexe);
        editor.apply();
    }

    public int getValueTaille() {
        return sharedPreferences.getInt(KEY_TAILLE, DEFAULT_TAILLE_VALUE);
    }

    public int getValuePoids() {
        return sharedPreferences.getInt(KEY_POIDS, DEFAULT_POIDS_VALUE);
    }

    public String getValueSexe() {
        return sharedPreferences.getString(KEY_SEXE, DEFAULT_SEXE_VALUE);
    }

    public boolean isCompleted() {
        return !(DEFAULT_TAILLE_VALUE == getValueTaille() ||
                DEFAULT_POIDS_VALUE == getValuePoids() ||
                DEFAULT_SEXE_VALUE.equals(getValueSexe()));

    }

}
