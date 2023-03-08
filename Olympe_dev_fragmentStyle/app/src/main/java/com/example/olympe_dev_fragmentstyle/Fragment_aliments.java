package com.example.olympe_dev_fragmentstyle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olympe_dev_fragmentstyle.utils.AdapterAliments;
import com.example.olympe_dev_fragmentstyle.utils.Aliment;

import java.util.ArrayList;
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
        aliments = activity.databaseManager.getAliments();
        recyclerView = rootView.findViewById(R.id.aliments_recyclerView);
        Log.d("debug", "recycler : " + rootView.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(new AdapterAliments(getContext(), aliments));

        return rootView;
    }
}
