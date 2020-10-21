package com.beratdamla.others;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beratdamla.klu_mobil.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HaberlerAdapter extends RecyclerView.Adapter<HaberlerAdapter.ViewHolder> {

    private List<Haber> mHaberler;
    private Context mContext;

    public HaberlerAdapter(List<Haber> haberler) {
        mHaberler = haberler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.cv_haberduyuru, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Haber haber = mHaberler.get(position);
        Picasso.get().load(Uri.parse(haber.getResim())).into(holder.resim);
        holder.baslik.setText(haber.getBaslik());
        holder.link.setOnClickListener(view -> Toast.makeText(mContext,haber.getLink(),Toast.LENGTH_LONG).show());
        holder.info.setText("Paylaşan:"+haber.getBirim()+"\tTarih:"+haber.getTarih()+"\t"+haber.getOkunma());
        holder.detay.setText(haber.getDetay()+"...");
    }

    @Override
    public int getItemCount() {
        return mHaberler.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView resim;
        public TextView baslik;
        public TextView info;
        public TextView detay;
        public View link;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resim = itemView.findViewById(R.id.cv_gorsel);
            baslik = itemView.findViewById(R.id.cv_baslik);
            info = itemView.findViewById(R.id.cv_info);
            detay = itemView.findViewById(R.id.cv_detay);
            link = itemView.findViewById(R.id.cv_link);
        }
    }
}
