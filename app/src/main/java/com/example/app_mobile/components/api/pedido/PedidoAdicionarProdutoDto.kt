package com.example.app_mobile.components.api.pedido

data class PedidoAdicionarProdutoDto(
    val idUsuario: Int,
    val idProduto: Int,
    val quantidadeProduto: Int
)