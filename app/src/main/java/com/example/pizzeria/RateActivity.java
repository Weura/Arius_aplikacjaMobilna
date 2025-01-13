package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pizzeria.R;
import com.example.pizzeria.data.api.ApiClient;
import com.example.pizzeria.data.api.ApiService;
import com.example.pizzeria.ui.login.LoginActivity;
import com.example.pizzeria.ui.login.RegisterActivity;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText rateComment;
    private Button returnButton;
    private Button submitRateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ratingBar = findViewById(R.id.ratingBar);
        rateComment = findViewById(R.id.rateComment);
        submitRateButton = findViewById(R.id.submitRateButton);
        returnButton = findViewById(R.id.returnButton);
        submitRateButton.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String comment = rateComment.getText().toString();  // Get the comment from EditText

            // Replace with actual user ID and order ID (retrieve from session or intent)
            int userId = getIntent().getIntExtra("user_id", -1);
            int orderId = getIntent().getIntExtra("order_id", -1);
            if (userId == -1 || orderId == -1) {
                Toast.makeText(this, "Invalid user or order information", Toast.LENGTH_SHORT).show();
                finish();  // Close activity if data is invalid
                return;
            }
            submitRating(userId, orderId, (int) rating, comment);

            Intent intent = new Intent(RateActivity.this, NavigationLoggedUser.class);
            startActivity(intent);
            finish();
        });
        returnButton.setOnClickListener(view -> {

            Intent intent = new Intent(RateActivity.this, NavigationLoggedUser.class);
            startActivity(intent);
            finish();
        });
    }

    private void submitRating(int userId, int orderId, int rating, String comment) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Create JSON object to send to the server
        JsonObject ratingData = new JsonObject();
        ratingData.addProperty("user_id", userId);
        ratingData.addProperty("order_id", orderId);
        ratingData.addProperty("rating", rating);

        // Add the comment property if it's not empty
        if (!comment.isEmpty()) {
            ratingData.addProperty("comment", comment);
        }

        // Make the API call
        apiService.submitRating(ratingData).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RateActivity.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RateActivity.this, "Failed to submit rating", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RateActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
