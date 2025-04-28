package com.example.app_mobile.components.api.usuario

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    var carregando by mutableStateOf(false)
        private set

    var erro by mutableStateOf<String?>(null)
        private set

    var usuarioLogado by mutableStateOf<SessaoUsuario?>(null)
        private set

    var usuario by mutableStateOf<Usuario?>(null)
        private set

    fun realizarLogin(email: String, senha: String) {
        viewModelScope.launch {
            carregando = true
            erro = null
            try {
                val resposta = UsuarioApi.api.login(LoginRequest(email, senha))
                usuarioLogado = SessaoUsuario(
                    userId = resposta.userId,
                    nome = resposta.nome,
                    email = resposta.email,
                    token = resposta.token
                )
            } catch (e: Exception) {
                Log.e("API", "Erro ao realizar login: ${e.message}")
                erro = "Erro ao realizar login: ${e.message}"
            } finally {
                carregando = false
            }
        }
    }

    fun criarConta(nome: String, cpf: String, telefone: String, email: String, senha: String, imagemUrl: String = "") {
        viewModelScope.launch {
            erro = null
            try {
                val request = CriarContaRequest(
                    nome = nome,
                    cpf = cpf,
                    telefone = telefone,
                    email = email,
                    senha = senha,
                    imagemUrl = imagemUrl
                )
                val resposta = UsuarioApi.api.cadastrar(request)
                carregando = false
            } catch (e: Exception) {
                Log.e("API", "Erro ao criar conta: ${e.message}")
                erro = "Erro ao criar conta: ${e.message}"
            }
        }
    }

    fun buscarUsuarioPorId(userId: Int) {
        viewModelScope.launch {
            carregando = true
            try {
                usuario = UsuarioApi.api.buscarUsuarioPorId(userId)
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar usuário: ${e.message}")
            } finally {
                carregando = false
            }
        }
    }


}


