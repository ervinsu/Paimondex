package com.id.ervin.genshin.paimondex.di

import com.id.ervin.genshin.paimondex.ui.fragment.characters.CharactersRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val characterRepositoryModule = module {
    single {
        CharactersRepository(get(named("genshinApiService")))
    }
}
