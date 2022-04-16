package com.id.ervin.genshin.paimondex.util

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.id.ervin.genshin.paimondex.R
import com.id.ervin.genshin.paimondex.data.state.LoadingState

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
        .apply(
            RequestOptions.placeholderOf(R.drawable.paimon_loading).error(R.drawable.paimon_failed)
        )
        .into(this)
}

fun View.createSnackBar(text: String, snackBarLength: Int) {
    Snackbar.make(this, text, snackBarLength).show()
}

fun showContentIfNotLoadingAndNotError(
    loadingState: LoadingState,
    contentView: View,
    loadingView: View,
    errorView: View
) {
    if (loadingState.isConnectionError) {
        errorView.visible()
        loadingView.gone()
        return
    } else {
        errorView.gone()
        if (loadingState.isLoading) {
            loadingView.visible()
            return
        } else {
            loadingView.gone()
        }
    }
    contentView.visible()
}
