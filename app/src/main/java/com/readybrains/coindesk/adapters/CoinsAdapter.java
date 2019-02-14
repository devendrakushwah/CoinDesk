package com.readybrains.coindesk.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.readybrains.coindesk.R;
import com.readybrains.coindesk.activities.NewsWebView;
import com.readybrains.coindesk.models.Coins;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


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
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Coins currentItem = mExampleList.get(position);

        String id = currentItem.getId();
        String rank = currentItem.getRank();
        String symbol = currentItem.getSymbol();
        String name = currentItem.getName();
        String price = currentItem.getPrice();
        String change_day = currentItem.getChange_day();
        String image = currentItem.getImage();

        holder.mTextViewName.setText(name+" ("+symbol+")");
        holder.mTextViewPrice.setText(price);
        holder.mTextViewChangeDay.setText(change_day);
        holder.mTextViewRank.setText(rank);

        Picasso.get().load(image).into(holder.mImageView);
        Log.d("Coins URL : ",image);
        Picasso.get().setLoggingEnabled(true);

        holder.mCoinsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(mContext,NewsWebView.class);
                browserIntent.putExtra("url","google.com");
                mContext.startActivity(browserIntent);
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

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.home_coin_image);
            mTextViewName = itemView.findViewById(R.id.home_coin_name);
            mTextViewRank = itemView.findViewById(R.id.home_rank);
            mTextViewPrice = itemView.findViewById(R.id.home_coin_price);
            mTextViewChangeDay = itemView.findViewById(R.id.home_day_change);
            mCoinsView = itemView.findViewById(R.id.home_coin_view);
        }
    }
}