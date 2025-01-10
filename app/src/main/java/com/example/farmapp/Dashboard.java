package com.example.farmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //user name and username status

        Intent getusername = getIntent();
        String username = getusername.getStringExtra("USERNAME_KEY");


        TextView UserStatus = findViewById(R.id.user_status);
        UserStatus.setText("Welcome Back, " + username);


        TextView FarmName = findViewById(R.id.farm_name);
        FarmName.setText("{farm_name}" + " Farm");

        //plantation cards

        //image


        //plantation details

        TextView PlantationDetails = findViewById(R.id.plantation_description);
        PlantationDetails.setText("Total Plants:"+"{total} "+ "\nReady:" + "{ready_plants}");



    }

    public void oneditplantationclick (View view){
        Intent StartPlantationEdit = new Intent(Dashboard.this,EditPlantation.class);
        startActivity(StartPlantationEdit);

    }
    public void onplantationclick (View view){
        Intent StartPlantation = new Intent(Dashboard.this,plant_details.class);
        startActivity(StartPlantation);
    }
}