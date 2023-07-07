package com.riko.android.locstion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LocationDB";
    private static final String TABLE_LOCATION = "location";
    private static final String KEY_ID = "id";
    private static final String KEY_ADDRESS = "address";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_LOCATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ADDRESS + " TEXT"
                + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        onCreate(db);
    }

    public void addLocation(String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESS, address);

        db.insert(TABLE_LOCATION, null, values);
        db.close();
    }

    public List<String> getAllLocations() {
        List<String> locations = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
                locations.add(address);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return locations;
    }
}
