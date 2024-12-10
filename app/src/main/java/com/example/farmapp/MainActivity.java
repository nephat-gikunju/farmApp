package com.example.farmapp;

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

        EditText emailInputedittext = findViewById(R.id.emailedittext);
        EditText passwordInputedittext = findViewById(R.id.passwordedittext);
        String emailinput = emailInputedittext.getText().toString().trim();
        String passwordinput =passwordInputedittext.getText().toString().trim();


        }
    public void onloginclick (View v){
        Toast.makeText(this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();


    }
}

