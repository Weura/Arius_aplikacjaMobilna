package com.example.pizzeria.data.model;

public class LoginResponse {

    private String userId;
    private String userName;
    private String token; // Jeśli API zwraca token

    // Konstruktor (jeśli będziesz inicjalizować obiekt z wartościami)
    public LoginResponse(String userId, String userName, String token) {
        this.userId = userId;
        this.userName = userName;
        this.token = token;
    }

    // Gettery
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getToken() {
        return token;
    }

    // Settery
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Możesz dodać metodę toString() do łatwego debugowania (opcjonalnie)
    @Override
    public String toString() {
        return "LoginResponse{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}