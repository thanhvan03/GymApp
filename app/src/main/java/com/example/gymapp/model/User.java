package com.example.gymapp.model;

import android.media.Image;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String fullname;
    private String password;
    private String role;
    private String email;
    private Date birthday;
    private String gender;
    private String avatar; // New property for avatar

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(int id, String username, String fullname, String password, String role, String email, Date birthday, String gender, String avatar) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.role = role;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getter and setter for avatar
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    // Other getters and setters

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
