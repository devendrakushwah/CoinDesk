package com.readybrains.coindesk.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.readybrains.coindesk.R;
import com.readybrains.coindesk.adapters.SearchAdapter;
import com.readybrains.coindesk.models.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText searchBox;
    ListView listView;
    ArrayList<SearchResult> listItem;
    private RequestQueue mRequestQueue;
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listItem=new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        searchBox = findViewById(R.id.search_box);
        searchBox.setInputType(InputType.TYPE_CLASS_TEXT);

        listView=(ListView)findViewById(R.id.searchListView);
        parseJson("");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                try {

                    SearchResult value=adapter.getItem(position);
                    Intent detail = new Intent(SearchActivity.this,DetailsActivity.class);
                    detail.putExtra("id",value.getId());
                    detail.putExtra("source","search");
                    startActivity(detail);
                }
                catch (Exception e){

                }
            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    parseJson(searchBox.getText().toString());
                    listItem.clear();
                    adapter.notifyDataSetChanged();
                    listView.invalidateViews();
                    listView.refreshDrawableState();
                }
                catch (Exception e){

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });


    }


   void parseJson(String q){
        String url = "http://devendra8112.pythonanywhere.com/api/search_coins?q="+q;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String id = hit.getString("id");
                                String name = hit.getString("name");
                                listItem.add(new SearchResult(name,id));
                            }

                            adapter = new SearchAdapter(SearchActivity.this, listItem);
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mRequestQueue.add(request);
    }

}