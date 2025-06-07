package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mobile.data.network.api.CriarContaRequest
import com.example.app_mobile.data.network.api.FeedbackApiService
import com.example.app_mobile.data.network.api.PedidoApiService
import com.example.app_mobile.domain.model.CarrinhoDto
import com.example.app_mobile.domain.model.Feedback
import com.example.app_mobile.domain.model.FeedbackRequisicaoDto
import com.example.app_mobile.domain.model.PedidoDto
import com.example.app_mobile.domain.model.Produto
import com.example.app_mobile.domain.model.SessaoUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TelaAvaliarViewModel(
    val apiFeedback : FeedbackApiService,
    val apiPedido : PedidoApiService,
    val usuarioLogado: SessaoUsuario,
): ViewModel() {

    var pedido by mutableStateOf<CarrinhoDto?>(null)
        private set

    var carregando by mutableStateOf(false)
        private set

    var avaliado by mutableStateOf(false)
        private set

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    var mensagemSucesso by mutableStateOf<String?>(null)

    fun buscarPedidoPorId(id: Int) {
        viewModelScope.launch {
            carregando = true
            try {
                pedido = apiPedido.buscarCarrinho(id)
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar pedido por ID: ${e.message}")
                _erro.value = "Erro ao buscar pedido"
            } finally {
                carregando = false
            }
        }
    }

    // criar feedback

    fun criarAvaliacao(comentario: String, pedidoId: Int, usuarioId: Int?, imagens: SnapshotStateList<Any?>) {
        viewModelScope.launch {
            _erro.value = null
            try {
                val dto = FeedbackRequisicaoDto(
                    comentario = comentario,
                    pedido = pedidoId,
                    usuario = usuarioId,
                    //imagensFeedback = imagens
                )
                apiFeedback.criarAvaliacao(dto)

                mensagemSucesso = "Obrigado por avaliar!"
                carregando = false
            } catch (e: Exception) {
                Log.e("API", "Erro ao criar conta: ${e.message}")
                _erro.value = "Erro ao criar conta: ${e.message}"
            } finally {
                avaliado = true
            }
        }
    }

}