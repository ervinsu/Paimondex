package com.id.ervin.genshin.paimondex.ui.feature.characters.list

import android.graphics.Color
import com.id.ervin.genshin.paimondex.R
import com.id.ervin.genshin.paimondex.ui.MainActivity
import com.id.ervin.genshin.paimondex.util.generateTransition
import com.id.ervin.genshin.paimondex.util.loadImage
import com.id.ervin.genshin.paimondex.util.showContentIfNotLoadingAndNotError
import java.util.Locale

fun MainActivity.initDetailCharacterObserver(
    charactersViewModel: CharactersViewModel
) {
    charactersViewModel.detailState.observe(this) { state ->
        showContentIfNotLoadingAndNotError(
            state.loadingState,
            binding.nestedScrollViewContent,
            binding.pbCharacterDetail,
            binding.customViewInternetError
        )
        if (state.character.name.isEmpty()) return@observe

        val character = state.character

        character.apply {
            val birthday = if (birthday.isNotEmpty())
                birthday.substring(5, birthday.count())
            else "-"

            binding.textDescription.text = description
            binding.ratingRarity.rating = rarity.toFloat()
            binding.textWeapon.text = weaponType
            binding.textVision.text = element.name
            binding.customViewBirthday.setValue(birthday)
            binding.customViewAffiliation.setValue(affiliation)
            binding.customViewNation.setValue(region)
            binding.customViewConsName.setValue(constellationName)
            binding.imageCharacter.loadImage(imageCardUrl)
            val transitionColor = generateTransition(
                resources.getColor(R.color.grey, theme),
                Color.parseColor(element.hexColor)
            )
            binding.toolbar.background = transitionColor
            transitionColor.startTransition(1000)

            binding.customViewTalents.setTalents(talents)
            binding.customViewConstellations.setTalents(constellations)
        }
    }
}

fun MainActivity.upgradeToolbar(
    characterName: String
) {
    with(binding.toolbar) {
        title = characterName.replaceFirstChar {
            it.titlecase(Locale.getDefault())
        }
    }
}

fun MainActivity.initFavoriteCharacterObserver(
    charactersViewModel: CharactersViewModel
) {
    charactersViewModel.character.observe(this) {
        characterBriefModel = it
        setBookmark(characterBriefModel)
    }
}

fun MainActivity.resetToolbar() {
    setSupportActionBar(null)
}

fun MainActivity.initRetryOnError(
    charactersViewModel: CharactersViewModel,
    characterName: String
) {
    binding.customViewInternetError.setRetryAction {
        charactersViewModel.retryState(characterName)
    }
}
