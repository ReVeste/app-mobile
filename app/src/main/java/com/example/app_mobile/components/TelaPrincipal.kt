package com.example.app_mobile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.app_mobile.R

@Composable
fun TelaPrincipal(navController: NavController) {
    var categoriaSelecionada by remember { mutableStateOf("Roupas") }

    Scaffold(
        topBar = { CustomHeader(categoriaSelecionada, {
            categoriaSelecionada = it
            println("Categoria alterada para: $categoriaSelecionada")
        }, navController) },
        bottomBar = { CustomBottomBar(navController) }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = innerPadding,
            modifier = Modifier.padding(10.dp)
        ) {
            if (categoriaSelecionada != "Avaliações") {
                items(10) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                println("Componente $index clicado na categoria $categoriaSelecionada!")
                                navController.navigate("TelaPeca/$index")
                            }
                    ) {
                        ComponentPecas(
                            imageResId = R.drawable.camisetakiss,
                            nome = "Produto $index",
                            especificacao = "Cor x, Tamanho $index",
                            preco = 3.14,
                            parcelas = "3x em R$1.45 sem juros"
                        )
                    }
                }
            } else {
                items(10) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                println("Componente $index clicado na categoria $categoriaSelecionada!")
                            }
                    ) {
                        ComponentAvaliacoes(
                            imageResId = R.drawable.camisetakiss,
                            nome = "Título da Carta $index",
                            especificacao = "Cor x, Tamanho $index"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomHeader(categoriaSelecionada: String, onCategoriaSelecionada: (String) -> Unit, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "icone earthmoon",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = { navController.navigate("TelaSacola") },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sacolaicon),
                    contentDescription = "Carrinho",
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CategoryButton("Roupas", categoriaSelecionada, onCategoriaSelecionada)
            CategoryButton("Acessórios", categoriaSelecionada, onCategoriaSelecionada)
            CategoryButton("Avaliações", categoriaSelecionada, onCategoriaSelecionada)
        }
    }
}

@Composable
fun CategoryButton(text: String, categoriaSelecionada: String, onCategoriaSelecionada: (String) -> Unit) {
    Button(
        onClick = { onCategoriaSelecionada(text) },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (text == categoriaSelecionada) Color.DarkGray else Color.Black,
            contentColor = Color.White
        ),
        modifier = Modifier.width(120.dp)
    ) {
        Text(text)
    }
}

@Composable
fun CustomBottomBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .zIndex(0f)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(50.dp))
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(vertical = 8.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { navController.navigate("TelaPrincipal") },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = { navController.navigate("TelaConta") },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.usericon),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}