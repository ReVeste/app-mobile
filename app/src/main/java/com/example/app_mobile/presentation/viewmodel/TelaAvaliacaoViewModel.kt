package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mobile.data.network.api.FeedbackApiService
import com.example.app_mobile.data.network.api.PedidoApiService
import com.example.app_mobile.domain.model.CarrinhoDto
import com.example.app_mobile.domain.model.Feedback
import com.example.app_mobile.domain.model.Produto
import com.example.app_mobile.domain.model.SessaoUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TelaAvaliacaoViewModel(
    val apiFeedback : FeedbackApiService,
    val apiPedido : PedidoApiService
): ViewModel() {

    var feedback by mutableStateOf<Feedback?>(null)
        private set

    var pedido by mutableStateOf<CarrinhoDto?>(null)
        private set

    var carregando by mutableStateOf(false)

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    fun buscarPorId(id: Int) {
        viewModelScope.launch {
            carregando = true
            try {
                feedback = apiFeedback.buscarPorId(id)
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar avaliação por ID: ${e.message}")
                _erro.value = "Erro ao buscar avaliação"
            } finally {
                buscarPedidoPorId(feedback!!.idPedido)
            }
        }
    }

    fun buscarPedidoPorId(id: Int) {
        viewModelScope.launch {
            try {
                pedido = apiPedido.buscarCarrinho(id)
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar Pedido da avaliação por ID: ${e.message}")
                _erro.value = "Erro ao buscar pedido"
            } finally {
                carregando = false
            }
        }
    }

}