package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;

import com.example.pizzeria.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.pizzeria.databinding.ActivityNavigationNotLoggedBinding;

public class NavigationNotLogged extends AppCompatActivity {

    private ActivityNavigationNotLoggedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationNotLoggedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize BottomNavigationView
        BottomNavigationView navView = binding.navView;

        // Configure AppBar with only MenuFragment
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_menu) // Only MenuFragment is a top-level destination
                .build();

        // Set up NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_navigation_not_logged);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Handle menu item clicks
        navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_menu:
                    // Navigate to MenuFragment
                    navController.navigate(R.id.navigation_menu);
                    return true;
                case R.id.navigation_login:
                    // Start LoginActivity
                    startActivity(new Intent(this, LoginActivity.class));
                    return true;
                default:
                    return false;
            }
        });
    }
}
