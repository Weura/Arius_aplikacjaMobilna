package com.example.pizzeria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.pizzeria.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.widget.Toast;

import com.example.pizzeria.databinding.ActivityNavigationLoggedUserBinding;

public class NavigationLoggedUser extends AppCompatActivity {

    private ActivityNavigationLoggedUserBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is logged in
        if (!isLoggedIn()) {
            redirectToLogin();
            return; // Stop further execution of onCreate
        }

        binding = ActivityNavigationLoggedUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize NavController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Set up ActionBar with NavController
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_order, R.id.navigation_menu_logged, R.id.navigation_history)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Set up BottomNavigationView
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Set up BottomNavigationView item click listener
        binding.navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_menu_logged:
                    navController.navigate(R.id.navigation_menu_logged);
                    return true;
                case R.id.navigation_history:
                    navController.navigate(R.id.navigation_history);
                    return true;
                case R.id.navigation_order:
                    navController.navigate(R.id.navigation_order);
                    return true;
//                    TODO: logout
//                case R.id.navigation_logout:
//                    logOut();
//                    return true;
                default:
                    return false;
            }
        });
    }

    // Method to check if the user is logged in
    private boolean isLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return prefs.getBoolean("is_logged_in", false);
    }

    // Redirect user to the login screen if not logged in
    private void redirectToLogin() {
        Toast.makeText(this, "Proszę się zalogować", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish(); // Close the current activity
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
        redirectToLogin();
    }
}
