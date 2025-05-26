package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mobile.data.network.api.PedidoApiService
import com.example.app_mobile.domain.model.ProdutoDto
import com.example.app_mobile.domain.model.SessaoUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class TelaSacolaViewModel(val api : PedidoApiService, val sessaoUsuario : SessaoUsuario) : ViewModel() {

    private val _produtosCarrinho = mutableStateListOf<ProdutoDto>()
    val produtosCarrinho: List<ProdutoDto> = _produtosCarrinho.toList()

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    init {
        atualizarProdutosPedidoEmAberto()
    }

    fun atualizarProdutosPedidoEmAberto() {

        if(sessaoUsuario.userId == null) {
            return
        }

        viewModelScope.launch{
        try {
            _produtosCarrinho.addAll(api.listarProdutosPedidoEmAberto(sessaoUsuario.userId!!))
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("API", "Erro ao buscar produtos por idUsuario: ${e.message}")
            _erro.value = "Falha ao buscar produtos"
        }
            }
    }

    fun removerProdutoPedido(idPedido: Int, idProduto: Int) {
        viewModelScope.launch {
            try {
                api.removerProduto(idPedido, idProduto)
                atualizarProdutosPedidoEmAberto()
                _erro.value = null
            } catch (e: HttpException) {
                Log.e("PedidoVM", "Falha HTTP ao remover: ${e.code()} ${e.message()}")
                _erro.value = "Falha ao remover o produto"
            } catch (e: Exception) {
                Log.e("PedidoVM", "Erro ao remover produto: ${e.message}")
                _erro.value = "Erro inesperado: ${e.message}"
            }
        }
    }

    // falta remover todos

}