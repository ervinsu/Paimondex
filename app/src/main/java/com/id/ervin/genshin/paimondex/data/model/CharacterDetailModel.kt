package com.id.ervin.genshin.paimondex.data.model

import com.id.ervin.genshin.paimondex.data.enum.Element

data class CharacterDetailModel(
    val name: String = "",
    val element: Element = Element.NONE,
    val weaponType: String = "",
    val nation: String = "",
    val affiliation: String = "",
    val rarity: Int = 0,
    val constellationName: String = "",
    val description: String = "",
)
