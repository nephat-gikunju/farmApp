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
        EditText usernameInputedittext = findViewById(R.id.usernameedittext);
        EditText passwordInputedittext = findViewById(R.id.passwordedittext);




        String usernameinput = usernameInputedittext.getText().toString().trim();
        String passwordinput =passwordInputedittext.getText().toString().trim();




        if (usernameinput.equals("admin") && passwordinput.equals("admin")){


            Intent usernameparser = new Intent(MainActivity.this,Dashboard.class);
            usernameparser.putExtra("USERNAME_KEY",usernameinput);
            startActivity(usernameparser);


        }else {
            Toast.makeText(this, "LOGIN UNSUCCESSFUL", Toast.LENGTH_SHORT).show();


        }





    }

    public void OnRegisterClick (View v){
        Intent registerlauncher = new Intent(MainActivity.this, Register.class);
        startActivity(registerlauncher);
    }
}

