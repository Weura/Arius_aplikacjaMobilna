package com.example.pizzeria.data.api;

import com.example.pizzeria.data.model.LoginRequest;
import com.example.pizzeria.data.model.LoginResponse;
import com.example.pizzeria.data.model.UserRequest;
import com.example.pizzeria.data.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("/register")
    static Call<UserResponse> registerUser(@Body UserRequest userRequest) {
        return null;
    }

}
