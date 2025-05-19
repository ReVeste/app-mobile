package com.example.app_mobile.domain.model

data class Usuario(
    val id: Int,
    val nome: String,
    val cpf: String,
    val telefone: String,
    val email: String,
    val tipo: String,
    val ativo: Boolean,
    val imagemUrl: String?,
    val dataCadastro: String
) {

}
