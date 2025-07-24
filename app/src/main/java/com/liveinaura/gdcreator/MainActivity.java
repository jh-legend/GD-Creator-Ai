package com.liveinaura.gdcreator;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.liveinaura.gdcreator.databinding.ActivityMainBinding;
import com.liveinaura.gdcreator.utils.ThemeUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.applyTheme(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (FirebaseAuth.getInstance().getCurrentUser() == null || !FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }
            return true;
        });
    }
}
