package com.example.ibanvalidator.extentions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach


suspend fun <T> Flow<T>.subscribeThem(
    onError: (exception: Throwable) -> Unit,
    onNext: (T) -> Unit,
    onComplete: () -> Unit,
    onCollect: (T) -> Unit
) {

    this.onEach {
        onNext(it)
    }.catch { throwable ->
        onError(throwable)
    }.onCompletion { cause: Throwable? ->
        if (cause == null) {
            onComplete()
        }
    }.collect {
        onCollect(it)
    }

}

fun Context.showToastLong(message: String) {
    this.let {
        (this as Activity).runOnUiThread {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }
}



fun Context.hasNetwork(): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}




