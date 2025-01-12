package com.example.pizzeria.data.model;

public class OrderItem {
    private int pizzaId;  // Przechowujemy tylko id pizzy
    private int quantity; // Ilość wybranych pizz

    public OrderItem(int pizzaId, int quantity) {
        this.pizzaId = pizzaId;
        this.quantity = quantity;
    }

    public int getPizzaId() {
        return pizzaId;
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
        if (quantity >= 0) {  // Upewnij się, że ilość jest nieujemna
            this.quantity = quantity;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OrderItem that = (OrderItem) obj;
        return pizzaId == that.pizzaId; // Porównujemy tylko identyfikator pizzy
    }

    @Override
    public int hashCode() {
        return pizzaId; // Unikalny hashCode na podstawie id pizzy
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "pizzaId=" + pizzaId +
                ", quantity=" + quantity +
                '}';
    }
}
