package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_mobile.data.network.api.UsuarioApiService
import com.example.app_mobile.domain.model.SessaoUsuario

class TelaContaViewModel(val api: UsuarioApiService, val usuarioLogado: SessaoUsuario) : ViewModel() {

    var carregando by mutableStateOf(false)

    // buscar pedidos concluidos do usuário



    // buscar pedidos avaliados do usuário


}