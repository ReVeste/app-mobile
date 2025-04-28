package com.example.app_mobile.components.api.pedido

import com.example.app_mobile.components.api.produto.ProdutoDto

data class CarrinhoDto(
    val id: Int,
    val dataHora: String,
    val tipoFrete: String,
    val valorFrete: Double,
    val valorTotal: Double,
    val status: String,
    val nomeUsuario: String,
    val produtos: List<ProdutoDto>
)