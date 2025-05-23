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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_mobile.components.api.usuario.UsuarioViewModel
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(
    onBack: () -> Unit = {},
    onCreateAccount: () -> Unit = {},
    navController: NavHostController
) {
    val usuarioViewModel: UsuarioViewModel = viewModel()
    var usuarioLogado = usuarioViewModel.usuarioLogado
    var userId = 0

    val erro = usuarioViewModel.erro
    val carregando = usuarioViewModel.carregando

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
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
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Senha", fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            placeholder = { Text("Digite sua senha") },
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                Log.d("Before api", "teste 1")
                usuarioViewModel.realizarLogin(email, senha)
                navController.navigate("TelaConta/1")
                Log.d("After api", "teste 2")
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
