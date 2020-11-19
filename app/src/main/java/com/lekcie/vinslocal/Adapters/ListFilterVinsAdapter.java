package com.lekcie.vinslocal.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lekcie.vinslocal.Holders.FilterVinsHolder;

import com.lekcie.vinslocal.Models.Domaines;
import com.lekcie.vinslocal.Models.Vin;

import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.Constants;

import java.util.ArrayList;

//class pour adapter la vue intérieur du patern  reçyclerview
public class ListFilterVinsAdapter extends RecyclerView.Adapter<FilterVinsHolder> {

    /* private Vins vins;*/

    private Activity activity;
    private ArrayList<Double> listekmtriees = new ArrayList<Double>();
    private ArrayList<Vin> listeVins = new ArrayList<Vin>();
    private Domaines domaines;


    public ListFilterVinsAdapter(ArrayList<Vin> listeVins, Domaines domaines, ArrayList<Double> listekmtriees, Activity activity) {
        this.listeVins = listeVins;
        this.domaines = domaines;
        this.listekmtriees = listekmtriees;
        this.activity = activity;


    }


    @Override
    public FilterVinsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_filter_vins, parent, false);
        return new FilterVinsHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterVinsHolder holder, int Domaineposition) {

        Vin vin = listeVins.get(Domaineposition);

        int Distancelistposition = 0;


        if (Domaineposition == 0) {
            holder.imgDetailClose.setVisibility(View.VISIBLE);

        } else {

            holder.imgDetailClose.setVisibility(View.INVISIBLE);


        }
        if(Domaineposition==listeVins.size()-1)
        {
            holder.separator.setVisibility(View.INVISIBLE);

        }
        else
        {
            holder.separator.setVisibility(View.VISIBLE);
        }

        for (Double km : listekmtriees) {


            if (Domaineposition == Distancelistposition) {
                holder.setFilterVin(vin, domaines, activity, km);

            }

            Distancelistposition++;


        }


    }

    @Override
    public int getItemCount() {


        return listeVins.size();
    }






}