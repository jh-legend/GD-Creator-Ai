package com.liveinaura.gdcreator.models.response.chat;

import com.liveinaura.gdcreator.models.request.chat.ChatMessage;

public class ChatChoice {
    private int index;
    private ChatMessage message;
    private String finish_reason;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }
}
