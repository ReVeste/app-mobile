package com.example.app_mobile.domain.model

data class PreferenceDto(
    val itens: List<ProdutosDto>,
    val usuarioNome: String?,
    val usuarioEmail: String?,
    val usuarioCodigoTelefone: String?,
    val usuarioNumeroTelefone: String?,
    val usuarioCpf: String?,
    val usuarioCep: String?,
    val usuarioRua: String?,
    val usuarioNumeroCasa: String
)

data class ProdutosDto(
    val produtoId: String,
    val produtoNome: String,
    val produtoDescricao: String,
    val produtoQuantidade: Int,
    val produtoPreco: Double
)