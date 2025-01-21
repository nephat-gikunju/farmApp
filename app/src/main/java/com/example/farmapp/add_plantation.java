package com.example.farmapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class add_plantation extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private DatabaseHelper dbhelper;

    private EditText plantationNameEditText;
    private EditText totalPlantsEditText;
    private EditText ripePlantsEditText;
    private EditText sicklyPlantsEditText;
    private EditText plantYearEditText;
    private EditText locationEditText;
    private EditText farmRowLayoutEditText;
    private EditText farmColumnLayoutEditText;

    private ImageView plantationImageView;
    private Uri selectedImageUri; // To store the URI of the selected image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_plantation);
        View rootView = findViewById(R.id.edit_plantation_layout);



        dbhelper = new DatabaseHelper(this);

        plantationNameEditText = findViewById(R.id.plantation_name_edittext);
        totalPlantsEditText = findViewById(R.id.total_plants_edittext);
        ripePlantsEditText = findViewById(R.id.ripe_plants_edittext);
        sicklyPlantsEditText = findViewById(R.id.sickly_plants_edittext);
        plantYearEditText = findViewById(R.id.plant_year_edittext);
        locationEditText = findViewById(R.id.location_edittext);
        farmRowLayoutEditText = findViewById(R.id.farm_row_layout_edittext);
        farmColumnLayoutEditText = findViewById(R.id.farm_column_layout_edittext);

        plantationImageView = findViewById(R.id.plantation_image_view);
        Button pickImageButton = findViewById(R.id.pick_image_button);

        // Set click listener for the image picker button
        pickImageButton.setOnClickListener(v -> openFilePicker());
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); // Allow only image files
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Plantation Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            plantationImageView.setImageURI(selectedImageUri); // Display the selected image
        }
    }

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
                farmRowLayout.isEmpty() || farmColumnLayout.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "Please fill out all fields and select an image.", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean ifplantationexists = dbhelper.checkplantationExists(plantationName);
        if (ifplantationexists){
            Toast.makeText(this, "plantation name exists", Toast.LENGTH_SHORT).show();
        }

        // Convert data types where necessary
        int totalPlantsInt = Integer.parseInt(totalPlants);
        int ripePlantsInt = Integer.parseInt(ripePlants);
        int sicklyPlantsInt = Integer.parseInt(sicklyPlants);
        int farmRowLayoutInt = Integer.parseInt(farmRowLayout);
        int farmColumnLayoutInt = Integer.parseInt(farmColumnLayout);

        // Save plantation to the database
        boolean isInserted = dbhelper.addPlantation(
                plantationName,
                selectedImageUri.toString(), // Store the URI as a string
                totalPlantsInt,
                ripePlantsInt,
                sicklyPlantsInt,
                plantYear,
                location,
                farmRowLayoutInt,
                farmColumnLayoutInt
        );

        if (isInserted) {
            Toast.makeText(this, "Plantation added successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        } else {
            Toast.makeText(this, "Failed to add plantation.", Toast.LENGTH_SHORT).show();
        }
    }
}
