package com.example.group19project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class updated extends AppCompatActivity {
    EditText editDescription, editDay, editStartTime, editEndTime, editCapacity;
    Button buttonTime, buttonUpdate, buttonDeregister;
    StringBuilder stringy;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        TextView intro = findViewById(R.id.textView);
        boolean flag= false;

        Intent intent = getIntent();
        String name = intent.getStringExtra(InstructorPanel.FIRST_NAME);
        String info = intent.getStringExtra(InstructorPanel.COURSE_INFO);
        intro.setText("Welcome "+name+" you selected "+info);
        String[] val = info.split(",");
        String code = val[0];
        String names = val[1];
        stringy = new StringBuilder();

        editDescription=findViewById(R.id.editDescription);
        editDay=findViewById(R.id.editDay);
        editStartTime=findViewById(R.id.editStartTime);
        editEndTime=findViewById(R.id.editEndTime);
        editCapacity = findViewById(R.id.editCapacity);

        buttonTime = findViewById(R.id.buttonTime);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDeregister = findViewById(R.id.buttonDeregister);
        dbHandler = new MyDBHandler(this);

        if(dbHandler.findCourseInstructor(names, code, name)){
            buttonUpdate.setText("UPDATE");
        }

        buttonDeregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.updateCourseInstructor(names,code,"null");
                dbHandler.updateCourseDescription(names, code, "null");
                dbHandler.updateCourseCapacity(names, code, "null");
                dbHandler.updateCourseTime(names, code, "null");
                Intent intent = new Intent(updated.this, InstructorPanel.class);
                startActivity(intent);
            }
        });

        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = editStartTime.getText().toString();
                String end = editEndTime.getText().toString();
                String day = editDay.getText().toString();
                if(start.isEmpty() || end.isEmpty() || day.isEmpty()){
                    Toast.makeText(updated.this, "Start, end times and Day fields must be filled,", Toast.LENGTH_SHORT).show();
                }

                if(validTime(start)&&validTime(end)&&validDay(day)){
                    Toast.makeText(updated.this, "valid,", Toast.LENGTH_SHORT).show();
                    stringy.append(start+"-"+end+"("+day+")");
                    Toast.makeText(updated.this, stringy.toString(), Toast.LENGTH_SHORT).show();
                    editStartTime.setText("");
                    editEndTime.setText("");
                    editDay.setText("");
                }

                else{
                    Toast.makeText(updated.this, "invalid,", Toast.LENGTH_SHORT).show();
                }

            }
        });



        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editDescription.getText().toString();
                String capacity = editCapacity.getText().toString();
                dbHandler.updateCourseInstructor(names,code,name);
                if(description.length()>0){
                    dbHandler.updateCourseDescription(names, code, description);
                    Toast.makeText(updated.this, "description updated", Toast.LENGTH_SHORT).show();
                }
                if(capacity.length()>0&&validCapacity(capacity)){
                    dbHandler.updateCourseCapacity(names, code, capacity);
                    Toast.makeText(updated.this, "capacity updated", Toast.LENGTH_SHORT).show();
                }
                if(stringy.toString().length()>0){
                    dbHandler.updateCourseTime(names, code, stringy.toString());
                    stringy.setLength(0);
                    Toast.makeText(updated.this, "Time slots updated", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(updated.this, InstructorPanel.class);
                startActivity(intent);


            }
        });





    }
    public boolean validTime(String time){
        try{
            String[] check = time.split(" ");
            String[] hour_min = check[0].split(":");
            int hour = Integer.parseInt(hour_min[0]);
            int min = Integer.parseInt(hour_min[1]);
            if(Objects.equals(check[1], "AM") || Objects.equals(check[1], "PM")) {
                if(hour>=1 && hour<=12){
                    if(min>=0 && min<=59){
                        return true;
                    }
                }
            }
            else{
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
        return false;
    }
    public boolean validDay(String day){
        if(Objects.equals(day, "Monday") || Objects.equals(day, "Tuesday") || Objects.equals(day, "Wednesday") || Objects.equals(day, "Thursday") || Objects.equals(day, "Friday") || Objects.equals(day, "Saturday")
                || Objects.equals(day, "Sunday")){
            return true;

        }
        return false;
    }

    public boolean validCapacity(String cap){
        try{
            int new_cap = Integer.parseInt(cap);
            if(new_cap>200){
                Toast.makeText(updated.this, "Max capacity cannot be greater than 200", Toast.LENGTH_SHORT).show();
                return false;
            }
            else{
                Toast.makeText(updated.this, "valid capacity ", Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(updated.this, "invalid capacity ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}