package com.id.ervin.genshin.paimondex.data.model

import com.id.ervin.genshin.paimondex.data.enum.Element
import com.id.ervin.genshin.paimondex.di.BASE_URL

data class CharacterDetailModel(
    val name: String = "",
    val simpleName: String = "",
    val element: Element = Element.None,
    val weaponType: String = "",
    val region: String = "",
    val affiliation: String = "",
    val birthday: String = "",
    val rarity: Int = 0,
    val constellationName: String = "",
    val description: String = "",
    val talents: List<CharacterTalentModel> = listOf(),
    val constellations: List<CharacterTalentModel> = listOf(),
    val isFavorite: Boolean = false
) {
    val imageCardUrl = "$BASE_URL/characters/${simpleName.lowercase()}/card"
}
