package com.example.olympe_dev_fragmentstyle.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olympe_dev_fragmentstyle.MainActivity;
import com.example.olympe_dev_fragmentstyle.R;
import com.example.olympe_dev_fragmentstyle.sharedPreferences.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class Fragment_profil extends Fragment {
    RelativeLayout layoutInfos, layoutIdentifiants, layoutBoutonONLINE, layoutBoutonOFFLINE;
    TextView textViewTaille, textViewPoids, textViewSexe, textViewModifierInfos,
            textViewPseudo, textViewModifierPseudo, textViewPassword, textViewModifierPassword;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    Spinner spinner_popup;
    TextView textViewLangue, textViewModifierLangue;
    Button buttonInscription, buttonConnexion, buttonDeconnexion, button_popup_langue_valider, button_popup_langue_retour,
            button_popup_infos_valider, button_popup_infos_retour;
    MainActivity activity;
    View rootView;
    //popup infos
    TextView popup_textViewTaille, popup_textViewPoids, popup_textViewSexe;
    NumberPicker popup_numberPickerTaille, popup_numberPickerPoids;
    RadioGroup popup_radioGroupSexe;
    RadioButton popup_checkedButton;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        rootView = inflater.inflate(R.layout.fragment_profil, container, false);
        alertDialogBuilder = new AlertDialog.Builder(activity);
        layoutInfos = rootView.findViewById(R.id.profil_layoutInfos);
        layoutIdentifiants = rootView.findViewById(R.id.profil_layoutIdentifiants);
        layoutBoutonONLINE = rootView.findViewById(R.id.profil_layoutBoutonONLINE);
        layoutBoutonOFFLINE = rootView.findViewById(R.id.profil_layoutBoutonOFFLINE);
        textViewLangue = rootView.findViewById(R.id.profil_textViewLangue);
        textViewLangue.setText(activity.getSharedPreferencesManager().getLangue());
        textViewModifierLangue = rootView.findViewById(R.id.profil_textViewChangerLangue);
        textViewModifierLangue.setOnClickListener(v -> {
            onModifierLangueClick();
        });

        if(activity.isConnected()) {
            layoutInfos.setVisibility(View.VISIBLE);
            layoutIdentifiants.setVisibility(View.VISIBLE);
            layoutBoutonONLINE.setVisibility(View.VISIBLE);
            layoutBoutonOFFLINE.setVisibility(View.GONE);
            initViews_ONLINE();
        } else {
            layoutInfos.setVisibility(View.GONE);
            layoutIdentifiants.setVisibility(View.GONE);
            layoutBoutonONLINE.setVisibility(View.GONE);
            layoutBoutonOFFLINE.setVisibility(View.VISIBLE);
            initViews_OFFLINE();
        }

        return rootView;
    }

    private void initViews_OFFLINE() {
        buttonInscription = rootView.findViewById(R.id.profil_buttonInscription);
        buttonConnexion = rootView.findViewById(R.id.profil_buttonConnexion);
        buttonInscription.setOnClickListener(v -> {
            onInscriptionClick();
        });
        buttonConnexion.setOnClickListener(v -> {
            onConnexionClick();
        });
    }

    private void initViews_ONLINE() {
        textViewTaille = rootView.findViewById(R.id.profil_textViewTaille);
        textViewPoids = rootView.findViewById(R.id.profil_textViewPoids);
        textViewSexe = rootView.findViewById(R.id.profil_textViewSexe);
        textViewModifierInfos = rootView.findViewById(R.id.profil_textViewChangerInfos);
        textViewPseudo = rootView.findViewById(R.id.profil_textViewPseudo);
        textViewModifierPseudo = rootView.findViewById(R.id.profil_textViewChangerPseudo);
        textViewPassword = rootView.findViewById(R.id.profil_textViewPassword);
        textViewModifierPassword = rootView.findViewById(R.id.profil_textViewChangerPassword);
        buttonDeconnexion = rootView.findViewById(R.id.profil_buttonDeconnexion);

        textViewModifierInfos.setOnClickListener(v -> {
            onModifierInfosClick();
        });
        textViewModifierPseudo.setOnClickListener(v -> {
            onModifierPseudoClick();
        });
        textViewModifierPassword.setOnClickListener(v -> {
            onModifierPasswordClick();
        });
        buttonDeconnexion.setOnClickListener(v -> {
            onDeconnexionClick();
        });
    }

    private void onModifierLangueClick() {
        final View popupView = getLayoutInflater().inflate(R.layout.popup_profil_langue, null);
        spinner_popup = popupView.findViewById(R.id.profil_popup_langue_spinner);
        List<String> langues = new ArrayList<>();
        langues.add(getResources().getString(R.string.all_langueFrancais));
        langues.add(getResources().getString(R.string.all_langueAnglais));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, langues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_popup.setAdapter(adapter);
        button_popup_langue_valider = popupView.findViewById(R.id.profil_popup_langue_buttonValider);
        button_popup_langue_retour = popupView.findViewById(R.id.profil_popup_langue_buttonRetour);
        alertDialogBuilder.setView(popupView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        button_popup_langue_valider.setOnClickListener(v -> {
            int position = -1;
            if(spinner_popup.getSelectedItem() != null) {
                position = spinner_popup.getSelectedItemPosition();
            }
            switch(position) {
                case 0 :
                    SharedPreferencesManager.setLocal(activity, "fr");
                    break;
                case 1 :
                    SharedPreferencesManager.setLocal(activity, "en");
                    break;
            }
            alertDialog.dismiss();
            activity.changeFragment(new Fragment_profil());
        });

        button_popup_langue_retour.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private void onModifierInfosClick() {
        final View popupView = getLayoutInflater().inflate(R.layout.popup_profil_infos, null);
        //init popup infos views
        popup_textViewTaille = rootView.findViewById(R.id.profil_tailleTV);
        popup_textViewPoids = rootView.findViewById(R.id.profil_poidsTV);
        popup_textViewSexe = rootView.findViewById(R.id.profil_sexeTV);
        popup_numberPickerTaille = rootView.findViewById(R.id.profil_tailleNP);
        popup_numberPickerTaille.setMinValue(50);
        popup_numberPickerTaille.setMaxValue(300);
        popup_numberPickerTaille.setValue(175);
        popup_numberPickerPoids = rootView.findViewById(R.id.profil_poidsNP);
        popup_numberPickerPoids.setMinValue(20);
        popup_numberPickerPoids.setMaxValue(200);
        popup_numberPickerPoids.setValue(75);
        popup_radioGroupSexe = rootView.findViewById(R.id.profil_sexeRG);
        button_popup_infos_valider = popupView.findViewById(R.id.profil_popup_validerB);
        button_popup_infos_retour = popupView.findViewById(R.id.profil_popup_retourB);
        alertDialogBuilder.setView(popupView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        button_popup_infos_valider.setOnClickListener(v -> {
            Button checkedButton = popupView.findViewById(popup_radioGroupSexe.getCheckedRadioButtonId());
            activity.getDatabaseManager().insertInfos(
                    popup_numberPickerTaille.getValue(),
                    popup_numberPickerPoids.getValue(),
                    checkedButton.toString(),
                    activity.getIdUser());
            Toast.makeText(activity, getResources().getString(R.string.profil_infosModifiees), Toast.LENGTH_SHORT);
            alertDialog.dismiss();
        });

        button_popup_infos_retour.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private void onModifierPseudoClick() {
        final View popupView = getLayoutInflater().inflate(R.layout.popup_profil_pseudo, null);
        EditText pseudo = popupView.findViewById(R.id.profil_popup_pseudo_editText);
        Button valider = popupView.findViewById(R.id.profil_popup_pseudo_buttonValider);
        Button retour = popupView.findViewById(R.id.profil_popup_pseudo_buttonRetour);

        pseudo.setText(textViewPseudo.getText());
        pseudo.selectAll();

        valider.setOnClickListener(v -> {
            if(activity.getDatabaseManager().isPseudoAlreadyUsed(pseudo.getText().toString())) {
                Toast.makeText(activity, getResources().getString(R.string.profil_pseudoUtilise), Toast.LENGTH_SHORT);
            } else if(pseudo.getText().toString().trim().isEmpty()) {
                Toast.makeText(activity, getResources().getString(R.string.all_erreurSaisie), Toast.LENGTH_SHORT);
            } else {
                activity.getDatabaseManager().updatePseudo(pseudo.getText().toString(), activity.getIdUser());
                Toast.makeText(activity, getResources().getString(R.string.profil_pseudoModifie), Toast.LENGTH_SHORT);
                alertDialog.dismiss();
            }
        });

        retour.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private void onModifierPasswordClick() {
        final View popupView = getLayoutInflater().inflate(R.layout.popup_profil_password, null);
        EditText password1 = popupView.findViewById(R.id.profil_popup_password1_editText);
        EditText password2 = popupView.findViewById(R.id.profil_popup_password2_editText);
        TextView erreurPassword1 = popupView.findViewById(R.id.profil_popup_password1_erreur);
        TextView erreurPassword2 = popupView.findViewById(R.id.profil_popup_password2_erreur);
        Button valider = popupView.findViewById(R.id.profil_popup_password_buttonValider);
        Button retour = popupView.findViewById(R.id.profil_popup_password_buttonRetour);


        valider.setOnClickListener(v -> {
             if (password1.getText().toString().trim().isEmpty()) {
                erreurPassword1.setText(getResources().getString(R.string.all_erreurSaisie));
                erreurPassword1.setVisibility(View.VISIBLE);
                erreurPassword1.setError("");
                erreurPassword2.setVisibility(View.GONE);
            } else if(!(password1.getText().toString().equals(password2.getText().toString()))) {
                erreurPassword2.setText(getResources().getString(R.string.profil_erreurPasswordEgaux));
                erreurPassword2.setVisibility(View.VISIBLE);
                erreurPassword2.setError("");
                erreurPassword1.setVisibility(View.GONE);
            } else {
                 activity.getDatabaseManager().updatePassword(password1.getText().toString(), activity.getIdUser());
                 Toast.makeText(activity, getResources().getString(R.string.profil_passwordModifie), Toast.LENGTH_SHORT);
                 alertDialog.dismiss();
             }
        });

        retour.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private void onInscriptionClick() {
        final View popupView = getLayoutInflater().inflate(R.layout.popup_profil_enregistrer, null);
        EditText pseudo = popupView.findViewById(R.id.profil_popup_enregistrer_pseudo);
        EditText password1 = popupView.findViewById(R.id.profil_popup_enregistrer_password1);
        EditText password2 = popupView.findViewById(R.id.profil_popup_enregistrer_password2);
        TextView erreurPassword1 = popupView.findViewById(R.id.profil_popup_enregistrer_password1_erreur);
        TextView erreurPassword2 = popupView.findViewById(R.id.profil_popup_enregistrer_password2_erreur);
        TextView tailleTV = popupView.findViewById(R.id.profil_popup_enregistrer_tailleTV);
        TextView poidsTV = popupView.findViewById(R.id.profil_popup_enregistrer_poidsTV);
        TextView sexeTV = popupView.findViewById(R.id.profil_popup_enregistrer_sexeTV);
        NumberPicker tailleNP = popupView.findViewById(R.id.profil_popup_enregistrer_tailleNP);
        tailleNP.setMinValue(50);
        tailleNP.setMaxValue(300);
        tailleNP.setValue(175);
        NumberPicker poidsNP = popupView.findViewById(R.id.profil_popup_enregistrer_poidsNP);
        poidsNP.setMinValue(20);
        poidsNP.setMaxValue(200);
        poidsNP.setValue(75);
        RadioGroup sexeRG = popupView.findViewById(R.id.profil_popup_enregistrer_sexeRG);
        Button checkedButton = popupView.findViewById(sexeRG.getCheckedRadioButtonId());
        Button validerB = popupView.findViewById(R.id.profil_popup_enregistrer_validerB);
        Button retourB = popupView.findViewById(R.id.profil_popup_enregistrer_retourB);

        validerB.setOnClickListener(v -> {
            if(activity.getDatabaseManager().isPseudoAlreadyUsed(pseudo.getText().toString())) {
                Toast.makeText(activity, getResources().getString(R.string.profil_pseudoUtilise), Toast.LENGTH_SHORT);
            } else if(pseudo.getText().toString().trim().isEmpty()) {
                Toast.makeText(activity, getResources().getString(R.string.all_erreurSaisie), Toast.LENGTH_SHORT);
            } else if (password1.getText().toString().trim().isEmpty()) {
                erreurPassword1.setText(getResources().getString(R.string.all_erreurSaisie));
                erreurPassword1.setVisibility(View.VISIBLE);
                erreurPassword1.setError("");
                erreurPassword2.setVisibility(View.GONE);
            } else if(!(password1.getText().toString().equals(password2.getText().toString()))) {
                erreurPassword2.setText(getResources().getString(R.string.profil_erreurPasswordEgaux));
                erreurPassword2.setVisibility(View.VISIBLE);
                erreurPassword2.setError("");
                erreurPassword1.setVisibility(View.GONE);
            } else {
                activity.getDatabaseManager().insertUser(pseudo.getText().toString(), password1.getText().toString());
                int idUser = activity.getDatabaseManager().getUserID(pseudo.getText().toString());
                activity.getDatabaseManager().insertInfos(
                        popup_numberPickerTaille.getValue(),
                        popup_numberPickerPoids.getValue(),
                        checkedButton.toString(),
                        idUser);
                Toast.makeText(activity, getResources().getString(R.string.profil_inscriptionReussie), Toast.LENGTH_SHORT);
                activity.changeFragment(new Fragment_profil());
                alertDialog.dismiss();
            }
        });

        retourB.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

    }

    private void onConnexionClick() {
        final View popupView = getLayoutInflater().inflate(R.layout.popup_profil_connexion, null);
        EditText pseudo = popupView.findViewById(R.id.profil_popup_connexion_pseudo);
        EditText password = popupView.findViewById(R.id.profil_popup_connexion_password);
        TextView erreur = popupView.findViewById(R.id.profil_popup_connexion_erreur);
        Button valider = popupView.findViewById(R.id.profil_popup_connexion_buttonValider);
        Button retour = popupView.findViewById(R.id.profil_popup_connexion_buttonRetour);

        valider.setOnClickListener(v -> {
            if(activity.getDatabaseManager().loginOK(pseudo.getText().toString(), password.getText().toString())) {
                int idUser = activity.getDatabaseManager().getUserID(pseudo.getText().toString());
                activity.setSession(idUser);
                activity.changeFragment(new Fragment_profil());
                alertDialog.dismiss();
                Toast.makeText(activity, getResources().getString(R.string.profil_connexionReussie), Toast.LENGTH_SHORT);
            } else {
                erreur.setVisibility(View.VISIBLE);
                erreur.setError("");
                erreur.setText(getResources().getString(R.string.profil_connexionRefusée));
            }
        });

        retour.setOnClickListener(v -> {

        });

        retour.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

    }

    private void onDeconnexionClick() {
        activity.disconnect();
        activity.changeFragment(new Fragment_profil());
    }
}