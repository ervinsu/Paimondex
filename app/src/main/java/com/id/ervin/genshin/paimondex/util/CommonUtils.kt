package com.id.ervin.genshin.paimondex.util

import android.app.Activity
import android.content.Context
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
