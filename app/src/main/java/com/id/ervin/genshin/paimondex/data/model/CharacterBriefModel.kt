package com.id.ervin.genshin.paimondex.data.model

import com.id.ervin.genshin.paimondex.di.BASE_URL

data class CharacterBriefModel(
    val name: String = "",
    val isFavorite: Boolean = false
) {
    val pictureUrl: String = "$BASE_URL/characters/${name.lowercase()}/icon"
}
