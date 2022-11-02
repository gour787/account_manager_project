package com.example.group19project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityDashboard extends AppCompatActivity {
    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        welcome = findViewById(R.id.welcomeText);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.FIRST_NAME);
        String type = intent.getStringExtra(MainActivity.ACCOUNT_TYPE);
        welcome.setText("Welcome "+name+ ", you are a "+type);
    }
}