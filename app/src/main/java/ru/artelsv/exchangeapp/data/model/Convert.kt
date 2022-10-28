package ru.artelsv.exchangeapp.data.model

import com.google.gson.annotations.SerializedName

data class Convert(
    @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    val rates: Map<String, Double>
)
