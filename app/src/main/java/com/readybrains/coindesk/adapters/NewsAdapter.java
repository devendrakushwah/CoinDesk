package com.readybrains.coindesk.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.readybrains.coindesk.R;
import com.readybrains.coindesk.models.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ExampleViewHolder> {

    private Context context;
    private ArrayList<News> newsList;
    private OnItemClicked mListner;


    public interface OnItemClicked{
        void OnItemClicked(int index);
    }

    public void SetOnItemClickListener(OnItemClicked listner){
        mListner = listner;
    }
    public NewsAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_item, viewGroup,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {

        News  news = newsList.get(i);
        String title=news.getTitle();
        String date=news.getDate();
        String url=news.getUrl();
        String image= news.getImage();
        String source = news.getSource();

        exampleViewHolder.title.setText(title);
        exampleViewHolder.source.setText(source);
        //string url to convert url using picasso
        Picasso.get().load(image).fit().centerInside().into(exampleViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    public class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView title;
        public TextView source;

        public ExampleViewHolder(View itemView){
            super(itemView);

            image = itemView.findViewById(R.id.image_view);
            title = itemView.findViewById(R.id.title);
            source = itemView.findViewById(R.id.source);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListner.OnItemClicked(position);
                        }

                    }
                }
            });

        }

    }
}
