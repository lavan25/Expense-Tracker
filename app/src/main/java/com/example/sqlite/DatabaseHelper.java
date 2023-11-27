package com.example.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "expense_db";
    private static final int DATABASE_VERSION = 3; // Updated version for database upgrade
    private static final String TABLE_NAME = "expenses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_TYPE = "type"; // New column name for type (debit or credit)

    public List<Expense> getAllExpenses() {
        List<Expense> expenseList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve (all columns in this case)
        String[] columns = {
                COLUMN_ID,
                COLUMN_CATEGORY,
                COLUMN_DATE,
                COLUMN_TIME,
                COLUMN_AMOUNT,
                COLUMN_DESCRIPTION,
                COLUMN_RATING,
                COLUMN_TYPE // Include the new column in the query
        };

        // Query the database to retrieve all rows from the expenses table
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        // Loop through the cursor and create Expense objects for each row
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                @SuppressLint("Range") String amount = cursor.getString(cursor.getColumnIndex(COLUMN_AMOUNT));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                @SuppressLint("Range") int rating = cursor.getInt(cursor.getColumnIndex(COLUMN_RATING));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)); // Retrieve the new column

                Expense expense = new Expense(id, category, date, time, amount, description, rating, type);
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return expenseList;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_AMOUNT + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_RATING + " INTEGER DEFAULT 0, " +
                COLUMN_TYPE + " TEXT)"; // New column with type (debit or credit)
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // If upgrading from version 1 to 2, add the new column for rating
            String alterTableQuery = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_RATING + " INTEGER DEFAULT 0";
            db.execSQL(alterTableQuery);
        }
        if (oldVersion < 3) {
            // If upgrading from version 2 to 3, add the new column for type (debit or credit)
            String alterTableQuery = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_TYPE + " TEXT";
            db.execSQL(alterTableQuery);
        }
    }

    public long insertExpense(String category, String date, String time, String amount, String description, int rating, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_RATING, rating);
        values.put(COLUMN_TYPE, type); // Insert the type (debit or credit) into the database
        return db.insert(TABLE_NAME, null, values);
    }
    public void deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Expense getExpenseById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("expenses", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Create an Expense object and populate it
            // Replace with your actual column names and data types
            Expense expense = new Expense(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7)
            );
            cursor.close();
            return expense;
        }

        return null;
    }

    public boolean updateExpense(int id, String category, String date, String time, String amount, String description, int rating, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category", category);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("amount", amount);
        contentValues.put("description", description);
        contentValues.put("rating", rating);
        contentValues.put("type", type);
        int result = db.update("expenses", contentValues, "id = ?", new String[]{String.valueOf(id)});
        return result != -1;
    }

}

