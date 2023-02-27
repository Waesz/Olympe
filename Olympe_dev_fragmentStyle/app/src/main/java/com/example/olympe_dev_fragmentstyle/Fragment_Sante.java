package com.example.olympe_dev_fragmentstyle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_Sante extends Fragment implements SensorEventListener {
    //******************* - Constantes - **************
    private static final float CALORIES_PAR_PAS = 0.035f;
    private static final float METRES_PAR_PAS = 0.7f;
    //******************* - Views - ***************
    private TextView nombrePasTitle, distanceTitle, caloriesTitle;
    private TextView nombrePasTV, distanceTV, caloriesTV;

    //****************** - Capteur - ****************
    private SensorManager sensorManager;
    private Sensor capteurPas;
    private boolean capteurPasDisponible;

    //**************** - Donnees - ********************
    private int nombrePas; // à mettre dans une BDD
    private float distance;
    private float calories;


    private MainActivity activity;
    private View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sante, container, false);
        activity = (MainActivity) getActivity();
        if(!activity.sharedPreferencesManager.isCompleted()) {
            AlertDialog.Builder popup = new AlertDialog.Builder(getContext());
            popup.setTitle("Informations requises");
            popup.setMessage("Veuillez entrer vos informations santé dans l'onglet profil");
            popup.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    activity.changeFragment(new Fragment_profil());
                }
            });
            popup.show();
        }


        initViews();
        initCapteurs();

        return rootView;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == capteurPas) {
            //*********** - Update les données - ***************
            nombrePas = (int) sensorEvent.values[0];
            distance = nombrePas*METRES_PAR_PAS;
            calories = nombrePas*CALORIES_PAR_PAS;

            //************ - Update les Views - ***************
            nombrePasTV.setText(String.valueOf(nombrePas) + " pas");
            distanceTV.setText(String.valueOf(distance/1000) + " km");
            caloriesTV.setText(String.valueOf(calories) + " kcal");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(capteurPasDisponible) {
            sensorManager.registerListener(this, capteurPas, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(capteurPasDisponible) {
            sensorManager.unregisterListener(this, capteurPas);
        }
    }

    public void initViews() {

        nombrePasTitle = rootView.findViewById(R.id.sante_nombrePasTitle);
        distanceTitle = rootView.findViewById(R.id.sante_distanceTitle);
        caloriesTitle = rootView.findViewById(R.id.sante_caloriesTitle);
        nombrePasTV = rootView.findViewById(R.id.sante_nombrePasTV);
        distanceTV = rootView.findViewById(R.id.sante_distanceTV);
        caloriesTV = rootView.findViewById(R.id.sante_caloriesTV);

    }

    public void initCapteurs() {

        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            capteurPas = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            capteurPasDisponible = true;
        } else {
            nombrePasTitle.setText("Capteur de pas non disponible");
            capteurPasDisponible = false;
        }

    }
}