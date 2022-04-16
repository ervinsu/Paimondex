package com.id.ervin.genshin.paimondex.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.id.ervin.genshin.paimondex.di.BASE_URL

@Entity(tableName = CharacterBriefEntity.TABLE_NAME)
data class CharacterBriefEntity(
    @PrimaryKey
    val name: String = "",
    val isFavorite: Boolean = false
) {
    @ColumnInfo
    var pictureUrl: String = "$BASE_URL/characters/${name.lowercase()}/icon"

    companion object {
        const val TABLE_NAME = "character"
    }
}
