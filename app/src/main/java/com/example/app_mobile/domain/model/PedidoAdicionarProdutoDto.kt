package com.example.app_mobile.domain.model

data class PedidoAdicionarProdutoDto(
    val idUsuario: Int,
    val idProduto: Int,
    val quantidadeProduto: Int
)