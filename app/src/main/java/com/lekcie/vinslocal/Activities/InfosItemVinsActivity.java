package com.lekcie.vinslocal.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lekcie.vinslocal.Adapters.PhotoDomainesAdapter;
import com.lekcie.vinslocal.Adapters.PhotoVinsAdapter;


import com.lekcie.vinslocal.Models.Caracterisques;
import com.lekcie.vinslocal.Models.Cepages;
import com.lekcie.vinslocal.Models.Domaine;
import com.lekcie.vinslocal.Models.Domaines;
import com.lekcie.vinslocal.Models.PhotosDomaines;
import com.lekcie.vinslocal.Models.PhotosVins;
import com.lekcie.vinslocal.Models.Vin;
import com.lekcie.vinslocal.Models.Vins;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.App;
import com.lekcie.vinslocal.Utils.AsyncCallWS;
import com.lekcie.vinslocal.Utils.Constants;


import java.util.ArrayList;
import java.util.Locale;


public class InfosItemVinsActivity extends AppCompatActivity {

    TextView txtnomvin;
    TextView txtprix;
    TextView txtdomaineDistance;
    String StrnomVin;
    Double prix = 0.0;
    String StrdistanceDomaine;
    String Strcaract;
    String Straccord = "accord du vin";
    String Strcouleur;
    String Strcepage;
    String StrautreInfos;
    String Strtelephone;
    String Stradresse;
    String Strhoraire;
    String StrancienCaract;
    String StrancienCepage;
    int annee;
    TextView txtInfoFiltItemCaract;
    TextView txtInfoFiltItemAccords;

    TextView txtInfoFiltItemAnnee;
    TextView txtInfoFiltItemCepage;
    TextView txtInfoFiltAutreinfos;
    TextView txtInfoFiltTelephone;
    TextView txtInfoFiltAdresse;
    TextView txtInfoFiltHoraires;
    TextView txtNomFiltItemDomaine;
    TextView txtTelephone;
    View itineraireIcon;
    View telephoneIcon;
    Context context;
    Button btnFiltCouleurRouge, btnFiltCouleurBlanc, btnFiltCouleurRosee;
    int idVins;
    TextView txtprix2;
    ArrayList<String> urls = new ArrayList<String>();
    ArrayList<String> urls2 = new ArrayList<String>();
    String nomDomaine;
    RecyclerView rcwPhotosVins;
    RecyclerView rcwPhotosDomaines;
    double latitude;
    double longitude;
    View adresseFiltItemIcon;
    View telephoneFiltItemIcon;
    int idDomaine;

    ImageView imgInfoClose;
    AsyncCallWS asyncCallWS;
    App app;
    Domaine dom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_vin_item);

        context = this;
        //on récupére les différents intents de MapsRayonfragment
        Intent intent = getIntent();
        StrnomVin = intent.getStringExtra("nomvin");
        prix = intent.getDoubleExtra("prix", prix);
        StrdistanceDomaine = intent.getStringExtra("distanceDomaine");
        Strcouleur = intent.getStringExtra("couleur");
        StrautreInfos = intent.getStringExtra("autreInfos");
        Strtelephone = intent.getStringExtra("telephone");
        Stradresse = intent.getStringExtra("adresse");
        Strhoraire = intent.getStringExtra("horaire");
        idVins = intent.getIntExtra("idVins", idVins);
        idDomaine = intent.getIntExtra("idDomaine", idDomaine);
        annee = intent.getIntExtra("annee", annee);
        nomDomaine = intent.getStringExtra("nomDomaine");
        latitude = intent.getDoubleExtra("latitude", latitude);
        longitude = intent.getDoubleExtra("longitude", longitude);


        Log.e("idVins:", "" + idVins);
        Log.e("idDomaine:", "" + idDomaine);

        loadCaractAndAllBDD();
        loadPhotosDomaines(idDomaine, this);

        txtnomvin = findViewById(R.id.txtnomvin);

        txtprix = findViewById(R.id.txtprix);

        txtdomaineDistance = findViewById(R.id.txtdomaineDistance);
        txtNomFiltItemDomaine = findViewById(R.id.txtNomFiltItemDomaine);
        txtInfoFiltItemCaract = findViewById(R.id.txtInfoFiltItemCaract);
        txtInfoFiltItemAccords = findViewById(R.id.txtInfoFiltItemAccords);
        txtInfoFiltItemAnnee = findViewById(R.id.txtInfoFiltItemAnnee);
        txtInfoFiltItemCepage = findViewById(R.id.txtInfoFiltItemCepage);
        txtInfoFiltAutreinfos = findViewById(R.id.txtInfoFiltAutreinfos);
        txtInfoFiltTelephone = findViewById(R.id.txtInfoFiltTelephone);
        txtInfoFiltAdresse = findViewById(R.id.txtInfoFiltAdresse);
        txtInfoFiltHoraires = findViewById(R.id.txtInfoFiltHoraires);
        btnFiltCouleurRouge = findViewById(R.id.btnFiltCouleurRouge);
        btnFiltCouleurBlanc = findViewById(R.id.btnFiltCouleurBlanc);
        btnFiltCouleurRosee = findViewById(R.id.btnFiltCouleurRosee);
        rcwPhotosVins = findViewById(R.id.rcwPhotosVins);
        rcwPhotosDomaines = findViewById(R.id.rcwPhotosDomaines);
        adresseFiltItemIcon = findViewById(R.id.adresseFiltItemIcon);
        telephoneFiltItemIcon = findViewById(R.id.telephoneFiltItemIcon);
        imgInfoClose = findViewById(R.id.imgInfoClose);

        txtTelephone = findViewById(R.id.txtTelephone);
        itineraireIcon = findViewById(R.id.itineraireIcon);
        telephoneIcon = findViewById(R.id.telephoneIcon);

        imgInfoClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });


        txtNomFiltItemDomaine.setText(nomDomaine);


        //pour afficher la bonne couleur
        if (Strcouleur.equals("Rouge")) {
            btnFiltCouleurRosee.setVisibility(View.INVISIBLE);
            btnFiltCouleurBlanc.setVisibility(View.INVISIBLE);
        } else if (Strcouleur.equals("Blanc")) {
            btnFiltCouleurRosee.setVisibility(View.INVISIBLE);
            btnFiltCouleurRouge.setVisibility(View.INVISIBLE);

        } else {
            btnFiltCouleurRouge.setVisibility(View.INVISIBLE);
            btnFiltCouleurBlanc.setVisibility(View.INVISIBLE);
        }


        //format pour remplacer le . par , à 1 chiffre après la virgule

        txtprix.setText("" + String.format(Locale.FRANCE, "%.1f", prix) + "€");
        txtnomvin.setText(StrnomVin);
        txtdomaineDistance.setText(StrdistanceDomaine);


        txtInfoFiltAutreinfos.setText(StrautreInfos);
        txtInfoFiltTelephone.setText(Strtelephone);
        txtInfoFiltAdresse.setText(Stradresse);
        txtInfoFiltHoraires.setText(Strhoraire);


        txtInfoFiltAdresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ouvrir maps avec coordonnées

                // uri :  est utilisé pour identifier une ressource
                //ex: d'une page de texte, d'un clip vidéo ou audio, d'une image fixe ou animée ou d'un programme
                //geo:latitude et longitude pour centrer les coords
                //?q= : pour ajouter à la barre de recherche de maps et ajouter le marker soit lat-longitude soit par le nom de rue
                Uri uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + Stradresse);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(it);

            }
        });

        adresseFiltItemIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ouvrir maps avec coordonnées

                // uri :  est utilisé pour identifier une ressource
                //ex: d'une page de texte, d'un clip vidéo ou audio, d'une image fixe ou animée ou d'un programme
                //geo:latitude et longitude pour centrer les coords
                //?q= : pour ajouter à la barre de recherche de maps et ajouter le marker soit lat-longitude soit par le nom de rue
                Uri uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + Stradresse);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(it);

            }
        });


        telephoneFiltItemIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Strtelephone));
                context.startActivity(appel);

            }
        });


        txtInfoFiltTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Strtelephone));
                context.startActivity(appel);
            }
        });


        txtTelephone.setText(Strtelephone);


        txtTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Strtelephone));
                context.startActivity(appel);

            }
        });

        telephoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Strtelephone));
                context.startActivity(appel);

            }
        });


        itineraireIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ouvrir maps avec coordonnées

                // uri :  est utilisé pour identifier une ressource
                //ex: d'une page de texte, d'un clip vidéo ou audio, d'une image fixe ou animée ou d'un programme
                //geo:latitude et longitude pour centrer les coords
                //?q= : pour ajouter à la barre de recherche de maps et ajouter le marker soit lat-longitude soit par le nom de rue
                Uri uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + Stradresse);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(it);

            }
        });
    }


    //attention important que loadvins soit en dernier (voir dans fonction la raison)
    public void loadCaractAndAllBDD() {


        loadCaract(idVins);
        loadPhotosVin(idVins);
        loadCepages(idVins);
        loadVins(idVins, this);


    }


    public void loadCaract(int idvin) {

        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getCaract.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadCaract: " + result);
                            Gson gson = new Gson();

                            try {

                                app.setCaracteristiques(gson.fromJson(result, Caracterisques.class));

                                Log.e("[bon]", "app.getCaracteristiques:" + app.getCaracteristiques());


                                //erreurs
                            } catch (Exception e) {
                                Log.e("[ERROR]", e.toString());
                                Log.e("[ERROR]", "For result " + result);
                                Log.e("[ERROR]", "app.getPhotosVins:" + app.getPhotosVins());

                            }
                        } else {

                            Log.e("[ERROR]", "vide ");


                        }
                    }
                });


        asyncCallWS.addParam("idvin", "" + idvin);
        Log.e("asyncCallWS:", "" + idvin);

        asyncCallWS.execute();
    }


    public void loadPhotosVin(int idvin) {

        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getPhotosVin.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadPhotosVin: " + result);
                            Gson gson = new Gson();

                            try {

                                app.setPhotosVins(gson.fromJson(result, PhotosVins.class));

                                Log.e("[bon]", "app.getPhotosVin:" + app.getPhotosVins());


                                //erreurs
                            } catch (Exception e) {
                                Log.e("[ERROR]", e.toString());
                                Log.e("[ERROR]", "For result " + result);
                                Log.e("[ERROR]", "app.getPhotosVins:" + app.getPhotosVins());

                            }
                        } else {

                            Log.e("[ERROR]", "vide ");


                        }
                    }
                });


        asyncCallWS.addParam("idvin", "" + idvin);
        Log.e("asyncCallWS:", "" + idvin);

        asyncCallWS.execute();
    }

    public void loadCepages(int idvin) {

        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getCepages.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadCepages: " + result);
                            Gson gson = new Gson();

                            try {

                                app.setCepages(gson.fromJson(result, Cepages.class));

                                Log.e("[bon]", "app.getCepages:" + app.getCepages());


                                //erreurs
                            } catch (Exception e) {
                                Log.e("[ERROR]", e.toString());
                                Log.e("[ERROR]", "For result " + result);
                                Log.e("[ERROR]", "app.getCepages:" + app.getCepages());

                            }
                        } else {

                            Log.e("[ERROR]", "vide ");


                        }
                    }
                });


        asyncCallWS.addParam("idvin", "" + idvin);
        Log.e("asyncCallWS:", "" + idvin);

        asyncCallWS.execute();
    }

    //ajoute les différents arraylist récupérés d'avant pour les mettres dans le model objet Vin
    public void loadVins(final int idvins, final Activity activity) {

        //appelle le service web
        asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getVins.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadVins: " + result);
                            Gson gson = new Gson();

                            try {

                                app.setVins(gson.fromJson(result, Vins.class));


                                Log.e("[bon]", "app.getVins:" + app.getVins());


                                for (Vin vin : App.getVins()) {


                                    Log.e("app.getCaracts:", "" + app.getCaracteristiques());
                                    vin.setCepages(app.getCepages());
                                    vin.setPhotosVins(app.getPhotosVins());
                                    vin.setCaracteristiques(app.getCaracteristiques());
                                    Log.e("vinsgetcaract:", "" + vin.getCaracteristiques());
                                    Log.e("app.getCaract:", "" + app.getCaracteristiques());
                                }


                                for (Vin vin : App.getVins()) {


                                    for (int i = 0; i < vin.getPhotosVins().size(); i++) {

                                        String urlVins = vin.getPhotosVins().get(i).getUrlVins();


                                        urls.add(urlVins);
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


                                    for (int i = 0; i < vin.getCaracteristiques().size(); i++) {
                                        String nouveauCaract;
                                        if (Strcaract != null) {
                                            nouveauCaract = vin.getCaracteristiques().get(i).getNomCaract();

                                            Strcaract += " , " + nouveauCaract;
                                        } else {
                                            Strcaract = vin.getCaracteristiques().get(0).getNomCaract();

                                        }

                                    }


//pour afficher plusieurs caract
                                    txtInfoFiltItemCaract.setText(Strcaract);
                                    if (StrancienCaract != null) {
                                        txtInfoFiltItemCaract.setText(StrancienCaract + ", " + Strcaract);
                                    } else {
                                        StrancienCaract = txtInfoFiltItemCaract.getText().toString();
                                        txtInfoFiltItemCaract.getText().toString();
                                    }


                                    txtInfoFiltItemAccords.setText(Straccord);
                                    txtInfoFiltItemAnnee.setText("" + annee);

                                    //pour afficher plusieurs cepages
                                    txtInfoFiltItemCepage.setText(Strcepage);


                                    if (StrancienCepage != null) {
                                        txtInfoFiltItemCepage.setText(StrancienCepage + ", " + Strcepage);
                                    } else {
                                        StrancienCepage = txtInfoFiltItemCepage.getText().toString();
                                        txtInfoFiltItemCepage.getText().toString();
                                    }

                                    // pour switcher de photosVins vers la droite


                                    Log.e("urls:", "" + urls);
                                    PhotoVinsAdapter photoVinsAdapter = new PhotoVinsAdapter(context, urls, activity);

//déclarer sa vue intérieur  global du reçyclerview et l'affecter ainsi que son adapter déclaré
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                                            LinearLayoutManager.HORIZONTAL, false);
                                    // pour  que les photos s'affichent horizontalement
                                    rcwPhotosVins.setLayoutManager(layoutManager);
                                    rcwPhotosVins.setAdapter(photoVinsAdapter);
                                }


                                final Vins vins = app.getVins();
                                Log.e("vinsfrag:", "" + vins);


                                //erreurs
                            } catch (Exception e) {
                                Log.e("[ERROR]", e.toString());
                                Log.e("[ERROR]", "For result " + result);
                                Log.e("[ERROR]", "app.getVins:" + app.getVins());

                            }
                        } else {

                            Log.e("[ERROR]", "vide ");


                        }
                    }
                });


        asyncCallWS.addParam("idvin", "" + idvins);
        Log.e("asyncCallWS:", "" + idvins);

        asyncCallWS.execute();
    }

    public void loadPhotosDomaines(int idDomaines, final Activity activity) {

        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getPhotosDomaines.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadPhotosDomaines: " + result);
                            Gson gson = new Gson();

                            try {

                                app.setPhotosDomaines(gson.fromJson(result, PhotosDomaines.class));

                                final Domaines domaines = App.getDomaines();

                                for (int i = 0; i < domaines.size(); i++) {

                                    domaines.get(i).setPhotoDomaines(app.getPhotosDomaines());
                                    dom = domaines.get(i);

                                }
                                for (int j = 0; j < App.getPhotosDomaines().size(); j++) {

                                    String urlDomaines = dom.getPhotoDomaines().get(j).getUrlDomaine();
                                    urls2.add(urlDomaines);

                                }

                                Log.e("[bon]", "app.getPhotosDomaines:" + app.getPhotosDomaines());


                                PhotoDomainesAdapter photoDomainesAdapter = new PhotoDomainesAdapter(context, urls2, activity);

                                //déclarer sa vue intérieur  global du reçyclerview et l'affecter ainsi que son adapter déclaré
                                RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(context,
                                        LinearLayoutManager.HORIZONTAL, false);
                                rcwPhotosDomaines.setLayoutManager(layoutManager2);
                                rcwPhotosDomaines.setAdapter(photoDomainesAdapter);


                                //erreurs
                            } catch (Exception e) {
                                Log.e("[ERROR]", e.toString());
                                Log.e("[ERROR]", "For result " + result);
                                Log.e("[ERROR]", "app.getPhotosDomaines:" + app.getPhotosDomaines());

                            }
                        } else {

                            Log.e("[ERROR]", "vide ");


                        }
                    }
                });


        asyncCallWS.addParam("idDomaine", "" + idDomaines);
        Log.e("asyncCallWS:", "" + idDomaines);

        asyncCallWS.execute();
    }

}
