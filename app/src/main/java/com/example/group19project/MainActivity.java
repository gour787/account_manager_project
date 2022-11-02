package com.example.group19project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editPassword, editUserName;
    Button buttonLogin, buttonRegister;
    MyDBHandler db;
    public static final String FIRST_NAME = "first name";
    public static final String ACCOUNT_TYPE = "user type";

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

                if(user.equals("admin") && password.equals("admin123")){
                    Intent intent = new Intent(MainActivity.this, AdminPanel.class);
                    startActivity(intent);
                }

                if(db.checkUserNameAndPassword(user, password)){
                    Toast.makeText(MainActivity.this, "Login Success",Toast.LENGTH_SHORT).show();
                    ArrayList<String> details = db.findUserDetails(user);
                    String name = details.get(0);
                    String types = details.get(1);
                    //creting a new screen
                    Intent intent = new Intent(MainActivity.this, Activity_Dashboard.class);
                    intent.putExtra(FIRST_NAME, name);
                    intent.putExtra(ACCOUNT_TYPE, types);
                    startActivity(intent);


                }
                if(user.equals("") | password.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter all fields",Toast.LENGTH_SHORT).show();
                }


                else if(!db.checkUserNameAndPassword(user, password)){
                    Toast.makeText(MainActivity.this, "Invalid Inputs",Toast.LENGTH_SHORT).show();
                    editUserName.setText("");
                    editPassword.setText("");
                }

                db.close();
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