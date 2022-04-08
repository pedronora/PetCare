package com.project.petcare.model.breedsapiobjects

import com.google.gson.annotations.SerializedName

data class Breeds(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("temperament")
    val temperament: String,
    @SerializedName("image")
    val image: Image,
    @SerializedName("height")
    val height: Height,
    @SerializedName("life_span")
    val life_span: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("bred_for")
    val bredFor: String
)
