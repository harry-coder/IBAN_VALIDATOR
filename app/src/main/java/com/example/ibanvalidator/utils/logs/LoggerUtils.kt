package com.example.ibanvalidator.utils.logs

import android.util.Log

object LoggerUtils {
    // Verbose Logs
    @JvmStatic
    fun logVerbose(key: String, value: String?) {
        Log.v(key, value ?: "")
    }
}