package com.id.ervin.genshin.paimondex.ui.fragment.characters

import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
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
}
