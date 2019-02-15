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
import com.readybrains.coindesk.R;
import com.readybrains.coindesk.adapters.NewsAdapter;
import com.readybrains.coindesk.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private NewsAdapter mExampleAdapter;
    private ArrayList<News> mExampleList;
    private RequestQueue mRequestQueue;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressDialog progressBar ;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mRecyclerView = view.findViewById(R.id.news_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout=view.findViewById(R.id.swipe_news);

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
        String url = "http://devendra8112.pythonanywhere.com/api/get_news/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String title = hit.getString("title");
                                String image = hit.getString("image");
                                String url = hit.getString("url");
                                String source = hit.getString("source");
                                String date = hit.getString("date");

                                mExampleList.add(new News(title,image,url,date,source));
                            }

                            mExampleAdapter = new NewsAdapter(getActivity(), mExampleList);
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
                        progressBar = ProgressDialog.show(getContext(), "", "Loading...");
                        parseJSON();
                    }
                }).show();
            }
        });

        mRequestQueue.add(request);
    }
}
