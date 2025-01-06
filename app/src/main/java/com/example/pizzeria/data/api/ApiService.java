package com.example.pizzeria.data.api;

import com.example.pizzeria.data.model.LoginRequest;
import com.example.pizzeria.data.model.LoginResponse;
import com.example.pizzeria.data.model.Pizza;
import com.example.pizzeria.data.model.Topping;
import com.example.pizzeria.data.model.UserRequest;
import com.example.pizzeria.data.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("/register")
    static Call<UserResponse> registerUser(@Body UserRequest userRequest) {
        return null;
    }

    // Pobieranie listy pizz
    @GET("/pizzas")
    Call<List<Pizza>> getPizzas();

    // Pobieranie listy dodatków (toppings)
    @GET("/toppings")
    Call<List<Topping>> getToppings();
}
