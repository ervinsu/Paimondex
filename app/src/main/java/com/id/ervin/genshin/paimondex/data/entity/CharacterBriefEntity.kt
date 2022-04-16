package com.id.ervin.genshin.paimondex.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CharacterBriefEntity.TABLE_NAME)
data class CharacterBriefEntity(
    @PrimaryKey
    val id: Int,
    val name: String = "",
    val pictureUrl: String = "",
    val isFavorite: Boolean = false
) {
    companion object {
        const val TABLE_NAME = "CHARACTER_TABLE"
    }
}
