package com.id.ervin.genshin.paimondex.network

import com.id.ervin.genshin.paimondex.data.dto.CharacterDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GenshinApiService {
    /**
     * Returns a [List] of characters name on [String] data type
     */
    @GET("/characters")
    suspend fun getCharacters(): List<String>

    @GET("/characters/{charName}")
    suspend fun getCharacterDetail(@Path("charName") charName: String): CharacterDetailDto
}


