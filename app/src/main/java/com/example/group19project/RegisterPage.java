package com.example.group19project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class RegisterPage extends AppCompatActivity {
    EditText firstName, lastName, userName,password ;
    Button buttonRegister;
    RadioGroup radioGroup;
    RadioButton radioButton;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        radioGroup=findViewById(R.id.radioGroup2);
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
                    Toast.makeText(RegisterPage.this, "Please swipe left to sign in ",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
    public void checkButton(View v){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);

    }


}