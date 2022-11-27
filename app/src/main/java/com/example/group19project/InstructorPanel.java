package com.example.group19project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InstructorPanel extends AppCompatActivity {
    ArrayList<String> courseList =new ArrayList<String>();
    Button filterButton;
    public static final String COURSE_INFO = "Course info";
    public static final String FIRST_NAME = "First name";
    MyDBHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_panel);
        TextView courseName2;
        TextView courseCode3;

        courseName2 = findViewById(R.id.courseName2);
        courseCode3 = findViewById(R.id.courseCode3);



        db = new MyDBHandler(this);
        ListView lv = findViewById(R.id.listview);

        courseList.clear();
        generateList();

        lv.setAdapter(new MyListAdapter(InstructorPanel.this, R.layout.info_dialogue,courseList));
        filterButton = findViewById(R.id.filterButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseList.clear();
                String name = courseName2.getText().toString();
                String code = courseCode3.getText().toString();
                MyDBHandler db = new MyDBHandler(InstructorPanel.this);

                if(name.length()>0 && code.isEmpty()){
                    courseList.clear();
                    generateLists(name);
                    ListView lv = findViewById(R.id.listview);
                    lv.setAdapter(new MyListAdapter(InstructorPanel.this, R.layout.info_dialogue,courseList));
                    courseName2.setText("");

                }
                if(code.length()>0 && name.isEmpty()){
                    courseList.clear();
                    generatedList(code);
                    ListView lv = findViewById(R.id.listview);
                    lv.setAdapter(new MyListAdapter(InstructorPanel.this, R.layout.info_dialogue,courseList));
                    courseCode3.setText("");

                }
                if(name.length()>0 && code.length()>0){
                    courseList.clear();
                    generatedLists(name, code);
                    ListView lv = findViewById(R.id.listview);
                    lv.setAdapter(new MyListAdapter(InstructorPanel.this, R.layout.info_dialogue,courseList));
                    courseCode3.setText("");
                    courseName2.setText("");
                }
                if(name.isEmpty() && code.isEmpty()){
                    courseList.clear();
                    generateList();
                    ListView lv = findViewById(R.id.listview);
                    lv.setAdapter(new MyListAdapter(InstructorPanel.this, R.layout.info_dialogue,courseList));
                }





            }
        });



    }

    public ArrayList<String> generateList() {
        courseList.clear();
        MyDBHandler dbHandler = new MyDBHandler(this);
        Cursor cursor = dbHandler.getCourseData();
        if (cursor.getCount() == 0) {
            // nothing to show
        }
        else {
            while (cursor.moveToNext()) {
                courseList.add(cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2));

            }
        }
        return courseList;
    }
    public ArrayList<String> generateLists(String name) {
        courseList.clear();
        MyDBHandler dbHandler = new MyDBHandler(this);
        Cursor cursor = dbHandler.findList(name);
        if (cursor.getCount() == 0) {
            // nothing to show
        }
        else {
            while (cursor.moveToNext()) {
                courseList.add(cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2));

            }
        }
        return courseList;
    }

    public ArrayList<String> generatedList(String code) {
        courseList.clear();
        MyDBHandler dbHandler = new MyDBHandler(this);
        Cursor cursor = dbHandler.findLists(code);
        if (cursor.getCount() == 0) {
            // nothing to show
        }
        else {
            while (cursor.moveToNext()) {
                courseList.add(cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2));

            }
        }
        return courseList;
    }

    public ArrayList<String> generatedLists(String name, String code) {
        courseList.clear();
        MyDBHandler dbHandler = new MyDBHandler(this);
        Cursor cursor = dbHandler.findsLists(name, code);
        if (cursor.getCount() == 0) {
            // nothing to show
        }
        else {
            while (cursor.moveToNext()) {
                courseList.add(cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2));

            }
        }
        return courseList;
    }

    public ArrayList<String> fullList() {
        courseList.clear();
        MyDBHandler dbHandler = new MyDBHandler(this);
        Cursor cursor = dbHandler.getCourseData();
        if (cursor.getCount() == 0) {
            // nothing to show
        }
        else {
            while (cursor.moveToNext()) {
                courseList.add(cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2)+ "," +
                        cursor.getString(3)+ "," + cursor.getString(4)+ "," + cursor.getString(5));

            }
        }
        return courseList;
    }

    /////////////////////////////////

    private class MyListAdapter extends ArrayAdapter<String>{
        private int layout;


        public MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            Intent intent = getIntent();
            String name = intent.getStringExtra(MainActivity.FIRST_NAME);
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.courseCode2 = (TextView) convertView.findViewById(R.id.courseCode2);



                viewHolder.changeInfo = (Button) convertView.findViewById(R.id.changeInfo);
                viewHolder.changeInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        fullList();
                        String[] teach = courseList.get(position).split(",");
                        if(Objects.equals(teach[2], "null")){
                            Intent intent = new Intent(InstructorPanel.this, updated.class);
                            intent.putExtra(COURSE_INFO, courseList.get(position));
                            intent.putExtra(FIRST_NAME, name);
                            startActivity(intent);
                        }
                        if(Objects.equals(teach[2], name)){

                            Intent intent = new Intent(InstructorPanel.this, updated.class);
                            intent.putExtra(COURSE_INFO, courseList.get(position));
                            intent.putExtra(FIRST_NAME, name);
                            startActivity(intent);
                        }

                        else{

                            Toast.makeText(InstructorPanel.this, "Teacher already assigned to course", Toast.LENGTH_SHORT).show();
                        }



                    }
                });
                viewHolder.viewInfo= (Button) convertView.findViewById(R.id.viewInfo);
                viewHolder.viewInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fullList();
                        Toast.makeText(InstructorPanel.this, fullList().get(position), Toast.LENGTH_SHORT).show();
                    }
                });
///

                //viewHolder.viewInfo = (Button) findViewById(R.id.viewInfo);

                convertView.setTag(viewHolder);




                mainViewHolder = (ViewHolder) convertView.getTag();
                mainViewHolder.courseCode2.setText(courseList.get(position));




            return convertView;
        }
    }
    public class ViewHolder{
        TextView courseCode2, courseName2, instructorName;
        Button changeInfo, viewInfo;
    }
}