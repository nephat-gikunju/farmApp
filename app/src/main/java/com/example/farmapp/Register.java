    package com.example.farmapp;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    public class Register extends AppCompatActivity {
        private DatabaseHelper dbhelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_register);


            dbhelper = new DatabaseHelper(this);



        }
        public void OnRegisterCLick(View view){
            EditText emailedittext = findViewById(R.id.emailedittext);
            EditText namesedittext = findViewById(R.id.Usernameedittext);
            EditText passwordedittext = findViewById(R.id.passwordedittext);
            EditText farmnameedittext = findViewById(R.id.farmNameedittext);


            String email =emailedittext.getText().toString().trim();
            String username=namesedittext.getText().toString().trim();
            String password =passwordedittext.getText().toString().trim();
            String farmname =farmnameedittext.getText().toString().trim();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || farmname.isEmpty()){
                Toast.makeText(this, "please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean userexists = dbhelper.checkUserExists(username);
            if (userexists){
                Toast.makeText(this, "Username Exists", Toast.LENGTH_SHORT).show();
            }
            boolean isInserted = dbhelper.insertUser(username, email, password, farmname);
            if (isInserted){
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this, "Registration Failed try Again", Toast.LENGTH_SHORT).show();
            }



        }
    }