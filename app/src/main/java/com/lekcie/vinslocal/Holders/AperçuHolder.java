package com.lekcie.vinslocal.Holders;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.lekcie.vinslocal.Adapters.PhotoDomainesAdapter;
import com.lekcie.vinslocal.Adapters.PhotoVinsAdapter;

import com.lekcie.vinslocal.Models.Cepages;
import com.lekcie.vinslocal.Models.Domaine;

import com.lekcie.vinslocal.Models.Domaines;
import com.lekcie.vinslocal.Models.PhotosDomaines;
import com.lekcie.vinslocal.Models.PhotosVins;
import com.lekcie.vinslocal.Models.Vin;
import com.lekcie.vinslocal.Models.Vins;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.App;

import java.util.ArrayList;
import java.util.Locale;


public class AperçuHolder extends RecyclerView.ViewHolder {


    private final TextView txtTelephone;
    private final TextView txtAdresse;
    private final TextView txtHoraires;
    private final TextView txtItemAnnee;
    private final TextView txtAutreinfos;
    private final TextView txtItemCaract;
    private final TextView txtItemCepage;
    private final TextView txtNomDomaine;

    private String StrancienCaract;
    private String Strcaract;
    private String StrancienCepage;
    private String Strcepage;
    private RecyclerView rcwPhotosVins;
    private RecyclerView rcwPhotosDomaines;
    private Button btnCouleurRouge;
    private Button btnCouleurBlanc;
    private Button btnCouleurRosee;
    private String StrnomDomaine;
    private View adresseIcon;
    private View telephoneIcon;


    public AperçuHolder(View view) {
        super(view);

        txtItemAnnee = view.findViewById(R.id.txtItemAnnee);
        txtAdresse = view.findViewById(R.id.txtAdresse);
        txtHoraires = view.findViewById(R.id.txtHoraires);

        txtTelephone = view.findViewById(R.id.txtTelephone);

        txtAutreinfos = view.findViewById(R.id.txtAutreinfos);
        txtItemCaract = view.findViewById(R.id.txtItemCaract);
        txtItemCepage = view.findViewById((R.id.txtItemCepage));

        rcwPhotosDomaines = view.findViewById(R.id.rcwPhotosDomaines);
        rcwPhotosVins = view.findViewById(R.id.rcwPhotosVins);
        btnCouleurRouge = view.findViewById(R.id.btnCouleurRouge);
        btnCouleurBlanc = view.findViewById(R.id.btnCouleurBlanc);
        btnCouleurRosee = view.findViewById(R.id.btnCouleurRosee);

        txtNomDomaine = view.findViewById(R.id.txtNomDomaine);
        adresseIcon = view.findViewById(R.id.adresseIcon);
        telephoneIcon = view.findViewById(R.id.telephoneIcon);
    }

    public void setAperçuVins(final Vin vin, final Context context, Activity activity, int idDomaine) {


        // pour switcher de photosVins vers la droite


        ArrayList<String> urls = new ArrayList<String>();
        ArrayList<String> urls2 = new ArrayList<String>();
        for (int i = 0; i < vin.getPhotosVins().size(); i++) {

            String urlVins = vin.getPhotosVins().get(i).getUrlVins();


            urls.add(urlVins);


            PhotoVinsAdapter photoVinsAdapter = new PhotoVinsAdapter(context, urls, activity);

//déclarer sa vue intérieur  global du reçyclerview et l'affecter ainsi que son adapter déclaré
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false);
            rcwPhotosVins.setLayoutManager(layoutManager);
            rcwPhotosVins.setAdapter(photoVinsAdapter);

        }


        final Vins vins = App.getVins();
        Log.e("vinsHolder:", "" + vins);

        final Cepages cepages = App.getCepages();
        Log.e("cepagesHolder:", "" + cepages);
        final PhotosVins photosVins = App.getPhotosVins();
        Log.e("photosVinsHolder:", "" + photosVins);
        final PhotosDomaines photosDomaines = App.getPhotosDomaines();
        Log.e("photosDomainesHolder:", "" + photosDomaines);


        if (vin.getCouleur().equals("Rouge")) {
            btnCouleurRosee.setVisibility(View.INVISIBLE);
            btnCouleurBlanc.setVisibility(View.INVISIBLE);
        } else if (vin.getCouleur().equals("Blanc")) {
            btnCouleurRosee.setVisibility(View.INVISIBLE);
            btnCouleurRouge.setVisibility(View.INVISIBLE);

        } else {
            btnCouleurRouge.setVisibility(View.INVISIBLE);
            btnCouleurBlanc.setVisibility(View.INVISIBLE);
        }


        txtItemAnnee.setText(" " + vin.getMillesime());


        txtAutreinfos.setText(vin.getAutreInfos());

        for (int i = 0; i < vin.getCaracteristiques().size(); i++) {
            String nouveauCaract;
            if (Strcaract != null) {
                nouveauCaract = vin.getCaracteristiques().get(i).getNomCaract();

                Strcaract += " , " + nouveauCaract;
            } else {
                Strcaract = vin.getCaracteristiques().get(0).getNomCaract();

            }

        }

        txtItemCaract.setText(Strcaract);
        if (StrancienCaract != null) {
            txtItemCaract.setText(StrancienCaract + ", " + Strcaract);
        } else {
            StrancienCaract = txtItemCaract.getText().toString();
            txtItemCaract.getText().toString();
        }

        for (int i = 0; i < vin.getCepages().size(); i++) {

            String nouveauCepage;
            if (Strcepage != null) {
                nouveauCepage = vin.getCepages().get(i).getNomCepage();

                Strcepage += " , " + nouveauCepage;
            } else {
                Strcepage = vin.getCepages().get(0).getNomCepage();

            }
        }
        //pour afficher plusieurs cepages
        txtItemCepage.setText(Strcepage);


        if (StrancienCepage != null) {
            txtItemCepage.setText(StrancienCepage + ", " + Strcepage);
        } else {
            StrancienCepage = txtItemCepage.getText().toString();
            txtItemCepage.getText().toString();
        }


        final Domaines domaines = App.getDomaines();
        for (int i = 0; i < domaines.size(); i++) {
            if (domaines.get(i).getIdDomaine() == idDomaine) {

                final Domaine domaine = domaines.get(i);

                txtNomDomaine.setText(domaine.getNomDomaine());

                txtHoraires.setText(domaine.getHoraires());

                txtAdresse.setText(domaine.getAdresse());
                txtTelephone.setText(domaine.getTelephone());
                txtAdresse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //ouvrir maps avec coordonnées

//                         uri :  est utilisé pour identifier une ressource
//                        ex: d'une page de texte, d'un clip vidéo ou audio, d'une image fixe ou animée ou d'un programme
//                        geo:latitude et longitude pour centrer les coords
//                        ?q= : pour ajouter à la barre de recherche de maps et ajouter le marker soit lat-longitude soit par le nom de rue
                        Uri uri = Uri.parse("geo:" + domaine.getLatitude() + "," + domaine.getLongitude() + "?q=" + domaine.getAdresse());
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(it);

                    }
                });

                adresseIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //ouvrir maps avec coordonnées

                        // uri :  est utilisé pour identifier une ressource
                        //ex: d'une page de texte, d'un clip vidéo ou audio, d'une image fixe ou animée ou d'un programme
                        //geo:latitude et longitude pour centrer les coords
                        //?q= : pour ajouter à la barre de recherche de maps et ajouter le marker soit lat-longitude soit par le nom de rue
                        Uri uri = Uri.parse("geo:" + domaine.getLatitude() + "," + domaine.getLongitude() + "?q=" + domaine.getAdresse());
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(it);

                    }
                });


                telephoneIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent appel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + domaine.getTelephone()));
                        context.startActivity(appel);

                    }
                });


                txtTelephone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent appel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + domaine.getTelephone()));
                        context.startActivity(appel);
                    }
                });

                for (int j = 0; j < App.getPhotosDomaines().size(); j++) {
                    String urlDomaines = domaine.getPhotoDomaines().get(j).getUrlDomaine();
                    urls2.add(urlDomaines);


                    PhotoDomainesAdapter photoDomainesAdapter = new PhotoDomainesAdapter(context, urls2, activity);

                    //déclarer sa vue intérieur  global du reçyclerview et l'affecter ainsi que son adapter déclaré
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                            LinearLayoutManager.HORIZONTAL, false);
                    rcwPhotosDomaines.setLayoutManager(layoutManager);
                    rcwPhotosDomaines.setAdapter(photoDomainesAdapter);
                }

                break;

            }


        }


    }


}
