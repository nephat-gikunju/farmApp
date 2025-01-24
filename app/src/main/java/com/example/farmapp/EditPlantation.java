package com.example.farmapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    private DatabaseHelper dbHelper;
    private String plantationName;  // To identify plantation to update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_plantation);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_plantation_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Initialize EditText fields
        plantationNameEditText = findViewById(R.id.plantation_name_edittext);
        totalPlantsEditText = findViewById(R.id.total_plants_edittext);
        ripePlantsEditText = findViewById(R.id.ripe_plants_edittext);
        sicklyPlantsEditText = findViewById(R.id.sickly_plants_edittext);
        plantYearEditText = findViewById(R.id.plant_year_edittext);
        locationEditText = findViewById(R.id.location_edittext);
        farmRowLayoutEditText = findViewById(R.id.farm_row_layout_edittext);
        farmColumnLayoutEditText = findViewById(R.id.farm_column_layout_edittext);

        // Get plantation name from intent
        plantationName = getIntent().getStringExtra("PLANTATION_NAME");

        // Load plantation details into form
        loadPlantationDetails();
    }

    private void loadPlantationDetails() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query plantation details based on plantation name
        Cursor cursor = db.query("Plantations",
                null,
                "PLANTATION_NAME = ?",
                new String[]{plantationName},
                null, null, null);

        if (cursor.moveToFirst()) {
            plantationNameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("PLANTATION_NAME")));
            totalPlantsEditText.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("TOTAL_PLANTS"))));
            ripePlantsEditText.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("RIPE_PLANTS"))));
            sicklyPlantsEditText.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("SICKLY_PLANTS"))));
            plantYearEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("PLANT_YEAR")));
            locationEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("LOCATION")));
            farmRowLayoutEditText.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("FARM_LAYOUT_ROWS"))));
            farmColumnLayoutEditText.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("FARM_LAYOUT_COLUMNS"))));
        }

        cursor.close();
        db.close();
    }

    // Method to handle the Save Changes button click
    public void onSavePlantationClick(View view) {
        // Retrieve data from EditText fields
        String newPlantationName = plantationNameEditText.getText().toString().trim();
        String totalPlants = totalPlantsEditText.getText().toString().trim();
        String ripePlants = ripePlantsEditText.getText().toString().trim();
        String sicklyPlants = sicklyPlantsEditText.getText().toString().trim();
        String plantYear = plantYearEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();
        String farmRowLayout = farmRowLayoutEditText.getText().toString().trim();
        String farmColumnLayout = farmColumnLayoutEditText.getText().toString().trim();

        // Validate inputs
        if (newPlantationName.isEmpty() || totalPlants.isEmpty() || ripePlants.isEmpty() ||
                sicklyPlants.isEmpty() || plantYear.isEmpty() || location.isEmpty() ||
                farmRowLayout.isEmpty() || farmColumnLayout.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update database with new values
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PLANTATION_NAME", newPlantationName);
        values.put("TOTAL_PLANTS", Integer.parseInt(totalPlants));
        values.put("RIPE_PLANTS", Integer.parseInt(ripePlants));
        values.put("SICKLY_PLANTS", Integer.parseInt(sicklyPlants));
        values.put("PLANT_YEAR", plantYear);
        values.put("LOCATION", location);
        values.put("FARM_LAYOUT_ROWS", Integer.parseInt(farmRowLayout));
        values.put("FARM_LAYOUT_COLUMNS", Integer.parseInt(farmColumnLayout));

        int rowsUpdated = db.update("Plantations", values, "PLANTATION_NAME = ?", new String[]{plantationName});

        db.close();

        if (rowsUpdated > 0) {
            Toast.makeText(this, "Plantation details updated successfully!", Toast.LENGTH_SHORT).show();
            finish();  // Close activity after successful update
        } else {
            Toast.makeText(this, "Failed to update plantation details.", Toast.LENGTH_SHORT).show();
        }
    }
}
