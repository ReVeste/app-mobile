import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app_mobile.components.api.usuario.UsuarioViewModel

@Composable
fun CriarContaScreen(
    onBack: () -> Unit = {},
    onCreateAccount: () -> Unit = {},
    navController: NavHostController
) {
    val usuarioViewModel: UsuarioViewModel = viewModel()

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }
    var carregando by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
        }

        Text("Criar conta", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        Text("Nome", fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            placeholder = { Text("Digite seu nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("CPF", fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = cpf,
            onValueChange = { cpf = it },
            placeholder = { Text("Digite seu CPF") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Telefone", fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = telefone,
            onValueChange = { telefone = it },
            placeholder = { Text("Digite seu telefone") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("E-mail", fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Digite seu e-mail") },
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
                carregando = true
                usuarioViewModel.criarConta(nome, cpf, telefone, email, senha)
                navController.navigate("TelaConta/7")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            enabled = !carregando
        ) {
            Text(if (carregando) "Criando conta..." else "Criar conta", color = Color.White)
        }
    }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ao cadastrar-se, você concorda com a Política de Privacidade e os Termos e Condições.",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }

