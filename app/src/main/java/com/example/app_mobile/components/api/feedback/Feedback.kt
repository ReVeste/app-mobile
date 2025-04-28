package com.example.app_mobile.components.api.feedback

import com.example.app_mobile.components.api.usuario.SessaoUsuario
import com.example.app_mobile.components.api.usuario.Usuario

data class Feedback(
    val id: Int,
    val nota: Int,
    val comentario: String,
    val usuario: Usuario

) {

}