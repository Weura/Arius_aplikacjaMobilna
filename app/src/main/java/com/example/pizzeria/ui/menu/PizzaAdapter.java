package com.example.pizzeria.ui.menu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.data.model.Pizza;
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

//    TODO: obrazki nadal nie działają w menu
    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        // Get the Pizza object at the current position
        Pizza pizza = pizzaList.get(position);

        // Set the pizza details
        holder.pizzaName.setText(pizza.getName());
        holder.pizzaDetails.setText(pizza.getDetails());
        holder.pizzaPrice.setText(String.format("$%.2f", pizza.getPrice()));
        Log.d("PizzaAdapter", "Image URL: " + pizza.getImageUrl());
        // Load the image using Picasso without placeholders or error handling
        Picasso.get()
                .load(pizza.getImageUrl())  // URL of the image
                .placeholder(R.drawable.loading2)  // Animated GIF or other drawable as placeholder
                .into(holder.pizzaImage);  // Target ImageView

    }


    @Override
    public int getItemCount() {
        return pizzaList.size();  // Zwraca liczbę elementów w liście
    }

    // ViewHolder klasy
    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaName, pizzaDetails, pizzaPrice;
        ImageView pizzaImage;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(R.id.pizza_name);
            pizzaDetails = itemView.findViewById(R.id.pizza_details);
            pizzaPrice = itemView.findViewById(R.id.pizza_price);
            pizzaImage = itemView.findViewById(R.id.pizza_image);
        }
    }
}
