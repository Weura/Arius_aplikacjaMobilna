package com.example.pizzeria.ui.cart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.data.model.OrderItem;
import com.example.pizzeria.data.model.Pizza;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterPizzaCart extends RecyclerView.Adapter<AdapterPizzaCart.PizzaViewHolder> {
    private List<OrderItem> orderItemList; // Lista OrderItem, nie Pizza

    public AdapterPizzaCart(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList != null ? orderItemList : new ArrayList<>();
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza_cart, parent, false);
        return new PizzaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        OrderItem orderItem = orderItemList.get(position);
        Pizza pizza = orderItem.getPizza(); // Pobranie pizzy z OrderItem
        int quantity = orderItem.getQuantity();

        holder.pizzaNameTextView.setText(pizza.getName()); // Nazwa pizzy
        holder.pizzaPriceTextView.setText(String.format("$%.2f", pizza.getPrice())); // Cena pizzy (jednostkowa)

        // Możesz dodać logikę dla przycisków, np. przycisk "+" i "-" do zmiany ilości
        holder.removeButton.setOnClickListener(v -> {
            if (quantity > 1) {
                orderItem.decrementQuantity(); // Zmniejszenie ilości
                notifyItemChanged(position); // Aktualizacja widoku
            } else {
                orderItemList.remove(position); // Usuwanie z listy
                notifyItemRemoved(position); // Aktualizacja widoku
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaNameTextView, pizzaPriceTextView;
        Button removeButton;

        public PizzaViewHolder(View itemView) {
            super(itemView);
            pizzaNameTextView = itemView.findViewById(R.id.pizza_name_cart);
            pizzaPriceTextView = itemView.findViewById(R.id.pizza_price_cart);
            removeButton = itemView.findViewById(R.id.remove_pizza_from_cart_button);
        }
    }

}
