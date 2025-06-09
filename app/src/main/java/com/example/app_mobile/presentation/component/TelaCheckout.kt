package com.example.app_mobile.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app_mobile.data.network.api.CriarEnderecoDto
import com.example.app_mobile.domain.model.EnderecoDto
import com.example.app_mobile.presentation.viewmodel.TelaSacolaViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCheckout(navController: NavController,
                 viewModel : TelaSacolaViewModel = koinViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.buscarEnderecos()
    }

    val enderecosCadastrados = viewModel._enderecos

    var apelido by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var rua by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    val numeroInt: Int? = numero.toIntOrNull()
    var complemento by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var enderecoSelecionado by remember { mutableStateOf<EnderecoDto?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {

    CheckoutHeader(navController = navController)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        if (enderecosCadastrados.isNotEmpty()) {
            Text(text = "Selecione um endereço salvo:", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = enderecoSelecionado?.apelido ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Apelido do endereço") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    enderecosCadastrados.forEach { endereco ->
                        DropdownMenuItem(
                            text = { Text(endereco.apelido) },
                            onClick = {
                                enderecoSelecionado = endereco
                                expanded = false

                                apelido = endereco.apelido
                                cep = endereco.cep
                                rua = endereco.rua
                                numero = endereco.numero.toString()
                                complemento = endereco.complemento
                                bairro = endereco.bairro
                                cidade = endereco.cidade
                                uf = endereco.uf
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = apelido,
                    onValueChange = { apelido = it },
                    label = { Text("Apelido") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = cep,
                    onValueChange = { cep = it },
                    label = { Text("CEP") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = rua,
                    onValueChange = { rua = it },
                    label = { Text("Rua") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = numero,
                    onValueChange = { numero = it },
                    label = { Text("Número") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = complemento,
                onValueChange = { complemento = it },
                label = { Text("Complemento") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = bairro,
                    onValueChange = { bairro = it },
                    label = { Text("Bairro") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = cidade,
                    onValueChange = { cidade = it },
                    label = { Text("Cidade") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uf,
                onValueChange = { uf = it },
                label = { Text("UF") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            val mensagemErro = remember { mutableStateOf<String?>(null) }

            if (mensagemErro.value != null) {
                Text(
                    text = mensagemErro.value!!,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    if (apelido.isBlank() || cep.isBlank() || rua.isBlank() || numero.isBlank()
                        || bairro.isBlank() || cidade.isBlank() || uf.isBlank()
                    ) {
                        mensagemErro.value = "Preencha todos os campos obrigatórios."
                    } else if (!uf.matches(Regex("^[A-Z]{2}$"))) {
                        mensagemErro.value = "UF deve conter apenas duas letras maiúsculas (ex: SP)."
                    } else {
                        mensagemErro.value = null

                        if (numeroInt != null) {
                            viewModel.cadastrarEndereco(apelido, cep, rua, numeroInt,
                                complemento, bairro, cidade, uf)
                            navController.navigate("TelaPagamento")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0084FF),
                    contentColor = Color.White
                )
            ) {
                Text("Próximo")
            }

        }
    }
}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutHeader(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Informe o endereço de entrega",
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
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