package com.lekcie.vinslocal.Holders;

import android.app.Activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;


import com.lekcie.vinslocal.Activities.InfosItemVinsActivity;
import com.lekcie.vinslocal.Activities.ListFilterVinsActivity;

import com.lekcie.vinslocal.Models.Domaine;
import com.lekcie.vinslocal.Models.Domaines;

import com.lekcie.vinslocal.Models.Vin;

import com.lekcie.vinslocal.R;

import java.util.Locale;


//définir les widgets dans la vue intérieur
public class FilterVinsHolder extends RecyclerView.ViewHolder {

    private final TextView txtFiltItemVin;
    private final TextView txtDomaineDistance;

    private final TextView txtFiltItemPrix;

    private View lltItemDomaine;
    private String StrnomDomaine;
    private String StrHoraires;
    private String StrAdresse;
    private String StrTelephone;
    private int idVins;
    private double prix;
    private double latitude;
    private double longitude;
    private int annee;
    private String StrAutreinfos;
    public View separator;
    public ImageView imgDetailClose;
    String StrNomVins;
    String StrCouleur;
    private int idDomaine;

    Vin vin;
    Activity activity;

    public FilterVinsHolder(View view) {
        super(view);

        txtFiltItemVin = (TextView) view.findViewById(R.id.txtFiltItemVin);
        txtDomaineDistance = (TextView) view.findViewById(R.id.txtDomaineDistance);

        txtFiltItemPrix = view.findViewById(R.id.txtFiltItemPrix);
        lltItemDomaine = view.findViewById(R.id.lltItemDomaine);
        separator = view.findViewById(R.id.separator);
        imgDetailClose = view.findViewById(R.id.imgDetailClose);
    }


    public void setFilterVin(Vin vin, Domaines domaines, final Activity activity, double km) {

        this.vin = vin;
        this.activity = activity;


        idVins = vin.getIdVins();


        StrAutreinfos = vin.getAutreInfos();


        StrCouleur = vin.getCouleur();

        StrNomVins = vin.getNomVin();

        prix = vin.getPrix();
        annee = vin.getMillesime();

        txtFiltItemPrix.setText("" + String.format(Locale.FRANCE, "%.1f", vin.getPrix()) + "€");

        for (int i = 0; i < domaines.size(); i++) {

            Domaine domaine = domaines.get(i);

            Log.e("vin.getIdDomaine():", "" + vin.getIdDomaine());
            Log.e("domaine.getIdDomaine():", "" + domaine.getIdDomaine());


            if (vin.getIdDomaine() == domaine.getIdDomaine()) {


                txtDomaineDistance.setText(domaine.getNomDomaine() + " à " + km + " km");
                latitude = domaine.getLatitude();
                longitude = domaine.getLongitude();

                StrHoraires = domaine.getHoraires();

                StrAdresse = domaine.getAdresse();


                StrTelephone = domaine.getTelephone();


                StrnomDomaine = domaine.getNomDomaine();

                idDomaine = domaine.getIdDomaine();

            }

        }


        txtFiltItemVin.setText(StrNomVins);





        imgDetailClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.finish();

            }
        });






        lltItemDomaine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;
                intent = new Intent(activity, InfosItemVinsActivity.class);
                intent.putExtra("nomvin", StrNomVins);
                intent.putExtra("prix", prix);
                intent.putExtra("distanceDomaine", txtDomaineDistance.getText());
                intent.putExtra("autreInfos", StrAutreinfos);
                intent.putExtra("telephone", StrTelephone);
                intent.putExtra("adresse", StrAdresse);
                intent.putExtra("horaire", StrHoraires);
                intent.putExtra("idVins", idVins);
                intent.putExtra("idDomaine", idDomaine);
                intent.putExtra("nomDomaine", StrnomDomaine);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("annee", annee);
                intent.putExtra("couleur", StrCouleur);


                Log.e("idVins:", "" + idVins);
                Log.e("idDomaine:", "" + idDomaine);

                if (activity != null) {
                    ListFilterVinsActivity listFilterVinsActivity = (ListFilterVinsActivity) activity;

                    listFilterVinsActivity.startActivity(intent);

                }
            }
        });


    }


}
