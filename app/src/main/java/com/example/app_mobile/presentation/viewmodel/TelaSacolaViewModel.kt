package com.example.app_mobile.presentation.viewmodel

import android.util.Log
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_mobile.data.network.api.CriarEnderecoDto
import com.example.app_mobile.data.network.api.EnderecoApiService
import com.example.app_mobile.data.network.api.PedidoApiService
import com.example.app_mobile.domain.model.EnderecoDto
import com.example.app_mobile.domain.model.FeedbackRequisicaoDto
import com.example.app_mobile.domain.model.OpcaoFrete
import com.example.app_mobile.domain.model.Preference
import com.example.app_mobile.domain.model.PreferenceDto
import com.example.app_mobile.domain.model.Produto
import com.example.app_mobile.domain.model.ProdutoDto
import com.example.app_mobile.domain.model.ProdutosDto
import com.example.app_mobile.domain.model.SessaoUsuario
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class TelaSacolaViewModel(
    val api : PedidoApiService,
    val sessaoUsuario : SessaoUsuario,
    val enderecoApi : EnderecoApiService
) : ViewModel() {

    val _produtosCarrinho = mutableStateListOf<ProdutoDto>()
    val produtosCarrinho: List<ProdutoDto> = _produtosCarrinho.toList()

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    val _enderecos = mutableStateListOf<EnderecoDto>()

    var enderecoAtual by mutableStateOf<EnderecoDto?>(null)
        private set

    val opcoesFrete = mutableStateListOf<OpcaoFrete>()

    var initPoint by mutableStateOf<String?>(null)

    fun atualizarProdutosPedidoEmAberto() {

        if (sessaoUsuario.userId == null) {
            return
        }

        viewModelScope.launch {
            try {
                _produtosCarrinho.clear()
                _produtosCarrinho.addAll(api.listarProdutosPedidoEmAberto(sessaoUsuario.userId!!))
                Log.e(
                    "API",
                    "Busca de produtos por idUsuario concluida: ${_produtosCarrinho.toList()}"
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API", "Erro ao buscar produtos por idUsuario: ${e.message}")
                _erro.value = "Falha ao buscar produtos"
            }
        }
    }

    fun removerProdutoPedido(idPedido: Int, idProduto: String) {
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

    fun buscarEnderecos() {
        viewModelScope.launch {
            _enderecos.clear()
            try {
                _enderecos.addAll(enderecoApi.buscarPorUsuario(sessaoUsuario.userId!!))
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar endereços por ID de usuário: ${e.message}")
                _erro.value = "Erro ao buscar endereços do usuário"
            }
        }
    }

    fun cadastrarEndereco(apelido: String, cep: String,
                          rua: String, numero: Int,
                          complemento: String, bairro: String,
                          cidade: String, uf: String,) {

        viewModelScope.launch {
            _erro.value = null
            try {
                val dto = CriarEnderecoDto(
                    apelido = apelido,
                    cep = cep,
                    rua = rua,
                    numero = numero,
                    complemento = complemento,
                    bairro = bairro,
                    cidade = cidade,
                    uf = uf,
                    idUsuario = sessaoUsuario.userId!!
                )
                enderecoAtual = enderecoApi.registrarEndereco(dto)
            } catch (e: Exception) {
                Log.e("API", "Erro ao cadastrar endereço: ${e.message}")
                _erro.value = "Erro ao cadastrar endereço: ${e.message}"
            } finally {
                calcularFrete(cep)
            }
        }
    }

    fun calcularFrete(cep: String) {
        viewModelScope.launch {
            try {
                opcoesFrete.clear()
                opcoesFrete.addAll(enderecoApi.calcularFrete(cep))
                Log.e(
                    "API",
                    "Busca de opções de frete: ${opcoesFrete.toList()}"
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API", "Erro ao buscar opções de frete: ${e.message}")
                _erro.value = "Falha ao buscar opções de frete"
            } finally {
                criarPreferencia()
            }
        }
    }

    fun criarPreferencia() {
        viewModelScope.launch {
            try {
                val freteMaisBarato = opcoesFrete
                    .filter { it.custom_price != null && it.error == null }
                    .minByOrNull { it.custom_price.toDouble() }
                val frete = freteMaisBarato?.custom_price?.toDoubleOrNull() ?: 0.0

                val valorFrete = ProdutoDto(
                    id = "Frete",
                    nome = "Frete",
                    preco = frete,
                    descricao = "Custo de entrega",
                    qtdEstoque = 1
                )

                Log.e("Api", "Valor frete: $valorFrete")

                val listaComFrete = _produtosCarrinho + valorFrete
                val usuarioDetail = api.buscarUsuario(sessaoUsuario.userId!!)

                val dto = PreferenceDto(
                    itens = listaComFrete.map { produto ->
                        ProdutosDto(
                            produtoId = produto.id.toString(),
                            produtoNome = produto.nome,
                            produtoDescricao = produto.descricao,
                            produtoQuantidade = 1,
                            produtoPreco = produto.preco
                        )
                    },
                    usuarioNome = sessaoUsuario.nome,
                    usuarioEmail = sessaoUsuario.email,
                    usuarioCodigoTelefone = usuarioDetail.telefone.take(2),
                    usuarioNumeroTelefone = usuarioDetail.telefone.drop(2),
                    usuarioCpf = usuarioDetail.cpf,
                    usuarioCep = enderecoAtual?.cep ?: "",
                    usuarioRua = enderecoAtual?.rua ?: "",
                    usuarioNumeroCasa = enderecoAtual?.numero?.toString() ?: ""
                )

                val response = api.criarPreferencia(dto)

                val rawJson = response.body()?.string()
                Log.d("API_RAW", "Resposta crua: $rawJson")

                if (response.isSuccessful && !rawJson.isNullOrBlank()) {
                    val gson = Gson()
                    val preference = gson.fromJson(rawJson, Preference::class.java)

                    initPoint = preference.initPoint
                    Log.d("MercadoPago", "InitPoint URL: $initPoint")

                } else {
                    val errorText = response.errorBody()?.string()
                    Log.e("API", "Erro ao criar preferência. Código: ${response.code()}")
                    Log.e("API", "Corpo de erro: $errorText")
                    _erro.value = "Erro ao criar preferência. Verifique os dados."
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API", "Exceção: ${e.message}")
                _erro.value = "Falha ao criar preferência. Tente novamente."
            }
        }
    }

    fun atualizarPedidoPago(){
        viewModelScope.launch {
            try {
                api.atualizarPedidoPago(_produtosCarrinho.get(0).idPedido)
                Log.e(
                    "API",
                    "Atualizar pedido para status pago"
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API", "Erro ao atualizar pedido para status pago: ${e.message}")
                _erro.value = "Falha ao atualizar pedido para pago"
            }
        }
    }

}