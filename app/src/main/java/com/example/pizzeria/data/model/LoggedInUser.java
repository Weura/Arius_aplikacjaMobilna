package com.example.pizzeria.data.model;

public class LoggedInUser {
    private int userId;
    private String userName;  // Może pozostać, ale może być null, jeśli nie ma w odpowiedzi

    public LoggedInUser(int userId, String userName) {
        this.userId = userId;
        this.userName = userName != null ? userName : "Unknown";  // Jeśli brak nazwy, ustawiamy "Unknown"
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return userName != null ? userName : "Unknown User"; // Zwraca nazwę użytkownika lub domyślną wartość
    }
}
