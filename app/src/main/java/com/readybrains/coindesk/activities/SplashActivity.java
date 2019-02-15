package com.readybrains.coindesk.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;
import com.readybrains.coindesk.R;
public class SplashActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        relativeLayout = findViewById(R.id.splash);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String currency = sharedPreferences.getString("currency",null);
        if(currency==null)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("currency","USD");
            editor.commit();
            Snackbar.make(relativeLayout,"Default currency set to USD. You can change it from settings.",Snackbar.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);


}}
