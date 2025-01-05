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

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pizzeria.databinding.ActivityNavigationLoggedUserBinding;

public class NavigationLoggedUser extends AppCompatActivity {

    private ActivityNavigationLoggedUserBinding binding;
    private NavController navController;
    private BottomNavigationView navView;

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
                R.id.navigation_order, R.id.navigation_menu, R.id.navigation_history)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Set up BottomNavigationView
        NavigationUI.setupWithNavController(binding.navView, navController);

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
                    // If the user is logged in, show the logout option
                    if (isLoggedIn()) {
                        logOut();
                    } else {
                        // Otherwise, navigate to the login screen
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                    return true;
                default:
                    return false;
            }
        });
    }

    // Method to create the menu (first time)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    // Dynamically update the menu based on the login status
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logoutItem = menu.findItem(R.id.action_logout);

        // Check if the user is logged in
        if (isLoggedIn()) {
            // If logged in, show the logout option
            logoutItem.setTitle("Wyloguj");
            logoutItem.setIcon(R.drawable.icon_logout);
        } else {
            // If not logged in, show the login option
            logoutItem.setTitle("Zaloguj");
            logoutItem.setIcon(R.drawable.icon_login); // Icon for "Login"
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                if (isLoggedIn()) {
                    // Log out the user
                    logOut();
                } else {
                    // If not logged in, go to the login screen
                    startActivity(new Intent(this, LoginActivity.class));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        startActivity(new Intent(this, LoginActivity.class));
        finish(); // Close the current activity
    }
}
