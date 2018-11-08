package com.motiolabs.android.myfast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.motiolabs.android.myfast.utils.Constants;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    SharedPreferences pref;
    String sess_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref = getSharedPreferences(Constants.fast_shared, Context.MODE_PRIVATE);
        sess_uid = pref.getString(Constants.spref_ouid, "");
        if (sess_uid.equals("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }


    }
}
