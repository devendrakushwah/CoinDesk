package com.readybrains.coindesk.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.readybrains.coindesk.R;
import com.readybrains.coindesk.models.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<News> mExampleList;

    public NewsAdapter(Context context, ArrayList<News> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        News currentItem = mExampleList.get(position);

        String title=currentItem.getTitle();
        String image=currentItem.getImage();
        String source = currentItem.getSource();
        String date = currentItem.getDate();
        final String url = currentItem.getUrl();

        holder.mTextViewTitle.setText(title);
        holder.mTextViewSource.setText(source);
        holder.mTextViewDate.setText(date);

        Picasso.get().load(image).into(holder.mImageView);
        Log.d("URL : ",image);
        Picasso.get().setLoggingEnabled(true);

        holder.mNewsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
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
        public TextView mTextViewTitle;
        public TextView mTextViewSource;
        public TextView mTextViewDate;
        public CardView mNewsView;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.news_image_view);
            mTextViewTitle = itemView.findViewById(R.id.news_title);
            mTextViewSource = itemView.findViewById(R.id.news_source);
            mTextViewDate = itemView.findViewById(R.id.news_date);
            mNewsView = itemView.findViewById(R.id.news_view);
        }
    }
}