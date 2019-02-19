package com.readybrains.coindesk.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.readybrains.coindesk.R;
import com.readybrains.coindesk.adapters.CoinsAdapter;
import com.readybrains.coindesk.models.Coins;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.readybrains.coindesk.activities.SplashActivity.DB;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {

    AdView favouritesAdView;
    private RecyclerView mRecyclerView;
    private CoinsAdapter mExampleAdapter;
    private ArrayList<Coins> mExampleList;
    private RequestQueue mRequestQueue;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressDialog progressBar ;

    public FavouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        favouritesAdView = view.findViewById(R.id.favouritesAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        favouritesAdView.loadAd(adRequest);

        mRecyclerView = view.findViewById(R.id.favourites_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout=view.findViewById(R.id.swipe_favourite);


        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(getActivity());
        progressBar = ProgressDialog.show(getContext(), "", "Loading...");
        parseJSON();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                progressBar = ProgressDialog.show(getContext(), "", "Loading...");
                parseJSON();
                mExampleList.clear();
                mRecyclerView.removeAllViews();
                mExampleAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
    private void parseJSON() {

        String currency = getActivity().getSharedPreferences(DB,MODE_PRIVATE).getString("defaultCurrency",null);

        String url = "http://devendra8112.pythonanywhere.com/api/get_top_coins/?exchange="+currency;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String id = hit.getString("id");
                                String rank = hit.getString("cmc_rank");
                                String symbol = hit.getString("symbol");
                                String name = hit.getString("name");
                                String price = hit.getString("price");
                                String change_day = hit.getString("change_day");
                                String image = hit.getString("image_url");


                                final Set<String> favourites= getActivity().getSharedPreferences(DB,MODE_PRIVATE).getStringSet("userFavourites",null);

                                if(favourites.contains(id)){
                                    mExampleList.add(new Coins(id,rank,symbol,name,price,change_day,image));
                                }
                            }

                            if(mExampleList.size() == 0){
                                mRecyclerView.setVisibility(View.INVISIBLE);
                            }
                            else {
                                    mRecyclerView.setVisibility(View.VISIBLE);
                            }
                            mExampleAdapter = new CoinsAdapter(getActivity(), mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            progressBar.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.dismiss();
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        "Problem with internet connection", 10000).setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar= ProgressDialog.show(getContext(), "", "Loading...");
                        parseJSON();
                    }
                }).show();
            }
        });
        mRequestQueue.add(request);
    }
}
