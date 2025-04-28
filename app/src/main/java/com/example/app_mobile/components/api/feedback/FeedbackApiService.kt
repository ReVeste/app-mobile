package com.example.app_mobile.components.api.feedback

import com.example.app_mobile.components.api.produto.Produto
import com.example.app_mobile.components.api.produto.ProdutoApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedbackApiService {

    @GET("feedbacks")
    suspend fun buscarTodos(): List<Feedback>

    @GET("feedbacks/{id}")
    suspend fun buscarPorId(@Path("id") id: Int): Feedback

    @POST("feedbacks")
    suspend fun criarAvaliacao()

}

object FeedbackApi {

    // é OBRIGATÓRIO que essa URL termine com /
    private val BASE_URL = "http://10.0.2.2:8080/"

    // http://52.86.31.207/api
    // http://10.0.2.2:8080/
    // base url

    val api: FeedbackApiService by lazy{

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val clienteHttp = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clienteHttp) // interceptor de log, opcional
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FeedbackApiService::class.java)
    }
}