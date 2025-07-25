package com.liveinaura.gdcreator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.liveinaura.gdcreator.adapters.HistoryAdapter;
import com.liveinaura.gdcreator.models.Message;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView historyRecyclerView;
    private TextView noHistoryTextView;
    private HistoryAdapter historyAdapter;
    private List<Message> messageList;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyRecyclerView = view.findViewById(R.id.historyRecyclerView);
        noHistoryTextView = view.findViewById(R.id.noHistoryTextView);

        messageList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(getContext(), messageList);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        historyRecyclerView.setAdapter(historyAdapter);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        loadChatHistory();

        return view;
    }

    private void loadChatHistory() {
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("chats").document(userId).collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        messageList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Message message = document.toObject(Message.class);
                            messageList.add(message);
                        }
                        historyAdapter.notifyDataSetChanged();
                        if (messageList.isEmpty()) {
                            noHistoryTextView.setVisibility(View.VISIBLE);
                            historyRecyclerView.setVisibility(View.GONE);
                        } else {
                            noHistoryTextView.setVisibility(View.GONE);
                            historyRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
