package com.example.app_mobile.components.api.usuario

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    suspend fun buscarUsuarioPorId(@Path("id") id: Int): Usuario

}

object UsuarioApi {

    private val BASE_URL = "http://10.0.2.2:8080/"

    val api: UsuarioApiService by lazy {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val clienteHttp = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioApiService::class.java)
    }
}
