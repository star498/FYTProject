package com.example.estrella.fytproject.Vista;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.estrella.fytproject.R;

/**
 * Created by Estrella on 25/04/2018.
 */

public class PrincipalPartidos extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View vista = inflater.inflate(R.layout.principal_partidos, container, false);
        return vista;

    }


}
