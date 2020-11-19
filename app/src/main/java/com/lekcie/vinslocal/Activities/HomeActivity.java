package com.lekcie.vinslocal.Activities;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;

import com.lekcie.vinslocal.Fragments.AperçuFragment;
import com.lekcie.vinslocal.Fragments.CouleurFragment;
import com.lekcie.vinslocal.Fragments.DistanceFragment;
import com.lekcie.vinslocal.Fragments.MapsRayonFragment;
import com.lekcie.vinslocal.Fragments.PrixFragment;
import com.lekcie.vinslocal.Models.Domaine;
import com.lekcie.vinslocal.Models.Domaines;
import com.lekcie.vinslocal.Models.Recherche;
import com.lekcie.vinslocal.Models.Vin;
import com.lekcie.vinslocal.Models.Vins;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.App;
import com.lekcie.vinslocal.Utils.AsyncCallWS;
import com.lekcie.vinslocal.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;


public class HomeActivity extends AppCompatActivity {

    Fragment fragHome;//frthome voir xml
    FragmentManager fragmentManager;//support du fragment
    Context context;
    Recherche recherche;
    App app;
    ImageView imgMaps;

    ImageView imgReglage;
    TextView txtVin;

    //MyPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //permet de rechercher le choix de user(couleur,prix)
        recherche = new Recherche();
        setContentView(R.layout.activity_home);
        context = this;


        //charge les erreurs
        App.loadErreur(context);



        imgMaps = findViewById(R.id.imgMaps);

        imgReglage = findViewById(R.id.imgReglage);
        imgMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;
                loadDomaineAndVin2();
                intent = new Intent(context, MapsRayonActivity.class);
                startActivity(intent);
            }
        });


        txtVin= findViewById(R.id.txtVin);



        //animation de fondue
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.fadein);


        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //lancer le fragment de départ
                fragmentManager = getSupportFragmentManager();
                changeFragment(Constants._FRAG_couleur, null);

            }
        });

        txtVin.startAnimation(animation);




    }


    public Recherche getRecherche() {
        return recherche;
    }


    // methode pour charger les domaines et vins (condition dans les php pour couleur et prix )
    public void loadDomaineAndVin() {
        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getDomainesAndVin.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadDomaineAndVin: " + result);
                            Gson gson = new Gson();

                            try {
                                //parser en json le modéle domaine créer à partir de son arraylist domaines
                                //app.setDomaines(gson.fromJson(result, Domaines.class));

                                app.setDomaines(new Domaines());


                                JSONArray jsonArr = new JSONArray(result);


                                //pour afficher ligne par ligne en json le model vin
                                for (int i = 0; i < jsonArr.length(); i++) {

                                    JSONObject jsonObj = jsonArr.getJSONObject(i);


                                    Log.e("jsonObj:", " " + jsonObj);

                                    Vin vin = new Vin();

                                    vin.setIdVins(Integer.parseInt((String) jsonObj.get("idVins")));

                                    vin.setNomVin((String) jsonObj.get("nomVin"));
                                    vin.setCouleur((String) jsonObj.get("couleur"));
                                    vin.setMillesime(Integer.parseInt((String) jsonObj.get("millesime")));
                                    vin.setPrix(Double.parseDouble((String) jsonObj.get("prix")));
                                    vin.setAutreInfos((String) jsonObj.get("autreInfos"));
                                    Log.e("vinssss:", "" + vin);


                                    int idDomaine = Integer.parseInt((String) jsonObj.get("idDomaine"));


                                    boolean isexist = false;

                                    for (int j = 0; j < app.getDomaines().size(); j++) {

                                        Domaine domaine = app.getDomaines().get(j);

                                        //ajoute dans arraylist vin du model domaine si celui ci existe
                                        if (domaine.getIdDomaine() == idDomaine) {
                                            domaine.getVins().add(vin);

                                            isexist = true;

                                            break;

                                        }

                                    }

                                    if (!isexist) {

                                        //ajoute le model domaine en format json
                                        Domaine domaine = new Domaine();

                                        domaine.setIdDomaine(Integer.parseInt((String) jsonObj.get("idDomaine")));
                                        domaine.setNomDomaine((String) jsonObj.get("nomDomaine"));

                                        domaine.setAdresse((String) jsonObj.get("adresse"));
                                        domaine.setHoraires((String) jsonObj.get("horaires"));
                                        domaine.setLatitude(Double.parseDouble((String) jsonObj.get("latitude")));
                                        domaine.setLongitude(Double.parseDouble((String) jsonObj.get("longitude")));
                                        domaine.setTelephone((String) jsonObj.get("telephone"));

                                        domaine.setVins(new Vins());
                                        domaine.getVins().add(vin);

                                        app.getDomaines().add(domaine);


                                        Log.e("domainessss:", "" + domaine);
                                    }


                                }

                                Log.e("[bon]", "app.getDomainesAndVin:" + app.getDomaines());


                                //erreurs
                            } catch (Exception e) {
                                Log.e("[ERROR]", e.toString());
                                Log.e("[ERROR]", "For result " + result);
                                Log.e("[ERROR]", "app.getDomaines:" + app.getDomaines());

                            }
                        } else {

                            Log.e("[ERROR]", "vide ");


                        }
                    }
                });


        asyncCallWS.addParam("couleur", "" + recherche.getCouleur());
        asyncCallWS.addParam("prix", "" + recherche.getPrix());
        asyncCallWS.execute();
    }

    @Override
    public void onBackPressed() {


        //la backstack(pile du fragment)
        super.onBackPressed();
        //récupére  l'ordre de la pile lancé
        int backStackCount = fragmentManager.getBackStackEntryCount();
        if (backStackCount == 0) {
            showDisconnectAppliDialog();
        }


    }

    //pour quitter avec un dialog
    private void showDisconnectAppliDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Quitter l'application ?");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "non", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                changeFragment(Constants._FRAG_couleur, null);
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                System.exit(0);

            }
        });
        alertDialog.show();
    }

    //pour switché sur les valeurs de fragments pour changer n'importe ou
    public void changeFragment(int code, Bundle params) {
        Fragment frag = null;
        switch (code) {
            case Constants._FRAG_couleur:
                frag = new CouleurFragment();
                break;
            case Constants._FRAG_detailVins:
                frag = new AperçuFragment();
                break;

            case Constants._FRAG_distance:
                frag = new DistanceFragment();
                break;
            case Constants._FRAG_MAPS_rayon:
                frag = new MapsRayonFragment();
                break;

            case Constants._FRAG_prix:
                frag = new PrixFragment();
                break;

            default:
                Log.e("[ERROR]", "changeFragment: code invalide " + code);
                break;
        }

        if (frag != null) {
            replaceFragment(frag);
        }

    }


    //pour changer de fragment par rapport au fragment de base

    public void replaceFragment(Fragment fragment) {

        fragHome = fragment;///to get current fragment object in FrameLayout R.id.frthome(framelayout)
        //récupére le numéro de fragment dans la pile
        int backStackCount = fragmentManager.getBackStackEntryCount();
        String tag = "Frag" + backStackCount;

//permet de commencer la transaction à un autre fragment avec animation
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setCustomAnimations(R.anim.enter_from_right,
                R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);

        transaction.addToBackStack(tag);
        //remplace le framelayout de base par le nouveau
        transaction.replace(R.id.frtHome, fragment, tag).commit();

        Log.e("tag: ", tag + " fragment: " + fragment);
    }


    // methode pour charger les domaines et vins
    public void loadDomaineAndVin2() {
        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getDomainesAndVin.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadDomaineAndVin: " + result);
                            Gson gson = new Gson();

                            try {
                                //parser en json le modéle domaine créer à partir de son arraylist domaines
                                //app.setDomaines(gson.fromJson(result, Domaines.class));

                                app.setDomaines(new Domaines());


                                JSONArray jsonArr = new JSONArray(result);


                                //pour afficher ligne par ligne en json le model vin
                                for (int i = 0; i < jsonArr.length(); i++) {

                                    JSONObject jsonObj = jsonArr.getJSONObject(i);


                                    Log.e("jsonObj:", " " + jsonObj);

                                    Vin vin = new Vin();

                                    vin.setIdVins(Integer.parseInt((String) jsonObj.get("idVins")));

                                    vin.setNomVin((String) jsonObj.get("nomVin"));
                                    vin.setCouleur((String) jsonObj.get("couleur"));
                                    vin.setMillesime(Integer.parseInt((String) jsonObj.get("millesime")));
                                    vin.setPrix(Double.parseDouble((String) jsonObj.get("prix")));
                                    vin.setAutreInfos((String) jsonObj.get("autreInfos"));
                                    Log.e("vinssss:", "" + vin);


                                    int idDomaine = Integer.parseInt((String) jsonObj.get("idDomaine"));


                                    boolean isexist = false;

                                    for (int j = 0; j < app.getDomaines().size(); j++) {

                                        Domaine domaine = app.getDomaines().get(j);

                                        //ajoute dans arraylist vin du model domaine si celui ci existe
                                        if (domaine.getIdDomaine() == idDomaine) {
                                            domaine.getVins().add(vin);

                                            isexist = true;

                                            break;

                                        }

                                    }

                                    if (!isexist) {

                                        //ajoute le model domaine en format json
                                        Domaine domaine = new Domaine();

                                        domaine.setIdDomaine(Integer.parseInt((String) jsonObj.get("idDomaine")));
                                        domaine.setNomDomaine((String) jsonObj.get("nomDomaine"));

                                        domaine.setAdresse((String) jsonObj.get("adresse"));
                                        domaine.setHoraires((String) jsonObj.get("horaires"));
                                        domaine.setLatitude(Double.parseDouble((String) jsonObj.get("latitude")));
                                        domaine.setLongitude(Double.parseDouble((String) jsonObj.get("longitude")));
                                        domaine.setTelephone((String) jsonObj.get("telephone"));

                                        domaine.setVins(new Vins());
                                        domaine.getVins().add(vin);

                                        app.getDomaines().add(domaine);


                                        Log.e("domainessss:", "" + domaine);
                                    }


                                }

                                Log.e("[bon]", "app.getDomainesAndVin:" + app.getDomaines());


                                //erreurs
                            } catch (Exception e) {
                                Log.e("[ERROR]", e.toString());
                                Log.e("[ERROR]", "For result " + result);
                                Log.e("[ERROR]", "app.getDomaines:" + app.getDomaines());

                            }
                        } else {

                            Log.e("[ERROR]", "vide ");


                        }
                    }
                });


        asyncCallWS.execute();
    }

}
