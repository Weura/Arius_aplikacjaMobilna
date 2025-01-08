package com.example.pizzeria.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.data.api.ApiClient;
import com.example.pizzeria.data.api.ApiService;
import com.example.pizzeria.data.model.Pizza;
import com.example.pizzeria.data.model.Topping;
import com.example.pizzeria.databinding.FragmentMenuBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;
    private RecyclerView pizzaRecyclerView;
    private PizzaAdapter pizzaAdapter;
    private List<Pizza> pizzaList;
    private RecyclerView toppingRecyclerView;
    private ToppingAdapter toppingAdapter;
    private List<Topping> toppingList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicjalizacja Bindingu
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicjalizacja RecyclerView dla pizzy
        pizzaRecyclerView = binding.pizzaRecyclerView;
        pizzaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicjalizacja RecyclerView dla toppingów
        toppingRecyclerView = binding.toppingRecyclerView;
        toppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Ładowanie danych o pizzach
        loadPizzas();

        // Ładowanie danych o toppingach
        loadToppings();

        return root;
    }

    private void loadToppings() {
        // Inicjalizacja API Service
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Wykonanie zapytania o toppingi
        apiService.getToppings().enqueue(new Callback<List<Topping>>() {
            @Override
            public void onResponse(Call<List<Topping>> call, Response<List<Topping>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    toppingList = response.body();
                    toppingAdapter = new ToppingAdapter(toppingList);
                    toppingRecyclerView.setAdapter(toppingAdapter);
                } else {
                    // Jeśli odpowiedź z backendu jest błędna
                    Toast.makeText(getContext(), "Failed to load toppings", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Topping>> call, Throwable t) {
                // Obsługa błędu połączenia
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPizzas() {
        // Inicjalizacja API Service
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Log the base URL and the endpoint to check the full URL
        String baseUrl = ApiClient.getClient().baseUrl().toString();
        Log.d("API Request", "Request URL: " + baseUrl + "pizzas"); // Log the full URL

        // Wykonanie zapytania o pizze
        apiService.getPizzas().enqueue(new Callback<List<Pizza>>() {
            @Override
            public void onResponse(Call<List<Pizza>> call, Response<List<Pizza>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pizzaList = response.body();

                    // Log the number of pizzas and each pizza's image URL
                    Log.d("API Response", "Pizzas loaded successfully: " + pizzaList.size() + " pizzas");

                    for (Pizza pizza : pizzaList) {
                        Log.d("Pizza Image URL", "Pizza name: " + pizza.getName() + ", Image URL: " + pizza.getImageUrl());
                    }

                    pizzaAdapter = new PizzaAdapter(pizzaList);
                    pizzaRecyclerView.setAdapter(pizzaAdapter);
                } else {
                    // Log the failure in the response
                    Log.e("API Response", "Failed to load pizzas. Response code: " + response.code());
                    Toast.makeText(getContext(), "Failed to load pizzas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pizza>> call, Throwable t) {
                // Log the failure reason
                Log.e("API Request", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
