package com.example.pizzeria;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.pizzeria.databinding.ActivityNavigationLoggedUserBinding;
import com.example.pizzeria.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationLoggedUser extends AppCompatActivity {
    private ActivityNavigationLoggedUserBinding binding;
    private NavController navController;

    // Check if the user is logged in
    private boolean isLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return prefs.getBoolean("is_logged_in", false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationLoggedUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize NavController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Set up BottomNavigationView
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Set listener for BottomNavigationView
        binding.navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_menu:
                    navController.navigate(R.id.navigation_menu);
                    return true;
                case R.id.navigation_cart:
                case R.id.navigation_history:
                    navigateToFragmentWithLoginCheck(item.getItemId());
                    return true;
                case R.id.navigation_login:
                    if (isLoggedIn()) {
                        showLogoutConfirmationDialog(); // Show logout confirmation dialog
                    } else {
                        startActivity(new Intent(this, LoginActivity.class)); // Navigate to LoginActivity
                    }
                    return true;
                default:
                    return false;
            }
        });
    }

    private void navigateToFragmentWithLoginCheck(int itemId) {
        if (isLoggedIn()) {
            switch (itemId) {
                case R.id.navigation_cart:
                    navController.navigate(R.id.navigation_cart);
                    break;
                case R.id.navigation_history:
                    navController.navigate(R.id.navigation_history);
                    break;
            }
        } else {
            showLoginToast(); // Show message to log in
        }
    }

    private void updateMenuItems(boolean fromLogout) {
        BottomNavigationView navView = binding.navView;
        MenuItem loginItem = navView.getMenu().findItem(R.id.navigation_login);
        MenuItem orderItem = navView.getMenu().findItem(R.id.navigation_cart);
        MenuItem historyItem = navView.getMenu().findItem(R.id.navigation_history);

        if (isLoggedIn()) {
            loginItem.setTitle("Logout");
            historyItem.setIcon(R.drawable.icon_history);
            orderItem.setIcon(R.drawable.icon_cart);
            loginItem.setIcon(R.drawable.icon_logout); // Replace with Logout icon

            if (!fromLogout) {
                navView.setSelectedItemId(R.id.navigation_menu); // Make sure "Menu" is selected
            }
        } else {
            loginItem.setTitle("Login");
            historyItem.setIcon(R.drawable.icon_history_unusable);
            orderItem.setIcon(R.drawable.icon_cart_unusable);
            loginItem.setIcon(R.drawable.icon_login); // Replace with Login icon

            if (!fromLogout) {
                navView.setSelectedItemId(R.id.navigation_menu); // Make sure "Menu" is selected
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMenuItems(false); // Update the menu when activity resumes
    }

    private void showLoginToast() {
        Toast.makeText(this, getString(R.string.have_to_log_in), Toast.LENGTH_SHORT).show();
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, id) -> {
                    logOut(); // Perform logout if "Yes" is clicked
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.dismiss(); // Close dialog if "No" is clicked
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void logOut() {
        // Clear the user's login status
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_logged_in", false);
        editor.remove("user_id");
        editor.apply();

        // Show a logout confirmation
        Toast.makeText(this, "Wylogowano", Toast.LENGTH_SHORT).show();

        // Update the menu items to reflect the logout state
        updateMenuItems(true);

        // Redirect to the main menu
        navController.navigate(R.id.navigation_menu);

        // Set "Menu" as selected item in BottomNavigationView after logout
        binding.navView.setSelectedItemId(R.id.navigation_menu);
    }
}
