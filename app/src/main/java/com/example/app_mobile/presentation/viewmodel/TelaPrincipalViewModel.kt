package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mobile.data.network.api.PedidoApiService
import com.example.app_mobile.data.network.api.ProdutoApiService
import com.example.app_mobile.domain.model.Produto
import com.example.app_mobile.domain.model.ProdutoDto
import com.example.app_mobile.domain.model.SessaoUsuario
import kotlinx.coroutines.launch

class TelaPrincipalViewModel(val api : ProdutoApiService, val sessaoUsuario : SessaoUsuario) : ViewModel() {

    private val _todosProdutos = mutableStateListOf<Produto>()
    val produtos: List<Produto> = _todosProdutos.toList()

    var carregando by mutableStateOf(false)
        private set

    var erro by mutableStateOf<String?>(null)
        private set

    init {
        buscarTodos()
    }

    fun buscarTodos() {
        viewModelScope.launch {
            carregando = true
            erro = null
            try {
                _todosProdutos.addAll(api.buscarTodos())
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
            "Roupas" -> produtos.filter { it.categoria.equals("ROUPA", ignoreCase = true) }
            "Acessórios" -> produtos.filter { it.categoria.equals("ACESSORIO", ignoreCase = true) }
            else -> emptyList()
        }
    }

    // falta buscar feedbacks

}