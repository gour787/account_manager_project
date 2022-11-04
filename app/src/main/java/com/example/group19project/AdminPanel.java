package com.example.group19project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminPanel extends AppCompatActivity {
    EditText courseID, courseName, confirmID;
    Button addButton, updateButton, deleteButton, deleteUser;
    ListView productListView;
    ArrayList<String> productList;
    ArrayAdapter adapter;
    MyDBHandler dbHandler;
    RadioGroup radioGrouping;
    RadioButton radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);


        courseID = findViewById(R.id.courseID);
        courseName = findViewById(R.id.courseName);
        confirmID = findViewById(R.id.confirmID);

        //buttons
        addButton = findViewById(R.id.addButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        deleteUser = findViewById(R.id.deleteUser);

        // listview
        productList = new ArrayList<>();
        productListView = findViewById(R.id.productListView);

        // db handler
        dbHandler = new MyDBHandler(this);

        //radio
        radioGrouping = findViewById(R.id.radioGroup2);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String corID = courseID.getText().toString();
                String corName = courseName.getText().toString();
                if(!corID.isEmpty()&&!corName.isEmpty()){
                    Course course = new Course(corName, corID);
                    dbHandler.addCourse(course);
                    courseID.setText("");
                    courseName.setText("");
                    confirmID.setVisibility(View.INVISIBLE);
                    Toast.makeText(AdminPanel.this, "Add product", Toast.LENGTH_SHORT).show();

                    viewProducts();
                }else{
                    Toast.makeText(AdminPanel.this, "Add product failed", Toast.LENGTH_SHORT).show();
                }
                dbHandler.close();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String corID = courseID.getText().toString();
                    String corName = courseName.getText().toString();

                    if(dbHandler.courseExists(corID, corName)){
                        dbHandler.deleteCourse(corID, corName);
                        courseID.setText("");
                        courseName.setText("");
                        confirmID.setVisibility(View.INVISIBLE);
                        Toast.makeText(AdminPanel.this, "Delete product", Toast.LENGTH_SHORT).show();
                        viewProducts();

                    }
                    else{
                        Toast.makeText(AdminPanel.this, "Course Does Not Exist", Toast.LENGTH_SHORT).show();
                    }
                    dbHandler.close();



            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userAcc = courseID.getText().toString();
                String userAcc2 = courseName.getText().toString();

                if(userAcc.equals("") && userAcc2.equals("")){

                    courseID.setHint("Please Enter a User Name");
                    courseName.setHint("Please Re-Enter a User Name");
                    confirmID.setVisibility(View.INVISIBLE);

                }
                else{
                    if(userAcc.equals(userAcc2)&&dbHandler.userExists(userAcc)){
                        dbHandler.deleteUser(userAcc);
                        Toast.makeText(AdminPanel.this, "Deleted user "+userAcc, Toast.LENGTH_SHORT).show();
                        courseID.setHint("Enter Course Code: ");
                        courseName.setHint("Enter Course Name:");
                        confirmID.setVisibility(View.INVISIBLE);
                    }
                    else{
                        Toast.makeText(AdminPanel.this, "Failed to delete User", Toast.LENGTH_SHORT).show();
                        courseID.setHint("Enter Course Code: ");
                        courseName.setHint("Enter Course Name:");
                        courseID.setText("");
                        courseName.setText("");
                        confirmID.setVisibility(View.INVISIBLE);
                    }
                    dbHandler.close();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String corID = courseID.getText().toString();
                String corName = courseName.getText().toString();

                int  radioID= radioGrouping.getCheckedRadioButtonId();


                if(corName.isEmpty()&&corID.isEmpty()){

                    confirmID.setVisibility(View.VISIBLE);

                }

                else if(dbHandler.courseExists(corID, corName)){
                    radioButtons = findViewById(radioID);
                    String type = radioButtons.getText().toString();
                    String confirm = confirmID.getText().toString();
                    if(type.equals("Course Code")){
                        dbHandler.updateCourseCode(corID, confirm);
                        Toast.makeText(AdminPanel.this, "Course Updated", Toast.LENGTH_SHORT).show();
                        viewProducts();
                    }
                    else{
                        dbHandler.updateCourseName(corName, confirm);
                        viewProducts();
                    }

                }

                else{
                    Toast.makeText(AdminPanel.this, "Course Failed to update", Toast.LENGTH_SHORT).show();
                    courseID.setText("");
                    courseName.setText("");
                    confirmID.setVisibility(View.INVISIBLE);
                }


            }
        });


        viewProducts();

    }
    private void viewProducts() {

        productList.clear();
        Cursor cursor = dbHandler.getCourseData();

        if (cursor.getCount() == 0) {
            Toast.makeText(AdminPanel.this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                productList.add(cursor.getString(0) + " (" + cursor.getString(1) + ")");
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        productListView.setAdapter(adapter);

        cursor.close();
        dbHandler.close();
    }
    public void checkButtons(View v){
        int radioIDD = radioGrouping.getCheckedRadioButtonId();
        radioButtons = findViewById(radioIDD);

    }

}