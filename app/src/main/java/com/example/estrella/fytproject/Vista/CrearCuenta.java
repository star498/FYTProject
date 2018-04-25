package com.example.estrella.fytproject.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.estrella.fytproject.Interfaces.InicioInterface;
import com.example.estrella.fytproject.Presentador.InicioPresenter;
import com.example.estrella.fytproject.R;
import com.google.firebase.auth.FirebaseAuth;

public class CrearCuenta extends AppCompatActivity implements InicioInterface.ViewInterface{

    private EditText correo, contraseña;
    private Button btncrearcuenta, btniniciarsession;
    private ProgressBar progressBar;

    private InicioInterface.PresenterInterface presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        btncrearcuenta = (Button) findViewById(R.id.btncrearcuenta);
        btniniciarsession = (Button) findViewById(R.id.iniciarsession);
        correo = (EditText) findViewById(R.id.correo);
        contraseña = (EditText) findViewById(R.id.contraseña);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        presenter = new InicioPresenter(this);

        btniniciarsession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearCuenta.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btncrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = correo.getText().toString().trim();
                String password = contraseña.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Ingresar Correo Electronico!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Ingrese Contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Contraseña demasiado corta, minimo 6 caracteres!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                presenter.CrearCuenta(email, password);

            }
        });


    }

    @Override
    public void MostrarMenu() {
        Toast.makeText(this, "llego al mostraractivity", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CrearCuenta.this, Principal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void Regresar(View view){

        Intent intent = new Intent(CrearCuenta.this, Fyt.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
