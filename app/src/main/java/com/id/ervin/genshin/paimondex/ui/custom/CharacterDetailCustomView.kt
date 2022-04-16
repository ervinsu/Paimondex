package com.id.ervin.genshin.paimondex.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.id.ervin.genshin.paimondex.R
import com.id.ervin.genshin.paimondex.databinding.CharacterDetailCustomViewBinding


class CharacterDetailCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private val binding: CharacterDetailCustomViewBinding =
        CharacterDetailCustomViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CharacterDetailCustomView,
            defStyle,
            0
        )
        val variable = typedArray.getString(R.styleable.CharacterDetailCustomView_variable) ?: ""
        binding.textVariable.text = variable
        typedArray.recycle()
    }

    fun setValue(value: String) {
        binding.textValue.text = value
    }
}
