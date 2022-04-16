package com.id.ervin.genshin.paimondex.ui.feature.characters.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.id.ervin.genshin.paimondex.ui.feature.characters.CharactersRepository

class FavoriteViewModel(
    repository: CharactersRepository
) : ViewModel() {
    val favoriteState = repository.getLocalBriefCharacters().asLiveData()
}
