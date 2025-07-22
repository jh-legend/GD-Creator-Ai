package com.liveinaura.gdcreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.liveinaura.gdcreator.models.User;
import com.liveinaura.gdcreator.services.FirebaseService;
import com.liveinaura.gdcreator.utils.ThemeUtils;

public class SettingFragment extends Fragment {

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView subscriptionStatusTextView;
    private SwitchMaterial themeSwitch;
    private Button logoutButton;

    private FirebaseService firebaseService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        subscriptionStatusTextView = view.findViewById(R.id.subscriptionStatusTextView);
        themeSwitch = view.findViewById(R.id.themeSwitch);
        logoutButton = view.findViewById(R.id.logoutButton);

        firebaseService = new FirebaseService();

        loadUserData();

        themeSwitch.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ThemeUtils.setTheme(getContext(), AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                ThemeUtils.setTheme(getContext(), AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }

    private void loadUserData() {
        if (firebaseService.getCurrentUser() != null) {
            firebaseService.getUserData().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user = document.toObject(User.class);
                        if (user != null) {
                            nameTextView.setText(user.getName());
                            emailTextView.setText(user.getEmail());
                            subscriptionStatusTextView.setText("Subscription Status: " + user.getSubscriptionStatus());
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}