package com.lekcie.vinslocal.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.lekcie.vinslocal.Activities.HomeActivity;
import com.lekcie.vinslocal.Activities.MainActivity;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.Constants;


public class CouleurFragment extends Fragment {


    Context context;
    Button btnCouleurRouge;
    Button btnCouleurBlanc;
    Button btnCouleurRosee;

    View view;
    TextView txtCouleur;

    public CouleurFragment() {
        // Required empty public constructor

    }


    public static CouleurFragment newInstance(String param1, String param2) {
        CouleurFragment fragment = new CouleurFragment();
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


        view = inflater.inflate(R.layout.fragment_couleur, container, false);

        btnCouleurBlanc = view.findViewById(R.id.btnCouleurBlanc);
        btnCouleurRouge = view.findViewById(R.id.btnCouleurRouge);
        btnCouleurRosee = view.findViewById(R.id.btnCouleurRosee);
        txtCouleur= view.findViewById(R.id.txtCouleur);

        btnCouleurRouge.setVisibility(View.INVISIBLE);
        btnCouleurBlanc.setVisibility(View.INVISIBLE);
        btnCouleurRosee.setVisibility(View.INVISIBLE);


        Animation animation= AnimationUtils.loadAnimation(context,R.anim.fadein);
        animation.setDuration(1500);
        txtCouleur.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btnCouleurRouge.setVisibility(View.VISIBLE);
                btnCouleurBlanc.setVisibility(View.VISIBLE);
                btnCouleurRosee.setVisibility(View.VISIBLE);
                animation= AnimationUtils.loadAnimation(context,R.anim.fadein);
                animation.setDuration(2000);
                btnCouleurRouge.startAnimation(animation);
                btnCouleurBlanc.startAnimation(animation);
                btnCouleurRosee.startAnimation(animation);
            }
        });




        btnCouleurBlanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants._FRAG_prix, null);


//permet de dire la couleur choisit et le passer dans les params du getdomaine dans homeactivity
                home.getRecherche().setCouleur("Blanc");


            }
        });

        btnCouleurRouge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants._FRAG_prix, null);

                home.getRecherche().setCouleur("Rouge");
            }
        });


        btnCouleurRosee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants._FRAG_prix, null);
                home.getRecherche().setCouleur("Rose");
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
