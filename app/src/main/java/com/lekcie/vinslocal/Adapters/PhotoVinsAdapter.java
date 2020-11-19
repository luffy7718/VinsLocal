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

public class PhotoVinsAdapter extends RecyclerView.Adapter<PhotoVinsAdapter.PhotoVinHolder> {

    private Context mcontext;

    private ImageView imgVins;
    private ArrayList<String> imageUrls = new ArrayList<String>();
    Activity activity;
    Picasso picasso;
    int positionPhoto;


    public PhotoVinsAdapter(Context context, ArrayList<String> imageUrls, Activity activity) {
        this.mcontext = context;
        this.imageUrls = imageUrls;
        this.activity = activity;
    }


    @Override
    public int getItemCount() {
        return imageUrls.size();
    }


    @Override
    public PhotoVinHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photosvins, parent, false);

        imgVins = itemView.findViewById(R.id.imgVins);

        return new PhotoVinHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoVinHolder holder, int position) {


        positionPhoto = 0;
        imageUrls.get(position);


        //prend url et la charge depuis la librairie picasso qui peut en plus faire des effets


        picasso.with(mcontext).load(imageUrls.get(position)).into(imgVins);


        Log.e("position:", "" + position);
        Log.e("mphotos.size:", "" + imageUrls.size());

        for (String url : imageUrls) {


            if (position == positionPhoto) {
                holder.addurl(url);

            }
            positionPhoto++;


        }

    }


    public class PhotoVinHolder extends RecyclerView.ViewHolder {


        String url;

        public PhotoVinHolder(View itemView) {
            super(itemView);

            imgVins.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();



                    LayoutInflater inflater = LayoutInflater.from(activity);
                    final View container = inflater.inflate(R.layout.dialog_photos_fullcreen, null);

                    final ImageView imgVinsFull = container.findViewById(R.id.imgVinsFull);
                    final ImageView   imgDetailClose= container.findViewById(R.id.imgDetailClose);
                    final TextView txtTitleBar= container.findViewById(R.id.txtTitleBar);


                    picasso.with(mcontext).load(url).into(imgVinsFull);

                    txtTitleBar.setText("Photos du vin");
                    imgVinsFull.setOnClickListener(new View.OnClickListener() {
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
