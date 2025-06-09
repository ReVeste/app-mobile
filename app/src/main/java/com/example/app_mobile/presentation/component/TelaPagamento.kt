package com.example.app_mobile.presentation.component

import android.annotation.SuppressLint
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app_mobile.R
import com.example.app_mobile.presentation.viewmodel.TelaSacolaViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("DefaultLocale")
@Composable
fun TelaPagamento(navController: NavController,
                  viewModel : TelaSacolaViewModel = koinViewModel()
) {

//    LaunchedEffect(Unit) {
//        viewModel.criarPreferencia()
//    }

    val itens = viewModel._produtosCarrinho
    val opcoesFrete = viewModel.opcoesFrete

    val subTotal = itens.sumOf { it.preco }

    val freteMaisBarato = opcoesFrete
        .filter { it.custom_price != null && it.error == null }
        .minByOrNull { it.custom_price.toDouble() }

    val frete = freteMaisBarato?.custom_price?.toDoubleOrNull() ?: 0.0
    val transportadora = freteMaisBarato?.name ?: "N/A"
    val total = subTotal + frete

    val context = LocalContext.current
    val init = viewModel.initPoint

    var podeAvancar by remember { mutableStateOf(false) }
    var mostrarMensagem by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {

        PagamentoHeader(navController = navController)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(itens) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val imagemPrincipal = item.imagens.firstOrNull()

                    if (imagemPrincipal != null) {
                        AsyncImage(
                            model = imagemPrincipal,
                            contentDescription = item.nome,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = "Sem imagem",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text("${item.nome} (${item.tamanho})", fontWeight = FontWeight.Bold)
                        Text("R$ %.2f".format(item.preco))
                    }
                }
            }

            item {
                Divider()

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text("Subtotal: R$ %.2f".format(subTotal))
                    Text("Transportadora: ${transportadora.uppercase()}")
                    Text("Frete: R$ %.2f".format(frete))
                    Text(
                        "Total: R$ %.2f".format(total),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        init?.let { link ->
                            val customTabsIntent = CustomTabsIntent.Builder().build()
                            customTabsIntent.launchUrl(context, Uri.parse(link))
                        }
                        podeAvancar = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600))
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pagar com o Mercado Pago", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                Text(
                    "Parcelamento com cartão ou Linha de Crédito",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    onClick = {
                        viewModel.atualizarPedidoPago()
                        mostrarMensagem = true
                    },
                    enabled = podeAvancar,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (podeAvancar) Color(0xFF007AFF) else Color.Gray
                    )
                ) {
                    Text("Concluir", color = Color.White, fontWeight = FontWeight.Bold)
                }

                if (mostrarMensagem) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Pedido efetuado!",
                        color = Color(0xFF4CAF50),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Baixe o aplicativo dos Correios para ser notificado sobre o envio do pedido!\n\nObrigada por confiar no Earth Moon!",
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagamentoHeader(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Resumo do pedido",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigate("TelaPrincipal") }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.White
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        )
    )
}