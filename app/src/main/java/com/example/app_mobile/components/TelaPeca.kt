package com.example.app_mobile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_mobile.R
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TelaPeca(navController: NavController, index: Int) {

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
            Text("Produto $index", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("cor x, tamanho $index", fontSize = 16.sp, color = Color.Gray)
            Text("Por: R$3,14", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "💳 Meios de Pagamento",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.clickable { /* Abrir modal*/ }
            )

            Text(
                text = "📏 Guia de Medidas",
                fontSize = 14.sp,
                color = Color(0xFF642323),
                modifier = Modifier.clickable { mostrarMedidas = !mostrarMedidas }
            )
        }

        Button(
            onClick = { /* Adicionar ação */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Adicionar na sacola")
        }

        if (mostrarMedidas) {
            SizeTable()
        }

    }

}

@Composable
fun SizeTable() {
    Column(modifier = Modifier.padding(16.dp)) {
        val sizes = listOf("P", "M", "G", "GG")
        val bust = listOf("95 CM", "98 CM", "101 CM", "104 CM")
        val waist = listOf("80 CM", "83 CM", "86 CM", "89 CM")
        val hip = listOf("102 CM", "105 CM", "108 CM", "111 CM")

        Row(Modifier.fillMaxWidth()) {
            sizes.forEach { size ->
                Text(size, Modifier.weight(1f), fontSize = 14.sp)
            }
        }
        Row(Modifier.fillMaxWidth()) {
            bust.forEach { b ->
                Text(b, Modifier.weight(1f), fontSize = 14.sp)
            }
        }
        Row(Modifier.fillMaxWidth()) {
            waist.forEach { w ->
                Text(w, Modifier.weight(1f), fontSize = 14.sp)
            }
        }
        Row(Modifier.fillMaxWidth()) {
            hip.forEach { h ->
                Text(h, Modifier.weight(1f), fontSize = 14.sp)
            }
        }
    }
}
