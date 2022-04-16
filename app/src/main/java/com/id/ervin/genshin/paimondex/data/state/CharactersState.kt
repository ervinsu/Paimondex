package com.id.ervin.genshin.paimondex.data.state

import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel

data class CharactersState(
    val loadingState: LoadingState = LoadingState(),
    val characters: List<CharacterBriefModel> = listOf()
)
