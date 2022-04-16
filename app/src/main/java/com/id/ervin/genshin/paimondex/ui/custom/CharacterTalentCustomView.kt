package com.id.ervin.genshin.paimondex.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.id.ervin.genshin.paimondex.data.model.CharacterTalentModel
import com.id.ervin.genshin.paimondex.databinding.CharacterTalentCustomViewBinding

class CharacterTalentCustomView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val binding: CharacterTalentCustomViewBinding =
        CharacterTalentCustomViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    fun setTalent(talent: CharacterTalentModel) {
        binding.textSkillName.text = talent.name
        binding.textSkillUnlock.text = talent.unlock
        binding.textSkillDescription.text = talent.description
    }
}
