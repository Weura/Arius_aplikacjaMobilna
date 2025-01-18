package com.example.pizzeria.ui.menu;

import android.os.Bundle;
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
import com.example.pizzeria.data.model.Topping;
import com.example.pizzeria.databinding.FragmentMenuBinding;
import com.example.pizzeria.ui.SharedViewModel;

import java.util.ArrayList;
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
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicjalizacja Bindingu
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicjalizacja SharedViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Inicjalizacja RecyclerView dla pizzy
        pizzaRecyclerView = binding.pizzaRecyclerView;
        pizzaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Utwórz pusty adapter i ustaw go
        pizzaAdapter = new PizzaAdapter(pizzaList, sharedViewModel);
        pizzaRecyclerView.setAdapter(pizzaAdapter);

        // Obsługa zmian w wybranych pizzach
        pizzaAdapter.setOnPizzaSelectionChanged(selectedPizzas -> {
            sharedViewModel.updateSelectedPizzas(selectedPizzas);
        });

        sharedViewModel.getSelectedPizzas().observe(getViewLifecycleOwner(), selectedPizzas -> {
            // Gdy dane się zmienią, aktualizujemy widok
            Log.d("MENULogamiks", "Wybrane pizze: " + selectedPizzas);
        });


        // Inicjalizacja RecyclerView dla toppingów
        toppingRecyclerView = binding.toppingRecyclerView;
        toppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Ładowanie danych
        loadPizzas();
        loadToppings();

        return root;
    }

    private void loadToppings() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getToppings().enqueue(new Callback<List<Topping>>() {
            @Override
            public void onResponse(Call<List<Topping>> call, Response<List<Topping>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    toppingList = response.body();
                    toppingAdapter = new ToppingAdapter(toppingList);
                    toppingRecyclerView.setAdapter(toppingAdapter);
                } else {
                    Toast.makeText(getContext(), "Failed to load toppings", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Topping>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPizzas() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getPizzas().enqueue(new Callback<List<Pizza>>() {
            @Override
            public void onResponse(Call<List<Pizza>> call, Response<List<Pizza>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pizzaList = response.body();

                    pizzaAdapter.updatePizzaList(pizzaList); // Zaktualizuj listę w adapterze
                    Log.d("MenuFragment", "Loaded pizzas: " + (pizzaList != null ? pizzaList.size() : 0));
                } else {
                    Toast.makeText(getContext(), "Failed to load pizzas", Toast.LENGTH_SHORT).show();
                    pizzaList = new ArrayList<>();
                }
            }

            @Override
            public void onFailure(Call<List<Pizza>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                pizzaList = new ArrayList<>();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

