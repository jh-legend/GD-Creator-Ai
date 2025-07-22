package com.liveinaura.gdcreator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liveinaura.gdcreator.adapters.ChatAdapter;
import com.liveinaura.gdcreator.api.ApiClient;
import com.liveinaura.gdcreator.api.OpenAiApiService;
import com.liveinaura.gdcreator.models.Message;
import com.liveinaura.gdcreator.models.request.CompletionRequest;
import com.liveinaura.gdcreator.models.response.CompletionResponse;
import com.liveinaura.gdcreator.utils.PermissionUtils;
import com.liveinaura.gdcreator.utils.PdfGenerator;
import android.content.pm.PackageManager;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {

    private RecyclerView chatRecyclerView;
    private EditText messageEditText;
    private Button sendButton;
    private ProgressBar progressBar;
    private Button downloadButton;

    private ChatAdapter chatAdapter;
    private List<Message> messageList;
    private OpenAiApiService openAiApiService;

    private static final String OPENAI_API_KEY = "YOUR_OPENAI_API_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);
        messageEditText = view.findViewById(R.id.messageEditText);
        sendButton = view.findViewById(R.id.sendButton);
        progressBar = view.findViewById(R.id.progressBar);
        downloadButton = view.findViewById(R.id.downloadButton);

        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatRecyclerView.setAdapter(chatAdapter);

        openAiApiService = ApiClient.getClient().create(OpenAiApiService.class);

        sendButton.setOnClickListener(v -> {
            String messageText = messageEditText.getText().toString().trim();
            if (!messageText.isEmpty()) {
                sendMessage(messageText);
            }
        });

        downloadButton.setOnClickListener(v -> {
            if (PermissionUtils.hasStoragePermission(getActivity())) {
                try {
                    String gdText = "";
                    for (Message message : messageList) {
                        if (!message.isUser()) {
                            gdText += message.getText() + "\n\n";
                        }
                    }
                    PdfGenerator.generatePdf(getContext(), gdText);
                    Toast.makeText(getContext(), "PDF downloaded successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to download PDF", Toast.LENGTH_SHORT).show();
                }
            } else {
                PermissionUtils.requestStoragePermission(getActivity());
            }
        });

        return view;
    }

    private void sendMessage(String messageText) {
        messageList.add(new Message(messageText, true));
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        messageEditText.setText("");
        progressBar.setVisibility(View.VISIBLE);

        CompletionRequest request = new CompletionRequest(
                "text-davinci-003",
                messageText,
                150
        );

        openAiApiService.getCompletion("Bearer " + OPENAI_API_KEY, request).enqueue(new Callback<CompletionResponse>() {
            @Override
            public void onResponse(Call<CompletionResponse> call, Response<CompletionResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().getChoices().isEmpty()) {
                    String aiResponse = response.body().getChoices().get(0).getText().trim();
                    messageList.add(new Message(aiResponse, false));
                    chatAdapter.notifyItemInserted(messageList.size() - 1);
                    chatRecyclerView.scrollToPosition(messageList.size() - 1);
                } else {
                    Toast.makeText(getContext(), "Failed to get response from AI", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CompletionResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadButton.performClick();
            } else {
                Toast.makeText(getContext(), "Storage permission is required to download PDF", Toast.LENGTH_SHORT).show();
            }
        }
    }
}