package com.example.olympe_dev_fragmentstyle.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympe_dev_fragmentstyle.MainActivity;
import com.example.olympe_dev_fragmentstyle.R;
import com.example.olympe_dev_fragmentstyle.aliments.AdapterAliments;
import com.example.olympe_dev_fragmentstyle.aliments.Aliment;

import java.util.List;

public class Fragment_aliments extends Fragment {
    View rootView;
    RecyclerView recyclerView;
    List<Aliment> aliments;
    MainActivity activity;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_aliments, container, false);
        activity = (MainActivity) getActivity();
        aliments = activity.getDatabaseManager().getAliments();
        recyclerView = rootView.findViewById(R.id.aliments_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(new AdapterAliments(getContext(), aliments));

        return rootView;
    }
}
