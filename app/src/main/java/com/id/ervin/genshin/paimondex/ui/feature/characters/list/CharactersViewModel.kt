package com.id.ervin.genshin.paimondex.ui.feature.characters.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.ervin.genshin.paimondex.data.state.CharactersState
import com.id.ervin.genshin.paimondex.ui.feature.characters.CharactersRepository
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _charactersState: MutableLiveData<CharactersState> = MutableLiveData()

    val charactersState: LiveData<CharactersState> = _charactersState

    fun getCharacters() {
        viewModelScope.launch {
            _charactersState.value = CharactersState(false, repository.getRemoteBriefCharacters())
        }
    }
}
