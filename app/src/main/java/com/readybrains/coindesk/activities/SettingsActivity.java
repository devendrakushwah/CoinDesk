package com.readybrains.coindesk.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.readybrains.coindesk.R;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Set;
import java.util.prefs.Preferences;

public class SettingsActivity extends AppCompatActivity {

    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sp=findViewById(R.id.settings_dropdown);


        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Set currencySet=Currency.getAvailableCurrencies();
        ArrayList<String> currencyAdapter =new ArrayList<String>();
        for(Object c:currencySet){
            currencyAdapter.add("    "+c.toString()+"    ");
        }

        String current = sharedPreferences.getString("currency","USD");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, currencyAdapter);
        int pos=adapter.getPosition("    "+current+"    ");

        sp.setAdapter(adapter);
        sp.setSelection(pos);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem=parent.getItemAtPosition(position).toString();
                editor.putString("currency",selectedItem.substring(4,7));
                editor.commit();

                Snackbar.make(parent,"Default currecncy changed to "+selectedItem.substring(4,7),Snackbar.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


    }
}
