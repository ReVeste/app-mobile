package com.example.app_mobile.domain.model

data class PedidoDto(
    val id: Int,
    val dataHora: String,
    val tipoFrete: String,
    val valorFrete: Double,
    val valorTotal: Double,
    val status: String,
    val usuario: Usuario
)