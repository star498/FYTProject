package com.example.estrella.fytproject.Interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.estrella.fytproject.Interfaces.InicioInterface;
import com.example.estrella.fytproject.Interfaces.LoginInterface;
import com.example.estrella.fytproject.R;
import com.example.estrella.fytproject.Vista.Login;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Estrella on 19/04/2018.
 */

public class LoginModel implements LoginInterface.Model {

    private LoginInterface.Presenter presenter;
    private FirebaseAuth auth;
    CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAutListener;
    private static final String TAG ="facebook";
    private DatabaseReference databaseusers;
    private Boolean s=false;



    public LoginModel(LoginInterface.Presenter presenter){
        this.presenter = presenter;
    }


    @Override
    public  boolean validarexistencia(String correo) {
        final boolean[] x = {false};

        databaseusers= FirebaseDatabase.getInstance().getReference("users");

        Query id= databaseusers.orderByChild("correo").equalTo(correo).limitToFirst(1);

        id.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
               UserEntity s= postSnapshot.getValue(UserEntity.class);
                    if(s.getCorreo()!=null){
                        x[0] =true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       return x[0];
    }

    @Override
    public void AddUser(String correo, String usuario) {

           try {
               databaseusers= FirebaseDatabase.getInstance().getReference("users");
               String  id= databaseusers.push().getKey();
               UserEntity usere=new UserEntity(id,usuario, correo);
               databaseusers.child(id).setValue(usere);
               Log.d("ADDUSER","usuario ya existe");
               presenter.MostrarMenu();
           }catch (Exception e){
               e.getStackTrace();
               Log.d("ERROR ", e.getMessage());
           }


    }





    @Override
    public void  LoginFacebook(AccessToken accessToken){
                Log.d(TAG, "handleFacebookAccessToken:" + accessToken);

                AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
                firebaseAuth.signInWithCredential(credential);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                           presenter.MostrarMenu();

                    }




    @Override
    public void LoginGmail() {

    }

    @Override
    public void LoginCuenta(String correo, String pass) {

    }

}
