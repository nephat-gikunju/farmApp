package com.example.farmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DatabaseHelper
        dbhelper = new DatabaseHelper(this);

        // Optional: Handle window insets
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }

    // Login Button Click Handler
    public void onloginclick(View v) {
        EditText usernameInputedittext = findViewById(R.id.usernameedittext);
        EditText passwordInputedittext = findViewById(R.id.passwordedittext);

        String usernameinput = usernameInputedittext.getText().toString().trim();
        String passwordinput = passwordInputedittext.getText().toString().trim();

        // Validate input fields
        if (usernameinput.isEmpty() || passwordinput.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check user credentials
        boolean isvalid = dbhelper.validateUser(usernameinput, passwordinput);
        if (isvalid) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Dashboard.class); // Replace with your actual dashboard activity
            intent.putExtra("USERNAME", usernameinput);

            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    // Register Button Click Handler
    public void OnRegisterClick(View v) {
        Intent registerlauncher = new Intent(MainActivity.this, Register.class);
        startActivity(registerlauncher);
    }
}
