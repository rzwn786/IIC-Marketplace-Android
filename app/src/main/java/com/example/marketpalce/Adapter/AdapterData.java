package com.example.marketpalce.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketpalce.Model.DataModel;
import com.example.marketpalce.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.dataHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    private final Context context;
    private final List<DataModel> itemListing;

    public AdapterData(Context context, List<DataModel> itemListing,RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.itemListing = itemListing;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public dataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        dataHolder holder = new dataHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull dataHolder holder, int position) {
        DataModel dataModel = itemListing.get(position);

        holder.tvTitle.setText(dataModel.getItem_title());
        holder.tvPrice.setText(String.valueOf(dataModel.getItem_price()));
        holder.tvLocation.setText(dataModel.getItem_location());
        holder.tvDayPosted.setText(dataModel.getCreated_date());

        //Change ip depends on wifi connected
        Glide.with(context).load("http://192.168.0.108//iicmarketplace/uploads/"+dataModel.getItem_image()).into(holder.tvItemImage);

    }

    @Override
    public int getItemCount() {
        return itemListing.size();
    }

    public  class dataHolder extends RecyclerView.ViewHolder{
        TextView tvId,tvTitle,tvPrice,tvLocation,tvDayPosted;
        ImageView tvItemImage;

        public dataHolder(@NonNull View itemView ) {
            super(itemView);

            tvId = itemView.findViewById(R.id.listingid);
            tvTitle = itemView.findViewById(R.id.item_title);
            tvPrice = itemView.findViewById(R.id.item_price);
            tvLocation = itemView.findViewById(R.id.item_location);
            tvDayPosted = itemView.findViewById(R.id.created_date);
            tvItemImage = itemView.findViewById(R.id.item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int position = getBindingAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
