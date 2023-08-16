package com.example.kampusku.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kampusku.R;

public class DetailStudentActivity extends AppCompatActivity {
    String nimStudent, nameStudent, birthStudent, genderStudent, addressStudent;
    private AutoCompleteTextView jenisKelamin;
    private EditText nim, nama, tglLahir, alamat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_student);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Detail Data Mahasiswa");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_dark)));
        }

        nim = findViewById(R.id.text_nomorNIM);
        nama = findViewById(R.id.text_nama);
        tglLahir = findViewById(R.id.text_tgl_Lahir);
        jenisKelamin = findViewById(R.id.text_jenis_kelamin);
        alamat = findViewById(R.id.text_alamat);

        getAndSetIntentData();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("nim") && getIntent().hasExtra("nama")
                && getIntent().hasExtra("tglLahir") && getIntent().hasExtra("jk")
                && getIntent().hasExtra("alamat")) {
            // Get Data
            nimStudent = getIntent().getStringExtra("nim");
            nameStudent = getIntent().getStringExtra("nama");
            birthStudent = getIntent().getStringExtra("tglLahir");
            genderStudent = getIntent().getStringExtra("jk");
            addressStudent = getIntent().getStringExtra("alamat");

            // Set Data
            nim.setText(nimStudent);
            nama.setText(nameStudent);
            tglLahir.setText(birthStudent);
            jenisKelamin.setText(genderStudent);
            alamat.setText(addressStudent);
        } else {
            Toast.makeText(this, "Tidak Ada Data Mahasiswa", Toast.LENGTH_SHORT).show();
        }
    }
}