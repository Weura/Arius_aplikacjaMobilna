package com.example.pizzeria.ui.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.data.model.Topping;

import java.util.List;

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.ToppingViewHolder> {

    private List<Topping> toppingList;

    // Konstruktor
    public ToppingAdapter(List<Topping> toppingList) {
        this.toppingList = toppingList;
    }

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topping_item, parent, false);
        return new ToppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        // Pobieranie obiektu Topping na podstawie pozycji
        Topping topping = toppingList.get(position);
        holder.toppingName.setText(topping.getName());
        holder.toppingPrice.setText(String.format("$%.2f", topping.getPrice()));
    }

    @Override
    public int getItemCount() {
        return toppingList.size();  // Zwraca liczbę elementów w liście
    }

    // ViewHolder klasy
    public static class ToppingViewHolder extends RecyclerView.ViewHolder {
        TextView toppingName, toppingPrice;

        public ToppingViewHolder(@NonNull View itemView) {
            super(itemView);
            toppingName = itemView.findViewById(R.id.topping_name);
            toppingPrice = itemView.findViewById(R.id.topping_price);
        }
    }
}
