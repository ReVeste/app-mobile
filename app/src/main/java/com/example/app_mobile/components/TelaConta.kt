package com.example.app_mobile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TelaConta(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate("TelaPrincipal") }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
            }
            Text(text = "Minha conta", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        InformacoesUsuario()

        Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth())

        Text("Histórico de pedidos", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyColumn(
            modifier = Modifier.height(200.dp)
        ) {
            items(10) { index ->
                PedidoItem("Pedido $index", "Data $index") { /* Avaliar ação */ }
            }
        }

        Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp))

        Text("Pedidos avaliados", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyColumn(
            modifier = Modifier.height(200.dp)
        ) {
            items(5) { index ->
                PedidoAvaliadoItem("Produto $index", "01/03/2025")
            }
        }
    }

}

@Composable
fun InformacoesUsuario() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Nome", fontWeight = FontWeight.Bold)
                Text("Nome Usuário")
            }
            Button(
                onClick = { /* Ação de editar */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD9D9D9),
                    contentColor = Color.Black
                ),
                modifier = Modifier.size(width = 80.dp, height = 32.dp)
            ) {
                Text("Editar", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Endereço de e-mail", fontWeight = FontWeight.Bold)
                Text("usuario@example.com")
            }
            Button(
                onClick = { /* Ação de editar */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD9D9D9),
                    contentColor = Color.Black
                ),
                modifier = Modifier.size(width = 80.dp, height = 32.dp)
            ) {
                Text("Editar", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun PedidoItem(nome: String, data: String, onAvaliarClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(nome, fontWeight = FontWeight.Bold)
            Text(data)
        }
        Button(
            onClick = onAvaliarClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD9D9D9),
                contentColor = Color.Black
            )
        ) {
            Text("Avaliar")
        }
    }
}

@Composable
fun PedidoAvaliadoItem(produto: String, data: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column {
            Text(produto, fontWeight = FontWeight.Bold)
            Text(data)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text("Avaliado!", fontWeight = FontWeight.Bold, color = Color.Gray)
    }
}