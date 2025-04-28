package com.example.app_mobile.components.api.produto

data class ProdutoDto(
    val id: Int,
    val nome: String,
    val tamanho: String,
    val categoria: String,
    val marca: String,
    val preco: Double,
    val descricao: String,
    val qtdEstoque: Int,
    val status: String,
    val idPedido: Int,
    val imagens: List<String>
)