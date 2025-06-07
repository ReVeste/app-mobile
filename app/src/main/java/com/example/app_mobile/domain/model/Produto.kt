package com.example.app_mobile.domain.model

data class Produto(
    val id: Int,
    val nome: String,
    val tamanho: String,
    val marca: String,
    val categoria: String,
    val preco: Double,
    val descricao: String,
    val qtdEstoque: Int,
    val status: String,
    val imagens: List<String>
) {

}