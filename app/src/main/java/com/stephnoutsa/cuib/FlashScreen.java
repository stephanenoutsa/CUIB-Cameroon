package com.stephnoutsa.cuib;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class FlashScreen extends AppCompatActivity {

    // Duration of wait
    private final long DELAY = 3000;

    TextView schoolName, acronym, schoolMotto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        // Change text font
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");
        schoolName = (TextView) findViewById(R.id.schoolName);
        acronym = (TextView) findViewById(R.id.acronym);
        schoolMotto = (TextView) findViewById(R.id.schoolMotto);
        schoolName.setTypeface(font);
        acronym.setTypeface(font);
        schoolMotto.setTypeface(font);

        /* New Handler to start the Home Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Home Activity. */
                Intent i = new Intent(FlashScreen.this, Home.class);
                FlashScreen.this.startActivity(i);
                FlashScreen.this.finish();
            }
        }, DELAY);
    }
}
