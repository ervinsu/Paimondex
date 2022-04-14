package com.id.ervin.genshin.paimondex.ui.feature.characters.detail

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.id.ervin.genshin.paimondex.R
import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
import com.id.ervin.genshin.paimondex.databinding.ActivityCharacterDetailBinding
import com.id.ervin.genshin.paimondex.util.generateTransition
import com.id.ervin.genshin.paimondex.util.gone
import com.id.ervin.genshin.paimondex.util.loadImage
import com.id.ervin.genshin.paimondex.util.visible
import org.koin.android.viewmodel.ext.android.viewModel

class CharacterDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterDetailBinding
    private lateinit var view: View
    private val charDetailViewModel: CharacterDetailViewModel by viewModel()
    private lateinit var characterName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)

        characterName =
            intent.getParcelableExtra<CharacterBriefModel>(CHARACTER_EXTRA)?.name ?: return
        initCharacterObserver(characterName)
        initToolbar()
        initBinding(characterName)
    }

    private fun initBinding(characterName: String) {
        binding.customViewInternetError.setRetryAction {
            charDetailViewModel.retryState(characterName)
        }
    }

    private fun initCharacterObserver(characterName: String) {
        charDetailViewModel.detailState.observe(this, { state ->
            if (state.isConnectionError) {
                binding.customViewInternetError.visible()
                binding.pbCharacterDetail.gone()
                return@observe
            } else {
                binding.customViewInternetError.gone()
                if (state.isLoading) {
                    binding.pbCharacterDetail.visible()
                    return@observe
                } else {
                    binding.pbCharacterDetail.gone()
                }
            }

            binding.nestedScrollViewContent.visible()
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
                val transition = generateTransition(
                    resources.getColor(R.color.grey, theme),
                    Color.parseColor(element.hexColor)
                )
                binding.detailToolbar.apply {
                    background = transition
                    title = name
                }
                transition.startTransition(1000)
            }
        })
        charDetailViewModel.fetchCharacterDetail(characterName)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val CHARACTER_EXTRA = "CHARACTER_EXTRA"
    }
}
