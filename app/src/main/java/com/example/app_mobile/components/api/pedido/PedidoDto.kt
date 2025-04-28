package com.example.app_mobile.components.api.pedido

import com.example.app_mobile.components.api.usuario.Usuario

data class PedidoDto(
    val id: Int,
    val dataHora: String,
    val tipoFrete: String,
    val valorFrete: Double,
    val valorTotal: Double,
    val status: String,
    val usuario: Usuario
)