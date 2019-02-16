package com.readybrains.coindesk.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.readybrains.coindesk.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity {


    RequestQueue mRequestQueue;
    RelativeLayout detailsLayout;
    TextView detailsName, detailsSymbol;
    TextView hourChange,dayChange,weekChange;
    ImageView coinImage,img1,img2,img3;
    TextView rankText,priceText,tSupply,mSupply,cSupply;
    String rank,symbol,name,price,total_supply,max_supply,circulating_supply,change_hour,change_day,change_week,image;
    CardView google;
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = ProgressDialog.show(this, "", "Loading...");
        setContentView(R.layout.activity_details);
        coinImage = findViewById(R.id.details_image);
        detailsLayout = findViewById(R.id.details_layout);
        google=findViewById(R.id.details_title);

        //Initialise views here

        hourChange = findViewById(R.id.details_hour_text);
        dayChange = findViewById(R.id.details_day_text);
        weekChange = findViewById(R.id.details_week_text);

        img1=findViewById(R.id.details_img1);
        img2=findViewById(R.id.details_img2);
        img3=findViewById(R.id.details_img3);

        detailsName=findViewById(R.id.details_name);
        detailsSymbol=findViewById(R.id.details_symbol);

        rankText=findViewById(R.id.details_rank);
        priceText=findViewById(R.id.details_price);
        tSupply=findViewById(R.id.details_totalsupply);
        mSupply=findViewById(R.id.details_maxsupply);
        cSupply=findViewById(R.id.details_csupply);


        mRequestQueue = Volley.newRequestQueue(this);

        Intent i = getIntent();
        String id=i.getStringExtra("id");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        final String currency = sharedPreferences.getString("defaultCurrency",null);

        String url = "http://devendra8112.pythonanywhere.com/api/get_details/?id="+id+"&exchange="+currency;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                rank = hit.getString("cmc_rank");
                                symbol = hit.getString("symbol");
                                name = hit.getString("name");
                                price = hit.getString("price");
                                circulating_supply = hit.getString("circulating_supply");
                                total_supply = hit.getString("total_supply");
                                max_supply = hit.getString("max_supply");
                                change_hour = hit.getString("change_hour");
                                change_day = hit.getString("change_day");
                                change_week = hit.getString("change_week");
                                image = hit.getString("image_url");


                                google.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(DetailsActivity.this,GoogleSearch.class);
                                        i.putExtra("url","https://www.google.com/search?q="+name);
                                        startActivity(i);
                                    }
                                });

                                setTitle(name+" ("+symbol+")");

                                //Populate views here

                                detailsName.setText(name);
                                detailsSymbol.setText("("+symbol+")");


                                //for hour Change
                                hourChange.setText(change_hour+"%");
                                if(change_hour.charAt(0)=='-'){
                                    hourChange.setTextColor(Color.RED);
                                    img1.setImageResource(R.drawable.decrease);
                                }
                                else {
                                    hourChange.setTextColor(Color.parseColor("#4BB543"));
                                    img1.setImageResource(R.drawable.increase);
                                }

                                //for day Change
                                dayChange.setText(change_day+"%");
                                if(change_day.charAt(0)=='-'){
                                    dayChange.setTextColor(Color.RED);
                                    img2.setImageResource(R.drawable.decrease);
                                }
                                else {
                                    dayChange.setTextColor(Color.parseColor("#4BB543"));
                                    img2.setImageResource(R.drawable.increase);
                                }

                                //for week Change
                                weekChange.setText(change_week+"%");
                                if(change_week.charAt(0)=='-'){
                                    weekChange.setTextColor(Color.RED);
                                    img3.setImageResource(R.drawable.decrease);
                                }
                                else {
                                    weekChange.setTextColor(Color.parseColor("#4BB543"));
                                    img3.setImageResource(R.drawable.increase);
                                }


                                rankText.setText(rank);

                                DecimalFormat df = new DecimalFormat("#.#####");
                                df.setRoundingMode(RoundingMode.CEILING);
                                float priceFloat=Float.parseFloat(price);
                                priceText.setText(df.format(priceFloat)+" "+currency);

                                tSupply.setText(total_supply+" "+symbol);
                                if(max_supply.equals("null")){
                                    max_supply="------";
                                }
                                mSupply.setText(max_supply+" "+symbol);
                                cSupply.setText(circulating_supply+" "+symbol);


                                Picasso.get().load(image).into(coinImage);
                                progressBar.dismiss();
                            }
                        } catch (JSONException e) {
                            progressBar.dismiss();
                            Snackbar.make(detailsLayout,"Something went wrong, Go back.",Snackbar.LENGTH_LONG);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.dismiss();
                Intent main=new Intent(DetailsActivity.this,MainActivity.class);
                startActivity(main);
                finish();
            }
        });
        mRequestQueue.add(request);
    }
}