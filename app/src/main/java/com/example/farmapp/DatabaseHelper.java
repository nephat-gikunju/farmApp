package com.example.farmapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "userdb";
    public static final int DATABASE_VERSION = 1;

    private static final String TABLE_REGISTRATION = "Registration";
    private static final String COLUMN_USERNAME = "UserName";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_FARMNAME = "FarmName";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the registration table with constraints
        String CREATE_TABLE = "CREATE TABLE " + TABLE_REGISTRATION + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " +
                COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_FARMNAME + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE);

        // Create the plantation table
        String CREATE_PLANTATION_TABLE = "CREATE TABLE Plantations (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PLANTATION_NAME TEXT, " +
                "PLANTATION_PICTURE TEXT, " +
                "TOTAL_PLANTS INTEGER, " +
                "RIPE_PLANTS INTEGER, " +
                "SICKLY_PLANTS INTEGER, " +
                "PLANT_YEAR TEXT, " +
                "LOCATION TEXT, " +
                "FARM_LAYOUT_ROWS INTEGER, " +
                "FARM_LAYOUT_COLUMNS INTEGER)";
        db.execSQL(CREATE_PLANTATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
        db.execSQL("DROP TABLE IF EXISTS Plantations");
        onCreate(db);
    }

    public boolean insertUser(String name, String email, String password, String farmname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COLUMN_USERNAME, name);
        content.put(COLUMN_EMAIL, email);
        content.put(COLUMN_PASSWORD, password);
        content.put(COLUMN_FARMNAME, farmname);

        long result = db.insert(TABLE_REGISTRATION, null, content);
        db.close(); // Close database connection
        return result != -1; // Return true if insertion is successful
    }

    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_REGISTRATION + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.moveToFirst(); // Check if there is at least one result
        cursor.close();
        db.close(); // Close database connection
        return exists;
    }

    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_REGISTRATION + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean isValid = cursor.moveToFirst(); // Check if credentials match
        cursor.close();
        db.close(); // Close database connection
        return isValid;
    }
    public String getfarmname(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_FARMNAME + " FROM " + TABLE_REGISTRATION + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        String farmName = "Unknown ";

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_FARMNAME);
            if (columnIndex != -1) {
                farmName = cursor.getString(columnIndex);
            }
        }

        cursor.close();
        db.close(); // Close database connection
        return farmName;
    }

}
