package com.id.ervin.genshin.paimondex.util

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun <T : RecyclerView.ViewHolder> T.onClick(action: (position: Int, view: View) -> Unit): T {
    itemView.setOnClickListener {
        action(adapterPosition, it)
    }
    return this
}

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}
