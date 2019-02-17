package com.readybrains.coindesk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.readybrains.coindesk.R;
import com.readybrains.coindesk.models.SearchResult;

import java.util.ArrayList;

public class SearchAdapter extends ArrayAdapter<SearchResult> {
    public SearchAdapter(Context context, ArrayList<SearchResult> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchResult result = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_item, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.search_item_text);
        tvName.setText(result.getName());
        return convertView;
    }
}