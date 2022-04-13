package com.id.ervin.genshin.paimondex

import android.app.Application
import com.id.ervin.genshin.paimondex.di.characterDetailFeatureModule
import com.id.ervin.genshin.paimondex.di.characterRepositoryModule
import com.id.ervin.genshin.paimondex.di.charactersFeatureModule
import com.id.ervin.genshin.paimondex.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class PaimondexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@PaimondexApp)
            val listModules = mutableListOf<Module>()
            listModules.add(remoteModule)
            listModules.add(characterRepositoryModule)
            listModules.add(charactersFeatureModule)
            listModules.add(characterDetailFeatureModule)
            modules(
                listModules
            )
        }
    }
}
