 package com.example.todonotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import Adapter.NotesAdapter;
import Adapter.NotesData;
import MyInterface.CustomViewClickListener;

 public class ToDoActivity extends AppCompatActivity {

    private FloatingActionButton floatButton;
    private Button LogoutButton;
    private RecyclerView recyclerViewNotes;
    private List<NotesData> list = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;



     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

       // getSupportActionBar().setTitle("My Notes");
        bindView();
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.SharedPref), MODE_PRIVATE);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpDialog();
            }
        });

    }

     private void bindView() {
         floatButton = findViewById(R.id.floatButtonID);
         recyclerViewNotes = findViewById(R.id.recycleID);
         LogoutButton = findViewById(R.id.logoutButtonID);

     }

     private void setUpDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null, false);

         final TextInputLayout Title = view.findViewById(R.id.textInputLayID1);
         final TextInputLayout Description = view.findViewById(R.id.textInputLayID2);
         Button saveButton = view.findViewById(R.id.saveButtonID);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Title.getEditText().getText().toString();
                String description = Description.getEditText().getText().toString();

                if ( !TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)){
                NotesData data = new NotesData(title, description);
                list.add(data);

                setUpAdapter();
                } else {
                    Toast.makeText(ToDoActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
                }

                alertDialog.hide();
            }
        });
     }

     private void setUpAdapter() {

         CustomViewClickListener customViewClickListener = new CustomViewClickListener() {
             @Override
             public void onClick(NotesData notesData) {

                 Intent intent = new Intent(ToDoActivity.this, NotesInDetail.class);
                 intent.putExtra("title", notesData.getTitle());
                 intent.putExtra("description", notesData.getDescription());
                 startActivity(intent);
             }
         };

         NotesAdapter notesAdapter = new NotesAdapter(list, customViewClickListener);
         LinearLayoutManager layoutManager = new LinearLayoutManager(ToDoActivity.this);
         layoutManager.setOrientation(RecyclerView.VERTICAL);

         recyclerViewNotes.setLayoutManager(layoutManager);
         recyclerViewNotes.setAdapter(notesAdapter);

     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {

         getMenuInflater().inflate(R.menu.logout_menu, menu);
         return super.onCreateOptionsMenu(menu);
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {

         if (item.getItemId() == R.id.logoutButtonID){
             startActivity(new Intent(ToDoActivity.this, LogInActivity.class));
             editor = sharedPreferences.edit();
             editor.putBoolean("isLogin", false);
             editor.apply();
             finish();
         }
         return super.onOptionsItemSelected(item);
     }

 }