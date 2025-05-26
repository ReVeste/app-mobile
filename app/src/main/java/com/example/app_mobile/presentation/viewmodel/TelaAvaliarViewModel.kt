package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.app_mobile.data.network.api.FeedbackApiService
import com.example.app_mobile.data.network.api.PedidoApiService
import com.example.app_mobile.domain.model.Feedback

class TelaAvaliarViewModel(
    val apiFeedback : FeedbackApiService,
    val apiPedido : PedidoApiService
): ViewModel() {

    suspend fun buscarPorId(id: Int): Feedback? {
        return try {
            apiFeedback.buscarPorId(id)
            // trocar por busca por id de Pedido.
            // apiPedido.buscarPorId()
        } catch (e: Exception) {
            Log.e("API", "Erro ao buscar feedback por ID: ${e.message}")
            null
        }
    }

    // suspend fun criarFeedback() {
//
//  }

    // buscar pedido
    // criar feedback

}