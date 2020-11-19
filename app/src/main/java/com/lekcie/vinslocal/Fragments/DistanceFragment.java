package com.lekcie.vinslocal.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lekcie.vinslocal.Activities.HomeActivity;
import com.lekcie.vinslocal.Activities.MapsRayonActivity;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.Constants;


public class DistanceFragment extends Fragment {

    Button btnMaps5km;
    Button btnMaps10km;
    Button btnMaps20km;


    ImageView imgRetour;
    Context context;
    FrameLayout framDistance;


    public DistanceFragment() {
        // Required empty public constructor
    }


    public static DistanceFragment newInstance(String param1, String param2) {
        DistanceFragment fragment = new DistanceFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_distance, container, false);

        imgRetour = view.findViewById(R.id.imgretour);

        framDistance = view.findViewById(R.id.framDistance);


        framDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants._FRAG_prix, null);

            }
        });


        imgRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants._FRAG_couleur, null);

            }
        });


        btnMaps5km = view.findViewById(R.id.btnMaps5km);
        btnMaps10km = view.findViewById(R.id.btnMaps10km);
        btnMaps20km = view.findViewById(R.id.btnMaps20km);

        btnMaps5km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//envoie la valeur à l'activité MapsRayonActivity
                Intent intent = null;
                intent = new Intent(context, MapsRayonActivity.class);
                intent.putExtra("rayon", 5);


                HomeActivity home = (HomeActivity) getActivity();


                home.getRecherche().setDistance(5);


                home.loadDomaineAndVin();


                startActivity(intent);


            }
        });

        btnMaps10km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;
                intent = new Intent(context, MapsRayonActivity.class);
                intent.putExtra("rayon", 10);
                startActivity(intent);
                HomeActivity home = (HomeActivity) getActivity();


                home.getRecherche().setDistance(10);


                home.loadDomaineAndVin();

            }
        });


        btnMaps20km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(context, MapsRayonActivity.class);
                intent.putExtra("rayon", 20);
                startActivity(intent);
                HomeActivity home = (HomeActivity) getActivity();
                home.getRecherche().setDistance(20);

                home.loadDomaineAndVin();
            }
        });


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


}
