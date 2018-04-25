package com.example.estrella.fytproject.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.estrella.fytproject.R;

public class Fyt extends AppCompatActivity {
    private ViewFlipper vf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fyt);
        //flipper
        int image[]= {R.drawable.bal,R.drawable.bal1, R.drawable.bal4,R.drawable.banlon};
        vf= (ViewFlipper) findViewById(R.id.v_flipper);
        for(int images: image){
            flipperImages(images);
        }
        
        
    }

    private void flipperImages(int images) {
        ImageView imageView= new ImageView(this);
        imageView.setBackgroundResource(images);
        vf.addView(imageView);
        vf.setFlipInterval(5000);
        vf.setAutoStart(true);


        //animacion
        vf.setInAnimation(this, android.R.anim.slide_in_left);
        vf.setOutAnimation(this, android.R.anim.slide_out_right);
    }
    public void flogin(View view){
        Intent intent = new Intent(Fyt.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void fcuenta(View view){
        Intent intent = new Intent(Fyt.this, CrearCuenta.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
