package com.liveinaura.gdcreator.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.liveinaura.gdcreator.models.User;

public class FirebaseService {
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore mDb;

    public FirebaseService() {
        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public Task<DocumentSnapshot> getUserData() {
        return mDb.collection("users").document(getCurrentUser().getUid()).get();
    }

    public Task<Void> setUserData(User user) {
        return mDb.collection("users").document(getCurrentUser().getUid()).set(user);
    }
}
