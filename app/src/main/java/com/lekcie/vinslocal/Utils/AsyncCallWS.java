package com.lekcie.vinslocal.Utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AsyncCallWS extends AsyncTask<String, Integer, String> {


    //service en tache de fonds qui récupére et tourne derriere les activity
    String url;
    HashMap<String, String> params;
    OnCallBackAsyncTask onCallBackAsyncTask;
    ProgressDialog progress;

    public interface OnCallBackAsyncTask{
        void onResultCallBack(String result);
    }

    public AsyncCallWS(String url, final OnCallBackAsyncTask OnCallBack) {
        this.url = url;
        this.params = new HashMap<>();
        this.onCallBackAsyncTask = OnCallBack;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if(progress != null)
            progress.show();
    }

    public void addParam(String key, String value)
    {
        this.params.put(key,value);
    }

    @Override
    protected String doInBackground(String... strings) {

        String retour="";

        OkHttpClient client = new OkHttpClient();

        //Méthode POST
        FormBody.Builder formBuilder = new FormBody.Builder();

        for(HashMap.Entry<String, String> entry:params.entrySet())
        {
            formBuilder.add(entry.getKey(), entry.getValue());
        }

        RequestBody requestBody = formBuilder.build();

        Request request = new Request.Builder()
                //.header("Authorization", tokenEncode)
                .method("POST",requestBody )
                .url(this.url)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful())
            {
                retour = response.body().string();
            }

        } catch (Exception e) {
            Log.e("Tag",e.getMessage());
        }

        return retour;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(progress != null)
            progress.hide();

        onCallBackAsyncTask.onResultCallBack(s);
    }

}
