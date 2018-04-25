package com.example.estrella.fytproject.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.estrella.fytproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inicio extends AppCompatActivity {
private static int SPLASH_TIME_OUT= 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio);
      new Handler().postDelayed(new Runnable() {

          @Override
       public void run() {
              Intent intent= new Intent(Inicio.this, Fyt.class);
          startActivity(intent);
          finish();
          }
            }, SPLASH_TIME_OUT);

    }
}
