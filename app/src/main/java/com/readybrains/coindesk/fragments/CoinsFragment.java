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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.readybrains.coindesk.R;
import com.readybrains.coindesk.adapters.CoinsAdapter;
import com.readybrains.coindesk.adapters.NewsAdapter;
import com.readybrains.coindesk.models.Coins;
import com.readybrains.coindesk.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoinsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CoinsAdapter mExampleAdapter;
    private ArrayList<Coins> mExampleList;
    private RequestQueue mRequestQueue;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressDialog progressBar ;

    public CoinsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coins, container, false);

        mRecyclerView = view.findViewById(R.id.home_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout=view.findViewById(R.id.swipe_home);

       progressBar = ProgressDialog.show(getContext(), "", "Loading...");

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(getActivity());
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
        String url = "http://devendra8112.pythonanywhere.com/api/get_top_coins/?exchange=INR";


        //Toast.makeText(getActivity(), "non", Toast.LENGTH_SHORT).show();

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

                                mExampleList.add(new Coins(id,rank,symbol,name,price,change_day,image));
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
