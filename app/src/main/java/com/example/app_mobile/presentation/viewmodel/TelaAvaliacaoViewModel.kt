package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.app_mobile.data.network.api.FeedbackApiService
import com.example.app_mobile.domain.model.Feedback
import com.example.app_mobile.domain.model.SessaoUsuario

class TelaAvaliacaoViewModel(
    val apiFeedback : FeedbackApiService
): ViewModel() {

    suspend fun buscarPorId(id: Int): Feedback? {
        return try {
            apiFeedback.buscarPorId(id)
        } catch (e: Exception) {
            Log.e("API", "Erro ao buscar feedback por ID: ${e.message}")
            null
        }
    }

}