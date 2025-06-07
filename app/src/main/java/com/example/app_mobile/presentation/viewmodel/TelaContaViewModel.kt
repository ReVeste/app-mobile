package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mobile.data.network.api.PedidoApiService
import com.example.app_mobile.data.network.api.UsuarioApiService
import com.example.app_mobile.domain.model.Feedback
import com.example.app_mobile.domain.model.PedidoDto
import com.example.app_mobile.domain.model.Produto
import com.example.app_mobile.domain.model.SessaoUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TelaContaViewModel(val api: UsuarioApiService,
                         val usuarioLogado: SessaoUsuario,
                         val apiPedido: PedidoApiService
    ) : ViewModel() {

    val _finalizados = mutableStateListOf<PedidoDto>()

    val _avaliados = mutableStateListOf<PedidoDto>()

    var carregando by mutableStateOf(false)

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    init {
        buscarPedidosFinalizados()
        buscarPedidosAvaliados()
    }

    fun buscarPedidosFinalizados() {
        viewModelScope.launch {
            carregando = true
            _finalizados.clear()
            try {
                _finalizados.addAll(apiPedido.buscarPorStatus(usuarioLogado.userId!!, "CONCLUIDO"))
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar pedidos concluídos por ID de usuário: ${e.message}")
                _erro.value = "Erro ao buscar pedidos concluídos"
            } finally {
                carregando = false
            }
        }
    }

    fun buscarPedidosAvaliados() {
        viewModelScope.launch {
            carregando = true
            _avaliados.clear()
            try {
                _avaliados.addAll(apiPedido.buscarPorStatus(usuarioLogado.userId!!, "AVALIADO"))
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar pedidos avaliados por ID de usuário: ${e.message}")
                _erro.value = "Erro ao buscar pedidos avaliados"
            } finally {
                carregando = false
            }
        }
    }

}