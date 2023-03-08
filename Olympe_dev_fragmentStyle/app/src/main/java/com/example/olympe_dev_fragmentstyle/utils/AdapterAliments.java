package com.example.olympe_dev_fragmentstyle.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympe_dev_fragmentstyle.R;

import java.util.List;

public class AdapterAliments extends RecyclerView.Adapter<ViewHolderAliments> {

    Context context;
    List<Aliment> listeAliments;

    public AdapterAliments(Context context, List<Aliment> listeAliments) {
        this.context = context;
        this.listeAliments = listeAliments;
    }

    @NonNull
    @Override
    public ViewHolderAliments onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderAliments(LayoutInflater.from(context).inflate(R.layout.view_aliments, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAliments holder, int position) {
        holder.nom.setText(listeAliments.get(position).getNom());
        holder.calories.setText(context.getResources().getString(R.string.aliments_calories) + listeAliments.get(position).getCalories() + "kcal");
        holder.proteines.setText(context.getResources().getString(R.string.aliments_proteines) + listeAliments.get(position).getProteines() + "g");
        holder.glucides.setText(context.getResources().getString(R.string.aliments_glucides) + listeAliments.get(position).getGlucides() + "g");
        holder.lipides.setText(context.getResources().getString(R.string.aliments_lipides) + listeAliments.get(position).getLipides()+ "g");
    }

    @Override
    public int getItemCount() {
        return listeAliments.size();
    }
}
