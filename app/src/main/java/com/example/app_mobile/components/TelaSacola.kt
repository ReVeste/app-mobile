package com.example.app_mobile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_mobile.R

@Composable
fun TelaSacola(navController: NavController){

    val itens = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        // Aqui é onde ocorrerá a busca dos itens do carrinho do usuário, atribuindo a lista

    }

    Scaffold(
        topBar = { CustomHeader(itens.size, navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (itens.isEmpty()) {
                Text(
                    text = "Nada no carrinho :)",
                    fontSize = 18.sp
                )
            } else {
                // Aqui virá a lista de itens quando houver produtos no carrinho

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomHeader(qtdItens: Int, navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = buildHeaderText(qtdItens),
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        )
    )
}

@Composable
fun buildHeaderText(qtdItens: Int): AnnotatedString {
    return buildAnnotatedString {
        append("Minha sacola")
        addStyle(SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold), 0, length)

        if (qtdItens > 0) {
            val itemText = " ($qtdItens itens)"
            append(itemText)
            addStyle(SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal), length - itemText.length, length)
        }
    }
}