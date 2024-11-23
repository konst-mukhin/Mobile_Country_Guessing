package com.example.projectx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = findViewById(R.id.loginArea);
        passwordEditText = findViewById(R.id.passwordArea);

        findViewById(R.id.signInButton).setOnClickListener(v -> {
            auth();
        });

        findViewById(R.id.goHomeButton).setOnClickListener(v -> {
            finish();
        });
    }

    protected void auth() {
        OkHttpClient client = new OkHttpClient();

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        RequestBody requestBody = RequestBody.create("{\n" +
                "    \"email\": \"" + email + "\",\n" +
                "    \"password\": \"" + password + "\"\n" +
                "}", MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url("http://172.20.10.2:8080/api/v1/auth/authenticate")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("ABOBA", "Request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("ABOBA", "Unexpected code: " + response);
                    return;
                }

                assert response.body() != null;
                String responseBodyString = response.body().string();
                Log.d("ABOBA", responseBodyString);

                Token.token = responseBodyString;

                    runOnUiThread(() -> {
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    });
            }
        });
    }
}