package com.example.ibanvalidator.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.ibanvalidator.BuildConfig
import com.example.ibanvalidator.intruders.events.UIEvents
import com.example.ibanvalidator.intruders.state.BankDataState
import com.example.ibanvalidator.remote.NetworkResponse
import com.example.ibanvalidator.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CurrencyViewModel"

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val dataMutableFlow = MutableStateFlow(BankDataState.ValidateBankIban())
    val dataFlow = dataMutableFlow.asStateFlow()

    private val currencyMutableFlow = MutableStateFlow(BankDataState.CurrencyData())
    val currencyFlow = currencyMutableFlow.asStateFlow()

    var selectedItem = mutableStateOf("")


    init {
        getCurrencies()
    }

    fun onEvents(events: UIEvents) {
        when (events) {
            is UIEvents.IbanValidate -> {
                getIbanData(iban = events.task)
            }

            is UIEvents.ConvertCurrency -> {
                getCurrencies(base = events.item)
            }
        }
    }

    private fun getIbanData(iban: String) {
        viewModelScope.launch {
            repository.getIbanValidationFromServer(iban = iban)
                .collectLatest {
                    when (it) {
                        is NetworkResponse.Success -> {
                            dataMutableFlow.value =
                                BankDataState.ValidateBankIban(showLoader = false, data = it.body)
                        }
                       else -> {
                            handleError(it)
                        }
                    }
                }
        }
    }

    private fun getCurrencies(base: String = "USD") {
        viewModelScope.launch {
            //val key= BuildConfig.

            repository.getCurrenciesFromServer(base=base)
                .collectLatest {
                when (it) {
                    is NetworkResponse.Success -> {
                        currencyMutableFlow.value = BankDataState.CurrencyData(data = it.body)
                    }
                    else -> {
                        handleError(it)
                    }
                }
            }

        }

    }

}






