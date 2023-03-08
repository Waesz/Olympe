package com.example.olympe_dev_fragmentstyle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_profil extends Fragment {
    MainActivity activity;
    View rootView;
    TextView tailleTV, poidsTV, sexeTV;
    NumberPicker tailleNP, poidsNP;
    RadioGroup sexeRG;
    RadioButton checkedButton;
    Button validerB, retourB;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profil, container, false);
        activity = (MainActivity) getActivity();
        initView();
        validerB.setOnClickListener(view -> {
            onValidateClick();
        });

        retourB.setOnClickListener(view -> {
            onBackClick();
        });
        return rootView;
    }

    private void initView() {
        tailleTV = rootView.findViewById(R.id.profil_tailleTV);
        poidsTV = rootView.findViewById(R.id.profil_poidsTV);
        sexeTV = rootView.findViewById(R.id.profil_sexeTV);
        tailleNP = rootView.findViewById(R.id.profil_tailleNP);
        tailleNP.setMinValue(50);
        tailleNP.setMaxValue(300);
        tailleNP.setValue(175);
        poidsNP = rootView.findViewById(R.id.profil_poidsNP);
        poidsNP.setMinValue(20);
        poidsNP.setMaxValue(200);
        poidsNP.setValue(75);
        sexeRG = rootView.findViewById(R.id.profil_sexeRG);
        validerB = rootView.findViewById(R.id.profil_validerB);
        retourB = rootView.findViewById(R.id.profil_retourB);
    }

    private void insertPreferences() {
        activity.getSharedPreferencesManager().setValueTaille(tailleNP.getValue());
        activity.getSharedPreferencesManager().setValuePoids(poidsNP.getValue());
        checkedButton = rootView.findViewById(sexeRG.getCheckedRadioButtonId());
        activity.getSharedPreferencesManager().setValueSexe(checkedButton.getText().toString());
    }

    private void onValidateClick() {
        if(tailleNP.getValue() < 50 || tailleNP.getValue() > 300
                || poidsNP.getValue() < 20 || poidsNP.getValue() > 200) {
            Toast.makeText(activity, getResources().getString(R.string.all_erreurSaisie), Toast.LENGTH_SHORT).show();
        } else {
            insertPreferences();
            activity.changeFragment(new Fragment_parametres());
        }
    }

    private void onBackClick() {
        activity.changeFragment(new Fragment_parametres());
    }
}