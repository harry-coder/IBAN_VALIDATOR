package com.example.ibanvalidator.intruders.events

sealed class UIEvents {
    data class IbanValidate(val task: String) : UIEvents()
    data class ConvertCurrency(val item: String) : UIEvents()
}