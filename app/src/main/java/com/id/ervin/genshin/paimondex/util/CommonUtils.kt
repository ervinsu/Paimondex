package com.id.ervin.genshin.paimondex.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View

fun Context.calculateNoOfColumn(widthColumnInFloat: Float): Int {
    val display: DisplayMetrics = resources.displayMetrics
    val screen = display.widthPixels / display.density
    return (screen / widthColumnInFloat + 0.5).toInt()
}

interface BaseRvCallback {
    fun onItemViewClicked(position: Int, view: View, activity: Activity)
}
