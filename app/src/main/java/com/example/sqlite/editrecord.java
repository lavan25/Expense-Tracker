package com.example.sqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class editrecord extends AppCompatActivity {
    private Spinner spinner;
    private EditText da1, ti1, descr, amt;
    private int mode = 0;
    private SimpleDateFormat timeFormat;
    private String selectedText;
    private DatabaseHelper databaseHelper;
    private int expenseId;  // Added to keep track of the expense ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editrecord);

        // Initialize UI elements
        spinner = findViewById(R.id.spinner);
        ToggleButton simpleToggleButton = findViewById(R.id.toggle);
        descr = findViewById(R.id.desc);
        amt = findViewById(R.id.amt);
        da1 = findViewById(R.id.dates);
        ti1 = findViewById(R.id.times);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize other settings
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        ti1.setText(currentTime);
        da1.setText(currentDate);

        // Initialize Spinner
        String[] spinnerValues = {
                "FOOD", "SNACKS", "TRAVEL", "EDUCATION", "HEALTH", "OTHERS"
        };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerValues);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(spinnerAdapter);

        // Fetch expenseId from the intent
        expenseId = getIntent().getIntExtra("expenseId", -1);

        if (expenseId != -1) {
            Expense existingExpense = databaseHelper.getExpenseById(expenseId);
            if (existingExpense != null) {
                descr.setText(existingExpense.getDescription());
                amt.setText(existingExpense.getAmount());
            }
        }

        // Button and toggle listeners
        simpleToggleButton.setOnClickListener(v -> {
            boolean isChecked = simpleToggleButton.isChecked();
            mode = isChecked ? 1 : 0;
        });

        RadioGroup radioGroup = findViewById(R.id.rdgrp);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            if (selectedRadioButton != null) {
                selectedText = selectedRadioButton.getText().toString();
            }
        });
    }

    public void submit(View view) {
        // Capture the current UI state
        String part = selectedText;
        String date = da1.getText().toString();
        String time = ti1.getText().toString();
        String amts = amt.getText().toString();
        String desc = descr.getText().toString();
        String selectedCategory = spinner.getSelectedItem().toString();

        if (expenseId != -1) {
            boolean isUpdated = databaseHelper.updateExpense(expenseId, selectedCategory, date, time, amts, desc, mode, "");
            if (isUpdated) {
                Toast.makeText(this, "Entry updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error updating data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            long newRowId = databaseHelper.insertExpense(selectedCategory, date, time, amts, desc, mode, "");
            if (newRowId != -1) {
                Toast.makeText(this, "Entry added to the database!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error inserting data to the database.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
