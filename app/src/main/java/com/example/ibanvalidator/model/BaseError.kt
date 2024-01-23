package com.example.ibanvalidator.model

import com.google.gson.annotations.SerializedName

data class BaseError(
        val code: String="0",
        val detail: String="",
        @SerializedName("message")
        val message: String = ""
    )
