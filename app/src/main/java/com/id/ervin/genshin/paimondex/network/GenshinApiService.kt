package com.id.ervin.genshin.paimondex.network

import retrofit2.http.GET

interface GenshinApiService {
    /**
     * Returns a [List] of characters name on [String] data type
     */
    @GET("/characters")
    suspend fun getCharacters(): List<String>
}


