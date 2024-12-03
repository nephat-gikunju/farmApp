package com.example.farmapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get references to UI elements
        TextView tvVariety = findViewById(R.id.tvVariety);
        TextView tvPlanted = findViewById(R.id.tvPlanted);
        TextView tvExpectedYield = findViewById(R.id.tvExpectedYield);
        TextView tvLocation = findViewById(R.id.tvLocation);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);

        // Populate the UI with the provided farm produce information
        tvVariety.setText("Variety: Fuji");
        tvPlanted.setText("Planted: 2022-03-15");
        tvExpectedYield.setText("Expected Yield: 500 kg");
        tvLocation.setText("Location: Orchard A");

        // Set up click listeners for the edit and delete buttons
        btnEdit.setOnClickListener(v -> {
            // Handle edit button click
            // Navigate to an "Edit Farm Produce" screen or show a dialog to allow the user to update the information
        });

        btnDelete.setOnClickListener(v -> {
            // Handle delete button click
            // Display a confirmation dialog to the user, and if confirmed, remove the farm produce from the system
        });
    }
}