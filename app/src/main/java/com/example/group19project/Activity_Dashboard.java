package com.example.group19project;

import static com.example.group19project.MainActivity.ACCOUNT_TYPE;
import static com.example.group19project.MainActivity.FIRST_NAME;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class Activity_Dashboard extends AppCompatActivity {
    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        welcome = findViewById(R.id.welcomeText);

        Intent intent = getIntent();
        String name = intent.getStringExtra(RegisterPage.FIRST_NAME);
        String type = intent.getStringExtra(RegisterPage.ACCOUNT_TYPE);
        welcome.setText("Welcome "+name+ ", you are a "+type);


    }
}