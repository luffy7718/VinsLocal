package com.lekcie.vinslocal.Utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.google.gson.Gson;
import com.lekcie.vinslocal.Models.Caracterisques;
import com.lekcie.vinslocal.Models.Cepages;
import com.lekcie.vinslocal.Models.Domaines;
import com.lekcie.vinslocal.Models.Erreur;
import com.lekcie.vinslocal.Models.Erreurs;
import com.lekcie.vinslocal.Models.PhotosDomaines;
import com.lekcie.vinslocal.Models.PhotosVins;
import com.lekcie.vinslocal.Models.Vins;


public class App extends Application {

    //arraylist
    public static Erreurs erreurs;
  public static Domaines domaines;
  public static Cepages cepages;
  public static PhotosVins photosVins;
  public  static Vins vins;
  public static Caracterisques caracteristiques;
  public static PhotosDomaines photosDomaines;



    public static PhotosDomaines getPhotosDomaines() {
        return photosDomaines;
    }

    public static void setPhotosDomaines(PhotosDomaines photosDomaines) {
        App.photosDomaines = photosDomaines;
    }

    public static Caracterisques getCaracteristiques() {
        return caracteristiques;
    }

    public static void setCaracteristiques(Caracterisques caracteristiques) {
        App.caracteristiques = caracteristiques;
    }

    public static Vins getVins() {
        return vins;
    }

    public static void setVins(Vins vins) {
        App.vins = vins;
    }

    public static Cepages getCepages() {
        return cepages;
    }

    public static void setCepages(Cepages cepages) {
        App.cepages = cepages;
    }

    public static PhotosVins getPhotosVins() {
        return photosVins;
    }

    public static void setPhotosVins(PhotosVins photosVins) {
        App.photosVins = photosVins;
    }



    public static Domaines getDomaines() {
        return domaines;
    }

    public static void setDomaines(Domaines domaines) {
        App.domaines = domaines;
    }

    public static Erreurs getErreurs() {
        return erreurs;
    }

    public static void setErreurs(Erreurs erreurs) {
        App.erreurs = erreurs;
    }



    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static void addErreur(Erreur erreur, Context context) {

        if(App.erreurs == null)
        {
            App.erreurs = new Erreurs();
        }
        App.erreurs.add(erreur);
        saveErreur(context);
    }

    public static void saveErreur(Context context)
    {
        Gson gson = new Gson();
        String erreurs = gson.toJson(App.erreurs);

        Functions.addPreferenceString(context,"Erreurs",erreurs);
    }

    public static void loadErreur(Context context)
    {
        String erreurs = Functions.getPreferenceString(context,"Erreurs");

        if(!erreurs.isEmpty())
        {
            Gson gson = new Gson();
            App.erreurs.addAll(gson.fromJson(erreurs,Erreurs.class));
        }
    }

    public static void deleteErreur(Context context)
    {
        App.erreurs = new Erreurs();
        Functions.addPreferenceString(context,"Erreurs","");
    }

}