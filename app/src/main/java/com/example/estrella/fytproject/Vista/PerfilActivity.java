package com.example.estrella.fytproject.Vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estrella.fytproject.Interactor.UserEntity;
import com.example.estrella.fytproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;

public class PerfilActivity extends AppCompatActivity {

    Fragment selectedfra= null;
    private TextView navUsername, nombre;
    private ImageView imageView;

    public Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);*/

        ImageButton img = (ImageButton) findViewById(R.id.cancel);
        ImageButton img2 = (ImageButton) findViewById(R.id.mensaje);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilActivity.this,Principal.class);
                startActivity(intent);
                finish();
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilActivity.this,PerfilConfigurationActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.menutab);
        Fragment selectedfra2= new PendientesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, selectedfra2).commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        // Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            String name= user.getDisplayName();
            String email= user.getEmail();
            String photoUrls="";

            if( user.getPhotoUrl()!=null){
                photoUrls=  user.getPhotoUrl().toString();

            }else{
                photoUrls="https://icon-icons.com/icon/wonder-woman-people-avatar-person-human/55030";
            }

            //String number = user.getPhoneNumber();


            navUsername = (TextView) findViewById(R.id.correo);
            nombre = (TextView)  findViewById(R.id.nombre);
            imageView=(ImageView)findViewById(R.id.photo);

            navUsername.setText(email);
            nombre.setText(name);
            new GetImagenFromURL(imageView).execute(photoUrls);



        } else{
            Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show();
            //goLoginScreen();
        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_pendiente:
                    selectedfra= new PendientesFragment();
                    break;
                case R.id.navigation_invitar:
                    selectedfra= new FragmentInvitar();
                    // mTextMessage.setText(R.string.title_dashboard);
                    break;
                case R.id.navigation_calificacion:
                    selectedfra= new FragmentCalificacion();
                    //  return true;
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content, selectedfra).commit();
            return true;
        }
    };


    public class GetImagenFromURL extends AsyncTask<String, Void, Bitmap> {

        ImageView iv;

        public GetImagenFromURL(ImageView iv) {
            this.iv = iv;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urls= url[0];
            bitmap=null;
            try {
                InputStream sr= new java.net.URL(urls).openStream();
                bitmap = BitmapFactory.decodeStream(sr);

            }catch (Exception e){
                e.getStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            iv.setImageBitmap(bitmap);

        }
    }


}
