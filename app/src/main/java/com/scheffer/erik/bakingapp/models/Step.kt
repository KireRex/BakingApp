package com.scheffer.erik.bakingapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Step(
        var id: Int = 0,
        var shortDescription: String = "",
        var description: String = "",
        var videoURL: String = "",
        var thumbnailURL: String = "") : Parcelable
