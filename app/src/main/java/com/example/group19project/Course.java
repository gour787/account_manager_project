package com.example.group19project;

public class Course {
    String courseName;
    String courseID;
    Course(){

    }

    public Course(String courseName, String courseID) {
        this.courseName = courseName;
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }


}
