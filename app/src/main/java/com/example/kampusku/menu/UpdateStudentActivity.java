package com.example.kampusku.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kampusku.DatabaseHelper.DatabaseHelper;
import com.example.kampusku.R;

import java.util.Calendar;

public class UpdateStudentActivity extends AppCompatActivity {
    String nimStudent, nameStudent, birthStudent, genderStudent, addressStudent;
    private AutoCompleteTextView jenisKelamin;
    private EditText nim, nama, tglLahir, alamat;
    private Button btnUpdate;
    private Calendar calendar;
    private ArrayAdapter<String> jenisKelaminAdapter;
    private String[] jenisKelaminOptions = {"Laki-Laki", "Perempuan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Edit Data Mahasiswa");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_dark)));
        }

        nim = findViewById(R.id.text_nomorNIM);
        nama = findViewById(R.id.text_nama);
        tglLahir = findViewById(R.id.text_tgl_Lahir);
        jenisKelamin = findViewById(R.id.text_jenis_kelamin);
        alamat = findViewById(R.id.text_alamat);
        btnUpdate = findViewById(R.id.btn_saveData);

        calendar = Calendar.getInstance();
        tglLahir.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        jenisKelaminAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, jenisKelaminOptions);
        jenisKelamin.setAdapter(jenisKelaminAdapter);
        // Disable filtering
        jenisKelamin.setThreshold(Integer.MAX_VALUE);

        getAndSetIntentData();
        processUpdateData();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    // Set the selected date in the EditText
                    String selectedDate = dayOfMonth1 + "/" + (monthOfYear + 1) + "/" + year1;
                    tglLahir.setText(selectedDate);
                },
                year, month, dayOfMonth
        );
        datePickerDialog.show();
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

    private void processUpdateData() {
        btnUpdate.setOnClickListener(v-> {
            DatabaseHelper databaseHelper = new DatabaseHelper(UpdateStudentActivity.this);

            nameStudent = nama.getText().toString();
            birthStudent = tglLahir.getText().toString();
            genderStudent = jenisKelamin.getText().toString();
            addressStudent = alamat.getText().toString();

            databaseHelper.updateStudent(nimStudent, nameStudent, birthStudent, genderStudent, addressStudent);

            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}