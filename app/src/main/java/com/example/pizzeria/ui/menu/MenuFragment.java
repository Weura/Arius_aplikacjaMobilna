package com.example.pizzeria.ui.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.data.api.ApiClient;
import com.example.pizzeria.data.api.ApiService;
import com.example.pizzeria.data.model.Pizza;
import com.example.pizzeria.databinding.FragmentMenuBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;
    private RecyclerView pizzaRecyclerView;
    private PizzaAdapter pizzaAdapter;
    private List<Pizza> pizzaList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicjalizacja Bindingu
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicjalizacja RecyclerView
        pizzaRecyclerView = binding.pizzaRecyclerView;
        pizzaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Ładowanie danych z API
        loadPizzas();

        return root;
    }

    private void loadPizzas() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getPizzas().enqueue(new Callback<List<Pizza>>() {
            @Override
            public void onResponse(Call<List<Pizza>> call, Response<List<Pizza>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pizzaList = response.body();

                    pizzaAdapter = new PizzaAdapter(pizzaList);
                    pizzaRecyclerView.setAdapter(pizzaAdapter);
                } else {
                    // W przypadku błędnej odpowiedzi z serwera
                    Toast.makeText(getContext(), "Failed to load pizzas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pizza>> call, Throwable t) {
                // Błąd połączenia z serwerem
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
