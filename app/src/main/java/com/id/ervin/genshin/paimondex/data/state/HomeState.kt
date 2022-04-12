package com.id.ervin.genshin.paimondex.data.state

import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel

data class HomeState(
    val isLoading: Boolean = false,
    val characters: List<CharacterBriefModel> = listOf()
)
