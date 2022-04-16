package com.id.ervin.genshin.paimondex.ui.feature.characters.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
import com.id.ervin.genshin.paimondex.data.state.CharDetailState
import com.id.ervin.genshin.paimondex.ui.feature.characters.CharactersRepository
import kotlinx.coroutines.launch

class CharacterDetailViewModel(private val repository: CharactersRepository) : ViewModel() {
    private val _detailState = MutableLiveData<CharDetailState>()
    val detailState: LiveData<CharDetailState> = _detailState

    private val _character: MutableLiveData<CharacterBriefModel> = MutableLiveData()
    val character: LiveData<CharacterBriefModel> = _character

    fun retryState(charName: String) {
        _detailState.value = CharDetailState(isLoading = true, isConnectionError = false)
        fetchCharacterDetail(charName)
    }

    fun saveOrUpdateCharacter(charName: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.addOrUpdateLocalFavoriteCharacter(charName, isFavorite)
        }
    }

    fun fetchCharacterDetail(charName: String) {
        viewModelScope.launch {
            _detailState.value = repository.getRemoteDetailCharacter(charName)
        }
    }

    fun getCharacter(charName: String) {
        viewModelScope.launch {
            _character.value = repository.getLocalBriefCharacter(charName)
        }
    }
}
