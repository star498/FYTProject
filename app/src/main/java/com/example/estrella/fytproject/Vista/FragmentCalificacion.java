package com.example.estrella.fytproject.Vista;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.estrella.fytproject.R;

/**
 * Created by Sol Mamani on 24/04/2018.
 */

public class FragmentCalificacion extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_calificacion, container, false);
    }
}
