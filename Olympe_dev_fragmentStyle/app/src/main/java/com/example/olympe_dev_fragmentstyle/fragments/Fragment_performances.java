package com.example.olympe_dev_fragmentstyle.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.olympe_dev_fragmentstyle.MainActivity;
import com.example.olympe_dev_fragmentstyle.R;
import com.example.olympe_dev_fragmentstyle.performances.Performance;

import java.util.List;

public class Fragment_performances extends Fragment {
    List<String> categories;
    Spinner spinner;
    TableLayout tableLayout;
    TableRow headerRow;
    MainActivity activity;
    View rootView;
    TextView stats;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_performances, container, false);
        activity = (MainActivity) getActivity();
        tableLayout = rootView.findViewById(R.id.performances_tableLayout);

        headerRow = new TableRow(activity);

        TextView exercice = new TextView(activity);
        exercice.setText(R.string.performances_tableau_titre1);
        headerRow.addView(exercice);

        TextView valeur = new TextView(activity);
        valeur.setText(R.string.performances_tableau_titre2);
        headerRow.addView(valeur);

        TextView date = new TextView(activity);
        date.setText(R.string.performances_tableau_titre3);
        headerRow.addView(date);

        tableLayout.addView(headerRow);

        categories = activity.getDatabaseManager().getPerformanceCategories();
        spinner = rootView.findViewById(R.id.performances_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableLayout.removeAllViews();
                tableLayout.addView(headerRow);
                String selectedCategorie = spinner.getSelectedItem().toString();
                List<Performance> performanceList = activity.getDatabaseManager().getPerformances(selectedCategorie);
                for(Performance performance : performanceList) {
                    TableRow row = new TableRow(activity);

                    TextView exercice = new TextView(activity);
                    exercice.setText(performance.getNomPerf());
                    row.addView(exercice);

                    TextView valeur = new TextView(activity);
                    valeur.setText(String.valueOf(performance.getValeurPerf()));
                    row.addView(valeur);

                    TextView date = new TextView(activity);
                    date.setText(String.valueOf(performance.getDatePerf()));
                    row.addView(date);

                    tableLayout.addView(row);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return rootView;
    }
}
