package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mobile.data.network.api.UsuarioApiService
import com.example.app_mobile.domain.model.SessaoUsuario
import com.example.app_mobile.domain.model.Usuario
import kotlinx.coroutines.launch

class TelaContaViewModel(val api: UsuarioApiService, val usuarioLogado: SessaoUsuario) : ViewModel() {

    var usuario by mutableStateOf<SessaoUsuario?>(null)

    var carregando by mutableStateOf(false)

    fun buscarUsuarioPorId(userId: Int) {
        viewModelScope.launch {
            carregando = true
            try {
                usuario = api.buscarUsuarioPorId(userId)
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar usuário: ${e.message}")
            } finally {
                carregando = false
            }
        }
    }

    // buscar pedidos concluidos do usuário
    // buscar pedidos avaliados do usuário

}