package com.id.ervin.genshin.paimondex.ui.feature.characters

import com.id.ervin.genshin.paimondex.data.entity.CharacterBriefEntity
import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
import com.id.ervin.genshin.paimondex.data.state.CharDetailState
import com.id.ervin.genshin.paimondex.db.GenshinLocalService
import com.id.ervin.genshin.paimondex.network.GenshinApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class CharactersRepository(
    private val genshinApiService: GenshinApiService,
    private val genshinLocalService: GenshinLocalService
) {

    suspend fun getBriefCharacters(): List<CharacterBriefModel> = withContext(Dispatchers.IO) {
        val listCharsApiDeferred = async { genshinApiService.getCharacters() }
        val listCharsLocalDeferred = async { genshinLocalService.getAllFavoriteCharacters() }

        val listChars = listCharsApiDeferred.await()
        val listLocalChars = listCharsLocalDeferred.await().first()

        listChars.map { charName ->
            val isFavoriteCharacter = listLocalChars.firstOrNull { it.name == charName } != null
            CharacterBriefModel(
                name = charName,
                isFavorite = isFavoriteCharacter
            )
        }
    }

    suspend fun addOrUpdateFavoriteCharacter(charName: String, isFavorite: Boolean) =
        withContext(Dispatchers.IO) {
            val updatedCharacter = genshinLocalService.getCharacter(charName).first()?.copy(
                isFavorite = isFavorite
            ) ?: CharacterBriefEntity(
                name = charName,
                isFavorite = isFavorite
            )

            genshinLocalService.postFavoriteCharacter(updatedCharacter)
        }

    suspend fun getCharacterBrief(charName: String): CharacterBriefModel =
        withContext(Dispatchers.IO) {
            val charEntity =
                genshinLocalService.getCharacter(charName).first() ?: CharacterBriefEntity(charName)
            CharacterBriefModel(
                charEntity.name,
                charEntity.isFavorite
            )
        }

    suspend fun getDetailCharacter(charName: String): CharDetailState =
        withContext(Dispatchers.IO) {
            try {
                val characterDto = genshinApiService.getCharacterDetail(charName)
                CharDetailState(
                    isLoading = false,
                    isConnectionError = false,
                    characterDto.toModel(charName)
                )
            } catch (e: Exception) {
                // TODO add log
                CharDetailState(isLoading = false, isConnectionError = true)
            }
        }
}
