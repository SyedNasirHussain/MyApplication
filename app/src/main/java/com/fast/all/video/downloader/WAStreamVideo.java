package com.fast.all.video.downloader;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.fast.all.video.downloader.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class WAStreamVideo extends AppCompatActivity {
    Button Save,Share;
    ProgressDialog pDialog;
  //  AdView mAdView;
    VideoView videoview;
    String VideoURL,CurrentFile,FileName;
    private static final String TAG_VIDURL = "video_url";
    private static String DIRECTORY_TO_SAVE_MEDIA_NOW = "/storage/emulated/0/Speedy Video Downloader/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wastream_video);
       // mAdView = (AdView) findViewById(R.id.adView);
        //inializeFooter();

        if (isOnline()) {
            showBannerAd();
        } else {
            // mAdView.setVisibility(View.GONE);
           // mAdView.setVisibility(View.GONE);

        }

        Save= findViewById(R.id.Save);
        Share= findViewById(R.id.Share);

        Intent i = getIntent();
        VideoURL = i.getStringExtra(TAG_VIDURL);
        CurrentFile=i.getStringExtra("CurrentFile");
        FileName=i.getStringExtra("FileName");
        videoview = findViewById(R.id.streaming_video_laner_rla);
        pDialog = new ProgressDialog(WAStreamVideo.this);
        pDialog.setTitle("Video Stream");
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();



        try {
            MediaController mediacontroller = new MediaController(
                    WAStreamVideo.this);
            mediacontroller.setAnchorView(videoview);
            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    copyFile(new File(CurrentFile), new File( DIRECTORY_TO_SAVE_MEDIA_NOW+FileName),WAStreamVideo.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
               alert();


            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("video/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(CurrentFile)));
               startActivity(Intent.createChooser(sharingIntent, "Share to"));

            }
        });
    }
    public void alert() {
        new AlertDialog.Builder(WAStreamVideo.this)
                .setTitle("Alert Message")
                .setMessage("File Saved Successfully")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               dialog.cancel();
                            }
                        }).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    public void copyFile(File sourceFile, File destFile, Activity activity) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }


        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(destFile);
            mediaScanIntent.setData(contentUri);
            activity.sendBroadcast(mediaScanIntent);

        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showBannerAd() {

        //mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
       // mAdView.loadAd(adRequest);

    }
}
