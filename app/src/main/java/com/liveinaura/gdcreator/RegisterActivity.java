package com.liveinaura.gdcreator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.liveinaura.gdcreator.models.User;
import com.liveinaura.gdcreator.MainActivity;
import com.liveinaura.gdcreator.utils.ValidationUtils;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d("Auth", "RegisterActivity:onCreate");

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();

        registerButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (name.isEmpty()) {
                nameEditText.setError("Name is required");
                Log.e("Auth", "RegisterActivity: Invalid name - Name is empty");
                return;
            }

            if (!ValidationUtils.isValidEmail(email)) {
                emailEditText.setError("Please enter a valid email address like name@email.com");
                Log.e("Auth", "RegisterActivity: Invalid email - " + email);
                return;
            }

            if (!ValidationUtils.isValidPassword(password)) {
                passwordEditText.setError("Password must be at least 6 characters with 2 numbers and 2 special characters.");
                Log.e("Auth", "RegisterActivity: Invalid password");
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Log.d("Auth", "RegisterActivity: Registration successful for email: " + email);
                            mAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Log.d("Auth", "RegisterActivity: Verification email sent to: " + email);
                                            String uid = mAuth.getCurrentUser().getUid();
                                            User user = new User(name, email, "TRIAL", new com.google.firebase.Timestamp(new Date()), null, null);
                                            mDb.collection("users").document(uid).set(user)
                                                    .addOnCompleteListener(task2 -> {
                                                        if (task2.isSuccessful()) {
                                                            Log.d("Auth", "RegisterActivity: User data saved for uid: " + uid);
                                                            Toast.makeText(RegisterActivity.this, "Registration successful. Please verify your email.", Toast.LENGTH_LONG).show();
                                                            mAuth.signOut();
                                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        } else {
                                                            Log.e("Auth", "RegisterActivity: Failed to save user data for uid: " + uid, task2.getException());
                                                            Toast.makeText(RegisterActivity.this, "Failed to save user data.",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {
                                            Log.e("Auth", "RegisterActivity: Failed to send verification email to: " + email, task1.getException());
                                            Toast.makeText(RegisterActivity.this, "Failed to send verification email.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Log.e("Auth", "RegisterActivity: Registration failed for email: " + email, task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "RegisterActivity:onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "RegisterActivity:onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "RegisterActivity:onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "RegisterActivity:onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "RegisterActivity:onDestroy");
    }
}
