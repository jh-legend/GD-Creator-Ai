package com.liveinaura.gdcreator.models.request.chat;

import java.util.List;

public class ChatCompletionRequest {
    private String model;
    private List<ChatMessage> messages;
    private int max_tokens;

    public ChatCompletionRequest(String model, List<ChatMessage> messages, int max_tokens) {
        this.model = model;
        this.messages = messages;
        this.max_tokens = max_tokens;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    @Override
    public String toString() {
        return "ChatCompletionRequest{" +
                "model='" + model + '\'' +
                ", messages=" + messages +
                ", max_tokens=" + max_tokens +
                '}';
    }
}
