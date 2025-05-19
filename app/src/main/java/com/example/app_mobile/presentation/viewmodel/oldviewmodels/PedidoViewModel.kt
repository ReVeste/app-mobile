package com.example.app_mobile.presentation.viewmodel.oldviewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mobile.data.network.api.PedidoApi
import com.example.app_mobile.domain.model.ProdutoDto
import com.example.app_mobile.domain.model.CarrinhoDto
import com.example.app_mobile.domain.model.PedidoAdicionarProdutoDto
import com.example.app_mobile.domain.model.PedidoDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PedidoViewModel : ViewModel() {

    private val api = PedidoApi.api

    private val _produtosCarrinho = MutableStateFlow<List<ProdutoDto>>(emptyList())
    val produtosCarrinho: List<ProdutoDto> = _produtosCarrinho.value

    // Estado para exibir mensagens de erro
    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    fun adicionarProduto(pedido: PedidoAdicionarProdutoDto) {
        viewModelScope.launch {
            try {
                val resposta: CarrinhoDto = api.adicionarProduto(pedido)
                _produtosCarrinho.value = resposta.produtos
                _erro.value = null // Limpa o erro caso a operação seja bem-sucedida
            } catch (e: HttpException) {
                Log.e("PedidoVM", "Falha HTTP ao adicionar produto: ${e.code()} ${e.message()}")
                _erro.value = "Erro ao adicionar produto"
            } catch (e: Exception) {
                Log.e("PedidoVM", "Erro ao adicionar produto: ${e.message}")
                _erro.value = "Erro inesperado: ${e.message}"
            }
        }
    }

    suspend fun listarProdutosPedidoEmAberto(idUsuario: Int): List<ProdutoDto> {
        return try {
            PedidoApi.api.listarProdutosPedidoEmAberto(idUsuario)
        } catch (e: Exception) {
            Log.e("API", "Erro ao buscar produtos por idUsuario: ${e.message}")
            emptyList()
        }
    }

    suspend fun buscarPorStatus(idUsuario: Int, status: String): List<PedidoDto> {
        return try {
            PedidoApi.api.buscarPorStatus(idUsuario, status)
        } catch (e: Exception) {
            Log.e("API", "Erro ao buscar produtos por idUsuario: ${e.message}")
            emptyList()
        }
    }

    fun removerProdutoPedido(idPedido: Int, idProduto: Int) {
        viewModelScope.launch {
            try {
                api.removerProduto(idPedido, idProduto)
                _produtosCarrinho.value = _produtosCarrinho.value.filterNot { it.id == idProduto }
                _erro.value = null // Limpa o erro caso a operação seja bem-sucedida
            } catch (e: HttpException) {
                Log.e("PedidoVM", "Falha HTTP ao remover: ${e.code()} ${e.message()}")
                _erro.value = "Falha ao remover o produto"
            } catch (e: Exception) {
                Log.e("PedidoVM", "Erro ao remover produto: ${e.message}")
                _erro.value = "Erro inesperado: ${e.message}"
            }
        }
    }

}