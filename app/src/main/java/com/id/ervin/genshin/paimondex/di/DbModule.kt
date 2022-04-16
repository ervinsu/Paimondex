package com.id.ervin.genshin.paimondex.di

import androidx.room.Room
import com.id.ervin.genshin.paimondex.db.GenshinLocalService
import com.id.ervin.genshin.paimondex.db.PaimondexDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dbModule = module {
    factory { get<PaimondexDatabase>().paimondexDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("Paimon".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            PaimondexDatabase::class.java, "Paimondex.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
    single(named("genshinLocalService")) { GenshinLocalService(get()) }
}
