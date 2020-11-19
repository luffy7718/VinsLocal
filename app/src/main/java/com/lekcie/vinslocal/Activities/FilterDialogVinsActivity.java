package com.lekcie.vinslocal.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lekcie.vinslocal.Activities.MapsRayonActivity;
import com.lekcie.vinslocal.Models.Caracterisques;
import com.lekcie.vinslocal.Models.Caracteristique;
import com.lekcie.vinslocal.Models.Cepage;
import com.lekcie.vinslocal.Models.Cepages;
import com.lekcie.vinslocal.Models.Vin;
import com.lekcie.vinslocal.Models.Vins;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.App;
import com.lekcie.vinslocal.Utils.AsyncCallWS;
import com.lekcie.vinslocal.Utils.Constants;
import com.lekcie.vinslocal.Utils.OnRangeChangeListener;
import com.lekcie.vinslocal.Utils.RangeBar;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class FilterDialogVinsActivity extends Activity {


    Context context;
    ImageView imgDetailClose;


    LinearLayout.LayoutParams params;
    Button btnSelectAllCepages;
    Button btnDelCepages;
    LinearLayout llFilterCepages;
    List<CheckBox> checkBoxesCepages;

    Button btnSelectAllCaract;
    Button btnDelCaract;
    LinearLayout llFilterCaract;
    List<CheckBox> checkBoxesCaract;

    Button btnSelectAllCouleur;
    Button btnDelCouleur;
    LinearLayout llFilterCouleur;
    List<CheckBox> checkBoxesCouleur;

    List<RadioButton> listradioButtonAnnee;


    List<CheckBox> checkBoxesPrix;
    Button btnFilter;
    App app;
    Button btnDelFilter;
    JsonObject jsonObject;
    RadioGroup rgœnologues;
    RadioButton rboui;
    RadioButton rbnon;
    RadioButton rbPartir;
    RadioButton rbAnneeExact;
    RangeBar rangeBar;
    double prixMax;
    TextView txtpgrPrix;
    RadioGroup radioGroupAnnee;
    String Strrecommandation;
    String StrAnnee;
    int prixDepart;
    int prixfin;
    EditText edtAnnee;
    String StrnomCouleur;
    String StrnomCepages;
    String StrnomCaract;
    Vins vins_annee_exact;
    Vins vins_annee_partir;
    int idAnnee;
    Intent intent = null;


    public FilterDialogVinsActivity() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         context=this;

        loadAllCepages();
        loadAllCaract();
        loadAllAnnee();
        loadAllCouleur();
        loadAllPrix();


        setContentView(R.layout.dialog_filter_vins);


        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgDetailClose = findViewById(R.id.imgDetailClose);

        btnSelectAllCepages = findViewById(R.id.btnSelectAllCepages);
        btnDelCepages = findViewById(R.id.btnDelCepages);
        llFilterCepages = findViewById(R.id.llFilterCepages);
        btnDelFilter = findViewById(R.id.btnDelFilter);

        btnSelectAllCaract = findViewById(R.id.btnSelectAllCaract);
        btnDelCaract = findViewById(R.id.btnDelCaract);
        llFilterCaract = findViewById(R.id.llFilterCaract);

        txtpgrPrix = findViewById(R.id.txtpgrPrix);

        btnSelectAllCouleur = findViewById(R.id.btnSelectAllCouleur);
        llFilterCouleur = findViewById(R.id.llFilterCouleur);
        btnDelCouleur = findViewById(R.id.btnDelCouleur);
        rangeBar = findViewById(R.id.range_bar);
        edtAnnee = findViewById(R.id.edtAnnee);

        rgœnologues = findViewById(R.id.rgœnologues);
        rbnon = findViewById(R.id.rbnon);
        rboui = findViewById(R.id.rboui);
        rbPartir = findViewById(R.id.rbPartir);
        rbAnneeExact = findViewById(R.id.rbAnneeExact);

        rgœnologues.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.rbnon) {

                    Strrecommandation = rbnon.getText().toString();


                } else if (checkedId == R.id.rboui) {

                    Strrecommandation = rboui.getText().toString();
                }
            }

        });
        radioGroupAnnee = findViewById(R.id.radioGroupAnnee);


        btnDelFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clear(llFilterCepages);
                clear(llFilterCaract);
                clear(llFilterCouleur);
            }
        });

        checkBoxesCepages = new ArrayList<>();
        checkBoxesCaract = new ArrayList<>();
        checkBoxesCouleur = new ArrayList<>();
        checkBoxesPrix = new ArrayList<>();
        listradioButtonAnnee = new ArrayList<>();

        btnSelectAllCepages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAll(llFilterCepages);
            }
        });

        btnDelCepages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear(llFilterCepages);
            }
        });

        btnSelectAllCaract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAll(llFilterCaract);
            }
        });

        btnDelCaract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear(llFilterCaract);
            }
        });


        btnSelectAllCouleur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAll(llFilterCouleur);
            }
        });

        btnDelCouleur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear(llFilterCouleur);
            }
        });


        imgDetailClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        btnFilter = findViewById(R.id.btnFilter);


        radioGroupAnnee.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.rbAnneeExact) {

                    Log.e("CheckedId:", "" + checkedId);


                } else if (checkedId == R.id.rbPartir) {

                    Log.e("CheckedId:", "" + checkedId);
                }


                idAnnee = checkedId;

                Log.e("idAnnee:", "" + idAnnee);

            }


        });

        idAnnee = R.id.rbAnneeExact;


        Strrecommandation = rbnon.getText().toString();
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonObject = new JsonObject();
                CepageFilter();
                CaractFilter();
                CouleurFilter();
                StrAnnee = edtAnnee.getText().toString();


                Log.e(Constants._TAG_LOG, "JSON: " + jsonObject + "recommandation: " + Strrecommandation
                        + ", Annee: " + StrAnnee
                        + ", prixDepart:" + prixDepart + " , prixfin:" + prixfin);


                loadFiltrerVins();

                Intent intent = null;
                intent = new Intent(context, MapsRayonActivity.class);
                startActivity(intent);


            }
        });


    }

    private void CepageFilter() {

        boolean isValid = false;
        JsonArray jsonArray = new JsonArray();
        for (CheckBox cb : checkBoxesCepages) {
            if (cb.isChecked()) {
                isValid = true;
                int val = (int) cb.getTag();
                StrnomCepages = cb.getText().toString();
                jsonArray.add(StrnomCepages);
            }
        }
        if (isValid) {

            jsonObject.add("Cepage", jsonArray);
        }
        Log.e(Constants._TAG_LOG, "CepageFILTER: " + jsonArray.toString());

    }


    private void CouleurFilter() {

        boolean isValid = false;
        JsonArray jsonArray = new JsonArray();
        for (CheckBox cb : checkBoxesCouleur) {
            if (cb.isChecked()) {


                isValid = true;
                int val = (int) cb.getTag();
                StrnomCouleur = cb.getText().toString();
                jsonArray.add(StrnomCouleur);
            }
        }
        if (isValid) {

            jsonObject.add("Couleur", jsonArray);
        }
        Log.e(Constants._TAG_LOG, "CouleurFILTER: " + jsonArray.toString());

    }


    private void CaractFilter() {

        boolean isValid = false;
        JsonArray jsonArray = new JsonArray();
        for (CheckBox cb : checkBoxesCaract) {
            if (cb.isChecked()) {
                isValid = true;
                int val = (int) cb.getTag();
                StrnomCaract = cb.getText().toString();
                jsonArray.add(StrnomCaract);
            }
        }
        if (isValid) {

            jsonObject.add("Caracteristique", jsonArray);
        }
        Log.e(Constants._TAG_LOG, "CaractFILTER: " + jsonArray.toString());

    }


    private void clear(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setChecked(false);
            }
        }
    }


    private void selectAll(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setChecked(true);
            }
        }
    }


    private LinearLayout.LayoutParams paramsCb() {
        params.bottomMargin = 25;
        return params;
    }


    public void loadAllCepages() {

        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getAllCepages.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadAllCepages: " + result);
                            Gson gson = new Gson();

                            try {

                                app.setCepages(gson.fromJson(result, Cepages.class));

                                Log.e("[bon]", "app.getCepages:" + app.getCepages());


                                for (Cepage cepage : App.getCepages()) {

                                    CheckBox cb = new CheckBox(context);
                                    cb.setTag(cepage.getIdCepage());
                                    cb.setText(cepage.getNomCepage());
                                    cb.setTextColor(getResources().getColor(R.color.colorTxtLight));
                                    params.bottomMargin = 25;

                                    llFilterCepages.addView(cb, paramsCb());
                                    checkBoxesCepages.add(cb);
                                }


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


        asyncCallWS.execute();
    }


    public void loadAllCaract() {

        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getAllCaract.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadAllCaract: " + result);
                            Gson gson = new Gson();

                            try {

                                app.setCaracteristiques(gson.fromJson(result, Caracterisques.class));

                                Log.e("[bon]", "app.getCaracteristiques:" + app.getCaracteristiques());


                                for (Caracteristique caracteristique : App.getCaracteristiques()) {

                                    CheckBox cb = new CheckBox(context);
                                    cb.setTag(caracteristique.getIdCaract());
                                    cb.setText(caracteristique.getNomCaract());
                                    cb.setTextColor(getResources().getColor(R.color.colorTxtLight));
                                    params.bottomMargin = 25;

                                    llFilterCaract.addView(cb, paramsCb());
                                    checkBoxesCaract.add(cb);
                                }


                                //erreurs
                            } catch (Exception e) {
                                Log.e("[ERROR]", e.toString());
                                Log.e("[ERROR]", "For result " + result);
                                Log.e("[ERROR]", "app.getCaracteristiques:" + app.getCepages());

                            }
                        } else {

                            Log.e("[ERROR]", "vide ");


                        }
                    }
                });


        asyncCallWS.execute();
    }


    public void loadAllCouleur() {

        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getAllCouleur.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadAllCouleur: " + result);
                            Gson gson = new Gson();

                            try {

                                app.setVins(gson.fromJson(result, Vins.class));

                                Log.e("[bon]", "app.getvins:" + app.getVins());


                                for (Vin vin : App.getVins()) {

                                    CheckBox cb = new CheckBox(context);


                                    cb.setTag(vin.getIdVins());
                                    cb.setText(vin.getCouleur());
                                    cb.setTextColor(getResources().getColor(R.color.colorTxtLight));
                                    params.bottomMargin = 25;

                                    llFilterCouleur.addView(cb, paramsCb());
                                    checkBoxesCouleur.add(cb);
                                }


                                //erreurs
                            } catch (Exception e) {
                                Log.e("[ERROR]", e.toString());
                                Log.e("[ERROR]", "For result " + result);
                                Log.e("[ERROR]", "app.getvins:" + app.getCepages());

                            }
                        } else {

                            Log.e("[ERROR]", "vide ");


                        }
                    }
                });


        asyncCallWS.execute();
    }

    public void loadAllAnnee() {

        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getAllAnnee.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadAllAnnee: " + result);
                            Gson gson = new Gson();

                            try {

                                app.setVins(gson.fromJson(result, Vins.class));

                                Log.e("[bon]", "app.getvins:" + app.getVins());


                                //erreurs
                            } catch (Exception e) {
                                Log.e("[ERROR]", e.toString());
                                Log.e("[ERROR]", "For result " + result);
                                Log.e("[ERROR]", "app.getvins:" + app.getVins());

                            }
                        } else {

                            Log.e("[ERROR]", "vide ");


                        }
                    }
                });


        asyncCallWS.execute();
    }


    public void loadAllPrix() {

        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getAllPrix.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {
                            Log.e(Constants._TAG_LOG, "loadAllPrix: " + result);
                            Gson gson = new Gson();

                            try {

                                app.setVins(gson.fromJson(result, Vins.class));

                                Log.e("[bon]", "app.getVins:" + app.getVins());


                                for (Vin vin : App.getVins()) {


                                    prixMax = vin.getPrix();

                                }
                                Math.max(prixMax, prixMax);

                                rangeBar.setMax((int) prixMax);


                                rangeBar.setOnRangeChangeListener(new OnRangeChangeListener() {
                                    @Override
                                    public void onRangeChanged(RangeBar bar, int startIndex, int endIndex, boolean fromUser) {


                                        prixDepart = startIndex;
                                        prixfin = endIndex;

                                        txtpgrPrix.setText(String.valueOf(" Entre " + (prixDepart) + "€" + " et " + (prixfin)) + " €");

                                        Log.e(TAG, "start = " + startIndex + ", end = " + endIndex + ", from user = " + fromUser);
                                    }

                                    @Override
                                    public void onStartTrackingTouch(RangeBar bar, int startIndex, int endIndex) {
                                        Log.e(TAG, "on start tracking touch");

                                    }

                                    @Override
                                    public void onStopTrackingTouch(RangeBar bar, int startIndex, int endIndex) {
                                        Log.e(TAG, "on end tracking touch");
                                    }
                                });


                                //erreurs
                            } catch (Exception e) {
                                Log.e("[ERROR]", e.toString());
                                Log.e("[ERROR]", "For result " + result);
                                Log.e("[ERROR]", "app.getprix:" + app.getVins());

                            }
                        } else {

                            Log.e("[ERROR]", "vide ");


                        }
                    }
                });


        asyncCallWS.execute();
    }


    public void loadFiltrerVins() {

        //appelle le service web
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getVinsByFilter.php",
                new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        //si resultat est bon à retourner
                        if (!result.isEmpty()) {


                            // Log.e(Constants._TAG_LOG, "loadFiltrer: " + result);
                            Gson gson = new Gson();
                            JsonObject json = gson.fromJson(result, JsonObject.class);


                            if (json.has("vins_annee_exact")) {
                                //Log.e(Constants._TAG_LOG, "vins_annee_exact: " + json.get("vins_annee_exact"));
                                try {


                                    if (idAnnee == R.id.rbAnneeExact) {

                                        vins_annee_exact = (gson.fromJson(json.get("vins_annee_exact"), Vins.class));

                                        Log.e("[bon]", "vins_annee_exact:" + vins_annee_exact);

                                        App.setVins(vins_annee_exact);

                                        Log.e("[bon]", "/////////////////////////////////////////////////////");
                                    }

                                    //erreurs
                                } catch (Exception e) {
                                    Log.e("[ERROR]", e.toString());
                                    Log.e("[ERROR]", "For result " + result);
                                    Log.e("[ERROR]", "vins_annee_exact:" + vins_annee_exact);

                                }

                            }
                            if (json.has("vins_annee_partir")) {
                                // Log.e(Constants._TAG_LOG, "vins_annee_partir: " + json.get("vins_annee_partir"));
                                try {


                                    if (idAnnee == R.id.rbPartir) {
                                        vins_annee_partir = (gson.fromJson(json.get("vins_annee_partir"), Vins.class));

                                        Log.e("[bon]", "vins_annee_partir:" + vins_annee_partir);

                                        App.setVins(vins_annee_partir);
                                        Log.e("[bon]", "/////////////////////////////////////////////////////");

                                    }


                                } catch (Exception e) {
                                    Log.e("[ERROR]", e.toString());
                                    Log.e("[ERROR]", "For result " + result);
                                    Log.e("[ERROR]", "vins_annee_partir:" + vins_annee_partir);
                                }


                            }
                        }

                    }
                });


        asyncCallWS.addParam("couleur", "" + StrnomCouleur);
        asyncCallWS.addParam("min", "" + prixDepart);
        asyncCallWS.addParam("max", "" + prixfin);
        asyncCallWS.addParam("millesime", "" + StrAnnee);


        asyncCallWS.execute();

    }

}
