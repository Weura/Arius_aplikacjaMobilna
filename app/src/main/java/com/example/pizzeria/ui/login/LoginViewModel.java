package com.example.pizzeria.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzeria.data.LoginRepository;
import com.example.pizzeria.R;

import com.example.pizzeria.data.api.ApiClient;
import com.example.pizzeria.data.api.ApiService;
import com.example.pizzeria.data.model.LoginRequest;
import com.example.pizzeria.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    private ApiService apiService;

    public LoginViewModel(LoginRepository instance) {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username, password);

        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    String userName = loginRequest.getUsername();

                    if (userName == null || userName.isEmpty()) {
                        userName = "Guest";
                    }

                    LoggedInUserView userView = new LoggedInUserView("User " + loginResponse.getUserId());
                    loginResult.postValue(new LoginResult(userView));
                } else {
                    loginResult.postValue(new LoginResult(R.string.login_failed));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResult.postValue(new LoginResult(R.string.login_failed));
            }
        });
    }


//    public void login(String username, String password) {
//        LoginRequest loginRequest = new LoginRequest(username, password);
//
//        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    LoginResponse loginResponse = response.body();
//                    LoggedInUserView userView = new LoggedInUserView("User " + loginResponse.getUserId());
//                    loginResult.postValue(new LoginResult(userView));
//                } else {
//                    loginResult.postValue(new LoginResult(R.string.login_failed));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                loginResult.postValue(new LoginResult(R.string.login_failed));
//            }
//        });
//    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isUserNameValid(String username) {
        return username != null && !username.trim().isEmpty();
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}

    //    private LoginRepository loginRepository;
//
//    private ApiService apiService;
//
//    LoginViewModel(LoginRepository loginRepository) {
//        this.loginRepository = loginRepository;
//    }
//
//    LiveData<LoginFormState> getLoginFormState() {
//        return loginFormState;
//    }
//
//    LiveData<LoginResult> getLoginResult() {
//        return loginResult;
//    }
//
//    public void login(String username, String password) {
//        // can be launched in a separate asynchronous job
//        Result<LoggedInUser> result = loginRepository.login(username, password);
//
//        if (result instanceof Result.Success) {
//            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
//            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
//        } else {
//            loginResult.setValue(new LoginResult(R.string.login_failed));
//        }
//    }
//
//    public void loginDataChanged(String username, String password) {
//        if (!isUserNameValid(username)) {
//            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
//        } else if (!isPasswordValid(password)) {
//            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
//        } else {
//            loginFormState.setValue(new LoginFormState(true));
//        }
//    }
//
//    // A placeholder username validation check
//    private boolean isUserNameValid(String username) {
//        if (username == null) {
//            return false;
//        }
//        if (username.contains("@")) {
//            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
//        } else {
//            return !username.trim().isEmpty();
//        }
//    }
//
//    // A placeholder password validation check
//    private boolean isPasswordValid(String password) {
//        return password != null && password.trim().length() > 5;
//    }
//}