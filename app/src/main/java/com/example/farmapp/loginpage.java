package com.example.farmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class loginpage extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin ,btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginpage);

        // Initialize UI elements
        etUsername = findViewById(R.id.Username);
        etPassword = findViewById(R.id.Password);
        btnLogin = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnReg);

        // Set click listener for the login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered username and password
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Perform login validation and authentication
                if (username.equals("admin") && password.equals("password")) {
                    // Login successful, navigate to the next screen
                    startActivity(new Intent(loginpage.this, DashboardActivity.class));
                } else {
                    // Login failed, show an error message
                    Toast.makeText(loginpage.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginpage.this, Register.class));
            }
        });
    }
    }
