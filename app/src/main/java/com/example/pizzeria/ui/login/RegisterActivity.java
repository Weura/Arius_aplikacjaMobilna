package com.example.pizzeria.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzeria.R;
import com.example.pizzeria.data.LoginRepository;
import com.example.pizzeria.data.api.ApiClient;
import com.example.pizzeria.data.api.ApiService;
import com.example.pizzeria.data.model.UserRequest;
import com.example.pizzeria.data.model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextUsername, editTextName, editTextSurname, editTextTelephoneNumber;
    private Button registerButton;

    private Button returnButton;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize fields
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextTelephoneNumber = findViewById(R.id.editTextTelephone);
        registerButton = findViewById(R.id.registerButton);
        returnButton = findViewById(R.id.returnButton);

        // Initially disable the register button
        registerButton.setEnabled(false);


        // Create an instance of ApiService
        apiService = ApiClient.getClient().create(ApiService.class);
        // Add text watchers to all fields
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Ignore
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Ignore
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if all fields are filled out correctly
                checkFields();
            }
        };

        editTextEmail.addTextChangedListener(afterTextChangedListener);
        editTextUsername.addTextChangedListener(afterTextChangedListener);
        editTextPassword.addTextChangedListener(afterTextChangedListener);
        editTextName.addTextChangedListener(afterTextChangedListener);
        editTextSurname.addTextChangedListener(afterTextChangedListener);
        editTextTelephoneNumber.addTextChangedListener(afterTextChangedListener);

        // Handle user registration
        registerButton.setOnClickListener(view -> registerUser());
        returnButton.setOnClickListener(view -> {

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void checkFields() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String telephoneNumber = editTextTelephoneNumber.getText().toString().trim();

        // Enable the register button only if all fields are non-empty
        registerButton.setEnabled(!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !surname.isEmpty() && !telephoneNumber.isEmpty());
    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String telephoneNumber = editTextTelephoneNumber.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || name.isEmpty() || surname.isEmpty() || telephoneNumber.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a UserRequest object
        UserRequest userRequest = new UserRequest(username, email, password, name, surname, telephoneNumber);

        // Sending the request to the backend
        apiService.registerUser(userRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    Toast.makeText(RegisterActivity.this, "Registration successful: " + userResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    // Redirect to login page
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
