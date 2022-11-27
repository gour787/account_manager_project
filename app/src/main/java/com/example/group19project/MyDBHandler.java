package com.example.group19project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "userAccounts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_STUDENT_FIRST_NAME = "firstName";
    private static final String COLUMN_STUDENT_LAST_NAME = "lastName";
    private static final String COLUMN_USER_NAME = "userName";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_ACCOUNT_TYPE = "accountType";
    private static final String COLUMN_USER_COURSES_LIST = "courseList";
    private static final String COLUMN_USER_TIME_BUSY = "courseTime";
    private static final String COURSE_TABLE = "courseTable";
    private static final int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "ProjectDataBase.db";


    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_cmd = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + "INTEGER PRIMARY KEY, " +
                COLUMN_STUDENT_FIRST_NAME + " TEXT, " +
                COLUMN_STUDENT_LAST_NAME + " TEXT, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_USER_PASSWORD + " TEXT, "
                + COLUMN_USER_ACCOUNT_TYPE + " TEXT, "
                + COLUMN_USER_COURSES_LIST + " TEXT, "
                + COLUMN_USER_TIME_BUSY + " TEXT "
                + ")";
        String create_table_2_cmd = "CREATE TABLE " + COURSE_TABLE + "(courseCode TEXT, courseName TEXT,courseInstructor TEXT, courseDescription TEXT, courseCapacity TEXT, courseTime TEXT)";
        db.execSQL(create_table_cmd);
        db.execSQL(create_table_2_cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE);
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null); // returns "cursor" all products from the table
    }


    public Cursor getCourseData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + COURSE_TABLE;
        return db.rawQuery(query, null); // returns "cursor" all products from the table
    }

    public void addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("courseCode", course.getCourseID());
        values.put("courseName", course.getCourseName());
        values.put("courseInstructor", course.getInstructor());
        values.put("courseDescription", course.getDescription());
        values.put("courseCapacity", course.getCapacity());
        values.put("courseTime", course.getTime());

        db.insert(COURSE_TABLE, null, values);
        db.close();
    }

    public void deleteCourse(String courseID, String courseName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(COURSE_TABLE, "courseCode=? AND courseName=?", new String[]{courseID, courseName});
        db.close();

    }


    public void addUser(Information info) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_FIRST_NAME, info.getFirstName());
        values.put(COLUMN_STUDENT_LAST_NAME, info.getLastName());
        values.put(COLUMN_USER_NAME, info.getUserName());
        values.put(COLUMN_USER_PASSWORD, info.getPassword());
        values.put(COLUMN_USER_ACCOUNT_TYPE, info.getAccountType());
        values.put(COLUMN_USER_COURSES_LIST, info.getCourses());
        values.put(COLUMN_USER_TIME_BUSY, info.getTimeBusy());
        db.insert(TABLE_NAME, null, values);
        db.close();


    }

    public Boolean userExists(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userAccounts WHERE userName= ?", new String[]{userName});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean InstructorExists(String instructor) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userAccounts WHERE userName= ? AND accountType LIKE 'Instructor%'"
                , new String[]{instructor});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean StudentExists(String student) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userAccounts WHERE userName= ? AND accountType LIKE 'Student%'"
                , new String[]{student});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }


    public Boolean checkUserNameAndPassword(String userName, String passWord) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userAccounts WHERE userName= ? AND password=?", new String[]{userName, passWord});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public ArrayList<String> findUserDetails(String user) {
        ArrayList<String> findList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE userName=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{user});
        if (cursor.getCount() == 0) {
            // do nothing
        } else {
            while (cursor.moveToNext()) {
                findList.add(cursor.getString(1));
                findList.add(cursor.getString(5));
            }
        }
        cursor.close();
        db.close();
        return findList;
    }


    public Boolean courseExists(String courseID, String courseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM courseTable WHERE courseCode= ? AND courseName=?"
                , new String[]{courseID, courseName});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;

    }


    public void deleteUser(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, userName);
        db.delete(TABLE_NAME, "userName=?", new String[]{userName});
        db.close();
    }

    public void updateCourseCode(String code, String Name, String confirm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("courseCode", confirm);
        db.update("courseTable", values, "courseCode=? AND courseName=?", new String[]{code, Name});

    }

    public void updateCourseName(String name, String confirm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("courseName", confirm);
        db.update("courseTable", values, "courseName=?", new String[]{name});

    }

    public void updateCourseDescription(String name, String code, String Description, String instructor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("courseDescription", Description);
        values.put("courseInstructor", instructor);
        db.update("courseTable", values, "courseName=? AND courseCode=?", new String[]{name, code});

    }

    public void updateCourseTime(String name, String code, String Time, String instructor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("courseTime", Time);
        values.put("courseInstructor", instructor);
        db.update("courseTable", values, "courseName=? AND courseCode=?", new String[]{name, code});

    }

    public void updateCourseCapacity(String name, String code, String Capacity, String instructor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("courseCapacity", Capacity);
        values.put("courseInstructor", instructor);
        db.update("courseTable", values, "courseName=? AND courseCode=?", new String[]{name, code});

    }

    public void updateCourseInstructor(String name, String code, String instructor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("courseInstructor", instructor);
        db.update("courseTable", values, "courseName=? AND courseCode=?", new String[]{name, code});

    }
    public void updateStudentCourseEnrollment(String stringy, String course_name, String code, String user_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("courseList", stringy+code+"("+course_name+")");
        db.update("userAccounts", values, "userName=?", new String[]{user_name});
        db.close();
    }

    public void deleteStudentCourseEnrollment(String stringy, String user_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("courseList", stringy);
        db.update("userAccounts", values, "userName=?", new String[]{user_name});
        db.close();
    }



    public ArrayList<String> findOldValues(String user_name){
        ArrayList<String> findList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE userName=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{user_name});
        if (cursor.getCount() == 0) {
            // do nothing
        } else {
            while (cursor.moveToNext()) {
                findList.add(cursor.getString(6)+",");
            }
        }
        cursor.close();
        db.close();
        return findList;

    }

    public boolean findCourseInstructor(String name, String code, String instructor) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM courseTable WHERE courseCode= ? AND courseName=? AND courseInstructor=?"
                , new String[]{code, name, instructor});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;

    }


    public Cursor findList(String names) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + COURSE_TABLE + " WHERE courseName LIKE ?";
        return db.rawQuery(query, new String[]{names}); // returns "cursor" all products from the table


    }

    public Cursor findLists(String code) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + COURSE_TABLE + " WHERE courseCode LIKE ?";
        return db.rawQuery(query, new String[]{code}); // returns "cursor" all products from the table


    }

    public Cursor findsLists(String name, String code) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + COURSE_TABLE + " WHERE courseCode LIKE ? AND courseName LIKE ?";
        return db.rawQuery(query, new String[]{code, name}); // returns "cursor" all products from the table


    }
}





