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

public class PrincipalNotificaciones extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View vista = inflater.inflate(R.layout.principal_notificaciones, container, false);
        return vista;

    }
}
