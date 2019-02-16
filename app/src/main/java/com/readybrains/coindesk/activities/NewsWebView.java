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

public class NewsWebView extends AppCompatActivity {

    private WebView myWebView;
    AdView newsWebAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);

        newsWebAdView = findViewById(R.id.newsWebAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        newsWebAdView.loadAd(adRequest);

        String url = getIntent().getStringExtra("url");
        myWebView = (WebView) findViewById(R.id.news_web_view);
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(false);
        myWebView.loadUrl(url);

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
