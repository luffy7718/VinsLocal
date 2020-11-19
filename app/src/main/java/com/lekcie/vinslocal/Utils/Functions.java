package com.lekcie.vinslocal.Utils;

import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

// espaces de stockages propres à chaque application Android.
// Avec un système de clé/valeur

public class Functions {


    public static void addPreferenceString(Context context, String key, String value) {
        addPreferenceString(context, key, value, true);
    }

    public static void addPreferenceString(Context context, String key, String value, boolean allowUpdate) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_PRIVATE);

        //Ouvre le fichier en mode édition
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //On enregistre les données dans le fichier
        if (!sharedPreferences.contains(key) || allowUpdate) {
            editor.putString(key, value);
        }
        //On referme le fichier
        editor.commit();
    }





    public static String getPreferenceString(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE,MODE_PRIVATE);

        String value = "";
        if(sharedPreferences.contains(key)){
            value = sharedPreferences.getString(key,"");
        }

        return value;
    }

    public static Bitmap loadFromInternalStorage(String path)
    {
        Bitmap bitmap=null;

        try {
            File f=new File(path);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (Exception e)
        {
            Log.e("Tag",e.getMessage());
        }
        return bitmap;
    }






}
