package com.example.kampusku.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
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

public class AddStudentActivity extends AppCompatActivity {
    private EditText nim, nama, tglLahir, alamat;
    private Button btnSimpan;
    private Calendar calendar;
    private AutoCompleteTextView jenisKelamin;
    private ArrayAdapter<String> jenisKelaminAdapter;
    private String[] jenisKelaminOptions = {"Laki-Laki", "Perempuan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Tambah Data Mahasiswa");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_dark)));
        }

        nim = findViewById(R.id.text_nomorNIM);
        nama = findViewById(R.id.text_nama);
        tglLahir = findViewById(R.id.text_tgl_Lahir);
        jenisKelamin = findViewById(R.id.text_jenis_kelamin);
        alamat = findViewById(R.id.text_alamat);
        btnSimpan = findViewById(R.id.btn_saveData);

        calendar = Calendar.getInstance();
        tglLahir.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        jenisKelaminAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, jenisKelaminOptions);
        jenisKelamin.setAdapter(jenisKelaminAdapter);

        processSaveData();
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

    private void processSaveData() {
        btnSimpan.setOnClickListener(v-> {
            String nimStudent = nim.getText().toString().trim();
            String nameStudent = nama.getText().toString();
            String birthStudent = tglLahir.getText().toString();
            String genderStudent = jenisKelamin.getText().toString();
            String addressStudent = alamat.getText().toString();

            try {
                DatabaseHelper databaseHelper = new DatabaseHelper(AddStudentActivity.this);
                databaseHelper.addStudent(nimStudent, nameStudent, birthStudent, genderStudent, addressStudent);

                // Set the result and finish the activity
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);

                finish();
                clearInput();
            } catch (SQLiteException ex) {
                ex.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error : " + ex, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearInput() {
        nim.setText("");
        nama.setText("");
        tglLahir.setText("");
        jenisKelamin.setText("");
        alamat.setText("");
    }
}