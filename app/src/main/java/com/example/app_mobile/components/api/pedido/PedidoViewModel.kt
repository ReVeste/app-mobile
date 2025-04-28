package com.example.app_mobile.components.api.pedido

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mobile.components.api.produto.Produto
import com.example.app_mobile.components.api.produto.ProdutoApi
import com.example.app_mobile.components.api.produto.ProdutoDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PedidoViewModel : ViewModel() {

    private val pedidoApiService: PedidoApiService = PedidoApi.api

    private val _carrinho = MutableStateFlow<CarrinhoDto?>(null)
    val carrinho: StateFlow<CarrinhoDto?> = _carrinho

    fun adicionarProduto(pedido: PedidoAdicionarProdutoDto) {
        viewModelScope.launch {
            try {
                val resposta = pedidoApiService.adicionarProduto(pedido)
                _carrinho.value = resposta
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun listarProdutosPedidoEmAberto(idUsuario: Int): List<ProdutoDto> {
        return try {
            PedidoApi.api.buscarPorStatus(idUsuario)
        } catch (e: Exception) {
            Log.e("API", "Erro ao buscar produtos por idUsuario: ${e.message}")
            emptyList()
        }
    }

}