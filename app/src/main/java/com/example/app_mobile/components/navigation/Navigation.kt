package com.example.app_mobile.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_mobile.components.TelaConta
import com.example.app_mobile.components.TelaPeca
import com.example.app_mobile.components.TelaPrincipal
import com.example.app_mobile.components.TelaSacola

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
        composable(route = "TelaConta") { TelaConta(navController) }
        composable(route = "TelaLogin") { TelaConta(navController) }
        composable(route = "TelaCadastro") { TelaConta(navController) }
    }
}