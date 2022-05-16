package com.example.composeaula03.listaJogador

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composeaula03.data.Jogador


@Composable
fun ListaJogadorScreen(
    navController: NavController,
    listaJogadorViewModel: ListaJogadorViewModel,
)   {
    Scaffold(
        //Criando um botão de adicionar
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addEeditarJogador")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicione um Jogador" )
            }
        }
    ) {
        val listaJogador by listaJogadorViewModel.listaJogadores.observeAsState(listOf())
        val filtro by listaJogadorViewModel.procurarPor.observeAsState("")

        Column() {
            ProcurarJogador(
                filtro,
                listaJogadorViewModel::atualizarFiltro
            )
            ListaJogador(
                jogadores = listaJogador,
                navController = navController
            )
        }

    }
}

@Composable
fun ProcurarJogador(
    filtro: String,
    onEscolhaFiltro: (String) -> Unit
){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),

        label = {
                Row() {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Procurar" )
                    Text(text = "Procure algum jogador")
                }
        },
        value = filtro,
        onValueChange = onEscolhaFiltro
    )
}


@Composable
//Lista com os jogadores
fun ListaJogador(
    jogadores: List<Jogador>,
    navController: NavController
) {
    LazyColumn(){
        items(jogadores){ jogador ->
            EntradaJogador(jogador = jogador){
                navController.navigate("addEeditarJogador?id=${jogador.id}")
            }
        }
    }
}

@Composable
fun EntradaJogador(
    jogador: Jogador,
    onEditar: () -> Unit
){
    var expanded by remember {
        mutableStateOf(false)
    }
    Card(
        //Card pode ser clicado para fazer a expansão ou nao
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                expanded = !expanded
            }
            .animateContentSize( //animacoes
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column() {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .size(70.dp)
                        .background(Color.Yellow),
                    //Centralizando conteudo
                    contentAlignment = Alignment.Center

                ) {
                    //Dentro do circle
                    Icon(
                        modifier = Modifier.size(35.dp),
                        imageVector = Icons.Default.Person,
                        contentDescription = "Foto Profile"
                    )
                }
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f),
                        text = jogador.nome,
                        style = MaterialTheme.typography.h6
                            .copy(fontWeight = FontWeight.Bold)
                    )
                    //botao editar
                    if(expanded){
                        Icon(
                            modifier = Modifier
                                .padding(10.dp)
                                .size(25.dp)
                                .clickable {
                                    onEditar()
                                },
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar"
                        )
                    }

            }
            if(expanded) {
                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = "Time: " + jogador.time,
                    style = MaterialTheme.typography.h6.copy(color = Color.DarkGray)
                )

                Text(
                    modifier = Modifier.padding(start = 6.dp, bottom = 6.dp, end = 6.dp),
                    text = "Sexo: " + jogador.genero,
                    style = MaterialTheme.typography.h6.copy(color = Color.DarkGray)
                )
            }
        }
    }
}
