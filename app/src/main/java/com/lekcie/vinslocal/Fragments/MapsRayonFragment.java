package com.lekcie.vinslocal.Fragments;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;

import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;


import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.lekcie.vinslocal.Activities.FilterDialogVinsActivity;
import com.lekcie.vinslocal.Activities.HomeActivity;
import com.lekcie.vinslocal.Activities.ListFilterVinsActivity;

import com.lekcie.vinslocal.Activities.MapsRayonActivity;

import com.lekcie.vinslocal.Adapters.VinsAdapter;
import com.lekcie.vinslocal.Models.Domaine;
import com.lekcie.vinslocal.Models.Domaines;
import com.lekcie.vinslocal.Models.Vin;
import com.lekcie.vinslocal.Models.Vins;
import com.lekcie.vinslocal.R;
import com.lekcie.vinslocal.Utils.App;
import com.lekcie.vinslocal.Utils.Constants;
import com.lekcie.vinslocal.Utils.ScaleBar;


import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.Collections;

import java.util.Locale;


import static android.content.Context.LOCATION_SERVICE;
import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN;


public class MapsRayonFragment extends Fragment implements OnMapReadyCallback, LocationListener {


    private GoogleMap mMap;
    private Context context;
    private MapView mapView;
    private static Marker marker;
    private Location location;
    LatLng mycoords;
    private double latitude = 48.866667;
    private double longitude = 2.333333;
    Circle circle;
    double latitudeDomaines = 0;
    double longitudeDomaines = 0;
    LatLng mycoordsDomaines;
    ImageView imgRetour;
    private String StrdomaineDistance = "";
    private double distancekm;
    private Button btnListe;
    ImageView imgDetailClose;

    private BottomSheetBehavior bottomSheetBehavior;
    private View viewbottomSheet;
    private View viewShadow;

    private View lltBody;

    private Boolean isup = false;
    private String StrnomVins = "";
    private Double prix;
    private TextView txtNomvins;
    private TextView txtPrix;
    private TextView txtDomaineDistance;

    private ScaleBar scaleBar;
    private App app;
    private Intent intent = null;

    private Fragment aperçuFragment;

    private FragmentManager fragmentManager;//support du fragment

    private TextView txtTelephone;
    private View itineraireIcon;
    private int idDomaine;
    private ArrayList<Double> listekmtriees = new ArrayList<Double>();
    private View separatorExtend;


    private String Strtelephone;
    private View telephoneIcon;
    private View viewblocfix;
    private View shadowblocfix;
    private int heightButtom;
    private ValueAnimator backgroundAnimator;
    private String Strnomvins;
    private Domaine domaine;
    private RecyclerView rcwvins;
    private Bundle params = new Bundle();
    int nombrevin;
    Vins vins;
    public View frtVins;
    public View imgDetailfrtVins;
    private Button btnFilter;
    TextView txtDomaineDistance2;
    ImageView imgSlideList;
    Domaines domaines;

    public MapsRayonFragment() {

    }


    public static MapsRayonFragment newInstance(Bundle args) {
        MapsRayonFragment fragment = new MapsRayonFragment();
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


        View view = inflater.inflate(R.layout.fragment_mapsrayon, container, false);

        //taille en dp du bouttonsheet
        heightButtom = (int) getResources().getDimension(R.dimen.height_buttomsheet);


        imgSlideList = view.findViewById(R.id.imgSlideList);


        txtDomaineDistance = view.findViewById(R.id.txtDomaineDistance);

        viewbottomSheet = view.findViewById(R.id.bottom_sheet);


        viewShadow = view.findViewById(R.id.shadow);


        rcwvins = view.findViewById(R.id.rcwvins);


        lltBody = view.findViewById(R.id.lltBody);


        txtNomvins = view.findViewById(R.id.txtVin);
//        txtCouleur=view.findViewById(R.id.txtCouleur);
        txtPrix = view.findViewById(R.id.txtPrix);
        txtTelephone = view.findViewById(R.id.txtTelephone);
        txtDomaineDistance2 = view.findViewById(R.id.txtDomaineDistance2);
        itineraireIcon = view.findViewById(R.id.itineraireIcon);

        separatorExtend = view.findViewById(R.id.separatorExtend);

        telephoneIcon = view.findViewById(R.id.telephoneIcon);


        viewblocfix = view.findViewById(R.id.viewblocfix);
        shadowblocfix = view.findViewById(R.id.shadowblocfix);

        frtVins = view.findViewById(R.id.frtVins);
        imgDetailfrtVins = view.findViewById(R.id.imgDetailfrtVins);

        imgDetailfrtVins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frtVins.setVisibility(View.INVISIBLE);
            }
        });


        btnFilter = view.findViewById(R.id.btnFilter);


//        permet de prendre l'instance du comportement
//        à partir des paramètres de présentation de l'instance View
        bottomSheetBehavior = BottomSheetBehavior.from(viewbottomSheet);


        //pour effet d'étendre et réduire en cliQUANT
        lltBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isup) { //false

                    // extendre la page
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    btnListe.setVisibility(View.INVISIBLE);


                    backgroundAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                    backgroundAnimator.setDuration(2000);
                    backgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {

                            float fractionAnim = (float) valueAnimator.getAnimatedValue();


                            lltBody.setBackgroundColor(ColorUtils.blendARGB(Color.parseColor("#FFFFFF")
                                    , Color.parseColor("#009AFB")
                                    , fractionAnim));

                        }
                    });
                    backgroundAnimator.start();
                    Animation animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) viewbottomSheet.getLayoutParams();
                            layoutParams.setMargins(0, 0, 0, 0);

                            viewbottomSheet.setLayoutParams(layoutParams);
                        }
                    };
                    animation.setDuration(2000);
                    animation.setInterpolator(new OvershootInterpolator());
                    viewbottomSheet.startAnimation(animation);

                    txtNomvins.setTextColor(Color.WHITE);
                    txtDomaineDistance.setTextColor(Color.WHITE);
                    txtPrix.setTextColor(Color.WHITE);
                    separatorExtend.setVisibility(View.INVISIBLE);
                    imgDetailClose.setImageResource(R.drawable.ic_close_white_24dp);

                    viewblocfix.setVisibility(View.VISIBLE);
                    shadowblocfix.setVisibility(View.VISIBLE);

                } else//true
                {
                    //extendre la page jusqu'à la taille indiquée

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    bottomSheetBehavior.setPeekHeight(heightButtom);
                    btnListe.setVisibility(View.VISIBLE);
                    txtNomvins.setTextColor(Color.BLACK);
                    txtDomaineDistance.setTextColor((Color.BLACK));
                    txtPrix.setTextColor(Color.parseColor("#9E0E40"));
                    separatorExtend.setVisibility(View.VISIBLE);
                    imgDetailClose.setImageResource(R.drawable.ic_close_black_24dp);


                    //backgroundAnimator.reverse();
                    Animation animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) viewbottomSheet.getLayoutParams();
                            int marginButtom = (int) getResources().getDimension(R.dimen.margin_buttomsheet);
                            layoutParams.setMargins(marginButtom, 0, marginButtom, 0);

                            viewbottomSheet.setLayoutParams(layoutParams);
                        }
                    };
                    animation.setDuration(2000);
                    animation.setInterpolator(new OvershootInterpolator());
                    viewbottomSheet.startAnimation(animation);

                }

                isup = !isup;


            }
        });


// mettre invisible au départ

        viewbottomSheet.setVisibility(View.INVISIBLE);
        viewShadow.setVisibility(View.INVISIBLE);


        frtVins.setVisibility(View.INVISIBLE);
        //définir la taille de départ
        bottomSheetBehavior.setPeekHeight(heightButtom);


        viewblocfix.setVisibility(View.INVISIBLE);
        shadowblocfix.setVisibility(View.INVISIBLE);

        //permet de changer  la taille du bottomsheet en déplaçant avec un slide des doigts
        // il regarde les possibilités de status possible et les éffectuent dans la mesure
        //du possible


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(final View bottomSheet, int newState) {

//pour extend au maximum possible
                if (newState == STATE_EXPANDED) {
                    btnListe.setVisibility(View.INVISIBLE);


                    backgroundAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                    backgroundAnimator.setDuration(1000);
                    backgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {

                            float fractionAnim = (float) valueAnimator.getAnimatedValue();


                            lltBody.setBackgroundColor(ColorUtils.blendARGB(Color.parseColor("#FFFFFF")
                                    , Color.parseColor("#009AFB")
                                    , fractionAnim));

                        }
                    });
                    backgroundAnimator.start();
                    Animation animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) viewbottomSheet.getLayoutParams();
                            layoutParams.setMargins(0, 0, 0, 0);

                            viewbottomSheet.setLayoutParams(layoutParams);
                        }
                    };
                    animation.setDuration(2000);
                    animation.setInterpolator(new OvershootInterpolator());
                    viewbottomSheet.startAnimation(animation);

                    txtNomvins.setTextColor(Color.WHITE);
                    txtDomaineDistance.setTextColor(Color.WHITE);
                    txtPrix.setTextColor(Color.WHITE);
                    separatorExtend.setVisibility(View.INVISIBLE);
                    imgDetailClose.setImageResource(R.drawable.ic_close_white_24dp);

                    viewblocfix.setVisibility(View.VISIBLE);
                    shadowblocfix.setVisibility(View.VISIBLE);
                    //pour la visibilité
                } else if (newState == STATE_HIDDEN) {

                    //pour réduire jusqu'à  valeur de taille défini
                } else if (newState == STATE_COLLAPSED) {
                    bottomSheetBehavior.setPeekHeight(heightButtom);
                    btnListe.setVisibility(View.VISIBLE);
                    txtNomvins.setTextColor(Color.BLACK);
                    txtDomaineDistance.setTextColor((Color.BLACK));
                    txtPrix.setTextColor(Color.parseColor("#9E0E40"));
                    separatorExtend.setVisibility(View.VISIBLE);
                    imgDetailClose.setImageResource(R.drawable.ic_close_black_24dp);


                    backgroundAnimator.reverse();
                    Animation animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) bottomSheet.getLayoutParams();
                            int marginButtom = (int) getResources().getDimension(R.dimen.margin_buttomsheet);
                            layoutParams.setMargins(marginButtom, 0, marginButtom, 0);

                            bottomSheet.setLayoutParams(layoutParams);
                        }
                    };
                    animation.setDuration(2000);
                    animation.setInterpolator(new OvershootInterpolator());
                    bottomSheet.startAnimation(animation);


                }


            }


            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });


        imgDetailClose = view.findViewById(R.id.imgDetailClose);

        imgDetailClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewShadow.setVisibility(View.INVISIBLE);
                viewbottomSheet.setVisibility(View.INVISIBLE);
                btnListe.setVisibility(View.VISIBLE);

            }
        });


        //toolbarMaps=view.findViewById(R.id.toolbarMaps);

        scaleBar = view.findViewById(R.id.scaleBarChart);

        btnListe = view.findViewById(R.id.btnListe);


        mapView = (MapView) view.findViewById(R.id.mapView);
        imgRetour = view.findViewById(R.id.imgretour);
        imgRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MapsRayonActivity) context).finish();
            }
        });

        mapView.onCreate(savedInstanceState);
        mapView.onResume(); //  pour que la carte soit affichée immédiatement

        //initialisation de la map
        mapView.getMapAsync(this);


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


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //pour pas afficher les deux toolbar en bas à droite
        mMap.getUiSettings().setMapToolbarEnabled(false);

        //récupérer le bouton localisation grâce à l'id
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));


        //permet de déplacer le bouton là ou on veut
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                locationButton.getLayoutParams();

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 0, 0, 100);


        //ajoute échelle sur la maps(voir xml!!)
        scaleBar.addTarget(mMap);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission
                .ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager) context.getSystemService
                    (LOCATION_SERVICE);

            // todo: à partir de l'api 23
            //LocationManager locationManager = (LocationManager)getContext().getSystemService
            // (LOCATION_SERVICE);


            String locationType = "";
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                locationType = LocationManager.GPS_PROVIDER;
            else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                locationType = LocationManager.NETWORK_PROVIDER;
            else locationType = LocationManager.PASSIVE_PROVIDER;
            if (!locationType.isEmpty()) {
                Log.e(Constants._TAG_LOG, "locationType: " + locationType);
                // locationManager.requestLocationUpdates(locationType, MIN_TIME_UPDATES,
                // MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                location = locationManager.getLastKnownLocation(locationType);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                }
            }


            //pour le filtre
            Log.e(Constants._TAG_LOG, "MapsRayonvins: " + "" + App.getVins());


            vins = App.getVins();


            //à améliorer ,ne marche qu'une fois  et pour un seul vin
            if (vins != null) {

                domaines = App.getDomaines();

                for (int i = 0; i < domaines.size(); i++) {



                    for (int j = 0; j < vins.size(); j++) {

                        Log.e("domaines.get(i):", "" + domaines.get(i).getIdDomaine());
                        Log.e("vins.get(j):", "" + vins.get(j).getIdDomaine());

                        if(vins.get(j).getIdDomaine()==domaines.get(i).getIdDomaine()) {
                            idDomaine = domaines.get(i).getIdDomaine();
                            latitudeDomaines = domaines.get(i).getLatitude();
                            longitudeDomaines = domaines.get(i).getLongitude();
                            StrdomaineDistance = domaines.get(i).getNomDomaine();

                        }

                        else
                        {
                            idDomaine = domaines.get(0).getIdDomaine();
                            latitudeDomaines = domaines.get(0).getLatitude();
                            longitudeDomaines = domaines.get(0).getLongitude();
                            StrdomaineDistance = domaines.get(0).getNomDomaine();

                        }


                        mycoordsDomaines = new LatLng(latitudeDomaines, longitudeDomaines);


                        domaine = new Domaine();
                        domaine.setIdDomaine(idDomaine);
                        domaine.setLongitude(longitudeDomaines);
                        domaine.setLatitude(latitudeDomaines);
                        domaine.setIdDomaine(idDomaine);
                        domaine.setNomDomaine(StrdomaineDistance);
                        domaine.setTelephone(Strtelephone);
                        domaine.setVins(new Vins());
                        domaine.setVins(vins);
                        app.setDomaines(new Domaines());
                        app.getDomaines().add(domaine);
                        Log.e("domainesssss:", "" + domaine);


                    }

                }


                //////////fin de filtre///////////////
            }


            Domaines domaines = App.getDomaines();

            Log.e("app.getDomainesss:", "" + app.getDomaines());


            //récupére les données de l'arraylist domaines

            //Log.e("rayonfrag:", "" + domaines);
            for (int i = 0; i < domaines.size(); i++) {


                latitudeDomaines = domaines.get(i).getLatitude();
                longitudeDomaines = domaines.get(i).getLongitude();

                Log.e("Strnomvins:", StrnomVins);

                mycoordsDomaines = new LatLng(latitudeDomaines, longitudeDomaines);

                idDomaine = domaines.get(i).getIdDomaine();


                Log.e("domaines:", "" + domaines.get(i));

                //condition

                View viewmarker = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_marker_icon, null);
                TextView txtMarker = (TextView) viewmarker.findViewById(R.id.txtMarker);






                //ajout du marqueur domaine

                nombrevin = domaines.get(i).getVins().size();


                if (nombrevin > 1 && nombrevin <= 9) {

                    txtMarker.setText("" + nombrevin);
                    marker = mMap.addMarker(new MarkerOptions().position(mycoordsDomaines)
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(createDrawableFromView(getContext(), viewmarker))));

                } else if (nombrevin > 9) {
                    txtMarker.setText("9+ ");

                    marker = mMap.addMarker(new MarkerOptions().position(mycoordsDomaines)
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(createDrawableFromView(getContext(), viewmarker))));

                } else if (nombrevin == 1) {

                    marker = mMap.addMarker(new MarkerOptions().position
                            (mycoordsDomaines).snippet(Strnomvins).icon(
                            BitmapDescriptorFactory.fromResource
                                    (R.drawable.icon_marker_rouge)));

                }


                domaines.get(i).setIdMarker(marker.getId());


                for (int j = 0; j < domaines.get(i).getVins().size(); j++) {
                    //calcule la distance entre les points


                    if (nombrevin > 1) {
                        latitudeDomaines = domaines.get(i).getLatitude();
                        longitudeDomaines = domaines.get(i).getLongitude();

                        distancekm = distance(latitudeDomaines, longitudeDomaines, location.getLatitude(), location.getLongitude(), 'K');
                        //permet d'arrondir à 1 chiffre aprés la virgule au max.
                        BigDecimal bd = new BigDecimal(distancekm);
                        bd = bd.setScale(1, BigDecimal.ROUND_DOWN);
                        distancekm = bd.doubleValue();
                        listekmtriees.add(distancekm);
                        StrdomaineDistance = domaines.get(i).getNomDomaine();
                        txtDomaineDistance2.setText(StrdomaineDistance + " à " + distancekm + " km");

                    } else {
                        distancekm = distance(latitudeDomaines, longitudeDomaines, location.getLatitude(), location.getLongitude(), 'K');
                        //permet d'arrondir à 1 chiffre aprés la virgule au max.
                        BigDecimal bd = new BigDecimal(distancekm);
                        bd = bd.setScale(1, BigDecimal.ROUND_DOWN);
                        distancekm = bd.doubleValue();
                        listekmtriees.add(distancekm);

                    }


                }


            }


        }
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;

                intent = new Intent(context, FilterDialogVinsActivity.class);

                startActivity(intent);
            }
        });

        btnListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //envoie à l'activity listVinsactivity directement triée la liste

                intent = new Intent(context, ListFilterVinsActivity.class);

                Collections.sort(listekmtriees);
                Log.e("listekmtriees:", "" + listekmtriees);
                intent.putExtra("listekmtriees", listekmtriees);
                startActivity(intent);
            }
        });

//pour avoir sa position
        mycoords = new LatLng(location.getLatitude(), location.getLongitude());

        //création d'un cercle
        final CircleOptions co = new CircleOptions();
        int rayon = ((MapsRayonActivity) context).getRayon();


        //paramétre du cercle
        circle = mMap.addCircle(co
                .center(mycoords)
                .radius(rayon * 1000)// exemple:2*1000=2000m=2km
                .strokeWidth(0.50f)
                .fillColor(0x80ffffff)


        );
        //mouvement de la caméra au centre de ma position
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(co.getCenter(), getZoomLevel(circle)));


        //mMap.addMarker(new MarkerOptions().position(mycoords).snippet("Ma position"));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {


                viewbottomSheet.setVisibility(View.INVISIBLE);
                viewShadow.setVisibility(View.INVISIBLE);
                frtVins.setVisibility(View.INVISIBLE);

            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                final Domaines domaines = App.getDomaines();
                for (int i = 0; i < domaines.size(); i++) {


                    Log.e("getIdMarker():", "" + domaines.get(i).getIdMarker());
                    Log.e("marker.getId():", "" + marker.getId());


                    if (domaines.get(i).getIdMarker().equals(marker.getId())) {


                        domaine = domaines.get(i);


                        if (domaines.get(i).getVins().size() == 1) {
                            frtVins.setVisibility(View.INVISIBLE);
                            idDomaine = domaines.get(i).getIdDomaine();
                            viewbottomSheet.setVisibility(View.VISIBLE);
                            viewShadow.setVisibility(View.VISIBLE);


                            params.putInt("idDomaine", idDomaine);
                            params.putInt("nombreVins", domaines.get(i).getVins().size());


                            Log.e("idDomaineMapsRayon:", "" + idDomaine);

                            //lancer le fragment de départ
                            fragmentManager = getChildFragmentManager();
                            aperçuFragment = AperçuFragment.newInstance(params);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frtMapsRayon, aperçuFragment)
                                    .addToBackStack(null)
                                    .commit();

                            prix = domaines.get(i).getVins().get(0).getPrix();


                            Strtelephone = domaines.get(i).getTelephone();


                            Log.e("idDomaine:", "" + idDomaine);
                            StrnomVins = domaines.get(i).getVins().get(0).getNomVin();
                            StrdomaineDistance = domaines.get(i).getNomDomaine();


                            telephoneIcon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent appel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Strtelephone));
                                    context.startActivity(appel);
                                }
                            });


                            // se trouve sur le bloc fixe ,n'est pas afficher sur xml et trop en bas
                            itineraireIcon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    //ouvrir maps avec coordonnées

                                    // uri :  est utilisé pour identifier une ressource
                                    //ex: d'une page de texte, d'un clip vidéo ou audio, d'une image fixe ou animée ou d'un programme
                                    //geo:latitude et longitude pour centrer les coords
                                    //?q= : pour ajouter à la barre de recherche de maps et ajouter le marker soit lat-longitude soit par le nom de rue
                                    Uri uri = Uri.parse("geo:" + latitudeDomaines + "," + longitudeDomaines + "?q=" + domaine.getAdresse());
                                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(it);

                                }
                            });


                            txtTelephone.setText(Strtelephone);


                            txtPrix.setText("" + String.format(Locale.FRANCE, "%.1f", prix) + "€");

                            txtNomvins.setText(StrnomVins);
                            Log.e("idMarker:", "" + marker.getId());

                            distancekm = distance(latitude, longitude, marker.getPosition().latitude, marker.getPosition().longitude, 'K');
                            //permet d'arrondir à 1 chiffre aprés la virgule au max.
                            BigDecimal bd = new BigDecimal(distancekm);
                            bd = bd.setScale(1, BigDecimal.ROUND_DOWN);
                            distancekm = bd.doubleValue();
                            String distance = "à " + String.format(Locale.FRANCE, "%.1f", distancekm) + " km";
                            txtDomaineDistance.setText(StrdomaineDistance + " " + distance);


                        } else {

                            for (int j = 0; j < domaines.get(i).getVins().size(); j++) {


                                Vin vin = domaines.get(i).getVins().get(j);

                                distancekm = distance(latitude, longitude, marker.getPosition().latitude, marker.getPosition().longitude, 'K');
                                //permet d'arrondir à 1 chiffre aprés la virgule au max.
                                BigDecimal bd = new BigDecimal(distancekm);
                                bd = bd.setScale(1, BigDecimal.ROUND_DOWN);
                                distancekm = bd.doubleValue();
                                String distance = "à " + String.format(Locale.FRANCE, "%.1f", distancekm) + " km";
                                txtDomaineDistance.setText(StrdomaineDistance + " " + distance);

                                StrdomaineDistance = domaines.get(i).getNomDomaine();

                                viewbottomSheet.setVisibility(View.INVISIBLE);
                                viewShadow.setVisibility(View.INVISIBLE);
                                frtVins.setVisibility(View.VISIBLE);


                                Log.e("distancekm:", "" + distancekm);


                                //déclarer l'adapter qui va contenir notre liste

                                VinsAdapter vinsAdapter = new VinsAdapter(domaines.get(i).getVins(), context, domaine, distancekm, frtVins);
//déclarer sa vue intérieur  global du reçyclerview et l'affecter ainsi que son adapter déclaré
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                                        LinearLayoutManager.VERTICAL, false);
                                rcwvins.setLayoutManager(layoutManager);
                                rcwvins.setAdapter(vinsAdapter);

                                Animation animation = AnimationUtils.loadAnimation(context, R.anim.slideinfinite);
                                imgSlideList.startAnimation(animation);

                                imgSlideList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                    }
                                });
                            }
                        }


                        break;


                    }
                }


                return false;
            }
        });


    }


    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();//effacer les marqueurs précédents

        LatLng mycoords = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(mycoords).snippet("Ma position"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mycoords, 20));//min:2, max:21
        marker.setPosition(mycoords);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    //calcul de distance en deux points depuis sa latitude-longitude en choissisant son unité
    public double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    //methode pour gérer le lvl de zoom en fonction du cercle
    public int getZoomLevel(Circle circle) {

        int zoomLevel = 11;
        if (circle != null) {

            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }


}
