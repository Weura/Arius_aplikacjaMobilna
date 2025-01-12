package com.example.pizzeria.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzeria.data.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<List<OrderItem>> selectedPizzas = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<OrderItem>> getSelectedPizzas() {
        return selectedPizzas;
    }

    public void updateSelectedPizzas(List<OrderItem> pizzas) {
        // Log przed zmianą
        Log.d("SharedViewModel", "Before updating: " + pizzas.toString());

        selectedPizzas.setValue(pizzas);

        // Log po zmianie
        Log.d("SharedViewModel", "Updated selected pizzas: " + pizzas.toString());
    }
}

