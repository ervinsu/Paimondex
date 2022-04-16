package com.id.ervin.genshin.paimondex.data.state

import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel

data class FavoriteState(
    val loadingState: LoadingState = LoadingState(),
    val listLocalCharacter: List<CharacterBriefModel> = listOf()
)
