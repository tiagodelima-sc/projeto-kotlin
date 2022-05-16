package com.example.composeaula03.addEeditarJogador

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeaula03.data.Jogador

class AddEeditarJogadorViewModel : ViewModel() {

    private val _id : MutableLiveData<Int> = MutableLiveData(6)
    val nome : MutableLiveData<String> = MutableLiveData("")
    val time : MutableLiveData<String> = MutableLiveData("")
    val genero : MutableLiveData<String> = MutableLiveData("")

    fun adicionarJogador(
        //Recebendo função para add no viewmodel
        adicionarJogadorViewModel: (Jogador) -> Unit
    ){
        val novoJogador = Jogador(
            _id.value ?: return,
            nome.value ?: return,
            time.value ?: return,
            genero.value ?: return
        )
        adicionarJogadorViewModel(novoJogador)
        var tempId: Int = _id.value ?: return
        tempId++
        _id.value = tempId

        nome.value = ""
        time.value = ""
        genero.value = ""

    }

    fun atualizarContato(
        id: Int,
        onAtualizarContato: (Jogador) -> Unit
    ){
        val jogador = Jogador(
            id,
            nome.value ?: return,
            time.value ?: return,
            genero.value ?: return
        )

        onAtualizarContato(jogador)
    }
}