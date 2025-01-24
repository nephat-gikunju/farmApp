package com.example.farmapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class plant_details extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private TextView plantationNameTV, totalPlantsTV, ripePlantsTV, sicklyPlantsTV, plantYearTV, locationTV;
    private ImageView editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plant_details);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Initialize TextViews
        plantationNameTV = findViewById(R.id.Plantaton_name);
        totalPlantsTV = findViewById(R.id.totalPlantsTextView);
        ripePlantsTV = findViewById(R.id.ripePlantsTextView);
        sicklyPlantsTV = findViewById(R.id.sicklyPlantsTextView);
        plantYearTV = findViewById(R.id.plantYearTextView);
        locationTV = findViewById(R.id.locationTextView);

        // Load plantation details
        loadPlantationDetails();
        generateFarmLayout();

    }

    private void loadPlantationDetails() {

        String plantationName = getIntent().getStringExtra("PLANTATION_NAME");
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Assuming you want to load the first plantation, modify as needed
        Cursor cursor = db.query("Plantations",
                null,
                "PLANTATION_NAME = ?",
                new String[]{plantationName},
                null, null, null);
        if (cursor.moveToFirst()) {
            // Extract data from cursor
            int totalPlants = cursor.getInt(cursor.getColumnIndexOrThrow("TOTAL_PLANTS"));
            int ripePlants = cursor.getInt(cursor.getColumnIndexOrThrow("RIPE_PLANTS"));
            int sicklyPlants = cursor.getInt(cursor.getColumnIndexOrThrow("SICKLY_PLANTS"));
            String plantYear = cursor.getString(cursor.getColumnIndexOrThrow("PLANT_YEAR"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("LOCATION"));

            // Set TextViews
            plantationNameTV.setText(plantationName + "Plantaion");
            totalPlantsTV.setText("Total Plants: " + totalPlants);
            ripePlantsTV.setText("Ripe: " + ripePlants);
            sicklyPlantsTV.setText("Sickly: " + sicklyPlants);
            plantYearTV.setText("Plant Year: " + plantYear);
            locationTV.setText("Location: " + location);
        } else {
            Toast.makeText(this, "No plantation data found", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }
    private void generateFarmLayout() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String plantationName = getIntent().getStringExtra("PLANTATION_NAME");

        Cursor cursor = db.query("Plantations",
                new String[]{"FARM_LAYOUT_ROWS", "FARM_LAYOUT_COLUMNS"},
                "PLANTATION_NAME = ?",
                new String[]{plantationName},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int rows = cursor.getInt(cursor.getColumnIndexOrThrow("FARM_LAYOUT_ROWS"));
            int columns = cursor.getInt(cursor.getColumnIndexOrThrow("FARM_LAYOUT_COLUMNS"));

            GridLayout farmLayoutGrid = findViewById(R.id.farmLayoutGrid);
            farmLayoutGrid.removeAllViews();
            farmLayoutGrid.setColumnCount(columns);
            farmLayoutGrid.setRowCount(rows);

            for (int i = 0; i < rows * columns; i++) {
                ImageView plantIcon = new ImageView(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 85;  // dp
                params.height = 85; // dp
                params.setMargins(2, 2, 2, 2);
                plantIcon.setImageResource(R.drawable.mango_plant_icon);
                plantIcon.setLayoutParams(params);

                // Handle long click for ripe/sick state change
                plantIcon.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (v.getTag() == null || v.getTag().equals("normal")) {
                            v.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                            v.setTag("sick");
                            Toast.makeText(getApplicationContext(), "Marked as Sick", Toast.LENGTH_SHORT).show();
                        } else if (v.getTag().equals("sick")) {
                            v.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                            v.setTag("ripe");
                            Toast.makeText(getApplicationContext(), "Marked as Ripe", Toast.LENGTH_SHORT).show();
                        } else {
                            v.setBackgroundColor(0); // Clear background
                            v.setTag("normal");
                            Toast.makeText(getApplicationContext(), "Reset to Normal", Toast.LENGTH_SHORT).show();
                        }
                        return true;  // Indicates the event was handled
                    }
                });

                farmLayoutGrid.addView(plantIcon);
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No layout data found for plantation: " + plantationName, Toast.LENGTH_SHORT).show();
        }

        db.close();
    }


    public void onEditPlantationClick(String plantationName) {
        Intent intent = new Intent(plant_details.this, EditPlantation.class);
        intent.putExtra("PLANTATION_NAME", plantationName);
        startActivity(intent);
    }
}