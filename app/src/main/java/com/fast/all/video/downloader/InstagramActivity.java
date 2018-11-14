package com.fast.all.video.downloader;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONObject;

import java.io.File;
import java.util.Random;

public class InstagramActivity extends AppCompatActivity {
    Button btngo;
    EditText t1;
    private Button b7,b8,b9,b10,b11;
    public String download_link;
    public String vidID;
    private Button  btndownload;
    private VideoView vv;
    private MediaController mediacontroller;
    private Uri uri;
    private ProgressBar progressBar;
    InterstitialAd mInterstitialAd;
    AdView mAdView;
    MainActivity1 obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
        btngo = findViewById(R.id.button);
        t1 = findViewById(R.id.editText);
        progressBar = findViewById(R.id.progrss);
        btndownload = findViewById(R.id.button5);
        vv = findViewById(R.id.videoView);
        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);
        obj = new MainActivity1();
        mediacontroller.setMediaPlayer(vv);
        mAdView = findViewById(R.id.adView);
        b7= findViewById(R.id.button7);
        b8= findViewById(R.id.button8);
        b9= findViewById(R.id.button9);
        b10= findViewById(R.id.button10);
        b11= findViewById(R.id.button11);
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
        try {
            if (!StaticData.URL.isEmpty()) {

                otherActivity();
            }
        }catch (Exception e){
            Log.d("OtherException", e.toString());
        }
        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (t1.getText().toString().isEmpty()) {
                    alert();
                }
                else if(t1.getText().toString().contains("instagram")) {

                    download_link = t1.getText().toString();
                    download_link = download_link + "?__a=1";
                    try {
                        Log.d("Before", download_link);
                        vidID = showdata(download_link);
                        Log.d("after", vidID);
                        progressBar.setVisibility(View.VISIBLE);
                        vv.setVisibility(View.VISIBLE);
                        vv.setMediaController(mediacontroller);
                        vv.setVideoURI(Uri.parse(vidID));
                        vv.requestFocus();
                        vv.start();
                        btndownload.setVisibility(View.VISIBLE);
                    }
                    catch (Exception e) {
                        Log.d("TEXT",e.toString());
                    }
                }
                else

                    alert();
            }
        });


        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t1.getText().toString().isEmpty())
                { // alert();
                }
               // obj.processVideo(InstagramActivity.this, vidID);
                StaticData.URL1=vidID;
                Intent in=new Intent(getApplicationContext(),Download.class);
                startActivity(in);
               //OtherURl.processVideo(InstagramActivity.this,vidID);
              //  processVideo(vidID);
            }
        });


        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext(), All_Downloads.class);
                startActivity(Intent);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(Intent);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(getApplicationContext(), MainActivity1.class);
                startActivity(Intent);
            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(InstagramActivity.this);
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
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, "Download this");
                share.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                startActivity(Intent.createChooser(share, "Share to Friends!"));
            }
        });


    }
    public void otherActivity(){



        t1.setVisibility(View.GONE);
        btngo.setVisibility(View.GONE);

        download_link = StaticData.URL; //t1.getText().toString();
        download_link = download_link + "?__a=1";
        try {
            Log.d("Before", download_link);
            vidID = showdata(download_link);
            Log.d("after", vidID);
            progressBar.setVisibility(View.VISIBLE);
            vv.setVisibility(View.VISIBLE);
            vv.setMediaController(mediacontroller);
            vv.setVideoURI(Uri.parse(vidID));
            vv.requestFocus();
            vv.start();
            btndownload.setVisibility(View.VISIBLE);
        }
        catch (Exception e) {
            Log.d("TEXT",e.toString());
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StaticData.URL = null;
        showAdsFull();
    }

    private void showBannerAd() {

        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

    }

    public void alert() {
        new AlertDialog.Builder(InstagramActivity.this)
                .setTitle("Alert Message")
                .setMessage("Please Enter Url of Instagram video")
                .setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
    }
    public String showdata(String URL){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1=new JSONObject(response);
                    String resource = jsonObject1.getString("graphql");
                    JSONObject jsonObject2 = new JSONObject(resource);
                    String resource1 = jsonObject2.getString("shortcode_media");
                    JSONObject jsonObject3 = new JSONObject(resource1);
                    String videoId = jsonObject3.getString("video_url");
                    Log.d("RESOURCE", videoId);
                    SharedPreferences.Editor editor = getSharedPreferences("Check", MODE_PRIVATE).edit();
                    editor.putString("url", videoId);
                    editor.apply();
                } catch (Exception ex) {
                    Log.d("EXCEPTION", ex.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        int sockettimeout=3000;
        RetryPolicy policy=new DefaultRetryPolicy(sockettimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
        SharedPreferences prefs = getSharedPreferences("Check", MODE_PRIVATE);
        String restoredText = prefs.getString("url", null);
        return restoredText;
    }



    public void processVideo(String v)
    {
        try
        {
            String mBaseFolderPath = android.os.Environment
                    .getExternalStorageDirectory()
                    + File.separator
                    + "Speedy Video Downloader" + File.separator;
            if (!new File(mBaseFolderPath).exists())
            {
                new File(mBaseFolderPath).mkdir();
            }
            String mFilePath = "file://" + mBaseFolderPath + "/Video" +  (new Random(10000)).nextInt()+".mp4";
            Uri downloadUri = Uri.parse(v);
            DownloadManager.Request req = new DownloadManager.Request(downloadUri);
            req.setDestinationUri(Uri.parse(mFilePath));
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            DownloadManager dm = (DownloadManager) getSystemService(getApplicationContext().DOWNLOAD_SERVICE);
            dm.enqueue(req);
            Toast.makeText(this, "Download Started", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Download Failed: " , Toast.LENGTH_LONG).show();
        }

    }

}
