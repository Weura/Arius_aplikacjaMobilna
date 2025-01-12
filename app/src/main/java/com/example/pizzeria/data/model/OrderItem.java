package com.example.pizzeria.data.model;

import java.util.UUID;

public class OrderItem {
    private String uniqueId; // Unikalny identyfikator
    private Pizza pizza;

    public OrderItem(Pizza pizza) {
        this.pizza = pizza;
        this.uniqueId = UUID.randomUUID().toString(); // Generowanie unikalnego ID
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
}
