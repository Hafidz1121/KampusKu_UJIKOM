package com.example.kampusku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kampusku.DatabaseHelper.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    TextView linkRegister;
    EditText email, password;
    CheckBox checkBox;
    Button btnLogin;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.text_emailLogin);
        password = findViewById(R.id.text_passwordLogin);
        linkRegister = findViewById(R.id.text_linkRegister);
        checkBox = findViewById(R.id.checkBox);
        btnLogin = findViewById(R.id.btn_login);
        databaseHelper = new DatabaseHelper(this);

        showPassword();
        linkToRegister();
        processLogin();
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
        linkRegister.setOnClickListener(v-> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    private void processLogin() {
        btnLogin.setOnClickListener(v-> {
            if (isValidation()) {
                checkDataLogin();
            } else {
                Toast.makeText(getApplicationContext(), "Mohon Masukan Email Dan Password !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean isValidation() {
        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void checkDataLogin() {
        String emailUser = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        Boolean checkEmailPassword = databaseHelper.checkEmailPassword(emailUser, pass);

        if (checkEmailPassword) {
            Toast.makeText(getApplicationContext(), "Login Berhasil !", Toast.LENGTH_SHORT).show();

            // Retrieve the user name
            String userName = databaseHelper.getUserName(emailUser);

            if (userName != null) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                // Pass the user's name to MainActivity
                intent.putExtra("USER_NAME", userName);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Email / Password Anda Salah !", Toast.LENGTH_SHORT).show();
        }
    }
}