package com.example.kampusku.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kampusku.DatabaseHelper.DatabaseHelper;
import com.example.kampusku.R;
import com.example.kampusku.adapter.CustomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StudentsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fabAdd;
    DatabaseHelper databaseHelper;
    ArrayList<String> nim, nama, tglLahir, jk, alamat;
    CustomAdapter customAdapter;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Data Mahasiswa");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_dark)));
        }

        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.add_button);
        imageView = findViewById(R.id.icon_empty);
        textView = findViewById(R.id.text_noData);

        addStudent();

        databaseHelper = new DatabaseHelper(StudentsActivity.this);
        nim = new ArrayList<>();
        nama = new ArrayList<>();
        tglLahir = new ArrayList<>();
        jk = new ArrayList<>();
        alamat = new ArrayList<>();

        storeDataInArrayList();

        customAdapter = new CustomAdapter(StudentsActivity.this, this, nim, nama, tglLahir, jk, alamat);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentsActivity.this));
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                updateStudentList();
            }
        }
    }

    private void updateStudentList() {
        storeDataInArrayList();

        customAdapter.notifyDataSetChanged();

        if (nim.isEmpty()) {
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }

    private void addStudent() {
        fabAdd.setOnClickListener(v-> {
            Intent intent = new Intent(this, AddStudentActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    private void storeDataInArrayList() {
        nim.clear();
        nama.clear();
        tglLahir.clear();
        jk.clear();
        alamat.clear();

        Cursor cursor = databaseHelper.readAllData();

        if (cursor.getCount() == 0) {
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                nim.add(cursor.getString(0));
                nama.add(cursor.getString(1));
                tglLahir.add(cursor.getString(2));
                jk.add(cursor.getString(3));
                alamat.add(cursor.getString(4));
            }

            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }
}