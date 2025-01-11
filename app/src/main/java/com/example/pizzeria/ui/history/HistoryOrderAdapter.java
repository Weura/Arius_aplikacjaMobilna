package com.example.pizzeria.ui.history;

import androidx.annotation.NonNull;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.data.model.Item;
import com.example.pizzeria.data.model.Order;

import java.util.List;

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public HistoryOrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView orderIdTextView, createdAtTextView, itemsTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            createdAtTextView = itemView.findViewById(R.id.createdAtTextView); // Date of the order
            itemsTextView = itemView.findViewById(R.id.itemsTextView); // Pizza and toppings details
        }

        public void bind(Order order) {
            int backgroundMiddleColor = ContextCompat.getColor(itemView.getContext(), R.color.background_middle);
            int backgroundDarkColor = ContextCompat.getColor(itemView.getContext(), R.color.background_dark);

            // Create a SpannableStringBuilder for the date
            SpannableStringBuilder dateBuilder = new SpannableStringBuilder("Date: " + order.getCreated_at());

            // Apply bold style to "Date:"
            dateBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Apply color to "Date:"
            dateBuilder.setSpan(new ForegroundColorSpan(backgroundMiddleColor), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            createdAtTextView.setText(dateBuilder);

            // StringBuilder to accumulate pizzas and toppings for the order
            SpannableStringBuilder itemsBuilder = new SpannableStringBuilder();

            // Iterate through each pizza and toppings in the order
            for (Item item : order.getItems()) {
                // Append the pizza name with "Pizza:" in bold
                String pizzaText = "Pizza: " + item.getPizza();
                int pizzaStart = itemsBuilder.length(); // Start position of the "Pizza:"
                itemsBuilder.append(pizzaText);

                // Apply bold style to "Pizza:"
                itemsBuilder.setSpan(new StyleSpan(Typeface.BOLD), pizzaStart, pizzaStart + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                // Apply white color to "Pizza:"
                itemsBuilder.setSpan(new ForegroundColorSpan(backgroundDarkColor), pizzaStart, pizzaStart + 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                // Check if the pizza has toppings, and if so, append them
                if (!item.getToppings().isEmpty()) {
                    itemsBuilder.append(" with ").append(TextUtils.join(", ", item.getToppings()));
                } else {
                    itemsBuilder.append(" with no toppings");
                }
                // New line for next item
                itemsBuilder.append("\n");
            }

            // Set the text view to show the pizza and toppings information
            itemsTextView.setText(itemsBuilder);
        }
    }
}

