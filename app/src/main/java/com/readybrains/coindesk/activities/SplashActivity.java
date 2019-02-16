package com.readybrains.coindesk.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;
import com.readybrains.coindesk.R;
public class SplashActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    public static final String DB="database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        relativeLayout = findViewById(R.id.splash);
        SharedPreferences.Editor editor = getSharedPreferences(DB,MODE_PRIVATE).edit();
        String currency = getSharedPreferences(DB,MODE_PRIVATE).getString("defaultCurrency",null);
        if(currency==null)
        {
            editor.putString("defaultCurrency","USD");
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
