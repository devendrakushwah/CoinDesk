package com.readybrains.coindesk.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.readybrains.coindesk.R;
import com.readybrains.coindesk.activities.DetailsActivity;
import com.readybrains.coindesk.models.Coins;
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.readybrains.coindesk.activities.SplashActivity.DB;


public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<Coins> mExampleList;

    public CoinsAdapter(Context context, ArrayList<Coins> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.coin_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, int position) {
        final Coins currentItem = mExampleList.get(position);

        holder.setIsRecyclable(false);
        final String id = currentItem.getId();
        String rank = currentItem.getRank();
        String symbol = currentItem.getSymbol();
        final String name = currentItem.getName();
        String price_string = currentItem.getPrice();
        String change_day = currentItem.getChange_day();
        String image = currentItem.getImage();

        DecimalFormat df = new DecimalFormat("#.#####");
        df.setRoundingMode(RoundingMode.CEILING);

        String currency = mContext.getSharedPreferences(DB,MODE_PRIVATE).getString("defaultCurrency","USD");
        final SharedPreferences.Editor editor = mContext.getSharedPreferences(DB,MODE_PRIVATE).edit();

        final Set<String> Mainfavourites= mContext.getSharedPreferences(DB,MODE_PRIVATE).getStringSet("userFavourites",null);

        //Log.d("Fav",Mainfavourites.toString());

        holder.mTextViewName.setText(name+" ("+symbol+")");
        float price=Float.parseFloat(price_string);
        holder.mTextViewPrice.setText("Price : "+df.format(price)+" "+currency);
        holder.mTextViewChangeDay.setText(change_day+"%");
        if(change_day.charAt(0)=='-'){
            holder.mTextViewChangeDay.setTextColor(Color.RED);
            holder.mImageViewIncDesc.setImageResource(R.drawable.decrease);
        }else {
            holder.mTextViewChangeDay.setTextColor(Color.parseColor("#4BB543"));
            holder.mImageViewIncDesc.setImageResource(R.drawable.increase);
        }

        holder.mTextViewRank.setText(rank);

        Picasso.get().load(image).into(holder.mImageView);

        if(Mainfavourites.contains(id)){
            holder.mImageViewFavourite.setImageResource(R.drawable.favourite);
        }


        holder.mImageViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String>favourites=mContext.getSharedPreferences(DB,MODE_PRIVATE).getStringSet("userFavourites",null);

                if(favourites.contains(id)){
                    holder.mImageViewFavourite.setImageResource(R.drawable.unfavourite);
                    Set<String>temp=favourites;
                    temp.remove(id);
                    editor.remove("userFavourites");
                    editor.commit();
                    editor.putStringSet("userFavourites",temp);
                    editor.commit();
                    //Log.d("Done",temp.toString());
                    Snackbar.make(v,name+" removed from favourites",Snackbar.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
                else {
                    holder.mImageViewFavourite.setImageResource(R.drawable.favourite);
                    SharedPreferences.Editor editor = mContext.getSharedPreferences(DB,MODE_PRIVATE).edit();
                    Set<String>temp=favourites;
                    temp.add(id);
                    editor.remove("userFavourites");
                    editor.commit();
                    editor.putStringSet("userFavourites",temp);
                    editor.commit();
                    Snackbar.make(v,name+" added to favourites",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        holder.mCoinsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent = new Intent(mContext,DetailsActivity.class);
                detailsIntent.putExtra("id",currentItem.getId());
                detailsIntent.putExtra("source","nonsearch");
                mContext.startActivity(detailsIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewName;
        public TextView mTextViewRank;
        public TextView mTextViewPrice;
        public TextView mTextViewChangeDay;
        public CardView mCoinsView;
        public ImageView mImageViewFavourite;
        public ImageView mImageViewIncDesc;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.home_coin_image);
            mTextViewName = itemView.findViewById(R.id.home_coin_name);
            mTextViewRank = itemView.findViewById(R.id.home_rank);
            mTextViewPrice = itemView.findViewById(R.id.home_coin_price);
            mTextViewChangeDay = itemView.findViewById(R.id.home_day_change);
            mCoinsView = itemView.findViewById(R.id.home_coin_view);
            mImageViewFavourite=itemView.findViewById(R.id.home_favourite);
            mImageViewIncDesc=itemView.findViewById(R.id.home_increase_decrease);
        }
    }
}