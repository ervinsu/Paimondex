package com.id.ervin.genshin.paimondex.home.ui.characters

import android.annotation.SuppressLint
import android.app.Activity
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.id.ervin.genshin.paimondex.R
import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel
import com.id.ervin.genshin.paimondex.databinding.CharacterItemBinding
import com.id.ervin.genshin.paimondex.util.BaseRvCallback
import com.id.ervin.genshin.paimondex.util.loadImage
import com.id.ervin.genshin.paimondex.util.onClick
import java.util.Locale

class CharactersAdapter(private val listener: BaseRvCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listCharacter = mutableListOf<CharacterBriefModel>()
    private var mLastClickTime: Long = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setCharacters(characters: List<CharacterBriefModel>) {
        listCharacter.clear()
        listCharacter.addAll(characters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.character_item,
            parent,
            false
        )
        @Suppress("NAME_SHADOWING")
        return CharacterViewHolder(view).onClick { position: Int, view: View ->
            val activity = view.context as? Activity ?: return@onClick

            /**
             * make sure it can't be doubled click
             */
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@onClick
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            listener.onItemViewClicked(position, view, activity)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = listCharacter[position]
        (holder as CharacterViewHolder).bind(character)
    }

    override fun getItemCount(): Int = listCharacter.size

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CharacterItemBinding.bind(view)
        fun bind(character: CharacterBriefModel) {
            binding.characterName.text = character.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            binding.itemImage.loadImage(character.pictureUrl)
        }

    }
}
