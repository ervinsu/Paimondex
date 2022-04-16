package com.id.ervin.genshin.paimondex.data.dto

import com.id.ervin.genshin.paimondex.data.enum.Element
import com.id.ervin.genshin.paimondex.data.model.CharacterDetailModel
import com.id.ervin.genshin.paimondex.data.model.CharacterTalentModel
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
    val skillTalents: List<CharacterTalentModel> = listOf(),
    val passiveTalents: List<CharacterTalentModel> = listOf(),
    val constellations: List<CharacterTalentModel> = listOf(),

    ) {
    fun toModel(simpleName: String): CharacterDetailModel {
        return CharacterDetailModel(
            name,
            simpleName,
            element,
            weaponType,
            nation,
            affiliation,
            birthday,
            rarity,
            constellationName,
            description,
            skillTalents + passiveTalents,
            constellations
        )
    }
}
