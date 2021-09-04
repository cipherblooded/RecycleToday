package com.appintuitions.recycletoday.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.appintuitions.recycletoday.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tv_title = findViewById(R.id.title);

        final Typeface pizza_title_font = Typeface.createFromAsset(getAssets(), "ComicSansMS3.ttf");
        tv_title.setTypeface(pizza_title_font);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            //Toast.makeText(this, "App Version " + version, Toast.LENGTH_LONG).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, SignIn.class);
                startActivity(i);
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
