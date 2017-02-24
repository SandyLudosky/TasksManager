package com.example.sandyl.todoapp_materialdesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    interface MyCallback {
        void callbackCall();
    }


    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private final int ANIMATION_DELAY_LENGTH = 1200;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                AnimateZoom();
            }
        }, ANIMATION_DELAY_LENGTH);

    }

    public void AnimateZoom(){
        ImageView image = (ImageView)findViewById(R.id.imageView);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_zoom_in);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_zoom_out);
        image.startAnimation(animation1);
        image.startAnimation(animation2);

    }

}