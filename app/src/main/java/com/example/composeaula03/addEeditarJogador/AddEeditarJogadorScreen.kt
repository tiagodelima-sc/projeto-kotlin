package com.example.composeaula03.addEeditarJogador

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composeaula03.data.Jogador

@Composable
fun AddEeditarJogadorScreen(
    navController: NavController,
    addEeditarJogadorViewModel: AddEeditarJogadorViewModel,
    onAdicionarJogador: (Jogador) -> Unit,
    onAtualizarContato: (Jogador) -> Unit,
    onRemoveContato: (Int) -> Unit,
    jogador: Jogador
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {

                if(jogador.id == -1)
                    addEeditarJogadorViewModel.adicionarJogador(onAdicionarJogador)
                else
                    addEeditarJogadorViewModel.atualizarContato(
                        jogador.id,
                        onAtualizarContato
                    )

                navController.navigate("listaJogador"){
                    popUpTo("listaJogador"){
                        inclusive = true
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Cadastrar Jogador" )
            }
        }
    ) {
        addEeditarJogadorViewModel.nome.value = jogador.nome
        addEeditarJogadorViewModel.time.value = jogador.time
        addEeditarJogadorViewModel.genero.value = jogador.genero
        
        AddEeditarJogadorForm(
            addEeditarJogadorViewModel,
            jogador.id,
            onRemoveContato,
        ) {
            navController.navigate("listaJogador"){
                popUpTo("listaJogador"){
                    inclusive = true
                }
            }
        }
    }

}

@Composable
fun AddEeditarJogadorForm(
    addEeditarJogadorViewModel: AddEeditarJogadorViewModel,
    id: Int,
    onRemoveContato: (Int) -> Unit,
    navigateBack: () -> Unit
) {

    var nome = addEeditarJogadorViewModel.nome.observeAsState()
    var time = addEeditarJogadorViewModel.time.observeAsState()
    var genero = addEeditarJogadorViewModel.genero.observeAsState()
    
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = {
                    Text(text = "Digite o nome do jogador: ")
                },
                value = "${nome.value}",
                onValueChange = { novoNome->
                    addEeditarJogadorViewModel.nome.value = novoNome
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = {
                    Text(text = "Digite o time do jogador: ")
                },
                value = "${time.value}",
                onValueChange = { novoTime->
                    addEeditarJogadorViewModel.time.value = novoTime
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Digite o gênero do jogador: ")
                },
                value = "${genero.value}",
                onValueChange = { novoGenero->
                    addEeditarJogadorViewModel.genero.value = novoGenero
                }
            )
        }
        if(id != -1)
            FloatingActionButton(
                modifier = Modifier.padding(15.dp),
                onClick = {
                    onRemoveContato(id)
                    navigateBack()
                }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Excluir"
                )
            }
    }
}

