package com.id.ervin.genshin.paimondex.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.id.ervin.genshin.paimondex.data.entity.CharacterBriefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaimondexDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCharacter(character: CharacterBriefEntity)

    @Query("SELECT * FROM character WHERE isFavorite=1")
    fun getAllFavoriteCharacters(): Flow<List<CharacterBriefEntity>>

    @Query("SELECT * FROM character WHERE name = :input")
    fun getFavoriteCharacter(input: String): Flow<CharacterBriefEntity?>
}
