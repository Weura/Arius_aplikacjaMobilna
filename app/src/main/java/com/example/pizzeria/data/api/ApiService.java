package com.example.pizzeria.data.api;
import com.google.gson.JsonObject;
import com.example.pizzeria.data.model.LoginRequest;
import com.example.pizzeria.data.model.LoginResponse;
import com.example.pizzeria.data.model.Order;
import com.example.pizzeria.data.model.OrderRequest;
import com.example.pizzeria.data.model.OrderResponse;
import com.example.pizzeria.data.model.Pizza;
import com.example.pizzeria.data.model.Topping;
import com.example.pizzeria.data.model.UserRequest;
import com.example.pizzeria.data.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

// -------------LOGIN------------------
    // Logowanie
    @POST("/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    // Rejestracja użytkownika
    @POST("/register")
    Call<UserResponse> registerUser(@Body UserRequest userRequest);

// -------------MENU------------------
    // Pobieranie listy pizz
    @GET("/pizzas")
    Call<List<Pizza>> getPizzas();

    // Pobieranie listy dodatków (toppings)
    @GET("/toppings")
    Call<List<Topping>> getToppings();

// -------------HISTORY------------------
    @GET("orders/{user_id}")
    Call<List<Order>> getOrders(@Path("user_id") int userId);

    @POST("/rate")
    Call<JsonObject> submitRating(@Body JsonObject ratingData);



// -------------ORDERING------------------
    @POST("order")
    Call<OrderResponse> createOrder(@Body OrderRequest orderRequest);

}
