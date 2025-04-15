package com.example.app_mobile.components.api

import com.example.app_mobile.components.api.usuario.SessaoUsuario
import org.koin.dsl.module

val moduloGeral = module {

    // single -> devolve a MESMA instância para todos que pedirem
    single<SessaoUsuario> {
        SessaoUsuario()
    }
}