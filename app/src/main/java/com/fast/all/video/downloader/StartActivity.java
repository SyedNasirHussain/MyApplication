package com.fast.all.video.downloader;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    Button b1,b2,b3,b4,b7,b8,b9,b10,b11;
    private boolean networkCheck = false;
    public Context context;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        b1= findViewById(R.id.button2);
        b2= findViewById(R.id.button3);
        b3= findViewById(R.id.button4);
        b4= findViewById(R.id.button6);
        b7= findViewById(R.id.button7);
        b8= findViewById(R.id.button8);
        b9= findViewById(R.id.button9);
        b10= findViewById(R.id.button10);
        b11= findViewById(R.id.button11);
        String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String readExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE;
        int hasWriteExternalStoragePermission = ActivityCompat.checkSelfPermission(this, writeExternalStorage);
        int hasReadExternalStoragePermission = ActivityCompat.checkSelfPermission(this, readExternalStorage);
        if(hasWriteExternalStoragePermission!=PackageManager.PERMISSION_GRANTED||hasReadExternalStoragePermission!=PackageManager.PERMISSION_GRANTED){
            getPermission();
        }

        mAdView = findViewById(R.id.adView);
        //inializeFooter();

        if (isOnline()) {
            showBannerAd();
        } else {
            // mAdView.setVisibility(View.GONE);
            mAdView.setVisibility(View.GONE);

        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    Intent Intent = new Intent(getApplicationContext(), FaceBookActivity.class);
                    getIntent().putExtra("Url","None");
                    startActivity(Intent);
                }
                else
                {
                    showConnectivityErrorDialog();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    Intent Intent = new Intent(getApplicationContext(), InstagramActivity.class);
                    startActivity(Intent);
                } else {
                    showConnectivityErrorDialog();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    Intent Intent = new Intent(getApplicationContext(), OtherURl.class);
                    startActivity(Intent);
                }
                else {
                showConnectivityErrorDialog();
                }
                }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(isOnline()) {
                    Intent Intent = new Intent(getApplicationContext(), WhatsApp_Activity.class);
                    startActivity(Intent);
               // }
                //else {
                   // showConnectivityErrorDialog();
               // }
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
                AlertDialog.Builder ad = new AlertDialog.Builder(StartActivity.this);
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
        protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showBannerAd() {

        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

    }
    private void getPermission() {
        String[] params = null;
        String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String readExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE;
        int hasWriteExternalStoragePermission = ActivityCompat.checkSelfPermission(this, writeExternalStorage);
        int hasReadExternalStoragePermission = ActivityCompat.checkSelfPermission(this, readExternalStorage);
        List<String> permissions = new ArrayList<String>();
        if (hasWriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
            permissions.add(writeExternalStorage);
        if (hasReadExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
            permissions.add(readExternalStorage);
        if (!permissions.isEmpty()) {
            params = permissions.toArray(new String[permissions.size()]);
        }
        if (params != null && params.length > 0) {
            ActivityCompat.requestPermissions(StartActivity.this,
                    params,
                    100);
        }
        else
        {
            //startActivity(new Intent(this, FadeEdit.class));
           // finish();
        }
    }
    public void showConnectivityErrorDialog(  ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        builder.setCancelable(true);
        builder.setIcon(null);
        builder.setTitle(null);
        builder.setMessage("YOU MUST ENABLE YOUR DATA CONNECTION (WIFI or 3G)");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent settingPage = new Intent(
                                android.provider.Settings.ACTION_SETTINGS);
                        startActivityForResult(settingPage, 0);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
       finish();
    }
}
