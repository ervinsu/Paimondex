package com.id.ervin.genshin.paimondex.ui.feature.characters.list

import android.graphics.Color
import com.id.ervin.genshin.paimondex.R
import com.id.ervin.genshin.paimondex.ui.MainActivity
import com.id.ervin.genshin.paimondex.util.generateTransition
import com.id.ervin.genshin.paimondex.util.loadImage
import com.id.ervin.genshin.paimondex.util.showContentIfNotLoadingAndNotError
import java.util.Locale

fun MainActivity.initDetailCharacterObserver() {
    charactersViewModel.detailState.observe(this) { state ->
        showContentIfNotLoadingAndNotError(
            state.loadingState,
            binding.clDetail.nestedScrollViewContent,
            binding.clDetail.pbCharacterDetail,
            binding.clDetail.customViewInternetError
        )
        if (state.character.name.isEmpty()) return@observe

        val character = state.character

        character.apply {
            val birthday = if (birthday.isNotEmpty())
                birthday.substring(5, birthday.count())
            else "-"

            with(binding.clDetail){
                textDescription.text = description
                ratingRarity.rating = rarity.toFloat()
                textWeapon.text = weaponType
                textVision.text = element.name
                customViewBirthday.setValue(birthday)
                customViewAffiliation.setValue(affiliation)
                customViewNation.setValue(region)
                customViewConsName.setValue(constellationName)
                imageCharacter.loadImage(imageCardUrl)
                customViewTalents.setTalents(talents)
                customViewConstellations.setTalents(constellations)
            }
            val transitionColor = generateTransition(
                resources.getColor(R.color.grey, theme),
                Color.parseColor(element.hexColor)
            )
            binding.toolbar.background = transitionColor
            transitionColor.startTransition(1000)
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

fun MainActivity.initFavoriteCharacterObserver() {
    charactersViewModel.character.observe(this) {
        setBookmark(it)
    }
}

fun MainActivity.resetToolbar() {
    setSupportActionBar(null)
}

fun MainActivity.initRetryOnError(
    characterName: String
) {
    binding.clDetail.customViewInternetError.setRetryAction {
        charactersViewModel.retryState(characterName)
    }
}
