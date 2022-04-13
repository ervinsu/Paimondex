package com.id.ervin.genshin.paimondex.di

import com.id.ervin.genshin.paimondex.ui.fragment.characters.CharactersAdapter
import com.id.ervin.genshin.paimondex.ui.fragment.characters.CharactersFragment
import com.id.ervin.genshin.paimondex.ui.fragment.characters.CharactersViewModel
import com.id.ervin.genshin.paimondex.util.BaseRvCallback
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val charactersFeatureModule = module {
    viewModel { CharactersViewModel(get()) }
    scope(named<CharactersFragment>()) {
        scoped { (action: BaseRvCallback) ->
            CharactersAdapter(action)
        }
    }
}
