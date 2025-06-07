package com.example.app_mobile.data.network.api

import com.example.app_mobile.domain.model.Feedback
import com.example.app_mobile.domain.model.FeedbackRequisicaoDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedbackApiService {

    @GET("feedbacks")
    suspend fun buscarTodos(): List<Feedback>

    @GET("feedbacks/{id}")
    suspend fun buscarPorId(@Path("id") id: Int): Feedback

    @POST("feedbacks")
    suspend fun criarAvaliacao(@Body avaliacao: FeedbackRequisicaoDto)

}
