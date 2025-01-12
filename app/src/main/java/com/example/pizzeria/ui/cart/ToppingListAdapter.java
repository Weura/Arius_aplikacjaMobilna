package com.example.pizzeria.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;

import java.util.List;

public class ToppingListAdapter extends RecyclerView.Adapter<ToppingListAdapter.ToppingViewHolder> {
    private List<String> toppings;

    public ToppingListAdapter(List<String> toppings) {
        this.toppings = toppings;
    }

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topping_cart, parent, false);
        return new ToppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        String topping = toppings.get(position);
        holder.toppingTextView.setText(topping);
    }

    @Override
    public int getItemCount() {
        return toppings.size();
    }

    public static class ToppingViewHolder extends RecyclerView.ViewHolder {
        TextView toppingTextView;

        public ToppingViewHolder(View itemView) {
            super(itemView);
            toppingTextView = itemView.findViewById(R.id.topping_name); // Ensure this ID exists in `item_topping.xml`
        }
    }
}

