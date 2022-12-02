package com.id.ervin.genshin.paimondex.ui.feature.characters.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
import com.id.ervin.genshin.paimondex.data.state.CharDetailState
import com.id.ervin.genshin.paimondex.data.state.CharactersState
import com.id.ervin.genshin.paimondex.data.state.LoadingState
import com.id.ervin.genshin.paimondex.ui.feature.characters.CharactersRepository
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _detailState = MutableLiveData<CharDetailState>()
    val detailState: LiveData<CharDetailState> = _detailState

    private val _character: MutableLiveData<CharacterBriefModel> = MutableLiveData()
    val character: LiveData<CharacterBriefModel> = _character

    private val _charactersState: MutableLiveData<CharactersState> = MutableLiveData()
    val charactersState: LiveData<CharactersState> = _charactersState

    fun getCharacters() {
        viewModelScope.launch {
            _charactersState.value = CharactersState(LoadingState(isLoading = true))
            _charactersState.value = repository.getRemoteBriefCharacters()
        }
    }

    fun retryState(charName: String) {
        _detailState.value = CharDetailState(LoadingState(isLoading = true))
        fetchCharacterDetail(charName)
    }

    fun saveOrUpdateCharacter(charName: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.addOrUpdateLocalFavoriteCharacter(charName, isFavorite)
        }
    }

    fun fetchCharacterDetail(charName: String) {
        viewModelScope.launch {
            _detailState.value = CharDetailState(LoadingState(isLoading = true))
            _detailState.value = repository.getRemoteDetailCharacter(charName)
        }
    }

    fun getCharacter(charName: String) {
        viewModelScope.launch {
            _character.value = repository.getLocalBriefCharacter(charName)
        }
    }
}
