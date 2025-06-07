package com.example.app_mobile.presentation.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.app_mobile.R
import com.example.app_mobile.domain.model.Feedback
import com.example.app_mobile.presentation.viewmodel.TelaAvaliarViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TelaAvaliar(
    navController: NavController,
    idPedido: Int,
    viewModel: TelaAvaliarViewModel = koinViewModel()
) {
    LaunchedEffect(idPedido) {
        viewModel.buscarPedidoPorId(idPedido)
    }

    val pedidoAvaliar = viewModel.pedido
    var comentario by remember { mutableStateOf("") }
    val imagensSelecionadas = remember { mutableStateListOf<Any?>(null, null, null, null) }
    val avaliado = viewModel.avaliado


    var indexParaSubstituir by remember { mutableStateOf(-1) }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            if (indexParaSubstituir in imagensSelecionadas.indices) {
                imagensSelecionadas[indexParaSubstituir] = it.toString()
            }
        }
    }

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

            Text(
                text = "icone earthmoon",
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { navController.navigate("TelaSacola") }) {
                Icon(
                    painter = painterResource(id = R.drawable.sacolaicon),
                    contentDescription = "Carrinho",
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        val imagensPedido = pedidoAvaliar?.produtos?.get(0)?.imagens?.take(4) ?: emptyList()

        val imagensCompletasPedido = buildList {
            addAll(imagensPedido)
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

                painter?.let {
                    Image(
                        painter = it,
                        contentDescription = "Imagem do pedido",
                        modifier = Modifier
                            .size(250.dp)
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(pedidoAvaliar?.produtos?.get(0)?.nome ?: "...", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Tamanho: ${pedidoAvaliar?.produtos?.get(0)?.tamanho ?: "..."}", fontSize = 16.sp, color = Color.Gray)
        }

        // Área de texto
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Avaliação do comprador:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            androidx.compose.material.OutlinedTextField(
                value = comentario,
                onValueChange = { comentario = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                placeholder = { Text("Envie sua avaliação!") }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            imagensSelecionadas.forEachIndexed { index, imagem ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.LightGray)
                        .clickable {
                            indexParaSubstituir = index
                            imageLauncher.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    val painter = when (imagem) {
                        is String -> rememberAsyncImagePainter(model = imagem)
                        is Int -> painterResource(id = imagem)
                        else -> null
                    }

                    painter?.let {
                        Image(
                            painter = it,
                            contentDescription = "Imagem selecionada",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        androidx.compose.material3.Button(
            onClick = {
                viewModel.criarAvaliacao(comentario, idPedido, viewModel.usuarioLogado.userId, imagensSelecionadas)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            enabled = !avaliado
        ) {
            Text(if (avaliado) "Feedback enviado!" else "Enviar")
        }

        if(avaliado) {
            Text(
                text = "Obrigado por avaliar!",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

    }
}