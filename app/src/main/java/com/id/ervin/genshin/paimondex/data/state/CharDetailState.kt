package com.id.ervin.genshin.paimondex.data.state

import com.id.ervin.genshin.paimondex.data.model.CharacterDetailModel

data class CharDetailState(
    val isLoading: Boolean = false,
    val isConnectionError: Boolean = false,
    val character: CharacterDetailModel = CharacterDetailModel()
)
