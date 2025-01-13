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

    // Metoda do uzyskania listy wybranych pizz
    public LiveData<List<OrderItem>> getSelectedPizzas() {
        return selectedPizzas;
    }

    // Metoda do dodawania pizzy
    public void addPizza(OrderItem orderItem) {
        List<OrderItem> currentList = selectedPizzas.getValue();
        if (currentList != null) {
            currentList.add(orderItem); // Dodajemy pizzę do listy
            selectedPizzas.setValue(currentList); // Zaktualizuj LiveData
        }
    }

    // Metoda do usuwania pizzy
    public void removePizza(String uniqueId) {
        List<OrderItem> pizzas = selectedPizzas.getValue();
        if (pizzas != null) {
            for (OrderItem item : pizzas) {
                if (item.getUniqueId().equals(uniqueId)) {
                    pizzas.remove(item);
                    break; // Usuwamy pierwszy znaleziony element
                }
            }
            selectedPizzas.setValue(pizzas);
        }
    }

    public void clearPizzas() {
        selectedPizzas.setValue(new ArrayList<>());
    }

    // Metoda do aktualizacji całej listy pizz (może być przydatna, gdy lista jest resetowana)
    public void updateSelectedPizzas(List<OrderItem> pizzas) {
        List<OrderItem> currentList = selectedPizzas.getValue();
        if (currentList != null) {
            currentList.clear(); // Usuwamy wszystkie obecne pizze
            currentList.addAll(pizzas); // Dodajemy nowe pizze
            selectedPizzas.setValue(currentList); // Zaktualizuj LiveData
        }
    }
}

