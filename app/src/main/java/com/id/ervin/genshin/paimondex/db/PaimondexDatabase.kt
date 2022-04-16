package com.id.ervin.genshin.paimondex.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.id.ervin.genshin.paimondex.data.entity.CharacterBriefEntity

@Database(
    entities = [CharacterBriefEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PaimondexDatabase : RoomDatabase() {
    abstract fun paimondexDao(): PaimondexDao
}
