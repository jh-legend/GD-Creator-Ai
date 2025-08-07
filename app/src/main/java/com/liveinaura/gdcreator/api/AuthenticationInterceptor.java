package com.liveinaura.gdcreator.api;

import com.liveinaura.gdcreator.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + BuildConfig.OPENAI_API_KEY);
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
