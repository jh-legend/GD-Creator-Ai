package com.liveinaura.gdcreator;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.liveinaura.gdcreator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // ✅ No bottom padding
            return insets;
        });


        // Default fragment
        replaceFragment(new ChatFragment());
        binding.bottomNavigationView.setBackground(null);

        // Navigation
        binding.bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            // ⚠️ Make sure these IDs match your menu XML file
            if (id == R.id.Chatwithai) {
                replaceFragment(new ChatFragment());
            } else if (id == R.id.DownloadedDocs) {
                replaceFragment(new DocsFragment());
            } else if (id == R.id.setting) {
                replaceFragment(new SettingFragment());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
}
