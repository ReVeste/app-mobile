package com.example.app_mobile.utils.navigation

import com.example.app_mobile.presentation.component.CriarContaScreen
import com.example.app_mobile.presentation.component.LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_mobile.presentation.component.TelaAvaliacao
import com.example.app_mobile.presentation.component.TelaAvaliar
import com.example.app_mobile.presentation.component.TelaConta
import com.example.app_mobile.presentation.component.TelaPeca
import com.example.app_mobile.presentation.component.TelaPrincipal
import com.example.app_mobile.presentation.component.TelaSacola
import android.util.Log
import com.example.app_mobile.presentation.viewmodel.TelaAvaliacaoViewModel
import com.example.app_mobile.presentation.viewmodel.TelaAvaliarViewModel
import com.example.app_mobile.presentation.viewmodel.TelaContaViewModel
import com.example.app_mobile.presentation.viewmodel.TelaLoginCadastroViewModel
import com.example.app_mobile.presentation.viewmodel.TelaPecaViewModel
import com.example.app_mobile.presentation.viewmodel.TelaPrincipalViewModel
import com.example.app_mobile.presentation.viewmodel.TelaSacolaViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val telaAvaliacaoViewModel : TelaAvaliacaoViewModel = koinViewModel()
    val telaAvaliarViewModel : TelaAvaliarViewModel = koinViewModel()
    val telaContaViewModel : TelaContaViewModel = koinViewModel()
    val telaLoginCadastroViewModel : TelaLoginCadastroViewModel = koinViewModel()
    val telaPecaViewModel : TelaPecaViewModel = koinViewModel()
    val telaPrincipalViewModel : TelaPrincipalViewModel = koinViewModel()
    val telaSacolaViewModel : TelaSacolaViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = "TelaPrincipal"
    ) {
        composable(route = "TelaPrincipal") { TelaPrincipal(navController, telaPrincipalViewModel) }
        composable(route = "TelaSacola") { TelaSacola(navController, telaSacolaViewModel) }

        composable(route = "TelaPeca/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            TelaPeca(navController, index, telaPecaViewModel)
        }

        composable(route = "TelaAvaliacao/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            TelaAvaliacao(navController, index, telaAvaliacaoViewModel)
        }

        composable(route = "TelaConta/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            Log.d("debug", "${userId}")
            if (userId == 0) {
                navController.navigate("TelaLogin")
            } else {
            Log.d("debug", "${userId}")
                TelaConta(navController = navController, telaContaViewModel)
            }
        }

        composable(route = "TelaLogin") {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onCreateAccount = { navController.navigate("TelaCadastro") },
                navController = navController,
                viewModel = telaLoginCadastroViewModel
            )
        }

        composable(route = "TelaCadastro") {
            CriarContaScreen(
                onBack = { navController.popBackStack() },
                onCreateAccount = {
                    navController.navigate("TelaPrincipal") {
                        popUpTo("TelaLogin") { inclusive = true }
                    }
                },
                navController = navController,
                viewModel = telaLoginCadastroViewModel
            )
        }

        composable(route = "TelaAvaliar/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            TelaAvaliar(navController, index, telaAvaliarViewModel)
        }
    }
}