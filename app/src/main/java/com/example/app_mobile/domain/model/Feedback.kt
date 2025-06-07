package com.example.app_mobile.domain.model

data class Feedback(
    val id: Int,
    val comentario: String,
    val idPedido: Int,
    val usuario: UsuarioFeedback,
    val imagensFeedbacks: List<ImagemFeedback>
)

data class UsuarioFeedback(
    val id: Int,
    val nome: String
)