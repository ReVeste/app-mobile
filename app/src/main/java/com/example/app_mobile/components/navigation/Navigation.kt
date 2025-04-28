package com.example.app_mobile.components.navigation

import CriarContaScreen
import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_mobile.components.TelaAvaliacao
import com.example.app_mobile.components.TelaAvaliar
import com.example.app_mobile.components.TelaConta
import com.example.app_mobile.components.TelaPeca
import com.example.app_mobile.components.TelaPrincipal
import com.example.app_mobile.components.TelaSacola
import androidx.navigation.NavType
import androidx.navigation.navArgument
import android.util.Log


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "TelaPrincipal"
    ) {
        composable(route = "TelaPrincipal") { TelaPrincipal(navController) }
        composable(route = "TelaSacola") { TelaSacola(navController) }

        composable(route = "TelaPeca/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            TelaPeca(navController, index)
        }

        composable(route = "TelaAvaliacao/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            TelaAvaliacao(navController, index)
        }

        composable(route = "TelaConta/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            Log.d("debug", "${userId}")
            if (userId == 0) {
                navController.navigate("TelaLogin")
            } else {
            Log.d("debug", "${userId}")
                TelaConta(userId = userId, navController = navController)
            }
        }


        composable(route = "VerificarTelaConta") {

        }

        composable(route = "TelaLogin") {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onCreateAccount = { navController.navigate("TelaCadastro") },
                navController = navController
            )
        }

        composable(route = "TelaCadastro") {
            CriarContaScreen(
                onBack = { navController.popBackStack() },
                onCreateAccount = {
                    navController.navigate("TelaPrincipal") {
                        popUpTo("TelaLogin") { inclusive = true }
                    }
                }
            )
        }

        composable(route = "TelaAvaliar/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            TelaAvaliar(navController, index)
        }
        composable(route = "TelaConta") { TelaConta(navController) }
        composable(route = "TelaLogin") { TelaConta(navController) }
        composable(route = "TelaCadastro") { TelaConta(navController) }
    }
}