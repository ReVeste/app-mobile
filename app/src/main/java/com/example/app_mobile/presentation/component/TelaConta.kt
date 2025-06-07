package com.example.app_mobile.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.LaunchedEffect
import com.example.app_mobile.presentation.viewmodel.TelaContaViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


@Composable
fun TelaConta(
              navController: NavController,
              viewModel: TelaContaViewModel = koinViewModel()
) {

    val usuario = viewModel.usuarioLogado

    LaunchedEffect(usuario) {
        if (usuario.userId == null) {
            navController.navigate("TelaLogin") {
                popUpTo("TelaConta") { inclusive = true }
            }
        }

        viewModel.buscarPedidosFinalizados()
        viewModel.buscarPedidosAvaliados()
    }

    val pedidosFinalizados = viewModel._finalizados
    val pedidosAvaliados = viewModel._avaliados

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate("TelaPrincipal") }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
            }
            Text(text = "Minha conta", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        usuario.let {
            InformacoesUsuario(nome = it.nome, email = it.email)
        }

        Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth())

        Text("Histórico de pedidos", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyColumn(
            modifier = Modifier.height(200.dp)
        ) {
            items(pedidosFinalizados.size) { pedido ->
                PedidoItem(
                    navController = navController,
                    nome = "Pedido #${pedido+1}",
                    data = pedidosFinalizados[pedido].dataHora,
                    idPedido = pedidosFinalizados[pedido].id,
                    onAvaliarClick = { /* ação para avaliar */ }
                )
            }
        }

        Divider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp))

        Text("Pedidos avaliados", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyColumn(
            modifier = Modifier.height(200.dp)
        ) {
            items(pedidosAvaliados.size) { pedido ->
                PedidoAvaliadoItem(
                    produto = "Avaliação #${pedido}",
                    data = pedidosAvaliados[pedido].dataHora
                )
            }
        }
    }

}

@Composable
fun InformacoesUsuario(nome: String?, email: String?) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Nome", fontWeight = FontWeight.Bold)
                Text(nome?: "Nome Usuário")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Endereço de e-mail", fontWeight = FontWeight.Bold)
                Text(email?: "usuario@example.com")
            }
        }
    }
}

@Composable
fun PedidoItem(navController: NavController, nome: String, data: String, idPedido: Int, onAvaliarClick: () -> Unit) {

    fun formatarData(dataIso: String): String {
        val formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val formatterSaida = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy 'às' HH:mm")
            .withLocale(Locale("pt", "BR"))

        val data = LocalDateTime.parse(dataIso, formatterEntrada)
        return data.format(formatterSaida)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(nome, fontWeight = FontWeight.Bold)
            Text(formatarData(data))
        }
        Button(
            onClick = { navController.navigate("TelaAvaliar/${idPedido}") },
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

    fun formatarData(dataIso: String): String {
        val formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val formatterSaida = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy 'às' HH:mm")
            .withLocale(Locale("pt", "BR"))

        val data = LocalDateTime.parse(dataIso, formatterEntrada)
        return data.format(formatterSaida)
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column {
            Text(produto, fontWeight = FontWeight.Bold)
            Text(formatarData(data))
        }
        Spacer(modifier = Modifier.weight(1f))
        Text("Avaliado!", fontWeight = FontWeight.Bold, color = Color.Gray)
    }
}