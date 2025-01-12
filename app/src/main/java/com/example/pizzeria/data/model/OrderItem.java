package com.example.pizzeria.data.model;

public class OrderItem {
    private Pizza pizza;  // Zmieniamy na przechowywanie obiektu klasy Pizza
    private int quantity; // Ilość wybranych pizz

    public OrderItem(Pizza pizza, int quantity) {
        this.pizza = pizza;
        this.quantity = quantity;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OrderItem that = (OrderItem) obj;
        return pizza.equals(that.pizza); // Porównujemy teraz obiekty pizza
    }

    @Override
    public int hashCode() {
        return pizza.hashCode(); // HashCode na podstawie obiektu pizza
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "pizza=" + pizza.getName() +
                ", quantity=" + quantity +
                '}';
    }
}
