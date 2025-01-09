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
import android.util.Log;
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

        // Set up ActionBar with NavController
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_menu, R.id.navigation_order, R.id.navigation_history, R.id.navigation_login)
//                .build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Set up BottomNavigationView
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Set listener for BottomNavigationView
        binding.navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_menu:
                    navController.navigate(R.id.navigation_menu);
                    return true;
                case R.id.navigation_order:
                    if (isLoggedIn()) {
                        navController.navigate(R.id.navigation_order);
                    } else {
                        showLoginToast(); // Show message to log in
                    }
                    return true;
                case R.id.navigation_history:
                    if (isLoggedIn()) {
                        navController.navigate(R.id.navigation_history);
                    } else {
                        showLoginToast(); // Show message to log in
                    }
                    return true;
                case R.id.navigation_login:
                    if (isLoggedIn()) {
                        logOut(); // Log out if already logged in
                    } else {
                        startActivity(new Intent(this, LoginActivity.class)); // Navigate to LoginActivity
                    }
                    return true;
                default:
                    return false;
            }
        });
    }

    private void updateLoginLogoutMenuItem() {
        BottomNavigationView navView = binding.navView;
        MenuItem loginItem = navView.getMenu().findItem(R.id.navigation_login);

        if (isLoggedIn()) {
            loginItem.setTitle("Logout");
            loginItem.setIcon(R.drawable.icon_logout); // Replace with Logout icon
        } else {
            loginItem.setTitle("Login");
            loginItem.setIcon(R.drawable.icon_login); // Replace with Login icon
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLoginLogoutMenuItem(); // Update the menu when activity resumes
    }

    // Show a toast to prompt the user to log in
    private void showLoginToast() {
        Toast.makeText(this, getString(R.string.have_to_log_in), Toast.LENGTH_SHORT).show();
    }

    // Method to log out the user
    private void logOut() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_logged_in", false);
        editor.apply();

        Toast.makeText(this, "Wylogowano", Toast.LENGTH_SHORT).show();

        // Redirect to the main menu
        navController.navigate(R.id.navigation_menu);
    }
}
