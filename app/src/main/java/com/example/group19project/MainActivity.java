package com.example.group19project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editPassword, editUserName;
    Button buttonLogin, buttonRegister;
    MyDBHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // button layout
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        //input layout
        editPassword = (EditText)findViewById(R.id.editPassword);
        editUserName = (EditText)findViewById(R.id.editUserName);
        db = new MyDBHandler(this);

        // button listeners
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkIfEmpty();
                //check if logins match
                String user = editUserName.getText().toString();
                String password = editPassword.getText().toString();

                if(user.equals("") | password.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter all fields",Toast.LENGTH_SHORT).show();
                }

                if(db.checkUserNameAndPassword(user, password)){
                    Intent intent = new Intent(MainActivity.this, RegisterPage.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Invalid Inputs",Toast.LENGTH_SHORT).show();
                    editUserName.setText("");
                    editPassword.setText("");
                }


            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, RegisterPage.class);
            startActivity(intent);

            }
        });

    }


}