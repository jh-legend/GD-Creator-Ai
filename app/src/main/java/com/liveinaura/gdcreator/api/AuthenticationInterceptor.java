package com.liveinaura.gdcreator.api;

import android.util.Log;

import com.liveinaura.gdcreator.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String apiKey = BuildConfig.OPENAI_API_KEY;

        if (apiKey == null || apiKey.isEmpty()) {
            Log.e("OpenAI", "AuthenticationInterceptor: API Key is null or empty. Please check your local.properties file.");
            // Proceed without the header, which will cause an API error that can be logged elsewhere
            return chain.proceed(originalRequest);
        }

        Log.d("OpenAI", "AuthenticationInterceptor: Using API Key.");

        Request.Builder builder = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + apiKey);
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
