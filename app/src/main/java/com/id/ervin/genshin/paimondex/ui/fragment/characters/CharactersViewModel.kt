package com.id.ervin.genshin.paimondex.ui.fragment.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.ervin.genshin.paimondex.data.state.HomeState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val charactersRepository: CharactersRepository
) : ViewModel() {

    private val _homeState: MutableLiveData<HomeState> = MutableLiveData()

    val homeState: LiveData<HomeState> = _homeState

    fun getCharacters() {
        viewModelScope.launch {
            val deferredGetChar = async { charactersRepository.getBriefCharacters() }
            _homeState.value = HomeState(false, deferredGetChar.await())
        }
    }
}
