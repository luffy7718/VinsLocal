package com.lekcie.vinslocal.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lekcie.vinslocal.Adapters.AperçuAdapter;

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


public class AperçuFragment extends Fragment {


    private Context context;
    AperçuAdapter aperçuAdapter;
    private RecyclerView rvwDetailVinList;
    App app;
    private int idVin;
    AsyncCallWS asyncCallWS;
    private int idDomaine;
    final Domaines domaines = App.getDomaines();
    Domaine domaine;
    int nombreVins;


    public AperçuFragment() {
        // Required empty public constructor
    }


    public static AperçuFragment newInstance(Bundle args) {
        AperçuFragment fragment = new AperçuFragment();


        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey("idDomaine") && getArguments().containsKey("nombreVins")) {

                idDomaine = getArguments().getInt("idDomaine");
                nombreVins = getArguments().getInt("nombreVins");


                Log.e("nombrevinssssss:", "" + nombreVins);

                if (nombreVins == 1) {

                    for (int i = 0; i < domaines.size(); i++) {
                        if (domaines.get(i).getIdDomaine() == idDomaine) {

                            idVin = domaines.get(i).getVins().get(0).getIdVins();

                            break;

                        }

                    }


                }

            }
        }

        Log.e("aperçufrag:", "" + idVin);

        Log.e("idDomaineAperçu:", "" + idDomaine);


        loadCaractAndAllBDD(idVin);

        loadPhotosDomaines(idDomaine);

// charge tout les requetes de récupération de la BDD


        Log.e("aperçufrag:", "j'ai créer le fragment ici");


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_apercu, container, false);


        rvwDetailVinList = view.findViewById(R.id.rvwDetailVinList);


        return view;
    }


    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.context = activity.getBaseContext();


        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void loadPhotosDomaines(int idDomaines) {

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

                                }


                                Log.e("[bon]", "app.getPhotosDomaines:" + app.getPhotosDomaines());


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


    //attention important que loadvins soit en dernier (voir dans fonction la raison)
    public void loadCaractAndAllBDD(final int idvin) {

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

                                loadPhotosVin(idVin);
                                loadCepages(idVin);
                                loadVins(idVin);


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
    public void loadVins(int idvins) {

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

                                    vin.setCaracteristiques(app.getCaracteristiques());
                                    vin.setCepages(app.getCepages());
                                    vin.setPhotosVins(app.getPhotosVins());

                                    Log.e("vinsgetcaract:", "" + vin.getCaracteristiques());


                                }


                                final Vins vins = app.getVins();
                                Log.e("vinsfrag:", "" + vins);

//déclarer l'adapter qui va contenir notre liste
                                aperçuAdapter = new AperçuAdapter(vins, context, getActivity(), idDomaine);
//déclarer sa vue intérieur  global du reçyclerview et l'affecter ainsi que son adapter déclaré
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                                        LinearLayoutManager.VERTICAL, false);
                                rvwDetailVinList.setLayoutManager(layoutManager);
                                rvwDetailVinList.setAdapter(aperçuAdapter);


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
}