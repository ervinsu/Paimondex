package com.id.ervin.genshin.paimondex.data.state

import com.id.ervin.genshin.paimondex.data.model.CharacterDetailModel

data class CharDetailState(
    val loadingState: LoadingState = LoadingState(),
    val character: CharacterDetailModel = CharacterDetailModel()
)
