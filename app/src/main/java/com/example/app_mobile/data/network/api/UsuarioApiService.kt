package com.example.app_mobile.data.network.api

import com.example.app_mobile.domain.model.SessaoUsuario
import com.example.app_mobile.domain.model.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class LoginRequest(
    val email: String,
    val senha: String
)

data class CriarContaRequest(
    val nome: String,
    val cpf: String,
    val telefone: String,
    val email: String,
    val senha: String,
    val imagemUrl: String = ""
)

data class CriarContaResponse(
    val id: Int,
    val nome: String,
    val cpf: String,
    val telefone: String,
    val email: String,
    val tipo: String,
    val ativo: Boolean,
    val imagemUrl: String
)

interface UsuarioApiService {

    @POST("usuarios/login")
    suspend fun login(@Body dadosLogin: LoginRequest): SessaoUsuario

    @POST("usuarios")
    suspend fun cadastrar(@Body dadosCriarContaRequest: CriarContaRequest): CriarContaResponse

    @GET("usuarios/{id}")
    suspend fun buscarUsuarioPorId(@Path("id") id: Int): SessaoUsuario

}

