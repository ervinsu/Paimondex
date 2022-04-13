package com.id.ervin.genshin.paimondex.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterBriefModel(
    val name: String = "",
    val pictureUrl: String = "",
) : Parcelable
