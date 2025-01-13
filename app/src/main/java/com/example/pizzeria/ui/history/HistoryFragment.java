package com.example.pizzeria.ui.history;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.data.api.ApiClient;
import com.example.pizzeria.data.api.ApiService;
import com.example.pizzeria.data.model.LoggedInUser;
import com.example.pizzeria.data.model.Order;
import com.example.pizzeria.data.model.UserSessionManager;
import com.example.pizzeria.ui.login.LoggedInUserView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private HistoryOrderAdapter historyOrderAdapter;
    private List<Order> orderList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.historyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        historyOrderAdapter = new HistoryOrderAdapter(orderList);
        recyclerView.setAdapter(historyOrderAdapter);

        UserSessionManager userSessionManager = UserSessionManager.getInstance(getContext());
        LoggedInUser currentUser = userSessionManager.getLoggedInUser();

        if (currentUser != null) {
            fetchOrders(currentUser.getUserId()); // Wywołaj API tylko, jeśli userId jest poprawny
        } else {
            Toast.makeText(getContext(), "User is not logged in", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void fetchOrders(int userId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getOrders(userId).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderList.clear();
                    orderList.addAll(response.body());
                    historyOrderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load orders", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
