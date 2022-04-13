package com.id.ervin.genshin.paimondex.ui.fragment.characters

import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
import com.id.ervin.genshin.paimondex.data.state.CharDetailState
import com.id.ervin.genshin.paimondex.di.BASE_URL
import com.id.ervin.genshin.paimondex.network.GenshinApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharactersRepository(
    private val genshinApiService: GenshinApiService
) {

    suspend fun getBriefCharacters(): List<CharacterBriefModel> = withContext(Dispatchers.IO) {
        val listChars = genshinApiService.getCharacters()
        listChars.map {
            CharacterBriefModel(
                it,
                "$BASE_URL/characters/$it/icon"
            )
        }
    }

    suspend fun getDetailCharacter(charName: String): CharDetailState =
        withContext(Dispatchers.IO) {
            try {
                val imageCardUrl = "$BASE_URL/characters/$charName/card"
                val characterDto = genshinApiService.getCharacterDetail(charName)
                CharDetailState(
                    isLoading = false,
                    isConnectionError = false,
                    characterDto.toModel(imageCardUrl)
                )
            } catch (e: Exception) {
                // TODO add log
                CharDetailState(isLoading = false, isConnectionError = true)
            }
        }
}
