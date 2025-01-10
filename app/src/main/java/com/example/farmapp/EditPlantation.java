package com.example.farmapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditPlantation extends AppCompatActivity {

    private EditText plantationNameEditText;
    private EditText totalPlantsEditText;
    private EditText ripePlantsEditText;
    private EditText sicklyPlantsEditText;
    private EditText plantYearEditText;
    private EditText locationEditText;
    private EditText farmRowLayoutEditText;
    private EditText farmColumnLayoutEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_plantation);

        // Apply padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_plantation_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize EditText fields
        plantationNameEditText = findViewById(R.id.plantation_name_edittext);
        totalPlantsEditText = findViewById(R.id.total_plants_edittext);
        ripePlantsEditText = findViewById(R.id.ripe_plants_edittext);
        sicklyPlantsEditText = findViewById(R.id.sickly_plants_edittext);
        plantYearEditText = findViewById(R.id.plant_year_edittext);
        locationEditText = findViewById(R.id.location_edittext);
        farmRowLayoutEditText = findViewById(R.id.farm_row_layout_edittext);
        farmColumnLayoutEditText = findViewById(R.id.farm_column_layout_edittext);
    }

    // Method to handle the Save Changes button click
    public void onSavePlantationClick(View view) {
        // Retrieve data from EditText fields
        String plantationName = plantationNameEditText.getText().toString().trim();
        String totalPlants = totalPlantsEditText.getText().toString().trim();
        String ripePlants = ripePlantsEditText.getText().toString().trim();
        String sicklyPlants = sicklyPlantsEditText.getText().toString().trim();
        String plantYear = plantYearEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();
        String farmRowLayout = farmRowLayoutEditText.getText().toString().trim();
        String farmColumnLayout = farmColumnLayoutEditText.getText().toString().trim();

        // Validate inputs
        if (plantationName.isEmpty() || totalPlants.isEmpty() || ripePlants.isEmpty() ||
                sicklyPlants.isEmpty() || plantYear.isEmpty() || location.isEmpty() ||
                farmRowLayout.isEmpty() || farmColumnLayout.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save changes (you can replace this with database or API integration logic)
        Toast.makeText(this, "Plantation details updated successfully!", Toast.LENGTH_SHORT).show();

        // You can add logic here to navigate back or update other parts of the app
    }
}
