package com.example.app_mobile.domain.model

data class SessaoUsuario(
    var userId: Int? = null,
    var nome: String? = null,
    var email: String? = null,
    var token: String? = null,
    var tipo: String? = null
) {

}