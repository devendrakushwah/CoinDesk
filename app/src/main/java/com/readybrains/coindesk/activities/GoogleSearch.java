package com.readybrains.coindesk.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.readybrains.coindesk.R;

public class GoogleSearch extends AppCompatActivity {

    private WebView myWebView;
    AdView googleWebAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_search);

        googleWebAdView=findViewById(R.id.googleSearchAdView);

        String url = getIntent().getStringExtra("url");
        myWebView = (WebView) findViewById(R.id.google_web_view);
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(false);
        myWebView.loadUrl(url);

        AdRequest adRequest = new AdRequest.Builder().build();
        googleWebAdView.loadAd(adRequest);

        final ProgressDialog progressBar = ProgressDialog.show(this, "", "Loading...");

        myWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }

            }
        });}}
