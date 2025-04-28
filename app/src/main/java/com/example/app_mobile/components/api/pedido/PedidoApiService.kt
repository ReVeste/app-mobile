package com.example.app_mobile.components.api.pedido

import com.example.app_mobile.components.api.produto.Produto
import com.example.app_mobile.components.api.produto.ProdutoApi
import com.example.app_mobile.components.api.produto.ProdutoApiService
import com.example.app_mobile.components.api.produto.ProdutoDto
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PedidoApiService {

    @POST("/pedidos")
    suspend fun adicionarProduto(@Body pedido: PedidoAdicionarProdutoDto): CarrinhoDto

    @GET("/pedidos/{idUsuario}/em-aberto")
    suspend fun listarProdutosPedidoEmAberto(
        @Path("idUsuario") idUsuario: Int
    ): List<ProdutoDto>

    @GET("/pedidos/{idUsuario}/status")
    suspend fun buscarPorStatus(
        @Path("idUsuario") idUsuario: Int,
        @Query("status") status: String
    ): List<PedidoDto>

    @DELETE("/pedidos/{idPedido}/produto/{idProduto}")
    suspend fun removerProduto(
        @Path("idPedido") idPedido: Int,
        @Path("idProduto") idProduto: Int
    )

}

object PedidoApi {

    private val BASE_URL = "http://10.0.2.2:8080/"

    val api: PedidoApiService by lazy{

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val clienteHttp = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .baseUrl(PedidoApi.BASE_URL)
            .client(clienteHttp) // interceptor de log, opcional
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PedidoApiService::class.java)
    }
}