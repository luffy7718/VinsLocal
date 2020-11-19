package com.lekcie.vinslocal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lekcie.vinslocal.Holders.AperçuHolder;
import com.lekcie.vinslocal.Models.Vin;
import com.lekcie.vinslocal.Models.Vins;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.Constants;

public class AperçuAdapter extends RecyclerView.Adapter<AperçuHolder> {


    private Vins vins;
    private Context context;
    private Activity activity;
    private int idDomaine;

    public AperçuAdapter(Vins vins, Context context, Activity activity, int idDomaine) {

        this.context = context;
        this.vins = vins;
        this.activity = activity;
        this.idDomaine = idDomaine;

    }


    @NonNull
    @Override
    public AperçuHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_apercu, parent, false);

        return new AperçuHolder(view);
    }

    @Override
    public void onBindViewHolder(AperçuHolder aperçuHolder, int position) {

        Vin vin = vins.get(position);
        aperçuHolder.setAperçuVins(vin, context, activity, idDomaine);

    }

    @Override
    public int getItemCount() {
        return vins.size();
    }


}
