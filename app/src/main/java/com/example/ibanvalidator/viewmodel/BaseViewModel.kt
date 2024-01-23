package com.example.ibanvalidator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ibanvalidator.model.BaseError
import com.example.ibanvalidator.model.FailureResponse
import com.example.ibanvalidator.remote.Event
import com.example.ibanvalidator.remote.NetworkResponse

import kotlinx.coroutines.*
import java.io.IOException

open class BaseViewModel : ViewModel() {


    // failureLiveData
    var failureResponseLiveData: MutableLiveData<Event<FailureResponse>> = MutableLiveData()


  protected  fun handleError(data: NetworkResponse<*, BaseError>) {
        when (data) {
            is NetworkResponse.ApiError -> {
                showApiError(data.body)
            }
            is NetworkResponse.NetworkError -> {
                showNetworkError(data.error)
            }
            is NetworkResponse.UnknownError -> {
                showUnknownError(data.error)
            }
            else-> showUnknownError(Throwable("Unknown Error"))

        }
    }

    private fun showApiError(error: BaseError) {
        failureResponseLiveData.value =
            Event(FailureResponse(error.code.toInt(), error.message, error.detail))

    }

    private fun showNetworkError(exception: IOException) {
        failureResponseLiveData.value =
            Event(FailureResponse(0, "No internet connection", exception.localizedMessage))
    }

    private fun showUnknownError(t: Throwable?) {
        if (t != null) {
            failureResponseLiveData.value =
                Event(FailureResponse(0, t.message ?: "", t.localizedMessage))
        }
    }

    fun <T : Any> ioThenMain( work: suspend (() -> T?),  callback: ((T?) -> Unit)? ): Job =
        viewModelScope.launch(Dispatchers.Main) {
            val data = viewModelScope.async(Dispatchers.IO) {
                return@async work()
            }.await()
            callback?.let {
                it(data)
            }
        }


    fun handlerWithDelay(includeDelay: Boolean = false, delay: Long = 0, work: () -> Unit): Job =
        viewModelScope.launch(Dispatchers.Main) {
            if (includeDelay) {
                delay(delay)
                work()
            } else work()
        }

     fun startCoroutineTimer(delayMillis: Long = 0, initialDelay: Long = 0,  action: () -> Unit) = viewModelScope.launch {
        delay(initialDelay)
            while (true) {
                action()
                delay(delayMillis)
            }
        }
    }
