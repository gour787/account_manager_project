package com.example.group19project;

public class Information {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String accountType;
    private String courses;
    private String timeBusy;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }


    public String getCourses() {
        return courses;
    }

    public String getTimeBusy() {
        return timeBusy;
    }



    public Information(){

    }


    public Information(String firstName, String lastName, String userName, String password, String accountType, String courses, String timeBusy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.accountType = accountType;
        this.courses = courses;
        this.timeBusy=timeBusy;

    }
}
