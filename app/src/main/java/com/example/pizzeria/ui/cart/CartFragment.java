package com.example.pizzeria.ui.cart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import com.example.pizzeria.R;
import com.example.pizzeria.RateActivity;
import com.example.pizzeria.data.api.ApiClient;
import com.example.pizzeria.data.api.ApiService;
import com.example.pizzeria.data.model.OrderItem;
import com.example.pizzeria.data.model.OrderRequest;
import com.example.pizzeria.data.model.OrderResponse;

import com.example.pizzeria.ui.SharedViewModel;
import com.example.pizzeria.ui.login.LoginActivity;
import com.example.pizzeria.ui.menu.MenuFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    private EditText pizzaIdInput;
    private EditText toppingIdsInput;

    private RecyclerView recyclerView;
    private AdapterPizzaCart pizzaCartAdapter;
    private SharedViewModel sharedViewModel;

    // Location
    private EditText locationInput;
    private Button getLocationButton;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;


    private Spinner hourSpinner, minuteSpinner, daySpinner, monthSpinner;
    private Button submitOrderButton, addMoreOrdersPizzaButton;
    private ApiService apiService;
    private NavController navController;

    private static final String[] HOURS = new String[24]; // 0-23
    private static final String[] MINUTES = new String[60]; // 0-59
    private static final String[] DAYS = new String[31]; // 1-31
    private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};


//    @Override
//    public void onCreate (Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Inicjalizacja ViewModel
//        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        sharedViewModel.getSelectedPizzas().observe(getViewLifecycleOwner(), this::updateCart);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize the ViewModel here instead of onCreate
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Now it's safe to observe LiveData as the fragment's view is created
        sharedViewModel.getSelectedPizzas().observe(getViewLifecycleOwner(), this::updateCart);

        pizzaIdInput = view.findViewById(R.id.pizza_id_input);
        toppingIdsInput = view.findViewById(R.id.topping_ids_input);

        recyclerView = view.findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addMoreOrdersPizzaButton = view.findViewById(R.id.add_more_pizzas_order_button);

        hourSpinner = view.findViewById(R.id.hour_spinner);
        minuteSpinner = view.findViewById(R.id.minute_spinner);
        daySpinner = view.findViewById(R.id.day_spinner);
        monthSpinner = view.findViewById(R.id.month_spinner);

        // Initialize the Spinner data
        for (int i = 0; i < 24; i++) {
            HOURS[i] = String.format("%02d", i);
        }
        for (int i = 0; i < 60; i++) {
            MINUTES[i] = String.format("%02d", i);
        }
        for (int i = 0; i < 31; i++) {
            DAYS[i] = String.format("%02d", i + 1);
        }

        // Set the adapters for each spinner
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, HOURS);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(hourAdapter);

        ArrayAdapter<String> minuteAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, MINUTES);
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteSpinner.setAdapter(minuteAdapter);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, DAYS);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, MONTHS);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        submitOrderButton = view.findViewById(R.id.submit_order_button);

        // Location
        locationInput = view.findViewById(R.id.location_input);
        getLocationButton = view.findViewById(R.id.get_location_button);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        // Set OnClickListener for the "Delivery Time Order" button
        getLocationButton.setOnClickListener(v -> checkLocationPermission());


        // Initialize the API Service using ApiClient
        apiService = ApiClient.getClient().create(ApiService.class);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        // Set OnClickListener for the "Submit Order" button
        submitOrderButton.setOnClickListener(v -> submitOrder());
        // Set OnClickListener for the "Add More Pizzas" button
        addMoreOrdersPizzaButton.setOnClickListener(v -> openMenuFragment());

        return view;
    }

    private void updateCart(List<OrderItem> selectedPizzas) {
        // Aktualizuj dane w adapterze
        if (pizzaCartAdapter == null) {
            pizzaCartAdapter = new AdapterPizzaCart(selectedPizzas);
            recyclerView.setAdapter(pizzaCartAdapter);
        } else {
            pizzaCartAdapter.notifyDataSetChanged();
        }
    }

    private void openMenuFragment() {
        // Get the current fragment in the navigation controller's back stack
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);

        // Check if we're already on the MenuFragment
        if (!(currentFragment instanceof MenuFragment)) {
            // Only navigate if we're not already on the MenuFragment
            navController.navigate(R.id.navigation_menu);
        }
    }


    //    // Obsługa odpowiedzi użytkownika
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Użytkownik udzielił uprawnień, uzyskujemy lokalizację
                getLocation();
            } else {
                // Uprawnienia zostały odrzucone
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {
        // Sprawdź uprawnienia
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Jeżeli brak uprawnień, poproś o nie
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Pobierz lokalizację
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Wyświetl lokalizację w EditText
                            String locationText = "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude();
                            locationInput.setText(locationText);
                        } else {
                            Toast.makeText(getContext(), "Unable to fetch location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Funkcja do sprawdzania uprawnień i żądania ich, jeśli są wymagane
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Nie ma uprawnień, trzeba poprosić użytkownika
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Mamy uprawnienia, możemy uzyskać lokalizację
            getLocation();
        }
    }

    // Funkcja do pobierania lokalizacji
    private void getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Zwrócono lokalizację
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                // Konwertowanie współrzędnych na adres
                                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    if (addresses != null && !addresses.isEmpty()) {
                                        Address address = addresses.get(0);

                                        // Składamy adres w formacie przyjaznym użytkownikowi
                                        String humanFriendlyAddress = address.getAddressLine(0);

                                        // Ustawiamy w EditText
                                        locationInput.setText(humanFriendlyAddress);
                                    } else {
                                        Toast.makeText(getContext(), "Unable to fetch address", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), "Geocoder error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Could not retrieve location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    private void submitOrder() {
        // Disable the button to prevent multiple clicks
        submitOrderButton.setEnabled(false);

        // Fetch user ID from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(getContext(), "User ID not found. Please log in.", Toast.LENGTH_SHORT).show();
            submitOrderButton.setEnabled(true); // Re-enable button
            return;
        }

        // Validate and prepare order data
        String pizzaIdText = pizzaIdInput.getText().toString().trim();
        if (pizzaIdText.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a valid Pizza ID.", Toast.LENGTH_SHORT).show();
            submitOrderButton.setEnabled(true); // Re-enable button
            return;
        }

        int pizzaId = Integer.parseInt(pizzaIdText);

        List<Integer> toppingIds = new ArrayList<>();
        String toppingsInput = toppingIdsInput.getText().toString().trim();
        if (!toppingsInput.isEmpty()) {
            String[] toppingIdsStr = toppingsInput.split(",");
            for (String toppingId : toppingIdsStr) {
                try {
                    toppingIds.add(Integer.parseInt(toppingId.trim()));
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid Topping ID: " + toppingId, Toast.LENGTH_SHORT).show();
                    submitOrderButton.setEnabled(true); // Re-enable button
                    return;
                }
            }
        }

        // Prepare order items
        List<OrderRequest.OrderItem> items = Collections.singletonList(new OrderRequest.OrderItem(pizzaId, toppingIds));
        OrderRequest orderRequest = new OrderRequest(userId, items);

        // Make the API call
        apiService.createOrder(orderRequest).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Order placed successfully! Order ID: " + response.body().getOrderId(), Toast.LENGTH_LONG).show();
                } else {
                    String errorMessage = response.errorBody() != null ? response.errorBody().toString() : "Unknown error";
                    Toast.makeText(getContext(), "Failed to place order: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
                submitOrderButton.setEnabled(true); // Re-enable button
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                submitOrderButton.setEnabled(true); // Re-enable button
            }
        });
    }

    private void validateAndSetDeliveryTime() {
        int hour = Integer.parseInt(HOURS[hourSpinner.getSelectedItemPosition()]);
        int minute = Integer.parseInt(MINUTES[minuteSpinner.getSelectedItemPosition()]);
        int day = Integer.parseInt(DAYS[daySpinner.getSelectedItemPosition()]);
        int month = monthSpinner.getSelectedItemPosition(); // Month is 0-based, so 0 = January

        // Get current time
        Calendar currentCalendar = Calendar.getInstance();
        Calendar deliveryCalendar = Calendar.getInstance();
        deliveryCalendar.set(Calendar.MONTH, month);
        deliveryCalendar.set(Calendar.DAY_OF_MONTH, day);
        deliveryCalendar.set(Calendar.HOUR_OF_DAY, hour);
        deliveryCalendar.set(Calendar.MINUTE, minute);
        deliveryCalendar.set(Calendar.SECOND, 0);

        // Check if delivery time is at least 30 minutes from now
        if (deliveryCalendar.getTimeInMillis() <= currentCalendar.getTimeInMillis() + 30 * 60 * 1000) {
            Toast.makeText(getContext(), "The delivery time must be at least 30 minutes from now.", Toast.LENGTH_SHORT).show();
            return;
        } else if (deliveryCalendar.getTimeInMillis() < currentCalendar.getTimeInMillis()) {
            Toast.makeText(getContext(), "You cannot select a time in the past.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed with setting the delivery time
        String formattedDeliveryTime = String.format("Delivery Time: %02d:%02d %02d.%02d", hour, minute, day, month + 1);
        Toast.makeText(getContext(), formattedDeliveryTime, Toast.LENGTH_SHORT).show();
        // Here you can save the time or proceed with the order submission
    }
}

