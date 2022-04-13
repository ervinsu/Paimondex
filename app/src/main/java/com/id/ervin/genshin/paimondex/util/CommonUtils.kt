package com.id.ervin.genshin.paimondex.util

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.util.DisplayMetrics
import android.view.View
import com.id.ervin.genshin.paimondex.data.model.CharacterBriefModel

fun Context.calculateNoOfColumn(widthColumnInFloat: Float): Int {
    val display: DisplayMetrics = resources.displayMetrics
    val screen = display.widthPixels / display.density
    return (screen / widthColumnInFloat + 0.5).toInt()
}

interface BaseRvCallback {
    fun onItemViewClicked(character: CharacterBriefModel, view: View, activity: Activity)
}

fun generateTransition(fromColor: Int, toColor: Int): TransitionDrawable {
    val backgrounds = arrayOfNulls<Drawable>(2)
    backgrounds[0] = ColorDrawable(fromColor)
    backgrounds[1] = ColorDrawable(toColor)
    return TransitionDrawable(backgrounds)
}
