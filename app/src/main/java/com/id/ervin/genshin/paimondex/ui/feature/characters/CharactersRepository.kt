package com.id.ervin.genshin.paimondex.ui.feature.characters

import com.id.ervin.genshin.paimondex.data.entity.CharacterBriefEntity
import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
import com.id.ervin.genshin.paimondex.data.state.CharDetailState
import com.id.ervin.genshin.paimondex.data.state.CharactersState
import com.id.ervin.genshin.paimondex.data.state.LoadingState
import com.id.ervin.genshin.paimondex.db.GenshinLocalService
import com.id.ervin.genshin.paimondex.network.GenshinApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

class CharactersRepository(
    private val genshinApiService: GenshinApiService,
    private val genshinLocalService: GenshinLocalService
) {

    suspend fun getRemoteBriefCharacters(): CharactersState =
        withContext(Dispatchers.IO) {
            supervisorScope { // Use  supervisor job since we using async to fetch data
                val listRemoteCharacterDeferred = async { genshinApiService.getCharacters() }
                val listLocalCharacterDeferred =
                    async { genshinLocalService.getAllFavoriteCharacters() }

                try {
                    val listChars = listRemoteCharacterDeferred.await()
                    val listLocalChars = listLocalCharacterDeferred.await().first()

                    val listOfBriefCharModel = listChars.map { charName ->
                        val isFavoriteCharacter =
                            listLocalChars.firstOrNull { it.name == charName } != null
                        CharacterBriefModel(
                            name = charName,
                            isFavorite = isFavoriteCharacter
                        )
                    }
                    CharactersState(LoadingState(), listOfBriefCharModel)
                } catch (e: Exception) {
                    // TODO LOG
                    CharactersState(LoadingState(isConnectionError = true))
                }
            }

        }

    fun getLocalBriefCharacters() = flow {
        try {
            emit(CharactersState(LoadingState(isLoading = true, isConnectionError = true)))

            emitAll(
                genshinLocalService.getAllFavoriteCharacters().map {
                    val characterList = it.map { char ->
                        CharacterBriefModel(char.name, char.isFavorite)
                    }
                    CharactersState(
                        LoadingState(),
                        characterList
                    )
                })
        } catch (e: Exception) {
            // TODO LOG
            emit(CharactersState(LoadingState(isConnectionError = true)))
        }
    }

    suspend fun addOrUpdateLocalFavoriteCharacter(charName: String, isFavorite: Boolean) =
        withContext(Dispatchers.IO) {
            val updatedCharacter = genshinLocalService.getCharacter(charName).first()?.copy(
                isFavorite = isFavorite
            ) ?: CharacterBriefEntity(
                name = charName,
                isFavorite = isFavorite
            )

            genshinLocalService.postFavoriteCharacter(updatedCharacter)
        }

    suspend fun getLocalBriefCharacter(charName: String): CharacterBriefModel =
        withContext(Dispatchers.IO) {
            val charEntity =
                genshinLocalService.getCharacter(charName).first() ?: CharacterBriefEntity(charName)
            CharacterBriefModel(
                charEntity.name,
                charEntity.isFavorite
            )
        }

    suspend fun getRemoteDetailCharacter(charName: String): CharDetailState =
        withContext(Dispatchers.IO) {
            try {
                val characterDto = genshinApiService.getCharacterDetail(charName)
                CharDetailState(
                    LoadingState(),
                    characterDto.toModel(charName)
                )
            } catch (e: Exception) {
                // TODO add log
                CharDetailState(LoadingState(isConnectionError = true))
            }
        }
}
