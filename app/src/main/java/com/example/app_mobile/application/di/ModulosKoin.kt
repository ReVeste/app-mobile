package com.example.app_mobile.application.di

import com.example.app_mobile.application.config.Configuracoes.BASE_URL_REVESTE_API
import com.example.app_mobile.data.network.api.EnderecoApiService
import com.example.app_mobile.data.network.api.FeedbackApiService
import com.example.app_mobile.data.network.api.PedidoApiService
import com.example.app_mobile.data.network.api.ProdutoApiService
import com.example.app_mobile.domain.model.SessaoUsuario
import com.example.app_mobile.data.network.api.UsuarioApiService
import com.example.app_mobile.data.network.interceptor.TokenInterceptor
import com.example.app_mobile.presentation.viewmodel.TelaAvaliacaoViewModel
import com.example.app_mobile.presentation.viewmodel.TelaAvaliarViewModel
import com.example.app_mobile.presentation.viewmodel.TelaContaViewModel
import com.example.app_mobile.presentation.viewmodel.TelaLoginCadastroViewModel
import com.example.app_mobile.presentation.viewmodel.TelaPecaViewModel
import com.example.app_mobile.presentation.viewmodel.TelaPrincipalViewModel
import com.example.app_mobile.presentation.viewmodel.TelaSacolaViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val moduloGeral = module {

    val BASE_URL = BASE_URL_REVESTE_API

    // single -> devolve a MESMA instância para todos que pedirem
    single<SessaoUsuario> {
        SessaoUsuario()
    }

    single<Retrofit>{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val clienteHttp = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(TokenInterceptor(get<SessaoUsuario>().token ?: ""))
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory<UsuarioApiService> {
        get<Retrofit>().create(UsuarioApiService::class.java)
    }

    factory<ProdutoApiService> {
        get<Retrofit>().create(ProdutoApiService::class.java)
    }

    factory<PedidoApiService> {
        get<Retrofit>().create(PedidoApiService::class.java)
    }

    factory<FeedbackApiService> {
        get<Retrofit>().create(FeedbackApiService::class.java)
    }

    factory<EnderecoApiService> {
        get<Retrofit>().create(EnderecoApiService::class.java)
    }

     viewModel<TelaAvaliacaoViewModel> {
        TelaAvaliacaoViewModel(get<FeedbackApiService>(), get<PedidoApiService>())
    }

    viewModel<TelaAvaliarViewModel> {
        TelaAvaliarViewModel(get<FeedbackApiService>(), get<PedidoApiService>(), get<SessaoUsuario>())
    }

    viewModel<TelaContaViewModel> {
        TelaContaViewModel(get<UsuarioApiService>(), get<SessaoUsuario>(), get<PedidoApiService>())
    }

    viewModel<TelaLoginCadastroViewModel> {
        TelaLoginCadastroViewModel(get<UsuarioApiService>(), get<SessaoUsuario>())
    }

    viewModel<TelaPecaViewModel> {
        TelaPecaViewModel(get<PedidoApiService>(), get<ProdutoApiService>(), get<SessaoUsuario>())
    }

    viewModel<TelaPrincipalViewModel> {
        TelaPrincipalViewModel(get<ProdutoApiService>(), get<FeedbackApiService>(), get<SessaoUsuario>())
    }

    viewModel<TelaSacolaViewModel> {
        TelaSacolaViewModel(get<PedidoApiService>(),get<SessaoUsuario>(),get<EnderecoApiService>())
    }

}