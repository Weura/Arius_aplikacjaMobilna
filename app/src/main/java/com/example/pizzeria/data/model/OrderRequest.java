package com.example.pizzeria.data.model;

import java.util.List;

public class OrderRequest {
    private int user_id;
    private String location;
    private String delivery_time;
    private List<OrderItem> items;

    // Constructor
    public OrderRequest(int user_id, List<OrderItem> items) {
        this.user_id = user_id;
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "user_id=" + user_id +
                ", items=" + items +
                '}';
    }

    // Inner static class for an OrderItem
    public static class OrderItem {
        private int pizza_id;
        private List<Integer> topping_ids;

        // Constructor
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
    }
}
