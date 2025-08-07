package com.liveinaura.gdcreator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.liveinaura.gdcreator.utils.ValidationUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private TextView resendVerificationTextView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("Auth", "LoginActivity:onCreate");

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);
        resendVerificationTextView = findViewById(R.id.resendVerificationTextView);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!ValidationUtils.isValidEmail(email)) {
                emailEditText.setError("Please enter a valid email address like name@email.com");
                Log.e("Auth", "LoginActivity: Invalid email - " + email);
                return;
            }

            if (password.isEmpty()) {
                passwordEditText.setError("Password is required");
                Log.e("Auth", "LoginActivity: Empty password");
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                Log.d("Auth", "LoginActivity: Login successful for email: " + email);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                mAuth.signOut();
                                Log.d("Auth", "LoginActivity: Email not verified for: " + email);
                                Toast.makeText(LoginActivity.this, "Please verify your email before logging in.", Toast.LENGTH_LONG).show();
                                resendVerificationTextView.setVisibility(TextView.VISIBLE);
                            }
                        } else {
                            Log.e("Auth", "LoginActivity: Login failed for email: " + email, task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        registerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        resendVerificationTextView.setOnClickListener(v -> {
            Log.d("Auth", "LoginActivity: Resending verification email to: " + mAuth.getCurrentUser().getEmail());
            mAuth.getCurrentUser().sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("Auth", "LoginActivity: Verification email sent to: " + mAuth.getCurrentUser().getEmail());
                            Toast.makeText(LoginActivity.this, "Verification email sent.", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("Auth", "LoginActivity: Failed to resend verification email", task.getException());
                            Toast.makeText(LoginActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "LoginActivity:onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "LoginActivity:onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "LoginActivity:onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "LoginActivity:onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "LoginActivity:onDestroy");
    }
}
