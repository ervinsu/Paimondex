package com.id.ervin.genshin.paimondex.data.dto

import com.id.ervin.genshin.paimondex.data.enum.Element
import com.id.ervin.genshin.paimondex.data.model.CharacterDetailModel
import com.squareup.moshi.Json

data class CharacterDetailDto(
    val name: String = "",
    @Json(name = "vision")
    val element: Element = Element.None,
    @Json(name = "weapon")
    val weaponType: String = "",
    val nation: String = "",
    val affiliation: String = "",
    val birthday: String = "",
    val rarity: Int = 0,
    @Json(name = "constellation")
    val constellationName: String = "",
    val description: String = "",
) {
    fun toModel(imageCardUrl: String): CharacterDetailModel {
        return CharacterDetailModel(
            name,
            element.name,
            weaponType,
            nation,
            affiliation,
            birthday,
            rarity,
            constellationName,
            description,
            imageCardUrl
        )
    }
}
