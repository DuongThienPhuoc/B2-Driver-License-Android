package com.fpt.phuocdt.b2driverslicense.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServices {
    private static final String BASE_URL = "http://192.168.16.100:9999";

    private static ApiServices instance = null;
    public TopicApiEnpoint topicApiEnpoint;

    private ApiServices() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        topicApiEnpoint = retrofit.create(TopicApiEnpoint.class);
    }

    private static ApiServices getInstance() {
        if (instance == null) {
            instance = new ApiServices();
        }
        return instance;
    }

    public static TopicApiEnpoint getPostApiEnpoint() {
        return getInstance().topicApiEnpoint;
    }
}
