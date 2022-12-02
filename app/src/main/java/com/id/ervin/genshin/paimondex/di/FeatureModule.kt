package com.id.ervin.genshin.paimondex.di

import com.id.ervin.genshin.paimondex.ui.feature.characters.detail.CharacterDetailViewModel
import com.id.ervin.genshin.paimondex.ui.feature.characters.favorite.FavoriteFragment
import com.id.ervin.genshin.paimondex.ui.feature.characters.favorite.FavoriteViewModel
import com.id.ervin.genshin.paimondex.ui.feature.characters.list.CharactersAdapter
import com.id.ervin.genshin.paimondex.ui.feature.characters.list.CharactersFragment
import com.id.ervin.genshin.paimondex.ui.feature.characters.list.CharactersViewModel
import com.id.ervin.genshin.paimondex.util.BaseRvCallback
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val charactersFeatureModule = module {
    single { CharactersViewModel(get()) }
    scope(named<CharactersFragment>()) {
        scoped { (action: BaseRvCallback) ->
            CharactersAdapter(action)
        }
    }
}

val favoriteCharacterFeatureModule = module {
    viewModel { FavoriteViewModel(get()) }
    scope(named<FavoriteFragment>()) {
        scoped { (action: BaseRvCallback) ->
            CharactersAdapter(action)
        }
    }
}

val characterDetailFeatureModule = module {
    viewModel { CharacterDetailViewModel(get()) }
}
