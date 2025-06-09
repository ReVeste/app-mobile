package com.example.app_mobile.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.app_mobile.R
import com.example.app_mobile.domain.model.Feedback
import com.example.app_mobile.presentation.viewmodel.TelaAvaliacaoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TelaAvaliacao(
    navController: NavController,
    index: Int,
    viewModel: TelaAvaliacaoViewModel = koinViewModel()){

    LaunchedEffect(Unit) {
        viewModel.buscarPorId(index)
    }

    val feedback = viewModel.feedback
    val pedido = viewModel.pedido

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar"
                )
            }

            Image(
                painter = painterResource(id = R.drawable.earthmoonicon),
                contentDescription = "Ícone Earth Moon",
                modifier = Modifier.size(55.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { navController.navigate("TelaSacola") }) {
                Icon(
                    painter = painterResource(id = R.drawable.sacolaicon),
                    contentDescription = "Carrinho",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        val imagensPedido = pedido?.produtos?.get(0)?.imagens
            ?.take(4)
            ?: emptyList()

        val imagensCompletasPedido = buildList {
            addAll(imagensPedido)

            val faltantes = 4 - size
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(imagensCompletasPedido) { image ->
                val painter = when (image) {
                    else -> rememberAsyncImagePainter(model = image)
                }

                painter.let {
                    Image(
                        painter = it,
                        contentDescription = "Imagem do Produto",
                        modifier = Modifier
                            .size(250.dp)
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(pedido?.produtos?.get(0)?.nome ?: "...", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Avaliação do comprador:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(feedback?.usuario?.nome ?: "Carregando nome...", fontSize = 18.sp, color = Color.Black)
            Text(feedback?.comentario ?: "Carregando comentário...", fontSize = 16.sp, color = Color.Gray)
        }

        val imagensAvaliacao = feedback?.imagensFeedbacks
            ?.map { it.imagemUrl }
            ?.take(4)
            ?: emptyList()

        val imagensCompletasAvaliacao = buildList {
            addAll(imagensAvaliacao)

        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(imagensCompletasAvaliacao) { image ->
                val painter = when (image) {
                    else -> rememberAsyncImagePainter(model = image)
                }

                painter.let {
                    Image(
                        painter = it,
                        contentDescription = "Imagem da Avaliação",
                        modifier = Modifier
                            .size(250.dp)
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }

    }

}