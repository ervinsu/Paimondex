package com.id.ervin.genshin.paimondex.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.id.ervin.genshin.paimondex.R
import com.id.ervin.genshin.paimondex.data.model.CharacterTalentModel
import com.id.ervin.genshin.paimondex.databinding.CharacterExpandableTalentCustomViewBinding
import com.id.ervin.genshin.paimondex.util.gone
import com.id.ervin.genshin.paimondex.util.visible

class CharacterExpandableTalentCustomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) :
    ConstraintLayout(context, attributeSet) {
    private val binding: CharacterExpandableTalentCustomViewBinding =
        CharacterExpandableTalentCustomViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    private var isExpanded = true

    init {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.CharacterExpandableTalentCustomView,
            defStyle,
            0
        )
        val headerText =
            typedArray.getString(R.styleable.CharacterExpandableTalentCustomView_headerText) ?: ""
        binding.textTalent.text = headerText
        typedArray.recycle()

        binding.textTalent.setOnClickListener {
            if (isExpanded) {
                isExpanded = false
                binding.groupTalent.gone()
                binding.imageExpandable.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_arrow_up
                    )
                )
            } else {
                isExpanded = true
                binding.groupTalent.visible()
                binding.imageExpandable.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_arrow_down
                    )
                )
            }
        }
    }

    fun setTalents(talents: List<CharacterTalentModel>) {
        if (talents.size < 6)
            return
        binding.customViewFirstTalent.setTalent(talents[0])
        binding.customViewSecondTalent.setTalent(talents[1])
        binding.customViewThirdTalent.setTalent(talents[2])
        binding.customViewFourthTalent.setTalent(talents[3])
        binding.customViewFifthTalent.setTalent(talents[4])
        binding.customViewSixthTalent.setTalent(talents[5])
    }
}
