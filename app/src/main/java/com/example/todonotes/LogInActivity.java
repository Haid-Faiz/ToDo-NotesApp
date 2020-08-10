package com.example.todonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LogInActivity extends AppCompatActivity {

    TextInputLayout Email, Pass;
    Button loginButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        bindView();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Email.getEditText().getText().toString();
                String pass = Pass.getEditText().getText().toString();

                if ( !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {

                    Intent intent = new Intent(LogInActivity.this, ToDoActivity.class);
                    startActivity(intent);
                    // saving session or login state
                    saveLogin();
                }else
                    Toast.makeText(LogInActivity.this, "Fiels can't be empty", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void saveLogin() {
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.SharedPref), MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", true);
        editor.apply();
    }

    private void bindView() {
        Email = findViewById(R.id.textInputLayout);
        Pass = findViewById(R.id.textInputLayout2);
        loginButton = findViewById(R.id.buttonID);
    }
}