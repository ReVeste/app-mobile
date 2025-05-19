package com.example.app_mobile.data.network.api

import com.example.app_mobile.domain.model.Produto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProdutoApiService {

    @GET("produtos")
    suspend fun buscarTodos(): List<Produto>

    @GET("produtos/{id}")
    fun buscarPorId(
        @Path("id") id: Int
    ): Produto

}
