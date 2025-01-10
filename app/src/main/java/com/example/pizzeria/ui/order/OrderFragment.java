package com.example.pizzeria.ui.order;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pizzeria.R;
import com.example.pizzeria.data.api.ApiClient;
import com.example.pizzeria.data.api.ApiService;
import com.example.pizzeria.data.model.OrderRequest;
import com.example.pizzeria.data.model.OrderResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    private EditText pizzaIdInput;
    private EditText toppingIdsInput;
    private Button submitOrderButton;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        pizzaIdInput = view.findViewById(R.id.pizza_id_input);
        toppingIdsInput = view.findViewById(R.id.topping_ids_input);
        submitOrderButton = view.findViewById(R.id.submit_order_button);

        // Initialize the API Service using ApiClient
        apiService = ApiClient.getClient().create(ApiService.class);

        submitOrderButton.setOnClickListener(v -> submitOrder());

        return view;
    }

    private void submitOrder() {
        // Fetch user ID from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(getContext(), "User ID not found. Please log in.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("APIlogiks", "user id: " + userId);

        // Validate and prepare order data
        String pizzaIdText = pizzaIdInput.getText().toString().trim();
        if (pizzaIdText.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a valid Pizza ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        int pizzaId = Integer.parseInt(pizzaIdText);

        List<Integer> toppingIds = new ArrayList<>();
        String toppingsInput = toppingIdsInput.getText().toString().trim();
        if (!toppingsInput.isEmpty()) {
            String[] toppingIdsStr = toppingsInput.split(",");
            for (String toppingId : toppingIdsStr) {
                try {
                    toppingIds.add(Integer.parseInt(toppingId.trim()));
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid Topping ID: " + toppingId, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        // Prepare order items
        List<OrderRequest.OrderItem> items = Collections.singletonList(new OrderRequest.OrderItem(pizzaId, toppingIds));
        OrderRequest orderRequest = new OrderRequest(userId, items);

        // Make the API call
        apiService.createOrder(orderRequest).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("APIlogiks", "poszlo: " + response.body());
                    Toast.makeText(getContext(), "Order placed successfully! Order ID: " + response.body().getOrderId(), Toast.LENGTH_LONG).show();
                } else {
                    Log.d("APIlogiks", "nie poszlo: " + response.body());
                    String errorMessage = response.errorBody() != null ? response.errorBody().toString() : "Unknown error";
                    Toast.makeText(getContext(), "Failed to place order: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.d("APIlogiks", "onFailure");
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
