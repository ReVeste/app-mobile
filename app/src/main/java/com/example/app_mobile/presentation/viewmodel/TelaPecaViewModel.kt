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
import com.example.app_mobile.domain.model.CarrinhoDto
import com.example.app_mobile.domain.model.PedidoAdicionarProdutoDto
import com.example.app_mobile.domain.model.Produto
import com.example.app_mobile.domain.model.SessaoUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class TelaPecaViewModel(val apiPedido : PedidoApiService,
                        val apiProduto : ProdutoApiService,
                        val sessaoUsuario : SessaoUsuario) : ViewModel() {

    var produto by mutableStateOf<Produto?>(null)
        private set

    var carregando by mutableStateOf(false)
        private set

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    fun buscarPorId(id: Int) {
        viewModelScope.launch {
            carregando = true
            try {
                produto = apiProduto.buscarPorId(id)
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar produto por ID: ${e.message}")
                _erro.value = "Erro ao buscar produto"
            } finally {
                carregando = false
            }
        }
    }

    fun adicionarProduto(idProduto: Int) {
        viewModelScope.launch {
            try {
                val pedido = PedidoAdicionarProdutoDto(
                    sessaoUsuario.userId!!,
                    idProduto,
                    1)

                apiPedido.adicionarProduto(pedido)
                _erro.value = null
            } catch (e: HttpException) {
                Log.e("PedidoVM", "Falha HTTP ao adicionar produto: ${e.code()} ${e.message()}")
                _erro.value = "Erro ao adicionar produto"
            } catch (e: Exception) {
                Log.e("PedidoVM", "Erro ao adicionar produto: ${e.message}")
                _erro.value = "Erro inesperado: ${e.message}"
            }
        }
    }

}
