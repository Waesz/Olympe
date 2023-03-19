package com.example.olympe_dev_fragmentstyle.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olympe_dev_fragmentstyle.MainActivity;
import com.example.olympe_dev_fragmentstyle.R;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class Fragment_entrainement extends Fragment {
    private RelativeLayout layout;
    private TextView messageConnexion, textView_AjouterCategorie, textView_ErreurSpinner, textView_ErreurEditText;
    private Spinner spinner_Categories;
    private EditText editText_ValeurPerf;
    private Button button_Valider;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    private EditText editText_popup;
    private Button button_popup_valider, button_popup_retour;

    private List<String> categories;
    private boolean isError = false;
    private View rootView;
    private MainActivity activity;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_entrainement, container, false);
        activity = (MainActivity) getActivity();

        messageConnexion = rootView.findViewById(R.id.entrainement_textViewConnexion);
        layout = rootView.findViewById(R.id.entrainement_layout);

        if(activity.isConnected()) {
            messageConnexion.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            initViews();
        } else {
            messageConnexion.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
        }

        return rootView;
    }

    private void initViews() {
        textView_AjouterCategorie = rootView.findViewById(R.id.entrainement_textViewAjouterCategorie);
        spinner_Categories = rootView.findViewById(R.id.entrainement_spinner);
        editText_ValeurPerf = rootView.findViewById(R.id.entrainement_editText);
        button_Valider = rootView.findViewById(R.id.entrainement_button_valider);
        textView_ErreurSpinner = rootView.findViewById(R.id.entrainement_textViewErreurSpinner);
        textView_ErreurEditText = rootView.findViewById(R.id.entrainement_textViewErreurEditText);
        categories = new ArrayList<>();
        categories.addAll(activity.getDatabaseManager().getPerformanceCategories(activity.getIdUser()));
        if(categories.isEmpty()) {
            categories.add("Entrer une catégorie");
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Categories.setAdapter(adapter);

        button_Valider.setOnClickListener(v -> {
            onValidateClick();
        });

        textView_AjouterCategorie.setOnClickListener(v -> {
            onAjouterCategorie();
        });
    }

    private void onValidateClick() {
        isError = false;
        String selectedCategorie = null;
        int valeurPerf = 0;
        if(spinner_Categories.getSelectedItem() != null && spinner_Categories.getSelectedItem() != "Entrer une catégorie") {
            selectedCategorie = spinner_Categories.getSelectedItem().toString();
        } else {
            textView_ErreurSpinner.setVisibility(View.VISIBLE);
            textView_ErreurSpinner.setError("");
            textView_ErreurSpinner.setText("Vous devez entrer une catégorie ");
            isError = true;
        }

        if(TextUtils.isEmpty(editText_ValeurPerf.getText().toString().trim())) {
            textView_ErreurEditText.setVisibility(View.VISIBLE);
            textView_ErreurEditText.setError("");
            textView_ErreurEditText.setText("Vous devez entrer une valeur ");
            isError = true;
        } else {
            valeurPerf = Integer.parseInt(editText_ValeurPerf.getText().toString().trim());
        }

        if(!isError) {
            activity.getDatabaseManager().insertPerf(activity.getDatabaseManager().getWritableDatabase(),activity.getIdUser(), selectedCategorie, valeurPerf);
            Toast.makeText(activity, getResources().getString(R.string.entrainement_performanceAjoutée), Toast.LENGTH_SHORT).show();
            activity.changeFragment(new Fragment_performances());
        }
    }

    private void onAjouterCategorie() {
        alertDialogBuilder = new AlertDialog.Builder(activity);
        final View popupView = getLayoutInflater().inflate(R.layout.popup_entrainement, null);
        editText_popup = popupView.findViewById(R.id.entrainement_popup_editText);
        button_popup_valider = popupView.findViewById(R.id.entrainement_popup_buttonValider);
        button_popup_retour = popupView.findViewById(R.id.entrainement_popup_buttonRetour);
        alertDialogBuilder.setView(popupView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        button_popup_valider.setOnClickListener(v -> {
            String ajout = editText_popup.getText().toString();
            categories.add(ajout);
            spinner_Categories.setSelection(categories.indexOf(ajout));
            alertDialog.dismiss();
            Log.d("debug", "categories : " + categories);
            Log.d("debug", "spinner categories : " + spinner_Categories.getSelectedItem().toString());
        });

        button_popup_retour.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }
}