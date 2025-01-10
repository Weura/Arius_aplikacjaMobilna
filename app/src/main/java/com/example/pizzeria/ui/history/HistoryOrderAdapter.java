package com.example.pizzeria.ui.history;

import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            createdAtTextView = itemView.findViewById(R.id.createdAtTextView);
            itemsTextView = itemView.findViewById(R.id.itemsTextView);
        }

        public void bind(Order order) {
            orderIdTextView.setText("Order ID: " + order.getOrder_id());
            createdAtTextView.setText("Date: " + order.getCreated_at());

            StringBuilder itemsBuilder = new StringBuilder();
            for (Item item : order.getItems()) {
                itemsBuilder.append(item.getPizza()).append(" with ");
                itemsBuilder.append(TextUtils.join(", ", item.getToppings()));
                itemsBuilder.append("\n");
            }
            itemsTextView.setText(itemsBuilder.toString());
        }


    }
}

