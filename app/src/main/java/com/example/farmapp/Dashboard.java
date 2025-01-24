package com.example.farmapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.color.DynamicColors;

public class Dashboard extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private LinearLayout plantationsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DynamicColors.applyToActivityIfAvailable(this);
        setContentView(R.layout.activity_dashboard);

        dbHelper = new DatabaseHelper(this);
        plantationsContainer = findViewById(R.id.plantations_container);

        // Set user information
        String username = getIntent().getStringExtra("USERNAME");
        TextView userStatus = findViewById(R.id.user_status);
        userStatus.setText("Welcome Back, " + username);

        String farmName = dbHelper.getfarmname(username);
        TextView farmNameView = findViewById(R.id.farm_name);
        farmNameView.setText(farmName + " Farm");

        // Load and display plantations
        loadPlantations();
    }

    private void loadPlantations() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Plantations", null);

        plantationsContainer.removeAllViews(); // Clear any existing views
        LinearLayout currentRow = null;

        int cardsInRow = 0;
        if (cursor.moveToFirst()) {
            do {
                // Get plantation data
                String plantationName = cursor.getString(cursor.getColumnIndexOrThrow("PLANTATION_NAME"));
                int totalPlants = cursor.getInt(cursor.getColumnIndexOrThrow("TOTAL_PLANTS"));
                int ripePlants = cursor.getInt(cursor.getColumnIndexOrThrow("RIPE_PLANTS"));
                String plantationPicture = cursor.getString(cursor.getColumnIndexOrThrow("PLANTATION_PICTURE"));

                // Create and add plantation card
                View plantationCard = createPlantationCard(plantationName, totalPlants, ripePlants, plantationPicture);

                if (cardsInRow == 0) {
                    currentRow = new LinearLayout(this);
                    currentRow.setOrientation(LinearLayout.HORIZONTAL);
                    currentRow.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    plantationsContainer.addView(currentRow);
                }

                currentRow.addView(plantationCard);
                cardsInRow++;

                if (cardsInRow == 3) {
                    cardsInRow = 0; // Reset for the next row
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    private View createPlantationCard(String name, int totalPlants, int ripePlants, String picturePath) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardView = inflater.inflate(R.layout.plantation_card, null);

        // Set plantation name
        TextView nameView = cardView.findViewById(R.id.Plantaton_name);
        nameView.setText(name);

        // Set plantation details
        TextView detailsView = cardView.findViewById(R.id.plantation_description);
        detailsView.setText("Total Plants: " + totalPlants + "\nRipe: " + ripePlants);

        // Set plantation image
        ImageView imageView = cardView.findViewById(R.id.plantation_image);
        if (picturePath != null && !picturePath.isEmpty()) {
            // Load image from path (e.g., using Glide or Picasso)
            // Glide.with(this).load(picturePath).into(imageView);
        }

        // Set click listeners
        cardView.setOnClickListener(v -> onPlantationClick(name));
        cardView.findViewById(R.id.oneditplantationclick).setOnClickListener(v -> onEditPlantationClick(name));
        cardView.findViewById(R.id.ondeleteplantationclick).setOnClickListener(v -> onDeletePlantationClick(name));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0, // Width of each card
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f // Weight to distribute cards evenly in the row
        );
        layoutParams.setMargins(8, 8, 8, 8);
        cardView.setLayoutParams(layoutParams);

        return cardView;
    }

    public void onPlantationClick(String plantationName) {
        Intent intent = new Intent(Dashboard.this, plant_details.class);
        intent.putExtra("PLANTATION_NAME", plantationName);
        startActivity(intent);
    }

    public void onEditPlantationClick(String plantationName) {
        Intent intent = new Intent(Dashboard.this, EditPlantation.class);
        intent.putExtra("PLANTATION_NAME", plantationName);
        startActivity(intent);
    }

    public void onDeletePlantationClick(String plantationName) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Plantation")
                .setMessage("Are you sure you want to delete this plantation?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete("Plantations", "PLANTATION_NAME = ?", new String[]{plantationName});
                    db.close();
                    recreate(); // Reload the activity
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void onaddplantationclick(View view) {
        Intent intent = new Intent(Dashboard.this, add_plantation.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh plantations when returning to dashboard
        plantationsContainer.removeAllViews();
        loadPlantations();
    }
}
