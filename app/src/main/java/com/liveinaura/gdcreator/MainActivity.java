package com.liveinaura.gdcreator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.liveinaura.gdcreator.databinding.ActivityMainBinding;
import com.liveinaura.gdcreator.utils.ThemeUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", "MainActivity:onCreate");
        ThemeUtils.applyTheme(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        auth.getCurrentUser().reload().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (auth.getCurrentUser().isEmailVerified()) {
                    Log.d("Auth", "MainActivity: User is verified.");
                } else {
                    Log.d("Auth", "MainActivity: User is not verified.");
                    Toast.makeText(MainActivity.this, "Please verify your email to continue.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Log.e("Auth", "MainActivity: reload failed", task.getException());
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                    new ChatFragment()).commit();
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.chatFragment) {
                selectedFragment = new ChatFragment();
            } else if (itemId == R.id.docsFragment) {
                selectedFragment = new DocsFragment();
            } else if (itemId == R.id.settingFragment) {
                selectedFragment = new SettingFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        selectedFragment).commit();
            }
            return true;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "MainActivity:onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "MainActivity:onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "MainActivity:onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "MainActivity:onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "MainActivity:onDestroy");
    }

    public BottomNavigationView getBottomNavigationView() {
        return binding.bottomNavigation;
    }
}
