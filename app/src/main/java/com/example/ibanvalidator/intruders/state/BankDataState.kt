package com.example.ibanvalidator.intruders.state

import com.example.ibanvalidator.intruders.events.UIEvents
import com.example.ibanvalidator.model.Currencies
import com.example.ibanvalidator.model.IbanData

sealed class BankDataState {
    data class ValidateBankIban(val showLoader: Boolean=false,val data:IbanData=IbanData()) : BankDataState()

    data class CurrencyData(val showLoader: Boolean=false,val data:Currencies= Currencies()) : BankDataState()

}