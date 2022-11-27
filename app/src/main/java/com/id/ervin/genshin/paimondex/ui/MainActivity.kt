package com.id.ervin.genshin.paimondex.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.id.ervin.genshin.paimondex.R
import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
import com.id.ervin.genshin.paimondex.databinding.ActivityMainBinding
import com.id.ervin.genshin.paimondex.ui.feature.characters.list.CharactersViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var transitionComplete = false
    val charactersViewModel: CharactersViewModel by viewModel()
    var characterBriefModel = CharacterBriefModel()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.bnvMenu

        binding.nestedScrollViewContent.setOnTouchListener { _, event ->
            binding.mlMain.onTouchEvent(event)
            return@setOnTouchListener false
        }

        binding.toolbar.setOnTouchListener { _, event ->
            binding.mlMain.onTouchEvent(event)
            return@setOnTouchListener false
        }
        binding.mlMain.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                // do nothing
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                // do nothing
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                transitionComplete = true
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
                // do nothing
            }

        })

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_characters, R.id.nav_favorite, R.id.nav_about
            )
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        initToolbar()
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            inflateMenu(R.menu.character_detail)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.character_detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_bookmark -> {
                        characterBriefModel = characterBriefModel.copy(
                            isFavorite = !characterBriefModel.isFavorite
                        ).apply {
                            charactersViewModel.saveOrUpdateCharacter(name, isFavorite)
                            setBookmark(this)
                        }
                        true
                    }
                    android.R.id.home -> {
                        onBackPressed()
                        true
                    }
                    else -> false
                }
            }
        }
        addMenuProvider(menuProvider)
    }

    fun setBookmark(characterBriefModel: CharacterBriefModel) {
        val menu = binding.toolbar.menu.getItem(0)
        if (!characterBriefModel.isFavorite) {
            menu.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_border)
        } else {
            menu.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark)
        }
    }

    override fun onBackPressed() {
        if (transitionComplete) {
            binding.mlMain.transitionToState(R.id.start)
            transitionComplete = false
        } else {
            super.onBackPressed()
        }
    }
}
