package com.example.pizzeria.ui.cart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.data.model.OrderItem;
import com.example.pizzeria.data.model.Pizza;
import com.example.pizzeria.data.model.Topping;
import com.example.pizzeria.ui.SharedViewModel;
import com.example.pizzeria.ui.cart.ToppingListAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterPizzaCart extends RecyclerView.Adapter<AdapterPizzaCart.PizzaViewHolderCart> {
    private List<OrderItem> pizzaList; // Lista OrderItem, nie Pizza
    private List<Topping> toppingsList;

    public AdapterPizzaCart(List<OrderItem> pizzaList, List<Topping> toppingsList) {
        this.pizzaList = pizzaList != null ? pizzaList : new ArrayList<>();
        this.toppingsList = toppingsList != null ? toppingsList : new ArrayList<>();
    }

    @NonNull
    @Override
    public PizzaViewHolderCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pizza_cart, parent, false);
        return new PizzaViewHolderCart(view);
    }

    // Method to update the pizza list
    public void updatePizzaList(List<OrderItem> updatedPizzaList) {
        this.pizzaList = updatedPizzaList;
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolderCart holder, int position) {
        if (position < 0 || position >= pizzaList.size()) {
            // Jeśli indeks jest nieprawidłowy, po prostu zwróć
            Log.e("AdapterPizzaCart", "Invalid position: " + position);
            return;
        }

        OrderItem orderItem = pizzaList.get(position);
        Pizza pizzaItem = orderItem.getPizza(); // Pobranie pizzy z OrderItem

        holder.pizzaNameTextView.setText(pizzaItem .getName()); // Nazwa pizzy
        holder.pizzaPriceTextView.setText(String.format("$%.2f", pizzaItem .getPrice())); // Cena pizzy (jednostkowa)

        // Set up the adapter for displaying all toppings under each pizza
        ToppingListAdapter toppingAdapter = new ToppingListAdapter(toppingsList);
        holder.toppingsRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.toppingsRecyclerView.setAdapter(toppingAdapter);

        holder.removeButton.setOnClickListener(v -> {
            String uniqueIdToRemove = orderItem.getUniqueId(); // Pobieramy unikalny ID elementu do usunięcia

            // Usuwanie elementu na podstawie uniqueId
            for (int i = 0; i < pizzaList.size(); i++) {
                if (pizzaList.get(i).getUniqueId().equals(uniqueIdToRemove)) {
                    pizzaList.remove(i); // Usuwamy element
                    notifyItemRemoved(i); // Powiadomienie RecyclerView o zmianach
                    break; // Przerywamy, bo usunięto element
                }
            }

            // Jeżeli lista jest pusta, wyświetl odpowiedni komunikat
            if (pizzaList.isEmpty()) {
                Log.d("AdapterPizzaCart", "Koszyk jest pusty");
                // Można tutaj np. pokazać pusty widok lub przekierować użytkownika
            }
        });


    }

    public static class PizzaViewHolderCart extends RecyclerView.ViewHolder {
        TextView pizzaNameTextView, pizzaPriceTextView;
        RecyclerView toppingsRecyclerView;
        Button removeButton, removeToppingButton, addToppingButton;

        public PizzaViewHolderCart(View itemView) {
            super(itemView);
            pizzaNameTextView = itemView.findViewById(R.id.pizza_name_cart);
            pizzaPriceTextView = itemView.findViewById(R.id.pizza_price_cart);
            toppingsRecyclerView = itemView.findViewById(R.id.selected_toppings_list);
            removeButton = itemView.findViewById(R.id.remove_pizza_from_cart_button);

            removeToppingButton = itemView.findViewById((R.id.remove_topping_from_cart_button));
            addToppingButton = itemView.findViewById((R.id.add_topping_to_cart_button));
        }
    }

    @Override
    public int getItemCount() {
        return pizzaList != null ? pizzaList.size() : 0; // Zwróć 0, jeśli lista jest null
    }
}
