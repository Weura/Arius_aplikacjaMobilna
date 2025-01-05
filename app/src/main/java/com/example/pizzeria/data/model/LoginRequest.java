package com.example.pizzeria.data.model;

public class LoginRequest {

    private String username;
    private String password;

    // Konstruktor
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Gettery
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Settery
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Możesz także dodać metodę toString, jeśli chcesz mieć możliwość szybkiego podglądu zawartości obiektu
    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
