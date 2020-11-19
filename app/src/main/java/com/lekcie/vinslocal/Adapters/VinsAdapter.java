package com.lekcie.vinslocal.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lekcie.vinslocal.Holders.VinHolders;
import com.lekcie.vinslocal.Models.Domaine;
import com.lekcie.vinslocal.Models.Vin;
import com.lekcie.vinslocal.Models.Vins;
import com.lekcie.vinslocal.R;

import java.util.ArrayList;

public class VinsAdapter extends RecyclerView.Adapter<VinHolders> {

    private Vins vins;
    private Context context;
    private Domaine domaine;
    private double km;
    public View frtVins;


    public VinsAdapter(Vins vins, Context context, Domaine domaine, double km, View frtVins) {

        this.context = context;
        this.vins = vins;
        this.domaine = domaine;
        this.km = km;
        this.frtVins = frtVins;

    }


    @NonNull
    @Override
    public VinHolders onCreateViewHolder(ViewGroup parent, int position) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vins, parent, false);

        return new VinHolders(view);
    }

    @Override
    public void onBindViewHolder(VinHolders vinHolders, int position) {
        Vin vin = vins.get(position);


        vinHolders.setVin(vin, context, domaine, km);




    }


    @Override
    public int getItemCount() {
        return vins.size();
    }





}
