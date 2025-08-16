package com.liveinaura.gdcreator.api;

import com.liveinaura.gdcreator.models.request.chat.ChatCompletionRequest;
import com.liveinaura.gdcreator.models.response.chat.ChatCompletionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OpenAiApiService {
    @POST("chat/completions")
    Call<ChatCompletionResponse> getChatCompletion(
            @Body ChatCompletionRequest request
    );
}
