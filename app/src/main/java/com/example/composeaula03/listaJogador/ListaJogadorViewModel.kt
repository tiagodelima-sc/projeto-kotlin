package com.example.composeaula03.listaJogador

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeaula03.data.Jogador

class ListaJogadorViewModel : ViewModel() {

    //Lista iniciada
    private val _listaJogadores: MutableLiveData<List<Jogador>> = MutableLiveData(
        listOf(
            Jogador(
                0,
                "Cristiano Ronaldo",
                "Manchester United - Inglaterra",
                "Masculino"
            ),
            Jogador(
                1,
                "Neymar Jr",
                "Paris Saint-Germain  - França",
                "Masculino"
            ),
            Jogador(
                2,
                "James Rodríguez",
                "Everton - Inglaterra",
                "Masculino"
            ),
            Jogador(
                3,
                "Andrés Iniesta",
                "Vissel Kobe - Japão",
                "Masculino"
            ),
            Jogador(
                4,
                "Megan Rapinoe",
                "OL Reign - Estados Unidos",
                "Feminino"
            ),
            Jogador(
                5,
                "Alex Morgan",
                "Orlando Pride - Estados Unidos",
                "Feminino"
            )
        )
    )

    private val _procurarPor: MutableLiveData<String> = MutableLiveData("")

    val procurarPor: LiveData<String>
        get() = _procurarPor

    //lista com get para eu obter essa lista
    val listaJogadores: LiveData<List<Jogador>>
        get() {
            return if (_procurarPor.value == "")
                _listaJogadores
            else {
                val lista: List<Jogador> = _listaJogadores.value?.filter { jogador ->
                    jogador.nome.contains(_procurarPor.value ?: "")
                } ?: listOf()
                MutableLiveData(lista)
            }
        }

    fun atualizarFiltro(novoFiltro: String) {
        _procurarPor.value = novoFiltro
    }

    //fun de inserir jogadores
    fun addJogador(jogador: Jogador) {
        val lista: MutableList<Jogador> = _listaJogadores.value?.toMutableList() ?: return
        lista.add(jogador)
        _listaJogadores.value = lista
    }

    fun atualizarContato(atualizarJogador: Jogador){
        var pos = -1
        _listaJogadores.value?.forEachIndexed{ index, jogador ->  
            if(atualizarJogador.id == jogador.id)
                pos = index
        }
        //adiciona lista temporaria
        val lista: MutableList<Jogador> = _listaJogadores.value?.toMutableList() ?: return
        lista.removeAt(pos)
        lista.add(pos, atualizarJogador)
        //atualiza na lista de contato
        _listaJogadores.value = lista
    }

    fun removerContato(id: Int){
        var pos = -1
        _listaJogadores.value?.forEachIndexed{ index, jogador ->
            if(id == jogador.id)
                pos = index
        }
        val lista: MutableList<Jogador> = _listaJogadores.value?.toMutableList() ?: return
        lista.removeAt(pos)
        //remove na lista de contato
        _listaJogadores.value = lista
    }

    fun pegarContato(id: Int): Jogador {
        _listaJogadores.value?.forEach { jogador ->
            if(id == jogador.id)
                return jogador
        }
            return Jogador(
                -1,
                "",
                "",
                ""
            )
    }
}




