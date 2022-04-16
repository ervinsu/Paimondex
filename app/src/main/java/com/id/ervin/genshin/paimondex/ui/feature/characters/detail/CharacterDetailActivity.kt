package com.id.ervin.genshin.paimondex.ui.feature.characters.detail

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.id.ervin.genshin.paimondex.R
import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
import com.id.ervin.genshin.paimondex.databinding.ActivityCharacterDetailBinding
import com.id.ervin.genshin.paimondex.util.generateTransition
import com.id.ervin.genshin.paimondex.util.loadImage
import com.id.ervin.genshin.paimondex.util.showContentIfNotLoadingAndNotError
import org.koin.android.viewmodel.ext.android.viewModel

class CharacterDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterDetailBinding
    private lateinit var view: View
    private val charDetailViewModel: CharacterDetailViewModel by viewModel()
    private var characterBriefModel = CharacterBriefModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)
        initToolbar()
        intent.getStringExtra(CHARACTER_EXTRA)?.apply {
            initDetailCharacterObserver(this)
            initRetryOnError(this)
            initFavoriteCharacterObserver(this)
        } ?: return
    }

    private fun initRetryOnError(characterName: String) {
        binding.customViewInternetError.setRetryAction {
            charDetailViewModel.retryState(characterName)
        }
    }

    private fun initFavoriteCharacterObserver(characterName: String) {
        charDetailViewModel.character.observe(this, {
            characterBriefModel = it
            setBookmark(it)
        })
        charDetailViewModel.getCharacter(characterName)

    }

    private fun initDetailCharacterObserver(characterName: String) {
        charDetailViewModel.detailState.observe(this, { state ->
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
                binding.detailToolbar.apply {
                    background = transitionColor
                    title = name
                }
                transitionColor.startTransition(1000)

                binding.customViewTalents.setTalents(talents)
                binding.customViewConstellations.setTalents(constellations)
            }
        })
        charDetailViewModel.fetchCharacterDetail(characterName)
    }

    private fun initToolbar() {
        binding.detailToolbar.inflateMenu(R.menu.character_detail)
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.character_detail, menu)
        setBookmark(characterBriefModel)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()

            }
            R.id.action_bookmark -> {
                characterBriefModel = characterBriefModel.copy(
                    isFavorite = !characterBriefModel.isFavorite
                ).apply {
                    charDetailViewModel.saveOrUpdateCharacter(name, isFavorite)
                    setBookmark(this)
                }
            }
        }
        return true
    }

    private fun setBookmark(characterBriefModel: CharacterBriefModel) {
        val menu = binding.detailToolbar.menu.getItem(0)
        if (!characterBriefModel.isFavorite) {
            menu.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_border)
        } else {
            menu.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark)
        }
    }

    companion object {
        const val CHARACTER_EXTRA = "CHARACTER_EXTRA"
    }
}
