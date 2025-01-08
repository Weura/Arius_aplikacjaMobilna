package com.example.pizzeria.ui.menuNotLogged;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzeria.data.model.Pizza;

import java.util.List;

public class MenuViewModelNot extends ViewModel {

    private final MutableLiveData<List<Pizza>> pizzaList;

    public MenuViewModelNot() {
        pizzaList = new MutableLiveData<>();
    }

    public LiveData<List<Pizza>> getPizzaList() {
        return pizzaList;
    }

    // Metoda do ustawiania pobranych danych o pizzach (jeśli chcesz to kontrolować w ViewModel)
    public void setPizzaList(List<Pizza> pizzas) {
        pizzaList.setValue(pizzas);
    }
}