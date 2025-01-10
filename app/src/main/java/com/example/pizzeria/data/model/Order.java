package com.example.pizzeria.data.model;

import java.util.List;

public class Order {
    private int order_id;
    private String created_at;
    private List<Item> items;

    // Getters

    public int getOrder_id() {
        return order_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public List<Item> getItems() {
        return items;
    }

    // Setters

    public void setOrder_id() {
        this.order_id = order_id;
    }

    public void setCreated_at() {
        this.created_at = created_at;
    }

    public void setItems() {
        this.items = items;
    }
}
