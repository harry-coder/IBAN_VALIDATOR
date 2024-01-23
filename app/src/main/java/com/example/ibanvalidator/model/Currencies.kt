package com.example.ibanvalidator.model

import com.google.gson.annotations.SerializedName

data class Currencies(
    @SerializedName("data")
    val data: Map<String, String> = hashMapOf()
)
