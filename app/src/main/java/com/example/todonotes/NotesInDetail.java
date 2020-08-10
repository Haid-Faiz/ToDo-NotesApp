package com.example.todonotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class NotesInDetail extends AppCompatActivity {

    private TextView title, desc;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_in_detail);
        bindView();
        setUpIntent();
    }

    private void setUpIntent() {

        Intent intent = getIntent();
        String str1 = intent.getStringExtra("title");
        String str2 = intent.getStringExtra("description");

        title.setText(str1);
        desc.setText(str2);
    }

    private void bindView() {

        title = findViewById(R.id.textView2);
        desc = findViewById(R.id.textView3);
    }

}