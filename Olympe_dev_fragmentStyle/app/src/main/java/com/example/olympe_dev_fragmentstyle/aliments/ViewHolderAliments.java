package com.example.olympe_dev_fragmentstyle.aliments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympe_dev_fragmentstyle.R;

public class ViewHolderAliments extends RecyclerView.ViewHolder {
    ImageView imageAliment;
    TextView nom, calories, proteines, glucides, lipides;
    public ViewHolderAliments(@NonNull View itemView) {
        super(itemView);
        nom = itemView.findViewById(R.id.view_aliments_nom);
        imageAliment = itemView.findViewById(R.id.view_aliments_image);
        calories = itemView.findViewById(R.id.view_aliments_calories);
        proteines = itemView.findViewById(R.id.view_aliments_proteines);
        glucides = itemView.findViewById(R.id.view_aliments_glucides);
        lipides = itemView.findViewById(R.id.view_aliments_lipides);

    }
}
