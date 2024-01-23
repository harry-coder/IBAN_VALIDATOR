package com.example.ibanvalidator.requestinterface

import com.example.ibanvalidator.BuildConfig
import com.example.ibanvalidator.model.BaseError
import com.example.ibanvalidator.model.Currencies
import com.example.ibanvalidator.model.IbanData
import com.example.ibanvalidator.remote.NetworkResponse

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {
   @GET("iban_validate")
   @Headers("apikey: ${BuildConfig.API_KEY_1}")
   suspend fun getIbanData(@Query("iban_number")iban:String): GenericResponse<IbanData>

   @GET("https://api.freecurrencyapi.com/v1/latest")
   @Headers("apikey: ${BuildConfig.API_KEY_2}")
   suspend fun getCurrencyData(@Query("base_currency")currency:String): GenericResponse<Currencies>


}


typealias GenericResponse<S> = NetworkResponse<S, BaseError>