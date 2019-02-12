package com.readybrains.coindesk.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.readybrains.coindesk.activities.MainActivity;
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

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private ArrayList<News> newsList;
    private RequestQueue requestQueue;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.newsrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());

        parseJson();

        return view;
    }

    private void parseJson() {
        String url = "http://devendra8112.pythonanywhere.com/api/get_news/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("JSON",response.toString());
                try {
                    JSONObject jo=new JSONObject(response.toString());
                    JSONArray jsonArray = jo.getJSONArray("1");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String title = hit.getString("title");
                        String image=hit.getString("image");
                        String url = hit.getString("url");
                        String date = hit.getString("date");
                        String source = hit.getString("source");
                        newsList.add(new News(title,image,url,date,source));

                    }

                    newsAdapter = new NewsAdapter(getActivity(),newsList);
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.SetOnItemClickListener(new NewsAdapter.OnItemClicked() {
                        @Override
                        public void OnItemClicked(int index) {
                            Snackbar.make(recyclerView,String.valueOf(index),2);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
