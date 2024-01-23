package com.example.ibanvalidator.repository


import com.example.ibanvalidator.model.Currencies
import com.example.ibanvalidator.model.IbanData
import com.example.ibanvalidator.requestinterface.ApiInterface
import com.example.ibanvalidator.requestinterface.GenericResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(private val apiInterface: ApiInterface) {

    /**Method for IBAN validation*/
    suspend fun getIbanValidationFromServer(iban: String): Flow<GenericResponse<IbanData>> = flow {
        emit(apiInterface.getIbanData(iban = iban))
    }.flowOn(Dispatchers.IO)

    /**Method for Currencies*/
    suspend fun getCurrenciesFromServer(base: String="USD"): Flow<GenericResponse<Currencies>> = flow {
        emit(apiInterface.getCurrencyData(currency = base))
    }.flowOn(Dispatchers.IO)
}