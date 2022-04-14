package com.id.ervin.genshin.paimondex.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.id.ervin.genshin.paimondex.databinding.InternetErrorCustomViewBinding

class InternetErrorCustomView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private var binding: InternetErrorCustomViewBinding =
        InternetErrorCustomViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    fun setRetryAction(action: (view: View) -> Unit) {
        binding.buttonRetry.setOnClickListener {
            action(it)
        }
    }
}
