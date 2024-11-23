package com.example.projectx;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.signInHomeButton).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.signUpHomeButton).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
