package com.lekcie.vinslocal.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lekcie.vinslocal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotoDomainesAdapter extends RecyclerView.Adapter<PhotoDomainesAdapter.PhotoDomaineHolder> {

    private Context mcontext;

    private ImageView imgDomaines;
    private ArrayList<String> imageUrls = new ArrayList<String>();
    Picasso picasso;
    int positionPhoto;
    Activity activity;

    public PhotoDomainesAdapter(Context context, ArrayList<String> imageUrls, Activity activity) {
        this.mcontext = context;
        this.imageUrls = imageUrls;
        this.activity = activity;
    }


    @Override
    public int getItemCount() {
        return imageUrls.size();
    }


    @Override
    public PhotoDomainesAdapter.PhotoDomaineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photosdomaine, parent, false);

        imgDomaines = itemView.findViewById(R.id.imgDomaines);

        return new PhotoDomainesAdapter.PhotoDomaineHolder(itemView);
    }


    @Override
    public void onBindViewHolder(PhotoDomaineHolder photoDomaineHolder, int position) {
        imageUrls.get(position);
        //prend url et la charge depuis la librairie picasso qui peut
        // en plus faire des effets
        positionPhoto = 0;

        Picasso.with(mcontext).load(imageUrls.get(position)).into(imgDomaines);


        Log.e("position:", "" + position);
        Log.e("mphotos.size:", "" + imageUrls.size());

        for (String url : imageUrls) {


            if (position == positionPhoto) {
                photoDomaineHolder.addurl(url);

            }
            positionPhoto++;


        }
    }


    public class PhotoDomaineHolder extends RecyclerView.ViewHolder {
        String url;

        public PhotoDomaineHolder(View itemView) {
            super(itemView);

            imgDomaines.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();



                    LayoutInflater inflater = LayoutInflater.from(activity);
                    final View container = inflater.inflate(R.layout.dialog_photos_fullcreen, null);
                    final ImageView imgDomaineFull = container.findViewById(R.id.imgVinsFull);
                    final ImageView imgDetailClose = container.findViewById(R.id.imgDetailClose);
                    final TextView txtTitleBar= container.findViewById(R.id.txtTitleBar);

                    picasso.with(mcontext).load(url).into(imgDomaineFull);

                    txtTitleBar.setText("Photos du domaine");
                    imgDomaineFull.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.dismiss();
                        }
                    });

                    imgDetailClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.setView(container);


                    alertDialog.show();
                }
            });
        }


        public void addurl(String url) {

            this.url = url;
        }
    }
}