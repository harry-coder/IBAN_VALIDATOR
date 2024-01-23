package com.example.ibanvalidator.model


import com.google.gson.annotations.SerializedName

data class IbanData(
    @SerializedName("bank_data")
    val bankData: BankData = BankData(),
    @SerializedName("country_iban_example")
    val countryIbanExample: String = "",
    @SerializedName("iban")
    val iban: String = "",
    @SerializedName("iban_data")
    val ibanData: IbanData = IbanData(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("valid")
    val valid: Boolean = false
) {
    data class BankData(
        @SerializedName("bank_code")
        val bankCode: String = "",
        @SerializedName("bic")
        val bic: String = "",
        @SerializedName("city")
        val city: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("zip")
        val zip: String = ""
    )

    data class IbanData(
        @SerializedName("account_number")
        val accountNumber: String = "",
        @SerializedName("BBAN")
        val bBAN: String = "",
        @SerializedName("bank_code")
        val bankCode: String = "",
        @SerializedName("branch")
        val branch: String = "",
        @SerializedName("checksum")
        val checksum: String = "",
        @SerializedName("country")
        val country: String = "",
        @SerializedName("country_code")
        val countryCode: String = "",
        @SerializedName("country_iban_format_as_regex")
        val countryIbanFormatAsRegex: String = "",
        @SerializedName("country_iban_format_as_swift")
        val countryIbanFormatAsSwift: String = "",
        @SerializedName("national_checksum")
        val nationalChecksum: String = "",
        @SerializedName("sepa_country")
        val sepaCountry: Boolean = false
    )
}