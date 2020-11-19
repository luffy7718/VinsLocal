package com.lekcie.vinslocal.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.gson.Gson;


import com.lekcie.vinslocal.Models.Domaines;
import com.lekcie.vinslocal.Models.Recherche;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.App;
import com.lekcie.vinslocal.Utils.AsyncCallWS;
import com.lekcie.vinslocal.Utils.Constants;


public class MainActivity extends AppCompatActivity {


    Context context;
     int SPLASH_TIME_OUT=500;
     ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_main);
        imgLogo=findViewById(R.id.imgLogo);



        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {

                goToHome();//aller à homeactivity
            }

        }, SPLASH_TIME_OUT);




    }


    //appele les permissions à partir api 23= version 6.0
    private void goToHome() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= 23) {
            intent = new Intent(getApplicationContext(), PermissionActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), HomeActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }
    }


}