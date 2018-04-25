package com.example.estrella.fytproject.Vista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estrella.fytproject.Interactor.UserEntity;
import com.example.estrella.fytproject.Interfaces.LoginInterface;
import com.example.estrella.fytproject.Presentador.LoginPresenter;
import com.example.estrella.fytproject.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Login extends AppCompatActivity  implements  GoogleApiClient.OnConnectionFailedListener ,LoginInterface.View{
    //sesion google
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    public static final int SING_IN_CODE = 777;
    //sesion facebook
    LoginButton loginButton;
    CallbackManager callbackManager;
    private  TextView name, email1, id1, cumple;
    private Button button2;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAutListener;
    private static final String TAG ="facebook";
    private DatabaseReference databaseusers;

    private Button btnfacebook;
    private EditText txtcorreo, txtcontraseña;
    private LoginInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseAutListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!=null){
                    goScreen();
                }
            }
        };
        setContentView(R.layout.activity_login);
        txtcorreo = (EditText) findViewById(R.id.txtcorreo);
        txtcontraseña = (EditText) findViewById(R.id.txtcontraseña);

        presenter = new LoginPresenter(this);
        //sesion google
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleApiClient= new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        signInButton= (SignInButton)findViewById(R.id.singInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, SING_IN_CODE);
            }
        });

        //sesion facebook

        callbackManager = CallbackManager.Factory.create();
        btnfacebook=(Button)findViewById(R.id.btnfacebook);
        btnfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("email", "public_profile", "user_birthday"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                          //  if(presenter.validarexistencia(user.getEmail())!=true){

                              //  Toast.makeText(Login.this, "no exite ", Toast.LENGTH_SHORT).show();
                               presenter.AddUser(user.getEmail(), user.getDisplayName());
                      //      }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //goScreen();
                        }
                    }
                });
    }

    public void goScreen(){
        Intent intent= new Intent(this, Principal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        //Toast.makeText(this, "codigo es "+ requestCode, Toast.LENGTH_SHORT).show();
        if(requestCode==SING_IN_CODE){
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            SingInGoogleResult(result);

        }

    }

    private void SingInGoogleResult(GoogleSignInResult result) {
        if(result.isSuccess()){

            firebaseAuthWithGoogle(result.getSignInAccount());

        }else{
            Toast.makeText(this, "no se puede iniciar sesión"+ result.isSuccess(), Toast.LENGTH_LONG).show();
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        AuthCredential credencial = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
        firebaseAuth.signInWithCredential(credencial).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), R.string.not_firebase_auth,Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    //boolean c=presenter.validarexistencia(user.getEmail());
                  //  Toast.makeText(Login.this, "correo "+ user.getEmail(), Toast.LENGTH_SHORT).show();
//Toast.makeText(Login.this, "llego aqui  "+ c, Toast.LENGTH_SHORT).show();

                        //Toast.makeText(Login.this, "no exite ", Toast.LENGTH_SHORT).show();

                         presenter.AddUser(user.getEmail(), user.getDisplayName());



                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAutListener);
    }
    @Override
    protected void onStop(){
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAutListener);
    }

    //google
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "fallo conecccion" + connectionResult, Toast.LENGTH_SHORT).show();
    }

    public void Ingresar(View view){

        String email=  txtcorreo.getText().toString();
        String password=   txtcontraseña.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Ingresar Correo Electronico!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Ingrese Contraseña!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(Login.this, "crear Usuario con Correo:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                //     progressBar.setVisibility(View.GONE);
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(Login.this, "Autenticacion fallida." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    goScreen();

                }
            }
        });

    }
    public void Regresar2(View view){
        Intent intent = new Intent(this, Inicio.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);



    }

    @Override
    public void MostrarMenu() {
        Intent intent= new Intent(this, Principal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
