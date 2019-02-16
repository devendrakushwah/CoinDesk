package com.readybrains.coindesk.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
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

        String id = currentItem.getId();
        String rank = currentItem.getRank();
        String symbol = currentItem.getSymbol();
        String name = currentItem.getName();
        String price_string = currentItem.getPrice();
        String change_day = currentItem.getChange_day();
        String image = currentItem.getImage();

        DecimalFormat df = new DecimalFormat("#.#####");
        df.setRoundingMode(RoundingMode.CEILING);

        String currency = mContext.getSharedPreferences(DB,MODE_PRIVATE).getString("defaultCurrency","USD");

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
        holder.mImageViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mImageViewFavourite.setImageResource(R.drawable.favourite);
            }
        });
        holder.mCoinsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent = new Intent(mContext,DetailsActivity.class);
                detailsIntent.putExtra("id",currentItem.getId());
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