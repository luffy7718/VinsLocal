package com.lekcie.vinslocal.Activities;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lekcie.vinslocal.Models.Domaines;
import com.lekcie.vinslocal.Models.Vin;
import com.lekcie.vinslocal.Models.Vins;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.App;
import com.lekcie.vinslocal.Utils.Constants;

import java.util.ArrayList;


public class MapsRayonActivity extends AppCompatActivity {

    private int rayon = 5;
    Context context;



    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_mapsrayon);
        //modifie le rayon en fonction du bouton que l'on à cliquer


        Intent i = getIntent();
        setRayon(i.getIntExtra("rayon", rayon));


    }







}
