package com.example.app_mobile.components.api.produto

import com.example.app_mobile.components.api.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProdutoApiService {

    @GET("produtos")
    suspend fun buscarTodos(): List<Produto>

    @GET("produtos/{id}")
    suspend fun buscarPorId(
        @Path("id") id: Int
    ): Produto

}

object ProdutoApi {

    // é OBRIGATÓRIO que essa URL termine com /
    private val BASE_URL = "http://localhost:8080/"

    val api: ProdutoApiService by lazy{

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val clienteHttp = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clienteHttp) // interceptor de log, opcional
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProdutoApiService::class.java)
    }
}