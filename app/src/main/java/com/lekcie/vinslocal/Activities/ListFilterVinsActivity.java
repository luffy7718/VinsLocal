package com.lekcie.vinslocal.Activities;


import android.content.Context;
import android.content.Intent;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.lekcie.vinslocal.Adapters.ListFilterVinsAdapter;


import com.lekcie.vinslocal.Models.Domaine;
import com.lekcie.vinslocal.Models.Domaines;


import com.lekcie.vinslocal.Models.Vin;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.App;


import java.util.ArrayList;


public class ListFilterVinsActivity extends AppCompatActivity {
    Context context;
    final Domaines domaines = App.getDomaines();
    Vin vin;

    ListFilterVinsAdapter listFilterVinsAdapter;
    //pattern de vue
    RecyclerView rvwDomainesList;


    private ArrayList<Double> listekmtriees = new ArrayList<Double>();
    private ArrayList<Vin> listeVins = new ArrayList<Vin>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_filter_vins);

//on récupére notre list créer depuis le mapsrayonfragment
        Intent intent = getIntent();

        listekmtriees = (ArrayList<Double>) intent.getSerializableExtra("listekmtriees");


        context = this;


        rvwDomainesList = findViewById(R.id.rvwDomainesList);


        for (int i = 0; i < domaines.size(); i++) {
            Domaine domaine = domaines.get(i);

            for (int j = 0; j < domaines.get(i).getVins().size(); j++) {

                if (domaines.get(i).getVins().size() == 1) {


                    vin = domaines.get(i).getVins().get(0);

                    domaines.get(i).getVins().get(0).setIdDomaine(domaine.getIdDomaine());

                    listeVins.add(vin);


                } else {


                    vin = domaines.get(i).getVins().get(j);
                    domaines.get(i).getVins().get(j).setIdDomaine(domaine.getIdDomaine());

                    listeVins.add(vin);

                }

            }


        }


//déclarer l'adapter qui va contenir notre liste
        listFilterVinsAdapter = new ListFilterVinsAdapter(listeVins, domaines, listekmtriees, this);
        //déclarer sa vue intérieur  global du reçyclerview et l'affecter ainsi que son adapter déclaré
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        rvwDomainesList.setLayoutManager(layoutManager);
        rvwDomainesList.setAdapter(listFilterVinsAdapter);



    }




}
