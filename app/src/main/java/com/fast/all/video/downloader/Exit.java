package com.fast.all.video.downloader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.fast.all.video.downloader.StartActivity;
import com.fast.all.video.downloader.R;


public class Exit extends Activity {
    Button bt1, bt2;
    Button store,play_btn;
    TextView playstore, appname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.exit_layout);

       /* NativeExpressAdView adViewNative = (NativeExpressAdView) findViewById(R.id.adViewNative);
        //adViewNative.loadAd(new AdRequest.Builder().build());
        if(adViewNative != null) {
            adViewNative.loadAd(new AdRequest.Builder().build());
        }*/
        //adViewNative.loadAd(new AdRequest.Builder().build());

        AdView mAdView = findViewById(R.id.adView);
        if(mAdView != null) {
            mAdView.loadAd(new AdRequest.Builder().build());
        }

        AdView mAdView2 = findViewById(R.id.adView2);
        if(mAdView2 != null) {
            mAdView2.loadAd(new AdRequest.Builder().build());
        }

        animateButton();
        bt1 = findViewById(R.id.yes_btn);
        bt2 = findViewById(R.id.no_btn);
        play_btn = findViewById(R.id.app_btn);
        store = findViewById(R.id.play_btn);
        playstore = findViewById(R.id.playstorebtn);
        appname = findViewById(R.id.ban_app);



        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  finish();
                Intent i = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(i);
                finish();
            }
        });
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getApplicationContext().getString(R.string.playstore_link))));
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getApplicationContext().getString(R.string.app_name_playstore_link))));
            }
        });
        playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getApplicationContext().getString(R.string.app_name_playstore_link))));
            }
        });
        appname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getApplicationContext().getString(R.string.playstore_link))));
            }
        });
    }

    void animateButton() {
        // Load the animation
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        double animationDuration = 40 * 1000;
        myAnim.setDuration((long)animationDuration);

        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(200, 200);

        myAnim.setInterpolator(interpolator);

        // Animate the button
        Button button = findViewById(R.id.app_btn);
        button.startAnimation(myAnim);
       // playSound();

        // Run button animation again after it finished
        myAnim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {}

            @Override
            public void onAnimationRepeat(Animation arg0) {}

            @Override
            public void onAnimationEnd(Animation arg0) {
                animateButton();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
