package com.example.estrella.fytproject.Vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estrella.fytproject.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView navUsername, nombre;
    private ImageView imageView;

    public Bitmap bitmap;
    Fragment selectedfra= null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nuevospartidos:
                    selectedfra= new PrincipalPartidos();
                    break;
                case R.id.pendientes:
                    selectedfra= new PrincipalPendientes();
                    // mTextMessage.setText(R.string.title_dashboard);
                    break;
                case R.id.notificaciones:
                    selectedfra= new PrincipalNotificaciones();
                    //  return true;
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_principal, selectedfra).commit();
            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //fragment partidos por default
        Fragment selectedfra2= new PrincipalPartidos();
        selectedfra2= new PrincipalPartidos();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_principal, selectedfra2).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.principal_navegation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnircrear);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this,Partido.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ////grid
     //   mainGrid= (GridLayout)findViewById(R.id.mainGrid) ;
       // setToasteEvent(mainGrid);

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

            View headerView = navigationView.getHeaderView(0);
            navUsername = (TextView) headerView.findViewById(R.id.txtcorreouser);
            nombre = (TextView)  headerView.findViewById(R.id.txtusuario);
            imageView=(ImageView)headerView.findViewById(R.id.imageuser);

            navUsername.setText(email);
            nombre.setText(name);
            new GetImagenFromURL(imageView).execute(photoUrls);

        } else{
            Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show();
            goLoginScreen();
        }

        //fragments


    }

   /* private void setToasteEvent(GridLayout mainGrid) {
        for(int i=0;i<mainGrid.getChildCount();i++){
            CardView cardView= (CardView)mainGrid.getChildAt(i);
            final int finalr=i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finalr==0){
                      Intent intent = new Intent(Principal.this,Partido.class);
                        startActivity(intent);
                    }
                    if(finalr==1){
                  /*      Intent intent = new Intent(Principal.this,MapsPrueba.class);
                        startActivity(intent);
                    }
                    if(finalr==3){

                    }
                    if(finalr==4){

                    }


                }
            });
        }
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_information) {
            Intent explicit_intent = new Intent(Principal.this,PerfilActivity.class);
            startActivity(explicit_intent);
        } else if (id == R.id.nav_amigos) {


        } else if (id == R.id.nav_invitaramigos) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            goLoginScreen();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void goLoginScreen() {
        Intent intent= new Intent(this, Fyt.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
