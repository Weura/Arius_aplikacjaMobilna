package com.example.pizzeria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.pizzeria.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;

import com.example.pizzeria.databinding.ActivityNavigationLoggedUserBinding;

public class NavigationLoggedUser extends AppCompatActivity {

    private ActivityNavigationLoggedUserBinding binding;
    private NavController navController;

    // Method to check if the user is logged in
    private boolean isLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return prefs.getBoolean("is_logged_in", false); // We assume login status is saved in SharedPreferences
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationLoggedUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize NavController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Set up ActionBar with NavController
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_order, R.id.navigation_menu, R.id.navigation_history, R.id.navigation_login)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Set up BottomNavigationView
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Update BottomNavigationMenu based on login status
        updateBottomNavigationMenu();

        // Set up BottomNavigationView item click listener for login/logout
        binding.navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_menu:
                    navController.navigate(R.id.navigation_menu);
                    return true;
                case R.id.navigation_history:
                    navController.navigate(R.id.navigation_history);
                    return true;
                case R.id.navigation_order:
                    navController.navigate(R.id.navigation_order);
                    return true;
                case R.id.navigation_login:
                    logOut();
                    return true;
                default:
                    return false;
            }
        });
    }

    // Update BottomNavigationMenu dynamically
    private void updateBottomNavigationMenu() {
        BottomNavigationView navView = binding.navView;
        navView.getMenu().clear(); // Clear existing menu items

        // Inflate menu based on login status
        if (isLoggedIn()) {
            navView.inflateMenu(R.menu.bottom_nav_menu); // Menu for logged-in users
        } else {
            navView.inflateMenu(R.menu.bottom_nav_menu_logged_out); // Menu for not-logged-in users
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBottomNavigationMenu(); // Refresh menu when activity is resumed
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    // Method to log out the user
    private void logOut() {
        // Clear the user's information from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_logged_in", false);
        editor.apply();

        Toast.makeText(this, "Wylogowano", Toast.LENGTH_SHORT).show();

        // Redirect to the login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }
}
