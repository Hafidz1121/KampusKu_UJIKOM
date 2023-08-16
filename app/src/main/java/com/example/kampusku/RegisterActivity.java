package com.example.kampusku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kampusku.DatabaseHelper.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    TextView linkLogin;
    EditText name, email, password;
    CheckBox checkBox;
    Button btnRegister;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.text_name);
        email =  findViewById(R.id.text_emailRegister);
        password = findViewById(R.id.text_passwordRegister);
        linkLogin = findViewById(R.id.text_linkLogin);
        checkBox = findViewById(R.id.checkBox);
        btnRegister = findViewById(R.id.btn_register);
        databaseHelper = new DatabaseHelper(this);

        showPassword();
        linkToRegister();
        addDataRegister();
    }

    private void showPassword() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    private void linkToRegister() {
        linkLogin.setOnClickListener(v-> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void addDataRegister() {
        btnRegister.setOnClickListener(v-> {
            if (isValidation()) {
                saveDataRegister();
            } else {
                Toast.makeText(getApplicationContext(), "Mohon Lengkapi Seluruh Data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean isValidation() {
        if (name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void saveDataRegister() {
        String nama = name.getText().toString();
        String emailUser = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        Boolean checkUser = databaseHelper.checkEmail(emailUser);

        if (!checkUser) {
            Boolean insert = databaseHelper.insertDataLogin(nama, emailUser, pass);

            if (insert) {
                Toast.makeText(getApplicationContext(), "Data Registrasi Berhasil Di Simpan !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Data Registrasi Gagal Di Simpan !", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Email Telah Terdaftar, Silahkan Login !", Toast.LENGTH_SHORT).show();
        }
    }
}