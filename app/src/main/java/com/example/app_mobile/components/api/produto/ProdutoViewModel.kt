package com.example.app_mobile.components.api.produto

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProdutoViewModel : ViewModel() {

    var todosProdutos by mutableStateOf(listOf<Produto>())
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
                todosProdutos = ProdutoApi.api.buscarTodos()
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar produtos: ${e.message}")
                erro = "Erro ao buscar produtos: ${e.message}"
            } finally {
                carregando = false
            }
        }
    }

    fun filtrarPorCategoria(categoriaUi: String): List<Produto> {
        return when (categoriaUi) {
            "Roupas" -> todosProdutos.filter { it.categoria.equals("ROUPA", ignoreCase = true) }
            "Acessórios" -> todosProdutos.filter { it.categoria.equals("ACESSORIO", ignoreCase = true) }
            else -> emptyList()
        }
    }

    suspend fun buscarPorId(id: Int): Produto? {
        return try {
            ProdutoApi.api.buscarPorId(id)
        } catch (e: Exception) {
            Log.e("API", "Erro ao buscar produto por ID: ${e.message}")
            null
        }
    }

}