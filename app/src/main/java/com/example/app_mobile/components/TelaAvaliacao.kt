package com.example.app_mobile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.app_mobile.R
import com.example.app_mobile.components.api.feedback.Feedback
import com.example.app_mobile.components.api.feedback.FeedbackViewModel

@Composable
fun TelaAvaliacao(navController: NavController, index: Int){

    val feedbackViewModel = remember { FeedbackViewModel() }
    var feedback by remember { mutableStateOf<Feedback?>(null) }

    LaunchedEffect(index) {
        feedback = feedbackViewModel.buscarPorId(index)
    }

    var mostrarMedidas by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
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

        val images = listOf(
            R.drawable.camisetakiss, R.drawable.camisetakiss,
            R.drawable.camisetakiss, R.drawable.camisetakiss
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(images) { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Imagem do Produto",
                    modifier = Modifier
                        .size(250.dp)
                        .padding(horizontal = 8.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Produto ${feedback?.id ?: "..."}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("cor x, tamanho y", fontSize = 16.sp, color = Color.Gray)
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Avaliação do comprador:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(feedback?.usuario?.nome ?: "Carregando nome...", fontSize = 16.sp, color = Color.Gray)
            Text(feedback?.comentario ?: "Carregando comentário...", fontSize = 16.sp, color = Color.Gray)
        }

        val imagesAvaliacao = listOf(
            R.drawable.camisetakiss, R.drawable.camisetakiss,
            R.drawable.camisetakiss, R.drawable.camisetakiss
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(imagesAvaliacao) { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Imagem do Produto",
                    modifier = Modifier
                        .size(250.dp)
                        .padding(horizontal = 8.dp)
                )
            }
        }

    }

}