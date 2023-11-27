package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class entry extends AppCompatActivity {
    private Spinner spinner;
    EditText da1,ti1,descr,amt;
    int mode=0;
    private SimpleDateFormat timeFormat;
    String selectedText;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ToggleButton simpleToggleButton = (ToggleButton) findViewById(R.id.toggle);
        descr=findViewById(R.id.desc);
        databaseHelper = new DatabaseHelper(this);
        RadioGroup radioGroup = findViewById(R.id.rdgrp);
        RadioButton radioButtonToSelect = findViewById(R.id.radioButton5);
        amt=findViewById(R.id.amt);

        simpleToggleButton.setText("OFLINE");
        simpleToggleButton.setTextOff("OFFLINE");
        simpleToggleButton.setTextOn("ONLINE");
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        da1=findViewById(R.id.dates);
        ti1=findViewById(R.id.times);
        timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        ti1.setText(currentTime);


        da1.setText(currentDate);
        spinner = findViewById(R.id.spinner);
        new DatePickerUniversal(da1,"dd-MM-yyyy");
        ti1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });



        // Define the values for the Spinner
        String[] spinnerValues = {
                "FOOD",
                "SNACKS",
                "TRAVEL",
                "EDUCATION",
                "HEALTH",
                "OTHERS",

        };

        // Create an ArrayAdapter to bind the values to the Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerValues
        );

        // Specify the layout to use for the dropdown items
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Attach the adapter to the Spinner
        spinner.setAdapter(spinnerAdapter);


        simpleToggleButton.setOnClickListener(v -> {
            // Check the state of the ToggleButton
            boolean isChecked = simpleToggleButton.isChecked();

            // Use the isChecked value as needed
            if (isChecked) {
                Toast.makeText(this,"a",Toast.LENGTH_SHORT).show();
                mode=1;
            } else {
                Toast.makeText(this,"b",Toast.LENGTH_SHORT).show();
                mode=0;
            }
        });



        // Set a listener to the RadioGroup to detect changes in the selection
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the selected RadioButton using the resource ID
                RadioButton selectedRadioButton = findViewById(checkedId);

                if (selectedRadioButton != null) {
                    // Get the text of the selected RadioButton
                    selectedText = selectedRadioButton.getText().toString();

                    // Do something with the selected RadioButton's text
                    // For example, display it in a Toast

                }
            }
        });

    }


    private void showTimePickerDialog() {
        // Get the current time from the system
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog and set the initial time
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Update the EditText with the selected time
                        Calendar selectedTime = Calendar.getInstance();
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedTime.set(Calendar.MINUTE, minute);

                        ti1.setText(timeFormat.format(selectedTime.getTime()));
                    }
                },
                hour,
                minute,
                true // set true to display in 24-hour format, false for AM/PM
        );

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

    public void submit(View view) {
        String part = selectedText;
        String date = da1.getText().toString();
        String time = ti1.getText().toString();
        String amts = amt.getText().toString();
        String desc = descr.getText().toString();
        String selectedCategory = spinner.getSelectedItem().toString();

        // Insert the data into the database
        long newRowId = databaseHelper.insertExpense(selectedCategory, date, time, amts, desc,0,"");

        if (newRowId != -1) {
            // Data inserted successfully
            Toast.makeText(this, "Entry added to the database!", Toast.LENGTH_SHORT).show();
        } else {
            // Error occurred while inserting data
            Toast.makeText(this, "Error inserting data to the database.", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View view) {
        finish();
    }
}