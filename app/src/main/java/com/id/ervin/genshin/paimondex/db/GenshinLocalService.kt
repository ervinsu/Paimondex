package com.id.ervin.genshin.paimondex.db

import com.id.ervin.genshin.paimondex.data.entity.CharacterBriefEntity

class GenshinLocalService(private val paimondexDao: PaimondexDao) {
    // Avoid delete row, use replace instead
    suspend fun postFavoriteCharacter(character: CharacterBriefEntity) =
        paimondexDao.insertFavoriteCharacter(character)

    fun getAllFavoriteCharacters() = paimondexDao.getAllFavoriteCharacters()

    fun getCharacter(charName: String) = paimondexDao.getFavoriteCharacter(charName)
}
