package com.lekcie.vinslocal.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lekcie.vinslocal.Activities.HomeActivity;
import com.lekcie.vinslocal.Activities.MainActivity;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.Constants;


public class PrixFragment extends Fragment {


    Context context;
    Button btnprix5;
    Button btnprix50;
    Button btnprix250;
    ImageView imgRetour;
    View framPrix;


    public PrixFragment() {
        // Required empty public constructor
    }


    public static PrixFragment newInstance(String param1, String param2) {
        PrixFragment fragment = new PrixFragment();
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

        final View view = inflater.inflate(R.layout.fragment_prix, container, false);
        imgRetour = view.findViewById(R.id.imgretour);
        framPrix = view.findViewById(R.id.framPrix);


        framPrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants._FRAG_distance, null);

            }
        });


        imgRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants._FRAG_couleur, null);
            }
        });


        btnprix5 = view.findViewById(R.id.btnprix5_50);
        btnprix50 = view.findViewById(R.id.btnprix50_250);
        btnprix250 = view.findViewById(R.id.btnprixplus250);

        btnprix5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants._FRAG_distance, null);
                //permet de dire le prix choisit et le passer dans les params du getdomaine dans homeactivity
                home.getRecherche().setPrix(5);

            }
        });

        btnprix50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants._FRAG_distance, null);
                home.getRecherche().setPrix(50);
            }
        });

        btnprix250.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants._FRAG_distance, null);
                home.getRecherche().setPrix(250);
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
