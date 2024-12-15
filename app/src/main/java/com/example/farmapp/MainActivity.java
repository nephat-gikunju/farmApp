package com.example.farmapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }



        }
    public void onloginclick (View v){
        EditText emailInputedittext = findViewById(R.id.emailedittext);
        EditText passwordInputedittext = findViewById(R.id.passwordedittext);


        String emailinput = emailInputedittext.getText().toString().trim();
        String passwordinput =passwordInputedittext.getText().toString().trim();

        if (emailinput.equals("admin@gmail.com") && passwordinput.equals("admin")){
            Intent dashboardlauncher = new Intent(MainActivity.this, Dashboard.class);
            startActivity(dashboardlauncher);
        }else {
            Toast.makeText(this, "LOGIN UNSUCCESSFUL", Toast.LENGTH_SHORT).show();


        }





    }

    public void OnRegisterClick (View v){
        Intent registerlauncher = new Intent(MainActivity.this, Register.class);
        startActivity(registerlauncher);
    }
}

