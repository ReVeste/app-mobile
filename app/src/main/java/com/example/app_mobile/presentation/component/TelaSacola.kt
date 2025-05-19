package com.example.app_mobile.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.app_mobile.R
import com.example.app_mobile.presentation.viewmodel.TelaSacolaViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TelaSacola(navController: NavController,
               viewModel : TelaSacolaViewModel = koinViewModel()) {

    val itens = viewModel.produtosCarrinho

    Scaffold(
        topBar = { CustomHeader(itens.size, navController) },
        bottomBar = { FooterPagamento() }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            if (itens.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.mensagem_pedido_vazio),
                    fontSize = 18.sp,
                    color = Color.Black
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(itens) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(item.imagens.firstOrNull()),
                                contentDescription = "Imagem do produto",
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.White)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = item.nome,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "cor preta, ${item.tamanho}",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Por: R$${item.preco}",
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = stringResource(id = R.string.botao_remover),
                                fontSize = 12.sp,
                                color = Color.Gray,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .clickable {
                                        viewModel.removerProdutoPedido(
                                            idPedido  = item.idPedido,
                                            idProduto = item.id
                                        )
                                    }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.LightGray)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FooterPagamento() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Button(
            onClick = { /* ação de seguir pagamento */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(text = stringResource(id = R.string.header_sacola), color = Color.White)
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
        append(text = stringResource(id = R.string.header_sacola))
        addStyle(SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold), 0, length)

        if (qtdItens > 0) {
            val itemText = " ($qtdItens itens)"
            append(itemText)
            addStyle(SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal), length - itemText.length, length)
        }
    }
}