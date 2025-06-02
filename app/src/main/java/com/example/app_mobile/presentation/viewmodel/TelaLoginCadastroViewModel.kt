package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mobile.data.network.api.CriarContaRequest
import com.example.app_mobile.data.network.api.LoginRequest
import com.example.app_mobile.data.network.api.UsuarioApiService
import com.example.app_mobile.domain.model.SessaoUsuario
import kotlinx.coroutines.launch

class TelaLoginCadastroViewModel(val api: UsuarioApiService, val usuarioLogado: SessaoUsuario) : ViewModel() {

    var carregando by mutableStateOf(false)
        private set

    var erro by mutableStateOf<String?>(null)
        private set

    var mensagemSucesso by mutableStateOf<String?>(null)
        private set

    fun realizarLogin(email: String, senha: String) {
        viewModelScope.launch {
            carregando = true
            erro = null
            try {
                val resposta = api.login(LoginRequest(email, senha))
                usuarioLogado.userId = resposta.userId
                usuarioLogado.nome = resposta.nome
                usuarioLogado.email = resposta.email
                usuarioLogado.token = resposta.token

                mensagemSucesso = "Login realizado com sucesso!"

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
                api.cadastrar(request)

                mensagemSucesso = "Cadastro realizado com sucesso!"
                carregando = false
            } catch (e: Exception) {
                Log.e("API", "Erro ao criar conta: ${e.message}")
                erro = "Erro ao criar conta: ${e.message}"
            }
        }
    }

}