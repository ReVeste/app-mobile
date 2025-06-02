package com.example.app_mobile.data.network.api

import com.example.app_mobile.application.config.Configuracoes.BASE_URL_REVESTE_API
import com.example.app_mobile.domain.model.ProdutoDto
import com.example.app_mobile.domain.model.CarrinhoDto
import com.example.app_mobile.domain.model.PedidoAdicionarProdutoDto
import com.example.app_mobile.domain.model.PedidoDto
import okhttp3.OkHttpClient
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

    @POST("pedidos")
    suspend fun adicionarProduto(@Body pedido: PedidoAdicionarProdutoDto): CarrinhoDto

    @GET("pedidos/{idUsuario}/em-aberto")
    suspend fun listarProdutosPedidoEmAberto(
        @Path("idUsuario") idUsuario: Int
    ): List<ProdutoDto>

    @DELETE("pedidos/{idPedido}/produto/{idProduto}")
    suspend fun removerProduto(
        @Path("idPedido") idPedido: Int,
        @Path("idProduto") idProduto: Int
    )

    @GET("pedidos/{idUsuario}/status")
    suspend fun buscarPorStatus(
        @Path("idUsuario") idUsuario: Int,
        @Query("status") status: String
    ): List<PedidoDto>

}
