package com.example.pizzeria.data.model;

import java.util.List;

public class OrderRequest {
    private int user_id;
    private String location;
    private String delivery_time;
    private List<OrderItem> items;

    // Konstruktor z wszystkimi wymaganymi parametrami
    public OrderRequest(int user_id, String location, String delivery_time, List<OrderItem> items) {
        this.user_id = user_id;
        this.location = location;
        this.delivery_time = delivery_time;
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "user_id=" + user_id +
                ", location='" + location + '\'' +
                ", delivery_time='" + delivery_time + '\'' +
                ", items=" + items +
                '}';
    }

    // Gettery i Settery
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    // Klasa wewnętrzna OrderItem
    public static class OrderItem {
        private int pizza_id;
        private List<Integer> topping_ids;

        // Konstruktor OrderItem
        public OrderItem(int pizza_id, List<Integer> topping_ids) {
            this.pizza_id = pizza_id;
            this.topping_ids = topping_ids;
        }

        @Override
        public String toString() {
            return "OrderItem{" +
                    "pizza_id=" + pizza_id +
                    ", topping_ids=" + topping_ids +
                    '}';
        }

        // Gettery i Settery
        public int getPizza_id() {
            return pizza_id;
        }

        public void setPizza_id(int pizza_id) {
            this.pizza_id = pizza_id;
        }

        public List<Integer> getTopping_ids() {
            return topping_ids;
        }

        public void setTopping_ids(List<Integer> topping_ids) {
            this.topping_ids = topping_ids;
        }
    }
}
