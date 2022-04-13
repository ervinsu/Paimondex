package com.id.ervin.genshin.paimondex.ui.feature.characters.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.ervin.genshin.paimondex.data.state.CharDetailState
import com.id.ervin.genshin.paimondex.ui.feature.characters.CharactersRepository
import kotlinx.coroutines.launch

class CharacterDetailViewModel(private val repository: CharactersRepository) : ViewModel() {
    private val _detailState = MutableLiveData<CharDetailState>()
    val detailState: LiveData<CharDetailState> = _detailState

    fun fetchCharacterDetail(charName: String) {
        viewModelScope.launch {
            _detailState.value = repository.getDetailCharacter(charName)
        }
    }
}
