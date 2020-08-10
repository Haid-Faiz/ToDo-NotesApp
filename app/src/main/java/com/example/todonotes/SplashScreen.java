package com.example.todonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final int delayTime = 2300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.SharedPref), MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sharedPreferences.getBoolean("isLogin", false))
                    startActivity(new Intent(SplashScreen.this, ToDoActivity.class));
                else
                    startActivity(new Intent(SplashScreen.this, LogInActivity.class));

                finish();

            }
        }, delayTime);
    }
}