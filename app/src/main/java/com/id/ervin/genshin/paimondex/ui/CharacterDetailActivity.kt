package com.id.ervin.genshin.paimondex.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
import com.id.ervin.genshin.paimondex.databinding.ActivityCharacterDetailBinding
import com.id.ervin.genshin.paimondex.util.loadImage
import org.koin.android.viewmodel.ext.android.viewModel

class CharacterDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterDetailBinding
    private lateinit var view: View
    private val charDetailViewModel: CharacterDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)

        val character = intent.getParcelableExtra<CharacterBriefModel>(CHARACTER_EXTRA) ?: return
        initCharacterObserver(character.name)
    }

    private fun initCharacterObserver(characterName: String) {
        charDetailViewModel.detailState.observe(this, { state ->
            if (state.isLoading) {
                // Set progress bar
            } else {

            }
            if (state.isConnectionError) {
                // Show error
                return@observe
            }
            val character = state.character
            val birthday = character.birthday.substring(5, character.birthday.count())
            binding.textDescription.text = character.description
            binding.ratingRarity.rating = character.rarity.toFloat()
            binding.textWeapon.text = character.weaponType
            binding.textVision.text = character.element
            binding.customViewBirthday.setValue(birthday)
            binding.customViewAffiliation.setValue(character.affiliation)
            binding.customViewNation.setValue(character.region)
            binding.customViewConsName.setValue(character.constellationName)
            binding.imageCharacter.loadImage(character.imageCardUrl)
        })
        charDetailViewModel.fetchCharacterDetail(characterName)
    }

    companion object {
        const val CHARACTER_EXTRA = "CHARACTER_EXTRA"
    }
}
