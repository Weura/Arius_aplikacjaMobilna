package com.example.pizzeria.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.data.model.Topping;

import java.util.List;

public class ToppingListAdapter extends RecyclerView.Adapter<ToppingListAdapter.ToppingViewHolder> {
    private List<Topping> toppingList;

    public ToppingListAdapter(List<Topping> toppings) {

        this.toppingList = toppings;
    }

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topping_cart, parent, false);
        return new ToppingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        Topping topping = toppingList.get(position);
        holder.toppingNameCart.setText(topping.getName());
        holder.toppingPriceCart.setText("");
    }


    @Override
    public int getItemCount() {
        return toppingList.size();
    }

    public static class ToppingViewHolder extends RecyclerView.ViewHolder {
        TextView toppingNameCart, toppingPriceCart;

        public ToppingViewHolder(View itemView) {
            super(itemView);
            toppingNameCart = itemView.findViewById(R.id.topping_name_cart);
            toppingPriceCart = itemView.findViewById(R.id.topping_price_cart);
        }
    }
}

