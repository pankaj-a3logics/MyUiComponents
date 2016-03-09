package com.pkj.wow.myuicomponents;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private RelativeLayout mRootLayout;
    private ImageView logo;
    private static boolean isAnimationOn;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        mRootLayout = (RelativeLayout) findViewById(R.id.root_layout);
        logo = (ImageView) findViewById(R.id.logo);

        if(isAnimationOn==false) {
            isAnimationOn = true;


            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setStartOffset(100);
            alphaAnimation.setDuration(2500);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setFillEnabled(true);
            logo.startAnimation(alphaAnimation);

            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                    isAnimationOn = false;
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }, SPLASH_TIME_OUT);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
