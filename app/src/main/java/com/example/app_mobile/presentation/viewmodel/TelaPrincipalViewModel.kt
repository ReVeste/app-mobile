package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mobile.data.network.api.FeedbackApiService
import com.example.app_mobile.data.network.api.PedidoApiService
import com.example.app_mobile.data.network.api.ProdutoApiService
import com.example.app_mobile.domain.model.Feedback
import com.example.app_mobile.domain.model.Produto
import com.example.app_mobile.domain.model.SessaoUsuario
import kotlinx.coroutines.launch

class TelaPrincipalViewModel(
    val apiProduto : ProdutoApiService,
    val apiFeedback : FeedbackApiService,
    val sessaoUsuario : SessaoUsuario) : ViewModel() {

    private val _todosProdutos = mutableStateListOf<Produto>()
    val produtos: List<Produto> = _todosProdutos.toList()

    private val _todosFeedbacks = mutableStateListOf<Feedback>()
    var feedbacks: List<Feedback> = _todosFeedbacks.toList()

    var carregando by mutableStateOf(false)
        private set

    var erro by mutableStateOf<String?>(null)
        private set

    init {
        buscarTodos()
        buscarTodosFeedback()
    }

    fun buscarTodos() {
        viewModelScope.launch {
            carregando = true
            erro = null
            try {
                _todosProdutos.clear()
                _todosProdutos.addAll(apiProduto.buscarTodos())
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar produtos: ${e.message}")
                erro = "Erro ao buscar produtos: ${e.message}"
            } finally {
                carregando = false
            }
        }
    }

    fun filtrarPorCategoria(categoria: String): List<Produto> {
        return when (categoria) {
            "Roupas" -> _todosProdutos.filter { it.categoria.equals("ROUPA", ignoreCase = true) }
            "Acessórios" -> _todosProdutos.filter { it.categoria.equals("ACESSORIO", ignoreCase = true) }
            else -> emptyList()
        }
    }

    fun buscarTodosFeedback() {
        viewModelScope.launch {
            carregando = true
            erro = null
            try {
                _todosFeedbacks.addAll(apiFeedback.buscarTodos())
            } catch (e: Exception) {
                erro = "Erro ao buscar feedbacks: ${e.message}"
            } finally {
                carregando = false
            }
        }
    }

}