package com.example.pizzeria.data;

import com.example.pizzeria.data.api.ApiClient;
import com.example.pizzeria.data.api.ApiService;
import com.example.pizzeria.data.model.LoggedInUser;
import com.example.pizzeria.data.model.LoginRequest;
import com.example.pizzeria.data.model.LoginResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private ApiService apiService;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // Przygotowanie danych do wysłania w body requestu
            LoginRequest loginRequest = new LoginRequest(username, password);

            // Wykonanie żądania do backendu
            Call<LoginResponse> call = apiService.loginUser(loginRequest);
            Response<LoginResponse> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                // Zwrócenie poprawnie zalogowanego użytkownika
                LoginResponse loginResponse = response.body();
                LoggedInUser user = new LoggedInUser(
                        loginResponse.getUserId(),
                        loginResponse.getUserName()
                );
                return new Result.Success<>(user);
            } else {
                // Obsługa błędnej odpowiedzi z serwera
                return new Result.Error(new IOException("Error logging in: " + response.message()));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}