package com.example.pizzeria.ui.cart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

        // Initialize RecyclerView for toppings
        List<String> selectedToppings = orderItem.getToppings();
        ToppingListAdapter toppingListAdapter = new ToppingListAdapter(selectedToppings);
        holder.selectedToppingsRecyclerView.setAdapter(toppingListAdapter);

        // Set up the Spinner for toppings
        String[] availableToppings = {"Cheese", "Pepperoni", "Mushrooms", "Onions", "Olives"}; // Example toppings
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(holder.itemView.getContext(),
                android.R.layout.simple_spinner_item, availableToppings);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.toppingSpinner.setAdapter(spinnerAdapter);

        holder.toppingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTopping = parent.getItemAtPosition(position).toString();
                if (!orderItem.getToppings().contains(selectedTopping)) {
                    orderItem.addTopping(selectedTopping); // Add topping to OrderItem
                    holder.selectedToppingsRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Remove item from cart
        holder.removeButton.setOnClickListener(v -> {
            String uniqueIdToRemove = orderItem.getUniqueId();
            int positionToRemove = -1;

            // Find item to remove by unique ID
            for (int i = 0; i < orderItemList.size(); i++) {
                if (orderItemList.get(i).getUniqueId().equals(uniqueIdToRemove)) {
                    positionToRemove = i;
                    break;
                }
            }

            if (positionToRemove != -1) {
                orderItemList.remove(positionToRemove);
                notifyItemRemoved(positionToRemove);

                // Log when the cart is empty
                if (orderItemList.isEmpty()) {
                    Log.d("AdapterPizzaCart", "Koszyk jest pusty");
                }
            }
        });


    }

    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaNameTextView, pizzaPriceTextView, pizzaToppingsTextView;;
        Spinner toppingSpinner;
        RecyclerView selectedToppingsRecyclerView;
        Button removeButton;

        public PizzaViewHolder(View itemView) {
            super(itemView);
            pizzaNameTextView = itemView.findViewById(R.id.pizza_name_cart);
            pizzaPriceTextView = itemView.findViewById(R.id.pizza_price_cart);
            toppingSpinner = itemView.findViewById(R.id.topping_spinner);
            selectedToppingsRecyclerView = itemView.findViewById(R.id.selected_toppings_list); // Ensure this matches the XML ID
            removeButton = itemView.findViewById(R.id.remove_pizza_from_cart_button);
            pizzaToppingsTextView = itemView.findViewById(R.id.pizza_toppings_cart);
        }
    }

    @Override
    public int getItemCount() {
        return orderItemList != null ? orderItemList.size() : 0; // Zwróć 0, jeśli lista jest null
    }
}
