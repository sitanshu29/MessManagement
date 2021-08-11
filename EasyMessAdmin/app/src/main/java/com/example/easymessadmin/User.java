package com.example.easymessadmin;

public class User {

    private String Name;
    private String Email;
    private String Password;
    private String Course;
    private String Branch;
    private String Mobile;

    public User(String name, String email, String password, String course, String branch, String mobile) {
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.Course = course;
        this.Branch = branch;
        this.Mobile = mobile;
    }

    public User() {


    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getCourse() {
        return Course;
    }

    public String getBranch() {
        return Branch;
    }

    public String getMobile() {
        return Mobile;
    }


}
