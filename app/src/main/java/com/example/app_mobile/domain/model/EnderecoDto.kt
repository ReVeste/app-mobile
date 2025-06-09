package com.example.app_mobile.domain.model

data class EnderecoDto(
    val id: Int,
    val apelido: String,
    val cep: String,
    val rua: String,
    val numero: Int,
    val complemento: String,
    val bairro: String,
    val cidade: String,
    val uf: String
)