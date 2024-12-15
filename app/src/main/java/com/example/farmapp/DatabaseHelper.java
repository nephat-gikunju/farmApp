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
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_FARMNAME = "FarmName";
    private static final String COLUMN_PHONENUMBER = "PhoneNumber";



    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String CREATE_TABLE = "CREATE TABLE " + TABLE_REGISTRATION + "(" +
        COLUMN_NAME +"," + COLUMN_EMAIL +"," + COLUMN_PASSWORD + "," + COLUMN_PHONENUMBER + "," + COLUMN_FARMNAME + ")";

    db.execSQL(CREATE_TABLE);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
        onCreate(db);


    }
    public long insertUser(String name ,String email,String password, String farmname ,String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COLUMN_NAME, name);
        content.put(COLUMN_EMAIL, email);
        content.put(COLUMN_FARMNAME, farmname);
        content.put(COLUMN_PASSWORD,password);
        content.put(COLUMN_PHONENUMBER,phonenumber);

        return db.insert(TABLE_REGISTRATION ,null ,content);

    }
    public boolean checkUsername (String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_REGISTRATION,
                new String[]{COLUMN_NAME},
                COLUMN_NAME + " = ?",
                new String[]{username},
                null,
                null,
                null

        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean validateUser(String username,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_REGISTRATION,
                new String[]{COLUMN_NAME},
                COLUMN_NAME + " = ? AND " + COLUMN_PASSWORD + " = ?",  // Where clause
                new String[]{username, password},  // Where arguments
                null,  // Group by
                null,  // Having
                null   // Order by
        );

        // Check if cursor has any results
        boolean isValid = cursor.getCount() > 0;

        // Close the cursor to prevent resource leaks
        cursor.close();

        return isValid;


    }




}
