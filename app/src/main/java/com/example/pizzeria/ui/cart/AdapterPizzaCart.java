package com.example.pizzeria.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.data.model.Pizza;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;

public class AdapterPizzaCart extends RecyclerView.Adapter<AdapterPizzaCart.PizzaViewHolder> {
    private List<Pizza> pizzaList;

    public AdapterPizzaCart(List<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza_cart, parent, false);
        return new PizzaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Pizza pizza = pizzaList.get(position);
        holder.pizzaNameTextView.setText(pizza.getName()); // Zakładając, że masz metodę getName() w klasie Pizza
        holder.pizzaPriceTextView.setText(String.format("$%.2f", pizza.getPrice())); // Zakładając, że masz metodę getPrice()
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaNameTextView, pizzaPriceTextView;

        public PizzaViewHolder(View itemView) {
            super(itemView);
            pizzaNameTextView = itemView.findViewById(R.id.pizza_name);
            pizzaPriceTextView = itemView.findViewById(R.id.pizza_price);
        }
    }
}

