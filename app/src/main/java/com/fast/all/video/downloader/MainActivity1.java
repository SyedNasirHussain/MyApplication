package com.fast.all.video.downloader;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Random;
public class MainActivity1 extends AppCompatActivity {
    //String urlDownload = "http://techslides.com/demos/sample-videos/small.mp4";
     static ListView lView;
     Button b7,b8,b9,b10,b11;
    String p;
    int i;
    long download=0;
    int ds;
    int ts;
    TextView T1;
    static Main2Activity ra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        lView = findViewById(R.id.androidList);
        b7= findViewById(R.id.button7);
        b8= findViewById(R.id.button8);
        b9= findViewById(R.id.button9);
        b10= findViewById(R.id.button10);
        b11= findViewById(R.id.button11);
        T1= findViewById(R.id.text);
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
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity1.this);
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

        if(!StaticData.downloads)
        {
            T1.setVisibility(View.VISIBLE);
        }
        }


    public  void processVideo(final Context context, String v) {
        try {
            StaticData.downloads = true;
            String mBaseFolderPath = Environment
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
            final DownloadManager manager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            final long downloadId = manager.enqueue(req);
            new Thread(new Runnable() {

                @Override
                public void run() {

                    boolean downloading = true;

                    while (downloading) {

                        DownloadManager.Query q = new DownloadManager.Query();
                        q.setFilterById(downloadId);

                        Cursor cursor = manager.query(q);
                        cursor.moveToFirst();
                        final int bytes_downloaded = cursor.getInt(cursor
                                .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        final int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            downloading = false;
                        }runOnUiThread(new Runnable() {



                            public void run()
                            {
                        try {

                            ds = bytes_downloaded;
                            ts = bytes_total;
                            Log.d("Downloadbytes",""+ds);
                            p= "Video-"+(new Random(10000)).nextInt() + ".mp4";
                            ra = new Main2Activity(context, ds, ts,p);
                            lView.setAdapter(ra);
                            ra.notifyDataSetChanged();
                        }
                        catch (Exception e)
                        {
                            Log.d("Fieldsss",e.toString());
                        }
                            }
                        });
                        cursor.close();
                    }

                }
            }).start();

            Toast.makeText(context, "Download Started", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "Download Failed: " , Toast.LENGTH_LONG).show();
        }
    }

}
