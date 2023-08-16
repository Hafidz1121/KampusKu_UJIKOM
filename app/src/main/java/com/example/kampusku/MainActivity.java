 package com.example.kampusku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kampusku.menu.AddStudentActivity;
import com.example.kampusku.menu.InformationActivity;
import com.example.kampusku.menu.StudentsActivity;

 public class MainActivity extends AppCompatActivity {
    TextView userLogin;
    CardView addData, seeData, infoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve users name from the intent
        String userName = getIntent().getStringExtra("USER_NAME");

        // Extract the first name
        String firstName = extractFirstName(userName);

        // Display the welcome message
        userLogin = findViewById(R.id.text_NamaUser);
        userLogin.setText(firstName + " !");

        addData = findViewById(R.id.btn_tambahData);
        seeData = findViewById(R.id.btn_lihatData);
        infoApp = findViewById(R.id.btn_informasiAplikasi);

        addStudent();
        seeStudent();
        infoAplication();
    }

     private String extractFirstName(String fullName) {
         // Split the full name by space
         String[] parts = fullName.split(" ");

         if (parts.length > 0) {
             // Return the first part (the first name)
             return parts[0];
         } else {
             // Return the full name if splitting fails (e.g., no space)
             return fullName;
         }
     }

     private void infoAplication() {
        infoApp.setOnClickListener(v-> {
            startActivity(new Intent(MainActivity.this, InformationActivity.class));
        });
     }

     private void addStudent() {
        addData.setOnClickListener(v-> {
            startActivity(new Intent(MainActivity.this, AddStudentActivity.class));
        });
     }

     private void seeStudent() {
        seeData.setOnClickListener(v-> {
            startActivity(new Intent(MainActivity.this, StudentsActivity.class));
        });
     }
}
