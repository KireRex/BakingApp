package com.scheffer.erik.bakingapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(val quantity: Float = 0F,
                      val measure: String = "",
                      val ingredient: String = "") : Parcelable
