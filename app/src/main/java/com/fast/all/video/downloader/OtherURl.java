package com.fast.all.video.downloader;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.Random;

public class OtherURl extends AppCompatActivity {
    MainActivity1 obj;
    Button btnplay;
    EditText t1;
    private Button  btndownload,b7,b8,b9,b10,b11;
    public String vidID;
    private VideoView vv;
    private MediaController mediacontroller;
    private Uri uri;
    private boolean isContinuously = false;
    private ProgressBar progressBar;
    InterstitialAd mInterstitialAd;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_url);
        obj=new MainActivity1();
        btnplay = findViewById(R.id.button);
        t1 = findViewById(R.id.editText);
        progressBar = findViewById(R.id.progrss);
        btndownload = findViewById(R.id.button5);
        b7= findViewById(R.id.button7);
        b8= findViewById(R.id.button8);
        b9= findViewById(R.id.button9);
        b10= findViewById(R.id.button10);
        b11= findViewById(R.id.button11);

        vv = findViewById(R.id.videoView);
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


        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);
        mediacontroller.setMediaPlayer(vv);
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (t1.getText().toString().isEmpty())
                    alert();
                else if(t1.getText().toString().contains("facebook")) {
                    StaticData.URL = t1.getText().toString();
                    Intent intent=new Intent(getApplicationContext(),OtherFB.class);
                    startActivity(intent);
                }
                else if(t1.getText().toString().contains("instagram")) {
                  //  Toast.makeText(getApplicationContext(),"Incompatible Link",Toast.LENGTH_SHORT).show();
                    StaticData.URL = t1.getText().toString();
                    Intent intent=new Intent(getApplicationContext(),InstagramActivity.class);
                    startActivity(intent);
                }
                else if(t1.getText().toString().contains("youtube.com")||(t1.getText().toString().contains("youtu"))) {
                Toast.makeText(getApplicationContext(),"Incompatible Link",Toast.LENGTH_SHORT).show();
                }
                    else{
                    vidID = t1.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    vv.setVisibility(View.VISIBLE);
                    vv.setMediaController(mediacontroller);
                    vv.setVideoURI(Uri.parse(vidID));
                    vv.requestFocus();
                    vv.start();
                    btndownload.setVisibility(View.VISIBLE);

                }
                }
        });


        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t1.getText().toString().isEmpty())
                {   // Toast.makeText(getApplicationContext(),"Please Enter Url of video",Toast.LENGTH_LONG).show();
                alert();
                }
                StaticData.URL1=vidID;
                Intent in=new Intent(getApplicationContext(),Download.class);
                startActivity(in);
                //obj.processVideo(OtherURl.this,vidID);
                //processVideo(OtherURl.this, vidID);
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
                AlertDialog.Builder ad = new AlertDialog.Builder(OtherURl.this);
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


        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });

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
        showAdsFull();
    }

    private void showBannerAd() {

        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

    }


    public static void processVideo(Context context, String v) {

        try {
            String mBaseFolderPath = android.os.Environment
                    .getExternalStorageDirectory()
                    + File.separator
                    + "Speedy Video Downloader" + File.separator;
            if (!new File(mBaseFolderPath).exists()) {
                new File(mBaseFolderPath).mkdir();
            }

            String mFilePath = "file://" + mBaseFolderPath + "/Video" + (new Random(10000)).nextInt() + ".mp4";
            Uri downloadUri = Uri.parse(v);
            DownloadManager.Request req = new DownloadManager.Request(downloadUri);
            req.setDestinationUri(Uri.parse(mFilePath));
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(req);
            Toast.makeText(context, "Download Started", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "Download Failed: " , Toast.LENGTH_LONG).show();
        }
    }
        public void alert() {
            new AlertDialog.Builder(OtherURl.this)
                    .setTitle("Alert Message")
                    .setMessage("Please Enter Url of video")
                    .setPositiveButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Toast.makeText(getApplicationContext(),"Thank you! You're awesome too!",Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            }).show();
        }
    }


