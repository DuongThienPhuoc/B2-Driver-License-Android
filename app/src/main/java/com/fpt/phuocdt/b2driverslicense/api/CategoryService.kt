package com.fpt.phuocdt.b2driverslicense.api

import com.fpt.phuocdt.b2driverslicense.entity.Category
import com.fpt.phuocdt.b2driverslicense.entity.Question
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryService {
    @GET("/category")
    suspend fun getCategory(): Response<List<Category>>

    @GET("/question/{id}")
    suspend fun getQuestionsByCategoryId(@Path("id") id: String): Response<List<Question>>
}

val CategoryServiceAPI: CategoryService = Retrofit.Builder().baseUrl("http://192.168.0.101:9999")
    .addConverterFactory(GsonConverterFactory.create()).build().create(CategoryService::class.java)