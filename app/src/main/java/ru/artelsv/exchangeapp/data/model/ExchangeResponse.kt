package ru.artelsv.exchangeapp.data.model

import com.google.gson.annotations.SerializedName

data class ExchangeResponse(

    @SerializedName("success")
    val success: Boolean,
    @SerializedName("symbols")
    val symbols: Map<String, String>
)