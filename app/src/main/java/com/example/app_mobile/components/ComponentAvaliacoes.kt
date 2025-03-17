package com.example.app_mobile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ComponentAvaliacoes(
    imageResId: Int,
    nome: String,
    especificacao: String
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(300.dp)
            .border(1.dp, color = Color(0xffacacac), RoundedCornerShape(5.dp))
            .background(Color.White),
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Imagem",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .background(Color.White)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(6.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = nome,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = especificacao,
                    color = Color.Black,
                    fontSize = 12.sp
                )
            }
        }
    }

}