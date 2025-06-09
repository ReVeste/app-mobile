package com.example.app_mobile.presentation.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.navigation.NavHostController
import com.example.app_mobile.domain.model.SessaoUsuario
import com.example.app_mobile.presentation.viewmodel.TelaLoginCadastroViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBack: () -> Unit = {},
    onCreateAccount: () -> Unit = {},
    navController: NavHostController,
    viewModel : TelaLoginCadastroViewModel = koinViewModel()
) {

    var usuarioLogado = koinInject<SessaoUsuario>()

    if(usuarioLogado.token != null) {
        navController.navigate("TelaConta/${usuarioLogado.userId}")
    }

    val erro = viewModel.erro
    val carregando = viewModel.carregando

    var email by remember { mutableStateOf("ketelyn.medina@sptech.school") }
    var senha by remember { mutableStateOf("123456") }
    var senhaVisivel by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
        }

        Text("Minha conta", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        Text("E-mail", fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Digite seu e-mail") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Senha", fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            placeholder = { Text("Digite sua senha") },
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                Log.d("Before api", "teste 1")
                viewModel.realizarLogin(email, senha)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            enabled = !carregando
        ) {
            Text(if (carregando) "Entrando..." else "Entrar", color = Color.White)
        }

        if (erro != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(erro, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Ainda não tem uma conta?", fontWeight = FontWeight.Medium)

        OutlinedButton(
            onClick = onCreateAccount,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text("Quero criar uma conta")
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}
