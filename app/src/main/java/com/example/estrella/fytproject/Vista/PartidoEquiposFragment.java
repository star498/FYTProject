package com.example.estrella.fytproject.Vista;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estrella.fytproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;

/**
 * Created by Estrella on 20/04/2018.
 */

public class PartidoEquiposFragment extends Fragment implements View.OnClickListener {

    private Button btnaddplayer, btnequipos;
    private FirebaseAuth firebaseAuth;
    private TextView txtorganizador;
    public Bitmap bitmap;
    private ImageView imageView;
    String usuario;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View vista =inflater.inflate(R.layout.frangment_partido_equipos, container, false);

       // btnaddplayer=(Button)vista.findViewById(R.id.addplayer);
       // btnaddplayer.setOnClickListener(this);
        txtorganizador=(TextView)vista.findViewById(R.id.txtorganizador);
        btnequipos =(Button)vista.findViewById(R.id.btnequipo);


        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user= firebaseAuth.getCurrentUser();
        usuario = user.getDisplayName();


        txtorganizador.setText(usuario);
        String photoUrls="";
        imageView=(ImageView)vista.findViewById(R.id.imageorganizador);

        if( user.getPhotoUrl()!=null){
            photoUrls=  user.getPhotoUrl().toString();
        }else{
            photoUrls="https://icon-icons.com/icon/wonder-woman-people-avatar-person-human/55030";
        }
        new GetImagenFromURL(imageView).execute(photoUrls);

        btnequipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedfra= new PartidoConfigFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedfra).commit();

            }
        });

        return vista;


    }



    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.addplayer: {

                Toast.makeText(getActivity(), "aqui se agregara jugador", Toast.LENGTH_SHORT).show();

            }
            break;
        }

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
}
