package com.example.estrella.fytproject.Vista;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.estrella.fytproject.R;

public class Partido extends AppCompatActivity {
    Fragment selectedfra= null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_partido:
                    selectedfra= new PartidoFrangment();
                    break;
                case R.id.navigation_equipos:
                    selectedfra= new PartidoEquiposFragment();
                   // mTextMessage.setText(R.string.title_dashboard);
                    break;
                case R.id.navigation_configuracion:
                    selectedfra= new PartidoConfigFragment();
                  //  return true;
                    break;
            }
       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedfra).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Fragment selectedfra2= new PartidoFrangment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedfra2).commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }

}
