package com.id.ervin.genshin.paimondex.di

import androidx.room.Room
import com.id.ervin.genshin.paimondex.db.GenshinLocalService
import com.id.ervin.genshin.paimondex.db.PaimondexDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dbModule = module {
    single { get<PaimondexDatabase>().paimondexDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            PaimondexDatabase::class.java, "Paimondex.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
    single(named("genshinLocalService")) { GenshinLocalService(get()) }
}
