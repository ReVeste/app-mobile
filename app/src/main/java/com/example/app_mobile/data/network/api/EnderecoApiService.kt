package com.example.app_mobile.data.network.api

import com.example.app_mobile.domain.model.EnderecoDto
import com.example.app_mobile.domain.model.Feedback
import com.example.app_mobile.domain.model.OpcaoFrete
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class CriarEnderecoDto(
    val apelido: String,
    val cep: String,
    val rua: String,
    val numero: Int,
    val complemento: String,
    val bairro: String,
    val cidade: String,
    val uf: String,
    val idUsuario: Int
)

interface EnderecoApiService {

    @GET("enderecos/usuario/{idUsuario}")
    suspend fun buscarPorUsuario(@Path("idUsuario") idUsuario: Int): List<EnderecoDto>

    @POST("enderecos")
    suspend fun registrarEndereco(
        @Body endereco: CriarEnderecoDto
    ): EnderecoDto

    @POST("entregas")
    suspend fun calcularFrete(
        @Query("cep") cep: String
    ): List<OpcaoFrete>

}