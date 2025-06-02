package com.example.app_mobile.data.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/*
Classe que "intercepta" todas as requisições,
adicionando o cabeçalho de autorização com o token
em todas as requisições
 */
class TokenInterceptor(val token: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i("api", "token para header: $token")

        if(chain.request().url.toString().endsWith("usuarios/login")){
            return chain.proceed(chain.request())
        }
        // Crie uma nova requisição com o cabeçalho adicionado
        val requisicao = chain.request().newBuilder()
            .header("Authorization", "Bearer $token") // Adiciona o cabeçalho de autorização
            .build()

        // Continue a cadeia com a nova requisição modificada
        return chain.proceed(requisicao)
    }
}