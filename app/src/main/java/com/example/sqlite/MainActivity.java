package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newent(View view) {
        Intent i=new Intent(MainActivity.this,entry.class);
        startActivity(i);
    }

    public void reco(View view) {
        Intent i=new Intent(MainActivity.this,records.class);
        startActivity(i);
    }
}