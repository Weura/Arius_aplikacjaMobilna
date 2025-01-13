package com.example.pizzeria.data.model;

import java.util.List;

public class Item {
    private String pizza;
    private List<String> toppings;

    // Getters

    public String getPizza() {
        return pizza;
    }

    public List<String> getToppings() {
        return toppings;
    }
    
    // Setters
    
    public void setPizza() {
        this.pizza = pizza;
    }
    
    public void setToppings() {
        this.toppings = toppings;
    }
}
