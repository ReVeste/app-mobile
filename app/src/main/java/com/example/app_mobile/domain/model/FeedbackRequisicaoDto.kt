package com.example.app_mobile.domain.model

data class FeedbackRequisicaoDto(
    val comentario: String,
    val pedido: Int?,
    val usuario: Int?,
    val imagensFeedback: List<String> = emptyList()
)