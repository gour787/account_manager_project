package com.example.group19project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminPanel extends AppCompatActivity {
    EditText courseID, courseName, confirmID;
    Button addButton, updateButton, deleteButton;
    ListView productListView;
    ArrayList<String> productList;
    ArrayAdapter<String> special;
    ArrayAdapter adapter;
    MyDBHandler dbHandler;

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

        // listview
        productList = new ArrayList<>();
        productListView = findViewById(R.id.productListView);

        // db handler
        dbHandler = new MyDBHandler(this);

        //cursor


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String corID = courseID.getText().toString();
                String corName = courseName.getText().toString();
                Course course = new Course(corName, corID);
                dbHandler.addCourse(course);
                courseID.setText("");
                courseName.setText("");
                Toast.makeText(AdminPanel.this, "Add product", Toast.LENGTH_SHORT).show();

                viewProducts();
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
                        Toast.makeText(AdminPanel.this, "Delete product", Toast.LENGTH_SHORT).show();
                        viewProducts();

                    }
                    else{
                        Toast.makeText(AdminPanel.this, "Course Does Not Exist", Toast.LENGTH_SHORT).show();
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
}