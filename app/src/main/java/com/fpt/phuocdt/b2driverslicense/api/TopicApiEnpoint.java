package com.fpt.phuocdt.b2driverslicense.api;


import com.fpt.phuocdt.b2driverslicense.entity.Answer;
import com.fpt.phuocdt.b2driverslicense.entity.Question;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface TopicApiEnpoint {

    @GET("question/topic/{id}")
    Call<List<Question>> getQuestionByTopic(@Path("id") String id);
}
