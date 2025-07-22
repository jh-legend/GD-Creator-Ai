package com.liveinaura.gdcreator.api;

import com.liveinaura.gdcreator.models.request.CompletionRequest;
import com.liveinaura.gdcreator.models.response.CompletionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OpenAiApiService {
    @POST("v1/completions")
    Call<CompletionResponse> getCompletion(
            @Header("Authorization") String authorization,
            @Body CompletionRequest request
    );
}
