package com.fast.all.video.downloader;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class FaceBookActivity extends AppCompatActivity {
    private MyWebChromeClient mWebChromeClient = null;
    private View mCustomView;
    Boolean mShouldPause;
    private RelativeLayout mContentView;
    private FrameLayout mCustomViewContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    Button button,b7,b8,b9,b10,b11;
    EditText t1;
    private static final String TAG_VIDURL = "video_url";
    public String vidData, vidId;
    private static final String target_url = "https://m.facebook.com/";
    private static WebView webView;
    private String URL;
    InterstitialAd mInterstitialAd;
    AdView mAdView;

    private ProgressBar progressBar;

    @SuppressLint("JavascriptInterface")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progrss);
        button = findViewById(R.id.button);
        b7= findViewById(R.id.button7);
        b8= findViewById(R.id.button8);
        b9= findViewById(R.id.button9);
        b10= findViewById(R.id.button10);
        b11= findViewById(R.id.button11);
        t1 = findViewById(R.id.editText);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "FBDownloader");
        mWebChromeClient = new MyWebChromeClient();
        webView.setWebChromeClient(mWebChromeClient);

        mAdView = findViewById(R.id.adView);
        //inializeFooter();

        if (isOnline()) {
            showBannerAd();
        } else {
            // mAdView.setVisibility(View.GONE);
            mAdView.setVisibility(View.GONE);

        }
        mInterstitialAd = new InterstitialAd(getApplicationContext());

        mInterstitialAd.setAdUnitId(getApplicationContext().getString(R.string.interstitial_full_screen));
        AdRequest addRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(addRequest);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { "
                        + "var el = document.querySelectorAll('div[data-sigil]');"
                        + "for(var i=0;i<el.length; i++)"
                        + "{"
                        + "var sigil = el[i].dataset.sigil;"
                        + "if(sigil.indexOf('inlineVideo') > -1){"
                        + "delete el[i].dataset.sigil;"
                        + "var jsonData = JSON.parse(el[i].dataset.store);"
                        + "el[i].setAttribute('onClick', 'FBDownloader.processVideo(\"'+jsonData['src']+'\");');"
                        + "}" + "}" + "})()");
                Log.e("WEBVIEWFIN", url);

            }

            @Override
            public void onLoadResource(WebView view, String url) {
                webView.loadUrl("javascript:(function prepareVideo() { "
                        + "var el = document.querySelectorAll('div[data-sigil]');"
                        + "for(var i=0;i<el.length; i++)"
                        + "{"
                        + "var sigil = el[i].dataset.sigil;"
                        + "if(sigil.indexOf('inlineVideo') > -1){"
                        + "delete el[i].dataset.sigil;"
                        + "console.log(i);"
                        + "var jsonData = JSON.parse(el[i].dataset.store);"
                        + "el[i].setAttribute('onClick', 'FBDownloader.processVideo(\"'+jsonData['src']+'\",\"'+jsonData['videoID']+'\");');"
                        + "}" + "}" + "})()");
                webView.loadUrl("javascript:( window.onload=prepareVideo;"
                        + ")()");
            }


        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (t1.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Url", Toast.LENGTH_LONG).show();
                }
                else if (t1.getText().toString().contains("youtube.com")||t1.getText().toString().contains("youtu")) {
                    mShouldPause = true;
                    Toast.makeText(getApplicationContext(),"Incompatible Link",Toast.LENGTH_SHORT).show();
                }
            else {
                    CookieSyncManager.createInstance(getApplicationContext());
                    CookieManager cookieManager = CookieManager.getInstance();
                    cookieManager.setAcceptCookie(true);
                    CookieSyncManager.getInstance().startSync();
                    webView.loadUrl(t1.getText().toString());

                }


            }
        });


        CookieSyncManager.createInstance(getApplicationContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        CookieSyncManager.getInstance().startSync();
        webView.loadUrl(target_url);

    }
    public void home(View v){
        Intent Intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(Intent);
    }

    public void download(View v){
        Intent Intent = new Intent(getApplicationContext(), All_Downloads.class);
        startActivity(Intent);
    }
    public void downloadpro(View v){
        Intent Intent = new Intent(getApplicationContext(), MainActivity1.class);
        startActivity(Intent);
    }
    public void like(View v){
        AlertDialog.Builder ad = new AlertDialog.Builder(FaceBookActivity.this);
        ad.setTitle("Do you Love this app?");
        ad.setMessage("Then Rate Us 5 Stars!");
        ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
            }

        });
        ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        ad.show();
    }
    public void share(View v) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, "Download this");
        share.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
        startActivity(Intent.createChooser(share, "Share to Friends!"));
    }

    public void showAdsFull() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        mInterstitialAd = new InterstitialAd(getApplicationContext());

        mInterstitialAd.setAdUnitId(getApplicationContext().getString(R.string.interstitial_full_screen));
        AdRequest addRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(addRequest);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showBannerAd() {

        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

    }

    public void onBackPressed() {
        if (mCustomViewContainer != null)
            mWebChromeClient.onHideCustomView();
        else if (webView.canGoBack())
            webView.goBack();
        else {
            super.onBackPressed();
            showAdsFull();
        }
    }


    public class MyWebChromeClient extends WebChromeClient {

        FrameLayout.LayoutParams LayoutParameters = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        @Override
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mContentView = findViewById(R.id.facebook);
            mContentView.setVisibility(View.GONE);
            mCustomViewContainer = new FrameLayout(FaceBookActivity.this);
            mCustomViewContainer.setLayoutParams(LayoutParameters);
            mCustomViewContainer.setBackgroundResource(android.R.color.black);
            view.setLayoutParams(LayoutParameters);
            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
            setContentView(mCustomViewContainer);
        }

        @Override
        public void onHideCustomView() {
            if (mCustomView == null) {
                return;
            } else {
                // Hide the custom view.
                mCustomView.setVisibility(View.GONE);
                // Remove the custom view from its container.
                mCustomViewContainer.removeView(mCustomView);
                mCustomView = null;
                mCustomViewContainer.setVisibility(View.GONE);
                mCustomViewCallback.onCustomViewHidden();
                // Show the content view.
                mContentView.setVisibility(View.VISIBLE);
                setContentView(mContentView);
            }
        }
    }


    @JavascriptInterface
    public void processVideo(final String vidData, final String vidID) {

        Log.e("WEBVIEWJS", "RUN");
        Log.e("WEBVIEWJS", vidData);
        this.vidData = vidData;
        this.vidId = vidID;
        if (vidData != null && vidID != null) {
            alertTwoButtons();
        }
    }

    public void alertTwoButtons() {
        new AlertDialog.Builder(FaceBookActivity.this)
                .setTitle("Select Options")
                .setMessage("Where Do You want to go?")
                .setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setNeutralButton("StreamVideo ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Intent i = new Intent(getApplicationContext(), StreamVideo.class);
                            i.putExtra(TAG_VIDURL, vidData);
                            startActivity(i);
                            Toast toast = Toast.makeText(getApplicationContext(), "Streaming Started",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(getApplicationContext(), "Streaming Failed",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Download", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getApplicationContext(), Dialog.class);
                        i.putExtra("vid_data", vidData);
                        i.putExtra("vid_id", vidId);
                        startActivity(i);
                        dialog.cancel();
                    }
                }).show();
        }

    public boolean Back() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        } else
            return false;
    }
    public void onPause() {
        try {
            super.onPause();
            if (mShouldPause) {
                webView.onPause();
            }
        }
        catch (Exception e)
        {
            Log.d("Whaterror",e.toString());
        }
        mShouldPause = false;
    }

}