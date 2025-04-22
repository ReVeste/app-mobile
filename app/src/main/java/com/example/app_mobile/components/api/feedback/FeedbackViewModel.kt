package com.example.app_mobile.components.api.feedback

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FeedbackViewModel : ViewModel() {

    var listaFeedbacks by mutableStateOf(listOf<Feedback>())
        private set

    var carregando by mutableStateOf(false)
        private set

    var erro by mutableStateOf<String?>(null)
        private set

    fun buscarTodos() {
        viewModelScope.launch {
            carregando = true
            erro = null
            try {
                listaFeedbacks = FeedbackApi.api.buscarTodos()
            } catch (e: Exception) {
                erro = "Erro ao buscar feedbacks: ${e.message}"
            } finally {
                carregando = false
            }
        }
    }

    suspend fun buscarPorId(id: Int): Feedback? {
        return try {
            FeedbackApi.api.buscarPorId(id)
        } catch (e: Exception) {
            Log.e("API", "Erro ao buscar feedback por ID: ${e.message}")
            null
        }
    }


}
