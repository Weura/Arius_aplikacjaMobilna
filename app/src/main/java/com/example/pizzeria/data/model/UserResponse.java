package com.example.pizzeria.data.model;

public class UserResponse {
    private int id; // Identyfikator użytkownika
    private String username; // Nazwa użytkownika
    private String email; // Adres email użytkownika
    private String message; // Wiadomość zwrotna np. "Rejestracja udana"

    // Konstruktory
    public UserResponse(int id, String username, String email, String message) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.message = message;
    }

    // Gettery i settery dla pól
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
