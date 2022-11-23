package com.example.group19project;

public class Course {
    String courseName;
    String courseID;
    String instructor;
    String description;
    String capacity;
    String time;

    Course(){

    }

    public Course(String courseName, String courseID, String instructor, String description, String capacity, String time) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.instructor =instructor;
        this.description=description;
        this.capacity=capacity;
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

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
