package com.scheffer.erik.bakingapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
        val id: Int = 0,
        val name: String = "",
        val ingredients: List<Ingredient> = ArrayList(),
        val steps: List<Step> = ArrayList(),
        val servings: Int = 0,
        val image: String = "") : Parcelable
