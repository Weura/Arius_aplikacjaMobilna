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
import com.example.pizzeria.ui.SharedViewModel;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterPizzaCart extends RecyclerView.Adapter<AdapterPizzaCart.PizzaViewHolder> {
    private List<OrderItem> orderItemList; // Lista OrderItem, nie Pizza
    private SharedViewModel sharedViewModel;

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
        if (position < 0 || position >= orderItemList.size()) {
            // Jeśli indeks jest nieprawidłowy, po prostu zwróć
            Log.e("AdapterPizzaCart", "Invalid position: " + position);
            return;
        }

        OrderItem orderItem = orderItemList.get(position);
        Pizza pizza = orderItem.getPizza(); // Pobranie pizzy z OrderItem

        holder.pizzaNameTextView.setText(pizza.getName()); // Nazwa pizzy
        holder.pizzaPriceTextView.setText(String.format("$%.2f", pizza.getPrice())); // Cena pizzy (jednostkowa)

        holder.removeButton.setOnClickListener(v -> {
            String uniqueIdToRemove = orderItem.getUniqueId(); // Pobieramy unikalny ID elementu do usunięcia

            // Usuwanie elementu na podstawie uniqueId
            for (int i = 0; i < orderItemList.size(); i++) {
                if (orderItemList.get(i).getUniqueId().equals(uniqueIdToRemove)) {
                    orderItemList.remove(i); // Usuwamy element
                    notifyItemRemoved(i); // Powiadomienie RecyclerView o zmianach
                    break; // Przerywamy, bo usunięto element
                }
            }

            // Jeżeli lista jest pusta, wyświetl odpowiedni komunikat
            if (orderItemList.isEmpty()) {
                Log.d("AdapterPizzaCart", "Koszyk jest pusty");
                // Można tutaj np. pokazać pusty widok lub przekierować użytkownika
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderItemList != null ? orderItemList.size() : 0; // Zwróć 0, jeśli lista jest null
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
