package com.example.olympe_dev_fragmentstyle.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olympe_dev_fragmentstyle.R;

public class Fragment_parametres extends Fragment {
    View rootView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_parametres, container, false);
        rootView = rootView;
        return rootView;
    }
}