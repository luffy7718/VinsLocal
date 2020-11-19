package com.lekcie.vinslocal.Holders;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;

import android.view.View;


import android.widget.TextView;


import com.lekcie.vinslocal.Activities.InfosItemVinsActivity;

import com.lekcie.vinslocal.Models.Domaine;


import com.lekcie.vinslocal.Models.Vin;

import com.lekcie.vinslocal.R;

import java.util.Locale;


public class VinHolders extends RecyclerView.ViewHolder {

    private TextView txtVin;
    private TextView txtDomaineDistance;
    private TextView txtPrix;

    private View cardItemvins;



    public VinHolders(View itemView) {

        super(itemView);

        txtVin = itemView.findViewById(R.id.txtVin);
        txtDomaineDistance = itemView.findViewById(R.id.txtDomaineDistance);
        txtPrix = itemView.findViewById(R.id.txtPrix);

        cardItemvins = itemView.findViewById(R.id.cardItemvins);



    }

    public void setVin(final Vin vin, final Context context, final Domaine domaine,final double km) {

        txtVin.setText(vin.getNomVin());
        txtPrix.setText("" + String.format(Locale.FRANCE, "%.1f", vin.getPrix()) + "€");




        cardItemvins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;
                intent = new Intent(context, InfosItemVinsActivity.class);
                intent.putExtra("nomvin", vin.getNomVin());
                intent.putExtra("prix", vin.getPrix());
                intent.putExtra("distanceDomaine",domaine.getNomDomaine() + " à " + km + " km");

                intent.putExtra("autreInfos", vin.getAutreInfos());
                intent.putExtra("telephone", domaine.getTelephone());
                intent.putExtra("adresse", domaine.getAdresse());
                intent.putExtra("horaire", domaine.getHoraires());
                intent.putExtra("idVins", vin.getIdVins());
                intent.putExtra("idDomaine", domaine.getIdDomaine());
                intent.putExtra("nomDomaine", domaine.getNomDomaine());
                intent.putExtra("latitude", domaine.getLatitude());
                intent.putExtra("longitude", domaine.getLongitude());
                intent.putExtra("annee", vin.getMillesime());
                intent.putExtra("couleur", vin.getCouleur());


                context.startActivity(intent);

            }
        });

    }




}
