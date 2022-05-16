package com.example.composeaula03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composeaula03.addEeditarJogador.AddEeditarJogadorScreen
import com.example.composeaula03.addEeditarJogador.AddEeditarJogadorViewModel
import com.example.composeaula03.listaJogador.ListaJogadorScreen
import com.example.composeaula03.listaJogador.ListaJogadorViewModel
import com.example.composeaula03.ui.theme.ComposeAula03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listaJogadorViewModel: ListaJogadorViewModel by viewModels()
        val addEeditarJogadorViewModel: AddEeditarJogadorViewModel by viewModels()

        setContent {
            ComposeAula03Theme {
                // A surface container using the 'background' color from the theme
                MyApp(
                    listaJogadorViewModel,
                    addEeditarJogadorViewModel,
                )
            }
        }
    }
}


@Composable
fun MyApp(
    listaJogadorViewModel: ListaJogadorViewModel,
    addEeditarJogadorViewModel: AddEeditarJogadorViewModel
) {
    val navController = rememberNavController()

    Scaffold(){ //Andaime para construir a aplicação
        NavHost(navController = navController, startDestination = "listaJogador"){
            composable("listaJogador"){
                ListaJogadorScreen(navController, listaJogadorViewModel)
            }
            composable(
                route = "addEeditarJogador?id={id}",
                arguments = listOf(navArgument("id"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ){ navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id") ?: -1
                val jogador = listaJogadorViewModel.pegarContato(id)
                AddEeditarJogadorScreen(
                    navController,
                    addEeditarJogadorViewModel,
                    listaJogadorViewModel::addJogador,
                    listaJogadorViewModel::atualizarContato,
                    listaJogadorViewModel::removerContato,
                    jogador
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ComposeAula03Theme {

    }
}