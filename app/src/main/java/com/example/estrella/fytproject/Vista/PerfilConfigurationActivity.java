package com.example.estrella.fytproject.Vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PerfilConfigurationActivity extends AppCompatActivity {

    private Button btn, button;
    private TextView navUsername, nombre, celular, informacion, edad;
    private ImageView imageView;
    public Bitmap bitmap;

    private Spinner deporte, sexo;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_configuration);

        btn = (Button)findViewById(R.id.regresar);
        button = (Button)findViewById(R.id.button2);
        celular = (TextView)findViewById(R.id.celular);
        informacion = (TextView)findViewById(R.id.descripcion);
        edad = (TextView)findViewById(R.id.edad);
        deporte = (Spinner)findViewById(R.id.spinnerDeporte);
        sexo = (Spinner)findViewById(R.id.spinnerSexo);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilConfigurationActivity.this,PerfilActivity.class);
                startActivity(intent);
                finish();
            }
        });




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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveUser();
            }
        });


    }

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

    private void saveUser() {
        //getting the values to save
        String name = nombre.getText().toString().trim();
        String email = navUsername.getText().toString().trim();
        String number = celular.getText().toString().trim();
        String about = informacion.getText().toString().trim();
        String age = edad.getText().toString().trim();
        String sport = deporte.getSelectedItem().toString();
        String sex = sexo.getSelectedItem().toString();



        if (!TextUtils.isEmpty(name)) {


            String id = databaseUser.push().getKey();


            UserEntity user= new UserEntity(id, name, email, number, about, age, sport, sex);


            databaseUser.child(id).setValue(user);


            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(this, "Porfavor Complete los Datos", Toast.LENGTH_LONG).show();
        }
    }
}
