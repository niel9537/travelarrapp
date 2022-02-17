package com.convergence.travelarrangement;


import androidx.annotation.NonNull;

import java.io.IOException;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
        private String authToken;
        AuthInterceptor(String token) {
            this.authToken = token;
        }
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .header("Authorization", authToken);
            Request request = builder.build();
            return chain.proceed(request);
        }
}

