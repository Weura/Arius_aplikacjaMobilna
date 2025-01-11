package com.example.pizzeria.ui.menu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.data.model.LoggedInUser;
import com.example.pizzeria.data.model.Pizza;
import com.example.pizzeria.data.model.UserSessionManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private List<Pizza> pizzaList;

    // Konstruktor
    public PizzaAdapter(List<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating layout pizza_item.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pizza_item, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        UserSessionManager userSessionManager = UserSessionManager.getInstance(holder.itemView.getContext());
        LoggedInUser currentUser = userSessionManager.getLoggedInUser();

        // Get the Pizza object at the current position
        Pizza pizza = pizzaList.get(position);

        // Set the pizza details
        holder.pizzaName.setText(pizza.getName());
        holder.pizzaDetails.setText(pizza.getDetails());
        holder.pizzaPrice.setText(String.format("$%.2f", pizza.getPrice()));

        // Load the image using Picasso without placeholders or error handling
        Picasso.get()
                .load(pizza.getImageUrl())  // URL of the image
                .placeholder(R.drawable.loading2)  // Animated GIF or other drawable as placeholder
                .into(holder.pizzaImage);  // Target ImageView
        // Button seen for logged users
        if (currentUser != null) {
            // User is logged in
            holder.addPizzaToCartButton.setVisibility(View.VISIBLE);  // Show button
        } else {
            // User is not logged in
            holder.addPizzaToCartButton.setVisibility(View.GONE);  // Hide button
        }


    }


    @Override
    public int getItemCount() {
        return pizzaList.size();  // Zwraca liczbę elementów w liście
    }

    // ViewHolder klasy
    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaName, pizzaDetails, pizzaPrice;
        ImageView pizzaImage;
        Button addPizzaToCartButton;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(R.id.pizza_name);
            pizzaDetails = itemView.findViewById(R.id.pizza_details);
            pizzaPrice = itemView.findViewById(R.id.pizza_price);
            pizzaImage = itemView.findViewById(R.id.pizza_image);
            addPizzaToCartButton = itemView.findViewById(R.id.addPizzaToCartButton);
        }
    }
}
