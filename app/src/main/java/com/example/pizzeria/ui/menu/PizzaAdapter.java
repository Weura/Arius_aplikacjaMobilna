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
import com.example.pizzeria.data.model.OrderItem;
import com.example.pizzeria.data.model.Pizza;
import com.example.pizzeria.data.model.UserSessionManager;
import com.example.pizzeria.ui.SharedViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private List<Pizza> pizzaList;
    private SharedViewModel sharedViewModel;
    private List<OrderItem> selectedPizzas = new ArrayList<>(); // Lista zamówień z ilościami

    public PizzaAdapter(List<Pizza> pizzaList, SharedViewModel sharedViewModel) {
        this.pizzaList = pizzaList;
        this.sharedViewModel = sharedViewModel;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pizza_item, parent, false);
        return new PizzaViewHolder(view);
    }

    private OrderItem findOrderItemByPizzaId(int pizzaId) {
        for (OrderItem orderItem : selectedPizzas) {
            if (orderItem.getPizza().getId() == pizzaId) {
                return orderItem;
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        UserSessionManager userSessionManager = UserSessionManager.getInstance(holder.itemView.getContext());
        LoggedInUser currentUser = userSessionManager.getLoggedInUser();

        Pizza pizza = pizzaList.get(position);

        holder.pizzaName.setText(pizza.getName());
        holder.pizzaDetails.setText(pizza.getDetails());
        holder.pizzaPrice.setText(String.format("$%.2f", pizza.getPrice()));
        Picasso.get()
                .load(pizza.getImageUrl())
                .placeholder(R.drawable.loading2)
                .into(holder.pizzaImage);

        // Button seen for logged users
        if (currentUser != null) {
            // User is logged in
            holder.addPizzaToCartButton.setVisibility(View.VISIBLE);  // Show button
        } else {
            // User is not logged in
            holder.addPizzaToCartButton.setVisibility(View.GONE);  // Hide button
        }

        holder.addPizzaToCartButton.setOnClickListener(v -> {
            if (pizza != null) {
                selectedPizzas.add(new OrderItem(pizza));

                // Logowanie wszystkich pizzy w zamówieniu
                StringBuilder orderDetails = new StringBuilder("Selected Pizzas: ");

                for (OrderItem item : selectedPizzas) {
                    orderDetails.append("Pizza ID: ")
                            .append(item.getUniqueId()) // Wyświetlenie unikalnego ID
                            .append(", Pizza Name: ")
                            .append(item.getPizza().getName())
                            .append(", ");
                }

                Log.d("PizzaAdapter", "Selected Pizzas: " + orderDetails.toString());

                // Aktualizowanie LiveData w SharedViewModel
                if (sharedViewModel != null) {
                    sharedViewModel.updateSelectedPizzas(new ArrayList<>(selectedPizzas));
                } else {
                    Log.e("PizzaAdapter", "SharedViewModel is null");
                }

                // Powiadomienie adaptera o zmianach (aktualizowanie widoku)
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzaList != null ? pizzaList.size() : 0; // Zwróć 0, jeśli lista jest null
    }

    public List<OrderItem> getSelectedPizzas() {
        return new ArrayList<>(selectedPizzas);
    }

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
            addPizzaToCartButton = itemView.findViewById(R.id.add_pizza_to_cart_button);
        }
    }

    private OnPizzaSelectionChangedListener selectionChangedListener;

    public interface OnPizzaSelectionChangedListener {
        void onSelectionChanged(List<OrderItem> selectedPizzas);
    }

    public void setOnPizzaSelectionChanged(OnPizzaSelectionChangedListener listener) {
        this.selectionChangedListener = listener;
    }

    // Wywołanie callback w momencie zmiany
    private void notifySelectionChanged() {
        if (selectionChangedListener != null) {
            selectionChangedListener.onSelectionChanged(new ArrayList<>(selectedPizzas));
        }
    }

    public void updatePizzaList(List<Pizza> newPizzaList) {
        this.pizzaList = newPizzaList != null ? newPizzaList : new ArrayList<>();
        notifyDataSetChanged();
    }
}

