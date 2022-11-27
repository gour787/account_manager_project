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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StudentPanel extends AppCompatActivity {
    TextView courseName5, courseCode5, courseDay;
    Button filterButton2, viewCourses2;
    ArrayList<String> courseList =new ArrayList<String>();
    MyDBHandler db;

    public static final String COURSE_INFO = "Course info";
    public static final String FIRST_NAME = "First name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);

        courseName5 = findViewById(R.id.courseName5);
        courseCode5 = findViewById(R.id.courseCode5);
        courseDay = findViewById(R.id.courseDay);
        filterButton2 = findViewById(R.id.filterButton2);
        viewCourses2 = findViewById(R.id.viewCourses2);


        db = new MyDBHandler(this);
        ListView lv = findViewById(R.id.listview2);

        courseList.clear();
        generateList();

        lv.setAdapter(new StudentPanel.MyListAdapter(StudentPanel.this, R.layout.student_course_dialogue,courseList));

        filterButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = courseName5.getText().toString();
                String code = courseCode5.getText().toString();
                String day = courseDay.getText().toString();

                if(name.length()>0 && code.isEmpty()){
                    courseList.clear();
                    generateLists(name);
                    ListView lv = findViewById(R.id.listview2);
                    lv.setAdapter(new StudentPanel.MyListAdapter(StudentPanel.this, R.layout.student_course_dialogue,courseList));
                    courseName5.setText("");

                }
                if(code.length()>0 && name.isEmpty()) {
                    courseList.clear();
                    generatedList(code);
                    ListView lv = findViewById(R.id.listview2);
                    lv.setAdapter(new StudentPanel.MyListAdapter(StudentPanel.this, R.layout.student_course_dialogue, courseList));
                    courseCode5.setText("");

                }
                if(name.isEmpty() && code.isEmpty()){
                    courseList.clear();
                    generateList();
                    ListView lv = findViewById(R.id.listview2);
                    lv.setAdapter(new StudentPanel.MyListAdapter(StudentPanel.this, R.layout.student_course_dialogue, courseList));
                }
                if(day.length()>0&& name.isEmpty()&& code.isEmpty()){
                    generateList();
                    generatedListofDays(day, courseList);
                    ListView lv = findViewById(R.id.listview2);
                    lv.setAdapter(new StudentPanel.MyListAdapter(StudentPanel.this, R.layout.student_course_dialogue, courseList));
                    courseDay.setText("");
                }

            }
        });

        viewCourses2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String user_name = intent.getStringExtra(MainActivity.USER_NAME);
                ArrayList<String> stringy = db.findOldValues(user_name);
                Toast.makeText(StudentPanel.this, stringy.toString(), Toast.LENGTH_SHORT).show();
            }
        });

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
                courseList.add(cursor.getString(0) + "," + cursor.getString(1)

                        +"," +cursor.getString(5)  );

            }
        }
        return courseList;
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
                courseList.add(cursor.getString(0) + "," + cursor.getString(1)
                        +"," +cursor.getString(5)  );

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
                courseList.add(cursor.getString(0) + "," + cursor.getString(1)
                        +"," +cursor.getString(5)  );

            }
        }
        return courseList;
    }
    public ArrayList<String> generatedListofDays(String day, ArrayList<String> courseList){
        ArrayList<String> newList = new ArrayList<>();
        for(int i=0;i<courseList.size();i++){
            String[] stringy = courseList.get(i).split(",");
            if(stringy[2].contains(day)){
                newList.add(courseList.get(i));
            }

        }
        courseList.clear();
        courseList.addAll(newList);
        return courseList;
    }

    public ArrayList<String> fullList() {
        courseList.clear();
        MyDBHandler dbHandler = new MyDBHandler(this);
        Cursor cursor = dbHandler.getData();
        if (cursor.getCount() == 0) {
            // nothing to show
        }
        else {
            while (cursor.moveToNext()) {
                courseList.add(cursor.getString(6));

            }
        }
        return courseList;
    }
    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;


        public MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           StudentPanel.ViewHolder mainViewHolder = null;
            Intent intent = getIntent();
            String user_name = intent.getStringExtra(MainActivity.USER_NAME);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            StudentPanel.ViewHolder viewHolder = new StudentPanel.ViewHolder();
            viewHolder.courseCode6 = (TextView) convertView.findViewById(R.id.courseCode6);


            viewHolder.enroll = (Button) convertView.findViewById(R.id.enroll);
            viewHolder.enroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    courseList.clear();
                    generateList();
                    String[] vals = courseList.get(position).split(",");
                    String code = vals[0];
                    String course_name = vals[1];
                    ArrayList<String> stringy = db.findOldValues(user_name);
                    String prev = stringy.get(0);
                    if(prev.contains(code+"("+course_name+")")){
                            Toast.makeText(StudentPanel.this, stringy.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(StudentPanel.this, "already enrolled", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        db.updateStudentCourseEnrollment(prev,course_name, code, user_name);
                        Toast.makeText(StudentPanel.this, "course enrolled", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            viewHolder.unenroll= (Button) convertView.findViewById(R.id.unenroll);
            viewHolder.unenroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    courseList.clear();
                    generateList();
                    String[] values = courseList.get(position).split(",");
                    ArrayList<String> stringy = db.findOldValues(user_name);
                    String code = values[0];
                    String course_name = values[1];
                    String prev = stringy.get(0);
                    if(prev.contains(code+"("+course_name+")")){
                        ArrayList<String> stringed = db.findOldValues(user_name);
                        String prev2 = stringed.get(0);
                        String replace = prev2.replace(","+code+"("+course_name+")","");
                        db.deleteStudentCourseEnrollment(replace, user_name);

                    }else{
                        Toast.makeText(StudentPanel.this, "Course is not enrolled", Toast.LENGTH_SHORT).show();
                    }



                }
            });
///

            //viewHolder.viewInfo = (Button) findViewById(R.id.viewInfo);

            convertView.setTag(viewHolder);




            mainViewHolder = (StudentPanel.ViewHolder) convertView.getTag();
            String[] vals = courseList.get(position).split(",");
            String code = vals[0];
            String name = vals[1];
            mainViewHolder.courseCode6.setText(code+","+name);




            return convertView;
        }
    }
    public class ViewHolder{
        TextView courseCode6;
        Button enroll, unenroll;
    }
}