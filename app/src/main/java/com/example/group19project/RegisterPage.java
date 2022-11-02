package com.example.group19project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RegisterPage extends AppCompatActivity {
    public static final String FIRST_NAME = "first name";
    public static final String ACCOUNT_TYPE = "user type";
    EditText firstName, lastName, userName,password, welcome ;
    Button buttonRegister;
    RadioGroup radioGroup;
    RadioButton radioButton;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        radioGroup=findViewById(R.id.radioGroup);
        buttonRegister = findViewById(R.id.buttonRegister);
        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        userName = findViewById(R.id.editUserName);
        password = findViewById(R.id.editPassword);
        dbHandler = new MyDBHandler(this);



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);

                String type = radioButton.getText().toString();
                String name  = firstName.getText().toString();
                String last = lastName.getText().toString();
                String user = userName.getText().toString();
                String pass = password.getText().toString();
                Information info = new Information(name, last, user, pass, type);


                if(dbHandler.userExists(user)){
                    Toast.makeText(RegisterPage.this, "User already exists! ",Toast.LENGTH_SHORT).show();
                }
                else{
                    dbHandler.addUser(info);
                    Toast.makeText(RegisterPage.this, "User added! ",Toast.LENGTH_SHORT).show();
                    ArrayList<String> details = dbHandler.findUserDetails(user);
                    String named = details.get(0);
                    String types = details.get(1);
                    Toast.makeText(RegisterPage.this, named+types,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterPage.this, Activity_Dashboard.class);
                    intent.putExtra(FIRST_NAME, name);
                    intent.putExtra(ACCOUNT_TYPE, types);
                    startActivity(intent);
                    finish();

                }

            }
        });




    }
    public void checkButton(View v){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        Toast.makeText(RegisterPage.this, radioButton.getText(),Toast.LENGTH_SHORT).show();
    }


}