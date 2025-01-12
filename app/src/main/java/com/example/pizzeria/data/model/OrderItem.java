package com.example.pizzeria.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderItem {
    private String uniqueId; // Unikalny identyfikator
    private Pizza pizza;
    private List<String> toppings;

    public OrderItem(Pizza pizza) {
        this.pizza = pizza;
        this.uniqueId = UUID.randomUUID().toString(); // Generowanie unikalnego ID
        this.toppings = new ArrayList<>();
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public int getPizzaId() {
        return pizza != null ? pizza.getId() : null;
    }

//    public void addTopping(String topping) {
//        toppings.add(topping);
//    }

    // Getter and Setter for toppings
    public List<String> getToppings() {
        return toppings;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }

    // Add topping method
    public void addTopping(String topping) {
        if (toppings == null) {
            toppings = new ArrayList<>();
        }
        toppings.add(topping);
    }

}
