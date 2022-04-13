package com.id.ervin.genshin.paimondex.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
import com.id.ervin.genshin.paimondex.databinding.ActivityCharacterDetailBinding
import com.id.ervin.genshin.paimondex.util.createSnackBar

class CharacterDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterDetailBinding
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)

        val character = intent.getParcelableExtra<CharacterBriefModel>(CHARACTER_EXTRA) ?: return
        view.createSnackBar(character.name, Snackbar.LENGTH_LONG)
    }

    companion object {
        const val CHARACTER_EXTRA = "CHARACTER_EXTRA"
    }
}
