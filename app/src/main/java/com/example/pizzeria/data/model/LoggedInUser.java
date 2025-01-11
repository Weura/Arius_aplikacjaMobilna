package com.example.pizzeria.data.model;

public class LoggedInUser {
    private int userId;
    private String userName;

    public LoggedInUser(int userId, String userName) {
        this.userId = userId;
        this.userName = userName != null ? userName : "Unknown";
    }

    public int getUserId() {
        return userId;
    }

//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public String getUserName() {
        return userName;
    }

//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

//    public String getDisplayName() {
//        return userName != null ? userName : "Unknown User";
//    }
}

