package com.readybrains.coindesk.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.readybrains.coindesk.R;

import static com.readybrains.coindesk.activities.SplashActivity.DB;

public class SettingsActivity extends AppCompatActivity {
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sp=findViewById(R.id.settings_dropdown);
        String currencyAdapter[]={"AED","AFN","ALL","AMD","ANG","AOA","ARS","AUD","AWG","AZN","BAM","BBD","BDT","BGN","BHD","BIF","BMD","BND","BOB","BRL","BSD","BTC","BTN","BWP","BYN","BYR","BZD","CAD","CDF","CHF","CLF","CLP","CNY","COP","CRC","CUC","CUP","CVE","CZK","DJF","DKK","DOP","DZD","EGP","ERN","ETB","EUR","FJD","FKP","GBP","GEL","GGP","GHS","GIP","GMD","GNF","GTQ","GYD","HKD","HNL","HRK","HTG","HUF","IDR","ILS","IMP","INR","IQD","IRR","ISK","JEP","JMD","JOD","JPY","KES","KGS","KHR","KMF","KPW","KRW","KWD","KYD","KZT","LAK","LBP","LKR","LRD","LSL","LTL","LVL","LYD","MAD","MDL","MGA","MKD","MMK","MNT","MOP","MRO","MUR","MVR","MWK","MXN","MYR","MZN","NAD","NGN","NIO","NOK","NPR","NZD","OMR","PAB","PEN","PGK","PHP","PKR","PLN","PYG","QAR","RON","RSD","RUB","RWF","SAR","SBD","SCR","SDG","SEK","SGD","SHP","SLL","SOS","SRD","STD","SVC","SYP","SZL","THB","TJS","TMT","TND","TOP","TRY","TTD","TWD","TZS","UAH","UGX","USD","UYU","UZS","VEF","VND","VUV","WST","XAF","XAG","XAU","XCD","XDR","XOF","XPF","YER","ZAR","ZMK","ZMW","ZWL"};
        String current = getSharedPreferences(DB,MODE_PRIVATE).getString("defaultCurrency","USD");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, currencyAdapter);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        int pos=adapter.getPosition(current);
        sp.setAdapter(adapter);
        sp.setSelection(pos);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem=parent.getItemAtPosition(position).toString();

                SharedPreferences.Editor editor = getSharedPreferences(DB, MODE_PRIVATE).edit();
                editor.putString("defaultCurrency",selectedItem);
                editor.commit();
                Snackbar.make(parent,"Default currency set to "+selectedItem,Snackbar.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
