package com.example.app_mobile.domain.model

data class ProdutoDto(
    val id: String = "",
    val nome: String = "",
    val tamanho: String = "",
    val categoria: String = "",
    val marca: String = "",
    val preco: Double = 0.0,
    val descricao: String = "",
    val qtdEstoque: Int = 0,
    val status: String = "",
    val idPedido: Int = 0,
    val imagens: List<String> = emptyList()
)